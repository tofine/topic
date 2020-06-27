package com.exercise.service;

import com.exercise.bean.TableRender;
import com.exercise.bean.TopicInfo;

public interface TopicInfoService {

    //增删改
    public int addTopic(TopicInfo topicInfo);
    public int deleteTopicById(int id);
    public int updateTopic(TopicInfo topicInfo);
    public int setReportTime(int id);

    //获取一页某阶段的选题数据
    public TableRender<TopicInfo> onePageNew(int page, int limit);
    public TableRender<TopicInfo> onePageAudit(int page,int limit);
    public TableRender<TopicInfo> onePageSurvey(int page,int limit);
    public TableRender<TopicInfo> onePageReport(int page,int limit);

    //操作选题状态
    public int next(int id);   //进入下一个状态
    public int get(int id);    //获取当前状态
    public int reverse(int id);   //上一个状态
}
