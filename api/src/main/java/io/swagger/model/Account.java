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
 * Account
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

public class Account   {
  @JsonProperty("asurite")
  private String asurite = null;

  @JsonProperty("accountType")
  private String accountType = null;

  @JsonProperty("isActive")
  private Boolean isActive = null;

  public Account asurite(String asurite) {
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

  public Account accountType(String accountType) {
    this.accountType = accountType;
    return this;
  }

  /**
   * Get accountType
   * @return accountType
  **/
  @ApiModelProperty(example = "admin", required = true, value = "")
  @NotNull


  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public Account isActive(Boolean isActive) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(this.asurite, account.asurite) &&
        Objects.equals(this.accountType, account.accountType) &&
        Objects.equals(this.isActive, account.isActive);
  }

  @Override
  public int hashCode() {
    return Objects.hash(asurite, accountType, isActive);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Account {\n");
    
    sb.append("    asurite: ").append(toIndentedString(asurite)).append("\n");
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
    sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
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

