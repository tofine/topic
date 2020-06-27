package com.exercise.service.impl;

import com.exercise.bean.Audit;
import com.exercise.bean.Survey;
import com.exercise.service.AuditService;
import com.exercise.service.ProcessService;
import com.exercise.service.SurveyService;
import com.exercise.service.TopicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessServiceImpl implements ProcessService {
    private AuditService auditService;

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    private SurveyService surveyService;

    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    private TopicInfoService topicInfoService;

    @Autowired
    public void setTopicInfoService(TopicInfoService topicInfoService) {
        this.topicInfoService = topicInfoService;
    }

    /*
    选题状态变更：(审核通过向下变更，不通过向上变更)

      表格           状态                               操作
     newList    新建0   初审未通过-1
                   \    /
                    \  /                        sendToAuditOne送初审
                     \/
    auditList       初审1
                      |                              audit审核
   surveyList      待市场调查2  终审未通过-3
                       \        /
                         \    /                 sendToAuditTwo送复审
                           \/
    auditList             终审3
                            |                        audit审核
   reportList             未上报4
                            |                        report上报
   reportList             已上报5
    */

    //送初审
    @Override
    @Transactional
    public int sendToAuditOne(int id) {
        int process = topicInfoService.get(id);
        topicInfoService.setReportTime(id);
        if (process >= 0) {
            return topicInfoService.next(id);
        } else {
            return topicInfoService.reverse(id);
        }
    }

    //送复审
    @Override
    @Transactional
    public int sendToAuditTwo(Survey survey) {
        int id = survey.getTopicId();
        int process = topicInfoService.get(id);
        surveyService.addSurvey(survey);
        if (process >= 0) {
            return topicInfoService.next(id);
        } else {
            return topicInfoService.reverse(id);
        }
    }

    //审核
    @Override
    public int audit(Audit audit) {
        int id = audit.getTopicId();
        auditService.addAudit(audit);
        if (audit.getResult() == 1) {
            return topicInfoService.next(id);
        } else {
            return topicInfoService.reverse(id);
        }
    }

    //上报
    @Override
    public int report(int id) {
        return topicInfoService.next(id);
    }
}
