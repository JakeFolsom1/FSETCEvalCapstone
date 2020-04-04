package io.swagger.repository;

import io.swagger.model.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findQuestionsByIsActiveAndEvalTypeOrderByQuestionNumber(Boolean isActive, Question.EvalType evalType);
}
