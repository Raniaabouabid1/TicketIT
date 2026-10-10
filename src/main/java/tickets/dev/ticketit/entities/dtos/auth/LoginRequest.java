package tickets.dev.ticketit.entities.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//Java records are ideal for DTOs because DTOs are mainly used to transfer data between application layers
//without needing to modify it.
// Records automatically generate constructors, getters (accessor methods),
// `equals()`, `hashCode()`, and `toString()`,
// reducing boilerplate/repetetive code and eliminating the need for Lombok.
// They are also immutable, meaning their fields cannot be reassigned after creation,
// making data transfer safer and more predictable.
public record LoginRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        String password

) {}
