package com.example.liuteng.livedemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.bokecc.sdk.mobile.live.DWLive;
import com.bokecc.sdk.mobile.live.DWLive.PlayMode;
import com.bokecc.sdk.mobile.live.DWLiveListener;
import com.bokecc.sdk.mobile.live.DWLivePlayer;
import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.pojo.Answer;
import com.bokecc.sdk.mobile.live.pojo.ChatMessage;
import com.bokecc.sdk.mobile.live.pojo.PrivateChatInfo;
import com.bokecc.sdk.mobile.live.pojo.QualityInfo;
import com.bokecc.sdk.mobile.live.pojo.Question;
import com.bokecc.sdk.mobile.live.pojo.Viewer;
import com.bokecc.sdk.mobile.live.rtc.RtcClient;
import com.bokecc.sdk.mobile.live.util.HttpUtil;
import com.bokecc.sdk.mobile.live.widget.DocView;
import com.example.liuteng.livedemo.adapter.MyChatListViewAdapter;
import com.example.liuteng.livedemo.adapter.MyGridViewAdapter;
import com.example.liuteng.livedemo.adapter.MyQAListViewAdapter;
import com.example.liuteng.livedemo.model.QAMsg;
import com.example.liuteng.livedemo.util.AppRTCAudioManager;
import com.example.liuteng.livedemo.view.BarrageLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.EglBase;
import org.webrtc.SurfaceViewRenderer;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnInfoListener;

public class LiveRoomActivity extends FragmentActivity implements SurfaceHolder.Callback, IMediaPlayer.OnPreparedListener, IMediaPlayer.OnVideoSizeChangedListener, IMediaPlayer.OnErrorListener, IMediaPlayer.OnBufferingUpdateListener, OnClickListener, IMediaPlayer.OnCompletionListener, OnInfoListener {

    private DWLivePlayer player;
    private SurfaceView sv;
    private SurfaceHolder holder;
    private DWLive dwLive;
    private DocView docView;

    private Switch swiPublicPrivate;
    private ImageButton sendMsgBtn;
    private Button btnFullScreen, changeSource, changeSoundVideo, changeQuality;
    private ImageButton sendQABtn;
    private EditText etMsg, etQA;
    private TextView tvCount;
    private ListView lvChat, lvQA;
    private MyChatListViewAdapter chatAdapter;
    private MyQAListViewAdapter qaAdapter;
    private List<ChatMessage> chatMsgs = new ArrayList<ChatMessage>();
    private RelativeLayout rlPlay;

    private boolean isSendPublicChatMsg = false;
    private int mPlaySourceCount = 0;
    private List<QualityInfo> mQualityInfoList;
    private PopupWindow mPopupWindow;
    private int sourceChangeCount = 0;
    private boolean isStop = false;

    private LinearLayout llFullscreen;
    private EditText etFullscreen;
    private Button btnFullscreenSendMsg;
    private BarrageLayout mBarrageLayout;
    private RadioButton rbChat, rbPic, rbQa, rbConnectMic;
    private RadioGroup rgTitle;
    private List<RadioButton> rbs = new ArrayList<RadioButton>();

    private LinkedHashMap<String, QAMsg> qaMap = new LinkedHashMap<String, QAMsg>();
    private MyHandler handler;


    private class MyHandler extends Handler {
        WeakReference<LiveRoomActivity> weakReference;

