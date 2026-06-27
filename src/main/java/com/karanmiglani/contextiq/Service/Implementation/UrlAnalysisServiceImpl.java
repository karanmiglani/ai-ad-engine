package com.karanmiglani.contextiq.Service.Implementation;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karanmiglani.contextiq.DTO.GeminiResponse;
import com.karanmiglani.contextiq.DTO.WebpageContent;
import com.karanmiglani.contextiq.Entity.UrlAnalysis;
import com.karanmiglani.contextiq.Repository.UrlAnalysisRepository;
import com.karanmiglani.contextiq.Service.GeminiService;
import com.karanmiglani.contextiq.Service.UrlAnalysisService;
import com.karanmiglani.contextiq.Utility.HashUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlAnalysisServiceImpl implements UrlAnalysisService {

    private final UrlAnalysisRepository analysisRepository;
    private final GeminiService geminiService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    @Override
    public GeminiResponse saveContent(String url, WebpageContent content) {

        try {
            String key = HashUtil.sha256(url);
            // check cache
            String cachedResponse = redisTemplate.opsForValue().get(key);
            System.out.println("Cached Response = " + cachedResponse);
            if(cachedResponse != null){
                log.info("Cache Hit : {}" , url);
                return objectMapper.readValue(cachedResponse, GeminiResponse.class);
            }
            log.info("CACHE MISS: {}" , url);
            GeminiResponse geminiResponse = geminiService.classify(content);
               UrlAnalysis analysis = UrlAnalysis.builder().url(url)
                                                                           .title(content.getTitle())
                                                                           .category(geminiResponse.getCategory())
                                                                           .subCategory(geminiResponse.getSubCategory())
                                                                           .keywords(new ObjectMapper().writeValueAsString(geminiResponse.getKeywords()))
                                                                           .intent(geminiResponse.getIntent())
                                                                           .createdAt(LocalDateTime.now())
                                                                           .build();
        analysisRepository.save(analysis);
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(geminiResponse), Duration.ofDays(7));
        return geminiResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
