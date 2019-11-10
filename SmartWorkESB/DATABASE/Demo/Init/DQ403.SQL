
--接口
DELETE FROM T_IF_CONFIG WHERE IF_CODE='DQ403';
INSERT INTO T_IF_CONFIG(IF_CODE, IF_NAME, TO_SYS_NAME, IF_TYPE, SERVICE_TYPE, RECEIVE_TRANSATION_TYPE)
          SELECT 'DQ403', 'webservice查询json数据', 'ESB', 1, 'Other', 1 FROM DUAL;
;

--接收配置
--RECEIVE_MESSAGE：固定，表示接收消息，结果反馈到日志
--RECEIVE_HEAD：固定，表示接收表头，当非直接数据时默认会最后接收
DELETE FROM T_IF_RECEIVE_CONFIG WHERE IF_CODE = 'DQ403';
INSERT INTO T_IF_RECEIVE_CONFIG(PK_ID, IF_CODE, TABLE_ID, TABLE_DESC, APPROACH, IS_VALUE_APPROACH, EMPTY_FROM_PARENT, SORT_NO)
          SELECT 41405, 'DQ403', 'RECEIVE_EFFDATE',         '接收生效时间',    '',           0,    0, 1 FROM DUAL
UNION ALL SELECT 41406, 'DQ403', 'RECEIVE_PRODUCT',         '接收产品编号',    '/productNo', 1,    0, 2 FROM DUAL
;

--查询配置
--APPROACH：最终发送XML的节点，多笔数据时要有，以防数据混乱
--主要是模拟不同参数的查询效果，当然可以使用接口参数配置SQL，拦截中根据判断直接设置SQL处理
DELETE FROM T_IF_QUERY_CONFIG WHERE IF_CODE='DQ403';
INSERT INTO T_IF_QUERY_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, APPROACH, EMPTY_FROM_DEFAULT, ALLOW_EMPTY, MAX_NUMBER, SORT_NO)
          SELECT 41401, 'DQ403', 'Q_HEAD',         '',                   '统计',        '',              0, 1, 1,    1 FROM DUAL
UNION ALL SELECT 41402, 'DQ403', 'EFFDATE_MASTER', '',                   '生效日期查询主表',     'data',    0, 1, 0,    2 FROM DUAL
UNION ALL SELECT 41403, 'DQ403', 'EFFDATE_DETAIL', 'EFFDATE_MASTER',     '生效日期查询从表',     'item',    0, 1, 0,    3 FROM DUAL
UNION ALL SELECT 41404, 'DQ403', 'PRODUCT_MASTER', '',                   '产品编号查询主表',     'data',    0, 1, 0,    4 FROM DUAL
UNION ALL SELECT 41405, 'DQ403', 'PRODUCT_DETAIL', 'PRODUCT_MASTER',     '产品编号查询从表',     'item',    0, 1, 0,    5 FROM DUAL
UNION ALL SELECT 41406, 'DQ403', 'QUERY_MESSAGE',  '',                   '返回信息',           'message', 0, 1, 0,    6 FROM DUAL
;

--
--因为是查询，所以只有手工部分
--参数要与对照关系的本方字段一致
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_Q_MASTER WHERE EFF_DATE BETWEEN :START_DATE AND :END_DATE' WHERE PK_ID=41402;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_Q_MASTER WHERE PRODUCT_NO IN (:PRODUCT_NO)' WHERE PK_ID=41404;

--查询参数PK_ID是主表的主键字段名(PK_ID)
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_Q_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=41403;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_Q_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=41405;

--接口参数
DELETE FROM T_IF_PARAM WHERE IF_CODE = 'DQ403';
INSERT INTO T_IF_PARAM(PK_ID, IF_CODE, TO_SYS_NAME, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, EDIT_ABLE)
          SELECT 41401, 'DQ403', 'ESB',   'STD',       'HANDLER_CLASS',           '接口拦截类',             'com.smartwork.demo.handler.DQ403Handler',  1 FROM DUAL
UNION ALL SELECT 41402, 'DQ403', 'ESB',   'STD',       'GENERATE_EMPTY',          '返回Json生成空节点',       '1',                                 1 FROM DUAL
UNION ALL SELECT 41403, 'DQ403', 'ESB',   'STD',       'RECEIVE_FILE_TYPE',       '接收文件类型',          'json',                              1 FROM DUAL
UNION ALL SELECT 41404, 'DQ403', 'ESB',   'STD',       'QUERY_FILE_TYPE',         '发送文件类型',          'json',                              1 FROM DUAL
;

--关系对照
--接收有主从结构时，主表一定要配置从表的查询SQL参数对照并且实在主键
--产品价格、产品数量转换的原因：读取数据库数据的类为java.math.BigDecimal，与返回不兼容
DELETE FROM T_IF_ELEMENT WHERE IF_CODE = 'DQ403';
INSERT INTO T_IF_ELEMENT(PK_ID, IF_CODE, TABLE_ID, ELEMENT_NAME, FIELD_NAME, ELEMENT_DESC, DEFAULT_VALUE, FIELD_TYPE, IS_ACTIVE, NULL_ABLE, MAX_LENGTH, INSERT_ABLE, UPDATE_ABLE, SORT_NO, DATA_CONVERT, DATA_FORMAT) 
--
--输入
          SELECT 41401, 'DQ403', 'RECEIVE_EFFDATE', 'startEffDate',    'START_DATE',      '开始生效时间', '1900-01-01',        1, 1, 1, 0, 1, 1, 30, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
