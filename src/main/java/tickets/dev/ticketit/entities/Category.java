package tickets.dev.ticketit.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter @Setter @ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    @ToString.Include
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    @ToString.Include
    private String name;

    // THIS MEANS :
    //active = true → category can be selected for new tickets
    //active = false → category is retired/disabled, but old tickets still keep their reference to it
    @Builder.Default
    @Column(name = "active", nullable = false)
    @ToString.Include
    private Boolean active = true;

    @Builder.Default
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
