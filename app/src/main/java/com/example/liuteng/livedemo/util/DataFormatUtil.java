package com.example.liuteng.livedemo.util;

import com.example.liuteng.livedemo.bean.Answer;
import com.example.liuteng.livedemo.bean.CourseDetailBean;
import com.example.liuteng.livedemo.bean.QaPageBean;
import com.example.liuteng.livedemo.bean.Quesition;
import com.example.liuteng.livedemo.bean.serverbean.CourseDetailRes;
import com.example.liuteng.livedemo.bean.serverbean.QARes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuteng on 2017/3/14.
 */

public class DataFormatUtil {
    public static QaPageBean convertQAResponse(QARes qaResponse) {
        QaPageBean page = new QaPageBean();
        List<QARes.ResultBean> results = qaResponse.getResult();
        page.setPageId(results.get(0).getOid());
        page.setTitle("问卷信息，共" + results.size() + "题");
        page.setStatus("0");
        ArrayList<Quesition> questions = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            QARes.ResultBean result = results.get(i);
            Quesition question = new Quesition();
            question.setQue_state(0);
            question.setQuesitionId(result.getQid());
            question.setType(result.getAnswer().get(0).getType());
            question.setContent(i + 1 + "、" + result.getContent());
            List<QARes.ResultBean.AnswerBean> answerResults = result.getAnswer();
            ArrayList<Answer> answers = new ArrayList<>();
            for (int j = 0; j < answerResults.size(); j++) {
                QARes.ResultBean.AnswerBean answerResult = answerResults.get(j);
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

    public static String emptyConvert(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    public static CourseDetailBean convertCourseDetailResponse(CourseDetailRes detailRes) {
        CourseDetailBean detailBean = new CourseDetailBean();
        CourseDetailRes.ResultBean result = detailRes.getResult();
        detailBean.setTitle(result.getTitle());
        detailBean.setContent(result.getContent());
        CourseDetailBean.PartMeetingBean meetingBean = new CourseDetailBean.PartMeetingBean();
        meetingBean.setLecture((String) result.getAuthor());
        meetingBean.setLectureInfo((String) result.getAuthorinfo());
        meetingBean.setType(result.getAdmintype());
        meetingBean.setEndTime(DateUtil.convertSpecial(result.getEnddate()));
        meetingBean.setStartTime(DateUtil.convertSpecial(result.getStarttime()));
        meetingBean.setPopular(result.getClick() + "");
        detailBean.setMeetinBean(meetingBean);
        List<CourseDetailBean.PartContentBean> contents = new ArrayList<>();
        for (CourseDetailRes.ResultBean.MeetinglistBean resBean : result.getMeetinglist()) {
            CourseDetailBean.PartContentBean contentBean = new CourseDetailBean.PartContentBean();
            contentBean.setTitle(resBean.getTitle());
            contentBean.setLecture(resBean.getAuthor());
            contentBean.setStartTime(DateUtil.convertSpecial(resBean.getStarttime()));
            contentBean.setEndTime(DateUtil.convertSpecial(resBean.getEnddate()));
            contents.add(contentBean);
        }
        detailBean.setPartList(contents);
        return detailBean;
    }
}
