package indi.gt.estudy.controller;

import indi.gt.estudy.EStudyConst;
import indi.gt.estudy.entity.Answer;
import indi.gt.estudy.entity.Homework;
import indi.gt.estudy.entity.Student;
import indi.gt.estudy.service.HomeworkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/30.
 */
@Controller
@RequestMapping("/homework")
public class HomeworkController {

    @Resource
    private HomeworkService homeworkService;

    @ModelAttribute
    public Student currentStudent(HttpSession session) {
        return (Student) session.getAttribute(EStudyConst.CURRENT_USER_KEY);
    }

    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> list(@ModelAttribute Student currentStudent) {
        Map<String, Object> result = new HashMap<>();
        if(currentStudent != null) {
            List<Homework> homeworkList = homeworkService.getByGrade(currentStudent.getGradeId());
            result.put("status", 0);
            result.put("msg", "获取成功");
            result.put("list", homeworkList);
        } else {
            result.put("status", 1);
            result.put("msg", "获取失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/answer")
    public Map<String, Object> answer(@ModelAttribute Student currentStudent, @RequestParam Integer homeworkId) {
        Map<String, Object> result = new HashMap<>();
        if(currentStudent != null) {
            Answer answer = homeworkService.getStudentHomeworkAnswer(currentStudent.getId(), homeworkId);
            result.put("status", 0);
            result.put("msg", "获取成功");
            result.put("answer", answer);
        } else {
            result.put("status", 1);
            result.put("msg", "获取失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/answerSubmit", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> answerSubmit(@ModelAttribute Student currentStudent, @RequestParam Integer homeworkId, @RequestParam String content) {
        Map<String, Object> result = new HashMap<>();
        Homework homework = homeworkService.get(homeworkId);
        if(currentStudent != null && homework != null) {
            Answer answer = new Answer();
            answer.setContent(content);
            answer.setCreateTime(new Date());
            answer.setHomeworkId(homeworkId);
            answer.setHomeworkTitle(homework.getTitle());
            answer.setHomeworkType(homework.getType());
            answer.setStatus(0);
            answer.setStudentId(currentStudent.getId());
            answer.setStudentName(currentStudent.getName());
            homeworkService.saveAnswer(answer);

            result.put("status", 0);
            result.put("msg", "提交作业答案成功, homeworkId=" + homeworkId + "; content=" + content);
            result.put("answer", answer);
        } else {
            result.put("status", 1);
            result.put("msg", "提交失败");
        }
        return result;
    }

}
