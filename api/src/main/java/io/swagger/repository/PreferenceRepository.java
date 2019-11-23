package io.swagger.repository;

import io.swagger.model.Preference;
import org.springframework.data.repository.CrudRepository;

public interface PreferenceRepository extends CrudRepository<Preference, Long> {
    Preference findByAsuriteAndPreferenceNumberAndSemester(String asurite, Long preferenceNumber, String semester);
}
