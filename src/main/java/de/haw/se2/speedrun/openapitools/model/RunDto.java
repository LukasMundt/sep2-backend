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
 * RunDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-03T06:27:08.812291795Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class RunDto {

  private String speedrunner;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date date;

  private Runtime runtime;

  private String videoLink;

  public RunDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RunDto(String speedrunner, Date date, Runtime runtime, String videoLink) {
    this.speedrunner = speedrunner;
    this.date = date;
    this.runtime = runtime;
    this.videoLink = videoLink;
  }

  public RunDto speedrunner(String speedrunner) {
    this.speedrunner = speedrunner;
    return this;
  }

  /**
   * Username of the speedrunner's account.
   * @return speedrunner
   */
  @NotNull 
  @Schema(name = "speedrunner", description = "Username of the speedrunner's account.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("speedrunner")
  public String getSpeedrunner() {
    return speedrunner;
  }

  public void setSpeedrunner(String speedrunner) {
    this.speedrunner = speedrunner;
  }

  public RunDto date(Date date) {
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

  public RunDto runtime(Runtime runtime) {
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

  public RunDto videoLink(String videoLink) {
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
    RunDto runDto = (RunDto) o;
    return Objects.equals(this.speedrunner, runDto.speedrunner) &&
        Objects.equals(this.date, runDto.date) &&
        Objects.equals(this.runtime, runDto.runtime) &&
        Objects.equals(this.videoLink, runDto.videoLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(speedrunner, date, runtime, videoLink);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RunDto {\n");
    sb.append("    speedrunner: ").append(toIndentedString(speedrunner)).append("\n");
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

