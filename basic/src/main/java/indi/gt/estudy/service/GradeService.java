package indi.gt.estudy.service;

import indi.gt.estudy.entity.Grade;
import indi.gt.estudy.mapper.GradeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/3/30.
 */
@Service
public class GradeService {
    @Resource
    private GradeMapper gradeMapper;


    public Grade get(int id) {
        return gradeMapper.selectByPrimaryKey(id);
    }

}
