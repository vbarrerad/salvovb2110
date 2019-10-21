package com.codeoftheweb.salvovb.rest.controller;


import com.codeoftheweb.salvovb.model.Game;
import com.codeoftheweb.salvovb.model.GamePlayer;
import com.codeoftheweb.salvovb.model.Salvo;
import com.codeoftheweb.salvovb.model.Ship;
import com.codeoftheweb.salvovb.repository.GamePlayerRepository;
import com.codeoftheweb.salvovb.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    //reemplazarlo por gamePlayerService


    @GetMapping ("/ids")
    public List <Long> findAllIds (){
        return gameService.findAll().stream().map(game -> game.getId()).collect(Collectors.toList());
    }


    @GetMapping ("/games")
    private List <Map<String,Object>> games (){
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

    @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String,Object> getGameView(@PathVariable long gamePlayerId){
        return this.gameViewDTO(gamePlayerRepository.findById(gamePlayerId).orElse(null));
    }

    private Map<String,Object> gameViewDTO(GamePlayer gamePlayer){
        Map<String,Object> dto = new LinkedHashMap<>();

        if(gamePlayer != null){
            dto.put("id", gamePlayer.getGame().getId());
            dto.put("creationDate", gamePlayer.getGame().getCreationDate());

            dto.put("gamePlayer", gamePlayer.getGame().getGamePlayers().stream().map(GamePlayer::gamePlayerDTO));
            dto.put("player", gamePlayer.getPlayer().getUserName());
            dto.put("ships", gamePlayer.getShips().stream().map(Ship::shipDTO));

        }else{
            dto.put("error", "no such game");
        }

        return dto;
    }


}
