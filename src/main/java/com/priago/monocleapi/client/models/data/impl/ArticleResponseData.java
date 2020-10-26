package com.priago.monocleapi.client.models.data.impl;

import com.priago.monocleapi.client.models.data.MonocleResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A data class modelling an article from NewsAPI
 *
 * <a href="https://newsapi.org/docs">NewsAPI Documentation</a>
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseData implements MonocleResponseData {

    @Getter
    private SourceResponseData source;

    @Getter
    private String author;

    @Getter
    private String title;

    @Getter
    private String description;

    @Getter
    private String url;

    @Getter
    private String urlToImage;

    @Getter
    private String publishedAt;

    @Getter
    private String content;
}
