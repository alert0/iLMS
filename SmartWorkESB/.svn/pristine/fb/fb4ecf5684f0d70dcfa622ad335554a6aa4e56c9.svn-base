
--接口
DELETE FROM T_IF_CONFIG WHERE IF_CODE='DR202';
INSERT INTO T_IF_CONFIG(IF_CODE, IF_NAME, TO_SYS_NAME, IF_TYPE, SERVICE_TYPE, RECEIVE_TRANSATION_TYPE)
          SELECT 'DR202', 'webservice接收XML', 'ESB', 1, 'Other', 1 FROM DUAL;
;

--接收配置
--RECEIVE_MESSAGE：固定，表示接收消息，结果反馈到日志
--RECEIVE_HEAD：固定，表示接收表头，当非直接数据时默认会最后接收
--APPROACH_SUFFIX字段：只针对单笔记录，如果有多笔起来，那么会合并成类似xml
--REVISION_NAME字段：一般用于同个路径下的配置区分，也可以过滤数据，当为空时为其他的意思，根据接收主表配置，只接收productNo字段为productNo1，productNo2，productNo3， 包含PNO
DELETE FROM T_IF_RECEIVE_CONFIG WHERE IF_CODE = 'DR202';
INSERT INTO T_IF_RECEIVE_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, TABLE_NAME, APPROACH, APPROACH_SUFFIX, REVISION_NAME, TABLE_TYPE, KEY_SEQUENCE, KEY_CLASS, CREATE_TYPE, UPDATE_TYPE, EMPTY_FROM_PARENT, SORT_NO)
          SELECT 21203, 'DR202', 'RECEIVE_MASTER',    '',                   '接收主表',     'TMP_R_MASTER',         'respone/data', 'master',  'productNo=productNo1;productNo=/productNo[2|3]/;productNo=/PNO/',     1, 'S_TMP',        '', 1, 1, 0, 3 FROM DUAL
UNION ALL SELECT 21204, 'DR202', 'RECEIVE_DETAIL',    'RECEIVE_MASTER',     '接收从表',     'TMP_R_DETAIL',         'Item',         '',        '',                                                    1, 'S_TMP',        '', 1, 1, 0, 4 FROM DUAL
UNION ALL SELECT 21205, 'DR202', 'RECEIVE_HEAD',      '',                   '接收表头',     'TMP_R_BATCH',          '_',            '',        '',                                                    4, 'S_TMP',        '', 1, 1, 0, 5 FROM DUAL
;

--查询配置
--APPROACH：最终发送XML的节点，多笔数据时要有，以防数据混乱
--QUERY_MESSAGE：固定配置代码，代表转换接收结果
DELETE FROM T_IF_QUERY_CONFIG WHERE IF_CODE='DR202';
INSERT INTO T_IF_QUERY_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, APPROACH, EMPTY_FROM_DEFAULT, ALLOW_EMPTY, MAX_NUMBER, SORT_NO)
          SELECT 21201, 'DR202', 'Q_HEAD',          '',             '统计',        '',           0, 1, 1,    1 FROM DUAL
UNION ALL SELECT 21202, 'DR202', 'QUERY_MESSAGE',   '',             '返回信息',     'message',    0, 1, 1,    2 FROM DUAL
;

--接口参数
DELETE FROM T_IF_PARAM WHERE IF_CODE = 'DR202';
INSERT INTO T_IF_PARAM(PK_ID, IF_CODE, TO_SYS_NAME, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, EDIT_ABLE)
          SELECT 21201, 'DR202', 'ESB',   'STD',       'HANDLER_CLASS',           '接口拦截类',             'com.smartwork.demo.handler.DR202Handler',  1 FROM DUAL
UNION ALL SELECT 21202, 'DR202', 'ESB',   'STD',       'BACK_SUCCESS_RECEIVE',    '接收主表成功也返回',       '1',                                 1 FROM DUAL
UNION ALL SELECT 21204, 'DR202', 'ESB',   'STD',       'GENERATE_EMPTY',          '返回XML生成空节点',       '1',                                 1 FROM DUAL
UNION ALL SELECT 21205, 'DR202', 'ESB',   'STD',       'QUERY_ELEMENT_ROOT',      '返回XML根节点',          'sw',                                1 FROM DUAL
;

--关系对照
--标识：来自发送表头THIRD_FILE_KEY
--接口代码：来自表头IF_CODE
--接收从表的productNo时从主表继承的，用于从表数据接收失败返回使用
--因为QUERY_MESSAGE直接时返回第三方结果，所以不需要本方字段
DELETE FROM T_IF_ELEMENT WHERE IF_CODE = 'DR202';
INSERT INTO T_IF_ELEMENT(PK_ID, IF_CODE, TABLE_ID, ELEMENT_NAME, FIELD_NAME, ELEMENT_DESC, DEFAULT_VALUE, FIELD_TYPE, IS_ACTIVE, NULL_ABLE, MAX_LENGTH, INSERT_ABLE, UPDATE_ABLE, SORT_NO, DATA_CONVERT, DATA_FORMAT) 
--
--输入
--
          SELECT 21251, 'DR202', 'RECEIVE_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',                     22, 1, 0, 0,  1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 21252, 'DR202', 'RECEIVE_MASTER', 'batchNo',     'BATCH_NO',      '生成批次', '$RH_THIRD_FILE_KEY',   21, 1, 1, 32, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 21253, 'DR202', 'RECEIVE_MASTER', 'ifCode',      'IF_CODE',       '接口代码', '$RH_IF_CODE',          23, 1, 0, 20, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 21254, 'DR202', 'RECEIVE_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',                      3, 1, 0, 30, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 21255, 'DR202', 'RECEIVE_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',                      1, 1, 0, 64, 1, 1, 50, '', '' FROM DUAL  
