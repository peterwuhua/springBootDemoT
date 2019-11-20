<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>

</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 5px 10px;">
				<form method="post" action="" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label></td>
								<td class="width-35">
								  ${vo.type}
								</td>
								<td class="width-15 active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></td>
								<td class="width-35">
									${vo.deptName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">开始时间:</label></td>
								<td>
									${vo.beginTime }
								</td>
								<td class="active"><label class="pull-right">结束时间:</label></td>
								<td>
									${vo.endTime }
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">共&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计:</label></td>
								<td class="width-35">
								   ${vo.sumDay}<label>小时</label>
								</td>
								<td class="active"></td>
								<td>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">请假事由:</label></td>
								<td colspan="3">
								   <textarea rows="3"  class="form-control" readonly="readonly">${vo.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">职位代理人:</label></td>
								<td>
									${vo.jober} 
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">申请人员:</label></td>
								<td>
									${vo.person}
								</td>
								<td class="active"><label class="pull-right">申请日期:</label></td>
								<td>
									${vo.supportDate}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">审批人员:</label></td>
								<td class="width-35">
									${vo.sumUserName}
								</td>
								<td class="width-15 active"><label class="pull-right">审批日期:</label></td>
								<td class="width-35">
									${vo.sumDate}
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
        <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
	
</body>
</html>