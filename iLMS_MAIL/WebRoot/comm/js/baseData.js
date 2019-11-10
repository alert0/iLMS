
//用户状态
var sysUserStatusArray = [['1', '正常'],['2', '禁用']];

//激活状态
var ACTIVATE_STATUS_NO = '0';//未激活
var ACTIVATE_STATUS_YES = '1';//已激活
var activateStatusArray = [[ACTIVATE_STATUS_YES,'已激活'],[ACTIVATE_STATUS_NO,'未激活']];

//供应商角色类型
var supRoleTypeArray = [ [ '2', '供应商' ], [ '3', '中转仓' ], [ '4', '外协仓' ] ];
/** 供应商角色类型-供应商 */
var SUP_ROLE_TYPE_SUPPLIER = '2';
/** 供应商角色类型-中转仓 */
var SUP_ROLE_TYPE_TRANSIT = '3';
/** 供应商角色类型-外协仓 */
var SUP_ROLE_TYPE_WX = '4';

//物流公司类型
var LOG_COMPANY_TYPE_TRANSIT = '3';//中转仓
var LOG_COMPANY_TYPE_WX = '4';//外协仓
var logCompanyTypeArray = [[LOG_COMPANY_TYPE_TRANSIT,'中转仓'],[LOG_COMPANY_TYPE_WX,'外协仓']];

// 角色状态
var roleStatusArray = [ [ '0', '无效' ], [ '1', '有效' ] ];

//角色类型
var roleTypeArray =[['1','广乘用户'],['2','供应商'],['3','中转仓'],['4','外协仓']];

//是否
var yesNoArray = [['1', '是'],['0','否']];

// 下载状态
var downloadStatusArray = [ [ '0', '未下载' ], [ '1', '已下载' ] ];
// 打印状态
var PRINT_STATUS_YES = '1'; //已打印
var PRINT_STATUS_NO = '0'; //未打印
var printStatusArray = [ [ PRINT_STATUS_NO, '未打印' ], [PRINT_STATUS_YES, '已打印' ] ];
// 发货状态
var deliveryStatusArray = [ [ '0', '未发货' ], [ '1', '部分发货' ], [ '2', '已发货'] ];
// 是否发货
var deliveryYesNoArray = [ [ '0', '未发货' ], [ '2', '已发货'] ];
// 收货状态
var RECEIVE_STATUS_ALL = '2';//完全收货
var receiveStatusArray = [ [ '0', '未收货' ], [ '1', '部分收货' ], [ '2', '完全收货' ] ];
// 过期状态
var overdueStatusArray = [ [ '0', '未过期' ], [ '1', '已过期' ] ];
// 关闭状态
var closeStatusArray = [ [ '0', '未关闭' ], [ '1', '已关闭' ] ];

//批次信息发货状态
var batchDeliveryStatusArray = [[ '0', '未发货' ], [ '1', '已发货' ]];


//需求预测阶段
var demandOrderStatusArray = [ [ '0', '日需求' ], [ '1', '月总量' ] ];
//需求预测类型
var demandOrderTypeArray = [ [ '0', '量产' ], [ '1', '试制' ] ];
//供货预测发货类型
var supplyOrderTypeArray = [ [ '0', '到货需求' ], [ '1', '取货需求' ] ];
//供货预测数据类型
var supplyDataTypeArray = [ [ '0', '预测' ], [ '1', '订单' ] ];

//取货计划-反馈状态
var pickupPlanFeedBackArray = [ [ '0', 'NG' ], [ '1', 'OK' ] ];
//取货计划-取货区分
var pickupPlanTypeArray = [ [ '0', '取货' ], [ '1', '送货' ], [ '2', '支给' ] ];

//发货管理-取消状态
var deliveryCancelStatusArray = [['0','未取消'],['1','供应商取消'],['2','GAMC取消'],['3','双方取消']];

//取货状态
var qhStatus = [['0','未取货'],['1','部分取货'],['2','全部取货']];
//收货状态
var shStatus = [['0','未收货'],['1','部分收货'],['2','全部收货']];


//-----------------------------------------


// 月份
var monthArray = [ [ '01', '1' ], [ '02', '2' ], [ '03', '3' ], [ '04', '4' ],
		[ '05', '5' ], [ '06', '6' ], [ '07', '7' ], [ '08', '8' ],
		[ '09', '9' ], [ '10', '10' ], [ '11', '11' ], [ '12', '12' ] ];
