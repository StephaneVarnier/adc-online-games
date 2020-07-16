package archiduchess.microservice_onlinegame.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import archiduchess.microservice_onlinegame.modele.MoveStat;

public interface MoveStatRepository extends MongoRepository<MoveStat,  String> {
	
	
}
