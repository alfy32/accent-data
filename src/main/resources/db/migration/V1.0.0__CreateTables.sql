CREATE TABLE employee (
  employee_id SERIAL PRIMARY KEY,
  name VARCHAR (80),
  phone_number VARCHAR (15),
  address TEXT
);

CREATE TABLE appointment (
  appointment_id SERIAL PRIMARY KEY,
  employee_id INTEGER NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
  created TIMESTAMP DEFAULT now(),
  start_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP NOT NULL,
  client TEXT NOT NULL
);