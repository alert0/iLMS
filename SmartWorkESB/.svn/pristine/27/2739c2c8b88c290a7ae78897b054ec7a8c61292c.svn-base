
CREATE SEQUENCE S_TMP;

--发送主表
CREATE TABLE TMP_S_MASTER(
    PK_ID          NUMBER(19),
    PRODUCT_NO     VARCHAR2(30),
    PRODUCT_NAME   VARCHAR2(64),
    PRICE          NUMBER(6,2),
    QUANTITY       NUMBER(6),
    EFF_DATE       DATE,
    IS_SEND        NUMBER(1) DEFAULT 0,
    FAILURE        NUMBER(2) DEFAULT 0,
    PRIMARY KEY   (PK_ID)
);

--发送从表
CREATE TABLE TMP_S_DETAIL(
    PK_ID          NUMBER(19),
    FK_ID          NUMBER(19),
    ITEM_NO        VARCHAR2(64),
    ITEM_NAME      VARCHAR2(64),
    PRICE          NUMBER(6,2),
    QUANTITY       NUMBER(6),
    EFF_DATE       DATE,
    PRIMARY KEY   (PK_ID)
);

--查询主表
CREATE TABLE TMP_Q_MASTER(
    PK_ID          NUMBER(19),
    PRODUCT_NO     VARCHAR2(30),
    PRODUCT_NAME   VARCHAR2(64),
    PRICE          NUMBER(6,2),
    QUANTITY       NUMBER(6),
    EFF_DATE       DATE,
    PRIMARY KEY   (PK_ID)
);

--查询从表
CREATE TABLE TMP_Q_DETAIL(
    PK_ID          NUMBER(19),
    FK_ID          NUMBER(19),
    ITEM_NO        VARCHAR2(64),
    ITEM_NAME      VARCHAR2(64),
    PRICE          NUMBER(6,2),
    QUANTITY       NUMBER(6),
    EFF_DATE       DATE,
    PRIMARY KEY   (PK_ID)
);

--接收批次表
CREATE TABLE TMP_R_BATCH(
    PK_ID          NUMBER(19),
    BATCH_NO       VARCHAR2(32),
    IF_CODE        VARCHAR2(20),
    CREATE_TIME    DATE,
    PRIMARY KEY   (PK_ID)
);

--接收主表
CREATE TABLE TMP_R_MASTER(
    PK_ID          NUMBER(19),
    BATCH_NO       VARCHAR2(32),
    IF_CODE        VARCHAR2(20),
    PRODUCT_NO     VARCHAR2(30),
    PRODUCT_NAME   VARCHAR2(64),
    PRICE          NUMBER(6,2),
    QUANTITY       NUMBER(6),
    EFF_DATE       DATE,
    CREATE_TIME    DATE,
    UPDATE_TIME    DATE, 
    PRIMARY KEY   (PK_ID)
);

--接收从表
CREATE TABLE TMP_R_DETAIL(
    PK_ID          NUMBER(19),
    FK_ID          NUMBER(19),
    ITEM_NO        VARCHAR2(64),
    ITEM_NAME      VARCHAR2(64),
    PRICE          NUMBER(6,2),
    QUANTITY       NUMBER(6),
    EFF_DATE       DATE,
    CREATE_TIME    DATE,
    UPDATE_TIME    DATE, 
    PRIMARY KEY   (PK_ID)
);

--发送返回的第三方关键信息
CREATE TABLE TMP_R_THIRD(
    PK_ID          NUMBER(19),
    FK_ID          VARCHAR2(64),
    IF_CODE        VARCHAR2(20),
    DATA           VARCHAR2(64),
    CREATE_TIME    DATE,
    PRIMARY KEY   (PK_ID)
);

