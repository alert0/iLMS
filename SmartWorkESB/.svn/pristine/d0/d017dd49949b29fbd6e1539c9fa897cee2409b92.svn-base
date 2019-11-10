--查询发送配置、接收配置、对照配置、参数、接收服务状态主键策略
IF OBJECT_ID('dbo.S_IF_CONFIG','U') IS NOT NULL
  DROP TABLE S_IF_CONFIG;
CREATE TABLE S_IF_CONFIG(
   NEXT_HI NUMERIC(19, 0)
);
INSERT INTO S_IF_CONFIG(NEXT_HI)VALUES(100000);--过滤手动部分

--文件日志主键策略
IF OBJECT_ID('dbo.S_IF_XML_FILE_LOG','U') IS NOT NULL
  DROP TABLE S_IF_XML_FILE_LOG;
CREATE TABLE S_IF_XML_FILE_LOG(
   NEXT_HI NUMERIC(19, 0)
);
INSERT INTO S_IF_XML_FILE_LOG(NEXT_HI)VALUES(1);


--接收日志主键策略
IF OBJECT_ID('dbo.S_IF_RECEIVE_LOG','U') IS NOT NULL
  DROP TABLE S_IF_RECEIVE_LOG; 
CREATE TABLE S_IF_RECEIVE_LOG(
   NEXT_HI NUMERIC(19, 0)
);
INSERT INTO S_IF_RECEIVE_LOG(NEXT_HI)VALUES(1);


--查询发送日志主键策略
IF OBJECT_ID('dbo.S_IF_QUERY_LOG','U') IS NOT NULL
  DROP TABLE S_IF_QUERY_LOG; 
CREATE TABLE S_IF_QUERY_LOG(
   NEXT_HI NUMERIC(19, 0)
);
INSERT INTO S_IF_QUERY_LOG(NEXT_HI)VALUES(1);


IF OBJECT_ID('dbo.T_IF_TIMER','U') IS NOT NULL
  DROP TABLE T_IF_TIMER;
CREATE TABLE T_IF_TIMER (
    JOB_CODE               VARCHAR(40),
    JOB_DESC               VARCHAR(255),
    IF_CODE                VARCHAR(20),
    MASTER_QUERY_TABLE_ID  VARCHAR(255),
    JOB_GROUP              VARCHAR(40),
    JOB_CLASS              VARCHAR(200),
    TRIGGER_NAME           VARCHAR(40),
    TRIGGER_VALUE          VARCHAR(40),
    START_TIME             DATETIME,
    UPDATE_STATE           NUMERIC(2, 0),
    RUN_STATE              NUMERIC(2, 0),
    LAST_RUN_TIME          DATETIME,
    NEXT_RUN_TIME          DATETIME,
    THREAD_STATE           NUMERIC(2, 0),
    THREAD_RUN_TIME        DATETIME,
    PRIMARY KEY   (JOB_CODE)
);

IF OBJECT_ID('dbo.T_IF_CONFIG','U') IS NOT NULL
  DROP TABLE T_IF_CONFIG;
CREATE TABLE T_IF_CONFIG (
    IF_CODE                   VARCHAR(20),
    IF_NAME                   VARCHAR(255),
    IF_NAME_ZH                VARCHAR(255),
    TO_SYS_NAME               VARCHAR(64),
    IF_TYPE                   NUMERIC(2, 0),
    SERVICE_TYPE              VARCHAR(10),
    SERVICE_CLASS             VARCHAR(255),
    RECEIVE_TRANSATION_TYPE   NUMERIC(2, 0),
    RECEIVE_DELETE_FLAG       VARCHAR(64),
    SEND_SUCCESS_FLAG         VARCHAR(64),
    DB_PROC                   VARCHAR(255),
    PRIMARY KEY   (IF_CODE)
);

IF OBJECT_ID('dbo.T_IF_QUERY_CONFIG','U') IS NOT NULL
  DROP TABLE T_IF_QUERY_CONFIG;
