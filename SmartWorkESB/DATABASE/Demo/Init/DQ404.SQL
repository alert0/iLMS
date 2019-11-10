
--接口
DELETE FROM T_IF_CONFIG WHERE IF_CODE='DQ404';
INSERT INTO T_IF_CONFIG(IF_CODE, IF_NAME, TO_SYS_NAME, IF_TYPE, SERVICE_TYPE, RECEIVE_TRANSATION_TYPE)
          SELECT 'DQ404', 'http查询XML数据', 'ESB', 1, 'Other', 1 FROM DUAL;
;

--接收配置
--RECEIVE_MESSAGE：固定，表示接收消息，结果反馈到日志
--RECEIVE_HEAD：固定，表示接收表头，当非直接数据时默认会最后接收
DELETE FROM T_IF_RECEIVE_CONFIG WHERE IF_CODE = 'DQ404';
INSERT INTO T_IF_RECEIVE_CONFIG(PK_ID, IF_CODE, TABLE_ID, TABLE_DESC, APPROACH, IS_VALUE_APPROACH, EMPTY_FROM_PARENT, SORT_NO)
          SELECT 41605, 'DQ404', 'RECEIVE_EFFDATE',         '接收生效时间',    'respone',           0,    0, 1 FROM DUAL
UNION ALL SELECT 41606, 'DQ404', 'RECEIVE_PRODUCT',         '接收产品编号',    'respone/productNo', 1,    0, 2 FROM DUAL
;

--查询配置
--APPROACH：最终发送XML的节点，多笔数据时要有，以防数据混乱
--主要是模拟不同参数的查询效果，当然可以使用接口参数配置SQL，拦截中根据判断直接设置SQL处理
DELETE FROM T_IF_QUERY_CONFIG WHERE IF_CODE='DQ404';
INSERT INTO T_IF_QUERY_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, APPROACH, EMPTY_FROM_DEFAULT, ALLOW_EMPTY, MAX_NUMBER, SORT_NO)
          SELECT 41601, 'DQ404', 'Q_HEAD',         '',                   '统计',        '',              0, 1, 1,    1 FROM DUAL
UNION ALL SELECT 41602, 'DQ404', 'EFFDATE_MASTER', '',                   '生效日期查询主表',     'data',    0, 1, 0,    2 FROM DUAL
UNION ALL SELECT 41603, 'DQ404', 'EFFDATE_DETAIL', 'EFFDATE_MASTER',     '生效日期查询从表',     'item',    0, 1, 0,    3 FROM DUAL
UNION ALL SELECT 41604, 'DQ404', 'PRODUCT_MASTER', '',                   '产品编号查询主表',     'data',    0, 1, 0,    4 FROM DUAL
UNION ALL SELECT 41605, 'DQ404', 'PRODUCT_DETAIL', 'PRODUCT_MASTER',     '产品编号查询从表',     'item',    0, 1, 0,    5 FROM DUAL
UNION ALL SELECT 41606, 'DQ404', 'QUERY_MESSAGE',  '',                   '返回信息',           'message', 0, 1, 0,    6 FROM DUAL
;

--
--因为是查询，所以只有手工部分
--参数要与对照关系的本方字段一致
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_Q_MASTER WHERE EFF_DATE BETWEEN :START_DATE AND :END_DATE' WHERE PK_ID=41602;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_Q_MASTER WHERE PRODUCT_NO IN (:PRODUCT_NO)' WHERE PK_ID=41604;

--查询参数PK_ID是主表的主键字段名(PK_ID)
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_Q_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=41603;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_Q_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=41605;

--接口参数
DELETE FROM T_IF_PARAM WHERE IF_CODE = 'DQ404';
INSERT INTO T_IF_PARAM(PK_ID, IF_CODE, TO_SYS_NAME, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, EDIT_ABLE)
          SELECT 41601, 'DQ404', 'ESB',   'STD',       'HANDLER_CLASS',           '接口拦截类',             'com.smartwork.demo.handler.DQ404Handler',  1 FROM DUAL
UNION ALL SELECT 41602, 'DQ404', 'ESB',   'STD',       'GENERATE_EMPTY',          '返回XML生成空节点',       '1',                                 1 FROM DUAL
UNION ALL SELECT 41603, 'DQ404', 'ESB',   'STD',       'QUERY_ELEMENT_ROOT',      '返回XML根节点',          'sw',                                1 FROM DUAL
;

--关系对照
--接收有主从结构时，主表一定要配置从表的查询SQL参数对照并且实在主键
--产品价格、产品数量转换的原因：读取数据库数据的类为java.math.BigDecimal，与返回不兼容
DELETE FROM T_IF_ELEMENT WHERE IF_CODE = 'DQ404';
INSERT INTO T_IF_ELEMENT(PK_ID, IF_CODE, TABLE_ID, ELEMENT_NAME, FIELD_NAME, ELEMENT_DESC, DEFAULT_VALUE, FIELD_TYPE, IS_ACTIVE, NULL_ABLE, MAX_LENGTH, INSERT_ABLE, UPDATE_ABLE, SORT_NO, DATA_CONVERT, DATA_FORMAT) 
--
--输入
          SELECT 41601, 'DQ404', 'RECEIVE_EFFDATE', 'startEffDate',    'START_DATE',      '开始生效时间', '1900-01-01',        1, 1, 1, 0, 1, 1, 30, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
