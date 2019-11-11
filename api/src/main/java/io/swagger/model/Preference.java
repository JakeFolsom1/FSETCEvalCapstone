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

  public Preference preferenceId(Long preferenceId) {
    this.preferenceId = preferenceId;
    return this;
  }

  /**
   * Get preferenceId
   * @return preferenceId
   **/
  @ApiModelProperty(example = "1", value = "")
  @NotNull


  public Long getPreferenceId() {
    return preferenceId;
  }

  public void setPreferenceId(Long preferenceId) {
    this.preferenceId = preferenceId;
  }

  public Preference asurite(String asurite) {
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

  public Preference preferenceNumber(Long preferenceNumber) {
    this.preferenceNumber = preferenceNumber;
    return this;
  }

  /**
   * Get preferenceNumber
   * minimum: 1
   * maximum: 3
   * @return preferenceNumber
  **/
  @ApiModelProperty(example = "2", required = true, value = "")
  @NotNull

@Min(1L) @Max(3L) 
  public Long getPreferenceNumber() {
    return preferenceNumber;
  }

  public void setPreferenceNumber(Long preferenceNumber) {
    this.preferenceNumber = preferenceNumber;
  }

  public Preference preferredAsurite(String preferredAsurite) {
    this.preferredAsurite = preferredAsurite;
    return this;
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Preference preference = (Preference) o;
    return Objects.equals(this.asurite, preference.asurite) &&
        Objects.equals(this.preferenceNumber, preference.preferenceNumber) &&
        Objects.equals(this.preferredAsurite, preference.preferredAsurite);
  }

  @Override
  public int hashCode() {
    return Objects.hash(asurite, preferenceNumber, preferredAsurite);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Preference {\n");
    
    sb.append("    asurite: ").append(toIndentedString(asurite)).append("\n");
    sb.append("    preferenceNumber: ").append(toIndentedString(preferenceNumber)).append("\n");
    sb.append("    preferredAsurite: ").append(toIndentedString(preferredAsurite)).append("\n");
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

