
--任务计划
DELETE FROM T_IF_TIMER WHERE IF_CODE='DS104';
INSERT INTO T_IF_TIMER(JOB_CODE, JOB_DESC, IF_CODE, MASTER_QUERY_TABLE_ID, JOB_GROUP, JOB_CLASS, TRIGGER_NAME, TRIGGER_VALUE, UPDATE_STATE, RUN_STATE)
          SELECT 'DS104', '发送模拟RFC', 'DS104', '', 'DS104', 'com.smartwork.esb.job.ClientSenderJob', 'DS104', '0 0/1 * * * ?', 1, 1 FROM DUAL
;

--接口
--RFC配置文件：对方系统名+_rfc.jcoDestination;当前为：SAP_rfc.jcoDestination；开发时放在项目的根目录，正式时放在tomcat根目录，具体情况看报错。
DELETE FROM T_IF_CONFIG WHERE IF_CODE='DS104';
INSERT INTO T_IF_CONFIG(IF_CODE, IF_NAME, TO_SYS_NAME, IF_TYPE, SERVICE_TYPE, SERVICE_CLASS, RECEIVE_TRANSATION_TYPE, SEND_SUCCESS_FLAG)
          SELECT 'DS104', '发送模拟RFC', 'SAP', 2, 'RFC', 'com.smartwork.demo.service.DS104SenderService', 1, 'S' FROM DUAL;
;

--发送配置
--APPROACH：RFC的结构体名，为空时代表输入；注意没有在RFC存在的字段要设置为临时字段，否则报错
--RFC没有发送从表的情况，所以业务主键需要重复
DELETE FROM T_IF_QUERY_CONFIG WHERE IF_CODE='DS104';
INSERT INTO T_IF_QUERY_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, APPROACH, EMPTY_FROM_DEFAULT, ALLOW_EMPTY, MAX_NUMBER, SORT_NO)
          SELECT 11601, 'DS104', 'S_HEAD',   '',             '发送表头',     '',         1, 0, 1,    1 FROM DUAL
UNION ALL SELECT 11602, 'DS104', 'S_MASTER', '',             '发送主表',     'SDATA',    0, 0, 100,  2 FROM DUAL
UNION ALL SELECT 11603, 'DS104', 'S_DETAIL', 'S_MASTER',     '发送从表',     'SITEM',    0, 1, 0,    3 FROM DUAL
;

--
--有状态发送数据
--手工发送部分使用主键过滤
UPDATE T_IF_QUERY_CONFIG SET AUTO_QUERY_SQL = 'SELECT * FROM TMP_S_MASTER WHERE IS_SEND=0' WHERE PK_ID=11602;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_S_MASTER WHERE PK_ID IN (:PK_ID)' WHERE PK_ID=11602;
UPDATE T_IF_QUERY_CONFIG SET SUCCESS_SQL = 'UPDATE TMP_S_MASTER SET IS_SEND=1 WHERE PK_ID=:PK_ID' WHERE PK_ID=11602;
UPDATE T_IF_QUERY_CONFIG SET FAILURE_SQL = 'UPDATE TMP_S_MASTER SET FAILURE=FAILURE+1 WHERE PK_ID=:PK_ID' WHERE PK_ID=11602;

--查询参数PK_ID是主表的主键字段名(PK_ID)
UPDATE T_IF_QUERY_CONFIG SET AUTO_QUERY_SQL = 'SELECT * FROM TMP_S_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=11603;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_S_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=11603;

--接收配置
--RECEIVE_MESSAGE：固定，表示接收消息，结果反馈到日志
--RECEIVE_HEAD：固定，表示接收表头，当非直接数据时默认会最后接收
--APPROACH：RFC的结构体名，为空时代表输出；注意没有在RFC存在的字段要设置为临时字段，否则输出报错日志
--RFC没有接收主从表情况，所以返回从表也是主表；返回对方消息时因为我们要解析接收消息所以弄的表，但不会从RFC接收数据
--RECEIVE_TEMP模拟从表：因为主表数据接收完成后会清除，该配置数据完全继承主表，主要为了获取主表的逻辑主键数据
DELETE FROM T_IF_RECEIVE_CONFIG WHERE IF_CODE = 'DS104';
INSERT INTO T_IF_RECEIVE_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, TABLE_NAME, APPROACH, TABLE_TYPE, KEY_SEQUENCE, KEY_CLASS, CREATE_TYPE, UPDATE_TYPE, EMPTY_FROM_PARENT, SORT_NO)
          SELECT 11601, 'DS104', 'RECEIVE_MESSAGE',   '',                   '接收消息',     '',                     'RETURN',    4, '',             '', 0, 0, 0, 1 FROM DUAL
