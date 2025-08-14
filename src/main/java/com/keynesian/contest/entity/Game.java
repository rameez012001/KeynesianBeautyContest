package com.keynesian.contest.entity;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Long id;
    private List<Player> players;
    private GameStatus status;

    public Game(Long id, List<Player> players, GameStatus status) {
        this.id = id;
        this.players = players;
        this.status = status;
    }

    public void addPlayer(Player player) {
        if (!players.contains(player) && players.size() < 5) {
            players.add(player);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}