// 发货状态
var sendStatus = [ [ '0', '未发货' ], [ '1', '部分发货' ], [ '2', '完全发货' ] ];
// 制单状态
var settlementMakeStatus = [ [ '0', '制单行' ], [ '1', '已剔除' ] ];
//结算单状态
var settlementStatus1 = [ [ '0', '未制单' ], [ '1', '已制单' ] ];
// 验收状态
var orderNeedStatus = [ [ '0', '未验收' ], [ '1', '部分验收' ], [ '2', '完全验收' ] ];
var calculateStatusArray = [ [ '0', '未计算' ], [ '1', '已计算需求' ], [ '2', '已计算差异' ] ];

//生产班次
var synShiftCode = [ [ '1', '第一班' ], [ '2', '第二班' ], [ '3', '第三班' ] ];
//VMI订单状态
var vmiOrderStatus = [ [ '0', '未打印' ], [ '1', '已打印' ], [ '2', '发货' ],
		[ '3', '部分收货' ], [ '4', '完全收货' ] ];

//外协拉动订单状态
var vmiJitOrderStatus = [ [ '0', '收货' ], [ '1', '部分收货' ], [ '2', '完全收货' ]];
//VMI订单状态
var vmiOrderChangeStatus = [ [ '0', '' ], [ '1', '有调整' ]];
var vmiOrderChangeStatusCb = [ [ '0', '未调整' ], [ '1', '有调整' ]];

//车辆分配状态
var carAllotStatus = [ [ '0', '未分配' ], [ '1', '已分配' ], [ '2', '司机已确认' ],
	['3', '已发车'],['4', '已到货'],['5', '已卸货']];

//VMI库位代码
var vmiWhLoction = [ [ '-', '-' ], [ 'YELLOW', '黄点' ], [ 'GREEN', '绿点' ] ];
//车间代码
var synWorkCenterCode = [ [ 'AF', '总装车间' ], [ 'PA', '涂装车间' ], [ 'WE', '焊装车间' ],
		[ 'PR', '冲压车间' ] ];

var orderLineNeedStatus = [ [ '0', '未验收' ], [ '1', '部分验收' ], [ '2', '完全验收' ],
		[ '3', '已取消' ] ];
//验收状态
var cbxOrderReciveState = [ [ '0', '未验收' ], [ '1', '部分验收' ], [ '2', '完全验收' ] ];
//验收状态
var cbxSynVmiOrderState = [ [ '0', '未同步' ], [ '1', '已同步' ] ];
//验收状态
var cbxVmiOrderType = [ [ '0', '系统单' ], [ '1', '手工单' ] ];

//结算状态
var militaryVehicleOrderStatus = [ [ '0', '未制单' ], [ '1', '已制单' ] ];

//打印状态
var poStatus = [ [ '0', '已创建' ], [ '1', '已处理' ], [ '2', '已上传' ] ];

var paVoteStatus = [ [ '0', '未处理' ], [ '1', '部分处理' ], [ '2', '完全处理' ] ];
var piJianFlg = [ [ '1', '是' ], [ '2', '否' ] ];

//指示票来源
var synLogisticsSource = [ [ '0', '人工' ], [ '1', '系统' ] ];

// 是否使用中转仓
var isOutSourcing = [ [ '0', '未使用' ], [ '1', '使用' ] ];
// 查看状态
var orderViewStatus = [ [ '0', '未查看' ], [ '1', '已查看' ] ];

//VMI例外出入库订单状态
var vmiExpOrderStatus = [ [ '0', '未审核' ],['2','审核通过'],['3','已打印'],['4','审核不通过'] ];
var vmiSubExpOrderStatus = [ ['2','审核通过'],['4','审核不通过'] ];
var opeTypeArray = [ [ 'LWRK', '例外入库' ], [ 'LWCK', '例外出库' ] ];
var vmiStockStatus = [ [ '0', '未审核' ], [ '1', '已审核' ],['2','审核不通过'] ];
//sw订单状态
var swOrderRecStatus = [['',''],['未收货','未收货'],['部分收货','部分收货'],['全部收货','全部收货']];

// 是否
var yesnoStatusArray = [ [ '0', '是' ], [ '1', '否' ] ];


// 有没有
var haveStatusArray = [ [ '0', '有' ], [ '1', '没有' ] ];
//
var subContractOrdersArray = [ [ '0', '' ], [ '1', '支给' ] ];

