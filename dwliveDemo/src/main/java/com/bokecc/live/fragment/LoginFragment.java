package com.bokecc.live.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bokecc.live.activity.LiveReplayActivity;
import com.bokecc.live.activity.LiveRoomActivity;
import com.bokecc.live.activity.R;
import com.bokecc.sdk.mobile.live.DWLive;
import com.bokecc.sdk.mobile.live.DWLiveLoginListener;
import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.pojo.PublishInfo;
import com.bokecc.sdk.mobile.live.pojo.RoomInfo;
import com.bokecc.sdk.mobile.live.pojo.TemplateInfo;
import com.bokecc.sdk.mobile.live.pojo.Viewer;
import com.bokecc.sdk.mobile.live.replay.DWLiveReplay;
import com.bokecc.sdk.mobile.live.replay.DWLiveReplayLoginListener;

public class LoginFragment extends Fragment {
	
	private boolean isLive;
	
	private EditText etUser, etRoomid, etNickName, etPassword; 
	private Spinner spLiveId;
	
	private Button btnOpenLiveRoom;
	
	private DWLive dwLive = DWLive.getInstance();
	private DWLiveLoginListener dwLiveLoginListener = new DWLiveLoginListener() {
		
		@Override
		public void onLogin(TemplateInfo info, Viewer viewer, RoomInfo roomInfo, PublishInfo publishInfo) {
			saveLoginParams();
			
			Intent intent = new Intent(getActivity(), LiveRoomActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("chat", info.getChatView());
			bundle.putString("pdf", info.getPdfView());
			bundle.putString("qa", info.getQaView());
			bundle.putInt("dvr", roomInfo.getDvr());
			intent.putExtras(bundle);
			
			startActivity(intent);
		}
		
		@Override
		public void onException(DWLiveException exception) {
			Message msg = new Message();
			msg.what = -1;
			msg.obj = exception.getMessage();
			mHandler.sendMessage(msg);
		}
	};
	
	private String fileName = "loginparams";
	
	private void saveLoginParams() {
		Editor editor = sp.edit();
		editor.putString("user", etUser.getText().toString());
		editor.putString("roomid", etRoomid.getText().toString());
		editor.putString("nickname", etNickName.getText().toString());
		editor.putString("password", etPassword.getText().toString());
		editor.apply();
	}
	
	private void initEditTextContent() {
		etUser.setText("D9180EE599D5BD46");
		etRoomid.setText("D2ACF9FDA4D4E2F79C33DC5901307461");
		etNickName.setText(sp.getString("nickname", ""));
		etPassword.setText(sp.getString("password", ""));
//		etNickName.setText(sp.getString("nickname", ""));
//		etPassword.setText(sp.getString("password", ""));
	}
	
	private DWLiveReplay dwLiveReplay = DWLiveReplay.getInstance();
	
	private DWLiveReplayLoginListener dwLiveReplayLoginListener = new DWLiveReplayLoginListener() {
		
		@Override
		public void onLogin(TemplateInfo templateInfo) {
			Intent intent = new Intent(getActivity(), LiveReplayActivity.class);
			
			Bundle bundle = new Bundle();
			bundle.putString("chat", templateInfo.getChatView());
			bundle.putString("pdf", templateInfo.getPdfView());
			bundle.putString("qa", templateInfo.getQaView());
			intent.putExtras(bundle);
			
			startActivity(intent);
		}
		
		@Override
		public void onException(DWLiveException exception) {
			Message msg = new Message();
			msg.what = -2;
			msg.obj = exception.getMessage();
			mHandler.sendMessage(msg);
		}
	};
	
	private List<String> liveIds = new ArrayList<String>();
	
	private ArrayAdapter<String> spinnerAdapter;
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case 0:
				Map<String, String> map = (Map)msg.obj;
				String userId = map.get("userid");
				if (userId != null) {
					etUser.setText(userId);
				}
				String roomId = map.get("roomid");
				if (roomId != null) {
					etRoomid.setText(roomId);
				}
				break;
			case 1:
				if (isLive) {
					return;
				}
				
				liveIds.clear(); //清空liveIds列表
				List<String> ccList = (ArrayList)msg.obj;
				for (String liveId: ccList) {
					liveIds.add(liveId);
				}
				currentLiveId = liveIds.get(0);
				spinnerAdapter.notifyDataSetChanged();
				break;
			case -1:
				String failLiveReason = (String) msg.obj;
				Toast.makeText(getActivity(), failLiveReason, Toast.LENGTH_SHORT).show();
				break;
			case -2:
				String failReplayReason = (String) msg.obj;
				Toast.makeText(getActivity(), failReplayReason, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	
	public LoginFragment(){}; // TODO 崩溃的地方，看看为什么
	
	@SuppressLint("ValidFragment")
    public LoginFragment(boolean isLive) {
		this.isLive = isLive;
	}
	
	private String currentLiveId;
	private SharedPreferences sp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		sp = getActivity().getSharedPreferences(fileName, getActivity().MODE_PRIVATE);
		View view = inflater.inflate(R.layout.login_fragment, container, false);
		
		etUser = (EditText) view.findViewById(R.id.user_id);
		etRoomid = (EditText) view.findViewById(R.id.room_id);
		
		spLiveId = (Spinner) view.findViewById(R.id.sp_live_id);
		liveIds.add("6C6A758977D8622D");
        liveIds.add("4FB4276694288271");
		spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_view, liveIds);
		spLiveId.setAdapter(spinnerAdapter);
		spLiveId.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				currentLiveId = liveIds.get(position);
				updateOpenLiveRoomStatus();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
		
		if (isLive) {
			spLiveId.setVisibility(View.GONE);
		}
		etNickName = (EditText) view.findViewById(R.id.nick_name);
		etPassword = (EditText) view.findViewById(R.id.password);
		etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
		
		btnOpenLiveRoom = (Button) view.findViewById(R.id.btn_open_live_room);
		btnOpenLiveRoom.setOnClickListener(new OnClickListener() {
			
			private int count;
			@Override
			public void onClick(View v) {
				String userId = etUser.getText().toString().trim();
				String roomId = etRoomid.getText().toString().trim();
				String viewerName = etNickName.getText().toString().trim();
				String password = etPassword.getText().toString().trim();

				if (isLive) {
					if (password == null || "".equals(password)) {
						dwLive.setDWLiveLoginParams(dwLiveLoginListener, true, userId, roomId, viewerName);
					} else {
						dwLive.setDWLiveLoginParams(dwLiveLoginListener, true, userId, roomId, viewerName, password);
					}
					dwLive.startLogin();
				} else {
					if (password == null || "".equals(password)) {
						dwLiveReplay.setLoginParams(dwLiveReplayLoginListener, true, userId, roomId, currentLiveId, viewerName);
					} else {
						dwLiveReplay.setLoginParams(dwLiveReplayLoginListener, true, userId, roomId, currentLiveId, viewerName, password);
					}
					dwLiveReplay.startLogin();
					Log.i("ccc", currentLiveId + "");
				}
			}
		});
		
		etUser.addTextChangedListener(MyTextWatcher);
		etNickName.addTextChangedListener(MyTextWatcher);
		etRoomid.addTextChangedListener(MyTextWatcher);
		initEditTextContent();
		return view;
	}
	
	private TextWatcher MyTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		
		@Override
		public void afterTextChanged(Editable s) {
			updateOpenLiveRoomStatus();
		}
	};
	
	private void updateOpenLiveRoomStatus() {
		if (isLive) {
			if (!"".equals(etUser.getText().toString()) && !"".equals(etRoomid.getText().toString()) && !"".equals(etNickName.getText().toString())) {
				btnOpenLiveRoom.setEnabled(true);
			} else {
				btnOpenLiveRoom.setEnabled(false);
			}
		} else {
			if (!"".equals(etUser.getText().toString()) && !"".equals(etRoomid.getText().toString()) && !"".equals(etNickName.getText().toString()) && currentLiveId!= null) {
				btnOpenLiveRoom.setEnabled(true);
			} else {
				btnOpenLiveRoom.setEnabled(false);
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	public Handler getHander() {
		return mHandler;
	}
}