# Kafka Live Flight Demo


## Description

This project provides a demo of Kafka capabilities including Connect, Kafka Streams, and ksqlDB using live flight data from [OpenSky Network](https://opensky-network.org/). This project has several dependencies including:

  * [KSQLGeo](https://github.com/wlaforest/KSQLGeo) - geospatial UDFs for ksqlDB
  * [KafkaGeoDemo](https://github.com/wlaforest/KafkaGeoDemo) - geo demo used for installing ksqlDB UDFs
  * [kafka-connect-opensky](https://github.com/nbuesing/kafka-connect-opensky) - OpenSky Network Kafka Connector
  * [Cesium](https://github.com/CesiumGS/cesium) - WebGL geospatial toolkit


## Requirements
  * Confluent Platform 5.5
  * Java 11 or higher

## Steps to Run the Demo
  1. Follow the instructions in the kafka-connect-opensky repo to build the full version and then update the connect plug-in path to include it prior to starting the confluent services.
  2. Follow the instructions KSQLGeo repo to install the ksqlDB UDFs
  3. run `confluent local services start`
  4. run `start.sh` script in the scripts folder
  5. In a web browers, go to http://localhost:8080 


