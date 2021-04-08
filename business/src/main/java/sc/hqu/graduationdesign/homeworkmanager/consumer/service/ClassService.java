package sc.hqu.graduationdesign.homeworkmanager.consumer.service;

import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.ClassDataDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.ClassStudentAddDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.ClassUpdateDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.ClassCreateDto;
import sc.hqu.graduationdesign.homeworkmanager.exception.BusinessException;

import java.util.List;

/**
 * 班级服务接口
 * @author tzx
 * @date 2021-04-07 20:26
 */
public interface ClassService {

    /**
     * 根据教工号来获取该教师管理的班级信息
     * @param teacherNo     教工号
     * @return              {@link ClassDataDto}
     */
    List<ClassDataDto>  getClassDataByTeacherNo(Long teacherNo);

    /**
     * 获取班级学生的分页数据
     * @param teacherNo     教工号
     * @param pageSize      分页大小
     * @param pageNum       分页索引
     * @return              分页数据
     */
    Object getClassStudentPage(Long teacherNo,int pageSize,int pageNum);

    /**
     * 创建班级
     * @param dto       {@link ClassCreateDto}
     * @return          创建完成的班级数据写入对象
     */
    ClassCreateDto create(ClassCreateDto dto);

    /**
     * 更新班级信息
     * @param dto       {@link ClassUpdateDto}
     */
    void update(ClassUpdateDto dto);

    /**
     * 删除班级
     * @param classId               班级id
     * @throws BusinessException    业务异常
     */
    void delete(Long classId) throws BusinessException;

    /**
     * 批量添加学生
     * @param dto       {@link ClassStudentAddDto}
     */
    void batchAddStudent(ClassStudentAddDto dto);

    /**
     * 将学生从班级中移除
     * @param studentNo     学号
     */
    void removeClassStudent(Long studentNo);

}
