package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Response
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

@Entity
@Table(name = "RESPONSE")
@IdClass(Response.ResponsePK.class)
public class Response implements Serializable {
    public static class ResponsePK implements Serializable {
        private Long questionId;
        private Long assignmentId;

        public ResponsePK() {}

        public ResponsePK(Long questionId, Long assignmentId) {
            this.questionId = questionId;
            this.assignmentId = assignmentId;
        }

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public Long getAssignmentId() {
            return assignmentId;
        }

        public void setAssignmentId(Long assignmentId) {
            this.assignmentId = assignmentId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ResponsePK that = (ResponsePK) o;
            return Objects.equals(questionId, that.questionId) &&
                    Objects.equals(assignmentId, that.assignmentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(questionId, assignmentId);
        }
    }

    @JsonProperty("response")
    private String response = null;

    @JsonProperty("isShared")
    private Boolean isShared = null;

    @Id
    @JsonProperty("assignment")
    private Long assignmentId = null;

    @Id
    @JsonProperty("question")
    private Long questionId = null;


    /**
     * Get assignmentId
     * @return assignmentId
     **/
    @ApiModelProperty(example = "54", required = true, value = "")
    @NotNull


    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    /**
     * Get questionId
     * @return questionId
     **/
    @ApiModelProperty(example = "22", required = true, value = "")
    @NotNull


    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /**
     * Get response
     * @return response
     **/
    @ApiModelProperty(example = "John does not wear his sash when he's on shift.", required = true, value = "")
    @NotNull


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * Get isShared
     * @return isShared
     **/
    @ApiModelProperty(example = "false", required = true, value = "")
    @NotNull


    public Boolean isIsShared() {
        return isShared;
    }

    public void setIsShared(Boolean isShared) {
        this.isShared = isShared;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Response response = (Response) o;
        return Objects.equals(this.assignmentId, response.assignmentId) &&
                Objects.equals(this.questionId, response.questionId) &&
                Objects.equals(this.response, response.response) &&
                Objects.equals(this.isShared, response.isShared);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentId, questionId, response, isShared);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Response {\n");

        sb.append("    assignmentId: ").append(toIndentedString(assignmentId)).append("\n");
        sb.append("    questionId: ").append(toIndentedString(questionId)).append("\n");
        sb.append("    response: ").append(toIndentedString(response)).append("\n");
        sb.append("    isShared: ").append(toIndentedString(isShared)).append("\n");
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