package ru.healthanmary.titlemanager.mysql;

import ru.healthanmary.titlemanager.util.Title;

import java.util.ArrayList;
import java.util.List;

public interface Storage {
    Title getTitleById(int titleId);
    List<Title> getArrayOfTitles(String playerName);
    Title getCurrentTitleByName(String playerName);
    String getPlayerPoints(String playerName);
    void setPlayerPoints(String playerName, int points);
    void resetPlayerPoints(String playerName);
    void takePlayerPoints(String playerName, int points);
    ArrayList<Title> getPendingTitles();
    boolean hasTitle(String playerName, int id);
    void setCurrentTitle(String playerName, Integer titleId);
}