CREATE TABLE T_IF_QUERY_CONFIG (
    PK_ID              NUMERIC(19, 0),
    IF_CODE            VARCHAR(20),
    TABLE_ID           VARCHAR(64),
    TABLE_DESC         VARCHAR(255),
    PARENT_TABLE_ID    VARCHAR(64),
    APPROACH           VARCHAR(255),
    APPROACH_SUFFIX    VARCHAR(255),
    APPROACH_PREFIX    VARCHAR(255),
    XML_ATTR           VARCHAR(2000),
    AUTO_QUERY_SQL     VARCHAR(2000),
    HAND_QUERY_SQL     VARCHAR(2000),
    EMPTY_FROM_DEFAULT NUMERIC(2, 0),
    ALLOW_EMPTY        NUMERIC(2, 0),
    MAX_NUMBER         NUMERIC(5, 0),
    SUCCESS_SQL        VARCHAR(2000),
    FAILURE_SQL        VARCHAR(2000),
    SORT_NO            NUMERIC(5, 0),
    PRIMARY KEY   (PK_ID)
);
CREATE INDEX IDX_IF_QUERY_CONFIG ON T_IF_QUERY_CONFIG(IF_CODE);

IF OBJECT_ID('dbo.T_IF_RECEIVE_CONFIG','U') IS NOT NULL
  DROP TABLE T_IF_RECEIVE_CONFIG;
CREATE TABLE T_IF_RECEIVE_CONFIG (
    PK_ID                  NUMERIC(19, 0),
    IF_CODE                VARCHAR(20),
    TABLE_ID               VARCHAR(64),
    TABLE_DESC             VARCHAR(255),
    TABLE_NAME             VARCHAR(64),
    PARENT_TABLE_ID        VARCHAR(64),
    APPROACH               VARCHAR(255),
    IS_VALUE_APPROACH      NUMERIC(2, 0),
    APPROACH_SUFFIX        VARCHAR(255),
    REVISION_NAME          VARCHAR(64),
    TABLE_TYPE             NUMERIC(2, 0),
    KEY_SEQUENCE           VARCHAR(64),
    KEY_CLASS              VARCHAR(64),
    CREATE_TYPE            NUMERIC(2, 0),
    UPDATE_TYPE            NUMERIC(2, 0),
    DELETE_SQL             VARCHAR(2000),
    EMPTY_FROM_PARENT      NUMERIC(2, 0),
    SORT_NO                NUMERIC(5, 0),
    PRIMARY KEY   (PK_ID)
);
CREATE INDEX IDX_IF_RECEIVE_CONFIG ON T_IF_RECEIVE_CONFIG(IF_CODE);

IF OBJECT_ID('dbo.T_IF_RECEIVE_LOCKER','U') IS NOT NULL
  DROP TABLE T_IF_RECEIVE_LOCKER;
CREATE TABLE T_IF_RECEIVE_LOCKER (
    TABLE_NAME        VARCHAR(64),
    STATE             VARCHAR(1)
);
CREATE INDEX IDX_IF_RECEIVE_LOCKER ON T_IF_RECEIVE_LOCKER(TABLE_NAME);

IF OBJECT_ID('dbo.T_IF_RECEIVE_SERVICE_STATE','U') IS NOT NULL
  DROP TABLE T_IF_RECEIVE_SERVICE_STATE;
CREATE TABLE T_IF_RECEIVE_SERVICE_STATE (
    PK_ID             NUMERIC(19, 0),
    IF_CODE           VARCHAR(20),
    TO_SYS_NAME       VARCHAR(64),
    SERVICE_STATE     NUMERIC(2, 0),
    SERVICE_MESSAGE   VARCHAR(2000),
    PRIMARY KEY   (PK_ID)
);
 
IF OBJECT_ID('dbo.T_IF_PARAM','U') IS NOT NULL
  DROP TABLE T_IF_PARAM;
