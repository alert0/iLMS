--查询发送配置、接收配置、对照配置、参数、接收服务状态主键策略
DROP TABLE S_IF_CONFIG;
CREATE TABLE S_IF_CONFIG(
   NEXT_HI NUMBER(19)
);
INSERT INTO S_IF_CONFIG(NEXT_HI)VALUES(100000);--过滤手动部分
COMMIT;

--文件日志主键策略
DROP TABLE S_IF_XML_FILE_LOG;
CREATE TABLE S_IF_XML_FILE_LOG(
   NEXT_HI NUMBER(19)
);
INSERT INTO S_IF_XML_FILE_LOG(NEXT_HI)VALUES(1);
COMMIT;

--接收日志主键策略
DROP TABLE S_IF_RECEIVE_LOG; 
CREATE TABLE S_IF_RECEIVE_LOG(
   NEXT_HI NUMBER(19)
);
INSERT INTO S_IF_RECEIVE_LOG(NEXT_HI)VALUES(1);
COMMIT;

--查询发送日志主键策略
DROP TABLE S_IF_QUERY_LOG; 
CREATE TABLE S_IF_QUERY_LOG(
   NEXT_HI NUMBER(19)
);
INSERT INTO S_IF_QUERY_LOG(NEXT_HI)VALUES(1);
COMMIT;


DROP TABLE T_IF_TIMER;
CREATE TABLE T_IF_TIMER (
    JOB_CODE               VARCHAR2(40),
    JOB_DESC               VARCHAR2(255),
    IF_CODE                VARCHAR2(20),
    MASTER_QUERY_TABLE_ID  VARCHAR2(255),
    JOB_GROUP              VARCHAR2(40),
    JOB_CLASS              VARCHAR2(200),
    TRIGGER_NAME           VARCHAR2(40),
    TRIGGER_VALUE          VARCHAR2(40),
    START_TIME             DATE,
    UPDATE_STATE           NUMBER(2),
    RUN_STATE              NUMBER(2),
    LAST_RUN_TIME          DATE,
    NEXT_RUN_TIME          DATE,
    THREAD_STATE           NUMBER(2),
    THREAD_RUN_TIME        DATE,
    PRIMARY KEY   (JOB_CODE)
);

DROP TABLE T_IF_CONFIG;
CREATE TABLE T_IF_CONFIG (
    IF_CODE                   VARCHAR2(20),
    IF_NAME                   VARCHAR2(255),
    IF_NAME_ZH                VARCHAR2(255),
    TO_SYS_NAME               VARCHAR2(64),
    IF_TYPE                   NUMBER(2),
    SERVICE_TYPE              VARCHAR2(10),
    SERVICE_CLASS             VARCHAR2(255),
    RECEIVE_TRANSATION_TYPE   NUMBER(2),
    RECEIVE_DELETE_FLAG       VARCHAR2(64),
    SEND_SUCCESS_FLAG         VARCHAR2(64),
    DB_PROC                   VARCHAR2(255),
    PRIMARY KEY   (IF_CODE)
);

DROP TABLE T_IF_QUERY_CONFIG;
CREATE TABLE T_IF_QUERY_CONFIG (
    PK_ID              NUMBER(19),
    IF_CODE            VARCHAR2(20),
    TABLE_ID           VARCHAR2(64),
    TABLE_DESC         VARCHAR2(255),
    PARENT_TABLE_ID    VARCHAR2(64),
    APPROACH           VARCHAR2(255),
    APPROACH_SUFFIX    VARCHAR2(255),
    APPROACH_PREFIX    VARCHAR2(255),
    XML_ATTR           VARCHAR2(2000),
    AUTO_QUERY_SQL     VARCHAR2(2000),
    HAND_QUERY_SQL     VARCHAR2(2000),
    EMPTY_FROM_DEFAULT NUMBER(2),
    ALLOW_EMPTY        NUMBER(2),
    MAX_NUMBER         NUMBER(5),
    SUCCESS_SQL        VARCHAR2(2000),
    FAILURE_SQL        VARCHAR2(2000),
    SORT_NO            NUMBER(5),
    PRIMARY KEY   (PK_ID)
);
CREATE INDEX IDX_IF_QUERY_CONFIG ON T_IF_QUERY_CONFIG(IF_CODE);

