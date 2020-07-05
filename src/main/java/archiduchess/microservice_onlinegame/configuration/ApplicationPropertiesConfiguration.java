package archiduchess.microservice_onlinegame.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("archiduchess")
@RefreshScope
public class ApplicationPropertiesConfiguration {

	private int gamesNumberLimit;

	public int getGamesNumberLimit() {
		return gamesNumberLimit;
	}

	public void setGamesNumberLimit(int gamesNumberLimit) {
		this.gamesNumberLimit = gamesNumberLimit;
	} 
	
	
	
}
