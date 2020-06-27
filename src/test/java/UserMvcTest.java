import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml","classpath:applicationContext.xml"})
public class UserMvcTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 测试登录
     * @throws Exception
     */
    @Test
    public void loginTest() throws Exception {
        Map<String,String> content=new HashMap<>();
        content.put("id","1001");
        content.put("password","124");
        String con= new ObjectMapper().writeValueAsString(content);
        String result=mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(con))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getPage() throws Exception{

        String result=mockMvc.perform(get("/userList/?page=1&limit=10")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }
    @Test
    public void putUserTest() throws Exception{
//        new UserInfo(1101,"456","wei",1,"12345678910","test")

        Map<String,String> content=new HashMap<>();
        content.put("id","1100");
        content.put("password","123");
        content.put("phone","15999999999");
        content.put("name","mei");
        content.put("gender","1");
        content.put("role","1");

        String result=mockMvc.perform(put("/user")
                .content(new ObjectMapper().writeValueAsString(content))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void postUserTest() throws Exception{

        Map<String,String> content=new HashMap<>();

        content.put("phone","15999999999");
        content.put("name","mei");
        content.put("gender","1");
        content.put("role","1");

        String result=mockMvc.perform(post("/user")
                .content(new ObjectMapper().writeValueAsString(content))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void deleteUser() throws Exception{
        String result=mockMvc.perform(delete("/user/1102"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getUser() throws Exception{
        String result=mockMvc.perform(get("/user/1100"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }
}
