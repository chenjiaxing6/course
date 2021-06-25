package cn.ishangit.server.service;

import cn.ishangit.server.domain.Section;
import cn.ishangit.server.domain.SectionExample;
import cn.ishangit.server.dto.SectionDto;
import cn.ishangit.server.dto.PageDto;
import cn.ishangit.server.mapper.SectionMapper;
import cn.ishangit.server.util.CopyUtils;
import cn.ishangit.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SectionService {
    @Resource
    private SectionMapper sectionMapper;

    /**
     *列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        SectionExample sectionExample = new SectionExample();
        List<Section> sections = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sections);
        pageDto.setTotal(pageInfo.getTotal());
        List<SectionDto>  sectionDtoList = CopyUtils.copyList(sections,SectionDto.class);
        pageDto.setList(sectionDtoList);
    }

    /**
     * 保存
     * @param sectionDto
     */
    public void save(SectionDto sectionDto){
        Section section = CopyUtils.copy(sectionDto,Section.class);
        if(StringUtils.isEmpty(section.getId())){
            this.insert(section);
        }else {
            this.update(section);
        }
    }

    /**
     * 更新
     * @param section
     */
    public void update(Section section){
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