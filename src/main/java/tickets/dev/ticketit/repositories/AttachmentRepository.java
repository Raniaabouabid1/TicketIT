package tickets.dev.ticketit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tickets.dev.ticketit.entities.Attachment;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}
