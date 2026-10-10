package tickets.dev.ticketit.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class Team {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    @ToString.Include
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    @ToString.Include
    private String name;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    //team should obviously not delete the members but transfer the user somewhere else !
    //Best rule for myapp: prevent deletion of a team while it still has members. Require the admin to reassign/remove the members first
    //That preserves user history and avoids accidentally breaking tickets/comments linked to those users!!!!!!
    @Builder.Default
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<User> members = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id", unique = true)
    private User supervisor;

    //Same idea for categories, if team is deleted, categories should go down with it lol!
    @Builder.Default
    @OneToMany(mappedBy = "team",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Category> categories = new ArrayList<>();
}
