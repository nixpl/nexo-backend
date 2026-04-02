package pl.edu.uj.tp.nexo.exception.config;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import pl.edu.uj.tp.nexo.exception.ErrorInfo;
import pl.edu.uj.tp.nexo.exception.annotation.ApiErrors;
import pl.edu.uj.tp.nexo.exception.dto.ErrorResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Component
public class ApiErrorsOperationCustomizer implements OperationCustomizer, OpenApiCustomizer {

    @Override
    public void customise(OpenAPI openApi) {
        if (openApi.getComponents() == null) {
            openApi.setComponents(new Components());
        }

        Map<String, Schema> schemas = openApi.getComponents().getSchemas();
        if (schemas == null || !schemas.containsKey("ErrorResponse")) {
            Schema<?> errorResponseSchema = ModelConverters.getInstance()
                    .resolveAsResolvedSchema(new io.swagger.v3.core.converter.AnnotatedType(ErrorResponse.class))
                    .schema;
            openApi.getComponents().addSchemas("ErrorResponse", errorResponseSchema);
        }
    }

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ApiErrors apiErrors = handlerMethod.getMethodAnnotation(ApiErrors.class);
        if (apiErrors == null || apiErrors.value().length == 0) {
            return operation;
        }

        List<ErrorInfo> allErrors = new ArrayList<>(Arrays.asList(apiErrors.value()));
        if (!allErrors.contains(ErrorInfo.INTERNAL_ERROR)) {
            allErrors.add(ErrorInfo.INTERNAL_ERROR);
        }

        Map<Integer, List<ErrorInfo>> errorsByStatus = allErrors.stream()
                .collect(Collectors.groupingBy(e -> e.getStatus().value()));

        ApiResponses responses = operation.getResponses();

        for (Map.Entry<Integer, List<ErrorInfo>> entry : errorsByStatus.entrySet()) {
            String statusCodeStr = String.valueOf(entry.getKey());
            List<ErrorInfo> errors = entry.getValue();

            ApiResponse apiResponse = responses.getOrDefault(statusCodeStr, new ApiResponse());

            StringBuilder description = new StringBuilder();
            if (apiResponse.getDescription() != null && !apiResponse.getDescription().isBlank()) {
                description.append(apiResponse.getDescription()).append("<br/><br/>");
            } else {
                description.append("Operation failed.<br/><br/>");
            }

            description.append("<strong>Possible errors:</strong><ul>");
            for (ErrorInfo errorInfo : errors) {
                description.append("<li><code>").append(errorInfo.getCode()).append("</code> - ")
                           .append(errorInfo.getDefaultMessage()).append("</li>");
            }
            description.append("</ul>");

            apiResponse.setDescription(description.toString());

            Content content = apiResponse.getContent();
            if (content == null) {
                content = new Content();
                apiResponse.setContent(content);
            }

            MediaType mediaType = content.getOrDefault(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType());
            if (mediaType.getSchema() == null) {
                Schema<?> schemaRef = new Schema<>();
                schemaRef.set$ref("#/components/schemas/ErrorResponse");
                mediaType.setSchema(schemaRef);
            }
            content.addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, mediaType);

            responses.addApiResponse(statusCodeStr, apiResponse);
        }

        return operation;
    }
}
