package io.swagger.repository;

import io.swagger.model.Preference;
import io.swagger.model.Semester;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PreferenceRepository extends CrudRepository<Preference, Long> {
    Preference findByAsuriteAndPreferenceNumberAndSemester(String asurite, Long preferenceNumber, Semester semester);
    List<Preference> findAllByAsuriteAndSemesterOrderByPreferenceNumber(String asurite, Semester semester);
}
