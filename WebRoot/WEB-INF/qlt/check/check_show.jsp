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
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal">
						<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">被查部门:</label></th>
								<td>
									${vo.orgName}
								</td>
								<th class="width-15 active"><label class="pull-right">抽查日期:</label></th>
								<td class="width-35">
									${vo.date}
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></th>
								<td class="width-35">
									${vo.user }
								</td>
								<th class="width-15 active"><label class="pull-right">联系电话:</label></th>
								<td class="width-35">
									${vo.phone }
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">执行标准:</label></th>
								<td class="width-35">
									<input id="stand" class="form-control" name="stand" type="text" value="${vo.stand}" />
								</td>
								<th class="width-15 active"><label class="pull-right">使用仪器:</label></th>
								<td class="width-35">
									<input id="appName" class="form-control" name="appName" type="text" value="${vo.appName}" />
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">抽&nbsp;&nbsp;查&nbsp;人:</label></th>
								<td class="width-35">
									${vo.userName}
								</td>
								<th class="width-15 active"><label class="pull-right">监&nbsp;&nbsp;督&nbsp;人:</label></th>
								<td class="width-35">
									${vo.jdName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">抽查内容:</label></td>
								<td colspan="3">
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">抽查结果:</label></td>
								<td colspan="3">
									${vo.result}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">综合评价:</label></td>
								<td colspan="3">
									${vo.pj}
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
		var index = parent.layer.getFrameIndex(window.name);
		function fnSelect() {
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
		$(document).ready(function() {
			$(".i-checks").iCheck({
				checkboxClass : "icheckbox_square-green",
				radioClass : "iradio_square-green",
			});
		});
	</script>
</body>
</html>
