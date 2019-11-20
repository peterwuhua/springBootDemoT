<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>

</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>请假审批</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content" style="padding: 5px 10px;">
				<form method="post" action="" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" id="id" value="${vo.id}" type="hidden" />
					</c:if>
					<c:if test="${fn:length(vo.no)>0}">
						<input name="no" id="no" value="${vo.no}" type="hidden" />
					</c:if>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label></td>
								<td class="width-35">
								  <input name="type" id="type" class="form-control " readonly="readonly" type="text"  value="${vo.type}" />
								</td>
								<td class="width-15 active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></td>
								<td class="width-35">
									<input  id="deptName" name="deptName" class="form-control " readonly="readonly"  type="text" value="${vo.deptName}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">开始时间:</label></td>
								<td>
										<input id="beginTime" name="beginTime"  class="form-control  " readonly="readonly"  type="text" value="${vo.beginTime }" /> 
								</td>
								<td class="active"><label class="pull-right">结束时间:</label></td>
								<td>
										 <input id="endTime" name="endTime"  class="form-control  " readonly="readonly" type="text" value="${vo.endTime }" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">共&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计:</label></td>
								<td class="width-35">
								   <div style="width:95%;height:39px;float:left;">
								   	<input  id="sumDay" name="sumDay"  class="form-control number" readonly="readonly" type="text" value="${vo.sumDay}" />
								   </div>
								   <div  style="width:5%;height:39px;float:left;"><label style=" position: relative;top:20%;left:50%;text-align: center;">小时</label></div>
								</td>
								<td class="active"></td>
								<td>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">请假事由:</label></td>
								<td colspan="3">
								   <textarea maxlength="128" rows="3" class="form-control" id="remark" readonly="readonly" name="remark">${vo.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">职位代理人:</label></td>
								<td>
										<input type="text" id="jober" name="jober" class="form-control" readonly="readonly" value="${vo.jober}" /> 
										<input type="hidden" id="joberId" name="joberId" value="${vo.joberId}" />
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">申请人员:</label></td>
								<td>
									<input  id="person" name="person" class="form-control " readonly="readonly" type="text" value="${vo.person}" />
									<input type="hidden" id="personId" name="personId" value="${vo.personId}" >
								</td>
								<td class="active"><label class="pull-right">申请日期:</label></td>
								<td>
									<input  id="supportDate" name="supportDate" class="form-control " readonly="readonly" type="text" value="${vo.supportDate}" />
								</td>
							</tr>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
								<c:choose>
								   	<c:when test="${vo.vstatus=='审批' }">
								   		<tr>
											<td class="width-15 active"><label class="pull-right">审批人员:</label></td>
											<td class="width-35">
												<input id="sumUserName" name="sumUserName"   class="form-control"  maxlength="128" type="text" value="${vo.sumUserName}" />
											    <input id="sumUserId" name="sumUserId" type="hidden" value="${vo.sumUserId}"/>
											</td>
											<td class="width-15 active"><label class="pull-right">审批日期:</label></td>
											<td class="width-35">
												<input id="sumDate" name="sumDate"   class="form-control"  type="text" value="${vo.sumDate}" />
											</td>
										</tr>
								   	</c:when>
							   </c:choose>
							<tr>
							   <c:choose>
								   	<c:when test="${vo.vstatus=='提交' }">
								   		<td class="width-15 active"><label class="pull-right">提交意见:</label></td>
										<td colspan="3"><textarea maxlength="128" rows="3" class="form-control" id="sumRemark" name="sumRemark" placeholder="请领导阅示！">请领导阅示！</textarea></td>
								   	</c:when>
								   	<c:otherwise>
										<td class="width-15 active"><label class="pull-right">审批意见:</label></td>
										<td colspan="3"><textarea maxlength="128" rows="3" class="form-control" id="sumRemark" name="sumRemark" placeholder="审批意见">${vo.sumRemark }</textarea></td>
								   	</c:otherwise>
							   </c:choose>
								
							</tr>
						 </tbody>	
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							 	<c:choose>
								   	<c:when test="${vo.vstatus=='提交' }">
								   		<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('updateDatad.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 提交</button>
								   	</c:when>
								   	<c:otherwise>
									   	<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('auditSuccess.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 审批通过</button>
										<button class="btn btn-w-m btn-danger" type="button" onclick="formSubmit('auditFailure.do');"><i class="fa fa-check" aria-hidden="true"></i> 审批不通过</button>
								   	</c:otherwise>
							   </c:choose>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
        <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
	<script>
	function fnSelectJober() {
		layer.open({
			title:'用户信息',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/selects.do',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var selectData = iframeWin.fnSelect();
			  $("#jober").val(selectData.name);
			  $("#joberId").val(selectData.id);
			}
		})
	}
	function formSubmitSave(url){
		$('#thisForm').attr('action',url);
		$('#thisForm').submit();
	}
	function formSubmit(url){
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
		}
	}
	
	function backMainPage(){
		 var basePath=location.href.substr(0,location.href.lastIndexOf("/")+1);
		 var currentIframe=$(window.parent.document).find(".J_iframe").filter( ":visible" );
		 var editPageKey=$(currentIframe).attr("data-id");
		 var mainPageKey=window.parent.pageMap.get(editPageKey);
		 window.parent.pageMap.remove(editPageKey);//移除map缓存
		 var mainIframe=$(window.parent.document).find('iframe[data-id="'+mainPageKey+'"]')[0];
		 $(mainIframe).attr("data-id",editPageKey);
		 $(mainIframe).css("display","inline");
		 var treeUrl=mainIframe.contentWindow.treeUrl;
		 if(!!treeUrl&&treeUrl!='undefined'){
			 mainIframe.contentWindow.reloadTree(basePath+treeUrl);
		 }
		 mainIframe.contentWindow.jqgridReload(); 
		 currentIframe.remove();
	}
	
	
	
	</script>
</body>
</html>