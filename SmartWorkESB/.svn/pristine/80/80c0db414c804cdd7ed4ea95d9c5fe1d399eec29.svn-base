
--任务计划
DELETE FROM T_IF_TIMER WHERE IF_CODE='DS206';
INSERT INTO T_IF_TIMER(JOB_CODE, JOB_DESC, IF_CODE, MASTER_QUERY_TABLE_ID, JOB_GROUP, JOB_CLASS, TRIGGER_NAME, TRIGGER_VALUE, UPDATE_STATE, RUN_STATE)
          SELECT 'DS206', '发送直接数据到数据库(DR206)', 'DS206', '', 'DS206', 'com.smartwork.esb.job.ClientSenderJob', 'DS206', '0 0/1 * * * ?', 1, 1 FROM DUAL
;

--接口
DELETE FROM T_IF_CONFIG WHERE IF_CODE='DS206';
INSERT INTO T_IF_CONFIG(IF_CODE, IF_NAME, TO_SYS_NAME, IF_TYPE, SERVICE_TYPE, SERVICE_CLASS, RECEIVE_TRANSATION_TYPE, SEND_SUCCESS_FLAG)
          SELECT 'DS206', '发送直接数据到数据库(DR206)', 'ESB', 2, 'Other', 'com.smartwork.demo.service.DS206SenderService', 1, '' FROM DUAL;
;

--发送配置
--APPROACH：最终发送XML的节点，多笔数据时要有，以防数据混乱
--APPROACH_PREFIX：合并前缀
DELETE FROM T_IF_QUERY_CONFIG WHERE IF_CODE='DS206';
INSERT INTO T_IF_QUERY_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, APPROACH, EMPTY_FROM_DEFAULT, ALLOW_EMPTY, MAX_NUMBER, SORT_NO)
          SELECT 26002, 'DS206', 'S_MASTER', '',             '发送主表',      'master',    0, 0, 100,  1 FROM DUAL
UNION ALL SELECT 26003, 'DS206', 'S_DETAIL', 'S_MASTER',     '发送从表',      'detail',    0, 1, 0,    2 FROM DUAL
UNION ALL SELECT 26001, 'DS206', 'S_HEAD',   '',             '发送表头',      '',           1, 0, 1,    3 FROM DUAL
;

--
--有状态发送数据
--手工发送部分使用主键过滤
UPDATE T_IF_QUERY_CONFIG SET AUTO_QUERY_SQL = 'SELECT * FROM TMP_S_MASTER WHERE IS_SEND=0' WHERE PK_ID=26002;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_S_MASTER WHERE PK_ID IN (:PK_ID)' WHERE PK_ID=26002;
UPDATE T_IF_QUERY_CONFIG SET SUCCESS_SQL = 'UPDATE TMP_S_MASTER SET IS_SEND=1 WHERE PK_ID=:PK_ID' WHERE PK_ID=26002;
UPDATE T_IF_QUERY_CONFIG SET FAILURE_SQL = 'UPDATE TMP_S_MASTER SET FAILURE=FAILURE+1 WHERE PK_ID=:PK_ID' WHERE PK_ID=26002;

--查询参数PK_ID是主表的主键字段名(PK_ID)
UPDATE T_IF_QUERY_CONFIG SET AUTO_QUERY_SQL = 'SELECT * FROM TMP_S_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=26003;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_S_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=26003;

--接口参数
DELETE FROM T_IF_PARAM WHERE IF_CODE = 'DS206';
INSERT INTO T_IF_PARAM(PK_ID, IF_CODE, TO_SYS_NAME, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, EDIT_ABLE)
          SELECT 26001, 'DS206', 'ESB',   'STD',       'HANDLER_CLASS',              '接口拦截类',      'com.smartwork.demo.handler.DS206Handler',  1 FROM DUAL
UNION ALL SELECT 26004, 'DS206', 'ESB',   'STD',       'SERVER_URL',                 '数据库服务',      'jdbc:oracle:thin:@192.168.31.201:1521:FRAMEWORK',  1 FROM DUAL
UNION ALL SELECT 26005, 'DS206', 'ESB',   'STD',       'DRIVER',                     '数据库驱动',      'oracle.jdbc.xa.client.OracleXADataSource',         1 FROM DUAL
UNION ALL SELECT 26006, 'DS206', 'ESB',   'STD',       'USER_NAME',                  '访问用户',       'MESIF',                                1 FROM DUAL
UNION ALL SELECT 26007, 'DS206', 'ESB',   'STD',       'PASSWORD',                   '访问密码',       'MESIF',                                1 FROM DUAL
;

--关系对照
--标识：来自发送表头THIRD_FILE_KEY
--接口代码：来自表头IF_CODE
DELETE FROM T_IF_ELEMENT WHERE IF_CODE = 'DS206';
INSERT INTO T_IF_ELEMENT(PK_ID, IF_CODE, TABLE_ID, ELEMENT_NAME, FIELD_NAME, ELEMENT_DESC, DEFAULT_VALUE, FIELD_TYPE, IS_ACTIVE, NULL_ABLE, MAX_LENGTH, INSERT_ABLE, UPDATE_ABLE, SORT_NO, DATA_CONVERT, DATA_FORMAT) 
--
--输入
          SELECT 26003, 'DS206', 'S_HEAD', 'id',       'ID',         '标识',       '$QH_THIRD_FILE_KEY',    1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL
UNION ALL SELECT 26004, 'DS206', 'S_HEAD', 'ifCode',   'IF_CODE',    '接口代码',    '$QH_IF_CODE',           1, 1, 0, 0, 0, 0, 40, '', '' FROM DUAL
--
UNION ALL SELECT 26010, 'DS206', 'S_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',         22, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL  
UNION ALL SELECT 26011, 'DS206', 'S_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',          3, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 26012, 'DS206', 'S_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',          1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL  
UNION ALL SELECT 26013, 'DS206', 'S_MASTER', 'price',       'PRICE',         '产品价格', '',          1, 1, 1, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 26014, 'DS206', 'S_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',          1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL  
UNION ALL SELECT 26015, 'DS206', 'S_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',          1, 1, 1, 0, 0, 0, 60, '', '' FROM DUAL
--
UNION ALL SELECT 26022, 'DS206', 'S_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',           1, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 26023, 'DS206', 'S_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',           1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL  
UNION ALL SELECT 26024, 'DS206', 'S_DETAIL', 'price',      'PRICE',        '物料价格', '',           1, 1, 1, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 26025, 'DS206', 'S_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',           1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL  
UNION ALL SELECT 26026, 'DS206', 'S_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',           1, 1, 1, 0, 0, 0, 60, '', '' FROM DUAL  
--
--
;

COMMIT;





