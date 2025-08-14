package com.keynesian.contest.service;

import com.keynesian.contest.entity.Game;
import com.keynesian.contest.entity.GameStatus;
import com.keynesian.contest.entity.Player;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GameService {
    private List<Game> games = new ArrayList<>();
    private final AtomicLong gameId = new AtomicLong(1);
    private final SimpMessagingTemplate messagingTemplate;

    public GameService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public Game createOrFindGame(Player player) {
        Optional<Game> existingGame = games.stream().filter(game -> game.getPlayers().size() < 5 && game.getStatus()== GameStatus.WAITING).findFirst();

        Game game;

        if (existingGame.isPresent()) {
            game = existingGame.get();
            game.addPlayer(player);
        }else{
            game = new Game(
                    gameId.getAndIncrement(),
                    new ArrayList<>(Collections.singletonList(player)),
                    GameStatus.WAITING
            );
            games.add(game);
        }

        messagingTemplate.convertAndSend("/topic/game/" + game.getId(), game);

        if (game.getPlayers().size() == 5) {
            game.setStatus(GameStatus.STARTED);
            messagingTemplate.convertAndSend("/topic/game/" + game.getId(), game);
        }

        return game;
    }

    public Game findGameById(Long id){
        return games.stream().filter(game -> game.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new RuntimeException("No Game Found"));
    }
}