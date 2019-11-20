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
				<form id="thisForm" method="post"  class="form-horizontal" >
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">设备编号:</label></th>
								<td class="width-35">${vo.apparaVo.no}</td>
								<th class="width-15 active"><label class="pull-right">仪器名称:</label></th>
								<td class="width-35">${vo.apparaVo.name}</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">维&nbsp;&nbsp;修&nbsp;人:</label></th>
								<td class="width-35">${vo.person}</td>
								<th class="width-15 active"><label class="pull-right">维修日期:</label></th>
								<td class="width-35">
									${vo.date }
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">维修单位:</label></th>
								<td class="width-35">${vo.unit}</td>
								<td class="active"><label class="pull-right">维修结果:</label></td>
								<td>
									${vo.result}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">维修内容:</label></td>
								<td colspan="3">
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">${vo.remark}</td>
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
		
		var index = parent.layer.getFrameIndex(window.name); 
		function fnSelect(){
			var t = $("#thisForm").FormValidate();
			if(t){
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    		parent.layer.msg(res.message, {icon: 0,time: 3000})
						parent.jqgridReload();
						parent.layer.close(index);
			        }else{
			        	layer.msg(res.message, {icon: 0,time: 3000});
			        }
				});
			}
		}
	</script>
</body>
</html>
