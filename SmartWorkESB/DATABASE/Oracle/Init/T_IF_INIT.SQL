
--增加超级用户
DELETE FROM T_IF_USER WHERE USER_NAME='root';
INSERT INTO T_IF_USER(USER_NAME, USER_DESC, USER_PWD, USER_STATUS, IS_UPDATE_PWD)
          SELECT 'root', '系统管理员', 'e10adc3949ba59abbe56e057f20f883e', 1, 0 from dual
;
COMMIT;

--增加模块
--MODULE_NAME：按分号分割，第一个：显示内容，第二个：是否展开；第三个：图标
DELETE FROM T_IF_MODULE; 
INSERT INTO T_IF_MODULE(PARENT_MODULE, MODULE_CODE, MODULE_NAME, SORT_NO)
          SELECT 'ROOT', 'PUB',     '系统管理;0;icon icon-th-list',    99 from dual
UNION ALL SELECT 'ROOT', 'CONFIG',  '配置管理;0;icon icon-th-list',    1  from dual
UNION ALL SELECT 'ROOT', 'LOG',     '日志管理;1;icon icon-th-list',    2  from dual
UNION ALL SELECT 'ROOT', 'LOG_HIS', '历史日志管理;0;icon icon-th-list', 3  from dual
;
COMMIT;          

--增加权限
--PRIVILEGES_DESC：按分号分割，第一个：显示内容；第三个：图标
DELETE FROM T_IF_PRIVILEGES; 
INSERT INTO T_IF_PRIVILEGES(PK_ID, MODULE_CODE, PRIVILEGES_TYPE, PRIVILEGES_DESC, CONTENT, SORT_NO)
          SELECT 99000001, 'PUB', 0, '用户信息;icon icon-circle-blank', 'pub/user.html',         2 from dual
UNION ALL SELECT 99000002, 'PUB', 0, '角色信息;icon icon-circle-blank', 'pub/role.html',         3 from dual
UNION ALL SELECT 99000003, 'PUB', 0, '数据字典;icon icon-circle-blank', 'pub/data_dict.html',     1 from dual
--
UNION ALL SELECT 11000001, 'CONFIG', 0, '全局参数配置;icon icon-circle-blank', 'config/param.html',          1 from dual
UNION ALL SELECT 11000002, 'CONFIG', 0, '发送接口配置;icon icon-circle-blank', 'config/send_config.html',    2 from dual
UNION ALL SELECT 11000003, 'CONFIG', 0, '接收接口配置;icon icon-circle-blank', 'config/receive_config.html', 3 from dual
UNION ALL SELECT 11000004, 'CONFIG', 0, '定时接口配置;icon icon-circle-blank', 'config/time_config.html',    4 from dual
UNION ALL SELECT 11000005, 'CONFIG', 0, '计划任务;icon icon-circle-blank',    'config/timer.html',          5 from dual
--
UNION ALL SELECT 12000001, 'LOG', 0, '发送日志查询;icon icon-circle-blank', 'log/send.html',    1 from dual
UNION ALL SELECT 12000002, 'LOG', 0, '接收日志查询;icon icon-circle-blank', 'log/receive.html', 2 from dual
UNION ALL SELECT 12000003, 'LOG', 0, '定时日志查询;icon icon-circle-blank', 'log/time.html',    3 from dual
--
UNION ALL SELECT 13000001, 'LOG_HIS', 0, '发送历史日志查询;icon icon-circle-blank', 'log/his_send.html',    1 from dual
UNION ALL SELECT 13000002, 'LOG_HIS', 0, '接收历史日志查询;icon icon-circle-blank', 'log/his_receive.html', 2 from dual
UNION ALL SELECT 13000003, 'LOG_HIS', 0, '定时历史日志查询;icon icon-circle-blank', 'log/his_time.html',    3 from dual
;
COMMIT;   

--增加数据字典
DELETE FROM T_IF_DATA_DICT;
INSERT INTO T_IF_DATA_DICT(PK_ID, CODE_TYPE, CODE_TYPE_NAME, CODE_VALUE, CODE_VALUE_NAME, CODE_DESC, IS_EDIT, SORT_NO)
          SELECT 101, 'JOB_CLASS', '定时器类名', 'com.smartwork.esb.job.ClientSenderJob', '发送任务',    '', 1, 1 from dual
