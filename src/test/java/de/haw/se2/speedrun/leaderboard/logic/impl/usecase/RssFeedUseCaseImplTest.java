package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.logic.impl.usecase.services.RssFeedViewer;
import de.haw.se2.speedrun.user.dataaccess.api.entity.User;
import de.haw.se2.speedrun.user.dataaccess.api.repo.UserRepository;
import de.haw.se2.speedrun.user.logic.api.usecase.UserUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RssFeedUseCaseImplTest {

    private UserRepository userRepository;
    private RssFeedViewer rssFeedViewer;
    private RssFeedUseCaseImpl rssFeedUseCase;
    private MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic;
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        rssFeedViewer = mock(RssFeedViewer.class);
        userUseCase = mock(UserUseCase.class);
        rssFeedUseCase = new RssFeedUseCaseImpl(userUseCase, rssFeedViewer);
    }

    @AfterEach
    void tearDown() {
        if (securityContextHolderMockedStatic != null) {
            securityContextHolderMockedStatic.close();
        }
    }

    @Test
    void getFeedUrl_returnsCorrectUrl() {

        String email = "test@example.com";
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn(email);

        var securityContext = mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(token);

        securityContextHolderMockedStatic = Mockito.mockStatic(SecurityContextHolder.class);
        securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        String url = rssFeedUseCase.getFeedUrl();

        assertEquals("/rest/rss/getFeed/" + userId, url);
    }

    @Test
    void getFeedUrl_throwsException_whenNoJwtToken() {
        var securityContext = mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);

        securityContextHolderMockedStatic = Mockito.mockStatic(SecurityContextHolder.class);
        securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

        assertThrows(InsufficientAuthenticationException.class, () -> rssFeedUseCase.getFeedUrl());
    }

    @Test
    void getFeedView_delegatesToRssFeedViewer() {
        String id = "123";
        String expectedFeed = "<rss>feed</rss>";
        when(rssFeedViewer.buildFeed(id)).thenReturn(expectedFeed);

        String result = rssFeedUseCase.getFeedView(id);

        assertEquals(expectedFeed, result);
        verify(rssFeedViewer).buildFeed(id);
    }

    @Test
    void getFeedUrl_throwsException_whenUserNotFound() {
        String email = "notfound@example.com";
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn(email);

        var securityContext = mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(token);

        securityContextHolderMockedStatic = Mockito.mockStatic(SecurityContextHolder.class);
        securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(InsufficientAuthenticationException.class, () -> rssFeedUseCase.getFeedUrl());
    }

    @Test
    void getFeedUrl_throwsException_whenAuthenticationIsNotJwtToken() {
        var authentication = mock(org.springframework.security.core.Authentication.class); // Nicht JwtAuthenticationToken
        var securityContext = mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        securityContextHolderMockedStatic = Mockito.mockStatic(SecurityContextHolder.class);
        securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

        assertThrows(InsufficientAuthenticationException.class, () -> rssFeedUseCase.getFeedUrl());
    }

    @Test
    void getFeedView_throwsException_whenRssFeedViewerThrows() {
        String id = "invalid";
        when(rssFeedViewer.buildFeed(id)).thenThrow(new RuntimeException("Feed error"));

        assertThrows(RuntimeException.class, () -> rssFeedUseCase.getFeedView(id));
    }
}