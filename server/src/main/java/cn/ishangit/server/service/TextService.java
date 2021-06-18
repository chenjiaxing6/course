package cn.ishangit.server.service;

import cn.ishangit.server.domain.Test;
import cn.ishangit.server.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TextService {
    @Resource
    private TestMapper testMapper;

    public List<Test> list() {
        return testMapper.selectByExample(null);
    }

}