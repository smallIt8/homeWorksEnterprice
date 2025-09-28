--Create table person
CREATE TABLE person (
    person_id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL CONSTRAINT unique_person_user_name_key UNIQUE,
    password VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL CONSTRAINT unique_person_email_key UNIQUE,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--Create table transaction
CREATE TABLE transaction(
    transaction_id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    transaction_name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL
        CONSTRAINT chk_transaction_type
        CHECK ((type)::text = ANY ((ARRAY ['INCOME'::character varying, 'EXPENSE'::character varying])::text[])),
    category_id UUID NOT NULL CONSTRAINT transaction_category_category_id_fk REFERENCES category,
    amount NUMERIC(10, 2) NOT NULL,
    person_id UUID NOT NULL CONSTRAINT transaction_person_person_id_fk REFERENCES person ON DELETE CASCADE,
    transaction_date date NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create index transaction_type_index
    on transaction (type);

--Create table budget
CREATE TABLE budget(
    budget_id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    budget_name VARCHAR(100) NOT NULL,
    category_id UUID NOT NULL CONSTRAINT budget_category_category_id_fk REFERENCES category,
    budget_limit NUMERIC(10, 2) NOT NULL,
    period DATE NOT NULL,
    person_id UUID NOT NULL CONSTRAINT budget_person_person_id_fk REFERENCES person ON DELETE CASCADE,
    CONSTRAINT unique_budget_per_category_per_period_per_person_key UNIQUE (category_id, period, person_id)
);

CREATE INDEX budget_person_index
    ON budget(person_id);

--Create table financial_goal
CREATE TABLE financial_goal (
    financial_goal_id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    financial_goal_name VARCHAR(100) NOT NULL,
    target_amount NUMERIC(10,2) NOT NULL,
    end_date DATE NOT NULL,
    person_id UUID NOT NULL CONSTRAINT financial_goal_person_person_id_fk REFERENCES person ON DELETE CASCADE,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_financial_goal_per_financial_goal_name_per_person_key UNIQUE (financial_goal_name, person_id)
);

CREATE INDEX financial_goal_person_index
    ON financial_goal(person_id);

--Create table category
CREATE TABLE category (
    category_id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL
        CONSTRAINT chk_category_type
        CHECK ((type)::text = ANY ((ARRAY['INCOME'::character varying, 'EXPENSE'::character varying])::text[])),
    person_id UUID NOT NULL CONSTRAINT category_person_id_fk REFERENCES person,
    CONSTRAINT unique_category_per_category_name_per_person_key UNIQUE (category_name, person_id)
);

CREATE INDEX category_type_index
    ON category (type);

--Create table family
CREATE TABLE family (
    family_id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    family_name VARCHAR(100) NOT NULL CONSTRAINT unique_family_name_key UNIQUE,
    person_id UUID NOT NULL CONSTRAINT family_person_person_id_fk REFERENCES person ON DELETE CASCADE
);

CREATE INDEX family_person_index
    ON family(person_id);

--Create table family_person(@ManyToMany)
CREATE TABLE family_person (
    family_id UUID NOT NULL CONSTRAINT family_person_family_family_id_fk REFERENCES family(family_id) ON DELETE CASCADE,
    person_id UUID NOT NULL CONSTRAINT family_person_person_person_id_fk REFERENCES person(person_id) ON DELETE CASCADE,
    PRIMARY KEY (person_id, family_id)
);