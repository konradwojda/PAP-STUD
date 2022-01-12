/*** TABLE CREATION ***/

CREATE TABLE agencies (
    agency_id NUMBER(9) NOT NULL,
    name      VARCHAR2(64 CHAR) NOT NULL,
    website   VARCHAR2(128 CHAR) NOT NULL,
    timezone  VARCHAR2(64 CHAR),
    telephone VARCHAR2(32 CHAR)
);

ALTER TABLE agencies ADD CONSTRAINT agency_pk PRIMARY KEY ( agency_id );

CREATE TABLE calendars (
    calendar_id NUMBER(9) NOT NULL,
    name        VARCHAR2(64 BYTE) NOT NULL,
    start_date  DATE NOT NULL,
    end_date    DATE,
    monday      CHAR(1) NOT NULL,
    tuesday     CHAR(1) NOT NULL,
    wednesday   CHAR(1) NOT NULL,
    thursday    CHAR(1) NOT NULL,
    friday      CHAR(1) NOT NULL,
    saturday    CHAR(1) NOT NULL,
    sunday      CHAR(1) NOT NULL
);

ALTER TABLE calendars ADD CONSTRAINT calendar_pk PRIMARY KEY ( calendar_id );

CREATE TABLE calendar_exceptions (
    calendar_exception_id NUMBER(9) NOT NULL,
    day                   DATE NOT NULL,
    calendar_id           NUMBER(9) NOT NULL,
    added                 CHAR(1) NOT NULL
);

ALTER TABLE calendar_exceptions ADD CONSTRAINT calendar_exception_pk PRIMARY KEY ( calendar_exception_id );

CREATE TABLE stop_groups (
    stop_group_id NUMBER(9) NOT NULL,
    name          VARCHAR2(64 CHAR) NOT NULL,
    code          VARCHAR2(32 CHAR),
    lat           NUMBER,
    lon           NUMBER
);

ALTER TABLE stop_groups ADD CONSTRAINT stopgroup_pk PRIMARY KEY ( stop_group_id );

CREATE TABLE stops (
    stop_id               NUMBER(9) NOT NULL,
    name                  VARCHAR2(64 CHAR) NOT NULL,
    code                  VARCHAR2(16 CHAR),
    lat                   NUMBER(9) NOT NULL,
    lon                   NUMBER(9) NOT NULL,
    wheelchair_accessible NUMBER(2) NOT NULL,
    stop_group_id         NUMBER
);

ALTER TABLE stops ADD CONSTRAINT stop_pk PRIMARY KEY ( stop_id );

CREATE TABLE vehicle_models (
    model_id      NUMBER(9) NOT NULL,
    brand         VARCHAR2(32 CHAR) NOT NULL,
    model         VARCHAR2(64 CHAR) NOT NULL,
    traction_type NUMBER(1) NOT NULL
);

ALTER TABLE vehicle_models ADD CONSTRAINT vehicle_model_pk PRIMARY KEY ( model_id );

CREATE TABLE vehicles (
    side_number           VARCHAR2(16 CHAR) NOT NULL,
    registration_number   VARCHAR2(32 CHAR),
    wheelchair_accessible NUMBER(1) NOT NULL,
    has_air_conditioning  NUMBER,
    model_id              NUMBER(9) NOT NULL
);

ALTER TABLE vehicles ADD CONSTRAINT vehicle_pk PRIMARY KEY ( side_number );

CREATE TABLE lines (
    line_id     NUMBER(9) NOT NULL,
    code        VARCHAR2(16 CHAR) NOT NULL,
    description VARCHAR2(64 CHAR),
    type        NUMBER(2) NOT NULL,
    agency_id   NUMBER(9) NOT NULL
);

ALTER TABLE lines ADD CONSTRAINT line_pk PRIMARY KEY ( line_id );

CREATE TABLE patterns (
    pattern_id NUMBER(9) NOT NULL,
    headsign   VARCHAR2(64 CHAR),
    direction  NUMBER(1),
    line_id    NUMBER(9) NOT NULL
);

ALTER TABLE patterns ADD CONSTRAINT pattern_pk PRIMARY KEY ( pattern_id );

CREATE TABLE pattern_shape_points (
    idx        NUMBER(6) NOT NULL,
    lat        NUMBER(9) NOT NULL,
    lon        NUMBER(9) NOT NULL,
    pattern_id NUMBER(9) NOT NULL
);

ALTER TABLE pattern_shape_points ADD CONSTRAINT pattern_shape_point_pk PRIMARY KEY ( pattern_id, idx );

CREATE TABLE pattern_stops (
    pattern_stop_id NUMBER(9) NOT NULL,
    stop_id         NUMBER(9) NOT NULL,
    pattern_id      NUMBER(9) NOT NULL,
    travel_time     NUMBER(6) NOT NULL,
    idx             NUMBER(6) NOT NULL
);

ALTER TABLE pattern_stops ADD CONSTRAINT pattern_stop_pk PRIMARY KEY ( pattern_stop_id );

