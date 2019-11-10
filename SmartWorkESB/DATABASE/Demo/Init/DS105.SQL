
--任务计划
--无状态发送的时长一般查询整张表，所以时间间隔很长
DELETE FROM T_IF_TIMER WHERE IF_CODE='DS105';
INSERT INTO T_IF_TIMER(JOB_CODE, JOB_DESC, IF_CODE, MASTER_QUERY_TABLE_ID, JOB_GROUP, JOB_CLASS, TRIGGER_NAME, TRIGGER_VALUE, UPDATE_STATE, RUN_STATE)
          SELECT 'DS105', '无状态发送模拟直接数据', 'DS105', '', 'DS105', 'com.smartwork.esb.job.ClientSenderJob', 'DS105', '0 0 0/2 * * ?', 1, 1 FROM DUAL
;

--接口
DELETE FROM T_IF_CONFIG WHERE IF_CODE='DS105';
INSERT INTO T_IF_CONFIG(IF_CODE, IF_NAME, TO_SYS_NAME, IF_TYPE, SERVICE_TYPE, SERVICE_CLASS, RECEIVE_TRANSATION_TYPE, SEND_SUCCESS_FLAG)
          SELECT 'DS105', '无状态发送模拟直接数据', 'ESB', 2, 'Other', 'com.smartwork.demo.service.DS105SenderService', 1, 'S' FROM DUAL;
;

--发送配置
--APPROACH：最终发送XML的节点，多笔数据时要有，以防数据混乱
--因为模拟，所以发送主表一次发送行数设置很小
DELETE FROM T_IF_QUERY_CONFIG WHERE IF_CODE='DS105';
INSERT INTO T_IF_QUERY_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, APPROACH, EMPTY_FROM_DEFAULT, ALLOW_EMPTY, MAX_NUMBER, SORT_NO)
          SELECT 11801, 'DS105', 'S_HEAD',   '',             '发送表头',     'head',    1, 0, 1,    1 FROM DUAL
UNION ALL SELECT 11802, 'DS105', 'S_MASTER', '',             '发送主表',     'data',    0, 0, 2,  2 FROM DUAL
UNION ALL SELECT 11803, 'DS105', 'S_DETAIL', 'S_MASTER',     '发送从表',     'item',    0, 1, 0,    3 FROM DUAL
;

--
--有状态发送数据
--无状态针对自动发送功能，自动发送SQL没有根据反写状态过滤
--手工发送部分使用主键过滤
UPDATE T_IF_QUERY_CONFIG SET AUTO_QUERY_SQL = 'SELECT * FROM TMP_S_MASTER' WHERE PK_ID=11802;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_S_MASTER WHERE PK_ID IN (:PK_ID)' WHERE PK_ID=11802;
UPDATE T_IF_QUERY_CONFIG SET SUCCESS_SQL = 'UPDATE TMP_S_MASTER SET IS_SEND=1 WHERE PK_ID=:PK_ID' WHERE PK_ID=11802;
UPDATE T_IF_QUERY_CONFIG SET FAILURE_SQL = 'UPDATE TMP_S_MASTER SET FAILURE=FAILURE+1 WHERE PK_ID=:PK_ID' WHERE PK_ID=11802;

--查询参数PK_ID是主表的主键字段名(PK_ID)
UPDATE T_IF_QUERY_CONFIG SET AUTO_QUERY_SQL = 'SELECT * FROM TMP_S_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=11803;
UPDATE T_IF_QUERY_CONFIG SET HAND_QUERY_SQL = 'SELECT * FROM TMP_S_DETAIL WHERE FK_ID=:PK_ID' WHERE PK_ID=11803;

--接收配置
--RECEIVE_MESSAGE：固定，表示接收消息，结果反馈到日志
--RECEIVE_HEAD：固定，表示接收表头，当非直接数据时默认会最后接收
DELETE FROM T_IF_RECEIVE_CONFIG WHERE IF_CODE = 'DS105';
INSERT INTO T_IF_RECEIVE_CONFIG(PK_ID, IF_CODE, TABLE_ID, PARENT_TABLE_ID, TABLE_DESC, TABLE_NAME, APPROACH, TABLE_TYPE, KEY_SEQUENCE, KEY_CLASS, CREATE_TYPE, UPDATE_TYPE, EMPTY_FROM_PARENT, SORT_NO)
          SELECT 11801, 'DS105', 'RECEIVE_MESSAGE',   '',                   '接收消息',     '',                     '',      4, '',             '', 0, 0, 0, 1 FROM DUAL
