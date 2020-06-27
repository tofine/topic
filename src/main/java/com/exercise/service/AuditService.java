package com.exercise.service;

import com.exercise.bean.Audit;
import com.exercise.bean.TableRender;

public interface AuditService {

    public int addAudit(Audit audit);
    public TableRender<Audit> getPageByTopicId(int topicId,int page,int limit);

    public int deleteAuditByTopicId(int topicId);
}
