package de.haw.se2.speedrun.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;

import java.util.Objects;

/**
 * Runtime
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-06T09:14:52.282358031Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class Runtime {

  private Integer hours;

  private Integer minutes;

  private Integer seconds;

  private Integer milliseconds;

  public Runtime() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Runtime(Integer hours, Integer minutes, Integer seconds, Integer milliseconds) {
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
    this.milliseconds = milliseconds;
  }

  public Runtime hours(Integer hours) {
    this.hours = hours;
    return this;
  }

  /**
   * Get hours
   * @return hours
   */
  @NotNull 
  @Schema(name = "hours", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("hours")
  public Integer getHours() {
    return hours;
  }

  public void setHours(Integer hours) {
    this.hours = hours;
  }

  public Runtime minutes(Integer minutes) {
    this.minutes = minutes;
    return this;
  }

  /**
   * Get minutes
   * @return minutes
   */
  @NotNull 
  @Schema(name = "minutes", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("minutes")
  public Integer getMinutes() {
    return minutes;
  }

  public void setMinutes(Integer minutes) {
    this.minutes = minutes;
  }

  public Runtime seconds(Integer seconds) {
    this.seconds = seconds;
    return this;
  }

  /**
   * Get seconds
   * @return seconds
   */
  @NotNull 
  @Schema(name = "seconds", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("seconds")
  public Integer getSeconds() {
    return seconds;
  }

  public void setSeconds(Integer seconds) {
    this.seconds = seconds;
  }

  public Runtime milliseconds(Integer milliseconds) {
    this.milliseconds = milliseconds;
    return this;
  }

  /**
   * Get milliseconds
   * @return milliseconds
   */
  @NotNull 
  @Schema(name = "milliseconds", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("milliseconds")
  public Integer getMilliseconds() {
    return milliseconds;
  }

  public void setMilliseconds(Integer milliseconds) {
    this.milliseconds = milliseconds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Runtime runtime = (Runtime) o;
    return Objects.equals(this.hours, runtime.hours) &&
        Objects.equals(this.minutes, runtime.minutes) &&
        Objects.equals(this.seconds, runtime.seconds) &&
        Objects.equals(this.milliseconds, runtime.milliseconds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hours, minutes, seconds, milliseconds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Runtime {\n");
    sb.append("    hours: ").append(toIndentedString(hours)).append("\n");
    sb.append("    minutes: ").append(toIndentedString(minutes)).append("\n");
    sb.append("    seconds: ").append(toIndentedString(seconds)).append("\n");
    sb.append("    milliseconds: ").append(toIndentedString(milliseconds)).append("\n");
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

