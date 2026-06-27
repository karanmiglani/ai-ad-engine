package com.karanmiglani.contextiq.Service;

import com.karanmiglani.contextiq.DTO.GeminiResponse;
import com.karanmiglani.contextiq.DTO.WebpageContent;

public interface GeminiService {

    GeminiResponse classify(WebpageContent content);
}
