package com.codeoftheweb.salvovb.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Salvo {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private GamePlayer gamePlayer;
    private int turn;
    @ElementCollection
    private List<String> locations = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public Salvo (){}

    public Salvo(int turn, List<String> locations) {
        this.turn = turn;
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "Salvo{" +
                "id=" + id +
                ", gamePlayer=" + gamePlayer +
                ", turn=" + turn +
                ", locations=" + locations +
                '}';
    }

    public Map<String, Object> salvoDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", this.getTurn());
        dto.put("player", this.getGamePlayer().getPlayer().getId());
        dto.put("locations", this.getLocations());
        return dto;
    }
}
