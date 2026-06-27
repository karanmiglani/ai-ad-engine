package com.karanmiglani.contextiq.Service;

import com.karanmiglani.contextiq.DTO.WebpageContent;

public interface ContentExctractorService {
    WebpageContent extract(String url);
}
