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
                    <div class="ibox-title">
                        <h5>
							<ol class="breadcrumb">
								<li><a href="gridPage.do">短信</a></li>
								<li><strong>详情</strong></li>
							</ol>
						</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form method="post" action="add.do" class="form-horizontal">
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="active"><label class="pull-right">模块:</label></td>
										<td>${vo.busType}
										</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">描述:</label></td>
										<td>${vo.busInfo}
										</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">收件人:</label></td>
										<td colspan="3">${vo.receiver}</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">内容:</label></td>
										<td colspan="3">${vo.content }</td>
									</tr>
									<tr>
									<td class="active" style="width: 15%"><label class="pull-right">发送时间:</label></td>
										<td >${vo.sendTime }</td>
									</tr>
									
									<tr>
										<td class="active"><label class="pull-right">备注:</label></td>
										<td colspan="3">${vo.remark }</td>
									</tr>
							</tbody>	
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <a href="edit.do?id=${vo.id}" class="btn btn-primary">再次发送</a>
                                    <a class="btn btn-default" href="gridPage.do" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
</body>
</html>