UNION ALL SELECT 11602, 'DS104', 'RECEIVE_THIRD',     'RECEIVE_MESSAGE',    '返回对方信息',  'TMP_R_THIRD',          '',          4, 'S_TMP',        '', 1, 1, 0, 2 FROM DUAL
UNION ALL SELECT 11603, 'DS104', 'RECEIVE_MASTER',    '',                   '返回主表',     'TMP_R_MASTER',         'RDATA',     1, 'S_TMP',        '', 1, 1, 0, 3 FROM DUAL
UNION ALL SELECT 11604, 'DS104', 'RECEIVE_TEMP',      'RECEIVE_MASTER',     '模拟从表',     '',                     '',          1, '',             '', 1, 1, 1, 5 FROM DUAL
UNION ALL SELECT 11605, 'DS104', 'RECEIVE_DETAIL',    '',                   '返回从表',     'TMP_R_DETAIL',         'RITEM',     1, 'S_TMP',        '', 1, 1, 0, 6 FROM DUAL
UNION ALL SELECT 11606, 'DS104', 'RECEIVE_HEAD',      '',                   '接收表头',     'TMP_R_BATCH',          '',          4, 'S_TMP',        '', 1, 1, 0, 7 FROM DUAL
;

--接口参数
--正常情况的异常表达式：一般用于RFC正常情况但返回异常（ABB的RFC->SAP->Bizztalk时发生）
DELETE FROM T_IF_PARAM WHERE IF_CODE = 'DS104';
INSERT INTO T_IF_PARAM(PK_ID, IF_CODE, TO_SYS_NAME, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, EDIT_ABLE)
          SELECT 11601, 'DS104', 'SAP',   'Other',     'IS_FIRST',               '是否首次发送',         '1',                                1 FROM DUAL
UNION ALL SELECT 11603, 'DS104', 'SAP',   'STD',       'HANDLER_CLASS',          '接口拦截类',          'com.smartwork.demo.handler.DS104Handler',  1 FROM DUAL
UNION ALL SELECT 11604, 'DS104', 'SAP',   'STD',       'FUN_NAME',               '调用方法名',          'XXXX',                              1 FROM DUAL
UNION ALL SELECT 11605, 'DS104', 'SAP',   'STD',       'COMMIT_FUN_NAME',        '提交方法名',          '',                                  1 FROM DUAL
UNION ALL SELECT 11606, 'DS104', 'SAP',   'STD',       'ROLLBACK_FUN_NAME',      '回滚方法名',          '',                                  1 FROM DUAL
UNION ALL SELECT 11607, 'DS104', 'SAP',   'STD',       'RFC_EXCEPTION_REGEX',    '正常情况的异常表达式',   '[1-9][0-9]* IDocs were generated',  1 FROM DUAL
;

--关系对照
--工厂参数的来源：通用参数FACTORY
--是否首次发送：来自接口参数IS_FIRST
--标识：来自发送表头THIRD_FILE_KEY
--接口代码：来自表头IF_CODE
--totalNum：因数据来自生成XML前一步，所以允许为空，否则验证不通过
--目前返回发送结果和发送查询数据集合在一块，实际当中一般要么是返回发送结果，要么是返回发送查询数据
DELETE FROM T_IF_ELEMENT WHERE IF_CODE = 'DS104';
INSERT INTO T_IF_ELEMENT(PK_ID, IF_CODE, TABLE_ID, ELEMENT_NAME, FIELD_NAME, ELEMENT_DESC, DEFAULT_VALUE, FIELD_TYPE, IS_ACTIVE, NULL_ABLE, MAX_LENGTH, INSERT_ABLE, UPDATE_ABLE, SORT_NO, DATA_CONVERT, DATA_FORMAT) 
--
--输入
          SELECT 11601, 'DS104', 'S_HEAD', 'plant',    'FACTORY',    '工厂',        '$QP_FACTORY',          1, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL
