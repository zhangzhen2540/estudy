package indi.gt.estudy.service;

import indi.gt.estudy.entity.Material;
import indi.gt.estudy.entity.MaterialExample;
import indi.gt.estudy.mapper.MaterialMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
@Service
public class MaterialService {
    @Resource
    private MaterialMapper materialMapper;

    public Material get(int id) {
        return materialMapper.selectByPrimaryKey(id);
    }

    public List<Material> getByGrade(int gradeId) {
        MaterialExample example = new MaterialExample();
        MaterialExample.Criteria criteria = example.createCriteria();
        criteria.andGradeIdEqualTo(gradeId);
        criteria.andStatusEqualTo(0);
        example.setOrderByClause("create_time");

        return materialMapper.selectByExample(example);
    }
}
