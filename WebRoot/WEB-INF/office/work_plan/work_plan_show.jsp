<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form method="post" class="form-horizontal" id="listForm">
					<input name="id" id="id" value="${vo.id}" type="hidden" />
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">类型:</label></td>
								<td class="width-35">
									${vo.type}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">计划人:</label></td>
								<td class="width-35">
									${vo.userName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">标题:</label></td>
								<td>
									${vo.title }
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内容:</label></td>
								<td>
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">开始时间:</label></td>
								<td>${vo.startDate}${vo.startTime}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">结束时间:</label></td>
								<td>${vo.endDate}${vo.endTime}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">紧急程度:</label></td>
								<td>
									${vo.urgentLevel}
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
        <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
	<script>
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
	});
	 
	</script>
</body>
</html>