package cn.ishangit.business.controller.admin;

import cn.ishangit.server.dto.ChapterDto;
import cn.ishangit.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    @RequestMapping("/hello")
    public String helloWorld() {
        return "success";
    }

    @RequestMapping("/chapter")
    public List<ChapterDto> chapter() {
        return chapterService.list();
    }

}