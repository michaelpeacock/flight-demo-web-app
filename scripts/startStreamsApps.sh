#/bin/sh

java -cp ../jars/flight-streams-demo-1.0-jar-with-dependencies.jar io.confluent.flightdemo.FlightTransformer > /dev/null 2>&1 &
java -cp ../jars/flight-streams-demo-1.0-jar-with-dependencies.jar io.confluent.flightdemo.FlightsInAir > /dev/null 2>&1 &
java -cp ../jars/flight-streams-demo-1.0-jar-with-dependencies.jar io.confluent.flightdemo.FlightsOnGround > /dev/null 2>&1 &
java -cp ../jars/flight-streams-demo-1.0-jar-with-dependencies.jar io.confluent.flightdemo.CurrentFlightCounter > /dev/null 2>&1 &
java -cp ../jars/flight-streams-demo-1.0-jar-with-dependencies.jar io.confluent.flightdemo.USFlightCounter > /dev/null 2>&1 &
java -cp ../jars/flight-streams-demo-1.0-jar-with-dependencies.jar io.confluent.flightdemo.FlightCounter > /dev/null 2>&1 &

