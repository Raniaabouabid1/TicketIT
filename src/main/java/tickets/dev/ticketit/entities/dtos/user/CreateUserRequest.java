package tickets.dev.ticketit.entities.dtos.user;

import jakarta.validation.constraints.*;
import tickets.dev.ticketit.entities.enumerations.RoleEnum;
import java.util.UUID;

public record CreateUserRequest(

        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must not exceed 100 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must not exceed 100 characters")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "Role is required")
        RoleEnum role,

        // international number regex
        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^\\+[1-9]\\d{1,14}$",
                message = "Phone number must be in international format (e.g. +33612345678)"
        )
        String phoneNumber,

        UUID teamId

) {}
