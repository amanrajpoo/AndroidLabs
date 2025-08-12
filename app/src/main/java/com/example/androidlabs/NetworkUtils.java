package com.example.nasaimageoftheday;

// Import your networking library (OkHttp, HttpURLConnection, etc.)

public class NetworkUtils {

    public static ApodResponse fetchApodData(String date) {
        // Make HTTP request to NASA APOD API and parse result.
        // Example static response (for demo):
        // TODO: Replace this with actual network code and JSON parsing.
        return new ApodResponse(
                "Astronomy Picture Title",
                "Sample explanation for APOD.",
                "https://apod.nasa.gov/apod/image/sample.jpg"
        );
    }
}
