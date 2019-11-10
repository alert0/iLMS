<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>xq_meetingroom_appointment</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
				<%-- 		<div class="buttons">
							<a class="btn btn-sm btn-primary fa-add" href="#" onclick="$.openFullWindow('${ctx}/flow/instance/instanceToStart?defId=10000005350037')" >
								<span>申请会议室</span>
							</a>
						</div>  --%>
						<div class="buttons" style='float: right;'>
							<a class="btn btn-sm btn-primary " href="#" id="last-week">
								<span>上一周</span>
							</a>
							<a class="btn btn-sm btn-primary" href="#" id="next-week">
								<span>下一周</span>
							</a>
						</div>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script>
var weekArr=[];
var currentFirstDate;
var defId='';
	$(function() {
	      setDate(new Date());	
	      document.getElementById('last-week').onclick = function(){
		        setDate(addDate(currentFirstDate,-7));     
		      };       
	      document.getElementById('next-week').onclick = function(){         
	        setDate(addDate(currentFirstDate,7));
	      };
	      getBpmDefId ();
	});
		
	 function setDate (date){ 
		 weekArr=[];
        var week = date.getDay()-1;
        date = addDate(date,week*-1);
        currentFirstDate = new Date(date);
        for(var i = 0;i<7;i++){ 
        	var dateStr=formatDate(i==0 ? date : addDate(date,1));
        	weekArr.push(dateStr);
        }
        loadGrid(weekArr);
      };
      function addDate (date,n){    
	        date.setDate(date.getDate()+n);    
	        return date;
	      };
	  function getBpmDefId (){    
		   $.post(__ctx +"/business/meeting/meeting/getBpmDefId", {}, function(data) {
			   defId=data;
			});
	   };      
      function formatDate(date){       
        var year = date.getFullYear();
        var month = (date.getMonth()+1)<10?'0'+(date.getMonth()+1):date.getMonth()+1;
        var day = date.getDate()<10?'0'+date.getDate():date.getDate();
        var week = ['周日','周一','周二','周三','周四','周五','周六'][date.getDay()]; 
        return year+'-'+month+'-'+day+'|'+week;
      };    
     
	  function  pendingMeeting(id,date)
		{   
		    var title="申请会议室";
		    var url="${ctx}/flow/instance/instanceToStart?defId="+defId+"&params=" +id;
		    if(date){
		    	url+= '.'+date;
		    }
		    $.openFullWindow(url);
		}
	  function openDetail(id)
		{
		    var title="查看会议详情";
		    HT.window.openEdit("${ctx}/flow/instance/instanceGet?id="+id,title, 'view', 'grid', 500, 500, null, null, id, true);
		    /* var url = __ctx + "/flow/instance/instanceGet?id="+id;
		    top.layer.open({  
		        type: 2,  
		        title: title,  
		        shadeClose: true,  
		        shade: 0.3,  
		        area : ['100%' , '100%'],  
		        content: [url, 'no']
		      }); */
		}
	function loadGrid(weekArr) {
		var elArr=[];
		var queryStr="";
		for(var i=0;i<weekArr.length;i++){
			queryStr+=weekArr[i]+",";
			var el=weekArr[i].split('|')[1].replace("]",'');
			elArr.push({elName:el,title:el+'<br>'+weekArr[i].split('|')[0]});
		}
		var column='';
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx +"/business/meetingAppoint/meetingAppoint/listJson",
			idField : "id",	
			sortName : 'ID_',
			sortOrder : 'desc',
			queryParams: {param:queryStr}, 
			nowrap:false,
			fitColumns:true,
			striped : true,
			columns : [ [
			/* {field : 'id',sortName : "ID_",checkbox : true},    */
			{field : 'mtName',sortName : "MEETINGROOM_ID_",title : '会议室名称',width : 100,align : 'center'
			  
			
			}, 
			{field : elArr[0].elName,sortName : "MEETING_ID_",title : elArr[0].title,width : 100,align : 'center'

			}, 
			{field : elArr[1].elName,sortName : "MEETING_NAME_",title : elArr[1].title,width : 100,align : 'center'
			
			}, 
			{field : elArr[2].elName,sortName : "HOSTESS_NAME_",title : elArr[2].title,width : 100,align : 'center'
		
			
			}, 
			{field : elArr[3].elName,sortName : "HOSTESS_NAME_",title : elArr[3].title,width : 100,align : 'center'

				
			}, 
			{field : elArr[4].elName,sortName : "HOSTESS_NAME_",title : elArr[4].title,width : 100,align : 'center'
	
			
			},  
			{field : elArr[5].elName,sortName : "HOSTESS_NAME_",title : elArr[5].title,width : 100,align : 'center'
	
				
			},  
			{field : elArr[6].elName,sortName : "HOSTESS_NAME_",title : elArr[6].title,width : 100,align : 'center'

				
			} , 
			{
				field : 'mtRoomId',
				title : '操作',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					//<span class=\"cur\" onClick=\"pendingMeeting("+row.mtRoomId+")\" >申请</span>
					 return "<a  class=\"btn btn-sm btn-primary \" onClick=\"pendingMeeting("+row.mtRoomId+")\">申请</a>";
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
				autoFixed();
			}
		}));
	}
	function autoFixed(){
		$('.pendingDiv').each(function(){
			var hieth=$(this).closest('td').height();
			$(this).css('height',hieth);
		})
	}
</script>
