package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Evaluations Released
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")
@Entity
@Table(name = "EVALUATIONS_RELEASED")
public class EvaluationsReleased implements Serializable {
    @Id
    @JsonProperty("semesterName")
    private String semesterName = null;

    @JsonProperty("isReleased")
    private Boolean isReleased = null;

    /**
     * Get semesterName
     * @return semesterName
     **/
    @ApiModelProperty(example = "fall19", required = true, value = "")
    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    /**
     * Get isReleased
     * @return isReleased
     **/
    @ApiModelProperty(example = "true", required = true, value = "")
    public Boolean isIsReleased() {
        return isReleased;
    }

    public void setIsReleased(Boolean isReleased) {
        this.isReleased = isReleased;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EvaluationsReleased)) return false;
        EvaluationsReleased that = (EvaluationsReleased) o;
        return Objects.equals(getSemesterName(), that.getSemesterName()) &&
                Objects.equals(isIsReleased(), that.isIsReleased());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSemesterName(), isIsReleased());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EvaluationsReleased {\n");

        sb.append("    semesterName: ").append(toIndentedString(semesterName)).append("\n");
        sb.append("    isReleased: ").append(toIndentedString(isReleased)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
