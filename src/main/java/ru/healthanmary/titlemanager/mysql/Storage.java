package ru.healthanmary.titlemanager.mysql;

import ru.healthanmary.titlemanager.util.Title;

import java.util.ArrayList;

public interface Storage {
    Title getTitleById(int id);
    ArrayList<Title> getArrayOfTitles(String name);
    Title getCurrentTitleByName(String name);
    String getPlayerPoints(String name);
    void setPlayerPoints(String name, int points);
    void resetPlayerPoints(String name);
    void takePlayerPoints(String name, int points);
    ArrayList<Title> getPendingTitles();
    void setCurrentTitle(String name, Integer id);
}
