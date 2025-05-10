package de.haw.se2.speedrun.leaderboard.common.api.datatype;

import jakarta.persistence.Embeddable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

@Embeddable
public record Runtime(Duration runDuration) {

    public Runtime(int hour, int minute, int second, int millisecond) {
        this(Duration.ofHours(hour).plusMinutes(minute).plusSeconds(second).plusMillis(millisecond));
        if (hour < 0 || minute < 0 || minute > 59 || second < 0 || second > 59 || millisecond < 0 || millisecond > 999) {
            throw new IllegalArgumentException("Invalid runtime parameters");
        }
    }

    @Override
    public @NotNull String toString() {
        return runDuration.toString();
    }
}
