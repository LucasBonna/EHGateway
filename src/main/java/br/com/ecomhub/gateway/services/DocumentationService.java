package br.com.ecomhub.gateway.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@ConfigurationProperties(prefix = "documentation")
public class DocumentationService {

    private List<Map<String, String>> services;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Map<String, String>> getServices() {
        return services;
    }

    public void setServices(List<Map<String, String>> services) {
        this.services = services;
    }

    public JsonNode getMergedDocumentation() {
        JsonNode mergedDocumentation = objectMapper.createObjectNode();
        for (Map<String, String> service : services) {
            String url = service.get("url");
            JsonNode serviceDocumentation = fetchDocumentation(url);
            mergeJsonNodes(mergedDocumentation, serviceDocumentation);
        }
        return mergedDocumentation;
    }

    private JsonNode fetchDocumentation(String url) {
        String response = restTemplate.getForObject(url, String.class);
        try {
            return objectMapper.readTree(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse documentation from " + url, e);
        }
    }

    private void mergeJsonNodes(JsonNode mainNode, JsonNode updateNode) {
        updateNode.fields().forEachRemaining(field -> {
            String fieldName = field.getKey();
            JsonNode value = field.getValue();
            if (mainNode.has(fieldName)) {
                JsonNode existingValue = mainNode.get(fieldName);
                if (existingValue.isObject() && value.isObject()) {
                    mergeJsonNodes(existingValue, value);
                }
            } else {
                ((ObjectNode) mainNode).set(fieldName, value);
            }
        });
    }
}