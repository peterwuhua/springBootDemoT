<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <form method="post"  class="form-horizontal" >
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<th class="width-15 active"><label class="pull-right">仪器编号:</label></th>
										<td class="width-35">${vo.no}</td>
										<td class="active"><label class="pull-right">仪器名称:</label></td>
										<td>${vo.name}</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">规格/型号:</label></td>
										<td>${vo.spec}</td>
										<td class="active"><label class="pull-right">不确定度 :</label></td>
										<td>${vo.accuracy}</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">测量范围:</label></td>
										<td>${vo.measureRange}</td>
										<td class="active"><label class="pull-right">是否强检:</label></td>
										<td>${vo.qj}</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">制造厂商:</label></td>
										<td>${vo.producer}</td>
										<td class="active"><label class="pull-right">出厂编号:</label></td>
										<td>${vo.code}</td>
									</tr>
									<tr>	
										<td class="active"><label class="pull-right">出厂日期 :</label></td>
										<td>${vo.productDate}</td>
										<td class="active"><label class="pull-right">厂家电话:</label></td>
										<td>${vo.producerPhone}</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">购置日期 :</label></td>
										<td>${vo.purTime}</td>
										<td class="active"><label class="pull-right">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格:</label></td>
										<td>
											${vo.price}
										</td>
									</tr>
									<tr>
										
										<td class="active"><label class="pull-right">放置地点:</label></td>
										<td>
											${vo.address}
										</td>
										<td class="active"><label class="pull-right">状态:</label></td>
										<td>
											${vo.status}
										</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">仪器保管人 :</label></td>
										<td>${vo.deptName}<!-- onclick="selectLabUser();" --></td>
										
										<td class="active"><label class="pull-right">仪器保管人 :</label></td>
										<td>${vo.keeper}<!-- onclick="selectLabUser();" --></td>
									</tr>
									 <tr>
										<td class="active"><label class="pull-right">附件上传:</label></td>
										<td colspan="3"><c:forEach items="${vo.fileList}" var="e" varStatus="v">
												<div style="float: left; margin-right: 10px;">
													<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a> <a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
												</div>
											</c:forEach></td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">仪器图片:</label></td>
										<td colspan="3">
											<c:if test="${fn:length(vo.logo)>0}">
												<img alt="logo" src="${basePath}${vo.logo}" style="width: 100px;width: 100px;">
											</c:if>
										</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">备注 :</label></td>
										<td colspan="3">${vo.remark}</td>
									</tr>
								</tbody>
							</table>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
</body>
</html>