CREATE TABLE trips (
    trip_id               NUMBER(9) NOT NULL,
    wheelchair_accessible NUMBER(2) NOT NULL,
    departure             NUMBER(6) NOT NULL,
    pattern_id            NUMBER(9) NOT NULL,
    calendar_id           NUMBER(9) NOT NULL,
    vehicle_side_number   VARCHAR2(16 CHAR)
);

ALTER TABLE trips ADD CONSTRAINT trip_pk PRIMARY KEY ( trip_id );


/*** FOREIGN KEYS ***/

ALTER TABLE calendar_exceptions
    ADD CONSTRAINT calendar_exception_calendar_fk FOREIGN KEY ( calendar_id )
        REFERENCES calendars ( calendar_id )
            ON DELETE CASCADE;

ALTER TABLE lines
    ADD CONSTRAINT line_agency_fk FOREIGN KEY ( agency_id )
        REFERENCES agencies ( agency_id )
            ON DELETE CASCADE;

ALTER TABLE patterns
    ADD CONSTRAINT pattern_line_fk FOREIGN KEY ( line_id )
        REFERENCES lines ( line_id )
            ON DELETE CASCADE;

ALTER TABLE pattern_shape_points
    ADD CONSTRAINT pattern_shape_point_pattern_fk FOREIGN KEY ( pattern_id )
        REFERENCES patterns ( pattern_id )
            ON DELETE CASCADE;

ALTER TABLE pattern_stops
    ADD CONSTRAINT pattern_stop_pattern_fk FOREIGN KEY ( pattern_id )
        REFERENCES patterns ( pattern_id )
            ON DELETE CASCADE;

ALTER TABLE pattern_stops
    ADD CONSTRAINT patternstop_stop_fk FOREIGN KEY ( stop_id )
        REFERENCES stops ( stop_id )
            ON DELETE CASCADE;

ALTER TABLE stops
    ADD CONSTRAINT stop_stop_group_fk FOREIGN KEY ( stop_group_id )
        REFERENCES stop_groups ( stop_group_id )
            ON DELETE CASCADE;

ALTER TABLE trips
    ADD CONSTRAINT trip_calendar_fk FOREIGN KEY ( calendar_id )
        REFERENCES calendars ( calendar_id )
            ON DELETE CASCADE;

ALTER TABLE trips
    ADD CONSTRAINT trip_pattern_fk FOREIGN KEY ( pattern_id )
        REFERENCES patterns ( pattern_id )
            ON DELETE CASCADE;

ALTER TABLE trips
    ADD CONSTRAINT trip_vehicle_fk FOREIGN KEY ( vehicle_side_number )
        REFERENCES vehicles ( side_number )
            ON DELETE SET NULL;

ALTER TABLE vehicles
    ADD CONSTRAINT vehicle_model_fk FOREIGN KEY ( model_id )
        REFERENCES vehicle_models ( model_id )
            ON DELETE CASCADE;


/*** AUTOMATIC PRIMARY KEY SEQUENCES ***/

CREATE SEQUENCE agency_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER agency_id_trg BEFORE
    INSERT ON agencies
    FOR EACH ROW
    WHEN ( new.agency_id IS NULL )
BEGIN
    :new.agency_id := agency_id_seq.nextval;
END;
/

CREATE SEQUENCE calendar_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER calendar_id_trg BEFORE
    INSERT ON calendars
    FOR EACH ROW
    WHEN ( new.calendar_id IS NULL )
BEGIN
    :new.calendar_id := calendar_id_seq.nextval;
END;
/

CREATE SEQUENCE calendar_exception_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER calendar_exception_id_trg BEFORE
    INSERT ON calendar_exceptions
    FOR EACH ROW
    WHEN ( new.calendar_exception_id IS NULL )
BEGIN
    :new.calendar_exception_id := calendar_exception_id_seq.nextval;
END;
/

CREATE SEQUENCE stop_group_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER stop_group_id_trg BEFORE
    INSERT ON stop_groups
    FOR EACH ROW
    WHEN ( new.stop_group_id IS NULL )
BEGIN
    :new.stop_group_id := stop_group_id_seq.nextval;
END;
/

CREATE SEQUENCE stop_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER stop_id_trg BEFORE
    INSERT ON stops
    FOR EACH ROW
    WHEN ( new.stop_id IS NULL )
BEGIN
    :new.stop_id := stop_id_seq.nextval;
END;
/

CREATE SEQUENCE vehicle_model_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER vehicle_model_id_trg BEFORE
    INSERT ON vehicle_models
    FOR EACH ROW
    WHEN ( new.model_id IS NULL )
BEGIN
    :new.model_id := vehicle_model_id_seq.nextval;
END;
/

CREATE SEQUENCE line_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER line_id_trg BEFORE
    INSERT ON lines
    FOR EACH ROW
    WHEN ( new.line_id IS NULL )
BEGIN
    :new.line_id := line_id_seq.nextval;
END;
/

CREATE SEQUENCE pattern_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER pattern_id_trg BEFORE
    INSERT ON patterns
    FOR EACH ROW
    WHEN ( new.pattern_id IS NULL )
