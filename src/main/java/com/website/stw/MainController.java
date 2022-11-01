package com.website.stw;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @RequestMapping("/stw")
    @ResponseBody
    public String index(){
        return "welcome!";
    }
}
