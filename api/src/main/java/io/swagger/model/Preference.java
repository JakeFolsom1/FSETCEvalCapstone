package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Preference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")
@Entity
@Table(name = "PREFERENCE")
public class Preference   {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonProperty("preferenceId")
  private Long preferenceId = null;

  @JsonProperty("asurite")
  private String asurite = null;

  @JsonProperty("preferenceNumber")
  private Long preferenceNumber = null;

  @JsonProperty("preferredAsurite")
  private String preferredAsurite = null;

  @JsonProperty("semester")
  private String semester = null;

  /**
   * Get preferenceId
   * @return preferenceId
   **/
  @ApiModelProperty(example = "1", value = "")


  public Long getPreferenceId() {
    return preferenceId;
  }

  public void setPreferenceId(Long preferenceId) {
    this.preferenceId = preferenceId;
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

  /**
   * Get preferenceNumber
   * @return preferenceNumber
  **/
  @ApiModelProperty(example = "2", required = true, value = "")
  @NotNull

  public Long getPreferenceNumber() {
    return preferenceNumber;
  }

  public void setPreferenceNumber(Long preferenceNumber) {
    this.preferenceNumber = preferenceNumber;
  }

  /**
   * Get preferredAsurite
   * @return preferredAsurite
  **/
  @ApiModelProperty(example = "smurra11", required = true, value = "")
  @NotNull


  public String getPreferredAsurite() {
    return preferredAsurite;
  }

  public void setPreferredAsurite(String preferredAsurite) {
    this.preferredAsurite = preferredAsurite;
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Preference preference = (Preference) o;
    return Objects.equals(this.preferenceId, preference.preferenceId) &&
            Objects.equals(this.asurite, preference.asurite) &&
            Objects.equals(this.preferenceNumber, preference.preferenceNumber) &&
            Objects.equals(this.semester, preference.semester) &&
            Objects.equals(this.preferredAsurite, preference.preferredAsurite);
  }

  @Override
  public int hashCode() {
    return Objects.hash(preferenceId, asurite, preferenceNumber, semester, preferredAsurite);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Preference {\n");

    sb.append("    preferenceId: ").append(toIndentedString(preferenceId)).append("\n");
    sb.append("    asurite: ").append(toIndentedString(asurite)).append("\n");
    sb.append("    preferenceNumber: ").append(toIndentedString(preferenceNumber)).append("\n");
    sb.append("    preferredAsurite: ").append(toIndentedString(preferredAsurite)).append("\n");
    sb.append("    semester: ").append(toIndentedString(semester)).append("\n");
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

