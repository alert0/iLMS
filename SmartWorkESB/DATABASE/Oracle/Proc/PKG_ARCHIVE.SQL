create or replace package PKG_ARCHIVE is

--函数名  : USP_ARCHIVE
--功能描述: 日志归档
--输入参数说明
--       AC_MAX_TIME 保留最大时间（不包括）
--输出参数说明：
--       ERROR_TAG：返回代码 0：成功，其他：失败
--       ERROR_MESSAGE:返回信息,
--创建人员: lym
--创建时间: 2018-03-14
--****************************************************************************
PROCEDURE USP_ARCHIVE(AC_MAX_TIME        IN  DATE,
                      ERROR_TAG          OUT NUMBER,
                      ERROR_MESSAGE      OUT VARCHAR2);


end PKG_ARCHIVE;
/
create or replace package body PKG_ARCHIVE is

  --消息的最大长度
  C_MESSAGE_MAX_LENGTH       NUMBER := 1000;
  
  --一次归档的数据量
  C_ARCHIVE_MAX_COUNT        NUMBER := 2000000;

--函数名  : USP_ARCHIVE_QUERY
--功能描述: 接收查询/发送日志归档
--输入参数说明
--       AC_MAX_TIME 保留最大时间（不包括）
--输出参数说明：
--       ERROR_TAG：返回代码 0：成功，其他：失败
--       ERROR_MESSAGE:返回信息,
--创建人员: lym
--创建时间: 2018-03-14
--****************************************************************************
PROCEDURE USP_ARCHIVE_QUERY(AC_MAX_TIME        IN  DATE,
                            ERROR_TAG          OUT NUMBER,
                            ERROR_MESSAGE      OUT VARCHAR2)
AS
  VAR_MIN_DATE      DATE; --归档最小日期
  VAR_AR_DIFFDAY    NUMBER; --归档相差天数
  VAR_AR_START_STP  DATE; --归档当天开始时间戳
  VAR_AR_END_STP    DATE; --归档当天结束时间戳
  VAR_COUNT         NUMBER; --数据量
  VAR_AR_DAYCNT     NUMBER; --当天遍历数
  VAR_AR_DAYINC     NUMBER; --当天的时间递增量
  VAR_AR_STP        DATE; --归档时间戳
BEGIN
  ERROR_TAG := 0;
  ERROR_MESSAGE := '';

  --获取未归档的最小日期、相差天数
  SELECT trunc(MIN(T.CREATE_TIME)) INTO VAR_MIN_DATE
    FROM T_IF_QUERY_LOG T
    WHERE T.CREATE_TIME < AC_MAX_TIME;
  VAR_AR_DIFFDAY := AC_MAX_TIME - VAR_MIN_DATE;
  
  --遍历每一天
  IF VAR_AR_DIFFDAY IS NOT NULL AND VAR_AR_DIFFDAY > 0 THEN

    --迁移数据每一天的处理
    FOR I IN 1..VAR_AR_DIFFDAY LOOP

      --获取归档当天开始日期和结束日期
      VAR_AR_START_STP := VAR_MIN_DATE + (I - 1);
      VAR_AR_END_STP := VAR_AR_START_STP + 1;

      --获取当天数据量、当天最小时间戳、当天最大时间戳
      SELECT COUNT(1),
             MIN(T.CREATE_TIME),
             MAX(T.CREATE_TIME)
        INTO VAR_COUNT,
             VAR_AR_START_STP,
             VAR_AR_END_STP
        FROM T_IF_QUERY_LOG T
        WHERE T.CREATE_TIME >= VAR_AR_START_STP
          AND T.CREATE_TIME <  VAR_AR_END_STP;
      IF VAR_COUNT > 0 THEN

        --获取当天轮询次数和递增时间量
        VAR_AR_DAYCNT := CEIL(VAR_COUNT * 1.0 / C_ARCHIVE_MAX_COUNT);
        VAR_AR_DAYINC := (VAR_AR_END_STP - VAR_AR_START_STP) / VAR_AR_DAYCNT;

        --轮询当天次数，次数加1是防止递增运算结果小于当天最大时间戳
        FOR K IN 1..(VAR_AR_DAYCNT + 1) LOOP

          --获取归档时间戳（不能超过当天结束日期）
          VAR_AR_STP := LEAST(VAR_AR_END_STP, VAR_AR_START_STP + VAR_AR_DAYINC * K);

          --将数据迁移到历史表
          INSERT INTO T_IF_QUERY_LOG_HIS
            SELECT * FROM T_IF_QUERY_LOG T
              WHERE T.CREATE_TIME <= VAR_AR_STP;

          IF SQL%ROWCOUNT > 0 THEN --有数据插入历史表

            --删除已迁移数据
            DELETE FROM T_IF_QUERY_LOG T
              WHERE T.CREATE_TIME <= VAR_AR_STP;

            COMMIT;
          ELSE --没有数据插入历史表
            ROLLBACK;
          END IF;
        END LOOP; --轮询当天次数
      END IF;--判断当前数据量
    END LOOP; --轮询相差天数
  END IF; --遍历每一天
