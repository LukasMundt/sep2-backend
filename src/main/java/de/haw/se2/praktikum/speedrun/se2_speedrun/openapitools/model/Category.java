package de.haw.se2.praktikum.speedrun.se2_speedrun.openapitools.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;

/**
 * Gets or Sets Category
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-06T09:14:52.282358031Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public enum Category {
  
  ANY_("ANY%"),
  
  ANY_GLITCHLESS("ANY% GLITCHLESS"),
  
  ALL_ARCHIEVMENTS("ALL ARCHIEVMENTS"),
  
  ALL_ADVANCEMENTS("ALL ADVANCEMENTS");

  private final String value;

  Category(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static Category fromValue(String value) {
    for (Category b : Category.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

