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
 * Question
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

public class Question   {
  @JsonProperty("questionId")
  private Long questionId = null;

  @JsonProperty("questionPrompt")
  private String questionPrompt = null;

  @JsonProperty("isActive")
  private Boolean isActive = null;

  @JsonProperty("questionNumber")
  private Long questionNumber = null;

  @JsonProperty("evalType")
  private String evalType = null;

  public Question questionId(Long questionId) {
    this.questionId = questionId;
    return this;
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

  public Question questionPrompt(String questionPrompt) {
    this.questionPrompt = questionPrompt;
    return this;
  }

  /**
   * Get questionPrompt
   * @return questionPrompt
  **/
  @ApiModelProperty(example = "Does tutor obey all procedures and policies of the center?", required = true, value = "")
  @NotNull


  public String getQuestionPrompt() {
    return questionPrompt;
  }

  public void setQuestionPrompt(String questionPrompt) {
    this.questionPrompt = questionPrompt;
  }

  public Question isActive(Boolean isActive) {
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

  public Question questionNumber(Long questionNumber) {
    this.questionNumber = questionNumber;
    return this;
  }

  /**
   * Get questionNumber
   * @return questionNumber
  **/
  @ApiModelProperty(example = "3", required = true, value = "")
  @NotNull


  public Long getQuestionNumber() {
    return questionNumber;
  }

  public void setQuestionNumber(Long questionNumber) {
    this.questionNumber = questionNumber;
  }

  public Question evalType(String evalType) {
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
    Question question = (Question) o;
    return Objects.equals(this.questionId, question.questionId) &&
        Objects.equals(this.questionPrompt, question.questionPrompt) &&
        Objects.equals(this.isActive, question.isActive) &&
        Objects.equals(this.questionNumber, question.questionNumber) &&
        Objects.equals(this.evalType, question.evalType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(questionId, questionPrompt, isActive, questionNumber, evalType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Question {\n");
    
    sb.append("    questionId: ").append(toIndentedString(questionId)).append("\n");
    sb.append("    questionPrompt: ").append(toIndentedString(questionPrompt)).append("\n");
    sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
    sb.append("    questionNumber: ").append(toIndentedString(questionNumber)).append("\n");
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

