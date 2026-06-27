package com.karanmiglani.contextiq.Service.Implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karanmiglani.contextiq.DTO.GeminiResponse;
import com.karanmiglani.contextiq.DTO.WebpageContent;
import com.karanmiglani.contextiq.Service.GeminiService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String baseUrl;

    private final ObjectMapper objectMapper;

    @Override
    public GeminiResponse classify(WebpageContent content) {
        try {
            String prompt = """
                    Analyze this webpage.

                    Return ONLY JSON.

                    {
                        "category" : "",
                        "subCategory"  : "",
                        "keywords" : [],
                        "intent": ""
                    }

                    Title:
                     %s
                     Description:
                     %s
                     Content:
                     %s


                    """.formatted(content.getTitle(), content.getDescription(), content.getContent());
            
            RestClient client = RestClient.builder().baseUrl(baseUrl).build();
            Map<String, Object> body = new HashMap<>();
            body.put("contents",List.of(Map.of("parts", List.of(Map.of("text", prompt)))));
            body.put("generationConfig", Map.of("responseMimeType", "application/json"));
            String response = client.post()
                                                .uri("/v1beta/models/gemini-2.5-flash:generateContent")
                                                .header("x-goog-api-key", apiKey)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .body(body)
                                                .retrieve()
                                                .body(String.class);
            System.out.println(response);
            JsonNode root = objectMapper.readTree(response);
            String json = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
            GeminiResponse geminiResponse = objectMapper.readValue(json, GeminiResponse.class);
            return geminiResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
