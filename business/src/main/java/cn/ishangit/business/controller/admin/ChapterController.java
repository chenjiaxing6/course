package cn.ishangit.business.controller.admin;

import cn.ishangit.server.dto.ChapterDto;
import cn.ishangit.server.dto.ChapterPageDto;
import cn.ishangit.server.dto.ResponseDto;
import cn.ishangit.server.service.ChapterService;
import cn.ishangit.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    public static final String BUSINESS_NAME = "大章";

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);

    /**
     * 列表查询
     * @param chapterPageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody ChapterPageDto chapterPageDto) {
        ValidatorUtil.require(chapterPageDto.getCourseId(),"课程ID");
        chapterService.list(chapterPageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(chapterPageDto);
        return responseDto;
    }

    /**
     * 保存
     * @param chapterDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ChapterDto chapterDto) {
        ValidatorUtil.require(chapterDto.getName(), "名称");
        ValidatorUtil.require(chapterDto.getCourseId(), "课程ID");
        ValidatorUtil.length(chapterDto.getCourseId(), "课程ID", 1, 8);
        chapterService.save(chapterDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(chapterDto);
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
        chapterService.delete(id);
        return responseDto;
    }
}