package cn.ishangit.business.controller.admin;

import cn.ishangit.server.dto.CourseCategoryDto;
import cn.ishangit.server.dto.CourseDto;
import cn.ishangit.server.dto.PageDto;
import cn.ishangit.server.dto.ResponseDto;
import cn.ishangit.server.service.CourseCategoryService;
import cn.ishangit.server.service.CourseService;
import cn.ishangit.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @Resource
    private CourseCategoryService courseCategoryService;

    public static final String BUSINESS_NAME = "课程";

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);

    /**
     * 查找课程下所有分类
     * @param courseId
     */
    @PostMapping("/list-category/{courseId}")
    public ResponseDto listCategory(@PathVariable(value = "courseId") String courseId) {
        ResponseDto responseDto = new ResponseDto();
        List<CourseCategoryDto> dtoList = courseCategoryService.listByCourse(courseId);
        responseDto.setContent(dtoList);
        return responseDto;
    }

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        courseService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存
     * @param courseDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto) {
        // 保存校验
        ValidatorUtil.require(courseDto.getName(), "名称");
        ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
        courseService.save(courseDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(courseDto);
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
        courseService.delete(id);
        return responseDto;
    }
}