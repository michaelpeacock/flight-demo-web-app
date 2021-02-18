### Start Confluent Components
confluent local services start

### Stop Connect since we are running a local connect app
confluent local services connect stop

### Create Topics
kafka-topics --bootstrap-server localhost:9092 --create --topic flight-data
kafka-topics --bootstrap-server localhost:9092 --create --topic dashboard-data

### Start the OpenSky Connect app
connect-standalone config/worker.properties config/connect-standalone.properties

### Consumers
kafka-console-consumer --bootstrap-server localhost:9092 --topic dashboard-data

kafka-console-consumer --bootstrap-server localhost:9092 --topic flights-by-key \
--property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer \
--from-beginning --property print.key=true

### File Sink Connector
org.apache.kafka.connect.storage.StringConverter


curl -X "POST" "http://localhost:8088/query" \
     -H "Content-Type: application/json; charset=utf-8" \
     -d $'{
  "ksql": "SELECT * FROM FLIGHT_DATA WHERE id=\'06a2de\';",
  "streamsProperties": {"ksql.streams.auto.offset.reset": "earliest"}
}'