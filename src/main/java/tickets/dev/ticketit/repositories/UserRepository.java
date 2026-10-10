package tickets.dev.ticketit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tickets.dev.ticketit.entities.User;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
