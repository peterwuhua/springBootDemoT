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
						<ol class="breadcrumb">
							<li><a href="gridPage.do">邮件</a></li>
							<li><strong>详情</strong></li>
						</ol>
                    </div>
                    <div class="ibox-content">
                        <form method="post" action="add.do" class="form-horizontal">
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="active"><label class="pull-right">收件人:</label></td>
										<td class="width-85">${vo.receiver}</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">主题:</label></td>
										<td colspan="3">${vo.subject }</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">内容:</label></td>
										<td colspan="3">${vo.content }</td>
									</tr>
									<tr>
										<td class="active" style="width: 15%"><label class="pull-right">发送时间:</label></td>
										<td>${vo.sendTime }</td>
									</tr>
								</tbody>	
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-12 col-sm-offset-1">
                                    <a class=" btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
</body>
</html>
