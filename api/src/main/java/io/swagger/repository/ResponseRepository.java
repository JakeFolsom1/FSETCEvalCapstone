package io.swagger.repository;

import io.swagger.model.Assignment;
import io.swagger.model.Question;
import io.swagger.model.Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResponseRepository extends CrudRepository<Response, Long> {
    Response findByQuestionAndAssignment(Question question, Assignment assignment);
    List<Response> findAllByAssignmentOrderByQuestionAsc(Assignment assignment);
}
