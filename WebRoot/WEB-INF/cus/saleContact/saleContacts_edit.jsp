<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
.panel-heading{
	padding: 0px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<form method="post" action="${'saveDatad.do'}" name="form" class="form-horizontal" id="thisForm">
			<c:if test="${fn:length(vo.id)>0}">
				<input name="id" value="${vo.id}" type="hidden" />
			</c:if>
			<input name="salerId" value="${vo.salerId}"  type="hidden"/>
			<input name="customerId" value="${vo.customerId}"  type="hidden"/>
			<input name="email" value="${vo.email}"  type="hidden"/>
			<input name="birthDate" value="${vo.birthDate}"  type="hidden"/>
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a href="javascript:backMainPage();">客户跟踪</a></li>
						<li><strong>编辑</strong></li>
					</ol>
				</div>
				<div class="ibox-content">
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
								<td class="width-35"><input id="customerName" name="customerName" class="form-control" readonly="readonly" maxlength="128" placeholder="" type="text" value="${vo.customerName }"  /></td>
								<td class="width-15 active"><label class="pull-right">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</label></td>
								<td class="width-35"><input id="address" name="address" class="form-control" readonly="readonly" maxlength="128" placeholder="" type="text" value="${vo.address }"  /></td>
								
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;&nbsp;人:</label></td>
								<td class="width-35">
									<div class="input-group">
											<input type="text" id="contactPerson" name="contactPerson" class="form-control required" validate="required" placeholder="请选择人员" value="${vo.contactPerson}" onclick="fnSelectUser()"> 
											<input type="hidden" id="contactId" name="contactId" value="${vo.contactId}">
											<div class="input-group-btn">
												<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
											</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">联系方式:</label></td>
								<td class="width-35"><input id="contactWay" name="contactWay" class="form-control" maxlength="128" placeholder="" type="text" value="${vo.contactWay}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">所属部门:</label></td>
									<td class="width-35"><input id="department" name="department" class="form-control"  maxlength="128" placeholder="" type="text" value="${vo.department}" /></td>
									<td class="width-15 active"><label class="pull-right">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务:</label></td>
									<td class="width-35">
										<input id="duty" name="duty" class="form-control"  maxlength="128" placeholder="" type="text" value="${vo.duty}" />
									</td>
							</tr>
							<tr>
							    <td class="width-15 active"><label class="pull-right">拜访日期:</label></td>
							    <td class="width-35">
							       <div class="input-group date">
										<input id="gzDate" name="gzDate"  class="form-control dateISO" placeholder="拜访日期" type="text" value="${vo.gzDate }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>	
								<td class="width-15 active"><label class="pull-right">拜&nbsp;&nbsp;访&nbsp;&nbsp;人:</label></td>
								<td class="width-35"><input id="saler" name="saler" class="form-control" readonly="readonly" maxlength="128" placeholder="" type="text" value="${vo.saler}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">拜访内容:</label></td>
								<td colspan="3"><textarea rows="2" class="form-control" id="content" name="content" placeholder="拜访内容">${vo.content}</textarea></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit4Save('saveDatad.do');"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script type="text/javascript">


	


	

	
	
	function fnSelectUser(){
		var id = '${vo.customerId}';
		parent.layer.open({
			title:'人员选择',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}cus/contacts/select.do?customerVo.id='+id,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin=layero.find('iframe')[0];
				var data=iframeWin.contentWindow.fnSelect();
			    $('#contactId').val(data.contactId);
				$('#contactPerson').val(data.contactPerson);
				$('#contactWay').val(data.contactWay);
				$('#department').val(data.department);
				$('#birthDate').val(data.birthDate);
				$('#email').val(data.email);
				parent.layer.close(index);
			}
		});
		
	}

	
	
	function formSubmit4Save(url){
		$('#thisForm').attr('action',url);
		$('#thisForm').submit()
	}
	function formSubmit(){
		$('#thisForm').attr('action');return;
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
	</script>
</body>
</html>
