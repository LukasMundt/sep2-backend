package de.haw.se2.speedrun.common;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import org.modelmapper.Converter;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Component;

@Component
public class CustomizedModelMapper extends org.modelmapper.ModelMapper {
    public CustomizedModelMapper() {
        this.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        configureRuntime();
    }

    private void configureRuntime(){
        Converter<Runtime, de.haw.se2.speedrun.openapitools.model.Runtime> runtimeToDtoConverter = context -> {
            de.haw.se2.speedrun.openapitools.model.Runtime r = new de.haw.se2.speedrun.openapitools.model.Runtime();
            return r.hours((int)context.getSource().runDuration().toHours())
                .minutes(context.getSource().runDuration().toMinutesPart())
                .seconds(context.getSource().runDuration().toSecondsPart())
                .milliseconds(context.getSource().runDuration().toMillisPart());
        };

        this.createTypeMap(Runtime.class, de.haw.se2.speedrun.openapitools.model.Runtime.class)
                .setConverter(runtimeToDtoConverter);
    }
}
