package tickets.dev.ticketit.entities.dtos.user;

import tickets.dev.ticketit.entities.enumerations.RoleEnum;
import java.time.Instant;
import java.util.UUID;

//Remember furture rania to use @Valid @RequestBody CreateUserRequest request in your controller.

public record CreateUserResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        RoleEnum role,
        String phoneNumber,
        UUID teamId,
        boolean active,
        boolean emailVerified,
        Instant createdAt
) {}
