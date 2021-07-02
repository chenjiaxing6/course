package cn.ishangit.business.controller.admin;

import cn.ishangit.server.dto.CategoryDto;
import cn.ishangit.server.dto.PageDto;
import cn.ishangit.server.dto.ResponseDto;
import cn.ishangit.server.service.CategoryService;
import cn.ishangit.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    public static final String BUSINESS_NAME = "目录表";

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        categoryService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 列表查询
     * @return
     */
    @PostMapping("/all")
    public ResponseDto all() {
        List<CategoryDto> categoryDtoList = categoryService.all();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(categoryDtoList);
        return responseDto;
    }

    /**
     * 保存
     * @param categoryDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CategoryDto categoryDto) {
        // 保存校验
        ValidatorUtil.require(categoryDto.getParent(), "父id");
        ValidatorUtil.require(categoryDto.getName(), "名称");
        ValidatorUtil.length(categoryDto.getName(), "名称", 1, 50);
        categoryService.save(categoryDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(categoryDto);
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
        categoryService.delete(id);
        return responseDto;
    }
}