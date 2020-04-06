package io.swagger.repository;

import io.swagger.model.Preference;
import io.swagger.model.Semester;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PreferenceRepository extends CrudRepository<Preference, Preference.PreferencePK> {
    List<Preference> findAllByAsuriteAndSemesterNameOrderByPreferenceNumber(String asurite, String semesterName);
    void deleteAllBySemesterName(String semesterName);
}
