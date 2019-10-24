package com.codeoftheweb.salvovb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Game {


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Date creationDate = new Date();


    @OneToMany (mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set <GamePlayer> gamePlayers = new HashSet<>();

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER, cascade= CascadeType.ALL)
    private Set<Score> scores = new HashSet<>();



    public Game () {
    }

    public Game(Date creationDate)
    {
        this.creationDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }


    //método para establecer la relación entre un objeto Game y un objeto GamePlayer
    public void addGamePlayer(GamePlayer gamePlayer) {
        //se agrega el gamePlayer que ingresa como parámetro al set declarado en los atributos
        this.gamePlayers.add(gamePlayer);
        //al gamePlayer ingresado se le agrega este game mediante su setter en la clase GamePlayer
        gamePlayer.setGame(this);
    }

    //método que retorna todos los players relacionados con el game a partir de los gamePlayers
    public List<Player> getPlayers() {
        return this.gamePlayers.stream().map(gp -> gp.getPlayer()).collect(Collectors.toList());
    }

    //DTO (data transfer object) para administrar la info de Game
    public Map<String, Object> gameDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("created", this.getCreationDate());
        dto.put("gamePlayers", this.getGamePlayers().stream().map(GamePlayer::gamePlayerDTO).collect(Collectors.toList()));
        return dto;
    }



    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", gamePlayers=" + gamePlayers +
                '}';
    }


}