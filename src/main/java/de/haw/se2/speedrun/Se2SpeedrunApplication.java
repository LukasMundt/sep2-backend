package de.haw.se2.speedrun;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@OpenAPIDefinition(
		servers = {
				@Server(url = "https://se2-9-und-se2-10-credentials-mit-denen-aus-der-db-abgleichen.backend.dev.lukas-mundt.de")
		}
)
@SpringBootApplication
@ComponentScan(basePackages = {"de.haw.se2"})
public class Se2SpeedrunApplication {

	public static void main(String[] args) {
		SpringApplication.run(Se2SpeedrunApplication.class, args);
	}
}
