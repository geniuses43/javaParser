CREATE TABLE bd6_departments(
  id number(38) PRIMARY KEY,
  name varchar2(64 CHAR),
  postal_code varchar2(6 CHAR),
  street varchar2(64 CHAR),
  building varchar2(16 CHAR),
  city varchar2(32 CHAR)
);
CREATE TABLE bd6_employees(
  id number(38) PRIMARY KEY,
  last_name varchar2(64 CHAR) NOT NULL,
  first_name varchar2(64 CHAR) NOT NULL,
  phone_number varchar2(18 CHAR),
  email varchar2(32 CHAR),
  department_id number(38) REFERENCES bd6_departments(id) NOT NULL,
  manager_id number(38) REFERENCES bd6_employees(id),
  salary_in_euro number(8, 2)  DEFAULT 0 NOT NULL,
  UNIQUE(last_name, first_name, department_id)
);

INSERT ALL
INTO bd6_departments VALUES(10, 'Administration', '109658', 'Leningradskoe shosse', '1', 'Moscow')
INTO bd6_departments VALUES(20, 'Marketing', '107701', 'Lenina', '22a', 'Volgograd')
INTO bd6_departments VALUES(30, 'Purchasing', '109901', 'Mikluho-Maklaya', '8', 'Bryansk')
INTO bd6_departments VALUES(40, 'Human Resources', '10967', '5-ya parkovaya', '16', 'Moscow')
INTO bd6_departments VALUES(50, 'Shipping', '109659', '38 Bakinskih komissarov', '77', 'Moscow')
INTO bd6_departments VALUES(60, 'IT', '109902', 'Pervomajskaya', '33', 'Kirov')
SELECT 1 FROM DUAL;

INSERT ALL
INTO bd6_employees VALUES(1, 'Radygin', 'Victor', '8-(495)-567-7788', 'vr@e.mail.mephi.ru', 10, NULL, 8000)
INTO bd6_employees VALUES(2, 'Kuprijanov', 'Dmitrij', '8-(495)-567-7794', 'kd@e.mail.mephi.ru', 60, 1, 6534.33)
INTO bd6_employees VALUES(3, 'Ivanov-Skladovskij', 'Ivan', '8-(495)-567-7799', 'ii1@mail.mephi.ru', 20, 1, 4404.14)
INTO bd6_employees VALUES(4, 'Petrov', 'Petr', '8-(495)-567-7794', 'petrovpetr@m.gmail.ru', 60, 2, 3456.43)
INTO bd6_employees VALUES(5, 'kozlov', 'Konstantin', '8-(495)-567-7794', 'kkozlov@mephi.ru', 60, 2, 2300)
INTO bd6_employees VALUES(6, 'Abramov', 'Abram', '8-(495)-567-7794', 'abramova@k75.mephi.ru', 60, 2, 2200.11)
INTO bd6_employees VALUES(7, 'Ivanovà-Skladovskàya-Petrova', 'Ivanka', '8-(495)-567-7794', 'ii2@mail.mephi.ru', 60, 4, 3756.33)
INTO bd6_employees VALUES(8, 'Petrov', 'Ivan', '8-(495)-567-7799', 'petrovivan@m.gmail.ru', 20, 3, 4850)
INTO bd6_employees VALUES(9, 'kozlov', 'Ivan', '8-(495)-567-7799', 'ikozlov@mephi.ru', 20, 3, 3460)
INTO bd6_employees VALUES(10, 'Abramov', 'Moisej', '8-(495)-567-7794', 'abramovm@k75.mephi.ru', 60, 4, 2345)
INTO bd6_employees VALUES(11, 'Petrov', 'Alex', '8-(495)-567-7799', 'petroval@m.gmail.ru', 20, 3, 2465)
INTO bd6_employees VALUES(12, 'kozlov', 'Maxim', '8-(495)-567-7799', 'mkozlov@mephi.ru', 20, 3, 2788)
INTO bd6_employees VALUES(13, 'Abramov', 'Isyaslav', '8-(495)-567-7738', 'abramovi@k75.mephi.ru', 30, 1, 6500)
SELECT 1 FROM DUAL;
