package com.example.liuteng.livedemo.model;

import com.example.liuteng.livedemo.bean.Answer;
import com.example.liuteng.livedemo.bean.PageBean;
import com.example.liuteng.livedemo.bean.Quesition;

import java.util.ArrayList;

/**
 * Created by liuteng on 2017/3/10.
 */

public class QuestionnaireInfo {
    public PageBean getQuestionInfo() {
        PageBean page;
        //假数据
        Answer a_one = new Answer();
        a_one.setAnswerId("0");
        a_one.setAnswer_content("男");
        a_one.setAns_state(0);
        Answer a_two = new Answer();
        a_two.setAnswerId("1");
        a_two.setAnswer_content("女");
        a_two.setAns_state(0);

        Answer a_three = new Answer();
        a_three.setAnswerId("2");
        a_three.setAnswer_content("是");
        a_three.setAns_state(0);
        Answer a_four = new Answer();
        a_four.setAnswerId("3");
        a_four.setAnswer_content("不是");
        a_four.setAns_state(0);


        Answer a_three1 = new Answer();
        a_three1.setAnswerId("4");
        a_three1.setAnswer_content("是");
        a_three1.setAns_state(0);
        Answer a_four1 = new Answer();
        a_four1.setAnswerId("5");
        a_four1.setAnswer_content("不是");
        a_four1.setAns_state(0);
        Answer a_one1 = new Answer();
        a_one1.setAnswerId("6");
        a_one1.setAnswer_content("男");
        a_one1.setAns_state(0);
        Answer a_two1 = new Answer();
        a_two1.setAnswerId("7");
        a_two1.setAnswer_content("女");
        a_two1.setAns_state(0);

        ArrayList<Answer> answers_one = new ArrayList<Answer>();
        answers_one.add(a_one);
        answers_one.add(a_two);


        ArrayList<Answer> answers_two = new ArrayList<Answer>();
        answers_two.add(a_three);
        answers_two.add(a_four);

        ArrayList<Answer> answers_three = new ArrayList<Answer>();
        answers_three.add(a_three1);
        answers_three.add(a_four1);
        answers_three.add(a_one1);
        answers_three.add(a_two1);


        Quesition q_one = new Quesition();
        q_one.setQuesitionId("00");
        q_one.setType("0");
        q_one.setContent("1、您的性别：");
        q_one.setAnswers(answers_one);
        q_one.setQue_state(0);

        Quesition q_two = new Quesition();
        q_two.setQuesitionId("01");
        q_two.setType("1");
        q_two.setContent("2、您是党员吗？");
        q_two.setAnswers(answers_two);
        q_two.setQue_state(0);


        Quesition q_three = new Quesition();
        q_three.setQuesitionId("03");
        q_three.setType("1");
        q_three.setContent("3、您是dsfsdfsd吗？");
        q_three.setAnswers(answers_three);
        q_three.setQue_state(0);

        Quesition q_four=new Quesition();
        q_four.setQuesitionId("04");
        q_four.setType("2");
        q_four.setContent("4、您喜欢做什么？");
        q_four.setQue_state(0);

        ArrayList<Quesition> quesitions = new ArrayList<Quesition>();
        quesitions.add(q_one);
        quesitions.add(q_two);
        quesitions.add(q_three);
        quesitions.add(q_four);

        page = new PageBean();
        page.setPageId("000");
        page.setStatus("0");
        page.setTitle("问卷信息，共4题");
        page.setQuesitions(quesitions);
        return page;
    }
}
