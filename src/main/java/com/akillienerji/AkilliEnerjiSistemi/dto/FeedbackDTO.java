package com.akillienerji.AkilliEnerjiSistemi.dto;

public class FeedbackDTO {
    private String username;
    private int rating;
    private String message;

    public FeedbackDTO() {
    }

    public FeedbackDTO(String username, int rating, String message) {
        this.username = username;
        this.rating = rating;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
