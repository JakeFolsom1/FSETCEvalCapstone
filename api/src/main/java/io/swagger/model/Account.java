package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

/**
 * Account
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")
@Entity
@Table(name = "ACCOUNT")
public class Account   {

  @Id
  @JsonProperty("asurite")
  private String asurite = null;

  @JsonProperty
  private String firstName = null;

  @JsonProperty
  private String lastName = null;

  @JsonProperty("accountType")
  @Enumerated(STRING)
  private AccountType accountType = null;

  public static enum AccountType {
    tutor,
    lead,
    admin
  }

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

  public Account firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   **/
  @ApiModelProperty(example = "Jedde", required = true, value = "")
  @NotNull


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Account lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   **/
  @ApiModelProperty(example = "Bowman", required = true, value = "")
  @NotNull


  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


  public Account accountType(AccountType accountType) {
    this.accountType = accountType;
    return this;
  }

  /**
   * Get accountType
   * @return accountType
  **/
  @ApiModelProperty(example = "admin", required = true, value = "")
  @NotNull


  public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
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
            Objects.equals(this.firstName, account.firstName) &&
            Objects.equals(this.lastName, account.lastName) &&
            Objects.equals(this.accountType, account.accountType) &&
            Objects.equals(this.isActive, account.isActive);
  }

  @Override
  public int hashCode() {
    return Objects.hash(asurite, firstName, lastName, accountType, isActive);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Account {\n");
    
    sb.append("    asurite: ").append(toIndentedString(asurite)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
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