UNION ALL SELECT 11802, 'DS105', 'RECEIVE_THIRD',     'RECEIVE_MESSAGE',    '返回对方信息',  'TMP_R_THIRD',          '',      4, 'S_TMP',        '', 1, 1, 0, 2 FROM DUAL
UNION ALL SELECT 11803, 'DS105', 'RECEIVE_MASTER',    '',                   '返回主表',     'TMP_R_MASTER',         '',      1, 'S_TMP',        '', 1, 1, 0, 3 FROM DUAL
UNION ALL SELECT 11804, 'DS105', 'RECEIVE_DETAIL',    'RECEIVE_MASTER',     '返回从表',     'TMP_R_DETAIL',         '',      1, 'S_TMP',        '', 1, 1, 0, 4 FROM DUAL
UNION ALL SELECT 11805, 'DS105', 'RECEIVE_HEAD',      '',                   '接收表头',     'TMP_R_BATCH',          '',      4, 'S_TMP',        '', 1, 1, 0, 5 FROM DUAL
;

--接口参数
--无状态发送必须设置顶级发送主表的查询开始行，否则每次都是从第0行开始
--有发送时连续发送：达到最快速度发送整张表
--连续发送间隔毫秒：留给双方缓冲时间，以防内存释放问题导致崩溃
DELETE FROM T_IF_PARAM WHERE IF_CODE = 'DS105';
INSERT INTO T_IF_PARAM(PK_ID, IF_CODE, TO_SYS_NAME, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, EDIT_ABLE)
          SELECT 11801, 'DS105', 'ESB',   'Other',     'IS_FIRST',              '是否首次发送',          '1',                                 1 FROM DUAL
UNION ALL SELECT 11803, 'DS105', 'ESB',   'STD',       'HANDLER_CLASS',         '接口拦截类',            'com.smartwork.demo.handler.DS105Handler',  1 FROM DUAL
UNION ALL SELECT 11804, 'DS105', 'ESB',   'STD',       'S_MASTER_START_ROW',    '发送主表查询开始行',      '0',                                 1 FROM DUAL
UNION ALL SELECT 11805, 'DS105', 'ESB',   'STD',       'CONTINUOUS_SEND',       '有发送时连续发送',       '1',                                  1 FROM DUAL
UNION ALL SELECT 11806, 'DS105', 'ESB',   'STD',       'CONTINUOUS_MSTIME',     '连续发送间隔毫秒',       '2000',                               1 FROM DUAL
;

--关系对照
--工厂参数的来源：通用参数FACTORY
--是否首次发送：来自接口参数IS_FIRST
--标识：来自发送表头THIRD_FILE_KEY
--接口代码：来自表头IF_CODE
--totalNum：因数据来自生成XML前一步，所以允许为空，否则验证不通过
--目前返回发送结果和发送查询数据集合在一块，实际当中一般要么是返回发送结果，要么是返回发送查询数据
DELETE FROM T_IF_ELEMENT WHERE IF_CODE = 'DS105';
INSERT INTO T_IF_ELEMENT(PK_ID, IF_CODE, TABLE_ID, ELEMENT_NAME, FIELD_NAME, ELEMENT_DESC, DEFAULT_VALUE, FIELD_TYPE, IS_ACTIVE, NULL_ABLE, MAX_LENGTH, INSERT_ABLE, UPDATE_ABLE, SORT_NO, DATA_CONVERT, DATA_FORMAT) 
--
--输入
          SELECT 11801, 'DS105', 'S_HEAD', 'plant',    'FACTORY',    '工厂',       '$QP_FACTORY',          1, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL
UNION ALL SELECT 11802, 'DS105', 'S_HEAD', 'first',    'IS_FIRST',   '是否首次发送',  '$QP_IS_FIRST',         1, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 11803, 'DS105', 'S_HEAD', 'token',    'TOKEN',      '标识',       '$QH_THIRD_FILE_KEY',    1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL
UNION ALL SELECT 11804, 'DS105', 'S_HEAD', 'ifCode',   'IF_CODE',    '接口代码',     '$QH_IF_CODE',          1, 1, 0, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 11805, 'DS105', 'S_HEAD', 'totalNum', 'TOTAL_NUM',  '数据总数',     '',                     1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL
--
UNION ALL SELECT 11810, 'DS105', 'S_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',         22, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL  
UNION ALL SELECT 11811, 'DS105', 'S_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',          3, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 11812, 'DS105', 'S_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',          1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL  
UNION ALL SELECT 11813, 'DS105', 'S_MASTER', 'price',       'PRICE',         '产品价格', '',          1, 1, 1, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 11814, 'DS105', 'S_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',          1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL  
UNION ALL SELECT 11815, 'DS105', 'S_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',          1, 1, 1, 0, 0, 0, 60, '', '' FROM DUAL
--
UNION ALL SELECT 11821, 'DS105', 'S_DETAIL', 'productNo',  'PRODUCT_NO',   '产品编号', '',           7, 1, 0, 0, 0, 0, 10, '', '' FROM DUAL  
UNION ALL SELECT 11822, 'DS105', 'S_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',           1, 1, 0, 0, 0, 0, 20, '', '' FROM DUAL
UNION ALL SELECT 11823, 'DS105', 'S_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',           1, 1, 0, 0, 0, 0, 30, '', '' FROM DUAL  
UNION ALL SELECT 11824, 'DS105', 'S_DETAIL', 'price',      'PRICE',        '物料价格', '',           1, 1, 1, 0, 0, 0, 40, '', '' FROM DUAL
UNION ALL SELECT 11825, 'DS105', 'S_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',           1, 1, 1, 0, 0, 0, 50, '', '' FROM DUAL  
UNION ALL SELECT 11826, 'DS105', 'S_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',           1, 1, 1, 0, 0, 0, 60, '', '' FROM DUAL  
--
--返回
UNION ALL SELECT 11831, 'DS105', 'RECEIVE_MESSAGE', 'status',         'SUCCESS',     '成功标记',    '',          1, 1, 1, 0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 11832, 'DS105', 'RECEIVE_MESSAGE', 'error',          'MESSAGE',     '错误信息',    '',          1, 1, 1, 0, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 11833, 'DS105', 'RECEIVE_MESSAGE', 'errorCode',      'ERROR_CODE',  '错误代码',    '',          1, 1, 1, 0, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11834, 'DS105', 'RECEIVE_MESSAGE', 'productNo',      'PRODUCT_NO',  '产品编号',    '',          1, 1, 1, 0, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 11835, 'DS105', 'RECEIVE_MESSAGE', 'pick',           'PICK',        '对方标识',    '',          1, 1, 1, 0, 1, 1, 50, '', '' FROM DUAL
--
UNION ALL SELECT 11841, 'DS105', 'RECEIVE_THIRD', 'pick',           'DATA',        '对方标识',    '',                  7,  1, 0, 64, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 11842, 'DS105', 'RECEIVE_THIRD', 'ifCode',         'IF_CODE',     '接口代码',    '$RH_IF_CODE',       21, 1, 0, 20, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 11843, 'DS105', 'RECEIVE_THIRD', 'createTime',     'CREATE_TIME', '创建时间',    '$SYSDATE',          21, 1, 0, 0,  1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11844, 'DS105', 'RECEIVE_THIRD', 'fkId',           'FK_ID',       '产品主键',    '',                   1, 1, 0, 64, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 11845, 'DS105', 'RECEIVE_THIRD', 'pkId',           'PK_ID',       '逻辑主键',    '',                  22, 1, 0, 19, 1, 1, 50, '', '' FROM DUAL
--
UNION ALL SELECT 11851, 'DS105', 'RECEIVE_MASTER', 'pkId',        'PK_ID',         '逻辑主键', '',                     22, 1, 0, 0,  1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 11852, 'DS105', 'RECEIVE_MASTER', 'batchNo',     'BATCH_NO',      '生成批次', '$RH_THIRD_FILE_KEY',   21, 1, 1, 32, 1, 1, 20, '', '' FROM DUAL
UNION ALL SELECT 11853, 'DS105', 'RECEIVE_MASTER', 'ifCode',      'IF_CODE',       '接口代码', '$RH_IF_CODE',          23, 1, 0, 20, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11854, 'DS105', 'RECEIVE_MASTER', 'productNo',   'PRODUCT_NO',    '产品编号', '',                      3, 1, 0, 30, 1, 1, 40, '', '' FROM DUAL
UNION ALL SELECT 11855, 'DS105', 'RECEIVE_MASTER', 'productName', 'PRODUCT_NAME',  '产品名称', '',                      1, 1, 0, 64, 1, 1, 50, '', '' FROM DUAL  
UNION ALL SELECT 11856, 'DS105', 'RECEIVE_MASTER', 'price',       'PRICE',         '产品价格', '',                      1, 1, 1, 4,  1, 1, 60, '', '' FROM DUAL
UNION ALL SELECT 11857, 'DS105', 'RECEIVE_MASTER', 'quantity',    'QUANTITY',      '产品数量', '',                      1, 1, 1, 6,  1, 1, 70, '', '' FROM DUAL  
UNION ALL SELECT 11858, 'DS105', 'RECEIVE_MASTER', 'effDate',     'EFF_DATE',      '生效时间', '',                      1, 1, 1, 0,  1, 1, 80, '', '' FROM DUAL
UNION ALL SELECT 11859, 'DS105', 'RECEIVE_MASTER', 'createTime',  'CREATE_TIME',   '创建时间', '$RH_CREATE_TIME',       1, 1, 1, 0,  1, 0, 90, '', '' FROM DUAL
UNION ALL SELECT 11860, 'DS105', 'RECEIVE_MASTER', 'updateTime',  'UPDATE_TIME',   '修改时间', '$RH_CREATE_TIME',       1, 1, 1, 0,  1, 1, 100,'', '' FROM DUAL
--
UNION ALL SELECT 11861, 'DS105', 'RECEIVE_DETAIL', 'pkId',       'PK_ID',        '逻辑主键', '',                 22, 1, 1,  0, 1, 1, 10, '', '' FROM DUAL  
UNION ALL SELECT 11862, 'DS105', 'RECEIVE_DETAIL', 'fkId',       'FK_ID',        '产品主键', '',                 25, 1, 0, 19, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 11863, 'DS105', 'RECEIVE_DETAIL', 'itemNo',     'ITEM_NO',      '物料编号', '',                  3, 1, 0, 64, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11864, 'DS105', 'RECEIVE_DETAIL', 'itemName',   'ITEM_NAME',    '物料名称', '',                  1, 1, 0, 64, 1, 1, 40, '', '' FROM DUAL  
UNION ALL SELECT 11865, 'DS105', 'RECEIVE_DETAIL', 'price',      'PRICE',        '物料价格', '',                  1, 1, 1,  4, 1, 1, 50, '', '' FROM DUAL
UNION ALL SELECT 11866, 'DS105', 'RECEIVE_DETAIL', 'quantity',   'QUANTITY',     '物料数量', '',                  1, 1, 1,  6, 1, 1, 60, '', '' FROM DUAL  
UNION ALL SELECT 11867, 'DS105', 'RECEIVE_DETAIL', 'effDate',    'EFF_DATE',     '生效时间', '',                  1, 1, 1,  0, 1, 1, 70, '', '' FROM DUAL  
UNION ALL SELECT 11868, 'DS105', 'RECEIVE_DETAIL', 'createTime', 'CREATE_TIME',  '创建时间', '$RH_CREATE_TIME',   1, 1, 1,  0, 1, 0, 80, '', '' FROM DUAL
UNION ALL SELECT 11869, 'DS105', 'RECEIVE_DETAIL', 'updateTime', 'UPDATE_TIME',  '修改时间', '$RH_CREATE_TIME',   1, 1, 1,  0, 1, 1, 90,'', '' FROM DUAL
--
UNION ALL SELECT 11881, 'DS105', 'RECEIVE_HEAD', 'pkId',         'PK_ID',           '逻辑主键', '',                   22, 1, 0,  0, 1, 1, 10, '', '' FROM DUAL
UNION ALL SELECT 11882, 'DS105', 'RECEIVE_HEAD', 'batchNo',      'BATCH_NO',        '生成批次', '$RH_THIRD_FILE_KEY', 21, 1, 0, 32, 1, 1, 20, '', '' FROM DUAL  
UNION ALL SELECT 11883, 'DS105', 'RECEIVE_HEAD', 'ifCode',       'IF_CODE',         '接口代码', '$RH_IF_CODE',        21, 1, 0, 20, 1, 1, 30, '', '' FROM DUAL
UNION ALL SELECT 11884, 'DS105', 'RECEIVE_HEAD', 'createTime',   'CREATE_TIME',     '创建时间', '$RH_CREATE_TIME',    21, 1, 0,  0, 1, 1, 40, '', '' FROM DUAL  
;

COMMIT;





