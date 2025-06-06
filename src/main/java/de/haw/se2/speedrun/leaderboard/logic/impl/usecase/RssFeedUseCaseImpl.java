package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RssFeedUseCase;
import de.haw.se2.speedrun.leaderboard.logic.impl.usecase.services.RssFeedViewer;
import de.haw.se2.speedrun.user.dataaccess.api.entity.User;
import de.haw.se2.speedrun.user.dataaccess.api.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RssFeedUseCaseImpl implements RssFeedUseCase{

    private final UserRepository userRepository;
    private final RssFeedViewer rssFeedViewer;

    @Override
    public String getFeedUrl() {
        String id = getUserId();
        String baseUrl = "/rest/rss/getFeed/";
        return baseUrl + id;
    }

    @SneakyThrows
    @Override
    public String getFeedView(String id) {
        return rssFeedViewer.buildFeed(id);
    }

    private String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken token) {
            Optional<User> user = userRepository.findByEmail(token.getName());
            if(user.isPresent()) {
                return user.get().getId().toString();
            }
        }

        throw new InsufficientAuthenticationException("No JWT token found");
    }
}
