
--函数名  : dbo.USP_ARCHIVE_QUERY
--功能描述: 接收查询/发送日志归档
--输入参数说明
--       AC_MAX_TIME 保留最大时间（不包括）
--输出参数说明：
--       ERROR_TAG：返回代码 0：成功，其他：失败
--       ERROR_MESSAGE:返回信息,
--创建人员: lym
--创建时间: 2018-03-14
--****************************************************************************
CREATE PROCEDURE [dbo].[USP_ARCHIVE_QUERY](@AC_MAX_TIME        DATETIME,
                                           @ERROR_TAG          NUMERIC(1)    OUTPUT,
                                           @ERROR_MESSAGE      VARCHAR(2000) OUTPUT)
AS
BEGIN
  DECLARE @C_MESSAGE_MAX_LENGTH   INT = 1000;
  DECLARE @C_ARCHIVE_MAX_COUNT    INT = 2000000;
  
  DECLARE @VAR_I                  INT;
  DECLARE @VAR_K                  INT;
  DECLARE @VAR_MIN_DATE           DATE; --归档最小日期
  DECLARE @VAR_AR_DIFFDAY         INT; --归档相差天数
  DECLARE @VAR_AR_START_STP       DATETIME; --归档当天开始时间戳
  DECLARE @VAR_AR_END_STP         DATETIME; --归档当天结束时间戳
  DECLARE @VAR_COUNT              INT; --数据量
  DECLARE @VAR_AR_DAYCNT          INT; --当天遍历数
  DECLARE @VAR_AR_DAYINC          FLOAT; --当天的时间递增量
  DECLARE @VAR_AR_STP             DATETIME; --归档时间戳

  BEGIN TRY
  
    --开始事务控制
    BEGIN TRANSACTION;
    
    --设置默认返回
    SET @ERROR_TAG = 0;
    SET @ERROR_MESSAGE = '';
    
    --获取未归档的最小日期、相差天数
    SELECT @VAR_MIN_DATE = MIN(T.CREATE_TIME) 
      FROM T_IF_QUERY_LOG  T WITH(NOLOCK)
      WHERE T.CREATE_TIME < @AC_MAX_TIME;
    SET @VAR_AR_DIFFDAY = DATEDIFF(DAY, @VAR_MIN_DATE, @AC_MAX_TIME);
    
    --遍历每一天
    IF @VAR_AR_DIFFDAY IS NOT NULL AND @VAR_AR_DIFFDAY > 0 
    BEGIN
    
      --迁移数据每一天的处理
      SET @VAR_I = 1;
      WHILE @VAR_I <= @VAR_AR_DIFFDAY 
      BEGIN
      
        --获取归档当天开始日期和结束日期
        SET @VAR_AR_START_STP = DATEADD(DAY, @VAR_I - 1, @VAR_MIN_DATE);
        SET @VAR_AR_END_STP = DATEADD(DAY, 1, @VAR_AR_START_STP);
        
        --获取当天数据量、当天最小时间戳、当天最大时间戳
        SELECT @VAR_COUNT        = COUNT(1),
               @VAR_AR_START_STP = MIN(T.CREATE_TIME),
               @VAR_AR_END_STP   = MAX(T.CREATE_TIME)
          FROM T_IF_QUERY_LOG T WITH(NOLOCK)
          WHERE T.CREATE_TIME >= @VAR_AR_START_STP
            AND T.CREATE_TIME <  @VAR_AR_END_STP;
        IF @VAR_COUNT > 0
        BEGIN
          
          --获取当天轮询次数和递增时间量
          SET @VAR_AR_DAYCNT = CEILING(@VAR_COUNT * 1.0 / @C_ARCHIVE_MAX_COUNT);
          SET @VAR_AR_DAYINC = DATEDIFF(SECOND, @VAR_AR_START_STP, @VAR_AR_END_STP) / @VAR_AR_DAYCNT;          
          
          --轮询当天次数，次数加1是防止递增运算结果小于当天最大时间戳
          SET @VAR_K = 1;
          WHILE @VAR_K <= (@VAR_AR_DAYCNT + 1) 
          BEGIN
          
            --获取归档时间戳（不能超过当天结束日期）
            SET @VAR_AR_STP = DATEADD(SECOND, CEILING(@VAR_AR_DAYINC * @VAR_K), @VAR_AR_START_STP);
            IF @VAR_AR_STP > @VAR_AR_END_STP
            BEGIN
              SET @VAR_AR_STP = @VAR_AR_END_STP;
            END
            
            --将数据迁移到历史表
            INSERT INTO T_IF_QUERY_LOG_HIS
              SELECT * FROM T_IF_QUERY_LOG T WITH(NOLOCK)
                WHERE T.CREATE_TIME <= @VAR_AR_STP;
            
            IF @@Rowcount > 0 --有数据插入历史表
            BEGIN
    
              --删除已迁移数据
              DELETE FROM T_IF_QUERY_LOG
                WHERE CREATE_TIME <= @VAR_AR_STP;
    
              COMMIT TRANSACTION;
              BEGIN TRANSACTION;
            END
            ELSE --没有数据插入历史表
            BEGIN
              ROLLBACK TRANSACTION;
              BEGIN TRANSACTION;
            END
            
            --自增
            SET @VAR_K= @VAR_K + 1;
          END
        END
        
        --自增
        SET @VAR_I = @VAR_I + 1;
      END
    END
    
    --最后提交事务 
    COMMIT TRANSACTION;
  END TRY
  BEGIN CATCH
  
    --事务回滚
    IF XACT_STATE() <> 0
    BEGIN
      ROLLBACK TRANSACTION;
    END
    
    SET @ERROR_TAG = 1;
    SET @ERROR_MESSAGE = LEFT(ERROR_MESSAGE(), @C_MESSAGE_MAX_LENGTH);
  END CATCH 
END;
