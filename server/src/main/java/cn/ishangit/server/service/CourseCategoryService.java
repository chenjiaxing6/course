package cn.ishangit.server.service;

import cn.ishangit.server.domain.CourseCategory;
import cn.ishangit.server.domain.CourseCategoryExample;
import cn.ishangit.server.dto.CategoryDto;
import cn.ishangit.server.dto.CourseCategoryDto;
import cn.ishangit.server.dto.PageDto;
import cn.ishangit.server.mapper.CourseCategoryMapper;
import cn.ishangit.server.util.CopyUtils;
import cn.ishangit.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CourseCategoryService {
    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    /**
     *列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        List<CourseCategory> courseCategorys = courseCategoryMapper.selectByExample(courseCategoryExample);
        PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategorys);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseCategoryDto>  courseCategoryDtoList = CopyUtils.copyList(courseCategorys,CourseCategoryDto.class);
        pageDto.setList(courseCategoryDtoList);
    }

    /**
     * 保存
     * @param courseCategoryDto
     */
    public void save(CourseCategoryDto courseCategoryDto){
        CourseCategory courseCategory = CopyUtils.copy(courseCategoryDto,CourseCategory.class);
        if(StringUtils.isEmpty(courseCategory.getId())){
            this.insert(courseCategory);
        }else {
            this.update(courseCategory);
        }
    }

    /**
     * 更新
     * @param courseCategory
     */
    public void update(CourseCategory courseCategory){
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

    /**
     * 插入
     * @param courseCategory
     */
    public void insert(CourseCategory courseCategory){
        courseCategory.setId(UuidUtil.getShortUuid());
        courseCategoryMapper.insert(courseCategory);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        courseCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据某一课程，先清空课程分类，再保存课程分类
     * @param dtoList
     */
    @Transactional
    public void saveBatch(String courseId, List<CategoryDto> dtoList) {
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        // 删除原本的分类信息
        courseCategoryMapper.deleteByExample(example);
        // 添加新的分类信息
        for (int i = 0, l = dtoList.size(); i < l; i++) {
            CategoryDto categoryDto = dtoList.get(i);
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UuidUtil.getShortUuid());
            courseCategory.setCourseId(courseId);
            courseCategory.setCategoryId(categoryDto.getId());
            insert(courseCategory);
        }
    }

    /**
     * 查找课程下所有分类
     * @param courseId
     */
    public List<CourseCategoryDto> listByCourse(String courseId) {
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(example);
        return CopyUtils.copyList(courseCategoryList, CourseCategoryDto.class);
    }

}