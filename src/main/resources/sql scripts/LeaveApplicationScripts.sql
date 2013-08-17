    alter table APPLICATION 
        drop 
        foreign key FKDCF79930BD244FF0;

    alter table APPLICATION 
        drop 
        foreign key FKDCF799305AA208CE;
        
    alter table MESSAGES 
        drop 
        foreign key FK131AF14CC619798B;

    alter table MESSAGES 
        drop 
        foreign key FK131AF14CC5E9705C;



    alter table USERS 
        drop 
        foreign key FK4D495E817F98C10;

    alter table USERS 
        drop 
        foreign key FK4D495E85AA208CE;

    drop table if exists APPLICATION;
    
    drop table if exists MESSAGES;

    drop table if exists ROLES;

    drop table if exists USERS;


    create table APPLICATION (
        APPLICATION_ID bigint not null auto_increment,
        CONTENT varchar(255),
        STATUS char(1),
        DATE datetime,
        USER_ID bigint not null,
        MANAGER_ID bigint,
        primary key (APPLICATION_ID)
    );

      create table MESSAGES (
        MESSAGE_ID bigint not null auto_increment,
        CONTENT varchar(255),
        CREATION_DATE datetime,
        isREAD bit,
        FROM_USER bigint,
        TO_USER bigint,
        primary key (MESSAGE_ID)
    );
    
    create table ROLES (
        ROLE_ID bigint not null,
        ROLE_NAME varchar(255),
        primary key (ROLE_ID)
    );

    create table USERS (
        USER_ID bigint not null auto_increment,
        PASSWORD varchar(255) not null,
        USERNAME varchar(255) not null,
        MANAGER_ID bigint default 1,
        ROLE_ID bigint default 1 not null,
        primary key (USER_ID),
        unique (USERNAME)
    );

    alter table APPLICATION 
        add index FKDCF79930BD244FF0 (USER_ID), 
        add constraint FKDCF79930BD244FF0 
        foreign key (USER_ID) 
        references USERS (USER_ID);

    alter table APPLICATION 
        add index FKDCF799305AA208CE (MANAGER_ID), 
        add constraint FKDCF799305AA208CE 
        foreign key (MANAGER_ID) 
        references USERS (USER_ID);
        
        alter table MESSAGES 
        add index FK131AF14CC619798B (FROM_USER), 
        add constraint FK131AF14CC619798B 
        foreign key (FROM_USER) 
        references USERS (USER_ID);

    	alter table MESSAGES 
        add index FK131AF14CC5E9705C (TO_USER), 
        add constraint FK131AF14CC5E9705C 
        foreign key (TO_USER) 
        references USERS (USER_ID);

    alter table USERS 
        add index FK4D495E817F98C10 (ROLE_ID), 
        add constraint FK4D495E817F98C10 
        foreign key (ROLE_ID) 
        references ROLES (ROLE_ID);

    alter table USERS 
        add index FK4D495E85AA208CE (MANAGER_ID), 
        add constraint FK4D495E85AA208CE 
        foreign key (MANAGER_ID) 
        references USERS (USER_ID);

INSERT INTO `LeaveApplication`.`ROLES` (`ROLE_ID`, `ROLE_NAME`) VALUES (1, 'Manager');
INSERT INTO `LeaveApplication`.`ROLES` (`ROLE_ID`, `ROLE_NAME`) VALUES (2, 'User');
INSERT INTO `LeaveApplication`.`ROLES` (`ROLE_ID`, `ROLE_NAME`) VALUES (3, 'SYSTEM');