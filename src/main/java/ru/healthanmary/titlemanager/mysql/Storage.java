package ru.healthanmary.titlemanager.mysql;

import ru.healthanmary.titlemanager.util.Title;

import java.util.ArrayList;
import java.util.List;

public interface Storage {
    Title getTitleById(int titleId);
    List<Title> getArrayOfTitles(String playerName);
    Title getCurrentTitleByName(String playerName);
    String getPlayerPoints(String playerName);
    void setPlayerPoints(String playerName, Integer points);
    void resetPlayerPoints(String playerName);
    void takePlayerPoints(String playerName, int points);
    void givePlayerPoints(String playerName, int points);
    boolean hasTitle(String playerName, int id);
    void setCurrentTitle(String playerName, Integer titleId);
    void sendTitleToReview(String playerName, String title);
    void changeTitleState(Title.State state, int titleId);
    List<Title> getReviewTitles();
    Title.State getTitleState(int titleId);
    void reviewTitle(int titleId, String adminName, String adminComment, boolean isAccepted);
}
