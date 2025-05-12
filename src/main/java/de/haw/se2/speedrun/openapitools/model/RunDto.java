package de.haw.se2.speedrun.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
<<<<<<<< HEAD:src/main/java/de/haw/se2/speedrun/openapitools/model/Entry.java
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.annotation.Generated;
========
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;
>>>>>>>> SE2-22:src/main/java/de/haw/se2/speedrun/openapitools/model/RunDto.java

/**
 * RunDto
 */

<<<<<<<< HEAD:src/main/java/de/haw/se2/speedrun/openapitools/model/Entry.java
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-08T16:37:51.806483709Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class Entry {
========
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-11T18:39:45.042073870Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class RunDto {
>>>>>>>> SE2-22:src/main/java/de/haw/se2/speedrun/openapitools/model/RunDto.java

  private String speedrunner;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date date;

  private Runtime runtime;

<<<<<<<< HEAD:src/main/java/de/haw/se2/speedrun/openapitools/model/Entry.java
  public Entry() {
========
  public RunDto() {
>>>>>>>> SE2-22:src/main/java/de/haw/se2/speedrun/openapitools/model/RunDto.java
    super();
  }

  /**
   * Constructor with only required parameters
   */
<<<<<<<< HEAD:src/main/java/de/haw/se2/speedrun/openapitools/model/Entry.java
  public Entry(String speedrunner, Date date, Runtime runtime) {
========
  public RunDto(String speedrunner, Date date, Runtime runtime) {
>>>>>>>> SE2-22:src/main/java/de/haw/se2/speedrun/openapitools/model/RunDto.java
    this.speedrunner = speedrunner;
    this.date = date;
    this.runtime = runtime;
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

<<<<<<<< HEAD:src/main/java/de/haw/se2/speedrun/openapitools/model/Entry.java
  public Entry date(Date date) {
========
  public RunDto date(Date date) {
>>>>>>>> SE2-22:src/main/java/de/haw/se2/speedrun/openapitools/model/RunDto.java
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
<<<<<<<< HEAD:src/main/java/de/haw/se2/speedrun/openapitools/model/Entry.java
    Entry entry = (Entry) o;
    return Objects.equals(this.speedrunner, entry.speedrunner) &&
        Objects.equals(this.date, entry.date) &&
        Objects.equals(this.runtime, entry.runtime);
========
    RunDto runDto = (RunDto) o;
    return Objects.equals(this.speedrunner, runDto.speedrunner) &&
        Objects.equals(this.date, runDto.date) &&
        Objects.equals(this.runtime, runDto.runtime);
>>>>>>>> SE2-22:src/main/java/de/haw/se2/speedrun/openapitools/model/RunDto.java
  }

  @Override
  public int hashCode() {
    return Objects.hash(speedrunner, date, runtime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RunDto {\n");
    sb.append("    speedrunner: ").append(toIndentedString(speedrunner)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    runtime: ").append(toIndentedString(runtime)).append("\n");
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

