package com.exercise.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.sql.Date;

@JsonSerialize(using=AuditSerializer.class)
public class Audit {
    private Integer id;

    private Integer topicId;

    private String auditor;
    private Integer result;
    private String opinion;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "id=" + id +
                ", topicId=" + topicId +
                ", auditor='" + auditor + '\'' +
                ", result=" + result +
                ", opinion='" + opinion + '\'' +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}

class AuditSerializer extends JsonSerializer<Audit>{

    @Override
    public void serialize(Audit audit, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id",audit.getId());
        jsonGenerator.writeStringField("auditor",audit.getAuditor());
        jsonGenerator.writeStringField("result",audit.getResult()==1?"通过":"不通过");
        jsonGenerator.writeStringField("opinion",audit.getOpinion());
        jsonGenerator.writeStringField("date",audit.getDate().toString());
        jsonGenerator.writeStringField("status",audit.getStatus()==1?"初审":"终审");
        jsonGenerator.writeEndObject();
    }
}
