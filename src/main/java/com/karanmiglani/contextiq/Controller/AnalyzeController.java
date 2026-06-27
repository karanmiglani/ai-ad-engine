package com.karanmiglani.contextiq.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karanmiglani.contextiq.DTO.AnalyzeRequest;
import com.karanmiglani.contextiq.DTO.GeminiResponse;
import com.karanmiglani.contextiq.DTO.WebpageContent;
import com.karanmiglani.contextiq.Service.ContentExctractorService;
import com.karanmiglani.contextiq.Service.GeminiService;
import com.karanmiglani.contextiq.Service.UrlAnalysisService;
import com.karanmiglani.contextiq.Utility.HashUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AnalyzeController {

    private final ContentExctractorService contentExctractorService;
    private final UrlAnalysisService analysisService;

    @PostMapping("/analyze")
    public GeminiResponse  analyze(@RequestBody AnalyzeRequest request){
        WebpageContent content =  contentExctractorService.extract(request.getUrl());
        GeminiResponse geminiResponse = analysisService.saveContent(request.getUrl(), content);
        return geminiResponse;
    }
}
