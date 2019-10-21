package com.codeoftheweb.salvovb;

import com.codeoftheweb.salvovb.model.*;
import com.codeoftheweb.salvovb.repository.GamePlayerRepository;
import com.codeoftheweb.salvovb.repository.GameRepository;
import com.codeoftheweb.salvovb.repository.PlayerRepository;
import com.codeoftheweb.salvovb.repository.ScoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Arrays;
import java.util.Date;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;

@SpringBootApplication
public class SalvovbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvovbApplication.class, args);
	}

@Bean
	public CommandLineRunner initData (PlayerRepository playerRepository, GameRepository gameRepository,
									   GamePlayerRepository gamePlayerRepository, ScoreRepository scoreRepository){

		return (args) -> {
			Player jack = playerRepository.save(new Player("Jack","Bauer", "j.bauer@ctu.gov"));
			Player chloe = playerRepository.save(new Player("Chloe","O'Brian", "c.obrian@ctu.gov"));
			Player kim = playerRepository.save(new Player("Kim","Bauer", "kim_bauer@gmail.com"));
			Player tony = playerRepository.save(new Player("Tony","Almeida", "t.almeida@ctu.gov"));

			Date date = new Date();
			Game game1 = gameRepository.save(new Game(date));
			Game game2 = gameRepository.save(new Game (Date.from(date.toInstant().plusSeconds(3600))));
			Game game3 = gameRepository.save(new Game (Date.from(date.toInstant().plusSeconds(7200))));
			Game game4 = gameRepository.save(new Game ());
			Game game5 = gameRepository.save(new Game ());

			GamePlayer gp1 = gamePlayerRepository.save(new GamePlayer(game1,jack));
			GamePlayer gp2 = gamePlayerRepository.save(new GamePlayer(game1,chloe));
			GamePlayer gp3 = gamePlayerRepository.save(new GamePlayer(game2,kim));
			GamePlayer gp4 = gamePlayerRepository.save(new GamePlayer(game2,tony));
			GamePlayer gp5 = gamePlayerRepository.save(new GamePlayer(game3,jack));

			gp1.addShip(new Ship("destoyer", Arrays.asList("A1","A2","A3")));
			gp1.addShip(new Ship("submarine", Arrays.asList("C1","C2","C3","C4")));

			gp2.addShip(new Ship("destoyer", Arrays.asList("H1","I1","J1")));
			gp2.addShip(new Ship("submarine", Arrays.asList("D4","D5","D6","D7")));

			gamePlayerRepository.save(gp1);
			gamePlayerRepository.save(gp2);

			scoreRepository.save(new Score(game1, jack, 0.5));
			scoreRepository.save(new Score(game1,chloe,0.5));

			scoreRepository.save(new Score(game2,kim,0));
			scoreRepository.save(new Score(game2,tony,1));

		};

}


}
