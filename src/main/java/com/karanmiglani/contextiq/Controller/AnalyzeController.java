package com.karanmiglani.contextiq.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karanmiglani.contextiq.DTO.AnalyzeRequest;
import com.karanmiglani.contextiq.DTO.GeminiResponse;
import com.karanmiglani.contextiq.DTO.WebpageContent;
import com.karanmiglani.contextiq.Service.ContentExctractorService;
import com.karanmiglani.contextiq.Service.GeminiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AnalyzeController {

    private final ContentExctractorService contentExctractorService;
    private final GeminiService  geminiService;

    @PostMapping("/analyze")
    public GeminiResponse  analyze(@RequestBody AnalyzeRequest request){
        WebpageContent content =  contentExctractorService.extract(request.getUrl());
        return geminiService.classify(content);
    }
}
