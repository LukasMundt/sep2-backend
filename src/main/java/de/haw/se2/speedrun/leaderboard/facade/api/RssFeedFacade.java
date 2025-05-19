package de.haw.se2.speedrun.leaderboard.facade.api;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

//TODO: Replace with actual api interface
public interface RssFeedFacade {

    @GetMapping(value = "/getFeedUrl")
    String getFeedUrl();

    @GetMapping(value = "/getFeed/{id}")
    ModelAndView getFeedView(@NotNull @PathVariable String id);
}
