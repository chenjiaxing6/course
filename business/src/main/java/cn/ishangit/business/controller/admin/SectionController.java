package cn.ishangit.business.controller.admin;

import cn.ishangit.server.dto.ResponseDto;
import cn.ishangit.server.dto.SectionDto;
import cn.ishangit.server.dto.SectionPageDto;
import cn.ishangit.server.service.SectionService;
import cn.ishangit.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/section")
public class SectionController {

    @Resource
    private SectionService sectionService;

    public static final String BUSINESS_NAME = "小节";

    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);

    /**
     * 列表查询
     * @param sectionPageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody SectionPageDto sectionPageDto) {
        ValidatorUtil.require(sectionPageDto.getCourseId(),"课程ID");
        ValidatorUtil.require(sectionPageDto.getChapterId(),"大章ID");
        sectionService.list(sectionPageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(sectionPageDto);
        return responseDto;
    }

    /**
     * 保存
     * @param sectionDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody SectionDto sectionDto) {
        // 保存校验
        ValidatorUtil.require(sectionDto.getTitle(), "标题");
        ValidatorUtil.length(sectionDto.getTitle(), "标题", 1, 50);
        ValidatorUtil.length(sectionDto.getVideo(), "视频", 1, 200);
        sectionService.save(sectionDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(sectionDto);
        return responseDto;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        sectionService.delete(id);
        return responseDto;
    }
}