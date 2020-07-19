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
				
		game.setDate("2020.07.15");
		game.setEloBlack(1386);
		game.setEloWhite(1358);
		game.setId("5153821727");
		game.setOpening("Alekhines-Defense-Two-Pawns-Lasker-Variation-4...Nd5-5.Bc4");
		game.setPgn(pgn);
		game.setPlayerBlack("tiou");
		game.setPlayerWhite("ecolas788");
		game.setResultat("1-0");
		game.setTimeControl(null);
		
		Optional<OnlineGame> optionalOnlineGame = Optional.of(game);
		
		given(onlineGameCtrl.getGameById(game.getId())).willReturn(optionalOnlineGame);
				
		String jsonStr = "{'id': '5153821727','playerWhite': 'ecolas788','playerBlack': 'tiou','eloWhite': 1358,'eloBlack': 1386,'date': '2020.07.15','timeControl': null,'resultat': '1-0','opening': 'Alekhines-Defense-Two-Pawns-Lasker-Variation-4...Nd5-5.Bc4',,'pgn': '[Event \\'Live Chess\\']\\n[Site \\'Chess.com\\']\\n[Date \\'2020.07.15\\']\\n[Round \\'-\\']\\n[White \\'ecolas788\\']\\n[Black \\'tiou\\']\\n[Result \\'1-0\\']\\n[ECO \\'B02\\']\\n[ECOUrl \\'https://www.chess.com/openings/Alekhines-Defense-Two-Pawns-Lasker-Variation-4...Nd5-5.Bc4\\']\\n[CurrentPosition \\'3r2k1/p3ppbp/2B3p1/3P4/Pr6/4B3/5PPP/2RR2K1 b - -\\']\\n[Timezone \\'UTC\\']\\n[UTCDate \\'2020.07.15\\']\\n[UTCTime \\'15:22:11\\']\\n[WhiteElo \\'1358\\']\\n[BlackElo \\'1386\\']\\n[TimeControl \\'180+2\\']\\n[Termination \\'ecolas788 won by resignation\\']\\n[StartTime \\'15:22:11\\']\\n[EndDate \\'2020.07.15\\']\\n[EndTime \\'15:26:21\\']\\n[Link \\'https://www.chess.com/live/game/5153821727\\']\\n\\n1. e4 {[%clk 0:03:01.9]} 1... Nf6 {[%clk 0:03:01.9]} 2. e5 {[%clk 0:03:00.7]} 2... Nd5 {[%clk 0:03:02.3]} 3. c4 {[%clk 0:03:00.8]} 3... Nb6 {[%clk 0:03:02.6]} 4. c5 {[%clk 0:03:01.3]} 4... Nd5 {[%clk 0:03:02.8]} 5. Bc4 {[%clk 0:02:59.2]} 5... c6 {[%clk 0:02:57.5]} 6. d4 {[%clk 0:02:59.4]} 6... d6 {[%clk 0:02:55.7]} 7. Nf3 {[%clk 0:02:58.5]} 7... dxe5 {[%clk 0:02:50.2]} 8. Nxe5 {[%clk 0:02:58.7]} 8... Nd7 {[%clk 0:02:49.1]} 9. Qh5 {[%clk 0:02:55.6]} 9... Nxe5 {[%clk 0:02:43.3]} 10. Qxe5 {[%clk 0:02:47.5]} 10... Qd7 {[%clk 0:02:23.4]} 11. O-O {[%clk 0:02:46.3]} 11... Qe6 {[%clk 0:02:18.5]} 12. Qh5 {[%clk 0:02:41.3]} 12... g6 {[%clk 0:02:17.4]} 13. Qf3 {[%clk 0:02:38.6]} 13... Bg7 {[%clk 0:02:14.6]} 14. Nc3 {[%clk 0:02:34.8]} 14... O-O {[%clk 0:02:12.1]} 15. Nxd5 {[%clk 0:02:27]} 15... cxd5 {[%clk 0:02:09.2]} 16. Bxd5 {[%clk 0:02:27]} 16... Qd7 {[%clk 0:02:07.6]} 17. Be3 {[%clk 0:02:24.1]} 17... Qf5 {[%clk 0:02:07.6]} 18. Qxf5 {[%clk 0:02:19]} 18... Bxf5 {[%clk 0:02:08.3]} 19. Bxb7 {[%clk 0:02:19.7]} 19... Rab8 {[%clk 0:02:09]} 20. c6 {[%clk 0:02:20.1]} 20... Be4 {[%clk 0:02:04.2]} 21. Rac1 {[%clk 0:02:10.3]} 21... Bxc6 {[%clk 0:01:58.5]} 22. Bxc6 {[%clk 0:02:06.9]} 22... Rxb2 {[%clk 0:01:57.9]} 23. a4 {[%clk 0:02:03.7]} 23... Rb4 {[%clk 0:01:53]} 24. Rfd1 {[%clk 0:02:00.6]} 24... Rd8 {[%clk 0:01:52.1]} 25. d5 {[%clk 0:02:00.1]} 1-0'}" ; 

		this.mockMvc
				.perform(MockMvcRequestBuilders.get(URI+"/{id}", "5153821727")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonStr)) ;
	}

}
