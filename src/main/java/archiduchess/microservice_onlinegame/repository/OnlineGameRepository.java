package archiduchess.microservice_onlinegame.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import archiduchess.microservice_onlinegame.modele.OnlineGame;

//@Repository
public interface OnlineGameRepository extends MongoRepository<OnlineGame,  String> {
	
	List<OnlineGame> findByPlayerWhite (String playerWhite) ; 
	
	List<OnlineGame> findByPlayerBlack (String playerBlack) ;

	Iterable<OnlineGame> findByPlayerBlackOrPlayerWhite(String playerBlack, String playerWhite);

	Iterable<OnlineGame> findByPlayerBlackAndResultat(String username, String resultat);

	Iterable<OnlineGame> findByPlayerWhiteAndResultat(String username, String resultat);

	Iterable<OnlineGame> findByResultat(String res);
	
	//OnlineGame findGameById(String id);
	

	OnlineGame findGameById(long id);




	
	
}
