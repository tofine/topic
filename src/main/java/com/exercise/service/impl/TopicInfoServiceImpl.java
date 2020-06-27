package com.exercise.service.impl;

import com.exercise.bean.TableRender;
import com.exercise.bean.TopicInfo;
import com.exercise.dao.TopicInfoDao;
import com.exercise.service.AuditService;
import com.exercise.service.SurveyService;
import com.exercise.service.TopicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class TopicInfoServiceImpl implements TopicInfoService {

    private TopicInfoDao topicInfoDao;
    private AuditService auditService;
    private SurveyService surveyService;
    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }
    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }
    @Autowired
    public void setTopicInfoDao(TopicInfoDao topicInfoDao) {
        this.topicInfoDao = topicInfoDao;
    }


    @Override
    @Transactional
    public int addTopic(TopicInfo topicInfo) {
        return topicInfoDao.addTopic(topicInfo);
    }

    @Transactional
    public int deleteTopicById(int id){

        topicInfoDao.deleteTopicById(id);
        return auditService.deleteAuditByTopicId(id);
    }

    @Override
    @Transactional
    public int updateTopic(TopicInfo topicInfo) {
        return topicInfoDao.updateTopic(topicInfo);
    }

    @Override
    public int setReportTime(int id) {
        return topicInfoDao.setReportDate(id,new Date(new java.util.Date().getTime()));
    }

    @Override
    @Transactional              //审核阶段的选题列表
    public TableRender<TopicInfo> onePageAudit(int page, int limit) {
        List<TopicInfo> list=topicInfoDao.getAuditList((page-1)*limit,limit);
        TableRender<TopicInfo> tableData=new TableRender<>();
        tableData.setData(list);
        tableData.setCount(topicInfoDao.getCountAuditList());
        return tableData;
    }


    @Override              //市场调查阶段的选题列表
    @Transactional
    public TableRender<TopicInfo> onePageSurvey(int page, int limit) {
        List<TopicInfo> list=topicInfoDao.getSurveyList((page-1)*limit,limit);
        TableRender<TopicInfo> tableData=new TableRender<>();
        tableData.setData(list);
        tableData.setCount(topicInfoDao.getCountSurveyList());
        return tableData;
    }

    @Transactional
    @Override          //选题上报列表
    public TableRender<TopicInfo> onePageReport(int page, int limit) {
        List<TopicInfo> list=topicInfoDao.getReportList((page-1)*limit,limit);

        TableRender<TopicInfo> tableData=new TableRender<>();
        tableData.setData(list);
        tableData.setCount(topicInfoDao.getCountReportList());
        return tableData;
    }

    @Override
    @Transactional           //新建选题列表
    public TableRender<TopicInfo> onePageNew(int page, int limit) {
        List<TopicInfo> list=topicInfoDao.getNewList((page-1)*limit,limit);

        TableRender<TopicInfo> tableData=new TableRender<>();
        tableData.setData(list);
        tableData.setCount(topicInfoDao.getCountNewList());
        return tableData;
    }

    @Override
    public int next(int id) {
        return topicInfoDao.nextProcess(id);
    }

    @Override
    public int get(int id) {
        return topicInfoDao.getProcess(id);
    }

    @Override
    public int reverse(int id) {
        return topicInfoDao.reverseProcess(id);
    }

}
