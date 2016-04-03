package indi.gt.estudy.controller;

import indi.gt.estudy.EStudyConst;
import indi.gt.estudy.entity.Grade;
import indi.gt.estudy.entity.Student;
import indi.gt.estudy.service.GradeService;
import indi.gt.estudy.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/30.
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @Resource
    private GradeService gradeService;


    @ResponseBody
    @RequestMapping(value = "/changePassword", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> changePassword(@RequestParam String password, @RequestParam String newPassword,
                                              HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Student currentStudent = (Student) session.getAttribute(EStudyConst.CURRENT_USER_KEY);
        if(currentStudent == null || !currentStudent.getPassword().equals(password)) {
            result.put("status", 1);
            result.put("msg", "修改密码失败");
        } else {
            currentStudent.setPassword(newPassword);
            studentService.update(currentStudent);
            result.put("status", 0);
            result.put("msg", "修改密码成功");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/changeInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> changeInfo(Student student, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Student currentStudent = (Student) session.getAttribute(EStudyConst.CURRENT_USER_KEY);
        if(currentStudent == null) {
            result.put("status", 1);
            result.put("msg", "修改失败");
        } else {
            if(student.getEmail() != null) {
                currentStudent.setEmail(student.getEmail());
            }
            if(student.getPhone() != null) {
                currentStudent.setPhone(student.getPhone());
            }
            studentService.update(currentStudent);

            result.put("status", 0);
            result.put("msg", "修改信息成功");
            result.put("student", currentStudent);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public Map<String, Object> login(@RequestParam String username, @RequestParam String password,
                                     HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Student student = studentService.getByUsername(username);
        if(student == null || !student.getPassword().equals(password)) {
            // login failure
            result.put("status", 1);
            result.put("msg", "用户名或密码错误");
        } else {
            session.setAttribute(EStudyConst.CURRENT_USER_KEY, student);

            Grade grade = gradeService.get(student.getGradeId());

            result.put("status", 0);
            result.put("msg", "登录成功");
            result.put("student", student);
            result.put("grade", grade);
        }
        return result;
    }
}
