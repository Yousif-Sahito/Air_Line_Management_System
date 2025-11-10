
USE airlinemanagementsystem;

CREATE TABLE passenger (
    username VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    dob VARCHAR(20),
    address VARCHAR(200),
    phone VARCHAR(20),
    email VARCHAR(100),
    nationality VARCHAR(50),
    gender VARCHAR(20),
    passport VARCHAR(50)
);



CREATE TABLE flight (
    f_code VARCHAR(10),
    f_name VARCHAR(50),
    source VARCHAR(50),
    destination VARCHAR(50),
    class_name VARCHAR(20),
    price INT,
    capacity INT
);


-- Optional: Clear all old data from the flight table


-- Insert the new, larger dataset
INSERT INTO flight (f_code, f_name, source, destination, class_name, price, capacity)
VALUES
/* --- Karachi <-> Lahore --- (PIA, Airblue) --- */
('PK-302', 'PIA', 'Karachi', 'Lahore', 'Economy', 15500, 140),
('PK-303', 'PIA', 'Karachi', 'Lahore', 'Normal', 19000, 90),
('PK-304', 'PIA', 'Karachi', 'Lahore', 'Business', 28000, 30),
('ER-202', 'Airblue', 'Karachi', 'Lahore', 'Economy', 15000, 150),
('ER-203', 'Airblue', 'Karachi', 'Lahore', 'Business', 27500, 25),

('PK-305', 'PIA', 'Lahore', 'Karachi', 'Economy', 15500, 140),
('PK-306', 'PIA', 'Lahore', 'Karachi', 'Normal', 19000, 90),
('PK-307', 'PIA', 'Lahore', 'Karachi', 'Business', 28000, 30),
('ER-204', 'Airblue', 'Lahore', 'Karachi', 'Economy', 15000, 150),
('ER-205', 'Airblue', 'Lahore', 'Karachi', 'Business', 27500, 25),

/* --- Karachi <-> Islamabad --- (PIA, SereneAir) --- */
('PK-310', 'PIA', 'Karachi', 'Islamabad', 'Economy', 16000, 140),
('PK-311', 'PIA', 'Karachi', 'Islamabad', 'Normal', 20000, 90),
('PK-312', 'PIA', 'Karachi', 'Islamabad', 'Business', 30000, 30),
('PA-101', 'SereneAir', 'Karachi', 'Islamabad', 'Economy', 15800, 160),
('PA-102', 'SereneAir', 'Karachi', 'Islamabad', 'Normal', 19500, 100),

('PK-313', 'PIA', 'Islamabad', 'Karachi', 'Economy', 16000, 140),
('PK-314', 'PIA', 'Islamabad', 'Karachi', 'Normal', 20000, 90),
('PK-315', 'PIA', 'Islamabad', 'Karachi', 'Business', 30000, 30),
('PA-103', 'SereneAir', 'Islamabad', 'Karachi', 'Economy', 15800, 160),
('PA-104', 'SereneAir', 'Islamabad', 'Karachi', 'Normal', 19500, 100),

/* --- Lahore <-> Islamabad --- (PIA, AirSial) --- */
('PK-320', 'PIA', 'Lahore', 'Islamabad', 'Economy', 12000, 130),
('PK-321', 'PIA', 'Lahore', 'Islamabad', 'Normal', 15000, 80),
('PF-401', 'AirSial', 'Lahore', 'Islamabad', 'Economy', 11800, 140),

('PK-322', 'PIA', 'Islamabad', 'Lahore', 'Economy', 12000, 130),
('PK-323', 'PIA', 'Islamabad', 'Lahore', 'Normal', 15000, 80),
('PF-402', 'AirSial', 'Islamabad', 'Lahore', 'Economy', 11800, 140),

/* --- Karachi <-> Quetta --- (PIA, Airblue) --- */
('PK-330', 'PIA', 'Karachi', 'Quetta', 'Economy', 14000, 120),
('PK-331', 'PIA', 'Karachi', 'Quetta', 'Normal', 17000, 70),
('ER-210', 'Airblue', 'Karachi', 'Quetta', 'Economy', 13900, 140),

('PK-332', 'PIA', 'Quetta', 'Karachi', 'Economy', 14000, 120),
('PK-333', 'PIA', 'Quetta', 'Karachi', 'Normal', 17000, 70),
('ER-211', 'Airblue', 'Quetta', 'Karachi', 'Economy', 13900, 140),

/* --- Lahore <-> Quetta --- (SereneAir) --- */
('PA-110', 'SereneAir', 'Lahore', 'Quetta', 'Economy', 13500, 150),
('PA-111', 'SereneAir', 'Lahore', 'Quetta', 'Normal', 16500, 90),

('PA-112', 'SereneAir', 'Quetta', 'Lahore', 'Economy', 13500, 150),
('PA-113', 'SereneAir', 'Quetta', 'Lahore', 'Normal', 16500, 90),

