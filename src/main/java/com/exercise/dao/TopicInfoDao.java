package com.exercise.dao;

import com.exercise.bean.TopicInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TopicInfoDao {

    public int addTopic(@Param("topic") TopicInfo topicInfo);

    public int deleteTopicById(int id);

    public int updateTopic(TopicInfo topicInfo);

    public int nextProcess(int id);

    public int setReportDate(@Param("id")int id, @Param("date")Date date);

    public int getProcess(int id);

    public int reverseProcess(int id);

    //一页新建选题列表
    public List<TopicInfo> getNewList(@Param("begin")int begin, @Param("number") int number);
    //全部新建选题数量
    public int getCountNewList();
    //一页选题审核列表
    public List<TopicInfo> getAuditList(@Param("begin")int begin, @Param("number") int number);
    //全部数量
    public int getCountAuditList();
    //一页选题社会调查表
    public List<TopicInfo> getSurveyList(@Param("begin")int begin, @Param("number") int number);
    //全部数量
    public int getCountSurveyList();
    //一页选题上报列表
    public List<TopicInfo> getReportList(@Param("begin")int begin, @Param("number") int number);
    //全部数量
    public int getCountReportList();
}
