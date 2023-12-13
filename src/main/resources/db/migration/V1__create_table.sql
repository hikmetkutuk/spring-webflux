CREATE TABLE course
(
    courseId uuid PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    duration VARCHAR(255) NOT NULL,
    course_metadata VARCHAR(255) NOT NULL
);

Create table student
(
    studentId uuid PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(255) NOT NULL,
    course_id VARCHAR[]
);