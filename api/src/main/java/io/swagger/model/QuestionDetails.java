package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * QuestionDetails
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-07T08:20:05.576Z")

public class QuestionDetails   {
  @JsonProperty("questionNumber")
  private Long questionNumber = null;

  @JsonProperty("questionPrompt")
  private String questionPrompt = null;

  @JsonProperty("questionType")
  private String questionType = null;

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

  /**
   * Get questionType
   * @return questionType
  **/
  @ApiModelProperty(example = "freeResponse", required = true, value = "")
  @NotNull


  public String getQuestionType() {
    return questionType;
  }

  public void setQuestionType(String questionType) {
    this.questionType = questionType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QuestionDetails questionDetails = (QuestionDetails) o;
    return Objects.equals(this.questionNumber, questionDetails.questionNumber) &&
        Objects.equals(this.questionPrompt, questionDetails.questionPrompt) &&
        Objects.equals(this.questionType, questionDetails.questionType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(questionNumber, questionPrompt, questionType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QuestionDetails {\n");
    
    sb.append("    questionNumber: ").append(toIndentedString(questionNumber)).append("\n");
    sb.append("    questionPrompt: ").append(toIndentedString(questionPrompt)).append("\n");
    sb.append("    questionType: ").append(toIndentedString(questionType)).append("\n");
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

