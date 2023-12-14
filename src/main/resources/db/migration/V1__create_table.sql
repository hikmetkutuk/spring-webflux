CREATE TABLE course
(
    course_id uuid PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    duration int4 NOT NULL,
    course_metadata jsonb NOT NULL
);

CREATE TABLE student
(
    id uuid PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(255) NOT NULL,
    course_id VARCHAR[]
);