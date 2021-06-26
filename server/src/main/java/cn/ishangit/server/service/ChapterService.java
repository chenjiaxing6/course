package cn.ishangit.server.service;

import cn.ishangit.server.domain.Chapter;
import cn.ishangit.server.domain.ChapterExample;
import cn.ishangit.server.dto.ChapterDto;
import cn.ishangit.server.dto.ChapterPageDto;
import cn.ishangit.server.mapper.ChapterMapper;
import cn.ishangit.server.util.CopyUtils;
import cn.ishangit.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;

    /**
     *列表查询
     * @param chapterPageDto
     */
    public void list(ChapterPageDto chapterPageDto) {
        PageHelper.startPage(chapterPageDto.getPage(), chapterPageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
        //条件查询
        ChapterExample.Criteria criteria = chapterExample.createCriteria();
        if (!StringUtils.isEmpty(chapterPageDto.getCourseId())){
            criteria.andCourseIdEqualTo(chapterPageDto.getCourseId());
        }
        List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapters);
        chapterPageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto>  chapterDtoList = CopyUtils.copyList(chapters,ChapterDto.class);
        chapterPageDto.setList(chapterDtoList);
    }

    /**
     * 保存
     * @param chapterDto
     */
    public void save(ChapterDto chapterDto){
        Chapter chapter = CopyUtils.copy(chapterDto,Chapter.class);
        if(StringUtils.isEmpty(chapter.getId())){
            this.insert(chapter);
        }else {
            this.update(chapter);
        }
    }

    /**
     * 更新
     * @param chapter
     */
    public void update(Chapter chapter){
        chapterMapper.updateByPrimaryKey(chapter);
    }

    /**
     * 插入
     * @param chapter
     */
    public void insert(Chapter chapter){
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        chapterMapper.deleteByPrimaryKey(id);
    }

}