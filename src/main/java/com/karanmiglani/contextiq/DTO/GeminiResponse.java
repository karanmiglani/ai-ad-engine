package com.karanmiglani.contextiq.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeminiResponse {

    private String category;
    private String subCategory;
    private List<String> keywords;
    private String intent;
}
