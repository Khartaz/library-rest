package com.library.config;

import java.sql.Timestamp;

public class Vote {

    private String candidate;
    private Timestamp timeCast;

    public Vote(String candidate, Timestamp timeCast) {
        this.candidate = candidate.toUpperCase();
        this.timeCast = timeCast;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate.toUpperCase();
    }

    public Timestamp getTimeCast() {
        return timeCast;
    }

    public void setTimeCast(Timestamp timeCast) {
        this.timeCast = timeCast;
    }

}