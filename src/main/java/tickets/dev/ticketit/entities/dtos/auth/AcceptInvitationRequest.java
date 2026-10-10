package tickets.dev.ticketit.entities.dtos.auth;

import jakarta.validation.constraints.*;

// this DTO is used when a user clicks the invitation link sent by email and creates their
// password to activate their account.
public record AcceptInvitationRequest(
        @NotBlank(message = "Invitation token is required")
        String inviteToken, //Activates an account!

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must contain at least 8 characters")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$",
                message = "Password must contain an uppercase letter, a lowercase letter, and a special character"
        )
        String password

)
{}
