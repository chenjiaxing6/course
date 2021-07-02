package cn.ishangit.server.service;

import cn.ishangit.server.domain.Category;
import cn.ishangit.server.domain.CategoryExample;
import cn.ishangit.server.dto.CategoryDto;
import cn.ishangit.server.dto.PageDto;
import cn.ishangit.server.mapper.CategoryMapper;
import cn.ishangit.server.util.CopyUtils;
import cn.ishangit.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     *列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categorys);
        pageDto.setTotal(pageInfo.getTotal());
        List<CategoryDto>  categoryDtoList = CopyUtils.copyList(categorys,CategoryDto.class);
        pageDto.setList(categoryDtoList);
    }

    /**
     * 查询全部
     */
    public List<CategoryDto> all() {
        List<Category> categorys = categoryMapper.selectByExample(null);
        List<CategoryDto>  categoryDtoList = CopyUtils.copyList(categorys,CategoryDto.class);
        return categoryDtoList;
    }

    /**
     * 保存
     * @param categoryDto
     */
    public void save(CategoryDto categoryDto){
        Category category = CopyUtils.copy(categoryDto,Category.class);
        if(StringUtils.isEmpty(category.getId())){
            this.insert(category);
        }else {
            this.update(category);
        }
    }

    /**
     * 更新
     * @param category
     */
    public void update(Category category){
        categoryMapper.updateByPrimaryKey(category);
    }

    /**
     * 插入
     * @param category
     */
    public void insert(Category category){
        category.setId(UuidUtil.getShortUuid());
        categoryMapper.insert(category);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        categoryMapper.deleteByPrimaryKey(id);
    }

}