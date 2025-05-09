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
 * Game
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-06T09:14:52.282358031Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
public class Game {

  private String name;

  private String imageUrl;

  private String slug;

  private Long id;

  @Valid
  private List<@Valid Leaderboard> leaderboards = new ArrayList<>();

  public Game() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Game(String name, String imageUrl, String slug, Long id) {
    this.name = name;
    this.imageUrl = imageUrl;
    this.slug = slug;
    this.id = id;
  }

  public Game name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @Schema(name = "name", example = "minecraft", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Game imageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  /**
   * Get imageUrl
   * @return imageUrl
   */
  @NotNull 
  @Schema(name = "image_url", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("image_url")
  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Game slug(String slug) {
    this.slug = slug;
    return this;
  }

  /**
   * Get slug
   * @return slug
   */
  @NotNull 
  @Schema(name = "slug", example = "mncrf??", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("slug")
  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public Game id(Long id) {
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

  public Game leaderboards(List<@Valid Leaderboard> leaderboards) {
    this.leaderboards = leaderboards;
    return this;
  }

  public Game addLeaderboardsItem(Leaderboard leaderboardsItem) {
    if (this.leaderboards == null) {
      this.leaderboards = new ArrayList<>();
    }
    this.leaderboards.add(leaderboardsItem);
    return this;
  }

  /**
   * Get leaderboards
   * @return leaderboards
   */
  @Valid 
  @Schema(name = "leaderboards", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("leaderboards")
  public List<@Valid Leaderboard> getLeaderboards() {
    return leaderboards;
  }

  public void setLeaderboards(List<@Valid Leaderboard> leaderboards) {
    this.leaderboards = leaderboards;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Game game = (Game) o;
    return Objects.equals(this.name, game.name) &&
        Objects.equals(this.imageUrl, game.imageUrl) &&
        Objects.equals(this.slug, game.slug) &&
        Objects.equals(this.id, game.id) &&
        Objects.equals(this.leaderboards, game.leaderboards);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, imageUrl, slug, id, leaderboards);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Game {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    imageUrl: ").append(toIndentedString(imageUrl)).append("\n");
    sb.append("    slug: ").append(toIndentedString(slug)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    leaderboards: ").append(toIndentedString(leaderboards)).append("\n");
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