UNION ALL SELECT 11602, 'DS104', 'S_HEAD', 'first',    'IS_FIRST',   '是否首次发送',  '$QP_IS_FIRST',         1, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 11603, 'DS104', 'S_HEAD', 'token',    'TOKEN',      '标识',        '$QH_THIRD_FILE_KEY',   1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL
UNION ALL SELECT 11604, 'DS104', 'S_HEAD', 'ifCode',   'IF_CODE',    '接口代码',     '$QH_IF_CODE',          1, 1, 0, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 11605, 'DS104', 'S_HEAD', 'totalNum', 'TOTAL_NUM',  '数据总数',     '',                     1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL
--
UNION ALL SELECT 11610, 'DS104', 'S_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',         22, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL  
UNION ALL SELECT 11611, 'DS104', 'S_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',          3, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 11612, 'DS104', 'S_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',          1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL  
UNION ALL SELECT 11613, 'DS104', 'S_MASTER', 'price',       'PRICE',         '产品价格', '',          1, 1, 1, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 11614, 'DS104', 'S_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',          1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL  
UNION ALL SELECT 11615, 'DS104', 'S_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',          6, 1, 1, 0, 0, 0, 60, '', '' FROM DUAL
--
UNION ALL SELECT 11621, 'DS104', 'S_DETAIL', 'productNo',  'PRODUCT_NO',   '产品编号', '',           7, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL  
UNION ALL SELECT 11622, 'DS104', 'S_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',           1, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 11623, 'DS104', 'S_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',           1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL  
UNION ALL SELECT 11624, 'DS104', 'S_DETAIL', 'price',      'PRICE',        '物料价格', '',           1, 1, 1, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 11625, 'DS104', 'S_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',           1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL  
UNION ALL SELECT 11626, 'DS104', 'S_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',           6, 1, 1, 0, 0, 0, 60, '', '' FROM DUAL  
--
--返回
UNION ALL SELECT 11631, 'DS104', 'RECEIVE_MESSAGE', 'status',         'SUCCESS',     '成功标记',    '',          1, 1, 1, 0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 11632, 'DS104', 'RECEIVE_MESSAGE', 'error',          'MESSAGE',     '错误信息',    '',          1, 1, 1, 0, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 11633, 'DS104', 'RECEIVE_MESSAGE', 'errorCode',      'ERROR_CODE',  '错误代码',    '',          1, 1, 1, 0, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11634, 'DS104', 'RECEIVE_MESSAGE', 'productNo',      'PRODUCT_NO',  '产品编号',    '',          1, 1, 1, 0, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 11635, 'DS104', 'RECEIVE_MESSAGE', 'pick',           'PICK',        '对方标识',    '',          1, 1, 1, 0, 1, 1, 50, '', '' FROM DUAL
--
UNION ALL SELECT 11641, 'DS104', 'RECEIVE_THIRD', 'pick',           'DATA',        '对方标识',    '',                  27, 1, 0, 64, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 11642, 'DS104', 'RECEIVE_THIRD', 'ifCode',         'IF_CODE',     '接口代码',    '$RH_IF_CODE',       21, 1, 0, 20, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 11643, 'DS104', 'RECEIVE_THIRD', 'createTime',     'CREATE_TIME', '创建时间',    '$SYSDATE',          21, 1, 0, 0,  1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11644, 'DS104', 'RECEIVE_THIRD', 'fkId',           'FK_ID',       '产品主键',    '',                  21, 1, 0, 64, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 11645, 'DS104', 'RECEIVE_THIRD', 'pkId',           'PK_ID',       '逻辑主键',    '',                  22, 1, 0, 19, 1, 1, 50, '', '' FROM DUAL
--
UNION ALL SELECT 11651, 'DS104', 'RECEIVE_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',                     22, 1, 0, 0,  1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 11652, 'DS104', 'RECEIVE_MASTER', 'batchNo',     'BATCH_NO',      '生成批次', '$RH_THIRD_FILE_KEY',   21, 1, 1, 32, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 11653, 'DS104', 'RECEIVE_MASTER', 'ifCode',      'IF_CODE',       '接口代码', '$RH_IF_CODE',          23, 1, 0, 20, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11654, 'DS104', 'RECEIVE_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',                      3, 1, 0, 30, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 11655, 'DS104', 'RECEIVE_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',                      1, 1, 0, 64, 1, 1, 50, '', '' FROM DUAL  
UNION ALL SELECT 11656, 'DS104', 'RECEIVE_MASTER', 'price',       'PRICE',         '产品价格', '',                      1, 1, 1, 4,  1, 1, 60, '', '' FROM DUAL
UNION ALL SELECT 11657, 'DS104', 'RECEIVE_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',                      1, 1, 1, 6,  1, 1, 70, '', '' FROM DUAL  
UNION ALL SELECT 11658, 'DS104', 'RECEIVE_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',                      6, 1, 1, 0,  1, 1, 80, '', '' FROM DUAL
UNION ALL SELECT 11659, 'DS104', 'RECEIVE_MASTER', 'createTime',  'CREATE_TIME',   '创建时间', '$RH_CREATE_TIME',      21, 1, 1, 0,  1, 0, 90, '', '' FROM DUAL
UNION ALL SELECT 11660, 'DS104', 'RECEIVE_MASTER', 'updateTime',  'UPDATE_TIME',   '修改时间', '$RH_CREATE_TIME',      21, 1, 1, 0,  1, 1, 100,'', '' FROM DUAL
--
UNION ALL SELECT 11661, 'DS104', 'RECEIVE_DETAIL', 'pkId',       'PK_ID',        '逻辑主键', '',                 22, 1, 1,  0, 1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 11662, 'DS104', 'RECEIVE_DETAIL', 'fkId',       'FK_ID',        '产品主键', '',                 23, 1, 0, 19, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 11663, 'DS104', 'RECEIVE_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',                  3, 1, 0, 64, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11664, 'DS104', 'RECEIVE_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',                  1, 1, 0, 64, 1, 1, 40, '', '' FROM DUAL  
UNION ALL SELECT 11665, 'DS104', 'RECEIVE_DETAIL', 'price',      'PRICE',        '物料价格', '',                  1, 1, 1,  4, 1, 1, 50, '', '' FROM DUAL
UNION ALL SELECT 11666, 'DS104', 'RECEIVE_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',                  1, 1, 1,  6, 1, 1, 60, '', '' FROM DUAL  
UNION ALL SELECT 11667, 'DS104', 'RECEIVE_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',                  6, 1, 1,  0, 1, 1, 70, '', '' FROM DUAL  
UNION ALL SELECT 11668, 'DS104', 'RECEIVE_DETAIL', 'createTime', 'CREATE_TIME',  '创建时间', '$RH_CREATE_TIME',  21, 1, 1,  0, 1, 0, 80, '', '' FROM DUAL
UNION ALL SELECT 11669, 'DS104', 'RECEIVE_DETAIL', 'updateTime', 'UPDATE_TIME',  '修改时间', '$RH_CREATE_TIME',  21, 1, 1,  0, 1, 1, 90,'', '' FROM DUAL
UNION ALL SELECT 11670, 'DS104', 'RECEIVE_DETAIL', 'productNo',  'PRODUCT_NO',   '产品编号', '',                  1, 1, 0,  0, 0, 0, 100, '', '' FROM DUAL
--
UNION ALL SELECT 11671, 'DS104', 'RECEIVE_TEMP',  'productNo',   'PRODUCT_NO',  '产品编号',  '',  27, 1, 1,  0, 1, 1, 10,'', '' FROM DUAL
--
UNION ALL SELECT 11681, 'DS104', 'RECEIVE_HEAD', 'pkId',         'PK_ID',           '逻辑主键', '',                   22, 1, 0,  0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 11682, 'DS104', 'RECEIVE_HEAD', 'batchNo',      'BATCH_NO',        '生成批次', '$RH_THIRD_FILE_KEY', 21, 1, 0, 32, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 11683, 'DS104', 'RECEIVE_HEAD', 'ifCode',       'IF_CODE',         '接口代码', '$RH_IF_CODE',        21, 1, 0, 20, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11684, 'DS104', 'RECEIVE_HEAD', 'createTime',   'CREATE_TIME',     '创建时间', '$RH_CREATE_TIME',    21, 1, 0,  0, 1, 1, 40, '', '' FROM DUAL  
;

COMMIT;





