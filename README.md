# Kafka Live Flight Demo


## Description

This project provides a demo of Kafka capabilities including Connect, Kafka Streams, and ksqlDB using live flight data from [OpenSky Network](https://opensky-network.org/). This project has several dependencies including:

     * [KSQLGeo](https://github.com/wlaforest/KSQLGeo) - geospatial UDFs for ksqlDB
     * [kafka-connect-opensky](https://github.com/nbuesing/kafka-connect-opensky) - OpenSky Network Kafka Connector
     * [Cesium](https://github.com/CesiumGS/cesium) - WebGL geospatial toolkit


## Requirements
    * Confluent Platform 5.5
    * Java 11 or higher

## Steps to Run the Demo
    1. Follow the instructions in the kafka-connect-opensky to build the full version and then update the connect plug-in path to include it prior to starting the confluent services.
    2. run 'confluent local services start'
    3. run 'start.sh' script in the scripts folder. You can optionally run each command individually.


