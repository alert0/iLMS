------------------------------------------------
-- Export file for user ILMSPORTAL@ILMSPORTAL --
-- Created by zhuol on 2019/11/10, 9:10:33 -----
------------------------------------------------

set define off
spool iLMS_PORTAL.log

prompt
prompt Creating table ACT_RE_PROCDEF
prompt =============================
prompt
create table ACT_RE_PROCDEF
(
  id_                     NVARCHAR2(64) not null,
  rev_                    INTEGER,
  category_               NVARCHAR2(255),
  name_                   NVARCHAR2(255),
  key_                    NVARCHAR2(255) not null,
  version_                INTEGER not null,
  deployment_id_          NVARCHAR2(64),
  resource_name_          NVARCHAR2(2000),
  dgrm_resource_name_     VARCHAR2(4000),
  description_            NVARCHAR2(2000),
  has_start_form_key_     NUMBER(1),
  has_graphical_notation_ NUMBER(1),
  suspension_state_       INTEGER,
  tenant_id_              NVARCHAR2(255) default ''
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ACT_RE_PROCDEF
  add primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table ACT_RE_PROCDEF
  add constraint ACT_UNIQ_PROCDEF unique (KEY_, VERSION_, TENANT_ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table ACT_RE_PROCDEF
  add check (HAS_START_FORM_KEY_ IN (1,0));
alter table ACT_RE_PROCDEF
  add check (HAS_GRAPHICAL_NOTATION_ IN (1,0));

prompt
prompt Creating table BASE_REL_RESOURCES
prompt =================================
prompt
create table BASE_REL_RESOURCES
(
  id_      VARCHAR2(50) not null,
  res_id_  VARCHAR2(50),
  name_    VARCHAR2(50),
  res_url_ VARCHAR2(100)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table BASE_REL_RESOURCES
  is '关联资源';
comment on column BASE_REL_RESOURCES.id_
  is '主键';
comment on column BASE_REL_RESOURCES.res_id_
  is '资源ID';
comment on column BASE_REL_RESOURCES.name_
  is '名称';
comment on column BASE_REL_RESOURCES.res_url_
  is '资源地址';
alter table BASE_REL_RESOURCES
  add constraint PK_BASE_REL_RESOURCES primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table BASE_RES_ROLE
prompt ============================
prompt
create table BASE_RES_ROLE
(
  id_        VARCHAR2(50) not null,
  system_id_ VARCHAR2(50) not null,
  res_id_    VARCHAR2(50) not null,
  role_id_   VARCHAR2(50) not null
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table BASE_RES_ROLE
  is '角色资源分配';
comment on column BASE_RES_ROLE.id_
  is '主键';
comment on column BASE_RES_ROLE.system_id_
  is '系统ID';
comment on column BASE_RES_ROLE.res_id_
  is '资源ID';
comment on column BASE_RES_ROLE.role_id_
  is '角色ID';
alter table BASE_RES_ROLE
  add constraint PK_BASE_RES_ROLE primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BASE_SUBSYSTEM
prompt =============================
prompt
create table BASE_SUBSYSTEM
(
  id_          VARCHAR2(50) not null,
  name_        VARCHAR2(100),
  alias_       VARCHAR2(50),
  logo_        VARCHAR2(100),
  enabled_     NUMBER,
  home_url_    VARCHAR2(100),
  base_url_    VARCHAR2(100),
  tenant_      VARCHAR2(50),
  memo_        VARCHAR2(200),
  creator_id_  VARCHAR2(50),
  creator_     VARCHAR2(50),
  create_time_ TIMESTAMP(6),
  is_default_  NUMBER
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table BASE_SUBSYSTEM
  is '子系统定义';
comment on column BASE_SUBSYSTEM.id_
  is '主键';
comment on column BASE_SUBSYSTEM.name_
  is '系统名称';
comment on column BASE_SUBSYSTEM.alias_
  is '系统别名';
comment on column BASE_SUBSYSTEM.logo_
  is 'LOGO';
comment on column BASE_SUBSYSTEM.enabled_
  is '是否可用 1 可用，0 ，不可用';
comment on column BASE_SUBSYSTEM.home_url_
  is '主页地址';
comment on column BASE_SUBSYSTEM.base_url_
  is '基础地址';
comment on column BASE_SUBSYSTEM.tenant_
  is '租户名称';
comment on column BASE_SUBSYSTEM.memo_
  is '备注';
comment on column BASE_SUBSYSTEM.creator_id_
  is '创建人ID';
comment on column BASE_SUBSYSTEM.creator_
  is '创建人';
comment on column BASE_SUBSYSTEM.create_time_
  is '创建时间';
comment on column BASE_SUBSYSTEM.is_default_
  is '默认子系统';
alter table BASE_SUBSYSTEM
  add constraint PK_BASE_SUBSYSTEM primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BASE_SYS_RESOURCE
prompt ================================
prompt
create table BASE_SYS_RESOURCE
(
  id_           VARCHAR2(50) not null,
  system_id_    VARCHAR2(50),
  alias_        VARCHAR2(50),
  name_         VARCHAR2(100),
  default_url_  VARCHAR2(100),
  enable_menu_  NUMBER,
  has_children_ NUMBER,
  opened_       NUMBER,
  icon_         VARCHAR2(100),
  new_window_   NUMBER,
  sn_           NUMBER,
  parent_id_    VARCHAR2(50),
  create_time_  TIMESTAMP(6),
  sys_res_type_ VARCHAR2(32)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table BASE_SYS_RESOURCE
  is '子系统资源';
comment on column BASE_SYS_RESOURCE.id_
  is '主键';
comment on column BASE_SYS_RESOURCE.system_id_
  is '子系统ID';
comment on column BASE_SYS_RESOURCE.alias_
  is '资源别名';
comment on column BASE_SYS_RESOURCE.name_
  is '资源名';
comment on column BASE_SYS_RESOURCE.default_url_
  is '资源URL';
comment on column BASE_SYS_RESOURCE.enable_menu_
  is '显示到菜单(1,显示,0 ,不显示)';
comment on column BASE_SYS_RESOURCE.has_children_
  is '是否有子节点';
comment on column BASE_SYS_RESOURCE.opened_
  is '默认展开';
comment on column BASE_SYS_RESOURCE.icon_
  is '图标';
comment on column BASE_SYS_RESOURCE.new_window_
  is '打开新窗口';
comment on column BASE_SYS_RESOURCE.sn_
  is '排序';
comment on column BASE_SYS_RESOURCE.parent_id_
  is '父节点ID';
comment on column BASE_SYS_RESOURCE.create_time_
  is '创建时间';
comment on column BASE_SYS_RESOURCE.sys_res_type_
  is '资源类型';
alter table BASE_SYS_RESOURCE
  add constraint PK_BASE_SYS_RESOURCE primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CUSTOM_DIALOG
prompt ============================
prompt
create table CUSTOM_DIALOG
(
  id_             VARCHAR2(64) not null,
  name_           VARCHAR2(64) not null,
  alias_          VARCHAR2(64) not null,
  style_          NUMBER,
  obj_name_       VARCHAR2(64) not null,
  need_page_      NUMBER,
  page_size_      NUMBER,
  displayfield_   CLOB,
  conditionfield_ CLOB,
  resultfield_    CLOB,
  sortfield_      VARCHAR2(200),
  dsalias_        VARCHAR2(64) not null,
  is_table_       NUMBER not null,
  diy_sql_        VARCHAR2(510),
  sql_build_type_ NUMBER,
  width_          NUMBER,
  height_         NUMBER,
  select_num_     NUMBER,
  system_         NUMBER default 0,
  parent_check_   NUMBER,
  children_check_ NUMBER
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table CUSTOM_DIALOG
  is '自定义对话框';
comment on column CUSTOM_DIALOG.id_
  is '主键';
comment on column CUSTOM_DIALOG.name_
  is '名字';
comment on column CUSTOM_DIALOG.alias_
  is '别名';
comment on column CUSTOM_DIALOG.style_
  is '显示样式：0-列表，1-树形';
comment on column CUSTOM_DIALOG.obj_name_
  is '对象名称，如果是表就是表名，视图则视图名';
comment on column CUSTOM_DIALOG.need_page_
  is '是否分页';
comment on column CUSTOM_DIALOG.page_size_
  is '分页大小';
comment on column CUSTOM_DIALOG.displayfield_
  is '显示字段';
comment on column CUSTOM_DIALOG.conditionfield_
  is '条件字段的json';
comment on column CUSTOM_DIALOG.resultfield_
  is '返回字段json';
comment on column CUSTOM_DIALOG.sortfield_
  is '排序字段';
comment on column CUSTOM_DIALOG.dsalias_
  is '数据源的别名';
comment on column CUSTOM_DIALOG.is_table_
  is '是否数据库表0:视图,1:数据库表';
comment on column CUSTOM_DIALOG.diy_sql_
  is '自定义SQL';
comment on column CUSTOM_DIALOG.sql_build_type_
  is 'SQL构建类型';
comment on column CUSTOM_DIALOG.width_
  is '弹出框的宽度';
comment on column CUSTOM_DIALOG.height_
  is '弹出框的高度';
comment on column CUSTOM_DIALOG.select_num_
  is '是否单选 -1:多选';
comment on column CUSTOM_DIALOG.system_
  is '系统默认';
comment on column CUSTOM_DIALOG.parent_check_
  is '树多选父级级联';
comment on column CUSTOM_DIALOG.children_check_
  is '树多选子级级联';
alter table CUSTOM_DIALOG
  add constraint PK_CUSTOM_DIALOG primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table CUSTOM_QUERY
prompt ===========================
prompt
create table CUSTOM_QUERY
(
  id_             VARCHAR2(64) not null,
  name_           VARCHAR2(64) not null,
  alias_          VARCHAR2(64) not null,
  obj_name_       VARCHAR2(64) not null,
  need_page_      NUMBER,
  page_size_      NUMBER,
  conditionfield_ CLOB,
  resultfield_    CLOB,
  sortfield_      CLOB,
  dsalias_        VARCHAR2(64) not null,
  is_table_       NUMBER not null,
  diy_sql_        VARCHAR2(510),
  sql_build_type_ NUMBER
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table CUSTOM_QUERY
  is '自定义查询';
comment on column CUSTOM_QUERY.id_
  is '主键';
comment on column CUSTOM_QUERY.name_
  is '名字';
comment on column CUSTOM_QUERY.alias_
  is '别名';
comment on column CUSTOM_QUERY.obj_name_
  is '对象名称，如果是表就是表名，视图则视图名';
comment on column CUSTOM_QUERY.need_page_
  is '是否分页';
comment on column CUSTOM_QUERY.page_size_
  is '分页大小';
comment on column CUSTOM_QUERY.conditionfield_
  is '条件字段的json';
comment on column CUSTOM_QUERY.resultfield_
  is '返回字段json';
comment on column CUSTOM_QUERY.sortfield_
  is '排序字段';
comment on column CUSTOM_QUERY.dsalias_
  is '数据源的别名';
comment on column CUSTOM_QUERY.is_table_
  is '是否数据库表0:视图,1:数据库表';
comment on column CUSTOM_QUERY.diy_sql_
  is '自定义SQL';
comment on column CUSTOM_QUERY.sql_build_type_
  is 'SQL构建类型';
alter table CUSTOM_QUERY
  add constraint PK_CUSTOM_QUERY primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_ASSEMBLY_MOULD
prompt ================================
prompt
create table IF_ASSEMBLY_MOULD
(
  id            NUMBER(19) not null,
  assembly_name VARCHAR2(64),
  model_code    VARCHAR2(32),
  uda1          VARCHAR2(50),
  uda2          VARCHAR2(50),
  uda3          VARCHAR2(50),
  uda4          VARCHAR2(50),
  uda5          VARCHAR2(50),
  do_flag       VARCHAR2(2),
  deal_flag     NUMBER(1) default 0,
  deal_time     DATE,
  creation_time DATE default sysdate,
  guid          VARCHAR2(50),
  file_key      VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_ASSEMBLY_MOULD.do_flag
  is 'I-新增；U-修改；D-删除';
comment on column IF_ASSEMBLY_MOULD.deal_flag
  is '0 未处理 1已处理';
alter table IF_ASSEMBLY_MOULD
  add constraint PK_IF_ASSEMBLY_MOULD primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_ASSEMBLY_MOULD_CONFIG
prompt =======================================
prompt
create table IF_ASSEMBLY_MOULD_CONFIG
(
  id          NUMBER(19) not null,
  location    VARCHAR2(32),
  config_name VARCHAR2(64),
  area_code   VARCHAR2(80),
  uda1        VARCHAR2(50),
  uda2        VARCHAR2(50),
  uda3        VARCHAR2(50),
  uda4        VARCHAR2(50),
  uda5        VARCHAR2(50),
  deal_flag   NUMBER(1) default 0,
  deal_time   DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_ASSEMBLY_MOULD_CONFIG.area_code
  is '单据区域：10：头；20：明细';
comment on column IF_ASSEMBLY_MOULD_CONFIG.deal_flag
  is '0 未处理 1已处理';

prompt
prompt Creating table IF_FEATURE_GROUP
prompt ===============================
prompt
create table IF_FEATURE_GROUP
(
  id                 NUMBER(19) not null,
  feature_group      VARCHAR2(64) not null,
  feature_group_desc VARCHAR2(256),
  default_value      VARCHAR2(64),
  uda1               VARCHAR2(50),
  uda2               VARCHAR2(50),
  uda3               VARCHAR2(50),
  uda4               VARCHAR2(50),
  uda5               VARCHAR2(50),
  do_flag            VARCHAR2(2),
  deal_flag          NUMBER(1) default 0,
  deal_time          DATE,
  creation_time      DATE default sysdate,
  guid               VARCHAR2(50),
  file_key           VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_FEATURE_GROUP.do_flag
  is 'I-新增；U-修改；D-删除';
comment on column IF_FEATURE_GROUP.deal_flag
  is '0 未处理 1已处理';
alter table IF_FEATURE_GROUP
  add constraint PK_IF_FEATURE_GROUP primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_FEATURE_GROUP_DETAIL
prompt ======================================
prompt
create table IF_FEATURE_GROUP_DETAIL
(
  id             NUMBER(19) not null,
  feature_code1  VARCHAR2(32),
  feature_value1 VARCHAR2(32),
  feature_code2  VARCHAR2(32),
  feature_value2 VARCHAR2(32),
  feature_code3  VARCHAR2(32),
  feature_value3 VARCHAR2(32),
  feature_code4  VARCHAR2(32),
  feature_value4 VARCHAR2(32),
  display_value  VARCHAR2(64),
  uda1           VARCHAR2(50),
  uda2           VARCHAR2(50),
  uda3           VARCHAR2(50),
  uda4           VARCHAR2(50),
  uda5           VARCHAR2(50),
  deal_flag      NUMBER(1) default 0,
  deal_time      DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_FEATURE_GROUP_DETAIL.deal_flag
  is '0 未处理 1已处理';

prompt
prompt Creating table IF_INV_REC
prompt =========================
prompt
create table IF_INV_REC
(
  logistics_rec_order VARCHAR2(50) not null,
  comp                VARCHAR2(10),
  purchase_order_no   VARCHAR2(50),
  user_id             VARCHAR2(30),
  rec_date            DATE,
  uda1                VARCHAR2(50),
  uda2                VARCHAR2(50),
  uda3                VARCHAR2(50),
  uda4                VARCHAR2(50),
  uda5                VARCHAR2(50),
  deal_flag           NUMBER(1) default 0,
  deal_time           DATE,
  creation_time       DATE default sysdate
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_INV_REC.user_id
  is '工号';
comment on column IF_INV_REC.rec_date
  is '格式：yyyy-MM-dd HH:mm:ss';
comment on column IF_INV_REC.deal_flag
  is '0 未处理 1已处理 2失败';
comment on column IF_INV_REC.creation_time
  is '创建时间';
create index IDX_IF_INV_REC1 on IF_INV_REC (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_INV_REC
  add constraint PK_IF_INV_REC primary key (LOGISTICS_REC_ORDER)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_INV_REC_DETAIL
prompt ================================
prompt
create table IF_INV_REC_DETAIL
(
  logistics_rec_order    VARCHAR2(50) not null,
  rec_row_no             NUMBER(10) not null,
  logistics_order_row_no NUMBER(10),
  purchase_order_no      VARCHAR2(50),
  purchase_order_row_no  NUMBER(10),
  depot_no               VARCHAR2(20),
  part_id                VARCHAR2(30),
  rec_num                NUMBER(10),
  deal_flag              NUMBER(1),
  deal_time              DATE,
  uda1                   VARCHAR2(50),
  uda2                   VARCHAR2(50),
  uda3                   VARCHAR2(50),
  uda4                   VARCHAR2(50),
  uda5                   VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_INV_REC_DETAIL.logistics_order_row_no
  is '物流产生的订单行号';
comment on column IF_INV_REC_DETAIL.purchase_order_row_no
  is 'ERP订单行号，直接接收ERP单进行收货的订单';
comment on column IF_INV_REC_DETAIL.depot_no
  is '仓库主数据接口里的数据';
comment on column IF_INV_REC_DETAIL.deal_flag
  is '0 未处理 1已处理';
create index IDX_IF_INV_REC_DETAIL1 on IF_INV_REC_DETAIL (LOGISTICS_REC_ORDER)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_INV_TRAN_ERP
prompt ==============================
prompt
create table IF_INV_TRAN_ERP
(
  id          NUMBER(19) not null,
  business_no VARCHAR2(50) not null,
  comp        VARCHAR2(20) not null,
  tran_type   VARCHAR2(10),
  user_id     VARCHAR2(30),
  create_date VARCHAR2(30),
  uda1        VARCHAR2(50),
  uda2        VARCHAR2(50),
  uda3        VARCHAR2(50),
  uda4        VARCHAR2(50),
  uda5        VARCHAR2(50),
  deal_flag   NUMBER(1) default 0,
  deal_time   DATE,
  guid        VARCHAR2(50),
  file_key    VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_INV_TRAN_ERP.tran_type
  is '551：加工不良（报废）；201：例外出库；';
comment on column IF_INV_TRAN_ERP.user_id
  is '格式：yyyy-MM-dd HH:mm:ss';
comment on column IF_INV_TRAN_ERP.create_date
  is '格式：yyyy-MM-dd HH:mm:ss';
comment on column IF_INV_TRAN_ERP.deal_flag
  is '0 未处理 1已处理';
comment on column IF_INV_TRAN_ERP.guid
  is 'GUID';
comment on column IF_INV_TRAN_ERP.file_key
  is '文件标识';
create index IDX_IF_INV_TRAN_ERP1 on IF_INV_TRAN_ERP (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_INV_TRAN_ERP
  add constraint PK_IF_INV_TRAN_ERP primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_INV_TRAN_ERP_DETAIL
prompt =====================================
prompt
create table IF_INV_TRAN_ERP_DETAIL
(
  id              NUMBER(19) not null,
  business_no     VARCHAR2(50) not null,
  business_row_no VARCHAR2(10) not null,
  from_depot_no   VARCHAR2(20),
  to_depot_no     VARCHAR2(20),
  part_id         VARCHAR2(30),
  part_num        VARCHAR2(10),
  kostl           VARCHAR2(20),
  uda1            VARCHAR2(50),
  uda2            VARCHAR2(50),
  uda3            VARCHAR2(50),
  uda4            VARCHAR2(50),
  uda5            VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_INV_TRAN_ERP_DETAIL.from_depot_no
  is '仓库主数据接口里的数据';
comment on column IF_INV_TRAN_ERP_DETAIL.to_depot_no
  is '仓库主数据接口里的数据';
comment on column IF_INV_TRAN_ERP_DETAIL.part_num
  is '移库数量';
create index IDX_IF_INV_TRAN_ERP_DETAIL1 on IF_INV_TRAN_ERP_DETAIL (ID)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_INV_TRAN_LMS
prompt ==============================
prompt
create table IF_INV_TRAN_LMS
(
  business_no VARCHAR2(50) not null,
  comp        VARCHAR2(10) not null,
  tran_type   VARCHAR2(10),
  supplier_no VARCHAR2(10),
  user_id     VARCHAR2(30),
  create_date DATE,
  uda1        VARCHAR2(50),
  uda2        VARCHAR2(50),
  uda3        VARCHAR2(50),
  uda4        VARCHAR2(50),
  uda5        VARCHAR2(50),
  deal_flag   NUMBER(1) default 0,
  deal_time   DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_INV_TRAN_LMS.tran_type
  is '物流产生的订单行号';
comment on column IF_INV_TRAN_LMS.user_id
  is 'ERP订单行号，直接接收ERP单进行收货的订单';
comment on column IF_INV_TRAN_LMS.create_date
  is '仓库主数据接口里的数据';
comment on column IF_INV_TRAN_LMS.deal_flag
  is '0 未处理 1已处理 2失败';
create index IDX_IF_INV_TRAN_LMS1 on IF_INV_TRAN_LMS (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_INV_TRAN_LMS
  add constraint PK_IF_INV_TRAN_LMS primary key (BUSINESS_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_INV_TRAN_LMS_DETAIL
prompt =====================================
prompt
create table IF_INV_TRAN_LMS_DETAIL
(
  comp            VARCHAR2(10) not null,
  business_no     VARCHAR2(50) not null,
  business_row_no NUMBER(10) not null,
  from_depot_no   VARCHAR2(20),
  to_depot_no     VARCHAR2(20),
  part_id         VARCHAR2(30),
  part_num        VARCHAR2(10),
  cost_center     VARCHAR2(20),
  uda1            VARCHAR2(50),
  uda2            VARCHAR2(50),
  uda3            VARCHAR2(50),
  uda4            VARCHAR2(50),
  uda5            VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_INV_TRAN_LMS_DETAIL.from_depot_no
  is '仓库主数据接口里的数据';
comment on column IF_INV_TRAN_LMS_DETAIL.to_depot_no
  is '仓库主数据接口里的数据';
comment on column IF_INV_TRAN_LMS_DETAIL.part_num
  is '移库数量、不良数量、盘点差值绝对值';
create index IDX_IF_INV_TRAN_LMS_DETAIL on IF_INV_TRAN_LMS_DETAIL (BUSINESS_NO, COMP)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_INV_WAREHOUSE
prompt ===============================
prompt
create table IF_INV_WAREHOUSE
(
  id             NUMBER(19) not null,
  comp           VARCHAR2(10) not null,
  warehouse_id   VARCHAR2(20) not null,
  warehouse_name VARCHAR2(150),
  uda1           VARCHAR2(50),
  uda2           VARCHAR2(50),
  uda3           VARCHAR2(50),
  uda4           VARCHAR2(50),
  uda5           VARCHAR2(50),
  deal_flag      NUMBER(1) default 0,
  deal_time      DATE,
  creation_time  DATE default sysdate,
  guid           VARCHAR2(50),
  file_key       VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_INV_WAREHOUSE.deal_flag
  is '0 未处理 1已处理';
comment on column IF_INV_WAREHOUSE.guid
  is 'GUID';
comment on column IF_INV_WAREHOUSE.file_key
  is '文件标识';
create index IDX_IF_INV_WAREHOUSE1 on IF_INV_WAREHOUSE (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_INV_WAREHOUSE
  add constraint PK_IF_INV_WAREHOUSE primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_KEYPART_BIND
prompt ==============================
prompt
create table IF_KEYPART_BIND
(
  id            NUMBER(19) not null,
  factory_code  VARCHAR2(10) not null,
  order_no      VARCHAR2(50) not null,
  station_code  VARCHAR2(20) not null,
  part_bar_code VARCHAR2(150) not null,
  part_no       VARCHAR2(30),
  bind_num      VARCHAR2(10),
  vin           VARCHAR2(20),
  key_part_type VARCHAR2(10),
  uda1          VARCHAR2(50),
  uda2          VARCHAR2(50),
  uda3          VARCHAR2(50),
  uda4          VARCHAR2(50),
  uda5          VARCHAR2(50),
  deal_flag     NUMBER(1) default 0,
  deal_time     DATE,
  creation_time DATE default sysdate
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_KEYPART_BIND.part_bar_code
  is '批次条码和按件条码两种，需要区分开';
comment on column IF_KEYPART_BIND.bind_num
  is '按件绑定时候数量必须输；批次绑定可不输';
comment on column IF_KEYPART_BIND.key_part_type
  is '1-按件绑定；2-批次绑定';
comment on column IF_KEYPART_BIND.deal_flag
  is '0 未处理 1已处理';
create index IDX_IF_KEYPART_BIND1 on IF_KEYPART_BIND (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_KEYPART_BIND
  add constraint PK_IF_KEYPART_BIND primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_LOGISTICS_ORDER
prompt =================================
prompt
create table IF_LOGISTICS_ORDER
(
  logistics_orderno VARCHAR2(50) not null,
  comp              VARCHAR2(10) not null,
  purchase_orderno  VARCHAR2(50),
  order_type        VARCHAR2(10),
  supplier_id       VARCHAR2(20),
  factory_id        VARCHAR2(20),
  assembly_sup      VARCHAR2(20),
  orderissue_date   DATE,
  issue_user        VARCHAR2(30),
  uda1              VARCHAR2(50),
  uda2              VARCHAR2(50),
  uda3              VARCHAR2(50),
  uda4              VARCHAR2(50),
  uda5              VARCHAR2(50),
  deal_flag         NUMBER(1) default 0,
  deal_time         DATE,
  creation_time     DATE default sysdate,
  system_flag       NUMBER(1) default 1
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_LOGISTICS_ORDER.purchase_orderno
  is '资材订单用PDA扫描看板录入需求生成物流单号，资材订单无采购单号';
comment on column IF_LOGISTICS_ORDER.order_type
  is '01:零件订单；02:例外订单；03:拉动订单:；04:同步订单；05:试制件订单；14：资材订单；';
comment on column IF_LOGISTICS_ORDER.assembly_sup
  is '支给件订单，此字段为总成供应商代码；非支给件订单时，此字段为空';
comment on column IF_LOGISTICS_ORDER.orderissue_date
  is '订单创建日期';
comment on column IF_LOGISTICS_ORDER.issue_user
  is '工号';
comment on column IF_LOGISTICS_ORDER.deal_flag
  is '0 未处理 1已处理 2失败';
comment on column IF_LOGISTICS_ORDER.creation_time
  is '创建时间';
comment on column IF_LOGISTICS_ORDER.system_flag
  is '系统标识：默认为1';
create index IDX_IF_LOGISTICS_ORDER1 on IF_LOGISTICS_ORDER (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_LOGISTICS_ORDER
  add constraint PK_IF_LOGISTICS_ORDER primary key (LOGISTICS_ORDERNO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_LOGISTICS_ORDER_DETAIL
prompt ========================================
prompt
create table IF_LOGISTICS_ORDER_DETAIL
(
  logistics_orderno VARCHAR2(50) not null,
  logistics_rowno   VARCHAR2(10) not null,
  purchase_orderno  VARCHAR2(50),
  purchase_row_no   VARCHAR2(10),
  plan_arrive_time  DATE,
  order_depot       VARCHAR2(20),
  part_id           VARCHAR2(30),
  order_num         NUMBER(10),
  part_unit         VARCHAR2(20),
  assembly_flag     VARCHAR2(10),
  lgpbe             VARCHAR2(20),
  zkostl            VARCHAR2(10),
  zbzgg             VARCHAR2(10),
  zstock            VARCHAR2(10),
  uda1              VARCHAR2(50),
  uda2              VARCHAR2(50),
  uda3              VARCHAR2(50),
  uda4              VARCHAR2(50),
  uda5              VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_LOGISTICS_ORDER_DETAIL.purchase_orderno
  is '资材订单无采购单号';
comment on column IF_LOGISTICS_ORDER_DETAIL.plan_arrive_time
  is '资材订单没有计划到货日期';
comment on column IF_LOGISTICS_ORDER_DETAIL.order_depot
  is '仓库主数据接口里的数据';
comment on column IF_LOGISTICS_ORDER_DETAIL.assembly_flag
  is '0-普通件；1-支给总成件';
comment on column IF_LOGISTICS_ORDER_DETAIL.lgpbe
  is '资材扫描看板内容';
comment on column IF_LOGISTICS_ORDER_DETAIL.zkostl
  is '资材扫描看板内容';
comment on column IF_LOGISTICS_ORDER_DETAIL.zbzgg
  is '资材扫描看板内容';
comment on column IF_LOGISTICS_ORDER_DETAIL.zstock
  is '资材扫描看板内容';
create index IDX_IF_LOGISTICS_ORDER_DETAIL1 on IF_LOGISTICS_ORDER_DETAIL (LOGISTICS_ORDERNO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_MM_INV_PART_LOCATION
prompt ======================================
prompt
create table IF_MM_INV_PART_LOCATION
(
  id                 NUMBER(19) not null,
  factory_code       VARCHAR2(10),
  part_no            VARCHAR2(20),
  supplier_no        VARCHAR2(20),
  ware_code          VARCHAR2(20),
  station_code       VARCHAR2(20),
  unload_port        VARCHAR2(20),
  dep_no             VARCHAR2(20),
  prepare_person     VARCHAR2(20),
  carpool            VARCHAR2(20),
  distri_person      VARCHAR2(100),
  location           VARCHAR2(50),
  shelf_no           VARCHAR2(20),
  location_num       NUMBER(10),
  model_code         VARCHAR2(10),
  storage            VARCHAR2(100),
  eff_start          DATE,
  eff_end            DATE,
  creation_user      VARCHAR2(30),
  creation_time      DATE default SYSDATE,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE default SYSDATE,
  workcenter         VARCHAR2(20),
  deal_flag          NUMBER(1) default 0,
  deal_time          DATE,
  guid               VARCHAR2(50),
  uda1               VARCHAR2(50),
  uda2               VARCHAR2(50),
  uda3               VARCHAR2(50),
  uda4               VARCHAR2(50),
  uda5               VARCHAR2(50),
  do_flag            VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_MM_INV_PART_LOCATION.deal_flag
  is '0 未处理 1已处理';
comment on column IF_MM_INV_PART_LOCATION.guid
  is 'GUID';
comment on column IF_MM_INV_PART_LOCATION.do_flag
  is 'I-新增；U-修改；D-删除';

prompt
prompt Creating table IF_MM_PKG_BOX
prompt ============================
prompt
create table IF_MM_PKG_BOX
(
  id                 NUMBER(19) not null,
  box_code           VARCHAR2(30),
  box_type           NUMBER(2),
  pack_length        NUMBER(19,3),
  pack_width         NUMBER(19,3),
  pack_height        NUMBER(19,3),
  status             NUMBER(2) default 0,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  factory_code       VARCHAR2(10),
  do_flag            VARCHAR2(10),
  deal_flag          NUMBER(1) default 0,
  deal_time          DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_MM_PKG_BOX.box_code
  is '箱code';
comment on column IF_MM_PKG_BOX.box_type
  is '"1-台车
2-EU箱
3-中空板
4-铁架"';
comment on column IF_MM_PKG_BOX.pack_length
  is '包装长';
comment on column IF_MM_PKG_BOX.pack_width
  is '包装宽';
comment on column IF_MM_PKG_BOX.pack_height
  is '包装高';
comment on column IF_MM_PKG_BOX.status
  is '"0-停用
1-启用"';
comment on column IF_MM_PKG_BOX.do_flag
  is 'I-新增；U-修改；D-删除';
comment on column IF_MM_PKG_BOX.deal_flag
  is '0 未处理 1已处理';
create index IDX_IF_MM_PKG_BOX1 on IF_MM_PKG_BOX (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_MM_PKG_BOX
  add constraint PK_IF_MM_PKG_BOX primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_MM_PKG_BOX_QTY
prompt ================================
prompt
create table IF_MM_PKG_BOX_QTY
(
  id                 NUMBER(19) not null,
  proposal_id        NUMBER(19),
  provide_qty        NUMBER(10),
  box_require_qty    NUMBER(10),
  hair_note_model    VARCHAR2(50),
  plan_date          DATE,
  delay_reason       VARCHAR2(300),
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  factory_code       VARCHAR2(10),
  do_flag            VARCHAR2(10),
  deal_flag          NUMBER(1) default 0,
  deal_time          DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table IF_MM_PKG_BOX_QTY
  is 'IF_MM_PKG_BOX_QTY包装箱子数量管理表
IF_MM_PKG_BOX_QTY包装箱子数量管理表
';
comment on column IF_MM_PKG_BOX_QTY.id
  is 'ID
ID
';
comment on column IF_MM_PKG_BOX_QTY.proposal_id
  is '包装提案ID
包装提案ID
';
comment on column IF_MM_PKG_BOX_QTY.provide_qty
  is '已纳入量
已纳入量
已纳入量';
comment on column IF_MM_PKG_BOX_QTY.box_require_qty
  is '箱子需求量
箱子需求量
箱子需求量';
comment on column IF_MM_PKG_BOX_QTY.hair_note_model
  is '发注模式
发注模式
发注模式';
comment on column IF_MM_PKG_BOX_QTY.plan_date
  is '计划完成日期
计划完成日期
计划完成日期';
comment on column IF_MM_PKG_BOX_QTY.delay_reason
  is '延迟原因
延迟原因
延迟原因';
comment on column IF_MM_PKG_BOX_QTY.creation_user
  is '创建人
创建人
';
comment on column IF_MM_PKG_BOX_QTY.creation_time
  is '创建时间
创建时间
';
comment on column IF_MM_PKG_BOX_QTY.last_modified_user
  is '最后修改用户
最后修改用户
';
comment on column IF_MM_PKG_BOX_QTY.last_modified_time
  is '最后修改时间
最后修改时间
';
comment on column IF_MM_PKG_BOX_QTY.factory_code
  is '工厂代码
工厂代码
';
comment on column IF_MM_PKG_BOX_QTY.do_flag
  is '处理类型
处理类型
I-新增；U-修改；D-删除';
comment on column IF_MM_PKG_BOX_QTY.deal_flag
  is '处理标识
处理标识
0 未处理 1已处理';
comment on column IF_MM_PKG_BOX_QTY.deal_time
  is '处理时间
处理时间
';
create index IDX_IF_MM_PKG_BOX_QTY1 on IF_MM_PKG_BOX_QTY (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_MM_PKG_BOX_QTY
  add constraint PK_IF_MM_PKG_BOX_QTY primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_MM_PKG_PROPOSAL
prompt =================================
prompt
create table IF_MM_PKG_PROPOSAL
(
  id                 NUMBER(19) not null,
  car_type           VARCHAR2(20),
  supplier_no        VARCHAR2(20),
  part_no            VARCHAR2(20),
  proposal_status    NUMBER(1) default 0,
  pack_type          NUMBER(1),
  box_type           NUMBER(2),
  reply_limit_date   DATE,
  is_com_pack        NUMBER(1),
  status             NUMBER(1),
  sign_pro_file      NUMBER(19),
  eff_start          DATE,
  eff_end            DATE,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  factory_code       VARCHAR2(10),
  part_resp_user     VARCHAR2(30),
  project            VARCHAR2(50),
  purchase_type      VARCHAR2(10),
  is_show_change     NUMBER(1),
  com_pack_remark    VARCHAR2(100),
  do_flag            VARCHAR2(10),
  deal_flag          NUMBER(1) default 0,
  deal_time          DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table IF_MM_PKG_PROPOSAL
  is 'IF_MM_PKG_PROPOSAL包装提案表
IF_MM_PKG_PROPOSAL包装提案表
';
comment on column IF_MM_PKG_PROPOSAL.id
  is 'ID
ID
';
comment on column IF_MM_PKG_PROPOSAL.car_type
  is '车型
车型
车型';
comment on column IF_MM_PKG_PROPOSAL.supplier_no
  is '供应商代码
供应商代码
供应商代码';
comment on column IF_MM_PKG_PROPOSAL.part_no
  is '零件编号
零件编号
零件编号';
comment on column IF_MM_PKG_PROPOSAL.proposal_status
  is '提案状态
提案状态
0-待发起
1-发起提案
2-待审核
3-提案通过
4-提案不通过
5-实物通过
6-实物不通过
7-终止';
comment on column IF_MM_PKG_PROPOSAL.pack_type
  is '包装类型
包装类型
0-非台车
1-台车';
comment on column IF_MM_PKG_PROPOSAL.box_type
  is '箱种类型
箱种类型
0：托盘 1：台车 2：EU箱 3：其他';
comment on column IF_MM_PKG_PROPOSAL.reply_limit_date
  is '回复期限日期
回复期限日期
回复期限';
comment on column IF_MM_PKG_PROPOSAL.is_com_pack
  is '是否组合包装
是否组合包装
0-否
1-是';
comment on column IF_MM_PKG_PROPOSAL.status
  is '留用新设状态
留用新设状态
1-新设
2-留用';
comment on column IF_MM_PKG_PROPOSAL.sign_pro_file
  is '签字提案文件(FTP文件ID)
签字提案文件(FTP文件ID)
';
comment on column IF_MM_PKG_PROPOSAL.eff_start
  is '生效时间
生效时间
生效时间';
comment on column IF_MM_PKG_PROPOSAL.eff_end
  is '失效时间
失效时间
失效时间';
comment on column IF_MM_PKG_PROPOSAL.creation_user
  is '创建人
创建人
';
comment on column IF_MM_PKG_PROPOSAL.creation_time
  is '创建时间
创建时间
';
comment on column IF_MM_PKG_PROPOSAL.last_modified_user
  is '最后修改用户
最后修改用户
';
comment on column IF_MM_PKG_PROPOSAL.last_modified_time
  is '最后修改时间
最后修改时间
';
comment on column IF_MM_PKG_PROPOSAL.factory_code
  is '工厂代码
工厂代码
';
comment on column IF_MM_PKG_PROPOSAL.part_resp_user
  is '零件担当
零件担当
';
comment on column IF_MM_PKG_PROPOSAL.project
  is '工程
工程
';
comment on column IF_MM_PKG_PROPOSAL.purchase_type
  is '采购类型
采购类型
';
comment on column IF_MM_PKG_PROPOSAL.is_show_change
  is '是否显示在变更界面
是否显示在变更界面
';
comment on column IF_MM_PKG_PROPOSAL.com_pack_remark
  is '组合包装备注
组合包装备注
';
comment on column IF_MM_PKG_PROPOSAL.do_flag
  is '处理类型
处理类型
I-新增；U-修改；D-删除';
comment on column IF_MM_PKG_PROPOSAL.deal_flag
  is '处理标识
处理标识
0 未处理 1已处理';
comment on column IF_MM_PKG_PROPOSAL.deal_time
  is '处理时间
处理时间
';
create index IDX_IF_MM_PKG_PROPOSAL1 on IF_MM_PKG_PROPOSAL (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_MM_PKG_PROPOSAL
  add constraint PK_IF_MM_PKG_PROPOSAL primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_MM_PKG_PROPOSAL_DETAIL
prompt ========================================
prompt
create table IF_MM_PKG_PROPOSAL_DETAIL
(
  id                    NUMBER(19) not null,
  proposal_id           NUMBER(19),
  group_no              VARCHAR2(30),
  max_package_num       NUMBER(10),
  standard_package      NUMBER(10),
  part_weight           NUMBER(10,3),
  part_total_weight     NUMBER(10,3),
  pack_weight           NUMBER(10,3),
  part_length           NUMBER(10,3),
  part_width            NUMBER(10,3),
  part_height           NUMBER(10,3),
  empty_trolley_length  NUMBER(10,3),
  empty_trolley_width   NUMBER(10,3),
  empty_trolley_height  NUMBER(10,3),
  real_trolley_length   NUMBER(10,3),
  real_trolley_width    NUMBER(10,3),
  real_trolley_height   NUMBER(10,3),
  trolley_weight        NUMBER(10,3),
  total_weight          NUMBER(10,3),
  is_trolley_code       NUMBER(1),
  is_positioner         NUMBER(1),
  dust_cover            NUMBER(1),
  one_by_package        NUMBER(1),
  board_location        VARCHAR2(10),
  wheel_diameter        NUMBER(10,3),
  word_desc             VARCHAR2(600),
  traction_rod_height   NUMBER(10,3),
  important_postion_pic NUMBER(19),
  empty_tro_front_pic   VARCHAR2(50),
  empty_tro_side_pic    VARCHAR2(50),
  real_tro_pic          VARCHAR2(50),
  box_code              VARCHAR2(20),
  pack_length           NUMBER(10,3),
  pack_width            NUMBER(10,3),
  pack_height           NUMBER(10,3),
  tray_length           NUMBER(10,3),
  tray_width            NUMBER(10,3),
  tray_height           NUMBER(10,3),
  work_require          VARCHAR2(500),
  single_part_pic       NUMBER(19),
  single_part_put_pic   NUMBER(19),
  pack_over_look_pic    NUMBER(19),
  pack_side_look_pic    NUMBER(19),
  creation_user         VARCHAR2(30),
  creation_time         DATE default sysdate,
  last_modified_user    VARCHAR2(30),
  last_modified_time    DATE,
  factory_code          VARCHAR2(10),
  do_flag               VARCHAR2(10),
  deal_flag             NUMBER(1) default 0,
  deal_time             DATE,
  sup_name              VARCHAR2(30),
  mobile                VARCHAR2(30),
  mail                  VARCHAR2(30)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table IF_MM_PKG_PROPOSAL_DETAIL
  is 'IF_MM_PKG_PROPOSAL_DETAIL包装提案明细表
';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.id
  is '"ID
ID
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.proposal_id
  is '"包装提案ID
包装提案ID
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.group_no
  is '"分组
分组
分组"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.max_package_num
  is '"最大收容数
最大收容数
最大收容数"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.standard_package
  is '"零件收容数
零件收容数
零件收容数"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.part_weight
  is '"零件单重
零件单重
零件单重"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.part_total_weight
  is '"零件总重量
零件总重量
零件总重量"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.pack_weight
  is '"包装重量
包装重量
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.part_length
  is '"零件长
零件长
零件长"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.part_width
  is '"零件宽
零件宽
零件宽"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.part_height
  is '"零件高
零件高
零件高"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.empty_trolley_length
  is '"空台车长
空台车长
空台车长"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.empty_trolley_width
  is '"空台车宽
空台车宽
空台车宽"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.empty_trolley_height
  is '"空台车高
空台车高
空台车高"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.real_trolley_length
  is '"实台车长
实台车长
实台车长"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.real_trolley_width
  is '"实台车宽
实台车宽
实台车宽"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.real_trolley_height
  is '"实台车高
实台车高
实台车高"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.trolley_weight
  is '"台车重量
台车重量
台车重量"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.total_weight
  is '"总重量
总重量
总重量"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.is_trolley_code
  is '"有无台车编码
有无台车编码
0-无
1-有"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.is_positioner
  is '"有无定位器
有无定位器
0-无
1-有"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.dust_cover
  is '"有无防尘罩
有无防尘罩
0-无
1-有"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.one_by_package
  is '"有无逐个包装
有无逐个包装
0-无
1-有"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.board_location
  is '"看板位置
看板位置
L  / W"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.wheel_diameter
  is '"车轮直径
车轮直径
车轮直径"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.word_desc
  is '"文字说明
文字说明
文字说明"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.traction_rod_height
  is '"牵引棒距地高
牵引棒距地高
牵引棒距地高"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.important_postion_pic
  is '"重要部位图(FTP文件ID)
重要部位图(FTP文件ID)
重要部位图"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.empty_tro_front_pic
  is '"空台车正视图
空台车正视图
空台车正视图"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.empty_tro_side_pic
  is '"空台车侧视图
空台车侧视图
空台车侧视图"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.real_tro_pic
  is '"实台车图
实台车图
实台车图"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.box_code
  is '"箱种编码
箱种编码
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.pack_length
  is '"包装长
包装长
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.pack_width
  is '"包装宽
包装宽
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.pack_height
  is '"包装高
包装高
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.tray_length
  is '"托盘尺寸长
托盘尺寸长
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.tray_width
  is '"托盘尺寸宽
托盘尺寸宽
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.tray_height
  is '"托盘尺寸高
托盘尺寸高
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.work_require
  is '"作业要求
作业要求
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.single_part_pic
  is '"单一零件图(FTP文件ID)
单一零件图(FTP文件ID)
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.single_part_put_pic
  is '"单一零件摆放图
单一零件摆放图
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.pack_over_look_pic
  is '"包装俯视图
包装俯视图
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.pack_side_look_pic
  is '"包装侧视图
包装侧视图
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.creation_user
  is '"创建人
创建人
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.last_modified_user
  is '"最后修改用户
最后修改用户
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.last_modified_time
  is '"最后修改时间
最后修改时间
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.factory_code
  is '"工厂代码
工厂代码
"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.do_flag
  is '"处理类型
处理类型
I-新增；U-修改；D-删除"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.deal_flag
  is '"处理标识
处理标识
0 未处理 1已处理"';
comment on column IF_MM_PKG_PROPOSAL_DETAIL.deal_time
  is '"处理时间
处理时间
"';
create index IDX_IF_MM_PKG_PROPOSAL_DETAIL1 on IF_MM_PKG_PROPOSAL_DETAIL (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_MM_PKG_PROPOSAL_DETAIL
  add constraint PK_IF_MM_PKG_PROPOSAL_DETAIL primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_MM_PKG_TRAY_QTY
prompt =================================
prompt
create table IF_MM_PKG_TRAY_QTY
(
  id                 NUMBER(19) not null,
  car_type           VARCHAR2(20),
  supplier_no        VARCHAR2(20),
  provide_qty        NUMBER(10),
  tray_require_qty   NUMBER(10),
  plan_date          DATE,
  delay_reason       VARCHAR2(300),
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  factory_code       VARCHAR2(10),
  do_flag            VARCHAR2(10),
  deal_flag          NUMBER(1) default 0,
  deal_time          DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table IF_MM_PKG_TRAY_QTY
  is 'IF_MM_PKG_TRAY_QTY包装托盘数量管理表
IF_MM_PKG_TRAY_QTY包装托盘数量管理表
';
comment on column IF_MM_PKG_TRAY_QTY.id
  is 'ID
ID
';
comment on column IF_MM_PKG_TRAY_QTY.car_type
  is '车型
车型
车型';
comment on column IF_MM_PKG_TRAY_QTY.supplier_no
  is '供应商代码
供应商代码
供应商代码';
comment on column IF_MM_PKG_TRAY_QTY.provide_qty
  is '已纳入量
已纳入量
已纳入量';
comment on column IF_MM_PKG_TRAY_QTY.tray_require_qty
  is '托盘需求量
托盘需求量
箱子需求量';
comment on column IF_MM_PKG_TRAY_QTY.plan_date
  is '计划完成日期
计划完成日期
计划完成日期';
comment on column IF_MM_PKG_TRAY_QTY.delay_reason
  is '延迟原因
延迟原因
延迟原因';
comment on column IF_MM_PKG_TRAY_QTY.creation_user
  is '创建人
创建人
';
comment on column IF_MM_PKG_TRAY_QTY.creation_time
  is '创建时间
创建时间
';
comment on column IF_MM_PKG_TRAY_QTY.last_modified_user
  is '最后修改用户
最后修改用户
';
comment on column IF_MM_PKG_TRAY_QTY.last_modified_time
  is '最后修改时间
最后修改时间
';
comment on column IF_MM_PKG_TRAY_QTY.factory_code
  is '工厂代码
工厂代码
';
comment on column IF_MM_PKG_TRAY_QTY.do_flag
  is '处理类型
处理类型
I-新增；U-修改；D-删除';
comment on column IF_MM_PKG_TRAY_QTY.deal_flag
  is '处理标识
处理标识
0 未处理 1已处理';
comment on column IF_MM_PKG_TRAY_QTY.deal_time
  is '处理时间
处理时间
';
create index IDX_IF_MM_PKG_TRAY_QTY1 on IF_MM_PKG_TRAY_QTY (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_MM_PKG_TRAY_QTY
  add constraint PK_IF_MM_PKG_TRAY_QTY primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_MM_PUB_PART_UDA
prompt =================================
prompt
create table IF_MM_PUB_PART_UDA
(
  id                 NUMBER(19) not null,
  factory_code       VARCHAR2(10) not null,
  part_no            VARCHAR2(30) not null,
  supplier_no        VARCHAR2(10),
  sup_factory        VARCHAR2(10),
  part_short_no      VARCHAR2(10),
  part_name_cn       VARCHAR2(100),
  supplier_name      VARCHAR2(100),
  standard_package   NUMBER(10),
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  deal_flag          NUMBER(1) default 0,
  deal_time          DATE,
  guid               VARCHAR2(50),
  uda1               VARCHAR2(50),
  uda2               VARCHAR2(50),
  uda3               VARCHAR2(50),
  uda4               VARCHAR2(50),
  uda5               VARCHAR2(50),
  do_flag            VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_MM_PUB_PART_UDA.deal_flag
  is '0 未处理 1已处理';
comment on column IF_MM_PUB_PART_UDA.guid
  is 'GUID';
comment on column IF_MM_PUB_PART_UDA.do_flag
  is 'I-新增；U-修改；D-删除';

prompt
prompt Creating table IF_MM_PUB_SUPPLIER
prompt =================================
prompt
create table IF_MM_PUB_SUPPLIER
(
  id                        NUMBER(19) not null,
  sup_factory               VARCHAR2(10) not null,
  supplier_no               VARCHAR2(10),
  supplier_name             VARCHAR2(150),
  sup_factory_addr          VARCHAR2(200),
  detail_addr               VARCHAR2(200),
  sup_status                VARCHAR2(10),
  email                     VARCHAR2(50),
  contact                   VARCHAR2(30),
  mobile_no                 VARCHAR2(30),
  tel_no                    VARCHAR2(30),
  creation_time             DATE default sysdate,
  last_modified_time        DATE,
  blong_area                VARCHAR2(50),
  sup_leader_name           VARCHAR2(30),
  sup_leader_tel            VARCHAR2(30),
  sup_leader_mail           VARCHAR2(100),
  order_contact             VARCHAR2(30),
  order_tel                 VARCHAR2(30),
  order_mail                VARCHAR2(100),
  deliv_contact             VARCHAR2(30),
  delivery_tel              VARCHAR2(30),
  delivery_mail             VARCHAR2(100),
  excep_contact             VARCHAR2(30),
  excep_tel                 VARCHAR2(30),
  excep_mail                VARCHAR2(100),
  pack_dept_name            VARCHAR2(60),
  pack_dept_tel             VARCHAR2(30),
  pack_dept_mail            VARCHAR2(100),
  pack_dept_contact         VARCHAR2(30),
  pack_dept_chief           VARCHAR2(30),
  pack_dept_minister        VARCHAR2(30),
  import_dep                VARCHAR2(60),
  import_position           VARCHAR2(50),
  import_name               VARCHAR2(30),
  import_mobile             VARCHAR2(30),
  import_tel                VARCHAR2(30),
  import_mail               VARCHAR2(50),
  pt_dep                    VARCHAR2(60),
  pt_position               VARCHAR2(50),
  pt_name                   VARCHAR2(30),
  pt_mobile                 VARCHAR2(30),
  pt_tel                    VARCHAR2(30),
  pt_mail                   VARCHAR2(50),
  mass_dep                  VARCHAR2(60),
  mass_position             VARCHAR2(50),
  mass_name                 VARCHAR2(30),
  mass_mobile               VARCHAR2(30),
  mass_tel                  VARCHAR2(30),
  mass_mail                 VARCHAR2(50),
  excep_dep_a               VARCHAR2(60),
  excep_dep_b               VARCHAR2(50),
  excep_position_a          VARCHAR2(50),
  excep_position_b          VARCHAR2(50),
  excep_name_a              VARCHAR2(30),
  excep_name_b              VARCHAR2(30),
  excep_mobile_a            VARCHAR2(30),
  excep_mobile_b            VARCHAR2(30),
  excep_tel_a               VARCHAR2(30),
  excep_tel_b               VARCHAR2(30),
  excep_mail_a              VARCHAR2(50),
  excep_mail_b              VARCHAR2(50),
  device_dep                VARCHAR2(60),
  device_position           VARCHAR2(30),
  device_name               VARCHAR2(30),
  device_mobile             VARCHAR2(30),
  device_tel                VARCHAR2(30),
  device_mail               VARCHAR2(50),
  import_dep_a              VARCHAR2(50),
  import_position_a         VARCHAR2(30),
  import_name_a             VARCHAR2(30),
  import_mobile_a           VARCHAR2(30),
  import_tel_a              VARCHAR2(30),
  import_mail_a             VARCHAR2(50),
  pt_dep_a                  VARCHAR2(60),
  pt_position_a             VARCHAR2(30),
  pt_name_a                 VARCHAR2(30),
  pt_mobil_a                VARCHAR2(30),
  pt_tel_a                  VARCHAR2(30),
  pt_mail_a                 VARCHAR2(50),
  mass_dep_a                VARCHAR2(60),
  mass_position_a           VARCHAR2(30),
  mass_name_a               VARCHAR2(30),
  mass_mobile_a             VARCHAR2(30),
  mass_tel_a                VARCHAR2(30),
  mass_mail_a               VARCHAR2(50),
  device_dep_a              VARCHAR2(60),
  device_position_a         VARCHAR2(30),
  device_name_a             VARCHAR2(30),
  device_mobile_a           VARCHAR2(30),
  device_tel_a              VARCHAR2(30),
  device_mail_a             VARCHAR2(50),
  pack_dep_a                VARCHAR2(60),
  pack_position_a           VARCHAR2(30),
  pack_name_a               VARCHAR2(30),
  pack_mobile_a             VARCHAR2(30),
  pack_tel_a                VARCHAR2(30),
  pack_mail_a               VARCHAR2(50),
  pack_dep_b                VARCHAR2(60),
  pack_position_b           VARCHAR2(30),
  pack_name_b               VARCHAR2(30),
  pack_mobile_b             VARCHAR2(30),
  pack_tel_b                VARCHAR2(30),
  pack_mail_b               VARCHAR2(50),
  pt_logistics_dep          VARCHAR2(60),
  pt_logistics_name         VARCHAR2(30),
  pt_logistics_position     VARCHAR2(30),
  pt_logistics_mobile       VARCHAR2(30),
  pt_logistics_tel          VARCHAR2(30),
  pt_logistics_mail         VARCHAR2(50),
  pt_logistics_dep_a        VARCHAR2(60),
  pt_logistics_name_a       VARCHAR2(30),
  pt_logistics_position_a   VARCHAR2(30),
  pt_logistics_mobile_a     VARCHAR2(30),
  pt_logistics_tel_a        VARCHAR2(30),
  pt_logistics_mail_a       VARCHAR2(50),
  mass_logistics_dep        VARCHAR2(60),
  mass_logistics_position   VARCHAR2(30),
  mass_logistics_name       VARCHAR2(30),
  mass_logistics_mobile     VARCHAR2(30),
  mass_logistics_tel        VARCHAR2(30),
  mass_logistics_mail       VARCHAR2(50),
  mass_logistics_dep_a      VARCHAR2(60),
  mass_logistics_position_a VARCHAR2(30),
  mass_logistics_name_a     VARCHAR2(30),
  mass_logistics_mobile_a   VARCHAR2(30),
  mass_logistics_tel_a      VARCHAR2(30),
  mass_logistics_mail_a     VARCHAR2(50),
  deal_flag                 NUMBER(1) default 0,
  deal_time                 DATE,
  do_flag                   VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table IF_MM_PUB_SUPPLIER
  is 'IF_MM_PUB_SUPPLIER供应商主数据中间表
IF_MM_PUB_SUPPLIER供应商主数据中间表
';
comment on column IF_MM_PUB_SUPPLIER.id
  is 'ID
ID
';
comment on column IF_MM_PUB_SUPPLIER.sup_factory
  is '供应商出货地
供应商出货地
出货地编码唯一';
comment on column IF_MM_PUB_SUPPLIER.supplier_no
  is '供应商代码
供应商代码
';
comment on column IF_MM_PUB_SUPPLIER.supplier_name
  is '供应商中文名
供应商中文名
';
comment on column IF_MM_PUB_SUPPLIER.sup_factory_addr
  is '出货地地址
出货地地址
';
comment on column IF_MM_PUB_SUPPLIER.detail_addr
  is '供应商详细地址
供应商详细地址
';
comment on column IF_MM_PUB_SUPPLIER.sup_status
  is '供应商状态
供应商状态
1：冻结；2：删除；3：活动';
comment on column IF_MM_PUB_SUPPLIER.email
  is '联系邮箱
联系邮箱
';
comment on column IF_MM_PUB_SUPPLIER.contact
  is '联系人
联系人
';
comment on column IF_MM_PUB_SUPPLIER.mobile_no
  is '联系人移动电话
联系人移动电话
';
comment on column IF_MM_PUB_SUPPLIER.tel_no
  is '联系人电话
联系人电话
';
comment on column IF_MM_PUB_SUPPLIER.creation_time
  is '创建时间
创建时间
';
comment on column IF_MM_PUB_SUPPLIER.last_modified_time
  is '最后修改时间
最后修改时间
';
comment on column IF_MM_PUB_SUPPLIER.blong_area
  is '所属区域
所属区域
所属区域';
comment on column IF_MM_PUB_SUPPLIER.sup_leader_name
  is '供应商领导联系人
供应商领导联系人
供应商领导联系人';
comment on column IF_MM_PUB_SUPPLIER.sup_leader_tel
  is '供应商领导电话
供应商领导电话
供应商领导电话';
comment on column IF_MM_PUB_SUPPLIER.sup_leader_mail
  is '供应商领导邮箱
供应商领导邮箱
供应商领导邮箱';
comment on column IF_MM_PUB_SUPPLIER.order_contact
  is '订货联系人
订货联系人
订货联系人';
comment on column IF_MM_PUB_SUPPLIER.order_tel
  is '订货联系电话
订货联系电话
订货联系电话';
comment on column IF_MM_PUB_SUPPLIER.order_mail
  is '订货联系邮箱
订货联系邮箱
订货联系邮箱';
comment on column IF_MM_PUB_SUPPLIER.deliv_contact
  is '发货联系人
发货联系人
发货联系人';
comment on column IF_MM_PUB_SUPPLIER.delivery_tel
  is '发货联系人电话
发货联系人电话
发货联系人电话';
comment on column IF_MM_PUB_SUPPLIER.delivery_mail
  is '发货联系人邮箱
发货联系人邮箱
发货联系人邮箱';
comment on column IF_MM_PUB_SUPPLIER.excep_contact
  is '物流异常联系人
物流异常联系人
物流异常联系人';
comment on column IF_MM_PUB_SUPPLIER.excep_tel
  is '物流异常联系电话
物流异常联系电话
物流异常联系电话';
comment on column IF_MM_PUB_SUPPLIER.excep_mail
  is '物流异常联系邮箱
物流异常联系邮箱
物流异常联系邮箱';
comment on column IF_MM_PUB_SUPPLIER.pack_dept_name
  is '包装管理部门
包装管理部门
包装管理部门';
comment on column IF_MM_PUB_SUPPLIER.pack_dept_tel
  is '包装联系电话
包装联系电话
包装联系电话';
comment on column IF_MM_PUB_SUPPLIER.pack_dept_mail
  is '包装联系邮箱
包装联系邮箱
包装联系邮箱';
comment on column IF_MM_PUB_SUPPLIER.pack_dept_contact
  is '包装管理部门担当
包装管理部门担当
包装管理部门担当';
comment on column IF_MM_PUB_SUPPLIER.pack_dept_chief
  is '包装管理部门科长
包装管理部门科长
包装管理部门科长';
comment on column IF_MM_PUB_SUPPLIER.pack_dept_minister
  is '包装管理部门部长
包装管理部门部长
包装管理部门部长';
comment on column IF_MM_PUB_SUPPLIER.import_dep
  is '重要联络人部门
重要联络人部门
';
comment on column IF_MM_PUB_SUPPLIER.import_position
  is '重要联络人职位
重要联络人职位
';
comment on column IF_MM_PUB_SUPPLIER.import_name
  is '重要联络人姓名
重要联络人姓名
';
comment on column IF_MM_PUB_SUPPLIER.import_mobile
  is '重要联络人移动电话
重要联络人移动电话
';
comment on column IF_MM_PUB_SUPPLIER.import_tel
  is '重要联络人固定电话
重要联络人固定电话
';
comment on column IF_MM_PUB_SUPPLIER.import_mail
  is '重要联络人邮箱地址
重要联络人邮箱地址
';
comment on column IF_MM_PUB_SUPPLIER.pt_dep
  is 'PT订单联络人部门
PT订单联络人部门
';
comment on column IF_MM_PUB_SUPPLIER.pt_position
  is 'PT订单联络人职位
PT订单联络人职位
';
comment on column IF_MM_PUB_SUPPLIER.pt_name
  is 'PT订单联络人姓名
PT订单联络人姓名
';
comment on column IF_MM_PUB_SUPPLIER.pt_mobile
  is 'PT订单联络人移动电话
PT订单联络人移动电话
';
comment on column IF_MM_PUB_SUPPLIER.pt_tel
  is 'PT订单联络人固定电话
PT订单联络人固定电话
';
comment on column IF_MM_PUB_SUPPLIER.pt_mail
  is 'PT订单联络人邮箱地址
PT订单联络人邮箱地址
';
comment on column IF_MM_PUB_SUPPLIER.mass_dep
  is '量产订单联络人部门
量产订单联络人部门
';
comment on column IF_MM_PUB_SUPPLIER.mass_position
  is '量产订单联络人职位
量产订单联络人职位
';
comment on column IF_MM_PUB_SUPPLIER.mass_name
  is '量产订单联络人姓名
量产订单联络人姓名
';
comment on column IF_MM_PUB_SUPPLIER.mass_mobile
  is '量产订单联络人移动电话
量产订单联络人移动电话
';
comment on column IF_MM_PUB_SUPPLIER.mass_tel
  is '量产订单联络人固定电话
量产订单联络人固定电话
';
comment on column IF_MM_PUB_SUPPLIER.mass_mail
  is '量产订单联络人邮箱地址
量产订单联络人邮箱地址
';
comment on column IF_MM_PUB_SUPPLIER.excep_dep_a
  is '异常联络人部门A
异常联络人部门A
';
comment on column IF_MM_PUB_SUPPLIER.excep_dep_b
  is '异常联络人部门B
异常联络人部门B
';
comment on column IF_MM_PUB_SUPPLIER.excep_position_a
  is '异常联络人职位A
异常联络人职位A
';
comment on column IF_MM_PUB_SUPPLIER.excep_position_b
  is '异常联络人职位B
异常联络人职位B
';
comment on column IF_MM_PUB_SUPPLIER.excep_name_a
  is '异常联络人姓名A
异常联络人姓名A
';
comment on column IF_MM_PUB_SUPPLIER.excep_name_b
  is '异常联络人姓名B
异常联络人姓名B
';
comment on column IF_MM_PUB_SUPPLIER.excep_mobile_a
  is '异常联络人移动电话A
异常联络人移动电话A
';
comment on column IF_MM_PUB_SUPPLIER.excep_mobile_b
  is '异常联络人移动电话B
异常联络人移动电话B
';
comment on column IF_MM_PUB_SUPPLIER.excep_tel_a
  is '异常联络人固定电话A
异常联络人固定电话A
';
comment on column IF_MM_PUB_SUPPLIER.excep_tel_b
  is '异常联络人固定电话B
异常联络人固定电话B
';
comment on column IF_MM_PUB_SUPPLIER.excep_mail_a
  is '异常联络人邮箱地址A
异常联络人邮箱地址A
';
comment on column IF_MM_PUB_SUPPLIER.excep_mail_b
  is '异常联络人邮箱地址B
异常联络人邮箱地址B
';
comment on column IF_MM_PUB_SUPPLIER.device_dep
  is '设变联络人部门
设变联络人部门
';
comment on column IF_MM_PUB_SUPPLIER.device_position
  is '设变联络人职位
设变联络人职位
';
comment on column IF_MM_PUB_SUPPLIER.device_name
  is '设变联络人姓名
设变联络人姓名
';
comment on column IF_MM_PUB_SUPPLIER.device_mobile
  is '设变联络人移动电话
设变联络人移动电话
';
comment on column IF_MM_PUB_SUPPLIER.device_tel
  is '设变联络人固定电话
设变联络人固定电话
';
comment on column IF_MM_PUB_SUPPLIER.device_mail
  is '设变联络人邮箱地址
设变联络人邮箱地址
';
comment on column IF_MM_PUB_SUPPLIER.import_dep_a
  is '重要联络人部门1
重要联络人部门1
';
comment on column IF_MM_PUB_SUPPLIER.import_position_a
  is '重要联络人职位1
重要联络人职位1
';
comment on column IF_MM_PUB_SUPPLIER.import_name_a
  is '重要联络人姓名1
重要联络人姓名1
';
comment on column IF_MM_PUB_SUPPLIER.import_mobile_a
  is '重要联络人移动电话1
重要联络人移动电话1
';
comment on column IF_MM_PUB_SUPPLIER.import_tel_a
  is '重要联络人固定电话1
重要联络人固定电话1
';
comment on column IF_MM_PUB_SUPPLIER.import_mail_a
  is '重要联络人邮箱地址1
重要联络人邮箱地址1
';
comment on column IF_MM_PUB_SUPPLIER.pt_dep_a
  is 'PT订单联络人部门1
PT订单联络人部门1
';
comment on column IF_MM_PUB_SUPPLIER.pt_position_a
  is 'PT订单联络人职位1
PT订单联络人职位1
';
comment on column IF_MM_PUB_SUPPLIER.pt_name_a
  is 'PT订单联络人姓名1
PT订单联络人姓名1
';
comment on column IF_MM_PUB_SUPPLIER.pt_mobil_a
  is 'PT订单联络人移动电话1
PT订单联络人移动电话1
';
comment on column IF_MM_PUB_SUPPLIER.pt_tel_a
  is 'PT订单联络人固定电话1
PT订单联络人固定电话1
';
comment on column IF_MM_PUB_SUPPLIER.pt_mail_a
  is 'PT订单联络人邮箱地址1
PT订单联络人邮箱地址1
';
comment on column IF_MM_PUB_SUPPLIER.mass_dep_a
  is '量产订单联络人部门1
量产订单联络人部门1
';
comment on column IF_MM_PUB_SUPPLIER.mass_position_a
  is '量产订单联络人职位1
量产订单联络人职位1
';
comment on column IF_MM_PUB_SUPPLIER.mass_name_a
  is '量产订单联络人姓名1
量产订单联络人姓名1
';
comment on column IF_MM_PUB_SUPPLIER.mass_mobile_a
  is '量产订单联络人移动电话1
量产订单联络人移动电话1
';
comment on column IF_MM_PUB_SUPPLIER.mass_tel_a
  is '量产订单联络人固定电话1
量产订单联络人固定电话1
';
comment on column IF_MM_PUB_SUPPLIER.mass_mail_a
  is '量产订单联络人邮箱地址1
量产订单联络人邮箱地址1
';
comment on column IF_MM_PUB_SUPPLIER.device_dep_a
  is '设变联络人部门1
设变联络人部门1
';
comment on column IF_MM_PUB_SUPPLIER.device_position_a
  is '设变联络人职位1
设变联络人职位1
';
comment on column IF_MM_PUB_SUPPLIER.device_name_a
  is '设变联络人姓名1
设变联络人姓名1
';
comment on column IF_MM_PUB_SUPPLIER.device_mobile_a
  is '设变联络人移动电话1
设变联络人移动电话1
';
comment on column IF_MM_PUB_SUPPLIER.device_tel_a
  is '设变联络人固定电话1
设变联络人固定电话1
';
comment on column IF_MM_PUB_SUPPLIER.device_mail_a
  is '设变联络人邮箱地址1
设变联络人邮箱地址1
';
comment on column IF_MM_PUB_SUPPLIER.pack_dep_a
  is '包装联络人部门1
包装联络人部门1
';
comment on column IF_MM_PUB_SUPPLIER.pack_position_a
  is '包装联络人职位1
包装联络人职位1
';
comment on column IF_MM_PUB_SUPPLIER.pack_name_a
  is '包装联络人姓名1
包装联络人姓名1
';
comment on column IF_MM_PUB_SUPPLIER.pack_mobile_a
  is '包装联络人移动电话1
包装联络人移动电话1
';
comment on column IF_MM_PUB_SUPPLIER.pack_tel_a
  is '包装联系人固定电话1
包装联系人固定电话1
';
comment on column IF_MM_PUB_SUPPLIER.pack_mail_a
  is '包装联络人邮箱地址1
包装联络人邮箱地址1
';
comment on column IF_MM_PUB_SUPPLIER.pack_dep_b
  is '包装联络人部门2
包装联络人部门2
';
comment on column IF_MM_PUB_SUPPLIER.pack_position_b
  is '包装联络人职位2
包装联络人职位2
';
comment on column IF_MM_PUB_SUPPLIER.pack_name_b
  is '包装联络人姓名2
包装联络人姓名2
';
comment on column IF_MM_PUB_SUPPLIER.pack_mobile_b
  is '包装联络人移动电话2
包装联络人移动电话2
';
comment on column IF_MM_PUB_SUPPLIER.pack_tel_b
  is '包装联系人固定电话2
包装联系人固定电话2
';
comment on column IF_MM_PUB_SUPPLIER.pack_mail_b
  is '包装联络人邮箱地址2
包装联络人邮箱地址2
';
comment on column IF_MM_PUB_SUPPLIER.pt_logistics_dep
  is 'PT物流对应联络人部门
PT物流对应联络人部门
';
comment on column IF_MM_PUB_SUPPLIER.pt_logistics_name
  is 'PT物流对应联络人姓名
PT物流对应联络人姓名
';
comment on column IF_MM_PUB_SUPPLIER.pt_logistics_position
  is 'PT物流对应联络人职位
PT物流对应联络人职位
';
comment on column IF_MM_PUB_SUPPLIER.pt_logistics_mobile
  is 'PT物流对应联络人移动电话
PT物流对应联络人移动电话
';
comment on column IF_MM_PUB_SUPPLIER.pt_logistics_tel
  is 'PT物流对应联络人固定电话
PT物流对应联络人固定电话
';
comment on column IF_MM_PUB_SUPPLIER.pt_logistics_mail
  is 'PT物流对应联络人邮箱地址
PT物流对应联络人邮箱地址
';
comment on column IF_MM_PUB_SUPPLIER.pt_logistics_dep_a
  is 'PT物流对应联络人部门1
PT物流对应联络人部门1
';
comment on column IF_MM_PUB_SUPPLIER.pt_logistics_name_a
  is 'PT物流对应联络人姓名1
PT物流对应联络人姓名1
';
comment on column IF_MM_PUB_SUPPLIER.pt_logistics_position_a
  is 'PT物流对应联络人职位1
PT物流对应联络人职位1
';
comment on column IF_MM_PUB_SUPPLIER.pt_logistics_mobile_a
  is 'PT物流对应联络人移动电话1
PT物流对应联络人移动电话1
';
comment on column IF_MM_PUB_SUPPLIER.pt_logistics_tel_a
  is 'PT物流对应联络人固定电话1
PT物流对应联络人固定电话1
';
comment on column IF_MM_PUB_SUPPLIER.pt_logistics_mail_a
  is 'PT物流对应联络人邮箱地址1
PT物流对应联络人邮箱地址1
';
comment on column IF_MM_PUB_SUPPLIER.mass_logistics_dep
  is '量产物流对应联络人部门
量产物流对应联络人部门
';
comment on column IF_MM_PUB_SUPPLIER.mass_logistics_position
  is '量产物流对应联络人职位
量产物流对应联络人职位
';
comment on column IF_MM_PUB_SUPPLIER.mass_logistics_name
  is '量产物流对应联络人姓名
量产物流对应联络人姓名
';
comment on column IF_MM_PUB_SUPPLIER.mass_logistics_mobile
  is '量产物流对应联络人移动电话
量产物流对应联络人移动电话
';
comment on column IF_MM_PUB_SUPPLIER.mass_logistics_tel
  is '量产物流对应联络人固定电话
量产物流对应联络人固定电话
';
comment on column IF_MM_PUB_SUPPLIER.mass_logistics_mail
  is '量产物流对应联络人邮箱地址
量产物流对应联络人邮箱地址
';
comment on column IF_MM_PUB_SUPPLIER.mass_logistics_dep_a
  is '量产物流对应联络人部门1
量产物流对应联络人部门1
';
comment on column IF_MM_PUB_SUPPLIER.mass_logistics_position_a
  is '量产物流对应联络人职位1
量产物流对应联络人职位1
';
comment on column IF_MM_PUB_SUPPLIER.mass_logistics_name_a
  is '量产物流对应联络人姓名1
量产物流对应联络人姓名1
';
comment on column IF_MM_PUB_SUPPLIER.mass_logistics_mobile_a
  is '量产物流对应联络人移动电话1
量产物流对应联络人移动电话1
';
comment on column IF_MM_PUB_SUPPLIER.mass_logistics_tel_a
  is '量产物流对应联络人固定电话1
量产物流对应联络人固定电话1
';
comment on column IF_MM_PUB_SUPPLIER.mass_logistics_mail_a
  is '量产物流对应联络人邮箱地址1
量产物流对应联络人邮箱地址1
';
comment on column IF_MM_PUB_SUPPLIER.deal_flag
  is '接口处理标识
接口处理标识
';
comment on column IF_MM_PUB_SUPPLIER.deal_time
  is '处理时间
处理时间
';
comment on column IF_MM_PUB_SUPPLIER.do_flag
  is '处理类型
处理类型
';
alter table IF_MM_PUB_SUPPLIER
  add constraint PK_IF_MM_PUB_SUPPLIER primary key (ID, SUP_FACTORY)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_MM_PUB_SUPPLIER_DETAIL
prompt ========================================
prompt
create table IF_MM_PUB_SUPPLIER_DETAIL
(
  id                        NUMBER(19) not null,
  sup_factory               VARCHAR2(10) not null,
  supplier_no               VARCHAR2(10),
  sup_factory_addr          VARCHAR2(200),
  email                     VARCHAR2(50),
  contact                   VARCHAR2(30),
  mobile_no                 VARCHAR2(30),
  tel_no                    VARCHAR2(30),
  creation_time             DATE default sysdate,
  last_modified_time        DATE,
  blong_area                VARCHAR2(50),
  sup_leader_name           VARCHAR2(30),
  sup_leader_tel            VARCHAR2(30),
  sup_leader_mail           VARCHAR2(100),
  order_contact             VARCHAR2(30),
  order_tel                 VARCHAR2(30),
  order_mail                VARCHAR2(100),
  deliv_contact             VARCHAR2(30),
  delivery_tel              VARCHAR2(30),
  delivery_mail             VARCHAR2(100),
  excep_contact             VARCHAR2(30),
  excep_tel                 VARCHAR2(30),
  excep_mail                VARCHAR2(100),
  pack_dept_name            VARCHAR2(60),
  pack_dept_tel             VARCHAR2(30),
  pack_dept_mail            VARCHAR2(100),
  pack_dept_contact         VARCHAR2(30),
  pack_dept_chief           VARCHAR2(30),
  pack_dept_minister        VARCHAR2(30),
  import_dep                VARCHAR2(60),
  import_position           VARCHAR2(50),
  import_name               VARCHAR2(30),
  import_mobile             VARCHAR2(30),
  import_tel                VARCHAR2(30),
  import_mail               VARCHAR2(50),
  pt_dep                    VARCHAR2(60),
  pt_position               VARCHAR2(50),
  pt_name                   VARCHAR2(30),
  pt_mobile                 VARCHAR2(30),
  pt_tel                    VARCHAR2(30),
  pt_mail                   VARCHAR2(50),
  mass_dep                  VARCHAR2(60),
  mass_position             VARCHAR2(50),
  mass_name                 VARCHAR2(30),
  mass_mobile               VARCHAR2(30),
  mass_tel                  VARCHAR2(30),
  mass_mail                 VARCHAR2(50),
  excep_dep_a               VARCHAR2(60),
  excep_dep_b               VARCHAR2(50),
  excep_position_a          VARCHAR2(50),
  excep_position_b          VARCHAR2(50),
  excep_name_a              VARCHAR2(30),
  excep_name_b              VARCHAR2(30),
  excep_mobile_a            VARCHAR2(30),
  excep_mobile_b            VARCHAR2(30),
  excep_tel_a               VARCHAR2(30),
  excep_tel_b               VARCHAR2(30),
  excep_mail_a              VARCHAR2(50),
  excep_mail_b              VARCHAR2(50),
  device_dep                VARCHAR2(60),
  device_position           VARCHAR2(30),
  device_name               VARCHAR2(30),
  device_mobile             VARCHAR2(30),
  device_tel                VARCHAR2(30),
  device_mail               VARCHAR2(50),
  import_dep_a              VARCHAR2(50),
  import_position_a         VARCHAR2(30),
  import_name_a             VARCHAR2(30),
  import_mobile_a           VARCHAR2(30),
  import_tel_a              VARCHAR2(30),
  import_mail_a             VARCHAR2(50),
  pt_dep_a                  VARCHAR2(60),
  pt_position_a             VARCHAR2(30),
  pt_name_a                 VARCHAR2(30),
  pt_mobile_a               VARCHAR2(30),
  pt_tel_a                  VARCHAR2(30),
  pt_mail_a                 VARCHAR2(50),
  mass_dep_a                VARCHAR2(60),
  mass_position_a           VARCHAR2(30),
  mass_name_a               VARCHAR2(30),
  mass_mobile_a             VARCHAR2(30),
  mass_tel_a                VARCHAR2(30),
  mass_mail_a               VARCHAR2(50),
  device_dep_a              VARCHAR2(60),
  device_position_a         VARCHAR2(30),
  device_name_a             VARCHAR2(30),
  device_mobile_a           VARCHAR2(30),
  device_tel_a              VARCHAR2(30),
  device_mail_a             VARCHAR2(50),
  pack_dep_a                VARCHAR2(60),
  pack_position_a           VARCHAR2(30),
  pack_name_a               VARCHAR2(30),
  pack_mobile_a             VARCHAR2(30),
  pack_tel_a                VARCHAR2(30),
  pack_mail_a               VARCHAR2(50),
  pack_dep_b                VARCHAR2(60),
  pack_position_b           VARCHAR2(30),
  pack_name_b               VARCHAR2(30),
  pack_mobile_b             VARCHAR2(30),
  pack_tel_b                VARCHAR2(30),
  pack_mail_b               VARCHAR2(50),
  pt_logistics_dep          VARCHAR2(60),
  pt_logistics_name         VARCHAR2(30),
  pt_logistics_position     VARCHAR2(30),
  pt_logistics_mobile       VARCHAR2(30),
  pt_logistics_tel          VARCHAR2(30),
  pt_logistics_mail         VARCHAR2(50),
  pt_logistics_dep_a        VARCHAR2(60),
  pt_logistics_name_a       VARCHAR2(30),
  pt_logistics_position_a   VARCHAR2(30),
  pt_logistics_mobile_a     VARCHAR2(30),
  pt_logistics_tel_a        VARCHAR2(30),
  pt_logistics_mail_a       VARCHAR2(50),
  mass_logistics_dep        VARCHAR2(60),
  mass_logistics_position   VARCHAR2(30),
  mass_logistics_name       VARCHAR2(30),
  mass_logistics_mobile     VARCHAR2(30),
  mass_logistics_tel        VARCHAR2(30),
  mass_logistics_mail       VARCHAR2(50),
  mass_logistics_dep_a      VARCHAR2(60),
  mass_logistics_position_a VARCHAR2(30),
  mass_logistics_name_a     VARCHAR2(30),
  mass_logistics_mobile_a   VARCHAR2(30),
  mass_logistics_tel_a      VARCHAR2(30),
  mass_logistics_mail_a     VARCHAR2(50),
  deal_flag                 NUMBER(1) default 0,
  deal_time                 DATE,
  do_flag                   VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column IF_MM_PUB_SUPPLIER_DETAIL.sup_factory
  is '出货地编码唯一';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.blong_area
  is '所属区域';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.sup_leader_name
  is '供应商领导联系人';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.sup_leader_tel
  is '供应商领导电话';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.sup_leader_mail
  is '供应商领导邮箱';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.order_contact
  is '订货联系人';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.order_tel
  is '订货联系电话';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.order_mail
  is '订货联系邮箱';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.deliv_contact
  is '发货联系人';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.delivery_tel
  is '发货联系人电话';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.delivery_mail
  is '发货联系人邮箱';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.excep_contact
  is '物流异常联系人';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.excep_tel
  is '物流异常联系电话';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.excep_mail
  is '物流异常联系邮箱';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.pack_dept_name
  is '包装管理部门';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.pack_dept_tel
  is '包装联系电话';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.pack_dept_mail
  is '包装联系邮箱';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.pack_dept_contact
  is '包装管理部门担当';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.pack_dept_chief
  is '包装管理部门科长';
comment on column IF_MM_PUB_SUPPLIER_DETAIL.pack_dept_minister
  is '包装管理部门部长';
alter table IF_MM_PUB_SUPPLIER_DETAIL
  add constraint PK_IF_MM_PUB_SUPPLIER_DETAIL primary key (ID, SUP_FACTORY)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_MM_SW_PICKUP_PLAN
prompt ===================================
prompt
create table IF_MM_SW_PICKUP_PLAN
(
  id                  NUMBER(19),
  order_no            VARCHAR2(50) not null,
  purchase_no         VARCHAR2(50) not null,
  confirm_days        NUMBER(10),
  inter_logis_manager VARCHAR2(30),
  order_use           VARCHAR2(100),
  plan_assemble_time  DATE,
  plan_arr_time       DATE,
  plan_pickup_time    DATE,
  today_car_batch     NUMBER(10),
  feedback_status     NUMBER(1) default 0,
  logistics_mode      VARCHAR2(50),
  area                VARCHAR2(50),
  car_type            VARCHAR2(30),
  route_code          VARCHAR2(30),
  total_batchs        VARCHAR2(30),
  merge_batchs        NUMBER(10),
  sup_factory         VARCHAR2(20),
  supplier_no         VARCHAR2(20),
  work_date           DATE,
  factory_code        VARCHAR2(10),
  diff_flag           NUMBER(1),
  order_desc          VARCHAR2(200),
  pickup_type         VARCHAR2(30),
  order_arr_date      DATE,
  unload_port         VARCHAR2(20),
  adj_status          NUMBER(1),
  download_status     NUMBER(1) default 0,
  download_time       DATE,
  creation_user       VARCHAR2(30),
  creation_time       DATE default sysdate,
  last_modified_user  VARCHAR2(30),
  last_modified_time  DATE,
  do_flag             VARCHAR2(10),
  deal_flag           NUMBER(1) default 0,
  deal_time           DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_MM_SW_PICKUP_PLAN.feedback_status
  is '"0-未反馈
1-NG
2-OK"';
comment on column IF_MM_SW_PICKUP_PLAN.diff_flag
  is '0-有差异  1-无差异';
comment on column IF_MM_SW_PICKUP_PLAN.pickup_type
  is '取货区分(0:取货;1:送货;2:支给)';
comment on column IF_MM_SW_PICKUP_PLAN.adj_status
  is '0-无调整  1-有调整 2-新发布';
comment on column IF_MM_SW_PICKUP_PLAN.download_status
  is '"0-未下载
1-已下载"';
comment on column IF_MM_SW_PICKUP_PLAN.do_flag
  is '"处理类型
处理类型
I-新增；U-修改；D-删除"';
comment on column IF_MM_SW_PICKUP_PLAN.deal_flag
  is '"处理标识
处理标识
0 未处理 1已处理"';
comment on column IF_MM_SW_PICKUP_PLAN.deal_time
  is '"处理时间
处理时间
"';

prompt
prompt Creating table IF_MODEL_BOM
prompt ===========================
prompt
create table IF_MODEL_BOM
(
  id             NUMBER(19) not null,
  factory_code   VARCHAR2(10) not null,
  mto            VARCHAR2(10) not null,
  part_id        VARCHAR2(30) not null,
  vehicle_type   VARCHAR2(20),
  line_station   VARCHAR2(30) not null,
  stage          VARCHAR2(30),
  version        VARCHAR2(10),
  purchase_type  VARCHAR2(10),
  feature        VARCHAR2(30),
  num            VARCHAR2(10),
  part_unit      VARCHAR2(20),
  supplier_no    VARCHAR2(20),
  next_placement VARCHAR2(20),
  process        VARCHAR2(20),
  a              VARCHAR2(20),
  b              VARCHAR2(20),
  c              VARCHAR2(20),
  eff_start      VARCHAR2(30),
  eff_end        VARCHAR2(30),
  do_flag        VARCHAR2(10),
  uda1           VARCHAR2(50),
  uda2           VARCHAR2(50),
  uda3           VARCHAR2(50),
  uda4           VARCHAR2(50),
  uda5           VARCHAR2(50),
  deal_flag      NUMBER(1) default 0,
  deal_time      DATE,
  creation_time  DATE default sysdate,
  guid           VARCHAR2(50),
  file_key       VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_MODEL_BOM.next_placement
  is '无';
comment on column IF_MODEL_BOM.do_flag
  is 'I-新增；U-修改；D-删除';
comment on column IF_MODEL_BOM.deal_flag
  is '0 未处理 1已处理';
comment on column IF_MODEL_BOM.guid
  is 'GUID';
comment on column IF_MODEL_BOM.file_key
  is '文件标识';
create index IDX_IF_MODEL_BOM1 on IF_MODEL_BOM (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_MODEL_BOM
  add constraint PK_IF_MODEL_BOM primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_MP_ADJUST_BOM
prompt ===============================
prompt
create table IF_MP_ADJUST_BOM
(
  comp              VARCHAR2(10),
  car_type          VARCHAR2(20),
  adj_date          VARCHAR2(30),
  part_row_no       VARCHAR2(10),
  part_id           VARCHAR2(30),
  line_station      VARCHAR2(30),
  num               VARCHAR2(18),
  usage_amount_unit VARCHAR2(20),
  purchase_type     VARCHAR2(10),
  uda1              VARCHAR2(50),
  uda2              VARCHAR2(50),
  uda3              VARCHAR2(50),
  uda4              VARCHAR2(50),
  uda5              VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_MP_ADJUST_BOM.part_row_no
  is '子件行项目';
comment on column IF_MP_ADJUST_BOM.part_id
  is '组件物料 ';
comment on column IF_MP_ADJUST_BOM.line_station
  is '装配线工位';
comment on column IF_MP_ADJUST_BOM.num
  is '用量';
comment on column IF_MP_ADJUST_BOM.usage_amount_unit
  is '用量单位';
comment on column IF_MP_ADJUST_BOM.purchase_type
  is '采购类型';
comment on column IF_MP_ADJUST_BOM.uda1
  is '预留字段1';
comment on column IF_MP_ADJUST_BOM.uda2
  is '预留字段2';
comment on column IF_MP_ADJUST_BOM.uda3
  is '预留字段3';
comment on column IF_MP_ADJUST_BOM.uda4
  is '预留字段4';
comment on column IF_MP_ADJUST_BOM.uda5
  is '预留字段5';

prompt
prompt Creating table IF_MP_ADJUST_PLAN
prompt ================================
prompt
create table IF_MP_ADJUST_PLAN
(
  id            NUMBER(19) not null,
  comp          VARCHAR2(10),
  car_type      VARCHAR2(20),
  adj_num       VARCHAR2(10),
  adj_date      VARCHAR2(30),
  uda1          VARCHAR2(50),
  uda2          VARCHAR2(50),
  uda3          VARCHAR2(50),
  uda4          VARCHAR2(50),
  uda5          VARCHAR2(50),
  deal_flag     NUMBER(1) default 0,
  deal_time     DATE,
  creation_time DATE default sysdate,
  guid          VARCHAR2(50),
  file_key      VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_MP_ADJUST_PLAN.adj_num
  is ' ';
comment on column IF_MP_ADJUST_PLAN.deal_flag
  is '0 未处理 1已处理';
comment on column IF_MP_ADJUST_PLAN.creation_time
  is '创建时间';
comment on column IF_MP_ADJUST_PLAN.guid
  is 'GUID';
comment on column IF_MP_ADJUST_PLAN.file_key
  is '文件标识';
create index IDX_IF_MP_ADJUST_PLAN1 on IF_MP_ADJUST_PLAN (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_MP_ADJUST_PLAN
  add constraint PK_IF_MP_ADJUST_PLAN primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_ORDER
prompt =======================
prompt
create table IF_ORDER
(
  id        NUMBER(19) not null,
  comp      VARCHAR2(10),
  order_no  VARCHAR2(50),
  do_flag   VARCHAR2(10),
  uda1      VARCHAR2(50),
  uda2      VARCHAR2(50),
  uda3      VARCHAR2(50),
  uda4      VARCHAR2(50),
  uda5      VARCHAR2(50),
  deal_flag NUMBER(1) default 0,
  deal_time DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table IF_ORDER
  is 'IF_ORDERERP生产订单表';
comment on column IF_ORDER.comp
  is '工厂';
comment on column IF_ORDER.order_no
  is '生产订单号';
comment on column IF_ORDER.do_flag
  is '操作标识';
comment on column IF_ORDER.deal_flag
  is '0 未处理 1已处理';
create index IDX_IF_ORDER on IF_ORDER (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_ORDER
  add constraint PK_IF_ORDER primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_ORDER_BOM
prompt ===========================
prompt
create table IF_ORDER_BOM
(
  id            NUMBER(19) not null,
  order_no      VARCHAR2(50),
  comp          VARCHAR2(10) not null,
  uda1          VARCHAR2(50),
  uda2          VARCHAR2(50),
  uda3          VARCHAR2(50),
  uda4          VARCHAR2(50),
  uda5          VARCHAR2(50),
  do_flag       VARCHAR2(2),
  deal_flag     NUMBER(1) default 0,
  deal_time     DATE,
  creation_time DATE default sysdate,
  guid          VARCHAR2(50),
  file_key      VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_ORDER_BOM.order_no
  is '生产订单号';
comment on column IF_ORDER_BOM.do_flag
  is 'I-新增；U-修改；D-删除';
comment on column IF_ORDER_BOM.deal_flag
  is '0 未处理 1已处理';
comment on column IF_ORDER_BOM.creation_time
  is '创建时间';
comment on column IF_ORDER_BOM.guid
  is 'GUID';
comment on column IF_ORDER_BOM.file_key
  is '文件标识';
create index IDX_IF_ORDER_BOM on IF_ORDER_BOM (ID)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_ORDER_BOM_DETAIL
prompt ==================================
prompt
create table IF_ORDER_BOM_DETAIL
(
  id                NUMBER(19) not null,
  order_no          VARCHAR2(50),
  part_row_no       VARCHAR2(10),
  vehicle_part_no   VARCHAR2(30),
  part_id           VARCHAR2(30),
  work_center       VARCHAR2(20),
  line_station      VARCHAR2(30),
  num               VARCHAR2(20),
  usage_amount_unit VARCHAR2(20),
  purchase_type     VARCHAR2(10),
  uda1              VARCHAR2(50),
  uda2              VARCHAR2(50),
  uda3              VARCHAR2(50),
  uda4              VARCHAR2(50),
  uda5              VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_ORDER_BOM_DETAIL.order_no
  is '生产订单号';
comment on column IF_ORDER_BOM_DETAIL.part_row_no
  is '子件行项目';
comment on column IF_ORDER_BOM_DETAIL.vehicle_part_no
  is '整车物料';
comment on column IF_ORDER_BOM_DETAIL.part_id
  is '子件号';
comment on column IF_ORDER_BOM_DETAIL.work_center
  is '车间，需根据装配工位和车间对应关系关联';
comment on column IF_ORDER_BOM_DETAIL.line_station
  is '工位号';
comment on column IF_ORDER_BOM_DETAIL.num
  is '组件物料在该总成件下的用量';
comment on column IF_ORDER_BOM_DETAIL.usage_amount_unit
  is '子件单位';
create index IDX_IF_ORDER_BOM_DETAIL on IF_ORDER_BOM_DETAIL (ID)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_ORDER_FEATURE
prompt ===============================
prompt
create table IF_ORDER_FEATURE
(
  id            NUMBER(19) not null,
  order_no      VARCHAR2(50) not null,
  factory_code  VARCHAR2(10) not null,
  uda1          VARCHAR2(50),
  uda2          VARCHAR2(50),
  uda3          VARCHAR2(50),
  uda4          VARCHAR2(50),
  uda5          VARCHAR2(50),
  do_flag       VARCHAR2(2),
  deal_flag     NUMBER(1) default 0,
  deal_time     DATE,
  creation_time DATE default sysdate,
  guid          VARCHAR2(50),
  file_key      VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_ORDER_FEATURE.do_flag
  is 'I-新增；U-修改；D-删除';
comment on column IF_ORDER_FEATURE.deal_flag
  is '0 未处理 1已处理';
alter table IF_ORDER_FEATURE
  add constraint PK_IF_ORDER_FEATURE primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_ORDER_FEATURE_DETAIL
prompt ======================================
prompt
create table IF_ORDER_FEATURE_DETAIL
(
  id                 NUMBER(19) not null,
  order_no           VARCHAR2(50) not null,
  feature_code       VARCHAR2(20) not null,
  feature_code_desc  VARCHAR2(100),
  feature_value      VARCHAR2(64) not null,
  feature_value_desc VARCHAR2(100),
  uda1               VARCHAR2(50),
  uda2               VARCHAR2(50),
  uda3               VARCHAR2(50),
  uda4               VARCHAR2(50),
  uda5               VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table IF_PART
prompt ======================
prompt
create table IF_PART
(
  id            NUMBER(19) not null,
  comp          VARCHAR2(10) not null,
  part_id       VARCHAR2(30) not null,
  part_short_no VARCHAR2(10),
  part_name_cn  VARCHAR2(150),
  part_name_en  VARCHAR2(150),
  part_spec     VARCHAR2(50),
  purchase_type VARCHAR2(10),
  part_unit     VARCHAR2(20),
  uda1          VARCHAR2(50),
  uda2          VARCHAR2(50),
  uda3          VARCHAR2(50),
  uda4          VARCHAR2(50),
  uda5          VARCHAR2(50),
  deal_flag     NUMBER(1) default 0,
  deal_time     DATE,
  creation_time DATE default sysdate,
  guid          VARCHAR2(50),
  file_key      VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_PART.part_short_no
  is '待确认维护系统';
comment on column IF_PART.part_spec
  is '主要为资材规格信息';
comment on column IF_PART.purchase_type
  is 'W；Z；AW';
comment on column IF_PART.deal_flag
  is '0 未处理 1已处理';
comment on column IF_PART.creation_time
  is '创建时间';
comment on column IF_PART.guid
  is 'GUID';
comment on column IF_PART.file_key
  is '文件标识';
create index IDX_IF_PART1 on IF_PART (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_PART
  add constraint PK_IF_PART primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_PART_SUPPLIER
prompt ===============================
prompt
create table IF_PART_SUPPLIER
(
  id                   NUMBER(19) not null,
  comp                 VARCHAR2(10) not null,
  supplier_id          VARCHAR2(10) not null,
  factory_id           VARCHAR2(10) not null,
  part_id              VARCHAR2(30) not null,
  min_num              VARCHAR2(10),
  standard_package     VARCHAR2(10),
  in_plan_forward_time VARCHAR2(10),
  eff_start            VARCHAR2(30),
  eff_end              VARCHAR2(30),
  uda1                 VARCHAR2(50),
  uda2                 VARCHAR2(50),
  uda3                 VARCHAR2(50),
  uda4                 VARCHAR2(50),
  uda5                 VARCHAR2(50),
  deal_flag            NUMBER(1) default 0,
  deal_time            DATE,
  creation_time        DATE default sysdate,
  do_flag              VARCHAR2(2),
  guid                 VARCHAR2(50),
  file_key             VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_PART_SUPPLIER.in_plan_forward_time
  is '单位(天)';
comment on column IF_PART_SUPPLIER.eff_start
  is '格式：yyyy-MM-dd HH:mm:ss';
comment on column IF_PART_SUPPLIER.eff_end
  is '格式：yyyy-MM-dd HH:mm:ss';
comment on column IF_PART_SUPPLIER.deal_flag
  is '0 未处理 1已处理';
comment on column IF_PART_SUPPLIER.creation_time
  is '创建时间';
comment on column IF_PART_SUPPLIER.do_flag
  is 'I-新增；U-修改；D-删除';
create index IDX_IF_PART_SUPPLIER1 on IF_PART_SUPPLIER (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_PART_SUPPLIER
  add constraint PK_IF_PART_SUPPLIER primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_PRO_ROUTE
prompt ===========================
prompt
create table IF_PRO_ROUTE
(
  id            NUMBER(19) not null,
  factory_code  VARCHAR2(10) not null,
  route_code    VARCHAR2(20) not null,
  route_name    VARCHAR2(100),
  do_flag       VARCHAR2(10),
  deal_flag     NUMBER(1) default 0,
  deal_time     DATE,
  creation_time DATE default sysdate
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_PRO_ROUTE.do_flag
  is 'I-新增；U-更新；D-删除。';
comment on column IF_PRO_ROUTE.deal_flag
  is '0 未处理 1已处理';
create index IDX_IF_PRO_ROUTE1 on IF_PRO_ROUTE (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_PRO_ROUTE
  add constraint PK_IF_PRO_ROUTE primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_PRO_ROUTE_DETAIL
prompt ==================================
prompt
create table IF_PRO_ROUTE_DETAIL
(
  id               NUMBER(19),
  work_center      VARCHAR2(20),
  station_code     VARCHAR2(20),
  station_name     VARCHAR2(100),
  station_order_by VARCHAR2(10),
  creation_time    DATE default sysdate
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_PRO_ROUTE_DETAIL.station_order_by
  is 'I-新增；U-更新；D-删除。';
create index IDX_IF_PRO_ROUTE_DETAIL1 on IF_PRO_ROUTE_DETAIL (ID)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_SPS_CONFIG
prompt ============================
prompt
create table IF_SPS_CONFIG
(
  id            NUMBER(19) not null,
  config_name   VARCHAR2(64) not null,
  config_desc   VARCHAR2(256),
  feature_group VARCHAR2(64) not null,
  do_flag       VARCHAR2(2),
  deal_flag     NUMBER(1) default 0,
  deal_time     DATE,
  creation_time DATE default sysdate,
  guid          VARCHAR2(50),
  file_key      VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_SPS_CONFIG.do_flag
  is 'I-新增；U-修改；D-删除';
comment on column IF_SPS_CONFIG.deal_flag
  is '0 未处理 1已处理';
alter table IF_SPS_CONFIG
  add constraint PK_IF_SPS_CONFIG primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_SUPER_BOM
prompt ===========================
prompt
create table IF_SUPER_BOM
(
  id                 NUMBER(19),
  model_code         VARCHAR2(40),
  factory_code       VARCHAR2(40),
  part_no            VARCHAR2(40),
  parent_part_code   VARCHAR2(40),
  part_name_cn       VARCHAR2(80),
  part_name_en       VARCHAR2(150),
  part_version       VARCHAR2(8),
  line_no            NUMBER(10,2),
  uc                 VARCHAR2(2000),
  amount             NUMBER(10,2),
  unit_code          VARCHAR2(8),
  plate_code         VARCHAR2(40),
  purchase_type      VARCHAR2(20),
  next_placement     VARCHAR2(200),
  process_type       VARCHAR2(200),
  plan_eff_start     VARCHAR2(40),
  plan_eff_end       VARCHAR2(40),
  actual_eff_start   VARCHAR2(40),
  actual_eff_end     VARCHAR2(40),
  pco                VARCHAR2(40),
  raw_num            VARCHAR2(256),
  gacne_key_part     VARCHAR2(10),
  inter_change_group VARCHAR2(20),
  after_sale_flag    VARCHAR2(10),
  guid               VARCHAR2(36),
  do_flag            VARCHAR2(30),
  uda1               VARCHAR2(50),
  uda2               VARCHAR2(50),
  uda3               VARCHAR2(50),
  uda4               VARCHAR2(50),
  uda5               VARCHAR2(50),
  file_key           VARCHAR2(50),
  deal_flag          NUMBER(1) default 0,
  deal_time          DATE,
  creation_time      DATE default sysdate
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table IF_SUPER_BOM
  is 'G-BOM下发给ERP的超级BOM';
comment on column IF_SUPER_BOM.id
  is 'ID';
comment on column IF_SUPER_BOM.model_code
  is '车型';
comment on column IF_SUPER_BOM.factory_code
  is '工厂';
comment on column IF_SUPER_BOM.part_no
  is '零件编号';
comment on column IF_SUPER_BOM.parent_part_code
  is '上级总成编码';
comment on column IF_SUPER_BOM.part_name_cn
  is '零件中文名称';
comment on column IF_SUPER_BOM.part_name_en
  is '零件英文名称';
comment on column IF_SUPER_BOM.part_version
  is '零件版本';
comment on column IF_SUPER_BOM.line_no
  is '行号';
comment on column IF_SUPER_BOM.uc
  is '用法';
comment on column IF_SUPER_BOM.amount
  is '用量';
comment on column IF_SUPER_BOM.unit_code
  is '单位';
comment on column IF_SUPER_BOM.plate_code
  is '板材编码';
comment on column IF_SUPER_BOM.purchase_type
  is 'GACNE采购类型';
comment on column IF_SUPER_BOM.next_placement
  is '下一落点';
comment on column IF_SUPER_BOM.process_type
  is '工序落点';
comment on column IF_SUPER_BOM.plan_eff_start
  is '计划生效时间';
comment on column IF_SUPER_BOM.plan_eff_end
  is '计划失效时间';
comment on column IF_SUPER_BOM.actual_eff_start
  is '实际生效时间';
comment on column IF_SUPER_BOM.actual_eff_end
  is '实际失效时间';
comment on column IF_SUPER_BOM.pco
  is 'PCO编码';
comment on column IF_SUPER_BOM.raw_num
  is '材料';
comment on column IF_SUPER_BOM.gacne_key_part
  is 'GACNE重要度';
comment on column IF_SUPER_BOM.inter_change_group
  is '互换组号';
comment on column IF_SUPER_BOM.after_sale_flag
  is '售后标识';
comment on column IF_SUPER_BOM.guid
  is 'GUID';
comment on column IF_SUPER_BOM.do_flag
  is '处理标识位';
comment on column IF_SUPER_BOM.file_key
  is '文件标识';
comment on column IF_SUPER_BOM.deal_flag
  is '0 未处理 1已处理';
comment on column IF_SUPER_BOM.creation_time
  is '创建时间';
create index IDX_IF_SUPER_BOM on IF_SUPER_BOM (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_SUPPLIER
prompt ==========================
prompt
create table IF_SUPPLIER
(
  id             NUMBER(19) not null,
  sup_factory_id VARCHAR2(10) not null,
  supplier_no    VARCHAR2(10),
  chinese_name   VARCHAR2(150),
  detail_addr    VARCHAR2(200),
  sup_status     VARCHAR2(10),
  email          VARCHAR2(50),
  contact        VARCHAR2(30),
  mobile_no      VARCHAR2(30),
  tel_no         VARCHAR2(30),
  uda1           VARCHAR2(50),
  uda2           VARCHAR2(50),
  uda3           VARCHAR2(50),
  uda4           VARCHAR2(50),
  uda5           VARCHAR2(50),
  deal_flag      NUMBER(1) default 0,
  deal_time      DATE,
  creation_time  DATE default sysdate,
  guid           VARCHAR2(50),
  file_key       VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_SUPPLIER.sup_factory_id
  is '出货地编码唯一';
comment on column IF_SUPPLIER.sup_status
  is '1：冻结；2：删除；3：活动';
comment on column IF_SUPPLIER.deal_flag
  is '0 未处理 1已处理';
comment on column IF_SUPPLIER.guid
  is 'GUID';
comment on column IF_SUPPLIER.file_key
  is '文件标识';
create index IDX_IF_SUPPLIER1 on IF_SUPPLIER (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_SUPPLIER
  add constraint PK_IF_SUPPLIER primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_SW_ACCOUNT_BILL
prompt =================================
prompt
create table IF_SW_ACCOUNT_BILL
(
  id                NUMBER(19) not null,
  comp              VARCHAR2(10) not null,
  account_bill_no   VARCHAR2(30) not null,
  supplier_no       VARCHAR2(20),
  free_tax_amount   VARCHAR2(20),
  total_amount      VARCHAR2(20),
  total_tax         VARCHAR2(20),
  account_date      VARCHAR2(30),
  currency_type     VARCHAR2(20),
  rebate            VARCHAR2(20),
  rebate_desc       VARCHAR2(200),
  deduct_money      VARCHAR2(20),
  deduct_money_desc VARCHAR2(200),
  year_adjust       VARCHAR2(20),
  year_adjust_desc  VARCHAR2(200),
  mould_amount      VARCHAR2(20),
  mould_amount_desc VARCHAR2(200),
  pay_term          VARCHAR2(50),
  remark            VARCHAR2(300),
  uda1              VARCHAR2(200),
  uda2              VARCHAR2(200),
  uda3              VARCHAR2(200),
  uda4              VARCHAR2(200),
  uda5              VARCHAR2(200),
  deal_flag         VARCHAR2(10) default '0',
  deal_time         DATE,
  creation_time     DATE default sysdate,
  do_flag           VARCHAR2(10),
  guid              VARCHAR2(50),
  file_key          VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_SW_ACCOUNT_BILL.comp
  is '公司代码即i-LMS系统的工厂代码';
comment on column IF_SW_ACCOUNT_BILL.free_tax_amount
  is '小数点保留几位？';
comment on column IF_SW_ACCOUNT_BILL.total_amount
  is '小数点保留几位？';
comment on column IF_SW_ACCOUNT_BILL.total_tax
  is '小数点保留几位？';
comment on column IF_SW_ACCOUNT_BILL.deal_flag
  is '0 未处理 1已处理';
comment on column IF_SW_ACCOUNT_BILL.creation_time
  is '创建时间';
comment on column IF_SW_ACCOUNT_BILL.do_flag
  is 'I-新增；U-修改；D-删除';
comment on column IF_SW_ACCOUNT_BILL.guid
  is 'GUID';
comment on column IF_SW_ACCOUNT_BILL.file_key
  is '文件标识';
create index IDX_IF_SW_ACCOUNT_BILL1 on IF_SW_ACCOUNT_BILL (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_SW_ACCOUNT_BILL
  add constraint PK_IF_SW_ACCOUNT_BILL primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_SW_ACCOUNT_BILL_DETAIL
prompt ========================================
prompt
create table IF_SW_ACCOUNT_BILL_DETAIL
(
  id                        NUMBER(19),
  account_bill_no           VARCHAR2(30) not null,
  account_bill_row_no       VARCHAR2(10) not null,
  purchase_order_no         VARCHAR2(50),
  purchase_row_no           VARCHAR2(10),
  rec_voucher_year          VARCHAR2(20),
  rec_voucher_bill_no       VARCHAR2(50),
  rec_voucher_row_no        VARCHAR2(10),
  ref_rec_voucher_bill      VARCHAR2(50),
  ref_rec_voucher_row_no    VARCHAR2(10),
  rec_date                  VARCHAR2(30),
  loan_flag                 VARCHAR2(10),
  price_status              VARCHAR2(10),
  eva_price                 VARCHAR2(20),
  official_price            VARCHAR2(20),
  adjust_diff_price         VARCHAR2(20),
  eva_price_percent         VARCHAR2(20),
  eva_settle_price          VARCHAR2(20),
  erp_factory_code          VARCHAR2(10),
  part_no                   VARCHAR2(30),
  supplier_no               VARCHAR2(20),
  rec_num                   VARCHAR2(20),
  part_unit                 VARCHAR2(20),
  pay_amount                VARCHAR2(20),
  tax_rate                  VARCHAR2(20),
  tax_amount                VARCHAR2(20),
  currency_type             VARCHAR2(20),
  uda1                      VARCHAR2(50),
  uda2                      VARCHAR2(50),
  uda3                      VARCHAR2(50),
  uda4                      VARCHAR2(50),
  uda5                      VARCHAR2(50),
  eva_price_unit            VARCHAR2(20),
  official_price_unit       VARCHAR2(20),
  ref_year_rec_voucher_bill VARCHAR2(20)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_SW_ACCOUNT_BILL_DETAIL.rec_voucher_bill_no
  is '物料凭证即收货出入库单号';
comment on column IF_SW_ACCOUNT_BILL_DETAIL.loan_flag
  is 'S-正数；H-负数';
comment on column IF_SW_ACCOUNT_BILL_DETAIL.price_status
  is '1-暂估价；2-正式价；3-调差价';
comment on column IF_SW_ACCOUNT_BILL_DETAIL.eva_price
  is '小数点保留几位？';
comment on column IF_SW_ACCOUNT_BILL_DETAIL.official_price
  is '小数点保留几位？';
comment on column IF_SW_ACCOUNT_BILL_DETAIL.adjust_diff_price
  is '小数点保留几位？';
comment on column IF_SW_ACCOUNT_BILL_DETAIL.eva_price_percent
  is '暂估价折扣百分比；百分比，如“15”代表为15%？？';
comment on column IF_SW_ACCOUNT_BILL_DETAIL.eva_settle_price
  is '暂估价折扣后价格';
comment on column IF_SW_ACCOUNT_BILL_DETAIL.tax_rate
  is '百分比，如“15”代表为15%？？';
create index IDX_IF_SW_ACCOU_BILL_DETAIL1 on IF_SW_ACCOUNT_BILL_DETAIL (ID)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_SW_ACCOUNT_INVOICE
prompt ====================================
prompt
create table IF_SW_ACCOUNT_INVOICE
(
  account_bill_no   VARCHAR2(30) not null,
  comp              VARCHAR2(10) not null,
  invoice_date      DATE,
  supplier_no       VARCHAR2(20),
  invoice_amount    NUMBER(19,3),
  tax_amount        NUMBER(19,3),
  rebate            VARCHAR2(20),
  rebate_desc       VARCHAR2(200),
  deduct_money      VARCHAR2(20),
  deduct_money_desc VARCHAR2(200),
  year_adjust       VARCHAR2(20),
  year_adjust_desc  VARCHAR2(200),
  mould_amount      VARCHAR2(20),
  mould_amount_desc VARCHAR2(200),
  currency_type     VARCHAR2(20),
  pay_term          VARCHAR2(50),
  uda1              VARCHAR2(50),
  uda2              VARCHAR2(50),
  uda3              VARCHAR2(50),
  uda4              VARCHAR2(50),
  uda5              VARCHAR2(50),
  deal_flag         NUMBER(1) default 0,
  deal_time         DATE,
  creation_time     DATE default sysdate
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_SW_ACCOUNT_INVOICE.comp
  is '公司代码即i-LMS系统的工厂代码';
comment on column IF_SW_ACCOUNT_INVOICE.invoice_date
  is '发票日期';
comment on column IF_SW_ACCOUNT_INVOICE.deal_flag
  is '0 未处理 1已处理 2失败';
comment on column IF_SW_ACCOUNT_INVOICE.creation_time
  is '创建时间';
create index IDX_IF_SW_ACCOUNT_INVOICE1 on IF_SW_ACCOUNT_INVOICE (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_SW_ACCOUNT_INVOICE
  add constraint PK_IF_SW_ACCOUNT_INVOICE primary key (ACCOUNT_BILL_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_SW_ACCOUNT_INVOICE_DETAIL
prompt ===========================================
prompt
create table IF_SW_ACCOUNT_INVOICE_DETAIL
(
  account_bill_no       VARCHAR2(30) not null,
  invoice_no            VARCHAR2(50),
  invoice_code          VARCHAR2(50),
  single_invoice_amount NUMBER(19,3),
  single_tax_amount     NUMBER(19,3),
  invoice_date          DATE,
  check_code            VARCHAR2(30),
  invoice_net_price     NUMBER(19,3),
  uda1                  VARCHAR2(50),
  uda2                  VARCHAR2(50),
  uda3                  VARCHAR2(50),
  uda4                  VARCHAR2(50),
  uda5                  VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_SW_ACCOUNT_INVOICE_DETAIL.invoice_no
  is '公司代码即i-LMS系统的工厂代码';
create index IDX_IF_SW_AI_DETAIL on IF_SW_ACCOUNT_INVOICE_DETAIL (ACCOUNT_BILL_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_SW_DEMAND_FORECAST
prompt ====================================
prompt
create table IF_SW_DEMAND_FORECAST
(
  id            NUMBER(19) not null,
  comp          VARCHAR2(10),
  version_no    VARCHAR2(20),
  rec_date      VARCHAR2(30),
  phase         VARCHAR2(20),
  supplier_no   VARCHAR2(20),
  part_id       VARCHAR2(30),
  fore_type     VARCHAR2(10),
  end_date      VARCHAR2(30),
  order_num     VARCHAR2(10),
  part_unit     VARCHAR2(20),
  start_date    VARCHAR2(30),
  model_code    VARCHAR2(20),
  uda1          VARCHAR2(50),
  uda2          VARCHAR2(50),
  uda3          VARCHAR2(50),
  uda4          VARCHAR2(50),
  uda5          VARCHAR2(50),
  deal_flag     NUMBER(1) default 0,
  deal_time     DATE,
  creation_time DATE default sysdate,
  part_short_no VARCHAR2(10),
  part_name_cn  VARCHAR2(150),
  chinese_name  VARCHAR2(150),
  guid          VARCHAR2(50),
  file_key      VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_SW_DEMAND_FORECAST.rec_date
  is '如果是月度预测取每月1日作为月份日期；如2018-08-01为8月预测；格式：yyyy-MM-dd HH:mm:ss';
comment on column IF_SW_DEMAND_FORECAST.phase
  is '量产；试制';
comment on column IF_SW_DEMAND_FORECAST.fore_type
  is '月预测；日预测';
comment on column IF_SW_DEMAND_FORECAST.end_date
  is '格式：yyyy-MM-dd HH:mm:ss';
comment on column IF_SW_DEMAND_FORECAST.start_date
  is '格式：yyyy-MM-dd HH:mm:ss';
comment on column IF_SW_DEMAND_FORECAST.model_code
  is '产线';
comment on column IF_SW_DEMAND_FORECAST.deal_flag
  is '0 未处理 1已处理';
comment on column IF_SW_DEMAND_FORECAST.part_short_no
  is '待确认维护系统';
comment on column IF_SW_DEMAND_FORECAST.guid
  is 'GUID';
comment on column IF_SW_DEMAND_FORECAST.file_key
  is '文件标识';
create index IDX_IF_SW_DEMAND_FORECAST1 on IF_SW_DEMAND_FORECAST (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_SW_DEMAND_FORECAST
  add constraint PK_IF_SW_DEMAND_FORECAST primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_SW_MATERIAL_ORDER_REPLY
prompt =========================================
prompt
create table IF_SW_MATERIAL_ORDER_REPLY
(
  comp               VARCHAR2(10) not null,
  purchase_order_no  VARCHAR2(50) not null,
  reply_seq_no       VARCHAR2(10) not null,
  purchase_row_no    VARCHAR2(10) not null,
  plan_delivery_date DATE,
  plan_delivery_num  NUMBER(10),
  reply_msg          VARCHAR2(300),
  reply_date         DATE,
  uda1               VARCHAR2(50),
  uda2               VARCHAR2(50),
  uda3               VARCHAR2(50),
  uda4               VARCHAR2(50),
  uda5               VARCHAR2(50),
  deal_flag          NUMBER(1) default 0,
  deal_time          DATE,
  creation_time      DATE default sysdate
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_SW_MATERIAL_ORDER_REPLY.reply_seq_no
  is '0:否；1:是';
comment on column IF_SW_MATERIAL_ORDER_REPLY.reply_date
  is 'yyyy-MM-dd';
comment on column IF_SW_MATERIAL_ORDER_REPLY.deal_flag
  is '0 未处理 1已处理';
comment on column IF_SW_MATERIAL_ORDER_REPLY.creation_time
  is '创建时间';
create index IDX_IF_SW_ORDER_REPLY1 on IF_SW_MATERIAL_ORDER_REPLY (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_SW_NON_STANDAR_CHECK
prompt ======================================
prompt
create table IF_SW_NON_STANDAR_CHECK
(
  id               NUMBER(19) not null,
  sale_no          VARCHAR2(50),
  sale_row_no      NUMBER(10),
  part_no          VARCHAR2(50),
  remark           VARCHAR2(300),
  check_result     NUMBER(1) default 0,
  checker          VARCHAR2(30),
  check_time       DATE,
  creation_user    VARCHAR2(30),
  creation_user_ip VARCHAR2(30),
  deal_flag        NUMBER(2),
  deal_time        DATE,
  do_flag          VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_SW_NON_STANDAR_CHECK.check_result
  is '"0：未检查
1：不通过
2：通过"';
comment on column IF_SW_NON_STANDAR_CHECK.deal_flag
  is '0未处理 1已处理';

prompt
prompt Creating table IF_SW_NON_STAND_PIC
prompt ==================================
prompt
create table IF_SW_NON_STAND_PIC
(
  check_id           NUMBER(19),
  sale_no            VARCHAR2(50),
  sale_row_no        NUMBER(10),
  part_no            VARCHAR2(30),
  pic_type           NUMBER(2),
  pic_id             NUMBER(19),
  creation_time      DATE,
  creation_user      VARCHAR2(30),
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  deal_flag          NUMBER(2),
  deal_time          DATE,
  do_flag            VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_SW_NON_STAND_PIC.pic_type
  is '0：实物上传的图片  1：反馈的图片';
comment on column IF_SW_NON_STAND_PIC.deal_flag
  is '0未处理 1已处理';

prompt
prompt Creating table IF_SW_ORDER
prompt ==========================
prompt
create table IF_SW_ORDER
(
  id                    NUMBER(19) not null,
  comp                  VARCHAR2(10) not null,
  purchase_order_no     VARCHAR2(50) not null,
  logistics_orderno     VARCHAR2(50),
  supplier_no           VARCHAR2(20),
  sup_shipment_place_no VARCHAR2(20),
  order_type            VARCHAR2(10),
  order_place           VARCHAR2(30),
  order_issue_date      VARCHAR2(30),
  dao_huo_time          VARCHAR2(30),
  depot_no              VARCHAR2(10),
  sp_type               VARCHAR2(10),
  hms_order_no          VARCHAR2(50),
  rec_address           VARCHAR2(300),
  rec_user              VARCHAR2(30),
  rec_tel               VARCHAR2(30),
  planner               VARCHAR2(30),
  uda1                  VARCHAR2(50),
  uda2                  VARCHAR2(50),
  uda3                  VARCHAR2(50),
  uda4                  VARCHAR2(50),
  uda5                  VARCHAR2(50),
  deal_flag             NUMBER(1) default 0,
  deal_time             DATE,
  creation_time         DATE default sysdate,
  guid                  VARCHAR2(50),
  file_key              VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_SW_ORDER.order_type
  is '01:零件订单；02:例外订单；03:拉动订单:；04:同步订单；05:试制件订单；11:长周期订单；12:售后订单(SP)；14：资材订单；';
comment on column IF_SW_ORDER.order_place
  is '订单到货场所(支给订单时为到货地供应商代码)';
comment on column IF_SW_ORDER.order_issue_date
  is 'yyyy-MM-dd HH:mm:ss';
comment on column IF_SW_ORDER.dao_huo_time
  is 'yyyy-MM-dd HH:mm:ss若明细有多个,取一条明细的数据';
comment on column IF_SW_ORDER.depot_no
  is '若明细有多个仓库，取一条明细的仓库';
comment on column IF_SW_ORDER.sp_type
  is '售后件使用(1:内制件；2：直供件；3:外采件；) 待确认';
comment on column IF_SW_ORDER.hms_order_no
  is '售后订单使用';
comment on column IF_SW_ORDER.rec_address
  is '售后订单外购件使用，收货地址信息';
comment on column IF_SW_ORDER.rec_user
  is '售后订单外购件使用，收货联系人';
comment on column IF_SW_ORDER.rec_tel
  is '售后订单外购件使用，收货联系电话';
comment on column IF_SW_ORDER.planner
  is '员工编号';
comment on column IF_SW_ORDER.deal_flag
  is '0 未处理 1已处理';
comment on column IF_SW_ORDER.creation_time
  is '创建时间';
comment on column IF_SW_ORDER.guid
  is 'GUID';
comment on column IF_SW_ORDER.file_key
  is '文件标识';
create index IDX_IF_SW_ORDER1 on IF_SW_ORDER (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table IF_SW_ORDER
  add constraint PK_IF_SW_ORDER primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_SW_ORDER_DETAIL
prompt =================================
prompt
create table IF_SW_ORDER_DETAIL
(
  id                     NUMBER(19) not null,
  purchase_order_no      VARCHAR2(50) not null,
  purchase_row_no        VARCHAR2(10) not null,
  logistics_orderno      VARCHAR2(50),
  logistics_order_row_no VARCHAR2(10),
  zg_flag                VARCHAR2(10),
  part_id                VARCHAR2(30),
  order_num              VARCHAR2(10),
  cancel_num             VARCHAR2(10),
  order_unit             VARCHAR2(20),
  daohuo_time            VARCHAR2(30),
  depot_no               VARCHAR2(20),
  package_num            VARCHAR2(10),
  zk_flag                VARCHAR2(10),
  zbzgg                  VARCHAR2(128),
  zstock                 VARCHAR2(32),
  lgpbe                  VARCHAR2(32),
  zkostl                 VARCHAR2(32),
  ktext                  VARCHAR2(100),
  do_flag                VARCHAR2(2),
  uda1                   VARCHAR2(50),
  uda2                   VARCHAR2(50),
  uda3                   VARCHAR2(50),
  uda4                   VARCHAR2(50),
  uda5                   VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_SW_ORDER_DETAIL.logistics_order_row_no
  is '物流产生的订单行号';
comment on column IF_SW_ORDER_DETAIL.zg_flag
  is '0:否；1:是';
comment on column IF_SW_ORDER_DETAIL.daohuo_time
  is 'yyyy-MM-dd HH:mm:ss';
comment on column IF_SW_ORDER_DETAIL.depot_no
  is '资材订单可能为空';
comment on column IF_SW_ORDER_DETAIL.package_num
  is '售后件传输HMS包装数';
comment on column IF_SW_ORDER_DETAIL.zk_flag
  is '售后件使用(0：否；1：是)';
comment on column IF_SW_ORDER_DETAIL.zbzgg
  is '资材订单使用';
comment on column IF_SW_ORDER_DETAIL.zstock
  is '资材订单使用';
comment on column IF_SW_ORDER_DETAIL.lgpbe
  is '资材订单使用';
comment on column IF_SW_ORDER_DETAIL.zkostl
  is '资材订单使用';
comment on column IF_SW_ORDER_DETAIL.ktext
  is '资材订单使用';
comment on column IF_SW_ORDER_DETAIL.do_flag
  is 'I-新增；U-修改；D-删除，没有删除标识，删除通过取消数量来实现。这个字段保留，自行判断是新增还是修改';
create index IDX_IF_SW_ORDER_DETAIL1 on IF_SW_ORDER_DETAIL (ID)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_TACT_TIME
prompt ===========================
prompt
create table IF_TACT_TIME
(
  id            NUMBER(19) not null,
  factory_code  VARCHAR2(10) not null,
  work_center   VARCHAR2(20) not null,
  pro_line      VARCHAR2(20) not null,
  eff_date      VARCHAR2(30),
  jph           VARCHAR2(20),
  oee           VARCHAR2(20),
  produce_time  VARCHAR2(20),
  pro_capacity  VARCHAR2(30),
  remark        VARCHAR2(200),
  doflag        VARCHAR2(10),
  uda1          VARCHAR2(50),
  uda2          VARCHAR2(50),
  uda3          VARCHAR2(50),
  uda4          VARCHAR2(50),
  uda5          VARCHAR2(50),
  deal_flag     NUMBER(1) default 0,
  deal_time     DATE,
  creation_time DATE default sysdate,
  guid          VARCHAR2(50),
  file_key      VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column IF_TACT_TIME.pro_line
  is '焊装钢线、铝线等；';
comment on column IF_TACT_TIME.doflag
  is 'I-新增；U-更新；D-删除';
comment on column IF_TACT_TIME.deal_flag
  is '0 未处理 1已处理';
comment on column IF_TACT_TIME.guid
  is 'GUID';
comment on column IF_TACT_TIME.file_key
  is '文件标识';
alter table IF_TACT_TIME
  add constraint PK_IF_TACT_TIME primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table IF_VEH_CONF_ITEM
prompt ===============================
prompt
create table IF_VEH_CONF_ITEM
(
  id NUMBER(19) not null
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ILMSPORTAL
prompt =========================
prompt
create table ILMSPORTAL
(
  id                 NUMBER(19) not null,
  car_type           VARCHAR2(20),
  supplier_no        VARCHAR2(20),
  provide_qty        NUMBER(10),
  tray_require_qty   NUMBER(10),
  plan_date          DATE,
  delay_reason       VARCHAR2(300),
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  factory_code       VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column ILMSPORTAL.car_type
  is '车型';
comment on column ILMSPORTAL.supplier_no
  is '供应商代码';
comment on column ILMSPORTAL.provide_qty
  is '已纳入量';
comment on column ILMSPORTAL.tray_require_qty
  is '箱子需求量';
comment on column ILMSPORTAL.plan_date
  is '计划完成日期';
comment on column ILMSPORTAL.delay_reason
  is '延迟原因';
alter table ILMSPORTAL
  add constraint PK_MM_PKG_TRAY_QTY primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table ILMSPORTAL
  add constraint AK_MM_PKG_TRAY_QTY unique (CAR_TYPE, SUPPLIER_NO, FACTORY_CODE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_INV_IN
prompt ========================
prompt
create table MM_INV_IN
(
  rec_no               VARCHAR2(50) not null,
  factory_code         VARCHAR2(10),
  order_no             VARCHAR2(50),
  rec_times            NUMBER(2),
  depot_no             VARCHAR2(20),
  ope_ip               VARCHAR2(50),
  rec_client           NUMBER(1),
  order_type           VARCHAR2(10),
  actual_arr_pro_seqno NUMBER(19),
  inv_flag             NUMBER(1) default 0,
  inv_time             DATE,
  deal_flag            NUMBER(1) default 0,
  deal_time            DATE,
  creation_user        VARCHAR2(30),
  creation_time        DATE default sysdate,
  note                 VARCHAR2(100),
  p_deal_flag          NUMBER(1),
  p_deal_time          DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_INV_IN
  is 'MM_INV_IN订单收货入库
';
comment on column MM_INV_IN.rec_no
  is '入库单号
入库单号';
comment on column MM_INV_IN.factory_code
  is '工厂代码
工厂代码';
comment on column MM_INV_IN.order_no
  is '物流订单号
订单号';
comment on column MM_INV_IN.rec_times
  is '收货次数
次数';
comment on column MM_INV_IN.depot_no
  is '入库仓库
入库仓库';
comment on column MM_INV_IN.ope_ip
  is '操作IP
IP';
comment on column MM_INV_IN.rec_client
  is '操作终端
1-PC
2-PDA';
comment on column MM_INV_IN.order_type
  is '订单类型
JIS-同步
JIT-拉动
SW-取货';
comment on column MM_INV_IN.actual_arr_pro_seqno
  is '实际到货批次进度
';
comment on column MM_INV_IN.inv_flag
  is '库存处理标识
0 未处理
1已处理';
comment on column MM_INV_IN.inv_time
  is '库存处理时间
库存处理时间';
comment on column MM_INV_IN.deal_flag
  is '接口处理标识
0 未处理
1已处理';
comment on column MM_INV_IN.deal_time
  is '接口处理时间
处理时间';
comment on column MM_INV_IN.creation_user
  is '创建人
创建人';
comment on column MM_INV_IN.creation_time
  is '创建时间
创建时间';
comment on column MM_INV_IN.note
  is '备注
备注';
comment on column MM_INV_IN.p_deal_flag
  is '内部接口处理标识
0 未处理 1已处理';
comment on column MM_INV_IN.p_deal_time
  is '内部处理时间
';
create index IDX_MM_INV_IN1 on MM_INV_IN (INV_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_INV_IN2 on MM_INV_IN (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_INV_IN
  add constraint PK_MM_INV_IN primary key (REC_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_INV_IN_DETAIL
prompt ===============================
prompt
create table MM_INV_IN_DETAIL
(
  rec_no        VARCHAR2(50),
  row_no        NUMBER(5),
  rec_times     NUMBER(2),
  part_no       VARCHAR2(20),
  order_qty     NUMBER(10),
  rec_qty       NUMBER(10),
  creation_time DATE default sysdate
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_INV_IN_DETAIL
  is 'MM_INV_IN_DETAIL订单收货入库明细
';
comment on column MM_INV_IN_DETAIL.rec_no
  is '入库单号
入库单号';
comment on column MM_INV_IN_DETAIL.row_no
  is '物流订单行号
订单行号';
comment on column MM_INV_IN_DETAIL.rec_times
  is '收货次数
';
comment on column MM_INV_IN_DETAIL.part_no
  is '零件编号
零件编号';
comment on column MM_INV_IN_DETAIL.order_qty
  is '订单数量
订单数量';
comment on column MM_INV_IN_DETAIL.rec_qty
  is '收货数量
收货数量';
comment on column MM_INV_IN_DETAIL.creation_time
  is '创建时间
创建时间';
create index IDX_MM_INV_IN_DETAIL1 on MM_INV_IN_DETAIL (REC_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_INV_PART_LOCATION
prompt ===================================
prompt
create table MM_INV_PART_LOCATION
(
  id                 NUMBER(19) not null,
  factory_code       VARCHAR2(10),
  part_no            VARCHAR2(20),
  supplier_no        VARCHAR2(20),
  ware_code          VARCHAR2(20),
  station_code       VARCHAR2(20),
  unload_port        VARCHAR2(20),
  dep_no             VARCHAR2(20),
  prepare_person     VARCHAR2(20),
  carpool            VARCHAR2(20),
  distri_person      VARCHAR2(100),
  location           VARCHAR2(50),
  shelf_no           VARCHAR2(20),
  location_num       NUMBER(10),
  model_code         VARCHAR2(10),
  storage            VARCHAR2(100),
  eff_start          DATE,
  eff_end            DATE,
  creation_user      VARCHAR2(30),
  creation_time      DATE default SYSDATE,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE default SYSDATE,
  workcenter         VARCHAR2(20),
  last_modified_ip   VARCHAR2(20)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table MM_INV_PART_LOCATION
  is 'MM_INV_PART_LOCATION零件属地管理';
comment on column MM_INV_PART_LOCATION.id
  is '"ID
"';
comment on column MM_INV_PART_LOCATION.factory_code
  is '"工厂代码
"';
comment on column MM_INV_PART_LOCATION.part_no
  is '"零件编号
"';
comment on column MM_INV_PART_LOCATION.supplier_no
  is '"供应商代码
"';
comment on column MM_INV_PART_LOCATION.ware_code
  is '"仓库代码
"';
comment on column MM_INV_PART_LOCATION.station_code
  is '"工位号
"';
comment on column MM_INV_PART_LOCATION.unload_port
  is '"卸货口
"';
comment on column MM_INV_PART_LOCATION.dep_no
  is '"责任班组
"';
comment on column MM_INV_PART_LOCATION.prepare_person
  is '"备货工程
"';
comment on column MM_INV_PART_LOCATION.carpool
  is '"台车号
"';
comment on column MM_INV_PART_LOCATION.distri_person
  is '"配送工程
"';
comment on column MM_INV_PART_LOCATION.location
  is '"落点地址
"';
comment on column MM_INV_PART_LOCATION.shelf_no
  is '"货架号
"';
comment on column MM_INV_PART_LOCATION.location_num
  is '"工程深度
"';
comment on column MM_INV_PART_LOCATION.model_code
  is '"车型
"';
comment on column MM_INV_PART_LOCATION.storage
  is '"物流库地址
"';
comment on column MM_INV_PART_LOCATION.eff_start
  is '"生效日期
"';
comment on column MM_INV_PART_LOCATION.eff_end
  is '"失效日期
"';
comment on column MM_INV_PART_LOCATION.creation_user
  is '"创建用户
"';
comment on column MM_INV_PART_LOCATION.creation_time
  is '"创建时间
"';
comment on column MM_INV_PART_LOCATION.last_modified_user
  is '"最后修改用户
"';
comment on column MM_INV_PART_LOCATION.last_modified_time
  is '"最后修改时间
"';
comment on column MM_INV_PART_LOCATION.workcenter
  is '"工作中心
"';
comment on column MM_INV_PART_LOCATION.last_modified_ip
  is '"最后修改IP
"';
alter table MM_INV_PART_LOCATION
  add constraint PK_MM_INV_PART_LOCATION primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_INV_SHELVES_LABEL
prompt ===================================
prompt
create table MM_INV_SHELVES_LABEL
(
  id                 NUMBER(10) not null,
  shelves_addr       VARCHAR2(150),
  factory_code       VARCHAR2(20),
  part_no            VARCHAR2(20),
  supplier_no        VARCHAR2(20),
  standard_pack      NUMBER(10),
  safe_stock         NUMBER(10),
  car_type           VARCHAR2(20),
  stack_layers       NUMBER(10),
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_INV_SHELVES_LABEL
  is 'MM_INV_SHELVES_LABEL货架标签信息表
';
comment on column MM_INV_SHELVES_LABEL.id
  is 'ID
';
comment on column MM_INV_SHELVES_LABEL.shelves_addr
  is '货架地址
';
comment on column MM_INV_SHELVES_LABEL.factory_code
  is '工厂
';
comment on column MM_INV_SHELVES_LABEL.part_no
  is '零件编号
';
comment on column MM_INV_SHELVES_LABEL.supplier_no
  is '供应商代码
';
comment on column MM_INV_SHELVES_LABEL.standard_pack
  is '规格包装
';
comment on column MM_INV_SHELVES_LABEL.safe_stock
  is '安全库存
';
comment on column MM_INV_SHELVES_LABEL.car_type
  is '车型
';
comment on column MM_INV_SHELVES_LABEL.stack_layers
  is '叠放层数
';
comment on column MM_INV_SHELVES_LABEL.creation_user
  is '创建人
';
comment on column MM_INV_SHELVES_LABEL.creation_time
  is '创建时间
';
comment on column MM_INV_SHELVES_LABEL.last_modified_user
  is '最后修改用户
';
comment on column MM_INV_SHELVES_LABEL.last_modified_time
  is '最后修改时间
';
alter table MM_INV_SHELVES_LABEL
  add constraint PK_MM_INV_SHELVES_LABEL primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_INV_SHELVES_LABEL_TEMP
prompt ========================================
prompt
create table MM_INV_SHELVES_LABEL_TEMP
(
  shelves_addr  VARCHAR2(150),
  factory_code  VARCHAR2(20),
  part_no       VARCHAR2(20),
  supplier_no   VARCHAR2(20),
  standard_pack NUMBER,
  safe_stock    NUMBER,
  car_type      VARCHAR2(20),
  stack_layers  NUMBER,
  id            NUMBER not null,
  deal_flag     NUMBER,
  check_flag    VARCHAR2(2),
  check_result  VARCHAR2(2),
  part_short_no VARCHAR2(150),
  part_name     VARCHAR2(20),
  supplier_name VARCHAR2(150),
  "Column_16"   VARCHAR2(150)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_INV_SHELVES_LABEL_TEMP
  is 'MM_INV_SHELVES_LABEL_TEMP货架标签临时表
';
comment on column MM_INV_SHELVES_LABEL_TEMP.shelves_addr
  is 'SHELVES_ADDR
';
comment on column MM_INV_SHELVES_LABEL_TEMP.factory_code
  is 'FACTORY_CODE
';
comment on column MM_INV_SHELVES_LABEL_TEMP.part_no
  is 'PART_NO
';
comment on column MM_INV_SHELVES_LABEL_TEMP.supplier_no
  is 'SUPPLIER_NO
';
comment on column MM_INV_SHELVES_LABEL_TEMP.standard_pack
  is 'STANDARD_PACK
';
comment on column MM_INV_SHELVES_LABEL_TEMP.safe_stock
  is 'SAFE_STOCK
';
comment on column MM_INV_SHELVES_LABEL_TEMP.car_type
  is 'CAR_TYPE
';
comment on column MM_INV_SHELVES_LABEL_TEMP.stack_layers
  is 'STACK_LAYERS
';
comment on column MM_INV_SHELVES_LABEL_TEMP.id
  is 'ID
';
comment on column MM_INV_SHELVES_LABEL_TEMP.deal_flag
  is 'DEAL_FLAG
';
comment on column MM_INV_SHELVES_LABEL_TEMP.check_flag
  is 'CHECK_FLAG
';
comment on column MM_INV_SHELVES_LABEL_TEMP.check_result
  is 'CHECK_RESULT
';
comment on column MM_INV_SHELVES_LABEL_TEMP.part_short_no
  is 'PART_SHORT_NO
';
comment on column MM_INV_SHELVES_LABEL_TEMP.part_name
  is 'PART_NAME
';
comment on column MM_INV_SHELVES_LABEL_TEMP.supplier_name
  is 'SUPPLIER_NAME
';
comment on column MM_INV_SHELVES_LABEL_TEMP."Column_16"
  is 'Column_16
';
alter table MM_INV_SHELVES_LABEL_TEMP
  add constraint PK_MM_INV_SHELVES_LABEL_TEMP primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_INV_SW_REC
prompt ============================
prompt
create table MM_INV_SW_REC
(
  rec_no       VARCHAR2(50) not null,
  purchase_no  VARCHAR2(50),
  factory_code VARCHAR2(10),
  rec_user     VARCHAR2(30),
  rec_time     DATE default sysdate,
  rec_ip       VARCHAR2(50),
  deal_flag    NUMBER(1) default 0,
  deal_time    DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_INV_SW_REC
  is 'MM_INV_SW_REC采购订单收货表
';
comment on column MM_INV_SW_REC.rec_no
  is '收货单号
';
comment on column MM_INV_SW_REC.purchase_no
  is '采购单号
';
comment on column MM_INV_SW_REC.factory_code
  is '工厂
';
comment on column MM_INV_SW_REC.rec_user
  is '收货人
';
comment on column MM_INV_SW_REC.rec_time
  is '收货时间
';
comment on column MM_INV_SW_REC.rec_ip
  is '收货IP
';
comment on column MM_INV_SW_REC.deal_flag
  is '接口处理标志
0-未处理
1-已处理';
comment on column MM_INV_SW_REC.deal_time
  is '接口处理时间
';
create index IDX_MM_INV_SW_REC1 on MM_INV_SW_REC (REC_TIME)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_INV_SW_REC2 on MM_INV_SW_REC (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_INV_SW_REC3 on MM_INV_SW_REC (PURCHASE_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_INV_SW_REC
  add constraint PK_MM_INV_SW_REC primary key (REC_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_INV_SW_REC_DETAIL
prompt ===================================
prompt
create table MM_INV_SW_REC_DETAIL
(
  rec_no        VARCHAR2(50),
  rec_rowno     NUMBER(10),
  part_no       VARCHAR2(20),
  rec_qty       NUMBER(10),
  rec_nums      NUMBER(10),
  creation_time DATE default sysdate
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_INV_SW_REC_DETAIL
  is 'MM_INV_SW_REC_DETAIL采购订单收货明细表
';
comment on column MM_INV_SW_REC_DETAIL.rec_no
  is '收货单号
';
comment on column MM_INV_SW_REC_DETAIL.rec_rowno
  is '收货单行号
';
comment on column MM_INV_SW_REC_DETAIL.part_no
  is '零件编号
';
comment on column MM_INV_SW_REC_DETAIL.rec_qty
  is '收货数量
';
comment on column MM_INV_SW_REC_DETAIL.rec_nums
  is '订单行收货次数
';
comment on column MM_INV_SW_REC_DETAIL.creation_time
  is '创建时间
';
create index IDX_MM_INV_SW_REC_DETAIL1 on MM_INV_SW_REC_DETAIL (REC_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_INV_SW_REC_DETAIL2 on MM_INV_SW_REC_DETAIL (REC_ROWNO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_INV_SW_REC_DETAIL
  add constraint AK_KEY_1_MM_INV_S unique (REC_NO, REC_ROWNO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_JISO_INS
prompt ==========================
prompt
create table MM_JISO_INS
(
  ins_no                 VARCHAR2(50) not null,
  plan_code              VARCHAR2(20) not null,
  partgroup_no           VARCHAR2(20) not null,
  arr_depot              VARCHAR2(20) not null,
  sup_factory            VARCHAR2(20) not null,
  ins_partgroup_seqno    VARCHAR2(30),
  ins_supfactory_seqno   VARCHAR2(30),
  partgroup_name         VARCHAR2(100),
  order_flg              NUMBER(1),
  gen_ins_way            NUMBER(1),
  supplier_no            VARCHAR2(20),
  supplier_name          VARCHAR2(150),
  route_code             VARCHAR2(20),
  route_desc             VARCHAR2(100),
  car_batch              NUMBER(5),
  car_batch_seqno        NUMBER(19),
  location               VARCHAR2(20),
  distri_person          VARCHAR2(30),
  prepare_product_seqno  NUMBER(19),
  dispatch_product_seqno NUMBER(19),
  delivery_product_seqno NUMBER(19),
  arrive_product_seqno   NUMBER(19),
  distri_product_seqno   NUMBER(19),
  prepare_time           DATE,
  dispatch_time          DATE,
  delivery_time          DATE,
  arrive_time            DATE,
  distri_time            DATE,
  use_location_num       VARCHAR2(10),
  arrive_batch_no        NUMBER(5),
  is_manu_deal           NUMBER(1) default 0,
  manu_req_time          DATE,
  manu_req_user          VARCHAR2(30),
  manu_req_ip            VARCHAR2(30),
  print_status           NUMBER(1) default 0,
  print_time             DATE,
  print_user             VARCHAR2(30),
  print_user_ip          VARCHAR2(30),
  creation_time          DATE default SYSDATE,
  order_no               VARCHAR2(50),
  order_deal_flag        NUMBER(1) default 0,
  order_deal_time        DATE,
  deal_flag              NUMBER(1) default 0,
  deal_time              DATE,
  deal_flag_in           NUMBER(1) default 0,
  deal_time_in           DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column MM_JISO_INS.order_flg
  is '数据字典类型“TRUE_FALSE"
';
comment on column MM_JISO_INS.gen_ins_way
  is '0：不考虑出货地切换
1：考虑出货地切换
';
comment on column MM_JISO_INS.supplier_name
  is '专属供应商订单';
comment on column MM_JISO_INS.car_batch
  is '车次:1-999循环';
comment on column MM_JISO_INS.car_batch_seqno
  is '车次一直递增';
comment on column MM_JISO_INS.use_location_num
  is 'SYS：系统参数
WC：车间
LOC：落点';
comment on column MM_JISO_INS.is_manu_deal
  is '数据字典类型“TRUE_FALSE"';
comment on column MM_JISO_INS.print_status
  is '数据字典类型“PRINT_STATUS”
';
comment on column MM_JISO_INS.order_deal_flag
  is '数据字典类型: “JIT_GEN_ORDER_STATUS”';
comment on column MM_JISO_INS.deal_flag
  is '0：未处理
1：已处理';
comment on column MM_JISO_INS.deal_flag_in
  is '0：未处理
1：已处理';
create index IDX_MM_JISO_INS1 on MM_JISO_INS (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_JISO_INS2 on MM_JISO_INS (PRINT_STATUS)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_JISO_INS3 on MM_JISO_INS (CREATION_TIME)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_JISO_INS4 on MM_JISO_INS (ORDER_DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_JISO_INS5 on MM_JISO_INS (DEAL_FLAG_IN)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_JISO_INS
  add constraint PK_MM_JISO_INS_HEAD primary key (INS_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_JISO_INS_DETAIL
prompt =================================
prompt
create table MM_JISO_INS_DETAIL
(
  ins_no             VARCHAR2(50),
  order_no           VARCHAR2(50),
  sale_no            VARCHAR2(50),
  sale_rowno         NUMBER(10),
  part_no            VARCHAR2(40),
  require_num        NUMBER(10),
  part_short_no      VARCHAR2(10),
  part_name          VARCHAR2(150),
  part_mark          VARCHAR2(20),
  remark_flag        VARCHAR2(2),
  vin                VARCHAR2(20),
  model_code         VARCHAR2(20),
  phase              VARCHAR2(20),
  pass_time          DATE,
  wc_seqno           VARCHAR2(10),
  pl_seqno           VARCHAR2(20),
  kb_product_seqno   NUMBER(19),
  kb_time            DATE,
  creation_time      DATE default SYSDATE,
  last_modified_time DATE default SYSDATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_JISO_INS_DETAIL
  is '按指示票、产品订单号、零件编号汇总';
comment on column MM_JISO_INS_DETAIL.remark_flag
  is '0：未切换
1：已切换';
create index IDX_MM_JISO_INS_DETAIL1 on MM_JISO_INS_DETAIL (INS_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_JISO_ORDER
prompt ============================
prompt
create table MM_JISO_ORDER
(
  order_no                 VARCHAR2(30) not null,
  plan_code                VARCHAR2(20) not null,
  arr_depot                VARCHAR2(20) not null,
  sup_factory              VARCHAR2(20) not null,
  route_code               VARCHAR2(20) not null,
  car_batch_seqno          NUMBER(19),
  supplier_no              VARCHAR2(20),
  supplier_name            VARCHAR2(100),
  route_desc               VARCHAR2(100),
  car_batch                NUMBER(5),
  prepare_product_seqno    NUMBER(19),
  dispatch_product_seqno   NUMBER(19),
  delivery_product_seqno   NUMBER(19),
  arrive_product_seqno     NUMBER(19),
  distri_product_seqno     NUMBER(19),
  prepare_time             DATE,
  dispatch_time            DATE,
  delivery_time            DATE,
  arrive_time              DATE,
  distri_time              DATE,
  arrive_batch_no          NUMBER(5),
  is_manu_deal             NUMBER(1) default 0,
  manu_req_time            DATE,
  manu_req_user            VARCHAR2(30),
  manu_req_ip              VARCHAR2(30),
  print_status             NUMBER(1) default 0,
  print_time               DATE,
  print_user               VARCHAR2(30),
  print_user_ip            VARCHAR2(30),
  purchase_orderno         VARCHAR2(50),
  creation_time            DATE default SYSDATE,
  arrive_status            NUMBER(1) default 0,
  arrive_count             NUMBER(5) default 0,
  actual_arr_product_seqno NUMBER(19),
  deal_flag                NUMBER(1) default 0,
  deal_time                DATE,
  deal_flag_in             NUMBER(1) default 0,
  deal_time_in             DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column MM_JISO_ORDER.car_batch_seqno
  is '车次一直递增';
comment on column MM_JISO_ORDER.supplier_name
  is '专属供应商订单';
comment on column MM_JISO_ORDER.car_batch
  is '车次:1-999循环';
comment on column MM_JISO_ORDER.is_manu_deal
  is '数据字典类型“TRUE_FALSE"';
comment on column MM_JISO_ORDER.print_status
  is '数据字典类型“PRINT_STATUS”
';
comment on column MM_JISO_ORDER.arrive_status
  is '数据字典类型“ARRIVE_STATUS”';
comment on column MM_JISO_ORDER.deal_flag
  is '0：未处理
1：已处理';
comment on column MM_JISO_ORDER.deal_flag_in
  is '0：未处理
1：已处理';
create index IDX_MM_JISO_ORDER1 on MM_JISO_ORDER (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index IDX_MM_JISO_ORDER2 on MM_JISO_ORDER (PRINT_STATUS)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index IDX_MM_JISO_ORDER3 on MM_JISO_ORDER (CREATION_TIME)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index IDX_MM_JISO_ORDER4 on MM_JISO_ORDER (DEAL_FLAG_IN)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MM_JISO_ORDER
  add constraint PK_MM_JISO_ORDER primary key (ORDER_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_JISO_ORDER_DETAIL
prompt ===================================
prompt
create table MM_JISO_ORDER_DETAIL
(
  order_no                 VARCHAR2(50) not null,
  order_rowno              NUMBER(10) not null,
  part_no                  VARCHAR2(40),
  require_num              NUMBER(10),
  part_short_no            VARCHAR2(10),
  part_name                VARCHAR2(150),
  standard_package         NUMBER(10),
  creation_time            DATE default SYSDATE,
  last_modified_time       DATE default SYSDATE,
  arrive_status            NUMBER(1) default 0,
  arrive_count             NUMBER(5) default 0,
  actual_arr_product_seqno NUMBER(19),
  arrive_num               NUMBER(19)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column MM_JISO_ORDER_DETAIL.arrive_status
  is '数据字典类型“ARRIVE_STATUS”';
create index IDX_MM_JISO_ORDER_DETAIL1 on MM_JISO_ORDER_DETAIL (ORDER_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MM_JISO_ORDER_DETAIL
  add constraint PK_MM_JISO_ORDER_DETAIL primary key (ORDER_NO, ORDER_ROWNO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_JISO_PART_PLAN
prompt ================================
prompt
create table MM_JISO_PART_PLAN
(
  id                 NUMBER(19) not null,
  supplier_no        VARCHAR2(30),
  arr_depot          VARCHAR2(20),
  partgroup_no       VARCHAR2(20),
  part_no            VARCHAR2(50),
  require_num        NUMBER(10),
  offline_time       DATE,
  creation_time      DATE,
  creation_user      VARCHAR2(50),
  last_modified_time DATE,
  last_modified_user VARCHAR2(50),
  factory_code       VARCHAR2(10),
  model_code         VARCHAR2(10),
  workcenter         VARCHAR2(20)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column MM_JISO_PART_PLAN.workcenter
  is '车间';
alter table MM_JISO_PART_PLAN
  add constraint PK_MM_JISO_PART_PLAN primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_JIT_LABEL
prompt ===========================
prompt
create table MM_JIT_LABEL
(
  order_no               VARCHAR2(50),
  label_rowno            NUMBER(19),
  plan_code              VARCHAR2(20),
  part_no                VARCHAR2(40),
  location               VARCHAR2(20),
  require_num            NUMBER(10),
  part_short_no          VARCHAR2(10),
  part_name              VARCHAR2(150),
  supplier_no            VARCHAR2(20),
  supplier_name          VARCHAR2(150),
  ship_depot             VARCHAR2(20),
  arr_depot              VARCHAR2(20),
  standard_package       NUMBER(10),
  distri_package         NUMBER(10),
  unload_port            VARCHAR2(20),
  prepare_person         VARCHAR2(30),
  storage                VARCHAR2(30),
  distri_person          VARCHAR2(30),
  kb_product_seqno       NUMBER(19),
  prepare_product_seqno  NUMBER(19),
  dispatch_product_seqno NUMBER(19),
  delivery_product_seqno NUMBER(19),
  arrive_product_seqno   NUMBER(19),
  distri_product_seqno   NUMBER(19),
  assemble_product_seqno NUMBER(19),
  kb_time                DATE,
  prepare_time           DATE,
  dispatch_time          DATE,
  delivery_time          DATE,
  arrive_time            DATE,
  distri_time            DATE,
  assemble_time          DATE,
  prepare_batch_no       NUMBER(5),
  kb_batch_no            NUMBER(5),
  label_part_rowno       NUMBER(10),
  print_status           NUMBER(1) default 0,
  print_time             DATE,
  print_user             VARCHAR2(30),
  print_user_ip          VARCHAR2(30),
  creation_time          DATE default SYSDATE,
  deal_flag              NUMBER(1) default 0,
  deal_time              DATE,
  deal_flag_in           NUMBER(1) default 0,
  deal_time_in           DATE,
  bar_uuid               VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column MM_JIT_LABEL.print_status
  is '数据字典类型“PRINT_STATUS"';
comment on column MM_JIT_LABEL.deal_flag
  is '0：未处理
1：已处理';
comment on column MM_JIT_LABEL.deal_flag_in
  is '0：未处理
1：已处理';
comment on column MM_JIT_LABEL.bar_uuid
  is 'UUID';
create index IDX_MM_JIT_LABEL1 on MM_JIT_LABEL (PRINT_STATUS)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_JIT_LABEL2 on MM_JIT_LABEL (CREATION_TIME)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_JIT_LABEL3 on MM_JIT_LABEL (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_JIT_LABEL4 on MM_JIT_LABEL (ORDER_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_JIT_LABEL5 on MM_JIT_LABEL (DEAL_FLAG_IN)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_JIT_LABEL
  add constraint AK_KEY_1_MM_JIT_L unique (ORDER_NO, LABEL_ROWNO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_JIT_ORDER
prompt ===========================
prompt
create table MM_JIT_ORDER
(
  order_no                  VARCHAR2(50) not null,
  plan_code                 VARCHAR2(20),
  ship_depot                VARCHAR2(20),
  arr_depot                 VARCHAR2(20),
  ship_depot_type           VARCHAR2(10),
  sup_factory               VARCHAR2(30),
  supplier_no               VARCHAR2(30),
  supplier_name             VARCHAR2(100),
  s_prepare_product_seqno   NUMBER(19),
  e_prepare_product_seqno   NUMBER(19),
  dispatch_product_seqno    NUMBER(19),
  delivery_product_seqno    NUMBER(19),
  arrive_product_seqno      NUMBER(19),
  prepare_time              DATE,
  dispatch_time             DATE,
  delivery_time             DATE,
  arrive_time               DATE,
  s_prepare_batch_no        NUMBER(5),
  e_prepare_batch_no        NUMBER(5),
  delivery_batch_no         NUMBER(5),
  print_status              NUMBER(2) default 0,
  print_time                DATE,
  print_user                VARCHAR2(30),
  print_user_ip             VARCHAR2(30),
  order_no_batch            VARCHAR2(50),
  order_no_diffseq          NUMBER(3) default 0,
  purchase_orderno          VARCHAR2(50),
  creation_time             DATE default SYSDATE,
  prepare_status            NUMBER(1) default 0,
  prepare_count             NUMBER(5) default 0,
  actual_pre_product_seqno  NUMBER(19),
  delivery_status           NUMBER(1) default 0,
  delivery_count            NUMBER(5) default 0,
  actual_deli_product_seqno NUMBER(19),
  arrive_status             NUMBER(1) default 0,
  arrive_count              NUMBER(5) default 0,
  actual_arr_product_seqno  NUMBER(19),
  deal_flag                 NUMBER(1) default 0,
  deal_time                 DATE,
  deal_flag_in              NUMBER(1) default 0,
  deal_time_in              DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column MM_JIT_ORDER.ship_depot_type
  is '数据字典类型“PUB_SHIP_DEPOT_TYPE”';
comment on column MM_JIT_ORDER.sup_factory
  is '专属供应商订单';
comment on column MM_JIT_ORDER.supplier_no
  is '专属供应商订单';
comment on column MM_JIT_ORDER.supplier_name
  is '专属供应商订单';
comment on column MM_JIT_ORDER.print_status
  is '数据字典类型“PRINT_STATUS”';
comment on column MM_JIT_ORDER.order_no_diffseq
  is '>0为差异单';
comment on column MM_JIT_ORDER.prepare_status
  is '数据字典类型“PREPARE_STATUS”';
comment on column MM_JIT_ORDER.delivery_status
  is '数据字典类型“DELIVERY_STATUS”';
comment on column MM_JIT_ORDER.arrive_status
  is '数据字典类型“ARRIVE_STATUS”';
comment on column MM_JIT_ORDER.deal_flag
  is '0：未处理
1：已处理';
comment on column MM_JIT_ORDER.deal_flag_in
  is '0：未处理
1：已处理';
create index IDX_MM_JIT_ORDER1 on MM_JIT_ORDER (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index IDX_MM_JIT_ORDER2 on MM_JIT_ORDER (CREATION_TIME)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index IDX_MM_JIT_ORDER3 on MM_JIT_ORDER (S_PREPARE_PRODUCT_SEQNO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index IDX_MM_JIT_ORDER4 on MM_JIT_ORDER (DEAL_FLAG_IN)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MM_JIT_ORDER
  add constraint PK_MM_JIT_ORDER primary key (ORDER_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_JIT_ORDER_DETAIL
prompt ==================================
prompt
create table MM_JIT_ORDER_DETAIL
(
  order_no                  VARCHAR2(50),
  order_rowno               NUMBER(10),
  part_no                   VARCHAR2(40),
  location                  VARCHAR2(20),
  require_num               NUMBER(10),
  part_short_no             VARCHAR2(10),
  part_name                 VARCHAR2(150),
  standard_package          NUMBER(10),
  box_num                   NUMBER(5),
  supplier_no               VARCHAR2(20),
  supplier_name             VARCHAR2(150),
  purchase_orderno          VARCHAR2(50),
  prepare_status            NUMBER(1) default 0,
  prepare_count             NUMBER(5) default 0,
  actual_pre_product_seqno  NUMBER(19),
  prepare_num               NUMBER(10) default 0,
  delivery_status           NUMBER(1) default 0,
  delivery_count            NUMBER(5) default 0,
  actual_deli_product_seqno NUMBER(19),
  delivery_num              NUMBER(10) default 0,
  arrive_status             NUMBER(1) default 0,
  arrive_count              NUMBER(5) default 0,
  actual_arr_product_seqno  NUMBER(19),
  arrive_num                NUMBER(10) default 0,
  creation_time             DATE default SYSDATE,
  last_modified_time        DATE default SYSDATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column MM_JIT_ORDER_DETAIL.prepare_status
  is '数据字典类型“PREPARE_STATUS”';
comment on column MM_JIT_ORDER_DETAIL.delivery_status
  is '数据字典类型“DELIVERY_STATUS”';
comment on column MM_JIT_ORDER_DETAIL.arrive_status
  is '数据字典类型“ARRIVE_STATUS”';
create index IDX_JIT_ORDER_DETAIL1 on MM_JIT_ORDER_DETAIL (ORDER_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MM_JIT_ORDER_DETAIL
  add constraint AK_KEY_1_MM_JIT_O unique (ORDER_NO, ORDER_ROWNO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_MON_ALLOW_DEVIATION
prompt =====================================
prompt
create table MM_MON_ALLOW_DEVIATION
(
  id           NUMBER(10) not null,
  route_code   VARCHAR2(20),
  error_date   NUMBER(10),
  factory_code VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_MON_ALLOW_DEVIATION
  is 'MM_MON_ALLOW_DEVIATION物流允许误差维护
';
comment on column MM_MON_ALLOW_DEVIATION.id
  is 'ID
';
comment on column MM_MON_ALLOW_DEVIATION.route_code
  is '集货线路
';
comment on column MM_MON_ALLOW_DEVIATION.error_date
  is '允许误差
';
comment on column MM_MON_ALLOW_DEVIATION.factory_code
  is '工厂代码
';
alter table MM_MON_ALLOW_DEVIATION
  add constraint PK_MM_MON_ALLOW_DEVIATION primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_MON_KB
prompt ========================
prompt
create table MM_MON_KB
(
  id                 NUMBER(19) not null,
  factory_code       VARCHAR2(10) not null,
  kb_code            VARCHAR2(20) not null,
  production_line    VARCHAR2(20),
  workcenter         VARCHAR2(20),
  station_code       VARCHAR2(20),
  kb_name            VARCHAR2(200),
  kb_type            VARCHAR2(10),
  batch_cycle_num    NUMBER(5),
  process_cycle_num  NUMBER(5),
  curr_batch_no      NUMBER(5),
  curr_process_no    NUMBER(5),
  curr_batch_seqno   NUMBER(19),
  product_seqno      NUMBER(19),
  is_edit            NUMBER(1) default 1,
  remark             VARCHAR2(256),
  max_pass_time      DATE default SYSDATE,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  last_modified_ip   VARCHAR2(30)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_MON_KB
  is 'MM_MON_KB看板信息维护
';
comment on column MM_MON_KB.id
  is 'ID
';
comment on column MM_MON_KB.factory_code
  is '工厂
';
comment on column MM_MON_KB.kb_code
  is '看板代码
';
comment on column MM_MON_KB.production_line
  is '产线
';
comment on column MM_MON_KB.workcenter
  is '车间
';
comment on column MM_MON_KB.station_code
  is '工位
数据字典类型“MON_KB_STATION”';
comment on column MM_MON_KB.kb_name
  is '看板名称
';
comment on column MM_MON_KB.kb_type
  is '所属业务
数据字典类型“MON_KB_TYPE”';
comment on column MM_MON_KB.batch_cycle_num
  is '批次循环基数
';
comment on column MM_MON_KB.process_cycle_num
  is '进度循环基数
';
comment on column MM_MON_KB.curr_batch_no
  is '当前批次
';
comment on column MM_MON_KB.curr_process_no
  is '当前进度
';
comment on column MM_MON_KB.curr_batch_seqno
  is '当前批次流水号
';
comment on column MM_MON_KB.product_seqno
  is '产品流水号
';
comment on column MM_MON_KB.is_edit
  is '是否可编辑
数据字典类型“TRUE_FALSE"';
comment on column MM_MON_KB.remark
  is '备注
';
comment on column MM_MON_KB.max_pass_time
  is '已跳动队列最大过点时间
';
comment on column MM_MON_KB.creation_user
  is '创建人
';
comment on column MM_MON_KB.creation_time
  is '创建时间
';
comment on column MM_MON_KB.last_modified_user
  is '最后修改用户
';
comment on column MM_MON_KB.last_modified_time
  is '最后修改时间
';
comment on column MM_MON_KB.last_modified_ip
  is '最后修改IP
';
alter table MM_MON_KB
  add constraint PK_MM_MON_KB primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_MON_KB
  add constraint AK_MM_MON_KB unique (KB_CODE, FACTORY_CODE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_MP_ADJ_SUP_FEEDBACK
prompt =====================================
prompt
create table MM_MP_ADJ_SUP_FEEDBACK
(
  id            NUMBER(19) not null,
  factory_code  VARCHAR2(10),
  part_no       VARCHAR2(20),
  supplier_no   VARCHAR2(20),
  sup_factory   VARCHAR2(20),
  diff_num      NUMBER(10),
  supply_date   DATE,
  is_supply     NUMBER(1),
  supply_reason VARCHAR2(200),
  feedback_time DATE default SYSDATE,
  creation_user VARCHAR2(30),
  creation_time DATE default sysdate
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_MP_ADJ_SUP_FEEDBACK
  is 'MM_MP_ADJ_SUP_FEEDBACK 调整计划供应能力反馈
调整计划供应能力反馈';
comment on column MM_MP_ADJ_SUP_FEEDBACK.id
  is 'ID
';
comment on column MM_MP_ADJ_SUP_FEEDBACK.factory_code
  is '工厂
工厂';
comment on column MM_MP_ADJ_SUP_FEEDBACK.part_no
  is '零件编号
零件编号';
comment on column MM_MP_ADJ_SUP_FEEDBACK.supplier_no
  is '供应商代码
供应商代码';
comment on column MM_MP_ADJ_SUP_FEEDBACK.sup_factory
  is '供应商出货地
出货地';
comment on column MM_MP_ADJ_SUP_FEEDBACK.diff_num
  is '调整差异数量
差异数量';
comment on column MM_MP_ADJ_SUP_FEEDBACK.supply_date
  is '供应日期
供应日期';
comment on column MM_MP_ADJ_SUP_FEEDBACK.is_supply
  is '是否可供应
0-不能供应
1-可供应';
comment on column MM_MP_ADJ_SUP_FEEDBACK.supply_reason
  is '原因
原因';
comment on column MM_MP_ADJ_SUP_FEEDBACK.feedback_time
  is '反馈时间
反馈时间';
comment on column MM_MP_ADJ_SUP_FEEDBACK.creation_user
  is '创建人
';
comment on column MM_MP_ADJ_SUP_FEEDBACK.creation_time
  is '创建时间
';

prompt
prompt Creating table MM_PDA_USER
prompt ==========================
prompt
create table MM_PDA_USER
(
  user_id            VARCHAR2(20) not null,
  user_name          VARCHAR2(30),
  name               VARCHAR2(30),
  factory_code       VARCHAR2(10),
  user_pwd           VARCHAR2(50),
  login_num          NUMBER(10),
  login_ip           VARCHAR2(50),
  login_time         DATE,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column MM_PDA_USER.user_id
  is '用户ID';
comment on column MM_PDA_USER.user_name
  is '用户名';
comment on column MM_PDA_USER.name
  is '姓名';
comment on column MM_PDA_USER.factory_code
  is '工厂';
comment on column MM_PDA_USER.user_pwd
  is '密码';
comment on column MM_PDA_USER.login_num
  is '登录次数';
comment on column MM_PDA_USER.login_ip
  is '登录IP';
comment on column MM_PDA_USER.login_time
  is '登录时间';
comment on column MM_PDA_USER.creation_user
  is '创建人';
comment on column MM_PDA_USER.creation_time
  is '创建时间';
comment on column MM_PDA_USER.last_modified_user
  is '最后修改用户';
comment on column MM_PDA_USER.last_modified_time
  is '最后修改时间';
alter table MM_PDA_USER
  add constraint PK_MM_PDA_USER primary key (USER_ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_PKG_BOX
prompt =========================
prompt
create table MM_PKG_BOX
(
  id                 NUMBER(19) not null,
  box_code           VARCHAR2(30),
  box_type           NUMBER(2),
  pack_length        NUMBER(19,3),
  pack_width         NUMBER(19,3),
  pack_height        NUMBER(19,3),
  status             NUMBER(2) default 0,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  factory_code       VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column MM_PKG_BOX.box_code
  is '箱code';
comment on column MM_PKG_BOX.box_type
  is '"1-台车
2-EU箱
3-中空板
4-铁架"';
comment on column MM_PKG_BOX.pack_length
  is '包装长';
comment on column MM_PKG_BOX.pack_width
  is '包装宽';
comment on column MM_PKG_BOX.pack_height
  is '包装高';
comment on column MM_PKG_BOX.status
  is '"0-停用
1-启用"';
alter table MM_PKG_BOX
  add constraint PK_MM_PKG_BOX primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_PKG_BOX
  add constraint AK_MM_PKG_BOX unique (BOX_CODE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_PKG_BOX_QTY
prompt =============================
prompt
create table MM_PKG_BOX_QTY
(
  id                 NUMBER(19) not null,
  proposal_id        NUMBER(19),
  provide_qty        NUMBER(10),
  box_require_qty    NUMBER(10),
  hair_note_model    VARCHAR2(50),
  plan_date          DATE,
  delay_reason       VARCHAR2(300),
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  factory_code       VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column MM_PKG_BOX_QTY.provide_qty
  is '已纳入量';
comment on column MM_PKG_BOX_QTY.box_require_qty
  is '箱子需求量';
comment on column MM_PKG_BOX_QTY.hair_note_model
  is '发注模式';
comment on column MM_PKG_BOX_QTY.plan_date
  is '计划完成日期';
comment on column MM_PKG_BOX_QTY.delay_reason
  is '延迟原因';
alter table MM_PKG_BOX_QTY
  add constraint PK_MM_PKG_BOX_QTY primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_PKG_BOX_QTY
  add constraint AK_MM_PKG_BOX_QTY unique (PROPOSAL_ID, FACTORY_CODE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_PKG_PART
prompt ==========================
prompt
create table MM_PKG_PART
(
  id                 NUMBER(19) not null,
  car_type           VARCHAR2(20),
  project            VARCHAR2(50),
  part_no            VARCHAR2(20),
  part_resp_user     VARCHAR2(30),
  supplier_no        VARCHAR2(20),
  status             NUMBER(1),
  creation_user      VARCHAR2(30),
  creation_time      DATE,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  factory_code       VARCHAR2(10),
  purchase_type      VARCHAR2(10),
  email_flag         NUMBER(1) default 0
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column MM_PKG_PART.status
  is '0：新设 1：留用';
create index IDX_MM_PKG_PART1 on MM_PKG_PART (EMAIL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_PKG_PART
  add constraint PK_MM_PKG_PART primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_PKG_PART
  add constraint AK_MM_PKG_PART unique (CAR_TYPE, PART_NO, SUPPLIER_NO, PURCHASE_TYPE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_PKG_PROPOSAL
prompt ==============================
prompt
create table MM_PKG_PROPOSAL
(
  id                 NUMBER(19) not null,
  car_type           VARCHAR2(20),
  supplier_no        VARCHAR2(20),
  part_no            VARCHAR2(20),
  proposal_status    NUMBER(1) default 0,
  pack_type          NUMBER(1),
  box_type           NUMBER(2),
  reply_limit_date   DATE,
  is_com_pack        NUMBER(1),
  status             NUMBER(1),
  sign_pro_file      NUMBER(19),
  eff_start          DATE,
  eff_end            DATE,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  factory_code       VARCHAR2(10),
  part_resp_user     VARCHAR2(30),
  project            VARCHAR2(50),
  purchase_type      VARCHAR2(10),
  com_pack_remark    VARCHAR2(100),
  is_show_change     NUMBER(1) default 0,
  email_flag         NUMBER(1) default 0,
  check_remark       VARCHAR2(300)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column MM_PKG_PROPOSAL.car_type
  is '车型';
comment on column MM_PKG_PROPOSAL.supplier_no
  is '供应商代码';
comment on column MM_PKG_PROPOSAL.part_no
  is '零件编号';
comment on column MM_PKG_PROPOSAL.proposal_status
  is '"0-待发起
1-发起提案
2-待审核
3-提案通过
4-提案不通过
5-实物通过
6-实物不通过
7-终止"';
comment on column MM_PKG_PROPOSAL.pack_type
  is '"0-非台车
1-台车"';
comment on column MM_PKG_PROPOSAL.box_type
  is '0：托盘 1：台车 2：EU箱 3：其他';
comment on column MM_PKG_PROPOSAL.reply_limit_date
  is '回复期限';
comment on column MM_PKG_PROPOSAL.is_com_pack
  is '"0-否
1-是"';
comment on column MM_PKG_PROPOSAL.status
  is '"1-新设
2-留用"';
comment on column MM_PKG_PROPOSAL.eff_start
  is '生效时间';
comment on column MM_PKG_PROPOSAL.eff_end
  is '失效时间';
comment on column MM_PKG_PROPOSAL.is_show_change
  is '0：否 1：是 2：两边界面都显示';
comment on column MM_PKG_PROPOSAL.email_flag
  is '"0未发送
1已发送"';
create index IDX_MM_PKG_PROPOSAL1 on MM_PKG_PROPOSAL (CREATION_TIME)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_PKG_PROPOSAL2 on MM_PKG_PROPOSAL (PROPOSAL_STATUS)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_PKG_PROPOSAL
  add constraint PK_MM_PKG_PROPOSAL primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_PKG_PROPOSAL_DETAIL
prompt =====================================
prompt
create table MM_PKG_PROPOSAL_DETAIL
(
  id                    NUMBER(19) not null,
  proposal_id           NUMBER(19),
  group_no              VARCHAR2(30),
  max_package_num       NUMBER(10),
  standard_package      NUMBER(10),
  part_weight           NUMBER(10,3),
  part_total_weight     NUMBER(10,3),
  pack_weight           NUMBER(10,3),
  part_length           NUMBER(10,3),
  part_width            NUMBER(10,3),
  part_height           NUMBER(10,3),
  empty_trolley_length  NUMBER(10,3),
  empty_trolley_width   NUMBER(10,3),
  empty_trolley_height  NUMBER(10,3),
  real_trolley_length   NUMBER(10,3),
  real_trolley_width    NUMBER(10,3),
  real_trolley_height   NUMBER(10,3),
  trolley_weight        NUMBER(10,3),
  total_weight          NUMBER(10,3),
  is_trolley_code       NUMBER(1),
  is_positioner         NUMBER(1),
  dust_cover            NUMBER(1),
  one_by_package        NUMBER(1),
  int_mate              NUMBER(1),
  board_location        VARCHAR2(10),
  wheel_diameter        NUMBER(10,3),
  word_desc             VARCHAR2(600),
  traction_rod_height   NUMBER(10,3),
  important_postion_pic NUMBER(19),
  empty_tro_front_pic   VARCHAR2(50),
  empty_tro_side_pic    VARCHAR2(50),
  real_tro_pic          VARCHAR2(50),
  box_code              VARCHAR2(20),
  pack_length           NUMBER(10,3),
  pack_width            NUMBER(10,3),
  pack_height           NUMBER(10,3),
  tray_length           NUMBER(10,3),
  tray_width            NUMBER(10,3),
  tray_height           NUMBER(10,3),
  work_require          VARCHAR2(500),
  single_part_pic       NUMBER(19),
  single_part_put_pic   NUMBER(19),
  pack_over_look_pic    NUMBER(19),
  pack_side_look_pic    NUMBER(19),
  creation_user         VARCHAR2(30),
  creation_time         DATE default sysdate,
  last_modified_user    VARCHAR2(30),
  last_modified_time    DATE,
  factory_code          VARCHAR2(10),
  sup_name              VARCHAR2(30),
  mobile                VARCHAR2(30),
  mail                  VARCHAR2(30),
  is_change             NUMBER(2)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column MM_PKG_PROPOSAL_DETAIL.group_no
  is '分组';
comment on column MM_PKG_PROPOSAL_DETAIL.max_package_num
  is '最大收容数';
comment on column MM_PKG_PROPOSAL_DETAIL.standard_package
  is '零件收容数';
comment on column MM_PKG_PROPOSAL_DETAIL.part_weight
  is '零件单重';
comment on column MM_PKG_PROPOSAL_DETAIL.part_total_weight
  is '零件总重量';
comment on column MM_PKG_PROPOSAL_DETAIL.part_length
  is '零件长';
comment on column MM_PKG_PROPOSAL_DETAIL.part_width
  is '零件宽';
comment on column MM_PKG_PROPOSAL_DETAIL.part_height
  is '零件高';
comment on column MM_PKG_PROPOSAL_DETAIL.empty_trolley_length
  is '空台车长';
comment on column MM_PKG_PROPOSAL_DETAIL.empty_trolley_width
  is '空台车宽';
comment on column MM_PKG_PROPOSAL_DETAIL.empty_trolley_height
  is '空台车高';
comment on column MM_PKG_PROPOSAL_DETAIL.real_trolley_length
  is '实台车长';
comment on column MM_PKG_PROPOSAL_DETAIL.real_trolley_width
  is '实台车宽';
comment on column MM_PKG_PROPOSAL_DETAIL.real_trolley_height
  is '实台车高';
comment on column MM_PKG_PROPOSAL_DETAIL.trolley_weight
  is '台车重量';
comment on column MM_PKG_PROPOSAL_DETAIL.total_weight
  is '总重量';
comment on column MM_PKG_PROPOSAL_DETAIL.is_trolley_code
  is '"0-无
1-有"';
comment on column MM_PKG_PROPOSAL_DETAIL.is_positioner
  is '"0-无
1-有"';
comment on column MM_PKG_PROPOSAL_DETAIL.dust_cover
  is '"0-无
1-有"';
comment on column MM_PKG_PROPOSAL_DETAIL.one_by_package
  is '"0-无
1-有"';
comment on column MM_PKG_PROPOSAL_DETAIL.board_location
  is 'L  / W';
comment on column MM_PKG_PROPOSAL_DETAIL.wheel_diameter
  is '车轮直径';
comment on column MM_PKG_PROPOSAL_DETAIL.word_desc
  is '文字说明';
comment on column MM_PKG_PROPOSAL_DETAIL.traction_rod_height
  is '牵引棒距地高';
comment on column MM_PKG_PROPOSAL_DETAIL.important_postion_pic
  is '重要部位图';
comment on column MM_PKG_PROPOSAL_DETAIL.empty_tro_front_pic
  is '空台车正视图';
comment on column MM_PKG_PROPOSAL_DETAIL.empty_tro_side_pic
  is '空台车侧视图';
comment on column MM_PKG_PROPOSAL_DETAIL.real_tro_pic
  is '实台车图';
comment on column MM_PKG_PROPOSAL_DETAIL.is_change
  is '0：否 1：是  是否是变更，用于触发器中作为判断，厂内到门户只有在变更时才把明细表中数据写到门户';
alter table MM_PKG_PROPOSAL_DETAIL
  add constraint PK_MM_PKG_PROPOSAL_DETAIL primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_PKG_PROPOSAL_DETAIL
  add constraint AK_MM_PKG_PROPOSAL_DETAIL unique (PROPOSAL_ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_PKG_TRAY_QTY
prompt ==============================
prompt
create table MM_PKG_TRAY_QTY
(
  id                 NUMBER(19) not null,
  car_type           VARCHAR2(20),
  supplier_no        VARCHAR2(20),
  provide_qty        NUMBER(10),
  tray_require_qty   NUMBER(10),
  plan_date          DATE,
  delay_reason       VARCHAR2(300),
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  factory_code       VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column MM_PKG_TRAY_QTY.car_type
  is '车型';
comment on column MM_PKG_TRAY_QTY.supplier_no
  is '供应商代码';
comment on column MM_PKG_TRAY_QTY.provide_qty
  is '已纳入量';
comment on column MM_PKG_TRAY_QTY.tray_require_qty
  is '箱子需求量';
comment on column MM_PKG_TRAY_QTY.plan_date
  is '计划完成日期';
comment on column MM_PKG_TRAY_QTY.delay_reason
  is '延迟原因';

prompt
prompt Creating table MM_PUB_DATA_DICT
prompt ===============================
prompt
create table MM_PUB_DATA_DICT
(
  id               NUMBER(19) not null,
  code_type        VARCHAR2(30),
  code_type_name   VARCHAR2(100),
  code_value       VARCHAR2(30),
  code_value_name  VARCHAR2(100),
  other_code_value VARCHAR2(30),
  remark           VARCHAR2(150),
  sort_no          NUMBER(5),
  is_edit          NUMBER(1),
  factory_code     VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table MM_PUB_DATA_DICT
  is '数据字典表';
comment on column MM_PUB_DATA_DICT.id
  is '逻辑主键';
comment on column MM_PUB_DATA_DICT.code_type
  is '类型代码';
comment on column MM_PUB_DATA_DICT.code_type_name
  is '类型名称';
comment on column MM_PUB_DATA_DICT.code_value
  is '编码';
comment on column MM_PUB_DATA_DICT.code_value_name
  is '编码名称';
comment on column MM_PUB_DATA_DICT.other_code_value
  is '第三方系统代码';
comment on column MM_PUB_DATA_DICT.remark
  is '备注';
comment on column MM_PUB_DATA_DICT.sort_no
  is '顺序';
comment on column MM_PUB_DATA_DICT.is_edit
  is '可否编辑';
comment on column MM_PUB_DATA_DICT.factory_code
  is '工厂';
alter table MM_PUB_DATA_DICT
  add constraint PK_MM_PUB_DATA_DICT primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MM_PUB_DATA_DICT
  add constraint AK_MM_PUB_DATA_DICT unique (CODE_TYPE, CODE_VALUE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_PUB_FACTORY
prompt =============================
prompt
create table MM_PUB_FACTORY
(
  id                 NUMBER(19),
  factory_code       VARCHAR2(32) not null,
  factory_name       VARCHAR2(128),
  creation_time      DATE,
  creation_user      VARCHAR2(30),
  last_modified_time DATE,
  last_modified_user VARCHAR2(30)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column MM_PUB_FACTORY.creation_time
  is '创建时间';
comment on column MM_PUB_FACTORY.creation_user
  is '创建人';
comment on column MM_PUB_FACTORY.last_modified_time
  is '最后修改时间';
comment on column MM_PUB_FACTORY.last_modified_user
  is '最后修改人';
alter table MM_PUB_FACTORY
  add constraint PK_MM_PUB_FACTORY primary key (FACTORY_CODE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_PUB_FACTORY_USER
prompt ==================================
prompt
create table MM_PUB_FACTORY_USER
(
  user_id       VARCHAR2(64) not null,
  factory_code  VARCHAR2(32) not null,
  creation_time DATE default SYSDATE,
  creation_user VARCHAR2(30)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column MM_PUB_FACTORY_USER.creation_time
  is '创建时间';
comment on column MM_PUB_FACTORY_USER.creation_user
  is '创建人';

prompt
prompt Creating table MM_PUB_PART
prompt ==========================
prompt
create table MM_PUB_PART
(
  part_no            VARCHAR2(30) not null,
  part_short_no      VARCHAR2(10),
  part_name_cn       VARCHAR2(150),
  part_name_en       VARCHAR2(150),
  part_spec          VARCHAR2(200),
  purchase_type      VARCHAR2(10),
  part_unit          VARCHAR2(20),
  creation_time      DATE default sysdate,
  last_modified_time DATE,
  factory_code       VARCHAR2(10) not null
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column MM_PUB_PART.part_short_no
  is '待确认维护系统';
comment on column MM_PUB_PART.part_spec
  is '主要为资材规格信息';
comment on column MM_PUB_PART.purchase_type
  is 'W；Z；AW';
alter table MM_PUB_PART
  add constraint PK_MM_PUB_PART primary key (PART_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_PUB_PART_SUPPLIER
prompt ===================================
prompt
create table MM_PUB_PART_SUPPLIER
(
  factory_code         VARCHAR2(10) not null,
  supplier_no          VARCHAR2(10) not null,
  sup_factory          VARCHAR2(10) not null,
  part_no              VARCHAR2(30) not null,
  min_order_num        NUMBER(10),
  standard_package     NUMBER(10),
  in_plan_forward_time NUMBER(10),
  eff_start            DATE,
  eff_end              DATE,
  creation_time        DATE default sysdate,
  last_modified_time   DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column MM_PUB_PART_SUPPLIER.in_plan_forward_time
  is '单位(天)';
comment on column MM_PUB_PART_SUPPLIER.eff_start
  is '格式：yyyy-MM-dd HH:mm:ss';
comment on column MM_PUB_PART_SUPPLIER.eff_end
  is '格式：yyyy-MM-dd HH:mm:ss';
alter table MM_PUB_PART_SUPPLIER
  add constraint PK_MM_PUB_PART_SUPPLIER primary key (PART_NO, SUPPLIER_NO, SUP_FACTORY, FACTORY_CODE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_PUB_PART_SUPPLIER_HIS
prompt =======================================
prompt
create table MM_PUB_PART_SUPPLIER_HIS
(
  factory_code         VARCHAR2(10) not null,
  supplier_no          VARCHAR2(10) not null,
  sup_factory          VARCHAR2(10),
  part_no              VARCHAR2(30) not null,
  min_order_num        NUMBER(10),
  standard_package     NUMBER(10),
  in_plan_forward_time NUMBER(10),
  eff_start            DATE,
  eff_end              DATE,
  creation_time        DATE default sysdate,
  last_modified_time   DATE,
  deal_flag            NUMBER(1) default 0,
  deal_time            DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_PUB_PART_SUPPLIER_HIS
  is 'MM_PUB_PART_SUPPLIER物料与供应商关系表';
comment on column MM_PUB_PART_SUPPLIER_HIS.factory_code
  is '工厂
工厂
';
comment on column MM_PUB_PART_SUPPLIER_HIS.supplier_no
  is '供应商编号
供应商编号
';
comment on column MM_PUB_PART_SUPPLIER_HIS.sup_factory
  is '供应商出货地
供应商出货地
';
comment on column MM_PUB_PART_SUPPLIER_HIS.part_no
  is '零件编号
零件编号
';
comment on column MM_PUB_PART_SUPPLIER_HIS.min_order_num
  is '最小采购量
最小采购量
';
comment on column MM_PUB_PART_SUPPLIER_HIS.standard_package
  is '规格包装数
规格包装数
';
comment on column MM_PUB_PART_SUPPLIER_HIS.in_plan_forward_time
  is '厂内提前期
厂内提前期
单位(天)';
comment on column MM_PUB_PART_SUPPLIER_HIS.eff_start
  is '生效日期
生效日期
格式：yyyy-MM-dd HH:mm:ss';
comment on column MM_PUB_PART_SUPPLIER_HIS.eff_end
  is '失效日期
失效日期
格式：yyyy-MM-dd HH:mm:ss';
comment on column MM_PUB_PART_SUPPLIER_HIS.creation_time
  is '创建时间
创建时间
';
comment on column MM_PUB_PART_SUPPLIER_HIS.last_modified_time
  is '最后修改时间
最后修改时间
';
comment on column MM_PUB_PART_SUPPLIER_HIS.deal_flag
  is '处理标识
处理标识
0 未处理 1已处理';
comment on column MM_PUB_PART_SUPPLIER_HIS.deal_time
  is '处理时间
处理时间
';
alter table MM_PUB_PART_SUPPLIER_HIS
  add constraint AK_PK_MM_PUB_PART_SUP unique (FACTORY_CODE, SUPPLIER_NO, PART_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_PUB_PART_UDA
prompt ==============================
prompt
create table MM_PUB_PART_UDA
(
  id                 NUMBER(19) not null,
  factory_code       VARCHAR2(10) not null,
  part_no            VARCHAR2(30) not null,
  supplier_no        VARCHAR2(10),
  sup_factory        VARCHAR2(10),
  part_short_no      VARCHAR2(10),
  part_name_cn       VARCHAR2(100),
  supplier_name      VARCHAR2(100),
  standard_package   NUMBER(10),
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MM_PUB_PART_UDA
  add constraint PK_MM_PUB_PART_UDA primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MM_PUB_PART_UDA
  add constraint AK_MM_PUB_PART_UDA unique (PART_NO, FACTORY_CODE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_PUB_PLAN_CODE
prompt ===============================
prompt
create table MM_PUB_PLAN_CODE
(
  plan_code          VARCHAR2(20) not null,
  plan_code_desc     VARCHAR2(50) not null,
  factory_code       VARCHAR2(10),
  production_line    VARCHAR2(20),
  workcenter         VARCHAR2(20),
  station_code       VARCHAR2(20),
  plan_code_type     VARCHAR2(10),
  is_auto_exec       NUMBER(1) default 0,
  exec_state         NUMBER(1) default 0,
  last_exec_time     DATE,
  kb_id              NUMBER(19),
  is_enable          NUMBER(1),
  is_show            NUMBER(1),
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  last_modified_ip   VARCHAR2(30)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column MM_PUB_PLAN_CODE.plan_code
  is '编码规则：工厂+信息点类型+过点工位
如：1_JISO_PAOFF';
comment on column MM_PUB_PLAN_CODE.plan_code_type
  is 'SPS：SPS
JISI：厂内同步
JISO：厂外同步
JITI：厂内拉动
JITO：厂外拉动';
comment on column MM_PUB_PLAN_CODE.is_auto_exec
  is '0：否
1：是';
comment on column MM_PUB_PLAN_CODE.exec_state
  is '0：未推算
1：推算中';
comment on column MM_PUB_PLAN_CODE.is_enable
  is '数据字典类型“TRUE_FALSE”';
comment on column MM_PUB_PLAN_CODE.is_show
  is '数据字典类型“TRUE_FALSE”';
alter table MM_PUB_PLAN_CODE
  add constraint PK_MM_PUB_PLAN_CODE primary key (PLAN_CODE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_PUB_PRO_ERROR
prompt ===============================
prompt
create table MM_PUB_PRO_ERROR
(
  id             NUMBER(19) not null,
  alert_type     VARCHAR2(50),
  error_name     NUMBER(19),
  error_desc     VARCHAR2(500),
  creation_date  DATE,
  key_name       VARCHAR2(100),
  send_mail_flag NUMBER(1) default 0,
  error_level    VARCHAR2(10),
  deal_flag      NUMBER(1) default 0,
  deal_time      DATE,
  deal_user      VARCHAR2(30)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
create index IDX_MM_PUB_PRO_ERROR1 on MM_PUB_PRO_ERROR (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_PUB_PRO_ERROR
  add constraint PK_MM_PUB_PRO_ERROR primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_PUB_PRO_PLAN
prompt ==============================
prompt
create table MM_PUB_PRO_PLAN
(
  factory_code       VARCHAR2(10) not null,
  order_no           VARCHAR2(50) not null,
  order_type         VARCHAR2(10),
  weon_time          DATE,
  afoff_time         DATE,
  sort_id            NUMBER(19),
  mtoc               VARCHAR2(64),
  model_code         VARCHAR2(10),
  phase              VARCHAR2(10),
  creation_time      DATE default sysdate,
  last_modified_time DATE,
  cal_status         NUMBER(1) default 0,
  cal_time           DATE,
  use_flag           NUMBER(1) default 0,
  dms_order_no       VARCHAR2(50),
  dms_order_row_no   NUMBER(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table MM_PUB_PRO_PLAN
  is '整车W+3周生产计划';
comment on column MM_PUB_PRO_PLAN.factory_code
  is '"工厂
工厂
"';
comment on column MM_PUB_PRO_PLAN.order_no
  is '"订单号
订单号
"';
comment on column MM_PUB_PRO_PLAN.order_type
  is '"订单类型
订单类型
"';
comment on column MM_PUB_PRO_PLAN.weon_time
  is '"焊装上线时间
焊装上线时间
"';
comment on column MM_PUB_PRO_PLAN.afoff_time
  is '"总装下线时间
总装下线时间
"';
comment on column MM_PUB_PRO_PLAN.sort_id
  is '"排序码
排序码
"';
comment on column MM_PUB_PRO_PLAN.mtoc
  is '"MTOC
MTOC
"';
comment on column MM_PUB_PRO_PLAN.model_code
  is '"车型
车型
A16、A28"';
comment on column MM_PUB_PRO_PLAN.phase
  is '"生产阶段
生产阶段
"';
comment on column MM_PUB_PRO_PLAN.creation_time
  is '"创建时间
创建时间
"';
comment on column MM_PUB_PRO_PLAN.last_modified_time
  is '"最后修改时间
最后修改时间
"';
comment on column MM_PUB_PRO_PLAN.cal_status
  is '"计算状态
计算状态
计算状态 默认值0未计算 1已计算 主要用于抓取新车型计划"';
comment on column MM_PUB_PRO_PLAN.cal_time
  is '"计算时间
计算时间
计算时间"';
comment on column MM_PUB_PRO_PLAN.use_flag
  is '"使用状态
使用状态
使用状态 默认值0 未使用 1已使用"';
create index IDX_MM_PUB_PRO_PLAN1 on MM_PUB_PRO_PLAN (AFOFF_TIME)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_PUB_PRO_PLAN
  add constraint PK_MM_PUB_PRO_PLAN primary key (FACTORY_CODE, ORDER_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_PUB_SUPPLIER
prompt ==============================
prompt
create table MM_PUB_SUPPLIER
(
  sup_factory        VARCHAR2(10) not null,
  supplier_no        VARCHAR2(10) not null,
  supplier_name      VARCHAR2(150),
  sup_factory_addr   VARCHAR2(200),
  detail_addr        VARCHAR2(200),
  sup_status         VARCHAR2(10),
  email              VARCHAR2(50),
  contact            VARCHAR2(30),
  mobile_no          VARCHAR2(30),
  tel_no             VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_time DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column MM_PUB_SUPPLIER.sup_factory
  is '出货地编码唯一';
comment on column MM_PUB_SUPPLIER.sup_status
  is '1：冻结；2：删除；3：活动';
alter table MM_PUB_SUPPLIER
  add constraint PK_MM_PUB_SUPPLIER primary key (SUP_FACTORY, SUPPLIER_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_PUB_SUPPLIER_DETAIL
prompt =====================================
prompt
create table MM_PUB_SUPPLIER_DETAIL
(
  sup_factory               VARCHAR2(10),
  supplier_no               VARCHAR2(10),
  import_dep                VARCHAR2(60),
  import_position           VARCHAR2(50),
  import_name               VARCHAR2(30),
  import_mobile             VARCHAR2(30),
  import_tel                VARCHAR2(30),
  import_mail               VARCHAR2(50),
  pt_dep                    VARCHAR2(60),
  pt_position               VARCHAR2(50),
  pt_name                   VARCHAR2(30),
  pt_mobile                 VARCHAR2(30),
  pt_tel                    VARCHAR2(30),
  pt_mail                   VARCHAR2(50),
  mass_dep                  VARCHAR2(60),
  mass_position             VARCHAR2(50),
  mass_name                 VARCHAR2(30),
  mass_mobile               VARCHAR2(30),
  mass_tel                  VARCHAR2(30),
  mass_mail                 VARCHAR2(50),
  excep_dep_a               VARCHAR2(60),
  excep_dep_b               VARCHAR2(50),
  excep_position_a          VARCHAR2(50),
  excep_position_b          VARCHAR2(50),
  excep_name_a              VARCHAR2(30),
  excep_name_b              VARCHAR2(30),
  excep_mobile_a            VARCHAR2(30),
  excep_mobile_b            VARCHAR2(30),
  excep_tel_a               VARCHAR2(30),
  excep_tel_b               VARCHAR2(30),
  excep_mail_a              VARCHAR2(50),
  excep_mail_b              VARCHAR2(50),
  device_dep                VARCHAR2(60),
  device_position           VARCHAR2(30),
  device_name               VARCHAR2(30),
  device_mobile             VARCHAR2(30),
  device_tel                VARCHAR2(30),
  device_mail               VARCHAR2(50),
  import_dep_a              VARCHAR2(50),
  import_position_a         VARCHAR2(30),
  import_name_a             VARCHAR2(30),
  import_mobile_a           VARCHAR2(30),
  import_tel_a              VARCHAR2(30),
  import_mail_a             VARCHAR2(50),
  pt_dep_a                  VARCHAR2(60),
  pt_position_a             VARCHAR2(30),
  pt_name_a                 VARCHAR2(30),
  pt_mobile_a               VARCHAR2(30),
  pt_tel_a                  VARCHAR2(30),
  pt_mail_a                 VARCHAR2(50),
  mass_dep_a                VARCHAR2(60),
  mass_position_a           VARCHAR2(30),
  mass_name_a               VARCHAR2(30),
  mass_mobile_a             VARCHAR2(30),
  mass_tel_a                VARCHAR2(30),
  mass_mail_a               VARCHAR2(50),
  device_dep_a              VARCHAR2(60),
  device_position_a         VARCHAR2(30),
  device_name_a             VARCHAR2(30),
  device_mobile_a           VARCHAR2(30),
  device_tel_a              VARCHAR2(30),
  device_mail_a             VARCHAR2(50),
  pack_dep_a                VARCHAR2(60),
  pack_position_a           VARCHAR2(30),
  pack_name_a               VARCHAR2(30),
  pack_mobile_a             VARCHAR2(30),
  pack_tel_a                VARCHAR2(30),
  pack_mail_a               VARCHAR2(50),
  pack_dep_b                VARCHAR2(60),
  pack_position_b           VARCHAR2(30),
  pack_name_b               VARCHAR2(30),
  pack_mobile_b             VARCHAR2(30),
  pack_tel_b                VARCHAR2(30),
  pack_mail_b               VARCHAR2(50),
  pt_logistics_dep          VARCHAR2(60),
  pt_logistics_name         VARCHAR2(30),
  pt_logistics_position     VARCHAR2(30),
  pt_logistics_mobile       VARCHAR2(30),
  pt_logistics_tel          VARCHAR2(30),
  pt_logistics_mail         VARCHAR2(50),
  pt_logistics_dep_a        VARCHAR2(60),
  pt_logistics_name_a       VARCHAR2(30),
  pt_logistics_position_a   VARCHAR2(30),
  pt_logistics_mobile_a     VARCHAR2(30),
  pt_logistics_tel_a        VARCHAR2(30),
  pt_logistics_mail_a       VARCHAR2(50),
  mass_logistics_dep        VARCHAR2(60),
  mass_logistics_position   VARCHAR2(30),
  mass_logistics_name       VARCHAR2(30),
  mass_logistics_mobile     VARCHAR2(30),
  mass_logistics_tel        VARCHAR2(30),
  mass_logistics_mail       VARCHAR2(50),
  mass_logistics_dep_a      VARCHAR2(60),
  mass_logistics_position_a VARCHAR2(30),
  mass_logistics_name_a     VARCHAR2(30),
  mass_logistics_mobile_a   VARCHAR2(30),
  mass_logistics_tel_a      VARCHAR2(30),
  mass_logistics_mail_a     VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table MM_PUB_SUPPLIER_DETAIL
  is 'MM_PUB_SUPPLIER_DETAIL供应商主数据明细表
';
comment on column MM_PUB_SUPPLIER_DETAIL.sup_factory
  is '供应商出货地(账号中的出货地)
';
comment on column MM_PUB_SUPPLIER_DETAIL.supplier_no
  is '供应商代码
';
comment on column MM_PUB_SUPPLIER_DETAIL.import_dep
  is '重要联络人部门
';
comment on column MM_PUB_SUPPLIER_DETAIL.import_position
  is '重要联络人职位
';
comment on column MM_PUB_SUPPLIER_DETAIL.import_name
  is '重要联络人姓名
';
comment on column MM_PUB_SUPPLIER_DETAIL.import_mobile
  is '重要联络人移动电话
';
comment on column MM_PUB_SUPPLIER_DETAIL.import_tel
  is '重要联络人固定电话
';
comment on column MM_PUB_SUPPLIER_DETAIL.import_mail
  is '重要联络人邮箱地址
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_dep
  is 'PT订单联络人部门
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_position
  is 'PT订单联络人职位
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_name
  is 'PT订单联络人姓名
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_mobile
  is 'PT订单联络人移动电话
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_tel
  is 'PT订单联络人固定电话
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_mail
  is 'PT订单联络人邮箱地址
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_dep
  is '量产订单联络人部门
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_position
  is '量产订单联络人职位
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_name
  is '量产订单联络人姓名
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_mobile
  is '量产订单联络人移动电话
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_tel
  is '量产订单联络人固定电话
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_mail
  is '量产订单联络人邮箱地址
';
comment on column MM_PUB_SUPPLIER_DETAIL.excep_dep_a
  is '异常联络人部门A
';
comment on column MM_PUB_SUPPLIER_DETAIL.excep_dep_b
  is '异常联络人部门B
';
comment on column MM_PUB_SUPPLIER_DETAIL.excep_position_a
  is '异常联络人职位A
';
comment on column MM_PUB_SUPPLIER_DETAIL.excep_position_b
  is '异常联络人职位B
';
comment on column MM_PUB_SUPPLIER_DETAIL.excep_name_a
  is '异常联络人姓名A
';
comment on column MM_PUB_SUPPLIER_DETAIL.excep_name_b
  is '异常联络人姓名B
';
comment on column MM_PUB_SUPPLIER_DETAIL.excep_mobile_a
  is '异常联络人移动电话A
';
comment on column MM_PUB_SUPPLIER_DETAIL.excep_mobile_b
  is '异常联络人移动电话B
';
comment on column MM_PUB_SUPPLIER_DETAIL.excep_tel_a
  is '异常联络人固定电话A
';
comment on column MM_PUB_SUPPLIER_DETAIL.excep_tel_b
  is '异常联络人固定电话B
';
comment on column MM_PUB_SUPPLIER_DETAIL.excep_mail_a
  is '异常联络人邮箱地址A
';
comment on column MM_PUB_SUPPLIER_DETAIL.excep_mail_b
  is '异常联络人邮箱地址B
';
comment on column MM_PUB_SUPPLIER_DETAIL.device_dep
  is '设变联络人部门
';
comment on column MM_PUB_SUPPLIER_DETAIL.device_position
  is '设变联络人职位
';
comment on column MM_PUB_SUPPLIER_DETAIL.device_name
  is '设变联络人姓名
';
comment on column MM_PUB_SUPPLIER_DETAIL.device_mobile
  is '设变联络人移动电话
';
comment on column MM_PUB_SUPPLIER_DETAIL.device_tel
  is '设变联络人固定电话
';
comment on column MM_PUB_SUPPLIER_DETAIL.device_mail
  is '设变联络人邮箱地址
';
comment on column MM_PUB_SUPPLIER_DETAIL.import_dep_a
  is '重要联络人部门1
';
comment on column MM_PUB_SUPPLIER_DETAIL.import_position_a
  is '重要联络人职位1
';
comment on column MM_PUB_SUPPLIER_DETAIL.import_name_a
  is '重要联络人姓名1
';
comment on column MM_PUB_SUPPLIER_DETAIL.import_mobile_a
  is '重要联络人移动电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.import_tel_a
  is '重要联络人固定电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.import_mail_a
  is '重要联络人邮箱地址1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_dep_a
  is 'PT订单联络人部门1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_position_a
  is 'PT订单联络人职位1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_name_a
  is 'PT订单联络人姓名1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_mobile_a
  is 'PT订单联络人移动电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_tel_a
  is 'PT订单联络人固定电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_mail_a
  is 'PT订单联络人邮箱地址1
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_dep_a
  is '量产订单联络人部门1
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_position_a
  is '量产订单联络人职位1
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_name_a
  is '量产订单联络人姓名1
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_mobile_a
  is '量产订单联络人移动电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_tel_a
  is '量产订单联络人固定电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_mail_a
  is '量产订单联络人邮箱地址1
';
comment on column MM_PUB_SUPPLIER_DETAIL.device_dep_a
  is '设变联络人部门1
';
comment on column MM_PUB_SUPPLIER_DETAIL.device_position_a
  is '设变联络人职位1
';
comment on column MM_PUB_SUPPLIER_DETAIL.device_name_a
  is '设变联络人姓名1
';
comment on column MM_PUB_SUPPLIER_DETAIL.device_mobile_a
  is '设变联络人移动电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.device_tel_a
  is '设变联络人固定电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.device_mail_a
  is '设变联络人邮箱地址1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pack_dep_a
  is '包装联络人部门1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pack_position_a
  is '包装联络人职位1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pack_name_a
  is '包装联络人姓名1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pack_mobile_a
  is '包装联络人移动电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pack_tel_a
  is '包装联系人固定电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pack_mail_a
  is '包装联络人邮箱地址1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pack_dep_b
  is '包装联络人部门2
';
comment on column MM_PUB_SUPPLIER_DETAIL.pack_position_b
  is '包装联络人职位2
';
comment on column MM_PUB_SUPPLIER_DETAIL.pack_name_b
  is '包装联络人姓名2
';
comment on column MM_PUB_SUPPLIER_DETAIL.pack_mobile_b
  is '包装联络人移动电话2
';
comment on column MM_PUB_SUPPLIER_DETAIL.pack_tel_b
  is '包装联系人固定电话2
';
comment on column MM_PUB_SUPPLIER_DETAIL.pack_mail_b
  is '包装联络人邮箱地址2
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_logistics_dep
  is 'PT物流对应联络人部门
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_logistics_name
  is 'PT物流对应联络人姓名
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_logistics_position
  is 'PT物流对应联络人职位
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_logistics_mobile
  is 'PT物流对应联络人移动电话
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_logistics_tel
  is 'PT物流对应联络人固定电话
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_logistics_mail
  is 'PT物流对应联络人邮箱地址
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_logistics_dep_a
  is 'PT物流对应联络人部门1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_logistics_name_a
  is 'PT物流对应联络人姓名1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_logistics_position_a
  is 'PT物流对应联络人职位1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_logistics_mobile_a
  is 'PT物流对应联络人移动电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_logistics_tel_a
  is 'PT物流对应联络人固定电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.pt_logistics_mail_a
  is 'PT物流对应联络人邮箱地址1
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_logistics_dep
  is '量产物流对应联络人部门
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_logistics_position
  is '量产物流对应联络人职位
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_logistics_name
  is '量产物流对应联络人姓名
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_logistics_mobile
  is '量产物流对应联络人移动电话
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_logistics_tel
  is '量产物流对应联络人固定电话
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_logistics_mail
  is '量产物流对应联络人邮箱地址
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_logistics_dep_a
  is '量产物流对应联络人部门1
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_logistics_position_a
  is '量产物流对应联络人职位1
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_logistics_name_a
  is '量产物流对应联络人姓名1
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_logistics_mobile_a
  is '量产物流对应联络人移动电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_logistics_tel_a
  is '量产物流对应联络人固定电话1
';
comment on column MM_PUB_SUPPLIER_DETAIL.mass_logistics_mail_a
  is '量产物流对应联络人邮箱地址1
';
alter table MM_PUB_SUPPLIER_DETAIL
  add constraint AK_KEY_1_MM_PUB_S unique (SUP_FACTORY, SUPPLIER_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_PUB_SYS_PARAM
prompt ===============================
prompt
create table MM_PUB_SYS_PARAM
(
  id                 NUMBER(19) not null,
  factory_code       VARCHAR2(10) not null,
  param_code         VARCHAR2(50) not null,
  param_name         VARCHAR2(100),
  param_val          VARCHAR2(100),
  param_group        VARCHAR2(50),
  is_edit            NUMBER(1) default 0,
  note               VARCHAR2(200),
  check_by           VARCHAR2(20),
  check_comp         VARCHAR2(2000),
  message            VARCHAR2(100),
  uda_1              VARCHAR2(50),
  uda_2              VARCHAR2(50),
  uda_3              VARCHAR2(50),
  creation_time      DATE default SYSDATE,
  creation_user      VARCHAR2(30),
  last_modified_time DATE,
  last_modified_user VARCHAR2(30),
  last_modified_ip   VARCHAR2(30)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table MM_PUB_SYS_PARAM
  is '系统参数表';
comment on column MM_PUB_SYS_PARAM.check_by
  is '数据字典类型“PUB_CHECK_BY”';
alter table MM_PUB_SYS_PARAM
  add constraint PK_MM_PUB_SYS_PARAM primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_PUB_SYS_PARAM
  add constraint AK_MM_PUB_SYS_PARAM unique (FACTORY_CODE, PARAM_CODE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_ACCOUNT_BILL
prompt =================================
prompt
create table MM_SW_ACCOUNT_BILL
(
  bill_no            VARCHAR2(30) not null,
  factory_code       VARCHAR2(10),
  supplier_no        VARCHAR2(20),
  tax_excluded       NUMBER(19,3),
  tax_inclusive      NUMBER(19,3),
  total_tax          NUMBER(19,3),
  make_date          DATE,
  currency_type      VARCHAR2(20),
  rebate             NUMBER(19,3),
  rebate_desc        VARCHAR2(300),
  deduct_money       NUMBER(19,3),
  deduct_money_desc  VARCHAR2(300),
  year_adjust        NUMBER(19,3),
  year_adjust_desc   VARCHAR2(300),
  mould_amount       NUMBER(19,3),
  mould_amount_desc  VARCHAR2(300),
  pay_term           VARCHAR2(100),
  invoice_status     NUMBER(1) default 0,
  invoice_entry_time DATE,
  deal_flag          NUMBER(1) default 0,
  deal_time          DATE,
  remark             VARCHAR2(300),
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  submit_status      NUMBER(1) default 0,
  account_status     VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_ACCOUNT_BILL
  is 'MM_SW_ACCOUNT_BILL结算对账单表
';
comment on column MM_SW_ACCOUNT_BILL.bill_no
  is '开票对账单号
';
comment on column MM_SW_ACCOUNT_BILL.factory_code
  is '工厂
';
comment on column MM_SW_ACCOUNT_BILL.supplier_no
  is '供应商代码
';
comment on column MM_SW_ACCOUNT_BILL.tax_excluded
  is '对账单不含税合计
';
comment on column MM_SW_ACCOUNT_BILL.tax_inclusive
  is '对账单含税合计
';
comment on column MM_SW_ACCOUNT_BILL.total_tax
  is '对账单税额合计
';
comment on column MM_SW_ACCOUNT_BILL.make_date
  is '制单日期
';
comment on column MM_SW_ACCOUNT_BILL.currency_type
  is '货币类型
';
comment on column MM_SW_ACCOUNT_BILL.rebate
  is '供应商返利值
';
comment on column MM_SW_ACCOUNT_BILL.rebate_desc
  is '供应商返利描述
';
comment on column MM_SW_ACCOUNT_BILL.deduct_money
  is '特殊扣款值
';
comment on column MM_SW_ACCOUNT_BILL.deduct_money_desc
  is '特殊扣款描述
';
comment on column MM_SW_ACCOUNT_BILL.year_adjust
  is '年底调差值
';
comment on column MM_SW_ACCOUNT_BILL.year_adjust_desc
  is '年底调差描述
';
comment on column MM_SW_ACCOUNT_BILL.mould_amount
  is '模具分摊值
';
comment on column MM_SW_ACCOUNT_BILL.mould_amount_desc
  is '模具分摊描述
';
comment on column MM_SW_ACCOUNT_BILL.pay_term
  is '付款条件
';
comment on column MM_SW_ACCOUNT_BILL.invoice_status
  is '发票反馈状态
0-未反馈
1-已反馈
';
comment on column MM_SW_ACCOUNT_BILL.invoice_entry_time
  is '发票反馈时间
';
comment on column MM_SW_ACCOUNT_BILL.deal_flag
  is '接口处理标志
0-未处理
1-已处理';
comment on column MM_SW_ACCOUNT_BILL.deal_time
  is '接口处理时间
';
comment on column MM_SW_ACCOUNT_BILL.remark
  is '备注
';
comment on column MM_SW_ACCOUNT_BILL.creation_user
  is '创建人
';
comment on column MM_SW_ACCOUNT_BILL.creation_time
  is '创建时间
';
comment on column MM_SW_ACCOUNT_BILL.last_modified_user
  is '最后修改用户';
comment on column MM_SW_ACCOUNT_BILL.last_modified_time
  is '最后修改时间';
comment on column MM_SW_ACCOUNT_BILL.submit_status
  is '提交标识';
create index IDX_MM_SW_ACCOUNT_BILL1 on MM_SW_ACCOUNT_BILL (MAKE_DATE)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_SW_ACCOUNT_BILL2 on MM_SW_ACCOUNT_BILL (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_SW_ACCOUNT_BILL3 on MM_SW_ACCOUNT_BILL (INVOICE_STATUS)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_SW_ACCOUNT_BILL
  add constraint PK_MM_SW_ACCOUNT_BILL primary key (BILL_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_ACCOUNT_BILL_DETAIL
prompt ========================================
prompt
create table MM_SW_ACCOUNT_BILL_DETAIL
(
  bill_no               VARCHAR2(30) not null,
  bill_row_no           NUMBER(10) not null,
  purchase_no           VARCHAR2(50),
  purchase_row_no       NUMBER(10),
  rec_voucher_year      VARCHAR2(20),
  rec_voucher_billno    VARCHAR2(50),
  rec_voucher_rowno     NUMBER(10),
  ref_rec_voucher_bill  VARCHAR2(50),
  ref_rec_voucher_rowno NUMBER(10),
  rec_date              DATE,
  loan_flag             VARCHAR2(10),
  price_status          NUMBER(1),
  eva_price             NUMBER(19,3),
  official_price        NUMBER(19,3),
  adjust_diff_price     NUMBER(19,3),
  eva_price_percent     NUMBER(19,3),
  eva_settle_price      NUMBER(19,3),
  erp_factory_code      VARCHAR2(10),
  part_no               VARCHAR2(20),
  supplier_no           VARCHAR2(20),
  rec_num               NUMBER(19,3),
  pay_amount            NUMBER(19,3),
  tax_rate              NUMBER(19,3),
  tax_amount            NUMBER(19,3),
  currency_type         VARCHAR2(20),
  part_unit             VARCHAR2(20)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_ACCOUNT_BILL_DETAIL
  is 'MM_SW_ACCOUNT_BILL_DETAIL结算对账单明细表
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.bill_no
  is '开票对账单号
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.bill_row_no
  is '对账单行号
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.purchase_no
  is '采购订单号
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.purchase_row_no
  is '采购单行号
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.rec_voucher_year
  is '收货凭证年度
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.rec_voucher_billno
  is '物料凭证
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.rec_voucher_rowno
  is '物料凭证行项目
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.ref_rec_voucher_bill
  is '参考物料凭证
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.ref_rec_voucher_rowno
  is '参考物料凭证行项目
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.rec_date
  is '收货凭证日期
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.loan_flag
  is '借贷标识
S-正数
H-负数';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.price_status
  is '价格状态
1-暂估价；
2-正式价；
3-调差价';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.eva_price
  is '暂估价(不含税)
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.official_price
  is '正式价(不含税)
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.adjust_diff_price
  is '调差价格(不含税)
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.eva_price_percent
  is '定价(暂估价)百分比
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.eva_settle_price
  is '暂估结算单价
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.erp_factory_code
  is 'ERP工厂代码
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.part_no
  is '零件号
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.supplier_no
  is '供应商代码
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.rec_num
  is '已收货数量
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.pay_amount
  is '应付(不含税)金额
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.tax_rate
  is '税率
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.tax_amount
  is '税额
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.currency_type
  is '货币类型
';
comment on column MM_SW_ACCOUNT_BILL_DETAIL.part_unit
  is '物料基本单位
';
alter table MM_SW_ACCOUNT_BILL_DETAIL
  add constraint PK_MM_SW_ACCOUNT_BILL_DETAIL primary key (BILL_NO, BILL_ROW_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_ACCOUNT_INVOICE
prompt ====================================
prompt
create table MM_SW_ACCOUNT_INVOICE
(
  bill_no            VARCHAR2(30) not null,
  invoice_no         VARCHAR2(50) not null,
  invoice_code       VARCHAR2(50) not null,
  invoice_amount     NUMBER(19,3),
  tax_amount         NUMBER(19,3),
  invoice_date       DATE,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  deal_flag          NUMBER(1) default 0,
  deal_time          DATE,
  check_code         VARCHAR2(30),
  invoice_net_price  NUMBER(19,3),
  do_flag            VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_ACCOUNT_INVOICE
  is 'MM_SW_ACCOUNT_INVOICE结算对账单与发票关系表
';
comment on column MM_SW_ACCOUNT_INVOICE.bill_no
  is '"开票对账单号
"';
comment on column MM_SW_ACCOUNT_INVOICE.invoice_no
  is '"金税发票号
"';
comment on column MM_SW_ACCOUNT_INVOICE.invoice_code
  is '"发票代码
"';
comment on column MM_SW_ACCOUNT_INVOICE.invoice_amount
  is '"发票含税总金额
"';
comment on column MM_SW_ACCOUNT_INVOICE.tax_amount
  is '"发票税额
"';
comment on column MM_SW_ACCOUNT_INVOICE.invoice_date
  is '"发票开票时间
"';
comment on column MM_SW_ACCOUNT_INVOICE.creation_user
  is '"创建人
"';
comment on column MM_SW_ACCOUNT_INVOICE.creation_time
  is '"创建时间
"';
comment on column MM_SW_ACCOUNT_INVOICE.last_modified_user
  is '"最后修改用户
"';
comment on column MM_SW_ACCOUNT_INVOICE.last_modified_time
  is '"最后修改时间
"';
comment on column MM_SW_ACCOUNT_INVOICE.deal_flag
  is '"处理标志位
"';
comment on column MM_SW_ACCOUNT_INVOICE.deal_time
  is '"处理时间
"';
comment on column MM_SW_ACCOUNT_INVOICE.check_code
  is '"校验码
"';
comment on column MM_SW_ACCOUNT_INVOICE.invoice_net_price
  is '"发票不含税金额（净价）
"';
comment on column MM_SW_ACCOUNT_INVOICE.do_flag
  is 'I插入 U修改 D删除';
create index IDX_MM_SW_ACCOUNT_INVOICE1 on MM_SW_ACCOUNT_INVOICE (BILL_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_SW_ACCOUNT_INVOICE
  add constraint PK_MM_SW_ACCOUNT_INVOICE primary key (BILL_NO, INVOICE_NO, INVOICE_CODE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_DELIVERY
prompt =============================
prompt
create table MM_SW_DELIVERY
(
  delivery_no        VARCHAR2(50) not null,
  purchase_no        VARCHAR2(50) not null,
  purchase_rowno     VARCHAR2(50),
  factory_code       VARCHAR2(10),
  delivery_user      VARCHAR2(30),
  delivery_time      DATE default sysdate,
  print_status       NUMBER(1) default 0,
  print_time         DATE,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_DELIVERY
  is 'MM_SW_DELIVERY发货单表
';
comment on column MM_SW_DELIVERY.delivery_no
  is '发货单号
';
comment on column MM_SW_DELIVERY.purchase_no
  is '采购单号
';
comment on column MM_SW_DELIVERY.factory_code
  is '工厂代码
';
comment on column MM_SW_DELIVERY.delivery_user
  is '发货人
';
comment on column MM_SW_DELIVERY.delivery_time
  is '发货时间
';
comment on column MM_SW_DELIVERY.print_status
  is '打印状态
0-未打印
1-已打印';
comment on column MM_SW_DELIVERY.print_time
  is '打印时间
';
comment on column MM_SW_DELIVERY.last_modified_user
  is '最后修改用户
';
comment on column MM_SW_DELIVERY.last_modified_time
  is '最后修改时间
';
create index IDX_MM_SW_DELIVERY1 on MM_SW_DELIVERY (DELIVERY_TIME)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_SW_DELIVERY2 on MM_SW_DELIVERY (PURCHASE_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_SW_DELIVERY
  add constraint PK_MM_SW_DELIVERY primary key (DELIVERY_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_DELIVERY_DETAIL
prompt ====================================
prompt
create table MM_SW_DELIVERY_DETAIL
(
  delivery_no    VARCHAR2(50),
  delivery_rowno NUMBER(10),
  part_no        VARCHAR2(20),
  delivery_qty   NUMBER(10),
  delivery_nums  NUMBER(10),
  creation_time  DATE default sysdate
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_DELIVERY_DETAIL
  is 'MM_SW_DELIVERY_DETAIL发货单明细表
';
comment on column MM_SW_DELIVERY_DETAIL.delivery_no
  is '发货单号
';
comment on column MM_SW_DELIVERY_DETAIL.delivery_rowno
  is '发货单行号
';
comment on column MM_SW_DELIVERY_DETAIL.part_no
  is '零件编号
';
comment on column MM_SW_DELIVERY_DETAIL.delivery_qty
  is '发货数量
';
comment on column MM_SW_DELIVERY_DETAIL.delivery_nums
  is '订单行发货次数
';
comment on column MM_SW_DELIVERY_DETAIL.creation_time
  is '创建时间
';
create index IDX_MM_SW_DELIV_DETAIL1 on MM_SW_DELIVERY_DETAIL (DELIVERY_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
create index IDX_MM_SW_DELIV_DETAIL2 on MM_SW_DELIVERY_DETAIL (DELIVERY_ROWNO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_DEMAND_FORECAST
prompt ====================================
prompt
create table MM_SW_DEMAND_FORECAST
(
  id                 NUMBER(10) not null,
  factory_code       VARCHAR2(20),
  fore_type          NUMBER(1),
  version            VARCHAR2(20),
  plan_delivery      DATE,
  start_date         DATE,
  end_date           DATE,
  phase              VARCHAR2(20),
  unload_port        VARCHAR2(20),
  part_no            VARCHAR2(20),
  part_unit          VARCHAR2(20),
  order_qty          NUMBER(10),
  supplier_no        VARCHAR2(20),
  supplier_name      VARCHAR2(100),
  sup_factory_addr   VARCHAR2(100),
  sup_factory        VARCHAR2(20),
  model_code         VARCHAR2(20),
  logistics_mode     VARCHAR2(50),
  download_status    NUMBER(1) default 0,
  download_time      DATE,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  release_status     NUMBER(1) default 1,
  feedback_status    NUMBER(1),
  feedback_remark    VARCHAR2(300),
  publish_month      VARCHAR2(30),
  obj_month          VARCHAR2(30),
  workcenter         VARCHAR2(10),
  obj_week           VARCHAR2(30),
  publish_week       VARCHAR2(30),
  advance_time       VARCHAR2(50),
  email_flag         NUMBER(2) default 0,
  p_supplier         VARCHAR2(20),
  seq                VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_DEMAND_FORECAST
  is 'MM_SW_DEMAND_FORECAST需求预测数据表
';
comment on column MM_SW_DEMAND_FORECAST.id
  is '"ID
"';
comment on column MM_SW_DEMAND_FORECAST.factory_code
  is '"工厂
"';
comment on column MM_SW_DEMAND_FORECAST.fore_type
  is '"预测类型
1-月预测
2-日预测"';
comment on column MM_SW_DEMAND_FORECAST.version
  is '"发布版本
"';
comment on column MM_SW_DEMAND_FORECAST.plan_delivery
  is '"需求到货日期
"';
comment on column MM_SW_DEMAND_FORECAST.start_date
  is '"开始日期
"';
comment on column MM_SW_DEMAND_FORECAST.end_date
  is '"结束日期
"';
comment on column MM_SW_DEMAND_FORECAST.phase
  is '"生产阶段
0-试制
1-量产"';
comment on column MM_SW_DEMAND_FORECAST.unload_port
  is '"卸货口
"';
comment on column MM_SW_DEMAND_FORECAST.part_no
  is '"零件编号
"';
comment on column MM_SW_DEMAND_FORECAST.part_unit
  is '"零件单位
"';
comment on column MM_SW_DEMAND_FORECAST.order_qty
  is '"需求数量
"';
comment on column MM_SW_DEMAND_FORECAST.supplier_no
  is '"供应商代码
"';
comment on column MM_SW_DEMAND_FORECAST.supplier_name
  is '"供应商名称
"';
comment on column MM_SW_DEMAND_FORECAST.sup_factory_addr
  is '"出货地地址
"';
comment on column MM_SW_DEMAND_FORECAST.sup_factory
  is '"出货地代码
"';
comment on column MM_SW_DEMAND_FORECAST.model_code
  is '"车型
"';
comment on column MM_SW_DEMAND_FORECAST.logistics_mode
  is '"订单物流模式
"';
comment on column MM_SW_DEMAND_FORECAST.download_status
  is '"下载状态
0-未下载
1-已下载"';
comment on column MM_SW_DEMAND_FORECAST.download_time
  is '"下载日期
"';
comment on column MM_SW_DEMAND_FORECAST.creation_user
  is '"创建人
"';
comment on column MM_SW_DEMAND_FORECAST.creation_time
  is '"创建时间
"';
comment on column MM_SW_DEMAND_FORECAST.last_modified_user
  is '"最后修改用户
"';
comment on column MM_SW_DEMAND_FORECAST.last_modified_time
  is '"最后修改时间
"';
comment on column MM_SW_DEMAND_FORECAST.release_status
  is '"发布状态
"';
comment on column MM_SW_DEMAND_FORECAST.feedback_status
  is '"反馈状态
"';
comment on column MM_SW_DEMAND_FORECAST.feedback_remark
  is '"反馈备注
"';
comment on column MM_SW_DEMAND_FORECAST.publish_month
  is '"发布月份
"';
comment on column MM_SW_DEMAND_FORECAST.obj_month
  is '"对象月份
"';
comment on column MM_SW_DEMAND_FORECAST.email_flag
  is '0：未发送  1：已发送';
comment on column MM_SW_DEMAND_FORECAST.p_supplier
  is '父件供应商';
comment on column MM_SW_DEMAND_FORECAST.seq
  is '版本序列号，同一个版本每发一次加一';
create index IDX_MM_SW_DEMAND_FORE1 on MM_SW_DEMAND_FORECAST (PLAN_DELIVERY)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_SW_DEMAND_FORECAST
  add constraint PK_MM_SW_DEMAND_FORECAST primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_SW_DEMAND_FORECAST
  add constraint AK_MM_SW_DEMAND_FORE unique (PLAN_DELIVERY, SUPPLIER_NO, PHASE, FORE_TYPE, VERSION, FACTORY_CODE, PART_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_DEMAND_FORECAST_IMP
prompt ========================================
prompt
create table MM_SW_DEMAND_FORECAST_IMP
(
  id                 NUMBER(10) not null,
  factory_code       VARCHAR2(20),
  fore_type          NUMBER(1),
  version            VARCHAR2(20),
  plan_delivery      DATE,
  start_date         DATE,
  end_date           DATE,
  phase              VARCHAR2(20),
  unload_port        VARCHAR2(20),
  part_no            VARCHAR2(50),
  part_unit          VARCHAR2(20),
  order_qty          NUMBER(10),
  supplier_no        VARCHAR2(20),
  supplier_name      VARCHAR2(100),
  sup_factory_addr   VARCHAR2(100),
  sup_factory        VARCHAR2(20),
  model_code         VARCHAR2(20),
  logistics_mode     VARCHAR2(50),
  download_status    NUMBER(1) default 0,
  download_time      DATE,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  release_status     NUMBER(1),
  feedback_status    NUMBER(1),
  feedback_remark    VARCHAR2(300),
  workcenter         VARCHAR2(10),
  obj_week           VARCHAR2(30),
  publish_week       VARCHAR2(30),
  advance_time       VARCHAR2(50),
  do_flag            VARCHAR2(10),
  deal_flag          NUMBER(1),
  deal_time          DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_DEMAND_FORECAST_IMP
  is 'MM_SW_DEMAND_FORECAST_IMP需求预测数据表
';
comment on column MM_SW_DEMAND_FORECAST_IMP.id
  is 'ID
';
comment on column MM_SW_DEMAND_FORECAST_IMP.factory_code
  is '工厂
';
comment on column MM_SW_DEMAND_FORECAST_IMP.fore_type
  is '预测类型
1-月预测
2-日预测';
comment on column MM_SW_DEMAND_FORECAST_IMP.version
  is '发布版本
';
comment on column MM_SW_DEMAND_FORECAST_IMP.plan_delivery
  is '需求到货日期
';
comment on column MM_SW_DEMAND_FORECAST_IMP.start_date
  is '开始日期
';
comment on column MM_SW_DEMAND_FORECAST_IMP.end_date
  is '结束日期
';
comment on column MM_SW_DEMAND_FORECAST_IMP.phase
  is '生产阶段
0-试制
1-量产';
comment on column MM_SW_DEMAND_FORECAST_IMP.unload_port
  is '卸货口
';
comment on column MM_SW_DEMAND_FORECAST_IMP.part_no
  is '零件编号
';
comment on column MM_SW_DEMAND_FORECAST_IMP.part_unit
  is '零件单位
';
comment on column MM_SW_DEMAND_FORECAST_IMP.order_qty
  is '需求数量
';
comment on column MM_SW_DEMAND_FORECAST_IMP.supplier_no
  is '供应商代码
';
comment on column MM_SW_DEMAND_FORECAST_IMP.supplier_name
  is '供应商名称
';
comment on column MM_SW_DEMAND_FORECAST_IMP.sup_factory_addr
  is '出货地地址
';
comment on column MM_SW_DEMAND_FORECAST_IMP.sup_factory
  is '出货地代码
';
comment on column MM_SW_DEMAND_FORECAST_IMP.model_code
  is '车型
';
comment on column MM_SW_DEMAND_FORECAST_IMP.logistics_mode
  is '订单物流模式
';
comment on column MM_SW_DEMAND_FORECAST_IMP.download_status
  is '下载状态
0-未下载
1-已下载';
comment on column MM_SW_DEMAND_FORECAST_IMP.download_time
  is '下载日期
';
comment on column MM_SW_DEMAND_FORECAST_IMP.creation_user
  is '创建人
';
comment on column MM_SW_DEMAND_FORECAST_IMP.creation_time
  is '创建时间
';
comment on column MM_SW_DEMAND_FORECAST_IMP.last_modified_user
  is '最后修改用户
';
comment on column MM_SW_DEMAND_FORECAST_IMP.last_modified_time
  is '最后修改时间
';
comment on column MM_SW_DEMAND_FORECAST_IMP.release_status
  is '发布状态
';
comment on column MM_SW_DEMAND_FORECAST_IMP.feedback_status
  is '反馈状态
';
comment on column MM_SW_DEMAND_FORECAST_IMP.feedback_remark
  is '反馈备注
';
comment on column MM_SW_DEMAND_FORECAST_IMP.obj_week
  is '对象周';
comment on column MM_SW_DEMAND_FORECAST_IMP.publish_week
  is '发布周';
comment on column MM_SW_DEMAND_FORECAST_IMP.advance_time
  is '提前取货时间';
create index IDX_MM_SW_DEMAND_FORE2 on MM_SW_DEMAND_FORECAST_IMP (PLAN_DELIVERY)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_SW_DEMAND_FORECAST_IMP
  add constraint PK_MM_SW_DEMAND_FORECAST_IMP primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_FEEDBACK_ZC
prompt ================================
prompt
create table MM_SW_FEEDBACK_ZC
(
  id                 VARCHAR2(19),
  factory_code       VARCHAR2(20) not null,
  purchase_no        VARCHAR2(50) not null,
  reply_seq_no       NUMBER(10) not null,
  purchase_row_no    NUMBER(10) not null,
  plan_time          DATE,
  plan_num           NUMBER(19,2),
  return_msg         VARCHAR2(500),
  return_time        DATE,
  deal_flag          NUMBER(1) default 0,
  deal_time          DATE,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  print_status       NUMBER(1) default 0,
  print_time         DATE default SYSDATE,
  print_user         VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_FEEDBACK_ZC
  is 'MM_SW_FEEDBACK_ZC资材反馈回复
';
comment on column MM_SW_FEEDBACK_ZC.factory_code
  is '工厂编码
';
comment on column MM_SW_FEEDBACK_ZC.purchase_no
  is '采购单号
';
comment on column MM_SW_FEEDBACK_ZC.reply_seq_no
  is '反馈序列号
';
comment on column MM_SW_FEEDBACK_ZC.purchase_row_no
  is '采购订单行号
';
comment on column MM_SW_FEEDBACK_ZC.plan_time
  is '计划交货日期
';
comment on column MM_SW_FEEDBACK_ZC.plan_num
  is '计划交货数量
';
comment on column MM_SW_FEEDBACK_ZC.return_msg
  is '反馈备注
';
comment on column MM_SW_FEEDBACK_ZC.return_time
  is '反馈时间
';
comment on column MM_SW_FEEDBACK_ZC.deal_flag
  is '接口处理标识
';
comment on column MM_SW_FEEDBACK_ZC.deal_time
  is '接口处理时间
';
comment on column MM_SW_FEEDBACK_ZC.creation_user
  is '创建人
';
comment on column MM_SW_FEEDBACK_ZC.creation_time
  is '创建时间
';
comment on column MM_SW_FEEDBACK_ZC.last_modified_user
  is '最后修改用户
';
comment on column MM_SW_FEEDBACK_ZC.last_modified_time
  is '最后修改时间
';
comment on column MM_SW_FEEDBACK_ZC.print_status
  is '订单打印状态
';
comment on column MM_SW_FEEDBACK_ZC.print_time
  is '订单打印时间
';
comment on column MM_SW_FEEDBACK_ZC.print_user
  is '打印人';
create index IDX_MM_SW_FEEDBACK_ZC1 on MM_SW_FEEDBACK_ZC (DEAL_FLAG)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_SW_FEEDBACK_ZC
  add constraint PK_MM_SW_FEEDBACK_ZC primary key (PURCHASE_NO, PURCHASE_ROW_NO, REPLY_SEQ_NO, FACTORY_CODE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_FEEDBACK_ZC_IMP
prompt ====================================
prompt
create table MM_SW_FEEDBACK_ZC_IMP
(
  id                 VARCHAR2(19),
  factory_code       VARCHAR2(20) not null,
  purchase_no        VARCHAR2(50) not null,
  reply_seq_no       NUMBER(10) not null,
  purchase_row_no    NUMBER(10) not null,
  arrive_date        DATE,
  order_qty          NUMBER(19,2),
  plan_time          DATE,
  plan_num           NUMBER(19,2),
  return_msg         VARCHAR2(500),
  return_time        DATE,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  imp_uuid           VARCHAR2(50),
  check_result       NUMBER(1) default 0,
  check_info         VARCHAR2(512),
  import_status      NUMBER(1) default 0,
  continue_check     NUMBER(1) default 1,
  ope_type           VARCHAR2(8) default 'I',
  busi_id            NUMBER(19)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_FEEDBACK_ZC_IMP
  is 'MM_SW_FEEDBACK_ZC_IMP导入临时表
';
comment on column MM_SW_FEEDBACK_ZC_IMP.factory_code
  is '工厂编码
';
comment on column MM_SW_FEEDBACK_ZC_IMP.purchase_no
  is '采购单号
';
comment on column MM_SW_FEEDBACK_ZC_IMP.reply_seq_no
  is '反馈序列号
';
comment on column MM_SW_FEEDBACK_ZC_IMP.purchase_row_no
  is '采购订单行号
';
comment on column MM_SW_FEEDBACK_ZC_IMP.arrive_date
  is '到货日期
';
comment on column MM_SW_FEEDBACK_ZC_IMP.order_qty
  is '订购数量
';
comment on column MM_SW_FEEDBACK_ZC_IMP.plan_time
  is '计划交货日期
';
comment on column MM_SW_FEEDBACK_ZC_IMP.plan_num
  is '计划交货数量
';
comment on column MM_SW_FEEDBACK_ZC_IMP.return_msg
  is '反馈备注
';
comment on column MM_SW_FEEDBACK_ZC_IMP.return_time
  is '反馈时间
';
comment on column MM_SW_FEEDBACK_ZC_IMP.creation_user
  is '创建人
';
comment on column MM_SW_FEEDBACK_ZC_IMP.creation_time
  is '创建时间
';
comment on column MM_SW_FEEDBACK_ZC_IMP.last_modified_user
  is '最后修改用户
';
comment on column MM_SW_FEEDBACK_ZC_IMP.last_modified_time
  is '最后修改时间
';
comment on column MM_SW_FEEDBACK_ZC_IMP.imp_uuid
  is '导入UUID
';
comment on column MM_SW_FEEDBACK_ZC_IMP.check_result
  is '检查结果
数据字典类型"PUB_IMP_CK_RESULT"';
comment on column MM_SW_FEEDBACK_ZC_IMP.check_info
  is '检查结果错误信息
';
comment on column MM_SW_FEEDBACK_ZC_IMP.import_status
  is '导入状态
数据字典类型“PUB_IMP_STATUS”';
comment on column MM_SW_FEEDBACK_ZC_IMP.continue_check
  is '是否继续检查
0:否
1:是';
comment on column MM_SW_FEEDBACK_ZC_IMP.ope_type
  is '导入数据操作类型
I:新增
U:更新';
comment on column MM_SW_FEEDBACK_ZC_IMP.busi_id
  is '业务表主键字段值
';

prompt
prompt Creating table MM_SW_LONG_ORDER
prompt ===============================
prompt
create table MM_SW_LONG_ORDER
(
  id                  NUMBER(19) not null,
  version             VARCHAR2(30),
  order_no            VARCHAR2(50),
  current_month       VARCHAR2(30),
  obj_month           VARCHAR2(30),
  order_period        NUMBER(19),
  forecast_period     NUMBER(19),
  forecast_first      VARCHAR2(30),
  forecast_first_num  NUMBER(19),
  forecast_second     VARCHAR2(30),
  forecast_second_num NUMBER(19),
  forecast_third      VARCHAR2(30),
  forecast_third_num  NUMBER(19),
  creation_time       DATE,
  creation_user       VARCHAR2(30),
  last_modified_user  VARCHAR2(30),
  last_modified_time  DATE,
  print_status        NUMBER(1),
  print_time          DATE,
  factory_code        VARCHAR2(10),
  del_flag            NUMBER(2) default 0
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_LONG_ORDER
  is 'MM_SW_LONG_ORDER长周期订单表
';
comment on column MM_SW_LONG_ORDER.version
  is '版本号
';
comment on column MM_SW_LONG_ORDER.order_no
  is '订单号
';
comment on column MM_SW_LONG_ORDER.current_month
  is '当前月
';
comment on column MM_SW_LONG_ORDER.obj_month
  is '对象月
';
comment on column MM_SW_LONG_ORDER.order_period
  is '订单周期
';
comment on column MM_SW_LONG_ORDER.forecast_period
  is '预测周期
';
comment on column MM_SW_LONG_ORDER.forecast_first
  is '预测对象月1
';
comment on column MM_SW_LONG_ORDER.forecast_first_num
  is '预测对象月1数量
';
comment on column MM_SW_LONG_ORDER.forecast_second
  is '预测对象月2
';
comment on column MM_SW_LONG_ORDER.forecast_second_num
  is '预测对象月2数量
';
comment on column MM_SW_LONG_ORDER.forecast_third
  is '预测对象月3
';
comment on column MM_SW_LONG_ORDER.forecast_third_num
  is '预测对象月3数量
';
comment on column MM_SW_LONG_ORDER.creation_time
  is '创建时间
';
comment on column MM_SW_LONG_ORDER.creation_user
  is '创建人
';
comment on column MM_SW_LONG_ORDER.last_modified_user
  is '最后修改人
';
comment on column MM_SW_LONG_ORDER.last_modified_time
  is '最后修改时间
';
comment on column MM_SW_LONG_ORDER.print_status
  is '打印状态
';
comment on column MM_SW_LONG_ORDER.print_time
  is '打印时间
';
comment on column MM_SW_LONG_ORDER.del_flag
  is '0：未删除  1：已删除';
alter table MM_SW_LONG_ORDER
  add constraint PK_MM_SW_LONG_ORDER primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_SW_LONG_ORDER
  add constraint AK_KEY_1_MM_SW_LO unique (VERSION, ORDER_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_LONG_ORDER_DETAIL
prompt ======================================
prompt
create table MM_SW_LONG_ORDER_DETAIL
(
  version             VARCHAR2(30),
  order_no            VARCHAR2(50),
  part_no             VARCHAR2(30),
  car_type            VARCHAR2(10),
  supplier_no         VARCHAR2(10),
  sup_factory         VARCHAR2(10),
  require_num         NUMBER(19),
  order_require_num   NUMBER(19),
  part_unit           VARCHAR2(10),
  order_num           NUMBER(19),
  order_box           NUMBER(19),
  current_deliv_qty   NUMBER(10),
  total_deliv_qty     NUMBER(10),
  feedback_remark     VARCHAR2(300),
  feedback_status     NUMBER(1) default 0,
  forecast_first_num  NUMBER(19),
  forecast_second_num NUMBER(19),
  forecast_third_num  NUMBER(19),
  del_flag            NUMBER(2) default 0,
  email_flag          NUMBER(2) default 0,
  receive_qty         NUMBER(19)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table MM_SW_LONG_ORDER_DETAIL
  is 'MM_SW_LONG_ORDER_DETAIL长周期订单明细表';
comment on column MM_SW_LONG_ORDER_DETAIL.version
  is '"版本号
"';
comment on column MM_SW_LONG_ORDER_DETAIL.order_no
  is '"订单号
"';
comment on column MM_SW_LONG_ORDER_DETAIL.part_no
  is '"零件号
"';
comment on column MM_SW_LONG_ORDER_DETAIL.car_type
  is '"车型
"';
comment on column MM_SW_LONG_ORDER_DETAIL.supplier_no
  is '"供应商代码
"';
comment on column MM_SW_LONG_ORDER_DETAIL.sup_factory
  is '"ERP下发出货地
"';
comment on column MM_SW_LONG_ORDER_DETAIL.require_num
  is '"收容数
"';
comment on column MM_SW_LONG_ORDER_DETAIL.order_require_num
  is '"订单收容数
"';
comment on column MM_SW_LONG_ORDER_DETAIL.part_unit
  is '"单位
"';
comment on column MM_SW_LONG_ORDER_DETAIL.order_num
  is '"订单个数
"';
comment on column MM_SW_LONG_ORDER_DETAIL.order_box
  is '"订单箱数
"';
comment on column MM_SW_LONG_ORDER_DETAIL.current_deliv_qty
  is '"本次发货数量
"';
comment on column MM_SW_LONG_ORDER_DETAIL.total_deliv_qty
  is '"已发货数量
"';
comment on column MM_SW_LONG_ORDER_DETAIL.del_flag
  is '0：未删除  1：已删除';
comment on column MM_SW_LONG_ORDER_DETAIL.email_flag
  is '0：未发送  1：已发送';
comment on column MM_SW_LONG_ORDER_DETAIL.receive_qty
  is '收货数量';

prompt
prompt Creating table MM_SW_NON_STANDARD
prompt =================================
prompt
create table MM_SW_NON_STANDARD
(
  id                 NUMBER(19) not null,
  factory_code       VARCHAR2(10),
  order_no           VARCHAR2(50),
  sale_no            VARCHAR2(50),
  sale_row_no        NUMBER(10),
  supplier_no        VARCHAR2(20),
  sup_factory        VARCHAR2(10),
  supplier_name      VARCHAR2(150),
  part_no            VARCHAR2(30),
  part_short_no      VARCHAR2(10),
  part_name_cn       VARCHAR2(150),
  order_num          NUMBER(19),
  creation_user      VARCHAR2(30),
  creation_time      DATE,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  last_check_id      NUMBER(19),
  pic_upload_status  NUMBER(2) default 0,
  deal_flag          NUMBER(2),
  deal_time          DATE,
  do_flag            VARCHAR2(10),
  email_flag         NUMBER(2) default 0
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column MM_SW_NON_STANDARD.last_check_id
  is '最后检查ID';
comment on column MM_SW_NON_STANDARD.pic_upload_status
  is '0：未上传 1：已上传';
comment on column MM_SW_NON_STANDARD.email_flag
  is '0：未发送    1：已发送';
alter table MM_SW_NON_STANDARD
  add constraint PK_MM_SW_NON_STANDARD primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_NON_STANDARD_DETAIL
prompt ========================================
prompt
create table MM_SW_NON_STANDARD_DETAIL
(
  sale_no            VARCHAR2(50),
  sale_rowno         NUMBER(10),
  part_no            VARCHAR2(30),
  feature            VARCHAR2(20),
  feature_type       VARCHAR2(2),
  feature_value      VARCHAR2(500),
  sort_no            NUMBER(5),
  creation_time      DATE,
  last_modified_time DATE,
  deal_flag          NUMBER(2),
  deal_time          DATE,
  do_flag            VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column MM_SW_NON_STANDARD_DETAIL.sale_no
  is '销售单号';
comment on column MM_SW_NON_STANDARD_DETAIL.sale_rowno
  is '销售单行号';
comment on column MM_SW_NON_STANDARD_DETAIL.part_no
  is '零件号';
comment on column MM_SW_NON_STANDARD_DETAIL.feature
  is '属性名称';
comment on column MM_SW_NON_STANDARD_DETAIL.feature_type
  is '属性类型';
comment on column MM_SW_NON_STANDARD_DETAIL.feature_value
  is '属性值';
comment on column MM_SW_NON_STANDARD_DETAIL.sort_no
  is '排序码';
comment on column MM_SW_NON_STANDARD_DETAIL.deal_flag
  is '0未处理 1已处理';

prompt
prompt Creating table MM_SW_NON_STANDAR_CHECK
prompt ======================================
prompt
create table MM_SW_NON_STANDAR_CHECK
(
  id               NUMBER(19) not null,
  sale_no          VARCHAR2(50),
  sale_row_no      NUMBER(10),
  part_no          VARCHAR2(50),
  remark           VARCHAR2(300),
  check_result     NUMBER(1) default 0,
  checker          VARCHAR2(30),
  check_time       DATE,
  creation_user    VARCHAR2(50),
  creation_user_ip VARCHAR2(30),
  deal_flag        NUMBER(2),
  deal_time        DATE,
  do_flag          VARCHAR2(10),
  creation_time    DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column MM_SW_NON_STANDAR_CHECK.check_result
  is '"0：未检查
1：不通过
2：通过"';
alter table MM_SW_NON_STANDAR_CHECK
  add constraint PK_MM_SW_NON_STANDAR_CHECK primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_NON_STAND_PIC
prompt ==================================
prompt
create table MM_SW_NON_STAND_PIC
(
  check_id           NUMBER(19),
  sale_no            VARCHAR2(50),
  sale_row_no        NUMBER(10),
  part_no            VARCHAR2(30),
  pic_type           NUMBER(2),
  pic_id             VARCHAR2(60),
  creation_time      DATE,
  creation_user      VARCHAR2(30),
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  deal_flag          NUMBER(2),
  deal_time          DATE,
  do_flag            VARCHAR2(10)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column MM_SW_NON_STAND_PIC.pic_type
  is '0：实物上传的图片  1：反馈的图片';

prompt
prompt Creating table MM_SW_NOTICE
prompt ===========================
prompt
create table MM_SW_NOTICE
(
  notice_id          NUMBER(10) not null,
  factory_code       VARCHAR2(10),
  notice_title       VARCHAR2(150),
  notice_content     VARCHAR2(2000),
  file_name          VARCHAR2(500),
  file_id            VARCHAR2(500),
  publish_user       VARCHAR2(30),
  publish_time       DATE,
  notice_start_time  DATE,
  notice_end_time    DATE,
  download_time      DATE,
  is_feedback        NUMBER(1),
  feedback_status    NUMBER(1) default 0,
  feedback_day       VARCHAR2(5),
  notice_status      NUMBER(1) default 0,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE,
  is_file_feedback   NUMBER(1),
  feedback_file_id   VARCHAR2(500),
  feedback_file_name VARCHAR2(500),
  is_send_mail       NUMBER(1) default 0
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_NOTICE
  is 'MM_SW_NOTICE公告信息
';
comment on column MM_SW_NOTICE.notice_id
  is '公告ID
';
comment on column MM_SW_NOTICE.factory_code
  is '工厂
';
comment on column MM_SW_NOTICE.notice_title
  is '公告标题
';
comment on column MM_SW_NOTICE.notice_content
  is '公告内容
';
comment on column MM_SW_NOTICE.file_name
  is '附件名称
';
comment on column MM_SW_NOTICE.file_id
  is '文件信息表ID
';
comment on column MM_SW_NOTICE.publish_user
  is '发布人员
';
comment on column MM_SW_NOTICE.publish_time
  is '发布时间
';
comment on column MM_SW_NOTICE.notice_start_time
  is '公告开始日期
';
comment on column MM_SW_NOTICE.notice_end_time
  is '公告结束日期
';
comment on column MM_SW_NOTICE.download_time
  is '查看下载日期
';
comment on column MM_SW_NOTICE.is_feedback
  is '是否需要反馈
0-否
1-是';
comment on column MM_SW_NOTICE.feedback_status
  is '反馈状态
0-未反馈
1-已反馈';
comment on column MM_SW_NOTICE.feedback_day
  is '反馈天数
';
comment on column MM_SW_NOTICE.notice_status
  is '公告状态
0-创建
1-发布';
comment on column MM_SW_NOTICE.creation_user
  is '创建人
';
comment on column MM_SW_NOTICE.creation_time
  is '创建时间
';
comment on column MM_SW_NOTICE.last_modified_user
  is '最后修改用户
';
comment on column MM_SW_NOTICE.last_modified_time
  is '最后修改时间
';
comment on column MM_SW_NOTICE.is_file_feedback
  is '是否需要附件反馈
是否需要附件反馈';
comment on column MM_SW_NOTICE.feedback_file_id
  is '反馈附件ID
';
comment on column MM_SW_NOTICE.feedback_file_name
  is '反馈附件名称
';
comment on column MM_SW_NOTICE.is_send_mail
  is '是否已发送邮件
';
alter table MM_SW_NOTICE
  add constraint PK_MM_SW_NOTICE primary key (NOTICE_ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_SW_NOTICE
  add constraint AK_MM_SW_NOTICE unique (FACTORY_CODE, NOTICE_TITLE)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_NOTICE_SUPGROUP
prompt ====================================
prompt
create table MM_SW_NOTICE_SUPGROUP
(
  notice_id          NUMBER(10) not null,
  group_id           NUMBER(10) not null,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_NOTICE_SUPGROUP
  is 'MM_SW_NOTICE_SUPGROUP公告供应商组关系
';
comment on column MM_SW_NOTICE_SUPGROUP.notice_id
  is '公告ID
公告ID';
comment on column MM_SW_NOTICE_SUPGROUP.group_id
  is '供应商分组ID
供应商分组ID';
comment on column MM_SW_NOTICE_SUPGROUP.creation_user
  is '创建人
';
comment on column MM_SW_NOTICE_SUPGROUP.creation_time
  is '创建时间
';
comment on column MM_SW_NOTICE_SUPGROUP.last_modified_user
  is '最后修改用户
';
comment on column MM_SW_NOTICE_SUPGROUP.last_modified_time
  is '最后修改时间
';
alter table MM_SW_NOTICE_SUPGROUP
  add constraint PK_MM_SW_NOTICE_SUPGROUP primary key (NOTICE_ID, GROUP_ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_NOTICE_VIEW
prompt ================================
prompt
create table MM_SW_NOTICE_VIEW
(
  notice_id       NUMBER(10) not null,
  supplier_no     VARCHAR2(10) not null,
  notice_status   NUMBER(1) default 0,
  notice_time     DATE,
  view_status     NUMBER(1) default 0,
  view_time       DATE,
  download_status NUMBER(1) default 0,
  download_time   DATE,
  return_msg      VARCHAR2(300),
  file_id         NUMBER(19),
  file_name       VARCHAR2(100),
  feedback_status NUMBER(1) default 0,
  feedback_time   DATE,
  stick_no        VARCHAR2(50),
  is_stick        NUMBER(1)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table MM_SW_NOTICE_VIEW
  is 'MM_SW_NOTICE_VIEW公告供应商查看记录
';
comment on column MM_SW_NOTICE_VIEW.notice_id
  is '公告ID
';
comment on column MM_SW_NOTICE_VIEW.supplier_no
  is '供应商代码
';
comment on column MM_SW_NOTICE_VIEW.notice_status
  is '通知状态
0:未通知
1:已通知';
comment on column MM_SW_NOTICE_VIEW.notice_time
  is '通知时间
';
comment on column MM_SW_NOTICE_VIEW.view_status
  is '查看状态
0:未查看
1:已查看';
comment on column MM_SW_NOTICE_VIEW.view_time
  is '查看时间
';
comment on column MM_SW_NOTICE_VIEW.download_status
  is '下载状态
0:未下载
1:已下载
';
comment on column MM_SW_NOTICE_VIEW.download_time
  is '下载时间
';
comment on column MM_SW_NOTICE_VIEW.return_msg
  is '回复信息
';
comment on column MM_SW_NOTICE_VIEW.file_id
  is '供应商反馈附件ID
供应商反馈附件ID';
comment on column MM_SW_NOTICE_VIEW.file_name
  is '供应商反馈附件名称
供应商反馈附件名称';
comment on column MM_SW_NOTICE_VIEW.feedback_status
  is '反馈状态
反馈状态';
comment on column MM_SW_NOTICE_VIEW.feedback_time
  is '反馈时间
反馈时间';
comment on column MM_SW_NOTICE_VIEW.stick_no
  is '置顶排序
';
comment on column MM_SW_NOTICE_VIEW.is_stick
  is '是否置顶
';
alter table MM_SW_NOTICE_VIEW
  add constraint PK_MM_SW_NOTICE_VIEW primary key (NOTICE_ID, SUPPLIER_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_ORDER
prompt ==========================
prompt
create table MM_SW_ORDER
(
  purchase_no         VARCHAR2(50) not null,
  order_no            VARCHAR2(50),
  order_type          VARCHAR2(2),
  factory_code        VARCHAR2(10),
  supplier_no         VARCHAR2(20),
  order_date          DATE,
  arrive_date         DATE,
  depot_no            VARCHAR2(20),
  sup_factory         VARCHAR2(20),
  print_status        NUMBER(1) default 0,
  print_time          DATE,
  label_print_status  NUMBER(1) default 0,
  label_print_time    DATE,
  delivery_status     NUMBER(1) default 0,
  receive_status      NUMBER(1) default 0,
  download_status     NUMBER(1) default 0,
  download_time       DATE,
  reply_delivery_date DATE,
  is_urgent           NUMBER(1) default 0,
  creation_user       VARCHAR2(30),
  creation_time       DATE default sysdate,
  last_modified_user  VARCHAR2(30),
  last_modified_time  DATE,
  sp_type             VARCHAR2(2),
  receive_date        DATE,
  receive_count       NUMBER(5),
  prepare_status      NUMBER(1),
  prepare_count       NUMBER(5),
  prepare_time        DATE,
  hms_order_no        VARCHAR2(50),
  rec_address         VARCHAR2(300),
  rec_user            VARCHAR2(30),
  rec_tel             VARCHAR2(30),
  planner             VARCHAR2(30),
  deal_flag           NUMBER(1) default 0,
  deal_time           DATE,
  plan_prepare_time   DATE,
  zg_flag             NUMBER(1),
  print_user          VARCHAR2(30),
  print_user_ip       VARCHAR2(30),
  email_flag          NUMBER(2) default 0
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table MM_SW_ORDER
  is 'MM_SW_ORDER采购订单表';
comment on column MM_SW_ORDER.purchase_no
  is '"采购单号
"';
comment on column MM_SW_ORDER.order_no
  is '"物流订单号
"';
comment on column MM_SW_ORDER.order_type
  is '"订单类型
01:零件订单；02:例外订单；03:拉动订单:；04:同步订单；05:试制件订单；11:长周期订单；12:售后订单(SP)；14：资材订单；"';
comment on column MM_SW_ORDER.factory_code
  is '"工厂代码
"';
comment on column MM_SW_ORDER.supplier_no
  is '"供应商代码
"';
comment on column MM_SW_ORDER.order_date
  is '"订购日期
"';
comment on column MM_SW_ORDER.arrive_date
  is '"到货日期
"';
comment on column MM_SW_ORDER.depot_no
  is '"仓库编码
"';
comment on column MM_SW_ORDER.sup_factory
  is '"出货地代码
"';
comment on column MM_SW_ORDER.print_status
  is '"打印状态
0:未打印
1:已打印"';
comment on column MM_SW_ORDER.print_time
  is '"打印时间
"';
comment on column MM_SW_ORDER.label_print_status
  is '"标签打印状态
0:未打印
1:已打印"';
comment on column MM_SW_ORDER.label_print_time
  is '"标签打印时间
"';
comment on column MM_SW_ORDER.delivery_status
  is '"发货状态
0-未发货
1-部分发货
2-全部发货"';
comment on column MM_SW_ORDER.receive_status
  is '"收货状态
0-未收货
1-部分收货
2-全部收货"';
comment on column MM_SW_ORDER.download_status
  is '"下载状态
0-未下载
1-已下载"';
comment on column MM_SW_ORDER.download_time
  is '"下载时间
"';
comment on column MM_SW_ORDER.reply_delivery_date
  is '"供应商反馈交付日期
"';
comment on column MM_SW_ORDER.is_urgent
  is '"是否紧急订单
0-正常
1-紧急"';
comment on column MM_SW_ORDER.creation_user
  is '"创建人
"';
comment on column MM_SW_ORDER.creation_time
  is '"创建时间
"';
comment on column MM_SW_ORDER.last_modified_user
  is '"最后修改用户
"';
comment on column MM_SW_ORDER.last_modified_time
  is '"最后修改时间
"';
comment on column MM_SW_ORDER.sp_type
  is '"售后订单类型
1:内制件；2：直供件；3:外采件"';
comment on column MM_SW_ORDER.receive_date
  is '"收货日期
"';
comment on column MM_SW_ORDER.receive_count
  is '"收货次数
"';
comment on column MM_SW_ORDER.prepare_status
  is '"备货状态
数据字典类型“PREPARE_STATUS”
"';
comment on column MM_SW_ORDER.prepare_count
  is '"备货次数
"';
comment on column MM_SW_ORDER.prepare_time
  is '"备件时间
"';
comment on column MM_SW_ORDER.hms_order_no
  is '"HMS订单号
"';
comment on column MM_SW_ORDER.rec_address
  is '"收货地址信息
"';
comment on column MM_SW_ORDER.rec_user
  is '"收货联系人
"';
comment on column MM_SW_ORDER.rec_tel
  is '"收货联系电话
"';
comment on column MM_SW_ORDER.planner
  is '"采购员/计划员
"';
comment on column MM_SW_ORDER.deal_flag
  is '"处理标识
处理标识
0 未处理 1已处理"';
comment on column MM_SW_ORDER.deal_time
  is '"处理时间
处理时间
"';
comment on column MM_SW_ORDER.plan_prepare_time
  is '计划备货时间 ';
comment on column MM_SW_ORDER.zg_flag
  is '支给标识 ';
comment on column MM_SW_ORDER.print_user
  is '打印人';
comment on column MM_SW_ORDER.print_user_ip
  is '打印IP';
comment on column MM_SW_ORDER.email_flag
  is '邮件标识  0：未发送  1：已发送';
create index IDX_MM_SW_ORDER1 on MM_SW_ORDER (ORDER_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index IDX_MM_SW_ORDER2 on MM_SW_ORDER (CREATION_TIME)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index IDX_MM_SW_ORDER3 on MM_SW_ORDER (PRINT_STATUS)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index IDX_MM_SW_ORDER4 on MM_SW_ORDER (DELIVERY_STATUS)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index IDX_MM_SW_ORDER5 on MM_SW_ORDER (RECEIVE_STATUS)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MM_SW_ORDER
  add constraint PK_MM_SW_ORDER primary key (PURCHASE_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_SW_ORDER_DETAIL
prompt =================================
prompt
create table MM_SW_ORDER_DETAIL
(
  id                 NUMBER(10) not null,
  purchase_no        VARCHAR2(50),
  purchase_rowno     NUMBER(10),
  order_rowno        NUMBER(10),
  depot_no           VARCHAR2(20),
  order_qty          NUMBER(19,2),
  order_unit         VARCHAR2(20),
  part_no            VARCHAR2(20),
  standard_package   NUMBER(10,2),
  total_deliv_qty    NUMBER(10),
  total_rec_qty      NUMBER(10) default 0,
  delivery_status    NUMBER(1) default 0,
  receive_status     NUMBER(1) default 0,
  receive_date       DATE,
  receive_count      NUMBER(5) default 0,
  cancel_num         NUMBER(19,2) default 0,
  cost_center        VARCHAR2(50),
  zk_flag            VARCHAR2(10),
  inv_type           VARCHAR2(30),
  cost_code          VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_time DATE,
  prepare_num        NUMBER(19),
  arrive_num         NUMBER(19),
  order_no           VARCHAR2(50),
  current_qty        NUMBER(19),
  depot_address      VARCHAR2(20),
  use                VARCHAR2(300),
  demand_department  VARCHAR2(150),
  demander           VARCHAR2(100),
  con_number         VARCHAR2(30)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table MM_SW_ORDER_DETAIL
  is 'MM_SW_ORDER_DETAIL采购订单明细表';
comment on column MM_SW_ORDER_DETAIL.id
  is '"订单明细ID
"';
comment on column MM_SW_ORDER_DETAIL.purchase_no
  is '"采购订单号
"';
comment on column MM_SW_ORDER_DETAIL.purchase_rowno
  is '"采购订单行号
"';
comment on column MM_SW_ORDER_DETAIL.order_rowno
  is '"物流订单行号
"';
comment on column MM_SW_ORDER_DETAIL.depot_no
  is '"到货仓库
"';
comment on column MM_SW_ORDER_DETAIL.order_qty
  is '"订购数量
"';
comment on column MM_SW_ORDER_DETAIL.order_unit
  is '"订购单位
"';
comment on column MM_SW_ORDER_DETAIL.part_no
  is '"零件编号
"';
comment on column MM_SW_ORDER_DETAIL.standard_package
  is '"包装规格
"';
comment on column MM_SW_ORDER_DETAIL.total_deliv_qty
  is '"累计发货数量
"';
comment on column MM_SW_ORDER_DETAIL.total_rec_qty
  is '"累计收货数量
"';
comment on column MM_SW_ORDER_DETAIL.delivery_status
  is '"发货状态
0:未发货
1:部分发货
2:全部发货"';
comment on column MM_SW_ORDER_DETAIL.receive_status
  is '"收货状态
0:未收货
1:部分收货
2:全部收货"';
comment on column MM_SW_ORDER_DETAIL.receive_date
  is '"收货日期
"';
comment on column MM_SW_ORDER_DETAIL.receive_count
  is '"收货次数
"';
comment on column MM_SW_ORDER_DETAIL.cancel_num
  is '"取消数量
"';
comment on column MM_SW_ORDER_DETAIL.cost_center
  is '"成本中心
资材订单使用"';
comment on column MM_SW_ORDER_DETAIL.zk_flag
  is '"是否直捆
N-否
Y-是
2019/01/08修改"';
comment on column MM_SW_ORDER_DETAIL.inv_type
  is '"库存区分
资材订单使用"';
comment on column MM_SW_ORDER_DETAIL.cost_code
  is '"费用归类代码
资材订单使用"';
comment on column MM_SW_ORDER_DETAIL.creation_time
  is '"创建时间
"';
comment on column MM_SW_ORDER_DETAIL.last_modified_time
  is '"最后修改时间
"';
comment on column MM_SW_ORDER_DETAIL.prepare_num
  is '"已备数量
"';
comment on column MM_SW_ORDER_DETAIL.arrive_num
  is '"到货数量
"';
comment on column MM_SW_ORDER_DETAIL.order_no
  is '"物流订单号
"';
comment on column MM_SW_ORDER_DETAIL.depot_address
  is '仓库货架号';
comment on column MM_SW_ORDER_DETAIL.use
  is '用途';
comment on column MM_SW_ORDER_DETAIL.demand_department
  is '需求部门';
comment on column MM_SW_ORDER_DETAIL.demander
  is '需求人';
comment on column MM_SW_ORDER_DETAIL.con_number
  is '联系电话';
create index IDX_MM_SW_ORDER_DETAIL1 on MM_SW_ORDER_DETAIL (PURCHASE_NO)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MM_SW_ORDER_DETAIL
  add constraint PK_MM_SW_ORDER_DETAIL primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MM_SW_ORDER_DETAIL
  add constraint AK_MM_SW_ORDER_DETAIL unique (PURCHASE_NO, PURCHASE_ROWNO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table MM_SW_PICKUP_PLAN
prompt ================================
prompt
create table MM_SW_PICKUP_PLAN
(
  order_no            VARCHAR2(50) not null,
  purchase_no         VARCHAR2(50) not null,
  confirm_days        NUMBER(10),
  inter_logis_manager VARCHAR2(30),
  order_use           VARCHAR2(100),
  plan_assemble_time  DATE,
  plan_arr_time       DATE,
  plan_pickup_time    DATE,
  today_car_batch     NUMBER(10),
  feedback_status     NUMBER(1) default 0,
  logistics_mode      VARCHAR2(50),
  area                VARCHAR2(50),
  car_type            VARCHAR2(30),
  route_code          VARCHAR2(30),
  total_batchs        VARCHAR2(30),
  merge_batchs        NUMBER(10),
  sup_factory         VARCHAR2(20),
  supplier_no         VARCHAR2(20),
  work_date           DATE,
  factory_code        VARCHAR2(10),
  order_desc          VARCHAR2(200),
  pickup_type         VARCHAR2(30),
  order_arr_date      DATE,
  unload_port         VARCHAR2(20),
  adj_status          NUMBER(1),
  download_status     NUMBER(1) default 0,
  download_time       DATE,
  creation_user       VARCHAR2(30),
  creation_time       DATE default sysdate,
  last_modified_user  VARCHAR2(30),
  last_modified_time  DATE,
  diff_flag           NUMBER(1)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table MM_SW_PICKUP_PLAN
  is 'MM_SW_PICKUP_PLAN取货计划';
comment on column MM_SW_PICKUP_PLAN.order_no
  is '"物流订单号
"';
comment on column MM_SW_PICKUP_PLAN.purchase_no
  is '"采购订单号
"';
comment on column MM_SW_PICKUP_PLAN.confirm_days
  is '"确认天数
"';
comment on column MM_SW_PICKUP_PLAN.inter_logis_manager
  is '"内物流管理员
"';
comment on column MM_SW_PICKUP_PLAN.order_use
  is '"订购用途
"';
comment on column MM_SW_PICKUP_PLAN.plan_assemble_time
  is '"计划装配时间
"';
comment on column MM_SW_PICKUP_PLAN.plan_arr_time
  is '"计划到货时间
"';
comment on column MM_SW_PICKUP_PLAN.plan_pickup_time
  is '"计划取货时间
"';
comment on column MM_SW_PICKUP_PLAN.today_car_batch
  is '"当日车次
"';
comment on column MM_SW_PICKUP_PLAN.feedback_status
  is '"反馈状态
0-未反馈
1-NG
2-OK"';
comment on column MM_SW_PICKUP_PLAN.logistics_mode
  is '"物流模式
"';
comment on column MM_SW_PICKUP_PLAN.area
  is '"区域
"';
comment on column MM_SW_PICKUP_PLAN.car_type
  is '"车型
"';
comment on column MM_SW_PICKUP_PLAN.route_code
  is '"路线
"';
comment on column MM_SW_PICKUP_PLAN.total_batchs
  is '"累计车次
"';
comment on column MM_SW_PICKUP_PLAN.merge_batchs
  is '"合并车次
"';
comment on column MM_SW_PICKUP_PLAN.sup_factory
  is '"出货地编码
"';
comment on column MM_SW_PICKUP_PLAN.supplier_no
  is '"供应商代码
"';
comment on column MM_SW_PICKUP_PLAN.work_date
  is '"工作日
"';
comment on column MM_SW_PICKUP_PLAN.factory_code
  is '"工厂
"';
comment on column MM_SW_PICKUP_PLAN.order_desc
  is '"订单说明
"';
comment on column MM_SW_PICKUP_PLAN.pickup_type
  is '"取货区分
取货区分(0:取货;1:送货;2:支给)"';
comment on column MM_SW_PICKUP_PLAN.order_arr_date
  is '"订单到货日期
"';
comment on column MM_SW_PICKUP_PLAN.unload_port
  is '"卸货口
"';
comment on column MM_SW_PICKUP_PLAN.adj_status
  is '"调整状态
0-无调整 1-有调整 2-新发布"';
comment on column MM_SW_PICKUP_PLAN.download_status
  is '"下载状态
0-未下载
1-已下载"';
comment on column MM_SW_PICKUP_PLAN.download_time
  is '"下载时间
"';
comment on column MM_SW_PICKUP_PLAN.creation_user
  is '"创建人
"';
comment on column MM_SW_PICKUP_PLAN.creation_time
  is '"创建时间
"';
comment on column MM_SW_PICKUP_PLAN.last_modified_user
  is '"最后修改用户
"';
comment on column MM_SW_PICKUP_PLAN.last_modified_time
  is '"最后修改时间
"';
create index IDX_MM_SW_PICKUP_PLAN1 on MM_SW_PICKUP_PLAN (PLAN_PICKUP_TIME)
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_SW_PICKUP_PLAN
  add constraint PK_MM_SW_PICKUP_PLAN primary key (ORDER_NO, PURCHASE_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_SUPPLIER_GROUP
prompt ===================================
prompt
create table MM_SW_SUPPLIER_GROUP
(
  group_id           NUMBER(10) not null,
  group_name         VARCHAR2(60),
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table MM_SW_SUPPLIER_GROUP
  is 'MM_SW_SUPPLIER_GROUP供应商分组维护';
comment on column MM_SW_SUPPLIER_GROUP.group_id
  is '"分组编码
"';
comment on column MM_SW_SUPPLIER_GROUP.group_name
  is '"分组名称
"';
comment on column MM_SW_SUPPLIER_GROUP.creation_user
  is '"创建人
"';
comment on column MM_SW_SUPPLIER_GROUP.creation_time
  is '"创建时间
"';
comment on column MM_SW_SUPPLIER_GROUP.last_modified_user
  is '"最后修改用户
"';
comment on column MM_SW_SUPPLIER_GROUP.last_modified_time
  is '"最后修改时间
"';
alter table MM_SW_SUPPLIER_GROUP
  add constraint PK_MM_SW_SUPPLIER_GROUP primary key (GROUP_ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;
alter table MM_SW_SUPPLIER_GROUP
  add constraint AK_MM_SW_SUP_GROUP unique (GROUP_NAME)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_SUPPLIER_GROUP_IMP
prompt =======================================
prompt
create table MM_SW_SUPPLIER_GROUP_IMP
(
  id            NUMBER(19) not null,
  group_name    VARCHAR2(60),
  supplier_no   VARCHAR2(20),
  factory_code  VARCHAR2(10),
  creation_user VARCHAR2(30),
  creation_time DATE default SYSDATE,
  imp_uuid      VARCHAR2(50),
  check_result  NUMBER(1),
  check_info    VARCHAR2(300),
  import_status NUMBER(1),
  ope_type      VARCHAR2(3) default 'I'
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table MM_SW_SUPPLIER_GROUP_IMP
  is '供应商分组维护导入临时表';
comment on column MM_SW_SUPPLIER_GROUP_IMP.id
  is '"ID
"';
comment on column MM_SW_SUPPLIER_GROUP_IMP.group_name
  is '"GROUP_NAME
"';
comment on column MM_SW_SUPPLIER_GROUP_IMP.supplier_no
  is '"SUPPLIER_NO
"';
comment on column MM_SW_SUPPLIER_GROUP_IMP.factory_code
  is '"FACTORY_CODE
"';
comment on column MM_SW_SUPPLIER_GROUP_IMP.creation_user
  is '"CREATION_USER
"';
comment on column MM_SW_SUPPLIER_GROUP_IMP.creation_time
  is '"CREATION_TIME
"';
comment on column MM_SW_SUPPLIER_GROUP_IMP.imp_uuid
  is '"IMP_UUID
"';
comment on column MM_SW_SUPPLIER_GROUP_IMP.check_result
  is '"CHECK_RESULT
"';
comment on column MM_SW_SUPPLIER_GROUP_IMP.check_info
  is '"CHECK_INFO
"';
comment on column MM_SW_SUPPLIER_GROUP_IMP.import_status
  is '"IMPORT_STATUS
"';
comment on column MM_SW_SUPPLIER_GROUP_IMP.ope_type
  is '"OPE_TYPE
"';
alter table MM_SW_SUPPLIER_GROUP_IMP
  add constraint PK_MM_SW_SUPPLIER_GROUP_IMP primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table MM_SW_SUP_GROUP_MEMBERS
prompt ======================================
prompt
create table MM_SW_SUP_GROUP_MEMBERS
(
  group_id           NUMBER(10) not null,
  supplier_no        VARCHAR2(20) not null,
  creation_user      VARCHAR2(30),
  creation_time      DATE default sysdate,
  last_modified_user VARCHAR2(30),
  last_modified_time DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table MM_SW_SUP_GROUP_MEMBERS
  is 'MM_SW_SUP_GROUP_MEMBERS供应商分组成员';
comment on column MM_SW_SUP_GROUP_MEMBERS.group_id
  is '"分组ID
"';
comment on column MM_SW_SUP_GROUP_MEMBERS.supplier_no
  is '"供应商代码
"';
comment on column MM_SW_SUP_GROUP_MEMBERS.creation_user
  is '"创建人
"';
comment on column MM_SW_SUP_GROUP_MEMBERS.creation_time
  is '"创建时间
"';
comment on column MM_SW_SUP_GROUP_MEMBERS.last_modified_user
  is '"最后修改用户
"';
comment on column MM_SW_SUP_GROUP_MEMBERS.last_modified_time
  is '"最后修改时间
"';
alter table MM_SW_SUP_GROUP_MEMBERS
  add constraint PK_MM_SW_SUP_GROUP_MEMBERS primary key (GROUP_ID, SUPPLIER_NO)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_DATASOURCE
prompt =============================
prompt
create table SYS_DATASOURCE
(
  id_            VARCHAR2(64) not null,
  name_          VARCHAR2(128) default '名称',
  alias_         VARCHAR2(64) not null,
  db_type_       VARCHAR2(64),
  setting_json_  CLOB,
  init_on_start_ NUMBER,
  enabled_       NUMBER,
  class_path_    VARCHAR2(128),
  init_method_   VARCHAR2(200) default '初始化方法，有些可以不填写',
  close_method_  VARCHAR2(150) default '关闭数据源的时候应该调用的方法，可不填'
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column SYS_DATASOURCE.id_
  is '主键';
comment on column SYS_DATASOURCE.alias_
  is '别名';
comment on column SYS_DATASOURCE.db_type_
  is '数据源id';
comment on column SYS_DATASOURCE.setting_json_
  is 'Json存储配置';
comment on column SYS_DATASOURCE.init_on_start_
  is '在启动时，启动连接池，并添加到spring容器中管理。';
comment on column SYS_DATASOURCE.enabled_
  is '是否生效';
alter table SYS_DATASOURCE
  add constraint PK_SYS_DATASOURCE primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_DEMENSION
prompt ============================
prompt
create table SYS_DEMENSION
(
  id_         VARCHAR2(64) not null,
  dem_name_   VARCHAR2(100),
  dem_desc    VARCHAR2(300),
  dem_code_   VARCHAR2(100),
  is_default_ NUMBER
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_DEMENSION
  is '维度管理';
comment on column SYS_DEMENSION.id_
  is '维度id';
comment on column SYS_DEMENSION.dem_name_
  is '维度名称';
comment on column SYS_DEMENSION.dem_desc
  is '描述';
comment on column SYS_DEMENSION.is_default_
  is '是否默认维度';
alter table SYS_DEMENSION
  add constraint PK_SYS_DEMENSION primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_DIC
prompt ======================
prompt
create table SYS_DIC
(
  id_        VARCHAR2(64) not null,
  type_id_   VARCHAR2(64),
  key_       VARCHAR2(40),
  name_      VARCHAR2(128),
  parent_id_ VARCHAR2(64),
  sn_        NUMBER
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_DIC
  is '数据字典';
comment on column SYS_DIC.id_
  is '主键';
comment on column SYS_DIC.type_id_
  is '类型ID';
comment on column SYS_DIC.key_
  is '字典值代码,在同一个字典中值不能重复';
comment on column SYS_DIC.name_
  is '字典值名称';
comment on column SYS_DIC.parent_id_
  is '父ID';
comment on column SYS_DIC.sn_
  is '排序号';
alter table SYS_DIC
  add constraint PK_SYS_DIC primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_DP_BASE_DATA
prompt ===============================
prompt
create table SYS_DP_BASE_DATA
(
  dp_base_id       NUMBER(18) not null,
  type_code        VARCHAR2(32) not null,
  value_code       VARCHAR2(64) not null,
  value_desc       VARCHAR2(128),
  sort_num         NUMBER(8),
  create_user      VARCHAR2(32),
  create_time      DATE default SYSDATE,
  last_modify_user VARCHAR2(32),
  last_modify_time DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table SYS_DP_BASE_DATA
  add constraint PK_SYS_DP_BASE_DATA primary key (DP_BASE_ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_DP_ROLE
prompt ==========================
prompt
create table SYS_DP_ROLE
(
  data_role_id     NUMBER(18) not null,
  data_role_name   VARCHAR2(256),
  data_role_type   VARCHAR2(32),
  remark           VARCHAR2(512),
  sort_num         NUMBER(8),
  create_user      VARCHAR2(32),
  create_time      DATE default SYSDATE,
  last_modify_user VARCHAR2(32),
  last_modify_time DATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table SYS_DP_ROLE
  add constraint PK_SYS_DP_ROLE primary key (DATA_ROLE_ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_DP_ROLE_DATA
prompt ===============================
prompt
create table SYS_DP_ROLE_DATA
(
  dp_base_id   NUMBER(18) not null,
  data_role_id NUMBER(18) not null,
  create_user  VARCHAR2(32),
  create_time  DATE default SYSDATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table SYS_DP_ROLE_DATA
  add constraint PK_SYS_DP_ROLE_DATA primary key (DATA_ROLE_ID, DP_BASE_ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_DP_USER_ROLE
prompt ===============================
prompt
create table SYS_DP_USER_ROLE
(
  user_id      VARCHAR2(64) not null,
  data_role_id NUMBER(18) not null,
  create_user  VARCHAR2(32),
  create_time  DATE default SYSDATE
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table SYS_DP_USER_ROLE
  add constraint PK_SYS_DP_USER_ROLE primary key (USER_ID, DATA_ROLE_ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_FILE
prompt =======================
prompt
create table SYS_FILE
(
  id_           VARCHAR2(64) not null,
  xb_type_id_   VARCHAR2(64),
  file_name_    VARCHAR2(128) not null,
  file_type_    VARCHAR2(40),
  store_type_   VARCHAR2(40) not null,
  file_path_    VARCHAR2(255),
  bytes_        BLOB,
  byte_count_   NUMBER,
  ext_          VARCHAR2(20),
  note_         VARCHAR2(255),
  creator_      VARCHAR2(64),
  creator_name_ VARCHAR2(64),
  create_time_  TIMESTAMP(6) not null,
  is_del_       CHAR(1) not null
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column SYS_FILE.id_
  is '主键';
comment on column SYS_FILE.xb_type_id_
  is '附件分类ID';
alter table SYS_FILE
  add constraint PK_SYS_FILE primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_IDENTITY
prompt ===========================
prompt
create table SYS_IDENTITY
(
  id_         VARCHAR2(64) not null,
  name_       VARCHAR2(64),
  alias_      VARCHAR2(20),
  regulation_ VARCHAR2(128),
  gen_type_   NUMBER,
  no_length_  NUMBER,
  cur_date_   VARCHAR2(20),
  init_value_ NUMBER,
  cur_value_  NUMBER,
  step_       NUMBER
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_IDENTITY
  is '流水号定义';
comment on column SYS_IDENTITY.id_
  is '主键';
comment on column SYS_IDENTITY.name_
  is '名称';
comment on column SYS_IDENTITY.alias_
  is '别名';
comment on column SYS_IDENTITY.regulation_
  is '规则';
comment on column SYS_IDENTITY.gen_type_
  is '生成类型';
comment on column SYS_IDENTITY.no_length_
  is '流水号长度';
comment on column SYS_IDENTITY.cur_date_
  is '当前日期';
comment on column SYS_IDENTITY.init_value_
  is '初始值';
comment on column SYS_IDENTITY.cur_value_
  is '当前值';
comment on column SYS_IDENTITY.step_
  is '步长';
alter table SYS_IDENTITY
  add constraint PK_SYS_IDENTITY primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_LOG_ERR
prompt ==========================
prompt
create table SYS_LOG_ERR
(
  id_          VARCHAR2(50) not null,
  account_     VARCHAR2(20),
  ip_          VARCHAR2(20),
  url_         VARCHAR2(1500),
  content_     CLOB,
  create_time_ TIMESTAMP(6)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table SYS_LOG_ERR
  is '系统出错日志';
comment on column SYS_LOG_ERR.id_
  is '主键';
comment on column SYS_LOG_ERR.account_
  is '帐号';
comment on column SYS_LOG_ERR.ip_
  is 'IP来源';
comment on column SYS_LOG_ERR.url_
  is '错误URL';
comment on column SYS_LOG_ERR.content_
  is '出错信息';
comment on column SYS_LOG_ERR.create_time_
  is '出错时间';
alter table SYS_LOG_ERR
  add constraint PK_SYS_LOG_ERR primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_ORG
prompt ======================
prompt
create table SYS_ORG
(
  id_        VARCHAR2(64) not null,
  name_      VARCHAR2(64) not null,
  parent_id_ VARCHAR2(64),
  order_no_  NUMBER default 100,
  code_      VARCHAR2(64) not null,
  grade_     VARCHAR2(64),
  path_      VARCHAR2(600),
  path_name_ VARCHAR2(600),
  dem_id_    VARCHAR2(64)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_ORG
  is '组织架构';
comment on column SYS_ORG.id_
  is '主键';
comment on column SYS_ORG.grade_
  is '级别';
comment on column SYS_ORG.path_
  is '组织id路径，包含层级关系  ';
comment on column SYS_ORG.path_name_
  is '组织名路径，包含层级关系';
comment on column SYS_ORG.dem_id_
  is '对应维度id';
alter table SYS_ORG
  add constraint PK_SYS_ORG primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_ORG_AUTH
prompt ===========================
prompt
create table SYS_ORG_AUTH
(
  id_            VARCHAR2(64) not null,
  user_id_       VARCHAR2(64),
  org_id_        VARCHAR2(64),
  dem_id_        VARCHAR2(64),
  org_perms_     VARCHAR2(200),
  user_perms_    VARCHAR2(200),
  pos_perms_     VARCHAR2(200),
  orgauth_perms_ VARCHAR2(200),
  layout_perms_  VARCHAR2(200)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_ORG_AUTH
  is '分级组织管理';
comment on column SYS_ORG_AUTH.user_id_
  is '分级组织管理员id';
comment on column SYS_ORG_AUTH.org_id_
  is '对应管理组织id';
comment on column SYS_ORG_AUTH.dem_id_
  is '对应维度id';
comment on column SYS_ORG_AUTH.org_perms_
  is '组织管理权限';
comment on column SYS_ORG_AUTH.user_perms_
  is '用户管理权限';
comment on column SYS_ORG_AUTH.pos_perms_
  is '岗位管理权限';
comment on column SYS_ORG_AUTH.orgauth_perms_
  is '分级管理员权限';
alter table SYS_ORG_AUTH
  add constraint PK_SYS_ORG_AUTH primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_ORG_PARAMS
prompt =============================
prompt
create table SYS_ORG_PARAMS
(
  id_     VARCHAR2(50) not null,
  org_id_ VARCHAR2(50),
  alias_  VARCHAR2(50),
  value_  VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_ORG_PARAMS
  is '组织参数';
alter table SYS_ORG_PARAMS
  add constraint PK_SYS_ORG_PARAMS primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_ORG_REL
prompt ==========================
prompt
create table SYS_ORG_REL
(
  id_         VARCHAR2(64) not null,
  org_id_     VARCHAR2(64),
  rel_def_id_ VARCHAR2(64),
  rel_name_   VARCHAR2(64),
  rel_code_   VARCHAR2(64)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_ORG_REL
  is '部门岗位';
comment on column SYS_ORG_REL.rel_name_
  is '岗位名称';
comment on column SYS_ORG_REL.rel_code_
  is '岗位编码';
alter table SYS_ORG_REL
  add constraint PK_SYS_ORG_REL primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_ORG_RELDEF
prompt =============================
prompt
create table SYS_ORG_RELDEF
(
  id_          VARCHAR2(64) not null,
  name_        VARCHAR2(64) not null,
  code_        VARCHAR2(64) not null,
  post_level_  VARCHAR2(64),
  description_ VARCHAR2(500)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_ORG_RELDEF
  is '职务定义';
comment on column SYS_ORG_RELDEF.name_
  is '名称';
comment on column SYS_ORG_RELDEF.code_
  is '编码';
comment on column SYS_ORG_RELDEF.post_level_
  is '职务级别';
comment on column SYS_ORG_RELDEF.description_
  is '描述';
alter table SYS_ORG_RELDEF
  add constraint PK_SYS_ORG_RELDEF primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_ORG_USER
prompt ===========================
prompt
create table SYS_ORG_USER
(
  id_        VARCHAR2(64) not null,
  org_id_    VARCHAR2(64) not null,
  user_id_   VARCHAR2(64) not null,
  is_master_ NUMBER default 0 not null,
  rel_id_    VARCHAR2(64),
  is_charge_ NUMBER default 0
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_ORG_USER
  is '用户组织关系';
comment on column SYS_ORG_USER.org_id_
  is '组织编号';
comment on column SYS_ORG_USER.user_id_
  is '用户编号';
comment on column SYS_ORG_USER.is_master_
  is '0:非主部门，1：主部门';
comment on column SYS_ORG_USER.rel_id_
  is '岗位编号';
comment on column SYS_ORG_USER.is_charge_
  is '2组织主负责人，1组织负责人，0 不是负责人  ';
alter table SYS_ORG_USER
  add constraint PK_SYS_ORG_USER primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_PROPERTIES
prompt =============================
prompt
create table SYS_PROPERTIES
(
  id          VARCHAR2(64) not null,
  name        VARCHAR2(64),
  alias       VARCHAR2(64),
  group_      VARCHAR2(64),
  value       VARCHAR2(2000),
  encrypt     NUMBER,
  createtime  TIMESTAMP(6),
  description VARCHAR2(300)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table SYS_PROPERTIES
  is '系统配置属性';
comment on column SYS_PROPERTIES.id
  is '主键';
comment on column SYS_PROPERTIES.name
  is '名称';
comment on column SYS_PROPERTIES.alias
  is '别名';
comment on column SYS_PROPERTIES.group_
  is '分组';
comment on column SYS_PROPERTIES.value
  is '参数值';
comment on column SYS_PROPERTIES.encrypt
  is '是否加密';
comment on column SYS_PROPERTIES.createtime
  is '创建时间';
comment on column SYS_PROPERTIES.description
  is '描述';
alter table SYS_PROPERTIES
  add constraint PK_SYS_PROPERTIES primary key (ID)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_ROLE
prompt =======================
prompt
create table SYS_ROLE
(
  id_         VARCHAR2(64) not null,
  name_       VARCHAR2(64) not null,
  alias_      VARCHAR2(64) not null,
  enabled_    NUMBER default 1 not null,
  description VARCHAR2(200)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table SYS_ROLE
  is '角色管理';
comment on column SYS_ROLE.id_
  is '主键';
comment on column SYS_ROLE.name_
  is '角色名称';
comment on column SYS_ROLE.alias_
  is '英文别名';
comment on column SYS_ROLE.enabled_
  is '可用 0：禁用，1：启用';
comment on column SYS_ROLE.description
  is '描述';
alter table SYS_ROLE
  add constraint PK_SYS_ROLE primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_TYPE
prompt =======================
prompt
create table SYS_TYPE
(
  id_             VARCHAR2(64) not null,
  type_group_key_ VARCHAR2(64) not null,
  name_           VARCHAR2(128) not null,
  type_key_       VARCHAR2(64) not null,
  stru_type_      VARCHAR2(40) not null,
  parent_id_      VARCHAR2(64),
  depth_          NUMBER,
  path_           VARCHAR2(255),
  is_leaf_        CHAR(1),
  owner_id_       VARCHAR2(64),
  sn_             NUMBER not null,
  create_by_      VARCHAR2(64),
  create_time_    TIMESTAMP(6),
  create_org_id_  VARCHAR2(64),
  update_by_      VARCHAR2(64),
  update_time_    TIMESTAMP(6)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table SYS_TYPE
  is '总分类表。用于显示平级或树层次结构的分类，可以允许任何层次结构。';
comment on column SYS_TYPE.id_
  is '分类ID';
comment on column SYS_TYPE.type_group_key_
  is '所属分类组业务主键';
comment on column SYS_TYPE.name_
  is '分类名称';
comment on column SYS_TYPE.type_key_
  is '节点的分类Key';
comment on column SYS_TYPE.stru_type_
  is 'flat 平行；tree 树形';
comment on column SYS_TYPE.parent_id_
  is '父节点';
comment on column SYS_TYPE.depth_
  is '层次';
comment on column SYS_TYPE.path_
  is '路径';
comment on column SYS_TYPE.is_leaf_
  is '是否叶子节点。Y=是；N=否';
comment on column SYS_TYPE.sn_
  is '序号';
comment on column SYS_TYPE.create_by_
  is '创建人ID';
comment on column SYS_TYPE.create_time_
  is '创建时间';
comment on column SYS_TYPE.create_org_id_
  is '创建者所属组织ID';
comment on column SYS_TYPE.update_by_
  is '更新人ID';
comment on column SYS_TYPE.update_time_
  is '更新时间';
alter table SYS_TYPE
  add constraint PK_SYS_TYPE primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_TYPE_GROUP
prompt =============================
prompt
create table SYS_TYPE_GROUP
(
  id_            VARCHAR2(64) not null,
  group_key_     VARCHAR2(64) not null,
  name_          VARCHAR2(128) not null,
  flag_          NUMBER,
  sn_            NUMBER,
  type_          NUMBER,
  create_by_     VARCHAR2(64),
  create_time_   TIMESTAMP(6),
  create_org_id_ VARCHAR2(64),
  update_by_     VARCHAR2(64),
  update_time_   TIMESTAMP(6)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_TYPE_GROUP
  is '系统分类组值表';
comment on column SYS_TYPE_GROUP.id_
  is '主键ID';
comment on column SYS_TYPE_GROUP.group_key_
  is '分类组业务主键';
comment on column SYS_TYPE_GROUP.name_
  is '分类名';
comment on column SYS_TYPE_GROUP.flag_
  is '标识';
comment on column SYS_TYPE_GROUP.sn_
  is '序号';
comment on column SYS_TYPE_GROUP.type_
  is '类别。0=平铺结构；1=树型结构';
comment on column SYS_TYPE_GROUP.create_by_
  is '创建人ID';
comment on column SYS_TYPE_GROUP.create_time_
  is '创建时间';
comment on column SYS_TYPE_GROUP.create_org_id_
  is '创建者所属组织ID';
comment on column SYS_TYPE_GROUP.update_by_
  is '更新人ID';
comment on column SYS_TYPE_GROUP.update_time_
  is '更新时间';
alter table SYS_TYPE_GROUP
  add constraint PK_SYS_TYPE_GROUP primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_USER
prompt =======================
prompt
create table SYS_USER
(
  id_              VARCHAR2(64) not null,
  fullname_        VARCHAR2(255) not null,
  account_         VARCHAR2(255) not null,
  password_        VARCHAR2(64) not null,
  email_           VARCHAR2(64),
  mobile_          VARCHAR2(32),
  weixin_          VARCHAR2(64),
  create_time_     TIMESTAMP(6),
  address_         VARCHAR2(512),
  photo_           VARCHAR2(255),
  sex_             VARCHAR2(10),
  from_            VARCHAR2(64),
  status_          NUMBER default 1 not null,
  has_sync_to_wx_  NUMBER,
  open_id_         VARCHAR2(64),
  sign_name_fileid VARCHAR2(64),
  factory_code     VARCHAR2(10),
  user_type        NUMBER(1),
  supplier_no      VARCHAR2(10),
  department       VARCHAR2(52)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table SYS_USER
  is '用户表';
comment on column SYS_USER.fullname_
  is '姓名';
comment on column SYS_USER.account_
  is '账号';
comment on column SYS_USER.password_
  is '密码';
comment on column SYS_USER.email_
  is '邮箱';
comment on column SYS_USER.mobile_
  is '手机号码';
comment on column SYS_USER.weixin_
  is '微信号';
comment on column SYS_USER.create_time_
  is '创建时间';
comment on column SYS_USER.address_
  is '地址';
comment on column SYS_USER.photo_
  is '头像';
comment on column SYS_USER.sex_
  is '性别：男，女，未知';
comment on column SYS_USER.from_
  is '来源';
comment on column SYS_USER.status_
  is '0:禁用，1正常';
comment on column SYS_USER.open_id_
  is '微信的open_id_';
comment on column SYS_USER.sign_name_fileid
  is '电子签名文件ID';
comment on column SYS_USER.factory_code
  is '工厂';
comment on column SYS_USER.user_type
  is '用户类型（1-广新用户  2-供应商用户,3-PDA用户）用户测试临时添加';
comment on column SYS_USER.supplier_no
  is '用户测试临时添加';
comment on column SYS_USER.department
  is '部门';
alter table SYS_USER
  add constraint PK_SYS_USER primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_USER_PARAMS
prompt ==============================
prompt
create table SYS_USER_PARAMS
(
  id_      VARCHAR2(50),
  user_id_ VARCHAR2(50),
  alias_   VARCHAR2(50),
  value_   VARCHAR2(50)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_USER_PARAMS
  is '用户组织参数';

prompt
prompt Creating table SYS_USER_REL
prompt ===========================
prompt
create table SYS_USER_REL
(
  id_        VARCHAR2(64) not null,
  user_id_   VARCHAR2(64),
  level_     VARCHAR2(64),
  parent_id_ VARCHAR2(64),
  type_id_   VARCHAR2(64)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_USER_REL
  is '用户关系表';
comment on column SYS_USER_REL.id_
  is '主键';
comment on column SYS_USER_REL.level_
  is '级别(用于区分有多个上级的情况)';
comment on column SYS_USER_REL.parent_id_
  is '父id';
comment on column SYS_USER_REL.type_id_
  is '用户关系分类下的id';
alter table SYS_USER_REL
  add constraint PK_SYS_USER_REL primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table SYS_USER_ROLE
prompt ============================
prompt
create table SYS_USER_ROLE
(
  id_      VARCHAR2(64) not null,
  role_id_ VARCHAR2(64) not null,
  user_id_ VARCHAR2(64) not null
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table SYS_USER_ROLE
  is '用户角色管理';
alter table SYS_USER_ROLE
  add constraint PK_SYS_USER_ROLE primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_USER_UNDER
prompt =============================
prompt
create table SYS_USER_UNDER
(
  id_              VARCHAR2(64) not null,
  user_id_         VARCHAR2(64),
  under_user_id_   VARCHAR2(64),
  under_user_name_ VARCHAR2(100),
  org_id_          VARCHAR2(64)
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table SYS_USER_UNDER
  is '下属管理';
comment on column SYS_USER_UNDER.user_id_
  is '用户id';
comment on column SYS_USER_UNDER.under_user_id_
  is '下属用户id';
comment on column SYS_USER_UNDER.under_user_name_
  is '下属用户名';
comment on column SYS_USER_UNDER.org_id_
  is '组织id';
alter table SYS_USER_UNDER
  add constraint PK_SYS_USER_UNDER primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table XB_DB_ID
prompt =======================
prompt
create table XB_DB_ID
(
  id_       VARCHAR2(64) not null,
  start_    NUMBER not null,
  max_      NUMBER not null,
  mac_name_ VARCHAR2(255) not null
)
tablespace ILMSPORTAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XB_DB_ID
  is '主键生成表';
comment on column XB_DB_ID.id_
  is '前缀';
comment on column XB_DB_ID.start_
  is '开始ID值';
comment on column XB_DB_ID.max_
  is '当前允许最大的ID值';
comment on column XB_DB_ID.mac_name_
  is '机器名称';
alter table XB_DB_ID
  add constraint PK_XB_DB_ID primary key (ID_)
  using index 
  tablespace ILMSPORTAL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating sequence SEQ_IF_MM_PUB_SUPPLIER
prompt ========================================
prompt
create sequence SEQ_IF_MM_PUB_SUPPLIER
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_IF_MM_PUB_SUPPLIER_DETAIL
prompt ===============================================
prompt
create sequence SEQ_IF_MM_PUB_SUPPLIER_DETAIL
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_IF_PKG_BOX_QTY
prompt ====================================
prompt
create sequence SEQ_IF_PKG_BOX_QTY
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_IF_PKG_PROPOSAL
prompt =====================================
prompt
create sequence SEQ_IF_PKG_PROPOSAL
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_IF_PKG_PROPOSAL_DETAIL
prompt ============================================
prompt
create sequence SEQ_IF_PKG_PROPOSAL_DETAIL
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_IF_PKG_TRAY_QTY
prompt =====================================
prompt
create sequence SEQ_IF_PKG_TRAY_QTY
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_INV_PART_LOCATION
prompt ==========================================
prompt
create sequence SEQ_MM_INV_PART_LOCATION
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 100;

prompt
prompt Creating sequence SEQ_MM_INV_PART_LOCATION_TEMP
prompt ===============================================
prompt
create sequence SEQ_MM_INV_PART_LOCATION_TEMP
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_INV_SHELVES_TEMP
prompt =========================================
prompt
create sequence SEQ_MM_INV_SHELVES_TEMP
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_JISO_PART_PLAN
prompt =======================================
prompt
create sequence SEQ_MM_JISO_PART_PLAN
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_MON_BASE_DATE
prompt ======================================
prompt
create sequence SEQ_MM_MON_BASE_DATE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 100;

prompt
prompt Creating sequence SEQ_MM_MON_KB
prompt ===============================
prompt
create sequence SEQ_MM_MON_KB
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_PKG_BOX
prompt ================================
prompt
create sequence SEQ_MM_PKG_BOX
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_PKG_BOX_QTY
prompt ====================================
prompt
create sequence SEQ_MM_PKG_BOX_QTY
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_PKG_PART
prompt =================================
prompt
create sequence SEQ_MM_PKG_PART
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_PKG_TRAY_QTY
prompt =====================================
prompt
create sequence SEQ_MM_PKG_TRAY_QTY
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_PUB_PART_UDA
prompt =====================================
prompt
create sequence SEQ_MM_PUB_PART_UDA
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_SW_DEMAND_FORE
prompt =======================================
prompt
create sequence SEQ_MM_SW_DEMAND_FORE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 100;

prompt
prompt Creating sequence SEQ_MM_SW_FEEDBACK_ZC
prompt =======================================
prompt
create sequence SEQ_MM_SW_FEEDBACK_ZC
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_SW_FEEDBACK_ZC_IMP
prompt ===========================================
prompt
create sequence SEQ_MM_SW_FEEDBACK_ZC_IMP
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_SW_LONG_ORDER
prompt ======================================
prompt
create sequence SEQ_MM_SW_LONG_ORDER
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_SW_NOTICE
prompt ==================================
prompt
create sequence SEQ_MM_SW_NOTICE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MM_SW_ORDER_DETAIL
prompt ========================================
prompt
create sequence SEQ_MM_SW_ORDER_DETAIL
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 100;

prompt
prompt Creating sequence SEQ_MM_SW_PICKUP_PLAN
prompt =======================================
prompt
create sequence SEQ_MM_SW_PICKUP_PLAN
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 100;

prompt
prompt Creating sequence SEQ_MM_SW_SUP_GROUP
prompt =====================================
prompt
create sequence SEQ_MM_SW_SUP_GROUP
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 100;

prompt
prompt Creating sequence SEQ_NON_STANDAR_CHECK
prompt =======================================
prompt
create sequence SEQ_NON_STANDAR_CHECK
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_PUB_PRO_ERROR
prompt ===================================
prompt
create sequence SEQ_PUB_PRO_ERROR
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_SW_DEMAND_FORECAST_IMP
prompt ============================================
prompt
create sequence SEQ_SW_DEMAND_FORECAST_IMP
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_SW_NON_STANDARD
prompt =====================================
prompt
create sequence SEQ_SW_NON_STANDARD
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating view VIEW_SYS_USER_DATA_PERM
prompt =====================================
prompt
CREATE OR REPLACE FORCE VIEW VIEW_SYS_USER_DATA_PERM AS
SELECT DISTINCT UR.USER_ID, U.ACCOUNT_ USER_NAME, B.TYPE_CODE, B.VALUE_CODE
FROM SYS_DP_USER_ROLE UR, SYS_DP_ROLE R, SYS_DP_ROLE_DATA RD, SYS_DP_BASE_DATA B, SYS_USER U
WHERE R.DATA_ROLE_ID = UR.DATA_ROLE_ID
  AND RD.DATA_ROLE_ID = R.DATA_ROLE_ID
  AND B.DP_BASE_ID = RD.DP_BASE_ID
  AND U.ID_ = UR.USER_ID
with read only;
comment on column VIEW_SYS_USER_DATA_PERM.USER_ID is '用户ID';
comment on column VIEW_SYS_USER_DATA_PERM.USER_NAME is '用户名账号';
comment on column VIEW_SYS_USER_DATA_PERM.TYPE_CODE is '权限分类编码';
comment on column VIEW_SYS_USER_DATA_PERM.VALUE_CODE is '权限值';

prompt
prompt Creating package PKG_IF_SEND_IN
prompt ===============================
prompt
create or replace package PKG_IF_SEND_IN is

  -- Author  : LBX
  -- Created : 2018/12/29 14:05:47
  -- Purpose : portal's data to factory

  --****************************************************************************
  --过程名  : USP_JIT_ORDER
  --功能描述: 将信息共享平台的拉动订单打印信息写入厂内平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2018-12-29
  --****************************************************************************
  PROCEDURE USP_JIT_ORDER;

  --****************************************************************************
  --过程名  : USP_JIT_LABEL
  --功能描述: 将信息共享平台的拉动订单打印信息写入厂内平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2018-12-27
  --****************************************************************************
  PROCEDURE USP_JIT_LABEL;

  --****************************************************************************
  --过程名  : USP_JISO_ORDER
  --功能描述: 将信息共享平台的拉动订单打印信息写入厂内平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2018-12-27
  --****************************************************************************
  PROCEDURE USP_JISO_ORDER;

  --****************************************************************************
  --过程名  : USP_JISO_INS
  --功能描述: 将信息共享平台的拉动订单打印信息写入厂内平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2018-12-27
  --****************************************************************************
  PROCEDURE USP_JISO_INS;

    --****************************************************************************
  --过程名  : USP_PUB_SUPPLIER_SEND
  --功能描述: 当信息共享平台录入供应商数据发生变化时，将信息传回至厂内平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_PUB_SUPPLIER_SEND;

   --****************************************************************************
  --过程名  : USP_PUB_SUPPLIER_SEND
  --功能描述: 当信息共享平台录入供应商数据发生变化时，将信息传回至厂内平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_PUB_SUPPLIER_DETAIL_SEND;

/*
  --****************************************************************************
  --过程名  : USP_PUB_SUPPLIER_SEND
  --功能描述: 当信息共享平台录入供应商数据发生变化时，将信息传回至厂内平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2018-12-27
  --****************************************************************************
  PROCEDURE USP_PUB_SUPPLIER_SEND;
*/

  --****************************************************************************
  --过程名  : USP_PKG_BOX_QTY
  --功能描述: 将包装箱子数量管理同步至信息共享平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_PKG_BOX_QTY;

  --****************************************************************************
  --过程名  : USP_PKG_PROPOSAL_DETAIL
  --功能描述: 将包装提案明细信息同步至信息共享平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_PKG_PROPOSAL_DETAIL;

  --****************************************************************************
  --过程名  : USP_PKG_PROPOSAL
  --功能描述: 将包装提案信息同步至信息共享平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_PKG_PROPOSAL;

  --****************************************************************************
  --过程名  : USP_PKG_TRAY_QTY
  --功能描述: 将包装托盘数量信息同步至信息共享平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_PKG_TRAY_QTY;
  
     --****************************************************************************
  --过程名  : USP_SW_ACCOUNT_BILL
  --功能描述: 将发票反馈同步至厂内，接口从厂内查询数据回传给供应商
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_SW_ACCOUNT_INVOICE;
  --****************************************************************************
  --过程名  : USP_SW_ZC_ORDER_FEEDBACK
  --功能描述: 将资材反馈同步到厂内系统
  --参数说明: 无
  --创建人员: dtp
  --创建时间: 2019-03-19
  --****************************************************************************
  PROCEDURE USP_SW_ZC_ORDER_FEEDBACK;

end PKG_IF_SEND_IN;
/

prompt
prompt Creating package PKG_PUB
prompt ========================
prompt
create or replace package PKG_PUB is

  -- Author  : wxl
  -- Created : 2018/9/10 星期一 下午 4:11:12
  -- Purpose : Definition of common methods

  --****************************************************************************
  --函数  : USF_GET_BATCH_CYCLE
  --功能描述: 获取批次循环基数
  --参数说明:
  -- in_plan_code  :信息点
  --返回值: 批次循环基数
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_BATCH_CYCLE(in_plan_code in varchar2) RETURN NUMBER;

  --****************************************************************************
  --函数  : USF_GET_PROCESS_CYCLE
  --功能描述: 获取进度循环基数
  --参数说明:
  -- in_plan_code  :信息点
  --返回值: 进度循环基数
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_PROCESS_CYCLE(in_plan_code in varchar2) RETURN NUMBER;


  --****************************************************************************
  --函数名  : USF_GET_BATCH_NO_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取批次
  --参数说明:
  -- in_kb_id  :看板ID
  -- in_product_seqno  :产品流水号
  --返回值  : 批次号
  --创建人员: wxl
  --创建时间: 2018-11-12
  --****************************************************************************
  FUNCTION USF_GET_BATCHNO_BY_PRODSEQNO(in_kb_id         in number,
                                        in_product_seqno in number)
    RETURN NUMBER;

  --****************************************************************************
  --函数名  : USF_GET_PROCESSNO_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取批次
  --参数说明:
  -- in_kb_id  :看板ID
  -- in_product_seqno  :产品流水号
  --返回值  : 进度号
  --创建人员: wxl
  --创建时间: 2018-11-12
  --****************************************************************************
  FUNCTION USF_GET_PROCESSNO_BY_PRODSEQNO(in_kb_id         in number,
                                          in_product_seqno in number)
    RETURN NUMBER;

  --****************************************************************************
  --函数名  : USF_GET_BATCHNO_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取批次
  --参数说明:
  -- in_plan_code  :信息点
  -- in_product_seqno  :产品流水号
  --返回值  : 批次号
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_BATCHNO_BY_PRODSEQNO(in_plan_code     in varchar2,
                                        in_product_seqno in number)
    RETURN NUMBER;

  --****************************************************************************
  --函数名  : USF_GET_PROCESSNO_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取批次
  --参数说明:
  -- in_plan_code  :信息点
  -- in_product_seqno  :产品流水号
  --返回值  : 进度号
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_PROCESSNO_BY_PRODSEQNO(in_plan_code     in varchar2,
                                          in_product_seqno in number)
    RETURN NUMBER;

  --****************************************************************************
  --函数名  : USF_GET_BATCHSEQ_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取批次流水号
  --参数说明:
  -- in_kb_id  :看板ID
  -- in_product_seqno  :产品流水号
  --返回值  : 批次流水号
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_BATCHSEQ_BY_PRODSEQNO(in_kb_id         in number,
                                         in_product_seqno in number)
    RETURN NUMBER;

  --****************************************************************************
  --函数名  : USF_GET_BATCHSEQ_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取批次流水号
  --参数说明:
  -- in_plan_code  :信息点
  -- in_product_seqno  :产品流水号
  --返回值  : 批次流水号
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_BATCHSEQ_BY_PRODSEQNO(in_plan_code     in varchar2,
                                         in_product_seqno in number)
    RETURN NUMBER;

  --****************************************************************************
  --函数名  : USF_GET_PRODSEQNO_BY_BATCH
  --功能描述: 根据批次获取产品流水号
  --参数说明:
  -- in_plan_code  :信息点
  -- in_batch_no  :批次号
  -- in_process_no  :进度号
  --返回值  : 产品流水号
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_PRODSEQNO_BY_BATCH(in_plan_code  in varchar2,
                                      in_batch_no   in number,
                                      in_process_no in number) RETURN NUMBER;
  --****************************************************************************
  --函数名  : USF_GET_BATCHPROC_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取 批次-进度
  --参数说明:
  -- in_plan_code  :信息点
  -- in_product_seqno  :产品流水号
  --返回值  : 批次-进度
  --创建人员: dtp
  --创建时间: 2018-10-20
  --****************************************************************************
  FUNCTION USF_GET_BATCHPROC_BY_PRODSEQNO(in_plan_code     in varchar2,
                                          in_product_seqno in number)
    RETURN VARCHAR2;

  --****************************************************************************
  
  --****************************************************************************
  --函数名  : USF_GET_SEQUENCE
  --功能描述: 获取数据库序列值
  --参数说明:
  -- in_seq_name    :序列名字
  --返回值  : 下一个序列值
  --创建人员: chenjq
  --创建时间: 2016-11-15
  --****************************************************************************
  FUNCTION USF_GET_SEQUENCE(in_seq_name IN VARCHAR2) RETURN NUMBER;

end PKG_PUB;
/

prompt
prompt Creating package PKG_PUB_CHECK
prompt ==============================
prompt
create or replace package PKG_PUB_CHECK is

  --*****************************************************************************
  --存储过程名：USP_SW_ZC_ORDER_CHECK
  --功能描述：资材导入校验
  --参数说明：
  --IN_VAR_UUID
  --IN_VAR_USERNAME 操作人
  --IN_VAR_OPEIP ip地址
  --OUT_ERROR_FLAG 返回错误标识
  --OUT_OUT_ERROR_MSG 返回错误信息
  --创建人员: dtp
  --创建时间： 2019/03/12
  PROCEDURE USP_SW_ZC_ORDER_CHECK(IN_VAR_UUID       IN VARCHAR2,
                                           IN_VAR_USERNAME   IN VARCHAR2,
                                           IN_VAR_OPEIP      IN VARCHAR2,
                                           OUT_ERROR_FLAG    OUT VARCHAR2,
                                           OUT_OUT_ERROR_MSG OUT VARCHAR2);
  --*******************************************************************************
  
   --***************************************************************************
  --存储过程名：USP_SW_SUPPLIER_GROUP_CHECK
  --功能描述：供应商信息导入校验
  --参数说明：
  --IN_VAR_UUID
  --IN_VAR_USERNAME 操作人
  --IN_VAR_OPEIP ip地址
  --OUT_ERROR_FLAG 返回错误标识
  --OUT_OUT_ERROR_MSG 返回错误信息
  --创建人员: luoxianqin
  --创建时间： 2018/09/10
  PROCEDURE USP_SW_SUPPLIER_GROUP_CHECK(IN_VAR_UUID       IN VARCHAR2,
                                   IN_VAR_USERNAME   IN VARCHAR2,
                                   IN_VAR_OPEIP      IN VARCHAR2,
                                   OUT_ERROR_FLAG    OUT VARCHAR2,
                                   OUT_OUT_ERROR_MSG OUT VARCHAR2);

end PKG_PUB_CHECK;
/

prompt
prompt Creating package PKG_SW_CHECK
prompt =============================
prompt
create or replace package PKG_SW_CHECK is

  --存储过程名：USP_SW_SUPPLIER_GROUP_CHECK
  --功能描述：例外订单导入校验
  --参数说明：
  --IN_VAR_UUID
  --IN_VAR_USERNAME 操作人
  --IN_VAR_OPEIP ip地址
  --OUT_ERROR_FLAG 返回错误标识
  --OUT_OUT_ERROR_MSG 返回错误信息
  --创建人员: luoxianqin
  --创建时间： 2018/13/10
  PROCEDURE USP_SW_SUPPLIER_GROUP_CHECK(IN_VAR_UUID       IN VARCHAR2,
                                   IN_VAR_USERNAME   IN VARCHAR2,
                                   IN_VAR_OPEIP      IN VARCHAR2,
                                   OUT_ERROR_FLAG    OUT VARCHAR2,
                                   OUT_OUT_ERROR_MSG OUT VARCHAR2);

end PKG_SW_CHECK;
/

prompt
prompt Creating type MYARRAY
prompt =====================
prompt
CREATE OR REPLACE TYPE "MYARRAY"                                          as table of VARCHAR(32767)
/

prompt
prompt Creating type MY_WM_CONCAT
prompt ==========================
prompt
CREATE OR REPLACE TYPE "MY_WM_CONCAT"                                          authid current_user as object (
    CURRSTR VARCHAR2(32767),
    DELIMITER VARCHAR2(64),
    ORDERBY VARCHAR2(64),
    STRARRAY MYARRAY,
    static Function ODCIAGGREGATEINITIALIZE (INIT IN OUT MY_wm_concat)
       return NUMBER,
    member Function ODCIAGGREGATEITERATE (SELF IN OUT MY_wm_concat,COLVALUE IN VARCHAR2)
       return NUMBER,
    member Function ODCIAGGREGATEMERGE (SELF IN OUT MY_wm_concat,NEXT MY_wm_concat)
       return NUMBER,
    member Function ODCIAGGREGATETERMINATE (SELF IN MY_wm_concat,RETURNVALUE OUT VARCHAR2,FLAGS IN NUMBER)
       return NUMBER
)
/

prompt
prompt Creating function MY_CONCAT
prompt ===========================
prompt
create or replace function MY_CONCAT(colValue  VARCHAR2)
RETURN VARCHAR2
  AGGREGATE USING MY_wm_concat;
/

prompt
prompt Creating function USF_DELIMITER
prompt ===============================
prompt
create or replace function USF_DELIMITER(colValue  in varchar2,
                                     delimiter in varchar2)
return varchar2 is
  rtnValue varchar2(32767);

begin

  rtnValue := colValue || ' delimiter=>' || delimiter || '; ';

  return rtnValue;

end USF_delimiter;
/

prompt
prompt Creating function USF_ORDERBY
prompt =============================
prompt
create or replace function USF_ORDERBY(colValue in varchar2,
                                   orderby  in varchar2)
return varchar2 is
  rtnValue varchar2(32767);

begin

  rtnValue := colValue || ' orderby=>' || LOWER(orderby) || '; ';

  return rtnValue;

end USF_orderby;
/

prompt
prompt Creating package body PKG_IF_SEND_IN
prompt ====================================
prompt
create or replace package body PKG_IF_SEND_IN is

  --****************************************************************************
  --过程名  : USP_JIT_ORDER
  --功能描述: 将厂内平台的拉动订单写入信息共享平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2018-12-27
  --****************************************************************************
  PROCEDURE USP_JIT_ORDER IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.ORDER_NO, T.PRINT_TIME, T.PRINT_USER, T.PRINT_USER_IP
                           FROM MM_JIT_ORDER T
                          WHERE T.DEAL_FLAG_IN = 0
                            AND T.PRINT_STATUS = 1
                          ORDER BY T.CREATION_TIME) LOOP
      --写入信息共享平台拉动订单主信息
      UPDATE ilmspec.MM_JIT_ORDER@MM_IF_IN_A a
         set a.PRINT_STATUS = 1,
             A.PRINT_TIME   = CUR_ORDER_NO.PRINT_TIME,
             A.PRINT_USER   = CUR_ORDER_NO.PRINT_USER,
             A.PRINT_USER_IP = CUR_ORDER_NO.PRINT_USER_IP
       WHERE A.ORDER_NO = CUR_ORDER_NO.ORDER_NO;

      --更新接口处理标识
      UPDATE MM_JIT_ORDER T
         SET T.DEAL_FLAG_IN = 1, T.DEAL_TIME_IN = SYSDATE
       WHERE ORDER_NO = CUR_ORDER_NO.ORDER_NO;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_JIT_ORDER',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_JIT_ORDER;

  --****************************************************************************
  --过程名  : USP_JIT_LABEL
  --功能描述: 将信息共享平台的拉动订单打印信息写入厂内平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2018-12-27
  --****************************************************************************
  PROCEDURE USP_JIT_LABEL IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.ORDER_NO, T.LABEL_ROWNO, T.PRINT_TIME, T.PRINT_USER, T.PRINT_USER_IP, T.BAR_UUID
                           FROM MM_JIT_LABEL T
                          WHERE T.DEAL_FLAG_IN = 0
                            AND T.PRINT_STATUS = 1
                          ORDER BY T.CREATION_TIME) LOOP
      --更新厂内厂内平台拉动零件标签信息的打印状态
      UPDATE ilmspec.MM_JIT_LABEL@MM_IF_IN_A A
         SET A.PRINT_STATUS = 1,
             A.PRINT_TIME   = CUR_ORDER_NO.PRINT_TIME,
             A.PRINT_USER   = CUR_ORDER_NO.PRINT_USER,
             A.PRINT_USER_IP = CUR_ORDER_NO.PRINT_USER_IP,
             A.BAR_UUID = CUR_ORDER_NO.BAR_UUID
       WHERE A.ORDER_NO = CUR_ORDER_NO.ORDER_NO
         AND A.LABEL_ROWNO = CUR_ORDER_NO.LABEL_ROWNO;

      --更新接口处理标识
      UPDATE MM_JIT_LABEL T
         SET T.DEAL_FLAG_IN = 1, T.DEAL_TIME_IN = SYSDATE
       WHERE ORDER_NO = CUR_ORDER_NO.ORDER_NO
         AND LABEL_ROWNO = CUR_ORDER_NO.LABEL_ROWNO;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_JIT_LABEL',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_JIT_LABEL;

  --****************************************************************************
  --过程名  : USP_JISO_ORDER
  --功能描述: 将信息共享平台的拉动订单打印信息写入厂内平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2018-12-27
  --****************************************************************************
  PROCEDURE USP_JISO_ORDER IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.ORDER_NO, T.PRINT_TIME, T.PRINT_USER, T.PRINT_USER_IP
                           FROM MM_JISO_ORDER T
                          WHERE T.DEAL_FLAG_IN = 0
                            AND T.PRINT_STATUS = 1
                          ORDER BY T.CREATION_TIME) LOOP
      --更新厂内平台厂外同步订单主信息打印状态
      UPDATE ilmspec.MM_JISO_ORDER@MM_IF_IN_A A
         SET A.PRINT_STATUS = 1,
             A.PRINT_TIME   = CUR_ORDER_NO.PRINT_TIME,
             A.PRINT_USER   = CUR_ORDER_NO.PRINT_USER,
             A.PRINT_USER_IP = CUR_ORDER_NO.PRINT_USER_IP
       WHERE A.ORDER_NO = CUR_ORDER_NO.ORDER_NO;

      --更新接口处理标识
      UPDATE MM_JISO_ORDER T
         SET T.DEAL_FLAG_IN = 1, T.DEAL_TIME_IN = SYSDATE
       WHERE ORDER_NO = CUR_ORDER_NO.ORDER_NO;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_JISO_ORDER',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_JISO_ORDER;

  --****************************************************************************
  --过程名  : USP_JISO_INS
  --功能描述: 将信息共享平台的拉动订单打印信息写入厂内平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2018-12-27
  --****************************************************************************
  PROCEDURE USP_JISO_INS IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.INS_NO, T.PRINT_TIME, T.PRINT_USER, T.PRINT_USER_IP
                           FROM MM_JISO_INS T
                          WHERE T.DEAL_FLAG_IN = 0
                            AND T.PRINT_STATUS = 1
                          ORDER BY T.CREATION_TIME) LOOP
      --更新厂内平台厂外同步指示票主信息打印状态
      UPDATE ilmspec.MM_JISO_INS@MM_IF_IN_A A
         SET A.PRINT_STATUS = 1,
             A.PRINT_TIME   = CUR_ORDER_NO.PRINT_TIME,
             A.PRINT_USER   = CUR_ORDER_NO.PRINT_USER,
             A.PRINT_USER_IP = CUR_ORDER_NO.PRINT_USER_IP
       WHERE A.INS_NO = CUR_ORDER_NO.INS_NO;

      --更新DEAL_FLAG标记位为1
      UPDATE MM_JISO_INS T
         SET T.DEAL_FLAG_IN = 1, T.DEAL_TIME_IN = SYSDATE
       WHERE INS_NO = CUR_ORDER_NO.INS_NO;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_JISO_INS',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_JISO_INS;

  --****************************************************************************
  --过程名  : USP_PUB_SUPPLIER_SEND
  --功能描述: 当信息共享平台录入供应商数据发生变化时，将信息传回至厂内平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2018-12-27
  --****************************************************************************
  PROCEDURE USP_PUB_SUPPLIER_SEND IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.*
                           FROM IF_MM_PUB_SUPPLIER T
                          WHERE T.DEAL_FLAG = 0
                          ORDER BY T.CREATION_TIME) LOOP
      --更新厂内平台供应商数据
      IF CUR_ORDER_NO.DO_FLAG = 'U' THEN
      UPDATE ilmspec.MM_PUB_SUPPLIER@MM_IF_IN_A a
         SET
             a.DETAIL_ADDR        = CUR_ORDER_NO.DETAIL_ADDR,
             a.SUP_STATUS         = CUR_ORDER_NO.SUP_STATUS,
             a.CREATION_TIME      = CUR_ORDER_NO.CREATION_TIME,
             a.LAST_MODIFIED_TIME = CUR_ORDER_NO.LAST_MODIFIED_TIME
       where
              a.SUP_FACTORY = CUR_ORDER_NO.SUP_FACTORY
              AND
              a.SUPPLIER_NO = CUR_ORDER_NO.SUPPLIER_NO;
      END IF;
      --更新DEAL_FLAG标记位为1
      UPDATE IF_MM_PUB_SUPPLIER T
         SET T.DEAL_FLAG = 1, T.DEAL_TIME = SYSDATE
       WHERE T.SUP_FACTORY = CUR_ORDER_NO.SUP_FACTORY
         AND T.ID = CUR_ORDER_NO.ID;

    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_PUB_SUPPLIER_SEND',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_PUB_SUPPLIER_SEND;

  --****************************************************************************
  --过程名  : USP_PUB_SUPPLIER_DETIAL_SEND
  --功能描述: 当信息共享平台录入供应商数据发生变化时，将信息传回至厂内平台
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2018-12-27
  --****************************************************************************
  PROCEDURE USP_PUB_SUPPLIER_DETAIL_SEND IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.*
                           FROM IF_MM_PUB_SUPPLIER_DETAIL T
                          WHERE T.DEAL_FLAG = 0
                          ORDER BY T.CREATION_TIME) LOOP
      --数据写入到明细表中
      IF CUR_ORDER_NO.DO_FLAG = 'I' THEN
      INSERT INTO ilmspec.MM_PUB_SUPPLIER_DETAIL@MM_IF_IN_A(
            SUPPLIER_NO,
            SUP_FACTORY,
            SUP_FACTORY_ADDR,

            IMPORT_DEP,
            IMPORT_POSITION,
            IMPORT_NAME,
            IMPORT_MOBILE,
            IMPORT_TEL ,
            IMPORT_MAIL,

            IMPORT_DEP_A,
            IMPORT_POSITION_A,
            IMPORT_NAME_A,
            IMPORT_MOBILE_A,
            IMPORT_TEL_A,
            IMPORT_MAIL_A,

            PT_DEP,
            PT_POSITION,
            PT_NAME,
           PT_MOBILE,
            PT_TEL,
            PT_MAIL,

            PT_DEP_A,
            PT_POSITION_A,
            PT_NAME_A,
            PT_MOBILE_A,
            PT_TEL_A,
            PT_MAIL_A,

            MASS_DEP,
            MASS_POSITION,
            MASS_NAME,
           MASS_MOBILE,
            MASS_TEL,
             MASS_MAIL,

            MASS_DEP_A,
            MASS_POSITION_A ,
            MASS_NAME_A ,
            MASS_MOBILE_A,
            MASS_TEL_A ,
            MASS_MAIL_A,

            EXCEP_DEP_A,
            EXCEP_POSITION_A,
            EXCEP_NAME_A,
             EXCEP_NAME_B,
            EXCEP_MOBILE_A,
            EXCEP_MOBILE_B,
            EXCEP_TEL_A,
           EXCEP_TEL_B,
            EXCEP_MAIL_A,
            EXCEP_MAIL_B,

            DEVICE_DEP,
            DEVICE_POSITION,
            DEVICE_NAME,
            DEVICE_MOBILE,
            DEVICE_TEL,
            DEVICE_MAIL,

            DEVICE_DEP_A,
            DEVICE_POSITION_A,
           DEVICE_NAME_A,
             DEVICE_MOBILE_A,
             DEVICE_TEL_A ,
            DEVICE_MAIL_A ,

             PACK_DEP_A,
             PACK_POSITION_A,
             PACK_NAME_A,
            PACK_MOBILE_A,
             PACK_TEL_A,
             PACK_MAIL_A,

             PACK_DEP_B ,
            PACK_POSITION_B,
            PACK_NAME_B,
            PACK_MOBILE_B ,
             PACK_TEL_B ,
             PACK_MAIL_B,

            PT_LOGISTICS_DEP,
             PT_LOGISTICS_NAME,
             PT_LOGISTICS_POSITION ,
             PT_LOGISTICS_MOBILE,
             PT_LOGISTICS_TEL,
             PT_LOGISTICS_MAIL,

             PT_LOGISTICS_DEP_A ,
             PT_LOGISTICS_NAME_A,
            PT_LOGISTICS_POSITION_A,
             PT_LOGISTICS_MOBILE_A ,
             PT_LOGISTICS_TEL_A,
             PT_LOGISTICS_MAIL_A,

             MASS_LOGISTICS_DEP,
            MASS_LOGISTICS_POSITION ,
             MASS_LOGISTICS_NAME ,
             MASS_LOGISTICS_MOBILE,
             MASS_LOGISTICS_TEL,
             MASS_LOGISTICS_MAIL ,

             MASS_LOGISTICS_DEP_A,
             MASS_LOGISTICS_POSITION_A ,
             MASS_LOGISTICS_NAME_A,
             MASS_LOGISTICS_MOBILE_A ,
             MASS_LOGISTICS_TEL_A ,
             MASS_LOGISTICS_MAIL_A     )
      VALUES(
             CUR_ORDER_NO.SUPPLIER_NO,
             CUR_ORDER_NO.SUP_FACTORY,
             CUR_ORDER_NO.SUP_FACTORY_ADDR,

             CUR_ORDER_NO.IMPORT_DEP,
             CUR_ORDER_NO.IMPORT_POSITION,
             CUR_ORDER_NO.IMPORT_NAME,
             CUR_ORDER_NO.IMPORT_MOBILE,
             CUR_ORDER_NO.IMPORT_TEL,
             CUR_ORDER_NO.IMPORT_MAIL,

             CUR_ORDER_NO.IMPORT_DEP_A,
             CUR_ORDER_NO.IMPORT_POSITION_A,
             CUR_ORDER_NO.IMPORT_NAME_A,
             CUR_ORDER_NO.IMPORT_MOBILE_A,
             CUR_ORDER_NO.IMPORT_TEL_A,
             CUR_ORDER_NO.IMPORT_MAIL_A,

             CUR_ORDER_NO.PT_DEP,
             CUR_ORDER_NO.PT_POSITION,
             CUR_ORDER_NO.PT_NAME,
             CUR_ORDER_NO.PT_MOBILE,
             CUR_ORDER_NO.PT_TEL,
             CUR_ORDER_NO.PT_MAIL,

             CUR_ORDER_NO.PT_DEP_A,
             CUR_ORDER_NO.PT_POSITION_A,
             CUR_ORDER_NO.PT_NAME_A,
             CUR_ORDER_NO.PT_MOBILE_A,
             CUR_ORDER_NO.PT_TEL_A,
             CUR_ORDER_NO.PT_MAIL_A,

             CUR_ORDER_NO.MASS_DEP,
             CUR_ORDER_NO.MASS_POSITION,
             CUR_ORDER_NO.MASS_NAME,
             CUR_ORDER_NO.MASS_MOBILE,
             CUR_ORDER_NO.MASS_TEL,
             CUR_ORDER_NO.MASS_MAIL,

            CUR_ORDER_NO.MASS_DEP_A,
             CUR_ORDER_NO.MASS_POSITION_A,
             CUR_ORDER_NO.MASS_NAME_A,
             CUR_ORDER_NO.MASS_MOBILE_A,
             CUR_ORDER_NO.MASS_TEL_A,
             CUR_ORDER_NO.MASS_MAIL_A,

             CUR_ORDER_NO.EXCEP_DEP_A,
             CUR_ORDER_NO.EXCEP_POSITION_A,
             CUR_ORDER_NO.EXCEP_NAME_A,
             CUR_ORDER_NO.EXCEP_NAME_B,
             CUR_ORDER_NO.EXCEP_MOBILE_A,
             CUR_ORDER_NO.EXCEP_MOBILE_B,
             CUR_ORDER_NO.EXCEP_TEL_A,
             CUR_ORDER_NO.EXCEP_TEL_B,
             CUR_ORDER_NO.EXCEP_MAIL_A,
             CUR_ORDER_NO.EXCEP_MAIL_B,

             CUR_ORDER_NO.DEVICE_DEP,
             CUR_ORDER_NO.DEVICE_POSITION,
             CUR_ORDER_NO.DEVICE_NAME,
             CUR_ORDER_NO.DEVICE_MOBILE,
             CUR_ORDER_NO.DEVICE_TEL,
             CUR_ORDER_NO.DEVICE_MAIL,

            CUR_ORDER_NO.DEVICE_DEP_A,
             CUR_ORDER_NO.DEVICE_POSITION_A,
             CUR_ORDER_NO.DEVICE_NAME_A,
             CUR_ORDER_NO.DEVICE_MOBILE_A,
             CUR_ORDER_NO.DEVICE_TEL_A,
             CUR_ORDER_NO.DEVICE_MAIL_A,

             CUR_ORDER_NO.PACK_DEP_A,
             CUR_ORDER_NO.PACK_POSITION_A,
             CUR_ORDER_NO.PACK_NAME_A,
             CUR_ORDER_NO.PACK_MOBILE_A,
             CUR_ORDER_NO.PACK_TEL_A,
             CUR_ORDER_NO.PACK_MAIL_A,

             CUR_ORDER_NO.PACK_DEP_B,
             CUR_ORDER_NO.PACK_POSITION_B,
             CUR_ORDER_NO.PACK_NAME_B,
             CUR_ORDER_NO.PACK_MOBILE_B,
             CUR_ORDER_NO.PACK_TEL_B,
             CUR_ORDER_NO.PACK_MAIL_B,

             CUR_ORDER_NO.PT_LOGISTICS_DEP,
             CUR_ORDER_NO.PT_LOGISTICS_NAME,
             CUR_ORDER_NO.PT_LOGISTICS_POSITION,
             CUR_ORDER_NO.PT_LOGISTICS_MOBILE,
             CUR_ORDER_NO.PT_LOGISTICS_TEL,
             CUR_ORDER_NO.PT_LOGISTICS_MAIL,

             CUR_ORDER_NO.PT_LOGISTICS_DEP_A,
             CUR_ORDER_NO.PT_LOGISTICS_NAME_A,
             CUR_ORDER_NO.PT_LOGISTICS_POSITION_A,
             CUR_ORDER_NO.PT_LOGISTICS_MOBILE_A,
             CUR_ORDER_NO.PT_LOGISTICS_TEL_A,
             CUR_ORDER_NO.PT_LOGISTICS_MAIL_A,

             CUR_ORDER_NO.MASS_LOGISTICS_DEP,
             CUR_ORDER_NO.MASS_LOGISTICS_POSITION,
             CUR_ORDER_NO.MASS_LOGISTICS_NAME,
             CUR_ORDER_NO.MASS_LOGISTICS_MOBILE,
             CUR_ORDER_NO.MASS_LOGISTICS_TEL,
             CUR_ORDER_NO.MASS_LOGISTICS_MAIL,

             CUR_ORDER_NO.MASS_LOGISTICS_DEP_A,
             CUR_ORDER_NO.MASS_LOGISTICS_POSITION_A,
             CUR_ORDER_NO.MASS_LOGISTICS_NAME_A,
             CUR_ORDER_NO.MASS_LOGISTICS_MOBILE_A,
             CUR_ORDER_NO.MASS_LOGISTICS_TEL_A,
             CUR_ORDER_NO.MASS_LOGISTICS_MAIL_A);
      END IF;
      --更新厂内平台供应商数据
      IF CUR_ORDER_NO.DO_FLAG = 'U' THEN
      UPDATE ilmspec.MM_PUB_SUPPLIER_DETAIL@MM_IF_IN_A a
         SET
             a.SUP_FACTORY_ADDR        = CUR_ORDER_NO.SUP_FACTORY_ADDR,

             a.IMPORT_DEP         = CUR_ORDER_NO.IMPORT_DEP,
             a.IMPORT_POSITION    = CUR_ORDER_NO.IMPORT_POSITION,
             a.IMPORT_NAME        = CUR_ORDER_NO.IMPORT_NAME,
             a.IMPORT_MOBILE      = CUR_ORDER_NO.IMPORT_MOBILE,
             a.IMPORT_TEL         = CUR_ORDER_NO.IMPORT_TEL,
             a.IMPORT_MAIL        = CUR_ORDER_NO.IMPORT_MAIL,

             a.IMPORT_DEP_A         = CUR_ORDER_NO.IMPORT_DEP_A,
             a.IMPORT_POSITION_A    = CUR_ORDER_NO.IMPORT_POSITION_A,
             a.IMPORT_NAME_A        = CUR_ORDER_NO.IMPORT_NAME_A,
             a.IMPORT_MOBILE_A      = CUR_ORDER_NO.IMPORT_MOBILE_A,
             a.IMPORT_TEL_A         = CUR_ORDER_NO.IMPORT_TEL_A,
             a.IMPORT_MAIL_A        = CUR_ORDER_NO.IMPORT_MAIL_A,

             a.PT_DEP             = CUR_ORDER_NO.PT_DEP,
             a.PT_POSITION        = CUR_ORDER_NO.PT_POSITION,
             a.PT_NAME            = CUR_ORDER_NO.PT_NAME,
             a.PT_MOBILE           = CUR_ORDER_NO.PT_MOBILE,
             a.PT_TEL             = CUR_ORDER_NO.PT_TEL,
             a.PT_MAIL            = CUR_ORDER_NO.PT_MAIL,

             a.PT_DEP_A             = CUR_ORDER_NO.PT_DEP_A,
             a.PT_POSITION_A        = CUR_ORDER_NO.PT_POSITION_A,
             a.PT_NAME_A            = CUR_ORDER_NO.PT_NAME_A,
             a.PT_MOBILE_A           = CUR_ORDER_NO.PT_MOBILE_A,
             a.PT_TEL_A             = CUR_ORDER_NO.PT_TEL_A,
             a.PT_MAIL_A            = CUR_ORDER_NO.PT_MAIL_A,

             a.MASS_DEP           = CUR_ORDER_NO.MASS_DEP,
             a.MASS_POSITION      = CUR_ORDER_NO.MASS_POSITION,
             a.MASS_NAME          = CUR_ORDER_NO.MASS_NAME,
             a.MASS_MOBILE         = CUR_ORDER_NO.MASS_MOBILE,
             a.MASS_TEL           = CUR_ORDER_NO.MASS_TEL,
             a.MASS_MAIL          = CUR_ORDER_NO.MASS_MAIL,

             a.MASS_DEP_A           = CUR_ORDER_NO.MASS_DEP_A,
             a.MASS_POSITION_A      = CUR_ORDER_NO.MASS_POSITION_A,
             a.MASS_NAME_A          = CUR_ORDER_NO.MASS_NAME_A,
             a.MASS_MOBILE_A         = CUR_ORDER_NO.MASS_MOBILE_A,
             a.MASS_TEL_A           = CUR_ORDER_NO.MASS_TEL_A,
             a.MASS_MAIL_A          = CUR_ORDER_NO.MASS_MAIL_A,

             a.EXCEP_DEP_A          = CUR_ORDER_NO.EXCEP_DEP_A,
             a.EXCEP_POSITION_A     = CUR_ORDER_NO.EXCEP_POSITION_A,
             a.EXCEP_NAME_A       = CUR_ORDER_NO.EXCEP_NAME_A,
             a.EXCEP_NAME_B       = CUR_ORDER_NO.EXCEP_NAME_B,
             a.EXCEP_MOBILE_A      = CUR_ORDER_NO.EXCEP_MOBILE_A,
             a.EXCEP_MOBILE_B      = CUR_ORDER_NO.EXCEP_MOBILE_B,
             a.EXCEP_TEL_A        = CUR_ORDER_NO.EXCEP_TEL_A,
             a.EXCEP_TEL_B        = CUR_ORDER_NO.EXCEP_TEL_B,
             a.EXCEP_MAIL_A       = CUR_ORDER_NO.EXCEP_MAIL_A,
             a.EXCEP_MAIL_B       = CUR_ORDER_NO.EXCEP_MAIL_B,

             a.DEVICE_DEP         = CUR_ORDER_NO.DEVICE_DEP,
             a.DEVICE_POSITION    = CUR_ORDER_NO.DEVICE_POSITION,
             a.DEVICE_NAME        = CUR_ORDER_NO.DEVICE_NAME,
             a.DEVICE_MOBILE       = CUR_ORDER_NO.DEVICE_MOBILE,
             a.DEVICE_TEL         = CUR_ORDER_NO.DEVICE_TEL,
             a.DEVICE_MAIL        = CUR_ORDER_NO.DEVICE_MAIL,

             a.DEVICE_DEP_A         = CUR_ORDER_NO.DEVICE_DEP_A,
             a.DEVICE_POSITION_A    = CUR_ORDER_NO.DEVICE_POSITION_A,
             a.DEVICE_NAME_A        = CUR_ORDER_NO.DEVICE_NAME_A,
             a.DEVICE_MOBILE_A       = CUR_ORDER_NO.DEVICE_MOBILE_A,
             a.DEVICE_TEL_A         = CUR_ORDER_NO.DEVICE_TEL_A,
             a.DEVICE_MAIL_A        = CUR_ORDER_NO.DEVICE_MAIL_A,

             a.PACK_DEP_A           = CUR_ORDER_NO.PACK_DEP_A,
             a.PACK_POSITION_A      = CUR_ORDER_NO.PACK_POSITION_A,
             a.PACK_NAME_A          = CUR_ORDER_NO.PACK_NAME_A,
             a.PACK_MOBILE_A        = CUR_ORDER_NO.PACK_MOBILE_A,
             a.PACK_TEL_A           = CUR_ORDER_NO.PACK_TEL_A,
             a.PACK_MAIL_A          = CUR_ORDER_NO.PACK_MAIL_A,

             a.PACK_DEP_B           = CUR_ORDER_NO.PACK_DEP_B,
             a.PACK_POSITION_B      = CUR_ORDER_NO.PACK_POSITION_B,
             a.PACK_NAME_B          = CUR_ORDER_NO.PACK_NAME_B,
             a.PACK_MOBILE_B        = CUR_ORDER_NO.PACK_MOBILE_B,
             a.PACK_TEL_B           = CUR_ORDER_NO.PACK_TEL_B,
             a.PACK_MAIL_B          = CUR_ORDER_NO.PACK_MAIL_B,

             a.PT_LOGISTICS_DEP     = CUR_ORDER_NO.PT_LOGISTICS_DEP,
             a.PT_LOGISTICS_NAME    = CUR_ORDER_NO.PT_LOGISTICS_NAME,
             a.PT_LOGISTICS_POSITION  = CUR_ORDER_NO.PT_LOGISTICS_POSITION,
             a.PT_LOGISTICS_MOBILE    = CUR_ORDER_NO.PT_LOGISTICS_MOBILE,
             a.PT_LOGISTICS_TEL       = CUR_ORDER_NO.PT_LOGISTICS_TEL,
             a.PT_LOGISTICS_MAIL      = CUR_ORDER_NO.PT_LOGISTICS_MAIL,

             a.PT_LOGISTICS_DEP_A     = CUR_ORDER_NO.PT_LOGISTICS_DEP_A,
             a.PT_LOGISTICS_NAME_A    = CUR_ORDER_NO.PT_LOGISTICS_NAME_A,
             a.PT_LOGISTICS_POSITION_A  = CUR_ORDER_NO.PT_LOGISTICS_POSITION_A,
             a.PT_LOGISTICS_MOBILE_A    = CUR_ORDER_NO.PT_LOGISTICS_MOBILE_A,
             a.PT_LOGISTICS_TEL_A       = CUR_ORDER_NO.PT_LOGISTICS_TEL_A,
             a.PT_LOGISTICS_MAIL_A      = CUR_ORDER_NO.PT_LOGISTICS_MAIL_A,

             a.MASS_LOGISTICS_DEP       = CUR_ORDER_NO.MASS_LOGISTICS_DEP,
             a.MASS_LOGISTICS_POSITION  = CUR_ORDER_NO.MASS_LOGISTICS_POSITION,
             a.MASS_LOGISTICS_NAME      = CUR_ORDER_NO.MASS_LOGISTICS_NAME,
             a.MASS_LOGISTICS_MOBILE    = CUR_ORDER_NO.MASS_LOGISTICS_MOBILE,
             a.MASS_LOGISTICS_TEL       = CUR_ORDER_NO.MASS_LOGISTICS_TEL,
             a.MASS_LOGISTICS_MAIL      = CUR_ORDER_NO.MASS_LOGISTICS_MAIL,

             a.MASS_LOGISTICS_DEP_A     = CUR_ORDER_NO.MASS_LOGISTICS_DEP_A,
             a.MASS_LOGISTICS_POSITION_A  = CUR_ORDER_NO.MASS_LOGISTICS_POSITION_A,
             a.MASS_LOGISTICS_NAME_A      = CUR_ORDER_NO.MASS_LOGISTICS_NAME_A,
             a.MASS_LOGISTICS_MOBILE_A    = CUR_ORDER_NO.MASS_LOGISTICS_MOBILE_A,
             a.MASS_LOGISTICS_TEL_A       = CUR_ORDER_NO.MASS_LOGISTICS_TEL_A,
             a.MASS_LOGISTICS_MAIL_A      = CUR_ORDER_NO.MASS_LOGISTICS_MAIL_A,


             a.CREATION_TIME      = CUR_ORDER_NO.CREATION_TIME,
             a.LAST_MODIFIED_TIME = CUR_ORDER_NO.LAST_MODIFIED_TIME
       where
              a.SUP_FACTORY = CUR_ORDER_NO.SUP_FACTORY
              AND
              a.SUPPLIER_NO = CUR_ORDER_NO.SUPPLIER_NO;
      END IF;
      --更新DEAL_FLAG标记位为1
      UPDATE IF_MM_PUB_SUPPLIER_DETAIL T
         SET T.DEAL_FLAG = 1, T.DEAL_TIME = SYSDATE
       WHERE T.SUP_FACTORY = CUR_ORDER_NO.SUP_FACTORY
         AND T.SUPPLIER_NO = CUR_ORDER_NO.SUPPLIER_NO;

    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_PUB_SUPPLIER_DETAIL_SEND',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_PUB_SUPPLIER_DETAIL_SEND;


  --****************************************************************************
  --过程名  : USP_PKG_BOX_QTY
  --功能描述: 将包装箱子数量管理同步至厂内
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_PKG_BOX_QTY IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.*
                           FROM IF_MM_PKG_BOX_QTY T
                          WHERE T.DEAL_FLAG = 0
                          ORDER BY T.CREATION_TIME) LOOP

      IF CUR_ORDER_NO.DO_FLAG = 'U' THEN
        UPDATE ilmspec.MM_PKG_BOX_QTY@MM_IF_IN_A A
           SET A.PROVIDE_QTY        = CUR_ORDER_NO.PROVIDE_QTY,
               A.PLAN_DATE          = CUR_ORDER_NO.PLAN_DATE,
               A.DELAY_REASON       = CUR_ORDER_NO.DELAY_REASON,
               A.LAST_MODIFIED_USER = CUR_ORDER_NO.LAST_MODIFIED_USER,
               A.LAST_MODIFIED_TIME = CUR_ORDER_NO.LAST_MODIFIED_TIME
         WHERE A.PROPOSAL_ID = CUR_ORDER_NO.PROPOSAL_ID
           AND A.FACTORY_CODE = CUR_ORDER_NO.FACTORY_CODE;
      END IF;
      --更新中间表处理标记位为已处理
      UPDATE IF_MM_PKG_BOX_QTY A
         SET A.DEAL_FLAG = 1, A.DEAL_TIME = SYSDATE
       WHERE A.ID = CUR_ORDER_NO.ID
         AND A.FACTORY_CODE = CUR_ORDER_NO.FACTORY_CODE;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_PKG_BOX_QTY',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_PKG_BOX_QTY;

   --****************************************************************************
  --过程名  : USP_PKG_PROPOSAL_DETAIL
  --功能描述: 将包装提案明细信息同步至厂内
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_PKG_PROPOSAL_DETAIL IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.*
                           FROM IF_MM_PKG_PROPOSAL_DETAIL T
                          WHERE T.DEAL_FLAG = 0
                          ORDER BY T.CREATION_TIME) LOOP
      IF CUR_ORDER_NO.DO_FLAG = 'U' THEN
        UPDATE ilmspec.MM_PKG_PROPOSAL_DETAIL@MM_IF_IN_A A
           SET A.GROUP_NO              = CUR_ORDER_NO.GROUP_NO,
               A.MAX_PACKAGE_NUM       = CUR_ORDER_NO.MAX_PACKAGE_NUM,
               A.STANDARD_PACKAGE      = CUR_ORDER_NO.STANDARD_PACKAGE,
               A.PART_WEIGHT           = CUR_ORDER_NO.PART_WEIGHT,
               A.PART_TOTAL_WEIGHT     = CUR_ORDER_NO.PART_TOTAL_WEIGHT,
               A.PACK_WEIGHT           = CUR_ORDER_NO.PACK_WEIGHT,
               A.PART_LENGTH           = CUR_ORDER_NO.PART_LENGTH,
               A.PART_WIDTH            = CUR_ORDER_NO.PART_WIDTH,
               A.PART_HEIGHT           = CUR_ORDER_NO.PART_HEIGHT,
               A.EMPTY_TROLLEY_LENGTH  = CUR_ORDER_NO.EMPTY_TROLLEY_LENGTH,
               A.EMPTY_TROLLEY_WIDTH   = CUR_ORDER_NO.EMPTY_TROLLEY_WIDTH,
               A.EMPTY_TROLLEY_HEIGHT  = CUR_ORDER_NO.EMPTY_TROLLEY_HEIGHT,
               A.REAL_TROLLEY_LENGTH   = CUR_ORDER_NO.REAL_TROLLEY_LENGTH,
               A.REAL_TROLLEY_WIDTH    = CUR_ORDER_NO.REAL_TROLLEY_WIDTH,
               A.REAL_TROLLEY_HEIGHT   = CUR_ORDER_NO.REAL_TROLLEY_HEIGHT,
               A.TROLLEY_WEIGHT        = CUR_ORDER_NO.TROLLEY_WEIGHT,
               A.TOTAL_WEIGHT          = CUR_ORDER_NO.TOTAL_WEIGHT,
               A.IS_TROLLEY_CODE       = CUR_ORDER_NO.IS_TROLLEY_CODE,
               A.IS_POSITIONER         = CUR_ORDER_NO.IS_POSITIONER,
               A.DUST_COVER            = CUR_ORDER_NO.DUST_COVER,
               A.ONE_BY_PACKAGE        = CUR_ORDER_NO.ONE_BY_PACKAGE,
               A.BOARD_LOCATION        = CUR_ORDER_NO.BOARD_LOCATION,
               A.WHEEL_DIAMETER        = CUR_ORDER_NO.WHEEL_DIAMETER,
               A.WORD_DESC             = CUR_ORDER_NO.WORD_DESC,
               A.TRACTION_ROD_HEIGHT   = CUR_ORDER_NO.TRACTION_ROD_HEIGHT,
               A.IMPORTANT_POSTION_PIC = CUR_ORDER_NO.IMPORTANT_POSTION_PIC,
               A.EMPTY_TRO_FRONT_PIC   = CUR_ORDER_NO.EMPTY_TRO_FRONT_PIC,
               A.EMPTY_TRO_SIDE_PIC    = CUR_ORDER_NO.EMPTY_TRO_SIDE_PIC,
               A.REAL_TRO_PIC          = CUR_ORDER_NO.REAL_TRO_PIC,
               A.BOX_CODE              = CUR_ORDER_NO.BOX_CODE,
               A.PACK_LENGTH           = CUR_ORDER_NO.PACK_LENGTH,
               A.PACK_WIDTH            = CUR_ORDER_NO.PACK_WIDTH,
               A.PACK_HEIGHT           = CUR_ORDER_NO.PACK_HEIGHT,
               A.TRAY_LENGTH           = CUR_ORDER_NO.TRAY_LENGTH,
               A.TRAY_WIDTH            = CUR_ORDER_NO.TRAY_WIDTH,
               A.TRAY_HEIGHT           = CUR_ORDER_NO.TRAY_HEIGHT,
               A.WORK_REQUIRE          = CUR_ORDER_NO.WORK_REQUIRE,
               A.SINGLE_PART_PIC       = CUR_ORDER_NO.SINGLE_PART_PIC,
               A.SINGLE_PART_PUT_PIC   = CUR_ORDER_NO.SINGLE_PART_PUT_PIC,
               A.PACK_OVER_LOOK_PIC    = CUR_ORDER_NO.PACK_OVER_LOOK_PIC,
               A.PACK_SIDE_LOOK_PIC    = CUR_ORDER_NO.PACK_SIDE_LOOK_PIC,
               A.LAST_MODIFIED_USER    = CUR_ORDER_NO.LAST_MODIFIED_USER,
               A.LAST_MODIFIED_TIME    = CUR_ORDER_NO.LAST_MODIFIED_TIME,
               A.SUP_NAME              = CUR_ORDER_NO.SUP_NAME,
               A.MOBILE                = CUR_ORDER_NO.MOBILE,
               A.MAIL                  = CUR_ORDER_NO.MAIL
         WHERE A.PROPOSAL_ID = CUR_ORDER_NO.PROPOSAL_ID;
      END IF;
      --更新中间表处理标记位为已处理
      UPDATE IF_MM_PKG_PROPOSAL_DETAIL A
         SET A.DEAL_FLAG = 1, A.DEAL_TIME = SYSDATE
       WHERE A.ID = CUR_ORDER_NO.ID
         AND NVL(A.FACTORY_CODE,'#') = NVL(CUR_ORDER_NO.FACTORY_CODE,'#');
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_PKG_PROPOSAL_DETAIL',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_PKG_PROPOSAL_DETAIL;

  --****************************************************************************
  --过程名  : USP_PKG_PROPOSAL
  --功能描述: 将包装提案信息同步至厂内
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_PKG_PROPOSAL IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.*
                           FROM IF_MM_PKG_PROPOSAL T
                          WHERE T.DEAL_FLAG = 0
                          ORDER BY T.CREATION_TIME) LOOP
      IF CUR_ORDER_NO.DO_FLAG = 'U' THEN
        UPDATE ilmspec.MM_PKG_PROPOSAL@MM_IF_IN_A A
           SET A.PROPOSAL_STATUS = CUR_ORDER_NO.PROPOSAL_STATUS,
               A.PACK_TYPE       = CUR_ORDER_NO.PACK_TYPE,
               A.BOX_TYPE        = CUR_ORDER_NO.BOX_TYPE,
               A.IS_COM_PACK     = CUR_ORDER_NO.IS_COM_PACK,
               A.COM_PACK_REMARK = CUR_ORDER_NO.COM_PACK_REMARK,
               A.SIGN_PRO_FILE   = CUR_ORDER_NO.SIGN_PRO_FILE
         WHERE A.CAR_TYPE = CUR_ORDER_NO.CAR_TYPE
           AND A.SUPPLIER_NO = CUR_ORDER_NO.SUPPLIER_NO
           AND A.PART_NO = CUR_ORDER_NO.PART_NO
           --AND A.PROPOSAL_STATUS = CUR_ORDER_NO.PROPOSAL_STATUS
           --AND NVL(A.EFF_START,'#') = NVL(CUR_ORDER_NO.EFF_START,'#')
           --AND NVL(A.EFF_END,'#')   = NVL(CUR_ORDER_NO.EFF_END,'#')
           AND A.STATUS               = CUR_ORDER_NO.STATUS
           AND A.FACTORY_CODE = CUR_ORDER_NO.FACTORY_CODE;
      END IF;
      --更新中间表处理标记位为已处理
      UPDATE IF_MM_PKG_PROPOSAL A
         SET A.DEAL_FLAG = 1, A.DEAL_TIME = SYSDATE
       WHERE A.ID = CUR_ORDER_NO.ID
         AND A.FACTORY_CODE = CUR_ORDER_NO.FACTORY_CODE;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_PKG_PROPOSAL',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_PKG_PROPOSAL;

  --****************************************************************************
  --过程名  : USP_PKG_TRAY_QTY
  --功能描述: 将包装托盘数量信息同步至厂内
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_PKG_TRAY_QTY IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.*
                           FROM IF_MM_PKG_TRAY_QTY T
                          WHERE T.DEAL_FLAG = 0
                          ORDER BY T.CREATION_TIME) LOOP
      IF CUR_ORDER_NO.DO_FLAG = 'U' THEN
        UPDATE ilmspec.MM_PKG_TRAY_QTY@MM_IF_IN_A A
           SET A.PROVIDE_QTY        = CUR_ORDER_NO.PROVIDE_QTY,
               A.PLAN_DATE          = CUR_ORDER_NO.PLAN_DATE,
               A.DELAY_REASON       = CUR_ORDER_NO.DELAY_REASON,
               A.LAST_MODIFIED_USER = CUR_ORDER_NO.LAST_MODIFIED_USER,
               A.LAST_MODIFIED_TIME = CUR_ORDER_NO.LAST_MODIFIED_TIME
         WHERE A.FACTORY_CODE = CUR_ORDER_NO.FACTORY_CODE
           AND A.CAR_TYPE = CUR_ORDER_NO.CAR_TYPE
           AND A.FACTORY_CODE = CUR_ORDER_NO.FACTORY_CODE;
      END IF;
      --更新中间表处理标记位为已处理
      UPDATE IF_MM_PKG_TRAY_QTY A
         SET A.DEAL_FLAG = 1, A.DEAL_TIME = SYSDATE
       WHERE A.ID = CUR_ORDER_NO.ID
         AND A.FACTORY_CODE = CUR_ORDER_NO.FACTORY_CODE;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_PKG_TRAY_QTY',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_PKG_TRAY_QTY;
  
   --****************************************************************************
  --过程名  : USP_SW_ACCOUNT_BILL
  --功能描述: 将发票反馈同步至厂内，接口从厂内查询数据回传给供应商
  --参数说明: 无
  --创建人员: lbx
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_SW_ACCOUNT_INVOICE IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.*,B.SUBMIT_STATUS FROM MM_SW_ACCOUNT_INVOICE T 
                         LEFT JOIN MM_SW_ACCOUNT_BILL B 
                          ON T.BILL_NO = B.BILL_NO
                          WHERE T.DEAL_FLAG = 0
                          ORDER BY T.CREATION_TIME) LOOP
    
      IF CUR_ORDER_NO.DO_FLAG = 'I' OR CUR_ORDER_NO.DO_FLAG IS NULL
         THEN
        insert into ilmspec.MM_SW_ACCOUNT_INVOICE@MM_IF_IN_A A
          (
            BILL_NO,
            INVOICE_NO,
            INVOICE_CODE,
            INVOICE_AMOUNT,
            TAX_AMOUNT,
            INVOICE_DATE,
            CREATION_USER,
            CREATION_TIME,
            LAST_MODIFIED_USER,
            LAST_MODIFIED_TIME,
            CHECK_CODE,
            INVOICE_NET_PRICE)
          VALUES
            (
            CUR_ORDER_NO.BILL_NO,
            CUR_ORDER_NO.INVOICE_NO,
            CUR_ORDER_NO.INVOICE_CODE,
            CUR_ORDER_NO.INVOICE_AMOUNT,
            CUR_ORDER_NO.TAX_AMOUNT,
            CUR_ORDER_NO.INVOICE_DATE,
            CUR_ORDER_NO.CREATION_USER,
            CUR_ORDER_NO.CREATION_TIME,
            CUR_ORDER_NO.LAST_MODIFIED_USER,
            CUR_ORDER_NO.LAST_MODIFIED_TIME,
            CUR_ORDER_NO.CHECK_CODE,
            CUR_ORDER_NO.INVOICE_NET_PRICE);
         
        --修改主表中提交状态为已提交
         UPDATE ilmspec.MM_SW_ACCOUNT_BILL@MM_IF_IN_A A
            SET A.SUBMIT_STATUS = CUR_ORDER_NO.SUBMIT_STATUS
         WHERE A.BILL_NO = CUR_ORDER_NO.BILL_NO;
      
      ELSIF CUR_ORDER_NO.DO_FLAG = 'U' THEN
        update ilmspec.MM_SW_ACCOUNT_INVOICE@MM_IF_IN_A A
           set  BILL_NO                 = CUR_ORDER_NO.BILL_NO,
                INVOICE_NO              = CUR_ORDER_NO.INVOICE_NO,
                INVOICE_CODE            = CUR_ORDER_NO.INVOICE_CODE,
                INVOICE_AMOUNT          = CUR_ORDER_NO.INVOICE_AMOUNT,
                TAX_AMOUNT              = CUR_ORDER_NO.TAX_AMOUNT,
                INVOICE_DATE            = CUR_ORDER_NO.INVOICE_DATE,
                CREATION_USER           = CUR_ORDER_NO.CREATION_USER,
                CREATION_TIME           = CUR_ORDER_NO.CREATION_TIME,
                LAST_MODIFIED_USER      = CUR_ORDER_NO.LAST_MODIFIED_USER,
                LAST_MODIFIED_TIME      = CUR_ORDER_NO.LAST_MODIFIED_TIME,
                CHECK_CODE              = CUR_ORDER_NO.CHECK_CODE,
                INVOICE_NET_PRICE       = CUR_ORDER_NO.INVOICE_NET_PRICE
         where a.BILL_NO = CUR_ORDER_NO.BILL_NO
               AND A.INVOICE_NO = CUR_ORDER_NO.INVOICE_NO
               AND A.INVOICE_CODE = CUR_ORDER_NO.INVOICE_CODE
               AND A.CHECK_CODE = CUR_ORDER_NO.CHECK_CODE;
          
         --修改主表中提交状态为已提交
          UPDATE ilmspec.MM_SW_ACCOUNT_BILL@MM_IF_IN_A A
            SET A.SUBMIT_STATUS = CUR_ORDER_NO.SUBMIT_STATUS
         WHERE A.BILL_NO = CUR_ORDER_NO.BILL_NO;
      
      ELSIF CUR_ORDER_NO.DO_FLAG = 'D' THEN
        delete from ilmspec.MM_SW_ACCOUNT_INVOICE@MM_IF_IN_A A
         where a.BILL_NO = CUR_ORDER_NO.BILL_NO
               AND A.INVOICE_NO = CUR_ORDER_NO.INVOICE_NO
               AND A.INVOICE_CODE = CUR_ORDER_NO.INVOICE_CODE
               AND A.CHECK_CODE = CUR_ORDER_NO.CHECK_CODE;
      
      END IF;
      --更新中间表处理标记位为已处理
      update MM_SW_ACCOUNT_INVOICE a
         set a.deal_flag = 1, a.deal_time = sysdate
       where   a.BILL_NO = CUR_ORDER_NO.BILL_NO
               AND A.INVOICE_NO = CUR_ORDER_NO.INVOICE_NO
               AND A.INVOICE_CODE = CUR_ORDER_NO.INVOICE_CODE
               AND A.CHECK_CODE = CUR_ORDER_NO.CHECK_CODE;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_SW_ACCOUNT_INVOICE',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_SW_ACCOUNT_INVOICE;
   --****************************************************************************
  --过程名  : USP_SW_ZC_ORDER_FEEDBACK
  --功能描述: 将资材反馈同步到厂内系统
  --参数说明: 无
  --创建人员: dtp
  --创建时间: 2019-03-19
  --****************************************************************************
  PROCEDURE USP_SW_ZC_ORDER_FEEDBACK IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理资材反馈记录
    FOR CUR_FB IN (SELECT T.*
        FROM MM_SW_FEEDBACK_ZC T WHERE 
             T.DEAL_FLAG = 0
             ORDER BY T.CREATION_TIME DESC)
    LOOP
      --全部新增到厂内业务表
      INSERT INTO ilmspec.MM_SW_FEEDBACK_ZC@MM_IF_IN_A Z
       (
             Z.ID,
             Z.FACTORY_CODE,
             Z.PURCHASE_NO,
             Z.REPLY_SEQ_NO,
             Z.PURCHASE_ROW_NO,
             Z.PLAN_TIME,
             Z.PLAN_NUM,
             Z.RETURN_MSG,
             Z.RETURN_TIME,
             Z.CREATION_USER,
             Z.CREATION_TIME
       )VALUES
       (
             ilmspec.SEQ_MM_SW_FEEDBACK_ZC.NEXTVAL@MM_IF_IN_A,
             CUR_FB.FACTORY_CODE,
             CUR_FB.PURCHASE_NO,
             CUR_FB.REPLY_SEQ_NO,
             CUR_FB.PURCHASE_ROW_NO,
             CUR_FB.PLAN_TIME,
             CUR_FB.PLAN_NUM,
             CUR_FB.RETURN_MSG,
             CUR_FB.RETURN_TIME,
             CUR_FB.CREATION_USER,
             SYSDATE
       );
       --更新deal_flag状态
       UPDATE MM_SW_FEEDBACK_ZC C SET 
              C.DEAL_FLAG = '1',C.DEAL_TIME = SYSDATE
       WHERE C.ID = CUR_FB.ID;
    END LOOP;
   COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_SW_ZC_ORDER_FEEDBACK',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_SW_ZC_ORDER_FEEDBACK;
  --****************************************************************************

--****************************************************************************
  --过程名  : USP_SW_NON_STANDAR
  --功能描述: 将非标件信息同步至共享平台
  --参数说明: 无
  --创建人员: lxq
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_SW_NON_STANDAR IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.*
                           FROM MM_SW_NON_STANDARD T 
                          WHERE T.DEAL_FLAG = 0
                          ORDER BY T.SALE_NO) LOOP
    
      IF CUR_ORDER_NO.DO_FLAG = 'I' THEN
        insert into ilmspec.MM_SW_NON_STANDARD@MM_IF_IN_A
          (ID,
           FACTORY_CODE,
           ORDER_NO,
           SALE_NO,
           SALE_ROW_NO,
           SUPPLIER_NO,
           SUP_FACTORY,
           PART_NO,
           ORDER_NUM,
           CREATION_USER,
           CREATION_TIME,
           LAST_MODIFIED_USER,
           LAST_MODIFIED_TIME,
           LAST_CHECK_ID,
           PIC_UPLOAD_STATUS,
           DEAL_FLAG)
        VALUES
          (ilmspec.SEQ_SW_NON_STANDARD.NEXTVAL@MM_IF_IN_A,
           CUR_ORDER_NO.FACTORY_CODE,
           CUR_ORDER_NO.ORDER_NO,
           CUR_ORDER_NO.SALE_NO,
           CUR_ORDER_NO.SALE_ROW_NO,
           CUR_ORDER_NO.SUPPLIER_NO,
           CUR_ORDER_NO.SUP_FACTORY,
           CUR_ORDER_NO.PART_NO,
           CUR_ORDER_NO.ORDER_NUM,
           CUR_ORDER_NO.CREATION_USER,
           CUR_ORDER_NO.CREATION_TIME,
           CUR_ORDER_NO.LAST_MODIFIED_USER,
           CUR_ORDER_NO.LAST_MODIFIED_TIME,
           CUR_ORDER_NO.LAST_CHECK_ID,
           CUR_ORDER_NO.PIC_UPLOAD_STATUS,
           '1');
      
      ELSIF CUR_ORDER_NO.DO_FLAG = 'U' THEN
        update ilmspec.MM_SW_NON_STANDARD@MM_IF_IN_A a
           set  
               FACTORY_CODE                        = CUR_ORDER_NO.FACTORY_CODE,
               ORDER_NO                            = CUR_ORDER_NO.ORDER_NO,
               SALE_NO                             = CUR_ORDER_NO.SALE_NO,
               SALE_ROW_NO                         = CUR_ORDER_NO.SALE_ROW_NO,
               SUPPLIER_NO                         = CUR_ORDER_NO.SUPPLIER_NO,
               SUP_FACTORY                         = CUR_ORDER_NO.SUP_FACTORY,
               PART_NO                             = CUR_ORDER_NO.PART_NO,
               ORDER_NUM                           = CUR_ORDER_NO.ORDER_NUM,
               CREATION_USER                       = CUR_ORDER_NO.CREATION_USER,
               CREATION_TIME                       = CUR_ORDER_NO.CREATION_TIME,
               LAST_MODIFIED_USER                  = CUR_ORDER_NO.LAST_MODIFIED_USER,
               LAST_MODIFIED_TIME                  = CUR_ORDER_NO.LAST_MODIFIED_TIME,
               LAST_CHECK_ID                       = CUR_ORDER_NO.LAST_CHECK_ID,
               PIC_UPLOAD_STATUS                   = CUR_ORDER_NO.PIC_UPLOAD_STATUS,
               DEAL_FLAG                           = '1'
         where a.SALE_NO = CUR_ORDER_NO.SALE_NO
               and a.SALE_ROW_NO    = CUR_ORDER_NO.SALE_ROW_NO
               AND A.PART_NO     = CUR_ORDER_NO.PART_NO;
      
      END IF;
      --更新处理标记位为已处理
      update MM_SW_NON_STANDARD a
         set a.deal_flag = 1, a.deal_time = sysdate
       where a.ID = CUR_ORDER_NO.ID;
         --and a.factory_code = CUR_ORDER_NO.FACTORY_CODE;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_SW_NON_STANDAR',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_SW_NON_STANDAR;
  
  
  --****************************************************************************
  --过程名  : USP_SW_NON_STANDAR_CHECK
  --功能描述: 将非标件检查信息同步至共享平台
  --参数说明: 无
  --创建人员: lxq
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_SW_NON_STANDAR_CHECK IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.*
                           FROM MM_SW_NON_STANDAR_CHECK T 
                          WHERE T.DEAL_FLAG = 0
                          ORDER BY T.SALE_NO) LOOP
    
      IF CUR_ORDER_NO.DO_FLAG = 'I' THEN
        insert into ilmspec.MM_SW_NON_STANDAR_CHECK@MM_IF_IN_A
          (ID,
            SALE_NO,
            SALE_ROW_NO,
            PART_NO,
            REMARK,
            CHECK_RESULT,
            CHECKER,
            CHECK_TIME,
            CREATION_USER,
            CREATION_TIME,
            CREATION_USER_IP,
            DEAL_FLAG)
        VALUES
          (CUR_ORDER_NO.ID,
            CUR_ORDER_NO.SALE_NO,
            CUR_ORDER_NO.SALE_ROW_NO,
            CUR_ORDER_NO.PART_NO,
            CUR_ORDER_NO.REMARK,
            CUR_ORDER_NO.CHECK_RESULT,
            CUR_ORDER_NO.CHECKER,
            CUR_ORDER_NO.CHECK_TIME,
            CUR_ORDER_NO.CREATION_USER,
            CUR_ORDER_NO.CREATION_TIME,
            CUR_ORDER_NO.CREATION_USER_IP,
            '1');
      
      ELSIF CUR_ORDER_NO.DO_FLAG = 'U' THEN
        update ilmspec.MM_SW_NON_STANDAR_CHECK@MM_IF_IN_A a
           set  
              SALE_NO                                   = CUR_ORDER_NO.SALE_NO,
              SALE_ROW_NO                               = CUR_ORDER_NO.SALE_ROW_NO,
              PART_NO                                   = CUR_ORDER_NO.PART_NO,
              REMARK                                    = CUR_ORDER_NO.REMARK,
              CHECK_RESULT                              = CUR_ORDER_NO.CHECK_RESULT,
              CHECKER                                   = CUR_ORDER_NO.CHECKER,
              CHECK_TIME                                = CUR_ORDER_NO.CHECK_TIME,
              CREATION_USER                             = CUR_ORDER_NO.CREATION_USER,
              CREATION_TIME                             = CUR_ORDER_NO.CREATION_TIME,
              CREATION_USER_IP                          = CUR_ORDER_NO.CREATION_USER_IP,
              DEAL_FLAG                                 = '1'
         where A.ID  = CUR_ORDER_NO.ID ;
      
      END IF;
      --更新处理标记位为已处理
      update MM_SW_NON_STANDAR_CHECK a
         set a.deal_flag = 1, a.deal_time = sysdate
       where  A.ID  = CUR_ORDER_NO.ID ;
         --and a.factory_code = CUR_ORDER_NO.FACTORY_CODE;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_SW_NON_STANDAR_CHECK',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_SW_NON_STANDAR_CHECK;
  
  
  --****************************************************************************
  --过程名  : USP_SW_NON_STAND_PIC
  --功能描述: 将非标件图片关系信息同步至共享平台
  --参数说明: 无
  --创建人员: lxq
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_SW_NON_STAND_PIC IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.*
                           FROM MM_SW_NON_STAND_PIC T 
                          WHERE T.DEAL_FLAG = 0
                          ORDER BY T.SALE_NO) LOOP
    
      IF CUR_ORDER_NO.DO_FLAG = 'I' THEN
        insert into ilmspec.MM_SW_NON_STAND_PIC@MM_IF_IN_A
          (CHECK_ID,
            SALE_NO,
            SALE_ROW_NO,
            PART_NO,
            PIC_TYPE,
            PIC_ID,
            CREATION_TIME,
            CREATION_USER,
            LAST_MODIFIED_USER,
            LAST_MODIFIED_TIME,
            DEAL_FLAG)
        VALUES
          (CUR_ORDER_NO.CHECK_ID,
            CUR_ORDER_NO.SALE_NO,
            CUR_ORDER_NO.SALE_ROW_NO,
            CUR_ORDER_NO.PART_NO,
            CUR_ORDER_NO.PIC_TYPE,
            CUR_ORDER_NO.PIC_ID,
            CUR_ORDER_NO.CREATION_TIME,
            CUR_ORDER_NO.CREATION_USER,
            CUR_ORDER_NO.LAST_MODIFIED_USER,
            CUR_ORDER_NO.LAST_MODIFIED_TIME,
            '1');

      ELSIF CUR_ORDER_NO.DO_FLAG = 'U' THEN
        update ilmspec.MM_SW_NON_STAND_PIC@MM_IF_IN_A a
           set  
              CHECK_ID                                  = CUR_ORDER_NO.CHECK_ID,
              SALE_NO                                   = CUR_ORDER_NO.SALE_NO,
              SALE_ROW_NO                               = CUR_ORDER_NO.SALE_ROW_NO,
              PART_NO                                   = CUR_ORDER_NO.PART_NO,
              PIC_TYPE                                  = CUR_ORDER_NO.PIC_TYPE,
              PIC_ID                                    = CUR_ORDER_NO.PIC_ID,
              CREATION_TIME                             = CUR_ORDER_NO.CREATION_TIME,
              CREATION_USER                             = CUR_ORDER_NO.CREATION_USER,
              LAST_MODIFIED_USER                        = CUR_ORDER_NO.LAST_MODIFIED_USER,
              LAST_MODIFIED_TIME                        = CUR_ORDER_NO.LAST_MODIFIED_TIME,
              DEAL_FLAG                                 = '1'
         where A.SALE_NO  = CUR_ORDER_NO.SALE_NO
           AND A.SALE_ROW_NO = CUR_ORDER_NO.SALE_ROW_NO
           AND A.PART_NO = CUR_ORDER_NO.PART_NO
           AND NVL(A.PIC_ID,'-1') = NVL(CUR_ORDER_NO.PIC_ID,'-1') ;
      
      END IF;
      
       --将图片信息同步至厂内
       insert into ilmspec.SYS_FILE@MM_IF_IN_A SELECT * FROM SYS_FILE A WHERE A.ID_ = CUR_ORDER_NO.PIC_ID;
      --更新处理标记位为已处理
      update MM_SW_NON_STAND_PIC a
         set a.deal_flag = '1', a.deal_time = sysdate
       where  A.SALE_NO  = CUR_ORDER_NO.SALE_NO
           AND A.SALE_ROW_NO = CUR_ORDER_NO.SALE_ROW_NO
           AND A.PART_NO = CUR_ORDER_NO.PART_NO
           AND NVL(A.PIC_ID,'-1') = NVL(CUR_ORDER_NO.PIC_ID,'-1') ;
         --and a.factory_code = CUR_ORDER_NO.FACTORY_CODE;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_SW_NON_STAND_PIC',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_SW_NON_STAND_PIC;
  
  --****************************************************************************
  --过程名  : USP_IF_SW_NON_STAND_PIC
  --功能描述: 将非标件图片关系信息同步至共享平台(处理删除操作)
  --参数说明: 无
  --创建人员: lxq
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_IF_SW_NON_STAND_PIC IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.*
                           FROM IF_SW_NON_STAND_PIC T 
                          WHERE T.DEAL_FLAG = 0
                          ORDER BY T.SALE_NO) LOOP
    
      IF CUR_ORDER_NO.DO_FLAG = 'D' THEN
        DELETE FROM ilmspec.MM_SW_NON_STAND_PIC@MM_IF_IN_A A
          WHERE A.CHECK_ID = CUR_ORDER_NO.CHECK_ID
          AND   A.SALE_NO = CUR_ORDER_NO.SALE_NO
          AND   A.SALE_ROW_NO = CUR_ORDER_NO.SALE_ROW_NO
          AND   A.PART_NO = CUR_ORDER_NO.PART_NO
          AND   NVL(A.PIC_TYPE,'-1') = NVL(CUR_ORDER_NO.PIC_TYPE,'-1')
          AND   NVL(A.PIC_ID,'-1') = NVL(CUR_ORDER_NO.PIC_ID,'-1');
      
      END IF;
      
       --将图片信息同步至厂内
       --insert into ilmspec.SYS_FILE@MM_IF_IN_A SELECT * FROM SYS_FILE A WHERE A.ID_ = CUR_ORDER_NO.PIC_ID;
      --更新处理标记位为已处理
      update IF_SW_NON_STAND_PIC a
         set a.deal_flag = 1, a.deal_time = sysdate
       where  A.CHECK_ID = CUR_ORDER_NO.CHECK_ID
          AND   A.SALE_NO = CUR_ORDER_NO.SALE_NO
          AND   A.SALE_ROW_NO = CUR_ORDER_NO.SALE_ROW_NO
          AND   A.PART_NO = CUR_ORDER_NO.PART_NO
          AND   NVL(A.PIC_TYPE,'-1') = NVL(CUR_ORDER_NO.PIC_TYPE,'-1')
          AND   NVL(A.PIC_ID,'-1') = NVL(CUR_ORDER_NO.PIC_ID,'-1');
         --and a.factory_code = CUR_ORDER_NO.FACTORY_CODE;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_IF_SW_NON_STAND_PIC',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_IF_SW_NON_STAND_PIC;
  
  --****************************************************************************
  --过程名  : USP_IF_SW_NON_STANDAR_CHECK
  --功能描述: 将非标件检查信息同步至共享平台(处理删除操作)
  --参数说明: 无
  --创建人员: lxq
  --创建时间: 2019-01-09
  --****************************************************************************
  PROCEDURE USP_IF_SW_NON_STANDAR_CHECK IS
    err_num number(10); --错误代码
    err_msg varchar2(200); --错误信息
  BEGIN
    --查询未处理订单
    FOR CUR_ORDER_NO IN (SELECT T.*
                           FROM IF_SW_NON_STANDAR_CHECK T 
                          WHERE T.DEAL_FLAG = 0
                          ORDER BY T.SALE_NO) LOOP
    
      IF CUR_ORDER_NO.DO_FLAG = 'D' THEN
        DELETE FROM ilmspec.MM_SW_NON_STANDAR_CHECK@MM_IF_IN_A A 
         WHERE A.ID = CUR_ORDER_NO.ID;
      
      END IF;
      --更新处理标记位为已处理
      update MM_SW_NON_STANDAR_CHECK a
         set a.deal_flag = 1, a.deal_time = sysdate
       where  A.ID  = CUR_ORDER_NO.ID ;
         --and a.factory_code = CUR_ORDER_NO.FACTORY_CODE;
      COMMIT;
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_num := SQLCODE;
      err_msg := SUBSTR(SQLERRM || dbms_utility.format_error_backtrace(),
                        1,
                        200);
      insert into mm_pub_pro_error
        (id, alert_type, error_name, error_desc, creation_date, key_name)
      values
        (SEQ_PUB_PRO_ERROR.nextval,
         'USP_IF_SW_NON_STANDAR_CHECK',
         err_num,
         err_msg,
         sysdate,
         '');
      COMMIT;
  END USP_IF_SW_NON_STANDAR_CHECK;
end PKG_IF_SEND_IN;
/

prompt
prompt Creating package body PKG_PUB
prompt =============================
prompt
create or replace package body PKG_PUB is

  --****************************************************************************
  --函数  : USF_GET_BATCH_CYCLE
  --功能描述: 获取批次循环基数
  --参数说明:
  -- in_plan_code  :信息点
  --返回值: 批次循环基数
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_BATCH_CYCLE(in_plan_code in varchar2) RETURN NUMBER AS
    out_batch_cycle mm_mon_kb.batch_cycle_num%type;
  BEGIN
    for cur_kb in (select b.batch_cycle_num
                     from mm_pub_plan_code a, mm_mon_kb b
                    where a.kb_id = b.id
                      and a.plan_code = in_plan_code) loop
      out_batch_cycle := cur_kb.batch_cycle_num;
    end loop;
    return out_batch_cycle;
  END USF_GET_BATCH_CYCLE;

  --****************************************************************************
  --函数  : USF_GET_PROCESS_CYCLE
  --功能描述: 获取进度循环基数
  --参数说明:
  -- in_plan_code  :信息点
  --返回值: 进度循环基数
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_PROCESS_CYCLE(in_plan_code in varchar2) RETURN NUMBER AS
    out_process_cycle mm_mon_kb.process_cycle_num%type;
  BEGIN
    for cur_kb in (select b.process_cycle_num
                     from mm_pub_plan_code a, mm_mon_kb b
                    where a.kb_id = b.id
                      and a.plan_code = in_plan_code) loop
      out_process_cycle := cur_kb.process_cycle_num;
    end loop;
    return out_process_cycle;
  END USF_GET_PROCESS_CYCLE;

  --****************************************************************************
  --函数名  : USF_GET_BATCH_NO_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取批次
  --参数说明:
  -- in_kb_id  :看板ID
  -- in_product_seqno  :产品流水号
  --返回值  : 批次号
  --创建人员: wxl
  --创建时间: 2018-11-12
  --****************************************************************************
  FUNCTION USF_GET_BATCHNO_BY_PRODSEQNO(in_kb_id         in number,
                                        in_product_seqno in number)
    RETURN NUMBER AS
    out_batch_no    mm_mon_kb.curr_batch_no%type;
    v_batch_cycle   mm_mon_kb.batch_cycle_num%type; --批次循环基数
    v_process_cycle mm_mon_kb.process_cycle_num%type; --进度循环基数
  BEGIN
    --获取批次循环基数和进度循环基数
    for cur_kb in (select b.batch_cycle_num, b.process_cycle_num
                     from mm_mon_kb b
                    where b.id = in_kb_id) loop
      v_batch_cycle := cur_kb.batch_cycle_num;
      v_process_cycle := cur_kb.process_cycle_num;
    end loop;

    --计算批次号
    if in_product_seqno < 1 then
      out_batch_no := mod(ceil((1-in_product_seqno) / v_process_cycle),
                          v_batch_cycle);
      if out_batch_no = 0 then
        out_batch_no := v_batch_cycle;
      end if;
      out_batch_no := v_batch_cycle - out_batch_no + 1;
    else
      out_batch_no := mod(ceil(in_product_seqno / v_process_cycle),
                          v_batch_cycle);
      if out_batch_no = 0 then
        out_batch_no := v_batch_cycle;
      end if;
    end if;
    return out_batch_no;
  END USF_GET_BATCHNO_BY_PRODSEQNO;

  --****************************************************************************
  --函数名  : USF_GET_PROCESSNO_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取批次
  --参数说明:
  -- in_kb_id  :看板ID
  -- in_product_seqno  :产品流水号
  --返回值  : 进度号
  --创建人员: wxl
  --创建时间: 2018-11-12
  --****************************************************************************
  FUNCTION USF_GET_PROCESSNO_BY_PRODSEQNO(in_kb_id         in number,
                                          in_product_seqno in number)
    RETURN NUMBER AS
    out_process_no  mm_mon_kb.curr_process_no%type;
    v_process_cycle mm_mon_kb.process_cycle_num%type; --进度循环基数
  BEGIN
    --获取进度循环基数
    for cur_kb in (select b.process_cycle_num
                     from mm_mon_kb b
                    where b.id = in_kb_id) loop
      v_process_cycle := cur_kb.process_cycle_num;
    end loop;

    --计算进度号
    if in_product_seqno < 1 then
      out_process_no := v_process_cycle -
                  mod(1-in_product_seqno, v_process_cycle) + 1;
    else
      out_process_no := v_process_cycle -
                  mod(in_product_seqno, v_process_cycle) + 1;
    end if;
    if out_process_no = v_process_cycle + 1 then
      out_process_no := 1;
    end if;

    return out_process_no;
  END USF_GET_PROCESSNO_BY_PRODSEQNO;

  --****************************************************************************
  --函数名  : USF_GET_BATCH_NO_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取批次
  --参数说明:
  -- in_plan_code  :信息点
  -- in_product_seqno  :产品流水号
  --返回值  : 批次号
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_BATCHNO_BY_PRODSEQNO(in_plan_code     in varchar2,
                                        in_product_seqno in number)
    RETURN NUMBER AS
    out_batch_no    mm_mon_kb.curr_batch_no%type;
    v_batch_cycle   mm_mon_kb.batch_cycle_num%type; --批次循环基数
    v_process_cycle mm_mon_kb.process_cycle_num%type; --进度循环基数
  BEGIN
    --获取批次循环基数和进度循环基数
    v_batch_cycle   := USF_GET_BATCH_CYCLE(in_plan_code);
    v_process_cycle := USF_GET_PROCESS_CYCLE(in_plan_code);

    --计算批次号
    if in_product_seqno < 1 then
      out_batch_no := mod(ceil((1-in_product_seqno) / v_process_cycle),
                          v_batch_cycle);
      if out_batch_no = 0 then
        out_batch_no := v_batch_cycle;
      end if;
      out_batch_no := v_batch_cycle - out_batch_no + 1;
    else
      out_batch_no := mod(ceil(in_product_seqno / v_process_cycle),
                          v_batch_cycle);
      if out_batch_no = 0 then
        out_batch_no := v_batch_cycle;
      end if;
    end if;
    return out_batch_no;
  END USF_GET_BATCHNO_BY_PRODSEQNO;

  --****************************************************************************
  --函数名  : USF_GET_PROCESSNO_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取批次
  --参数说明:
  -- in_plan_code  :信息点
  -- in_product_seqno  :产品流水号
  --返回值  : 进度号
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_PROCESSNO_BY_PRODSEQNO(in_plan_code     in varchar2,
                                          in_product_seqno in number)
    RETURN NUMBER AS
    out_process_no  mm_mon_kb.curr_process_no%type;
    v_process_cycle mm_mon_kb.process_cycle_num%type; --进度循环基数
  BEGIN
    --获取进度循环基数
    v_process_cycle := USF_GET_PROCESS_CYCLE(in_plan_code);

    --计算进度号
    if in_product_seqno < 1 then
      out_process_no := v_process_cycle -
                  mod(1-in_product_seqno, v_process_cycle) + 1;
    else
      out_process_no := v_process_cycle -
                  mod(in_product_seqno, v_process_cycle) + 1;
    end if;
    if out_process_no = v_process_cycle + 1 then
      out_process_no := 1;
    end if;

    return out_process_no;
  END USF_GET_PROCESSNO_BY_PRODSEQNO;

  --****************************************************************************
  --函数名  : USF_GET_PRODSEQNO_BY_BATCH
  --功能描述: 根据批次获取产品流水号
  --参数说明:
  -- in_plan_code  :信息点
  -- in_batch_no  :批次号
  -- in_process_no  :进度号
  --返回值  : 产品流水号
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_PRODSEQNO_BY_BATCH(in_plan_code  in varchar2,
                                      in_batch_no   in number,
                                      in_process_no in number) RETURN NUMBER AS
    out_product_seqno  mm_mon_kb.curr_batch_seqno%type;
    v_process_cycle    mm_mon_kb.process_cycle_num%type; --进度循环基数
    v_kb_batch_no      mm_mon_kb.curr_batch_no%type; --看板批次号
    v_kb_process_no    mm_mon_kb.curr_process_no%type; --看板进度号
    v_kb_product_seqno mm_mon_kb.product_seqno%type; --看板产品流水号
  BEGIN
    --获取进度循环基数
    v_process_cycle := USF_GET_PROCESS_CYCLE(in_plan_code);

    --获取当前看板的相关信息
    select b.curr_batch_no, b.curr_process_no, b.product_seqno
      into v_kb_batch_no, v_kb_process_no, v_kb_product_seqno
      from mm_pub_plan_code a, mm_mon_kb b
     where a.kb_id = b.id
       and a.plan_code = in_plan_code;

    --计算当前批次循环，首台产品流水号
    out_product_seqno := v_kb_product_seqno -
                         v_kb_batch_no * v_process_cycle + v_kb_process_no;
    --计算传入批次对应产品流水号
    out_product_seqno := out_product_seqno + in_batch_no * v_process_cycle -
                         in_process_no;

    return out_product_seqno;
  END USF_GET_PRODSEQNO_BY_BATCH;

  --****************************************************************************
  --函数名  : USF_GET_BATCHSEQ_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取批次流水号
  --参数说明:
  -- in_kb_id  :看板ID
  -- in_product_seqno  :产品流水号
  --返回值  : 批次流水号
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_BATCHSEQ_BY_PRODSEQNO(in_kb_id         in number,
                                         in_product_seqno in number)
    RETURN NUMBER AS
    out_batch_seqno mm_mon_kb.curr_batch_seqno%type;
    v_process_cycle mm_mon_kb.process_cycle_num%type; --进度循环基数
  BEGIN
    --获取进度循环基数
    --获取批次循环基数和进度循环基数
    for cur_kb in (select b.batch_cycle_num, b.process_cycle_num
                     from mm_mon_kb b
                    where b.id = in_kb_id) loop
      v_process_cycle := cur_kb.process_cycle_num;
    end loop;

    --计算批次流水流水号
    if v_process_cycle is not null then
      out_batch_seqno := ceil(in_product_seqno / v_process_cycle);
    end if;

    return out_batch_seqno;
  END USF_GET_BATCHSEQ_BY_PRODSEQNO;

  --****************************************************************************
  --函数名  : USF_GET_BATCHSEQ_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取批次流水号
  --参数说明:
  -- in_plan_code  :信息点
  -- in_product_seqno  :产品流水号
  --返回值  : 批次流水号
  --创建人员: wxl
  --创建时间: 2018-09-10
  --****************************************************************************
  FUNCTION USF_GET_BATCHSEQ_BY_PRODSEQNO(in_plan_code     in varchar2,
                                         in_product_seqno in number)
    RETURN NUMBER AS
    out_batch_seqno mm_mon_kb.curr_batch_seqno%type;
    v_process_cycle mm_mon_kb.process_cycle_num%type; --进度循环基数
  BEGIN
    --获取进度循环基数
    v_process_cycle := USF_GET_PROCESS_CYCLE(in_plan_code);

    --计算批次流水流水号
    if v_process_cycle is not null then
      out_batch_seqno := ceil(in_product_seqno / v_process_cycle);
    end if;

    return out_batch_seqno;
  END USF_GET_BATCHSEQ_BY_PRODSEQNO;
  --****************************************************************************
  --函数名  : USF_GET_BATCHPROC_BY_PRODSEQNO
  --功能描述: 根据产品流水号获取 批次-进度
  --参数说明:
  -- in_plan_code  :信息点
  -- in_product_seqno  :产品流水号
  --返回值  : 批次-进度
  --创建人员: dtp
  --创建时间: 2018-10-20
  --****************************************************************************
  FUNCTION USF_GET_BATCHPROC_BY_PRODSEQNO(in_plan_code     in varchar2,
                                        in_product_seqno in number)
    RETURN VARCHAR2 AS
    out_batch_process VARCHAR2(20);
    v_batch_no mm_mon_kb.curr_batch_no%type; --批次
    v_process_no mm_mon_kb.curr_process_no%type; --进度
  BEGIN
    v_batch_no := PKG_PUB.USF_GET_BATCHNO_BY_PRODSEQNO(in_plan_code, in_product_seqno);
    v_process_no := PKG_PUB.USF_GET_PROCESSNO_BY_PRODSEQNO(in_plan_code, in_product_seqno);
    out_batch_process := v_batch_no || '-' || v_process_no;
    return out_batch_process;
  END USF_GET_BATCHPROC_BY_PRODSEQNO;

  --****************************************************************************
  
   --****************************************************************************
  --函数名  : USF_GET_SEQUENCE
  --功能描述: 获取数据库序列值
  --参数说明:
  -- in_seq_name    :序列名字
  --返回值  : 下一个序列值
  --创建人员: chenjq
  --创建时间: 2016-11-15
  --****************************************************************************
  FUNCTION USF_GET_SEQUENCE(in_seq_name IN VARCHAR2) RETURN NUMBER AS
    v_seq_next_val number(19); --下一个序列值
  BEGIN
    --获取下一个序列值
    v_seq_next_val := null;
    execute immediate 'SELECT ' || in_seq_name || '.nextval FROM dual'
      INTO v_seq_next_val;
    RETURN v_seq_next_val;
  END USF_GET_SEQUENCE;

end PKG_PUB;
/

prompt
prompt Creating package body PKG_PUB_CHECK
prompt ===================================
prompt
create or replace package body PKG_PUB_CHECK is

  --*****************************************************************************
  --存储过程名：USP_SW_ZC_ORDER_CHECK
  --功能描述：资材导入校验
  --参数说明：
  --IN_VAR_UUID
  --IN_VAR_USERNAME 操作人
  --IN_VAR_OPEIP ip地址
  --OUT_ERROR_FLAG 返回错误标识
  --OUT_OUT_ERROR_MSG 返回错误信息
  --创建人员: dtp
  --创建时间： 2019/02/20
   PROCEDURE USP_SW_ZC_ORDER_CHECK(IN_VAR_UUID       IN VARCHAR2,
                                           IN_VAR_USERNAME   IN VARCHAR2,
                                           IN_VAR_OPEIP      IN VARCHAR2,
                                           OUT_ERROR_FLAG    OUT VARCHAR2,
                                           OUT_OUT_ERROR_MSG OUT VARCHAR2)  IS
    v_err_num VARCHAR2(16);
    v_err_msg VARCHAR2(256);
  BEGIN
    OUT_ERROR_FLAG    := '0';
    OUT_OUT_ERROR_MSG := '';
  --计划交货数量超过订购数量
  UPDATE MM_SW_FEEDBACK_ZC_IMP IMP
     SET IMP.CHECK_RESULT = '0',
         IMP.CHECK_INFO  =
         (nvl(imp.check_info, '') || '计划交货数量超过订购数量;')
     WHERE IMP.IMPORT_STATUS = 0
     AND IMP.IMP_UUID =  IN_VAR_UUID
     AND IMP.ID IN (
           SELECT 
             T3.ID
           FROM(
           SELECT 
                 T.FACTORY_CODE,
                 T.PURCHASE_NO,
                 T.PURCHASE_ROW_NO,
                 SUM(T.PLAN_NUM) SUM
           FROM MM_SW_FEEDBACK_ZC_IMP T
           WHERE T.IMP_UUID = IN_VAR_UUID
           GROUP BY 
                 T.FACTORY_CODE,
                 T.PURCHASE_NO,
                 T.PURCHASE_ROW_NO) T2
           INNER JOIN MM_SW_FEEDBACK_ZC_IMP T3
           ON T3.FACTORY_CODE = T2.FACTORY_CODE AND T3.PURCHASE_NO = T2.PURCHASE_NO AND T3.PURCHASE_ROW_NO = T2.PURCHASE_ROW_NO
           WHERE T3.ORDER_QTY < T2.SUM
     );
   --计划交货数量不满足订购数量
   UPDATE MM_SW_FEEDBACK_ZC_IMP IMP
     SET IMP.CHECK_RESULT = '0',
         IMP.CHECK_INFO  =
         (nvl(imp.check_info, '') || '计划交货数量不满足订购数量;')
     WHERE IMP.IMPORT_STATUS = 0
     AND IMP.IMP_UUID =  IN_VAR_UUID
     AND IMP.ID IN (
           SELECT 
             T3.ID
           FROM(
           SELECT 
                 T.FACTORY_CODE,
                 T.PURCHASE_NO,
                 T.PURCHASE_ROW_NO,
                 SUM(T.PLAN_NUM) SUM
           FROM MM_SW_FEEDBACK_ZC_IMP T
           WHERE T.IMP_UUID = IN_VAR_UUID
           GROUP BY 
                 T.FACTORY_CODE,
                 T.PURCHASE_NO,
                 T.PURCHASE_ROW_NO) T2
           INNER JOIN MM_SW_FEEDBACK_ZC_IMP T3
           ON T3.FACTORY_CODE = T2.FACTORY_CODE AND T3.PURCHASE_NO = T2.PURCHASE_NO AND T3.PURCHASE_ROW_NO = T2.PURCHASE_ROW_NO
           WHERE T3.ORDER_QTY > T2.SUM
     );
   --计划交货日期早于要求交货日期
   /*UPDATE MM_SW_FEEDBACK_ZC_IMP IMP
     SET IMP.CHECK_RESULT = '0',
         IMP.CHECK_INFO  =
         (nvl(imp.check_info, '') || '计划交货日期早于要求交货日期;')
         --(nvl(imp.check_info, '') || '计划交货日期早于当前日期;')
     WHERE IMP.IMPORT_STATUS = 0
     AND IMP.IMP_UUID =  IN_VAR_UUID
     AND IMP.ID IN (
         SELECT 
          T.ID
         FROM MM_SW_FEEDBACK_ZC_IMP T 
         WHERE T.IMP_UUID = IN_VAR_UUID
         AND T.ARRIVE_DATE < T.PLAN_TIME
         --AND T.PLAN_TIME < SYSDATE
     );*/
     --计划交货日期早于要求交货日期
   UPDATE MM_SW_FEEDBACK_ZC_IMP IMP
     SET IMP.CHECK_RESULT = '0',
         IMP.CHECK_INFO  =
        -- (nvl(imp.check_info, '') || '计划交货日期早于要求交货日期;')
         (nvl(imp.check_info, '') || '计划交货日期小于当前日期;')
     WHERE IMP.IMPORT_STATUS = 0
     AND IMP.IMP_UUID =  IN_VAR_UUID
     AND IMP.ID IN (
         SELECT 
          T.ID
         FROM MM_SW_FEEDBACK_ZC_IMP T 
         WHERE T.IMP_UUID = IN_VAR_UUID
         --AND T.ARRIVE_DATE < T.PLAN_TIME
         AND T.PLAN_TIME < SYSDATE
     );
    --订单已反馈
    UPDATE MM_SW_FEEDBACK_ZC_IMP IMP
         SET IMP.CHECK_RESULT = '0',
             IMP.CHECK_INFO  =
             (nvl(imp.check_info, '') || '订单已反馈;')
         WHERE IMP.IMPORT_STATUS = 0
         AND IMP.IMP_UUID =  IN_VAR_UUID
         AND EXISTS (SELECT 1 FROM 
             MM_SW_FEEDBACK_ZC Z WHERE Z.PURCHASE_NO = IMP.PURCHASE_NO
         );
    --发货次数超过4次
    UPDATE MM_SW_FEEDBACK_ZC_IMP IMP
       SET IMP.CHECK_RESULT = '0',
           IMP.CHECK_INFO  =
           (nvl(imp.check_info, '') || '发货次数超过4次;')
     WHERE IMP.IMPORT_STATUS = 0
       AND IMP.IMP_UUID = IN_VAR_UUID
       AND IMP.PURCHASE_NO IN (SELECT T.PURCHASE_NO
                                 FROM MM_SW_FEEDBACK_ZC_IMP T
                                WHERE T.IMP_UUID = IN_VAR_UUID
                                GROUP BY T.PURCHASE_NO, T.PURCHASE_ROW_NO
                               HAVING COUNT(*) > 4);
    --------------------------------------------------
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      v_err_num := sqlcode;
      v_err_msg := substr(sqlerrm || dbms_utility.format_error_backtrace(),
                          1,
                          200);
      INSERT INTO MM_PUB_PRO_ERROR
        (ID, ALERT_TYPE, ERROR_NAME, ERROR_DESC, CREATION_DATE, KEY_NAME)
      VALUES
        (SEQ_PUB_PRO_ERROR.NEXTVAL,
         'PKG_PUB_CHECK.USP_SW_ZC_ORDER_CHECK',
         v_err_num,
         v_err_msg,
         SYSDATE,
         '');
      OUT_ERROR_FLAG    := '1';
      OUT_OUT_ERROR_MSG := v_err_msg;

  END USP_SW_ZC_ORDER_CHECK;
  --*****************************************************************************
  
  --***************************************************************************
  --存储过程名：USP_SW_SUPPLIER_GROUP_CHECK
  --功能描述：供应商信息导入校验
  --参数说明：
  --IN_VAR_UUID
  --IN_VAR_USERNAME 操作人
  --IN_VAR_OPEIP ip地址
  --OUT_ERROR_FLAG 返回错误标识
  --OUT_OUT_ERROR_MSG 返回错误信息
  --创建人员: luoxianqin
  --创建时间： 2018/09/10
  PROCEDURE USP_SW_SUPPLIER_GROUP_CHECK(IN_VAR_UUID       IN VARCHAR2,
                                   IN_VAR_USERNAME   IN VARCHAR2,
                                   IN_VAR_OPEIP      IN VARCHAR2,
                                   OUT_ERROR_FLAG    OUT VARCHAR2,
                                   OUT_OUT_ERROR_MSG OUT VARCHAR2) IS
    v_err_num VARCHAR2(16);
    v_err_msg VARCHAR2(256);
  BEGIN
    UPDATE MM_SW_SUPPLIER_GROUP_IMP A
       SET A.CHECK_RESULT = '0',
           A.CHECK_INFO   = A.CHECK_INFO || '用户不存在;'
     WHERE NOT EXISTS (SELECT 1
              FROM SYS_USER B
             WHERE A.SUPPLIER_NO = B.ACCOUNT_
             AND B.USER_TYPE = 2)
       AND A.IMP_UUID = IN_VAR_UUID;

    MERGE INTO MM_SW_SUPPLIER_GROUP_IMP A
    USING (SELECT C.SUPPLIER_NO,DD.GROUP_NAME
             FROM MM_SW_SUPPLIER_GROUP DD 
             LEFT JOIN MM_SW_SUP_GROUP_MEMBERS C 
              ON DD.GROUP_ID=C.GROUP_ID,MM_SW_SUPPLIER_GROUP_IMP B
            WHERE B.check_result = 1
              AND B.imp_uuid = in_var_uuid
              AND B.SUPPLIER_NO = C.SUPPLIER_NO
              AND B.GROUP_NAME = DD.GROUP_NAME
              ) D
    ON (A.SUPPLIER_NO = D.SUPPLIER_NO AND A.GROUP_NAME = D.GROUP_NAME AND A.IMP_UUID = in_var_uuid)
    WHEN MATCHED THEN
      UPDATE
         SET A.CHECK_RESULT = 0,
             A.OPE_TYPE   = 'U',
             A.CHECK_INFO = A.CHECK_INFO || '数据已存在;';
             
                  --EXCEL是否重复
    update MM_SW_SUPPLIER_GROUP_IMP siglt
       set siglt.check_result = 0,
           siglt.ope_type = 'U',
           siglt.check_info  = (nvl(siglt.check_info, '') || 'Excel数据重复;')
     where siglt.id in
           (select distinct T.ID
              from MM_SW_SUPPLIER_GROUP_IMP t,
                   (select supplier_no,
                           Group_Name
                      from (select count(*) count,
                                   siglt.supplier_no,
                                   siglt.Group_Name
                              from MM_SW_SUPPLIER_GROUP_IMP siglt
                             where siglt.imp_uuid = in_var_uuid
                               and siglt.import_status = '0'
                             group by  siglt.supplier_no,
                                       siglt.Group_Name
                                       )
                     where count > 1) b
             where t.supplier_no = b.supplier_no
               and t.Group_Name = B.Group_Name
               );
               
    OUT_ERROR_FLAG    := '0';
    out_OUT_ERROR_MSG := '';


  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      v_err_num := sqlcode;
      v_err_msg := substr(sqlerrm || dbms_utility.format_error_backtrace(),
                          1,
                          200);
      INSERT INTO MM_PUB_PRO_ERROR
        (ID, ALERT_TYPE, ERROR_NAME, ERROR_DESC, CREATION_DATE, KEY_NAME)
      VALUES
        (SEQ_PUB_PRO_ERROR.NEXTVAL,
         'USP_SW_SUPPLIER_GROUP_CHECK',
         v_err_num,
         v_err_msg,
         SYSDATE,
         '');
      COMMIT;
      OUT_ERROR_FLAG    := '1';
      OUT_OUT_ERROR_MSG := v_err_msg;

  END USP_SW_SUPPLIER_GROUP_CHECK;
  --***************************************************************************

end PKG_PUB_CHECK;
/

prompt
prompt Creating package body PKG_SW_CHECK
prompt ==================================
prompt
create or replace package body PKG_SW_CHECK is

  --***************************************************************************
  --存储过程名：USP_SW_SUPPLIER_GROUP_CHECK
  --功能描述：供应商信息导入校验
  --参数说明：
  --IN_VAR_UUID
  --IN_VAR_USERNAME 操作人
  --IN_VAR_OPEIP ip地址
  --OUT_ERROR_FLAG 返回错误标识
  --OUT_OUT_ERROR_MSG 返回错误信息
  --创建人员: luoxianqin
  --创建时间： 2018/09/10
  PROCEDURE USP_SW_SUPPLIER_GROUP_CHECK(IN_VAR_UUID       IN VARCHAR2,
                                   IN_VAR_USERNAME   IN VARCHAR2,
                                   IN_VAR_OPEIP      IN VARCHAR2,
                                   OUT_ERROR_FLAG    OUT VARCHAR2,
                                   OUT_OUT_ERROR_MSG OUT VARCHAR2) IS
    v_err_num VARCHAR2(16);
    v_err_msg VARCHAR2(256);
  BEGIN
    UPDATE MM_SW_SUPPLIER_GROUP_IMP A
       SET A.CHECK_RESULT = '0',
           A.CHECK_INFO   = A.CHECK_INFO || '供应商代码不存在;'
     WHERE NOT EXISTS (SELECT 1
              FROM MM_PUB_SUPPLIER B
             WHERE A.SUPPLIER_NO = B.SUPPLIER_NO)
       AND A.IMP_UUID = IN_VAR_UUID;

    MERGE INTO MM_SW_SUPPLIER_GROUP_IMP A
    USING (SELECT C.SUPPLIER_NO,DD.GROUP_NAME
             FROM MM_SW_SUPPLIER_GROUP DD LEFT JOIN MM_SW_SUP_GROUP_MEMBERS C 
             ON DD.GROUP_ID=C.GROUP_ID, MM_SW_SUPPLIER_GROUP_IMP B
            WHERE B.check_result = 1
              AND B.imp_uuid = in_var_uuid
              AND B.SUPPLIER_NO = C.SUPPLIER_NO
              AND B.GROUP_NAME = DD.GROUP_NAME
              ) D
    ON (A.SUPPLIER_NO = D.SUPPLIER_NO AND A.GROUP_NAME = D.GROUP_NAME AND A.IMP_UUID = in_var_uuid)
    WHEN MATCHED THEN
      UPDATE
         SET A.CHECK_RESULT = 0,
             A.OPE_TYPE   = 'U',
             A.CHECK_INFO = A.CHECK_INFO || '数据已存在;';

    OUT_ERROR_FLAG    := '0';
    OUT_OUT_ERROR_MSG := '';
    
        --EXCEL是否重复
    update MM_SW_SUPPLIER_GROUP_IMP siglt
       set siglt.check_result = 0,
           siglt.ope_type = 'U',
           siglt.check_info  = (nvl(siglt.check_info, '') || 'Excel数据重复;')
     where siglt.id in
           (select distinct T.ID
              from MM_SW_SUPPLIER_GROUP_IMP t,
                   (select supplier_no,
                           Group_Name
                      from (select count(*) count,
                                   siglt.supplier_no,
                                   siglt.Group_Name
                              from MM_SW_SUPPLIER_GROUP_IMP siglt
                             where siglt.imp_uuid = in_var_uuid
                               and siglt.import_status = '0'
                             group by  siglt.supplier_no,
                                       siglt.Group_Name
                                       )
                     where count > 1) b
             where t.supplier_no = b.supplier_no
               and t.Group_Name = B.Group_Name
               );

  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      v_err_num := sqlcode;
      v_err_msg := substr(sqlerrm || dbms_utility.format_error_backtrace(),
                          1,
                          200);
      INSERT INTO MM_PUB_PRO_ERROR
        (ID, ALERT_TYPE, ERROR_NAME, ERROR_DESC, CREATION_DATE, KEY_NAME)
      VALUES
        (SEQ_PUB_PRO_ERROR.NEXTVAL,
         'USP_SW_SUPPLIER_GROUP_CHECK',
         v_err_num,
         v_err_msg,
         SYSDATE,
         '');
      COMMIT;
      OUT_ERROR_FLAG    := '1';
      OUT_OUT_ERROR_MSG := v_err_msg;

  END USP_SW_SUPPLIER_GROUP_CHECK;
  --***************************************************************************

end PKG_SW_CHECK;
/

prompt
prompt Creating type body MY_WM_CONCAT
prompt ===============================
prompt
CREATE OR REPLACE TYPE BODY "MY_WM_CONCAT" as
   static Function ODCIAGGREGATEINITIALIZE (INIT IN OUT MY_wm_concat)
      return NUMBER as
      begin
          init := MY_wm_concat('', ',', 'asc', MYARRAY());
          return ODCICONST.Success;
        END;
   member Function ODCIAGGREGATEITERATE (SELF IN OUT MY_wm_concat,COLVALUE IN VARCHAR2)
      return NUMBER as
      tempStr varchar(500);

          extendStr varchar(500);

          deStr varchar(100);

          deLen int default 0;

          segStr varchar(500);

          --定义一个二维数组
          TYPE varArry IS VARRAY(2) OF VARCHAR2(200);

          tempArry varArry := varArry('', '');

        begin

          if instr(colValue, ' ', 1) > 0 then
            tempStr := substr(colValue, 1, instr(colValue, ' ', 1) - 1);
          else
            tempStr := colValue;
          end if;

          --排序和分隔符
          extendStr := REPLACE(colValue, tempStr || ' ');

          if instr(extendStr, ' ', 1) > 0 then

            tempArry(1) := substr(extendStr, 1, instr(extendStr, ' ', 1) - 1);

            tempArry(2) := substr(extendStr, instr(extendStr, ' ', 1));

            for i in 1 .. tempArry.count loop
              -- 获取分隔符
              if (tempArry(i) is not null) and
                 (instr(tempArry(i), 'delimiter=>') > 0) THEN

                deStr := 'delimiter=>';

                deLen := length(deStr);

                segStr := substr(trim(tempArry(i)),
                                 instr(trim(tempArry(i)), deStr) + deLen);

                self.delimiter := SUBSTR(segStr, 1, instr(segStr, ';', -1) - 1);
              END IF;

              -- 获取排序字符串
              if tempArry(i) is not null and
                 (instr(tempArry(i), 'orderby=>') > 0) THEN

                deStr := 'orderby=>';

                deLen := length(deStr);

                segStr := substr(trim(tempArry(i)),
                                 instr(trim(tempArry(i)), deStr) + deLen);

                self.orderby := SUBSTR(segStr, 1, instr(segStr, ';', -1) - 1);

              END IF;

            end loop;

          end if;

          -- 存放入数组
          self.strArray.extend;

          self.strArray(self.strArray.count) := tempStr;

          return ODCICONST.Success;
        END;
   member Function ODCIAGGREGATEMERGE (SELF IN OUT MY_wm_concat,NEXT MY_wm_concat)
      return NUMBER as
      begin

          -- 将next数组中元素全部放入self指针对应的数组中
          for i in 1 .. next.strArray.count loop

            self.strArray.extend;

            self.strArray(self.strArray.count) := next.strArray(i);

          end loop;

          return ODCICONST.Success;
        END;
   member Function ODCIAGGREGATETERMINATE (SELF IN MY_wm_concat,RETURNVALUE OUT VARCHAR2,FLAGS IN NUMBER)
      return NUMBER as
      temp_rtnValue varchar2(32767);

        BEGIN
          -- 排序
          if INSTR(self.orderby, 'desc') > 0 THEN

            for x in (select column_value
                        from Table(self.strArray)
                       order by 1 DESC) loop

              temp_rtnValue := temp_rtnValue || self.delimiter || x.column_value;

            end loop;
          ELSE
            for x in (select column_value from Table(self.strArray) order by 1 ASC) loop

              temp_rtnValue := temp_rtnValue || self.delimiter || x.column_value;

            end loop;

          END IF;

          returnValue := ltrim(temp_rtnValue, self.delimiter);

          return ODCICONST.Success;
        END;
end;
/


spool off