UNION ALL SELECT 102, 'JOB_CLASS', '定时器类名', 'com.smartwork.esb.job.TimerJob',        '定时任务',    '', 1, 2 from dual
--
UNION ALL SELECT 111, 'SEND_SERVICE_TYPE', '发送服务类型', 'RFC',      'SAP发送',        '', 1, 1 from dual
UNION ALL SELECT 112, 'SEND_SERVICE_TYPE', '发送服务类型', 'WS',       'WebService发送', '', 1, 2 from dual 
UNION ALL SELECT 113, 'SEND_SERVICE_TYPE', '发送服务类型', 'ActiveMQ', 'ActiveMQ发送',   '', 1, 3 from dual 
UNION ALL SELECT 114, 'SEND_SERVICE_TYPE', '发送服务类型', 'Http',     'http发送',       '', 1, 4 from dual 
UNION ALL SELECT 115, 'SEND_SERVICE_TYPE', '发送服务类型', 'Other',    '自定义发送',       '', 1, 0 from dual 
--
UNION ALL SELECT 131, 'RECEIVE_SERVICE_TYPE', '接收服务类型', 'IDoc',        'SAP接收',        '', 1, 1 from dual
UNION ALL SELECT 132, 'RECEIVE_SERVICE_TYPE', '接收服务类型', 'ActiveMQ',    'ActiveMQ接收',   '', 1, 2 from dual 
UNION ALL SELECT 133, 'RECEIVE_SERVICE_TYPE', '接收服务类型', 'Other',       '自定义接收',       '', 1, 0 from dual 
UNION ALL SELECT 134, 'RECEIVE_SERVICE_TYPE', '接收服务类型', 'ASynFile',    '异步文件',        '', 1, 3 from dual
--
UNION ALL SELECT 141, 'TIMER_SERVICE_TYPE', '定时服务类型', 'Procedure',      '存储过程定时',        '', 1, 1 from dual
UNION ALL SELECT 142, 'TIMER_SERVICE_TYPE', '定时服务类型', 'Other',          '自定义定时',          '', 1, 0 from dual 
--
UNION ALL SELECT 161, 'RECEIVE_TRANSATION_TYPE', '接收事务类型', '1', '每笔数据',    '', 1, 1 from dual
UNION ALL SELECT 162, 'RECEIVE_TRANSATION_TYPE', '接收事务类型', '2', '每笔主表数据', '', 1, 2 from dual 
UNION ALL SELECT 163, 'RECEIVE_TRANSATION_TYPE', '接收事务类型', '3', '整个接收数据', '', 1, 3 from dual         
--
UNION ALL SELECT 171, 'RECEIVE_TABLE_TYPE', '接收表类型', '1', '普通表',          '', 1, 1 from dual
UNION ALL SELECT 172, 'RECEIVE_TABLE_TYPE', '接收表类型', '2', 'DS表',           '', 1, 2 from dual 
UNION ALL SELECT 173, 'RECEIVE_TABLE_TYPE', '接收表类型', '3', 'AT表',           '', 1, 3 from dual 
UNION ALL SELECT 174, 'RECEIVE_TABLE_TYPE', '接收表类型', '4', '接口表（只增）',    '', 1, 0 from dual
--
UNION ALL SELECT 181, 'RECEIVE_KEY_SEQUENCE', '接收表序列', '$UUID', '32位UUID',      '', 1, 1 from dual
UNION ALL SELECT 182, 'RECEIVE_KEY_SEQUENCE', '接收表序列', '$DB',   '数据库自生成',     '', 1, 2 from dual 
--
UNION ALL SELECT 191, 'RECEIVE_CREATE_TYPE', '接收新增数据类型', '0', '不允许新增',          '', 1, 1 from dual
UNION ALL SELECT 192, 'RECEIVE_CREATE_TYPE', '接收新增数据类型', '1', '允许新增',           '', 1, 2 from dual 
--
UNION ALL SELECT 201, 'RECEIVE_UPDATE_TYPE', '接收更新数据类型', '1', '更新所有字段',          '', 1, 1 from dual
UNION ALL SELECT 202, 'RECEIVE_UPDATE_TYPE', '接收更新数据类型', '2', '只更新有字段的数据',      '', 1, 2 from dual 
UNION ALL SELECT 203, 'RECEIVE_UPDATE_TYPE', '接收更新数据类型', '3', '只更新不为空的数据',      '', 1, 3 from dual 
UNION ALL SELECT 204, 'RECEIVE_UPDATE_TYPE', '接收更新数据类型', '4', '不允许更新',            '', 1, 4 from dual
--
UNION ALL SELECT 212, 'QUERY_TABLE_ID', '发送/查询特殊配置代码', 'QUERY_MESSAGE', '查询消息',      '', 1, 2 from dual
--
UNION ALL SELECT 221, 'RECEIVE_TABLE_ID', '接收特殊配置代码', 'RECEIVE_HEAD',    '接收表头',      '', 1, 1 from dual
UNION ALL SELECT 222, 'RECEIVE_TABLE_ID', '接收特殊配置代码', 'RECEIVE_MESSAGE', '接收消息',      '', 1, 2 from dual
--
--UNION ALL SELECT 231, 'ELEMENT_ELEMENT_NAME', '特殊对方字段', '$SUCCESS', '发送成功标记（发送接口）',      '', 1, 1 from dual
--UNION ALL SELECT 232, 'ELEMENT_ELEMENT_NAME', '特殊对方字段', '$MESSAGE', '发送失败信息（发送接口）',      '', 1, 2 from dual 
--UNION ALL SELECT 233, 'ELEMENT_ELEMENT_NAME', '特殊对方字段', 'SUCCESS',  '接收成功标记（接收接口）',      '', 1, 3 from dual 
--UNION ALL SELECT 234, 'ELEMENT_ELEMENT_NAME', '特殊对方字段', 'MESSAGE',  '接收异常信息（接收接口）',      '', 1, 4 from dual
--UNION ALL SELECT 235, 'ELEMENT_ELEMENT_NAME', '特殊对方字段', 'TABLE_ID', '接收配置代码（接收接口）',      '', 1, 4 from dual
--
UNION ALL SELECT 241, 'ELEMENT_FIELD_NAME', '特殊本方字段', '$DELETE_FLAG', '返回删除标记（发送接口）',         '', 1, 1 from dual
UNION ALL SELECT 242, 'ELEMENT_FIELD_NAME', '特殊本方字段', 'SUCCESS',      '返回发送/接收成功标记（接收消息/接收接口）',      '', 1, 2 from dual 
UNION ALL SELECT 243, 'ELEMENT_FIELD_NAME', '特殊本方字段', 'MESSAGE',      '返回发送/接收异常信息（接收消息/接收接口）',      '', 1, 3 from dual
UNION ALL SELECT 244, 'ELEMENT_FIELD_NAME', '特殊本方字段', 'ERROR_CODE',   '返回发送/接收异常代码（接收消息/接收接口）',      '', 1, 4 from dual
UNION ALL SELECT 245, 'ELEMENT_FIELD_NAME', '特殊本方字段', 'TABLE_ID',     '接收配置代码（接收接口）',      '', 1, 4 from dual
--
UNION ALL SELECT 251, 'ELEMENT_DEFAULT_VALUE', '默认值', '$UUID',                 '32位UUID',            '', 1, 1 from dual
UNION ALL SELECT 252, 'ELEMENT_DEFAULT_VALUE', '默认值', '$SYSDATE',              '系统时间',              '', 1, 2 from dual 
UNION ALL SELECT 253, 'ELEMENT_DEFAULT_VALUE', '默认值', '$QP_',                  '接口参数前缀',           '', 1, 3 from dual
UNION ALL SELECT 254, 'ELEMENT_DEFAULT_VALUE', '默认值', '$RP_',                  '接口接收参数前缀',        '', 1, 4 from dual
UNION ALL SELECT 255, 'ELEMENT_DEFAULT_VALUE', '默认值', '$QH_',                  '发送/查询表头前缀',       '', 1, 5 from dual
UNION ALL SELECT 256, 'ELEMENT_DEFAULT_VALUE', '默认值', '$QH_ORIG_FILE_KEY',     '发送/查询原始文件主键',    '', 1, 6 from dual 
UNION ALL SELECT 257, 'ELEMENT_DEFAULT_VALUE', '默认值', '$QH_THIRD_FILE_KEY',    '发送转换文件主键',        '', 1, 7 from dual
UNION ALL SELECT 258, 'ELEMENT_DEFAULT_VALUE', '默认值', '$QH_IF_CODE',           '发送/查询接口代码',       '', 1, 8 from dual
UNION ALL SELECT 259, 'ELEMENT_DEFAULT_VALUE', '默认值', '$QH_CREATE_TIME',       '发送/查询时间',          '', 1, 9 from dual
UNION ALL SELECT 260, 'ELEMENT_DEFAULT_VALUE', '默认值', '$RH_',                  '接收表头前缀',           '', 1, 10 from dual
UNION ALL SELECT 261, 'ELEMENT_DEFAULT_VALUE', '默认值', '$RH_THIRD_FILE_KEY',    '接收文件主键',           '', 1, 11 from dual 
UNION ALL SELECT 262, 'ELEMENT_DEFAULT_VALUE', '默认值', '$RH_THIRD_KEY',         '接收对方关键数据',        '', 1, 12 from dual
UNION ALL SELECT 263, 'ELEMENT_DEFAULT_VALUE', '默认值', '$RH_THIRD_IF_CODE',     '接收对方接口代码',        '', 1, 13 from dual
UNION ALL SELECT 264, 'ELEMENT_DEFAULT_VALUE', '默认值', '$RH_IF_CODE',           '接收接口代码',           '', 1, 14 from dual 
UNION ALL SELECT 265, 'ELEMENT_DEFAULT_VALUE', '默认值', '$RH_CREATE_TIME',       '接收时间',              '', 1, 15 from dual
--
UNION ALL SELECT 281, 'ELEMENT_FIELD_TYPE', '字段类型转换', '1',     '00000000',            '普通字段',         1, 1 from dual
UNION ALL SELECT 282, 'ELEMENT_FIELD_TYPE', '字段类型转换', '2',     '01000000',            '逻辑主键',         1, 2 from dual 
UNION ALL SELECT 283, 'ELEMENT_FIELD_TYPE', '字段类型转换', '3',     '00100000',            '业务主键',         1, 3 from dual
UNION ALL SELECT 284, 'ELEMENT_FIELD_TYPE', '字段类型转换', '4',     '00010000',            '外键',            1, 4 from dual
UNION ALL SELECT 285, 'ELEMENT_FIELD_TYPE', '字段类型转换', '5',     '00110000',            '业务主键、外键',    1, 5 from dual
UNION ALL SELECT 286, 'ELEMENT_FIELD_TYPE', '字段类型转换', '6',     '00001000',            '对方日期字段',      1, 6 from dual 
UNION ALL SELECT 287, 'ELEMENT_FIELD_TYPE', '字段类型转换', '7',     '00000100',            '数据来自父表',      1, 7 from dual
UNION ALL SELECT 288, 'ELEMENT_FIELD_TYPE', '字段类型转换', '8',     '00000010',            '别名字段',         1, 8 from dual
UNION ALL SELECT 289, 'ELEMENT_FIELD_TYPE', '字段类型转换', '9',     '00000001',            '对方关键字段',      1, 9 from dual
UNION ALL SELECT 290, 'ELEMENT_FIELD_TYPE', '字段类型转换', '21',    '10000000',            '临时普通字段',      1, 10 from dual
UNION ALL SELECT 291, 'ELEMENT_FIELD_TYPE', '字段类型转换', '22',    '11000000',            '临时逻辑主键',      1, 11 from dual 
UNION ALL SELECT 292, 'ELEMENT_FIELD_TYPE', '字段类型转换', '23',    '10100000',            '临时业务主键',      1, 12 from dual
UNION ALL SELECT 293, 'ELEMENT_FIELD_TYPE', '字段类型转换', '24',    '10010000',            '临时外键',         1, 13 from dual
UNION ALL SELECT 294, 'ELEMENT_FIELD_TYPE', '字段类型转换', '25',    '10110000',            '临时业务主键、外键', 1, 14 from dual 
UNION ALL SELECT 295, 'ELEMENT_FIELD_TYPE', '字段类型转换', '26',    '10001000',            '临时对方日期字段',   1, 15 from dual
UNION ALL SELECT 296, 'ELEMENT_FIELD_TYPE', '字段类型转换', '27',    '10000100',            '临时来自父表字段',   1, 16 from dual 
UNION ALL SELECT 297, 'ELEMENT_FIELD_TYPE', '字段类型转换', '28',    '10000010',            '临时别名字段',      1, 17 from dual
--CODE_DESC：字段互斥，0代表不可以
UNION ALL SELECT 310, 'ELEMENT_USER_FIELD_TYPE_INDEX', '自定义字段类型下标', '0',     '普通',            '00000000',         1, 1 from dual
UNION ALL SELECT 311, 'ELEMENT_USER_FIELD_TYPE_INDEX', '自定义字段类型下标', '1',     '临时',            '11111111',         1, 1 from dual
UNION ALL SELECT 312, 'ELEMENT_USER_FIELD_TYPE_INDEX', '自定义字段类型下标', '2',     '逻辑主键',         '11000000',         1, 2 from dual 
UNION ALL SELECT 313, 'ELEMENT_USER_FIELD_TYPE_INDEX', '自定义字段类型下标', '3',     '业务主键',         '10111111',         1, 3 from dual
UNION ALL SELECT 314, 'ELEMENT_USER_FIELD_TYPE_INDEX', '自定义字段类型下标', '4',     '外键',            '10110000',         1, 4 from dual
UNION ALL SELECT 315, 'ELEMENT_USER_FIELD_TYPE_INDEX', '自定义字段类型下标', '5',     '对方日期',         '10101111',         1, 5 from dual
UNION ALL SELECT 316, 'ELEMENT_USER_FIELD_TYPE_INDEX', '自定义字段类型下标', '6',     '来自父表',         '10101101',         1, 6 from dual 
UNION ALL SELECT 317, 'ELEMENT_USER_FIELD_TYPE_INDEX', '自定义字段类型下标', '7',     '别名',            '10101011',         1, 7 from dual
UNION ALL SELECT 318, 'ELEMENT_USER_FIELD_TYPE_INDEX', '自定义字段类型下标', '8',     '关键数据',         '10101111',         1, 8 from dual
--
UNION ALL SELECT 331, 'ELEMENT_DATA_CONVERT', '数据转换', 'TIMESTAMP2DATE',           '去除时间部分',          '', 1, 1 from dual
UNION ALL SELECT 332, 'ELEMENT_DATA_CONVERT', '数据转换', 'DATE2STRING',              '日期转字符串',          '', 1, 2 from dual 
UNION ALL SELECT 333, 'ELEMENT_DATA_CONVERT', '数据转换', 'DATE2CALENDAR',            '日期转日历',            '', 1, 3 from dual
UNION ALL SELECT 334, 'ELEMENT_DATA_CONVERT', '数据转换', 'CALENDAR2STRING',          '日历转字符串',          '', 1, 4 from dual
UNION ALL SELECT 335, 'ELEMENT_DATA_CONVERT', '数据转换', 'CALENDAR2DATE',            '日历转日期',            '', 1, 5 from dual
UNION ALL SELECT 336, 'ELEMENT_DATA_CONVERT', '数据转换', 'BOOL2INT',                 'Bool转换0/1',         '', 1, 6 from dual 
UNION ALL SELECT 337, 'ELEMENT_DATA_CONVERT', '数据转换', 'STRING2DATE',              '字符串转日期时间',       '', 1, 7 from dual
UNION ALL SELECT 338, 'ELEMENT_DATA_CONVERT', '数据转换', 'STRING2CALENDAR',          '字符串转日历',          '', 1, 8 from dual
UNION ALL SELECT 339, 'ELEMENT_DATA_CONVERT', '数据转换', 'STRING2BOOL',              '字符串转Bool',         '', 1, 9 from dual
UNION ALL SELECT 340, 'ELEMENT_DATA_CONVERT', '数据转换', 'STRING2BYTE',              '字符串转Byte',         '', 1, 10 from dual
UNION ALL SELECT 341, 'ELEMENT_DATA_CONVERT', '数据转换', 'STRING2SHORT',             '字符串转Short',        '', 1, 11 from dual 
UNION ALL SELECT 342, 'ELEMENT_DATA_CONVERT', '数据转换', 'STRING2INT',               '字符串转Int',          '', 1, 12 from dual
UNION ALL SELECT 343, 'ELEMENT_DATA_CONVERT', '数据转换', 'STRING2LONG',              '字符串转Long',         '', 1, 13 from dual
UNION ALL SELECT 344, 'ELEMENT_DATA_CONVERT', '数据转换', 'STRING2FLOAT',             '字符串转Float',        '', 1, 14 from dual 
UNION ALL SELECT 345, 'ELEMENT_DATA_CONVERT', '数据转换', 'STRING2DOUBLE',            '字符串转Double',       '', 1, 15 from dual
UNION ALL SELECT 346, 'ELEMENT_DATA_CONVERT', '数据转换', 'OTHER2STRING',             '其他数据转字符串',       '', 1, 16 from dual
UNION ALL SELECT 347, 'ELEMENT_DATA_CONVERT', '数据转换', 'STRING2TRIM',              '字符串去前后空格',       '', 1, 17 from dual 
UNION ALL SELECT 348, 'ELEMENT_DATA_CONVERT', '数据转换', 'STRING2LTRIM',             '字符串去前空格',        '', 1, 18 from dual
UNION ALL SELECT 349, 'ELEMENT_DATA_CONVERT', '数据转换', 'STRING2RTRIM',             '字符串去后空格',        '', 1, 19 from dual
UNION ALL SELECT 350, 'ELEMENT_DATA_CONVERT', '数据转换', 'NUMBER2DATE',              '数字转日期',           '', 1, 20 from dual 
UNION ALL SELECT 351, 'ELEMENT_DATA_CONVERT', '数据转换', 'NUMBER2CALENDAR',          '数字转日历',           '', 1, 21 from dual
--
UNION ALL SELECT 371, 'ELEMENT_DATA_FORMAT', '数据转换格式', 'yyyy-MM-dd',                     '日期',          '', 1, 1 from dual
UNION ALL SELECT 372, 'ELEMENT_DATA_FORMAT', '数据转换格式', 'HH:mm:ss',                       '时间',          '', 1, 2 from dual 
UNION ALL SELECT 373, 'ELEMENT_DATA_FORMAT', '数据转换格式', 'yyyy-MM-dd HH:mm:ss',            '日期时间',       '', 1, 3 from dual
UNION ALL SELECT 374, 'ELEMENT_DATA_FORMAT', '数据转换格式', 'yyyy-MM-dd''T''HH:mm:ss+08:00',  '完整日期时间',    '', 1, 4 from dual
--
UNION ALL SELECT 381, 'LOG_RECEIVE_RESULT', '接收处理结果', '1',   '失败',          '', 1, 1 from dual
UNION ALL SELECT 382, 'LOG_RECEIVE_RESULT', '接收处理结果', '2',   '成功',          '', 1, 2 from dual 
UNION ALL SELECT 383, 'LOG_RECEIVE_RESULT', '接收处理结果', '3',   '部分成功',       '', 1, 3 from dual
UNION ALL SELECT 384, 'LOG_RECEIVE_RESULT', '接收处理结果', '4',   '提示',          '', 1, 4 from dual
--
UNION ALL SELECT 391, 'LOG_LOG_TYPE', '日志类型', '1',  '主表',          '', 1, 1 from dual
UNION ALL SELECT 392, 'LOG_LOG_TYPE', '日志类型', '2',  '从表',          '', 1, 2 from dual 
UNION ALL SELECT 393, 'LOG_LOG_TYPE', '日志类型', '3',  '过程',          '', 1, 3 from dual
UNION ALL SELECT 394, 'LOG_LOG_TYPE', '日志类型', '4',  '整体',          '', 1, 4 from dual
--
UNION ALL SELECT 401, 'LOG_SEND_RESULT', '发送处理结果', '1',   '失败',          '', 1, 1 from dual
UNION ALL SELECT 402, 'LOG_SEND_RESULT', '发送处理结果', '2',   '成功',          '', 1, 2 from dual 
UNION ALL SELECT 403, 'LOG_SEND_RESULT', '发送处理结果', '3',   '部分成功',       '', 1, 3 from dual
UNION ALL SELECT 404, 'LOG_SEND_RESULT', '发送处理结果', '4',   '提示',          '', 1, 4 from dual
--
;
COMMIT;


