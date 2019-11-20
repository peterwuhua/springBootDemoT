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
					<li><a href="javascript:backMainPage();">部门</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" id="editForm" class="form-horizontal" name="form" onsubmit="return checkOrgCode(form.code.value);"">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">上级:</label></th>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="selectName" class="form-control " placeholder="请选择上级部门" value="${vo.parentVo.name}" onclick="fnSelect()"> 
										<input type="hidden" id="selectId" name="parentVo.id" value="${vo.parentVo.id}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">名称:</label></td>
								<td class="width-35"><input id="name" placeholder="名称" class="form-control required" validate="required" maxlength=64 name="name" type="text" value="${vo.name}" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">code:</label></td>
								<td><c:choose>
										<c:when test="${fn:length(vo.id)>0}">
											<input id="code" name="code" class="form-control required" validate="required" maxlength=64 placeholder="编码" type="text" value="${vo.code }" readonly="readonly" />
										</c:when>
										<c:otherwise>
											<input id="code" name="code" class="form-control required" validate="required" maxlength=64 placeholder="编码" type="text" value="${vo.code }" />
										</c:otherwise>
									</c:choose></td>
								<td class="active"><label class="pull-right">排序:</label></td>
								<td><input id="sort" name="sort" class="form-control " placeholder="排序" type="text" value="${vo.sort }" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">类别:</label></td>
								<td>
									<select id="type" name="type" class="form-control ">
									</select>
								</td>
								<td class="active"><label class="pull-right">说明:</label></td>
								<td><input id="describtion" name="describtion" class="form-control " placeholder="说明" type="text" value="${vo.describtion }" /></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="row">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('save.do');">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();">
								<i class="fa fa-check" aria-hidden="true"></i> 保存并返回
							</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
<script>
	function fnSelect() {
		var pId = $('#selectId').val();
		var pName = $('#selectName').val();
		layer.open({
			title : '部门选择',
			type : 2,
			area : [ '350px', '75%' ],
			fix : false, //不固定
			maxmin : true,
			content : 'select.do?parentVo.id=' + pId + '&parentVo.name='
					+ encodeURI(pName),
			btn : [ '确定', '取消' ], //按钮
			btn1 : function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data = iframeWin.fnSelect();
				$('#selectId').val(data.id);
				$('#selectName').val(data.name);
			}
		});
	}
	function checkOrgCode(code) {
		var id = '${vo.id}';
		var flag = false;
		$.ajax({
			url : '${basePath}sys/org/checkOrgCode.do',
			dataType : "json",
			data : {
				"id" : id,
				"code" : code
			},
			type : "post",
			async : false,
			success : function(data) {
				if ("error" == data.type) {
					layer.alert(data.message, {
						icon : 2,
						shadeClose : true
					});
				}
				if ("success" == data.type) {
					flag = true;
				}
			},
			error : function(ajaxobj) {
			}
		});
		return flag;
	}
	function typeSelect() {
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=org-type',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					if('${vo.type}'== value[i]){
					optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"+ value[i] + "</option>";
					}else{
						optionstring += "<option value=\"" + value[i] + "\" >"+ value[i] + "</option>";
					}
				}
				$("#type").html(optionstring);
			}
		});
	}
	$(function(){
		typeSelect()
	});
</script>
</html>
