
--接口
DELETE FROM T_IF_CONFIG WHERE IF_CODE='DQ402';
INSERT INTO T_IF_CONFIG(IF_CODE, IF_NAME, TO_SYS_NAME, IF_TYPE, SERVICE_TYPE, RECEIVE_TRANSATION_TYPE)
          SELECT 'DQ402', 'webservice查询XML数据', 'ESB', 1, 'Other', 1 FROM DUAL;
;

--接收配置
--RECEIVE_MESSAGE：固定，表示接收消息，结果反馈到日志
--RECEIVE_HEAD：固定，表示接收表头，当非直接数据时默认会最后接收
--RECEIVE_PRODUCT：根据文件为直接数据路径
DELETE FROM T_IF_RECEIVE_CONFIG WHERE IF_CODE = 'DQ402';
INSERT INTO T_IF_RECEIVE_CONFIG(PK_ID, IF_CODE, TABLE_ID, TABLE_DESC, APPROACH, IS_VALUE_APPROACH, EMPTY_FROM_PARENT, SORT_NO)
          SELECT 41205, 'DQ402', 'RECEIVE_EFFDATE',         '接收生效时间',    'respone',           0,    0, 1 FROM DUAL
UNION ALL SELECT 41206, 'DQ402', 'RECEIVE_PRODUCT',         '接收产品编号',    'respone/productNo', 1,    0, 2 FROM DUAL
;

--查询配置
--APPROACH：最终发送XML的节点，多笔数据时要有，以防数据混乱
--主要是模拟不同参数的查询效果，当然可以使用接口参数配置SQL，拦截中根据判断直接设置SQL处理
DELETE FROM T_IF_QUERY_CONFIG WHERE IF_CODE='DQ402';
INSERT INTO T_IF_QUERY_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, APPROACH, EMPTY_FROM_DEFAULT, ALLOW_EMPTY, MAX_NUMBER, SORT_NO)
          SELECT 41201, 'DQ402', 'Q_HEAD',         '',                   '统计',        '',              0, 1, 1,    1 FROM DUAL
UNION ALL SELECT 41202, 'DQ402', 'EFFDATE_MASTER', '',                   '生效日期查询主表',     'data',    0, 1, 0,    2 FROM DUAL
UNION ALL SELECT 41203, 'DQ402', 'EFFDATE_DETAIL', 'EFFDATE_MASTER',     '生效日期查询从表',     'item',    0, 1, 0,    3 FROM DUAL
UNION ALL SELECT 41204, 'DQ402', 'PRODUCT_MASTER', '',                   '产品编号查询主表',     'data',    0, 1, 0,    4 FROM DUAL
UNION ALL SELECT 41205, 'DQ402', 'PRODUCT_DETAIL', 'PRODUCT_MASTER',     '产品编号查询从表',     'item',    0, 1, 0,    5 FROM DUAL
UNION ALL SELECT 41206, 'DQ402', 'QUERY_MESSAGE',  '',                   '返回信息',           'message', 0, 1, 0,    6 FROM DUAL
;

--
--因为是查询，所以只有手工部分
--参数要与对照关系的本方字段一致
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_Q_MASTER WHERE EFF_DATE BETWEEN :START_DATE AND :END_DATE' WHERE PK_ID=41202;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_Q_MASTER WHERE PRODUCT_NO IN (:PRODUCT_NO)' WHERE PK_ID=41204;

--查询参数PK_ID是主表的主键字段名(PK_ID)
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_Q_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=41203;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_Q_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=41205;

--接口参数
DELETE FROM T_IF_PARAM WHERE IF_CODE = 'DQ402';
INSERT INTO T_IF_PARAM(PK_ID, IF_CODE, TO_SYS_NAME, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, EDIT_ABLE)
          SELECT 41201, 'DQ402', 'ESB',   'STD',       'HANDLER_CLASS',           '接口拦截类',             'com.smartwork.demo.handler.DQ402Handler',  1 FROM DUAL
UNION ALL SELECT 41202, 'DQ402', 'ESB',   'STD',       'GENERATE_EMPTY',          '返回XML生成空节点',       '1',                                 1 FROM DUAL
UNION ALL SELECT 41203, 'DQ402', 'ESB',   'STD',       'QUERY_ELEMENT_ROOT',      '返回XML根节点',          'sw',                                1 FROM DUAL
;

--关系对照
--接收有主从结构时，主表一定要配置从表的查询SQL参数对照并且实在主键
--产品价格、产品数量转换的原因：读取数据库数据的类为java.math.BigDecimal，与返回不兼容
DELETE FROM T_IF_ELEMENT WHERE IF_CODE = 'DQ402';
INSERT INTO T_IF_ELEMENT(PK_ID, IF_CODE, TABLE_ID, ELEMENT_NAME, FIELD_NAME, ELEMENT_DESC, DEFAULT_VALUE, FIELD_TYPE, IS_ACTIVE, NULL_ABLE, MAX_LENGTH, INSERT_ABLE, UPDATE_ABLE, SORT_NO, DATA_CONVERT, DATA_FORMAT) 
--
--输入
          SELECT 41201, 'DQ402', 'RECEIVE_EFFDATE', 'startEffDate',    'START_DATE',      '开始生效时间', '1900-01-01',        1, 1, 1, 0, 1, 1, 30, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
