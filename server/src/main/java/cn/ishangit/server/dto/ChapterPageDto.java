package cn.ishangit.server.dto;

/**
 * @author Chen
 * @date 2021-06-26
 * @description
 */
public class ChapterPageDto extends PageDto {
    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ChapterPageDto{");
        sb.append("courseId='").append(courseId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
