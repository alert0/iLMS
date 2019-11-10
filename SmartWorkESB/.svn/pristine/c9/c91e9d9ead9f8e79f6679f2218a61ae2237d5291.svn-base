
--任务计划
DELETE FROM T_IF_TIMER WHERE IF_CODE='DS203';
INSERT INTO T_IF_TIMER(JOB_CODE, JOB_DESC, IF_CODE, MASTER_QUERY_TABLE_ID, JOB_GROUP, JOB_CLASS, TRIGGER_NAME, TRIGGER_VALUE, UPDATE_STATE, RUN_STATE)
          SELECT 'DS203', '发送JSON到DR203', 'DS203', '', 'DS203', 'com.smartwork.esb.job.ClientSenderJob', 'DS203', '0 0/1 * * * ?', 1, 1 FROM DUAL
;

--接口
DELETE FROM T_IF_CONFIG WHERE IF_CODE='DS203';
INSERT INTO T_IF_CONFIG(IF_CODE, IF_NAME, TO_SYS_NAME, IF_TYPE, SERVICE_TYPE, SERVICE_CLASS, RECEIVE_TRANSATION_TYPE, SEND_SUCCESS_FLAG)
          SELECT 'DS203', '发送JSON到DR203', 'ESB', 2, 'WS', '', 1, '1' FROM DUAL;
;

--发送配置
--APPROACH：最终发送JSON的节点，多笔数据时要有，以防数据混乱
DELETE FROM T_IF_QUERY_CONFIG WHERE IF_CODE='DS203';
INSERT INTO T_IF_QUERY_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, APPROACH, EMPTY_FROM_DEFAULT, ALLOW_EMPTY, MAX_NUMBER, SORT_NO)
          SELECT 25401, 'DS203', 'S_HEAD',   '',             '发送表头',     '',            1, 0, 1,    1 FROM DUAL
UNION ALL SELECT 25402, 'DS203', 'S_MASTER', '',             '发送主表',     'data',        0, 0, 100,  2 FROM DUAL
UNION ALL SELECT 25403, 'DS203', 'S_DETAIL', 'S_MASTER',     '发送从表',     'Item',        0, 1, 0,    3 FROM DUAL
;

--
--有状态发送数据
--手工发送部分使用主键过滤
UPDATE T_IF_QUERY_CONFIG SET AUTO_QUERY_SQL = 'SELECT * FROM TMP_S_MASTER WHERE IS_SEND=0' WHERE PK_ID=25402;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_S_MASTER WHERE PK_ID IN (:PK_ID)' WHERE PK_ID=25402;
UPDATE T_IF_QUERY_CONFIG SET SUCCESS_SQL = 'UPDATE TMP_S_MASTER SET IS_SEND=1 WHERE PK_ID=:PK_ID' WHERE PK_ID=25402;
UPDATE T_IF_QUERY_CONFIG SET FAILURE_SQL = 'UPDATE TMP_S_MASTER SET FAILURE=FAILURE+1 WHERE PK_ID=:PK_ID' WHERE PK_ID=25402;

--查询参数PK_ID是主表的主键字段名(PK_ID)
UPDATE T_IF_QUERY_CONFIG SET AUTO_QUERY_SQL = 'SELECT * FROM TMP_S_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=25403;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_S_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=25403;

--接收配置
--RECEIVE_MESSAGE：固定，表示接收消息，结果反馈到日志
--接收路径：顶级主表从XML根目录开始算
DELETE FROM T_IF_RECEIVE_CONFIG WHERE IF_CODE = 'DS203';
INSERT INTO T_IF_RECEIVE_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, TABLE_NAME, APPROACH, TABLE_TYPE, KEY_SEQUENCE, KEY_CLASS, CREATE_TYPE, UPDATE_TYPE, EMPTY_FROM_PARENT, SORT_NO)
          SELECT 25401, 'DS203', 'RECEIVE_MESSAGE',   '',                   '接收消息',     '',                     '/message',   4, '',             '', 0, 0, 0, 1 FROM DUAL
;

--接口参数
DELETE FROM T_IF_PARAM WHERE IF_CODE = 'DS203';
INSERT INTO T_IF_PARAM(PK_ID, IF_CODE, TO_SYS_NAME, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, EDIT_ABLE)
          SELECT 25401,   'DS203', 'ESB',   'STD',       'HANDLER_CLASS',             '接口拦截类',      'com.smartwork.demo.handler.DS203Handler',  1 FROM DUAL
