package io.swagger.repository;

import io.swagger.model.Assignment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AssignmentRepository extends CrudRepository<Assignment, Long> {
//    List<Assignment> getAllByIsComplete(boolean isComplete);
}
