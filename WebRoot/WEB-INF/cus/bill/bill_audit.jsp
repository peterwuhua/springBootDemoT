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
						<li><strong>审批</strong></li>
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
								</td>
								<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
								<td class="width-35"><input id="itemno" name="itemno" readonly="readonly" class="form-control" maxlength="32" type="text" value="${vo.itemno }" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">合同金额:</label></td>
								<td class="width-35">
									 <input type="text" id="contractJe" name="htje" readonly="readonly" class="form-control"   value="${vo.htje}"> 
								</td>
								<td class="width-15 active"><label class="pull-right">发票金额:</label></td>
								<td class="width-35"><input id="fpje" name="fpje"  class="form-control" readonly="readonly" maxlength="32" type="text" value="${vo.fpje }" />
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
									
								</td>
								<td class="width-15 active"><label class="pull-right">信用代码 /<br> 身份证明:</label></td>
								<td class="width-35"><input id="custCode" name="custCode" readonly="readonly"  class="form-control" maxlength="32" type="text" value="${vo.custCode}" /></td>
							 </tr>
							 <tr>   
							    <td class="width-15 active"><label class="pull-right">注册地址:</label></td>
								<td class="width-35"><input id="address" name="address" readonly="readonly"  class="form-control"  maxlength="128"  type="text" value="${vo.address}" /></td>
								
								<td class="width-15 active"><label class="pull-right">注册电话:</label></td>
								<td class="width-35"><input id="telephone" name="telephone" readonly="readonly"  class="form-control"   type="text" value="${vo.telephone}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开户银行:</label></td>
								<td class="width-35"><input id="custBank" name="custBank" readonly="readonly"  class="form-control" maxlength="64" type="text" value="${vo.custBank}" /></td>
								<td class="width-15 active"><label class="pull-right">开户账号:</label></td>
								<td class="width-35"><input id="custAccount" name="custAccount" readonly="readonly"  class="form-control" type="text" value="${vo.custAccount}"/></td>
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
								<td colspan="3"><textarea rows="2" class="form-control" id="fdesc" name="fdesc"  readonly="readonly"  >${vo.fdesc}</textarea></td>
							</tr>
						</tbody>
					</table>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">审批人员:</label></td>
								<td class="width-35">
									<input id="sumUserName" name="sumUserName" readonly="readonly"  class="form-control"  maxlength="128" type="text" value="${vo.sumUserName}" />
								</td>
								<td class="width-15 active"><label class="pull-right">审批日期:</label></td>
								<td class="width-35">
									<input id="sumDate" name="sumDate" readonly="readonly"  class="form-control"  type="text" value="${vo.sumDate}" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">审批意见:</label></td>
								<td colspan="3"><textarea rows="2" class="form-control" id="sumRemark" name="sumRemark" placeholder="审批意见" maxlength="128"></textarea></td>
							</tr>
						 </tbody>	
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('auditSuccess.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 审批通过</button>
							<button class="btn btn-w-m btn-danger" type="button" onclick="formSubmit('auditFailure.do');"><i class="fa fa-check" aria-hidden="true"></i> 审批不通过</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<%@ include file="../../include/js.jsp"%>
	
	
	
	  <script type="text/javascript">
	  
		function formSubmit(url){
			
			$('#thisForm').attr('action',url);
			$('#thisForm').submit()
		}
	  </script>	
</body>
</html>
