package com.codeoftheweb.salvovb.model;

import javax.persistence.*;
import java.util.Date;

@Entity

public class GamePlayer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Long id;
    private Date joinDate;

    @ManyToOne
    @JoinColumn (name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn (name = "player_id")
    private Player player;


    public GamePlayer (){}

    public GamePlayer(Date joinDate, Game game, Player player) {
        this.joinDate = joinDate;
        this.game = game;
        this.player = player;
    }


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



    @Override
    public String toString() {
        return "GamePlayer{" +
                "id=" + id +
                ", joinDate=" + joinDate +
                ", game=" + game +
                ", player=" + player +
                '}';
    }
}
