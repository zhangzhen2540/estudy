package indi.gt.estudy.service;

import indi.gt.estudy.entity.Notice;
import indi.gt.estudy.entity.NoticeExample;
import indi.gt.estudy.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
@Service
public class NoticeService {

    @Resource
    private NoticeMapper noticeMapper;


    public Notice get(int id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

    public List<Notice> getByGrade(int gradeId) {
        NoticeExample example = new NoticeExample();
        NoticeExample.Criteria criteria = example.createCriteria();
        criteria.andGradeIdEqualTo(gradeId);
        criteria.andStatusEqualTo(0);

        example.setOrderByClause("create_time desc");
        return noticeMapper.selectByExample(example);
    }



}