BEGIN
    :new.pattern_id := pattern_id_seq.nextval;
END;
/

CREATE SEQUENCE pattern_stop_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER pattern_stop_id_trg BEFORE
    INSERT ON pattern_stops
    FOR EACH ROW
    WHEN ( new.pattern_stop_id IS NULL )
BEGIN
    :new.pattern_id := pattern_stop_id_seq.nextval;
END;
/

CREATE SEQUENCE trip_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER trip_id_trg BEFORE
    INSERT ON trips
    FOR EACH ROW
    WHEN ( new.trip_id IS NULL )
BEGIN
    :new.trip_id := trip_id_seq.nextval;
END;
/


/*** CONSTRAINTS FOR BOOLEAN FIELDS ***/

ALTER TABLE calendars
    ADD CONSTRAINT calendar_monday_boolean CHECK ( monday IN ( 'T', 'F' ) );

ALTER TABLE calendars
    ADD CONSTRAINT calendar_tuesday_boolean CHECK ( tuesday IN ( 'T', 'F' ) );

ALTER TABLE calendars
    ADD CONSTRAINT calendar_wednesday_boolean CHECK ( wednesday IN ( 'T', 'F' ) );

ALTER TABLE calendars
    ADD CONSTRAINT calendar_thursday_boolean CHECK ( thursday IN ( 'T', 'F' ) );

ALTER TABLE calendars
    ADD CONSTRAINT calendar_friday_boolean CHECK ( friday IN ( 'T', 'F' ) );

ALTER TABLE calendars
    ADD CONSTRAINT calendar_saturday_boolean CHECK ( saturday IN ( 'T', 'F' ) );

ALTER TABLE calendars
    ADD CONSTRAINT calendar_sunday_boolean CHECK ( sunday IN ( 'T', 'F' ) );

ALTER TABLE calendar_exceptions
    ADD CONSTRAINT calendar_exception_added_boolean CHECK ( added IN ( 'T', 'F' ) );


/*** CONSTRAINTS FOR ENUM FIELDS ***/

-- wheelchair_accessible: 0 - unknown, 1 - accessible, 2 - inaccessible
ALTER TABLE stops
    ADD CONSTRAINT stop_wheelchair_accessible_enum CHECK ( wheelchair_accessible BETWEEN 0 AND 2 );

ALTER TABLE trips
    ADD CONSTRAINT trip_wheelchair_accessible_enum CHECK ( wheelchair_accessible BETWEEN 0 AND 2 );

ALTER TABLE vehicles
    ADD CONSTRAINT vehicle_wheelchair_accessible_enum CHECK ( wheelchair_accessible BETWEEN 0 AND 2 );

-- line_type: 0 - tram, 1 - metro, 2 - rail, 3 - bus
ALTER TABLE lines
    ADD CONSTRAINT line_type_enum CHECK ( type BETWEEN 0 AND 3 );

ALTER TABLE vehicle_models
    ADD CONSTRAINT vehicle_model_traction_type_enum CHECK ( traction_type BETWEEN 0 AND 3 );

-- direction: 0 - outbound, 1 - inbound
ALTER TABLE patterns
    ADD CONSTRAINT pattern_direction_enum CHECK ( direction BETWEEN 0 AND 1 );


/*** CONSTRAINTS FOR NON-NEGATIVE FIELDS ***/

ALTER TABLE pattern_shape_points
    ADD CONSTRAINT pattern_shape_point_index_non_negative CHECK ( idx >= 0 );

ALTER TABLE pattern_stops
    ADD CONSTRAINT pattern_stop_index_non_negative CHECK ( idx >= 0 );

ALTER TABLE pattern_stops
    ADD CONSTRAINT pattern_stop_travel_time_non_negative CHECK ( travel_time >= 0 );

ALTER TABLE trips
    ADD CONSTRAINT trip_departure_non_negative CHECK ( departure >= 0 );


/*** CONSTRAINTS FOR TIME PERIODS ***/

ALTER TABLE calendars
    ADD CONSTRAINT calendar_end_after_start CHECK ( end_date >= start_date );


/*** CONSTRAINTS FOR LATITUDE AND LONGITUDE ***/

ALTER TABLE stops
    ADD CONSTRAINT stop_lat CHECK ( lat BETWEEN -90 AND 90 );

ALTER TABLE stops
    ADD CONSTRAINT stop_lon CHECK ( lon BETWEEN -180 AND 180 );

ALTER TABLE stop_groups
    ADD CONSTRAINT stop_group_lat CHECK ( lat BETWEEN -90 AND 90 );

ALTER TABLE stop_groups
    ADD CONSTRAINT stop_group_lon CHECK ( lon BETWEEN -180 AND 180 );

ALTER TABLE pattern_shape_points
    ADD CONSTRAINT pattern_shape_point_lat CHECK ( lat BETWEEN -90 AND 90 );

ALTER TABLE pattern_shape_points
    ADD CONSTRAINT pattern_shape_point_lon CHECK ( lon BETWEEN -180 AND 180 );
