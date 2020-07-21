# ArchiDuChess

ArchiDuChess (ADC) is an application allowing his users to consult :

  - their chess games played on ICC (http://chess.com)
  - some statistics about their efficiency related to some openings or positions
  - the best chess players on ICC
  

## adc-online-games

This is one of the microservices used in the backend part of the application
was generated in Java 8, with the framework Spring Boot

### All the components used for this application 

https://github.com/StephaneVarnier/adc-front
https://github.com/StephaneVarnier/adc-online-games
https://github.com/StephaneVarnier/adc-chess-stats
https://github.com/StephaneVarnier/adc-users
https://github.com/StephaneVarnier/adc-icc-api
https://github.com/StephaneVarnier/adc-leaderboard
https://github.com/StephaneVarnier/chessboardjs


### Installation of adc-online-games

```sh
cd Archiduchess
git clone https://github.com/StephaneVarnier/adc-online-games
```

Import the project in your favorite IDE 
Add the environnement variables

```sh
maven clean install package
```

### ENVIRONNEMENT VARIABLES : 

GAMES_NB_LIMIT (default : 10000)
MONGO_DB
MONGO_PASSWORD
MONGO_USER
ONLINE_GAMES_PORT(default : 9999)

### API documentation 
http://localhost:9999/swagger-ui.html#/  (if you did not change the ONLINE_GAMES_PORT) 






