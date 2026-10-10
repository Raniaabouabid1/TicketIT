package tickets.dev.ticketit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tickets.dev.ticketit.entities.Ticket;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
}