        public MyHandler(LiveRoomActivity liveRoomActivity) {
            weakReference = new WeakReference<LiveRoomActivity>(liveRoomActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get() == null) {
                return;
            }
            switch (msg.what) {
                case PUBLIC_MSG:
                    ChatMessage publicMsg = (ChatMessage) msg.obj;

                    if (mBarrageLayout.getVisibility() == View.VISIBLE) {
                        mBarrageLayout.addNewInfo(publicMsg.getMessage());
                    }

                    chatMsgs.add(publicMsg);
                    chatAdapter.notifyDataSetChanged();
                    lvChat.setSelection(chatMsgs.size() - 1);
                    break;
                case PRIVATE_QUESTION_MSG:
                case PRIVATE_ANSWER_MSG:
                    ChatMessage privateMsg = (ChatMessage) msg.obj;
                    chatMsgs.add(privateMsg);
                    chatAdapter.notifyDataSetChanged();
                    lvChat.setSelection(chatMsgs.size() - 1);
                    break;
                case QUESTION:
                    Question question = (Question) msg.obj;
                    String questionId = question.getId();
                    if (!qaMap.containsKey(questionId)) {
                        QAMsg qaMsg = new QAMsg();
                        qaMsg.setQuestion(question);
                        qaMap.put(questionId, qaMsg);
                        qaAdapter.notifyDataSetChanged();
                        lvQA.setSelection(qaMap.size() - 1);
                    }
                    break;
                case ANSWER:
                    Answer answer = (Answer) msg.obj;
                    String qaId = answer.getQuestionId();
                    int indexQa = new ArrayList<String>(qaMap.keySet()).indexOf(qaId);
                    if (indexQa == -1) {
                        return; //没有收到answer对应的问题，直接返回
                    }
                    QAMsg qaMsg = qaMap.get(qaId);
                    qaMsg.setAnswer(answer);
                    qaAdapter.notifyDataSetChanged();
                    lvQA.setSelection(indexQa);
                    break;
                case USER_COUNT:
                    tvCount.setText("在线：" + (Integer) msg.obj + "人");
                    break;
                case FINISH:
                    if (isFinish) {
                        return;
                    }
                    player.pause();
                    player.stop();
                    setHolderBlack("直播结束");
                    initSpeakPage();
                    break;
                case KICK_OUT:
                    Toast.makeText(getApplicationContext(), "已被踢出", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

    ;

    private List<View> pagerViewList = new ArrayList<View>();

    // 初始化tab页
    private void initPagerItemView() {
        LayoutInflater inflater = LayoutInflater.from(this);

        View chatView = inflater.inflate(R.layout.chat_layout, null);
        pagerViewList.add(chatView);
        initChatLayout(chatView);

        View picView = inflater.inflate(R.layout.pic_layout, null);
        pagerViewList.add(picView);
        initPicLayout(picView);

        View qaView = inflater.inflate(R.layout.qa_layout, null);
        pagerViewList.add(qaView);
        initQaLayout(qaView);

        View connectMicView = inflater.inflate(R.layout.cm_layout, null);
        pagerViewList.add(connectMicView);
        initCmLayout(connectMicView);
    }

    private void initChatLayout(View view) {
        swiPublicPrivate = (Switch) view.findViewById(R.id.swi);
        swiPublicPrivate.performClick();

        sendMsgBtn = (ImageButton) view.findViewById(R.id.btn_msg);
        sendMsgBtn.setOnClickListener(LiveRoomActivity.this);

        lvChat = (ListView) view.findViewById(R.id.lv_chat);

        etMsg = (EditText) view.findViewById(R.id.et_msg);
        etMsg.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendChatMsg(true);
                }
                return false;
            }
        });

