<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
.panel-heading{
	padding: 0px;
}
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<form method="post" action="" name="form" class="form-horizontal">
			<input name="id" value="${vo.id}" type="hidden" />
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<div class="panel-heading">
						<div class="panel-options">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#cus1"  data-toggle="tab">客户信息 </a></li>
								<li><a  href="#" onclick="location.href='${basePath}cus/saleContact/gridPage4Cus2.do?customerId=${vo.id}&salerId=${vo.salerId}'"  data-toggle="tab">客户跟踪记录</a></li>
								<li><a  href="#" onclick="location.href='${basePath}bus/task/gridPage4MySelect.do?customerId=${vo.id}'"  data-toggle="tab">历史任务进度</a></li>
							</ul>
						</div>
					</div>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
								<td class="width-35">${vo.name }</td>
								<td class="width-15 active"><label class="pull-right">法&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人:</label></td>
								<td class="width-35">${vo.custFaRen }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</label></td>
								<td class="width-35">${vo.address}</td>
								<td class="width-15 active"><label class="pull-right">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话:</label></td>
								<td class="width-35">${vo.telephone}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">社会信用代码:</label></td>
								<td>${vo.custCode }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开户银行:</label></td>
								<td class="width-35">${vo.custBank}</td>
								<td class="width-15 active"><label class="pull-right">开户账号:</label></td>
								<td class="width-35">${vo.custAccount }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;&nbsp;人:</label></td>
								<td class="width-35">${vo.person}</td>
								<td class="width-15 active"><label class="pull-right">联系电话:</label></td>
								<td class="width-35">${vo.phone}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
								<td class="width-35">${vo.email}</td>
								<td class="width-15 active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
								<td class="width-35">${vo.zip}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真:</label></td>
								<td class="width-35">${vo.fax }</td>
								<td class="width-15 active"><label class="pull-right">成立日期:</label></td>
								<td class="width-35">${vo.buildDate }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">所属区域:</label></td>
								<td class="width-35">
									${vo.areaPath}
								</td>
								<td class="width-15 active"><label class="pull-right">行&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label></td>
								<td class="width-35">
									${vo.industry}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">客户属性:</label></td>
								<td class="width-35">
									${vo.cusType}
								</td>
								<c:if test="${not empty vo.cusCates }">
								<td class="width-15 active"><label class="pull-right">客户类型:</label></td>
								<td class="width-35">
									${vo.cusCates}
								</td>
								</c:if>
								<c:if test="${empty vo.cusCates }">
								<td class="width-15 active"></td>
								<td class="width-35">
								</td>
								</c:if>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">销售人员:</label></td>
										<td class="width-35" >
											${vo.saler}
										</td>
								<td class="active"><label class="pull-right">提交日期:</label></td>
								<td>
								${vo.supportDate}
								</td>
							</tr>
							<tr>
							<td class="active"><label class="pull-right">跟进状态:</label></td>
							<td>
								${vo.gjStatus}
							</td>
							<td class="width-15 active"></td>
								<td class="width-35"></td>	
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">${vo.remark}</td>
							</tr>
						</tbody>
					</table>
					<c:if test="${fn:length(contactsList)>0}">
						<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						  <legend>联系人</legend>
						</fieldset>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<thead>
								<tr>
									<th>
					                   	 联系人
									</th>
									<th>
					             	      部门
									</th>
									<th>
					                 	  职务
									</th>
									<th>
					                 	  出生年月
									</th>
									<th>
					                  	联系方式
									</th>
									<th>
					                 	   邮箱
									</th>
								</tr>
							</thead>
							<c:forEach items="${contactsList}" var="e" varStatus="v">
								<tr>
									<td>
					                    ${e.name}
									</td>
									<td>
					                    ${e.depart}
									</td>
									<td>
					                    ${e.duty}
									</td>
									<td>
					                    ${e.birthDate}
									</td>
									<td>
					                    ${e.phone}
									</td>
									<td>
					                    ${e.email}
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</div>
			</div>
		</form>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
</html>
