package de.haw.se2.speedrun.common;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import org.modelmapper.Converter;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper extends org.modelmapper.ModelMapper {
    public ModelMapper() {
        this.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);


    }

    private void configureRuntime(){
        //Converter<Runtime, de.haw.se2.speedrun.openapitools.model.Runtime>

        //this.addConverter();
    }
}
