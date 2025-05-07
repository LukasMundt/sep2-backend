package de.haw.se2.praktikum.speedrun.se2_speedrun.common;

import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Component;

@Component
public class MyModelMapper extends ModelMapper {
    public MyModelMapper() {
        this.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        this.typeMap(de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity.Leaderboard.class, de.haw.se2.praktikum.speedrun.se2_speedrun.openapitools.model.Leaderboard.class).addMapping(Leaderboard::getRuns, de.haw.se2.praktikum.speedrun.se2_speedrun.openapitools.model.Leaderboard::setRuns);
        this.typeMap(de.haw.se2.praktikum.speedrun.se2_speedrun.openapitools.model.Leaderboard.class, de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity.Leaderboard.class).addMapping(de.haw.se2.praktikum.speedrun.se2_speedrun.openapitools.model.Leaderboard::getRuns, Leaderboard::setRuns);
    }
}
