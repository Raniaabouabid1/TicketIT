package tickets.dev.ticketit.repositories;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import tickets.dev.ticketit.entities.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email);
}
