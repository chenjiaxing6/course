package cn.ishangit.system.controller;

import cn.ishangit.system.domain.Test;
import cn.ishangit.system.service.TextService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Chen
 * @date 2021-06-17
 * @description
 */
@RestController
public class TestController {

    @Resource
    private TextService textService;



    @RequestMapping("/test")
    public List<Test> test() {
        return textService.list();
    }
}
