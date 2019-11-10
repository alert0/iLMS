
--任务计划
DELETE FROM T_IF_TIMER WHERE IF_CODE='DS102';
INSERT INTO T_IF_TIMER(JOB_CODE, JOB_DESC, IF_CODE, MASTER_QUERY_TABLE_ID, JOB_GROUP, JOB_CLASS, TRIGGER_NAME, TRIGGER_VALUE, UPDATE_STATE, RUN_STATE)
          SELECT 'DS102', '发送模拟XML', 'DS102', '', 'DS102', 'com.smartwork.esb.job.ClientSenderJob', 'DS102', '0 0/1 * * * ?', 1, 1 FROM DUAL
;

--接口
DELETE FROM T_IF_CONFIG WHERE IF_CODE='DS102';
INSERT INTO T_IF_CONFIG(IF_CODE, IF_NAME, TO_SYS_NAME, IF_TYPE, SERVICE_TYPE, SERVICE_CLASS, RECEIVE_TRANSATION_TYPE, SEND_SUCCESS_FLAG)
          SELECT 'DS102', '发送模拟XML', 'ESB', 2, 'Other', 'com.smartwork.demo.service.DS102SenderService', 1, 'S' FROM DUAL;
;

--发送配置
--APPROACH：最终发送XML的节点，多笔数据时要有，以防数据混乱
DELETE FROM T_IF_QUERY_CONFIG WHERE IF_CODE='DS102';
INSERT INTO T_IF_QUERY_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, APPROACH, EMPTY_FROM_DEFAULT, ALLOW_EMPTY, MAX_NUMBER, SORT_NO)
          SELECT 11201, 'DS102', 'S_HEAD',   '',             '发送表头',     'head',    1, 0, 1,    1 FROM DUAL
UNION ALL SELECT 11202, 'DS102', 'S_MASTER', '',             '发送主表',     'data',    0, 0, 100,  2 FROM DUAL
UNION ALL SELECT 11203, 'DS102', 'S_DETAIL', 'S_MASTER',     '发送从表',     'item',    0, 1, 0,    3 FROM DUAL
;

--
--有状态发送数据
--手工发送部分使用主键过滤
UPDATE T_IF_QUERY_CONFIG SET AUTO_QUERY_SQL = 'SELECT * FROM TMP_S_MASTER WHERE IS_SEND=0' WHERE PK_ID=11202;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_S_MASTER WHERE PK_ID IN (:PK_ID)' WHERE PK_ID=11202;
UPDATE T_IF_QUERY_CONFIG SET SUCCESS_SQL = 'UPDATE TMP_S_MASTER SET IS_SEND=1 WHERE PK_ID=:PK_ID' WHERE PK_ID=11202;
UPDATE T_IF_QUERY_CONFIG SET FAILURE_SQL = 'UPDATE TMP_S_MASTER SET FAILURE=FAILURE+1 WHERE PK_ID=:PK_ID' WHERE PK_ID=11202;

--查询参数PK_ID是主表的主键字段名(PK_ID)
UPDATE T_IF_QUERY_CONFIG SET AUTO_QUERY_SQL = 'SELECT * FROM TMP_S_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=11203;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_S_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=11203;

--接收配置
--RECEIVE_MESSAGE：固定，表示接收消息，结果反馈到日志
--RECEIVE_HEAD：固定，表示接收表头，当非直接数据时默认会最后接收
--接收路径：顶级主表从XML根目录开始算
DELETE FROM T_IF_RECEIVE_CONFIG WHERE IF_CODE = 'DS102';
INSERT INTO T_IF_RECEIVE_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, TABLE_NAME, APPROACH, TABLE_TYPE, KEY_SEQUENCE, KEY_CLASS, CREATE_TYPE, UPDATE_TYPE, EMPTY_FROM_PARENT, SORT_NO)
          SELECT 11201, 'DS102', 'RECEIVE_MESSAGE',   '',                   '接收消息',     '',                     'respone/message',   4, '',             '', 0, 0, 0, 1 FROM DUAL
UNION ALL SELECT 11202, 'DS102', 'RECEIVE_THIRD',     'RECEIVE_MESSAGE',    '返回对方信息',  'TMP_R_THIRD',          '_',                 4, 'S_TMP',        '', 1, 1, 0, 2 FROM DUAL
UNION ALL SELECT 11203, 'DS102', 'RECEIVE_MASTER',    '',                   '返回主表',     'TMP_R_MASTER',         'respone/data',      1, 'S_TMP',        '', 1, 1, 0, 3 FROM DUAL
UNION ALL SELECT 11204, 'DS102', 'RECEIVE_DETAIL',    'RECEIVE_MASTER',     '返回从表',     'TMP_R_DETAIL',         'Item',              1, 'S_TMP',        '', 1, 1, 0, 4 FROM DUAL
UNION ALL SELECT 11205, 'DS102', 'RECEIVE_HEAD',      '',                   '接收表头',     'TMP_R_BATCH',          '_',                 4, 'S_TMP',        '', 1, 1, 0, 5 FROM DUAL
;

