--liquibase formatted sql
--changeset yong:2020022301

create table user
(
    user_id        bigint                                 not null primary key,
    email          varchar(200)                           not null comment '로그인용',
    password       varchar(100)                           null,
    username       varchar(50)                            not null,
    is_enabled     tinyint(1) default 0                   not null,
    withdraw       tinyint(1) default 0                   not null,
    last_login_dt  datetime   default current_timestamp() not null,
    create_dt      datetime   default current_timestamp() not null,
    create_user_id bigint                                 not null,
    update_dt      datetime   default current_timestamp() not null on update current_timestamp(),
    update_user_id bigint                                 not null
);


create table role_type
(
    role_id        bigint                               not null primary key,
    role_eng_name  varchar(50)                          not null comment '실제 권한맵핑시 영문명 사용',
    role_name      varchar(50)                          null,
    create_dt      datetime default current_timestamp() not null,
    create_user_id bigint                               not null,
    update_dt      datetime default current_timestamp() not null on update current_timestamp(),
    update_user_id bigint                               not null
);

create table user_role_rel
(
    user_role_rel_id bigint                               not null
        primary key,
    create_dt        datetime default current_timestamp() not null,
    create_user_id   bigint                               not null,
    update_dt        datetime default current_timestamp() not null on update current_timestamp(),
    update_user_id   bigint                               not null,
    role_id          bigint                               not null,
    user_id          bigint                               not null,

    constraint FK_role_type_TO_user_role_rel_1
        foreign key (role_id) references role_type (role_id),
    constraint FK_user_TO_user_role_rel_1
        foreign key (user_id) references user (user_id)
);

create table menu
(
    menu_id        bigint                               not null
        primary key,
    menu_name      varchar(100)                         null,
    menu_dtl_name  varchar(100)                         null,
    scope_name     varchar(100)                         not null,
    create_dt      datetime default current_timestamp() not null,
    create_user_id bigint                               not null,
    update_dt      datetime default current_timestamp() not null on update current_timestamp(),
    update_user_id bigint                               not null
);

create table role_type_menu_rel
(
    user_type_menu_rel_id bigint                               not null
        primary key,
    create_dt             datetime default current_timestamp() not null,
    create_user_id        bigint                               not null,
    update_dt             datetime default current_timestamp() not null on update current_timestamp(),
    update_user_id        bigint                               not null,
    menu_id               bigint                               not null,
    role_id               bigint                               not null,
    constraint FK_menu_TO_role_type_menu_rel_1
        foreign key (menu_id) references menu (menu_id),
    constraint FK_role_type_TO_role_type_menu_rel_1
        foreign key (role_id) references role_type (role_id)
);




/**
 * spring 에서 필요한 테이블(인증 & sequence)
 */

/* jpa 시퀀스 테이블 */
DROP TABLE IF EXISTS `hibernate_sequence`;

create table hibernate_sequence
(
    next_val bigint null
)
    engine = MyISAM;

/* id값들을 1억 이상으로 디폴트 셋팅 */
insert into hibernate_sequence (next_val)
values (100000000);

DROP TABLE IF EXISTS oauth_client_details;

create table oauth_client_details
(
    client_id               VARCHAR(256) PRIMARY KEY,
    resource_ids            VARCHAR(256),
    client_secret           VARCHAR(256),
    scope                   VARCHAR(256),
    authorized_grant_types  VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(256)
) ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS oauth_client_token;

create table oauth_client_token
(
    token_id          VARCHAR(256),
    token             LONGBLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256)
) ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS oauth_access_token;

create table oauth_access_token
(
    token_id          VARCHAR(256),
    token             LONGBLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    LONGBLOB,
    refresh_token     VARCHAR(256)
) ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS oauth_refresh_token;

create table oauth_refresh_token
(
    token_id       VARCHAR(256),
    token          LONGBLOB,
    authentication LONGBLOB
) ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS oauth_code;

create table oauth_code
(
    code           VARCHAR(256),
    authentication LONGBLOB
) ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS oauth_approvals;

create table oauth_approvals
(
    userId         VARCHAR(256),
    clientId       VARCHAR(256),
    scope          VARCHAR(256),
    status         VARCHAR(10),
    expiresAt      TIMESTAMP,
    lastModifiedAt TIMESTAMP
);


-- customized oauth_client_details table
-- create table ClientDetails (
--   appId VARCHAR(256) PRIMARY KEY,
--   resourceIds VARCHAR(256),
--   appSecret VARCHAR(256),
--   scope VARCHAR(256),
--   grantTypes VARCHAR(256),
--   redirectUrl VARCHAR(256),
--   authorities VARCHAR(256),
--   access_token_validity INTEGER,
--   refresh_token_validity INTEGER,
--   additionalInformation VARCHAR(4096),
--   autoApproveScopes VARCHAR(256)
-- );

-- client_secret은 "secret" bcrypt로 암호화
INSERT INTO oauth_client_details
(client_id, client_secret, scope, authorized_grant_types,
 web_server_redirect_uri, authorities, access_token_validity,
 refresh_token_validity, additional_information, autoapprove)
VALUES ('emotion', '$2a$10$V/yB6pY4IuMp6crt7IH.ae57jM1Zt0hyeACQsQgySEDQ4Ces/yC7.', 'read,write',
        'password, refresh_token, authorization_code', null, null, 36000, 36000, null, true);

-- 롤타입이 필요
insert into role_type (role_id, role_eng_name, role_name, create_user_id, update_user_id)
values (1, 'ROLE_ADMIN', '관리자', 0, 0),
       (2, 'ROLE_USER', '사용자', 0, 0);


