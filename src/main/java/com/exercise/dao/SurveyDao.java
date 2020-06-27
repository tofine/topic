package com.exercise.dao;

import com.exercise.bean.Survey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyDao {

    @Insert("insert into survey (topic_id,path,investigator,survey_time) " +
            "values (#{topicId},#{path},#{investigator},#{surveyTime})")
    public int addSurvey(Survey survey);

    @Select("select path from survey where topic_id=#{topicId}")
    public String getPath(int topicId);

    @Select("select topic_id,investigator,survey_time from survey where topic_id=#{topicId}")
    public Survey getSurveyByTopicId(int topicId);

    @Delete("delete from survey where topic_id =#{topicId}")
    public int deleteSurveyByTopicId(int topicId);
}
