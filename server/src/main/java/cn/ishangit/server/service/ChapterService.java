package cn.ishangit.server.service;

import cn.ishangit.server.domain.Chapter;
import cn.ishangit.server.domain.ChapterExample;
import cn.ishangit.server.dto.ChapterDto;
import cn.ishangit.server.dto.PageDto;
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


    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapters);
        pageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto>  chapterDtoList = CopyUtils.copyList(chapters,ChapterDto.class);
        pageDto.setList(chapterDtoList);
    }

    public void save(ChapterDto chapterDto){
        Chapter chapter = CopyUtils.copy(chapterDto,Chapter.class);
        if(StringUtils.isEmpty(chapter.getId())){
            this.insert(chapter);
        }else {
            this.update(chapter);
        }
    }
    public void update(Chapter chapter){
        chapterMapper.updateByPrimaryKey(chapter);
    }

    public void insert(Chapter chapter){
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }

    public void delete(String id){
        chapterMapper.deleteByPrimaryKey(id);
    }

}