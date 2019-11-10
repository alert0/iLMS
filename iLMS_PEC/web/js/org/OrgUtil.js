function setMaster(id,isMaster,userId){
	var msg=isMaster?"确定设置主组织吗?":"确定取消主组织吗?";
	if(isMaster){
		try {
			var  hasMaster = getOrgUserIsMaster(userId);
			if(hasMaster){
				msg = "该用户已有主组织，重新设置主组织将取消原来的主组织！确定重新设置主组织吗？";
			}
		} catch (e) {}
	}
	$.topCall.confirm("提示信息",msg,function(rtn){
		if(!rtn) return;
		var url=__ctx +"/org/orgUser/setMaster?id="+id;
		$.post(url,function(){
			refreshTargetGrid("grid");
		})
	});
}

function setCharge(userId,isChargeVal,isCharge,orgId){
	var msg=isCharge?"确定设置为组织负责人吗?":"确定取消组织负责人吗?";
	if(isChargeVal==2  && !isCharge){
		msg = "确定取消主负责人吗?";
	}
	if(orgId){
		if(isCharge){
			try {
				var hasCharge = getOrgUserIsCharge(orgId,userId);
				if(hasCharge){
					msg = "组织已有主负责人，确定将该用户设置为组织的主负责人吗？";
				}else{
					msg = "确定设置为组织的主负责人吗？";
				}
			} catch (e) {}
		}
	}
	$.topCall.confirm("提示信息",msg,function(rtn){
		if(!rtn) return;
		var url=__ctx +"/org/orgUser/setCharge?userId="+userId+"&orgId="+orgId+"&isCharge="+isCharge;
		$.post(url,function(){
			refreshTargetGrid("grid");
		})
	});
}


//获取组织是否已有主负责人
function getOrgUserIsCharge(orgId,userId){
	var isCharge = false;
	var url=__ctx +"/org/orgUser/getOrgIsCharge?orgId="+orgId+"&userId="+userId;
	$.ajax({
		url : url,
		async:false,
		dataType : 'json',
		success : function(result) {
			if(parseInt(result.result) ==1){
				isCharge = true;
			}
		}
	});
	return isCharge;
}

//获取该用户是否已有主组织
function getOrgUserIsMaster(id){
	var ismaster = false;
	var url=__ctx +"/org/orgUser/getOrgUserIsMaster?id="+id;
	$.ajax({
		url : url,
		async:false,
		dataType : 'json',
		success : function(result) {
			if(parseInt(result.result) ==1){
				ismaster = true;
			}
		}
	});
	return ismaster;
}
