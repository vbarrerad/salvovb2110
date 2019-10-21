package com.codeoftheweb.salvovb.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Ship {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "gamePlayer_id")
    private GamePlayer gamePlayer;
    @ElementCollection
    private List <String> locations = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GamePlayer getGamePlayer() {
        return this.gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getLocations() {
        return this.locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }


    public Ship(){

    }

    public Ship(String type, List<String> locations) {
        this.type = type;
        this.locations = locations;
    }

    public Map<String, Object> shipDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("type", this.getType());
        dto.put("locations", this.getLocations());
        return dto;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", gamePlayer=" + gamePlayer +
                ", locations=" + locations +
                '}';
    }
}
