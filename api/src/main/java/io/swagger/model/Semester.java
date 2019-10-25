package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Semester
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

public class Semester   {
  @JsonProperty("semesterName")
  private String semesterName = null;

  @JsonProperty("isActive")
  private Boolean isActive = null;

  public Semester semesterName(String semesterName) {
    this.semesterName = semesterName;
    return this;
  }

  /**
   * Get semesterName
   * @return semesterName
  **/
  @ApiModelProperty(example = "Fall 2019", required = true, value = "")
  @NotNull


  public String getSemesterName() {
    return semesterName;
  }

  public void setSemesterName(String semesterName) {
    this.semesterName = semesterName;
  }

  public Semester isActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  /**
   * Get isActive
   * @return isActive
  **/
  @ApiModelProperty(example = "true", required = true, value = "")
  @NotNull


  public Boolean isIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Semester semester = (Semester) o;
    return Objects.equals(this.semesterName, semester.semesterName) &&
        Objects.equals(this.isActive, semester.isActive);
  }

  @Override
  public int hashCode() {
    return Objects.hash(semesterName, isActive);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Semester {\n");
    
    sb.append("    semesterName: ").append(toIndentedString(semesterName)).append("\n");
    sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
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

