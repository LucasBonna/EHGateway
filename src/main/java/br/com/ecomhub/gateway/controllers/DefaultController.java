package br.com.ecomhub.gateway.controllers;

import br.com.ecomhub.gateway.services.DocumentationService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class DefaultController {

    @Autowired
    private DocumentationService documentationService;

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> getStatus() {
        Map<String, String> status = Collections.singletonMap("application", "gateway-up");
        return ResponseEntity.ok(status);
    }

    @GetMapping("/redoc")
    @Hidden
    public String redocHtml() {
        return """
            <!DOCTYPE html>
            <html>
              <head>
                <title>API Documentation</title>
                <meta charset="utf-8"/>
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <link href="https://fonts.googleapis.com/css?family=Montserrat:300,400,700|Roboto:300,400,700" rel="stylesheet">
                <style>
                  body {
                    margin: 0;
                    padding: 0;
                  }
                </style>
              </head>
              <body>
                <redoc spec-url="/merged-docs"></redoc>
                <script src="https://cdn.redoc.ly/redoc/latest/bundles/redoc.standalone.js"></script>
              </body>
            </html>
        """;
    }

    @GetMapping("/merged-docs")
    @Hidden
    public ResponseEntity<JsonNode> getMergedDocs() {
        JsonNode mergedDocs = documentationService.getMergedDocumentation();
        return ResponseEntity.ok(mergedDocs);
    }
}
