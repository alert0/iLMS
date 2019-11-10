/*
 * Simplified Chinese translation
 * By DavidHu
 * 09 April 2007
 */
/******************************************************************************************************/
l_language = "语言";
l_userName = "用户名";
l_password = "密码";
l_welcome = "欢迎您";
l_login = "登录";
l_clear = "清空";
l_waitMsg = "请稍候...";
l_select='请选择';
l_add='新增';
l_modify='修改';
l_delete='删除';
l_userList='用户一览';
l_userId='用户Id';

Ext.UpdateManager.defaults.indicatorText = '<div class="loading-indicator">加载中...</div>';

if(Ext.View){
   Ext.View.prototype.emptyText = "";
}

if(Ext.grid.GridPanel){
   Ext.grid.GridPanel.prototype.ddText = "{0} 选择行";
}

if(Ext.TabPanelItem){
   Ext.TabPanelItem.prototype.closeText = "关闭";
}

if(Ext.form.Field){
   Ext.form.Field.prototype.invalidText = "输入值非法";
}
if(Ext.form.CheckboxGroup){
	   Ext.form.CheckboxGroup.prototype.blankText = "请至少选择一个。";
	}
Date.monthNames = [
   "1月",
   "2月",
   "3月",
   "4月",
   "5月",
   "6月",
   "7月",
   "8月",
   "9月",
   "10月",
   "11月",
   "12月"
];

Date.dayNames = [
   "日",
   "一",
   "二",
   "三",
   "四",
   "五",
   "六"
];

if(Ext.MessageBox){
   Ext.MessageBox.buttonText = {
      ok     : "确定",
      cancel : "取消",
      yes    : "是",
      no     : "否"
   };
}

if(Ext.util.Format){
   Ext.util.Format.date = function(v, format){
      if(!v) return "";
      if(!(v instanceof Date)) v = new Date(Date.parse(v));
      return v.dateFormat(format || "y年m月d日");
   };
}

if(Ext.DatePicker){
   Ext.apply(Ext.DatePicker.prototype, {
      todayText         : "今天",
      minText           : "日期在最小日期之前",
      maxText           : "日期在最大日期之后",
      disabledDaysText  : "",
      disabledDatesText : "",
      monthNames        : Date.monthNames,
      dayNames          : Date.dayNames,
      nextText          : '下月 (Control+Right)',
      prevText          : '上月 (Control+Left)',
      monthYearText     : '选择一个月 (Control+Up/Down 来改变年)',
      todayTip          : "{0} (空格键选择)",
      format            : "y年m月d日",
      okText            : "确定",
      cancelText        : "取消"
   });
}

if(Ext.PagingToolbar){
   Ext.apply(Ext.PagingToolbar.prototype, {
      beforePageText : "第",
      afterPageText  : "页　共 {0} 页",
      firstText      : "第一页",
      prevText       : "前一页",
      nextText       : "下一页",
      lastText       : "最后页",
      refreshText    : "刷新",
      displayMsg     : "显示 {0} - {1}，共 {2} 条",
      emptyMsg       : '<h3><font color=red>没有数据需要显示！</font></h3>'
   });
}

if(Ext.form.TextField){
   Ext.apply(Ext.form.TextField.prototype, {
      minLengthText : "该输入项的最小长度是 {0}",
      maxLengthText : "该输入项的最大长度是 {0}",
      blankText     : "该输入项为必输项",
      regexText     : "",
      emptyText     : null
   });
}

if(Ext.form.NumberField){
   Ext.apply(Ext.form.NumberField.prototype, {
      minText : "该输入项的最小值是 {0}",
      maxText : "该输入项的最大值是 {0}",
      nanText : "{0} 不是有效数值"
   });
}

if(Ext.form.DateField){
   Ext.apply(Ext.form.DateField.prototype, {
      disabledDaysText  : "禁用",
      disabledDatesText : "禁用",
      minText           : "该输入项的日期必须在 {0} 之后",
      maxText           : "该输入项的日期必须在 {0} 之前",
      invalidText       : "{0} 是无效的日期 - 必须符合格式： {1}",
      format            : "y年m月d日"
   });
}

if(Ext.form.ComboBox){
   Ext.apply(Ext.form.ComboBox.prototype, {
      loadingText       : "加载...",
      valueNotFoundText : undefined
   });
}

if(Ext.form.VTypes){
   Ext.apply(Ext.form.VTypes, {
      emailText    : '该输入项必须是电子邮件地址，格式如： "user@domain.com"',
      urlText      : '该输入项必须是URL地址，格式如： "http:/'+'/www.domain.com"',
      alphaText    : '该输入项只能包含字符和_',
      alphanumText : '该输入项只能包含字符,数字和_'
   });
}

if(Ext.grid.GridView){
   Ext.apply(Ext.grid.GridView.prototype, {
      sortAscText  : "正序",
      sortDescText : "逆序",
      lockText     : "锁列",
      unlockText   : "解锁列",
      columnsText  : "列"
   });
}

if(Ext.grid.PropertyColumnModel){
   Ext.apply(Ext.grid.PropertyColumnModel.prototype, {
      nameText   : "名称",
      valueText  : "值",
      dateFormat : "y年m月d日"
   });
}

if(Ext.layout.BorderLayout && Ext.layout.BorderLayout.SplitRegion){
   Ext.apply(Ext.layout.BorderLayout.SplitRegion.prototype, {
      splitTip            : "拖动来改变尺寸.",
      collapsibleSplitTip : "拖动来改变尺寸. 双击隐藏."
   });
}
/******************************************************************************************************/
/**
 * Simplified Chinese translation
 * By e-PPS
 * 10 June 2010
 */
tip_export_succ = '导出成功。';
tip_export_nodata = '没有可导出的数据，请先检索。';
l_language = "语言";
l_userName = "用户名";
l_password = "密码";
l_blankPassword = "密　码";
l_welcome = "欢迎您";
l_login = "登录";
l_search = "检索";
l_clear = "清空";
l_waitMsg = "请稍候...";
l_select = '请选择';
l_add = '新增';
l_modify = '修改';
l_delete = '删除';
l_save = "保存";
l_userList = '用户一览';
l_userId = '用户Id';
l_close = "关闭";
l_import = "导入";
l_dataCheck="数据校验";
l_aogFactory_lable="到货工厂";
l_aogFactory_value = "GAMC01";
l_supplierId="供应商代码";
l_supplierName="供应商名称";
l_unloadPort="卸货口";
l_unloadPalce="卸货场所";
l_factoryId="厂家代码";
l_factoryName="厂家名称";
l_partId="零件编号";
l_partNameCn="零件名称";
l_partUnit="零件单位";
l_noName="未命名";
l_homePage="主页";
l_userType="身份类型";
l_validateCode="验证码";
l_kanBuQing="看不清";
l_refreshValidateCode="重新获得验证码";
l_pleaseWait="请稍等";


/******************* 系统管理 start**********************/
//******* 用户信息录入(M01)页面开始 *******//
l_M01 = "用户信息录入";
l_loginName_M01 = "登录名";
l_password_M01 = "密码";
l_mobile_M01 = "手机 ";
l_comfirmPassword_M01 = "确认密码";
l_telephone_M01 = "电话";
l_name_M01 = "姓名";
l_fax_M01 = "传真";
l_department_M01 = "部门";
l_email_M01 = "电子邮箱";
l_role_M01 = "角色";
msg_pwd_wrong_M01 = "两次输入的密码不同";
//******* 用户信息录入(M01)页面结束 *******//

//******* 用户信息管理(M02)页面开始 *******//
l_M02 = "用户信息管理";
l_userInfo_M02 = "查看用户信息";
l_userUpdate_M02 = "修改用户信息";
l_loginName_M02 = "登录名";
l_userName_M02 = "用户名";
l_password_M02 = "密码";
l_comfirmPassword_M02 = "确认密码";
l_name_M02 = "姓名";
l_role_M02 = "角色";
l_department_M02 = "部门";
l_userList_M02 = '用户信息一览';
l_telephone_M02 = '联系电话';
l_mobile_M02 = '手机';
l_email_M02 = "电子邮箱";
l_userStatus_M02 = '用户状态';
l_loginStatus_M02 = '登录状态';
l_loginNum_M02 = '登录次数';
l_fax_M02 = '传真';
msg_pwd_min_M02 = '该输入项的最小长度为6';
msg_pwd_max_M02 = '该输入项的最大长度为40';
msg_pwd_wrong_M02 = "两次输入的密码不同";
msg_delete_M02 = "不能删除自己!";
msg_disable_M02 = "不能禁用自己!";
msg_alreadyDisable_M02 = "该用户已处于禁用状态";
msg_alreadyUsable_M02 = "该用户已处于可用状态";
//******* 用户信息管理(M02)页面结束 *******//

//******* 角色权限管理(M03)页面开始 *******//
l_M03 = "角色权限管理";
l_addRole_M03 = "新增角色信息";
l_role_M03 = "角色";
l_department_M03 = "部门";
l_searchForm_M03 = "查询条件";
l_roleInfo_M03 = "角色信息一览";
l_roleDesc_M03 = "角色说明";
l_roleStatus_M03 = "状态";
l_allRole_M03 = "所有权限";
l_roleModify_M03 = "权限配置";
l_roleModifyWin_M03 = "修改角色信息";
msg_infoTitle_M03 = "提示";
msg_modifyInfo_M03 = "确认要保存权限修改吗?"	;
msg_sysRole_M03 = "系统业务用角色不可删除";
msg_usedRole_M03 = "对不起,该角色有用户在使用,不可删除!";
msg_modifySuccess_M03 = "权限修改成功!";
l_quanXianMoKuai_M03 = "权限模块";
l_huaMianMing_M03 = "画面名";

//******* 角色权限管理(M03)页面结束 *******//

//******* 部门信息录入(M04)页面开始 *******//
l_M04 = "部门信息录入";
l_deptName_M04 = "部门名";
l_deptManager_M04 = "部门负责人";
l_deptTel_M04 = "部门电话";
l_fax_M04 = "部门传真";
l_deptMemo_M04 = "部门描述";
//******* 部门信息录入(M04)页面结束 *******//

//******* 部门信息管理(M05)页面开始 *******//
l_M05 = "部门信息管理";
l_deptModify_M05 = "修改部门信息";
l_deptName_M05 = "部门名";
l_deptTel_M05 = "部门电话";
l_deptManager_M05 = "部门负责人";
l_managerTel_M05 = "负责人电话";
l_deptFax_M05 = "部门传真";
l_deptMemo_M05 = "部门描述";
l_deptInfo_M05 = "部门信息一览";
msg_infoTitle_M05 = "提示";
msg_delSure_M05 = "您确定要删除吗？";
msg_delSuccess_M05 = "删除执行成功";
msg_delFailure_M05 = "该部门还有用户存在，不能删除";
//******* 部门信息管理(M05)页面结束 *******//
//******* 公告信息录入(M06)页面开始 *******//
l_M06 = "公告信息录入";
//******* 公告信息录入(M06)页面结束 *******//
//******* 公告信息管理(M07)页面开始 *******//
l_M07 = "公告信息管理";
l_M07 = "公告信息查看";
l_M073 = "公告信息一览";
l_M071 = "公告信息发布";
l_M072 = "公告信息修改";
l_TITLE_M07 = "公告标题";
l_ENTRYDATE_M07 = "公告发布日期";
l_INFOTITLE_M07 = "公告标题";
l_DATA_M07 = "发布日期";
l_NOTICER_M07 = "发布人";
l_ENTRYLIMIT_M07 = "公告有效期";
l_FILENO_M07 = "编号";
l_FILENAME_M07 = "附件名称";
l_MESSAGE_M07 = "消息内容";
l_MESSAGEALERT_M07 = "请选择一个文件上传...";
l_ABOUTIT_M07 = "公告附件";
l_VIEW_M07 = "浏览";
l_ALERTWRONG_M07 = "附件只能上传pdf|xls|doc|rar类型文档！";
l_ALERTWRONG_M06 = "注:附件最大不超过5M";
l_M07 = "公告信息管理";
//******* 公告信息管理(M07)页面结束 *******//
/******************* 系统管理 end**********************/

/******************* 基础主数据维护 start**********************/
//******* 卸货口检索(B01)页面开始 *******//
l_workCenter_warn_B01="工作中心不能为空";
l_arrivalWarehouse_warn_B01="到货仓库不能为空";
l_dischargePort_warn_B01="卸货口不能为空";
l_B01 = "卸货口检索";
l_workCenter_B01 = "工作中心";
l_dischargePort_B01 = "卸货口";
l_isDefaulf_B01 = "是否是默认卸货口";
l_regexText_B01 = "该输入项只能英文和数字";
l_arrivalId_B01 = "到货工厂";
l_arrivalWarehouse_B01 = "到货仓库";
l_dischargePlace_B01 = "卸货场所";
l_sortPlace_B01 = "分拣区";
l_isDefaultUnloadPort_B01 = "是否是默认卸货口";
l_dischargeDataView_B01 = '卸货口数据一览';
l_dischargeport_modify_B01 = "卸货口数据修改";
l_dischargeport_new_B01 = "卸货口数据新增";
l_search_B01 = "检索";
l_save_B01 = "保存";
l_clear_B01 = "关闭";
l_arrivalPo_B01 = "到货工厂";
l_delate_B01 = "你是否要删除?";
l_theUnloadPortIsInUse_B01 = "该卸货口正在被使用不能删除！";
l_infoDelFailure_B01 = "信息删除失败";
l_isDefaulfTime_B01 = "是默认工作时间";
//******* 卸货口检索(B01)页面结束 *******//

//******* 需求来源检索(B04)页面开始 *******//
l_B04="需求来源检索";
l_unloadPort_B04="卸货口";
l_demandSrcDataView_B04="需求来源数据一览";
l_unloadPort_B04="卸货口";
l_unloadPortName_B04="卸货场所";
l_frequencyDaily_B04="每日趟次";
error_MaxValueText_B04="该输入项最大值为999";
win_demandSrc_edit_B04="修改需求来源信息";
win_demandSrc_add_B04="新增需求来源信息";

//******* 需求来源检索(B04)页面结束 *******//

//******* 需求来源查看(B05)页面开始 *******//
//******* 需求来源查看(B05)页面结束 *******//

//******* 需求来源添加(B06)页面开始*******//
//******* 需求来源添加(B06)页面结束*******//

//******* 需求来源修改(B07)页面开始 *******//
//******* 需求来源修改(B07)页面结束 *******//

//******* 计算范围检索(B08)页面开始 *******//
l_supplierCode_B08="供应商代码";
l_factoryCode_B08="厂家代码";
l_unloadPort_B08="卸货口";
l_calcScopeMaindataAdd_B08="计算范围主数据添加";
l_day_B08="日";
l_week_B08="周";
l_aogFactory_B08="到货工厂";
l_span_unit_B08="计算范围结束点单位";
l_span_quantity_B08="计算范围结束点";
l_time_B08 = "计算范围结束点时间";
l_cal_maindata_modify_B08="计算范围主数据修改";
l_cal_maindata_inspect_B08="计算范围主数据查看";
l_cal_maindata_out_view_B08="计算范围主数据一览";
l_cal_maindata_insert_B08="计算范围主数据添加";
l_cal_maindata_query_B08="计算范围主数据检索";
//******* 计算范围检索(B08)页面结束 *******//

//******* 计算范围添加(B09)页面开始 *******//
l_supplierCode_B09="供应商代码";
l_factoryCode_B09="厂家代码";
l_unloadPort_B09="卸货口";
l_aogFactory_B09="到货工厂";
l_span_unit_B09="计算范围结束点单位";
l_span_quantity_B09="计算范围结束点";
l_time_B09 = "计算范围结束点时间";
l_cal_maindata_insert_B09="计算范围主数据添加";
//******* 计算范围添加(B09)页面结束 *******//

// ******** 生产提前期维护（B10）开始*******//
l_workCenter_B10 = "工作中心";
l_produceAdvanceTime_B10 = "生产提前期";
l_advence_B10="生产提前期信息一览";
l_max_B10 = " 输入的最大值超过了9999.9";
/****************VMI警告****************************/
vmiHasNoLocation= "该零件不存在黄绿点，请确认！";
vmiNumTooSmall = " 输入的发货数量太小！";
vmiNumTooLarge= " 输入的发货数量大于订购数量！";

l_max_B10 = " 输入的最大值超过了9999.9";

// ******** 生产提前期维护（B10）结束*******//

/******************* 基础主数据维护 end**********************/

/******************* 生产计划管理 start**********************/
//******* 工作时间检索(P01)页面开始 *******//
l_P01 = "工作时间检索";
l_workCenter_P01 = "工作中心";
l_order_P01 = "班次";
l_search_P01 = "检索";
l_clear_P01 = "关闭";
l_week_P01 = "日期（周）";
l_orderClass_P01 = "班次";
l_workTime_P01 = "工作时间";
l_workTime1_P01 = "第一班工作时间";
l_workTime2_P01 = "第二班工作时间";
l_workTime3_P01 = "第三班工作时间";
l_workTime4_P01 = "第四班工作时间";
l_startTime_P01 = "起始时间";
l_endTime_P01 = "结束时间";
l_worktimeView_P01 = "工作时间信息一览";
l_save_P01 = "保存";
l_worktime_modify_P01 = "工作时间修改";
error_MaxValueText_P01 = "输入数字必须小于100";
l_worktime_new_P01 = "工作时间添加";
l_insertSuccess_P01 = "插入工作时间成功";
//******* 工作时间检索(P01)页面结束 *******//

//******* 新增工作时间数据(P02)页面开始 *******//
l_worktimeNotAllowNull_P02 = "工作时间不能为空！";
l_msg1_P02 = "第一班工作时间结束时间不能早于第一班工作时间开始时间！";
l_msg2_P02 = "第二班工作时间开始时间不能早于第一班工作时间结束时间！";
l_msg3_P02 = "第二班工作时间结束时间不能早于第二班工作时间开始时间！";
l_msg4_P02 = "第三班工作时间开始时间不能早于第二班工作时间结束时间！";
l_msg5_P02 = "第三班工作时间结束时间不能早于第三班工作时间开始时间！";
l_msg6_P02 = "第四班工作时间开始时间不能早于第三班工作时间结束时间！";
l_msg7_P02 = "第四班工作时间结束时间不能早于第四班工作时间开始时间！";
l_msg8_P02 = "工作时间结束时间不能早于工作时间开始时间！";
//******* 新增工作时间数据(P02)页面结束 *******//

//******* 生产计划调整(P04)页面开始 *******//
//******* 生产计划调整(P04)页面结束 *******//

//******* 整车日排序汇总(P05)页面开始 *******//
l_P05="整车日排序汇总";
l_workCenter_P05 = "工作中心";
l_date_P05 = "日期";
l_query_P05 = "检索";
l_mtoId_P05 = '整车代码';
l_num_P05 = '数量';
l_yilan_P05="整车日排序汇总一览";
l_chakan_P05 = '整车日排序汇总查看';
//******* 整车日排序汇总(P05)页面结束 *******//

//******* 生产订单管理(P06)页面开始 *******//
l_P06="生产订单管理";
l_query_P06 = "检索";
l_clear_P06 = "清空";
l_queryyilan_Q07="估计材料";
l_operate_Q07="操作";
l_yes_Q07="是";
l_gujicailiao_Q07="估计材料(生产订单)";
l_production_P06 = "生产订单";
l_producjianhao_P06 = "生产件号";
l_producdate_P06 = "生产起始日期";
l_producname_P06 = "生产件名称";
l_ordernum_P06="订单数量";
l_jihuajiaohuo_P06="计划交货日期";
l_cangku_P06="仓库";
l_back_P06="返工单";
l_status_P06="订单状态";
l_calstatus_P06="计算状态";
l_partOrderMainDataDetail_P06 = '生产订单一览';
//******* 生产订单管理(P06)页面结束 *******//