DROP TABLE T_IF_RECEIVE_CONFIG;
CREATE TABLE T_IF_RECEIVE_CONFIG (
    PK_ID                  NUMBER(19),
    IF_CODE                VARCHAR2(20),
    TABLE_ID               VARCHAR2(64),
    TABLE_DESC             VARCHAR2(255),
    TABLE_NAME             VARCHAR2(64),
    PARENT_TABLE_ID        VARCHAR2(64),
    APPROACH               VARCHAR2(255),
    IS_VALUE_APPROACH      NUMBER(2),
    APPROACH_SUFFIX        VARCHAR2(255),
    REVISION_NAME          VARCHAR2(64),
    TABLE_TYPE             NUMBER(2),
    KEY_SEQUENCE           VARCHAR2(64),
    KEY_CLASS              VARCHAR2(64),
    CREATE_TYPE            NUMBER(2),
    UPDATE_TYPE            NUMBER(2),
    DELETE_SQL             VARCHAR2(2000),
    EMPTY_FROM_PARENT      NUMBER(2),
    SORT_NO                NUMBER(5),
    PRIMARY KEY   (PK_ID)
);
CREATE INDEX IDX_IF_RECEIVE_CONFIG ON T_IF_RECEIVE_CONFIG(IF_CODE);

DROP TABLE T_IF_RECEIVE_LOCKER;
CREATE TABLE T_IF_RECEIVE_LOCKER (
    TABLE_NAME        VARCHAR2(64),
    STATE             VARCHAR2(1)
);
CREATE INDEX IDX_IF_RECEIVE_LOCKER ON T_IF_RECEIVE_LOCKER(TABLE_NAME);

DROP TABLE T_IF_RECEIVE_SERVICE_STATE;
CREATE TABLE T_IF_RECEIVE_SERVICE_STATE (
    PK_ID             NUMBER(19),
    IF_CODE           VARCHAR2(20),
    TO_SYS_NAME       VARCHAR2(64),
    SERVICE_STATE     NUMBER(2),
    SERVICE_MESSAGE   VARCHAR2(2000),
    PRIMARY KEY   (PK_ID)
);
 
DROP TABLE T_IF_PARAM;
CREATE TABLE T_IF_PARAM (
    PK_ID              NUMBER(19),
    TO_SYS_NAME        VARCHAR2(64),
    IF_CODE            VARCHAR2(20),
    PARAM_GROUP        VARCHAR2(64),
    PARAM_CODE         VARCHAR2(64),
    PARAM_NAME         VARCHAR2(64),
    PARAM_VALUE        VARCHAR2(255),
    PARAM_DESC         VARCHAR2(255),
    EDIT_ABLE          VARCHAR2(1) ,
    LAST_UPDATE_TIME   DATE,
    PRIMARY KEY   (PK_ID)
);
CREATE INDEX IDX_IF_PARAM ON T_IF_PARAM(IF_CODE, TO_SYS_NAME);

