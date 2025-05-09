package de.haw.se2.speedrun.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Leaderboard
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-06T09:14:52.282358031Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class Leaderboard {

  private Category category;

  @Valid
  private List<@Valid Entry> runs = new ArrayList<>();

  private Long id;

  public Leaderboard() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Leaderboard(Category category, Long id) {
    this.category = category;
    this.id = id;
  }

  public Leaderboard category(Category category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
   */
  @NotNull @Valid 
  @Schema(name = "category", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("category")
  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Leaderboard runs(List<@Valid Entry> runs) {
    this.runs = runs;
    return this;
  }

  public Leaderboard addRunsItem(Entry runsItem) {
    if (this.runs == null) {
      this.runs = new ArrayList<>();
    }
    this.runs.add(runsItem);
    return this;
  }

  /**
   * Get runs
   * @return runs
   */
  @Valid 
  @Schema(name = "runs", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("runs")
  public List<@Valid Entry> getRuns() {
    return runs;
  }

  public void setRuns(List<@Valid Entry> runs) {
    this.runs = runs;
  }

  public Leaderboard id(Long id) {
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
    Leaderboard leaderboard = (Leaderboard) o;
    return Objects.equals(this.category, leaderboard.category) &&
        Objects.equals(this.runs, leaderboard.runs) &&
        Objects.equals(this.id, leaderboard.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, runs, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Leaderboard {\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    runs: ").append(toIndentedString(runs)).append("\n");
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