//******* 估计材料（生产订单）(P07)页面开始 *******//
l_P07 = '估计材料（生产订单）';
l_production_P07 = "生产订单";
l_producjianhao_P07 = "生产件号";
l_producdate_P07 = "生产起始日期";
l_producname_P07 = "生产件名称";
l_ordernum_P07="订单数量";
l_jihuajiaohuo_P07="计划交货日期";
l_cangku_P07="仓库";
l_back_P07="返工单";
l_status_P07="订单状态";
l_failure_P07="对不起，数据加载失败";
l_compxuqiu_P07="估计材料";
l_location_P07 ="位置";
l_sequence_P07 ="序号";
l_partNumber_P07="零件编号";
l_partNameCN_P07 ="零件名称";
l_partSimpleName_P07 = '零件简号';
l_centerName_P07 ="工序落点";
l_planNum_P07 = '计划数量';
l_partUnit_P07 = '零件单位';
l_lblDaoHuoShCode_P07="到货工厂";
l_unLoadPort_P07 = '卸货口';
l_fenJianQu_P07 = '分拣区';
l_unloadPalce_P07="卸货场所";
l_jiSuanStatus_P07="计算状态";
//******* 估计材料（生产订单）(P07)页面结束 *******//
/******************* 生产计划管理 end**********************/

/******************* 订购主数据管理 start**********************/
//******* 零件订购主数据检索(D01)页面开始 *******//
l_D01 = '零件订购主数据检索';
l_partCode_D01 = '零件编号';
l_partSimpleName_D01 = '零件简号';
l_supplierCode_D01 = '供应商代码';
l_partName_D01 = '零件名称';
l_partUnit_D01 = '零件单位';
l_factoryCode_D01 = '厂家代码';
l_factoryName_D01 = '厂家名称';
l_aogFactory_D01 = '到货工厂';
l_unLoadPort_D01 = '卸货口';
l_itemCalculateNum_D01 = '提前台套数';
l_itemFirstSortId_D01 = '初始SORTID';
l_packQuantity_D01 = '标准包装';
l_orderPackQuantity_D01 = '订购包装';
l_dataOrigin_D01 = '数据来源';
l_itemCalculateDiffType_D01 = '差异处理类型';
l_partSearch_D01 = '按零件查询';
l_supplierSearch_D01 = '按供应商查询';
l_partOrderMainDataDetail_D01 = '零件订购主数据一览';
//******* 零件订购主数据检索(D01)页面结束 *******//

//******* 零件订购主数据查看(D02)页面开始 *******//
l_englishName_D02 = '英文名称';
l_partSingalWight_D02 = '零件单重';
l_package_D02 = '包装数';
l_orderPackage_D02 = '订购包装';
l_smallNum_D02 = '最小订货数量';
l_packageId_D02 = '包装规格ID';
l_jnCarName_D02 = '供应商名称';
l_factoryName_D02 = '厂家名称';
l_qsFlg_D02 = '物流标识（取/送）';
l_aogName_D02 = '到货商名称';
l_unLoadPortName_D02 = '卸货场所';
l_status_D02 = '状态';
l_doFlgStatus_D02 = '处理状态';
l_firstSelect_D02 = '首选';
l_priorityLevel_D02 = '优先级';
l_supplyRate_D02 = '供货比率';
l_purchaseDept_D02 = '采购部门';
l_verify_D02 = '检验';
l_effectiveDate_D02 = '生效日期';
l_expiryDate_D02 = '失效日期';
l_facInForwardTime_D02 = '厂内提前期';
l_facInForwardTimeUnit_D02 = '厂内提前期单位';

l_pProgress_D02 = '包装确认进度';
l_pStandardCode_D02 = '包装规格代码';
l_pType_D02 = '包装类型';
l_pNum_D02 = '包装数';
l_pLength_D02 = '包装长';
l_pWidth_D02 = '包装宽';
l_pHeight_D02 = '包装高';
l_pUnitVolume_D02 = '包装单元体积';
l_partNetWeight_D02 = '零件净重';
l_packetNetWeight_D02 = '包装净重';
l_pUnitWeight_D02 = '包装单元重量';
l_packetRemark_D02 = '包装备注';

l_partIdCheck_D02 = '该输入项只能包含字符,数字和-';

l_close_D02 = '关闭';
l_partData_D02 = '零件数据';
l_buyData_D02 = '采购数据';
l_drData_D02 = 'D-RANGE数据';
l_lrData_D02 = 'L-RANGE数据';
l_wlData_D02 = '物流数据';
l_partOrderMainDataView_D02 = '零件订购主数据查看';
l_partOrderMainDataAdd_D02 = '零件订购主数据新增';
l_day_D02 = '天';
l_hour_D02 = '小时';
//******* 零件订购主数据查看(D02)页面结束 *******//

//******* 零件订购主数据修改(D17)页面开始 *******//
l_partOrderMainDataModify_D17 = '零件订购主数据修改';
l_updateConfirm_D17 = '您确定要保存吗？';
l_updateSuccess_D17 = '零件订购主数据修改成功！';
//******* 零件订购主数据修改(D17)页面结束 *******//

//******* 零件订购主数据新增(D18)页面开始 *******//
l_facInForwardTimeUnit_D18 = '厂内提前期单位';
l_clearForm_D18 = '清空';
l_txtQhValidate_D18 = '物流标识（取/送）的值只能为GAM！';
l_dateValidate_D18 = '失效日期必须大于或等于生效日期！';
//******* 零件订购主数据新增(D18)页面结束 *******//

//******* 供应商主数据检索(D03)页面开始 *******//
l_supplierCode_D03 = '供应商代码';
l_supplierName_D03 = '供应商名称';
l_factoryCode_D03 = '厂家代码';
l_factoryName_D03 = '厂家名称';
l_aogFactory_D03 = '到货工厂';
l_unLoadPort_D03 = '卸货口';
l_supplierName_D03 = '供应商名称';
l_factoryName_D03 = '厂家名称';
l_aogName_D03 = '到货商名称';
l_supplierMainDataView_D03 = '供应商主数据一览';
l_supplierMainDataSearch_D03 = '供应商主数据检索';
l_calculateType_D03 = '计算类型';
l_calculateNum_D03 = '台套数';
l_calculateNumNotNullOrZero_D03 = "台套数不能为空或零。";
//******* 供应商主数据检索(D03)页面结束 *******//

//******* 供应商主数据修改(D04)页面开始 *******//
l_readyTime_D04 = '准备时间';
l_supplyTime_D04 = '供应时间';
l_safeTime_D04 = '安全时间';
l_unLoadPortName_D04 = '卸货场所';
l_supplierInfoEdit_D04 = '供应商信息修改';
//******* 供应商主数据查看(D04)页面结束 *******//

//******* 积载率提高检索(D05)页面开始 *******//
l_D05="积载率提高检索";
l_supplierId_D05="供应商代码";
l_unloadPort_D05="卸货口";
l_groupId_D05="组合代码";
l_factoryId_D05="厂家代码";
l_aogFactory_D05="到货工厂";
l_loadBox_D05="装载箱数";
l_accumulateAriseView_D05="积载率提高一览";
tab_accumulateAriseAdd_D05="积载率提高添加";
tab_accumulateAriseView_D05="积载率提高查看";
tab_accumulateAriseModify_D05="积载率提高修改";
//******* 积载率提高检索(D05)页面结束 *******//

//******* 积载率提高查看(D06)页面开始 *******//
l_D06 = "积载率提高信息";
l_supplierId_D06="供应商代码";
l_unloadPort_D06="卸货口";
l_groupId_D06="组合代码";
l_factoryId_D06="厂家代码";
l_aogFactory_D06="到货工厂";
l_loadBox_D06="装载箱数";
l_componentView_D06="同组零件一览";
l_partId_D06="零件编号";
l_partNameCn_D06="零件名称";
tab_accumulateAriseView_D06="积载率提高查看";
tab_accumulateAriseModify_D06="积载率提高修改";
l_baseInfo_D06="基本信息";
//******* 积载率提高查看(D06)页面结束 *******//

//******* 积载率提高添加(D07)页面开始 *******//
l_D07 = "请先输入装载分组基本信息";
l_supplierId_D07="供应商代码";
l_supplierName_D07="供应商名称";
l_unloadPort_D07="卸货口";
l_unloadPalce_D07="卸货场所";
l_groupId_D07="组合代码";
l_factoryId_D07="厂家代码";
l_factoryName_D07="厂家名称";
l_aogFactory_D07="到货工厂";
l_loadBox_D07="装载箱数";
error_MaxValueText_D07="该输入项最大值为9999";
l_componentView_D07="同组零件一览";
l_componentNo_D07="零件编号";
l_componentName_D07="零件名称";
l_add_D07="添加";
l_save_next_D07="保存并下一步";
tab_accumulateAriseAdd_D07="积载率提高添加";
tab_accumulateAriseModify_D07="积载率提高修改";
//******* 积载率提高添加(D07)页面结束 *******//

//******* 积载率提高修改(D08)页面开始 *******//
l_D08 = "积载率提高修改";
l_supplierId_D08="供应商代码";
l_unloadPort_D08="卸货口";
l_groupId_D08="组合代码";
l_factoryId_D08="厂家代码";
l_aogFactory_D08="到货工厂";
l_loadBox_D08="装载箱数";
l_componentView_D08="同组零件一览";
l_partId_D08="零件编号";
l_partNameCn_D08="零件名称";
l_add_D08="添加";
tab_accumulateAriseModify_D08="积载率提高修改";
alert_NoModify_D08="没有修改任何内容!";
alert_MaxValue_D08="该输入项最大值为9999";
win_addPart_D08="新增同组零件";
l_baseInfo_D08="基本信息";
//******* 积载率提高修改(D08)页面结束 *******//

//******* 加工不良基准检索(D09)页面开始  *******//
l_D09 = "加工不良基准检索";
l_partCode_D09 = "零件编号";
l_partCode_simple_D09 = "零件简号";
l_supplier_Code_D09 = "供应商代码";
l_unLoad_port_D09 = "卸货口";
l_process_main_data_view_D09 = "加工不良基准主数据一览";
l_partName_D09 = "零件名称";
l_factoryName_D09 = "厂家代码";
l_AogFactory_D09 = "到货工厂";
l_orderBox_D09 = "订货点（箱）";
l_Max_D09 = "最大量（箱）";
//******* 加工不良基准检索(D09)页面结束  *******//

//******* 加工不良基准查看(D10)页面开始  *******//
l_D10 = "加工不良基准查看";
//******* 加工不良基准查看(D10)页面结束  *******//

//******* 加工不良基准添加(D11)页面开始  *******//
l_D11 = "加工不良基准添加";
l_message_D11 = "请输入加工不良设定信息：";
l_messageDescript_D11 = "不特别指定时，零件编号，供应商代码，厂家代码，卸货口代码可以输入*。";
//******* 加工不良基准添加(D11)页面结束  *******//

//******* 加工不良基准修改(D12)页面开始  *******//
l_D12 = "加工不良基准修改";
l_save_D12 = "保存";
//******* 加工不良基准修改(D12)页面结束  *******//

//******* 物料清单检索(D13)页面开始  *******//
//******* 物料清单检索(D13)页面结束  *******//

//******* 物料清单查看(D14)页面开始  *******//
//******* 物料清单查看(D14)页面结束  *******//

//******* 物料清单修改(D15)页面开始  *******//
//******* 物料清单修改(D15)页面结束  *******//
/******************* 订购主数据管理 end**********************/

/******************* 订购时间线管理 start**********************/
//******* 订单时间线检索 (T01)页面开始  *******//
l_orderLineType_T01="订单时间线类型";
/***yanqiang  start***/
//订单时间线管理
l_supplierId_T01 = "供应商代码";
l_unloadPort_T01 = "卸货口";
l_orderNo_T01 = "物流订单号";
l_dataSrc_T01 = "数据来源";
l_search_T01 = "检索";
l_timeLineDataView_T01 = "订单时间线数据一览";
l_factoryId_T01 = "厂家代码";
l_factoryName_T01 = "厂家名称";
l_aogFactory_T01 = "到货工厂";
l_unloadPort_T01 = "卸货口";
l_lblUnit_T01="TL周期";
l_logisticsOrder_T01 = "物流订单号";
l_routeName_T01 = "路线名";
l_routeNum_T01 = "路线趟次";
l_routeFrequency_T01 = "路线频次";
l_routeArriveDate_T01 = "路线到货日";
l_travelSeq_T01 = "行驶SEQ";
l_logisticsPoint_T01 = "物流据点名称";
l_trackPoint_T01 = "TrackPoint";
l_isArriveDelivery_T01 = "A/D";
l_planTime_T01 = "计划时间";
l_T01 = "订单时间线检索";
error_MaxValueText_T01 = "输入数字必须小于100";
l_save_T01 = "保存";
l_clear_T01 = "关闭";
l_timeLine_modify_T01 = "订单时间线修改";
l_planTime_T01 = "计划时间";
l_routeArriveDate_T01 = "路线到货日";
l_canNotModifyDate_T01 = "不能修改计划时间的日期部分！";
l_tipTitle_T01 = "提示";
l_chooseOneData_T01 = "请至少选中一条数据后再进行批量删除！";
l_confirmBatchDelete_T01 = "您确定要进行批量删除吗？";
l_batchDeleteSuccess_T01 = "批量删除成功！";
l_batchDeleteFailure_T01 = "批量删除失败！";

/*yanqiang end**/
//******* 订单时间线检索 (T01)页面结束  *******//

//******* 订单时间线查看 (T02)页面开始  *******//
//******* 订单时间线查看 (T02)页面结束  *******//

//******* 订单时间线导入 (T03)页面开始  *******//
l_T03="订单时间线导入 ";
//******* 订单时间线导入 (T03)页面结束  *******//

//******* 订单时间线修改 (T04)页面开始  *******//
//******* 订单时间线修改 (T04)页面结束  *******//

//******* 简易订单时间线检索 (T05)页面开始  *******//
l_T05="简易订单时间线检索";
l_id_T05="流水号";
l_orderView_T05="简易时间线一览";
l_supplierId_T05="供应商代码";
l_supplierName_T05="供应商名称";
l_factoryId_T05="厂家代码";
l_factoryName_T05="厂家名称";
l_unLoadPort_T05="卸货口";
l_unLoadPlace_T05="卸货口名称";
l_unLoad_T05="卸货场所";
l_frequence_T05="供应商发货趟次";
l_planTime_T05="供应商发货时间";
l_recSupplierId_T05="到货工厂";
alert_MaxValue_T05="该输入项最大值为999";
alert_orderTime_T05="简易订单时间线添加";
modify_orderTime_T05="简易时间线修改";
modify_manage_T05="简易订单时间线修改";
view_manage_T05="简易订单时间线查看";
add_orderTime_T05="简易时间线添加";
error_format_T05="输入格式错误";
//******* 简易订单时间线检索 (T05)页面结束  *******//
//******* 简易订单时间线查看 (T06)页面开始  *******//
tab_orderInspect_T06="简易订单时间线查看";
l_supplierId_T06="供应商代码";
l_supplierName_T06="供应商名称";
l_factoryId_T06="厂家代码";
l_factoryName_T06="厂家名称";
l_unLoadPort_T06="卸货口";
l_unLoadPlace_T06="卸货口名称";
l_recSupplierId_T06="到货工厂";
l_orderInspect_T06="简易订单时间线查看";

l_routeNum_T06="发货趟次";
l_send_T06="发货趟次";
l_lblFromToTime_T06="适用时间段";
l_lblUnit_T06 = "匹配单位";
l_weeklyTime_T06="按周发货时间";
l_weekDay_T06="星期";
l_weekRouteNum_T06="趟次";
l_weekRouteTime_T06="趟次时间";

//******* 简易订单时间线查看 (T06)页面结束  *******//

//******* 简易订单时间线添加 (T07)页面开始  *******//
msg_error_NoInfo_T07="您没有添加任何趟次信息,请检查!";
msg_error_Repeat_T07="相同的数据已经存在,请检查";
tab_orderAdd_T07="简易订单时间线添加";
win_addRouteTime_T07="新增趟次";
win_modifyRouteTime_T07="修改趟次";
l_routeNum_T07="到货趟次";
l_send_T07="发货趟次";
l_fromToTime_T07="适用时间段";
l_cmbUnit_T07 = "匹配单位";
l_weeklyTime_T07="按周发货时间";
l_weekDay_T07="星期";
l_weekRouteNum_T07="趟次";
l_weekRouteTime_T07="趟次时间";
l_di_T07="第";
l_ciTime_T07="次时间";
l_confirmDelete_T07="确认删除吗？";
l_timeMustBigThan_T07="次时间必须大于第";

//******* 简易订单时间线添加 (T07)页面结束  *******//
//******* 简易订单时间线修改 (T08)页面开始  *******//
tab_orderModify_T08="简易订单时间线修改";
win_addRouteTime_T08="新增趟次";
l_supplierId_T08="供应商代码";
l_supplierName_T08="供应商名称";
l_factoryId_T08="厂家代码";
l_factoryName_T08="厂家名称";
l_unLoadPort_T08="卸货口";
l_unLoadPlace_T08="卸货口名称";
l_recSupplierId_T08="到货工厂";


l_routeNum_T08="发货趟次";
l_send_T08="发货趟次";
l_fromToTime_T08="适用时间段";
l_cmbUnit_T08 = "匹配单位";
l_weeklyTime_T08="按周发货时间";
l_weekDay_T08="星期";
l_weekRouteNum_T08="趟次";
l_weekRouteTime_T08="趟次时间";

//******* 简易订单时间线修改 (T08)页面结束  *******//
/******************* 订购时间线管理 end**********************/

/******************* 订购数量调整 start**********************/
//******* 手工调整 (Q01)页面开始  *******//
l_Q01="系统自动运行时间段设定";
l_cbxEffect_Q01="时间生效";
l_tfdPlanTime_Q01="净需求计算时间";
l_tfdERPOrderTime_Q01="生成ERP采购订单时间";
//******* 手工调整 (Q01)页面结束  *******//

//******* 加工不良记录检索(Q02)页面开始  *******//
l_partId_Q02="零件编号";
l_partNameCn_Q02="零件名称";
l_partName_Q02="零件简号";
l_supplierId_Q02="供应商代码";
l_supplierName_Q02="供应商名称";
l_factoryId_Q02="厂家代码";
l_factoryName_Q02="厂家名称";
l_unLoadPort_Q02="卸货口";
l_unLoadPalce_Q02="卸货场所";
l_recSupplierId_Q02="到货工厂";
l_badNum_Q02="加工不良数量";
l_partUnit_Q02="零件单位";
l_time_Q02="时间";
l_request_Q02="是否已生成需求";
l_reserch_Q02="查询条件";
l_title_Q02="加工不良记录检索";
l_modify_Q02="加工不良记录修改";
l_add_Q02="加工不良记录添加";
l_import_Q02="加工不良记录导入";
l_inspect_Q02="加工不良记录查看";
l_failure_Q02="对不起，数据加载失败";
l_eimport_Q02="Excel导入";
l_view_Q02="加工不良记录一览";
//******* 加工不良记录检索(Q02)页面结束  *******//

//******* 加工不良记录查看(Q03)页面开始  *******//
//******* 加工不良记录查看(Q03)页面结束  *******//

//******* 加工不良记录添加(Q04)页面开始  *******//
l_partId_Q04="零件编号";
l_partName_Q04="零件名称";
l_supplierId_Q04="供应商代码";
l_supplierName_Q04="供应商名称";
l_factoryId_Q04="厂家代码";
l_factoryName_Q04="厂家名称";
l_unLoadPort_Q04="卸货口";
l_unLoadPalce_Q04="卸货场所";
l_recSupplierId_Q04="到货工厂";
l_badNum_Q04="加工不良数量";
l_partUnit_Q04="零件单位";
l_time_Q04="时间";
l_title_Q04="加工不良记录添加";
//******* 加工不良记录添加(Q04)页面结束  *******//

//******* 加工不良记录修改(Q05)页面开始  *******//
//******* 加工不良记录修改(Q05)页面结束  *******//

//******* 加工不良记录导入(Q06)页面开始  *******//

l_Q06 = "加工不良记录导入";
l_defectiveFileImport_Q06 = "选择文件";
t_defectiveFileImport_Q06 = "加工不良记录导入";
error_formatError_Q06 = "格式不正确,请检查文件是否是CSV格式!";
l_confirmImport_Q06 = "您确定要导入数据吗？";
l_waitMsg_Q06 = "正在导入数据,请稍候...";
l_partId_Q06="零件编号";
l_partName_Q06="零件名称";
l_supplierId_Q06="供应商代码";
l_supplierName_Q06="供应商名称";
l_factoryId_Q06="厂家代码";
l_factoryName_Q06="厂家名称";
l_unLoadPort_Q06="卸货口";
l_unLoadPalce_Q06="卸货场所";
l_recSupplierId_Q06="到货工厂";
l_badNum_Q06="加工不良数量";
l_partUnit_Q06="零件单位";
l_time_Q06="时间";
l_title_Q06="加工不良记录添加";
l_importSuccess_Q06="数据导入成功。";
l_importFailure_Q06="数据导入失败。";
l_importRepeat_Q06="该文件您今天已经导入过一次,您确定继续导入吗?<br>如果继续导入,您之前导入的数据将被删除并不可还原!";
l_importRepeat1_Q06="您今天已经导入过该文件,并且已经生成需求<br>您不能再导入该文件了!";

