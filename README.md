## Project Detail
This project tries to cover all the Scenerios for the JSONB implementaion which developers will be facing in day to day life when using spring data JPA.

## What is JSONB 
The data types json and jsonb, as defined by the PostgreSQL documentation,are almost identical; the key difference is that json data is stored as an exact copy of the JSON input text, whereas jsonb stores data in a decomposed binary form; that is, not as an ASCII/UTF-8 string, but as binary code.

## What are the benifits of JSONB 
- More efficiency,
- Significantly faster to process,
- Supports indexing (which can be a significant advantage, as we'll see later),
- Simpler schema designs (replacing entity-attribute-value (EAV) tables with jsonb columns, which can be queried,  indexed and joined, allowing for performance improvements up until 1000X!)

## What are the drawbacks of JSONB 
- Slightly slower input (due to added conversion overhead),
- It may take more disk space than plain json due to a larger table footprint, though not always,
- Certain queries (especially aggregate ones) may be slower due to the lack of statistics.

## Scenerios
- Working on simple json document using jsonb 
- Working on complex json document using jsonb 

Please see `StudentController.java` as a starting for that.

We have used dependency from `https://github.com/vladmihalcea/hibernate-types` for modeling our pojo's.

````xml
<dependency>
    <groupId>com.vladmihalcea</groupId>
    <artifactId>hibernate-types-52</artifactId>
    <version>2.10.4</version>
</dependency>
````

For jsonb type we have to define the columns type as jsonb as follows:

````java
@Type(type = "jsonb")
````

## Project Setup:
- clone this repo.
- Update the database configutaions.
- Create student table using below query:
````sql
CREATE TABLE student (
	id varchar(255) NOT NULL,
	age varchar(255) NULL,
	"name" varchar(255) NULL,
	bio jsonb NULL,
	CONSTRAINT student_pkey PRIMARY KEY (id)
);
````

- Insert scripts used:

````sql
INSERT INTO public.student
(id, age, "name", bio)
VALUES('1', '26', 'hakuna_matata', '{"professional": {"xyz": true}, "personal": {"xyx": "test"}}');

````
- Run the project in console using `mvn spring-boot:run`


