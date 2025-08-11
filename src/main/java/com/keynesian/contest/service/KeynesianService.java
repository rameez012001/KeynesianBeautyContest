package com.keynesian.contest.service;

import com.keynesian.contest.entity.Player;

import java.util.*;

public class KeynesianService {
    private Random random = new Random();
    Map<Integer, Player> players = new HashMap<>();

    public void initializePlayers(int numberOfPlayers) {
        players.clear();
        for (int i = 0; i < numberOfPlayers; i++) {
            int randomNumber = random.nextInt(101);
            players.put(i, new Player("Player" + i, 0, randomNumber));
        }
    }

    private int calculateAverageNumber() {
        int sum = players.values().stream()
                .mapToInt(Player::getNumber)
                .sum();
        return sum / players.size();
    }

    private int calculateTargetNumber(double targetFactor) {
        int average = calculateAverageNumber();
        return (int) (average * targetFactor);
    }

    public Player determineWinner(double targetFactor) {
        int targetNumber = calculateTargetNumber(targetFactor);

        if (players.size() == 2) {
            return determineWinnerForTwoPlayers();
        }

        players.entrySet().removeIf(player->player.getValue().getScore()<=-3);

        return players.entrySet().stream()
                .min(Comparator.comparingInt(e ->
                        Math.abs(e.getValue().getNumber() - targetNumber)))
                .map(entry -> {
                    entry.getValue().setScore(1);
                    return entry.getValue();
                })
                .orElse(null);
    }

    private Player determineWinnerForTwoPlayers() {
        List<Player> players = new ArrayList<>(this.players.values());
        Player p1 = players.get(0);
        Player p2 = players.get(1);

        if (p1.getNumber() == p2.getNumber()) {
            p1.setScore(p1.getScore() - 1);
            p2.setScore(p2.getScore() - 1);
            return null;
        }

        if (p1.getNumber() == 0 && p2.getNumber() == 100) {
            p2.setScore(p2.getScore() + 1);
            return p2;
        }
        if (p1.getNumber() == 100 && p2.getNumber() == 0) {
            p1.setScore(p1.getScore() + 1);
            return p1;
        }
        if (p1.getNumber() == 1 && p2.getNumber() == 100) {
            p1.setScore(p1.getScore() + 1);
            return p1;
        }
        if (p1.getNumber() == 100 && p2.getNumber() == 1) {
            p2.setScore(p2.getScore() + 1);
            return p2;
        }
        if (p1.getNumber() == 0 && p2.getNumber() == 1) {
            p1.setScore(p1.getScore() + 1);
            return p1;
        }
        if (p1.getNumber() == 1 && p2.getNumber() == 0) {
            p2.setScore(p2.getScore() + 1);
            return p2;
        }

        return null;
    }

}
