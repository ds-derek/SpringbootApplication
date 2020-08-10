INSERT INTO ROLE (name) values('ROLE_ADMIN');
INSERT INTO ROLE (name) values('ROLE_USER');
INSERT INTO ACCOUNT (email_addr, name, password, activated) values ('test@naver.com', 'myName','{bcrypt}$2a$10$vTSKjhCXJfpRZzp0WDvH.e7F.ayqDcY0L2waxjg/8xCg1yHGO9pQq', true);
INSERT INTO ACCOUNT_ROLES (ACCOUNT_ID, ROLES_ID) values (1,2)