package com.karanmiglani.contextiq.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karanmiglani.contextiq.DTO.AnalyzeRequest;

@RestController
@RequestMapping("/api/v1")
public class AnalyzeController {

    @PostMapping("/analyze")
    public String analyze(@RequestBody AnalyzeRequest request){
        return request.getUrl();
    }
}
