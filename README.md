# Kafka Live Flight Demo


## Description

This project provides a demo of Kafka capabilities including Connect, Kafka Streams, and ksqlDB using live flight data from [OpenSky Network](https://opensky-network.org/).


### Start Confluent Components
confluent local services start

### Stop Connect since we are running a local connect app
confluent local services connect stop

### Create Topics
kafka-topics --bootstrap-server localhost:9092 --create --topic flights
kafka-topics --bootstrap-server localhost:9092 --create --topic dashboard-data

### Start the OpenSky Connect app
connect-standalone config/worker.properties config/connect-standalone.properties
confluent local load OpenSkySourceConnector -- -d connect-standalone.properties

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


CREATE STREAM flights (
    `id` VARCHAR, 
    `callSign` VARCHAR, 
    `originCountry` VARCHAR,
  	`updateTime` DOUBLE,
  	`latitude` DOUBLE,
  	`longitude` DOUBLE,
  	`altitude` DOUBLE,
  	`speed` DOUBLE,
  	`heading` DOUBLE
  ) WITH (
    KAFKA_TOPIC='flights',
    PARTITIONS=1,
    VALUE_FORMAT='JSON'
  );

 CREATE STREAM FLIGHT_DATA WITH (KAFKA_TOPIC='flight-data', PARTITIONS=1, REPLICAS=1) AS SELECT
  *,
  1 UNITY
FROM FLIGHTS
EMIT CHANGES;

CREATE STREAM FENCE_RAW
  (type VARCHAR, "properties" MAP<VARCHAR, VARCHAR>,
   geometry MAP<VARCHAR, VARCHAR>, _raw_data VARCHAR)
WITH
  (kafka_topic='fence_raw', value_format='JSON', PARTITIONS=1);

CREATE STREAM FENCE WITH (KAFKA_TOPIC='fence', PARTITIONS=1, REPLICAS=1) AS SELECT
  *,
  1 UNITY
FROM FENCE_RAW FENCE_RAW
EMIT CHANGES;

CREATE STREAM FLIGHT_ALERT WITH (KAFKA_TOPIC='flight_alert', PARTITIONS=1, REPLICAS=1) AS SELECT
  *
FROM FLIGHT_DATA A
INNER JOIN FENCE F WITHIN 7 DAYS ON ((A.UNITY = F.UNITY))
WHERE GEO_CONTAINED(A.`latitude`, A.`longitude`, F._RAW_DATA)
EMIT CHANGES;
