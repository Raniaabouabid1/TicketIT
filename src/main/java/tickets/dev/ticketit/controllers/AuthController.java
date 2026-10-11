
package tickets.dev.ticketit.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tickets.dev.ticketit.entities.User;
import tickets.dev.ticketit.entities.dtos.auth.LoginRequest;
import tickets.dev.ticketit.entities.dtos.auth.LoginResponse;
import tickets.dev.ticketit.repositories.UserRepository;
import tickets.dev.ticketit.security.JwtUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.email(),
                        req.password()
                )
        );

        User user = userRepo.findByEmail(req.email())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Bad credentials")
                );

        if (!Boolean.TRUE.equals(user.getIsEmailVerified())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Email must be verified before logging in."
            );
        }

        String jwt = jwtUtil.generateToken(user);

        return ResponseEntity.ok(
                new LoginResponse(jwt, "Bearer", 3600)
        );
    }
}
