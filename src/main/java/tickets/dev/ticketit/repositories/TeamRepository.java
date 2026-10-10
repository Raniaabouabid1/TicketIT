package tickets.dev.ticketit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tickets.dev.ticketit.entities.Team;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
}
