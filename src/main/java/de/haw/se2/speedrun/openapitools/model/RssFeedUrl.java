package de.haw.se2.speedrun.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * RssFeedUrl
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-11T13:07:45.254582879Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class RssFeedUrl {

  private String url;

  public RssFeedUrl() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RssFeedUrl(String url) {
    this.url = url;
  }

  public RssFeedUrl url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
   */
  @NotNull 
  @Schema(name = "url", example = "https://speedrun.lukas-mundt.de/rest/rss", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("url")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RssFeedUrl rssFeedUrl = (RssFeedUrl) o;
    return Objects.equals(this.url, rssFeedUrl.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RssFeedUrl {\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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

