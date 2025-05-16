package de.haw.se2.speedrun.leaderboard.common.api.datatype;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public final class Category {
    private String categoryId;
    private String label;
}
