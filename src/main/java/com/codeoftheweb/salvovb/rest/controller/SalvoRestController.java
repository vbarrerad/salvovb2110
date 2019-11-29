package com.codeoftheweb.salvovb.rest.controller;


import com.codeoftheweb.salvovb.model.*;
import com.codeoftheweb.salvovb.repository.GamePlayerRepository;
import com.codeoftheweb.salvovb.repository.GameRepository;
import com.codeoftheweb.salvovb.repository.PlayerRepository;
import com.codeoftheweb.salvovb.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;



    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String userName, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByUserName(userName) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(firstName, lastName, userName, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

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

   /* @RequestMapping("/game_view/{gamePlayerId}")
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
*/



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
            dto.put("ships", gamePlayer.getShips().stream().map(Ship::shipDTO));
            dto.put("salvoes", gamePlayer.getGame().getGamePlayers()
                    .stream().flatMap(gp -> gp.getSalvoes()
                            .stream().map(salvo -> salvo.salvoDTO()))
            );
        }else{
            dto.put("error", "no such game");
        }

        return dto;

    }

}