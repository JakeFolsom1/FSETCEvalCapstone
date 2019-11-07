package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.QuestionDetails;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * QuestionAndResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-07T08:20:05.576Z")

public class QuestionAndResponse   {
  @JsonProperty("question")
  private QuestionDetails question = null;

  @JsonProperty("response")
  private String response = null;

  public QuestionAndResponse question(QuestionDetails question) {
    this.question = question;
    return this;
  }

  /**
   * Get question
   * @return question
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public QuestionDetails getQuestion() {
    return question;
  }

  public void setQuestion(QuestionDetails question) {
    this.question = question;
  }

  public QuestionAndResponse response(String response) {
    this.response = response;
    return this;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QuestionAndResponse questionAndResponse = (QuestionAndResponse) o;
    return Objects.equals(this.question, questionAndResponse.question) &&
        Objects.equals(this.response, questionAndResponse.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(question, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QuestionAndResponse {\n");
    
    sb.append("    question: ").append(toIndentedString(question)).append("\n");
    sb.append("    response: ").append(toIndentedString(response)).append("\n");
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

