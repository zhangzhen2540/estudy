package indi.gt.estudy.service;

import indi.gt.estudy.entity.Student;
import indi.gt.estudy.entity.StudentExample;
import indi.gt.estudy.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;


    public void save(Student student) {
        studentMapper.insertSelective(student);
    }

    public void update(Student student) {
        studentMapper.updateByPrimaryKey(student);
    }

    public Student get(int id) {
        return studentMapper.selectByPrimaryKey(id);
    }


    public Student getByUsername(String username) {
        if(username == null) {
            return null;
        }

        StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<Student> result = studentMapper.selectByExample(example);
        return result.isEmpty() ? null : result.get(0);
    }
}
