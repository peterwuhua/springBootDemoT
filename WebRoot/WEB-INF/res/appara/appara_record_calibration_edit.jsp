<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
.panel-heading {
	padding: 0px;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'update2File.do':'add2File.do'}" class="form-horizontal" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<input type="hidden" name="apparaVo.id" id="apparaId" class="formText" value="${vo.apparaVo.id}" />
					<input type="hidden" name="type" id="type" value="${vo.type}" /> 
					<input type="hidden" id="cycle" name="cycle" value="${vo.cycle}" /> 
					<input type="hidden" name="status" value="${isShow}" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">校&nbsp;准&nbsp;&nbsp;人:</label></th>
								<td class="width-35">
									 <div class="input-group" style="width: 100%">
										<input id="person" class="form-control required" validate="required" name="person" type="text" value="${vo.person}" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
								<th class="width-15 active"><label class="pull-right">校准日期:</label></th>
								<td class="width-35">
									<div class="input-group date">
										<input id="date" class="form-control required dateISO" validate="required" name="date" type="text" value="${vo.date }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">计量单位:</label></th>
								<td class="width-35"><input id="unit" class="form-control" name="unit" type="text" value="${vo.unit}" /></td>
								<td class="active"><label class="pull-right">校准器具:</label></td>
								<td><input name="appliances" value="${vo.appliances}" id="appliances" type="text" class="form-control" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">校准标准不确定度:</label></td>
								<td><input name="uncertainty" value="${vo.uncertainty}" id="uncertainty" type="text" class="form-control" /></td>
								<td class="active"><label class="pull-right">计量标准量程:</label></td>
								<td><input name="standardRange" value="${vo.standardRange}" id="standardRange" type="text" class="form-control" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">所依据技术文件:</label></td>
								<td><input id="basisFile" name="basisFile" value="${vo.basisFile}" type="text" class="form-control" /></td>
								<td class="active"><label class="pull-right">校准总结论:</label></td>
								<td><input id="result" name="result" value="${vo.result}" type="text" class="form-control" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3"><input maxlength="128" name="remark" id="remark" value="${vo.remark}" type="text" class="form-control" /></td>
							</tr>
							<tr>	
								<td class="active"><label class="pull-right">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:</label></td>
								<input type="hidden" name="fileUrl" value="${vo.fileUrl}" />
								<td><input type="file" name="file" /></td>
								<td colspan="2">
									<a href="download.do?filePath=${vo.fileUrl}&trueName=${vo.trueName}">${vo.trueName}</a>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script type="text/javascript">
		$('input[type="file"]').prettyFile();
		function fnSelectUser(){
			parent.layer.open({
				title:'人员选择',	
				type: 2,
				 area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}sys/account/select.do',
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin=layero.find('iframe')[0];
				  var data=iframeWin.contentWindow.fnSelect();
				   $('#person').val(data.name);
				}
			});
		}
		var index = parent.layer.getFrameIndex(window.name);
		function fnSelect() {
			if(!$("#cycle").val()){
				layer.msg('请先在仪器信息中维护校准周期', {icon: 0,time: 3000});
				return false;
			} 
			var t = $("#thisForm").FormValidate();
			if (t) {
				$('#thisForm').ajaxSubmit(function(res) {
					if (res.status == 'success') {
						parent.layer.msg(res.message, {
							icon : 0,
							time : 3000
						})
						parent.jqgridReload();
						parent.layer.close(index);
					} else {
						layer.msg(res.message, {
							icon : 0,
							time : 3000
						});
					}
				});
			}
		}
	</script>
</body>
</html>
