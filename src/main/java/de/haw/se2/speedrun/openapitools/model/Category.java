package de.haw.se2.speedrun.openapitools.model;

import com.fasterxml.jackson.annotation.JsonValue;


import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets Category
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-08T16:37:51.806483709Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public enum Category {
  
  ANY_PERCENT("ANY_PERCENT"),
  
  ANY_PERCENT_GLITCHLESS("ANY_PERCENT_GLITCHLESS"),
  
  ALL_ACHIEVEMENTS("ALL_ACHIEVEMENTS"),
  
  ALL_ADVANCEMENTS("ALL_ADVANCEMENTS");

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

