package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * NumberOfAssignment
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")
@Entity
@Table(name = "NUM_ASSIGNMENTS")
public class NumberOfAssignments implements Serializable {
    @Id
    @JsonProperty("semester")
    @OneToOne
    @JoinColumn(name = "SEMESTER_NAME")
    private Semester semester = null;

    @JsonProperty("numAssignments")
    private Long numAssignments = null;

    /**
     * Get semesterName
     * @return semesterName
     **/
    @ApiModelProperty(example = "fall19", required = true, value = "")
    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    /**
     * Get numAssignments
     * @return numAssignments
     **/
    @ApiModelProperty(example = "3", required = true, value = "")
    public Long getNumAssignments() {
        return numAssignments;
    }

    public void setNumAssignments(Long numAssignments) {
        this.numAssignments = numAssignments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberOfAssignments)) return false;
        NumberOfAssignments that = (NumberOfAssignments) o;
        return Objects.equals(getSemester(), that.getSemester()) &&
                Objects.equals(getNumAssignments(), that.getNumAssignments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSemester(), getNumAssignments());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NumberOfAssignments {\n");

        sb.append("    semesterName: ").append(toIndentedString(semester)).append("\n");
        sb.append("    numAssignments: ").append(toIndentedString(numAssignments)).append("\n");
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
