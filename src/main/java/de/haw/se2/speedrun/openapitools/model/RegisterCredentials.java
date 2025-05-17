package de.haw.se2.speedrun.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;


/**
 * RegisterCredentials
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-17T08:46:35.398598281Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class RegisterCredentials {

  private String username;

  private String password;

  private String email;

  public RegisterCredentials() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RegisterCredentials(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public RegisterCredentials username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
   */
  @NotNull
  @Schema(name = "username", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public RegisterCredentials password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   */
  @NotNull 
  @Schema(name = "password", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public RegisterCredentials email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @NotNull 
  @Schema(name = "email", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RegisterCredentials registerCredentials = (RegisterCredentials) o;
    return Objects.equals(this.username, registerCredentials.username) &&
        Objects.equals(this.password, registerCredentials.password) &&
        Objects.equals(this.email, registerCredentials.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, email);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RegisterCredentials {\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
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

