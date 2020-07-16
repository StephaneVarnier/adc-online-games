package archiduchess.microservice_onlinegame.controler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import archiduchess.microservice_onlinegame.modele.MoveStat;
import archiduchess.microservice_onlinegame.repository.MoveStatRepository;
import archiduchess.microservice_onlinegame.repository.OnlineGameRepository;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/archiduchess")
public class MoveStatControler {

	@Autowired
	private OnlineGameControler onlineGameCtrl;
	
	@Autowired
	private MoveStatRepository moveStatRepo;
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	
	
	public void createMoveStats() {
	   
	}
	
	
	
}