UNION ALL SELECT 25403,   'DS203', 'ESB',   'STD',       'SERVER_URL',                '服务地址',       'http://127.0.0.1:8080/SmartWorkESB/services/dr203',  1 FROM DUAL
UNION ALL SELECT 25404,   'DS203', 'ESB',   'STD',       'ACTION_URL',                '方法地址',       'http://ws.smartwork.com/receive',          1 FROM DUAL
UNION ALL SELECT 25405,   'DS203', 'ESB',   'STD',       'REQUEST_PARAM_NAME',        '请求参数名',      'json',                                     1 FROM DUAL
UNION ALL SELECT 25406,   'DS203', 'ESB',   'STD',       'USER_NAME',                 '访问用户',       'smartwork',                                1 FROM DUAL
UNION ALL SELECT 25407,   'DS203', 'ESB',   'STD',       'PASSWORD',                  '访问密码',       'smartwork',                                1 FROM DUAL
UNION ALL SELECT 25408,   'DS203', 'ESB',   'STD',       'RECEIVE_FILE_TYPE',         '接收文件类型',    'json',                                     1 FROM DUAL
UNION ALL SELECT 25409,   'DS203', 'ESB',   'STD',       'QUERY_FILE_TYPE',           '发送文件类型',    'json',                                     1 FROM DUAL
UNION ALL SELECT 25410,   'DS203', 'ESB',   'STD',       'RESPONSE_STRING',           '返回字符串',      '1',                                        1 FROM DUAL
;

--关系对照
--工厂参数的来源：通用参数FACTORY
--是否首次发送：来自接口参数IS_FIRST
--标识：来自发送表头THIRD_FILE_KEY
--接口代码：来自表头IF_CODE
--totalNum：因数据来自生成XML前一步，所以允许为空，否则验证不通过
--目前返回发送结果和发送查询数据集合在一块，实际当中一般要么是返回发送结果，要么是返回发送查询数据
DELETE FROM T_IF_ELEMENT WHERE IF_CODE = 'DS203';
INSERT INTO T_IF_ELEMENT(PK_ID, IF_CODE, TABLE_ID, ELEMENT_NAME, FIELD_NAME, ELEMENT_DESC, DEFAULT_VALUE, FIELD_TYPE, IS_ACTIVE, NULL_ABLE, MAX_LENGTH, INSERT_ABLE, UPDATE_ABLE, SORT_NO, DATA_CONVERT, DATA_FORMAT) 
--
--输入
          SELECT 25401, 'DS203', 'S_HEAD', 'id',       'ID',         '标识',        '$QH_THIRD_FILE_KEY',   1, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL
UNION ALL SELECT 25404, 'DS203', 'S_HEAD', 'ifCode',   'IF_CODE',    '接口代码',     '$QH_IF_CODE',          1, 1, 0, 0, 0, 0, 40, '', '' FROM DUAL
--
UNION ALL SELECT 25410, 'DS203', 'S_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',         22, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL  
UNION ALL SELECT 25411, 'DS203', 'S_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',          3, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 25412, 'DS203', 'S_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',          1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL  
UNION ALL SELECT 25413, 'DS203', 'S_MASTER', 'price',       'PRICE',         '产品价格', '',          1, 1, 1, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 25414, 'DS203', 'S_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',          1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL  
UNION ALL SELECT 25415, 'DS203', 'S_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',          1, 1, 1, 0, 0, 0, 60, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
--
UNION ALL SELECT 25422, 'DS203', 'S_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',           1, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 25423, 'DS203', 'S_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',           1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL  
UNION ALL SELECT 25424, 'DS203', 'S_DETAIL', 'price',      'PRICE',        '物料价格', '',           1, 1, 1, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 25425, 'DS203', 'S_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',           1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL  
UNION ALL SELECT 25426, 'DS203', 'S_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',           1, 1, 1, 0, 0, 0, 60, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
--
--返回
UNION ALL SELECT 25431, 'DS203', 'RECEIVE_MESSAGE', 'status',          'SUCCESS',     '成功标记',    '',          1, 1, 1, 0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 25432, 'DS203', 'RECEIVE_MESSAGE', 'error',           'MESSAGE',     '错误信息',    '',          1, 1, 1, 0, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 25433, 'DS203', 'RECEIVE_MESSAGE', 'productNo',       'PRODUCT_NO',  '产品编号',    '',          1, 1, 1, 0, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 25434, 'DS203', 'RECEIVE_MESSAGE', 'itemNo',          'ITEM_NO',     '物料编号',    '',          1, 1, 1, 0, 1, 1, 40, '', '' FROM DUAL
--
;

COMMIT;





