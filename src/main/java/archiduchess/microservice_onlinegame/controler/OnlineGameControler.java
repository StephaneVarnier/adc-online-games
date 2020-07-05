package archiduchess.microservice_onlinegame.controler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import archiduchess.microservice_onlinegame.configuration.ApplicationPropertiesConfiguration;
import archiduchess.microservice_onlinegame.modele.FenStat;
import archiduchess.microservice_onlinegame.modele.OnlineGame;
import archiduchess.microservice_onlinegame.repository.OnlineGameRepository;
import chesspresso.game.Game;
import chesspresso.pgn.PGNReader;
import chesspresso.pgn.PGNSyntaxError;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/archiduchess")
public class OnlineGameControler {

	@Autowired
	private OnlineGameRepository onlineGameRepo;

	@Autowired
	ApplicationPropertiesConfiguration appProperties;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@ApiOperation(value = "Liste toutes les parties en base.")
	@GetMapping(path = "/onlineGames")
	public @ResponseBody Iterable<OnlineGame> getAllOnlineGames() {

		Iterable<OnlineGame> gamesIterable = onlineGameRepo.findAll();
		return limitList(gamesIterable);
	}

	@ApiOperation(value = "Recherche une partie par id")
	@GetMapping(path = "/onlineGames/{id}")
	public @ResponseBody Optional<OnlineGame> getGameById(@PathVariable String id) throws Exception {
		//log.info("<----------------- " + id + " -------------->");
		Optional<OnlineGame> og = onlineGameRepo.findById(id);

		if (!og.isPresent())
			throw new Exception("There is no game " + id + " !");

		return og;
	}

	@ApiOperation(value = "Recherche les ouvertures jouées par un utilisateur donné")
	@GetMapping(path = "/onlineGames/openings/{username}")
	public @ResponseBody Set<String> getOpeningsByUser(@PathVariable String username) throws Exception {

		List<String> list = StreamSupport.stream(getOnlineGamesByUsername(username).spliterator(), false)
				.map(e -> e.getOpening()).sorted().collect(Collectors.toList());

		Set<String> tSet = new TreeSet<String>(list);

		return tSet;
	}

	@ApiOperation(value = "Recherche les positions d'une partie par id")
	@GetMapping(path = "/onlineGames/{id}/fens")
	public @ResponseBody List<String> getFensById(@PathVariable String id) throws PGNSyntaxError, IOException {

		Optional<OnlineGame> onlineGame = onlineGameRepo.findById(id);
		List<String> fens = new ArrayList<>();

		if (onlineGame.isPresent()) {
			Game game = this.parseOnlineGame(onlineGame.get());

			game.gotoStart();
			do {
				fens.add(game.getPosition().getFEN());
				game.goForward();
			} while (game.hasNextMove());

			fens.add(game.getPosition().getFEN());
		}

		return fens;
	}

	@ApiOperation(value = "Recherche les coups d'une partie par id sous format LAN - long annotation")
	@GetMapping(path = "/onlineGames/{id}/lans")
	public @ResponseBody List<String> getLansById(@PathVariable String id) throws PGNSyntaxError, IOException {
		Optional<OnlineGame> onlineGame = onlineGameRepo.findById(id);
		List<String> moves = new ArrayList<>();

		if (onlineGame.isPresent()) {
			Game game = this.parseOnlineGame(onlineGame.get());

			game.gotoStart();
			do {
				moves.add(game.getNextMove().getLAN());
				game.goForward();
			} while (game.hasNextMove());
		}

		// moves.add(game.getPosition().getFEN());
		return moves;
	}

	@ApiOperation(value = "Recherche les coups d'une partie par id sous format SAN - short annotation")
	@GetMapping(path = "/onlineGames/{id}/sans")
	public @ResponseBody List<String> getSansById(@PathVariable String id) throws PGNSyntaxError, IOException {
		Optional<OnlineGame> onlineGame = onlineGameRepo.findById(id);
		List<String> moves = new ArrayList<>();

		if (onlineGame.isPresent()) {
			Game game = this.parseOnlineGame(onlineGame.get());

			game.gotoStart();
			while (game.hasNextMove()) {
				moves.add(game.getNextMove().getSAN());
				game.goForward();
			}
			;
		}

		// moves.add(game.getPosition().getFEN());
		return moves;
	}

