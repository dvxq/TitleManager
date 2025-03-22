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
    private String title_text;
    private String player_name;
    private Timestamp request_date;
    private Timestamp accept_date;
    private String accepted_admin;
    private String admin_comment;
    private State state;
}
