package com.exercise.controller;

import com.exercise.bean.Survey;
import com.exercise.service.SurveyService;
import com.exercise.service.TopicInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyController {

    TopicInfoService topicInfoService;
    SurveyService surveyService;

    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Autowired
    public void setTopicInfoService(TopicInfoService topicInfoService) {
        this.topicInfoService = topicInfoService;
    }

    //获取市场调查记录
    @GetMapping("/survey")
    public Survey getSurveyById(@Param("topicId") int topicId) {
        Survey survey = surveyService.getSurveyByTopicId(topicId);
        return survey;
    }
}
