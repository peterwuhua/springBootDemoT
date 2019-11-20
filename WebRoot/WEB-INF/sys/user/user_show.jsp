<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
 <link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}" class="form-horizontal" enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">员工编号:</label></td>
								<td class="width-35">${vo.no }</td>
								<td class="width-15 active"><label class="pull-right">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</label></td>
								<td class="width-35">${vo.name }</td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label></td>
								<td>
									${vo.sex}
								</td>
								<td class=" active"><label class="pull-right">身份证号码:</label></td>
								<td>${vo.credentialsNo }</td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">公司手机号:</label></td>
								<td>${vo.telephone }</td>
								<td class="active"><label class="pull-right">短&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label></td>
								<td>${vo.subTel}</td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">个人手机号:</label></td>
								<td>${vo.mobile }</td>
								<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
								<td>${vo.email }</td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">毕业学校:</label></td>
								<td>${vo.graduationSchool }</td>
								<td class=" active"><label class="pull-right">专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label></td>
								<td>${vo.profession }</td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">学历/学位:</label></td>
								<td>${vo.education }</td>
								<td class="active"><label class="pull-right">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</label></td>
								<td>${vo.techTitle }</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">入职日期:</label></td>
								<td>${vo.workDate }</td>
								<td class="active"><label class="pull-right ">从事本技术领域年限:</label></td>
								<td>${vo.workYear }</td>
							</tr>
							<tr>	
								<th class="width-15 active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></th>
								<td class="width-35">
									${vo.orgName}
								</td>
								<td class="active"><label class="pull-right">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务:</label></td>
								<td>${vo.job }</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">岗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
								<td>
									${vo.duty}
								</td>
								 <td class="active"><label class="pull-right">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label></td>
								<td>${vo.sort }</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td>
									${vo.remark }
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">上传文件:</label></td>
								<td colspan="3" id="removeFile">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
									 	<div style="float: left;margin-right: 10px;">
										 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
									 	</div>
									 </c:forEach>
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
