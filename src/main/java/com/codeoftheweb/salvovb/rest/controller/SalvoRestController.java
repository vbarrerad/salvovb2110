package com.codeoftheweb.salvovb.rest.controller;


import com.codeoftheweb.salvovb.model.Game;
import com.codeoftheweb.salvovb.model.GamePlayer;
import com.codeoftheweb.salvovb.repository.GamePlayerRepository;
import com.codeoftheweb.salvovb.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api")

public class SalvoRestController {
    @Autowired
    private GameService gameService;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;
    //reemplazarlo por gameServiceRepository

    @GetMapping ("/games")
    //reubicarlo en game controller
    public List<Game> findAll() {
        return gameService.findAll();
    }

    @GetMapping ("/ids")
    public List <Long> findAllIds (){
        return gameService.findAll().stream().map(game -> game.getId()).collect(Collectors.toList());
    }

    /*@GetMapping ("/mapa")
    private List <Map<String,Object>> mapa (){
        return gameService.findAll().stream().map(this::mapFromGameToDTO).collect(Collectors.toList());
    }


    private Map <String, Object> mapFromGameToDTO (Game game) {
        Map <String,Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("creation", game.getCreationDate());
        dto.put("gamePlayer", game.getGamePlayers());
        return dto;
    }*/

    @GetMapping ("/mapa")
    private List <Map<String,Object>> mapa (){
        return gameService.findAll().stream().map(this::mapFromGameToDTO).collect(Collectors.toList());
    }


    private Map <String, Object> mapFromGameToDTO (Game game) {
        Map <String,Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("creation", game.getCreationDate());
        dto.put("gamePlayer", mapGamePlayerToGamePlayerDTO(game.getGamePlayers()));
        return dto;
    }

    private List<Map<String, Object>> mapGamePlayerToGamePlayerDTO(Set<GamePlayer> gamePlayers) {
        return gamePlayers.stream().map(this::mapGamePlayerToGamePlayerDTO).collect(Collectors.toList());
    }

    private Map<String, Object> mapGamePlayerToGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", gamePlayer.getPlayer().playerDTO());
        return dto;
    }
}
