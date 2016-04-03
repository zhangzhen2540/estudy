package indi.gt.estudy.controller;

import indi.gt.estudy.EStudyConst;
import indi.gt.estudy.entity.Notice;
import indi.gt.estudy.entity.Student;
import indi.gt.estudy.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/3.
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @ModelAttribute
    public Student currentStudent(HttpSession session) {
        return (Student) session.getAttribute(EStudyConst.CURRENT_USER_KEY);
    }

    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> list(@ModelAttribute Student currentStudent) {
        Map<String, Object> result = new HashMap<>();
        if(currentStudent == null) {
            result.put("status", 1);
            result.put("msg", "获取公告列表失败");
        } else {
            List<Notice> noticeList = noticeService.getByGrade(currentStudent.getGradeId());
            result.put("status", 0);
            result.put("list", noticeList);
        }
        return result;
    }
}
