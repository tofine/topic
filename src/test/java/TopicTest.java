import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.Hashtable;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml","classpath:applicationContext.xml"})
public class TopicTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test//新建选题
    public void addTest() throws Exception {

        Hashtable<String,String> m=new Hashtable<>();
        m.put("bookName","book-n");
        m.put("bookClassify","dwjf");
        m.put("bookNature","23");
        m.put("declareReason","33");
        m.put("edition","4");
        m.put("editor","lihua");
        m.put("introduction","fdf");
        m.put("process","0");
        m.put("planContributeTime",new Date((new java.util.Date()).getTime()).toString());
        m.put("planPublishTime",new Date((new java.util.Date()).getTime()).toString());


        mockMvc.perform(post("/newTopic")
                .contentType("application/json")
                .characterEncoding("utf-8")
                .content(new ObjectMapper().writeValueAsString(m)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test//新建选题列表
    public void getNewPage() throws Exception {
        mockMvc.perform(get("/newTopicList?page=1&limit=10")
                .contentType("application/json")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test   //送初审
    public void next() throws Exception {
        mockMvc.perform(put("/next/46")
                .contentType("application/json")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getAuditPage() throws Exception {
        mockMvc.perform(get("/audit?page=1&limit=10")
                .contentType("application/json")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getReportPage() throws Exception {
        mockMvc.perform(get("/report?page=1&limit=10")
                .contentType("application/json")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test   //删除选题
    public void deleteTopic() throws Exception {
        mockMvc.perform(delete("/newTopic/49")
                .contentType("application/json")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }
}


