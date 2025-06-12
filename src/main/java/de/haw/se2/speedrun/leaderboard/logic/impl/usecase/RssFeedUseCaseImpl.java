package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RssFeedUseCase;
import de.haw.se2.speedrun.leaderboard.logic.impl.usecase.services.RssFeedViewer;
import de.haw.se2.speedrun.user.logic.api.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RssFeedUseCaseImpl implements RssFeedUseCase{

    private final UserUseCase userUseCase;
    private final RssFeedViewer rssFeedViewer;

    @Override
    public String getFeedUrl() {
        String id = getUserId();
        return "/rest/rss/getFeed/" + id;
    }

    @SneakyThrows
    @Override
    public String getFeedView(String id) {
        return rssFeedViewer.buildFeed(id);
    }

    private String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken token) {
            return userUseCase.findUserByEmail(token.getName()).getId().toString();
        }

        throw new InsufficientAuthenticationException("No JWT token found");
    }
}
