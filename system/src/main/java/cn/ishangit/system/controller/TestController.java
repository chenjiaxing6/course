package cn.ishangit.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chen
 * @date 2021-06-17
 * @description
 */
@RestController
public class TestController {


    @RequestMapping("/test")
    public String test(){
        return "success";
    }
}
