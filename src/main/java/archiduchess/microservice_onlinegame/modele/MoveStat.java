package archiduchess.microservice_onlinegame.modele;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "MoveStat")
public class MoveStat {

	@Id
	private String Id;
	
	private String gameId; 
	private String fen;
	private String user;
	private Color color;
	private double score;
	private String nextMove;
	
	
	
	public String getId() {
		return Id;
	}
	
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getFen() {
		return fen;
	}
	public void setFen(String fen) {
		this.fen = fen;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getNextMove() {
		return nextMove;
	}
	public void setNextMove(String nextMove) {
		this.nextMove = nextMove;
	}
	
	
	
	
}