// 需要不需要状态
var needStatusArray = [ [ '0', '需要' ], [ '1', '不需要' ] ];

// 通过与否状态
var passStatusArray = [ [ '0', '通过' ], [ '1', '未通过' ] ];

// 中转仓收货状态
var zzcReceiveStatusArray = [ [ '0', '未收货' ], [ '1', '已收货' ] ];
// 取消状态条件
var cancelStatusArray = [ [ '0', '未取消' ], [ '1', '单方取消' ], [ '2', '双方取消' ] ];
// 发货取消状态条件
var cancelStatusGridArray = [ [ '0', '未取消' ], [ '3', 'GAMC单方取消' ],
		[ '4', '供应商单方取消' ], [ '2', '双方取消' ] ];
// 供应商状态/供应商角色
var supplierRoleTypeArray = [ [ '000', '未激活' ], [ '001', '已激活' ],
		[ '002', '已注册' ], [ '003', '已禁用' ] ];
// 开票通知单
var settlementCheckStatusArray = [ [ '000', '已创建' ], [ '008', '已发布' ],
		[ '009', '供应商审核有差异' ], [ '010', '供应商审核完毕' ] ];
// 供应商侧开票通知单状态
var settlementSupCheckStatusArray = [ [ '008', '待审核' ], [ '009', '审核完毕有差异' ],
		[ '010', '审核完毕' ] ];

// 价格数量审核状态
var priceAndNumCheckStatusArray = [ [ '0', '已认可' ], [ '1', '已差异' ] ];
// 价格数量审核状态
var settlementRowCheckStatusArray = [ [ '0', '认可' ], [ '1', '有差异' ],
		[ '2', '供应商差异未提交' ] ];
// 审批状态
var checkStatusArray = [ [ '000', '待审' ], [ '001', '通过' ], [ '002', '退回' ] ];

// 二级审批状态
// 退回
var TWOCHECKSTATUS_REFUSE = '2';
var twoCheckStatusArray = [ [ '0', '待审核' ], [ '1', '待批准' ],
		[ TWOCHECKSTATUS_REFUSE, '退回' ], [ '3', '通过' ] ];

// 调查表填写状态
var inquireWriteStatusArray = [ [ '0', '未填写' ], [ '1', '已填写' ] ];

// 保密协议认可状态
var nondiscAgreeStatusArray = [ [ '0', '未认可' ], [ '1', '已认可' ], [ '2', '已存档' ] ];



// 分摊方法
var mouldTypeArray = [ [ '1', '1月/期' ], [ '2', '2月/期' ], [ '3', '3月/期' ],
		[ '4', '4月/期' ], [ '5', '5月/期' ], [ '6', '6月/期' ], [ '7', '7月/期' ],
		[ '8', '8月/期' ], [ '9', '9月/期' ], [ '10', '10月/期' ], [ '11', '11月/期' ],
		[ '12', '12月/期' ] ];
// 系统角色
var roleArray = [ [ '000', '系统管理员' ], [ '001', '采购部核心领导小组' ],
		[ '002', '采购管理委员会' ], [ '003', '采购工程师' ], [ '004', '采购经理' ],
		[ '005', '采购部长' ], [ '006', 'PE' ], [ '007', '供应商质量经理' ],
		[ '008', 'SQE' ], [ '009', 'IT人员' ] ];
var supRoleArray = [ [ '100', '系统管理员' ] ];



// 发送密码提示邮件
var passMailArray = [ [ '0', '发送' ], [ '1', '不发送' ] ];

// 身份类型
var userTypeArray = [ [ '0', '供应商' ], [ '1', 'GAMC' ] ];

// 权限操作名
var permissionTypeArray = [ [ '00', '查看' ], [ '01', '新增' ], [ '02', '修改' ],
		[ '03', '删除/取消' ], [ '04', '激活' ], [ '05', '保存' ], [ '06', '解锁' ],
		[ '07', '禁用' ], [ '08', '制单' ], [ '10', '订单打印' ], [ '11', '标签打印' ],
		[ '30', '人员设定审核' ], [ '40', '审核' ], [ '41', '批准/反批准' ], [ 'SP', '操作' ] ];