//******* 加工不良记录导入(Q06)页面结束  *******//

//******* 剩余量检索(Q07)页面开始  *******//
l_Q07="剩余量检索";
l_view_Q07 = "剩余量查看";
l_modify_Q07 = "剩余量修改";
l_add_Q07 = "剩余量添加";
l_partId_Q07="零件编号";
l_partName_Q07="零件名称";
l_partProfileId_Q07 = '零件简号';
l_supplierId_Q07="供应商代码";
l_unLoadPort_Q07="卸货口";
l_factoryId_Q07="厂家代码";
l_necessaryOrderResidual_Q07 = '必要数订购剩余量';
l_defectiveOrderResidual_Q07 = '加工不良库存量';
l_planChangeResidual_Q07 = '计划变更剩余量 ';
l_queryyilan_Q07="剩余量检索一览";
//******* 剩余量检索(Q07)页面结束  *******//

//******* 剩余量查看(Q08)页面开始  *******//
l_Q08="剩余量查看";
l_partId_Q08="零件编号";
l_partName_Q08="零件名称";
l_supplierId_Q08="供应商代码";
l_supplierName_Q08="供应商名称";
l_factoryId_Q08="厂家代码";
l_factoryName_Q08="厂家名称";
l_unLoadPort_Q08="卸货口";
l_unLoadPalce_Q08="卸货场所";
l_lblDaoHuoShCode_Q08="到货工厂";
l_necessaryOrderResidual_Q08 = '必要数订购剩余量';
l_defectiveOrderResidual_Q08 = '加工不良库存量';
l_closed_Q08 = '关闭';
l_view_Q08 = "剩余量查看";
l_failure_Q08="对不起，数据加载失败";
//******* 剩余量查看(Q08)页面结束  *******//

//******* 剩余量添加(Q09)页面开始  *******//
l_Q09="剩余量添加";
l_partId_Q09="零件编号";
l_partName_Q09="零件名称";
l_supplierId_Q09="供应商代码";
l_supplierName_Q09="供应商名称";
l_factoryId_Q09="厂家代码";
l_factoryName_Q09="厂家名称";
l_unLoadPort_Q09="卸货口";
l_unLoadPalce_Q09="卸货场所";
l_lblDaoHuoShCode_Q09="到货工厂";
l_necessaryOrderResidual_Q09 = '必要数订购剩余量';
l_defectiveOrderResidual_Q09 = '加工不良库存量';
l_save_Q09 = "保存";
l_minTip_Q09 = "该输入项目最小值为-9999";
l_maxTip_Q09 = "该输入项目最大值为9999";
//******* 剩余量添加(Q09)页面结束  *******//

//******* 剩余量修改(Q10)页面开始  *******//
l_Q10="剩余量修改";
l_partId_Q10="零件编号";
l_partName_Q10="零件名称";
l_supplierId_Q10="供应商代码";
l_supplierName_Q10="供应商名称";
l_factoryId_Q10="厂家代码";
l_factoryName_Q10="厂家名称";
l_unLoadPort_Q10="卸货口";
l_unLoadPalce_Q10="卸货场所";
l_lblDaoHuoShCode_Q10="到货工厂";
l_necessaryOrderResidual_Q10 = '必要数订购剩余量';
l_defectiveOrderResidual_Q10 = '加工不良库存量';
l_save_Q10 = "保存";
l_failure_Q10="对不起，数据加载失败";
//******* 剩余量修改(Q10)页面结束  *******//

//******* 库存个数检索(Q11)页面开始  *******//
//******* 库存个数检索(Q11)页面结束  *******//

//******* 库存个数查看(Q12)页面开始  *******//
//******* 库存个数查看(Q12)页面结束  *******//

//******* 库存个数添加(Q13)页面开始  *******//
//******* 库存个数添加(Q13)页面结束  *******//

//******* 库存个数修改(Q14)页面开始  *******//
//******* 库存个数修改(Q14)页面结束  *******//

//******* 库存箱数检索(Q15)页面开始 ***//
//******* 库存箱数检索(Q15)页面结束 ***//

//******* 库存箱数查看(Q16)页面开始  *******//
//******* 库存箱数查看(Q16)页面结束  *******//

//******* 库存箱数添加(Q17)页面开始  *******//
//******* 库存箱数添加(Q17)页面结束  *******//

//******* 库存箱数修改(Q18)页面开始  *******//
//******* 库存箱数修改(Q18)页面结束  *******//

//******* 打切订购数量检索(Q19)页面开始  *******//
l_partId_Q19="零件编号";
l_partSimpleId_Q19="零件简号";
l_supplierCode_Q19="供应商代码";
l_factoryCode_Q19="厂家代码";
l_aogFactory_Q19="到货工厂";
l_unloadPort_Q19="卸货口";
l_cutoffNum_Q19="打切数量";
l_cutoffResidual_Q19="打切剩余量";
l_cutoffStartTime_Q19="打切开始时间";
l_queryCondition_Q19="查询条件";
l_cutoffOrderQuantity_outview_Q19="打切订购数量查看";
l_cutoffOrderQuantity_modify_Q19="打切订购数量修改";
l_cutoffOrderQuantity_add_Q19="打切订购数量添加";
l_cutoffOrderQuantity_List_Q19="打切订购数量一览";
l_cutoffOrderQuantity_query_Q19="打切订购数量检索";
//******* 打切订购数量检索(Q19)页面结束  *******//

//******* 打切订购数量查看(Q20页面开始  *******//
l_partId_Q20="零部件号";
l_partName_Q20="零部件名称";
l_supplierCode_Q20="供应商代码";
l_supplierName_Q20="供应商名称";
l_factoryCode_Q20="厂家代码";
l_factoryName_Q20="厂家名称";
l_unloadPortCode_Q20="卸货口代码";
l_unloadPortName_Q20="卸货口名称";
l_aogFactory_Q20="到货工厂";
l_cutoffStartTime_Q20="打切开始时间";
l_cutoffNum_Q20="打切数量";
l_cutoffResidual_Q20="打切剩余量";
l_partUnit_Q20="零件单位";
l_close_Q20="关闭";
l_cutoffOrderQuantityView_Q20="打切订购数量查看";
//******* 打切订购数量查看(Q20页面结束  *******//

//******* 打切订购数量添加(Q21)页面开始  *******//
l_partId_Q21="零部件号";
l_partName_Q21="零部件名称";
l_aogFactory_Q21="到货工厂";
l_cutoffStartTime_Q21="打切开始时间";
l_cutoffNum_Q21="打切数量";
l_cutoffResidual_Q21="打切剩余量";
l_partUnit_Q21="零件单位";
l_save_Q21="保存";
l_partId_not_null_Q21="零部件号不能为空！";
l_supplierCode_not_null_Q21="供应商代码不能为空！";
l_factoryCode_not_null_Q21="厂家代码不能为空！";
l_unloadPort_not_null_Q21="卸货口不能为空！";
l_tip_Q21="提示";
l_tip_content_Q21="该记录已存在，你确定要插入吗";
l_info_success_Q21="信息保存成功！";
l_is_sure_save_Q21="你确定要保存";
l_cutoffOrderQuantity_add_Q21="打切订购数量添加";
l_inputContent_Q21="录入内容";
//******* 打切订购数量添加(Q21)页面结束  *******//

//******* 打切订购数量修改(Q22)页面开始  *******//
l_partId_Q22="零部件号";
l_partName_Q22="零部件名称";
l_supplierCode_Q22="供应商代码";
l_supplierName_Q22="供应商名称";
l_factoryCode_Q22="厂家代码";
l_factoryName_Q22="厂家名称";
l_unloadPortCode_Q22="卸货口代码";
l_unloadPortName_Q22="卸货口名称";
l_aogFactory_Q22="到货工厂";
l_cutoffStartTime_Q22="打切开始时间";
l_cutoffNum_Q22="打切数量";
l_cutoffResidual_Q22="打切剩余量";
l_partUnit_Q22="零件单位";
l_save_Q22="保存";
l_close_Q22="关闭";
l_cutoffStartTime_not_null_Q22="打切开始时间不能为空！";
l_cutoffOrderQuantity_modify_Q22="打切订购数量修改";
l_cutoffManage_Q22="打切管理";
//******* 打切订购数量修改(Q22)页面结束  *******//

//******* ERP采购订单生成（手工）(Q23)页面开始 ****//
l_supplierCode_Q23="供应商代码";  
l_dischargePort_Q23="卸货口";
l_orderNo_Q23="物流订单号";

l_orderIssueDate_Q23="订单发行日";
l_arrivalDate_Q23="GAMC到货日期";
l_orderGen_Q23="生成采购订单";
l_selectdeal_Q23="生成采购订单";
l_warnmessage_Q23="该处理范围PO数据不存在,不能生成采购订单！";
l_successmessage_Q23="订单生成成功！";
l_successTip_Q23="采购订单生成成功";
l_failmessage_Q23="采购订单生成失败！";
l_supplierIdCheck_Q23="供应商代码From不能大于供应商代码To！";
l_orderNoCheck_Q23="物流订单号From不能大于物流订单号To！";
l_disportCheck_Q23="卸货口From不能大于物流订单号To！";
//******* ERP采购订单生成（手工）(Q23)页面结束 *******//

//******* DMS计划订单接收（自动）(Q24)页面开始 *******//
//******* DMS计划订单接收（自动）(Q24)页面结束 *******//

//******* ERP计划订单导入（自动）(Q25)页面开始 *******//
//******* ERP计划订单导入（自动）(Q25)页面结束 *******//

//******* EPPS净需求计算（自动+手工）(Q26)页面开始 *******//
l_orderIssueDate_Q26="订单发行时间";
l_demandCal_Q26="净需求计算";
l_success_Q26="计算成功，零件需求信息已生成";
l_false_Q26 = "计算失败";
l_autoExecuteTimeSet_Q26 = "自动执行时间设置";
l_autoEffect_Q26 = "自动生效";
l_dataCheck_Q26 = "数据校验";
l_dataCheckSuccess_Q26 = "数据校验成功!";

l_lock_purchase_Q26 = "生成采购订单功能已被别的用户或进程启动，您现在不能使用，请稍等。";
l_lock_need_part_Q26 = "净需求计算功能已被别的用户或进程启动，您现在不能使用，请稍等。";
l_bad_data_materiel_Q26 = "物料清单中存在异常数据，不能进行净需求计算。";
l_bad_data_mrp_Q26 = "MRP计划订单中存在异常数据，不能进行净需求计算。";
l_bad_data_dms_Q26 = "DMS计划订单中存在异常数据，不能进行净需求计算。";
l_bad_data_produce_Q26 = "生产订单中存在异常数据，不能进行净需求计算。";
l_bad_data_pv_Q26 = "车辆规格查询中存在异常数据，不能进行净需求计算。";

//******* EPPS净需求计算（自动+手工）(Q26)页面结束 *******//
/******************* 订购数量调整 end**********************/

/******************* 数据查询 start**********************/
//******* 订单履历检索(S01)页面开始 *******//
l_S01 = '订单履历检索';
l_show_S01 = '提示';
l_saved_S01 = "你确定要保存吗？";
l_success_S01 = "信息已被保存";
l_false_S01 = "失败";
l_S00 = '订单履历修改';
l_supplierNo_S01 = '供应商代码';
l_planPickNo_S01 = '计划订单号';
l_unloadPort_S01 = '卸货口';
l_partId_S01 = '零件编号';
l_partProfileId_S01 = '零件简号';
l_partName_S01 = '零件名称';
l_orderStatus_S01 = '订单状态';
l_planOrderId_S01 = 'ERP采购订单号';
l_logisticsOrder_S01 = '物流订单号';
l_orderIssueDate_S01 = '订单发行日';
l_isCutoff_S01 = '打切';
l_planArrivalDate_S01 = '计划到货日期';
l_packageNum_S01 = '总订购箱数';
l_orderState_S01 = '订单状态';
l_totalOrderNum_S01 = '总订购数量';
l_orderNum_S01 = '订购数量';
l_gamcArriveTime_S01 = 'GAMC到货时间';
l_orderView_S01 = '订单履历信息一览';
l_orderRecordView_S01 = '订单履历查看';
l_creatERP_S01 = '生成ERP采购订单';
l_bigFuzhi_S01 = "输入的负值太大会造成总订购箱数为负，请重新输入！";
l_max_S01 = '手工调整数最大值为99999999！';
//******* 订单履历检索(S01)页面结束 *******//

//******* 订单履历查看(S02)页面开始 *******//
l_S02 = '订单履历查看';
l_purchaseDept_S02 = '采购部门';
l_factoryId_S02 = '厂家代码';
l_dmsOrderId_S02 = 'DMS订单号';
l_selectionZone_S02 = '分拣区';
l_unloadPlace_S02 = '卸货场所';
l_aogWarehouse_S02 = '到货仓库';
l_erpPoId_S02 = 'ERP PO号';
l_erpPoRowNum_S02 = 'ERP PO行号';
l_partUnit_S02 = '零件单位';
l_packNum_S02 = '包装数量';
l_manualAdjustNum_S02 = '手工调整箱数';
l_necessaryNetNum_S02 = '必要数净数量';
l_necessaryOrderResidual_S02 = '必要数订购剩余量';
l_necessaryPlanNum_S02 = '必要数计划数量';
l_necessaryRealOrderNum_S02 = '必要数实际订购数量';
l_defectiveNetNum_S02 = '加工不良净数量';
l_defectiveOrderResidual_S02 = '加工不良库存量';
l_defectivePlanNum_S02 = '加工不良计划数量';
l_defectiveRealOrderNum_S02 = '加工不良实际订购数量';
l_notVehicleBQuantity_S02 = '非PV定量数量';
l_totalOrderNum_S02 = '总订购数量';
l_packUnitWeight_S02 = '包装单元重量';
l_packUnitVolume_S02 = '包装单元体积';
l_packSingleWeight_S02 = '包装单重';
l_packLength_S02 = '包装长';
l_packWidth_S02 = '包装宽';
l_packHeight_S02 = '包装高';
l_totalOrderBox_S02 = '总订购箱数';
l_planDeliveryTimeStr_S02 = '计划发货时间';
l_planArriveTimeStr_S02 = '计划到货时间';
l_shipmentTimeStr_S02 = '供应商发货时间';
l_firstTransitArriveTimeStr_S02 = '第一中转仓到货时间';
l_firstTransitDeliveryTimeStr_S02 = '第一中转仓发货时间';
l_secondTransitArriveTimeStr_S02 = '第二中转仓到货时间';
l_secondTransitDeliveryTimeStr_S02 = '第二中转仓发货时间';
l_gamcArriveTimeStr_S02 = 'GAMC到货时间';
l_GamcArrivalFreq_S02 = 'GAMC到货频次';
l_SupplierShippingFreq_S02 = '供应商发货频次';
l_collectGoodsLine_S02 = '厂家集货路线';
l_gamcArriveLine_S02 = 'GAMC到达路线';
l_info_S02 = '基本信息';
l_shipInfo_S02 = '采购信息';
l_partInfo_S02 = '零件信息';
l_logistic_S02 = '物流信息';
l_orderView_S02 = '订单履历查看';
l_orderNum_S02 = '订购数量';
l_shipmentTimeStr_S04 = '供应商发货时间不能为空！';
l_shipmentTimeStr_S03 = '第一中转仓到货时间应该比供应商发货时间晚,请重新输入！';
l_firstTransitArriveTimeStr_S03 = '第一中转仓发货时间应该比第一中转仓到货时间晚,请重新输入！';
l_secondTransitArriveTimeStr_S03 = '第二中转仓发货时间应该比第二中转仓到货时间晚,请重新输入！';
l_secondTransitDeliveryTimeStr_S03 = '第二中转仓到货时间应该比第一中转仓发货时间晚,请重新输入！';
l_secondTransitDeliveryTimeStrLate_S03 = 'GAMC到货时间应该比第二中转仓发货时间晚,请重新输入！';
//******* 订单履历查看(S02)页面结束 *******//
//******* 车辆总成查询(S04)页面开始 *******//
l_S04 = '车辆总成查询';
l_produceSeq_S04 = '产品变型号 ';
l_mtoId_S04 = '整车代码';
l_planUnloadTime_S04 = '总装下线时间';
l_isProduct_S04 = '是否试制';
l_getNewMes_S04 = '获取MES最新数据';
l_changeId_S04 = '产品变型号';
l_view_S04 = '车辆总成一览';
l_planNum_S04 = '计划数量';
l_planFlg_S04 = '计划标识';
l_planState_S04 = '计划订单状态';
l_weldUnderlineTimeStr_S04 = '焊装下线时间';
l_paintUnderlineTimeStr_S04 = '凃装下线时间';
l_finalUnderlineTimeStr_S04 = '总装下线时间';
l_assemblyLine_S04 = '装配线';
l_isProduct_S04 = '是否试制';
l_assemblyStatus_S04 = '装配状态';
l_calculateStatus_S04 = '计算状态';
l_onOffStatus_S04 = '开关状态';
l_msg1_S04 = "该条记录已被更新(产品变型号：";
l_msg2_S04 = ")，请取最新数据!";
l_msg3_S04 = "插入估计材料失败！(产品变型号：";
//******* 整车日排序计划(S04)页面结束 *******//

//******* 估计材料（整车日）排序计划(S05)页面开始 *******//
l_S05 = '估计材料（整车日）排序计划';
l_view_S05 = '估计材料一览';
l_wareHouseId_S05 = '需求仓库';
l_sequence_S05 = '工序';
l_centerId_S05 = '工序落点';
l_wareHouseId1_S05 = '订货仓库';
l_aogFactory_S05 = '到货工厂';
l_isCount_S05 = '已计算';
l_aogWareHouse_S05 = '库区代码';
l_unloadPlace_S05 = '卸货场所';
//******* 估计材料（整车日）排序计划(S05)页面结束 *******//

//******* 零件需求数量检索(S06)页面开始 *******//

l_S06 = '零件需求数量检索';
l_planArriveTime_S06 = '计划到货日期';
l_demandOrigin_S06 = '来源';
l_isCalculate_S06 = '计算状态';
l_serch_S06 = '查询条件';
l_netQuantity_S06 = '计划数量';
l_partNeedsView = '零件需求数量一览';
l_partUnit_S06 = '零件单位';
l_orderDepot_S06 = '订货仓库';
l_date_S06 = '天';
l_hour_S06 = '小时';
l_getOrSend_S06 = '物流标识';
l_supplierPrepareTime_S06 = '准备时间';
l_transportTime_S06 = '供应时间';
l_inPlanForwardTime_S06 = '安全时间';
//******* 零件需求数量检索(S06)页面结束 *******//

//******* 零件需求数量查看(S07)页面开始 *******//
l_S07 = ' 零件需求数量查看';
l_BuyNo_S07 = '产品变型号';
l_aogFactory_S07 = '到货工厂';
//******* 零件需求数量查看(S07)页面结束 *******//

//******* 接口异常数据处理--物料清单查询(S08)页面开始 *******//
l_MaterielListTempView_S08 = '物料清单数据一览';
l_retry_S08 = '重试';
l_operate_S08 = '操作';
l_producePieceName_S08 = '生产件名称';
l_partName_S08 = '零件名称';
//******* 接口异常数据处理--物料清单查询(S08)页面结束 *******//

//******* 接口异常数据处理--车辆规格查询(S09)页面开始 *******//
l_VehicleStandardQuery_S09 = '车辆规格查询';
l_VehicleStandardView_S09 = '车辆规格一览';
l_stage_S09 = '阶段';
l_sn_S09 = 'SN';
l_verifyInfo_S09 = 'PV订单验证信息';
//******* 接口异常数据处理--车辆规格查询(S09)页面结束 *******//

//******* 接口异常数据处理--生产订单(S10)页面开始 *******//
l_produceOrderNo_S10 = '生产订单号';
l_verifyInfo_S10 = '生产订单验证信息';
//******* 接口异常数据处理--生产订单(S10)页面结束 *******//
/******************* 数据查询 end**********************/