UNION ALL SELECT 21256, 'DR202', 'RECEIVE_MASTER', 'price',       'PRICE',         '产品价格', '',                      1, 1, 1, 4,  1, 1, 60, 'STRING2DOUBLE', '' FROM DUAL
UNION ALL SELECT 21257, 'DR202', 'RECEIVE_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',                      1, 1, 1, 6,  1, 1, 70, 'STRING2INT', '' FROM DUAL  
UNION ALL SELECT 21258, 'DR202', 'RECEIVE_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',                      1, 1, 1, 0,  1, 1, 80, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
UNION ALL SELECT 21259, 'DR202', 'RECEIVE_MASTER', 'createTime',  'CREATE_TIME',   '创建时间', '$RH_CREATE_TIME',      21, 1, 1, 0,  1, 0, 90, '', '' FROM DUAL
UNION ALL SELECT 21260, 'DR202', 'RECEIVE_MASTER', 'updateTime',  'UPDATE_TIME',   '修改时间', '$RH_CREATE_TIME',      21, 1, 1, 0,  1, 1, 100,'', '' FROM DUAL
--
UNION ALL SELECT 21261, 'DR202', 'RECEIVE_DETAIL', 'pkId',       'PK_ID',        '逻辑主键', '',                 22, 1, 1,  0, 1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 21262, 'DR202', 'RECEIVE_DETAIL', 'fkId',       'FK_ID',        '产品主键', '',                 25, 1, 0, 19, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 21263, 'DR202', 'RECEIVE_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',                  3, 1, 0, 64, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 21264, 'DR202', 'RECEIVE_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',                  1, 1, 0, 64, 1, 1, 40, '', '' FROM DUAL  
UNION ALL SELECT 21265, 'DR202', 'RECEIVE_DETAIL', 'price',      'PRICE',        '物料价格', '',                  1, 1, 1,  4, 1, 1, 50, 'STRING2DOUBLE', '' FROM DUAL
UNION ALL SELECT 21266, 'DR202', 'RECEIVE_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',                  1, 1, 1,  6, 1, 1, 60, 'STRING2INT', '' FROM DUAL  
UNION ALL SELECT 21267, 'DR202', 'RECEIVE_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',                  1, 1, 1,  0, 1, 1, 70, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
UNION ALL SELECT 21268, 'DR202', 'RECEIVE_DETAIL', 'createTime', 'CREATE_TIME',  '创建时间', '$RH_CREATE_TIME',  21, 1, 1,  0, 1, 0, 80, '', '' FROM DUAL
UNION ALL SELECT 21269, 'DR202', 'RECEIVE_DETAIL', 'updateTime', 'UPDATE_TIME',  '修改时间', '$RH_CREATE_TIME',  21, 1, 1,  0, 1, 1, 90,'', '' FROM DUAL
UNION ALL SELECT 21270, 'DR202', 'RECEIVE_DETAIL', 'productNo',  'PRODUCT_NO',   '产品编号', '',                  9, 1, 1,  0, 0, 0, 100, '', '' FROM DUAL
--
UNION ALL SELECT 21281, 'DR202', 'RECEIVE_HEAD', 'pkId',         'PK_ID',           '逻辑主键', '',                   22, 1, 0,  0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 21282, 'DR202', 'RECEIVE_HEAD', 'batchNo',      'BATCH_NO',        '生成批次', '$RH_THIRD_FILE_KEY', 21, 1, 0, 32, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 21283, 'DR202', 'RECEIVE_HEAD', 'ifCode',       'IF_CODE',         '接口代码', '$RH_IF_CODE',        21, 1, 0, 20, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 21284, 'DR202', 'RECEIVE_HEAD', 'createTime',   'CREATE_TIME',     '创建时间', '$RH_CREATE_TIME',    21, 1, 0,  0, 1, 1, 40, '', '' FROM DUAL  
--
UNION ALL SELECT 21301, 'DR202', 'Q_HEAD', 'id',              '-',        '返回主键', '',                   1, 1, 0, 0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 21302, 'DR202', 'Q_HEAD', 'totalNum',        '-',        '返回总数', '',                   1, 1, 0, 0, 1, 1, 20, '', '' FROM DUAL  
--
UNION ALL SELECT 21321, 'DR202', 'QUERY_MESSAGE', 'status',       'SUCCESS',        '成功状态', '',                   1, 1, 0,  0, 1, 1, 10, 'BOOL2INT', '' FROM DUAL
UNION ALL SELECT 21322, 'DR202', 'QUERY_MESSAGE', 'error',        'MESSAGE',        '错误信息', '',                   1, 1, 0,  0, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 21323, 'DR202', 'QUERY_MESSAGE', 'productNo',    '-',              '产品编号', '',                   1, 1, 0,  0, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 21324, 'DR202', 'QUERY_MESSAGE', 'itemNo',       '-',              '物料编号', '',                   1, 1, 0,  0, 1, 1, 40, '', '' FROM DUAL  
;

COMMIT;