// 登录状态
var LOGINSTATUS_OFFLINE = '00';
var LOGINSTATUS_ONLINE = '01';
var LOGINSTATUS_LOCK = '02';
var loginStatusArray = [ [ LOGINSTATUS_OFFLINE, '离线' ],
		[ LOGINSTATUS_ONLINE, '在线' ], [ LOGINSTATUS_LOCK, '密锁中' ] ];

// 日期单位
var expUnitArray = [ [ '0', '年' ], [ '1', '月' ], [ '2', '日' ] ];

// 处理状态
var doStatusArray = [ [ '0', '未处理' ], [ '1', '已处理' ] ];

// 询价状态
var questStatusArray = [ [ '0', '未查看' ], [ '1', '已查看' ], [ '2', '已报价' ] ];

// 开发类型
var developTypeArray = [ [ '000', '普通开发计划' ], [ '001', '紧急/临时开发计划' ] ];

// 问题状态
var problemStatusArray = [ [ '0', '待整改' ], [ '1', '整改中' ], [ '2', '已整改' ] ];

// 评价状态
var assessStatusArray = [ [ '0', '满意' ], [ '1', '需改进' ], [ '2', '不可接受' ] ];

// 删除状态
var deleteStatusArray = [ [ '0', '未删除' ], [ '1', '已删除' ] ];

// 激活状态
var activeStatusArray = [ [ '0', '已激活' ], [ '1', '未激活' ] ];

// 订单类型
var orderTypeArray = [ [ '0', '资材订单' ], [ '1', '样件订单' ], [ '2', '零件订单' ],
		[ '3', '例外订单' ], [ '4', '销售备件订单' ], [ '5', '同步物流指示票' ],
		[ '6', 'JIT指示票' ] ];
// 订单类型订单总部代打
var orderTypeGamcArray = [ [ '2', '零件订单' ], [ '3', '例外订单' ] ];

// 需求预测拒绝状态
var demandRefuseStatusArray = [ [ '0', '未认可' ], [ '1', '已认可' ], [ '2', '拒绝' ] ];

// 菜单等级
var menuRankArray = [ [ '0', '1级菜单' ], [ '1', '2级菜单' ], [ '2', '3级菜单' ] ];

// 计划类型
var planTypeArray = [ [ '0', '新建' ], [ '1', '变更' ] ];


// 材料取得方式
var stuffGetTypeArray = [ [ '0', '自制' ], [ '1', '外购' ] ];

// 国内/国外
var addrTypeArray = [ [ '0', '国内' ], [ '1', '国外' ] ];

// APQP计划状态
var apqpPlanStatusArray = [ [ '000', '未响应' ], [ '001', '响应中' ],
		[ '002', '已响应' ], [ '003', '实施中' ], [ '004', '已结束' ] ];

// 评审结论
var reviewVerdictArray = [ [ '0', '否定' ], [ '1', '有条件肯定，需整改再评' ],
		[ '2', '肯定' ], [ '3', '优秀' ] ];

// 货运方式
var huoyunArray = [ [ '0', '海运' ], [ '1', '陆运' ], [ '2', '空运' ], [ '3', '其他' ] ];

// 零部件状态
var componentStatusArray = [ [ '0', '可用' ], [ '1', '停用' ] ];
// 支给qiaoqiming
var zhigei = '支给';
// 用户状态
var USER_STATUS_USABLE = '0';
var USER_STATUS_DISABLE = '1';
var userStatusArray = [ [ USER_STATUS_USABLE, '可用' ],
		[ USER_STATUS_DISABLE, '禁用' ] ];
// 重点控制
var keyControlArray = [ [ '000', '正常' ], [ '001', '长周期' ], [ '002', '短周期' ] ];

// 付款方式
var fukuanArray = [ [ '0', '现金' ], [ '1', '信用卡' ] ];

// 公司性质
var corpTypeArray = [ [ '0', '国有企业' ], [ '1', '私有企业' ], [ '2', '中外合资企业' ] ];
// 部件属性 0：一般件 1：关键 2：重要件
var componentShuXingArray = [ [ '000', '一般件' ], [ '001', '关键' ],
		[ '002', '重要件' ] ];

// 需求类型
var needOrderTypeArray = [ [ '0', '资材订单' ], [ '1', '样件订单' ], [ '2', '零件订单' ],
		[ '3', '例外订单' ] ];