/******* DMS厂外取货采购订单(E02)页面开始 *******/
l_serch_E02 = '查询条件 ';
l_supplyId_E02 = '供应商代码 ';
l_plantId_E02 = '厂家代码';
l_partId_E02 = '零件编号';
l_partName_E02 = '零件名称';
l_planArriveTime_E02 = '计划到货时间 ';
l_dmsView_E02 = 'DMS厂外取货采购订单一览';
l_serialNum_E02 = '流水号 ';
l_netQuantity_E02 = '净数量 ';
l_partUnit_E02 = '零件单位 ';
l_orderDepot_E02 = '订货仓库 ';
l_logisticsFlg_E02 = '物流标识 ';
l_planArriveTime_E02 = '计划到货时间 ';
l_demandOrigin_E02 = '需求来源';
l_dmsOrderId_E02 = 'DMS订单号 ';
l_operate_E02 = '操作';
l_retry_E02 = '重试';
l_dmsView = 'MRP计划订单一览';
l_verifyInfo_E02 = '验证信息';
l_dealSuccess_E02 = "处理成功！";
l_dealFailure_E02 = "处理失败！";
l_msg1_E02 = "该计划订单的订货仓库对应的卸货口表数据不存在。";
l_msg2_E02 = "该订单所对应的零件订购主数据不存在或已失效！";
l_msg3_E02 = "该生产订单的估计材料中有数据不完整的情况，请参见验证信息。";
l_msg4_E02 = "该PV的估计材料中在零件订购主数据中有不存在或失效的情况。";
l_msg5_E02 = "该PV的零件需求数量中间表中的数据也被删除，不能进行再计算。";
l_error_E02 = "发生系统错误，请联系系统管理员。";
l_aogFactory_E02 = '到货工厂';
l_unloadPort_E02 = '卸货口';
l_orderIssueDate_E02 = '订单发行日';
l_planDeliveryTime_E02 = '计划发货时间';
l_calculateStatus_E02 = '计算状态';

/*******  DMS厂外取货采购订单(E02)页面结束 *******/

/******* 定量取货物流订单修改(Q99)页面开始 *******/
l_serch_Q99 = '查询条件';
l_supplierNo_Q99 = '供应商代码';
l_factoryId_Q99 = '厂家代码';
l_unloadPort_Q99 = '卸货口';
l_partId_Q99 = '零件编号';
l_partProfileId_Q99 = '零件简号';
l_logisticsOrder_Q99 = '物流订单号';
l_orderIssueDate_Q99 = '订单发行日';
l_planArrivalDate_Q99 = '计划到货日期';
l_dealTimeStr_Q99 = '处理日期';
l_orderStatus_Q99 = '订单状态';

l_planOrderNo_Q99 = '计划订单号';
l_partName_Q99 = '零件名称';
l_manualAdjustNum_Q99 = '手工调整箱数 ';
l_orderNum_Q99 = '订购数量 ';
l_totalOrderNum_Q99 = '总订购数量';
l_packNum_Q99 = '包装数量';
l_packageNum_Q99 = '总订购箱数';
l_orderIssueDate_Q99 = '订单发行日';
l_gamcArriveDateStr_Q99 = 'GAMC到货时间 ';
l_dealTime_Q99 = '处理日期';
l_isCutoff_Q99 = '打切';
l_isCutoffNum_Q99 = '打切数量';
l_stockBoxQuantity_Q99 = '箱数调整数量';
l_loadRateQuantity_Q99 = '装载率调整量';
l_orderStatus_Q99 = '订单状态';
l_orderView_Q99 = '定量取货物流订单一览';
l_factoryShipTime_Q99 = '厂家出货时间';
l_save_Q99 = '保存';

l_title_Q99 = "提示";
l_chooseOneMateriel_Q99 = "请选择一行物料清单数据！";
l_chooseShipDate_Q99 = "请选择厂家出货时间！";
l_chooseGamcArriveTime_Q99 = "请选择GAMC到货时间 ！";
l_confirmUpdateTime_Q99 = "您确定要修改厂家出货时间和GAMC到货时间吗？";
l_updateSuccess_Q99 = "厂家出货时间和GAMC到货时间修改成功！";
l_updateFailure_Q99 = "厂家出货时间和GAMC到货时间修改失败！";
/******* 定量取货物流订单修改(Q99)页面结束 *******/

/******* 车辆规格查询再计算(Q98)页面开始 *******/
l_serch_Q98 = '查询条件';
l_produceSeq_Q98 = '产品变型号';
l_finalUnderlineTime_Q98 = '总装下线时间';
l_calculateStatus_Q98 = 'PV零件需求计算状态';
l_operate_Q98 = '操作';
l_recalculate_Q98 = '再计算';
l_view_Q98 = '车辆总成一览';

l_mtoId_Q98 = '整车代码';
l_planNum_Q98 = '计划数量';
l_planState_Q98 = '计划订单状态';
l_weldUnderlineTimeStr_Q98 = '焊装下线时间';
l_paintUnderlineTimeStr_Q98 = '凃装下线时间';
l_finalUnderlineTimeStr_Q98 = '总装下线时间';
l_isProduct_Q98 = '是否试制';
l_onOffStatus_Q98 = '开关状态';
l_calculateStatus_Q98 = '计算状态';
l_show_Q98 = '错误';
l_produceSeqConfirm_Q98 = "产品变型号大小关系不正确！";
l_inputNum_Q98 = "请输入数字";
/******* 车辆规格查询再计算(Q98)页面结束 *******/

l_date = "日期";
l_arrivalTime = "出勤时间";
l_leaveTime = "退勤时间";
l_time = "工作时间";
l_save = "保存";
l_cancel = "取消";
l_skipweekend = "自动跳过周末";
l_supplierscode = "供应商代码";
l_factorycode = "厂家代码";
l_timefrequency = "时间频度";
l_countrange = "计算范围";

/******************* 物料清单 start**********************/
  /*************** 物料清单检索 start***************/
l_producePieceB13 = "生产件号";
l_producePieceNameB13 = "生产件名称";
l_unitB13 = "单位";
l_partBriefNoB13="零件简号";
l_centerIdB13="工作中心";
l_warehouseIdB13="仓库";
l_aogFactoryB13="到货工厂";
l_unloadPortB13="卸货口";
l_updateUnloadPortB13 = "修改卸货口";
l_updateItemInfoB13 = "修改零件信息";
l_titlePartB13="零件信息一览";
l_check_cbxB13="仅查询未录入落点组合的物料";
l_inspectB13="物料清单查看";
l_modifyB13="物料清单修改";
l_titleB13="物料清单数据一览";
l_partNumberB13="零件编号";
l_partNameCNB13 ="零件名称";
l_locationB13 ="位置";
l_sequenceB13 ="序号";
l_netQuantityB13 ="净数量";
l_centerNameB13 ="工序落点";
l_placementPortfolioB13 ="卸货口";
l_effectiveDateB13 ="生效日期";
l_expiryDateB13 ="失效日期";
l_isVirtualB13 ="是否虚拟";
l_bomSearchB13 = "物料清单检索";
l_searchB13 = "检索";
l_clearB13 = "清空";
l_all_retry = "全部重试";
l_okB13 = "确定";
l_backB13 = "返回";
l_previousB13 = "已经是第一页了";
l_nextPageB13 = "已经是最后一页了";
l_titleB131 = "提示";
l_chooseOnePartB131 = "请选择一个零件！";
l_chooseOneMaterielB131 = "请选择一行物料清单数据！";
l_chooseOneUnloadPortB131 = "卸货口为必输项。";
l_isNotExistedUnloadPortB131 = "卸货口不存在！";
l_confirmUpdateUnloadPortB131 = "您确定要修改卸货口吗？";
l_updateUnloadPortWaitMsgB131 = "数据保存中...";
l_updateSuccessB131 = "卸货口修改成功！";
l_updateFailureB131 = "卸货口修改失败！";
l_updateItemSuccessB131 = "零件主数据修改成功！";
l_updateItemFailureB131 = "零件主数据修改失败！";
l_msg1_B131 = "该物料信息对应的卸货口，到货工厂和到货仓库不存在，不能生成物料信息。";
l_searchResultTooMuch_B131 = "本次检索的数据超过一万条，请指定检索条件。 否则检索速度将会十分缓慢！";
    /*************** 物料清单检索 end***************/

    /*************** 物料清单查看 start***************/
l_producePieceB14 = "生产件号";
l_locationB14 ="位置";
l_sequenceB14 ="序号";
l_partNumberB14="零件编号";
l_partNameCNB14 ="零件名称";
l_partBriefNoB14="零件简号";
l_netQuantityB14 ="净数量";
l_wasteRateB14 ="废品率";
l_wasteQuantityB14 ="废品数量";
l_processB14 ="工序";
l_centerNameB14 ="工序落点";
l_isVirtualB14 ="是否虚拟";
l_effectiveDateB14 ="生效日期";
l_expiryDateB14 ="失效日期";
l_AogWarehouseB14 ="到货仓库";
l_AogFactoryB14 ="到货工厂";
l_UuloadPlaceB14 ="卸货场所";
l_SelectionZoneB14 ="分拣区";
l_placementprocessesportfolioB14 ="工序落点组合";
l_mainpartcodeB14="主零件代码";
l_mainpartnameB14="主零件名称";
l_titleB14="物料清单查看";
l_bomDataB14="物料清单数据";
l_componentPlacementDataB14 = "零件落点数据";
l_PurchaseB14="配套采购数据";
l_inspectB14="零部件信息查看";
l_previous_B14 = "上一条";
l_nextPage_B14 = "下一条";
    /*************** 物料清单查看 end***************/

     /*************** 物料清单修改 start***************/
l_producePieceB15 = "生产件号";
l_locationB15 ="位置";
l_sequenceB15 ="序号";
l_partNumberB15="零件编号";
l_partNameCNB15 ="零件名称";
l_partBriefNoB15="零件简号";
l_netQuantityB15 ="净数量";
l_wasteRateB15 ="废品率";
l_wasteQuantityB15 ="废品数量";
l_processB15 ="工序";
l_centerNameB15 ="工序落点";
l_isVirtualB15 ="是否虚拟";
l_effectiveDateB15 ="生效日期";
l_expiryDateB15 ="失效日期";
l_placementB15 ="卸货口";
l_AogWarehouseB15 ="到货仓库";
l_AogFactoryB15 ="到货工厂";
l_UuloadPlaceB15 ="卸货场所";
l_SelectionZoneB15 ="分拣区";
l_mainpartcodeB15="主零件代码";
l_mainpartnameB15="主零件名称";
l_titleB15="物料清单修改";
l_bomDataB15="物料清单数据";
l_componentPlacementDataB15 = "零件落点数据";
l_PurchaseB15="配套采购数据";
l_modifyB15="物料清单修改";
l_previous_B15 = "上一条";
l_nextPage_B15 = "下一条";
l_save_B15 = "保存";
l_warning_B15 = "主零件代码和零件编号不能相同";
    /*************** 物料清单修改 end***************/

/******************* 物料清单 end**********************/

/******************* 库存个数 start**********************/

/*************** 库存个数检索 start***************/
l_componentNo_Q11 = "零件编号";
l_componentId_Q11 = "零件简号";
l_supplierNo_Q11 = "供应商代码";
l_xieHuoKou_Q11 = "卸货口";
l_query_Q11 = "查询";
l_strstrdealStratDate_Q11 = "处理起止时间";
l_inspect_Q11 = "库存个数查看";
l_cmbChangjId_Q11 = "厂家代码";
l_lblDaoHuoShCode_Q11 = "到货工厂";
l_entrydate_Q11 = "录入时间";
l_dailyAdjustment_Q11 = '每日调整量';
l_isGenerateRequire_Q11 = '是否生成需求';
l_stockResidualBox_Q11 = '处理结束日期';
l_close_Q11 = "关闭";
l_stockBoxNumbeModify_Q11 = "库存个数修改";
l_save_Q11 = "保存";
l_invalid_Q11 ="失效";
l_stockBoxNumbeAdd_Q11 = "库存个数添加";
l_stockBoxList_Q11 = "库存个数一览 ";
l_stockBoxQuery_Q11 = "库存个数检索";
l_tishi_Q11 = "处理结束日期要大于处理开始时间";
	/*************** 库存个数检索 end***************/

	/*************** 库存个数添加 start***************/
l_componentNo_Q13 = "零件编号";
l_supplierNo_Q13 = "供应商代码";
l_cmbChangjId_Q13 = "厂家代码";
l_xieHuoKou_Q13 = "卸货口";
l_lblDaoHuoShCode_Q13 = "到货工厂";
l_arrivalStartDate_Q13 = "处理开始日期";
l_arrivalEndDate_Q13 = "处理结束日期";
l_dailyAdjustmentVolume_Q13 = '每日调整量';
l_save_Q13 = "保存";
l_close_Q13 = "关闭";
l_stockNumberAdd_Q13 = "库存个数添加";
l_partName_Q13 = "零件名称";
	/*************** 库存个数添加 end***************/

	/*************** 库存箱数检索 start***************/
l_componentNo_Q15 = "零件编号";
l_componentId_Q15 = "零件简号";
l_supplierNo_Q15 = "供应商代码";
l_xieHuoKou_Q15 = "卸货口";
l_query_Q15 = "查询";
l_arrivalStartDate_Q15 = "到货开始日期";
l_inspect_Q15 = "库存箱数查看";
l_cmbChangjId_Q15 = "厂家代码";
l_lblDaoHuoShCode_Q15 = "到货工厂";
l_arrivalStartDate_Q15 = "到货开始日期";
l_dailyAdjustmentVolume_Q15 = '调整箱数';
l_stockResidualBox_Q15 = '调整剩余箱数';
l_minBox_Q15 = '最小箱数';
l_close_Q15 = "关闭";
l_stockBoxNumbeModify_Q15 = "库存箱数修改";
l_save_Q15 = "保存";
l_stockBoxNumbeAdd_Q15 = "库存箱数添加";
l_minBox_Q15 = "最小箱数 ";
l_stockBox_Q15 = "调整箱数 ";
l_stockResidualBox_Q15 = "调整剩余箱数 ";
l_stockBoxList_Q15 = "库存箱数一览 ";
l_stockBoxQuery_Q15 = "库存箱数检索";
l_explain_Q15 = "（保存后，剩余调整箱数将被清零）";
	/*************** 库存箱数检索 end***************/

	/*************** 库存箱数添加 start***************/
l_componentNo_Q17 = "零件编号";
l_supplierNo_Q17 = "供应商代码";
l_cmbChangjId_Q17 = "厂家代码";
l_Q17="库存箱数添加";
l_minBox_Q17 = "最小箱数 ";
l_xieHuoKou_Q17 = "卸货口";
l_lblDaoHuoShCode_Q17 = "到货工厂";
l_arrivalStartDate_Q17 = "到货开始日期";
l_dailyAdjustmentVolume_Q17 = '调整箱数';
l_save_Q17 = "保存";
l_close_Q17 = "关闭";
l_stockBoxAdd_Q17 = "库存箱数添加";
l_partName_Q17 = "零件名称";
	/*************** 库存箱数添加 end***************/

/*************** 工作日历 start***************/

l_w_001 = '早班开始时间';
l_w_002 = '结束时间';
l_w_003 = '午班开始时间';
l_w_004 = '晚班开始时间';
l_w_005 = '星期';
l_w_0061 = '周一';
l_w_0062 = '周二';
l_w_0063 = '周三';
l_w_0064 = '周四';
l_w_0065 = '周五';
l_w_0066 = '周六';
l_w_0067 = '周日';
l_w_007 = '工作时间';
l_w_008 = '选择日期';
l_w_009 = '日期性质';
l_w_0010 = '日期备注';

l_msg1_workCal = "早班开始时间不能晚于早班结束时间！";
l_msg2_workCal = "午班开始时间不能晚于午班结束时间！";
l_msg3_workCal = "午班开始时间不能早于早班结束时间！";
l_msg4_workCal = "晚班开始时间不能早于午班结束时间！";
l_msg5_workCal = "晚班开始时间不能早于早班结束时间！";
l_msg6_workCal = "晚班开始时间不能晚于晚班结束时间！";
l_chooseWorkCenter_workCal = "请选择工作中心！";
l_chooseDate_workCal = "请选择日期！";
l_workCal = "工作日历";


l_workCal_copy_01 = "源工作中心";
l_workCal_copy_02 = "复制时间段";
l_workCal_copy_03 = "复制源";
l_workCal_copy_04 = "复制目标";
l_workCal_copy_05 = "工作中心代码";
l_workCal_copy_06 = "工作中心名称";
l_workCal_copy_07 = "复制";

l_workCal_copy_11 = "复制源工作中心不能为空。";
l_workCal_copy_12 = "复制时间段的开始时间不能为空。";
l_workCal_copy_13 = "复制时间段的截止时间不能为空。";
l_workCal_copy_14 = "复制时间段的开始时间不能大于截止时间。";
l_workCal_copy_15 = "请至少选择一个复制目标工作中心。";
l_workCal_copy_16 = "复制目标工作中心中不能有复制源工作中心。";
l_workCal_copy_17 = "您确认要复制吗？";


/*************** 工作日历 end***************/

/******************* 库存箱数 end************************/

Ext.UpdateManager.defaults.indicatorText = '<div class="loading-indicator">加载中...</div>';

if(Ext.View){
   Ext.View.prototype.emptyText = "";
}

if(Ext.grid.GridPanel){
   Ext.grid.GridPanel.prototype.ddText = "{0} 选择行";
}

if(Ext.TabPanelItem){
   Ext.TabPanelItem.prototype.closeText = "关闭";
}

if(Ext.form.Field){
   Ext.form.Field.prototype.invalidText = "输入值非法";
}
if(Ext.form.CheckboxGroup){
       Ext.form.CheckboxGroup.prototype.blankText = "请至少选择一个。";
    }
Date.monthNames = [
   "1月",
   "2月",
   "3月",
   "4月",
   "5月",
   "6月",
   "7月",
   "8月",
   "9月",
   "10月",
   "11月",
   "12月"
];

Date.dayNames = [
   "日",
   "一",
   "二",
   "三",
   "四",
   "五",
   "六"
];

if(Ext.MessageBox){
   Ext.MessageBox.buttonText = {
      ok     : "确定",
      cancel : "取消",
      yes    : "是",
      no     : "否"
   };
}

if(Ext.util.Format){
   Ext.util.Format.date = function(v, format){
      if(!v) return "";
      if(!(v instanceof Date)) v = new Date(Date.parse(v));
      return v.dateFormat(format || "y年m月d日");
   };
}

if(Ext.DatePicker){
   Ext.apply(Ext.DatePicker.prototype, {
      todayText         : "今天",
      minText           : "日期在最小日期之前",
      maxText           : "日期在最大日期之后",
      disabledDaysText  : "",
      disabledDatesText : "",
      monthNames        : Date.monthNames,
      dayNames          : Date.dayNames,
      nextText          : '下月 (Control+Right)',
      prevText          : '上月 (Control+Left)',
      monthYearText     : '选择一个月 (Control+Up/Down 来改变年)',
      todayTip          : "{0} (空格键选择)",
      format            : "y年m月d日",
      okText            : "确定",
      cancelText        : "取消"
   });
}

if(Ext.PagingToolbar){
   Ext.apply(Ext.PagingToolbar.prototype, {
      beforePageText : "第",
      afterPageText  : "页　共 {0} 页",
      firstText      : "第一页",
      prevText       : "前一页",
      nextText       : "下一页",
      lastText       : "最后页",
      refreshText    : "刷新",
      displayMsg     : "显示 {0} - {1}，共 {2} 条",
      emptyMsg       : '没有数据需要显示'
   });
}

if(Ext.form.TextField){
   Ext.apply(Ext.form.TextField.prototype, {
      minLengthText : "该输入项的最小长度是 {0}",
      maxLengthText : "该输入项的最大长度是 {0}",
      blankText     : "该输入项为必输项",
      regexText     : "",
      emptyText     : null
   });
}

if(Ext.form.NumberField){
   Ext.apply(Ext.form.NumberField.prototype, {
      minText : "该输入项的最小值是 {0}",
      maxText : "该输入项的最大值是 {0}",
      nanText : "{0} 不是有效数值"
   });
}