EXCEPTION
WHEN OTHERS THEN
  ERROR_TAG := 1;
  ERROR_MESSAGE  := SUBSTRB (SQLERRM||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE(), 1, C_MESSAGE_MAX_LENGTH);
  ROLLBACK;

END USP_ARCHIVE_QUERY;

--函数名  : USP_ARCHIVE_RECEIVE
--功能描述: 接收日志归档
--输入参数说明
--       AC_MAX_TIME 保留最大时间（不包括）
--输出参数说明：
--       ERROR_TAG：返回代码 0：成功，其他：失败
--       ERROR_MESSAGE:返回信息,
--创建人员: lym
--创建时间: 2018-03-14
--****************************************************************************
PROCEDURE USP_ARCHIVE_RECEIVE(AC_MAX_TIME        IN  DATE,
                              ERROR_TAG          OUT NUMBER,
                              ERROR_MESSAGE      OUT VARCHAR2)
AS
  VAR_MIN_DATE      DATE; --归档最小日期
  VAR_AR_DIFFDAY    NUMBER; --归档相差天数
  VAR_AR_START_STP  DATE; --归档当天开始时间戳
  VAR_AR_END_STP    DATE; --归档当天结束时间戳
  VAR_COUNT         NUMBER; --数据量
  VAR_AR_DAYCNT     NUMBER; --当天遍历数
  VAR_AR_DAYINC     NUMBER; --当天的时间递增量
  VAR_AR_STP        DATE; --归档时间戳
