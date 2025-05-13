package de.haw.se2.speedrun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"de.haw.se2"})
public class Se2SpeedrunApplication {

	public static void main(String[] args) {
		SpringApplication.run(Se2SpeedrunApplication.class, args);
	}
}
