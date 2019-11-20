<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">培训管理</a></li>
					<li><strong> <c:if test="${fn:length(vo.id)>0}">编辑</c:if> <c:if test="${fn:length(vo.id)==0}">新增</c:if>
					</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input id="selectName" placeholder="请选择" class="form-control required" validate="required" name="orgName" type="text" value="${vo.orgName}" /> 
										<input id="selectId" type="hidden" name="orgId" value="${vo.orgId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectOrg()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">负&nbsp;责&nbsp;人:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input id="userName" placeholder="请选择" class="form-control required" validate="required" name="userName" type="text" value="${vo.userName }" /> 
										<input type="hidden" id="userId" name="userId" value="${vo.userId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>

								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题 :</label></td>
								<td class="width-35"><input id="title" placeholder="请输入" class="form-control required" validate="required" value="${vo.title}" name="title" type="text" /></td>
								<td class="width-15 active"><label class="pull-right">岗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
								<td class="width-35"><input id="post" placeholder="请输入" class="form-control" name="post" value="${vo.post}" type="text" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开始时间:</label></td>
								<td class="width-35">
									<div class="input-group date">
										<input id="startDate" placeholder="请选择"  class="form-control datetimeISO" name="startDate" type="text" value="${vo.startDate }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">结束时间:</label></td>
								<td class="width-35">
									<div class="input-group date">
										<input id="endDate" placeholder="请选择" class="form-control datetimeISO" name="endDate" type="text" value="${vo.endDate }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型 :</label></td>
								<td class="width-35"><select id="type" name="type" class="form-control">
										<c:choose>
											<c:when test="${vo.type=='考核'}">
												<option value="培训">培训</option>
												<option value="考核" selected="selected">考核</option>
											</c:when>
											<c:otherwise>
												<option value="培训" selected="selected">培训</option>
												<option value="考核">考核</option>
											</c:otherwise>
										</c:choose>
								</select></td>
								<td class="width-15 active"><label class="pull-right">培训地点:</label></td>
								<td class="width-35"><input id="address" placeholder="请输入" class="form-control" name="address" type="text" value="${vo.address}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">参&nbsp;与&nbsp;人:</label></td>
								<td class="width-35" colspan="3"><textarea id="trainNames" name="trainNames" rows="2" style="width: 600px;" placeholder="请输入" class="form-control" onclick="fnSelectUsers()">${vo.trainNames }</textarea> <input id="trainIds" name="trainIds" type="hidden" value="${vo.trainIds}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">培训内容:</label></td>
								<td class="width-35" colspan="3"><textarea id="content" name="content" rows="2" style="width: 600px;" placeholder="请输入" class="form-control">${vo.content }</textarea></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td class="width-35" colspan="3"><textarea maxlength="128" id="remark" name="remark" rows="2" style="width: 600px;" placeholder="请输入" class="form-control">${vo.remark }</textarea></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmitSave('save.do');">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('${fn:length(vo.id)>0? 'updateData.do?status=1':'addData.do?status=1'}');">
								<i class="fa fa-check" aria-hidden="true"></i> 保存并执行
							</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script type="text/javascript">
	function formSubmitSave(url){
		$('#thisForm').attr('action',url);
		$('#thisForm').submit()
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
	function fnSelectUser(){
		var userId=$('#userId').val();
		var userName=$('#userName').val();
		layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/user/select.do?id='+userId+'&name='+userName,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			 var user=iframeWin.fnSelect();
				 $('#userId').val(user.id);
				 $('#userName').val(user.name);
			}
		});
	}
	function fnSelectUsers(){
		var trainIds=$('#trainIds').val();
		var trainNames=$('#trainNames').val();
		layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'user_select.do?trainIds='+trainIds+'&trainNames='+trainNames,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				iframeWin.fnSelect();
			}
		});
	}
	function setUserData(id,name){
		$('#trainIds').val(id);
		$('#trainNames').html(name);
	}
	function fnSelectOrg(){
		var pId=$('#selectId').val();
		var pName=$('#selectName').val();
		layer.open({
			title:'部门选择',	
			type: 2,
			area: ['300px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/org/select.do?id='+pId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#selectId').val(data.id);
			  $('#selectName').val(data.name);
			}
		});
	}
    </script>
</body>
</html>
