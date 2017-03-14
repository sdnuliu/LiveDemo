package com.example.liuteng.livedemo.bean;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by liuteng on 2017/3/10.
 */

public class Quesition {
    //题目id
    private int quesitionId;
    //单选多选填空标识
    private String type;
    //题目
    private String content;
    //选项
    private ArrayList<Answer> answers;
    //是否解答
    private int que_state;

    private HashSet<Answer> selectedAnswer;

    public HashSet<Answer> getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(HashSet<Answer> selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public int getQue_state() {
        return que_state;
    }
    public void setQue_state(int que_state) {
        this.que_state = que_state;
    }

    public int getQuesitionId() {
        return quesitionId;
    }
    public void setQuesitionId(int quesitionId) {
        this.quesitionId = quesitionId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public ArrayList<Answer> getAnswers() {
        return answers;
    }
    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
