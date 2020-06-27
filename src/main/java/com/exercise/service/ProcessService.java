package com.exercise.service;

import com.exercise.bean.Audit;
import com.exercise.bean.Survey;

public interface ProcessService {

    //送初审
    public int sendToAuditOne(int id);
    //送复审
    public int sendToAuditTwo(Survey survey);
    //审核
    public int audit(Audit audit);
    //上报
    public int report(int id);

}