// 是否到中转仓
var isTransitArray = [ [ '0', '否' ], [ '1', '是' ] ];
// 是否到外协仓
var isJointStoreArray = [ [ '0', '否' ], [ '1', '是' ] ];
// 是否是完全完成
var mouldStatusArray = [ [ '0', '否' ], [ '1', '是' ] ];
// 是否是模具零件
var isMouldCompArray = [ [ '0', '否' ], [ '1', '是' ] ];
// 模具头分摊状态
// 模具分摊头状态 0：未分摊 1：完全分摊 2：部分分摊 3： 终止
var mouldStatus = [ [ '0', '未分摊' ], [ '1', '完全分摊' ], [ '2', '部分分摊' ],
		[ '3', '终止' ] ];
//模具类型 0：按期分摊 1：暂定回调 ,2：设变新增，3：能扩工装，4：台车新增
var mouldStyle = [ [ '0', '按期分摊' ], [ '1', '暂定回调' ], [ '2', '设变新增' ],
		[ '3', '能扩工装' ], [ '4', '台车新增' ], [ '5', '工装到期补差' ] ];
// 模具行分摊状态
// 分摊行分摊状态 0:未分摊 1：完全分摊 3：终止 4：不分摊 5：分摊中
var mouldRowStatus = [ [ '0', '未分摊' ], [ '1', '分摊中' ], [ '4', '人工调整' ],
		[ '3', '终止' ], [ '2', '已分摊' ] ];
// 批准状态
var mouldVerifyStatus = [ [ '0', '未批准' ], [ '1', '已批准' ] ];
// 审核状态
var mouldReViewStatus = [ [ '0', '未审核' ], [ '1', '已审核' ] ];
//数量审核状态
var numberApproveStatus = [ [ '0', '未审核' ], [ '1', '已审核' ], [ '2', '有差异' ] ];
//制单状态
var makeOrderStatus =[['已制单','已制单'],['可制单','可制单'],['不可制单','不可制单']]
// 定单类型
var receiveOrderType = [ [ '0', '(供)' ], [ '1', '(中)' ] ];
// 判定
var claimantMethodArray = [ [ '000', '换货' ], [ '001', '退货' ], [ '002', '返修' ] ];
// 订单阶段
var orderStageArray = [ [ '000', '收货前' ], [ '001', '收货后' ] ];
// 索赔状态
var claimantStatusArray = [ [ '000', '待认可' ], [ '001', '供应商拒绝' ],
		[ '002', '供应商认可' ], [ '003', 'GAMC拒绝' ], [ '004', '已关闭' ] ]
// 使用目的
var usePurposeArray = [ [ '0', '生准' ], [ '1', '试生产' ], [ '2', '量产' ],
		[ '3', '其他' ] ];
// 不合格项目
var unqualifiedArray = [ [ '0', '外观' ], [ '1', '尺寸' ], [ '2', '性能' ],
		[ '3', '材料' ], [ '4', '其他' ] ];
// 申报处理
var declarationProcessingArray = [ [ '0', '索赔' ], [ '1', '返工/返修' ],
		[ '2', '其他' ] ];

/** zhangye 20100909 追加Start*****/
/** 后续计划查询用*****/
// 汇总方式
var summaryTypeArray = [ [ '0', '周' ], [ '1', '月' ] ];
//后续计划查询用 订单类型
var orderTypeForDeliveryPlanArray = [ [ '0', '资材订单' ], [ '1', '样件订单' ],
		[ '2', '零件订单' ], [ '3', '例外订单' ], [ '4', '销售备件订单' ] ];
/** zhangye 20100909 追加End*****/
/** zhangye 20101011 追加Start*****/
// 数据类型
var dataTypeArray = [ [ 'AP', 'AP' ], [ 'AR', 'AR' ] ];
/** zhangye 20101011 追加End*****/
/** *******************************************message常量定义******************************************************** */
GSL_Q_M003 = "确定要批准选中的供应商吗？";
GSL_Q_M006 = "确定要退回选中的供应商吗？";
// GSL_SELCET="查询";
// 订单头收货状态为完全收货
var SW_RECEIVESTATUS_ALL = '2';
// 打印状态
var SW_PRINTSTATUS_NO = '0';

/** *******************************************EPPS******************************************************** */
//////   E-PPS用Code定义        开始 ////////////////////////////////////////////////////////////////////////

// 删除状态
var deleteStatusArray = [ [ '0', '未删除' ], [ '1', '已删除' ] ];