        etMsg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showNormalKeyBoard();
            }
        });

        initFace(view);
        initSmileKeyboard(view);
    }

    private GridView gvEmoji;

    // 初始化表情的布局
    private void initFace(View view) {
        gvEmoji = (GridView) view.findViewById(R.id.gv_face);
        gvEmoji.setAdapter(new MyGridViewAdapter(this, etMsg));
    }

    private ImageView ivSmile, ivKeyBoard;

    private void initSmileKeyboard(View view) {
        ivSmile = (ImageView) view.findViewById(R.id.iv_smile);
        ivSmile.setOnClickListener(this);
        ivKeyBoard = (ImageView) view.findViewById(R.id.iv_keyboard);
        ivKeyBoard.setOnClickListener(this);
    }

    private void initLvChat() {
        chatAdapter = new MyChatListViewAdapter(this, viewer, chatMsgs);
        lvChat.setAdapter(chatAdapter);
    }

    private void hideEditTextSoftInput(EditText editText) {
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void hideKeyBoardEditTextSoftInput() {
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void hideFocusKeyBoard() {
        View view = getCurrentFocus();
        if (view != null) {
            IBinder binder = view.getWindowToken();
            if (binder != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    private void showBottomEditTextSoftInput() {
        imm.showSoftInput(etMsg, 0);
    }

    private void initPicLayout(View view) {
        docView = (DocView) view.findViewById(R.id.live_docView);
    }

    private LinearLayout llApply;
    private TextView tvCmInfo, tvCmTips, tvCmVideoTips;
    private Button btnApplyVideo, btnDisconnectCm;

    private void initCmLayout(View v) {
        llApply = (LinearLayout) v.findViewById(R.id.ll_apply);
        tvCmInfo = (TextView) v.findViewById(R.id.tv_cm);

        btnApplyVideo = (Button) v.findViewById(R.id.btn_connect_video);
        btnApplyVideo.setOnClickListener(cmOnclickListener);

        btnDisconnectCm = (Button) v.findViewById(R.id.btn_disconnect_cm);
        btnDisconnectCm.setOnClickListener(cmOnclickListener);

        tvCmTips = (TextView) findViewById(R.id.tv_cm_tips);
        tvCmVideoTips = (TextView) findViewById(R.id.tv_cm_video_tips);
    }

    private void hideCmLayout() {
        llApply.setVisibility(View.INVISIBLE);
        tvCmInfo.setVisibility(View.INVISIBLE);
        btnDisconnectCm.setVisibility(View.INVISIBLE);
    }

    boolean isVideo = false;
    private OnClickListener cmOnclickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_connect_video:

                    if (!isNetworkConnected()) {
                        Toast.makeText(getApplicationContext(), "没有网络，请检查", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    hideCmLayout();

                    isVideo = true;
                    tvCmInfo.setVisibility(View.VISIBLE);
                    tvCmInfo.setText("视频" + getText(R.string.cm_applying));
                    tvCmVideoTips.setVisibility(View.VISIBLE);
                    tvCmVideoTips.setText("申请中……");
                    localRender.setVisibility(View.VISIBLE);
                    dwLive.startRtcConnect();
                    break;
                case R.id.btn_disconnect_cm:
                    hideCmLayout();
                    dwLive.disConnectSpeak();
                    llApply.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };


    private void initQaLayout(View view) {
        sendQABtn = (ImageButton) view.findViewById(R.id.btn_qa);
        sendQABtn.setOnClickListener(LiveRoomActivity.this);

        lvQA = (ListView) view.findViewById(R.id.lv_qa);
        etQA = (EditText) view.findViewById(R.id.et_qa);
    }

    private void initLvQa() {
        qaAdapter = new MyQAListViewAdapter(this, viewer, qaMap);
        lvQA.setAdapter(qaAdapter);
    }


    private void initPager() {

        mPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                rbs.get(arg0).setChecked(true);
                hideFocusKeyBoard();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        mPager.setAdapter(new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(pagerViewList.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = pagerViewList.get(position);
                container.addView(view);
                if (position == 0) {
                    etMsg.requestFocus();
                }
                return view;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return pagerViewList.size();
            }
        });
    }

    private Viewer viewer;
    private ViewPager mPager;
    private InputMethodManager imm;
    private WindowManager wm;
    private String chatStr, pdfStr, qaStr;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.room_live);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //禁止锁屏

        handler = new MyHandler(this);

        // 初始化audioManager，控制当前声音模式为普通模式
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_NORMAL);

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        chatStr = bundle.getString("chat");
        pdfStr = bundle.getString("pdf");
        qaStr = bundle.getString("qa");

        initCurrentView();
        initPagerItemView();
        initPlayer();
        initDwLive();
        initRoomShow();
        HttpUtil.LOG_LEVEL = HttpUtil.HttpLogLevel.DETAIL;
    }

    private LinearLayout llBottomLayout;
    private TextView tvPlayMsg;
    private ImageView ivBack;
    private RelativeLayout rlPlayTop;
    private SurfaceViewRenderer localRender, remoteRender;
    private boolean isSpeaking = false;

    private void initCurrentView() {

        // 连麦使用的布局
        localRender = (SurfaceViewRenderer) findViewById(R.id.gsv1);
        remoteRender = (SurfaceViewRenderer) findViewById(R.id.gsv2);

        EglBase rootEglBase = EglBase.create();
        localRender.init(rootEglBase.getEglBaseContext(), null);
        remoteRender.init(rootEglBase.getEglBaseContext(), null);

        localRender.setMirror(true);
        localRender.setZOrderMediaOverlay(true); // 设置让本地摄像头置于最顶层

        rlPlayTop = (RelativeLayout) findViewById(R.id.rl_play_top);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        llBottomLayout = (LinearLayout) findViewById(R.id.ll_bottom_layout);
        tvPlayMsg = (TextView) findViewById(R.id.tv_play_msg);
        mPager = (ViewPager) findViewById(R.id.pager);

        llFullscreen = (LinearLayout) findViewById(R.id.ll_fullscreen_msg_send);

        etFullscreen = (EditText) findViewById(R.id.et_fullscreen);
        etFullscreen.setOnClickListener(this);
        btnFullscreenSendMsg = (Button) findViewById(R.id.btn_fullscreen_send);
        btnFullscreenSendMsg.setOnClickListener(this);

        mBarrageLayout = (BarrageLayout) findViewById(R.id.bl_barrage);

        tvCount = (TextView) findViewById(R.id.tv_count);

        btnFullScreen = (Button) findViewById(R.id.btn_full_screen);
        btnFullScreen.setOnClickListener(this);
        rlPlay = (RelativeLayout) findViewById(R.id.rl_play);
        setRelativeLayoutPlay(true);
        rlPlay.setClickable(true);
        rlPlay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isPrepared) {

                    if (btnFullScreen.getVisibility() == View.VISIBLE) {
                        hideEditTextSoftInput(etFullscreen);
                        handler.removeCallbacks(playHideRunnable);
                        setPlayControllerVisible(false);
                    } else {
                        setPlayControllerVisible(true);
                        hidePlayHander();
                    }
                }
            }
        });

        changeSoundVideo = (Button) findViewById(R.id.sound_video);
        changeSoundVideo.setOnClickListener(this);

        changeSource = (Button) findViewById(R.id.play_source_change);
        changeSource.setOnClickListener(this);

        changeQuality = (Button) findViewById(R.id.play_quality_change);
        changeQuality.setOnClickListener(this);

        sv = (SurfaceView) findViewById(R.id.live_sv);
        holder = sv.getHolder();
        holder.addCallback(this);

        rgTitle = (RadioGroup) findViewById(R.id.rg_title);
        rgTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_chat:
                        mPager.setCurrentItem(0);
                        break;
                    case R.id.rb_pic:
                        int indexPic = rbs.indexOf(rbPic);
                        mPager.setCurrentItem(indexPic);
                        break;
                    case R.id.rb_qa:
                        int indexQa = rbs.indexOf(rbQa);
                        mPager.setCurrentItem(indexQa);
                        break;
                    case R.id.rb_connect_mic:
                        int indexCM = rbs.indexOf(rbConnectMic);
                        mPager.setCurrentItem(indexCM);
                        break;
                }
            }
        });

        rbChat = (RadioButton) findViewById(R.id.rb_chat);
        rbPic = (RadioButton) findViewById(R.id.rb_pic);
        rbQa = (RadioButton) findViewById(R.id.rb_qa);
        rbConnectMic = (RadioButton) findViewById(R.id.rb_connect_mic);
        rbs.add(rbChat);
        rbs.add(rbPic);
        rbs.add(rbQa);
        rbs.add(rbConnectMic);
    }

    private void initRoomShow() {
        if (!"1".equals(qaStr)) {
            pagerViewList.remove(2);
            rbs.remove(2);
            rbQa.setVisibility(View.GONE);
        }
        if (!"1".equals(pdfStr)) {
            pagerViewList.remove(1);
            rbs.remove(1);
            rbPic.setVisibility(View.GONE);
        }
        if (!"1".equals(chatStr)) {
            pagerViewList.remove(0);
            rbs.remove(0);
            rbChat.setVisibility(View.GONE);
        }
        if (rbs.size() > 0) {
            rgTitle.setVisibility(View.VISIBLE);
            rbs.get(0).setChecked(true);
        } else {
            // TODO 如果没有的话，直接进入全屏模式
        }
        initPager();
        initLvChat();
        initLvQa();
    }

    private void initDwLive() {
        dwLive = DWLive.getInstance();
        //设置播放的参数
        dwLive.setDWLivePlayParams(dwLiveListener, getApplicationContext(), docView, player);
        //设置连麦的事件监听器
        dwLive.setRtcClientParameters(rtcClientListener, localRender, remoteRender);
        viewer = dwLive.getViewer();
    }

    private Runnable playHideRunnable = new Runnable() {

        @Override
        public void run() {
            setPlayControllerVisible(false);
        }
    };

    private void setPlayControllerVisible(boolean isVisible) {
        int visibility = 0;
        if (isVisible) {
            visibility = View.VISIBLE;
        } else {
            visibility = View.INVISIBLE;
            if (mPopupWindow != null && mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
        }

        if (!isPortrait() && "1".equals(chatStr)) {
            llFullscreen.setVisibility(visibility);
            etFullscreen.requestFocus();
        }

        ivBack.setVisibility(visibility);
        btnFullScreen.setVisibility(visibility);
        if (isSpeaking) {
            tvCount.setVisibility(View.INVISIBLE);
            rlPlayTop.setVisibility(View.INVISIBLE);
        } else {
            tvCount.setVisibility(visibility);
            rlPlayTop.setVisibility(visibility);
        }
    }

    private boolean isPortrait() {
        int mOrientation = getApplicationContext().getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        } else {
            return true;
        }
    }

    private void hidePlayHander() {
        handler.removeCallbacks(playHideRunnable);
        handler.postDelayed(playHideRunnable, 5000);
    }

    private void initPlayer() {
        player = new DWLivePlayer(this);
        player.setVolume(1.0f, 1.0f);
        player.setOnPreparedListener(this);
        player.setOnVideoSizeChangedListener(this);
        player.setOnErrorListener(this);
        player.setOnInfoListener(this);
        player.setOnBufferingUpdateListener(this);
        player.setOnCompletionListener(this);
    }

    private boolean isFinish = false;

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(playHideRunnable);
        handler.removeCallbacks(r);
        isFinish = true;
        player.stop();
        player.release();