--增加参数模板
DELETE FROM T_IF_PARAM_TEMPLATE_GROUP;
INSERT INTO T_IF_PARAM_TEMPLATE_GROUP(PK_ID, PARAM_GROUP, PARAM_GROUP_NAME)
          SELECT 1,  'STRING_CHARSET',     '字符串编码'           from dual
UNION ALL SELECT 2,  'TIME_ZONE',          '时区'                from dual
UNION ALL SELECT 3,  'COL_VALUE',          'AT/DS列值'           from dual
UNION ALL SELECT 4,  'RFC',                'SAP发送'             from dual
UNION ALL SELECT 5,  'IDOC',               'SAP接收'             from dual
UNION ALL SELECT 6,  'WEB_SERVICE',        'webservice发送'      from dual
UNION ALL SELECT 7,  'HTTP',               'http发送'            from dual
UNION ALL SELECT 8,  'DATABSE',            '对方数据库'           from dual
UNION ALL SELECT 9,  'XML',                'XML生成和解析'        from dual
UNION ALL SELECT 10, 'JSON',               'JSON生成和解析'       from dual
UNION ALL SELECT 12, 'CONTINUE_SEND',      '连续发送'            from dual
UNION ALL SELECT 13, 'NO_STATUS_SEND',     '无反写状态发送'        from dual
UNION ALL SELECT 14, 'CHILD_IF_SEND',      '发送子接口'           from dual
UNION ALL SELECT 15, 'CONTINUE_RECEIVE',   '异步连续文件接收'       from dual
UNION ALL SELECT 17, 'COMMON',             '常规'               from dual
;

