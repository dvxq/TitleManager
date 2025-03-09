package ru.healthanmary.titlemanager.mysql;

import ru.healthanmary.titlemanager.util.Title;

import java.util.ArrayList;

public interface Storage {
    // 1
    Title getTitleById(int id);
    // 2
    ArrayList<Title> arrayOfTitles(int id);
    //3
    Title getTitleByName(String name);
    //4
    int getPlayerPoints(String name);
    // я хз как работать с енумами  5-6
    void setPlayerPoints(String name, int points);
    void resetPlayerPoints(String name);
    void takePlayerPoints(String name, int points);
    ArrayList<Title> getPendingTitles();
}
