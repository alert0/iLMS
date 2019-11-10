--函数名  : dbo.USP_ARCHIVE
--功能描述: 日志归档
--输入参数说明
--       AC_MAX_TIME 保留最大时间（不包括）
--输出参数说明：
--       ERROR_TAG：返回代码 0：成功，其他：失败
--       ERROR_MESSAGE:返回信息,
--创建人员: lym
--创建时间: 2018-03-14
--****************************************************************************
CREATE PROCEDURE [dbo].[USP_ARCHIVE](@AC_MAX_TIME        DATETIME,
                                     @ERROR_TAG          NUMERIC(1)    OUTPUT,
                                     @ERROR_MESSAGE      VARCHAR(2000) OUTPUT)
AS           
BEGIN

  DECLARE @C_MESSAGE_MAX_LENGTH   INT = 1000;
  DECLARE @VAR_ERROR_TAG          NUMERIC(1);
  DECLARE @VAR_ERROR_MESSAGE      VARCHAR(2000); 
  
  BEGIN TRY
  
  --设置默认返回值
  SET @ERROR_TAG = 0;
  SET @ERROR_MESSAGE = '';

  --接收查询/发送日志归档
  EXECUTE [dbo].[USP_ARCHIVE_QUERY] @AC_MAX_TIME, @VAR_ERROR_TAG OUTPUT,  @VAR_ERROR_MESSAGE OUTPUT;
  IF 0 <> @VAR_ERROR_TAG 
  BEGIN
    SET @ERROR_TAG = 1;
    SET @ERROR_MESSAGE = @VAR_ERROR_MESSAGE + ';';
  END;
  
  --接收日志归档
  EXECUTE [dbo].[USP_ARCHIVE_RECEIVE] @AC_MAX_TIME, @VAR_ERROR_TAG OUTPUT, @VAR_ERROR_MESSAGE OUTPUT;
  IF 0 <> @VAR_ERROR_TAG 
  BEGIN
    SET @ERROR_TAG = 1;
    SET @ERROR_MESSAGE = @ERROR_MESSAGE + @VAR_ERROR_MESSAGE + ';';
  END;
  
  --截断超出字符
  IF DATALENGTH(@ERROR_MESSAGE) > @C_MESSAGE_MAX_LENGTH
  BEGIN
    SET @ERROR_MESSAGE = LEFT(@ERROR_MESSAGE, @C_MESSAGE_MAX_LENGTH);
  END;
  
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