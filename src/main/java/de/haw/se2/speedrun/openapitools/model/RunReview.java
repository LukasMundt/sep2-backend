package de.haw.se2.speedrun.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * RunReview
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-05T18:48:11.037630545Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class RunReview {

  private String gameName;

  private String categoryLabel;

  private RunDto run;

  public RunReview() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RunReview(String gameName, String categoryLabel, RunDto run) {
    this.gameName = gameName;
    this.categoryLabel = categoryLabel;
    this.run = run;
  }

  public RunReview gameName(String gameName) {
    this.gameName = gameName;
    return this;
  }

  /**
   * Get gameName
   * @return gameName
   */
  @NotNull 
  @Schema(name = "gameName", requiredMode = Schema.RequiredMode.REQUIRED)
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
  @NotNull 
  @Schema(name = "categoryLabel", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("categoryLabel")
  public String getCategoryLabel() {
    return categoryLabel;
  }

  public void setCategoryLabel(String categoryLabel) {
    this.categoryLabel = categoryLabel;
  }

  public RunReview run(RunDto run) {
    this.run = run;
    return this;
  }

  /**
   * Get run
   * @return run
   */
  @NotNull @Valid 
  @Schema(name = "run", requiredMode = Schema.RequiredMode.REQUIRED)
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
        Objects.equals(this.run, runReview.run);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gameName, categoryLabel, run);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RunReview {\n");
    sb.append("    gameName: ").append(toIndentedString(gameName)).append("\n");
    sb.append("    categoryLabel: ").append(toIndentedString(categoryLabel)).append("\n");
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

