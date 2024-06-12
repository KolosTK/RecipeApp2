package org.example.model;

public enum Rating {
    POOR("Poor"),
    AVERAGE("Average"),
    GOOD("Good"),
    VERY_GOOD("Very Good"),
    EXCELLENT("Excellent");

    private final String description;

    Rating(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
}
