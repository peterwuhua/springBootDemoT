<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
.panel-heading{
	padding: 0px;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">合同管理</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}" class="form-horizontal" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<input name="flag" value="${vo.flag}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">合同编号:</label></td>
								<td class="width-35"><input id="code" readonly="readonly" class="form-control"  maxlength=64 name="code" type="text" value="${vo.code}" /></td>
								<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
								<td class="width-35">
										<input type="text" readonly="readonly" id="cusName" class="form-control"   value="${vo.customerVo.name}"> 
										<input type="hidden" id="cusId" name="customerVo.id" value="${vo.customerVo.id}">
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td class="width-35"><input id="contacts" class="form-control"  readonly="readonly" maxlength=32 name="contacts" type="text" value="${vo.contacts}" /></td>
								<td class="width-15 active"><label class="pull-right">联系电话:</label></td>
								<td class="width-35"><input id="contactPhone" class="form-control"  readonly="readonly" maxlength=32 name="contactPhone" type="text" value="${vo.contactPhone}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</label></td>
								<td class="width-35"><input id="address" class="form-control"  readonly="readonly" maxlength=256 name="address" type="text" value="${vo.address}" /></td>
								<td class="width-15 active"><label class="pull-right">合同金额:</label></td>
								<td class="width-35"><input id="contractSum" class="form-control"  readonly="readonly" maxlength=16 name="contractSum" type="text" value="${vo.contractSum}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开始日期:</label></td>
								<td class="width-35"><input id="sdate" class="form-control" readonly="readonly"  maxlength=16 name="sdate" type="text" value="${vo.sdate}" /></td>
								<td class="width-15 active"><label class="pull-right">结束日期:</label></td>
								<td class="width-35"><input id="edime" class="form-control" readonly="readonly"  maxlength=16 name="edime" type="text" value="${vo.edime}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">负&nbsp;&nbsp;责&nbsp;人:</label></td>
								<td class="width-35">
									<div class="input-group col-md-12">
									 <c:if test="${empty vo.saler}">
									    <input id="leadName" name="leadName" class="form-control" readonly="readonly"  maxlength="32" type="text" value="${vo.leadName}" />
										<input name="leadId" id="leadId" value="${vo.leadId}" type="hidden" />
									 </c:if>
									 <c:if test="${empty vo.leadName}">
									 	<input id="saler" name="saler" class="form-control" readonly="readonly"  maxlength="32" type="text" value="${vo.saler}" />
										<input name="salerId" id="salerId" value="${vo.salerId}" type="hidden" />
									 </c:if>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">电子合同:</label>
								</td>
								<td>
								<div class="col-md-12">
								    <div class="col-md-8"></div>
								    <div class="col-md-4">
										<c:if test="${fn:length(vo.path)>0}">
												<a href="download.do?filePath=${vo.path}&trueName=${vo.trueName}" class="btn btn-w-m btn-info">下载文件</a>
										</c:if>
									</div>
								</div>	
							    </td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">文件上传:</label></td>
								<td><input id="file" name="file" class="form-control" type="file" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<input id="remark" class="form-control" name="remark" maxlength=128 type="text" value="${vo.remark}" />
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();"><i class="fa fa-check" aria-hidden="true"></i> 确定</button>
							<a href="javascript:backMainPage();" class="btn btn-w-m btn-white" type="button"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<script>
function fnSelectCustomer(pId,pName){
	layer.open({
		title:'客户信息选择',	
		type: 2,
		area: ['800px', '470px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}cus/customer/select.do?id='+pId+'&name='+pName,
		btn: ['确定','取消'],
		btn1: function(index, layero) {
		  var iframeWin = window[layero.find('iframe')[0]['name']];
		  var selectData = iframeWin.fnSelect();
		  $("#cusName").val(selectData.name);
		  $("#cusId").val(selectData.id);
		  $("#contacts").val(selectData.person);
		  $("#contactPhone").val(selectData.phone);
		  $("#address").val(selectData.address);
		}
	});
}
/**
 * 通过公共代码获取合同执行状态
 */
function conStatusSelect(){
	$.ajax({
		url : '${basePath}sys/code/ajaxCodeContent.do?code=con-status',
		datatype : "json",
		success : function(data) {
			var value = data.split(",");
			var optionstring = "";
			var status = '${vo.status}';
			for (var i = 0; i < value.length; i++) {
				if(value[i] == status){
					optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\" >"+ value[i] + "</option>";
				}else{
					optionstring += "<option value=\"" + value[i] + "\"  >"+ value[i] + "</option>";
				}
			}
			$("#status").html(optionstring);
		}
	});
}
function fnSelectUser(){
	var salerId=$('#salerId').val();
	layer.open({
		title:'人员选择',	
		type: 2,
		 area: ['70%', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: '/sys/account/user_select.do?id='+salerId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			console.log(data)
			$('#salerId').val(data.id);
			$('#saler').val(data['userVo.name']);
		}
	});
}
$(function(){
	conStatusSelect();
});
$('input[type="file"]').prettyFile();
</script>
</body>
</html>
