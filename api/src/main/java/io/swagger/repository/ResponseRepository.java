package io.swagger.repository;

import io.swagger.model.Assignment;
import io.swagger.model.Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResponseRepository extends CrudRepository<Response, Response.ResponsePK> {
    List<Response> findAllByAssignmentIdOrderByQuestionIdAsc(Long assignmentId);
}
