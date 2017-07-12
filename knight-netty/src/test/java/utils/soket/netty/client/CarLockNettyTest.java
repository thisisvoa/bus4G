package utils.soket.netty.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by feixiang on 2016-09-20.
 */
public class CarLockNettyTest {
    @Autowired
    private ApplicationContext applicationContext;


    public static void main(String[] args) {

        System.out.println("test");
        String[] configLocation = {"classpath*:spring-server.xml"};
        ApplicationContext factory = new FileSystemXmlApplicationContext(configLocation);
    }
}
