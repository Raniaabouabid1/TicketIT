package tickets.dev.ticketit.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import tickets.dev.ticketit.entities.enumerations.RoleEnum;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// A point to remember future rania : we will not be cascading here ! That would be disastrous for my history (Tickets, comments etc ...).
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter @ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    @ToString.Include
    private UUID id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    @ToString.Include
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    @ToString.Include
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(name = "email", nullable = false, unique = true)
    @ToString.Include
    private String email;

    // Invitation
    // Store the HASH of the token, not the raw token future rania !!!!!!!!!!!!!!
    @Builder.Default
    @Column(name = "is_email_verified", nullable = false)
    @ToString.Include
    private Boolean isEmailVerified = false;

    @Column(name = "invite_token", unique = true)
    private String inviteToken;

    @Column(name = "invite_token_expires_at")
    private Instant inviteTokenExpiresAt;

    @Column(name = "invite_token_last_sent_at")
    private Instant inviteTokenLastSentAt;

    @Builder.Default
    @Column(name = "invite_token_attempts", nullable = false)
    private int inviteTokenAttempts = 0;

    //  ACCOUNT SECURITY
    @Builder.Default
    @Column(name = "must_change_password", nullable = false)
    private Boolean mustChangePassword = true;

    @NotBlank(message = "Password is required")
    @Size(min = 8)
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one special character"
    )
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^(?:0[67]\\d{8}|\\+33[67]\\d{8}|\\+\\d{5,15})$",
            message = "Phone number must be a valid French or international number"
    )
    @Column(name = "phone_number", nullable = false, unique = true)
    @ToString.Include
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @ToString.Include
    private RoleEnum role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    // team_id must stay nullable in the database because Admin has no team:
    //in eager loading if we load the user data it will also load up the team data too and store it in memory
    // LAZY because not every operation on a user needs the team
    // so if i need the team i can simply do user.getTeam().getWhatever()
    private Team team;

    @Builder.Default
    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY)
    private List<Ticket> assignedTickets = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Ticket> createdTickets = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Comment> createdComments = new ArrayList<>();

    @OneToOne(mappedBy = "supervisor")
    private Team supervisedTeam;

    @Builder.Default
    @Column(name = "active", nullable = false)
    @ToString.Include
    private Boolean active = true;
}