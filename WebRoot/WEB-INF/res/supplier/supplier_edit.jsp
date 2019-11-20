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
					<li><a href="/res/supplier/gridPage.do">供应商</a></li>
					<li><strong> <c:if test="${fn:length(vo.id)>0}">编辑</c:if> <c:if test="${fn:length(vo.id)==0}">新增</c:if>
					</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'update2File.do':'add2File.do'}" class="form-horizontal" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<div class="panel panel-default">
						<div class="panel-heading" style="cursor: pointer" onclick="$(this).next().toggle()">供应商管理</div>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tbody>
								<tr>
									<td class="width-15 active"><label class="pull-right">供应商编号 :</label></td>
									<td class="width-35"><input id="no" placeholder="请输入供应商编号" class="form-control required" validate="required" name="no" type="text" value="${vo.no }" /></td>
									</td>
									<td class="width-15 active"><label class="pull-right">供应商名称:</label></td>
									<td class="width-35"><input type="text" id="name" placeholder="请输入供应商名称" name="name" class="form-control required" validate="required" value="${vo.name }"></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">公司类型 :</label></td>
									<td>
										<select id="companytype" class="form-control" name="companytype" type="text">
										</select>
									</td>
									<td class="active"><label class="pull-right">成立时间:</label></td>
									<td>
										<div class="input-group date">
											<input id="foundingTime" placeholder="请输入成立时间" class="form-control dateISO" vlaue="${vo.foundingTime }" name="foundingTime" type="text" />
											<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
										</div>
									</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">供应产品类型:</label></td>
									<td>
										<select id="productType" class="form-control" name="productType" type="text">
										</select>
									</td>
									<td class="active"><label class="pull-right">关系建立时间:</label></td>
									<td>
										<div class="input-group date">
											<input id="relationBuildTime" class="form-control dateISO" name="relationBuildTime" type="text" value="${vo.relationBuildTime }" />
											<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
										</div>
									</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">联系人:</label></td>
									<td><input id="linkman" placeholder="请输入联系人" class="form-control required" validate="required" name="linkman" type="text" value="${vo.linkman }" /></td>
									<td class="active"><label class="pull-right">联系电话:</label></td>
									<td><input id="linkmanTel" placeholder="请输入联系电话" class="form-control required" validate="required" name="linkmanTel" type="text" value="${vo.linkmanTel }" /></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">邮箱:</label></td>
									<td><input id="email" placeholder="请输入邮箱" class="form-control email" name="email" type="text" value="${vo.email }" /></td>
									<td class="active"><label class="pull-right">传真:</label></td>
									<td><input id="fax" placeholder="请输入传真" class="form-control" name="fax" type="text" value="${vo.fax }" /></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">邮编:</label></td>
									<td><input id="postcode" placeholder="请输入邮编" class="form-control" name="postcode" type="text" value="${vo.postcode }" /></td>
									<td class="active"><label class="pull-right">公司地址:</label></td>
									<td><input id="address" placeholder="请输入公司地址" class="form-control" name="address" type="text" value="${vo.address }" /></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">级别:</label></td>
									<td>
										<select id="division" class="form-control" name="division" type="text">
										</select>
									</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">上传资质</label></td>
									<td>
										<input type="file" name="file" multiple="multiple" class="form-control"/>
									</td>
									<td colspan="2" id="removeFile">
										<c:forEach items="${vo.fileList}" var="e" varStatus="v">
										 	<div style="float: left;margin-right: 10px;">
											 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
											 	<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
										 	</div>
										 </c:forEach>
									</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">备注:</label></td>
									<td colspan="3"><textarea maxlength="128" rows="2" name="remark" class="form-control">${vo.remark }</textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading" style="cursor: pointer" onclick="$(this).next().toggle()">账户信息</div>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tbody>
								<tr>
									<td class="width-15 active"><label class="pull-right">开户行 :</label></td>
									<td class="width-35"><input id="bankName" placeholder="请输入开户行" class="form-control required" validate="required" name="bankName" type="text" value="${vo.bankName }" /></td>
									</td>
									<td class="width-15 active"><label class="pull-right">账户名称:</label></td>
									<td class="width-35"><input type="text" id="accountName" name="accountName" placeholder="请输入账户名称" class="form-control required" validate="required" value="${vo.accountName }"></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">账号:</label></td>
									<td><input id="bankNumber" class="form-control required" validate="required" placeholder="请输入帐号" name="bankNumber" type="text" value="${vo.bankNumber }" /></td>
									<td class="active"></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading" style="cursor: pointer" onclick="$(this).next().toggle()">供应信息</div>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tbody>
								<tr>
									<td class="width-15 active"><label class="pull-right">供应产品详情:</label></td>
									<td colspan="3"><textarea rows="2" placeholder="请输入供应产品详情" name="productDetails"  class="form-control">${vo.productDetails }</textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('save2File.do');">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
							<button class="btn btn-w-m btn-primary"  type="button"  onclick="formSubmitAndBack();">
								<i class="fa fa-check" aria-hidden="true"></i> 保存并返回
							</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
<script type="text/javascript">

	$('input[type="file"]').prettyFile();
	
	function removeFile(id){
		location.href='/res/supplier/removeFile.do?id='+id;
	}
	function companyTypeSelect() {
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=res-companyType',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					if('${vo.companytype}'== value[i]){
					optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
							+ value[i] + "</option>";
					}else{
						optionstring += "<option value=\"" + value[i] + "\" >"
							+ value[i] + "</option>";
					}
				}
				$("#companytype").html(optionstring);
			}
		});
	}

	function proTypeSelect() {
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=res-productType',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					if('${vo.division}'== value[i]){
					optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
							+ value[i] + "</option>";
					}else{
						optionstring += "<option value=\"" + value[i] + "\" >"
							+ value[i] + "</option>";
					}
				}
				$("#productType").html(optionstring);
			}
		});
	}
	function divisionSelect() {
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=res-division',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					if('${vo.productType}'== value[i]){
					optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
							+ value[i] + "</option>";
					}else{
						optionstring += "<option value=\"" + value[i] + "\" >"
							+ value[i] + "</option>";
					}
				}
				$("#division").html(optionstring);
			}
		});
	}
	$(function(){
		companyTypeSelect();
		proTypeSelect();
		divisionSelect();
	});
</script>
</html>