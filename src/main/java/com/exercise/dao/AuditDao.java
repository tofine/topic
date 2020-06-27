package com.exercise.dao;

import com.exercise.bean.Audit;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditDao {

    @Insert(value = "insert into audit (topic_id,auditor,result,opinion,date,status) " +
            "values (#{topicId},#{auditor},#{result},#{opinion},#{date},#{status})")
    public int addAudit(Audit audit);

    @Select(value = "select * from audit where topic_id=#{topicId} limit #{begin},#{number}")
    public List<Audit> getAuditListByTopicId(@Param("topicId") int topicId, @Param("begin")int begin, @Param("number") int number);

    @Select(value="select count(*) from audit where topic_id = #{topicId}")
    public int getCountAuditListByTopicId(int topicId);

    @Delete(value = "delete from audit where topic_id = #{topicId}")
    public int deleteAuditByTopicId(int topicId);
}
