package cn.ishangit.server.service;

import cn.ishangit.server.domain.${Domain};
import cn.ishangit.server.domain.${Domain}Example;
import cn.ishangit.server.dto.${Domain}Dto;
import cn.ishangit.server.dto.PageDto;
import cn.ishangit.server.mapper.${Domain}Mapper;
import cn.ishangit.server.util.CopyUtils;
import cn.ishangit.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

<#list typeSet as type>
    <#if type=='Date'>
import java.util.Date;
    </#if>
</#list>

@Service
public class ${Domain}Service {
    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    /**
     *列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        <#list fieldList as field>
            <#if field.nameHump=='sort'>
        ${domain}Example.setOrderByClause("sort asc");
            </#if>
        </#list>
        List<${Domain}> ${domain}s = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}s);
        pageDto.setTotal(pageInfo.getTotal());
        List<${Domain}Dto>  ${domain}DtoList = CopyUtils.copyList(${domain}s,${Domain}Dto.class);
        pageDto.setList(${domain}DtoList);
    }

    /**
     * 保存
     * @param ${domain}Dto
     */
    public void save(${Domain}Dto ${domain}Dto){
        ${Domain} ${domain} = CopyUtils.copy(${domain}Dto,${Domain}.class);
        if(StringUtils.isEmpty(${domain}.getId())){
            this.insert(${domain});
        }else {
            this.update(${domain});
        }
    }

    /**
     * 更新
     * @param ${domain}
     */
    public void update(${Domain} ${domain}){
        <#list fieldList as field>
            <#if field.nameHump=='updatedAt'>
        ${domain}.setUpdatedAt(new Date());
            </#if>
        </#list>
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }

    /**
     * 插入
     * @param ${domain}
     */
    public void insert(${Domain} ${domain}){
        <#list typeSet as type>
            <#if type=='Date'>
        Date now = new Date();
            </#if>
        </#list>
        <#list fieldList as field>
            <#if field.nameHump=='createdAt'>
        ${domain}.setCreatedAt(now);
            </#if>
            <#if field.nameHump=='updatedAt'>
        ${domain}.setUpdatedAt(now);
            </#if>
        </#list>
        ${domain}.setId(UuidUtil.getShortUuid());
        ${domain}Mapper.insert(${domain});
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        ${domain}Mapper.deleteByPrimaryKey(id);
    }

}