/* --- Islamabad <-> Peshawar --- (PIA) --- */
('PK-340', 'PIA', 'Islamabad', 'Peshawar', 'Economy', 9000, 110),
('PK-341', 'PIA', 'Islamabad', 'Peshawar', 'Normal', 11000, 60),

('PK-342', 'PIA', 'Peshawar', 'Islamabad', 'Economy', 9000, 110),
('PK-343', 'PIA', 'Peshawar', 'Islamabad', 'Normal', 11000, 60),

/* --- Karachi <-> Multan --- (AirSial) --- */
('PF-410', 'AirSial', 'Karachi', 'Multan', 'Economy', 13000, 140),
('PF-411', 'AirSial', 'Karachi', 'Multan', 'Normal', 16000, 80),

('PF-412', 'AirSial', 'Multan', 'Karachi', 'Economy', 13000, 140),
('PF-413', 'AirSial', 'Multan', 'Karachi', 'Normal', 16000, 80),

/* --- Lahore <-> Multan --- (PIA) --- */
('PK-350', 'PIA', 'Lahore', 'Multan', 'Economy', 10500, 130),

('PK-351', 'PIA', 'Multan', 'Lahore', 'Economy', 10500, 130),

/* --- Islamabad <-> Sialkot --- (AirSial) --- */
('PF-420', 'AirSial', 'Islamabad', 'Sialkot', 'Economy', 9500, 140),
('PF-421', 'AirSial', 'Islamabad', 'Sialkot', 'Business', 15000, 20),

('PF-422', 'AirSial', 'Sialkot', 'Islamabad', 'Economy', 9500, 140),
('PF-423', 'AirSial', 'Sialkot', 'Islamabad', 'Business', 15000, 20),

/* --- Karachi <-> Faisalabad --- (SereneAir) --- */
('PA-120', 'SereneAir', 'Karachi', 'Faisalabad', 'Economy', 14200, 150),

('PA-121', 'SereneAir', 'Faisalabad', 'Karachi', 'Economy', 14200, 150);

select * from flight;

CREATE TABLE booking (
    ticket_id VARCHAR(20) PRIMARY KEY,
    source VARCHAR(100),
    destination VARCHAR(100),
    class_name VARCHAR(50),
    f_name VARCHAR(50),
   
    price VARCHAR(20),
    flight_code VARCHAR(20),
    username VARCHAR(50),
    name VARCHAR(100),
    age VARCHAR(10),
    dob VARCHAR(20),
    passport VARCHAR(50),
    j_time VARCHAR(20),
    j_date VARCHAR(20),
    status VARCHAR(20) DEFAULT 'Success',
    cancellation_reason VARCHAR(255)
);



-- Insert sample data
INSERT INTO booking (
    ticket_id,
    source,
    destination,
    class_name,
    f_name,
    price,
    flight_code,
    username,
    name,
    age,
    dob,
    passport,
    j_time,
    j_date,
    status
)
VALUES (
    'T1001',
    'Karachi',
    'Lahore',
    'Economy',
    'PIA',
    '15000',
    'PK301',
    'ali123',
    'Ali Ahmed',
    '28',
    '1997-03-12',
    'AB1234567',
    '10:30 AM',
    '2025-11-05',
    'Success'
);

CREATE TABLE cancel (
    /*cancellation_id INT AUTO_INCREMENT PRIMARY KEY,*/
    ticket_id VARCHAR(20) NOT NULL,
    source VARCHAR(100),
    destination VARCHAR(100),
    class_name VARCHAR(50),
    f_name VARCHAR(50),
    price VARCHAR(20),
    flight_code VARCHAR(20),
    username VARCHAR(50),
    passenger_name VARCHAR(100),
    age VARCHAR(10),
    dob VARCHAR(20),
    passport VARCHAR(50),
    journey_time VARCHAR(20),
    journey_date VARCHAR(20),
    cancellation_reason VARCHAR(255) NOT NULL,
    cancellation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    refund_status VARCHAR(20) DEFAULT 'Pending',
    refund_amount VARCHAR(20),
    FOREIGN KEY (ticket_id) REFERENCES booking(ticket_id) ON DELETE CASCADE
);


	INSERT INTO cancel (
		ticket_id,
		source,
		destination,
		class_name,
		f_name,
		price,
		flight_code,
		username,
		passenger_name,
		age,
		dob,
		passport,
		journey_time,
		journey_date,
		cancellation_reason,
		refund_status,
		refund_amount
	) VALUES (
		'T1001',
		'Karachi',
		'Lahore',
		'Economy',
		'Pakistan International Airlines',
		'15000',
		'PK301',
		'ali123',
		'Ali Ahmed',
		'28',
		'1997-03-12',
		'AB1234567',
		'10:30 AM',
		'2025-11-05',
		'Change of travel plans due to emergency',
		'Processed',
		'13500'
	);






CREATE TABLE signup (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    password VARCHAR(255) NOT NULL,
    security_question VARCHAR(255) NOT NULL,
    security_answer VARCHAR(255) NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
