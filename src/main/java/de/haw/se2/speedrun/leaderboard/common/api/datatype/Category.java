package de.haw.se2.speedrun.leaderboard.common.api.datatype;

import jakarta.persistence.Embeddable;

@Embeddable
public record Category(String categoryId, String label) {
}
