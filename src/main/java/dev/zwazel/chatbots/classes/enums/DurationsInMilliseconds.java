package dev.zwazel.chatbots.classes.enums;

public enum DurationsInMilliseconds {
    MINUTE(60000),
    HOUR(3600000),
    DAY(86400000),
    WEEK(604800000),
    MONTH(2592000000L),
    YEAR(31536000000L);

    private final long duration;

    DurationsInMilliseconds(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }
}
