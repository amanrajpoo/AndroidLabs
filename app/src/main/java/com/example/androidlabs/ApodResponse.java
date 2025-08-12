package com.example.nasaimageoftheday;

public class ApodResponse {
    private String title;
    private String explanation;
    private String url;

    public ApodResponse(String title, String explanation, String url) {
        this.title = title;
        this.explanation = explanation;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getUrl() {
        return url;
    }
}
