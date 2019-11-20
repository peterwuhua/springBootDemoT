<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

</head>
<body class="gray-bg">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a href="javascript:backMainPage();">样品类别</a></li>
						<li><strong>编辑</strong></li>
					</ol>
				</div>
				<div class="ibox-content">
					<form method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" id="editForm" class="form-horizontal" name="form"　>
                    <c:if test="${fn:length(vo.id)>0}">
                    	<input name="id" value="${vo.id}" type="hidden" />
                    </c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">上级类型:</label></th>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="selectName" class="form-control" placeholder="请选择上级部门" value="${vo.parentVo.name}">
										<input type="hidden" id="selectId"  name="parentVo.id" value="${vo.parentVo.id}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">样品类别:</label></td>
								<td class="width-35"><input id="name" placeholder="样品类别"  class="form-control required" validate="required" maxlength=64 name="name" type="text" value="${vo.name}"/></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">样品编号:</label></td>
								<td><input id="typeNo" name="typeNo"
									class="form-control"  maxlength=64 placeholder="类型编号" type="text" value="${vo.typeNo}" />
								</td>
								<td class="active"><label class="pull-right">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类:</label></td>
								<td><input id="type" name="type" class="form-control required" validate="required" placeholder="水、气、声、土、生" type="text" value="${vo.type}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label></td>
								<td><input id="sort" name="sort"
									class="form-control " placeholder="排序" type="text" value="${vo.sort}" />
								</td>
								<td class="active"><label class="pull-right">说&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;明:</label></td>
								<td colspan="3">
									<input maxlength="128" id="remark" name="remark"
										class="form-control " placeholder="说明" type="text" value="${vo.remark}" />
								</td>
							</tr>
						</tbody>
					</table>
					<div class="row">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
							<a href="javascript:backMainPage();" class="btn btn-w-m btn-white" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
					</form>
				</div>
			</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
<script>
	
function fnSelect(){
	var pId=$('#selectId').val();
	layer.open({
		title:'上级选择',	
		type: 2,
		area: ['350px', '75%'],
		fix: false, //不固定
		maxmin: true,
		content: 'select.do?parentVo.id='+pId,
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
</html>
