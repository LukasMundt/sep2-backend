package de.haw.se2.speedrun.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * RegisterError
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-27T20:21:07.566967269Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class RegisterError {

  private @Nullable String usernameError;

  private @Nullable String emailError;

  private @Nullable String passwordError;

  public RegisterError usernameError(String usernameError) {
    this.usernameError = usernameError;
    return this;
  }

  /**
   * Get usernameError
   * @return usernameError
   */
  
  @Schema(name = "usernameError", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("usernameError")
  public String getUsernameError() {
    return usernameError;
  }

  public void setUsernameError(String usernameError) {
    this.usernameError = usernameError;
  }

  public RegisterError emailError(String emailError) {
    this.emailError = emailError;
    return this;
  }

  /**
   * Get emailError
   * @return emailError
   */
  
  @Schema(name = "emailError", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("emailError")
  public String getEmailError() {
    return emailError;
  }

  public void setEmailError(String emailError) {
    this.emailError = emailError;
  }

  public RegisterError passwordError(String passwordError) {
    this.passwordError = passwordError;
    return this;
  }

  /**
   * Get passwordError
   * @return passwordError
   */
  
  @Schema(name = "passwordError", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("passwordError")
  public String getPasswordError() {
    return passwordError;
  }

  public void setPasswordError(String passwordError) {
    this.passwordError = passwordError;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RegisterError registerError = (RegisterError) o;
    return Objects.equals(this.usernameError, registerError.usernameError) &&
        Objects.equals(this.emailError, registerError.emailError) &&
        Objects.equals(this.passwordError, registerError.passwordError);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usernameError, emailError, passwordError);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RegisterError {\n");
    sb.append("    usernameError: ").append(toIndentedString(usernameError)).append("\n");
    sb.append("    emailError: ").append(toIndentedString(emailError)).append("\n");
    sb.append("    passwordError: ").append(toIndentedString(passwordError)).append("\n");
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

