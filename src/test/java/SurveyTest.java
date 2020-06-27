import com.exercise.bean.Survey;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml","classpath:applicationContext.xml"})
public class SurveyTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void getPage() throws Exception {
        mockMvc.perform(get("/onePageSurvey?page=1&limit=10")
                .contentType("application/json"))
                .andDo(print()).andReturn().getResponse().getContentAsString();
    }

    @Test//市场调查完成送终审
    public void addOne() throws Exception {
        Survey survey = new Survey();
        survey.setInvestigator("lili");
        survey.setPath("E://topic/upload/abc.docx");
        survey.setTopicId(46);
        survey.setSurveyTime(new Date(new java.util.Date().getTime()));
        mockMvc.perform(post("/survey")
                .content(mapper.writeValueAsString(survey))
                .contentType("application/json;charset=utf-8"))
                .andDo(print()).andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getOne() throws Exception {
        mockMvc.perform(get("/survey?topicId=6")
                .contentType("application/json;charset=utf-8"))
                .andDo(print()).andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getFile() throws Exception {
        mockMvc.perform(get("/download/16")
                .contentType("application/json;charset=utf-8"))
                .andDo(print()).andReturn().getResponse().getContentAsString();
    }

}