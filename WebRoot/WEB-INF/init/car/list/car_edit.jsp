<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>车辆清单</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">车牌号码:</label></td>
								<td class="width-35"><input id="code" name="code" class="form-control required" validate="required" type="text" value="${vo.code}" /></td>
								<td class="width-15 active"><label class="pull-right">车辆名称:</label></td>
								<td class="width-35"><input id="name" name="name" class="form-control required" validate="required" type="text" value="${vo.name}" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label></td>
								<td><input id="rule" name="rule" class="form-control" type="text" value="${vo.rule }" /></td>
								<td class="width-15 active"></td>
								<td class="width-35"></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">保管人员:</label></td>
								<td>
									<div class="input-group">
										<input id="userName" name="userName" class="form-control required" validate="required" type="text" value="${vo.userName}" onclick="fnSelectUser()" /> <input id="userId" name="userId" type="hidden" value="${vo.userId}" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
								<td class="active"><label class="pull-right">购买日期 :</label></td>
								<td class="width-35">
								<div class="input-group date" >
										<input id="buyDate" name="buyDate"  class="form-control dateISO"  type="text" value="${vo.buyDate}" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>								
								 </td>
								
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">保养时间:</label></td>
								<td class="width-35">
								<div class="input-group date" >
										<input id="careDate" name="careDate"  class="form-control dateISO"  type="text" value="${vo.careDate}" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>								
								 </td>
								<td class="width-15 active"><label class="pull-right">保养周期:</label></td>
								 <td><input id="careCycle" name="careCycle" class="form-control" type="text" value="${vo.careCycle }" placeholder="公里"/></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">使用油类:</label></td>
								<td class="width-35"><input id="oil" name="oil" class="form-control" type="text" value="${vo.oil }" /></td>
								<td class="active"><label class="pull-right">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</label></td>
								<td><input id="status" name="status" class="form-control" type="text" value="${vo.status}" readonly="readonly" />
								<%-- <select id="status" name="status" class="form-control">
										<c:choose>
											<c:when test="${vo.status=='使用中'}">
												<option value="使用中" selected="selected">使用中</option>
												<option value="未使用">未使用</option>
											</c:when>
											<c:otherwise>
												<option value="使用中">使用中</option>
												<option value="未使用" selected="selected">未使用</option>
											</c:otherwise>
										</c:choose>
								</select> --%>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3"><textarea maxlength="128" rows="2" class="form-control" id="remark" name="remark">${vo.remark}</textarea></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('save4Date.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a> 
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}');"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</a> 
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<script>
		function formSubmitSave(url){
			$('#thisForm').attr('action',url);
			$('#thisForm').submit()
		}
		function formSubmit(url){
			var b = $("#thisForm").FormValidate();
			if (b){
				$('#thisForm').attr('action',url);
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
	<script>
	function fnSelectUser(){
		var idStr=$('#userId').val();
		layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/sys/account/user_select.do?id='+idStr,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelect();
				$('#userId').val(data.id);
				$('#userName').val(data['userVo.name']);
			}
		});
	}
	
	</script>
</body>
</html>
