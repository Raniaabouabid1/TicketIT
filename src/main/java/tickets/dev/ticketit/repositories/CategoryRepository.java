package tickets.dev.ticketit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tickets.dev.ticketit.entities.Category;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