//用户状态
var USER_STATUS_USABLE = '0';
var USER_STATUS_DISABLE = '1';
var userStatusArray = [ [ USER_STATUS_USABLE, '可用' ],
		[ USER_STATUS_DISABLE, '禁用' ] ];

// 登录状态
var LOGINSTATUS_OFFLINE = '00';
var LOGINSTATUS_ONLINE = '01';
var LOGINSTATUS_LOCK = '02';
var loginStatusArray = [ [ LOGINSTATUS_OFFLINE, '离线' ],
		[ LOGINSTATUS_ONLINE, '在线' ], [ LOGINSTATUS_LOCK, '密锁中' ] ];
// 零件类型
var orderTypeArray = [ [ '0', '资材订单' ], [ '1', '样件订单' ], [ '2', '零件订单' ],
		[ '3', '例外订单' ] ];

// 是否已生成需求
var isRequestArray = [ [ '', '' ], [ '0', '否' ], [ '1', '是' ] ];

// 权限操作名
var permissionTypeArray = [ [ '00', '查看/下载' ], [ '01', '新增' ], [ '02', '修改' ],
		[ '03', '删除' ], [ '04', '禁用' ], [ '05', 'Excel导入' ] ];

// 系统角色
var roleArray = [ [ '000', '系统管理员' ], [ '001', '采购部核心领导小组' ],
		[ '002', '采购管理委员会' ], [ '003', '采购工程师' ], [ '004', '采购经理' ],
		[ '005', '采购部长' ], [ '006', 'PE' ], [ '007', '供应商质量经理' ],
		[ '008', 'SQE' ], [ '009', 'IT人员' ] ];

// 是否是默认卸货口
var isDefaulfArray = [ [ '0', '否' ], [ '1', '是' ] ];

////////// 订单履历   杨选举 star////////////
// 是否打切
var isCutoffArray = [ [ '0', '是' ], [ '1', '否' ] ];
// 返工单wangchunfeng06/18
var isReturnArray = [ [ '0', '是' ], [ '1', '否' ] ];
// 计算状态wangchunfeng06/18
var isComputerArray = [ [ '1', '未计算' ], [ '0', '已计算估计材料' ], [ '2', '已计算零件需求' ] ];
// 订单状态
var orderStatusEppsArray = [ [ '0', '已计划' ], [ '1', '已生成' ], [ '9', '已上传' ] ];

//表的操作类型
var trxTypeArray = [ [ 'D', '删除' ], [ 'M', '修改' ],['I','插入'] ,['E','事件']];

//物流标识
var logisticsOrderArray = [ [ 'GAM', 'GAM' ], [ 'VMI', 'VMI' ] ];

//订单状态(来自ERP）
var orderStatusEppsArrayFromErp = [ [ '0', '已创建' ], [ '1', '已下达' ],
		[ '2', '活动' ], [ '3', '待完成、已完成' ], [ '4', '已结算' ] ];

////////// 订单履历  杨选举 end////////////
////////// 零件需求信息 杨选举 star////////////
// 零件来源
var partDemandOriginArray = [ [ '1', 'MRP' ], [ '2', 'DMS' ], [ '3', '加工不良' ],
		[ '4', '调整数' ], [ '5', '必要数' ] ];
// 时间单位
var partTimeUnitArray = [ [ '10', '天' ], [ '5', '小时' ] ];
//////////零件需求信息  杨选举  end////////////
//////////整车日排序计划 杨选举 star////////////
//是否试制
var sortPlanIsProductArray = [ [ '0', '否' ], [ '1', '是' ] ];
var lockTypeArray = [ [ '1', '需求计算' ], [ '2', '采购订单生成' ] ];
var isLockArray = [ [ '0', '正运行' ], [ '1', '结束' ] ];
// 开关状态
var onOffStatusArray = [ [ '0', '关' ], [ '1', '开' ] ];
// EPPS 车车辆规格订购状态
var orderPreStatus = [ [ '0', '未订购（可删）' ], [ '1', '已订购' ] ];
// 装配状态
//var statusArray = [['1','初始'],['2','已计划'],['3','已冻结'],['4','已结算'],['5','已取消']];
//是否计算
var isCountArray = [ [ '0', '是' ], [ '1', '否' ] ];
// 计算状态
var isCalculateStatus = [ [ '0', '已计算' ], [ '1', '未计算' ] ];

// 是否匹配
var isMatchStatus = [ [ '0', '已匹配' ], [ '1', '未匹配' ] ];

