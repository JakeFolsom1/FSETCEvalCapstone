package io.swagger.repository;

import io.swagger.model.Semester;
import org.springframework.data.repository.CrudRepository;

public interface SemesterRepository extends CrudRepository<Semester, String> {
    Semester findByIsActive(Boolean isActive);
}
