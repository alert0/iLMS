
--接口
DELETE FROM T_IF_CONFIG WHERE IF_CODE='DR203';
INSERT INTO T_IF_CONFIG(IF_CODE, IF_NAME, TO_SYS_NAME, IF_TYPE, SERVICE_TYPE, RECEIVE_TRANSATION_TYPE)
          SELECT 'DR203', 'webservice接收Json', 'ESB', 1, 'Other', 1 FROM DUAL;
;

--接收配置
--RECEIVE_MESSAGE：固定，表示接收消息，结果反馈到日志
--RECEIVE_HEAD：固定，表示接收表头，当非直接数据时默认会最后接收
DELETE FROM T_IF_RECEIVE_CONFIG WHERE IF_CODE = 'DR203';
INSERT INTO T_IF_RECEIVE_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, TABLE_NAME, APPROACH, TABLE_TYPE, KEY_SEQUENCE, KEY_CLASS, CREATE_TYPE, UPDATE_TYPE, EMPTY_FROM_PARENT, SORT_NO)
          SELECT 21403, 'DR203', 'RECEIVE_MASTER',    '',                   '接收主表',     'TMP_R_MASTER',         '/data',             1, 'S_TMP',        '', 1, 1, 0, 3 FROM DUAL
UNION ALL SELECT 21404, 'DR203', 'RECEIVE_DETAIL',    'RECEIVE_MASTER',     '接收从表',     'TMP_R_DETAIL',         'Item',              1, 'S_TMP',        '', 1, 1, 0, 4 FROM DUAL
UNION ALL SELECT 21405, 'DR203', 'RECEIVE_HEAD',      '',                   '接收表头',     'TMP_R_BATCH',          '_',                 4, 'S_TMP',        '', 1, 1, 0, 5 FROM DUAL
;

--查询配置
--APPROACH：最终发送json的节点，多笔数据时要有，以防数据混乱
--QUERY_MESSAGE：固定配置代码，代表转换接收结果
DELETE FROM T_IF_QUERY_CONFIG WHERE IF_CODE='DR203';
INSERT INTO T_IF_QUERY_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, APPROACH, EMPTY_FROM_DEFAULT, ALLOW_EMPTY, MAX_NUMBER, SORT_NO)
          SELECT 21401, 'DR203', 'Q_HEAD',          '',             '统计',        '',           0, 1, 1,    1 FROM DUAL
UNION ALL SELECT 21402, 'DR203', 'QUERY_MESSAGE',   '',             '返回信息',     'message',    0, 1, 1,    2 FROM DUAL
;

--接口参数
DELETE FROM T_IF_PARAM WHERE IF_CODE = 'DR203';
INSERT INTO T_IF_PARAM(PK_ID, IF_CODE, TO_SYS_NAME, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, EDIT_ABLE)
          SELECT 21401, 'DR203', 'ESB',   'STD',       'HANDLER_CLASS',           '接口拦截类',             'com.smartwork.demo.handler.DR203Handler',  1 FROM DUAL
UNION ALL SELECT 21402, 'DR203', 'ESB',   'STD',       'BACK_SUCCESS_RECEIVE',    '接收主表成功也返回',       '1',                                 1 FROM DUAL
UNION ALL SELECT 21404, 'DR203', 'ESB',   'STD',       'GENERATE_EMPTY',          '返回Json生成空节点',      '1',                                 1 FROM DUAL
UNION ALL SELECT 21405, 'DR203', 'ESB',   'STD',       'RECEIVE_FILE_TYPE',  '接收文件类型',          'json',                              1 FROM DUAL
UNION ALL SELECT 21406, 'DR203', 'ESB',   'STD',       'QUERY_FILE_TYPE',    '发送文件类型',          'json',                              1 FROM DUAL
;

--关系对照
--标识：来自发送表头THIRD_FILE_KEY
--接口代码：来自表头IF_CODE
--接收从表的productNo时从主表继承的，用于从表数据接收失败返回使用
--因为QUERY_MESSAGE直接时返回第三方结果，所以不需要本方字段
DELETE FROM T_IF_ELEMENT WHERE IF_CODE = 'DR203';
INSERT INTO T_IF_ELEMENT(PK_ID, IF_CODE, TABLE_ID, ELEMENT_NAME, FIELD_NAME, ELEMENT_DESC, DEFAULT_VALUE, FIELD_TYPE, IS_ACTIVE, NULL_ABLE, MAX_LENGTH, INSERT_ABLE, UPDATE_ABLE, SORT_NO, DATA_CONVERT, DATA_FORMAT) 
--
--输入
--
          SELECT 21451, 'DR203', 'RECEIVE_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',                     22, 1, 0, 0,  1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 21452, 'DR203', 'RECEIVE_MASTER', 'batchNo',     'BATCH_NO',      '生成批次', '$RH_THIRD_FILE_KEY',   21, 1, 1, 32, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 21453, 'DR203', 'RECEIVE_MASTER', 'ifCode',      'IF_CODE',       '接口代码', '$RH_IF_CODE',          23, 1, 0, 20, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 21454, 'DR203', 'RECEIVE_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',                      3, 1, 0, 30, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 21455, 'DR203', 'RECEIVE_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',                      1, 1, 0, 64, 1, 1, 50, '', '' FROM DUAL  
