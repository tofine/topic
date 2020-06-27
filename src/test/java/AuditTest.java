import com.exercise.dao.AuditDao;
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

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml","classpath:applicationContext.xml"})
public class AuditTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Autowired
    private AuditDao auditDao;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mapper=new ObjectMapper();
    }

    @Test//审核
    public void insert() throws Exception {

        Map<String,String> map=new HashMap<>();
        map.put("auditor","lihua");
        map.put("date","2020-05-06");
        map.put("status","2");
        map.put("opinion","bbb");
        map.put("result","1");
        map.put("topicId","46");

        System.out.println(mapper.writeValueAsString(map));

        mockMvc.perform(post("/audit")
                .contentType("application/json")
                .content(mapper.writeValueAsString(map)))
                .andDo(print())
        .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void testPage() throws Exception {
        mockMvc.perform(get("/auditList/27")
        .param("page","1")
        .param("limit","10")
                .contentType("application/json")
        ).andDo(print()).andReturn().getResponse().getContentAsString();
    }
}
