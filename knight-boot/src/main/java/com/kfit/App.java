package com.kfit;

import cn.com.reformer.netty.bean.Client;
import cn.com.reformer.netty.bean.TcpUser;
import cn.com.reformer.netty.bean.TcpUserVO;
import cn.com.reformer.netty.communication.CarLockTcpMessageSender;
import cn.com.reformer.netty.guava.SimpleListener;
import com.kfit.service.*;
import com.kfit.service.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 大家也许会看到有些demo使用了3个注解： @Configuration；
 *
 * @author Angel(QQ:412887952)
 * @version v.0.1
 * @EnableAutoConfiguration
 * @ComponentScan 其实：@SpringBootApplication申明让spring boot自动给程序进行必要的配置，
 * <p/>
 * 等价于以默认属性使用@Configuration，
 * @EnableAutoConfiguration和@ComponentScan 所以大家不要被一些文档误导了，让自己很迷茫了，希望本文章对您有所启发；
 */
@Controller
@EnableAutoConfiguration
@SpringBootApplication
public class App {

    @Autowired
    private CarLockTcpMessageSender carLockTcpMessageSender;
    @Autowired
    SimpleListener simpleListener;
    @Resource
    SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("hello", " 1123123");
        return "hello";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {

        ConcurrentHashMap<String, Client> clientConcurrentHashMap = carLockTcpMessageSender.getClientManager().getClientMap();
        List<TcpUser> tcpUserList = new ArrayList<TcpUser>();
        Iterator iter = clientConcurrentHashMap.entrySet().iterator();
        StringBuffer sb = new StringBuffer();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            Client user = (Client) val;
            if (null != user) {
                TcpUser user1 = user.getUser();
                TcpUserVO vo = new TcpUserVO();
                vo.setSn(user1.getSn());
                vo.setType(user1.getType());
                vo.setIpAndPort(key.toString());
                tcpUserList.add(vo);
                sb.append("clientId:" + key + "---" + "TcpUser:" + user1.getSn());
            }

            sb.append("<br>");
        }
        // model.addAttribute("list", sb);
        model.addAttribute("tcpUserList", tcpUserList);

        return "list";
    }


    @RequestMapping(value = "/open", method = RequestMethod.GET)
    @ResponseBody
    public String open(String sn) {
        carLockTcpMessageSender.openDoor(sn);
        return "open success";
    }

    // @MessageMapping("/face")
    // @SendTo("/topic/getResponse")
    @RequestMapping(value = "/face", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> face(String sn, Integer num) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");//设置日期格式
        Date date1 = new Date();
        String date = df.format(date1);// new Date()为获取当前系统时间，也可使用当前时间戳
        System.out.println("startTime:"+date);
        carLockTcpMessageSender.face(sn, num);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", date);
        map.put("startLong", date1.getTime());
        return map;
        // return  new ResponseMessage("test");
    }

    @RequestMapping(value = "/handlerQrcode", method = RequestMethod.GET)
    @ResponseBody
    public void handlerQrcode(String sn) {
        carLockTcpMessageSender.handlerQrcode(sn);
    }

    @RequestMapping(value = "/getStatus", method = RequestMethod.GET)
    @ResponseBody
    public String getStatus(String sn) {
        carLockTcpMessageSender.getStatus(sn);
        return "open success";
    }

    //这里指定是条状的jsp界面
    @RequestMapping(value = "/index1")
    public String index(Model model) {
        model.addAttribute("sb", "this is my fries测试不是好领导了副经理看fjldj 1123123");
        return "index";
    }

    @RequestMapping(value = "json")
    @ResponseBody
    public Map<String, Object> mytest() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "Ryan");
        map.put("age", "3");
        map.put("sex", "man");
        return map;
    }


    /*@Bean
    InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }*/

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


}
