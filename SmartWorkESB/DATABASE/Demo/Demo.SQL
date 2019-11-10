--DS105：接口代码
--查询发送/查询日志
select * from MESIF.T_IF_QUERY_LOG T WHERE T.IF_CODE='DS105' order by T.CREATE_TIME DESC, PK_ID DESC;

--查询接收日志
select * from MESIF.T_IF_RECEIVE_LOG T WHERE T.IF_CODE='DS105' order by T.CREATE_TIME DESC, PK_ID DESC;

--查询接口俄批准
select * from MESIF.T_IF_PARAM T WHERE  T.IF_CODE='DS105' order by PK_ID DESC;

--查询发送主从表
select * from TMP_S_MASTER;
select * from TMP_S_DETAIL;

--重置发送主表发送状态
update TMP_S_MASTER t set t.IS_SEND = 0, t.FAILURE=0;

--查询查询主从表
select * from TMP_Q_MASTER;
select * from TMP_Q_DETAIL;

--查询接收数据
select * from TMP_R_MASTER T WHERE T.IF_CODE='DS105' order by PK_ID DESC;
select * from TMP_R_DETAIL T where exists(select 1 from TMP_R_MASTER a where a.pk_id=t.fk_id and a.if_code='DS105') order by PK_ID DESC;
select * from TMP_R_THIRD T WHERE T.IF_CODE='DS105' order by PK_ID DESC;
select * from TMP_R_BATCH T WHERE T.IF_CODE='DS105' order by PK_ID DESC;

