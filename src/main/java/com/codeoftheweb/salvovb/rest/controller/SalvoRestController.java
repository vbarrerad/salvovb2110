package com.codeoftheweb.salvovb.rest.controller;


import com.codeoftheweb.salvovb.model.Game;
import com.codeoftheweb.salvovb.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api")

public class SalvoRestController {
    @Autowired
    private GameService gameService;

    @GetMapping ("/games")
    /*public List<Game> findAll() {
        return gameService.findAll();
    }

    @GetMapping ("/ids")
    public List <Long> findAllIds (){
        return gameService.findAll().stream().map(game -> game.getId()).collect(Collectors.toList());
    }

    @GetMapping ("/mapa")*/
    private List <Map<String,Object>> mapa (){
        return gameService.findAll().stream().map(this::mapFromGameToDTO).collect(Collectors.toList());
    }


    private Map <String, Object> mapFromGameToDTO (Game game) {
        Map <String,Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("creation", game.getCreationDate());
        dto.put("gamePlayer", game.getGamePlayers());
        return dto;
    }
}
