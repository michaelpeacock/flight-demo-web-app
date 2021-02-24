echo "JDK 11 or greater required"
echo "Confluent Platform 5.5 requirement"

confluent local load OpenSkySourceConnector -- -d ../config/connect-standalone.properties

./prepTopics.sh

../ksql/prepKsql.sh

java -jar ../jars/flight-demo.jar  > /dev/null 2>&1 &

sleep 5

./startStreamsApps.sh

echo "To access go to http://localhost:8080. Confluent Control Center go to http://localhost:9092"
