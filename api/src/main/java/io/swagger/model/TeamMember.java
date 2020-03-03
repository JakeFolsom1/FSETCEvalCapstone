package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * TeamMember
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")
@Entity
@Table(name = "TEAM_MEMBER", uniqueConstraints = {@UniqueConstraint(columnNames = {"TUTOR_ASURITE", "SEMESTER_NAME"})})
public class TeamMember implements Serializable {
  @JsonProperty("leadAsurite")
  private String leadAsurite;

  @Id
  @JsonProperty("tutorAsurite")
  private String tutorAsurite;

  @ManyToOne
  @JoinColumn(name = "SEMESTER_NAME")
  @JsonProperty("semester")
  private Semester semester;

  /**
   * Get leadAsurite
   * @return leadAsurite
  **/
  @ApiModelProperty(example = "smurra11", required = true, value = "")
  @NotNull


  public String getLeadAsurite() {
    return leadAsurite;
  }

  public void setLeadAsurite(String leadAsurite) {
    this.leadAsurite = leadAsurite;
  }

  /**
   * Get tutorAsurite
   * @return tutorAsurite
  **/
  @ApiModelProperty(example = "jjbowma2", required = true, value = "")
  @NotNull


  public String getTutorAsurite() {
    return tutorAsurite;
  }

  public void setTutorAsurite(String tutorAsurite) {
    this.tutorAsurite = tutorAsurite;
  }


  /**
   * Get semester
   * @return semester
   **/
  @ApiModelProperty(example = "fall19", required = true, value = "")
  @NotNull


  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TeamMember teamMember = (TeamMember) o;
    return Objects.equals(this.tutorAsurite, teamMember.tutorAsurite) &&
            Objects.equals(this.semester, teamMember.semester);
  }

  @Override
  public int hashCode() {
    return Objects.hash(leadAsurite, tutorAsurite);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TeamMember {\n");
    
    sb.append("    leadAsurite: ").append(toIndentedString(leadAsurite)).append("\n");
    sb.append("    tutorAsurite: ").append(toIndentedString(tutorAsurite)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

