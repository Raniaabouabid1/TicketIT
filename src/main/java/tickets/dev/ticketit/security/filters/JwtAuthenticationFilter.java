package tickets.dev.ticketit.security.filters;

import jakarta.servlet.ServletException;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.ObjectMapper;
import tickets.dev.ticketit.entities.User;
import tickets.dev.ticketit.entities.dtos.auth.LoginRequest;
import tickets.dev.ticketit.repositories.UserRepository;
import tickets.dev.ticketit.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    {
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) throws AuthenticationException {
        try {
            LoginRequest creds = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            creds.email(),
                            creds.password()
                    );
            return authManager.authenticate(authToken);

        } catch (IOException e) {
            throw new AuthenticationException("Failed to parse login request", e) {};
        }
    }


    @Override
    protected void successfulAuthentication(
            @NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain,
            @NonNull Authentication authResult
    ) throws IOException {
        String userId = authResult.getName();

       User user = userRepository.findById(UUID.fromString(userId))
                        .orElseThrow(() -> new IllegalStateException("User not found"));

        String token = jwtUtil.generateToken(user);

        res.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper()
                .writeValue(res.getWriter(), Map.of("token", token));
    }
}
