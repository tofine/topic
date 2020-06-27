package com.exercise.controller;

import com.exercise.bean.TableRender;
import com.exercise.bean.TopicInfo;
import com.exercise.service.TopicInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TopicInfoController {

    private TopicInfoService topicInfoService;
    @Autowired
    public void setTopicInfoService(TopicInfoService topicInfoService) {
        this.topicInfoService = topicInfoService;
    }

    @PostMapping("/newTopic")          //新建选题
    public int newTopic(@RequestBody TopicInfo topicInfo){
        return topicInfoService.addTopic(topicInfo);
    }

    @DeleteMapping("/newTopic/{id}")      //删除选题
    public int deleteTopic(@PathVariable int id){
        return topicInfoService.deleteTopicById(id);
    }

    @PutMapping("/newTopic")         //更新选题
    public int putTopic(@RequestBody TopicInfo topicInfo){
        return topicInfoService.updateTopic(topicInfo);
    }

    @GetMapping("/newTopicList")     //新建选题列表
    public TableRender<TopicInfo> newTopicList(@Param(value = "page") int page, @Param(value = "limit")int limit){
        return topicInfoService.onePageNew(page,limit);
    }

    @GetMapping("/audit")       //获取待审核选题列表
    public TableRender<TopicInfo> auditTopic(@Param(value = "page") int page, @Param(value = "limit")int limit){
        return  topicInfoService.onePageAudit(page,limit);
    }

    @GetMapping("/onePageSurvey")  //社会调查列表
    public TableRender<TopicInfo> oneSurveyTopicList(@Param(value = "page")int page, @Param(value = "limit")int limit){
        return topicInfoService.onePageSurvey(page,limit);
    }

    @GetMapping("/report")            //上报选题列表
    public TableRender<TopicInfo> reportTopic(@Param(value = "page")int page, @Param(value = "limit")int limit){
        return topicInfoService.onePageReport(page,limit);
    }

}
