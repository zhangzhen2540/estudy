package indi.gt.estudy.controller;

import indi.gt.estudy.EStudyConst;
import indi.gt.estudy.entity.Forum;
import indi.gt.estudy.entity.Student;
import indi.gt.estudy.service.ForumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/3.
 */
@Controller
@RequestMapping("/forum")
public class ForumController {
    @Resource
    private ForumService forumService;


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
            result.put("msg", "获取失败");
        } else {
            List<Forum> forums = forumService.getRootForumByGrade(currentStudent.getGradeId());
            result.put("list", forums);
            result.put("status", 0);
            result.put("msg", "获取成功");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/childForums")
    public Map<String, Object> childForums(@ModelAttribute Student currentStudent, @RequestParam Integer forumId) {
        Map<String, Object> result = new HashMap<>();
        if(currentStudent == null) {
            result.put("status", 1);
            result.put("msg", "获取失败");
        } else {
            List<Forum> forums = forumService.getChildForums(forumId);
            result.put("list", forums);
            result.put("status", 0);
            result.put("msg", "获取成功");
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/reply", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> replay(@ModelAttribute Student currentStudent, @RequestParam Integer replyForumId, @RequestParam String replyContent) {
        Map<String, Object> result = new HashMap<>();
        if(currentStudent == null) {
            result.put("status", 1);
            result.put("msg", "提交回复失败");
        } else {
            Forum forum = new Forum();
            forum.setContent(replyContent);
            forum.setAuthorId(currentStudent.getId());
            forum.setAuthorName(currentStudent.getName());
            forum.setAuthorType(2);
            forum.setCreateTime(new Date());
            forum.setGradeId(currentStudent.getGradeId());
            forum.setParentId(replyForumId);
            forum.setSort(forumService.getChildForumCount(replyForumId) + 1);
            forumService.save(forum);

            result.put("status", 0);
            result.put("msg", "提交回复成功");
            result.put("forum", forum);
        }
        return result;
    }
}
