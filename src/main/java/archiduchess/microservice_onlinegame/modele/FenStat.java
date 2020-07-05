package archiduchess.microservice_onlinegame.modele;

public class FenStat {

	private String fen;
	private int playedGames; 
	private double points;
	
	public FenStat() {	}

	public FenStat(String fen, int playedGames, double points) {
		this.fen = fen;
		this.playedGames = playedGames;
		this.points = points;
	}

	public String getFen() {
		return fen;
	}

	public void setFen(String fen) {
		this.fen = fen;
	}

	public int getPlayedGames() {
		return playedGames;
	}

	public void setPlayedGames(int playedGames) {
		this.playedGames = playedGames;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}
	
	
	
}
