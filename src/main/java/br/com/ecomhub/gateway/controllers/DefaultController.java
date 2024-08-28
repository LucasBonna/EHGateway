package br.com.ecomhub.gateway.controllers;

import br.com.ecomhub.gateway.services.DocumentationService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Map;

@RestController
public class DefaultController {

    @Autowired
    private DocumentationService documentationService;

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> getStatus() {
        Map<String, String> status = Collections.singletonMap("application", "gateway-up");
        return ResponseEntity.ok(status);
    }

    @GetMapping("/merged-docs")
    public ResponseEntity<JsonNode> getMergedDocs() {
        JsonNode mergedDocs = documentationService.getMergedDocumentation();
        return ResponseEntity.ok(mergedDocs);
    }
}
