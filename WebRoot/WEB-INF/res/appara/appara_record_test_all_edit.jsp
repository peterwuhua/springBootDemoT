<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/common.jsp"%>
<script type="text/javascript" src="${basePath}page/include/res.js"></script>
</head>
<body class="commonbody">
		<%@ include file="../../include/status.jsp"%>
		<div class="body">
		<ul id="tab" class="tab">
			<li>
					<input type="button" onclick="location.href='/res/appara/gridPage.do'" value="仪器列表" hidefocus /> 
				</li>
				<li>
					<input type="button" onclick="location.href='/res/apparaRecord/allPage.do?type=test'" class="current" value="待检定仪器设备" hidefocus /> 
				</li>
				<li>
					<input type="button" onclick="location.href='/res/apparaRecord/allPage.do?type=calibration'" value="待校准仪器设备" hidefocus /> 
				</li>
				<li>
					<input type="button" onclick="location.href='/res/apparaRecord/allPage.do?type=inspect'" value="待核查仪器设备" hidefocus /> 
				</li>
		</ul>
		<div class="Mediabar">
		<div class="bar">
			<c:set var="action" value="#"/>
			<c:choose>
				<c:when test="${isEdit}">
					<c:set var="action" value="updateAll.do" />	
					编辑仪器检定
				</c:when>
				<c:otherwise>
					添加仪器检定
					<c:set var="action" value="add.do" />
				</c:otherwise>
			</c:choose>
		</div>
		</div>
		<form id="validateForm" action="${action}" method="post" enctype="multipart/form-data">
		<input type="hidden" name="appara.id" id="apparaId" class="formText" value="${vo.appara.id}"/>
		<input type="hidden" name="id"  class="formText" value="${vo.id}"/>
		<input type="hidden" name="type" id="type" value="${vo.type}" />
		<input type="hidden" id="cycle" value="${cycle}" />
			<table class="inputTable">
				<tr>
					<th width="10%"> 计量单位</th>
					<td width="35%"><input name="unit" value="${vo.unit }" id="unit" type="text" class="formText"/></td>
					<th width="10%"> 检定日期</th>
					<td width="35%"><input name="date" value="${vo.date}" id="date" type="text" class="formText" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true});" readonly="readonly"/></td>
				</tr>
				<tr>
					<th> 计量器具</th>
					<td><input name="appliances" value="${vo.appliances}" id="appliances" type="text" class="formText"/></td>
					<th> 计量标准不确定度</th>
					<td><input name="uncertainty" type="text" value="${vo.uncertainty}" class="formText" id="uncertainty"/></td>
					<input name="nextDate" value="${vo.nextDate }" id="nextDate" type="hidden" class="formText"/>
				</tr>
				<tr>
					<th> 计量标准量程</th>
					<td><input name="standardRange" value="${vo.standardRange}" id="standardRange" type="text" class="formText"/></td>
					<th>附件</th>
					<td><input name="fileUrl" value="${vo.fileUrl}" id="fileUrl" type="text" class="formText" /></td>
				</tr>
				<tr>
					<th>所依据技术文件</th>
					<td><input value="${vo.basisFile}" name="basisFile" type="text" class="formText"/></td>
					<th> 证书编号</th>
					<td><input name="certificateNo" value="${vo.certificateNo}" id="certificateNo" type="text" class="formText" /></td>
				</tr>
				<tr>
					<th> 检定总结论 </th>
					<td>
						<select name="result" style="width:90%">
							<option value="合格" <c:if test="${'合格' == vo.result}">selected="selected"</c:if>>合格</option>
							<option value="不合格" <c:if test="${'不合格' == vo.result}">selected="selected"</c:if>>不合格</option>
							<option value="其他" <c:if test="${'其他' == vo.result}">selected="selected"</c:if>>其他</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>备注 </th>
						<%-- 新增 字段用检定日期的代替--%>
						<td colspan="3"><textarea maxlength="128" rows="4" cols="60" name="remark" class="formText">${vo.remark}</textarea></td>
				</tr>
			</table>
			<div class="buttonArea">
				<a class="btn pull_left" onclick="submit()"><i class="btnicon icon icon-save"></i>确定</a>
				<a class="btn nor pull_left" onclick="back()"><i class="btnicon icon icon-reply"></i>返回</a>
			</div>
		</form>
	</div>
</body>
</html>