package cn.ishangit.server.service;

import cn.ishangit.server.domain.Section;
import cn.ishangit.server.domain.SectionExample;
import cn.ishangit.server.dto.SectionDto;
import cn.ishangit.server.dto.SectionPageDto;
import cn.ishangit.server.mapper.SectionMapper;
import cn.ishangit.server.util.CopyUtils;
import cn.ishangit.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SectionService {
    @Resource
    private SectionMapper sectionMapper;

    @Resource
    private CourseService courseService;

    /**
     *列表查询
     * @param sectionPageDto
     */
    public void list(SectionPageDto sectionPageDto) {
        PageHelper.startPage(sectionPageDto.getPage(), sectionPageDto.getSize());
        SectionExample sectionExample = new SectionExample();
        //条件查询
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if (!StringUtils.isEmpty(sectionPageDto.getCourseId())){
            criteria.andCourseIdEqualTo(sectionPageDto.getCourseId());
        }
        if (!StringUtils.isEmpty(sectionPageDto.getChapterId())){
            criteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
        }
        sectionExample.setOrderByClause("sort asc");
        List<Section> sections = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sections);
        sectionPageDto.setTotal(pageInfo.getTotal());
        List<SectionDto>  sectionDtoList = CopyUtils.copyList(sections,SectionDto.class);
        sectionPageDto.setList(sectionDtoList);
    }

    /**
     * 保存
     * @param sectionDto
     */
    @Transactional
    public void save(SectionDto sectionDto){
        Section section = CopyUtils.copy(sectionDto,Section.class);
        Date now = new Date();
        section.setCreatedAt(now);
        section.setUpdatedAt(now);
        if(StringUtils.isEmpty(section.getId())){
            this.insert(section);
        }else {
            this.update(section);
        }
        courseService.updateTime(sectionDto.getCourseId());
    }

    /**
     * 更新
     * @param section
     */
    public void update(Section section){
        section.setUpdatedAt(new Date());
        sectionMapper.updateByPrimaryKey(section);
    }

    /**
     * 插入
     * @param section
     */
    public void insert(Section section){
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        sectionMapper.deleteByPrimaryKey(id);
    }

}