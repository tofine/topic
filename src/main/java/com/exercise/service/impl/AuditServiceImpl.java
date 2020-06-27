package com.exercise.service.impl;

import com.exercise.bean.Audit;
import com.exercise.bean.TableRender;
import com.exercise.dao.AuditDao;
import com.exercise.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {

    private AuditDao auditDao;

    @Autowired
    public void setAuditDao(AuditDao auditDao) {
        this.auditDao = auditDao;
    }

    @Override
    public int addAudit(Audit audit) {
        return auditDao.addAudit(audit);
    }

    @Override
    public TableRender<Audit> getPageByTopicId(int topicId, int page, int limit) {

        List<Audit> list= auditDao.getAuditListByTopicId(topicId,(page-1)*limit,limit);

        TableRender<Audit> tableData=new TableRender<>();
        tableData.setData(list);
        tableData.setCount(auditDao.getCountAuditListByTopicId(topicId));
        return tableData;
    }

    @Override
    public int deleteAuditByTopicId(int topicId) {
        return auditDao.deleteAuditByTopicId(topicId);
    }
}
