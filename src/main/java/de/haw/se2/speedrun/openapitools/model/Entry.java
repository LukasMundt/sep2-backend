package de.haw.se2.speedrun.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Entry
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-06T09:14:52.282358031Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class Entry {

  private String speedrunner;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime date;

  private Runtime runtime;

  private Long id;

  public Entry() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Entry(String speedrunner, OffsetDateTime date, Runtime runtime, Long id) {
    this.speedrunner = speedrunner;
    this.date = date;
    this.runtime = runtime;
    this.id = id;
  }

  public Entry speedrunner(String speedrunner) {
    this.speedrunner = speedrunner;
    return this;
  }

  /**
   * name des Speedrunneraccounts, erstmal Platzhalter, später ist hier auch einen Enittät
   * @return speedrunner
   */
  @NotNull 
  @Schema(name = "speedrunner", description = "name des Speedrunneraccounts, erstmal Platzhalter, später ist hier auch einen Enittät", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("speedrunner")
  public String getSpeedrunner() {
    return speedrunner;
  }

  public void setSpeedrunner(String speedrunner) {
    this.speedrunner = speedrunner;
  }

  public Entry date(OffsetDateTime date) {
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
  public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }

  public Entry runtime(Runtime runtime) {
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

  public Entry id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @NotNull 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Entry entry = (Entry) o;
    return Objects.equals(this.speedrunner, entry.speedrunner) &&
        Objects.equals(this.date, entry.date) &&
        Objects.equals(this.runtime, entry.runtime) &&
        Objects.equals(this.id, entry.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(speedrunner, date, runtime, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Entry {\n");
    sb.append("    speedrunner: ").append(toIndentedString(speedrunner)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    runtime: ").append(toIndentedString(runtime)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

