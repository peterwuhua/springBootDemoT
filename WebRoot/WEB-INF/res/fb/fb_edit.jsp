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
		<form method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" name="form" class="form-horizontal" >
			<c:if test="${fn:length(vo.id)>0}">
				<input name="id" value="${vo.id}" type="hidden" />
			</c:if>
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a href="javascript:backMainPage();">分包单位</a></li>
						<li><strong>编辑</strong></li>
					</ol>
				</div>
				<div class="ibox-content">
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label></td>
								<td class="width-35"><input id="code" name="code" class="form-control required" validate="required" maxlength="32" type="text" value="${vo.code}" /></td>
								<td class="width-15 active"><label class="pull-right">单位名称:</label></td>
								<td class="width-35"><input id="name" name="name" class="form-control required" validate="required" maxlength="128" type="text" value="${vo.name }"/></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">单位类型:</label></td>
								<td class="width-35">
									<input id="type" name="type" class="form-control"  maxlength="32" type="text" value="${vo.type}"/>
								</td>
								<td class="width-15 active"><label class="pull-right">建立日期:</label></td>
								<td class="width-35">
									<div class="input-group date">
										<input id="buildDate" name="buildDate"  class="form-control dateISO" type="text" value="${vo.buildDate }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">所属区域:</label></td>
								<td class="width-35">
									<div class="input-group">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect()">选择</button>
										</div>
										<input type="text" readonly="readonly" id="selectPath" name="areaPath" class="form-control" placeholder="请选择上级区域" value="${vo.areaPath}"> 
										<input type="hidden" id="selectId" name="areaId" value="${vo.areaId}">
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;&nbsp;人:</label></td>
								<td class="width-35"><input id="person" name="person" class="form-control " maxlength="32" type="text" value="${vo.person}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">认证能力:</label></td>
								<td class="width-35" colspan="3"><input id="zz" name="zz" class="form-control " maxlength="128" type="text" value="${vo.zz}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开始日期:</label></td>
								<td class="width-35">
									<div class="input-group date">
										<input id="startDate" name="startDate" class="form-control dateISO" type="text" value="${vo.startDate}" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">截止日期:</label></td>
								<td class="width-35">
									<div class="input-group date">
										<input id="endDate" name="endDate" class="form-control dateISO" type="text" value="${vo.endDate}" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</label></td>
								<td class="width-35"><input id="address" name="address" class="form-control " maxlength="128" type="text" value="${vo.address}" /></td>
								<td class="width-15 active"><label class="pull-right">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话:</label></td>
								<td class="width-35"><input id="phone" name="phone" class="form-control" type="text" value="${vo.phone}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
								<td class="width-35"><input id="email" name="email" class="form-control email" type="text" value="${vo.email}" /></td>
								<td class="width-15 active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
								<td class="width-35"><input id="zip" name="zip" class="form-control" type="text" value="${vo.zip}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">负&nbsp;责&nbsp;人:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input id="userName" name="userName" class="form-control required" validate="required" maxlength="32" type="text" value="${vo.userName}" onclick="fnSelectUser()"/>
										<input name="userId" id="userId" value="${vo.userId}" type="hidden" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3"><textarea maxlength="128" rows="2" class="form-control" id="remark" name="remark" >${vo.remark}</textarea></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script type="text/javascript">
	 $(document).ready(function(){
		$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})
		 
	});
	 function fnSelectUser(){
			var userId=$('#userId').val();
			layer.open({
				title:'人员选择',	
				type: 2,
				 area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '/sys/account/user_select.do?id='+userId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var data=iframeWin.fnSelect();
					console.log(data)
					$('#userId').val(data.id);
					$('#userName').val(data['userVo.name']);
				}
			});
		}
 	function gradeSelect(){
 		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=fb-grade',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					if('${vo.grade}'== value[i]){
					optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
							+ value[i] + "</option>";
					}else{
						optionstring += "<option value=\"" + value[i] + "\" >"
							+ value[i] + "</option>";
					}
				}
				$("#grade").html(optionstring);
			}
		});
 	}
	function typeSelect() {
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=fb-type',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					if('${vo.type}'== value[i]){
					optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
							+ value[i] + "</option>";
					}else{
						optionstring += "<option value=\"" + value[i] + "\" >"
							+ value[i] + "</option>";
					}
				}
				$("#type").html(optionstring);
			}
		});
	}
	 
	function fnSelect() {
		layer.open({
			title : '区域选择',
			type : 2,
			area : [ '300px', '470px' ],
			fix : false, //不固定
			maxmin : true,
			content : '${basePath}sys/area/select.do?id=' + $('#selectId').val(),
			btn : [ '确定', '取消' ], //按钮
			btn1 : function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#selectId').val(data.id);
				$('#selectPath').val(data.path);
			}
		});
	}
</script>
</body>
</html>
