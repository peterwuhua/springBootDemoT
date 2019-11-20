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
                    <div class="ibox-title">
                       	<ol class="breadcrumb">
			                <li>单位</li>
			                <li>
			                    <strong>编辑</strong>
			                </li>
				        </ol>
                    </div>
                    <div class="ibox-content">
                        <form method="post" action="save.do" class="form-horizontal">
                            <input name="id" value="${vo.id}" type="hidden" />
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">名称:</label></td>
										<td class="width-85"><input id="name" placeholder="请输入名称"  class="form-control required" validate="required" maxlength=64 name="name" type="text" value="${vo.name}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">电话:</label></td>
										<td class="width-85"><input id="telephone" placeholder="请输入电话"  class="form-control required" validate="required" maxlength=14 name="telephone" type="text" value="${vo.telephone}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">地址:</label></td>
										<td class="width-85"><input id="address" placeholder="请输入地址"  class="form-control required" validate="required" maxlength=64 name="address" type="text" value="${vo.address}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">邮编:</label></td>
										<td class="width-85"><input id="post" placeholder="请输入邮编"  class="form-control isZipCode" name="post" type="text" value="${vo.post}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">联系人:</label></td>
										<td class="width-85"><input id="linkMan" placeholder="请输入联系人"  class="form-control" name="linkMan" type="text" value="${vo.linkMan}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">传真:</label></td>
										<td class="width-85"><input id="fax" placeholder="请输入传真"  class="form-control" name="fax" type="text" value="${vo.fax}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">网站:</label></td>
										<td class="width-85"><input id="website" placeholder="请输入网站"  class="form-control url" name="website" type="text" value="${vo.website}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">电子邮箱:</label></td>
										<td class="width-85"><input id="email" placeholder="请输入电子邮箱"  class="form-control email" name="email" type="text" value="${vo.email}"/></td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-12 col-sm-offset-1">
                                    <button class="btn btn-w-m btn-primary" type="submit">保存</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
</body>
</html>
