<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend {
	border-bottom: 0px;
	width: 80px;
	margin-bottom: 0px;
	font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <form id="thisForm" method="post" class="form-horizontal">
		                    <input name="id" value="${vo.id}" type="hidden" />
		                    <div align="center" style="font-size: 18px;font-weight: bold;">
		                    	${vo.year}年${vo.month}月份内部质控计划表
		                    </div>
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">制定人:</label></td>
										<td class="width-35">
											${vo.acceptName}
	                                    </td>
										<td class="width-15 active"><label class="pull-right">制定时间:</label></td>
										<td class="width-35">
											${vo.acceptDate }
										</td>
	                                </tr>
								</tbody>
							</table>
							<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
							  <legend>计划内容</legend>
							</fieldset>
							 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								 <thead>
								 	<tr>
								 		<th style="text-align: center;width: 50px;">序号</th>
								 		<th style="text-align: center;width: 100px;">考核日期</th>
								 		<th style="text-align: center;width: 150px;">考核项目</th>
								 		<th style="text-align: center;width: 120px;">考核方式</th>
								 		<th style="text-align: center;width: 100px;">被考核人员</th>
								 		<th style="text-align: center;width: 150px;">检测仪器</th>
								 		<th style="text-align: center;width: 200px;">标准物质</th>
								 		<th>备注</th>
								 	</tr>
								 </thead>
								<tbody id="detail_tb">
									<c:forEach items="${vo.sampList}" var="e" varStatus="s">
										<tr index="${s.index}">
									 		<td style="text-align: center;">
									 			${s.index+1}
									 		</td>
									 		<td>
									 			${e.assDate}
									 		</td>
									 		<td>
									 			${e.itemNames}
									 		</td>
									 		<td>
									 			${e.type}
									 		</td>
									 		<td>
									 			${e.userNames}
									 		</td>
									 		<td>
									 			${e.appNames}
									 		</td>
									 		<td>
									 			${e.standNames}
									 		</td>
									 		<td>
									 			${e.remark}
									 		</td>
									 	</tr>
									</c:forEach>
								</tbody>
							</table>
							<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
							  <legend>实施记录</legend>
							</fieldset>
							<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<thead>
									<tr>
										<td style="width: 100px;text-align: center;">
											样品编号
										</td>
										<td style="text-align: center;">
											检测项目
										</td>
										<td style="text-align: center;">
											检测方法
										</td>
										 <td style="text-align: center;">
											检测人员
										</td>
										<td style="text-align: center;">
											检测结果
										</td>
										<td style="text-align: center;">
											给定标准值
										</td>
										<td style="text-align: center;">
											测定结果<br>相对差值
										</td>
										 <td style="text-align: center;">
											回收率
										</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${vo.itemList}" var="v" varStatus="vs">
										<tr>
											<td style="text-align: center;">
												${v.samplingVo.sampCode}
											</td>
											<td>
												${v.itemName}
											</td>
											<td>
												${v.standCode}&nbsp;&nbsp;${v.methodName}
											</td>
											<td>
												${v.testMan}
											</td>
	                                 		<td>
                              					${v.value}
                               				</td>
	                                 		<td>
												${v.limited}
											</td>
											 <td>
												${v.limitLine}
											</td>
											<td>
												${v.hsl}
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">考核结果评价:</label></td>
										<td class="width-85">
											${vo.result}
										</td>
									</tr>
							</table>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%> 
</body>
</html>