//		dwLive.onDestroy();
        imm = null;
        etMsg = null;
        etQA = null;
        etFullscreen = null;
        localRender.release();
        remoteRender.release();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (dwLive != null && !isOnPause) {
            if (isSpeaking) {
                try {
                    dwLive.restartVideo(surfaceHolder.getSurface());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                dwLive.start(surfaceHolder.getSurface());
            }
        }
        player.setScreenOnWhilePlaying(true);
        this.holder = surfaceHolder;
        setHolderBlack("请稍候……");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        Log.i("demo", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        player.pause();
        player.stop();
        player.reset();
        Log.i("demo", "surfaceDestroyed");
    }

    private boolean isPrepared = false;

    @Override
    public void onPrepared(IMediaPlayer mp) {
        isPrepared = true;
        tvPlayMsg.setVisibility(View.GONE);
        setPlayControllerVisible(true);
        hidePlayHander();
        llBottomLayout.setVisibility(View.VISIBLE);
        player.start();
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
        if (width == 0 || height == 0) {
            setHolderBlack("音频播放中……");
            return;
        }
        tvPlayMsg.setVisibility(View.GONE);
        sv.setLayoutParams(getVideoSizeParams());
    }

    // 当视频流不可见时，置黑并提示信息
    private void setHolderBlack(String text) {
        tvPlayMsg.setVisibility(View.VISIBLE);
        tvPlayMsg.setText(text);

        SurfaceHolder mHolder = sv.getHolder();
        Canvas canvas = mHolder.lockCanvas();
        if (canvas == null) {
            return;
        }
        canvas.drawColor(Color.BLACK);
        mHolder.unlockCanvasAndPost(canvas);
    }

    // 视频等比缩放
    private LayoutParams getVideoSizeParams() {
        int width = 600;
        int height = 400;

        if (isPortrait()) {
            width = wm.getDefaultDisplay().getWidth();
            height = wm.getDefaultDisplay().getHeight() / 3; //TODO 根据当前布局更改
        } else {
            width = wm.getDefaultDisplay().getWidth();
            height = wm.getDefaultDisplay().getHeight();
        }

        int vWidth = player.getVideoWidth();
        int vHeight = player.getVideoHeight();

        if (vWidth == 0) {
            vWidth = 600;
        }
        if (vHeight == 0) {
            vHeight = 400;
        }

        if (vWidth > width || vHeight > height) {
            float wRatio = (float) vWidth / (float) width;
            float hRatio = (float) vHeight / (float) height;
            float ratio = Math.max(wRatio, hRatio);

            width = (int) Math.ceil((float) vWidth / ratio);
            height = (int) Math.ceil((float) vHeight / ratio);
        } else {
            float wRatio = (float) width / (float) vWidth;
            float hRatio = (float) height / (float) vHeight;
            float ratio = Math.min(wRatio, hRatio);

            width = (int) Math.ceil((float) vWidth * ratio);
            height = (int) Math.ceil((float) vHeight * ratio);
        }

        LayoutParams params = new LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }

    // 连麦远端视频组件等比缩放
    private LayoutParams getRemoteRenderSizeParams() {
        int width = 600;
        int height = 400;

        if (isPortrait()) {
            width = wm.getDefaultDisplay().getWidth();
            height = wm.getDefaultDisplay().getHeight() / 3; //TODO 根据当前布局更改
        } else {
            width = wm.getDefaultDisplay().getWidth();
            height = wm.getDefaultDisplay().getHeight();
        }

        int vWidth = mVideoSizes[0];
        int vHeight = mVideoSizes[1];

        if (vWidth == 0) {
            vWidth = 600;
        }
        if (vHeight == 0) {
            vHeight = 400;
        }

        if (vWidth > width || vHeight > height) {
            float wRatio = (float) vWidth / (float) width;
            float hRatio = (float) vHeight / (float) height;
            float ratio = Math.max(wRatio, hRatio);

            width = (int) Math.ceil((float) vWidth / ratio);
            height = (int) Math.ceil((float) vHeight / ratio);
        } else {
            float wRatio = (float) width / (float) vWidth;
            float hRatio = (float) height / (float) vHeight;
            float ratio = Math.min(wRatio, hRatio);

            width = (int) Math.ceil((float) vWidth * ratio);
            height = (int) Math.ceil((float) vHeight * ratio);
        }

        LayoutParams params = new LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }


    @Override
    public boolean onError(IMediaPlayer mp, int what, int extra) {
        Log.e("demo", "=============================>onError:" + what);
        return false;
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer mp, int percent) {
    }

    private DWLiveListener dwLiveListener = new DWLiveListener() {
        @Override
        public void onQuestion(Question question) {
            Log.i("demo", "onQuestion:" + question.toString());
            Message msg = new Message();
            msg.what = QUESTION;
            msg.obj = question;
            handler.sendMessage(msg);
        }

        @Override
        public void onAnswer(Answer answer) {
            Log.i("demo", "onAnswer:" + answer.toString());
            Message msg = new Message();
            msg.what = ANSWER;
            msg.obj = answer;
            handler.sendMessage(msg);
        }

        @Override
        public void onLiveStatus(DWLive.PlayStatus status) {
            Log.i("demo", "onLiveStatusChange:" + status);
            switch (status) {
                case PLAYING:
                    isStop = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            llBottomLayout.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
                case PREPARING:
                    isStop = true;
            }
        }

        @Override
        public void onPublicChatMessage(ChatMessage msg) {
            Log.i("demo", "onPublicChatMessage:" + msg);
            Message handlerMsg = new Message();
            handlerMsg.what = PUBLIC_MSG;
            handlerMsg.obj = msg;
            handler.sendMessage(handlerMsg);
        }

        @Override
        public void onPrivateQuestionChatMessage(ChatMessage msg) {
            Log.i("demo", "onPrivateQuestionChatMessage:" + msg);
            Message handlerMsg = new Message();
            handlerMsg.what = PRIVATE_QUESTION_MSG;
            handlerMsg.obj = msg;
            handler.sendMessage(handlerMsg);
        }

        @Override
        public void onPrivateAnswerChatMessage(ChatMessage msg) {
            Log.i("demo", "onPrivateAnswerChatMessage:" + msg);
            Message handlerMsg = new Message();
            handlerMsg.what = PRIVATE_ANSWER_MSG;
            handlerMsg.obj = msg;
            handler.sendMessage(handlerMsg);
        }

        @Override
        public void onPrivateChat(PrivateChatInfo info) {
        }

        @Override
        public void onPrivateChatSelf(PrivateChatInfo info) {

        }

        @Override
        public void onUserCountMessage(int count) {
            Message msg = new Message();
            msg.what = USER_COUNT;
            msg.obj = count;
            handler.sendMessage(msg);
        }

        @Override
        public void onNotification(String msg) {
            Log.i("demo", "onNotification:" + msg);
        }

        @Override
        public void onInformation(String msg) {
            Log.i("demo", "information:" + msg);
        }

        @Override
        public void onException(DWLiveException exception) {
            Log.e("demo", exception.getMessage() + "");
        }

        @Override
        public void onInitFinished(int playSourceCount, List<QualityInfo> qualityInfoList) {
            mPlaySourceCount = playSourceCount;
            mQualityInfoList = qualityInfoList;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initPopupWindow();
                }
            });
        }

        private void initPopupWindow() {
            if (mPopupWindow != null) {
                return;
            }
            View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.video_quality_layout, null);
            mPopupWindow = new PopupWindow(contentView, 150, LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setTouchable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.bg));

            ListView lv = (ListView) contentView.findViewById(R.id.lv_quality);
            lv.setAdapter(new ArrayAdapter<QualityInfo>(getApplicationContext(), R.layout.quality_tv_layout, mQualityInfoList));

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mPopupWindow.dismiss();
                    Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_LONG).show();
                    changeQuality.setText("清晰度 " + mQualityInfoList.get(position).toString());
                    dwLive.setQuality(position);
                    try {
                        dwLive.restartVideo(holder.getSurface());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DWLiveException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        @Override
        public void onSilenceUserChatMessage(ChatMessage msg) {
            Log.i("demo", "onSilenceUserChatMessage:" + msg);
            Message handlerMsg = new Message();
            handlerMsg.what = PUBLIC_MSG; //收到禁言消息，作为公有消息展示出去，也可以不展示
            handlerMsg.obj = msg;
            handler.sendMessage(handlerMsg);
        }

        @Override
        public void onKickOut() {
            Message kickOutMsg = new Message();
            kickOutMsg.what = KICK_OUT;
            handler.sendMessage(kickOutMsg);
        }

        @Override
        public void onStreamEnd(boolean isNormal) {
            Log.e("demo", "stream end");
            isStop = true;
            isPrepared = false;
            Message msg = new Message();
            msg.what = FINISH;
            handler.sendMessage(msg);
        }

        @Override
        public void onLivePlayedTime(int playedTime) {
        }

        @Override
        public void onLivePlayedTimeException(Exception exception) {
        }

        @Override
        public void isPlayedBack(boolean isPlayedBack) {
        }

        @Override
        public void onStatisticsParams(Map<String, String> statisticsMap) {
            if (player != null) {
                player.initStatisticsParams(statisticsMap);
            }
        }

        @Override
        public void onCustomMessage(String customMessage) {

        }

        @Override
        public void onBanStream(String reason) {

        }

        @Override
        public void onUnbanStream() {

        }

        @Override
        public void onAnnouncement(boolean isRemove, String announcement) {

        }

        @Override
        public void onRollCall(int duration) {

        }

        @Override
        public void onStartLottery() {

        }

        @Override
        public void onLotteryResult(boolean isWin, String lotteryCode, String winnerName) {

        }

        @Override
        public void onStopLottery() {

        }

        @Override
        public void onVoteStart(int voteCount, int VoteType) {

        }

        @Override
        public void onVoteStop() {

        }

        @Override
        public void onVoteResult(JSONObject jsonObject) {

        }
    };

    // 远程视频的宽高
    private int[] mVideoSizes = new int[2];

    private void initSpeakPage() {
        hideVideoRenderAndTips();
        hideCmLayout();
        isSpeaking = false;
        isVideo = false;
        tvCmInfo.setVisibility(View.VISIBLE);
        tvCmInfo.setText(getText(R.string.cm_not_start));
        sv.setVisibility(View.VISIBLE);
    }

    private RtcClient.RtcClientListener rtcClientListener = new RtcClient.RtcClientListener() {
        @Override
        public void onAllowSpeakStatus(final boolean isAllowSpeak) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isAllowSpeak && isSpeaking) {
                        return;
                    }

                    hideVideoRenderAndTips();
                    hideCmLayout();
                    if (isAllowSpeak) {
                        llApply.setVisibility(View.VISIBLE);
                    } else {
                        //TODO 是否需要加上断开语音
                        isSpeaking = false;
                        isVideo = false;
                        tvCmInfo.setVisibility(View.VISIBLE);
                        tvCmInfo.setText(getText(R.string.cm_not_start));
                        sv.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        AppRTCAudioManager mAudioManager;

        private void processRemoteVideoSize(String videoSize) {
            String[] sizes = videoSize.split("x");
            int width = Integer.parseInt(sizes[0]);
            int height = Integer.parseInt(sizes[1]);
            double ratio = (double) width / (double) height;
            // 对于分辨率为16：9的，更改默认分辨率为16：10
            if (ratio > 1.76 && ratio < 1.79) {
                mVideoSizes[0] = 1600;
                mVideoSizes[1] = 1000;
            }
        }

        @Override
        public void onEnterSpeak(final String videoSize) {

            processRemoteVideoSize(videoSize);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isSpeaking) {
                        return;
                    }

                    player.pause();
                    player.stop();
                    sv.setVisibility(View.INVISIBLE);
                    localRender.setVisibility(View.INVISIBLE);
                    remoteRender.setVisibility(View.VISIBLE);
                    remoteRender.setLayoutParams(getRemoteRenderSizeParams());

                    // 由于rtc是走的通话音频，所以需要做处理
                    mAudioManager = AppRTCAudioManager.create(LiveRoomActivity.this, null);
                    mAudioManager.init();
                    setPlayControllerVisible(false);
                    isSpeaking = true;

                    hideCmLayout();
                    btnDisconnectCm.setVisibility(View.VISIBLE);

                    tvCmVideoTips.setVisibility(View.INVISIBLE);
                    tvCmTips.setVisibility(View.VISIBLE);
                    dwLive.removeLocalRender();

                    btnDisconnectCm.setText(getText(R.string.cm_disconnect_video));

                    startCmTimer();
                }
            });

        }

        @Override
        public void onDisconnectSpeak() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mAudioManager != null) {
                        mAudioManager.close();
                    }

                    hideSpeak();
                }
            });
        }

        @Override
        public void onSpeakError(final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    if (mAudioManager != null) {
                        mAudioManager.close();
                    }
                    hideSpeak();
                }
            });
        }

        @Override
        public void onCameraOpen(final int width, final int height) {
        }
    };

    private void hideSpeak() {
        if (isVideo || isSpeaking) {
            dwLive.closeCamera();
            hideVideoRenderAndTips();
            sv.setVisibility(View.VISIBLE);
            hideCmLayout();
            isVideo = false;
            llApply.setVisibility(View.VISIBLE);
            isSpeaking = false;
            stopCmTimer();
        }
    }

    private void hideVideoRenderAndTips() {
        localRender.setVisibility(View.GONE);
        remoteRender.setVisibility(View.GONE);
        tvCmTips.setVisibility(View.INVISIBLE);
        tvCmVideoTips.setVisibility(View.INVISIBLE);
    }

    private Timer cmTimer;
    private TimerTask cmTimerTask;

    // 增加一个间隔为1s的定时器，如果断网，则增加一个10s的延时器，超过10s，重置dwlive
    private void startCmTimer() {
        cmCount = 0;

        if (cmTimer == null) {
            cmTimer = new Timer();
        }

        if (cmTimerTask != null) {
            cmTimerTask.cancel();
        }

        cmTimerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCmTips.setText(formatTime(cmCount++));
                        if (!isNetworkConnected()) {
                            start10sTimerTask();
                        } else {
                            cancel10sTimerTask();
                        }
                    }
                });
            }
        };

        cmTimer.schedule(cmTimerTask, 0, 1000);
    }

    private TimerTask cm10sTimerTask;

    private void start10sTimerTask() {
        if (cm10sTimerTask != null) {
            return;
        }

        cm10sTimerTask = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dwLive.disConnectSpeak();
                        hideSpeak();
                        stopCmTimer();
                        cancel10sTimerTask();
                    }
                });
            }
        };

        cmTimer.schedule(cm10sTimerTask, 10 * 1000);
    }

    private void cancel10sTimerTask() {
        if (cm10sTimerTask != null) {
            cm10sTimerTask.cancel();
            cm10sTimerTask = null;
        }

    }

    private void checkWebState() {
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isAvailable();
    }

    private String formatTime(int t) {
        StringBuilder sb = new StringBuilder("视频连麦中: ");

        int minTime = t / 60;
        int secondTime = t % 60;

        sb.append(addZero(minTime));
        sb.append(":");
        sb.append(addZero(secondTime));
        return sb.toString();
    }

    private String addZero(int t) {
        return t > 9 ? String.valueOf(t) : String.valueOf("0" + t);
    }

    private int cmCount;

    private void stopCmTimer() {

        if (cmTimerTask != null) {
            cmTimerTask.cancel();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (isPortrait()) {
                    finish();
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            case R.id.btn_msg:
                sendChatMsg(false);
                break;
            case R.id.btn_qa:
                String qaMsg = etQA.getText().toString().trim();
                if (!"".equals(qaMsg)) {
                    try {
                        dwLive.sendQuestionMsg(qaMsg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                etQA.setText("");
                hideEditTextSoftInput(etQA);
                break;
            case R.id.btn_full_screen:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    hideEditTextSoftInput(etFullscreen);
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    hideEditTextSoftInput(etMsg);
                    gvEmoji.setVisibility(View.GONE);
                    ivSmile.setVisibility(View.VISIBLE);
                    ivKeyBoard.setVisibility(View.GONE);
                }
                break;
            case R.id.play_quality_change:
                if (mPopupWindow != null) {
                    int[] location = new int[2];
                    changeQuality.getLocationOnScreen(location);
                    mPopupWindow.showAsDropDown(changeQuality, 80, 10);
                }
                break;
            case R.id.play_source_change:
                if (mPlaySourceCount <= 1) {
                    break;
                }
                dwLive.changePlaySource((++sourceChangeCount) % mPlaySourceCount);
                changeSource.setText("切换线路(" + ((sourceChangeCount + 1) % mPlaySourceCount + 1) + ")");
                break;
            case R.id.sound_video:
                if ("切换模式(音频)".equals(changeSoundVideo.getText())) {
                    dwLive.changePlayMode(PlayMode.SOUND);
                    changeSoundVideo.setText("切换模式(视频)");
                } else {
                    dwLive.changePlayMode(PlayMode.VIDEO);
                    changeSoundVideo.setText("切换模式(音频)");
                }
                break;
            case R.id.btn_fullscreen_send:
                String info = etFullscreen.getText().toString().trim();
                if (!"".equals(info)) {
                    dwLive.sendPublicChatMsg(info);
                    etFullscreen.setText("");
                }
                hideEditTextSoftInput(etFullscreen);
                hidePlayHander();
                break;
            case R.id.et_fullscreen:
                handler.removeCallbacks(playHideRunnable);
                break;
            case R.id.iv_smile:
                showSmileKeyboard();
                break;
            case R.id.iv_keyboard:
                showNormalKeyBoard();
                break;
            default:
                break;
        }
    }

    private void showNormalKeyBoard() {
        gvEmoji.setVisibility(View.GONE);
        ivSmile.setVisibility(View.VISIBLE);
        ivKeyBoard.setVisibility(View.GONE);
        showBottomEditTextSoftInput();
    }

    private void showSmileKeyboard() {
        hideEditTextSoftInput(etMsg);
        etMsg.requestFocus();
        ivSmile.setVisibility(View.GONE);
        ivKeyBoard.setVisibility(View.VISIBLE);
        gvEmoji.setVisibility(View.VISIBLE);
    }

    private void sendChatMsg(boolean isKeyboard) {
        String msg = etMsg.getText().toString().trim();
        if (!TextUtils.isEmpty(msg)) {
            if (swiPublicPrivate.isChecked()) {
                dwLive.sendPublicChatMsg(msg);
            } else {
                dwLive.sendPrivateChatMsg(msg);
            }
        }
        etMsg.setText("");
        gvEmoji.setVisibility(View.GONE);
        ivSmile.setVisibility(View.VISIBLE);
        ivKeyBoard.setVisibility(View.GONE);
        if (isKeyboard) {
            hideKeyBoardEditTextSoftInput();
        } else {
            hideEditTextSoftInput(etMsg);
        }
    }

    private void resetPlay() {
        hideSpeak();
        qaMap.clear();
        dwLive.stop();
        dwLive.start(holder.getSurface());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRelativeLayoutPlay(true);
            llFullscreen.setVisibility(View.GONE);
            mBarrageLayout.stop();
            mBarrageLayout.setVisibility(View.GONE);
            btnFullScreen.setBackgroundResource(R.drawable.fullscreen_close);


        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRelativeLayoutPlay(false);
//           	llFullscreen.setVisibility(View.VISIBLE);
            mBarrageLayout.start();
            mBarrageLayout.setVisibility(View.VISIBLE);
//           	etFullscreen.requestFocus();
            btnFullScreen.setBackgroundResource(R.drawable.fullscreen_open);
        }
        sv.setLayoutParams(getVideoSizeParams());
        remoteRender.setLayoutParams(getRemoteRenderSizeParams());
        handler.removeCallbacks(playHideRunnable);
        setPlayControllerVisible(true);
        hidePlayHander();
    }

    @Override
    public void onBackPressed() {
        if (isPortrait()) {
            if (gvEmoji.getVisibility() == View.VISIBLE) {
                gvEmoji.setVisibility(View.GONE);
                ivSmile.setVisibility(View.VISIBLE);
                ivKeyBoard.setVisibility(View.GONE);
            } else {
                super.onBackPressed();
            }
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void setRelativeLayoutPlay(boolean isPortraitOrien) {
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        LinearLayout.LayoutParams layoutParams = null;
        if (isPortraitOrien) {
            layoutParams = new LinearLayout.LayoutParams(width, height / 3);
        } else {
            layoutParams = new LinearLayout.LayoutParams(width, height);
        }
        rlPlay.setLayoutParams(layoutParams);
    }

    Runnable r;

    @Override
    public boolean onInfo(IMediaPlayer arg0, int arg1, int arg2) {
        if (arg1 == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {
            r = new Runnable() {
                @Override
                public void run() {
                    resetPlay();
                }
            };
            handler.postDelayed(r, 10 * 1000);
        } else if (arg1 == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
            if (r != null) {
                handler.removeCallbacks(r);
            }
        }
        return false;
    }

    private final int PUBLIC_MSG = 0;
    private final int PRIVATE_QUESTION_MSG = 1;
    private final int PRIVATE_ANSWER_MSG = 2;
    private final int QUESTION = 10;
    private final int ANSWER = 11;
    private final int USER_COUNT = 20;
    private final int FINISH = 40;
    private final int KICK_OUT = -1;

    private boolean isOnPause = false;

    @Override
    protected void onPause() {
        isPrepared = false;
        isOnPause = true;
        if (player != null && player.isPlaying()) {
            player.pause();
        }

        hideSpeak();
        qaMap.clear();
        dwLive.stop();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOnPause) {
            dwLive.start(holder.getSurface());
            isOnPause = false;
        }
    }

    @Override
    public void onCompletion(IMediaPlayer mp) {
        if (dwLive != null && !isStop) {
            resetPlay();
        }
    }
}
