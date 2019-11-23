package io.swagger.repository;

import io.swagger.model.Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResponseRepository extends CrudRepository<Response, Long> {
    Response findByQuestionIdAndAssignmentId(Long questionId, Long assignmentId);
    List<Response> findAllByAssignmentIdOrderByQuestionIdAsc(Long assignmentId);
}
