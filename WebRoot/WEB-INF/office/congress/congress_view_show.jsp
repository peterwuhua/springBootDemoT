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
			<div class="ibox-content" style="padding: 5px 10px;">
				<form method="post" action="" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" id="id" value="${vo.id}" type="hidden" />
					</c:if>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
							  <td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>会<br>议<br>内<br>容</label></td>
								<td class="width-15 active"><label class="pull-right">会议名称:</label></td>
								<td class="width-35">
								    ${vo.name}
								</td>
									<td class="width-15 active"><label class="pull-right">会议地点:</label></td>
								<td class="width-35">
									${vo.address}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">主&nbsp;&nbsp;讲&nbsp;人:</label></td>
								<td class="width-35">
									${vo.zjr}
								</td>
								<td class="width-15 active"><label class="pull-right">参与人员:</label></td>
								<td class="width-35">
									${vo.users}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">会议内容:</label></td>
								<td colspan="3">
								   ${vo.content}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开始时间:</label></td>
								<td class="width-35">
										${vo.beginTime }
								</td>
								<td class="width-15 active"><label class="pull-right">结束时间:</label></td>
								<td class="width-35">
										${vo.endTime }
								</td>
							</tr>
							<tr>
								<td class="active" rowspan="2" style="text-align: center;width: 50px;"><label>会<br>议<br>物<br>资</label></td>
								<td class="width-15 active"><label class="pull-right">使用车辆:</label></td>
								<td class="width-35">									
										${vo.carNames} 
								</td>
								<td class="width-15 active"><label class="pull-right">会议预算:</label></td>
								<td class="width-35">
									${vo.ysfee}
								</td>
							</tr>
							<tr>
							    <td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							    <td colspan="3">
							    	<textarea rows="3" class="form-control" readonly="readonly">${vo.qtRemark}</textarea>
							    </td>
							</tr>
							<tr>
							<td class="active" style="text-align: center;width: 50px;"></td>
							 <td class="width-15 active"><label class="pull-right">申&nbsp;&nbsp;请&nbsp;人:</label></td>
								<td class="width-35">
										${vo.sqr }
								</td>
								<td class="width-15 active"><label class="pull-right">申请日期:</label></td>
								<td class="width-35">
										${vo.supportDate }
								</td>
							</tr>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
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
							<tr>
								<td class="width-15 active"><label class="pull-right">审批意见:</label></td>
								<td colspan="3"><textarea rows="3" class="form-control" readonly="readonly">${vo.sumRemark}</textarea></td>
							</tr>
						 </tbody>	
					</table>
					<div class="hr-line-dashed"></div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
        <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
    <script type="text/javascript">
    var fstatus = '${vo.fstatus}';//状态
    if (fstatus == '未查阅') 
    	{
	        $.ajax({
	      	  url: '/office/congressView/updateStatus.do',
	      	  data:{id:'${vo.id}'},
	            type:"post"
	        })
    	}
    
    </script>
    
    
</body>
</html>