UNION ALL SELECT 41202, 'DQ402', 'RECEIVE_EFFDATE', 'endEffDate',      'END_DATE',        '结束生效时间', '2999-01-01',        1, 1, 1, 0, 1, 1, 40, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
--
UNION ALL SELECT 41203, 'DQ402', 'RECEIVE_PRODUCT', 'productNo',       'PRODUCT_NO',      '产品编号',    '',                  1, 1, 0, 0, 1, 1, 50, '', '' FROM DUAL
--
--
UNION ALL SELECT 41253, 'DQ402', 'EFFDATE_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',                     22, 1, 1,  0, 1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 41254, 'DQ402', 'EFFDATE_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',                      1, 1, 1, 0, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 41255, 'DQ402', 'EFFDATE_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',                      1, 1, 1, 0, 1, 1, 50, '', '' FROM DUAL  
UNION ALL SELECT 41256, 'DQ402', 'EFFDATE_MASTER', 'price',       'PRICE',         '产品价格', '',                      1, 1, 1, 0,  1, 1, 60, '', '' FROM DUAL
UNION ALL SELECT 41257, 'DQ402', 'EFFDATE_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',                      1, 1, 1, 0,  1, 1, 70, '', '' FROM DUAL  
UNION ALL SELECT 41258, 'DQ402', 'EFFDATE_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',                      1, 1, 1, 0,  1, 1, 80, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
--
UNION ALL SELECT 41263, 'DQ402', 'EFFDATE_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',                  1, 1, 1,  0, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 41264, 'DQ402', 'EFFDATE_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',                  1, 1, 1,  0, 1, 1, 40, '', '' FROM DUAL  
UNION ALL SELECT 41265, 'DQ402', 'EFFDATE_DETAIL', 'price',      'PRICE',        '物料价格', '',                  1, 1, 1,  0, 1, 1, 50, '', '' FROM DUAL
UNION ALL SELECT 41266, 'DQ402', 'EFFDATE_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',                  1, 1, 1,  0, 1, 1, 60, '', '' FROM DUAL  
UNION ALL SELECT 41267, 'DQ402', 'EFFDATE_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',                  1, 1, 1,  0, 1, 1, 70, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
--
UNION ALL SELECT 41353, 'DQ402', 'PRODUCT_MASTER', 'pkId',        'PK_ID',        '逻辑主键', '',                      22, 1, 1,  0, 1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 41354, 'DQ402', 'PRODUCT_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',                      1, 1, 1, 0, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 41355, 'DQ402', 'PRODUCT_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',                      1, 1, 1, 0, 1, 1, 50, '', '' FROM DUAL  
UNION ALL SELECT 41356, 'DQ402', 'PRODUCT_MASTER', 'price',       'PRICE',         '产品价格', '',                      1, 1, 1, 0,  1, 1, 60, '', '' FROM DUAL
UNION ALL SELECT 41357, 'DQ402', 'PRODUCT_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',                      1, 1, 1, 0,  1, 1, 70, '', '' FROM DUAL  
UNION ALL SELECT 41358, 'DQ402', 'PRODUCT_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',                      1, 1, 1, 0,  1, 1, 80, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
--
UNION ALL SELECT 41363, 'DQ402', 'PRODUCT_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',                  1, 1, 1,  0, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 41364, 'DQ402', 'PRODUCT_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',                  1, 1, 1,  0, 1, 1, 40, '', '' FROM DUAL  
UNION ALL SELECT 41365, 'DQ402', 'PRODUCT_DETAIL', 'price',      'PRICE',        '物料价格', '',                  1, 1, 1,  0, 1, 1, 50, '', '' FROM DUAL
UNION ALL SELECT 41366, 'DQ402', 'PRODUCT_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',                  1, 1, 1,  0, 1, 1, 60, '', '' FROM DUAL  
UNION ALL SELECT 41367, 'DQ402', 'PRODUCT_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',                  1, 1, 1,  0, 1, 1, 70, 'DATE2STRING', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
--
UNION ALL SELECT 41376, 'DQ402', 'Q_HEAD', 'id',              '-',        '返回主键', '',                   1, 1, 1, 0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 41377, 'DQ402', 'Q_HEAD', 'totalNum',        '-',        '返回总数', '',                   1, 1, 1, 0, 1, 1, 20, '', '' FROM DUAL  
--
UNION ALL SELECT 41386, 'DQ402', 'QUERY_MESSAGE', 'status',       'SUCCESS',        '成功状态', '',                   1, 1, 0,  0, 1, 1, 10, 'BOOL2INT', '' FROM DUAL
UNION ALL SELECT 41387, 'DQ402', 'QUERY_MESSAGE', 'error',        'MESSAGE',        '错误信息', '',                   1, 1, 0,  0, 1, 1, 20, '', '' FROM DUAL  
;

COMMIT;





