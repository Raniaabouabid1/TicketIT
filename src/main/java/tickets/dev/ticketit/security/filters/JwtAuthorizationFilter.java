package tickets.dev.ticketit.security.filters;

import io.jsonwebtoken.Claims;
import tickets.dev.ticketit.repositories.UserRepository;
import tickets.dev.ticketit.security.JwtUtil;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
    public class JwtAuthorizationFilter extends OncePerRequestFilter {
        private final JwtUtil jwtUtil;
        private final UserRepository userRepository;

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) {
            // public endpoints
            String p = request.getRequestURI();
            return p.startsWith("/api/auth/");
        }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {

        String hdr = req.getHeader(HttpHeaders.AUTHORIZATION);


//no token -> 401
            if (hdr == null || !hdr.startsWith("Bearer ")) {
                chain.doFilter(req, res);
                return;
            }

            String token = hdr.substring(7);

            try {
                Jws<Claims> claims = jwtUtil.parseToken(token);
                String userId = claims.getPayload().getSubject();
                String tokenRole = claims.getPayload().get("role", String.class);

                // Validate user from DB
                var user = userRepository.findById(UUID.fromString(userId))
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));

                String dbRole = user.getRole().name();

                // forbidden 403 : authenticated but forbidden
                if (!dbRole.equals(tokenRole)) {
                    send(res, HttpServletResponse.SC_FORBIDDEN, "Role mismatch");
                    return;
                }

                var auth = new UsernamePasswordAuthenticationToken(
                        user.getId(),
                        null,
                        List.of(new SimpleGrantedAuthority(dbRole))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);

                chain.doFilter(req, res);

            } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
//401                SecurityContextHolder.clearContext();
                send(res, HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            }
        }

        private void send(HttpServletResponse res, int status, String msg) throws IOException {
            res.setStatus(status);
            res.setContentType("application/json");
            res.getWriter().write("{\"message\":\"" + msg + "\"}");
        }
    }



