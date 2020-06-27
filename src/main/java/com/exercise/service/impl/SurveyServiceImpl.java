package com.exercise.service.impl;

import com.exercise.bean.Survey;
import com.exercise.dao.SurveyDao;
import com.exercise.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SurveyServiceImpl implements SurveyService {
    private SurveyDao surveyDao;

    @Autowired
    public void setSurveyDao(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
    }

    @Override
    @Transactional
    public int addSurvey(Survey survey) {
        return surveyDao.addSurvey(survey);
    }

    @Override
    @Transactional
    public String getPath(int id) {
        return surveyDao.getPath(id);
    }

    @Override
    @Transactional
    public Survey getSurveyByTopicId(int id) {
        return surveyDao.getSurveyByTopicId(id);
    }

    @Override
    public int deleteSurveyByTopicId(int topicId) {
        return surveyDao.deleteSurveyByTopicId(topicId);
    }

}
