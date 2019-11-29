package com.codeoftheweb.salvovb;

import com.codeoftheweb.salvovb.model.*;
import com.codeoftheweb.salvovb.repository.GamePlayerRepository;
import com.codeoftheweb.salvovb.repository.GameRepository;
import com.codeoftheweb.salvovb.repository.PlayerRepository;
import com.codeoftheweb.salvovb.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;

@SpringBootApplication
public class SalvovbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvovbApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

@Bean
	public CommandLineRunner initData (PlayerRepository playerRepository, GameRepository gameRepository,
									   GamePlayerRepository gamePlayerRepository, ScoreRepository scoreRepository){

		return (args) -> {
			Player jack = playerRepository.save(new Player("Jack","Bauer", "j.bauer@ctu.gov", "$2a$10$pSMyxs/5EEU3CCOwUC.RTuDfUL8kSiryHXOqj0m8H./IW9XrNOtlG"));
			Player chloe = playerRepository.save(new Player("Chloe","O'Brian", "c.obrian@ctu.gov", "$2a$10$pSMyxs/5EEU3CCOwUC.RTuDfUL8kSiryHXOqj0m8H./IW9XrNOtlG" ));
			Player kim = playerRepository.save(new Player("Kim","Bauer", "kim_bauer@gmail.com", "$2a$10$pSMyxs/5EEU3CCOwUC.RTuDfUL8kSiryHXOqj0m8H./IW9XrNOtlG"));
			Player tony = playerRepository.save(new Player("Tony","Almeida", "t.almeida@ctu.gov", "$2a$10$pSMyxs/5EEU3CCOwUC.RTuDfUL8kSiryHXOqj0m8H./IW9XrNOtlG"));

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

			gp1.addSalvo(new Salvo(1,Arrays.asList("B5","C6")));
			gp1.addSalvo(new Salvo(2,Arrays.asList("J1","D9")));

			gp2.addSalvo(new Salvo(1,Arrays.asList("A1","F6")));
			gp2.addSalvo(new Salvo(2,Arrays.asList("A2","A3")));


			gamePlayerRepository.save(gp1);
			gamePlayerRepository.save(gp2);

			scoreRepository.save(new Score(game1, jack, 0.5));
			scoreRepository.save(new Score(game1,chloe,0.5));

			scoreRepository.save(new Score(game2,kim,0));
			scoreRepository.save(new Score(game2,tony,1));

		};

}

}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = playerRepository.findByUserName(inputName);
			if (player != null) {
				return new User(player.getUserName(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("rest/**").hasAuthority("ADMIN")
				.antMatchers("/api/game_view/**").hasAnyAuthority("USER","ADMIN");
		http.formLogin()
				.usernameParameter("username")
				.passwordParameter("password")
				.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}

	}
}