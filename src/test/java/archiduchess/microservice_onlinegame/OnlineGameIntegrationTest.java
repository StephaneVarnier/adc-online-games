package archiduchess.microservice_onlinegame;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import archiduchess.microservice_onlinegame.controler.OnlineGameControler;
import archiduchess.microservice_onlinegame.modele.OnlineGame;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class OnlineGameIntegrationTest {

	private final static String URI = "/archiduchess/onlineGames";

	@MockBean
	private OnlineGameControler onlineGameCtrl;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@BeforeAll
	public void setup() {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testGetOnlineGameById() throws Exception {

		OnlineGame game = new OnlineGame();
		
		String pgn = "[Event \"Live Chess\"]\n[Site \"Chess.com\"]\n[Date \"2020.07.15\"]\n[Round \"-\"]\n[White \"ecolas788\"]\n[Black \"tiou\"]\n[Result \"1-0\"]\n[ECO \"B02\"]\n[ECOUrl \"https://www.chess.com/openings/Alekhines-Defense-Two-Pawns-Lasker-Variation-4...Nd5-5.Bc4\"]\n[CurrentPosition \"3r2k1/p3ppbp/2B3p1/3P4/Pr6/4B3/5PPP/2RR2K1 b - -\"]\n[Timezone \"UTC\"]\n[UTCDate \"2020.07.15\"]\n[UTCTime \"15:22:11\"]\n[WhiteElo \"1358\"]\n[BlackElo \"1386\"]\n[TimeControl \"180+2\"]\n[Termination \"ecolas788 won by resignation\"]\n[StartTime \"15:22:11\"]\n[EndDate \"2020.07.15\"]\n[EndTime \"15:26:21\"]\n[Link \"https://www.chess.com/live/game/5153821727\"]\n\n1. e4 {[%clk 0:03:01.9]} 1... Nf6 {[%clk 0:03:01.9]} 2. e5 {[%clk 0:03:00.7]} 2... Nd5 {[%clk 0:03:02.3]} 3. c4 {[%clk 0:03:00.8]} 3... Nb6 {[%clk 0:03:02.6]} 4. c5 {[%clk 0:03:01.3]} 4... Nd5 {[%clk 0:03:02.8]} 5. Bc4 {[%clk 0:02:59.2]} 5... c6 {[%clk 0:02:57.5]} 6. d4 {[%clk 0:02:59.4]} 6... d6 {[%clk 0:02:55.7]} 7. Nf3 {[%clk 0:02:58.5]} 7... dxe5 {[%clk 0:02:50.2]} 8. Nxe5 {[%clk 0:02:58.7]} 8... Nd7 {[%clk 0:02:49.1]} 9. Qh5 {[%clk 0:02:55.6]} 9... Nxe5 {[%clk 0:02:43.3]} 10. Qxe5 {[%clk 0:02:47.5]} 10... Qd7 {[%clk 0:02:23.4]} 11. O-O {[%clk 0:02:46.3]} 11... Qe6 {[%clk 0:02:18.5]} 12. Qh5 {[%clk 0:02:41.3]} 12... g6 {[%clk 0:02:17.4]} 13. Qf3 {[%clk 0:02:38.6]} 13... Bg7 {[%clk 0:02:14.6]} 14. Nc3 {[%clk 0:02:34.8]} 14... O-O {[%clk 0:02:12.1]} 15. Nxd5 {[%clk 0:02:27]} 15... cxd5 {[%clk 0:02:09.2]} 16. Bxd5 {[%clk 0:02:27]} 16... Qd7 {[%clk 0:02:07.6]} 17. Be3 {[%clk 0:02:24.1]} 17... Qf5 {[%clk 0:02:07.6]} 18. Qxf5 {[%clk 0:02:19]} 18... Bxf5 {[%clk 0:02:08.3]} 19. Bxb7 {[%clk 0:02:19.7]} 19... Rab8 {[%clk 0:02:09]} 20. c6 {[%clk 0:02:20.1]} 20... Be4 {[%clk 0:02:04.2]} 21. Rac1 {[%clk 0:02:10.3]} 21... Bxc6 {[%clk 0:01:58.5]} 22. Bxc6 {[%clk 0:02:06.9]} 22... Rxb2 {[%clk 0:01:57.9]} 23. a4 {[%clk 0:02:03.7]} 23... Rb4 {[%clk 0:01:53]} 24. Rfd1 {[%clk 0:02:00.6]} 24... Rd8 {[%clk 0:01:52.1]} 25. d5 {[%clk 0:02:00.1]} 1-0";
				
		game.setDate("2020.06.28");
		game.setEloBlack(3022);
		game.setEloWhite(2698);
		game.setId("5070708028");
		game.setOpening("Indian-Game-East-Indian-London-System-3...Bg7-4.e3-O-O");
		game.setPgn(pgn);
		game.setPlayerBlack("LyonBeast");
		game.setPlayerWhite("Blitzstream");
		game.setResultat("1-0");
		game.setTimeControl(null);
		
		Optional<OnlineGame> optionalOnlineGame = Optional.of(game);
		
		given(onlineGameCtrl.getGameById(game.getId())).willReturn(optionalOnlineGame);
				
		String jsonStr = "{'id': '5070708028','playerWhite': 'Blitzstream','playerBlack': 'LyonBeast','eloWhite': 2698,'eloBlack': 3022,'date': '2020.06.28','timeControl': null,'resultat': '1-0','opening': 'French-Defense-Normal-Variation','pgn': \"[Event \\\"Live Chess\\\"]\\n[Site \\\"Chess.com\\\"]\\n[Date \\\"2020.06.28\\\"]\\n[Round \\\"-\\\"]\\n[White \\\"Blitzstream\\\"]\\n[Black \\\"LyonBeast\\\"]\\n[Result \\\"1-0\\\"]\\n[ECO \\\"C00\\\"]\\n[ECOUrl \\\"https://www.chess.com/openings/French-Defense-Normal-Variation\\\"]\\n[CurrentPosition \\\"3Q4/8/8/1N4k1/p1p2pBp/P1P4K/1P3PP1/4r3 b - -\\\"]\\n[Timezone \\\"UTC\\\"]\\n[UTCDate \\\"2020.06.28\\\"]\\n[UTCTime \\\"17:10:24\\\"]\\n[WhiteElo \\\"2698\\\"]\\n[BlackElo \\\"3022\\\"]\\n[TimeControl \\\"180\\\"]\\n[Termination \\\"Blitzstream won by resignation\\\"]\\n[StartTime \\\"17:10:24\\\"]\\n[EndDate \\\"2020.06.28\\\"]\\n[EndTime \\\"17:16:24\\\"]\\n[Link \\\"https://www.chess.com/live/game/5070708028\\\"]\\n\\n1. d4 {[%clk 0:02:59.9]} 1... e6 {[%clk 0:02:56.4]} 2. e4 {[%clk 0:02:58.2]} 2... d5 {[%clk 0:02:55.2]} 3. Nd2 {[%clk 0:02:57.7]} 3... a6 {[%clk 0:02:52.2]} 4. Ngf3 {[%clk 0:02:56.3]} 4... Nf6 {[%clk 0:02:50.9]} 5. exd5 {[%clk 0:02:52.1]} 5... exd5 {[%clk 0:02:49.5]} 6. Bd3 {[%clk 0:02:51.3]} 6... c5 {[%clk 0:02:48.3]} 7. c3 {[%clk 0:02:47.5]} 7... c4 {[%clk 0:02:45.4]} 8. Bc2 {[%clk 0:02:46.3]} 8... Bd6 {[%clk 0:02:44.5]} 9. O-O {[%clk 0:02:45.2]} 9... O-O {[%clk 0:02:43.6]} 10. Re1 {[%clk 0:02:44.2]} 10... Nc6 {[%clk 0:02:37.4]} 11. Nf1 {[%clk 0:02:43.3]} 11... Ne7 {[%clk 0:02:33]} 12. Ng3 {[%clk 0:02:42.3]} 12... h6 {[%clk 0:02:29]} 13. Ne5 {[%clk 0:02:40.9]} 13... Re8 {[%clk 0:02:26.8]} 14. Bf4 {[%clk 0:02:17.9]} 14... b5 {[%clk 0:02:16.4]} 15. Qf3 {[%clk 0:02:02.8]} 15... Bxe5 {[%clk 0:01:44]} 16. Bxe5 {[%clk 0:01:56.7]} 16... Ng4 {[%clk 0:01:30.7]} 17. Bxg7 {[%clk 0:01:47.6]} 17... Kxg7 {[%clk 0:01:30.6]} 18. Nh5+ {[%clk 0:01:46.6]} 18... Kf8 {[%clk 0:01:26.9]} 19. h3 {[%clk 0:01:36.7]} 19... Ng8 {[%clk 0:01:24.1]} 20. hxg4 {[%clk 0:01:32.6]} 20... Rxe1+ {[%clk 0:01:22.6]} 21. Rxe1 {[%clk 0:01:30.9]} 21... Qg5 {[%clk 0:01:22.5]} 22. Re5 {[%clk 0:01:21.2]} 22... Bxg4 {[%clk 0:01:17.3]} 23. Qxd5 {[%clk 0:01:06]} 23... Qd8 {[%clk 0:01:08.8]} 24. Qc5+ {[%clk 0:00:50.9]} 24... Ne7 {[%clk 0:01:08.1]} 25. Nf6 {[%clk 0:00:49.3]} 25... Rc8 {[%clk 0:01:07]} 26. Qxe7+ {[%clk 0:00:42.1]} 26... Qxe7 {[%clk 0:01:06.9]} 27. Rxe7 {[%clk 0:00:42]} 27... Kxe7 {[%clk 0:01:06.5]} 28. Nxg4 {[%clk 0:00:41.9]} 28... h5 {[%clk 0:01:06.1]} 29. Ne5 {[%clk 0:00:41]} 29... a5 {[%clk 0:01:04]} 30. Bf5 {[%clk 0:00:38.7]} 30... Rc7 {[%clk 0:01:02]} 31. Be4 {[%clk 0:00:38]} 31... Kd6 {[%clk 0:00:58.3]} 32. Bf3 {[%clk 0:00:37]} 32... h4 {[%clk 0:00:55]} 33. Kh2 {[%clk 0:00:36]} 33... f6 {[%clk 0:00:52.4]} 34. Nc6 {[%clk 0:00:34.6]} 34... a4 {[%clk 0:00:46.7]} 35. Nb4 {[%clk 0:00:31.9]} 35... Re7 {[%clk 0:00:45.2]} 36. Nc2 {[%clk 0:00:31.2]} 36... f5 {[%clk 0:00:41.7]} 37. a3 {[%clk 0:00:29.6]} 37... Ke6 {[%clk 0:00:39.9]} 38. Kh3 {[%clk 0:00:28.2]} 38... Rh7 {[%clk 0:00:38.6]} 39. Ne3 {[%clk 0:00:27.4]} 39... Kf6 {[%clk 0:00:37]} 40. Nd5+ {[%clk 0:00:26.3]} 40... Kg5 {[%clk 0:00:36.1]} 41. Ne3 {[%clk 0:00:24.4]} 41... Re7 {[%clk 0:00:35.5]} 42. d5 {[%clk 0:00:23.5]} 42... f4 {[%clk 0:00:34.9]} 43. Nc2 {[%clk 0:00:22]} 43... Rd7 {[%clk 0:00:33.1]} 44. Nd4 {[%clk 0:00:20.7]} 44... Re7 {[%clk 0:00:32.8]} 45. Ne6+ {[%clk 0:00:19.8]} 45... Kf5 {[%clk 0:00:32.3]} 46. d6 {[%clk 0:00:15.6]} 46... Rd7 {[%clk 0:00:28.9]} 47. Nd4+ {[%clk 0:00:12.9]} 47... Kg5 {[%clk 0:00:25.3]} 48. Nxb5 {[%clk 0:00:11.5]} 48... Rd8 {[%clk 0:00:24.9]} 49. Bg4 {[%clk 0:00:10.6]} 49... Re8 {[%clk 0:00:23.8]} 50. d7 {[%clk 0:00:10]} 50... Re1 {[%clk 0:00:21.4]} 51. d8=Q+ {[%clk 0:00:08.8]} 1-0'}" ; 

		this.mockMvc
				.perform(MockMvcRequestBuilders.get(URI+"/{id}", "5070708028")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonStr)) ;
	}

}
