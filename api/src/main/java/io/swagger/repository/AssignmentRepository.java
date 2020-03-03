package io.swagger.repository;

import io.swagger.model.Assignment;
import io.swagger.model.Question;
import io.swagger.model.Semester;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AssignmentRepository extends CrudRepository<Assignment, Long> {
    Assignment findByAsuriteAndAssignmentNumberAndEvalTypeAndSemester(String asurite, Long assignmentNumber, Question.EvalType evalType, Semester semester);
    List<Assignment> findAllByAssignedAsuriteAndSemester(String assignedAsurite, Semester semester);
    List<Assignment> findDistinctByIsCompleteAndSemester(boolean isComplete, Semester semester);
    List<Assignment> findAllByIsCompleteAndAsuriteAndSemester(boolean isComplete, String asurite, Semester semester);
    List<Assignment> findAllByIsComplete(boolean isComplete);
    List<Assignment> findAllByIsCompleteAndAssignedAsurite(boolean isComplete, String assignedAsurite);
    List<Assignment> findAllByAssignmentId(Long assignmentId);
    List<Assignment> findAllByAsurite(String asurite);
    void deleteAllBySemester(Semester semester);

}
