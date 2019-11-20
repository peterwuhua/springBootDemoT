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
								<li><a href="gridPage.do">自定义导出</a></li>
								<li><strong>编辑</strong></li>
							</ol>
                        </h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                    	<%@ include file="../../include/status.jsp"%>
                        <form method="post" action="${fn:length(vo.id)>0? 'update.do':'add.do'}" class="form-horizontal">
                            <c:if test="${fn:length(vo.id)>0}">
                            	<input name="id" value="${vo.id}" type="hidden" />
                            </c:if>
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">模块:</label></td>
										<td class="width-35"><input id="busType" placeholder="模块"  class="form-control required" maxlength=64 name="busType" type="text" value="${vo.busType}"/></td>
										<td class="width-15 active"></td>
										<td class="width-35"></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">名称:</label></td>
										<td><input id="name" placeholder="名称"  class="form-control required" maxlength=64 name="name" type="text" value="${vo.name}"/></td>
										<td class="active"><label class="pull-right">code:</label></td>
										<td><input id="code" name="code" class="form-control required" maxlength=64 placeholder="编码" type="text" value="${vo.code }" /> </td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">内容:</label></td>
										<td colspan="3"><input id="content" name="content"
											class="form-control required" placeholder="内容" type="text" value="${vo.content }" />
										</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">排序:</label></td>
										<td><input id="sort" name="sort"
											class="form-control " placeholder="排序" type="text" value="${vo.sort }" />
										</td>
										<td class=" active"><label class="pull-right">说明:</label></td>
										<td><input id="describtion" name="describtion"
											class="form-control " placeholder="说明" type="text" value="${vo.describtion }" />
										</td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="button" onclick="formSubmit('save.do');">保存</button>
									<button class="btn btn-primary" type="submit">保存并返回</button>
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
