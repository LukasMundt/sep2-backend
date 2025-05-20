package de.haw.se2.speedrun.leaderboard.facade.api;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.View;

//TODO: Replace with actual api interface
public interface RssFeedFacade {

    @GetMapping(value = "/getFeedUrl")
    ResponseEntity<String> getFeedUrl();

    @GetMapping(value = "/getFeed/{id}")
    ResponseEntity<View> getFeedView(@NotNull @PathVariable String id);
}
