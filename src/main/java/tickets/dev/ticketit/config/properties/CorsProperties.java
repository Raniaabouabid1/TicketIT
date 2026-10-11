package tickets.dev.ticketit.config.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "cors")
@Getter @Setter
public class CorsProperties {
    private List<String> allowedOrigins = new ArrayList<>();
}