if(Ext.form.DateField){
   Ext.apply(Ext.form.DateField.prototype, {
      disabledDaysText  : "禁用",
      disabledDatesText : "禁用",
      minText           : "该输入项的日期必须在 {0} 之后",
      maxText           : "该输入项的日期必须在 {0} 之前",
      invalidText       : "{0} 是无效的日期 - 必须符合格式： {1}",
      format            : "y年m月d日"
   });
}

if(Ext.form.ComboBox){
   Ext.apply(Ext.form.ComboBox.prototype, {
      loadingText       : "加载...",
      valueNotFoundText : undefined
   });
}

if(Ext.form.VTypes){
   Ext.apply(Ext.form.VTypes, {
      emailText    : '该输入项必须是电子邮件地址，格式如： "user@domain.com"',
      urlText      : '该输入项必须是URL地址，格式如： "http:/'+'/www.domain.com"',
      alphaText    : '该输入项只能包含字符和_',
      alphanumText : '该输入项只能包含字符,数字和_'
   });
}


/**
 * Simplified Chinese translation
 * By e-PPS
 * 10 June 2010
 */
tip_export_succ = '导出成功。';
tip_export_nodata = '没有可导出的数据，请先检索。';
l_language = "语言";
l_userName = "用户名";
l_password = "密码";
l_blankPassword = "密　码";
l_welcome = "欢迎您";
l_login = "登录";
l_search = "检索";
l_mackData='生成异常数据';
l_clear = "清空";
l_waitMsg = "请稍候...";
l_select = '请选择';
l_add = '新增';
l_modify = '修改';
l_delete = '删除';
l_save = "保存";
l_userList = '用户一览';
l_userId = '用户Id';
l_close = "关闭";
l_import = "导入";
l_dataCheck="数据校验";
l_aogFactory_lable="到货工厂";
l_aogFactory_value = "GAMC01";
l_supplierId="供应商代码";
l_supplierName="供应商名称";
l_unloadPort="卸货口";
l_shipDepot="出货仓库";
l_unloadPalce="卸货场所";
l_factoryId="厂家代码";
l_factoryName="厂家名称";
l_partId="零件编号";
l_partNameCn="零件名称";
l_partUnit="零件单位";
l_noName="未命名";
l_homePage="主页";
l_userType="身份类型";
l_validateCode="验证码";
l_kanBuQing="看不清";
l_refreshValidateCode="重新获得验证码";
l_pleaseWait="请稍等";


/******************* 系统管理 start**********************/
//******* 用户信息录入(M01)页面开始 *******//
l_M01 = "用户信息录入";
l_loginName_M01 = "登录名";
l_password_M01 = "密码";
l_mobile_M01 = "手机 ";
l_comfirmPassword_M01 = "确认密码";
l_telephone_M01 = "电话";
l_name_M01 = "姓名";
l_fax_M01 = "传真";
l_department_M01 = "部门";
l_email_M01 = "电子邮箱";
l_role_M01 = "角色";
msg_pwd_wrong_M01 = "两次输入的密码不同";
//******* 用户信息录入(M01)页面结束 *******//

//******* 用户信息管理(M02)页面开始 *******//
l_M02 = "用户信息管理";
l_userInfo_M02 = "查看用户信息";
l_userUpdate_M02 = "修改用户信息";
l_loginName_M02 = "登录名";
l_userName_M02 = "用户名";
l_password_M02 = "密码";
l_comfirmPassword_M02 = "确认密码";
l_name_M02 = "姓名";
l_role_M02 = "角色";
l_department_M02 = "部门";
l_userList_M02 = '用户信息一览';
l_telephone_M02 = '联系电话';
l_mobile_M02 = '手机';
l_email_M02 = "电子邮箱";
l_userStatus_M02 = '用户状态';
l_loginStatus_M02 = '登录状态';
l_loginNum_M02 = '登录次数';
l_fax_M02 = '传真';
msg_pwd_min_M02 = '该输入项的最小长度为6';
msg_pwd_max_M02 = '该输入项的最大长度为40';
msg_pwd_wrong_M02 = "两次输入的密码不同";
msg_delete_M02 = "不能删除自己!";
msg_disable_M02 = "不能禁用自己!";
msg_alreadyDisable_M02 = "该用户已处于禁用状态";
msg_alreadyUsable_M02 = "该用户已处于可用状态";
//******* 用户信息管理(M02)页面结束 *******//

//******* 角色权限管理(M03)页面开始 *******//
l_M03 = "角色权限管理";
l_addRole_M03 = "新增角色信息";
l_role_M03 = "角色";
l_department_M03 = "部门";
l_searchForm_M03 = "查询条件";
l_roleInfo_M03 = "角色信息一览";
l_roleDesc_M03 = "角色说明";
l_roleStatus_M03 = "状态";
l_allRole_M03 = "所有权限";
l_roleModify_M03 = "权限配置";
l_roleModifyWin_M03 = "修改角色信息";
msg_infoTitle_M03 = "提示";
msg_modifyInfo_M03 = "确认要保存权限修改吗?"	;
msg_sysRole_M03 = "系统业务用角色不可删除";
msg_usedRole_M03 = "对不起,该角色有用户在使用,不可删除!";
msg_modifySuccess_M03 = "权限修改成功!";
l_quanXianMoKuai_M03 = "权限模块";
l_huaMianMing_M03 = "画面名";

//******* 角色权限管理(M03)页面结束 *******//

//******* 部门信息录入(M04)页面开始 *******//
l_M04 = "部门信息录入";
l_deptName_M04 = "部门名";
l_deptManager_M04 = "部门负责人";
l_deptTel_M04 = "部门电话";
l_fax_M04 = "部门传真";
l_deptMemo_M04 = "部门描述";
//******* 部门信息录入(M04)页面结束 *******//

//******* 部门信息管理(M05)页面开始 *******//
l_M05 = "部门信息管理";
l_deptModify_M05 = "修改部门信息";
l_deptName_M05 = "部门名";
l_deptTel_M05 = "部门电话";
l_deptManager_M05 = "部门负责人";
l_managerTel_M05 = "负责人电话";
l_deptFax_M05 = "部门传真";
l_deptMemo_M05 = "部门描述";
l_deptInfo_M05 = "部门信息一览";
msg_infoTitle_M05 = "提示";
msg_delSure_M05 = "您确定要删除吗？";
msg_delSuccess_M05 = "删除执行成功";
msg_delFailure_M05 = "该部门还有用户存在，不能删除";
//******* 部门信息管理(M05)页面结束 *******//
//******* 公告信息录入(M06)页面开始 *******//
l_M06 = "公告信息录入";
//******* 公告信息录入(M06)页面结束 *******//
//******* 公告信息管理(M07)页面开始 *******//
l_M07 = "公告信息管理";
l_M07 = "公告信息查看";
l_M073 = "公告信息一览";
l_M071 = "公告信息发布";
l_M072 = "公告信息修改";
l_TITLE_M07 = "公告标题";
l_ENTRYDATE_M07 = "公告发布日期";
l_INFOTITLE_M07 = "公告标题";
l_DATA_M07 = "发布日期";
l_NOTICER_M07 = "发布人";
l_ENTRYLIMIT_M07 = "公告有效期";
l_FILENO_M07 = "编号";
l_FILENAME_M07 = "附件名称";
l_MESSAGE_M07 = "消息内容";
l_MESSAGEALERT_M07 = "请选择一个文件上传...";
l_ABOUTIT_M07 = "公告附件";
l_VIEW_M07 = "浏览";
l_ALERTWRONG_M07 = "附件只能上传pdf|xls|doc|rar类型文档！";
l_ALERTWRONG_M06 = "注:附件最大不超过5M";
l_M07 = "公告信息管理";
//******* 公告信息管理(M07)页面结束 *******//
/******************* 系统管理 end**********************/

/******************* 基础主数据维护 start**********************/
//******* 卸货口检索(B01)页面开始 *******//
l_workCenter_warn_B01="工作中心不能为空";
l_arrivalWarehouse_warn_B01="到货仓库不能为空";
l_dischargePort_warn_B01="卸货口不能为空";
l_B01 = "卸货口检索";
l_workCenter_B01 = "工作中心";
l_dischargePort_B01 = "卸货口";
l_isDefaulf_B01 = "是否是默认卸货口";
l_regexText_B01 = "该输入项只能英文和数字";
l_arrivalId_B01 = "到货工厂";
l_arrivalWarehouse_B01 = "到货仓库";
l_dischargePlace_B01 = "卸货场所";
l_sortPlace_B01 = "分拣区";
l_isDefaultUnloadPort_B01 = "是否是默认卸货口";
l_dischargeDataView_B01 = '卸货口数据一览';
l_dischargeport_modify_B01 = "卸货口数据修改";
l_dischargeport_new_B01 = "卸货口数据新增";
l_search_B01 = "检索";
l_save_B01 = "保存";
l_clear_B01 = "关闭";
l_arrivalPo_B01 = "到货工厂";
l_delate_B01 = "你是否要删除?";
l_theUnloadPortIsInUse_B01 = "该卸货口正在被使用不能删除！";
l_infoDelFailure_B01 = "信息删除失败";
l_isDefaulfTime_B01 = "是默认工作时间";
//******* 卸货口检索(B01)页面结束 *******//

//******* 需求来源检索(B04)页面开始 *******//
l_B04="需求来源检索";
l_unloadPort_B04="卸货口";
l_demandSrcDataView_B04="需求来源数据一览";
l_unloadPort_B04="卸货口";
l_unloadPortName_B04="卸货场所";
l_frequencyDaily_B04="每日趟次";
error_MaxValueText_B04="该输入项最大值为999";
win_demandSrc_edit_B04="修改需求来源信息";
win_demandSrc_add_B04="新增需求来源信息";

//******* 需求来源检索(B04)页面结束 *******//

//******* 需求来源查看(B05)页面开始 *******//
//******* 需求来源查看(B05)页面结束 *******//

//******* 需求来源添加(B06)页面开始*******//
//******* 需求来源添加(B06)页面结束*******//

//******* 需求来源修改(B07)页面开始 *******//
//******* 需求来源修改(B07)页面结束 *******//

//******* 计算范围检索(B08)页面开始 *******//
l_supplierCode_B08="供应商代码";
l_factoryCode_B08="厂家代码";
l_unloadPort_B08="卸货口";
l_calcScopeMaindataAdd_B08="计算范围主数据添加";
l_calcParameterAdd_B08="计算范围参数添加";
l_planRealViewAdd_B08="计划实际数据添加";
l_day_B08="日";
l_week_B08="周";
l_aogFactory_B08="到货工厂";
l_span_unit_B08="计算范围结束点单位";
l_span_quantity_B08="计算范围结束点";
l_time_B08 = "计算范围结束点时间";
l_cal_maindata_modify_B08="计算范围主数据修改";
l_cal_maindata_inspect_B08="计算范围主数据查看";
l_cal_maindata_out_view_B08="计算范围主数据一览";
l_cal_maindata_insert_B08="计算范围主数据添加";
l_cal_maindata_query_B08="计算范围主数据检索";
//******* 计算范围检索(B08)页面结束 *******//

//******* 计算范围添加(B09)页面开始 *******//
l_supplierCode_B09="供应商代码";
l_factoryCode_B09="厂家代码";
l_unloadPort_B09="卸货口";
l_aogFactory_B09="到货工厂";
l_span_unit_B09="计算范围结束点单位";
l_span_quantity_B09="计算范围结束点";
l_time_B09 = "计算范围结束点时间";
l_cal_maindata_insert_B09="计算范围主数据添加";
//******* 计算范围添加(B09)页面结束 *******//

// ******** 生产提前期维护（B10）开始*******//
l_workCenter_B10 = "工作中心";
l_produceAdvanceTime_B10 = "生产提前期";
l_advence_B10="生产提前期信息一览";
l_max_B10 = " 输入的最大值超过了9999.9";
// ******** 生产提前期维护（B10）结束*******//

/******************* 基础主数据维护 end**********************/

/******************* 生产计划管理 start**********************/
//******* 工作时间检索(P01)页面开始 *******//
l_P01 = "工作时间检索";
l_workCenter_P01 = "工作中心";
l_order_P01 = "班次";
l_search_P01 = "检索";
l_clear_P01 = "关闭";
l_week_P01 = "日期（周）";
l_orderClass_P01 = "班次";
l_workTime_P01 = "工作时间";
l_workTime1_P01 = "第一班工作时间";
l_workTime2_P01 = "第二班工作时间";
l_workTime3_P01 = "第三班工作时间";
l_workTime4_P01 = "第四班工作时间";
l_startTime_P01 = "起始时间";
l_endTime_P01 = "结束时间";
l_worktimeView_P01 = "工作时间信息一览";
l_save_P01 = "保存";
l_worktime_modify_P01 = "工作时间修改";
error_MaxValueText_P01 = "输入数字必须小于100";
l_worktime_new_P01 = "工作时间添加";
l_insertSuccess_P01 = "插入工作时间成功";
//******* 工作时间检索(P01)页面结束 *******//

//******* 新增工作时间数据(P02)页面开始 *******//
l_worktimeNotAllowNull_P02 = "工作时间不能为空！";
l_msg1_P02 = "第一班工作时间结束时间不能早于第一班工作时间开始时间！";
l_msg2_P02 = "第二班工作时间开始时间不能早于第一班工作时间结束时间！";
l_msg3_P02 = "第二班工作时间结束时间不能早于第二班工作时间开始时间！";
l_msg4_P02 = "第三班工作时间开始时间不能早于第二班工作时间结束时间！";
l_msg5_P02 = "第三班工作时间结束时间不能早于第三班工作时间开始时间！";
l_msg6_P02 = "第四班工作时间开始时间不能早于第三班工作时间结束时间！";
l_msg7_P02 = "第四班工作时间结束时间不能早于第四班工作时间开始时间！";
l_msg8_P02 = "工作时间结束时间不能早于工作时间开始时间！";
//******* 新增工作时间数据(P02)页面结束 *******//

//******* 生产计划调整(P04)页面开始 *******//
//******* 生产计划调整(P04)页面结束 *******//

//******* 整车日排序汇总(P05)页面开始 *******//
l_P05="整车日排序汇总";
l_workCenter_P05 = "工作中心";
l_createDate_P05='创建时间';
l_date_P05 = "日期";
l_query_P05 = "检索";
l_mtoId_P05 = '整车代码';
l_num_P05 = '数量';
l_yilan_P05="整车日排序汇总一览";
l_chakan_P05 = '整车日排序汇总查看';
//******* 整车日排序汇总(P05)页面结束 *******//

//******* 生产订单管理(P06)页面开始 *******//
l_P06="生产订单管理";
l_query_P06 = "检索";
l_clear_P06 = "清空";
l_queryyilan_Q07="估计材料";
l_operate_Q07="操作";
l_yes_Q07="是";
l_gujicailiao_Q07="估计材料(生产订单)";
l_production_P06 = "生产订单";
l_producjianhao_P06 = "生产件号";
l_producdate_P06 = "生产起始日期";
l_producname_P06 = "生产件名称";
l_ordernum_P06="订单数量";
l_jihuajiaohuo_P06="计划交货日期";
l_cangku_P06="仓库";
l_back_P06="返工单";
l_status_P06="订单状态";
l_calstatus_P06="计算状态";
l_partOrderMainDataDetail_P06 = '生产订单一览';
//******* 生产订单管理(P06)页面结束 *******//

//******* 估计材料（生产订单）(P07)页面开始 *******//
l_P07 = '估计材料（生产订单）';
l_production_P07 = "生产订单";
l_producjianhao_P07 = "生产件号";
l_producdate_P07 = "生产起始日期";
l_producname_P07 = "生产件名称";
l_ordernum_P07="订单数量";
l_jihuajiaohuo_P07="计划交货日期";
l_cangku_P07="仓库";
l_back_P07="返工单";
l_status_P07="订单状态";
l_failure_P07="对不起，数据加载失败";
l_compxuqiu_P07="估计材料";
l_location_P07 ="位置";
l_sequence_P07 ="序号";
l_partNumber_P07="零件编号";
l_partNameCN_P07 ="零件名称";
l_partSimpleName_P07 = '零件简号';
l_centerName_P07 ="工序落点";
l_planNum_P07 = '计划数量';
l_partUnit_P07 = '零件单位';
l_lblDaoHuoShCode_P07="到货工厂";
l_unLoadPort_P07 = '卸货口';
l_fenJianQu_P07 = '分拣区';
l_unloadPalce_P07="卸货场所";
l_jiSuanStatus_P07="计算状态";
//******* 估计材料（生产订单）(P07)页面结束 *******//
/******************* 生产计划管理 end**********************/

/******************* 订购主数据管理 start**********************/
//******* 零件订购主数据检索(D01)页面开始 *******//
l_D01 = '零件订购主数据检索';
l_partCode_D01 = '零件编号';
l_partSimpleName_D01 = '零件简号';
l_supplierCode_D01 = '供应商代码';
l_partName_D01 = '零件名称';
l_partUnit_D01 = '零件单位';
l_factoryCode_D01 = '厂家代码';
l_factoryName_D01 = '厂家名称';
l_aogFactory_D01 = '到货工厂';
l_unLoadPort_D01 = '卸货口';
l_itemCalculateNum_D01 = '提前台套数';
l_itemFirstSortId_D01 = '初始SORTID';
l_packQuantity_D01 = '标准包装';
l_orderPackQuantity_D01 = '订购包装';
l_dataOrigin_D01 = '数据来源';
l_itemCalculateDiffType_D01 = '差异处理类型';
l_partSearch_D01 = '按零件查询';
l_supplierSearch_D01 = '按供应商查询';
l_partOrderMainDataDetail_D01 = '零件订购主数据一览';
//******* 零件订购主数据检索(D01)页面结束 *******//

/******************* 订购主数据管理 start**********************/
//******* 零件订购主数据异常处理开始 *******//
l_partIdNew_D01 = '设变前物料';
l_groupId = '分组序号';
l_dr_sortId_s='D_R_起始号';
l_dr_sortId_e='D_R_截止号';
l_lr_sortId_s='L_R_起始号';
l_lr_sortId_e='L_R_截止号';
l_real_lr_sortId_e='L_R_实际截止号';
//******* 零件订购主数据异常处理页面结束 *******//

//******* 零件订购主数据查看(D02)页面开始 *******//
l_englishName_D02 = '英文名称';
l_partSingalWight_D02 = '零件单重';
l_package_D02 = '包装数';
l_orderPackage_D02 = '订购包装';
l_smallNum_D02 = '最小订货数量';
l_packageId_D02 = '包装规格ID';
l_jnCarName_D02 = '供应商名称';
l_factoryName_D02 = '厂家名称';
l_qsFlg_D02 = '物流标识（取/送）';
l_aogName_D02 = '到货商名称';
l_unLoadPortName_D02 = '卸货场所';
l_status_D02 = '状态';
l_doFlgStatus_D02 = '处理状态';
l_firstSelect_D02 = '首选';
l_priorityLevel_D02 = '优先级';
l_supplyRate_D02 = '供货比率';
l_purchaseDept_D02 = '采购部门';
l_verify_D02 = '检验';
l_effectiveDate_D02 = '生效日期';
l_expiryDate_D02 = '失效日期';
l_facInForwardTime_D02 = '厂内提前期';
l_facInForwardTimeUnit_D02 = '厂内提前期单位';

l_pProgress_D02 = '包装确认进度';
l_pStandardCode_D02 = '包装规格代码';
l_pType_D02 = '包装类型';
l_pNum_D02 = '包装数';
l_pLength_D02 = '包装长';
l_pWidth_D02 = '包装宽';
l_pHeight_D02 = '包装高';
l_pUnitVolume_D02 = '包装单元体积';
l_partNetWeight_D02 = '零件净重';
l_packetNetWeight_D02 = '包装净重';
l_pUnitWeight_D02 = '包装单元重量';
l_packetRemark_D02 = '包装备注';

l_partIdCheck_D02 = '该输入项只能包含字符,数字和-';

l_close_D02 = '关闭';
l_partData_D02 = '零件数据';
l_buyData_D02 = '采购数据';
l_drData_D02 = 'D-RANGE数据';
l_lrData_D02 = 'L-RANGE数据';
l_wlData_D02 = '物流数据';
l_partOrderMainDataView_D02 = '零件订购主数据查看';
l_partOrderMainDataAdd_D02 = '零件订购主数据新增';
l_day_D02 = '天';
l_hour_D02 = '小时';
//******* 零件订购主数据查看(D02)页面结束 *******//

