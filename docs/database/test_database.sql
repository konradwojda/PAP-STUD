-- Test `verify_trip_vehicle` (all following should fail)
UPDATE TRIPS SET VEHICLE_SIDE_NUMBER = '001' WHERE TRIP_ID = 16;
UPDATE TRIPS SET VEHICLE_SIDE_NUMBER = '3101' WHERE TRIP_ID = 16;

-- Test `calendar_exception_useless_check` (all following should fail):
INSERT INTO CALENDAR_EXCEPTIONS VALUES (3, '05-JAN-22', 1, 1);
INSERT INTO CALENDAR_EXCEPTIONS VALUES (3, '08-JAN-22', 1, 0);
INSERT INTO CALENDAR_EXCEPTIONS VALUES (3, '05-JAN-22', 2, 0);
INSERT INTO CALENDAR_EXCEPTIONS VALUES (3, '08-JAN-22', 2, 1);

-- Test `set_stop_group_coordinates`
SELECT * FROM stop_groups WHERE stop_group_id = 1;
EXEC set_group_stop_coordinates(1);
/
SELECT * FROM stop_groups WHERE stop_group_id = 1;

-- Test `insert_into_pattern_stop`
SELECT * FROM pattern_stops WHERE pattern_id = 1 ORDER BY idx ASC;
EXEC insert_into_pattern_stop(13, 1, 1, 4, 20);
/
SELECT * FROM pattern_stops WHERE pattern_id = 1 ORDER BY idx ASC;

-- Test `departure_time`
SELECT departure_time(1, 2) FROM DUAL;

-- Test `is_pattern_wheelchair_accessible`
SELECT is_pattern_wheelchair_accessible(1) FROM DUAL;
SELECT is_pattern_wheelchair_accessible(4) FROM DUAL;
