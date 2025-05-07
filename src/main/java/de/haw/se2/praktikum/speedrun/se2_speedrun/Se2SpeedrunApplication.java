package de.haw.se2.praktikum.speedrun.se2_speedrun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

@SpringBootApplication
public class Se2SpeedrunApplication {

	public static void main(String[] args) {
		SpringApplication.run(Se2SpeedrunApplication.class, args);
	}
}
