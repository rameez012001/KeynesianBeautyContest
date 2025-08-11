package com.keynesian.contest.entity;

public class Player {
    private String name;
    private int score;
    private int number;

    public Player(String name, int score, int number) {
        this.name = name;
        this.score = score;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
