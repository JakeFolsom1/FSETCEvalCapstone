package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Preference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")
@Entity
@Table(name = "PREFERENCE")
@IdClass(Preference.PreferencePK.class)
public class Preference implements Serializable {

    public static class PreferencePK implements Serializable {
        private String asurite;
        private String semesterName;
        private Long preferenceNumber;

        public PreferencePK() {}

        public PreferencePK(String asurite, String semesterName, Long preferenceNumber) {
            this.asurite = asurite;
            this.semesterName = semesterName;
            this.preferenceNumber = preferenceNumber;
        }

        public String getAsurite() {
            return asurite;
        }

        public void setAsurite(String asurite) {
            this.asurite = asurite;
        }

        public String getSemesterName() {
            return semesterName;
        }

        public void setSemesterName(String semesterName) {
            this.semesterName = semesterName;
        }

        public Long getPreferenceNumber() {
            return preferenceNumber;
        }

        public void setPreferenceNumber(Long preferenceNumber) {
            this.preferenceNumber = preferenceNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PreferencePK that = (PreferencePK) o;
            return Objects.equals(asurite, that.asurite) &&
                    Objects.equals(semesterName, that.semesterName) &&
                    Objects.equals(preferenceNumber, that.preferenceNumber);
        }

        @Override
        public int hashCode() {
            return Objects.hash(asurite, semesterName, preferenceNumber);
        }
    }

    @Id
    @JsonProperty("asurite")
    private String asurite = null;

    @Id
    @JsonProperty("preferenceNumber")
    private Long preferenceNumber = null;

    @JsonProperty("preferredAsurite")
    private String preferredAsurite = null;

    @Id
    @JsonProperty("semesterName")
    private String semesterName = null;


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
    @ApiModelProperty(example = "fall19", required = true, value = "")
    @NotNull


    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
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
                Objects.equals(this.semesterName, preference.semesterName) &&
                Objects.equals(this.preferredAsurite, preference.preferredAsurite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asurite, preferenceNumber, semesterName, preferredAsurite);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Preference {\n");

        sb.append("    asurite: ").append(toIndentedString(asurite)).append("\n");
        sb.append("    preferenceNumber: ").append(toIndentedString(preferenceNumber)).append("\n");
        sb.append("    preferredAsurite: ").append(toIndentedString(preferredAsurite)).append("\n");
        sb.append("    semester: ").append(toIndentedString(semesterName)).append("\n");
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

