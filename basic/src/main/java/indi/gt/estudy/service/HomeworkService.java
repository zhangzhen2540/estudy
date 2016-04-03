package indi.gt.estudy.service;

import indi.gt.estudy.entity.Answer;
import indi.gt.estudy.entity.AnswerExample;
import indi.gt.estudy.entity.Homework;
import indi.gt.estudy.entity.HomeworkExample;
import indi.gt.estudy.mapper.AnswerMapper;
import indi.gt.estudy.mapper.HomeworkMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
@Service
public class HomeworkService {
    @Resource
    private HomeworkMapper homeworkMapper;

    @Resource
    private AnswerMapper answerMapper;


    public Homework get(int id) {
        return homeworkMapper.selectByPrimaryKey(id);
    }


    public List<Homework> getByGrade(int gradeId) {
        HomeworkExample example = new HomeworkExample();
        HomeworkExample.Criteria criteria = example.createCriteria();
        criteria.andGradeIdEqualTo(gradeId);
        criteria.andStatusEqualTo(0);
        example.setOrderByClause("sort");
        return homeworkMapper.selectByExample(example);
    }



    public Answer getAnswerById(int id) {
        return answerMapper.selectByPrimaryKey(id);
    }

    public Answer getStudentHomeworkAnswer(int studentId, int homeworkId) {
        AnswerExample example = new AnswerExample();
        AnswerExample.Criteria criteria = example.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        criteria.andHomeworkIdEqualTo(homeworkId);
        criteria.andStatusEqualTo(0);

        List<Answer> answers = answerMapper.selectByExample(example);
        return answers.isEmpty() ? null : answers.get(0);
    }




    public void saveAnswer(Answer answer) {
        answerMapper.insert(answer);
    }

}