UNION ALL SELECT 41602, 'DQ404', 'RECEIVE_EFFDATE', 'endEffDate',      'END_DATE',        '结束生效时间', '2999-01-01',        1, 1, 1, 0, 1, 1, 40, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
--
UNION ALL SELECT 41603, 'DQ404', 'RECEIVE_PRODUCT', 'productNo',       'PRODUCT_NO',      '产品编号',    '',                  1, 1, 0, 0, 1, 1, 50, '', '' FROM DUAL
--
--
UNION ALL SELECT 41653, 'DQ404', 'EFFDATE_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',                     22, 1, 1,  0, 1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 41654, 'DQ404', 'EFFDATE_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',                      1, 1, 1, 0, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 41655, 'DQ404', 'EFFDATE_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',                      1, 1, 1, 0, 1, 1, 50, '', '' FROM DUAL  
UNION ALL SELECT 41656, 'DQ404', 'EFFDATE_MASTER', 'price',       'PRICE',         '产品价格', '',                      1, 1, 1, 0,  1, 1, 60, '', '' FROM DUAL
UNION ALL SELECT 41657, 'DQ404', 'EFFDATE_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',                      1, 1, 1, 0,  1, 1, 70, '', '' FROM DUAL  
UNION ALL SELECT 41658, 'DQ404', 'EFFDATE_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',                      1, 1, 1, 0,  1, 1, 80, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
--
UNION ALL SELECT 41663, 'DQ404', 'EFFDATE_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',                  1, 1, 1,  0, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 41664, 'DQ404', 'EFFDATE_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',                  1, 1, 1,  0, 1, 1, 40, '', '' FROM DUAL  
UNION ALL SELECT 41665, 'DQ404', 'EFFDATE_DETAIL', 'price',      'PRICE',        '物料价格', '',                  1, 1, 1,  0, 1, 1, 50, '', '' FROM DUAL
UNION ALL SELECT 41666, 'DQ404', 'EFFDATE_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',                  1, 1, 1,  0, 1, 1, 60, '', '' FROM DUAL  
UNION ALL SELECT 41667, 'DQ404', 'EFFDATE_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',                  1, 1, 1,  0, 1, 1, 70, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
--
UNION ALL SELECT 41753, 'DQ404', 'PRODUCT_MASTER', 'pkId',        'PK_ID',        '逻辑主键', '',                      22, 1, 1,  0, 1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 41754, 'DQ404', 'PRODUCT_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',                      1, 1, 1, 0, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 41755, 'DQ404', 'PRODUCT_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',                      1, 1, 1, 0, 1, 1, 50, '', '' FROM DUAL  
UNION ALL SELECT 41756, 'DQ404', 'PRODUCT_MASTER', 'price',       'PRICE',         '产品价格', '',                      1, 1, 1, 0,  1, 1, 60, '', '' FROM DUAL
UNION ALL SELECT 41757, 'DQ404', 'PRODUCT_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',                      1, 1, 1, 0,  1, 1, 70, '', '' FROM DUAL  
UNION ALL SELECT 41758, 'DQ404', 'PRODUCT_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',                      1, 1, 1, 0,  1, 1, 80, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
--
UNION ALL SELECT 41763, 'DQ404', 'PRODUCT_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',                  1, 1, 1,  0, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 41764, 'DQ404', 'PRODUCT_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',                  1, 1, 1,  0, 1, 1, 40, '', '' FROM DUAL  
UNION ALL SELECT 41765, 'DQ404', 'PRODUCT_DETAIL', 'price',      'PRICE',        '物料价格', '',                  1, 1, 1,  0, 1, 1, 50, '', '' FROM DUAL
UNION ALL SELECT 41766, 'DQ404', 'PRODUCT_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',                  1, 1, 1,  0, 1, 1, 60, '', '' FROM DUAL  
UNION ALL SELECT 41767, 'DQ404', 'PRODUCT_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',                  1, 1, 1,  0, 1, 1, 70, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
--
UNION ALL SELECT 41776, 'DQ404', 'Q_HEAD', 'id',              '-',        '返回主键', '',                   1, 1, 1, 0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 41777, 'DQ404', 'Q_HEAD', 'totalNum',        '-',        '返回总数', '',                   1, 1, 1, 0, 1, 1, 20, '', '' FROM DUAL  
--
UNION ALL SELECT 41786, 'DQ404', 'QUERY_MESSAGE', 'status',       'SUCCESS',        '成功状态', '',                   1, 1, 0,  0, 1, 1, 10, 'BOOL2INT', '' FROM DUAL
UNION ALL SELECT 41787, 'DQ404', 'QUERY_MESSAGE', 'error',        'MESSAGE',        '错误信息', '',                   1, 1, 0,  0, 1, 1, 20, '', '' FROM DUAL  
;

COMMIT;