UNION ALL SELECT 21456, 'DR203', 'RECEIVE_MASTER', 'price',       'PRICE',         '产品价格', '',                      1, 1, 1, 4,  1, 1, 60, 'STRING2DOUBLE', '' FROM DUAL
UNION ALL SELECT 21457, 'DR203', 'RECEIVE_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',                      1, 1, 1, 6,  1, 1, 70, 'STRING2INT', '' FROM DUAL  
UNION ALL SELECT 21458, 'DR203', 'RECEIVE_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',                      1, 1, 1, 0,  1, 1, 80, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL
UNION ALL SELECT 21459, 'DR203', 'RECEIVE_MASTER', 'createTime',  'CREATE_TIME',   '创建时间', '$RH_CREATE_TIME',      21, 1, 1, 0,  1, 0, 90, '', '' FROM DUAL
UNION ALL SELECT 21460, 'DR203', 'RECEIVE_MASTER', 'updateTime',  'UPDATE_TIME',   '修改时间', '$RH_CREATE_TIME',      21, 1, 1, 0,  1, 1, 100,'', '' FROM DUAL
--
UNION ALL SELECT 21461, 'DR203', 'RECEIVE_DETAIL', 'pkId',       'PK_ID',        '逻辑主键', '',                 22, 1, 1,  0, 1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 21462, 'DR203', 'RECEIVE_DETAIL', 'fkId',       'FK_ID',        '产品主键', '',                 25, 1, 0, 19, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 21463, 'DR203', 'RECEIVE_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',                  3, 1, 0, 64, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 21464, 'DR203', 'RECEIVE_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',                  1, 1, 0, 64, 1, 1, 40, '', '' FROM DUAL  
UNION ALL SELECT 21465, 'DR203', 'RECEIVE_DETAIL', 'price',      'PRICE',        '物料价格', '',                  1, 1, 1,  4, 1, 1, 50, 'STRING2DOUBLE', '' FROM DUAL
UNION ALL SELECT 21466, 'DR203', 'RECEIVE_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',                  1, 1, 1,  6, 1, 1, 60, 'STRING2INT', '' FROM DUAL  
UNION ALL SELECT 21467, 'DR203', 'RECEIVE_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',                  1, 1, 1,  0, 1, 1, 70, 'STRING2DATE', 'yyyy-MM-dd HH:mm:ss' FROM DUAL  
UNION ALL SELECT 21468, 'DR203', 'RECEIVE_DETAIL', 'createTime', 'CREATE_TIME',  '创建时间', '$RH_CREATE_TIME',  21, 1, 1,  0, 1, 0, 80, '', '' FROM DUAL
UNION ALL SELECT 21469, 'DR203', 'RECEIVE_DETAIL', 'updateTime', 'UPDATE_TIME',  '修改时间', '$RH_CREATE_TIME',  21, 1, 1,  0, 1, 1, 90,'', '' FROM DUAL
UNION ALL SELECT 21470, 'DR203', 'RECEIVE_DETAIL', 'productNo',  'PRODUCT_NO',   '产品编号', '',                  9, 1, 1,  0, 0, 0, 100, '', '' FROM DUAL
--
UNION ALL SELECT 21481, 'DR203', 'RECEIVE_HEAD', 'pkId',         'PK_ID',           '逻辑主键', '',                   22, 1, 0,  0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 21482, 'DR203', 'RECEIVE_HEAD', 'batchNo',      'BATCH_NO',        '生成批次', '$RH_THIRD_FILE_KEY', 21, 1, 0, 32, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 21483, 'DR203', 'RECEIVE_HEAD', 'ifCode',       'IF_CODE',         '接口代码', '$RH_IF_CODE',        21, 1, 0, 20, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 21484, 'DR203', 'RECEIVE_HEAD', 'createTime',   'CREATE_TIME',     '创建时间', '$RH_CREATE_TIME',    21, 1, 0,  0, 1, 1, 40, '', '' FROM DUAL  
--
UNION ALL SELECT 21501, 'DR203', 'Q_HEAD', 'id',              '-',        '返回主键', '',                   1, 1, 0, 0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 21502, 'DR203', 'Q_HEAD', 'totalNum',        '-',        '返回总数', '',                   1, 1, 0, 0, 1, 1, 20, '', '' FROM DUAL  
--
UNION ALL SELECT 21521, 'DR203', 'QUERY_MESSAGE', 'status',       'SUCCESS',        '成功状态', '',                   1, 1, 0,  0, 1, 1, 10, 'BOOL2INT', '' FROM DUAL
UNION ALL SELECT 21522, 'DR203', 'QUERY_MESSAGE', 'error',        'MESSAGE',        '错误信息', '',                   1, 1, 0,  0, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 21523, 'DR203', 'QUERY_MESSAGE', 'productNo',    '-',              '产品编号', '',                   1, 1, 0,  0, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 21524, 'DR203', 'QUERY_MESSAGE', 'itemNo',       '-',              '物料编号', '',                   1, 1, 0,  0, 1, 1, 40, '', '' FROM DUAL  
;

COMMIT;





