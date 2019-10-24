package com.codeoftheweb.salvovb.model;

import javax.persistence.*;
import java.util.*;

@Entity

public class GamePlayer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Date joinDate;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "game_id")
    private Game game;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "player_id")
    private Player player;

    @OneToMany(mappedBy="gamePlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Ship> ships= new HashSet<>();

    @OneToMany(mappedBy="gamePlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Salvo> salvoes= new HashSet<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void addShip (Ship ship) {
        this.ships.add(ship);
        ship.setGamePlayer(this);
    }

    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

    public void addSalvo (Salvo salvo) {
        this.salvoes.add(salvo);
        salvo.setGamePlayer(this);
    }


    public GamePlayer (){}


    public GamePlayer(Date joinDate) {
        this.joinDate = joinDate;
    }

    public GamePlayer(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.joinDate = new Date();
    }


    //DTO (data transfer object) para administrar la info de GamePlayer
    public Map<String, Object> gamePlayerDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("player", this.getPlayer().playerDTO());
        return dto;
    }

    @Override
    public String toString() {
        return "GamePlayer{" +
                "id=" + id +
                ", joinDate=" + joinDate +
                ", game=" + game +
                ", player=" + player +
                ", ships=" + ships +
                '}';
    }

}
