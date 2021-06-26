package cn.ishangit.server.service;

import cn.ishangit.server.domain.Course;
import cn.ishangit.server.domain.CourseExample;
import cn.ishangit.server.dto.CourseDto;
import cn.ishangit.server.dto.PageDto;
import cn.ishangit.server.mapper.CourseMapper;
import cn.ishangit.server.mapper.my.MyCourseMapper;
import cn.ishangit.server.util.CopyUtils;
import cn.ishangit.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {
    @Resource
    private CourseMapper courseMapper;

    @Resource
    private MyCourseMapper myCourseMapper;

    private final static Logger LOG = LoggerFactory.getLogger(CourseService.class);

    /**
     *列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseExample = new CourseExample();
        courseExample.setOrderByClause("sort asc");
        List<Course> courses = courseMapper.selectByExample(courseExample);
        PageInfo<Course> pageInfo = new PageInfo<>(courses);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseDto>  courseDtoList = CopyUtils.copyList(courses,CourseDto.class);
        pageDto.setList(courseDtoList);
    }

    /**
     * 保存
     * @param courseDto
     */
    public void save(CourseDto courseDto){
        Course course = CopyUtils.copy(courseDto,Course.class);
        Date now = new Date();
        course.setCreatedAt(now);
        course.setUpdatedAt(now);
        if(StringUtils.isEmpty(course.getId())){
            this.insert(course);
        }else {
            this.update(course);
        }
    }

    /**
     * 更新
     * @param course
     */
    public void update(Course course){
        course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }

    /**
     * 插入
     * @param course
     */
    public void insert(Course course){
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        courseMapper.deleteByPrimaryKey(id);
    }

    /*
     * 更新课程时长
     * @param courseId
     * @return
     */
    public void updateTime(String courseId) {
        LOG.info("更新课程时长：{}", courseId);
        myCourseMapper.updateTime(courseId);
    }

}