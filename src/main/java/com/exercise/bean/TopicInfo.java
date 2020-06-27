package com.exercise.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.sql.Date;

@JsonSerialize(using=TopicSerializer.class)
public class TopicInfo {
    private Integer id;
    private String bookName;
    private String bookClassify;
    private String edition;
    private String bookNature;
    private String editor;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reportTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planContributeTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planPublishTime;

    private String introduction;
    private String declareReason;

    private Integer process;

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public TopicInfo(){
        this.process=0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookClassify() {
        return bookClassify;
    }

    public void setBookClassify(String bookClassify) {
        this.bookClassify = bookClassify;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getBookNature() {
        return bookNature;
    }

    public void setBookNature(String bookNature) {
        this.bookNature = bookNature;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Date getPlanContributeTime() {
        return planContributeTime;
    }

    public void setPlanContributeTime(Date planContributeTime) {
        this.planContributeTime = planContributeTime;
    }

    public Date getPlanPublishTime() {
        return planPublishTime;
    }

    public void setPlanPublishTime(Date planPublishTime) {
        this.planPublishTime = planPublishTime;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDeclareReason() {
        return declareReason;
    }

    public void setDeclareReason(String declareReason) {
        this.declareReason = declareReason;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }


    @Override
    public String toString() {
        return "TopicInfo{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", bookClassify='" + bookClassify + '\'' +
                ", edition='" + edition + '\'' +
                ", bookNature='" + bookNature + '\'' +
                ", editor='" + editor + '\'' +
                ", planContributeTime=" + planContributeTime +
                ", planPublishTime=" + planPublishTime +
                ", introduction='" + introduction + '\'' +
                ", declareReason='" + declareReason + '\'' +
                ", process=" + process +
                ", reportTime="+reportTime+
                '}';
    }
}

class TopicSerializer extends JsonSerializer<TopicInfo>{

    @Override
    public void serialize(TopicInfo topicInfo, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        String process;
        int p=topicInfo.getProcess();
        if(p==0)  process="新建";
        else if(p==1) process="初审";
        else if(p==-1) process="初审未通过";
        else if(p==2) process="待市场调查";
        else if(p==3) process="终审";
        else if(p==-3) process="终审未通过";
        else if(p==4) process="未上报";
        else process="已上报";

        jsonGenerator.writeNumberField("id",topicInfo.getId());
        jsonGenerator.writeStringField("bookName",topicInfo.getBookName());
        jsonGenerator.writeStringField("bookClassify",topicInfo.getBookClassify());
        jsonGenerator.writeStringField("edition",topicInfo.getEdition());
        jsonGenerator.writeStringField("bookNature",topicInfo.getBookNature());
        jsonGenerator.writeStringField("editor",topicInfo.getEditor());
        jsonGenerator.writeStringField("planContributeTime",topicInfo.getPlanContributeTime().toString());
        jsonGenerator.writeStringField("planPublishTime",topicInfo.getPlanPublishTime().toString());
        jsonGenerator.writeStringField("introduction",topicInfo.getIntroduction());
        jsonGenerator.writeStringField("declareReason",topicInfo.getDeclareReason());
        jsonGenerator.writeStringField("process",process);
        jsonGenerator.writeStringField("reportTime",topicInfo.getReportTime()==null?"null":topicInfo.getReportTime().toString());
        jsonGenerator.writeEndObject();
    }
}
