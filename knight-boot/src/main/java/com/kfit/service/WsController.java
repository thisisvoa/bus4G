package com.kfit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/7/12.
 */
@Controller
public class WsController {


    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public ResponseMessage say() {


            return new ResponseMessage("welcome," + " !");
    }
    @SendTo("/topic/getResponse2")
    public ResponseMessage say2() {


        return null;

    }


    @RequestMapping(value = "/1", method = RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("hello", " 1123123");
        return "welcome";
    }


}
