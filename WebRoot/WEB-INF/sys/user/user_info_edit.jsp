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
					<li><strong>基本信息编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="saveUserInfo.do" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label></td>
								<td class="width-35"><input id="no" name="no" class="form-control" type="text" value="${vo.no }" readonly="readonly" /></td>
								<td class="width-15 active"><label class="pull-right">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</label></td>
								<td class="width-35"><input id="name" name="name" class="form-control"   type="text" value="${vo.name }" readonly="readonly" /></td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label></td>
								<td><input id="sex" name="sex" class="form-control " type="text" value="${vo.sex }"  readonly="readonly"/></td>
								<td class=" active"><label class="pull-right">身份证号码:</label></td>
								<td><input id="credentialsNo" name="credentialsNo" class="form-control " type="text" value="${vo.credentialsNo }"  readonly="readonly"/></td>
							</tr>
							<tr>	
								<td class=" active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></td>
								<td><input id="orgName" name="orgName" class="form-control" type="text" value="${vo.orgName}"  readonly="readonly"/></td>
								 	<td class=" active"><label class="pull-right">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历:</label></td>
								<td><input id="education" name="education" class="form-control " type="text" value="${vo.education }" readonly="readonly"/></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">岗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
								<td> 
									<input id="duty" name="duty" class="form-control " type="text" value="${vo.duty }" readonly="readonly"/>
								</td>
								<td class="active"><label class="pull-right">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</label></td>
								<td><input id="techTitle" name="techTitle" class="form-control " type="text" value="${vo.techTitle }" readonly="readonly"/></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
								<td><input id="email" name="email" class="form-control email required" validate="required" type="email" value="${vo.email }" /></td>
								<td class=" active"><label class="pull-right">联系电话:</label></td>
								<td><input id="mobile" name="mobile" class="form-control mobile required" validate="required" type="text" value="${vo.mobile }" /></td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">毕业学校:</label></td>
								<td><input id="graduationSchool" name="graduationSchool" class="form-control " type="text" value="${vo.graduationSchool }" /></td>
								<td class=" active"><label class="pull-right">专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label></td>
								<td><input id="profession" name="profession" class="form-control " type="text" value="${vo.profession }" /></td>
							</tr>
						 
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="submit">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
</html>