CREATE TABLE T_IF_PARAM (
    PK_ID              NUMERIC(19, 0),
    TO_SYS_NAME        VARCHAR(64),
    IF_CODE            VARCHAR(20),
    PARAM_GROUP        VARCHAR(64),
    PARAM_CODE         VARCHAR(64),
    PARAM_NAME         VARCHAR(64),
    PARAM_VALUE        VARCHAR(255),
    PARAM_DESC         VARCHAR(255),
    EDIT_ABLE          VARCHAR(1) ,
    LAST_UPDATE_TIME   DATETIME,
    PRIMARY KEY   (PK_ID)
);
CREATE INDEX IDX_IF_PARAM ON T_IF_PARAM(IF_CODE, TO_SYS_NAME);

IF OBJECT_ID('dbo.T_IF_ELEMENT','U') IS NOT NULL
  DROP TABLE T_IF_ELEMENT;
CREATE TABLE T_IF_ELEMENT (
    PK_ID          NUMERIC(19, 0),
    IF_CODE        VARCHAR(20),
    ELEMENT_NAME   VARCHAR(64),
    TABLE_ID       VARCHAR(64),
    FIELD_NAME     VARCHAR(64),
    DEFAULT_VALUE  VARCHAR(64),
    XML_ATTR       VARCHAR(512),
    CREATOR        VARCHAR(64),
    FIELD_TYPE     NUMERIC(2, 0),
    USER_FIELD_TYPE  CHAR(20),
    MAX_LENGTH     NUMERIC(6, 0),
    IS_ACTIVE      NUMERIC(2, 0),
    NULL_ABLE      NUMERIC(2, 0),
    INSERT_ABLE    NUMERIC(2, 0),
    UPDATE_ABLE    NUMERIC(2, 0),
    SORT_NO        NUMERIC(5, 0),
    ELEMENT_DESC   VARCHAR(255),
    DATA_CONVERT   VARCHAR(255),
    DATA_FORMAT    VARCHAR(64),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_ELEMENT ON T_IF_ELEMENT(IF_CODE, TABLE_ID);
 
IF OBJECT_ID('dbo.T_IF_XML_FILE_LOG','U') IS NOT NULL
  DROP TABLE T_IF_XML_FILE_LOG;
CREATE TABLE T_IF_XML_FILE_LOG (
    PK_ID          NUMERIC(19, 0),
    FILE_KEY       CHAR(32),
    FILE_PATH      VARCHAR(255),
    IF_CODE        VARCHAR(20),
    CREATE_TIME    DATETIME,
    ASYN_RECEIVED  NUMERIC(2,0),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_XML_FILE_LOG ON T_IF_XML_FILE_LOG(IF_CODE);

IF OBJECT_ID('dbo.T_IF_RECEIVE_LOG','U') IS NOT NULL
  DROP TABLE T_IF_RECEIVE_LOG;
CREATE TABLE T_IF_RECEIVE_LOG (
    PK_ID                    NUMERIC(19, 0),
    IF_CODE                  VARCHAR(20),
    IF_TYPE                  NUMERIC(2, 0),
    TABLE_ID                 VARCHAR(64),
    THIRD_IF_CODE            VARCHAR(64),
    THIRD_KEY                VARCHAR(64),
    QUERY_KEY                CHAR(32),
    ORIG_QUERY_FILE_KEY      CHAR(32),
    THIRD_QUERY_FILE_KEY     CHAR(32),
    RECEIVE_FILE_KEY         CHAR(32),
    BACK_RECEIVE_FILE_KEY    CHAR(32),
    RECEIVE_RESULT           NUMERIC(2, 0),
    LOG_TYPE                 NUMERIC(2, 0),
    ERROR_CODE               VARCHAR(64),
    ERROR_MSG                VARCHAR(2000),
    CREATE_TIME              DATETIME,
    KEY1_NAME                VARCHAR(64),
    KEY1_VALUE               VARCHAR(255),
    KEY2_NAME                VARCHAR(64),
    KEY2_VALUE               VARCHAR(255),
    KEY3_NAME                VARCHAR(64),
    KEY3_VALUE               VARCHAR(255),
    KEY4_NAME                VARCHAR(64),
    KEY4_VALUE               VARCHAR(255),
    KEY5_NAME                VARCHAR(64),
    KEY5_VALUE               VARCHAR(255),
    KEY6_NAME                VARCHAR(64),
    KEY6_VALUE               VARCHAR(255),
    KEY7_NAME                VARCHAR(64),
    KEY7_VALUE               VARCHAR(255),
    KEY8_NAME                VARCHAR(64),
    KEY8_VALUE               VARCHAR(255),
    KEY9_NAME                VARCHAR(64),
    KEY9_VALUE               VARCHAR(255),
    KEY10_NAME               VARCHAR(64),
    KEY10_VALUE              VARCHAR(255),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_RECEIVE_LOG ON T_IF_RECEIVE_LOG(IF_CODE, IF_TYPE);

IF OBJECT_ID('dbo.T_IF_QUERY_LOG','U') IS NOT NULL
  DROP TABLE T_IF_QUERY_LOG;
CREATE TABLE T_IF_QUERY_LOG (
    PK_ID                NUMERIC(19, 0),
    IF_CODE              VARCHAR(20),
    IF_TYPE              NUMERIC(2, 0),
    TABLE_ID             VARCHAR(64),
    QUERY_KEY            CHAR(32),
    ORIG_QUERY_FILE_KEY  CHAR(32),
    THIRD_QUERY_FILE_KEY CHAR(32),
    SEND_RESULT          NUMERIC(2, 0),
    RECEIVE_FILE_KEY     CHAR(32),
    RECEIVE_RESULT       NUMERIC(2, 0),
    LOG_TYPE             NUMERIC(2, 0),
    ERROR_CODE           VARCHAR(64),
    ERROR_MSG            VARCHAR(2000),
    CREATE_TIME          DATETIME,
    KEY1_NAME            VARCHAR(64),
    KEY1_VALUE           VARCHAR(255),
    KEY2_NAME            VARCHAR(64),
    KEY2_VALUE           VARCHAR(255),
    KEY3_NAME            VARCHAR(64),
    KEY3_VALUE           VARCHAR(255),
    KEY4_NAME            VARCHAR(64),
    KEY4_VALUE           VARCHAR(255),
    KEY5_NAME            VARCHAR(64),
    KEY5_VALUE           VARCHAR(255),
    KEY6_NAME            VARCHAR(64),
    KEY6_VALUE           VARCHAR(255),
    KEY7_NAME            VARCHAR(64),
    KEY7_VALUE           VARCHAR(255),
    KEY8_NAME            VARCHAR(64),
    KEY8_VALUE           VARCHAR(255),
    KEY9_NAME            VARCHAR(64),
    KEY9_VALUE           VARCHAR(255),
    KEY10_NAME           VARCHAR(64),
    KEY10_VALUE          VARCHAR(255),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_QUERY_LOG ON T_IF_QUERY_LOG(IF_CODE, IF_TYPE);

IF OBJECT_ID('dbo.T_IF_DATA_DICT','U') IS NOT NULL
  DROP TABLE T_IF_DATA_DICT;
CREATE TABLE T_IF_DATA_DICT (
    PK_ID               NUMERIC(19, 0),
    CODE_TYPE           VARCHAR(40),
    CODE_TYPE_NAME      VARCHAR(80),
    CODE_VALUE          VARCHAR(255),
    CODE_VALUE_NAME     VARCHAR(255),
    SORT_NO             NUMERIC(4, 0),
    CODE_DESC           VARCHAR(128),
    IS_EDIT             NUMERIC(1, 0),
    CREATE_TIME         DATETIME,
    LAST_UPDATE_TIME    DATETIME,
    PRIMARY KEY   (PK_ID)
);
CREATE INDEX IDX_IF_DATA_DICT ON T_IF_DATA_DICT(CODE_TYPE);

IF OBJECT_ID('dbo.T_IF_PARAM_TEMPLATE_GROUP','U') IS NOT NULL
  DROP TABLE T_IF_PARAM_TEMPLATE_GROUP;
CREATE TABLE T_IF_PARAM_TEMPLATE_GROUP (
    PK_ID               NUMERIC(19, 0),
    PARAM_GROUP         VARCHAR(64),
    PARAM_GROUP_NAME    VARCHAR(64),
    PRIMARY KEY   (PK_ID)
); 

IF OBJECT_ID('dbo.T_IF_PARAM_TEMPLATE','U') IS NOT NULL
  DROP TABLE T_IF_PARAM_TEMPLATE;
CREATE TABLE T_IF_PARAM_TEMPLATE (
    PK_ID       NUMERIC(19, 0),
    PARAM_GROUP VARCHAR(64),
    PARAM_CODE  VARCHAR(64),
    PARAM_NAME  VARCHAR(64),
    PARAM_VALUE VARCHAR(255),
    PARAM_DESC  VARCHAR(255),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_PARAM_TEMPLATE ON T_IF_PARAM_TEMPLATE(PARAM_GROUP);

IF OBJECT_ID('dbo.T_IF_RECEIVE_LOG_HIS','U') IS NOT NULL
  DROP TABLE T_IF_RECEIVE_LOG_HIS;
CREATE TABLE T_IF_RECEIVE_LOG_HIS (
    PK_ID                    NUMERIC(19, 0),
    IF_CODE                  VARCHAR(20),
    IF_TYPE                  NUMERIC(2, 0),
    TABLE_ID                 VARCHAR(64),
    THIRD_IF_CODE            VARCHAR(64),
    THIRD_KEY                VARCHAR(64),
    QUERY_KEY                CHAR(32),
    ORIG_QUERY_FILE_KEY      CHAR(32),
    THIRD_QUERY_FILE_KEY     CHAR(32),
    RECEIVE_FILE_KEY         CHAR(32),
    BACK_RECEIVE_FILE_KEY    CHAR(32),
    RECEIVE_RESULT           NUMERIC(2, 0),
    LOG_TYPE                 NUMERIC(2, 0),
    ERROR_CODE               VARCHAR(64),
    ERROR_MSG                VARCHAR(2000),
    CREATE_TIME              DATETIME,
    KEY1_NAME                VARCHAR(64),
    KEY1_VALUE               VARCHAR(255),
    KEY2_NAME                VARCHAR(64),
    KEY2_VALUE               VARCHAR(255),
    KEY3_NAME                VARCHAR(64),
    KEY3_VALUE               VARCHAR(255),
    KEY4_NAME                VARCHAR(64),
    KEY4_VALUE               VARCHAR(255),
    KEY5_NAME                VARCHAR(64),
    KEY5_VALUE               VARCHAR(255),
    KEY6_NAME                VARCHAR(64),
    KEY6_VALUE               VARCHAR(255),
    KEY7_NAME                VARCHAR(64),
    KEY7_VALUE               VARCHAR(255),
    KEY8_NAME                VARCHAR(64),
    KEY8_VALUE               VARCHAR(255),
    KEY9_NAME                VARCHAR(64),
    KEY9_VALUE               VARCHAR(255),
    KEY10_NAME               VARCHAR(64),
    KEY10_VALUE              VARCHAR(255),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_RECEIVE_LOG_HIS ON T_IF_RECEIVE_LOG_HIS(IF_CODE, IF_TYPE);

IF OBJECT_ID('dbo.T_IF_QUERY_LOG_HIS','U') IS NOT NULL
  DROP TABLE T_IF_QUERY_LOG_HIS;
CREATE TABLE T_IF_QUERY_LOG_HIS (
    PK_ID                NUMERIC(19, 0),
    IF_CODE              VARCHAR(20),
    IF_TYPE              NUMERIC(2, 0),
    TABLE_ID             VARCHAR(64),
    QUERY_KEY            CHAR(32),
    ORIG_QUERY_FILE_KEY  CHAR(32),
    THIRD_QUERY_FILE_KEY CHAR(32),
    SEND_RESULT          NUMERIC(2, 0),
    RECEIVE_FILE_KEY     CHAR(32),
    RECEIVE_RESULT       NUMERIC(2, 0),
    LOG_TYPE             NUMERIC(2, 0),
    ERROR_CODE           VARCHAR(64),
    ERROR_MSG            VARCHAR(2000),
    CREATE_TIME          DATETIME,
    KEY1_NAME            VARCHAR(64),
    KEY1_VALUE           VARCHAR(255),
    KEY2_NAME            VARCHAR(64),
    KEY2_VALUE           VARCHAR(255),
    KEY3_NAME            VARCHAR(64),
    KEY3_VALUE           VARCHAR(255),
    KEY4_NAME            VARCHAR(64),
    KEY4_VALUE           VARCHAR(255),
    KEY5_NAME            VARCHAR(64),
    KEY5_VALUE           VARCHAR(255),
    KEY6_NAME            VARCHAR(64),
    KEY6_VALUE           VARCHAR(255),
    KEY7_NAME            VARCHAR(64),
    KEY7_VALUE           VARCHAR(255),
    KEY8_NAME            VARCHAR(64),
    KEY8_VALUE           VARCHAR(255),
    KEY9_NAME            VARCHAR(64),
    KEY9_VALUE           VARCHAR(255),
    KEY10_NAME           VARCHAR(64),
    KEY10_VALUE          VARCHAR(255),
    PRIMARY KEY   (PK_ID)
); 
CREATE INDEX IDX_IF_QUERY_LOG_HIS ON T_IF_QUERY_LOG_HIS(IF_CODE, IF_TYPE);

IF OBJECT_ID('dbo.T_IF_USER','U') IS NOT NULL
  drop table T_IF_USER;
create table T_IF_USER  (
   USER_NAME            VARCHAR(36),
   USER_DESC            VARCHAR(64),
   USER_PWD             VARCHAR(100),
   USER_STATUS          NUMERIC(1, 0),
   IS_UPDATE_PWD        NUMERIC(1, 0),
   CREATE_TIME          DATETIME,
   LAST_UPDATE_TIME     DATETIME,
   primary key (USER_NAME)
);

IF OBJECT_ID('dbo.T_IF_USER_ROLE','U') IS NOT NULL
  drop table T_IF_USER_ROLE;
create table T_IF_USER_ROLE  (
   USER_NAME            VARCHAR(36),
   ROLE_ID              NUMERIC(19, 0),
   CREATE_TIME          DATETIME,
   LAST_UPDATE_TIME     DATETIME,
   primary key (USER_NAME, ROLE_ID)
);

IF OBJECT_ID('dbo.T_IF_ROLE','U') IS NOT NULL
  drop table T_IF_ROLE;
create table T_IF_ROLE  (
   PK_ID                NUMERIC(19, 0),
   ROLE_NAME            VARCHAR(64),
   ROLE_DESC            VARCHAR(255),
   CREATE_TIME          DATETIME,
   LAST_UPDATE_TIME     DATETIME,
   primary key (PK_ID)
);

IF OBJECT_ID('dbo.T_IF_ROLE_PRIVILEGES','U') IS NOT NULL
  drop table T_IF_ROLE_PRIVILEGES;
create table T_IF_ROLE_PRIVILEGES  (
   ROLE_ID              NUMERIC(19, 0),
   PRIVILEGES_ID        NUMERIC(19, 0),
   CREATE_TIME          DATETIME,
   LAST_UPDATE_TIME     DATETIME,
   primary key (ROLE_ID, PRIVILEGES_ID)
);

IF OBJECT_ID('dbo.T_IF_PRIVILEGES','U') IS NOT NULL
  drop table T_IF_PRIVILEGES;
create table T_IF_PRIVILEGES  (
   PK_ID                NUMERIC(19, 0),
   MODULE_CODE          VARCHAR(40),
   PRIVILEGES_TYPE      VARCHAR(20),
   CONTENT              VARCHAR(64),
   PRIVILEGES_DESC      VARCHAR(255),
   SORT_NO              NUMERIC(4, 0),
   CREATE_TIME          DATETIME,
   LAST_UPDATE_TIME     DATETIME,
   primary key (PK_ID)
);

IF OBJECT_ID('dbo.T_IF_MODULE','U') IS NOT NULL
  drop table T_IF_MODULE;
create table T_IF_MODULE  (
   PARENT_MODULE        VARCHAR(40),
   MODULE_CODE          VARCHAR(40),
   MODULE_NAME          VARCHAR(64),
   SORT_NO              NUMERIC(4, 0),
   MODULE_LEVEL         NUMERIC(4, 0),
   CREATE_TIME          DATETIME,
   LAST_UPDATE_TIME     DATETIME,
   primary key (MODULE_CODE)
);

--接口执行统计
IF OBJECT_ID('dbo.S_IF_EXECUTION','U') IS NOT NULL
  DROP TABLE S_IF_EXECUTION;
CREATE TABLE S_IF_EXECUTION(
   NEXT_HI NUMERIC(19, 0)
);
INSERT INTO S_IF_EXECUTION(NEXT_HI)VALUES(1);

--接口执行天统计
IF OBJECT_ID('dbo.T_IF_EXECUTION_DAY','U') IS NOT NULL
  drop table T_IF_EXECUTION_DAY;
create table T_IF_EXECUTION_DAY  (
   PK_ID                NUMERIC(19, 0),
   DAY                  VARCHAR(10),
   IF_CODE              VARCHAR(20),
   AUTO_SUCCESS         NUMERIC(9, 0),
   AUTO_TOTAL           NUMERIC(9, 0),
   HAND_SUCCESS         NUMERIC(9, 0),
   HAND_TOTAL           NUMERIC(9, 0),
   primary key (PK_ID)
);

--接口执行周统计
IF OBJECT_ID('dbo.T_IF_EXECUTION_WEEK','U') IS NOT NULL
  drop table T_IF_EXECUTION_WEEK;
create table T_IF_EXECUTION_WEEK  (
   PK_ID                NUMERIC(19, 0),
   WEEK                 VARCHAR(7),
   IF_CODE              VARCHAR(20),
   AUTO_SUCCESS         NUMERIC(9, 0),
   AUTO_TOTAL           NUMERIC(9, 0),
   HAND_SUCCESS         NUMERIC(9, 0),
   HAND_TOTAL           NUMERIC(9, 0),
   primary key (PK_ID)
);

--接口执行月统计
IF OBJECT_ID('dbo.T_IF_EXECUTION_MONTH','U') IS NOT NULL
  drop table T_IF_EXECUTION_MONTH;
create table T_IF_EXECUTION_MONTH  (
   PK_ID                NUMERIC(19, 0),
   MONTH                VARCHAR(7),
   IF_CODE              VARCHAR(20),
   AUTO_SUCCESS         NUMERIC(9, 0),
   AUTO_TOTAL           NUMERIC(9, 0),
   HAND_SUCCESS         NUMERIC(9, 0),
   HAND_TOTAL           NUMERIC(9, 0),
   primary key (PK_ID)
);

--接口执行季度统计
IF OBJECT_ID('dbo.T_IF_EXECUTION_QUARTER','U') IS NOT NULL
  drop table T_IF_EXECUTION_QUARTER;
create table T_IF_EXECUTION_QUARTER  (
   PK_ID                NUMERIC(19, 0),
   QUARTER              VARCHAR(6),
   IF_CODE              VARCHAR(20),
   AUTO_SUCCESS         NUMERIC(13, 0),
   AUTO_TOTAL           NUMERIC(13, 0),
   HAND_SUCCESS         NUMERIC(13, 0),
   HAND_TOTAL           NUMERIC(13, 0),
   primary key (PK_ID)
);

--接口执行年统计
IF OBJECT_ID('dbo.T_IF_EXECUTION_YEAR','U') IS NOT NULL
  drop table T_IF_EXECUTION_YEAR;
create table T_IF_EXECUTION_YEAR  (
   PK_ID                NUMERIC(19, 0),
   YEAR                 VARCHAR(4),
   IF_CODE              VARCHAR(20),
   AUTO_SUCCESS         NUMERIC(13, 0),
   AUTO_TOTAL           NUMERIC(13, 0),
   HAND_SUCCESS         NUMERIC(13, 0),
   HAND_TOTAL           NUMERIC(13, 0),
   primary key (PK_ID)
);

