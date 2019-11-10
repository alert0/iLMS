
--接口
DELETE FROM T_IF_CONFIG WHERE IF_CODE='DS402';
INSERT INTO T_IF_CONFIG(IF_CODE, IF_NAME, TO_SYS_NAME, IF_TYPE, SERVICE_TYPE, SERVICE_CLASS, RECEIVE_TRANSATION_TYPE, SEND_SUCCESS_FLAG)
          SELECT 'DS402', '发送XML查询DQ402', 'ESB', 2, 'WS', '', 1, '1' FROM DUAL;
;

--发送配置
--APPROACH：最终发送XML的节点，多笔数据时要有，以防数据混乱
DELETE FROM T_IF_QUERY_CONFIG WHERE IF_CODE='DS402';
INSERT INTO T_IF_QUERY_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, APPROACH, EMPTY_FROM_DEFAULT, ALLOW_EMPTY, MAX_NUMBER, SORT_NO)
          SELECT 45201, 'DS402', 'S_EFFDATE',   '',             '生效时间',      '',    0, 0, 1,      1 FROM DUAL
UNION ALL SELECT 45202, 'DS402', 'S_PRODUCTNO', '',             '产品编号',      '',    0, 0, 100,    2 FROM DUAL
;

--接收配置
--RECEIVE_MESSAGE：固定，表示接收消息，结果反馈到日志
--RECEIVE_HEAD：固定，表示接收表头，当非直接数据时默认会最后接收
DELETE FROM T_IF_RECEIVE_CONFIG WHERE IF_CODE = 'DS402';
INSERT INTO T_IF_RECEIVE_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, TABLE_NAME, APPROACH, TABLE_TYPE, KEY_SEQUENCE, KEY_CLASS, CREATE_TYPE, UPDATE_TYPE, EMPTY_FROM_PARENT, SORT_NO)
          SELECT 45201, 'DS402', 'RECEIVE_MESSAGE',   '',                   '接收消息',     '',                     'sw/message',     4, '',             '', 0, 0, 0, 1 FROM DUAL
UNION ALL SELECT 45203, 'DS402', 'RECEIVE_MASTER',    '',                   '返回主表',     'TMP_R_MASTER',         'sw/data',        1, 'S_TMP',        '', 1, 1, 0, 3 FROM DUAL
UNION ALL SELECT 45204, 'DS402', 'RECEIVE_DETAIL',    'RECEIVE_MASTER',     '返回从表',     'TMP_R_DETAIL',         'item',           1, 'S_TMP',        '', 1, 1, 0, 4 FROM DUAL
UNION ALL SELECT 45205, 'DS402', 'RECEIVE_HEAD',      '',                   '接收表头',     'TMP_R_BATCH',          '_',              4, 'S_TMP',        '', 1, 1, 0, 5 FROM DUAL
;

--接口参数
DELETE FROM T_IF_PARAM WHERE IF_CODE = 'DS402';
INSERT INTO T_IF_PARAM(PK_ID, IF_CODE, TO_SYS_NAME, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, EDIT_ABLE)
          SELECT 45201, 'DS402', 'ESB',   'STD',       'HANDLER_CLASS',              '接口拦截类',      'com.smartwork.demo.handler.DS402Handler',  1 FROM DUAL
UNION ALL SELECT 45202, 'DS402', 'ESB',   'STD',       'QUERY_ELEMENT_ROOT',         '发送XML根节点',   'respone',                                  1 FROM DUAL
UNION ALL SELECT 45203, 'DS402', 'ESB',   'STD',       'REQUEST_PARAM_NAME',         '请求参数名',      'xml',                                      1 FROM DUAL
UNION ALL SELECT 45204, 'DS402', 'ESB',   'STD',       'SERVER_URL',                 '服务地址',       'http://127.0.0.1:8080/SmartWorkESB/services/dq402',  1 FROM DUAL
UNION ALL SELECT 45205, 'DS402', 'ESB',   'STD',       'ACTION_URL',                 '方法地址',       'http://ws.smartwork.com/query',            1 FROM DUAL
UNION ALL SELECT 45206, 'DS402', 'ESB',   'STD',       'USER_NAME',                  '访问用户',       'smartwork',                                1 FROM DUAL
UNION ALL SELECT 45207, 'DS402', 'ESB',   'STD',       'PASSWORD',                   '访问密码',       'smartwork',                                1 FROM DUAL
UNION ALL SELECT 45208, 'DS402', 'ESB',   'STD',       'RESPONSE_STRING',            '返回字符串',      '1',                               1 FROM DUAL
;

--关系对照
--标识：来自发送表头THIRD_FILE_KEY
--接口代码：来自表头IF_CODE
--totalNum：因数据来自生成XML前一步，所以允许为空，否则验证不通过
--目前返回发送结果和发送查询数据集合在一块，实际当中一般要么是返回发送结果，要么是返回发送查询数据
DELETE FROM T_IF_ELEMENT WHERE IF_CODE = 'DS402';
INSERT INTO T_IF_ELEMENT(PK_ID, IF_CODE, TABLE_ID, ELEMENT_NAME, FIELD_NAME, ELEMENT_DESC, DEFAULT_VALUE, FIELD_TYPE, IS_ACTIVE, NULL_ABLE, MAX_LENGTH, INSERT_ABLE, UPDATE_ABLE, SORT_NO, DATA_CONVERT, DATA_FORMAT) 
--
--输入
          SELECT 45201, 'DS402', 'S_EFFDATE', 'startEffDate',    'START_EFFDATE',   '开始生效时间',       '',          1, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL
UNION ALL SELECT 45202, 'DS402', 'S_EFFDATE', 'endEffDate',      'END_EFFDATE',     '接收生效时间',       '',          1, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
--
UNION ALL SELECT 45203, 'DS402', 'S_PRODUCTNO', 'productNo',    'PRODUCT_NO',     '产品编号',       '',    1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL
--
--返回
UNION ALL SELECT 45231, 'DS402', 'RECEIVE_MESSAGE', 'status',         'SUCCESS',     '成功标记',    '',          1, 1, 1, 0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 45232, 'DS402', 'RECEIVE_MESSAGE', 'error',          'MESSAGE',     '错误信息',    '',          1, 1, 1, 0, 1, 1, 20, '', '' FROM DUAL
--
UNION ALL SELECT 45251, 'DS402', 'RECEIVE_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',                     22, 1, 0, 0,  1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 45252, 'DS402', 'RECEIVE_MASTER', 'batchNo',     'BATCH_NO',      '生成批次', '$RH_THIRD_FILE_KEY',   21, 1, 1, 32, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 45253, 'DS402', 'RECEIVE_MASTER', 'ifCode',      'IF_CODE',       '接口代码', '$RH_IF_CODE',          23, 1, 0, 20, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 45254, 'DS402', 'RECEIVE_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',                      3, 1, 0, 30, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 45255, 'DS402', 'RECEIVE_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',                      1, 1, 0, 64, 1, 1, 50, '', '' FROM DUAL  
UNION ALL SELECT 45256, 'DS402', 'RECEIVE_MASTER', 'price',       'PRICE',         '产品价格', '',                      1, 1, 1, 4,  1, 1, 60, 'STRING2DOUBLE', '' FROM DUAL
UNION ALL SELECT 45257, 'DS402', 'RECEIVE_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',                      1, 1, 1, 6,  1, 1, 70, 'STRING2INT', '' FROM DUAL  
UNION ALL SELECT 45258, 'DS402', 'RECEIVE_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',                      1, 1, 1, 0,  1, 1, 80, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
UNION ALL SELECT 45259, 'DS402', 'RECEIVE_MASTER', 'createTime',  'CREATE_TIME',   '创建时间', '$RH_CREATE_TIME',       1, 1, 1, 0,  1, 0, 90, '', '' FROM DUAL
UNION ALL SELECT 45260, 'DS402', 'RECEIVE_MASTER', 'updateTime',  'UPDATE_TIME',   '修改时间', '$RH_CREATE_TIME',       1, 1, 1, 0,  1, 1, 100,'', '' FROM DUAL
--
UNION ALL SELECT 45261, 'DS402', 'RECEIVE_DETAIL', 'pkId',       'PK_ID',        '逻辑主键', '',                 22, 1, 1,  0, 1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 45262, 'DS402', 'RECEIVE_DETAIL', 'fkId',       'FK_ID',        '产品主键', '',                 25, 1, 0, 19, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 45263, 'DS402', 'RECEIVE_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',                  3, 1, 0, 64, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 45264, 'DS402', 'RECEIVE_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',                  1, 1, 0, 64, 1, 1, 40, '', '' FROM DUAL  
UNION ALL SELECT 45265, 'DS402', 'RECEIVE_DETAIL', 'price',      'PRICE',        '物料价格', '',                  1, 1, 1,  4, 1, 1, 50, 'STRING2DOUBLE', '' FROM DUAL
UNION ALL SELECT 45266, 'DS402', 'RECEIVE_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',                  1, 1, 1,  6, 1, 1, 60, 'STRING2INT', '' FROM DUAL  
UNION ALL SELECT 45267, 'DS402', 'RECEIVE_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',                  1, 1, 1,  0, 1, 1, 70, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
UNION ALL SELECT 45268, 'DS402', 'RECEIVE_DETAIL', 'createTime', 'CREATE_TIME',  '创建时间', '$RH_CREATE_TIME',   1, 1, 1,  0, 1, 0, 80, '', '' FROM DUAL
UNION ALL SELECT 45269, 'DS402', 'RECEIVE_DETAIL', 'updateTime', 'UPDATE_TIME',  '修改时间', '$RH_CREATE_TIME',   1, 1, 1,  0, 1, 1, 90,'', '' FROM DUAL
--
UNION ALL SELECT 45281, 'DS402', 'RECEIVE_HEAD', 'pkId',         'PK_ID',           '逻辑主键', '',                   22, 1, 0,  0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 45282, 'DS402', 'RECEIVE_HEAD', 'batchNo',      'BATCH_NO',        '生成批次', '$RH_THIRD_FILE_KEY', 21, 1, 0, 32, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 45283, 'DS402', 'RECEIVE_HEAD', 'ifCode',       'IF_CODE',         '接口代码', '$RH_IF_CODE',        21, 1, 0, 20, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 45284, 'DS402', 'RECEIVE_HEAD', 'createTime',   'CREATE_TIME',     '创建时间', '$RH_CREATE_TIME',    21, 1, 0,  0, 1, 1, 40, '', '' FROM DUAL  
;

COMMIT;





