package io.swagger.repository;

import io.swagger.model.TeamMember;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamMemberRepository extends CrudRepository<TeamMember, String> {
    List<TeamMember> findTeamMembersByLeadAsurite(String leadAsurite);
    TeamMember findByTutorAsurite(String tutorAsurite);
}
