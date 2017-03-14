package com.example.liuteng.livedemo.util;

import com.example.liuteng.livedemo.bean.Answer;
import com.example.liuteng.livedemo.bean.PageBean;
import com.example.liuteng.livedemo.bean.Quesition;
import com.example.liuteng.livedemo.bean.serverbean.QAResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuteng on 2017/3/14.
 */

public class DataFormatUtil {
    public static PageBean convertQAResponse(QAResponse qaResponse) {
        PageBean page = new PageBean();
        List<QAResponse.ResultBean> results = qaResponse.getResult();
        page.setPageId(results.get(0).getOid());
        page.setTitle("问卷信息，共" + results.size() + "题");
        page.setStatus("0");
        ArrayList<Quesition> questions = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            QAResponse.ResultBean result = results.get(i);
            Quesition question = new Quesition();
            question.setQue_state(0);
            question.setQuesitionId(result.getQid());
            question.setType(result.getAnswer().get(0).getType());
            question.setContent(i+1+"、"+result.getContent());
            List<QAResponse.ResultBean.AnswerBean> answerResults = result.getAnswer();
            ArrayList<Answer> answers = new ArrayList<>();
            for (int j = 0; j < answerResults.size(); j++) {
                QAResponse.ResultBean.AnswerBean answerResult = answerResults.get(j);
                Answer answer = new Answer();
                answer.setAnswerId(answerResult.getAid());
                answer.setAnswer_content(answerResult.getName());
                answer.setAns_state(0);
                answers.add(answer);
            }
            question.setAnswers(answers);
            questions.add(question);
        }
        page.setQuesitions(questions);
        return page;
    }
}