	@ApiOperation(value = "Liste les parties d'un joueur donné avec les blancs")
	@GetMapping(path = "onlineGames/white/{username}")
	public @ResponseBody Iterable<OnlineGame> getOnlineGamesByWhiteUsername(@PathVariable String username) {

		Iterable<OnlineGame> gamesIterable = onlineGameRepo.findByPlayerWhite(username);
		return limitList(gamesIterable);
	}

	@ApiOperation(value = "Liste les parties d'un joueur donné avec les noirs")
	@GetMapping(path = "onlineGames/black/{username}")
	public @ResponseBody Iterable<OnlineGame> getOnlineGamesByBlackUsername(@PathVariable String username) {

		Iterable<OnlineGame> gamesIterable = onlineGameRepo.findByPlayerBlack(username);
		return limitList(gamesIterable);
	}

	@ApiOperation(value = "Liste les parties d'un joueur donné")
	@GetMapping(path = "onlineGames/user/{username}")
	public @ResponseBody Iterable<OnlineGame> getOnlineGamesByUsername(@PathVariable String username) {

		Iterable<OnlineGame> gamesIterable = onlineGameRepo.findByPlayerBlackOrPlayerWhite(username, username);
		return limitList(gamesIterable);
	}

	@ApiOperation(value = "Liste les parties d'un joueur selon une ouverture donnée ")
	@GetMapping(path = "onlineGames/user/{username}/opening/{opening}")
	public @ResponseBody List<OnlineGame> getOnlineGamesByUsernameAndOpening(@PathVariable String username,
			@PathVariable String opening) {

		return StreamSupport.stream(getOnlineGamesByUsername(username).spliterator(), false)
				.filter(e -> e.getOpening().contains(opening)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Liste les parties d'un joueur donné selon la couleur et le résultat final")
	@GetMapping(path = "onlineGames/{color}/{username}/{resultat}")
	public @ResponseBody Iterable<OnlineGame> getGamesByUsernameColorResult(@PathVariable String color,
			@PathVariable String username, @PathVariable String resultat) {

		Iterable<OnlineGame> gamesIterable;
		if (color.equals("black")) {
			gamesIterable = onlineGameRepo.findByPlayerBlackAndResultat(username, resultat);
		} else {
			gamesIterable = onlineGameRepo.findByPlayerWhiteAndResultat(username, resultat);
		}
		return limitList(gamesIterable);
	}

	@ApiOperation(value = "Liste les parties ayant atteint une position donnée sous format FEN")
	@RequestMapping(value = "onlineGames/fen/**", method = RequestMethod.GET)
	public @ResponseBody List<OnlineGame> getGamesByFen(HttpServletRequest request) throws PGNSyntaxError, IOException {

		String requestURL = request.getRequestURL().toString();
		String fenURL = requestURL.split("onlineGames/fen/")[1];
		String fen = java.net.URLDecoder.decode(fenURL, StandardCharsets.UTF_8.name());
		//log.info("FEN -------------------------> " + fen);

		return getGamesbyFen(fen);
	}

	public List<OnlineGame> getGamesbyFen(String fen) throws PGNSyntaxError, IOException {
		Iterable<OnlineGame> gamesIterable = onlineGameRepo.findAll();

		List<OnlineGame> filteredGames = new ArrayList<>();

		for (OnlineGame onlineGame : gamesIterable) {
			// log.info(onlineGame.getId() + " " + onlineGame.getPlayerWhite() + " - " +
			// onlineGame.getPlayerBlack());
			try {
				if (contains(onlineGame, fen))
					filteredGames.add(onlineGame);
			} catch (RuntimeException error) {
				//log.info(error.getMessage());
			}
			;

		}
		return filteredGames;
	}

	@ApiOperation(value = "Liste les parties ayant atteint une position donnée sous format FEN")
	@RequestMapping(value = "onlineGames/user/**/fen/**", method = RequestMethod.GET)
	public @ResponseBody List<OnlineGame> getGamesByFenAndUsername(HttpServletRequest request)
			throws PGNSyntaxError, IOException {

		String requestURL = request.getRequestURL().toString();
		String fenURL = requestURL.split("/fen/")[1];
		String user = requestURL.split("/fen/")[0].split("user/")[1];
		String fen = java.net.URLDecoder.decode(fenURL, StandardCharsets.UTF_8.name());
		//log.info("FEN -------------------------> " + fen);
		//log.info("User -------------------------> " + user);

		return getGamesbyFenAndUsername(fen, user);
	}
	
	@ApiOperation(value = "Donne le coup joué dans une partie et une position donnée sous format FEN")
	@RequestMapping(value = "onlineGames/nextMove/id/**/fen/**", method = RequestMethod.GET)
	public @ResponseBody String getNextMoveByFenAndId(HttpServletRequest request)
			throws PGNSyntaxError, IOException {

		String requestURL = request.getRequestURL().toString();
		String fenURL = requestURL.split("/fen/")[1];
		String id = requestURL.split("/fen/")[0].split("id/")[1];
		String fen = java.net.URLDecoder.decode(fenURL, StandardCharsets.UTF_8.name());
		//log.info("FEN -------------------------> " + fen);
		//log.info("id -------------------------> " + id);

		return getNextMoveByFenAndId(fen, id);
	}
	
	public String getNextMoveByFenAndId(String fen, String id) throws PGNSyntaxError, IOException {
		
		int moveNumber = getFensById(id).indexOf(fen);
		return getSansById(id).get(moveNumber);
	}
	

	@ApiOperation(value = "Liste les coups joués par un joueur donné dans les parties ayant atteint une position donnée sous format FEN")
	@RequestMapping(value = "onlineGames/nextMoves/user/**/fen/**", method = RequestMethod.GET)
	public @ResponseBody List<String> getNextMovesByFenAndUsername(HttpServletRequest request)
			throws PGNSyntaxError, IOException {

		String requestURL = request.getRequestURL().toString();
		String fenURL = requestURL.split("/fen/")[1];
		String user = requestURL.split("/fen/")[0].split("user/")[1];
		String fen = java.net.URLDecoder.decode(fenURL, StandardCharsets.UTF_8.name());

		List<String> nextMoves = new ArrayList<String>();
		List<OnlineGame> games = getGamesbyFenAndUsername(fen, user);
		for (OnlineGame game : games) {
			int moveNumber = getMoveNumber(game, fen);
			if (moveNumber >= 0) {
				//log.info("id --> " + game.getId());
				//log.info(getSansById(game.getId()).toString());
				if (getSansById(game.getId()).size() > moveNumber) 
				{
					nextMoves.add(getSansById(game.getId()).get(moveNumber));
				}
			}
		}
		return nextMoves;
	}

	public List<OnlineGame> getGamesbyFenAndUsername(String fen, String username) throws PGNSyntaxError, IOException {
		Iterable<OnlineGame> gamesIterable = getOnlineGamesByUsername(username);

		List<OnlineGame> filteredGames = new ArrayList<>();

		for (OnlineGame onlineGame : gamesIterable) {
			try {
				// log.info(onlineGame.getId());
				// log.info(""+contains(onlineGame, fen));
				if (contains(onlineGame, fen)) {
					filteredGames.add(onlineGame);

				}
			} catch (RuntimeException error) {
				log.info(error.getMessage());
			}
			;

		}
		return filteredGames;
	}

	@ApiOperation(value = "Liste les parties ayant atteint une position donnée sous format FEN, selon un résultat")
	@RequestMapping(value = "onlineGames/{resultat}/fen/**", method = RequestMethod.GET)
	public @ResponseBody List<OnlineGame> getGamesByFenAndResultat(HttpServletRequest request)
			throws PGNSyntaxError, IOException {

		String requestURL = request.getRequestURL().toString();
		String fenURL0 = requestURL.split("/fen/")[0];
		String fenURL1 = requestURL.split("/fen/")[1];

		String res = fenURL0.split("onlineGames/")[1];
		String fen = java.net.URLDecoder.decode(fenURL1, StandardCharsets.UTF_8.name());
		//log.info("FEN -------------------------> " + fen);

		Iterable<OnlineGame> gamesIterable = onlineGameRepo.findByResultat(res);

		List<OnlineGame> filteredGames = new ArrayList<>();

		for (OnlineGame onlineGame : gamesIterable) {
			try {
				if (contains(onlineGame, fen)) {
					filteredGames.add(onlineGame);
				}
			} catch (RuntimeException error) {
				log.info(error.getMessage());
			}
			;

		}
		return limitList(filteredGames);
	}

	@ApiOperation(value = "Retourne le pourcentage de points pour une position et un joueur donné")
	@GetMapping(value = "onlineGames/fen-list-stats/{id}/{username}")
	public @ResponseBody List<FenStat> getStatsIdUser(@PathVariable String id, @PathVariable String username)
			throws PGNSyntaxError, IOException {

		List<String> fens = getFensById(id);
		List<FenStat> fenStats = new ArrayList<>();

		for (String fen : fens) {

			List<OnlineGame> games = getGamesbyFenAndUsername(fen, username);

			double pct = pctCalculate(games, username);
			fenStats.add(new FenStat(fen, games.size(), pct));

		}

		return fenStats;
	}

	public boolean contains(OnlineGame onlineGame, String fen) throws PGNSyntaxError, IOException {

		try {
			Game game = parseOnlineGame(onlineGame);

			game.gotoStart();
			do {

				if (game.getPosition().getFEN().contains(fen))
					return true;
				game.goForward();

			} while (game.hasNextMove());

			if (game.getPosition().getFEN().contains(fen))
				return true;

			return false;
		} catch (RuntimeException e) {
			log.info(e.getMessage());
			return false;
		}
	}

	public int getMoveNumber(OnlineGame onlineGame, String fen) throws PGNSyntaxError, IOException {

		Game game = parseOnlineGame(onlineGame);

		game.gotoStart();
		int moveNumber = 0;
		do {

			if (game.getPosition().getFEN().contains(fen))
				return moveNumber;
			game.goForward();
			moveNumber++;

		} while (game.hasNextMove());

		if (game.getPosition().getFEN().contains(fen))
			return moveNumber;

		return -1;
	}

	public Game parseOnlineGame(OnlineGame onlineGame) throws PGNSyntaxError, IOException {
		String pgnStr = removeClk(onlineGame.getPgn(), "{[", "]}");
//		if (onlineGame.getId().equals("4578498224")) 
//				pgnStr=pgnStr.split("40...")[0];
//		log.info(pgnStr);

		InputStream is = new ByteArrayInputStream(pgnStr.getBytes());
		PGNReader pgn = new PGNReader(is, "");

		return pgn.parseGame();

	}

	// limit the size of the returned list
	private List<OnlineGame> limitList(Iterable<OnlineGame> gamesIterable) {
		List gamesList = StreamSupport.stream(gamesIterable.spliterator(), false).collect(Collectors.toList());

		int limit = Math.min(appProperties.getGamesNumberLimit(), gamesList.size());
		List<OnlineGame> limitedList = gamesList.subList(0, limit);
		return limitedList;
	}

	public static String removeClk(String str, String start, String end) {
		StringBuilder sb = new StringBuilder(str);
		while (sb.toString().contains(end)) {
			int endIndex = sb.lastIndexOf(end);
			int startIndex = sb.lastIndexOf(start);
			sb = sb.delete(startIndex, endIndex + start.length());
		}
		return sb.toString();
	}

	public double pctCalculate(List<OnlineGame> games, String username) {

		double points = 0;

		for (OnlineGame game : games) {
			points = points + pointsCalculate(game, username);
		}
		Double pct = points / games.size() * 100;
		return (pct);
	}

	public double pointsCalculate(OnlineGame game, String username) {

		if (game.getPlayerWhite().equals(username)) {
			if (game.getResultat().equals("1-0"))
				return 1.0;
			if (game.getResultat().equals("0-1"))
				return 0.0;
			return 0.5;

		}
		if (game.getPlayerBlack().equals(username)) {
			if (game.getResultat().equals("1-0"))
				return 0.0;
			if (game.getResultat().equals("0-1"))
				return 1.0;
			return 0.5;
		}
		return 0;
	}

}