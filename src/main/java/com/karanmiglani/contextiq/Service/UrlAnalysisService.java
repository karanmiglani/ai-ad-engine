package com.karanmiglani.contextiq.Service;

import com.karanmiglani.contextiq.DTO.GeminiResponse;
import com.karanmiglani.contextiq.DTO.WebpageContent;

public interface UrlAnalysisService {

    public GeminiResponse saveContent(String url, WebpageContent content);
}
