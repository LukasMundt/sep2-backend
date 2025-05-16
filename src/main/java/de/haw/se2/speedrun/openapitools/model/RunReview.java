package de.haw.se2.speedrun.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * RunReview
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-11T18:39:45.042073870Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class RunReview {

  private @Nullable String gameName;

  private @Nullable String categoryLabel;

  private @Nullable String uuid;

  private @Nullable RunDto run;

  public RunReview gameName(String gameName) {
    this.gameName = gameName;
    return this;
  }

  /**
   * Get gameName
   * @return gameName
   */
  
  @Schema(name = "gameName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("gameName")
  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public RunReview categoryLabel(String categoryLabel) {
    this.categoryLabel = categoryLabel;
    return this;
  }

  /**
   * Get categoryLabel
   * @return categoryLabel
   */
  
  @Schema(name = "categoryLabel", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("categoryLabel")
  public String getCategoryLabel() {
    return categoryLabel;
  }

  public void setCategoryLabel(String categoryLabel) {
    this.categoryLabel = categoryLabel;
  }

  public RunReview uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  /**
   * Get uuid
   * @return uuid
   */
  
  @Schema(name = "uuid", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uuid")
  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public RunReview run(RunDto run) {
    this.run = run;
    return this;
  }

  /**
   * Get run
   * @return run
   */
  @Valid 
  @Schema(name = "run", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("run")
  public RunDto getRun() {
    return run;
  }

  public void setRun(RunDto run) {
    this.run = run;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RunReview runReview = (RunReview) o;
    return Objects.equals(this.gameName, runReview.gameName) &&
        Objects.equals(this.categoryLabel, runReview.categoryLabel) &&
        Objects.equals(this.uuid, runReview.uuid) &&
        Objects.equals(this.run, runReview.run);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gameName, categoryLabel, uuid, run);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RunReview {\n");
    sb.append("    gameName: ").append(toIndentedString(gameName)).append("\n");
    sb.append("    categoryLabel: ").append(toIndentedString(categoryLabel)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    run: ").append(toIndentedString(run)).append("\n");
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

