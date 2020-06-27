package com.exercise.controller;

import com.exercise.service.SurveyService;
import com.exercise.service.TopicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {

    TopicInfoService topicInfoService;
    SurveyService surveyService;

    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Autowired
    public void setTopicInfoService(TopicInfoService topicInfoService) {
        this.topicInfoService = topicInfoService;
    }

    @ResponseBody
    @PostMapping("/upload/{topicId}")
    public Map upload(@PathVariable int topicId, @RequestPart("file") MultipartFile file) {

        Map map=new HashMap();
        map.put("code","0");
        try{
            String original = file.getOriginalFilename();        //获取文件名
            String suffix = original.substring(original.lastIndexOf('.')); //获取文件后缀
            String filename = "E://topic/uploads/调查报告"+topicId+suffix;   //重名名
            file.transferTo(new File(filename));                //写入文件系统
            Map m = new HashMap();
            m.put("src",filename);
            map.put("data",m);
            map.put("msg","上传成功！");
        }catch (Exception e){
            map.put("data",null);
            map.put("msg","上传失败！");
        }
        return map;
    }

    @GetMapping("/download/{topicId}")
    public ResponseEntity<byte[]> download(@PathVariable int topicId) throws IOException {
        String filename=surveyService.getPath(topicId);  //surveyService.getPath获取文件路径
        File file = new File(filename);
        InputStream is = new FileInputStream(file);
        byte[] body = new byte[is.available()];
        is.read(body);
        is.close();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        return entity;
    }
}
