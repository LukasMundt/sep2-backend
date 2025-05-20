package de.haw.se2.speedrun.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Category
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-16T13:03:18.760533262Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class Category {

  private String categoryId;

  private String label;

  public Category() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Category(String categoryId, String label) {
    this.categoryId = categoryId;
    this.label = label;
  }

  public Category categoryId(String categoryId) {
    this.categoryId = categoryId;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @NotNull 
  @Schema(name = "categoryId", example = "ANY_PERCENT", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("categoryId")
  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public Category label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Get label
   * @return label
   */
  @NotNull 
  @Schema(name = "label", example = "Any %", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("label")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return Objects.equals(this.categoryId, category.categoryId) &&
        Objects.equals(this.label, category.label);
  }

  @Override
  public int hashCode() {
    return Objects.hash(categoryId, label);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Category {\n");
    sb.append("    id: ").append(toIndentedString(categoryId)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
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

