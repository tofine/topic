import com.exercise.bean.UserInfo;
import com.exercise.service.UserInfoService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Random;

public class UserInfoTest {

    private ApplicationContext ioc;

    private final int[] duty={1,2,3};

    private Random r;
    private UserInfoService user;
    @Before
    public void getContext(){
        ioc=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        r=new Random();
        user=ioc.getBean(UserInfoService.class);
    }
    /**
     * 批量插入数据
     */
    @Test
    public void batchUser(){

        for(int i=1;i<=100;i++){
            Integer id =1000+i;
            String password="123";
            String name="user-"+i;
            int gender=r.nextInt(2);
            String phone="phoneNumber-"+i;
            int role=duty[r.nextInt(duty.length)];
            user.addUser(new UserInfo(id,password,name,gender,phone,role));
       }

    }



}
