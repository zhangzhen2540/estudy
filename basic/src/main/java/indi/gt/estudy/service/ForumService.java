package indi.gt.estudy.service;

import indi.gt.estudy.entity.Forum;
import indi.gt.estudy.entity.ForumExample;
import indi.gt.estudy.mapper.ForumMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
@Service
public class ForumService {
    @Resource
    private ForumMapper forumMapper;

    public Forum get(int id) {
        return forumMapper.selectByPrimaryKey(id);
    }

    public List<Forum> getRootForumByGrade(int gradeId) {
        ForumExample example = new ForumExample();
        ForumExample.Criteria criteria = example.createCriteria();
        criteria.andGradeIdEqualTo(gradeId);
        criteria.andStatusEqualTo(0);
        criteria.andParentIdEqualTo(0);     // 根贴子parentId为0
        criteria.andStatusEqualTo(0);
        example.setOrderByClause("sort, create_time");
        return forumMapper.selectByExample(example);
    }

    public List<Forum> getChildForums(int forumId) {
        ForumExample example = new ForumExample();
        ForumExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(forumId);
        criteria.andStatusEqualTo(0);
        example.setOrderByClause("sort, create_time");
        return forumMapper.selectByExample(example);
    }

    public int getChildForumCount(int forumId) {
        ForumExample example = new ForumExample();
        ForumExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(forumId);
        criteria.andStatusEqualTo(0);
        example.setOrderByClause("sort, create_time");
        return forumMapper.selectByExample(example).size();
    }

    public void save(Forum forum) {
        forumMapper.insertSelective(forum);
    }
}