//******* 零件订购主数据修改(D17)页面开始 *******//
l_partOrderMainDataModify_D17 = '零件订购主数据修改';
l_updateConfirm_D17 = '您确定要保存吗？';
l_updateSuccess_D17 = '零件订购主数据修改成功！';
//******* 零件订购主数据修改(D17)页面结束 *******//

//******* 零件订购主数据新增(D18)页面开始 *******//
l_facInForwardTimeUnit_D18 = '厂内提前期单位';
l_clearForm_D18 = '清空';
l_txtQhValidate_D18 = '物流标识（取/送）的值只能为GAM！';
l_dateValidate_D18 = '失效日期必须大于或等于生效日期！';
//******* 零件订购主数据新增(D18)页面结束 *******//

//******* 供应商主数据检索(D03)页面开始 *******//
l_supplierCode_D03 = '供应商代码';
l_supplierName_D03 = '供应商名称';
l_factoryCode_D03 = '厂家代码';
l_factoryName_D03 = '厂家名称';
l_aogFactory_D03 = '到货工厂';
l_unLoadPort_D03 = '卸货口';
l_supplierName_D03 = '供应商名称';
l_factoryName_D03 = '厂家名称';
l_aogName_D03 = '到货商名称';
l_supplierMainDataView_D03 = '供应商主数据一览';
l_supplierMainDataSearch_D03 = '供应商主数据检索';
l_calculateType_D03 = '计算类型';
l_carType_D03 = '车型';
l_calculateNum_D03 = '台套数';
l_calculateNumNotNullOrZero_D03 = "台套数不能为空或零。";
//******* 供应商主数据检索(D03)页面结束 *******//

//******* 供应商主数据修改(D04)页面开始 *******//
l_readyTime_D04 = '准备时间';
l_supplyTime_D04 = '供应时间';
l_safeTime_D04 = '安全时间';
l_unLoadPortName_D04 = '卸货场所';
l_supplierInfoEdit_D04 = '供应商信息修改';
//******* 供应商主数据查看(D04)页面结束 *******//

//******* 积载率提高检索(D05)页面开始 *******//
l_D05="积载率提高检索";
l_supplierId_D05="供应商代码";
l_unloadPort_D05="卸货口";
l_groupId_D05="组合代码";
l_factoryId_D05="厂家代码";
l_aogFactory_D05="到货工厂";
l_loadBox_D05="装载箱数";
l_accumulateAriseView_D05="积载率提高一览";
tab_accumulateAriseAdd_D05="积载率提高添加";
tab_accumulateAriseView_D05="积载率提高查看";
tab_accumulateAriseModify_D05="积载率提高修改";
//******* 积载率提高检索(D05)页面结束 *******//

//******* 积载率提高查看(D06)页面开始 *******//
l_D06 = "积载率提高信息";
l_supplierId_D06="供应商代码";
l_unloadPort_D06="卸货口";
l_groupId_D06="组合代码";
l_factoryId_D06="厂家代码";
l_aogFactory_D06="到货工厂";
l_loadBox_D06="装载箱数";
l_componentView_D06="同组零件一览";
l_partId_D06="零件编号";
l_partNameCn_D06="零件名称";
tab_accumulateAriseView_D06="积载率提高查看";
tab_accumulateAriseModify_D06="积载率提高修改";
l_baseInfo_D06="基本信息";
//******* 积载率提高查看(D06)页面结束 *******//

//******* 积载率提高添加(D07)页面开始 *******//
l_D07 = "请先输入装载分组基本信息";
l_supplierId_D07="供应商代码";
l_supplierName_D07="供应商名称";
l_unloadPort_D07="卸货口";
l_unloadPalce_D07="卸货场所";
l_groupId_D07="组合代码";
l_factoryId_D07="厂家代码";
l_factoryName_D07="厂家名称";
l_aogFactory_D07="到货工厂";
l_loadBox_D07="装载箱数";
error_MaxValueText_D07="该输入项最大值为9999";
l_componentView_D07="同组零件一览";
l_componentNo_D07="零件编号";
l_componentName_D07="零件名称";
l_add_D07="添加";
l_save_next_D07="保存并下一步";
tab_accumulateAriseAdd_D07="积载率提高添加";
tab_accumulateAriseModify_D07="积载率提高修改";
//******* 积载率提高添加(D07)页面结束 *******//

//******* 积载率提高修改(D08)页面开始 *******//
l_D08 = "积载率提高修改";
l_supplierId_D08="供应商代码";
l_unloadPort_D08="卸货口";
l_groupId_D08="组合代码";
l_factoryId_D08="厂家代码";
l_aogFactory_D08="到货工厂";
l_loadBox_D08="装载箱数";
l_componentView_D08="同组零件一览";
l_partId_D08="零件编号";
l_partNameCn_D08="零件名称";
l_add_D08="添加";
tab_accumulateAriseModify_D08="积载率提高修改";
alert_NoModify_D08="没有修改任何内容!";
alert_MaxValue_D08="该输入项最大值为9999";
win_addPart_D08="新增同组零件";
l_baseInfo_D08="基本信息";
//******* 积载率提高修改(D08)页面结束 *******//

//******* 加工不良基准检索(D09)页面开始  *******//
l_D09 = "加工不良基准检索";
l_partCode_D09 = "零件编号";
l_partCode_simple_D09 = "零件简号";
l_supplier_Code_D09 = "供应商代码";
l_unLoad_port_D09 = "卸货口";
l_process_main_data_view_D09 = "加工不良基准主数据一览";
l_partName_D09 = "零件名称";
l_factoryName_D09 = "厂家代码";
l_AogFactory_D09 = "到货工厂";
l_orderBox_D09 = "订货点（箱）";
l_Max_D09 = "最大量（箱）";
//******* 加工不良基准检索(D09)页面结束  *******//

//******* 加工不良基准查看(D10)页面开始  *******//
l_D10 = "加工不良基准查看";
//******* 加工不良基准查看(D10)页面结束  *******//

//******* 加工不良基准添加(D11)页面开始  *******//
l_D11 = "加工不良基准添加";
l_message_D11 = "请输入加工不良设定信息：";
l_messageDescript_D11 = "不特别指定时，零件编号，供应商代码，厂家代码，卸货口代码可以输入*。";
//******* 加工不良基准添加(D11)页面结束  *******//

//******* 加工不良基准修改(D12)页面开始  *******//
l_D12 = "加工不良基准修改";
l_save_D12 = "保存";
//******* 加工不良基准修改(D12)页面结束  *******//

//******* 物料清单检索(D13)页面开始  *******//
//******* 物料清单检索(D13)页面结束  *******//

//******* 物料清单查看(D14)页面开始  *******//
//******* 物料清单查看(D14)页面结束  *******//

//******* 物料清单修改(D15)页面开始  *******//
//******* 物料清单修改(D15)页面结束  *******//
/******************* 订购主数据管理 end**********************/

/******************* 订购时间线管理 start**********************/
//******* 订单时间线检索 (T01)页面开始  *******//
l_orderLineType_T01="订单时间线类型";
/***yanqiang  start***/
//订单时间线管理
l_supplierId_T01 = "供应商代码";
l_unloadPort_T01 = "卸货口";
l_orderNo_T01 = "物流订单号";
l_dataSrc_T01 = "数据来源";
l_search_T01 = "检索";
l_timeLineDataView_T01 = "订单时间线数据一览";
l_factoryId_T01 = "厂家代码";
l_factoryName_T01 = "厂家名称";
l_aogFactory_T01 = "到货工厂";
l_unloadPort_T01 = "卸货口";
l_lblUnit_T01="TL周期";
l_logisticsOrder_T01 = "物流订单号";
l_routeName_T01 = "路线名";
l_routeNum_T01 = "路线趟次";
l_routeFrequency_T01 = "路线频次";
l_routeArriveDate_T01 = "路线到货日";
l_travelSeq_T01 = "行驶SEQ";
l_logisticsPoint_T01 = "物流据点名称";
l_trackPoint_T01 = "TrackPoint";
l_isArriveDelivery_T01 = "A/D";
l_planTime_T01 = "计划时间";
l_T01 = "订单时间线检索";
error_MaxValueText_T01 = "输入数字必须小于100";
l_save_T01 = "保存";
l_clear_T01 = "关闭";
l_timeLine_modify_T01 = "订单时间线修改";
l_planTime_T01 = "计划时间";
l_routeArriveDate_T01 = "路线到货日";
l_canNotModifyDate_T01 = "不能修改计划时间的日期部分！";
l_tipTitle_T01 = "提示";
l_chooseOneData_T01 = "请至少选中一条数据后再进行批量删除！";
l_confirmBatchDelete_T01 = "您确定要进行批量删除吗？";
l_batchDeleteSuccess_T01 = "批量删除成功！";
l_batchDeleteFailure_T01 = "批量删除失败！";

/*yanqiang end**/
//******* 订单时间线检索 (T01)页面结束  *******//

//******* 订单时间线查看 (T02)页面开始  *******//
//******* 订单时间线查看 (T02)页面结束  *******//

//******* 订单时间线导入 (T03)页面开始  *******//
l_T03="订单时间线导入 ";
//******* 订单时间线导入 (T03)页面结束  *******//

//******* 订单时间线修改 (T04)页面开始  *******//
//******* 订单时间线修改 (T04)页面结束  *******//

//******* 简易订单时间线检索 (T05)页面开始  *******//
l_T05="简易订单时间线检索";
l_id_T05="流水号";
l_orderView_T05="简易时间线一览";
l_supplierId_T05="供应商代码";
l_supplierName_T05="供应商名称";
l_factoryId_T05="厂家代码";
l_factoryName_T05="厂家名称";
l_unLoadPort_T05="卸货口";
l_unLoadPlace_T05="卸货口名称";
l_unLoad_T05="卸货场所";
l_frequence_T05="供应商发货趟次";
l_planTime_T05="供应商发货时间";
l_recSupplierId_T05="到货工厂";
alert_MaxValue_T05="该输入项最大值为999";
alert_orderTime_T05="简易订单时间线添加";
modify_orderTime_T05="简易时间线修改";
modify_manage_T05="简易订单时间线修改";
view_manage_T05="简易订单时间线查看";
add_orderTime_T05="简易时间线添加";
error_format_T05="输入格式错误";
//******* 简易订单时间线检索 (T05)页面结束  *******//
//******* 简易订单时间线查看 (T06)页面开始  *******//
tab_orderInspect_T06="简易订单时间线查看";
l_supplierId_T06="供应商代码";
l_supplierName_T06="供应商名称";
l_factoryId_T06="厂家代码";
l_factoryName_T06="厂家名称";
l_unLoadPort_T06="卸货口";
l_unLoadPlace_T06="卸货口名称";
l_recSupplierId_T06="到货工厂";
l_orderInspect_T06="简易订单时间线查看";

l_routeNum_T06="发货趟次";
l_send_T06="发货趟次";
l_lblFromToTime_T06="适用时间段";
l_lblUnit_T06 = "匹配单位";
l_weeklyTime_T06="按周发货时间";
l_weekDay_T06="星期";
l_weekRouteNum_T06="趟次";
l_weekRouteTime_T06="趟次时间";

//******* 简易订单时间线查看 (T06)页面结束  *******//

//******* 简易订单时间线添加 (T07)页面开始  *******//
msg_error_NoInfo_T07="您没有添加任何趟次信息,请检查!";
msg_error_Repeat_T07="相同的数据已经存在,请检查";
tab_orderAdd_T07="简易订单时间线添加";
win_addRouteTime_T07="新增趟次";
win_modifyRouteTime_T07="修改趟次";
l_routeNum_T07="到货趟次";
l_send_T07="发货趟次";
l_fromToTime_T07="适用时间段";
l_cmbUnit_T07 = "匹配单位";
l_weeklyTime_T07="按周发货时间";
l_weekDay_T07="星期";
l_weekRouteNum_T07="趟次";
l_weekRouteTime_T07="趟次时间";
l_di_T07="第";
l_ciTime_T07="次时间";
l_confirmDelete_T07="确认删除吗？";
l_timeMustBigThan_T07="次时间必须大于第";

//******* 简易订单时间线添加 (T07)页面结束  *******//
//******* 简易订单时间线修改 (T08)页面开始  *******//
tab_orderModify_T08="简易订单时间线修改";
win_addRouteTime_T08="新增趟次";
l_supplierId_T08="供应商代码";
l_supplierName_T08="供应商名称";
l_factoryId_T08="厂家代码";
l_factoryName_T08="厂家名称";
l_unLoadPort_T08="卸货口";
l_unLoadPlace_T08="卸货口名称";
l_recSupplierId_T08="到货工厂";


l_routeNum_T08="发货趟次";
l_send_T08="发货趟次";
l_fromToTime_T08="适用时间段";
l_cmbUnit_T08 = "匹配单位";
l_weeklyTime_T08="按周发货时间";
l_weekDay_T08="星期";
l_weekRouteNum_T08="趟次";
l_weekRouteTime_T08="趟次时间";

//******* 简易订单时间线修改 (T08)页面结束  *******//
/******************* 订购时间线管理 end**********************/

/******************* 订购数量调整 start**********************/
//******* 手工调整 (Q01)页面开始  *******//
l_Q01="系统自动运行时间段设定";
l_cbxEffect_Q01="时间生效";
l_tfdPlanTime_Q01="净需求计算时间";
l_tfdERPOrderTime_Q01="生成ERP采购订单时间";
l_cal_demand_Q01="验证零件、供应商及卸货口信息了吗？";
//******* 手工调整 (Q01)页面结束  *******//

//******* 加工不良记录检索(Q02)页面开始  *******//
l_partId_Q02="零件编号";
l_partNameCn_Q02="零件名称";
l_partName_Q02="零件简号";
l_supplierId_Q02="供应商代码";
l_supplierName_Q02="供应商名称";
l_factoryId_Q02="厂家代码";
l_factoryName_Q02="厂家名称";
l_unLoadPort_Q02="卸货口";
l_unLoadPalce_Q02="卸货场所";
l_recSupplierId_Q02="到货工厂";
l_badNum_Q02="加工不良数量";
l_partUnit_Q02="零件单位";
l_time_Q02="时间";
l_request_Q02="是否已生成需求";
l_reserch_Q02="查询条件";
l_title_Q02="加工不良记录检索";
l_modify_Q02="加工不良记录修改";
l_add_Q02="加工不良记录添加";
l_import_Q02="加工不良记录导入";
l_inspect_Q02="加工不良记录查看";
l_failure_Q02="对不起，数据加载失败";
l_eimport_Q02="Excel导入";
l_view_Q02="加工不良记录一览";
//******* 加工不良记录检索(Q02)页面结束  *******//

//******* 加工不良记录查看(Q03)页面开始  *******//
//******* 加工不良记录查看(Q03)页面结束  *******//

//******* 加工不良记录添加(Q04)页面开始  *******//
l_partId_Q04="零件编号";
l_partName_Q04="零件名称";
l_supplierId_Q04="供应商代码";
l_supplierName_Q04="供应商名称";
l_factoryId_Q04="厂家代码";
l_factoryName_Q04="厂家名称";
l_unLoadPort_Q04="卸货口";
l_unLoadPalce_Q04="卸货场所";
l_recSupplierId_Q04="到货工厂";
l_badNum_Q04="加工不良数量";
l_partUnit_Q04="零件单位";
l_time_Q04="时间";
l_title_Q04="加工不良记录添加";
//******* 加工不良记录添加(Q04)页面结束  *******//

//******* 加工不良记录修改(Q05)页面开始  *******//
//******* 加工不良记录修改(Q05)页面结束  *******//

//******* 加工不良记录导入(Q06)页面开始  *******//

l_Q06 = "加工不良记录导入";
l_defectiveFileImport_Q06 = "选择文件";
t_defectiveFileImport_Q06 = "加工不良记录导入";
error_formatError_Q06 = "格式不正确,请检查文件是否是CSV格式!";
l_confirmImport_Q06 = "您确定要导入数据吗？";
l_waitMsg_Q06 = "正在导入数据,请稍候...";
l_partId_Q06="零件编号";
l_partName_Q06="零件名称";
l_supplierId_Q06="供应商代码";
l_supplierName_Q06="供应商名称";
l_factoryId_Q06="厂家代码";
l_factoryName_Q06="厂家名称";
l_unLoadPort_Q06="卸货口";
l_unLoadPalce_Q06="卸货场所";
l_recSupplierId_Q06="到货工厂";
l_badNum_Q06="加工不良数量";
l_partUnit_Q06="零件单位";
l_time_Q06="时间";
l_title_Q06="加工不良记录添加";
l_importSuccess_Q06="数据导入成功。";
l_importFailure_Q06="数据导入失败。";
l_importRepeat_Q06="该文件您今天已经导入过一次,您确定继续导入吗?<br>如果继续导入,您之前导入的数据将被删除并不可还原!";
l_importRepeat1_Q06="您今天已经导入过该文件,并且已经生成需求<br>您不能再导入该文件了!";

//******* 加工不良记录导入(Q06)页面结束  *******//

//******* 剩余量检索(Q07)页面开始  *******//
l_Q07="剩余量检索";
l_view_Q07 = "剩余量查看";
l_modify_Q07 = "剩余量修改";
l_add_Q07 = "剩余量添加";
l_partId_Q07="零件编号";
l_partName_Q07="零件名称";
l_partProfileId_Q07 = '零件简号';
l_supplierId_Q07="供应商代码";
l_unLoadPort_Q07="卸货口";
l_factoryId_Q07="厂家代码";
l_necessaryOrderResidual_Q07 = '必要数订购剩余量';
l_defectiveOrderResidual_Q07 = '加工不良库存量';
l_planChangeResidual_Q07 = '计划变更剩余量 ';
l_queryyilan_Q07="剩余量检索一览";
//******* 剩余量检索(Q07)页面结束  *******//

//******* 剩余量查看(Q08)页面开始  *******//
l_Q08="剩余量查看";
l_partId_Q08="零件编号";
l_partName_Q08="零件名称";
l_supplierId_Q08="供应商代码";
l_supplierName_Q08="供应商名称";
l_factoryId_Q08="厂家代码";
l_factoryName_Q08="厂家名称";
l_unLoadPort_Q08="卸货口";
l_unLoadPalce_Q08="卸货场所";
l_lblDaoHuoShCode_Q08="到货工厂";
l_necessaryOrderResidual_Q08 = '必要数订购剩余量';
l_defectiveOrderResidual_Q08 = '加工不良库存量';
l_closed_Q08 = '关闭';
l_view_Q08 = "剩余量查看";
l_failure_Q08="对不起，数据加载失败";
//******* 剩余量查看(Q08)页面结束  *******//

//******* 剩余量添加(Q09)页面开始  *******//
l_Q09="剩余量添加";
l_partId_Q09="零件编号";
l_partName_Q09="零件名称";
l_supplierId_Q09="供应商代码";
l_supplierName_Q09="供应商名称";
l_factoryId_Q09="厂家代码";
l_factoryName_Q09="厂家名称";
l_unLoadPort_Q09="卸货口";
l_unLoadPalce_Q09="卸货场所";
l_lblDaoHuoShCode_Q09="到货工厂";
l_necessaryOrderResidual_Q09 = '必要数订购剩余量';
l_defectiveOrderResidual_Q09 = '加工不良库存量';
l_save_Q09 = "保存";
l_minTip_Q09 = "该输入项目最小值为-9999";
l_maxTip_Q09 = "该输入项目最大值为9999";
//******* 剩余量添加(Q09)页面结束  *******//

//******* 剩余量修改(Q10)页面开始  *******//
l_Q10="剩余量修改";
l_partId_Q10="零件编号";
l_partName_Q10="零件名称";
l_supplierId_Q10="供应商代码";
l_supplierName_Q10="供应商名称";
l_factoryId_Q10="厂家代码";
l_factoryName_Q10="厂家名称";
l_unLoadPort_Q10="卸货口";
l_unLoadPalce_Q10="卸货场所";
l_lblDaoHuoShCode_Q10="到货工厂";
l_necessaryOrderResidual_Q10 = '必要数订购剩余量';
l_defectiveOrderResidual_Q10 = '加工不良库存量';
l_save_Q10 = "保存";
l_failure_Q10="对不起，数据加载失败";
//******* 剩余量修改(Q10)页面结束  *******//

//******* 库存个数检索(Q11)页面开始  *******//
//******* 库存个数检索(Q11)页面结束  *******//

//******* 库存个数查看(Q12)页面开始  *******//
//******* 库存个数查看(Q12)页面结束  *******//

