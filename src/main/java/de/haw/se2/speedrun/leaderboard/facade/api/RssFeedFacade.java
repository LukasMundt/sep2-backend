package de.haw.se2.speedrun.leaderboard.facade.api;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//TODO: Replace with actual api interface
public interface RssFeedFacade {

    @GetMapping(value = "/getFeedUrl")
    ResponseEntity<String> getFeedUrl();

    @GetMapping(value = "/getFeed/{id}", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    ResponseEntity<String> getFeedView(@NotNull @PathVariable String id);
}
