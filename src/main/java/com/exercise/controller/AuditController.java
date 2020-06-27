package com.exercise.controller;

import com.exercise.bean.Audit;
import com.exercise.bean.TableRender;
import com.exercise.service.AuditService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditController {
    private AuditService auditService;

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/auditList/{topicId}")
    public TableRender<Audit> getOnePageAuditByTopicId(@PathVariable("topicId") int topicId, @Param("page") int page,@Param("limit") int limit){
        return auditService.getPageByTopicId(topicId,page,limit);
    }

}