//******* 库存个数添加(Q13)页面开始  *******//
//******* 库存个数添加(Q13)页面结束  *******//

//******* 库存个数修改(Q14)页面开始  *******//
//******* 库存个数修改(Q14)页面结束  *******//

//******* 库存箱数检索(Q15)页面开始 ***//
//******* 库存箱数检索(Q15)页面结束 ***//

//******* 库存箱数查看(Q16)页面开始  *******//
//******* 库存箱数查看(Q16)页面结束  *******//

//******* 库存箱数添加(Q17)页面开始  *******//
//******* 库存箱数添加(Q17)页面结束  *******//

//******* 库存箱数修改(Q18)页面开始  *******//
//******* 库存箱数修改(Q18)页面结束  *******//

//******* 打切订购数量检索(Q19)页面开始  *******//
l_partId_Q19="零件编号";
l_partSimpleId_Q19="零件简号";
l_supplierCode_Q19="供应商代码";
l_factoryCode_Q19="厂家代码";
l_aogFactory_Q19="到货工厂";
l_unloadPort_Q19="卸货口";
l_cutoffNum_Q19="打切数量";
l_cutoffResidual_Q19="打切剩余量";
l_cutoffStartTime_Q19="打切开始时间";
l_queryCondition_Q19="查询条件";
l_cutoffOrderQuantity_outview_Q19="打切订购数量查看";
l_cutoffOrderQuantity_modify_Q19="打切订购数量修改";
l_cutoffOrderQuantity_add_Q19="打切订购数量添加";
l_cutoffOrderQuantity_List_Q19="打切订购数量一览";
l_cutoffOrderQuantity_query_Q19="打切订购数量检索";
//******* 打切订购数量检索(Q19)页面结束  *******//

//******* 打切订购数量查看(Q20页面开始  *******//
l_partId_Q20="零部件号";
l_partName_Q20="零部件名称";
l_supplierCode_Q20="供应商代码";
l_supplierName_Q20="供应商名称";
l_factoryCode_Q20="厂家代码";
l_factoryName_Q20="厂家名称";
l_unloadPortCode_Q20="卸货口代码";
l_unloadPortName_Q20="卸货口名称";
l_aogFactory_Q20="到货工厂";
l_cutoffStartTime_Q20="打切开始时间";
l_cutoffNum_Q20="打切数量";
l_cutoffResidual_Q20="打切剩余量";
l_partUnit_Q20="零件单位";
l_close_Q20="关闭";
l_cutoffOrderQuantityView_Q20="打切订购数量查看";
//******* 打切订购数量查看(Q20页面结束  *******//

//******* 打切订购数量添加(Q21)页面开始  *******//
l_partId_Q21="零部件号";
l_partName_Q21="零部件名称";
l_aogFactory_Q21="到货工厂";
l_cutoffStartTime_Q21="打切开始时间";
l_cutoffNum_Q21="打切数量";
l_cutoffResidual_Q21="打切剩余量";
l_partUnit_Q21="零件单位";
l_save_Q21="保存";
l_partId_not_null_Q21="零部件号不能为空！";
l_supplierCode_not_null_Q21="供应商代码不能为空！";
l_factoryCode_not_null_Q21="厂家代码不能为空！";
l_unloadPort_not_null_Q21="卸货口不能为空！";
l_tip_Q21="提示";
l_tip_content_Q21="该记录已存在，你确定要插入吗";
l_info_success_Q21="信息保存成功！";
l_is_sure_save_Q21="你确定要保存";
l_cutoffOrderQuantity_add_Q21="打切订购数量添加";
l_inputContent_Q21="录入内容";
//******* 打切订购数量添加(Q21)页面结束  *******//

//******* 打切订购数量修改(Q22)页面开始  *******//
l_partId_Q22="零部件号";
l_partName_Q22="零部件名称";
l_supplierCode_Q22="供应商代码";
l_supplierName_Q22="供应商名称";
l_factoryCode_Q22="厂家代码";
l_factoryName_Q22="厂家名称";
l_unloadPortCode_Q22="卸货口代码";
l_unloadPortName_Q22="卸货口名称";
l_aogFactory_Q22="到货工厂";
l_cutoffStartTime_Q22="打切开始时间";
l_cutoffNum_Q22="打切数量";
l_cutoffResidual_Q22="打切剩余量";
l_partUnit_Q22="零件单位";
l_save_Q22="保存";
l_close_Q22="关闭";
l_cutoffStartTime_not_null_Q22="打切开始时间不能为空！";
l_cutoffOrderQuantity_modify_Q22="打切订购数量修改";
l_cutoffManage_Q22="打切管理";
//******* 打切订购数量修改(Q22)页面结束  *******//

//******* ERP采购订单生成（手工）(Q23)页面开始 ****//
l_supplierCode_Q23="供应商代码";  
l_dischargePort_Q23="卸货口";
l_orderNo_Q23="物流订单号";

l_orderIssueDate_Q23="订单发行日";
l_arrivalDate_Q23="GAMC到货日期";
l_orderGen_Q23="生成采购订单";
l_selectdeal_Q23="生成采购订单";
l_warnmessage_Q23="该处理范围PO数据不存在,不能生成采购订单！";
l_successmessage_Q23="订单生成成功！";
l_successTip_Q23="采购订单生成成功";
l_failmessage_Q23="采购订单生成失败！";
l_supplierIdCheck_Q23="供应商代码From不能大于供应商代码To！";
l_orderNoCheck_Q23="物流订单号From不能大于物流订单号To！";
l_disportCheck_Q23="卸货口From不能大于物流订单号To！";
//******* ERP采购订单生成（手工）(Q23)页面结束 *******//

//******* DMS计划订单接收（自动）(Q24)页面开始 *******//
//******* DMS计划订单接收（自动）(Q24)页面结束 *******//

//******* ERP计划订单导入（自动）(Q25)页面开始 *******//
//******* ERP计划订单导入（自动）(Q25)页面结束 *******//

//******* EPPS净需求计算（自动+手工）(Q26)页面开始 *******//
l_orderIssueDate_Q26="订单发行时间";
l_demandCal_Q26="净需求计算";
l_import_success_Q26="新计划已导入，请查询车辆规格信息";
l_success_Q26="计算成功，零件需求信息已生成";
l_false_Q26 = "计算失败";
l_false_importdemand_Q26 = "已经存在未计算的需求计划，是否确定导入新计划";
l_autoExecuteTimeSet_Q26 = "自动执行时间设置";
l_autoEffect_Q26 = "自动生效";
l_dataCheck_Q26 = "数据校验";
l_dataCheckSuccess_Q26 = "数据校验成功!";

l_lock_purchase_Q26 = "生成采购订单功能已被别的用户或进程启动，您现在不能使用，请稍等。";
l_lock_need_part_Q26 = "净需求计算功能已被别的用户或进程启动，您现在不能使用，请稍等。";
l_bad_data_materiel_Q26 = "物料清单中存在异常数据，不能进行净需求计算。";
l_bad_data_mrp_Q26 = "MRP计划订单中存在异常数据，不能进行净需求计算。";
l_bad_data_dms_Q26 = "DMS计划订单中存在异常数据，不能进行净需求计算。";
l_bad_data_produce_Q26 = "生产订单中存在异常数据，不能进行净需求计算。";
l_bad_data_pv_Q26 = "车辆规格查询中存在异常数据，不能进行净需求计算。";
l_bad_data_logistics_order_Q26 = "计划已生成订单，前端数据无法删除，不能进行净需求计算。";

//******* EPPS净需求计算（自动+手工）(Q26)页面结束 *******//
/******************* 订购数量调整 end**********************/

/******************* 数据查询 start**********************/
//******* 订单履历检索(S01)页面开始 *******//
l_S01 = '订单履历检索';
l_show_S01 = '提示';
l_saved_S01 = "你确定要保存吗？";
l_success_S01 = "信息已被保存";
l_false_S01 = "失败";
l_S00 = '订单履历修改';
l_supplierNo_S01 = '供应商代码';
l_planPickNo_S01 = '计划订单号';
l_unloadPort_S01 = '卸货口';
l_partId_S01 = '零件编号';
l_partProfileId_S01 = '零件简号';
l_partName_S01 = '零件名称';
l_orderStatus_S01 = '订单状态';
l_planOrderId_S01 = 'ERP采购订单号';
l_logisticsOrder_S01 = '物流订单号';
l_orderIssueDate_S01 = '订单发行日';
l_isCutoff_S01 = '打切';
l_planArrivalDate_S01 = '计划到货日期';
l_packageNum_S01 = '总订购箱数';
l_orderState_S01 = '订单状态';
l_totalOrderNum_S01 = '总订购数量';
l_orderNum_S01 = '订购数量';
l_gamcArriveTime_S01 = 'GAMC到货时间';
l_orderView_S01 = '订单履历信息一览';
l_orderRecordView_S01 = '订单履历查看';
l_creatERP_S01 = '生成ERP采购订单';
l_bigFuzhi_S01 = "输入的负值太大会造成总订购箱数为负，请重新输入！";
l_max_S01 = '手工调整数最大值为99999999！';
//******* 订单履历检索(S01)页面结束 *******//

//******* 订单履历查看(S02)页面开始 *******//
l_S02 = '订单履历查看';
l_purchaseDept_S02 = '采购部门';
l_factoryId_S02 = '厂家代码';
l_dmsOrderId_S02 = 'DMS订单号';
l_selectionZone_S02 = '分拣区';
l_unloadPlace_S02 = '卸货场所';
l_aogWarehouse_S02 = '到货仓库';
l_aogFactory_S02 = '到货工厂';
l_unloadPort_S02 = '卸货口';
l_erpPoId_S02 = 'ERP PO号';
l_erpPoRowNum_S02 = 'ERP PO行号';
l_partUnit_S02 = '零件单位';
l_packNum_S02 = '包装数量';
l_manualAdjustNum_S02 = '手工调整箱数';
l_necessaryNetNum_S02 = '必要数净数量';
l_necessaryOrderResidual_S02 = '必要数订购剩余量';
l_necessaryPlanNum_S02 = '必要数计划数量';
l_necessaryRealOrderNum_S02 = '必要数实际订购数量';
l_defectiveNetNum_S02 = '加工不良净数量';
l_defectiveOrderResidual_S02 = '加工不良库存量';
l_defectivePlanNum_S02 = '加工不良计划数量';
l_defectiveRealOrderNum_S02 = '加工不良实际订购数量';
l_notVehicleBQuantity_S02 = '非PV定量数量';
l_totalOrderNum_S02 = '总订购数量';
l_packUnitWeight_S02 = '包装单元重量';
l_packUnitVolume_S02 = '包装单元体积';
l_packSingleWeight_S02 = '包装单重';
l_packLength_S02 = '包装长';
l_packWidth_S02 = '包装宽';
l_packHeight_S02 = '包装高';
l_totalOrderBox_S02 = '总订购箱数';
l_planDeliveryTimeStr_S02 = '计划发货时间';
l_planArriveTimeStr_S02 = '计划到货时间';
l_shipmentTimeStr_S02 = '供应商发货时间';
l_firstTransitArriveTimeStr_S02 = '第一中转仓到货时间';
l_firstTransitDeliveryTimeStr_S02 = '第一中转仓发货时间';
l_secondTransitArriveTimeStr_S02 = '第二中转仓到货时间';
l_secondTransitDeliveryTimeStr_S02 = '第二中转仓发货时间';
l_gamcArriveTimeStr_S02 = 'GAMC到货时间';
l_GamcArrivalFreq_S02 = 'GAMC到货频次';
l_SupplierShippingFreq_S02 = '供应商发货频次';
l_collectGoodsLine_S02 = '厂家集货路线';
l_gamcArriveLine_S02 = 'GAMC到达路线';
l_info_S02 = '基本信息';
l_shipInfo_S02 = '采购信息';
l_partInfo_S02 = '零件信息';
l_logistic_S02 = '物流信息';
l_orderView_S02 = '订单履历查看';
l_orderNum_S02 = '订购数量';
l_shipmentTimeStr_S04 = '供应商发货时间不能为空！';
l_shipmentTimeStr_S03 = '第一中转仓到货时间应该比供应商发货时间晚,请重新输入！';
l_firstTransitArriveTimeStr_S03 = '第一中转仓发货时间应该比第一中转仓到货时间晚,请重新输入！';
l_secondTransitArriveTimeStr_S03 = '第二中转仓发货时间应该比第二中转仓到货时间晚,请重新输入！';
l_secondTransitDeliveryTimeStr_S03 = '第二中转仓到货时间应该比第一中转仓发货时间晚,请重新输入！';
l_secondTransitDeliveryTimeStrLate_S03 = 'GAMC到货时间应该比第二中转仓发货时间晚,请重新输入！';
//******* 订单履历查看(S02)页面结束 *******//
//******* 车辆总成查询(S04)页面开始 *******//
l_S04 = '车辆总成查询';
l_plan_qty_S04 ='计划日产量';
l_real_qty_S04 ='实际日产量';
l_plan_adjust_qty_S04='计划调整日产量';
l_real_adjust_qty_S04='实际调整日产量';
l_plan_real_adjust_modify_S04='计划实际调整修改';
l_plan_real_S04='计划/实际数据';
l_plan_real_adjust_S04='调整数据';
l_parameter_S04='参数范围';
l_parameterView_S04='参数一览表';
l_produceSeq_S04 = '产品变型号 ';
l_sn_S04 = '生产序列 ';
l_vin_S04 = '车身代码 ';
l_mtoId_S04 = '整车代码';
l_planUnloadTime_S04 = '总装下线时间';
l_isProduct_S04 = '是否试制';
l_getNewMes_S04 = '获取MES最新数据';
l_changeId_S04 = '产品变型号';
l_view_S04 = '车辆总成一览';
l_planRealView_S04 = '计划/实际调整一览';
l_planNum_S04 = '计划数量';
l_planFlg_S04 = '计划标识';
l_planState_S04 = '计划订单状态';
l_weldUnderlineTimeStr_S04 = '焊装下线时间';
l_paintUnderlineTimeStr_S04 = '凃装下线时间';
l_finalUnderlineTimeStr_S04 = '总装下线时间';
l_assemblyLine_S04 = '装配线';
l_isProduct_S04 = '是否试制';
l_assemblyStatus_S04 = '装配状态';
l_calculateStatus_S04 = '计算状态';
l_onOffStatus_S04 = '开关状态';
l_msg1_S04 = "该条记录已被更新(产品变型号：";
l_msg2_S04 = ")，请取最新数据!";
l_msg3_S04 = "插入估计材料失败！(产品变型号：";
//******* 整车日排序计划(S04)页面结束 *******//

//******* 估计材料（整车日）排序计划(S05)页面开始 *******//
l_S05 = '估计材料（整车日）排序计划';
l_view_S05 = '估计材料一览';
l_wareHouseId_S05 = '需求仓库';
l_sequence_S05 = '工序';
l_centerId_S05 = '工序落点';
l_wareHouseId1_S05 = '订货仓库';
l_aogFactory_S05 = '到货工厂';
l_isCount_S05 = '已计算';
l_aogWareHouse_S05 = '库区代码';
l_unloadPlace_S05 = '卸货场所';
//******* 估计材料（整车日）排序计划(S05)页面结束 *******//

//******* 零件需求数量检索(S06)页面开始 *******//

l_S06 = '零件需求数量检索';
l_planArriveTime_S06 = '计划到货日期';
l_demandOrigin_S06 = '来源';
l_isCalculate_S06 = '计算状态';
l_serch_S06 = '查询条件';
l_netQuantity_S06 = '计划数量';
l_partNeedsView = '零件需求数量一览';
l_partUnit_S06 = '零件单位';
l_orderDepot_S06 = '订货仓库';
l_date_S06 = '天';
l_hour_S06 = '小时';
l_getOrSend_S06 = '物流标识';
l_supplierPrepareTime_S06 = '准备时间';
l_transportTime_S06 = '供应时间';
l_inPlanForwardTime_S06 = '安全时间';
//******* 零件需求数量检索(S06)页面结束 *******//

//******* 零件需求数量查看(S07)页面开始 *******//
l_S07 = ' 零件需求数量查看';
l_BuyNo_S07 = '产品变型号';
l_aogFactory_S07 = '到货工厂';
//******* 零件需求数量查看(S07)页面结束 *******//

//******* 接口异常数据处理--物料清单查询(S08)页面开始 *******//
l_MaterielListTempView_S08 = '物料清单数据一览';
l_retry_S08 = '重试';
l_operate_S08 = '操作';
l_producePieceName_S08 = '生产件名称';
l_partName_S08 = '零件名称';
//******* 接口异常数据处理--物料清单查询(S08)页面结束 *******//

//******* 接口异常数据处理--车辆规格查询(S09)页面开始 *******//
l_VehicleStandardQuery_S09 = '车辆规格查询';
l_VehicleStandardView_S09 = '车辆规格一览';
l_stage_S09 = '阶段';
l_sn_S09 = 'SN';
l_verifyInfo_S09 = 'PV订单验证信息';
//******* 接口异常数据处理--车辆规格查询(S09)页面结束 *******//

//******* 接口异常数据处理--生产订单(S10)页面开始 *******//
l_produceOrderNo_S10 = '生产订单号';
l_verifyInfo_S10 = '生产订单验证信息';
//******* 接口异常数据处理--生产订单(S10)页面结束 *******//
/******************* 数据查询 end**********************/




/******* DMS厂外取货采购订单(E02)页面开始 *******/
l_serch_E02 = '查询条件 ';
l_supplyId_E02 = '供应商代码 ';
l_plantId_E02 = '厂家代码';
l_partId_E02 = '零件编号';
l_partName_E02 = '零件名称';
l_planArriveTime_E02 = '计划到货时间 ';
l_dmsView_E02 = 'DMS厂外取货采购订单一览';
l_serialNum_E02 = '流水号 ';
l_netQuantity_E02 = '净数量 ';
l_partUnit_E02 = '零件单位 ';
l_orderDepot_E02 = '订货仓库 ';
l_logisticsFlg_E02 = '物流标识 ';
l_planArriveTime_E02 = '计划到货时间 ';
l_demandOrigin_E02 = '需求来源';
l_dmsOrderId_E02 = 'DMS订单号 ';
l_operate_E02 = '操作';
l_retry_E02 = '重试';
l_dmsView = 'MRP计划订单一览';
l_verifyInfo_E02 = '验证信息';
l_dealSuccess_E02 = "处理成功！";
l_dealFailure_E02 = "处理失败！";
l_msg1_E02 = "该计划订单的订货仓库对应的卸货口表数据不存在。";
l_msg2_E02 = "该订单所对应的零件订购主数据不存在或已失效！";
l_msg3_E02 = "该生产订单的估计材料中有数据不完整的情况，请参见验证信息。";
l_msg4_E02 = "该PV的估计材料中在零件订购主数据中有不存在或失效的情况。";
l_msg5_E02 = "该PV的零件需求数量中间表中的数据也被删除，不能进行再计算。";
l_error_E02 = "发生系统错误，请联系系统管理员。";
l_aogFactory_E02 = '到货工厂';
l_unloadPort_E02 = '卸货口';
l_orderIssueDate_E02 = '订单发行日';
l_planDeliveryTime_E02 = '计划发货时间';
l_calculateStatus_E02 = '计算状态';

/*******  DMS厂外取货采购订单(E02)页面结束 *******/

/******* 定量取货物流订单修改(Q99)页面开始 *******/
l_serch_Q99 = '查询条件';
l_supplierNo_Q99 = '供应商代码';
l_factoryId_Q99 = '厂家代码';
l_unloadPort_Q99 = '卸货口';
l_partId_Q99 = '零件编号';
l_partProfileId_Q99 = '零件简号';
l_logisticsOrder_Q99 = '物流订单号';
l_orderIssueDate_Q99 = '订单发行日';
l_planArrivalDate_Q99 = '计划到货日期';
l_dealTimeStr_Q99 = '处理日期';
l_orderStatus_Q99 = '订单状态';

