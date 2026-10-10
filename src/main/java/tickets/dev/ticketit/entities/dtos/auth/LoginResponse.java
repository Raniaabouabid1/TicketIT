package tickets.dev.ticketit.entities.dtos.auth;

public record LoginResponse(
        String accessToken, //the actual token generated after successful auth
        String tokenType, // usually bearer
        long expiresIn //exemple : 3600ms
) {}
