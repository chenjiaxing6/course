package cn.ishangit.server.mapper.my;

import org.apache.ibatis.annotations.Param;

public interface MyCourseMapper {
    int updateTime(@Param("courseId") String courseId);
}