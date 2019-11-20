<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
    <%@ include file="../../include/status.jsp"%>
 <style type="text/css">
.panel-heading{
	padding: 0px;
}
</style>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
						<h5>
							<ol class="breadcrumb">
								<li><a>受检单位</a></li>
								<li><strong>查看</strong></li>
							</ol>
						</h5>
					</div>
                    <div class="ibox-content">
                       <form method="post"  class="form-horizontal" id="thisForm">
		                    <c:if test="${fn:length(vo.id)>0}">
		                    	<input name="id" value="${vo.id}" type="hidden" />
		                    </c:if>
		                    <!-- <div class="panel-heading">
								<div class="panel-options">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#cus1" data-toggle="tab">单位信息 </a></li>
									</ul>
								</div>
							</div> -->
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">单位名称:</label></td>
										<td class="width-35">${vo.name}</td>
										<td class="width-15 active"><label class="pull-right">单位性质:</label></td>
										<td class="width-35">
												${vo.attribute}
										</td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">所属行业:</label></td>
										<td class="width-35">
												${vo.industry}
										<td class="width-15 active"><label class="pull-right">所在区域:</label></td>
										<td class="width-35">
		                                        ${vo.areaPath}
		                                        <input type="hidden" id="selectId" name="areaId" value="${vo.areaId}">
										</td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">单位地址:</label></td>
										<td class="width-35">${vo.address}</td>
										<td class="width-15 active"><label class="pull-right">联&nbsp;系&nbsp;&nbsp;人:</label></td>
										<td class="width-35">${vo.person}</td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">联系电话:</label></td>
										<td class="width-35">${vo.phone}</td>
										<td class="width-15 active"><label class="pull-right">电子邮箱:</label></td>
										<td class="width-35">${vo.email}</td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">主要产品:</label></td>
										<td class="width-35" colspan="3">
											${vo.product}
										</td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">备注信息:</label></td>
										<td class="width-35" colspan="3">
											${vo.remark}
										</td>
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
