SET 'auto.offset.reset'='earliest';

CREATE STREAM FILTERED_FLIGHTS WITH (KAFKA_TOPIC='filtered_flights', PARTITIONS=1, REPLICAS=1) AS SELECT
  *
FROM FLIGHT_DATA A
INNER JOIN FENCE F WITHIN 7 DAYS ON ((A.UNITY = F.UNITY))
WHERE GEO_CONTAINED(A.`latitude`, A.`longitude`, F._RAW_DATA)
EMIT CHANGES;