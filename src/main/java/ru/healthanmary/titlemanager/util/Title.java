package ru.healthanmary.titlemanager.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor @Getter
public class Title {
    public enum State {
        REJECTED("REJECTED"),
        ACCEPTED("ACCEPTED"),
        UNDER_REVIEW("UNDER_REVIEW");
        private final String state;

        State(String state) {
            this.state = state;
        }
    }
    private int id;
    private String titleText;
    private String playerName;
    private Timestamp requestDate;
    private Timestamp reviewtDate;
    private String reviewAdmin;
    private String adminComment;
    private State state;
}
