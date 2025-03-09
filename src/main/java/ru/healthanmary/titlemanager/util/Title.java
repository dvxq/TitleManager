package ru.healthanmary.titlemanager.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor @Getter
public class Title {
    int id;
    String title_text;
    String player_name;
    Timestamp request_date;
    Timestamp accept_date;
    String accepted_admin;
    String admin_comment;
    boolean is_accepted;
}
