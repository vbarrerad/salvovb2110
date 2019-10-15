package com.codeoftheweb.salvovb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Game {


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    private Date creationDate = new Date();


    @OneToMany (mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set <GamePlayer> gamePlayers = new HashSet<>();


    public Game () {
    }

    public Game(Date creationDate)
    {
        this.creationDate = creationDate;
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


    public List <Player> getPlayers (){
        return this.gamePlayers.stream().map(gp -> gp.getPlayer()).collect(Collectors.toList());
    }
//en el codigo de Rodrigo el DTO de Game esta aca, el mio esta en SalvoRestController, deberia estar en GameRestController



    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", gamePlayers=" + gamePlayers +
                '}';
    }


}