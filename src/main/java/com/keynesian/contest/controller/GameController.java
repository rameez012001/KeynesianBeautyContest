package com.keynesian.contest.controller;

import com.keynesian.contest.entity.Game;
import com.keynesian.contest.entity.Player;
import com.keynesian.contest.service.GameService;
import com.keynesian.contest.service.KeynesianService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
public class GameController {
    KeynesianService keynesianService;

    GameService gameService;

    public GameController(KeynesianService keynesianService, GameService gameService) {
        this.keynesianService = keynesianService;
        this.gameService = gameService;
    }

    @GetMapping
    private String createGame() {
        return "index";
    }

    // this will get the name
    // perform post on whether to create or find the game
    @PostMapping
    private String gameLobby(@RequestParam String name, Model model) {
        Player player = new Player(name);
        Game game = gameService.createOrFindGame(player);
        model.addAttribute("game", game);
        model.addAttribute("player",player);
        return "gameWindow";
    }

    //this will throw the game id to enter the game window html
    @GetMapping("/{gameId}")
    private String enterGame(@PathVariable Long gameId, Model model) {
        Game game = gameService.findGameById(gameId);
        model.addAttribute("game",game);
        return "gameWindow";
    }
}