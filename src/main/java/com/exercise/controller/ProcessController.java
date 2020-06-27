package com.exercise.controller;

import com.exercise.bean.Audit;
import com.exercise.bean.Survey;
import com.exercise.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProcessController {

    private ProcessService processService;
    @Autowired
    public void setProcessService(ProcessService processService) {
        this.processService = processService;
    }

    @PostMapping("/survey")  //送复审
    public int surveyToAudit(@RequestBody Survey survey){
        return processService.sendToAuditTwo(survey);
    }

    @PutMapping("/next/{id}")    //送初审
    public int topicNext(@PathVariable int id){
        return processService.sendToAuditOne(id);
    }

    @PostMapping("/audit")     //审核
    public int topicAudit(@RequestBody Audit audit){
        return processService.audit(audit);
    }

    @PutMapping("/report/{id}")   //上报
    public int report(@PathVariable int id){
        return processService.report(id);
    }

}
