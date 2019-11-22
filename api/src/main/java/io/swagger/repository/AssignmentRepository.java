package io.swagger.repository;

import io.swagger.model.Assignment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AssignmentRepository extends CrudRepository<Assignment, Long> {
    List<Assignment> findDistinctByIsCompleteAndSemester(boolean isComplete, String semester);
    List<Assignment> findAllByIsCompleteAndAsuriteAndSemester(boolean isComplete, String asurite, String semester);
    List<Assignment> findAllByIsComplete(boolean isComplete);
    List<Assignment> findAllByIsCompleteAndAssignedAsurite(boolean isComplete, String assignedAsurite);
    List<Assignment> findAllByAssignmentId(Long assignmentId);

}