DELETE FROM T_IF_PARAM_TEMPLATE;
INSERT INTO T_IF_PARAM_TEMPLATE(PK_ID, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, PARAM_DESC)
          SELECT 101, 'STRING_CHARSET', 'STRING_CHARSET_NAME',      '本方字符串长度判断编码',   'UTF-8', '' from dual
UNION ALL SELECT 102, 'STRING_CHARSET', 'THIRD_STRING_CHARSET_NAME','对方字符串长度判断编码',   'UTF-8', '' from dual
--
UNION ALL SELECT 111, 'TIME_ZONE', 'TIME_ZONE',    '当前时区',              'GMT+08:00', '' from dual
UNION ALL SELECT 112, 'TIME_ZONE', 'TIME_ZONE_U',  '当前时区与GMT时差（小时）', '8.0',       '' from dual
--
UNION ALL SELECT 120, 'COL_VALUE', 'COL_SITE_NUM',       '表SITE_NUM字段值',        '130000027',    '要求填写数字' from dual
UNION ALL SELECT 121, 'COL_VALUE', 'COL_ATR_NAME',       '表ATR_NAME字段值',        '',             '为空时默认32位UUID' from dual
UNION ALL SELECT 122, 'COL_VALUE', 'COL_PURGE_STATUS',   '表PURGE_STATUS字段值',    '0',            '要求填写数字' from dual
UNION ALL SELECT 123, 'COL_VALUE', 'COL_XFR_INSERT_PID', '表XFR_INSERT_PID字段值',  '0',            '要求填写数字' from dual
UNION ALL SELECT 124, 'COL_VALUE', 'COL_XFR_UPDATE_PID', '表XFR_UPDATE_PID字段值',  '0',            '要求填写数字' from dual
UNION ALL SELECT 125, 'COL_VALUE', 'COL_TRX_ID',         '表TRX_ID字段值',          '',             '为空时默认32位UUID' from dual
--
UNION ALL SELECT 130, 'RFC', 'FUN_NAME',               '执行方法名',        '',      '必须配置' from dual
UNION ALL SELECT 131, 'RFC', 'COMMIT_FUN_NAME',        '提交方法名',        '',      '' from dual
UNION ALL SELECT 132, 'RFC', 'ROLLBACK_FUN_NAME',      '回滚方法名',        '',      '' from dual
UNION ALL SELECT 133, 'RFC', 'RFC_EXCEPTION_REGEX',    '正常的报错正则表达式', '',     'RFC走Biztalk时，一般为[1-9][0-9]* IDocs were generated' from dual
UNION ALL SELECT 134, 'RFC', 'REQUEST_TIMEOUT',        '请求超时时间',      '300000','5分钟（访问卡住时长）'                               from dual
--
UNION ALL SELECT 140, 'IDOC', 'THIRD_IF_CODE',         '对方接口代码',       '',     '必须配置' from dual
--
UNION ALL SELECT 150, 'WEB_SERVICE', 'SERVER_URL',              '服务地址',        '',           '不用加?wsdl'                        from dual
UNION ALL SELECT 151, 'WEB_SERVICE', 'ACTION_URL',              '方法地址',        '',           '一般为“namespace+/+方法名”'             from dual
UNION ALL SELECT 152, 'WEB_SERVICE', 'REQUEST_PARAM_NAME',      '请求参数名',       '',          '直接发送文件内容时使用'                  from dual
UNION ALL SELECT 153, 'WEB_SERVICE', 'QUERY_ELEMENT_ROOT',      'XML根标签名',      '',          '一般为方法名；直接发送文件内容时不配置'           from dual
UNION ALL SELECT 154, 'WEB_SERVICE', 'QUERY_ELEMENT_ROOT_ATTR', 'XML根标签属性',    '',          '一般为xmlns=namespace；直接发送文件内容时不配置' from dual
UNION ALL SELECT 155, 'WEB_SERVICE', 'USER_NAME',               '访问用户名',       '',          '网页访问wsdl时弹窗式'                    from dual
UNION ALL SELECT 156, 'WEB_SERVICE', 'PASSWORD',                '访问密码',         '',          '网页访问wsdl时弹窗式'                   from dual
UNION ALL SELECT 157, 'WEB_SERVICE', 'REQUEST_ENCODING',        '请求编码',        'UTF-8',      ''                                   from dual
UNION ALL SELECT 158, 'WEB_SERVICE', 'REQUEST_TIMEOUT',         '请求超时时间',     '300000',     '5分钟'                               from dual
UNION ALL SELECT 159, 'WEB_SERVICE', 'QUERY_FILE_ENCODING',     '发送文件编码',     'UTF-8',      ''                                   from dual
UNION ALL SELECT 160, 'WEB_SERVICE', 'RECEIVE_FILE_ENCODING',   '接收文件编码',     'UTF-8',      ''                                   from dual
UNION ALL SELECT 161, 'WEB_SERVICE', 'QUERY_FILE_TYPE',         '发送文件类型',     'xml',        '发送文件内容时使用；值为xml或json'         from dual
UNION ALL SELECT 162, 'WEB_SERVICE', 'RECEIVE_FILE_TYPE',       '接收文件类型',     'xml',        '发送文件内容时使用；值为xml或json'         from dual
UNION ALL SELECT 163, 'WEB_SERVICE', 'RESPONSE_STRING',         '是否返回字符串',    '0',          '0：返回对象，1：返回文件内容'              from dual
--
UNION ALL SELECT 180, 'HTTP', 'SERVER_URL',              'http地址',        '',          ''                                  from dual
UNION ALL SELECT 181, 'HTTP', 'REQUEST_PARAM_NAME',      '请求参数名',       '',          ''                                   from dual
UNION ALL SELECT 182, 'HTTP', 'USER_NAME',               '访问用户名',       '',          '网页访问时弹窗式'                       from dual
UNION ALL SELECT 183, 'HTTP', 'PASSWORD',                '访问密码',         '',          '网页访问时弹窗式'                       from dual
UNION ALL SELECT 184, 'HTTP', 'REQUEST_ENCODING',        '请求编码',        'UTF-8',      ''                                   from dual
UNION ALL SELECT 185, 'HTTP', 'REQUEST_TIMEOUT',         '请求超时时间',     '300000',     '5分钟'                               from dual
UNION ALL SELECT 186, 'HTTP', 'QUERY_FILE_ENCODING',     '发送文件编码',     'UTF-8',      ''                                   from dual
UNION ALL SELECT 187, 'HTTP', 'RECEIVE_FILE_ENCODING',   '接收文件编码',     'UTF-8',      ''                                   from dual
UNION ALL SELECT 188, 'HTTP', 'QUERY_FILE_TYPE',         '发送文件类型',     'xml',        '发送文件内容时使用；值为xml或json'         from dual
UNION ALL SELECT 189, 'HTTP', 'RECEIVE_FILE_TYPE',       '接收文件类型',     'xml',        '发送文件内容时使用；值为xml或json'         from dual
--
UNION ALL SELECT 200, 'DATABSE', 'DRIVER',                  '数据库驱动类名',    '',          ''      from dual
UNION ALL SELECT 201, 'DATABSE', 'SERVER_URL',              '数据库连接字符串',   '',         ''       from dual
UNION ALL SELECT 202, 'DATABSE', 'USER_NAME',               '访问用户名',        '',         ''      from dual
UNION ALL SELECT 203, 'DATABSE', 'PASSWORD',                '访问密码',         '',          ''      from dual
UNION ALL SELECT 204, 'DATABSE', 'REQUEST_TIMEOUT',         '请求超时时间',      '300000',   '5分钟（访问卡住时长）'  from dual
--
UNION ALL SELECT 220, 'XML', 'QUERY_ELEMENT_ROOT',               '生成XML根标签名',         'ESB',           ''                           from dual
UNION ALL SELECT 221, 'XML', 'QUERY_ELEMENT_ROOT_ATTR',          '生成XML根标签属性',        '',              '多值以;分开'                  from dual
UNION ALL SELECT 223, 'XML', 'IS_INDENT',                        '生成XML缩进',             '0',             '0：不缩进；1：缩进'            from dual
UNION ALL SELECT 224, 'XML', 'GENERATE_EMPTY',                   '是否生成空数据标签',        '0',             '0：不生成；1：生成'            from dual
UNION ALL SELECT 225, 'XML', 'QUERY_FILE_ENCODING',              '生成XML文件编码',          'UTF-8',         ''                         from dual
UNION ALL SELECT 226, 'XML', 'PARSE_MORE_VALUE_MAX_LENGTH',      '解析多值时数据最大长度',     '255',           '0：不限制'                   from dual
UNION ALL SELECT 227, 'XML', 'PARSE_CHILD_VALUE_ELEMENT_DEEP',   '值标签解析子标签深度',       '1',             '0：不解析子标签'              from dual
UNION ALL SELECT 228, 'XML', 'ELEMENT_NAME_SEPARATOR',           '解析标签名分隔符',          ':',             '截取最后一个分隔符后的字符串'     from dual
UNION ALL SELECT 229, 'XML', 'ELEMENT_ATTR_MERGER',              '解析属性合并符',            '%',             '对方字段名称为“标签名+合并符+属性”'  from dual
UNION ALL SELECT 230, 'XML', 'ENABLE_PARSE_ELEMENT_ATTR',        '是否解析标签属性',           '0',             '0：不解析；1：解析'          from dual
UNION ALL SELECT 231, 'XML', 'FILE_CONFIGURATOR',                'XML解析配置类名',           '',              ''                        from dual
UNION ALL SELECT 232, 'XML', 'THIRD_IF_CODE_REGEX',              'XML解析获取对方接口代码正则表达式',     '<([^\?!][^>]+)>',      '已确定对方对方接口代码或接口代码时不配置'  from dual
UNION ALL SELECT 233, 'XML', 'RECEIVE_FILE_ENCODING',            '接收XML文件编码',           'UTF-8',      ''                           from dual
--
UNION ALL SELECT 241, 'JSON', 'IS_INDENT',                        '生成JSON缩进',            '0',            '0：不缩进；1：缩进'            from dual
UNION ALL SELECT 242, 'JSON', 'GENERATE_EMPTY',                   '是否生成空数据标签',        '0',             '0：不生成；1：生成'            from dual
UNION ALL SELECT 243, 'JSON', 'QUERY_FILE_ENCODING',              '生成JSON文件编码',         'UTF-8',         ''                         from dual
UNION ALL SELECT 244, 'JSON', 'PARSE_MORE_VALUE_MAX_LENGTH',      '解析多值时数据最大长度',     '255',           '0：不限制'                   from dual
UNION ALL SELECT 245, 'JSON', 'PARSE_CHILD_VALUE_ELEMENT_DEEP',   '值标签解析子标签深度',       '1',             '0：不解析子标签'              from dual
UNION ALL SELECT 246, 'JSON', 'ELEMENT_NAME_SEPARATOR',           '解析标签名分隔符',          ':',             '截取最后一个分隔符后的字符串'     from dual
UNION ALL SELECT 247, 'JSON', 'FILE_CONFIGURATOR',                'JSON解析配置类名',         '',              ''                        from dual
UNION ALL SELECT 248, 'JSON', 'THIRD_IF_CODE_REGEX',              'JSON解析获取对方接口代码正则表达式',     '',      '已确定对方对方接口代码或接口代码时不配置'  from dual
UNION ALL SELECT 249, 'JSON', 'RECEIVE_FILE_ENCODING',            '接收JSON文件编码',         'UTF-8',      ''                           from dual
--
UNION ALL SELECT 261, 'CONTINUE_SEND', 'CONTINUOUS_SEND',                  '有发送数据时连续发送',       '0',            '0：不连续；1：连续'            from dual
UNION ALL SELECT 262, 'CONTINUE_SEND', 'CONTINUOUS_RECEIVE',               '有接收数据时连续发送',       '0',             '0：不连续；1：连续'            from dual
UNION ALL SELECT 263, 'CONTINUE_SEND', 'CONTINUOUS_MSTIME',                '连续发送时间间隔（毫秒）',    '',              '要求填写数字'                         from dual
--
UNION ALL SELECT 267, 'CONTINUE_RECEIVE', 'CONTINUOUS_RECEIVE',               '有接收数据时连续接收',       '0',             '0：不连续；1：连续'            from dual
UNION ALL SELECT 268, 'CONTINUE_RECEIVE', 'CONTINUOUS_MSTIME',                '连续接收时间间隔（毫秒）',    '',              '要求填写数字'                         from dual
--
UNION ALL SELECT 270, 'NO_STATUS_SEND', 'XXX_START_ROW',                    '发送配置代码XXX的查询开始行',  '0',            '必须要配置，否则一直发送前固定行数据'            from dual
UNION ALL SELECT 271, 'NO_STATUS_SEND', 'CONTINUOUS_SEND',                  '有发送数据时连续发送',        '0',            '0：不连续；1：连续'            from dual
UNION ALL SELECT 272, 'NO_STATUS_SEND', 'CONTINUOUS_RECEIVE',               '有接收数据时连续发送',        '0',            '0：不连续；1：连续'            from dual
UNION ALL SELECT 273, 'NO_STATUS_SEND', 'CONTINUOUS_MSTIME',                '连续发送时间间隔（毫秒）',     '',             '要求填写数字'                         from dual
--
UNION ALL SELECT 280, 'CHILD_IF_SEND', 'CHILD_IF_CODE',                     '子发送接口代码',          '',            ''            from dual
UNION ALL SELECT 281, 'CHILD_IF_SEND', 'CHILD_RUN_MASTER_QUERY_TABLE_ID',   '子发送接口的发送配置代码',  '',            '多个时以;分开'  from dual
--
UNION ALL SELECT 290, 'COMMON', 'STORAGE_PROCESS_LOG',              '保存过程日志',                   '1',            '1：保存；0：不保存'            from dual
UNION ALL SELECT 291, 'COMMON', 'HANDLER_CLASS',                    '接口拦截类',                     '',             ''                          from dual
UNION ALL SELECT 292, 'COMMON', 'SUPPORT_SCROLL_QUERY',             '本方JDBC是否支持滚动查询',         '1',           '1：支持；0：不支持'            from dual
UNION ALL SELECT 293, 'COMMON', 'EVERY_QUERY_MAX_RESULTS',          '不支持滚动查询时的每页查询行数',     '100',          '0：不限制'                         from dual
UNION ALL SELECT 294, 'COMMON', 'ENABLE_CACHE',                     '是否启用缓存功能',                '1',           '1：启用；0：不启用。不启用只是不把发送接收数据保存到本地文件数据库'            from dual
UNION ALL SELECT 295, 'COMMON', 'BACK_SUCCESS_RECEIVE',             '接收时是否返回接收成功的数据',       '0',           '0：不返回；1：返回'                         from dual
UNION ALL SELECT 296, 'COMMON', 'FILE_HAND_QUERY',                  '生成手动调用数据文件',             '1',            '1：生成；0：不生成'                       from dual
UNION ALL SELECT 297, 'COMMON', 'FILE_ORIG_QUERY',                  '生成原始发送数据文件',             '1',            '1：生成；0：不生成'                       from dual
UNION ALL SELECT 298, 'COMMON', 'FILE_THIRD_QUERY',                 '生成转换发送数据文件',             '1',            '1：生成；0：不生成（对发送文件的服务类型无效）'            from dual
UNION ALL SELECT 299, 'COMMON', 'FILE_THIRD_REICEIVE',              '生成接收数据文件',                '1',            '1：生成；0：不生成'                       from dual
; 
COMMIT; 
 
 
 
 
 
 
 
       