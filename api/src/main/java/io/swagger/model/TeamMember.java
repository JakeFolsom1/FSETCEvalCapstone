package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TeamMember
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")
@Entity
@Table(name = "TEAM_MEMBER")
public class TeamMember   {
  @JsonProperty("leadAsurite")
  private String leadAsurite = null;

  @Id
  @JsonProperty("tutorAsurite")
  private String tutorAsurite = null;

  public TeamMember leadAsurite(String leadAsurite) {
    this.leadAsurite = leadAsurite;
    return this;
  }

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

  public TeamMember tutorAsurite(String tutorAsurite) {
    this.tutorAsurite = tutorAsurite;
    return this;
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TeamMember teamMember = (TeamMember) o;
    return Objects.equals(this.leadAsurite, teamMember.leadAsurite) &&
        Objects.equals(this.tutorAsurite, teamMember.tutorAsurite);
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

