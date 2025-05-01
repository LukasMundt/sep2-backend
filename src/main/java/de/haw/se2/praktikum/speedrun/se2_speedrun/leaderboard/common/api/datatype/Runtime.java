package de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.common.api.datatype;

import jakarta.persistence.Embeddable;
import org.jetbrains.annotations.NotNull;

@Embeddable
public record Runtime(int hour, int minute, int second, int millisecond) {

    public Runtime {
        if (hour < 0 || minute < 0 || minute > 59 || second < 0 || second > 59 || millisecond < 0 || millisecond > 999) {
            throw new IllegalArgumentException("Invalid runtime parameters");
        }
    }

    @Override
    public @NotNull String toString() {
        return String.format("%02d:%02d:%02d:%04d", hour, minute, second, millisecond);
    }
}
