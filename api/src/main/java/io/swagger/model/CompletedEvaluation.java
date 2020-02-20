package io.swagger.model;

import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.QuestionAndResponse;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CompletedEvaluation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-07T08:20:05.576Z")

public class CompletedEvaluation   {
  @JsonProperty("evaluator")
  private String evaluator = null;

  @JsonProperty("evaluatee")
  private String evaluatee = null;

  @JsonProperty("evalType")
  private String evalType = null;

  @JsonProperty("isShared")
  private Boolean isShared = null;

  @JsonProperty("semester")
  private String semester = null;

  @JsonProperty("questionsAndResponses")
  private List<QuestionAndResponse> questionsAndResponses = null;

  public CompletedEvaluation evaluator(String evaluator) {
    this.evaluator = evaluator;
    return this;
  }

  /**
   * Get evaluator
   * @return evaluator
  **/
  @ApiModelProperty(example = "jjbowma2", required = true, value = "")
  @NotNull


  public String getEvaluator() {
    return evaluator;
  }

  public void setEvaluator(String evaluator) {
    this.evaluator = evaluator;
  }

  public CompletedEvaluation evaluatee(String evaluatee) {
    this.evaluatee = evaluatee;
    return this;
  }

  /**
   * Get evaluatee
   * @return evaluatee
  **/
  @ApiModelProperty(example = "smurra11", required = true, value = "")
  @NotNull


  public String getEvaluatee() {
    return evaluatee;
  }

  public void setEvaluatee(String evaluatee) {
    this.evaluatee = evaluatee;
  }

  public CompletedEvaluation evalType(String evalType) {
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

  public CompletedEvaluation isShared(Boolean isShared) {
    this.isShared = isShared;
    return this;
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

  public CompletedEvaluation semester(String semester) {
    this.semester = semester;
    return this;
  }


  /**
   * Get isShared
   * @return isShared
  **/
  @ApiModelProperty(example = "true", required = true, value = "")
  @NotNull


  public Boolean isIsShared() {
    return isShared;
  }

  public void setIsShared(Boolean isShared) {
    this.isShared = isShared;
  }

  public CompletedEvaluation questionsAndResponses(List<QuestionAndResponse> questionsAndResponses) {
    this.questionsAndResponses = questionsAndResponses;
    return this;
  }

  /**
   * Get questionsAndResponses
   * @return questionsAndResponses
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<QuestionAndResponse> getQuestionsAndResponses() {
    return questionsAndResponses;
  }

  public void setQuestionsAndResponses(List<QuestionAndResponse> questionsAndResponses) {
    this.questionsAndResponses = questionsAndResponses;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompletedEvaluation completedEvaluation = (CompletedEvaluation) o;
    return Objects.equals(this.evaluator, completedEvaluation.evaluator) &&
        Objects.equals(this.evaluatee, completedEvaluation.evaluatee) &&
        Objects.equals(this.evalType, completedEvaluation.evalType) &&
        Objects.equals(this.isShared, completedEvaluation.isShared) &&
        Objects.equals(this.questionsAndResponses, completedEvaluation.questionsAndResponses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(evaluator, evaluatee, evalType, isShared, questionsAndResponses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompletedEvaluation {\n");
    
    sb.append("    evaluator: ").append(toIndentedString(evaluator)).append("\n");
    sb.append("    evaluatee: ").append(toIndentedString(evaluatee)).append("\n");
    sb.append("    evalType: ").append(toIndentedString(evalType)).append("\n");
    sb.append("    isShared: ").append(toIndentedString(isShared)).append("\n");
    sb.append("    questionAndResponse: ").append(toIndentedString(questionsAndResponses)).append("\n");
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

