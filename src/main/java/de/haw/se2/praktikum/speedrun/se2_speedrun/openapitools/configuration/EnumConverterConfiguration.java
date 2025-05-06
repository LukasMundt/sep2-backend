package de.haw.se2.praktikum.speedrun.se2_speedrun.openapitools.configuration;


import de.haw.se2.praktikum.speedrun.se2_speedrun.openapitools.model.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration(value = "org.openapitools.configuration.enumConverterConfiguration")
public class EnumConverterConfiguration {

    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.categoryConverter")
    Converter<String, Category> categoryConverter() {
        return new Converter<String, Category>() {
            @Override
            public Category convert(String source) {
                return Category.fromValue(source);
            }
        };
    }

}