// 计划状态 初定、锁定、调整、发布
var planStatusArray = [ [ '1', '初定' ], [ '2', '锁定' ], [ '4', '发布' ] ];
//////////整车日排序计划 杨选举 end////////////
// 跨度单位
var spanUnitStatus = [ [ '0', '日' ], [ '1', '周' ] ];
// 日期(周)
var weekArray = [ [ '000', '周日' ], [ '001', '周一' ], [ '002', '周二' ],
		[ '003', '周三' ], [ '004', '周四' ], [ '005', '周五' ], [ '006', '周六' ] ];
// 趟次0-8
var routeNumArray = [ [ '0', '0' ], [ '1', '1' ], [ '2', '2' ], [ '3', '3' ],
		[ '4', '4' ], [ '5', '5' ], [ '6', '6' ], [ '7', '7' ], [ '8', '8' ] ];
// 班次(周)
var orderClassArray = [ [ '1', '一班' ], [ '2', '二班' ], [ '3', '三班' ],
		[ '4', '四班' ] ];

var dataSrcArray = [ [ '', '' ], [ '0', 'Excel' ], [ '1', '简易' ] ];
// PV零件需求计算状态  已计算估计材料、已计算零件需求
var pvCalculateStatus = [ [ '0', '已计算估计材料' ], [ '2', '已计算零件需求' ] ];

//是否处理
var isDoStatus = [['0','已处理'],['1','未处理']];

// 零件订购主数据常量定义start//////////////////////////////////////////////////
// 状态
var PART_MAIN_DATA_STATUS_HEZHUN_FLG = '1';
var PART_MAIN_DATA_STATUS_HEZHUN = '已核准';
var PART_MAIN_DATA_STATUS_DONGJIE_FLG = '2';
var PART_MAIN_DATA_STATUS_DONGJIE = '已冻结';

// 校验
var PART_MAIN_DATA_VERIFY_YES_FLG = '1';
var PART_MAIN_DATA_VERIFY_YES = '是';
var PART_MAIN_DATA_VERIFY_NO_FLG = '2';
var PART_MAIN_DATA_VERIFY_NO = '否';

// 单位
var PART_MAIN_DATA_WEIGHT = 'kg';
var PART_MAIN_DATA_SUPPLY_RATE = '%';
var PART_MAIN_DATA_UNIT_WEIGHT = 'KG';
var PART_MAIN_DATA_UNIT_VOLUME = 'M3';
var PART_MAIN_DATA_SIZE = 'MM';
// 供应商主数据常量定义end//////////////////////////////////////////////////
var SUPPLIER_MAIN_DATA_TIME_DAY_FLG = '10';
var SUPPLIER_MAIN_DATA_TIME_DAY = '天';
var SUPPLIER_MAIN_DATA_TIME_HOUR_FLG = '5';
var SUPPLIER_MAIN_DATA_TIME_HOUR = '小时';
var AOG_FACTORY = 'GAMC01';
var AOG_NAME = '广汽丰田';
var CALCULATE_TYPE_A = 'A';
var CALCULATE_TYPE_B = 'B';
var calculateTypeArray = [ [ "A", "定时" ], [ "B", "定量" ] ];
var itemCalculateTypeArray = [ [ "A", "分批比较" ], [ "B", "累计剩余量" ] ];
// 供应商主数据常量定义start//////////////////////////////////////////////////

// 零件订购主数据数据来源
var partOrderDataOrigin = [ [ "1", "系统导入" ], [ "2", "手工添加" ] ]
//计划状态 初定、锁定、调整、发布
var planMesStatusArray = [ [ '5', '已排序' ], [ '20', '已生成VIN' ], [ '25', '在制' ],
		[ '30', '报废' ], [ '35', '完成' ] ];
//物料清单是否虚拟
var DATETYPEARRAY = [ [ 0, "工作日" ], [ 1, "非工作日" ] ];

// 零件订购主数据常量定义end//////////////////////////////////////////////////
// 加工不良基准常量定义start//////////////////////////////////////////////////
var PROCESSBAD_DELFLGNO = '0';
var PROCESSBAD_DELFLGYES = '1';
// 加工不良基准常量定义end//////////////////////////////////////////////////
//////   E-PPS用Code定义      结束  ////////////////////////////////////////////////////////////////////////


//厂外拉动
var ld_PrintStatus = [['0','未打印'],['1','已打印'],['2','已手工打印']];