DROP TABLE T_IF_ELEMENT;
CREATE TABLE T_IF_ELEMENT (
    PK_ID          NUMBER(19),
    IF_CODE        VARCHAR2(20),
    ELEMENT_NAME   VARCHAR2(64),
    TABLE_ID       VARCHAR2(64),
    FIELD_NAME     VARCHAR2(64),
    DEFAULT_VALUE  VARCHAR2(64),
    XML_ATTR       VARCHAR2(512),
    CREATOR        VARCHAR2(64),
    FIELD_TYPE     NUMBER(2),
    USER_FIELD_TYPE  CHAR(20),
    MAX_LENGTH     NUMBER(6),
    IS_ACTIVE      NUMBER(2),
    NULL_ABLE      NUMBER(2),
    INSERT_ABLE    NUMBER(2),
    UPDATE_ABLE    NUMBER(2),
    SORT_NO        NUMBER(5),
    ELEMENT_DESC   VARCHAR2(255),
    DATA_CONVERT   VARCHAR2(255),
    DATA_FORMAT    VARCHAR2(64),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_ELEMENT ON T_IF_ELEMENT(IF_CODE, TABLE_ID);
 
DROP TABLE T_IF_XML_FILE_LOG;
CREATE TABLE T_IF_XML_FILE_LOG (
    PK_ID          NUMBER(19),
    FILE_KEY       CHAR(32),
    FILE_PATH      VARCHAR2(255),
    IF_CODE        VARCHAR2(20),
    CREATE_TIME    DATE,
    ASYN_RECEIVED  NUMBER(2),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_XML_FILE_LOG ON T_IF_XML_FILE_LOG(IF_CODE);

DROP TABLE T_IF_RECEIVE_LOG;
CREATE TABLE T_IF_RECEIVE_LOG (
    PK_ID                    NUMBER(19),
    IF_CODE                  VARCHAR2(20),
    IF_TYPE                  NUMBER(2),
    TABLE_ID                 VARCHAR2(64),
    THIRD_IF_CODE            VARCHAR2(64),
    THIRD_KEY                VARCHAR2(64),
    QUERY_KEY                CHAR(32),
    ORIG_QUERY_FILE_KEY      CHAR(32),
    THIRD_QUERY_FILE_KEY     CHAR(32),
    RECEIVE_FILE_KEY         CHAR(32),
    BACK_RECEIVE_FILE_KEY    CHAR(32),
    RECEIVE_RESULT           NUMBER(2),
    LOG_TYPE                 NUMBER(2),
    ERROR_CODE               VARCHAR2(64),
    ERROR_MSG                VARCHAR2(2000),
    CREATE_TIME              DATE,
    KEY1_NAME                VARCHAR2(64),
    KEY1_VALUE               VARCHAR2(255),
    KEY2_NAME                VARCHAR2(64),
    KEY2_VALUE               VARCHAR2(255),
    KEY3_NAME                VARCHAR2(64),
    KEY3_VALUE               VARCHAR2(255),
    KEY4_NAME                VARCHAR2(64),
    KEY4_VALUE               VARCHAR2(255),
    KEY5_NAME                VARCHAR2(64),
    KEY5_VALUE               VARCHAR2(255),
    KEY6_NAME                VARCHAR2(64),
    KEY6_VALUE               VARCHAR2(255),
    KEY7_NAME                VARCHAR2(64),
    KEY7_VALUE               VARCHAR2(255),
    KEY8_NAME                VARCHAR2(64),
    KEY8_VALUE               VARCHAR2(255),
    KEY9_NAME                VARCHAR2(64),
    KEY9_VALUE               VARCHAR2(255),
    KEY10_NAME               VARCHAR2(64),
    KEY10_VALUE              VARCHAR2(255),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_RECEIVE_LOG ON T_IF_RECEIVE_LOG(IF_CODE, IF_TYPE);

DROP TABLE T_IF_QUERY_LOG;
CREATE TABLE T_IF_QUERY_LOG (
    PK_ID                NUMBER(19),
    IF_CODE              VARCHAR2(20),
    IF_TYPE              NUMBER(2),
    TABLE_ID             VARCHAR2(64),
    QUERY_KEY            CHAR(32),
    ORIG_QUERY_FILE_KEY  CHAR(32),
    THIRD_QUERY_FILE_KEY CHAR(32),
    SEND_RESULT          NUMBER(2),
    RECEIVE_FILE_KEY     CHAR(32),
    RECEIVE_RESULT       NUMBER(2),
    LOG_TYPE             NUMBER(2),
    ERROR_CODE           VARCHAR2(64),
    ERROR_MSG            VARCHAR2(2000),
    CREATE_TIME          DATE,
    KEY1_NAME            VARCHAR2(64),
    KEY1_VALUE           VARCHAR2(255),
    KEY2_NAME            VARCHAR2(64),
    KEY2_VALUE           VARCHAR2(255),
    KEY3_NAME            VARCHAR2(64),
    KEY3_VALUE           VARCHAR2(255),
    KEY4_NAME            VARCHAR2(64),
    KEY4_VALUE           VARCHAR2(255),
    KEY5_NAME            VARCHAR2(64),
    KEY5_VALUE           VARCHAR2(255),
    KEY6_NAME            VARCHAR2(64),
    KEY6_VALUE           VARCHAR2(255),
    KEY7_NAME            VARCHAR2(64),
    KEY7_VALUE           VARCHAR2(255),
    KEY8_NAME            VARCHAR2(64),
    KEY8_VALUE           VARCHAR2(255),
    KEY9_NAME            VARCHAR2(64),
    KEY9_VALUE           VARCHAR2(255),
    KEY10_NAME           VARCHAR2(64),
    KEY10_VALUE          VARCHAR2(255),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_QUERY_LOG ON T_IF_QUERY_LOG(IF_CODE, IF_TYPE);

DROP TABLE T_IF_DATA_DICT;
CREATE TABLE T_IF_DATA_DICT (
    PK_ID               NUMBER(19),
    CODE_TYPE           VARCHAR2(40),
    CODE_TYPE_NAME      VARCHAR2(80),
    CODE_VALUE          VARCHAR2(255),
    CODE_VALUE_NAME     VARCHAR2(255),
    SORT_NO             NUMBER(4),
    CODE_DESC           VARCHAR2(128),
    IS_EDIT             NUMBER(1),
    CREATE_TIME         DATE,
    LAST_UPDATE_TIME    DATE,
    PRIMARY KEY   (PK_ID)
);
CREATE INDEX IDX_IF_DATA_DICT ON T_IF_DATA_DICT(CODE_TYPE);

DROP TABLE T_IF_PARAM_TEMPLATE_GROUP;
CREATE TABLE T_IF_PARAM_TEMPLATE_GROUP (
    PK_ID               NUMBER(19),
    PARAM_GROUP         VARCHAR2(64),
    PARAM_GROUP_NAME    VARCHAR2(64),
    PRIMARY KEY   (PK_ID)
); 

DROP TABLE T_IF_PARAM_TEMPLATE;
CREATE TABLE T_IF_PARAM_TEMPLATE (
    PK_ID       NUMBER(19),
    PARAM_GROUP VARCHAR2(64),
    PARAM_CODE  VARCHAR2(64),
    PARAM_NAME  VARCHAR2(64),
    PARAM_VALUE VARCHAR2(255),
    PARAM_DESC  VARCHAR2(255),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_PARAM_TEMPLATE ON T_IF_PARAM_TEMPLATE(PARAM_GROUP);

DROP TABLE T_IF_RECEIVE_LOG_HIS;
CREATE TABLE T_IF_RECEIVE_LOG_HIS (
    PK_ID                    NUMBER(19),
    IF_CODE                  VARCHAR2(20),
    IF_TYPE                  NUMBER(2),
    TABLE_ID                 VARCHAR2(64),
    THIRD_IF_CODE            VARCHAR2(64),
    THIRD_KEY                VARCHAR2(64),
    QUERY_KEY                CHAR(32),
    ORIG_QUERY_FILE_KEY      CHAR(32),
    THIRD_QUERY_FILE_KEY     CHAR(32),
    RECEIVE_FILE_KEY         CHAR(32),
    BACK_RECEIVE_FILE_KEY    CHAR(32),
    RECEIVE_RESULT           NUMBER(2),
    LOG_TYPE                 NUMBER(2),
    ERROR_CODE               VARCHAR2(64),
    ERROR_MSG                VARCHAR2(2000),
    CREATE_TIME              DATE,
    KEY1_NAME                VARCHAR2(64),
    KEY1_VALUE               VARCHAR2(255),
    KEY2_NAME                VARCHAR2(64),
    KEY2_VALUE               VARCHAR2(255),
    KEY3_NAME                VARCHAR2(64),
    KEY3_VALUE               VARCHAR2(255),
    KEY4_NAME                VARCHAR2(64),
    KEY4_VALUE               VARCHAR2(255),
    KEY5_NAME                VARCHAR2(64),
    KEY5_VALUE               VARCHAR2(255),
    KEY6_NAME                VARCHAR2(64),
    KEY6_VALUE               VARCHAR2(255),
    KEY7_NAME                VARCHAR2(64),
    KEY7_VALUE               VARCHAR2(255),
    KEY8_NAME                VARCHAR2(64),
    KEY8_VALUE               VARCHAR2(255),
    KEY9_NAME                VARCHAR2(64),
    KEY9_VALUE               VARCHAR2(255),
    KEY10_NAME               VARCHAR2(64),
    KEY10_VALUE              VARCHAR2(255),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_RECEIVE_LOG_HIS ON T_IF_RECEIVE_LOG_HIS(IF_CODE, IF_TYPE);

DROP TABLE T_IF_QUERY_LOG_HIS;
CREATE TABLE T_IF_QUERY_LOG_HIS (
    PK_ID                NUMBER(19),
    IF_CODE              VARCHAR2(20),
    IF_TYPE              NUMBER(2),
    TABLE_ID             VARCHAR2(64),
    QUERY_KEY            CHAR(32),
    ORIG_QUERY_FILE_KEY  CHAR(32),
    THIRD_QUERY_FILE_KEY CHAR(32),
    SEND_RESULT          NUMBER(2),
    RECEIVE_FILE_KEY     CHAR(32),
    RECEIVE_RESULT       NUMBER(2),
    LOG_TYPE             NUMBER(2),
    ERROR_CODE           VARCHAR2(64),
    ERROR_MSG            VARCHAR2(2000),
    CREATE_TIME          DATE,
    KEY1_NAME            VARCHAR2(64),
    KEY1_VALUE           VARCHAR2(255),
    KEY2_NAME            VARCHAR2(64),
    KEY2_VALUE           VARCHAR2(255),
    KEY3_NAME            VARCHAR2(64),
    KEY3_VALUE           VARCHAR2(255),
    KEY4_NAME            VARCHAR2(64),
    KEY4_VALUE           VARCHAR2(255),
    KEY5_NAME            VARCHAR2(64),
    KEY5_VALUE           VARCHAR2(255),
    KEY6_NAME            VARCHAR2(64),
    KEY6_VALUE           VARCHAR2(255),
    KEY7_NAME            VARCHAR2(64),
    KEY7_VALUE           VARCHAR2(255),
    KEY8_NAME            VARCHAR2(64),
    KEY8_VALUE           VARCHAR2(255),
    KEY9_NAME            VARCHAR2(64),
    KEY9_VALUE           VARCHAR2(255),
    KEY10_NAME           VARCHAR2(64),
    KEY10_VALUE          VARCHAR2(255),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_QUERY_LOG_HIS ON T_IF_QUERY_LOG_HIS(IF_CODE, IF_TYPE);

drop table T_IF_USER;
create table T_IF_USER  (
   USER_NAME            VARCHAR2(36),
   USER_DESC            VARCHAR2(64),
   USER_PWD             VARCHAR2(100),
   USER_STATUS          NUMBER(1),
   IS_UPDATE_PWD        NUMBER(1),
   CREATE_TIME          DATE,
   LAST_UPDATE_TIME     DATE,
   primary key (USER_NAME)
);

drop table T_IF_USER_ROLE;
create table T_IF_USER_ROLE  (
   USER_NAME            VARCHAR2(36),
   ROLE_ID              NUMBER(19),
   CREATE_TIME          DATE,
   LAST_UPDATE_TIME     DATE,
   primary key (USER_NAME, ROLE_ID)
);

drop table T_IF_ROLE;
create table T_IF_ROLE  (
   PK_ID                NUMBER(19),
   ROLE_NAME            VARCHAR2(64),
   ROLE_DESC            VARCHAR2(255),
   CREATE_TIME          DATE,
   LAST_UPDATE_TIME     DATE,
   primary key (PK_ID)
);

drop table T_IF_ROLE_PRIVILEGES;
create table T_IF_ROLE_PRIVILEGES  (
   ROLE_ID              NUMBER(19),
   PRIVILEGES_ID        NUMBER(19),
   CREATE_TIME          DATE,
   LAST_UPDATE_TIME     DATE,
   primary key (ROLE_ID, PRIVILEGES_ID)
);

drop table T_IF_PRIVILEGES;
create table T_IF_PRIVILEGES  (
   PK_ID                NUMBER(19),
   MODULE_CODE          VARCHAR2(40),
   PRIVILEGES_TYPE      VARCHAR2(20),
   CONTENT              VARCHAR2(64),
   PRIVILEGES_DESC      VARCHAR2(255),
   SORT_NO              NUMBER(4),
   CREATE_TIME          DATE,
   LAST_UPDATE_TIME     DATE,
   primary key (PK_ID)
);

drop table T_IF_MODULE;
create table T_IF_MODULE  (
   PARENT_MODULE        VARCHAR2(40),
   MODULE_CODE          VARCHAR2(40),
   MODULE_NAME          VARCHAR2(64),
   SORT_NO              NUMBER(4),
   MODULE_LEVEL         NUMBER(4),
   CREATE_TIME          DATE,
   LAST_UPDATE_TIME     DATE,
   primary key (MODULE_CODE)
);

--接口执行统计
DROP TABLE S_IF_EXECUTION;
CREATE TABLE S_IF_EXECUTION(
   NEXT_HI NUMBER(19)
);
INSERT INTO S_IF_EXECUTION(NEXT_HI)VALUES(1);
COMMIT;

--接口执行天统计
drop table T_IF_EXECUTION_DAY;
create table T_IF_EXECUTION_DAY  (
   PK_ID                NUMBER(19),
   DAY                  VARCHAR2(10),
   IF_CODE              VARCHAR2(20),
   AUTO_SUCCESS         NUMBER(9),
   AUTO_TOTAL           NUMBER(9),
   HAND_SUCCESS         NUMBER(9),
   HAND_TOTAL           NUMBER(9),
   primary key (PK_ID)
);

--接口执行周统计
drop table T_IF_EXECUTION_WEEK;
create table T_IF_EXECUTION_WEEK  (
   PK_ID                NUMBER(19),
   WEEK                 VARCHAR2(7),
   IF_CODE              VARCHAR2(20),
   AUTO_SUCCESS         NUMBER(9),
   AUTO_TOTAL           NUMBER(9),
   HAND_SUCCESS         NUMBER(9),
   HAND_TOTAL           NUMBER(9),
   primary key (PK_ID)
);

--接口执行月统计
drop table T_IF_EXECUTION_MONTH;
create table T_IF_EXECUTION_MONTH  (
   PK_ID                NUMBER(19),
   MONTH                VARCHAR2(7),
   IF_CODE              VARCHAR2(20),
   AUTO_SUCCESS         NUMBER(9),
   AUTO_TOTAL           NUMBER(9),
   HAND_SUCCESS         NUMBER(9),
   HAND_TOTAL           NUMBER(9),
   primary key (PK_ID)
);

--接口执行季度统计
drop table T_IF_EXECUTION_QUARTER;
create table T_IF_EXECUTION_QUARTER  (
   PK_ID                NUMBER(19),
   QUARTER              VARCHAR2(6),
   IF_CODE              VARCHAR2(20),
   AUTO_SUCCESS         NUMBER(13),
   AUTO_TOTAL           NUMBER(13),
   HAND_SUCCESS         NUMBER(13),
   HAND_TOTAL           NUMBER(13),
   primary key (PK_ID)
);

--接口执行年统计
drop table T_IF_EXECUTION_YEAR;
create table T_IF_EXECUTION_YEAR  (
   PK_ID                NUMBER(19),
   YEAR                 VARCHAR2(4),
   IF_CODE              VARCHAR2(20),
   AUTO_SUCCESS         NUMBER(13),
   AUTO_TOTAL           NUMBER(13),
   HAND_SUCCESS         NUMBER(13),
   HAND_TOTAL           NUMBER(13),
   primary key (PK_ID)
);