package com.keynesian.contest.controller;

import com.keynesian.contest.entity.Game;
import com.keynesian.contest.service.GameService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.util.Map;

public class GameWebSocketController {
    private GameService gameService;

    public GameWebSocketController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("game/{gameId}/choose")
    public void chooseNumber(@DestinationVariable Long gameId, Map<String, Object> payload){
        String players = (String) payload.get("player");
        Integer number = (Integer) payload.get("number");

        Game game = gameService.findGameById(gameId);
        game.getPlayers().stream().filter(player -> player.getName().equals(players))
                .findFirst()
                .ifPresent(p->p.setNumber(number));
    }
}
