package tickets.dev.ticketit.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tickets.dev.ticketit.entities.enumerations.TicketPriority;
import tickets.dev.ticketit.entities.enumerations.TicketStatus;
import java.time.LocalDateTime;
import org.hibernate.annotations.UuidGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// We will be cascading ! cascading makes more sense on things that are 'truly' owned by the ticket
@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class Ticket {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    @ToString.Include
    private UUID id;

    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    @ToString.Include
    private String title;

    @NotBlank(message = "Title is required")
    @Column(name = "description")
    @ToString.Include
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    @ToString.Include
    private TicketPriority priority;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status", nullable = false)
    private TicketStatus ticketStatus = TicketStatus.OPEN;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @ToString.Include
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    @ToString.Include
    private LocalDateTime modifiedAt;

    //For attachments, CascadeType.ALL is reasonable because an attachment belongs to a ticket lifecycle
    @Builder.Default
    @OneToMany(
            mappedBy = "ticket",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Attachment> attachments = new ArrayList<>();

    //Same idea for comments, if comments should disappear when the ticket is deleted
    @Builder.Default
    @OneToMany(
            mappedBy = "ticket",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Comment> comments = new ArrayList<>();

   // ticket should obviously not delete the creator, assignee, or category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;



}