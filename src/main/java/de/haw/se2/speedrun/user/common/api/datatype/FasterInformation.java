package de.haw.se2.speedrun.user.common.api.datatype;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record FasterInformation(UUID playerId, UUID runId) {
}
