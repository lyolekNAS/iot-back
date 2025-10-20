DROP PROCEDURE aggregate_port_history;

CREATE PROCEDURE aggregate_port_history(IN start_date DATETIME, IN end_date DATETIME)
BEGIN
  CREATE TEMPORARY TABLE tmp_agg AS
  SELECT
    dph.port_id,
    FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(dph.on_time) / 300) * 300) AS on_time,
    AVG(dph.value) AS value
  FROM device_ports_history dph
  WHERE dph.on_time BETWEEN start_date AND end_date
  GROUP BY dph.port_id, FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(dph.on_time) / 300) * 300);

  DELETE FROM device_ports_history
  WHERE on_time BETWEEN start_date AND end_date;

  INSERT INTO device_ports_history (port_id, on_time, value)
  SELECT port_id, on_time, value FROM tmp_agg;

  DROP TEMPORARY TABLE tmp_agg;
END