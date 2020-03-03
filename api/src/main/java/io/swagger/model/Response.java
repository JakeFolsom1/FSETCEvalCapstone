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
@Table(name = "RESPONSE", uniqueConstraints = {@UniqueConstraint(columnNames = {"QUESTION_ID", "ASSIGNMENT_ID"})})
public class Response implements Serializable {
  @Id
  @GeneratedValue
  @JsonProperty
  private Long responseId;

  @JsonProperty("response")
  private String response = null;

  @JsonProperty("isShared")
  private Boolean isShared = null;

  @ManyToOne
  @JoinColumn(name = "ASSIGNMENT_ID")
  @JsonProperty("assignment")
  private Assignment assignment = null;

  @ManyToOne
  @JoinColumn(name = "QUESTION_ID")
  @JsonProperty("question")
  private Question question = null;

  /**
   * Get responseId
   * @return responseId
   **/
  @ApiModelProperty(example = "1", value = "")


  public Long getResponseId() {
    return responseId;
  }

  public void setResponseId(Long responseId) {
    this.responseId = responseId;
  }

  /**
   * Get assignmentId
   * @return assignmentId
  **/
  @ApiModelProperty(example = "54", required = true, value = "")
  @NotNull


  public Assignment getAssignment() {
    return assignment;
  }

  public void setAssignment(Assignment assignment) {
    this.assignment = assignment;
  }

  /**
   * Get questionId
   * @return questionId
  **/
  @ApiModelProperty(example = "22", required = true, value = "")
  @NotNull


  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
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
    return Objects.equals(this.responseId, response.responseId) &&
            Objects.equals(this.assignment, response.assignment) &&
            Objects.equals(this.question, response.question) &&
            Objects.equals(this.response, response.response) &&
            Objects.equals(this.isShared, response.isShared);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseId, assignment, question, response, isShared);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Response {\n");

    sb.append("    responseId: ").append(toIndentedString(responseId)).append("\n");
    sb.append("    assignmentId: ").append(toIndentedString(assignment)).append("\n");
    sb.append("    questionId: ").append(toIndentedString(question)).append("\n");
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