package io.swagger.repository;

import io.swagger.model.NumberOfAssignments;
import io.swagger.model.Semester;
import org.springframework.data.repository.CrudRepository;

public interface NumberOfAssignmentsRepository extends CrudRepository<NumberOfAssignments, Semester> {
}
