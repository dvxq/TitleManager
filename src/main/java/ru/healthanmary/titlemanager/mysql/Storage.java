package ru.healthanmary.titlemanager.mysql;

import ru.healthanmary.titlemanager.util.Title;

import java.util.ArrayList;

public interface Storage {
    // 1
    Title getTitleById(int id);
    // 2
    ArrayList<Title> arrayOfTitles(int id);
    //3
    Title getCurrentTitleByName(String name);
    //4
    String getPlayerPoints(String name);
    // я хз как работать с енумами  5-6
    void setPlayerPoints(String name, int points);
    void resetPlayerPoints(String name);
    void takePlayerPoints(String name, int points);
    ArrayList<Title> getPendingTitles();
    void setCurrentTitle(String name, Integer id);
}
