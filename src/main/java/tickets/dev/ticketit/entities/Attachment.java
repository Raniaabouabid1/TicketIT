package tickets.dev.ticketit.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attachments")
@Getter @Setter @ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Attachment {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    @ToString.Include
    private UUID id;

    @Column(name = "file_name", nullable = false)
    @ToString.Include
    private String fileName;

    @Column(name = "file_type", nullable = false)
    @ToString.Include
    private String fileType;

    @Column(name = "file_size", nullable = false)
    @ToString.Include
    private long fileSize;

    @Column(name = "storage_path", nullable = false, unique = true)
    @ToString.Include
    private String storagePath;

    @CreationTimestamp
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    @ToString.Include
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

}
