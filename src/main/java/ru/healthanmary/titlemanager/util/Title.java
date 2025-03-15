package ru.healthanmary.titlemanager.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor @Getter
public class Title {
    private int id;
    private String title_text;
    private String player_name;
    private Timestamp request_date;
    private Timestamp accept_date;
    private String accepted_admin;
    private String admin_comment;
    private boolean is_accepted;
}
