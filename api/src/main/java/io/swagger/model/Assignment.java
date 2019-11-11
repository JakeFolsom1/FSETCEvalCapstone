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
 * Assignment
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")
@Entity
@Table(name = "ASSIGNMENT")
public class Assignment   {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonProperty("assignmentId")
  private Long assignmentId = null;

  @JsonProperty("asurite")
  private String asurite = null;

  @JsonProperty("assignedAsurite")
  private String assignedAsurite = null;

  @JsonProperty("semester")
  private String semester = null;

  @JsonProperty("isComplete")
  private Boolean isComplete = null;

  @JsonProperty("evalType")
  private String evalType = null;

  public Assignment assignmentId(Long assignmentId) {
    this.assignmentId = assignmentId;
    return this;
  }

  /**
   * Get assignmentId
   * @return assignmentId
  **/
  @ApiModelProperty(example = "54", value = "")
  @NotNull


  public Long getAssignmentId() {
    return assignmentId;
  }

  public void setAssignmentId(Long assignmentId) {
    this.assignmentId = assignmentId;
  }

  public Assignment asurite(String asurite) {
    this.asurite = asurite;
    return this;
  }

  /**
   * Get asurite
   * @return asurite
  **/
  @ApiModelProperty(example = "jjbowma2", required = true, value = "")
  @NotNull


  public String getAsurite() {
    return asurite;
  }

  public void setAsurite(String asurite) {
    this.asurite = asurite;
  }

  public Assignment assignedAsurite(String assignedAsurite) {
    this.assignedAsurite = assignedAsurite;
    return this;
  }

  /**
   * Get assignedAsurite
   * @return assignedAsurite
  **/
  @ApiModelProperty(example = "smurra11", required = true, value = "")
  @NotNull


  public String getAssignedAsurite() {
    return assignedAsurite;
  }

  public void setAssignedAsurite(String assignedAsurite) {
    this.assignedAsurite = assignedAsurite;
  }

  public Assignment semester(String semester) {
    this.semester = semester;
    return this;
  }

  /**
   * Get semester
   * @return semester
  **/
  @ApiModelProperty(example = "Fall 2019", required = true, value = "")
  @NotNull


  public String getSemester() {
    return semester;
  }

  public void setSemester(String semester) {
    this.semester = semester;
  }

  public Assignment isComplete(Boolean isComplete) {
    this.isComplete = isComplete;
    return this;
  }

  /**
   * Get isComplete
   * @return isComplete
  **/
  @ApiModelProperty(example = "false", required = true, value = "")
  @NotNull


  public Boolean isIsComplete() {
    return isComplete;
  }

  public void setIsComplete(Boolean isComplete) {
    this.isComplete = isComplete;
  }

  public Assignment evalType(String evalType) {
    this.evalType = evalType;
    return this;
  }

  /**
   * Get evalType
   * @return evalType
  **/
  @ApiModelProperty(example = "p2p", required = true, value = "")
  @NotNull


  public String getEvalType() {
    return evalType;
  }

  public void setEvalType(String evalType) {
    this.evalType = evalType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Assignment assignment = (Assignment) o;
    return Objects.equals(this.assignmentId, assignment.assignmentId) &&
        Objects.equals(this.asurite, assignment.asurite) &&
        Objects.equals(this.assignedAsurite, assignment.assignedAsurite) &&
        Objects.equals(this.semester, assignment.semester) &&
        Objects.equals(this.isComplete, assignment.isComplete) &&
        Objects.equals(this.evalType, assignment.evalType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(assignmentId, asurite, assignedAsurite, semester, isComplete, evalType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Assignment {\n");
    
    sb.append("    assignmentId: ").append(toIndentedString(assignmentId)).append("\n");
    sb.append("    asurite: ").append(toIndentedString(asurite)).append("\n");
    sb.append("    assignedAsurite: ").append(toIndentedString(assignedAsurite)).append("\n");
    sb.append("    semester: ").append(toIndentedString(semester)).append("\n");
    sb.append("    isComplete: ").append(toIndentedString(isComplete)).append("\n");
    sb.append("    evalType: ").append(toIndentedString(evalType)).append("\n");
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