UNION ALL SELECT 41402, 'DQ403', 'RECEIVE_EFFDATE', 'endEffDate',      'END_DATE',        '结束生效时间', '2999-01-01',        1, 1, 1, 0, 1, 1, 40, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
--
UNION ALL SELECT 41403, 'DQ403', 'RECEIVE_PRODUCT', 'productNo',       'PRODUCT_NO',      '产品编号',    '',                  1, 1, 0, 0, 1, 1, 50, '', '' FROM DUAL
--
--
UNION ALL SELECT 41453, 'DQ403', 'EFFDATE_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',                     22, 1, 1,  0, 1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 41454, 'DQ403', 'EFFDATE_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',                      1, 1, 1, 0, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 41455, 'DQ403', 'EFFDATE_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',                      1, 1, 1, 0, 1, 1, 50, '', '' FROM DUAL  
UNION ALL SELECT 41456, 'DQ403', 'EFFDATE_MASTER', 'price',       'PRICE',         '产品价格', '',                      1, 1, 1, 0,  1, 1, 60, '', '' FROM DUAL
UNION ALL SELECT 41457, 'DQ403', 'EFFDATE_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',                      1, 1, 1, 0,  1, 1, 70, '', '' FROM DUAL  
UNION ALL SELECT 41458, 'DQ403', 'EFFDATE_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',                      1, 1, 1, 0,  1, 1, 80, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
--
UNION ALL SELECT 41463, 'DQ403', 'EFFDATE_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',                  1, 1, 1,  0, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 41464, 'DQ403', 'EFFDATE_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',                  1, 1, 1,  0, 1, 1, 40, '', '' FROM DUAL  
UNION ALL SELECT 41465, 'DQ403', 'EFFDATE_DETAIL', 'price',      'PRICE',        '物料价格', '',                  1, 1, 1,  0, 1, 1, 50, '', '' FROM DUAL
UNION ALL SELECT 41466, 'DQ403', 'EFFDATE_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',                  1, 1, 1,  0, 1, 1, 60, '', '' FROM DUAL  
UNION ALL SELECT 41467, 'DQ403', 'EFFDATE_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',                  1, 1, 1,  0, 1, 1, 70, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
--
UNION ALL SELECT 41553, 'DQ403', 'PRODUCT_MASTER', 'pkId',        'PK_ID',        '逻辑主键', '',                      22, 1, 1,  0, 1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 41554, 'DQ403', 'PRODUCT_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',                      1, 1, 1, 0, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 41555, 'DQ403', 'PRODUCT_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',                      1, 1, 1, 0, 1, 1, 50, '', '' FROM DUAL  
UNION ALL SELECT 41556, 'DQ403', 'PRODUCT_MASTER', 'price',       'PRICE',         '产品价格', '',                      1, 1, 1, 0,  1, 1, 60, '', '' FROM DUAL
UNION ALL SELECT 41557, 'DQ403', 'PRODUCT_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',                      1, 1, 1, 0,  1, 1, 70, '', '' FROM DUAL  
UNION ALL SELECT 41558, 'DQ403', 'PRODUCT_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',                      1, 1, 1, 0,  1, 1, 80, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
--
UNION ALL SELECT 41563, 'DQ403', 'PRODUCT_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',                  1, 1, 1,  0, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 41564, 'DQ403', 'PRODUCT_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',                  1, 1, 1,  0, 1, 1, 40, '', '' FROM DUAL  
UNION ALL SELECT 41565, 'DQ403', 'PRODUCT_DETAIL', 'price',      'PRICE',        '物料价格', '',                  1, 1, 1,  0, 1, 1, 50, '', '' FROM DUAL
UNION ALL SELECT 41566, 'DQ403', 'PRODUCT_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',                  1, 1, 1,  0, 1, 1, 60, '', '' FROM DUAL  
UNION ALL SELECT 41567, 'DQ403', 'PRODUCT_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',                  1, 1, 1,  0, 1, 1, 70, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
--
UNION ALL SELECT 41576, 'DQ403', 'Q_HEAD', 'id',              '-',        '返回主键', '',                   1, 1, 1, 0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 41577, 'DQ403', 'Q_HEAD', 'totalNum',        '-',        '返回总数', '',                   1, 1, 1, 0, 1, 1, 20, '', '' FROM DUAL  
--
UNION ALL SELECT 41586, 'DQ403', 'QUERY_MESSAGE', 'status',       'SUCCESS',        '成功状态', '',                   1, 1, 0,  0, 1, 1, 10, 'BOOL2INT', '' FROM DUAL
UNION ALL SELECT 41587, 'DQ403', 'QUERY_MESSAGE', 'error',        'MESSAGE',        '错误信息', '',                   1, 1, 0,  0, 1, 1, 20, '', '' FROM DUAL  
;

COMMIT;





