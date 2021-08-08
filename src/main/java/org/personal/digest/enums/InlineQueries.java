package org.personal.digest.enums;

public enum InlineQueries {
    PING("ping"),
    WEATHER("weather") {},
    NEWS("news") {};
    private final String query;

    InlineQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
