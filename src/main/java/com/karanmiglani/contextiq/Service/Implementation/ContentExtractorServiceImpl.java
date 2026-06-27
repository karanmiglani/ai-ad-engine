package com.karanmiglani.contextiq.Service.Implementation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.karanmiglani.contextiq.DTO.WebpageContent;
import com.karanmiglani.contextiq.Service.ContentExctractorService;

@Service
public class ContentExtractorServiceImpl implements ContentExctractorService {

    @Override
    public WebpageContent extract(String url) {
        try {
            Document document = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
            String title = document.title();
            Element meta = document.selectFirst("meta[name=description]");
            String description = meta != null ? meta.attr("content") : "";
            String content = document.body().text();
            if(content.length() > 5000)
                content = content.substring(0,5000);
            
            return WebpageContent.builder().title(title).description(description).Content(content).build();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
