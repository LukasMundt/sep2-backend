package de.haw.se2.praktikum.speedrun.se2_speedrun.openapitools.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Speedruns - OpenAPI 3.0")
                                .description("Api für den vertikalen Prototypen")
                                .version("0.0.2")
                )
        ;
    }
}