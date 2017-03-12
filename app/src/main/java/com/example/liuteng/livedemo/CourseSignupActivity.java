package com.example.liuteng.livedemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liuteng.livedemo.base.BaseActivity;
import com.example.liuteng.livedemo.bean.Answer;
import com.example.liuteng.livedemo.bean.PageBean;
import com.example.liuteng.livedemo.bean.Quesition;
import com.example.liuteng.livedemo.model.QuestionnaireInfo;
import com.example.liuteng.livedemo.util.XlfLog;
import com.example.liuteng.livedemo.view.TitleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by 刘腾 on 2017/3/6.
 */

public class CourseSignupActivity extends BaseActivity {
    private TitleView mTitleView;
    private LinearLayout test_layout;
    private PageBean the_page;
    //答案列表
//    private ArrayList<Answer> the_answer_list;
    //问题列表
    private ArrayList<Quesition> the_quesition_list;
    //问题所在的View
    private View que_view;
    //答案所在的View
    private View ans_view;
    private LayoutInflater xInflater;
    //下面这两个list是为了实现点击的时候改变图片，因为单选多选时情况不一样，为了方便控制
    //存每个问题下的imageview
    private ArrayList<ArrayList<ImageView>> imglist = new ArrayList<ArrayList<ImageView>>();
    //存每个答案的imageview
    private ArrayList<ImageView> imglist2;
    private EditText mNameEt;
    private EditText mTelEt;
    private EditText mMobileEt;
    private EditText mCompanyEt;
    private EditText mEmailEt;
    private EditText etAnswer;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_course_signup;
    }

    @Override
    public void initView(View view) {
        mTitleView = $(R.id.tv_title_signup);
        mNameEt = $(R.id.et_signup_name);
        mTelEt = $(R.id.et_signup_tel);
        mMobileEt = $(R.id.et_signup_mobile);
        mCompanyEt = $(R.id.et_signup_company);
        mEmailEt = $(R.id.et_signup_email);
        mTitleView.title.setText("报名");
        mTitleView.mRightTv.setVisibility(View.VISIBLE);
        mTitleView.mRightTv.setText("提交");
        mTitleView.mRightTv.setOnClickListener(new submitOnClickListener(the_page));
        xInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void doBusiness(Context mContext) {
        //假数据  
        initDate();
    }

    private void initDate() {
        QuestionnaireInfo info = new QuestionnaireInfo();
        the_page = info.getQuestionInfo();
        initPage(the_page);
    }

    private void initPage(PageBean the_page) {
        //这是要把问题的动态布局加入的布局
        test_layout = (LinearLayout) findViewById(R.id.lly_test);
        TextView page_txt = (TextView) findViewById(R.id.txt_title);
        page_txt.setText(the_page.getTitle());
        //获得问题即第二层的数据
        the_quesition_list = the_page.getQuesitions();
        //根据第二层问题的多少，来动态加载布局
        for (int i = 0; i < the_quesition_list.size(); i++) {
            que_view = xInflater.inflate(R.layout.question_layout, null);
            TextView txt_que = (TextView) que_view.findViewById(R.id.txt_question_item);
            //这是第三层布局要加入的地方
            LinearLayout add_layout = (LinearLayout) que_view.findViewById(R.id.lly_answer);
            //判断单选-多选来实现后面是*号还是*多选，
            if (the_quesition_list.get(i).getType().equals("1")) {
                set(txt_que, the_quesition_list.get(i).getContent(), 1);
            } else {
                set(txt_que, the_quesition_list.get(i).getContent(), 0);
            }
            Quesition quesition = the_quesition_list.get(i);
            if ("2".equals(quesition.getType())) {
                final Quesition ques = quesition;
                ans_view = xInflater.inflate(R.layout.answer_special_layout, null);
                etAnswer = (EditText) ans_view.findViewById(R.id.et_answer);
                etAnswer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(s.toString())) {
                            ques.setQue_state(0);
                        } else {
                            ques.setQue_state(1);
                        }
                    }
                });
                add_layout.addView(ans_view);
            } else {
                //获得答案即第三层数据
                ArrayList<Answer> the_answer_list = the_quesition_list.get(i).getAnswers();
                imglist2 = new ArrayList<ImageView>();
                for (int j = 0; j < the_answer_list.size(); j++) {
                    ans_view = xInflater.inflate(R.layout.answer_layout, null);
                    TextView txt_ans = (TextView) ans_view.findViewById(R.id.txt_answer_item);
                    ImageView image = (ImageView) ans_view.findViewById(R.id.image);
                    View line_view = ans_view.findViewById(R.id.vw_line);
                    if (j == the_answer_list.size() - 1) {
                        //最后一条答案下面不要线是指布局的问题
                        line_view.setVisibility(View.GONE);
                    }
                    //判断单选多选加载不同选项图片
                    if (the_quesition_list.get(i).getType().equals("1")) {
                        image.setImageResource(R.drawable.multiselect_false);
                    } else {
                        image.setImageResource(R.drawable.radio_false);
                    }
                    imglist2.add(image);
                    txt_ans.setText(the_answer_list.get(j).getAnswer_content());
                    LinearLayout lly_answer_size = (LinearLayout) ans_view.findViewById(R.id.lly_answer_size);
                    lly_answer_size.setOnClickListener(new answerItemOnClickListener(i, j, quesition, txt_ans));
                    add_layout.addView(ans_view);
                }
            }

            imglist.add(imglist2);

            test_layout.addView(que_view);
        }
        /*for(int q=0;q<imglist.size();q++){
            for(int w=0;w<imglist.get(q).size();w++){
                Log.e("---", "共有------"+imglist.get(q).get(w));
            }
        }*/
    }

    private void set(TextView txt_que, String content, int type) {
        //为了加载问题后面的* 和*多选
        String w;
        if (type == 1) {
            w = content + "*[多选题]";
        } else {
            w = content + "*";
        }

        int start = content.length();
        int end = w.length();
        Spannable word = new SpannableString(w);
        word.setSpan(new AbsoluteSizeSpan(35), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        word.setSpan(new StyleSpan(Typeface.BOLD), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        word.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_que.setText(word);
    }

    class answerItemOnClickListener implements View.OnClickListener {
        private int i;
        private int j;
        private TextView txt;
        private ArrayList<Answer> the_answer_lists;
        private Quesition question;
        private HashSet<Answer> selectedAnswers = new HashSet<>();

        public answerItemOnClickListener(int i, int j, Quesition question, TextView text) {
            this.i = i;
            this.j = j;
            this.the_answer_lists = question.getAnswers();
            this.question = question;
            this.txt = text;

        }

        //实现点击选项后改变选中状态以及对应图片
        @Override
        public void onClick(View arg0) {
            //判断当前问题是单选还是多选
            /*Log.e("------", "选择了-----第"+i+"题");
            for(int q=0;q<imglist.size();q++){
                for(int w=0;w<imglist.get(q).size();w++){
//                  Log.e("---", "共有------"+imglist.get(q).get(w));
                }
            }
            Log.e("----", "点击了---"+imglist.get(i).get(j));*/
            XlfLog.d("被点击了" + the_answer_lists.get(j).toString());
            if (the_quesition_list.get(i).getType().equals("1")) {
                //多选
                if (the_answer_lists.get(j).getAns_state() == 0) {
                    //如果未被选中
                    txt.setTextColor(Color.parseColor("#EA5514"));
                    selectedAnswers.add(the_answer_lists.get(j));
                    imglist.get(i).get(j).setImageResource(R.drawable.multiselect_true);
                    the_answer_lists.get(j).setAns_state(1);
                    the_quesition_list.get(i).setQue_state(1);
                } else {
                    selectedAnswers.remove(the_answer_lists.get(j));
                    txt.setTextColor(Color.parseColor("#595757"));
                    imglist.get(i).get(j).setImageResource(R.drawable.multiselect_false);
                    the_answer_lists.get(j).setAns_state(0);
                    the_quesition_list.get(i).setQue_state(1);
                }
            } else {
                //单选

                for (int z = 0; z < the_answer_lists.size(); z++) {
                    the_answer_lists.get(z).setAns_state(0);
                    imglist.get(i).get(z).setImageResource(R.drawable.radio_false);
                }
                if (the_answer_lists.get(j).getAns_state() == 0) {
                    //如果当前未被选中
                    imglist.get(i).get(j).setImageResource(R.drawable.radio_true);
                    selectedAnswers.add(the_answer_lists.get(j));
                    question.setSelectedAnswer(selectedAnswers);
                    the_answer_lists.get(j).setAns_state(1);
                    the_quesition_list.get(i).setQue_state(1);
                } else {
                    //如果当前已被选中
                    the_answer_lists.get(j).setAns_state(1);
                    the_quesition_list.get(i).setQue_state(1);
                }

            }
            question.setSelectedAnswer(selectedAnswers);
            //判断当前选项是否选中


        }

    }

    class submitOnClickListener implements View.OnClickListener {
        private PageBean page;

        public submitOnClickListener(PageBean page) {
            this.page = page;
        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            //判断是否答完题
            boolean isState = true;
            //最终要的json数组
            JSONArray jsonArray = new JSONArray();
            //点击提交的时候，先判断状态，如果有未答完的就提示，如果没有再把每条答案提交（包含问卷ID 问题ID 及答案ID）
            //注：不用管是否是一个问题的答案，就以答案的个数为准来提交上述格式的数据
            for (int i = 0; i < the_quesition_list.size(); i++) {
                ArrayList<Answer> the_answer_list = the_quesition_list.get(i).getAnswers();
                Quesition quesition= the_quesition_list.get(i);
                //判断是否有题没答完
                if(!"2".equals(quesition.getType())&&(
                        quesition.getQue_state() == 0 || the_quesition_list.get(i).getSelectedAnswer() == null
                                || the_quesition_list.get(i).getSelectedAnswer().size() == 0)) {
                    Toast.makeText(getApplicationContext(), "您第" + (i + 1) + "题没有答完", Toast.LENGTH_LONG).show();
                    jsonArray = null;
                    isState = false;
                    break;
                } else if ("2".equals(the_quesition_list.get(i).getType())) {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("psychologicalId", the_page.getPageId());
                        json.put("questionId", the_quesition_list.get(i).getQuesitionId());
                        json.put("answercontent", etAnswer.getText().toString());
                        jsonArray.put(json);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                } else {
                    for (int j = 0; j < the_answer_list.size(); j++) {
                        if (the_answer_list.get(j).getAns_state() == 1) {
                            JSONObject json = new JSONObject();
                            try {
                                json.put("psychologicalId", the_page.getPageId());
                                json.put("questionId", the_quesition_list.get(i).getQuesitionId());
                                json.put("optionId", the_answer_list.get(j).getAnswerId());
                                jsonArray.put(json);
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                }

            }
            if (isState) {
                if (jsonArray.length() > 0) {
                    for (int item = 0; item < jsonArray.length(); item++) {
                        JSONObject job;
                        try {
                            job = jsonArray.getJSONObject(item);
                            XlfLog.d(job.toString());
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }  // 遍历 jsonarray 数组，把每一个对象转成 json 对象

                    }

                }

            }

        }
    }

    @Override
    public void widgetClick(View v) {

    }
}