BEGIN
  ERROR_TAG := 0;
  ERROR_MESSAGE := '';

  --获取未归档的最小日期、相差天数
  SELECT trunc(MIN(T.CREATE_TIME)) INTO VAR_MIN_DATE
    FROM T_IF_RECEIVE_LOG T
    WHERE T.CREATE_TIME < AC_MAX_TIME;
  VAR_AR_DIFFDAY := AC_MAX_TIME - VAR_MIN_DATE;
  
  --遍历每一天
  IF VAR_AR_DIFFDAY IS NOT NULL AND VAR_AR_DIFFDAY > 0 THEN

    --迁移数据每一天的处理
    FOR I IN 1..VAR_AR_DIFFDAY LOOP

      --获取归档当天开始日期和结束日期
      VAR_AR_START_STP := VAR_MIN_DATE + (I - 1);
      VAR_AR_END_STP := VAR_AR_START_STP + 1;

      --获取当天数据量、当天最小时间戳、当天最大时间戳
      SELECT COUNT(1),
             MIN(T.CREATE_TIME),
             MAX(T.CREATE_TIME)
        INTO VAR_COUNT,
             VAR_AR_START_STP,
             VAR_AR_END_STP
        FROM T_IF_RECEIVE_LOG T
        WHERE T.CREATE_TIME >= VAR_AR_START_STP
          AND T.CREATE_TIME <  VAR_AR_END_STP;
      IF VAR_COUNT > 0 THEN

        --获取当天轮询次数和递增时间量
        VAR_AR_DAYCNT := CEIL(VAR_COUNT * 1.0 / C_ARCHIVE_MAX_COUNT);
        VAR_AR_DAYINC := (VAR_AR_END_STP - VAR_AR_START_STP) / VAR_AR_DAYCNT;

        --轮询当天次数，次数加1是防止递增运算结果小于当天最大时间戳
        FOR K IN 1..(VAR_AR_DAYCNT + 1) LOOP

          --获取归档时间戳（不能超过当天结束日期）
          VAR_AR_STP := LEAST(VAR_AR_END_STP, VAR_AR_START_STP + VAR_AR_DAYINC * K);

          --将数据迁移到历史表
          INSERT INTO T_IF_RECEIVE_LOG_HIS
            SELECT * FROM T_IF_RECEIVE_LOG T
              WHERE T.CREATE_TIME <= VAR_AR_STP;

          IF SQL%ROWCOUNT > 0 THEN --有数据插入历史表

            --删除已迁移数据
            DELETE FROM T_IF_RECEIVE_LOG T
              WHERE T.CREATE_TIME <= VAR_AR_STP;

            COMMIT;
          ELSE --没有数据插入历史表
            ROLLBACK;
          END IF;
        END LOOP; --轮询当天次数
      END IF;--判断当前数据量
    END LOOP; --轮询相差天数
  END IF; --遍历每一天
EXCEPTION
WHEN OTHERS THEN
  ERROR_TAG := 1;
  ERROR_MESSAGE  := SUBSTRB (SQLERRM||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE(), 1, C_MESSAGE_MAX_LENGTH);
  ROLLBACK;
END USP_ARCHIVE_RECEIVE;

--函数名  : USP_ARCHIVE
--功能描述: 日志归档
--输入参数说明
--       AC_MAX_TIME 保留最大时间（不包括）
--输出参数说明：
--       ERROR_TAG：返回代码 0：成功，其他：失败
--       ERROR_MESSAGE:返回信息,
--创建人员: lym
--创建时间: 2018-03-14
--****************************************************************************
PROCEDURE USP_ARCHIVE(AC_MAX_TIME        IN  DATE,
                      ERROR_TAG          OUT NUMBER,
                      ERROR_MESSAGE      OUT VARCHAR2)
AS           
  VAR_ERROR_TAG      NUMBER;
  VAR_ERROR_MESSAGE  VARCHAR2(2000);   
BEGIN
  
  --设置默认返回值
  ERROR_TAG := 0;
  ERROR_MESSAGE := '';

  --接收查询/发送日志归档
  USP_ARCHIVE_QUERY(AC_MAX_TIME, VAR_ERROR_TAG,  VAR_ERROR_MESSAGE);
  IF 0 <> VAR_ERROR_TAG THEN
    ERROR_TAG := 1;
    ERROR_MESSAGE := VAR_ERROR_MESSAGE || ';';
  END IF;
  
  --接收日志归档
  USP_ARCHIVE_RECEIVE(AC_MAX_TIME, VAR_ERROR_TAG, VAR_ERROR_MESSAGE);
  IF 0 <> VAR_ERROR_TAG THEN
    ERROR_TAG := 1;
    ERROR_MESSAGE := ERROR_MESSAGE || VAR_ERROR_MESSAGE || ';';
  END IF;
  
  --截断超出字符
  IF length(ERROR_MESSAGE) > C_MESSAGE_MAX_LENGTH THEN
    ERROR_MESSAGE  := SUBSTRB (ERROR_MESSAGE, 1, C_MESSAGE_MAX_LENGTH);
  END IF;
  
EXCEPTION
WHEN OTHERS THEN
  ERROR_TAG := 1;
END USP_ARCHIVE;


end PKG_ARCHIVE;
/