l_planOrderNo_Q99 = '计划订单号';
l_partName_Q99 = '零件名称';
l_manualAdjustNum_Q99 = '手工调整箱数 ';
l_orderNum_Q99 = '订购数量 ';
l_totalOrderNum_Q99 = '总订购数量';
l_packNum_Q99 = '包装数量';
l_packageNum_Q99 = '总订购箱数';
l_orderIssueDate_Q99 = '订单发行日';
l_gamcArriveDateStr_Q99 = 'GAMC到货时间 ';
l_dealTime_Q99 = '处理日期';
l_isCutoff_Q99 = '打切';
l_isCutoffNum_Q99 = '打切数量';
l_stockBoxQuantity_Q99 = '箱数调整数量';
l_loadRateQuantity_Q99 = '装载率调整量';
l_orderStatus_Q99 = '订单状态';
l_orderView_Q99 = '定量取货物流订单一览';
l_factoryShipTime_Q99 = '厂家出货时间';
l_save_Q99 = '保存';

l_title_Q99 = "提示";
l_chooseOneMateriel_Q99 = "请选择一行物料清单数据！";
l_chooseShipDate_Q99 = "请选择厂家出货时间！";
l_chooseGamcArriveTime_Q99 = "请选择GAMC到货时间 ！";
l_confirmUpdateTime_Q99 = "您确定要修改厂家出货时间和GAMC到货时间吗？";
l_updateSuccess_Q99 = "厂家出货时间和GAMC到货时间修改成功！";
l_updateFailure_Q99 = "厂家出货时间和GAMC到货时间修改失败！";
/******* 定量取货物流订单修改(Q99)页面结束 *******/

/******* 车辆规格查询再计算(Q98)页面开始 *******/
l_serch_Q98 = '查询条件';
l_createBy_Q98='创建人';
l_startSortId_Q98='起始排序号';
l_produceSeq_Q98 = '产品变型号';
l_finalUnderlineTime_Q98 = '总装下线时间';
l_planRealDate_Q98='日期范围';
l_calculateStatus_Q98 = 'PV零件需求计算状态';
l_firstSortID_Q98='起始排序号';
l_operate_Q98 = '操作';
l_recalculate_Q98 = '再计算';
l_view_Q98 = '车辆总成一览';
tab_sortplan_excelImport_I98="手工计划导入";
tab_remained_excelImport_I98="剩余量导入";

l_mtoId_Q98 = '整车代码';
l_planNum_Q98 = '计划数量';
l_planAdjustNum_Q98='计划调整数量';
l_realNum_Q98 = '实际数量';
l_realAdjustNum_Q98 = '实际调整数量';
l_planState_Q98 = '计划订单状态';
l_weldUnderlineTimeStr_Q98 = '焊装下线时间';
l_paintUnderlineTimeStr_Q98 = '凃装下线时间';
l_finalUnderlineTimeStr_Q98 = '总装下线时间';
l_planStartSortId_Q98='计划起始排序号';
l_planEndSortId_Q98='计划截止排序号';
l_realStartSortId_Q98='实际起始排序号';
l_realEndSortId_Q98='实际起始排序号';
l_isProduct_Q98 = '是否试制';
l_onOffStatus_Q98 = '开关状态';
l_calculateStatus_Q98 = '计算状态';
l_show_Q98 = '错误';
l_produceSeqConfirm_Q98 = "产品变型号大小关系不正确！";
l_inputNum_Q98 = "请输入数字";
/******* 车辆规格查询再计算(Q98)页面结束 *******/

l_date = "日期";
l_arrivalTime = "出勤时间";
l_leaveTime = "退勤时间";
l_time = "工作时间";
l_save = "保存";
l_cancel = "取消";
l_skipweekend = "自动跳过周末";
l_supplierscode = "供应商代码";
l_factorycode = "厂家代码";
l_timefrequency = "时间频度";
l_countrange = "计算范围";

/******************* 物料清单 start**********************/
  /*************** 物料清单检索 start***************/
l_producePieceB13 = "生产件号";
l_producePieceNameB13 = "生产件名称";
l_carTypeB13="车型";
l_carTypeMtoB13="车型代码(MTO)";
l_carColorB13="颜色";
l_carStyleB13="内饰风格";
l_supplierIdB13="供应商代码";
l_supplierNameB13="供应商名称";
l_factoryIdB13="出货地";
l_packQuantityB13="包装数";
l_unitB13 = "单位";
l_partBriefNoB13="零件简号";
l_centerIdB13="工作中心";
l_warehouseIdB13="仓库";
l_aogFactoryB13="到货工厂";
l_unloadPortB13="卸货口";
l_updateUnloadPortB13 = "修改卸货口";
l_updateItemInfoB13 = "修改零件信息";
l_titlePartB13="零件信息一览";
l_check_cbxB13="仅查询未录入落点组合的物料";
l_inspectB13="物料清单查看";
l_modifyB13="物料清单修改";
l_titleB13="物料清单数据一览";
l_carTypeConfigInspectB13="车型配置查看";
l_carTypeConfigAddB13="车型配置新增";
l_carTypeConfigModifyB13="车型配置修改";
l_carTypeConfigTitleB13="车型配置信息一览";
l_partNumberB13="零件编号";
l_partNameCNB13 ="零件名称";
l_locationB13 ="位置";
l_sequenceB13 ="序号";
l_netQuantityB13 ="净数量";
l_centerNameB13 ="工序落点";
l_placementPortfolioB13 ="卸货口";
l_effectiveDateB13 ="生效日期";
l_expiryDateB13 ="失效日期";
l_isVirtualB13 ="是否虚拟";
l_bomSearchB13 = "物料清单检索";
l_searchB13 = "检索";
l_planReal='检索并生成';
l_clearB13 = "清空";
l_all_retry = "全部重试";
l_okB13 = "确定";
l_backB13 = "返回";
l_previousB13 = "已经是第一页了";
l_nextPageB13 = "已经是最后一页了";
l_titleB131 = "提示";
l_chooseOnePartB131 = "请选择一个零件！";
l_chooseOneMaterielB131 = "请选择一行物料清单数据！";
l_chooseOneUnloadPortB131 = "卸货口为必输项。";
l_isNotExistedUnloadPortB131 = "卸货口不存在！";
l_confirmUpdateUnloadPortB131 = "您确定要修改卸货口吗？";
l_confirmUpdateItemInfoB131 = "您确定要修改零件信息吗？";
l_updateUnloadPortWaitMsgB131 = "数据保存中...";
l_updateSuccessB131 = "卸货口修改成功！";
l_updateFailureB131 = "卸货口修改失败！";
l_updateItemSuccessB131 = "零件主数据修改成功！";
l_updateItemFailureB131 = "零件主数据修改失败！";
l_msg1_B131 = "该物料信息对应的卸货口，到货工厂和到货仓库不存在，不能生成物料信息。";
l_searchResultTooMuch_B131 = "本次检索的数据超过一万条，请指定检索条件。 否则检索速度将会十分缓慢！";
    /*************** 物料清单检索 end***************/

    /*************** 物料清单查看 start***************/
l_producePieceB14 = "生产件号";
l_locationB14 ="位置";
l_sequenceB14 ="序号";
l_partNumberB14="零件编号";
l_partNameCNB14 ="零件名称";
l_partBriefNoB14="零件简号";
l_netQuantityB14 ="净数量";
l_wasteRateB14 ="废品率";
l_wasteQuantityB14 ="废品数量";
l_processB14 ="工序";
l_centerNameB14 ="工序落点";
l_isVirtualB14 ="是否虚拟";
l_effectiveDateB14 ="生效日期";
l_expiryDateB14 ="失效日期";
l_AogWarehouseB14 ="到货仓库";
l_AogFactoryB14 ="到货工厂";
l_UuloadPlaceB14 ="卸货场所";
l_SelectionZoneB14 ="分拣区";
l_placementprocessesportfolioB14 ="工序落点组合";
l_mainpartcodeB14="主零件代码";
l_mainpartnameB14="主零件名称";
l_titleB14="物料清单查看";
l_bomDataB14="物料清单数据";
l_componentPlacementDataB14 = "零件落点数据";
l_PurchaseB14="配套采购数据";
l_inspectB14="零部件信息查看";
l_previous_B14 = "上一条";
l_nextPage_B14 = "下一条";
    /*************** 物料清单查看 end***************/

     /*************** 物料清单修改 start***************/
l_producePieceB15 = "生产件号";
l_locationB15 ="位置";
l_sequenceB15 ="序号";
l_partNumberB15="零件编号";
l_partNameCNB15 ="零件名称";
l_partBriefNoB15="零件简号";
l_netQuantityB15 ="净数量";
l_wasteRateB15 ="废品率";
l_wasteQuantityB15 ="废品数量";
l_processB15 ="工序";
l_centerNameB15 ="工序落点";
l_isVirtualB15 ="是否虚拟";
l_effectiveDateB15 ="生效日期";
l_expiryDateB15 ="失效日期";
l_placementB15 ="卸货口";
l_AogWarehouseB15 ="到货仓库";
l_AogFactoryB15 ="到货工厂";
l_UuloadPlaceB15 ="卸货场所";
l_SelectionZoneB15 ="分拣区";
l_mainpartcodeB15="主零件代码";
l_mainpartnameB15="主零件名称";
l_titleB15="物料清单修改";
l_bomDataB15="物料清单数据";
l_componentPlacementDataB15 = "零件落点数据";
l_PurchaseB15="配套采购数据";
l_modifyB15="物料清单修改";
l_previous_B15 = "上一条";
l_nextPage_B15 = "下一条";
l_save_B15 = "保存";
l_warning_B15 = "主零件代码和零件编号不能相同";
    /*************** 物料清单修改 end***************/

/******************* 物料清单 end**********************/

/******************* 库存个数 start**********************/

/*************** 库存个数检索 start***************/
l_componentNo_Q11 = "零件编号";
l_componentId_Q11 = "零件简号";
l_supplierNo_Q11 = "供应商代码";
l_xieHuoKou_Q11 = "卸货口";
l_query_Q11 = "查询";
l_strstrdealStratDate_Q11 = "处理起止时间";
l_inspect_Q11 = "库存个数查看";
l_cmbChangjId_Q11 = "厂家代码";
l_lblDaoHuoShCode_Q11 = "到货工厂";
l_entrydate_Q11 = "录入时间";
l_dailyAdjustment_Q11 = '每日调整量';
l_isGenerateRequire_Q11 = '是否生成需求';
l_stockResidualBox_Q11 = '处理结束日期';
l_close_Q11 = "关闭";
l_stockBoxNumbeModify_Q11 = "库存个数修改";
l_save_Q11 = "保存";
l_invalid_Q11 ="失效";
l_stockBoxNumbeAdd_Q11 = "库存个数添加";
l_stockBoxList_Q11 = "库存个数一览 ";
l_stockBoxQuery_Q11 = "库存个数检索";
l_tishi_Q11 = "处理结束日期要大于处理开始时间";
	/*************** 库存个数检索 end***************/

	/*************** 库存个数添加 start***************/
l_componentNo_Q13 = "零件编号";
l_supplierNo_Q13 = "供应商代码";
l_cmbChangjId_Q13 = "厂家代码";
l_xieHuoKou_Q13 = "卸货口";
l_lblDaoHuoShCode_Q13 = "到货工厂";
l_arrivalStartDate_Q13 = "处理开始日期";
l_arrivalEndDate_Q13 = "处理结束日期";
l_dailyAdjustmentVolume_Q13 = '每日调整量';
l_save_Q13 = "保存";
l_close_Q13 = "关闭";
l_stockNumberAdd_Q13 = "库存个数添加";
l_partName_Q13 = "零件名称";
	/*************** 库存个数添加 end***************/

	/*************** 库存箱数检索 start***************/
l_componentNo_Q15 = "零件编号";
l_componentId_Q15 = "零件简号";
l_supplierNo_Q15 = "供应商代码";
l_xieHuoKou_Q15 = "卸货口";
l_query_Q15 = "查询";
l_arrivalStartDate_Q15 = "到货开始日期";
l_inspect_Q15 = "库存箱数查看";
l_cmbChangjId_Q15 = "厂家代码";
l_lblDaoHuoShCode_Q15 = "到货工厂";
l_arrivalStartDate_Q15 = "到货开始日期";
l_dailyAdjustmentVolume_Q15 = '调整箱数';
l_stockResidualBox_Q15 = '调整剩余箱数';
l_minBox_Q15 = '最小箱数';
l_close_Q15 = "关闭";
l_stockBoxNumbeModify_Q15 = "库存箱数修改";
l_save_Q15 = "保存";
l_stockBoxNumbeAdd_Q15 = "库存箱数添加";
l_minBox_Q15 = "最小箱数 ";
l_stockBox_Q15 = "调整箱数 ";
l_stockResidualBox_Q15 = "调整剩余箱数 ";
l_stockBoxList_Q15 = "库存箱数一览 ";
l_stockBoxQuery_Q15 = "库存箱数检索";
l_explain_Q15 = "（保存后，剩余调整箱数将被清零）";
	/*************** 库存箱数检索 end***************/

	/*************** 库存箱数添加 start***************/
l_componentNo_Q17 = "零件编号";
l_supplierNo_Q17 = "供应商代码";
l_cmbChangjId_Q17 = "厂家代码";
l_Q17="库存箱数添加";
l_minBox_Q17 = "最小箱数 ";
l_xieHuoKou_Q17 = "卸货口";
l_lblDaoHuoShCode_Q17 = "到货工厂";
l_arrivalStartDate_Q17 = "到货开始日期";
l_dailyAdjustmentVolume_Q17 = '调整箱数';
l_save_Q17 = "保存";
l_close_Q17 = "关闭";
l_stockBoxAdd_Q17 = "库存箱数添加";
l_partName_Q17 = "零件名称";
	/*************** 库存箱数添加 end***************/

/*************** 工作日历 start***************/

l_w_001 = '早班开始时间';
l_w_002 = '结束时间';
l_w_003 = '午班开始时间';
l_w_004 = '晚班开始时间';
l_w_0011 = '早休开始时间';
l_w_0031 = '午休开始时间';
l_w_0041 = '晚休开始时间';
l_w_005 = '星期';
l_w_0061 = '周一';
l_w_0062 = '周二';
l_w_0063 = '周三';
l_w_0064 = '周四';
l_w_0065 = '周五';
l_w_0066 = '周六';
l_w_0067 = '周日';
l_w_007 = '工作时间';
l_w_008 = '选择日期';
l_w_009 = '日期性质';
l_w_0010 = '日期备注';

l_msg1_workCal = "早班开始时间不能晚于早班结束时间！";
l_msg2_workCal = "午班开始时间不能晚于午班结束时间！";
l_msg3_workCal = "午班开始时间不能早于早班结束时间！";
l_msg4_workCal = "晚班开始时间不能早于午班结束时间！";
l_msg5_workCal = "晚班开始时间不能早于早班结束时间！";
l_msg6_workCal = "晚班开始时间不能晚于晚班结束时间！";
l_chooseWorkCenter_workCal = "请选择工作中心！";
l_chooseDate_workCal = "请选择日期！";
l_workCal = "工作日历";


l_workCal_copy_01 = "源工作中心";
l_workCal_copy_02 = "复制时间段";
l_workCal_copy_03 = "复制源";
l_workCal_copy_04 = "复制目标";
l_workCal_copy_05 = "工作中心代码";
l_workCal_copy_06 = "工作中心名称";
l_workCal_copy_07 = "复制";

l_workCal_copy_11 = "复制源工作中心不能为空。";
l_workCal_copy_12 = "复制时间段的开始时间不能为空。";
l_workCal_copy_13 = "复制时间段的截止时间不能为空。";
l_workCal_copy_14 = "复制时间段的开始时间不能大于截止时间。";
l_workCal_copy_15 = "请至少选择一个复制目标工作中心。";
l_workCal_copy_16 = "复制目标工作中心中不能有复制源工作中心。";
l_workCal_copy_17 = "您确认要复制吗？";


/*************** 工作日历 end***************/

/******************* 库存箱数 end************************/

Ext.UpdateManager.defaults.indicatorText = '<div class="loading-indicator">加载中...</div>';

if(Ext.View){
   Ext.View.prototype.emptyText = "";
}

if(Ext.grid.GridPanel){
   Ext.grid.GridPanel.prototype.ddText = "{0} 选择行";
}

if(Ext.TabPanelItem){
   Ext.TabPanelItem.prototype.closeText = "关闭";
}

if(Ext.form.Field){
   Ext.form.Field.prototype.invalidText = "输入值非法";
}
if(Ext.form.CheckboxGroup){
       Ext.form.CheckboxGroup.prototype.blankText = "请至少选择一个。";
    }
Date.monthNames = [
   "1月",
   "2月",
   "3月",
   "4月",
   "5月",
   "6月",
   "7月",
   "8月",
   "9月",
   "10月",
   "11月",
   "12月"
];

Date.dayNames = [
   "日",
   "一",
   "二",
   "三",
   "四",
   "五",
   "六"
];

if(Ext.MessageBox){
   Ext.MessageBox.buttonText = {
      ok     : "确定",
      cancel : "取消",
      yes    : "是",
      no     : "否"
   };
}

if(Ext.util.Format){
   Ext.util.Format.date = function(v, format){
      if(!v) return "";
      if(!(v instanceof Date)) v = new Date(Date.parse(v));
      return v.dateFormat(format || "y年m月d日");
   };
}

if(Ext.DatePicker){
   Ext.apply(Ext.DatePicker.prototype, {
      todayText         : "今天",
      minText           : "日期在最小日期之前",
      maxText           : "日期在最大日期之后",
      disabledDaysText  : "",
      disabledDatesText : "",
      monthNames        : Date.monthNames,
      dayNames          : Date.dayNames,
      nextText          : '下月 (Control+Right)',
      prevText          : '上月 (Control+Left)',
      monthYearText     : '选择一个月 (Control+Up/Down 来改变年)',
      todayTip          : "{0} (空格键选择)",
      format            : "y年m月d日",
      okText            : "确定",
      cancelText        : "取消"
   });
}

if(Ext.PagingToolbar){
   Ext.apply(Ext.PagingToolbar.prototype, {
      beforePageText : "第",
      afterPageText  : "页　共 {0} 页",
      firstText      : "第一页",
      prevText       : "前一页",
      nextText       : "下一页",
      lastText       : "最后页",
      refreshText    : "刷新",
      displayMsg     : "显示 {0} - {1}，共 {2} 条",
      emptyMsg       : '没有数据需要显示'
   });
}

if(Ext.form.TextField){
   Ext.apply(Ext.form.TextField.prototype, {
      minLengthText : "该输入项的最小长度是 {0}",
      maxLengthText : "该输入项的最大长度是 {0}",
      blankText     : "该输入项为必输项",
      regexText     : "",
      emptyText     : null
   });
}

if(Ext.form.NumberField){
   Ext.apply(Ext.form.NumberField.prototype, {
      minText : "该输入项的最小值是 {0}",
      maxText : "该输入项的最大值是 {0}",
      nanText : "{0} 不是有效数值"
   });
}

if(Ext.form.DateField){
   Ext.apply(Ext.form.DateField.prototype, {
      disabledDaysText  : "禁用",
      disabledDatesText : "禁用",
      minText           : "该输入项的日期必须在 {0} 之后",
      maxText           : "该输入项的日期必须在 {0} 之前",
      invalidText       : "{0} 是无效的日期 - 必须符合格式： {1}",
      format            : "y年m月d日"
   });
}

if(Ext.form.ComboBox){
   Ext.apply(Ext.form.ComboBox.prototype, {
      loadingText       : "加载...",
      valueNotFoundText : undefined
   });
}

if(Ext.form.VTypes){
   Ext.apply(Ext.form.VTypes, {
      emailText    : '该输入项必须是电子邮件地址，格式如： "user@domain.com"',
      urlText      : '该输入项必须是URL地址，格式如： "http:/'+'/www.domain.com"',
      alphaText    : '该输入项只能包含字符和_',
      alphanumText : '该输入项只能包含字符,数字和_'
   });
}

if(Ext.grid.GridView){
   Ext.apply(Ext.grid.GridView.prototype, {
      sortAscText  : "正序",
      sortDescText : "逆序",
      lockText     : "锁列",
      unlockText   : "解锁列",
      columnsText  : "列"
   });
}

if(Ext.grid.PropertyColumnModel){
   Ext.apply(Ext.grid.PropertyColumnModel.prototype, {
      nameText   : "名称",
      valueText  : "值",
      dateFormat : "y年m月d日"
   });
}

if(Ext.layout.BorderLayout && Ext.layout.BorderLayout.SplitRegion){
   Ext.apply(Ext.layout.BorderLayout.SplitRegion.prototype, {
      splitTip            : "拖动来改变尺寸.",
      collapsibleSplitTip : "拖动来改变尺寸. 双击隐藏."
   });
}





