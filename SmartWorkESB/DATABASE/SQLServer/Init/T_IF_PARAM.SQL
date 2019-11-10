
--普通参数
DELETE FROM T_IF_PARAM WHERE TO_SYS_NAME IS NULL AND IF_CODE IS NULL;
INSERT INTO T_IF_PARAM(PK_ID, PARAM_GROUP, PARAM_CODE, PARAM_NAME, PARAM_VALUE, EDIT_ABLE, LAST_UPDATE_TIME, PARAM_DESC)
          SELECT 1,   'STD', 'STORAGE_FOLDER','文件存储路径',    'D:/XML/',        0, GETDATE(), '禁止设置到系统盘'
UNION ALL SELECT 100, 'STD', 'WORK_CALENDAR', '工作日历',       '',               1, GETDATE(), ''
--
--          
UNION ALL SELECT 2, 'STD', 'THIRD_IF_CODE_REGEX', '从接收文件提取对方接口代码的正则表达式',    '<([^\?!][^>]+)>', 1, GETDATE(), '' 
--
UNION ALL SELECT 3, 'STD', 'ACTION_VALID_KEY', '其他系统http调用验证码',    'SmartWork@123', 1, GETDATE(), '' 
--
UNION ALL SELECT 4, 'STD', 'STORAGE_PROCESS_LOG', '保存过程日志',    '1', 1, GETDATE(), '1:保存；0:不保存'
--
UNION ALL SELECT 5, 'STD', 'DEFUALT_USER_PASSWORD', '登录用户默认密码',    'esb', 1, GETDATE(), ''
--
UNION ALL SELECT 10, 'CHARSET', 'STRING_CHARSET_NAME',      '本方字符串长度判断编码',   'UTF-8',  1, GETDATE(), '' 
UNION ALL SELECT 11, 'CHARSET', 'THIRD_STRING_CHARSET_NAME','对方字符串长度判断编码',   'UTF-8',  1, GETDATE(), '' 
--
--
UNION ALL SELECT 20, 'TIME_ZONE', 'TIME_ZONE',    '当前时区',             'GMT+08:00',  1, GETDATE(), '' 
UNION ALL SELECT 21, 'TIME_ZONE', 'TIME_ZONE_U',  '当前时区与GMT时差（小时）', '8.0',        1, GETDATE(), ''
--
--表字段默认值
UNION ALL SELECT 30, 'DEFAULT_COL_VALUE', 'COL_SITE_NUM',       '表SITE_NUM字段默认值',        '130000027', 1, GETDATE(), '要求填写数字'
UNION ALL SELECT 31, 'DEFAULT_COL_VALUE', 'COL_ATR_NAME',       '表ATR_NAME字段默认值',        '',          1, GETDATE(), '为空时默认32位UUID'
UNION ALL SELECT 32, 'DEFAULT_COL_VALUE', 'COL_PURGE_STATUS',   '表PURGE_STATUS字段默认值',    '0',         1, GETDATE(), '要求填写数字'
UNION ALL SELECT 33, 'DEFAULT_COL_VALUE', 'COL_XFR_INSERT_PID', '表XFR_INSERT_PID字段默认值',  '0',         1, GETDATE(), '要求填写数字'
UNION ALL SELECT 34, 'DEFAULT_COL_VALUE', 'COL_XFR_UPDATE_PID', '表XFR_UPDATE_PID字段默认值',  '0',         1, GETDATE(), '要求填写数字'
UNION ALL SELECT 35, 'DEFAULT_COL_VALUE', 'COL_TRX_ID',         '表TRX_ID字段默认值',          '',          1, GETDATE(), '为空时默认32位UUID'
--
--
;
