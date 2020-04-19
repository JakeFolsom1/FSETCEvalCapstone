package io.swagger.repository;

import io.swagger.model.Assignment;
import io.swagger.model.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AssignmentRepository extends CrudRepository<Assignment, Long> {
    Assignment findByAsuriteAndAssignmentNumberAndEvalTypeAndSemesterName(String asurite, Long assignmentNumber, Question.EvalType evalType, String semesterName);
    List<Assignment> findAllByAssignedAsuriteAndSemesterNameAndEvalType(String assignedAsurite, String semesterName, Question.EvalType evalType);
    Assignment findByAsuriteAndAssignedAsurite(String asurite, String assignedAsurite);
    List<Assignment> findDistinctByIsCompleteAndSemesterName(boolean isComplete, String semesterName);
    List<Assignment> findAllByIsCompleteAndAsuriteAndSemesterName(boolean isComplete, String asurite, String semesterName);
    List<Assignment> findAllByIsComplete(boolean isComplete);
    List<Assignment> findAllByIsCompleteAndAssignedAsurite(boolean isComplete, String assignedAsurite);
    List<Assignment> findAllBySemesterName(String semesterName);
    List<Assignment> findAllByAssignmentId(Long assignmentId);
    List<Assignment> findAllByAsurite(String asurite);
    List<Assignment> findAllByAsuriteAndSemesterNameAndEvalType(String asurite, String semesterName, Question.EvalType evalType);
    void deleteAllBySemesterName(String semesterName);
    void deleteAllBySemesterNameAndEvalType(String semesterName, Question.EvalType evalType);
    void deleteAssignmentByAssignedAsuriteAndAsuriteAndSemesterName(String assignedAsurite, String asurite, String semesterName);
    void deleteAllBySemesterNameAndEvalTypeAndAssignmentNumberGreaterThan(String semesterName, Question.EvalType evalType, Long assignmentNumber);

}
