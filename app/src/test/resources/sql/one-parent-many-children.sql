create table parent1
(
    parent1_id int primary key
);

create table child1
(
    child1_id int primary key,
    CONSTRAINT `fk_1` FOREIGN KEY (`child1_id`) REFERENCES `parent1` (`parent1_id`)
);

create table child2
(
    child2_id int primary key,
    CONSTRAINT fk_2 FOREIGN KEY (child2_id) REFERENCES child1 (child1_id)
);

create table child3
(
    child3_id int primary key,
    CONSTRAINT fk_3 FOREIGN KEY (child3_id) REFERENCES child2 (child2_id)
);

create table child4
(
    child4_id int primary key,
    CONSTRAINT fk_4 FOREIGN KEY (child4_id) REFERENCES child3 (child3_id)
);

create table child5
(
    child5_id int primary key,
    CONSTRAINT fk_5 FOREIGN KEY (child5_id) REFERENCES child4 (child4_id)
);

create table child6
(
    child6_id int primary key,
    CONSTRAINT fk_6 FOREIGN KEY (child6_id) REFERENCES child5 (child5_id)
);

create table child7
(
    child7_id int primary key,
    CONSTRAINT fk_7 FOREIGN KEY (child7_id) REFERENCES child6 (child6_id)
);

create table child8
(
    child8_id int primary key,
    CONSTRAINT fk_8 FOREIGN KEY (child8_id) REFERENCES child7 (child7_id)
);

create table child9
(
    child9_id int primary key,
    CONSTRAINT fk_9 FOREIGN KEY (child9_id) REFERENCES child8 (child8_id)
);

create table child10
(
    child10_id int primary key,
    CONSTRAINT fk_10 FOREIGN KEY (child10_id) REFERENCES child9 (child9_id)
);

INSERT INTO parent1(parent1_id) VALUES (100500);
INSERT INTO child1(child1_id) VALUES (100500);
INSERT INTO child2(child2_id) VALUES (100500);
INSERT INTO child3(child3_id) VALUES (100500);
INSERT INTO child4(child4_id) VALUES (100500);
INSERT INTO child5(child5_id) VALUES (100500);
INSERT INTO child6(child6_id) VALUES (100500);
INSERT INTO child7(child7_id) VALUES (100500);
INSERT INTO child8(child8_id) VALUES (100500);
INSERT INTO child9(child9_id) VALUES (100500);
INSERT INTO child10(child10_id) VALUES (100500);
