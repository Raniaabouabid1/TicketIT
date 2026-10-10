package tickets.dev.ticketit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tickets.dev.ticketit.entities.Comment;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
