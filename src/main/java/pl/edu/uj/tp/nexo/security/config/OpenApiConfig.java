package pl.edu.uj.tp.nexo.security.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Nexo API",
                version = "1.0",
                description = "API documentation for Nexo Application"
        )
)
public class OpenApiConfig {
}
