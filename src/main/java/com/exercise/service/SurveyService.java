package com.exercise.service;

import com.exercise.bean.Survey;

public interface SurveyService {

    public int addSurvey(Survey survey);

    public String getPath(int topicId);

    public Survey getSurveyByTopicId(int topicId);

    public int deleteSurveyByTopicId(int topicId);
}
