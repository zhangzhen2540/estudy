package indi.gt.estudy.controller;

import indi.gt.estudy.entity.Student;
import indi.gt.estudy.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/3/30.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private StudentService studentService;

    @ResponseBody
    @RequestMapping
    public Student test() {
        return studentService.get(1);
    }


    @RequestMapping("/test2")
    public String test2() {
        Student student = new Student();
        student.setUsername("sds");
        student.setPassword("sds");
        student.setEmail("ew@we.com");
        student.setPhone("130");
        student.setName("hahaha");
        student.setGradeId(1);
        studentService.save(student);
        return "hello";
    }
}
