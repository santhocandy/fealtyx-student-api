package com.fealtyx.studentapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class OllamaService {

    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private static final ObjectMapper mapper = new ObjectMapper();

    public String getStudentSummary(String name, int age, String email) throws IOException, InterruptedException {

        Map<String, Object> payload = getStringObjectMap(name, age, email);

        String requestBody = mapper.writeValueAsString(payload);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode root = mapper.readTree(response.body());

        if (root.has("response")) {
            String raw = root.get("response").asText().trim();
            
            int start = raw.indexOf("{");
            int end = raw.lastIndexOf("}");

            if (start != -1 && end != -1 && end > start) {
                return raw.substring(start, end + 1);
            } else {
                return "{\"error\": \"Malformed response received from Llama3.\"}";
            }
        } else {
            return "{\"error\": \"No 'response' field in Llama output.\"}";
        }
    }

    private static Map<String, Object> getStringObjectMap(String name, int age, String email) {
        String prompt = String.format
                ("""
        You are an API backend and must respond with only valid JSON.
        Do not include any explanation, markdown, or code blocks.
        Just return the object in this format:
        {
          "name": "%s",
          "age": %d,
          "email": "%s"
        }
        """, name, age, email);

        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "llama3");
        payload.put("prompt", prompt);
        payload.put("stream", false);
        return payload;
    }
}