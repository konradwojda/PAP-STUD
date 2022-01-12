/*** TRIGGERS ***/

-- When updating/setting a trip vehicle,
-- check if the vehicle's traction_type is the same as line's type
-- and whether the vehicle's wheelchair_accessibility matches.
CREATE OR REPLACE TRIGGER verify_trip_vehicle
BEFORE INSERT OR UPDATE ON trips
FOR EACH ROW
WHEN (new.vehicle_side_number IS NOT NULL)
DECLARE
    v_vehicle vehicles%ROWTYPE;
    v_vehicle_model vehicle_models%ROWTYPE;
    v_line_id NUMBER;
    v_line lines%ROWTYPE;
BEGIN
    SELECT * INTO v_vehicle FROM vehicles WHERE side_number = :new.vehicle_side_number;
    SELECT * INTO v_vehicle_model FROM vehicle_models WHERE model_id = v_vehicle.model_id;
    SELECT l.line_id INTO v_line_id
        FROM lines l RIGHT join patterns p ON (p.line_id = l.line_id)
        WHERE p.pattern_id = :new.pattern_id;
    SELECT * INTO v_line FROM lines WHERE line_id = v_line_id;

    IF :new.wheelchair_accessible != 0 AND v_vehicle.wheelchair_accessible != :new.wheelchair_accessible THEN
        RAISE_APPLICATION_ERROR(-20001, 'Wheelchair accessibilities of trip and vehicle doesn''t match');
    END IF;

    IF v_vehicle_model.TRACTION_TYPE != v_line.TYPE THEN
        RAISE_APPLICATION_ERROR(-20001, 'Type of line and traction type of vehicle doesn''t match');
    END IF;
END;
/

-- When creating/updating a calendar_exception make sure it is not useless:
-- that it doesn't remove a day when a calendar is inactive or
-- add a day when a calendar is active.
CREATE OR REPLACE TRIGGER calendar_exception_useless_check
BEFORE INSERT OR UPDATE ON calendar_exceptions
FOR EACH ROW
DECLARE
    v_cal calendars%ROWTYPE;
    v_cal_active VARCHAR(10) := 'F';
    v_exception_weekday VARCHAR(10) := NULL;
    v_query_cal_weekday VARCHAR2(64) := NULL;
BEGIN
    SELECT * INTO v_cal FROM calendars WHERE calendar_id = :new.calendar_id;

    CASE to_char(:new.day, 'D')
        WHEN '1' THEN v_exception_weekday := 'sunday';
        WHEN '2' THEN v_exception_weekday := 'monday';
        WHEN '3' THEN v_exception_weekday := 'tuesday';
        WHEN '4' THEN v_exception_weekday := 'wednesday';
        WHEN '5' THEN v_exception_weekday := 'thursday';
        WHEN '6' THEN v_exception_weekday := 'friday';
        WHEN '7' THEN v_exception_weekday := 'saturday';
    END CASE;

    v_query_cal_weekday := 'SELECT ' || v_exception_weekday || ' FROM calendars WHERE calendar_id = :1';
    EXECUTE IMMEDIATE v_query_cal_weekday INTO v_cal_active USING :new.calendar_id;

    IF v_cal_active = 'T' AND v_cal.start_date <= :new.day AND (:new.day <= v_cal.end_date OR v_cal.end_date IS NULL) THEN
        v_cal_active := 'T';
    ELSE
        v_cal_active := 'F';
    END IF;

    IF :new.added = v_cal_active THEN
        RAISE_APPLICATION_ERROR(-20011, 'Useless exception - day already added/removed');
    END IF;
END;
/


/*** PROCEDURES ***/

-- Updates the coordinates of a stop group with the average
-- position of all the attached stops.
CREATE OR REPLACE PROCEDURE set_stop_group_coordinates (stop_group_id NUMBER)
AS
    v_avg_lat NUMBER;
    v_avg_lon NUMBER;
BEGIN
    SELECT AVG(lat), AVG(lon) INTO v_avg_lat, v_avg_lon
        FROM stops WHERE stop_group_id = stop_group_id;

    UPDATE stop_groups SET lat = v_avg_lat, lon = v_avg_lon
    WHERE stop_group_id = stop_group_id;
END;
/

-- Inserts a pattern_stop into the middle of a pattern (by first shifting all following indices),
-- then adding the actual row.
CREATE OR REPLACE PROCEDURE insert_into_pattern_stop (p_pattern_stop_id NUMBER, p_idx NUMBER, p_pattern_id NUMBER, p_stop_id NUMBER, p_travel_time NUMBER)
AS
BEGIN
    UPDATE pattern_stops SET idx = idx + 1 WHERE idx >= p_idx AND pattern_id = p_pattern_id;
    INSERT INTO pattern_stops (pattern_stop_id,stop_id,pattern_id,travel_time,idx)
        VALUES (p_pattern_stop_id, p_stop_id, p_pattern_id, p_travel_time, p_idx);
END;
/


/*** FUNCTIONS ***/

-- Returns the real departure time of a trip from a particular stop
CREATE OR REPLACE FUNCTION departure_time (p_trip_id NUMBER, p_pattern_stop_id NUMBER)
RETURN NUMBER
AS
    v_departure NUMBER;
    v_travel_time NUMBER;
BEGIN
    SELECT departure INTO v_departure FROM trips WHERE trip_id = p_trip_id;
    SELECT travel_time INTO v_travel_time FROM pattern_stops WHERE pattern_stop_id = p_pattern_stop_id;
    RETURN v_departure + v_travel_time;
END;
/

-- Checks wheether all stops and trips of a pattern are wheelchair_accessible.
-- Returns a tri-state enum (0 - unknown, 1 - accessible, 2 - inaccessible)
-- TODO: Also check stops
-- TODO: Handle unknown accessibility
CREATE OR REPLACE FUNCTION is_pattern_wheelchair_accessible(p_pattern_id NUMBER)
RETURN NUMBER
AS
    v_is_wheelchair_accessible NUMBER := 1;
BEGIN
    FOR r_pattern IN (SELECT * FROM patterns p
                      LEFT JOIN pattern_stops ps ON (p.pattern_id = ps.pattern_id)
                      LEFT JOIN stops s ON (ps.stop_id = s.stop_id)
                      WHERE p.pattern_id = p_pattern_id)
    LOOP
        IF r_pattern.wheelchair_accessible = 2 THEN
            v_is_wheelchair_accessible := 2;
        END IF;
    END LOOP;
    RETURN v_is_wheelchair_accessible;
END;
/
