--SYSTEM 계정에서 해야한느 것들...
-- CREATE USER TIS001 IDENTIFIED BY 1234;   생성하기...
-- CREATE ROLE USER_ROLE;   ROLE 생성
-- GRANT CONNECT, RESOURCE,CREATE VIEW, CREATE TABLE, CREATE SYNONYM, CREATE SEQUENCE TO USER_ROLE;
-- GRANT USER_ROLE TO TIS001;

CREATE TABLE MEMBER(
  ID NUMBER(4) CONSTRAINT TABLE1_PK PRIMARY KEY  , 
  NAME VARCHAR2(20) , 
  EMAIL VARCHAR2(100) , 
  HP VARCHAR2(13),
  REGDATE DATE
);
DROP TABLE MEMBER;
SELECT * FROM MEMBER;
INSERT INTO MEMBER VALUES (SEQ_MEMBER.NEXTVAL,'JJANG051','JJANG051@HANMAIL.NET','010-2582-2242',SYSDATE);
commit;

-- SEQUENCE 생성
--CREATE SEQUENCE SEQ_MEMBER2 
--INCREMENT BY 1 
--START WITH 1000 
--MAXVALUE 9999 
--MINVALUE 1000 
--CYCLE CACHE 4;
SELECT * FROM MEMBER;
DELETE FROM MEMBER
WHERE ID = 1002;
ROLLBACK;


