package io.swagger.repository;

import io.swagger.model.Assignment;
import io.swagger.model.Question;
import io.swagger.model.Semester;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AssignmentRepository extends CrudRepository<Assignment, Long> {
    Assignment findByAsuriteAndAssignmentNumberAndEvalTypeAndSemesterName(String asurite, Long assignmentNumber, Question.EvalType evalType, String semesterName);
    List<Assignment> findAllByAssignedAsuriteAndSemesterName(String assignedAsurite, String semesterName);
    Assignment findByAsuriteAndAssignedAsurite(String asurite, String assignedAsurite);
    List<Assignment> findDistinctByIsCompleteAndSemesterName(boolean isComplete, String semesterName);
    List<Assignment> findAllByIsCompleteAndAsuriteAndSemesterName(boolean isComplete, String asurite, String semesterName);
    List<Assignment> findAllByIsComplete(boolean isComplete);
    List<Assignment> findAllByIsCompleteAndAssignedAsurite(boolean isComplete, String assignedAsurite);
    List<Assignment> findAllByAssignmentId(Long assignmentId);
    List<Assignment> findAllByAsurite(String asurite);
    List<Assignment> findAllByAsuriteAndSemesterName(String asurite, String semesterName);
    void deleteAllBySemesterName(String semesterName);
    void deleteAllBySemesterNameAndEvalType(String semesterName, Question.EvalType evalType);
    void deleteAssignmentByAssignedAsuriteAndAsuriteAndSemesterName(String assignedAsurite, String asurite, String semesterName);

}