--接口参数
DELETE FROM T_IF_PARAM WHERE IF_CODE = 'DS102';
INSERT INTO T_IF_PARAM(PK_ID, IF_CODE, TO_SYS_NAME, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, EDIT_ABLE)
          SELECT 11201,   'DS102', 'ESB',   'Other',     'IS_FIRST',                  '是否首次发送',     '1',                                1 FROM DUAL
UNION ALL SELECT 11203,   'DS102', 'ESB',   'STD',       'HANDLER_CLASS',             '接口拦截类',      'com.smartwork.demo.handler.DS102Handler',  1 FROM DUAL
UNION ALL SELECT 11204,   'DS102', 'ESB',   'STD',       'GENERATE_EMPTY',            '发送XML生成空节点','1',                                 1 FROM DUAL
UNION ALL SELECT 11205,   'DS102', 'ESB',   'STD',       'QUERY_ELEMENT_ROOT',        '发送XML根节点',   'sw',                                1 FROM DUAL
UNION ALL SELECT 11206,   'DS102', 'ESB',   'STD',       'QUERY_ELEMENT_ROOT_ATTR',   '发送XML根节点属性','xmlns=http://sw.com.cn;att2=xxx',   1 FROM DUAL
;

--关系对照
--工厂参数的来源：通用参数FACTORY
--是否首次发送：来自接口参数IS_FIRST
--标识：来自发送表头THIRD_FILE_KEY
--接口代码：来自表头IF_CODE
--totalNum：因数据来自生成XML前一步，所以允许为空，否则验证不通过
--目前返回发送结果和发送查询数据集合在一块，实际当中一般要么是返回发送结果，要么是返回发送查询数据
DELETE FROM T_IF_ELEMENT WHERE IF_CODE = 'DS102';
INSERT INTO T_IF_ELEMENT(PK_ID, IF_CODE, TABLE_ID, ELEMENT_NAME, FIELD_NAME, ELEMENT_DESC, DEFAULT_VALUE, FIELD_TYPE, IS_ACTIVE, NULL_ABLE, MAX_LENGTH, INSERT_ABLE, UPDATE_ABLE, SORT_NO, DATA_CONVERT, DATA_FORMAT) 
--
--输入
          SELECT 11201, 'DS102', 'S_HEAD', 'plant',    'FACTORY',    '工厂',       '$QP_FACTORY',          1, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL
UNION ALL SELECT 11202, 'DS102', 'S_HEAD', 'first',    'IS_FIRST',   '是否首次发送',  '$QP_IS_FIRST',         1, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 11203, 'DS102', 'S_HEAD', 'token',    'TOKEN',      '标识',       '$QH_THIRD_FILE_KEY',    1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL
UNION ALL SELECT 11204, 'DS102', 'S_HEAD', 'ifCode',   'IF_CODE',    '接口代码',     '$QH_IF_CODE',          1, 1, 0, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 11205, 'DS102', 'S_HEAD', 'totalNum', 'TOTAL_NUM',  '数据总数',     '',                     1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL
--
UNION ALL SELECT 11210, 'DS102', 'S_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',         22, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL  
UNION ALL SELECT 11211, 'DS102', 'S_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',          3, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 11212, 'DS102', 'S_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',          1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL  
UNION ALL SELECT 11213, 'DS102', 'S_MASTER', 'price',       'PRICE',         '产品价格', '',          1, 1, 1, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 11214, 'DS102', 'S_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',          1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL  
UNION ALL SELECT 11215, 'DS102', 'S_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',          1, 1, 1, 0, 0, 0, 60, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
--
UNION ALL SELECT 11221, 'DS102', 'S_DETAIL', 'productNo',  'PRODUCT_NO',   '产品编号', '',           7, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL  
UNION ALL SELECT 11222, 'DS102', 'S_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',           1, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 11223, 'DS102', 'S_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',           1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL  
UNION ALL SELECT 11224, 'DS102', 'S_DETAIL', 'price',      'PRICE',        '物料价格', '',           1, 1, 1, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 11225, 'DS102', 'S_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',           1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL  
UNION ALL SELECT 11226, 'DS102', 'S_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',           1, 1, 1, 0, 0, 0, 60, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
--
--返回
UNION ALL SELECT 11231, 'DS102', 'RECEIVE_MESSAGE', 'status',         'SUCCESS',     '成功标记',    '',          1, 1, 1, 0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 11232, 'DS102', 'RECEIVE_MESSAGE', 'error',          'MESSAGE',     '错误信息',    '',          1, 1, 1, 0, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 11233, 'DS102', 'RECEIVE_MESSAGE', 'errorCode',      'ERROR_CODE',  '错误代码',    '',          1, 1, 1, 0, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11234, 'DS102', 'RECEIVE_MESSAGE', 'productNo',      'PRODUCT_NO',  '产品编号',    '',          1, 1, 1, 0, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 11235, 'DS102', 'RECEIVE_MESSAGE', 'pick',           'PICK',        '对方标识',    '',          1, 1, 1, 0, 1, 1, 50, '', '' FROM DUAL
--
UNION ALL SELECT 11241, 'DS102', 'RECEIVE_THIRD', 'pick',           'DATA',        '对方标识',    '',                  7,  1, 0, 64, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 11242, 'DS102', 'RECEIVE_THIRD', 'ifCode',         'IF_CODE',     '接口代码',    '$RH_IF_CODE',       21, 1, 0, 20, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 11243, 'DS102', 'RECEIVE_THIRD', 'createTime',     'CREATE_TIME', '创建时间',    '$SYSDATE',          21, 1, 0, 0,  1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11244, 'DS102', 'RECEIVE_THIRD', 'fkId',           'FK_ID',       '产品主键',    '',                   1, 1, 0, 64, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 11245, 'DS102', 'RECEIVE_THIRD', 'pkId',           'PK_ID',       '逻辑主键',    '',                  22, 1, 0, 19, 1, 1, 50, '', '' FROM DUAL
--
UNION ALL SELECT 11251, 'DS102', 'RECEIVE_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',                     22, 1, 0, 0,  1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 11252, 'DS102', 'RECEIVE_MASTER', 'batchNo',     'BATCH_NO',      '生成批次', '$RH_THIRD_FILE_KEY',   21, 1, 1, 32, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 11253, 'DS102', 'RECEIVE_MASTER', 'ifCode',      'IF_CODE',       '接口代码', '$RH_IF_CODE',          23, 1, 0, 20, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11254, 'DS102', 'RECEIVE_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',                      3, 1, 0, 30, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 11255, 'DS102', 'RECEIVE_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',                      1, 1, 0, 64, 1, 1, 50, '', '' FROM DUAL  
UNION ALL SELECT 11256, 'DS102', 'RECEIVE_MASTER', 'price',       'PRICE',         '产品价格', '',                      1, 1, 1, 4,  1, 1, 60, 'STRING2DOUBLE', '' FROM DUAL
UNION ALL SELECT 11257, 'DS102', 'RECEIVE_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',                      1, 1, 1, 6,  1, 1, 70, 'STRING2INT', '' FROM DUAL  
UNION ALL SELECT 11258, 'DS102', 'RECEIVE_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',                      1, 1, 1, 0,  1, 1, 80, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
UNION ALL SELECT 11259, 'DS102', 'RECEIVE_MASTER', 'createTime',  'CREATE_TIME',   '创建时间', '$RH_CREATE_TIME',       1, 1, 1, 0,  1, 0, 90, '', '' FROM DUAL
UNION ALL SELECT 11260, 'DS102', 'RECEIVE_MASTER', 'updateTime',  'UPDATE_TIME',   '修改时间', '$RH_CREATE_TIME',       1, 1, 1, 0,  1, 1, 100,'', '' FROM DUAL
--
UNION ALL SELECT 11261, 'DS102', 'RECEIVE_DETAIL', 'pkId',       'PK_ID',        '逻辑主键', '',                 22, 1, 1,  0, 1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 11262, 'DS102', 'RECEIVE_DETAIL', 'fkId',       'FK_ID',        '产品主键', '',                 25, 1, 0, 19, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 11263, 'DS102', 'RECEIVE_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',                  3, 1, 0, 64, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11264, 'DS102', 'RECEIVE_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',                  1, 1, 0, 64, 1, 1, 40, '', '' FROM DUAL  
UNION ALL SELECT 11265, 'DS102', 'RECEIVE_DETAIL', 'price',      'PRICE',        '物料价格', '',                  1, 1, 1,  4, 1, 1, 50, 'STRING2DOUBLE', '' FROM DUAL
UNION ALL SELECT 11266, 'DS102', 'RECEIVE_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',                  1, 1, 1,  6, 1, 1, 60, 'STRING2INT', '' FROM DUAL  
UNION ALL SELECT 11267, 'DS102', 'RECEIVE_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',                  1, 1, 1,  0, 1, 1, 70, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
UNION ALL SELECT 11268, 'DS102', 'RECEIVE_DETAIL', 'createTime', 'CREATE_TIME',  '创建时间', '$RH_CREATE_TIME',   1, 1, 1,  0, 1, 0, 80, '', '' FROM DUAL
UNION ALL SELECT 11269, 'DS102', 'RECEIVE_DETAIL', 'updateTime', 'UPDATE_TIME',  '修改时间', '$RH_CREATE_TIME',   1, 1, 1,  0, 1, 1, 90,'', '' FROM DUAL
--
UNION ALL SELECT 11281, 'DS102', 'RECEIVE_HEAD', 'pkId',         'PK_ID',           '逻辑主键', '',                   22, 1, 0,  0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 11282, 'DS102', 'RECEIVE_HEAD', 'batchNo',      'BATCH_NO',        '生成批次', '$RH_THIRD_FILE_KEY', 21, 1, 0, 32, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 11283, 'DS102', 'RECEIVE_HEAD', 'ifCode',       'IF_CODE',         '接口代码', '$RH_IF_CODE',        21, 1, 0, 20, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11284, 'DS102', 'RECEIVE_HEAD', 'createTime',   'CREATE_TIME',     '创建时间', '$RH_CREATE_TIME',    21, 1, 0,  0, 1, 1, 40, '', '' FROM DUAL  
;

COMMIT;





