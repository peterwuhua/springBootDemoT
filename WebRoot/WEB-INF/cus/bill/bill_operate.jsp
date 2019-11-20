<%@ page contentType="text/html;charset=UTF-8"%>
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
		<form method="post" action="" name="form" class="form-horizontal" id="thisForm">
			<c:if test="${fn:length(vo.id)>0}">
				<input name="id" value="${vo.id}" type="hidden" />
			</c:if>
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a href="javascript:backMainPage();">发票</a></li>
						<li><strong>操作</strong></li>
					</ol>
				</div>
				<div class="ibox-content">
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active" rowspan="3" style="text-align: center;width: 50px;"><label>合<br>同<br>信<br>息</label></td>
								<td class="width-15 active"><label class="pull-right">发票编号:</label></td>
								<td class="width-35"><input id="billno" name="billno" class="form-control" readonly="readonly"  maxlength="128" placeholder="发票编号" type="text" value="${vo.billno }"  /></td>
								<td class="width-15 active"></td>
								<td class="width-35"></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">合同编号:</label></td>
								<td class="width-35">
												  <input type="text" id="contractCode" name="contractCode" readonly="readonly" class="form-control"   value="${vo.contractCode}"> 
												<input type="hidden" id="contractId" name="htId" value="${vo.htId}">
								</td>
								<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
								<td class="width-35"><input id="itemno" name="itemno" readonly="readonly" class="form-control" maxlength="32" type="text" value="${vo.itemno }" />
								<input id="projectId" name="pjtId" value="${vo.pjtId}" type="hidden" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">合同金额:</label></td>
								<td class="width-35">
									 <input type="text" id="contractJe" name="htje" readonly="readonly" class="form-control"   value="${vo.htje}"> 
								</td>
								<td class="width-15 active"><label class="pull-right">开票金额:</label></td>
								<td class="width-35"><input id="fpje" name="fpje"  class="form-control number" readonly="readonly" maxlength="32" type="text" value="${vo.fpje }" />
								</td>
							</tr>
							<tr>
								<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>开<br>票<br>信<br>息</label></td>
								<td class="active"><label class="pull-right">发票类型:</label></td>
								<td>
								<input type="text" id="ftype" name="ftype" readonly="readonly" class="form-control"   value="${vo.ftype}">
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
								<td class="width-35">
									<input type="text" id="custName" name="custName" readonly="readonly"  class="form-control"  value="${vo.custName}"> 
									<input type="hidden" id="custId" name="custId" value="${vo.customerVo.id}">
								</td>
								<td class="width-15 active"><label class="pull-right">信用代码 /<br> 身份证明:</label></td>
								<td class="width-35"><input id="custCode" name="custCode"   class="form-control" readonly="readonly" maxlength="32" type="text" value="${vo.custCode}" /></td>
							 </tr>
							 <tr>   
							    <td class="width-15 active"><label class="pull-right">注册地址:</label></td>
								<td class="width-35"><input id="address" name="address"  class="form-control" readonly="readonly"  maxlength="128"  type="text" value="${vo.address}" /></td>
								
								<td class="width-15 active"><label class="pull-right">注册电话:</label></td>
								<td class="width-35"><input id="telephone" name="telephone"   class="form-control"  readonly="readonly" type="text" value="${vo.telephone}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开户银行:</label></td>
								<td class="width-35"><input id="custBank" name="custBank"   class="form-control" readonly="readonly" maxlength="64" type="text" value="${vo.custBank }" /></td>
								<td class="width-15 active"><label class="pull-right">开户账号:</label></td>
								<td class="width-35"><input id="custAccount" name="custAccount"   class="form-control" readonly="readonly" type="text" value="${vo.custAccount}"/></td>
							</tr>
							 <tr>  
							 <td class="width-10 active"></td> 
							    <td class="width-15 active"><label class="pull-right">申&nbsp;&nbsp;请&nbsp;&nbsp;人:</label></td>
								<td class="width-35"><input id="person" name="person" readonly="readonly"  class="form-control"  maxlength="128" type="text" value="${vo.person}" /></td>
								 <input type="hidden" id="personId" name="personId" value="${vo.personId}">
								<td class="width-15 active"><label class="pull-right">申请日期:</label></td>
								<td class="width-35"><input id="supportDate" name="supportDate" readonly="readonly"  class="form-control"  type="text" value="${vo.supportDate}" /></td>
							</tr>
							<tr>
							<td class="width-10 active"></td> 
								<td class="width-10 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3"><textarea rows="2" class="form-control" readonly="readonly" id="fdesc" name="fdesc" >${vo.fdesc}</textarea></td>
							</tr>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">审批人员:</label></td>
								<td class="width-35">${vo.sumUserName} </td>
								<td class="width-15 active"><label class="pull-right">审批日期:</label></td>
								<td class="width-35">${vo.sumDate}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">审批意见:</label></td>
								<td colspan="3"><textarea rows="2" class="form-control" readonly="readonly" id="sumRemark" name="sumRemark" maxlength="128">${vo.sumRemark}</textarea></td>
							</tr>
						 </tbody>	
					</table>
					
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('fpykj.do');"><i class="fa fa-check" aria-hidden="true"></i> 已开据</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script src="${basePath}h/js/plugins/suggest/bootstrap-suggest.min.js"></script>
	<script>
		
		function formSubmit(url){
				$("form").attr('action',url);
				$("form").submit();
		}
		

		
		
	</script>
</body>
</html>
