package de.haw.se2.speedrun.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

/**
 * RunSubmit
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-05T18:48:11.037630545Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class RunSubmit {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date date;

  private Runtime runtime;

  private String videoLink;

  public RunSubmit() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RunSubmit(Date date, Runtime runtime, String videoLink) {
    this.date = date;
    this.runtime = runtime;
    this.videoLink = videoLink;
  }

  public RunSubmit date(Date date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
   */
  @NotNull @Valid 
  @Schema(name = "date", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("date")
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public RunSubmit runtime(Runtime runtime) {
    this.runtime = runtime;
    return this;
  }

  /**
   * Get runtime
   * @return runtime
   */
  @NotNull @Valid 
  @Schema(name = "runtime", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("runtime")
  public Runtime getRuntime() {
    return runtime;
  }

  public void setRuntime(Runtime runtime) {
    this.runtime = runtime;
  }

  public RunSubmit videoLink(String videoLink) {
    this.videoLink = videoLink;
    return this;
  }

  /**
   * Get videoLink
   * @return videoLink
   */
  @NotNull 
  @Schema(name = "videoLink", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("videoLink")
  public String getVideoLink() {
    return videoLink;
  }

  public void setVideoLink(String videoLink) {
    this.videoLink = videoLink;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RunSubmit runSubmit = (RunSubmit) o;
    return Objects.equals(this.date, runSubmit.date) &&
        Objects.equals(this.runtime, runSubmit.runtime) &&
        Objects.equals(this.videoLink, runSubmit.videoLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, runtime, videoLink);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RunSubmit {\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    runtime: ").append(toIndentedString(runtime)).append("\n");
    sb.append("    videoLink: ").append(toIndentedString(videoLink)).append("\n");
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

