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
			<input name="fpId" value="${vo.fpId}" type="hidden" />
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a href="javascript:backMainPage();">费用</a></li>
						<li><strong>审批</strong></li>
					</ol>
				</div>
				<div class="ibox-content">
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
							<td class="width-10 active"></td>
								<td class="width-15 active"><label class="pull-right">发票编号:</label></td>
								<td class="width-35"><input id="billno" name="billno" class="form-control" readonly="readonly"  maxlength="128"  type="text" value="${vo.billno }"  /></td>
							<td class="width-15 active"><label class="pull-right">费用状态:</label></td>
								<td class="width-35"><input id="fstatus" name="fstatus" class="form-control" readonly="readonly"  maxlength="128" type="text" value="${vo.fstatus }"  /></td>
							</tr>
							<tr>
							<td class="width-10 active"></td>
								<td class="width-15 active"><label class="pull-right">合同编号:</label></td>
								<td class="width-35">
												    <input type="text" id="contractCode" name="contractCode" readonly="readonly" class="form-control"   value="${vo.contractCode}"> 
												<input type="hidden" id="contractId" name="htId" value="${vo.htId}">
								</td>
								<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
								<td class="width-35"><input id="itemno" name="billVo.itemno" readonly="readonly" class="form-control" maxlength="32" type="text" value="${vo.billVo.itemno }" />
								<input id="projectId" name="pjtId" value="${vo.pjtId}" type="hidden" />
								</td>
							</tr>
							<tr>
											<td class="width-10 active"></td>
								<td class="width-15 active"><label class="pull-right">合同金额:</label></td>
								<td class="width-35">
									 <input type="text" id="contractJe" name="htje" readonly="readonly" class="form-control"   value="${vo.billVo.htje}"> 
								</td>
								<td class="width-15 active"><label class="pull-right">发票金额:</label></td>
								<td class="width-35"><input id="fpje" name="fpje"  readonly="readonly" class="form-control" maxlength="32" type="text" value="${vo.billVo.fpje }" />
								</td>
							</tr>
							
							<tr>
								<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>开<br>票<br>信<br>息</label></td>
								<td class="active"><label class="pull-right">发票类型:</label></td>
									<td class="width-35"><input id="ftype" name="billVo.ftype" readonly="readonly"  class="form-control" maxlength="32" type="text" value="${vo.billVo.ftype }" />
									</td>
								<td class="width-15 active"></td>
								<td class="width-35"></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
								<td class="width-35">
									<input type="text" id="custName" name="customerName" readonly="readonly"  class="form-control"  value="${vo.customerName}"> 
									<input type="hidden" id="custId" name="custId" value="${vo.custId}">
								</td>
								<td class="width-15 active"><label class="pull-right">信用代码 /<br> 身份证明:</label></td>
								<td class="width-35"><input id="custCode" name="billVo.customerVo.custCode" readonly="readonly"  class="form-control" maxlength="32" type="text" value="${vo.billVo.customerVo.custCode }" /></td>
							 </tr>
							 <tr>   
							    <td class="width-15 active"><label class="pull-right">注册地址:</label></td>
								<td class="width-35"><input id="address" name="billVo.customerVo.custAddress" readonly="readonly"  class="form-control"  maxlength="128"  type="text" value="${vo.billVo.customerVo.address}" /></td>
								
								<td class="width-15 active"><label class="pull-right">注册电话:</label></td>
								<td class="width-35"><input id="telephone" name="billVo.customerVo.custTel" readonly="readonly"  class="form-control"   type="text" value="${vo.billVo.customerVo.telephone}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开户银行:</label></td>
								<td class="width-35"><input id="custBank" name="billVo.customerVo.custBank" readonly="readonly"  class="form-control" maxlength="64" type="text" value="${vo.billVo.customerVo.custBank }" /></td>
								<td class="width-15 active"><label class="pull-right">开户账号:</label></td>
								<td class="width-35"><input id="custAccount" name="billVo.customerVo.custAccount" readonly="readonly"  class="form-control" type="text" value="${vo.billVo.customerVo.custAccount }"/></td>
							</tr>
										<tr>
								<td class="width-10 active"></td>
								<td class="width-15 active"><label class="pull-right">支付方式:</label></td>
								<td class="width-35">
									<select id="payWay" name="payWay" class="form-control"></select>
								</td>
								<td class="width-15 active"><label class="pull-right">收款比例:</label></td>
								<td class="width-35">
								<input id="feeratio" name="feeratio"   class="form-control number" maxlength="128" type="text" value="${vo.feeratio}" onchange="countYSPrice();"/>
								</td>
							</tr>
								<tr>
								 <td class="width-10 active"></td>
									<td class="width-15 active"><label class="pull-right">预付款:</label></td>
									<td class="width-35"><input id="yfje" name="yfje" validate="required"  class="form-control required number" maxlength="128" type="text" value="${vo.yfje}" />
									</td>
									<td class="width-15 active"><label class="pull-right">应收款:</label></td>
									<td class="width-35"><input id="receive" name="receive" readonly="readonly"   class="form-control" maxlength="128" type="text" value="${vo.receive}" />
									</td>
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
								<td colspan="3"><textarea rows="2" class="form-control" id="fdesc" name="fdesc" placeholder="备注">${vo.fdesc}</textarea></td>
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
								<td colspan="3"><textarea maxlength="128" rows="2" class="form-control" id="sumRemark" name="sumRemark" placeholder="审批意见"></textarea></td>
								<td><input type="hidden" name="sumUserName" value="${vo.sumUserName}"></td>
								<td><input type="hidden" name="sumDate" value="${vo.sumDate}"></td>
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
	<script>
	 $(document).ready(function(){
			$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})
			payWaySelect();	
		});
	  
	  function payWaySelect()
	  {
			$.ajax({
				url : '${basePath}/cus/normalList/fetchPayWay.do',
				datatype : "json",
				success : function(data) {
					var value = data;
					var optionstring = "<option value=\"\" >-请选择-</option>";
					for (var i = 0; i < value.length; i++) {
						if('${vo.payWay}'== value[i]){
						optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
								+ value[i] + "</option>";
						}else{
							optionstring += "<option value=\"" + value[i] + "\" >"
								+ value[i] + "</option>";
						}
					}
					$("#payWay").html(optionstring);
				}
			});
	  }
	  
	  
		function formSubmit(url){
			
			var yfje = parseFloat($('#yfje').val());
			var receive = parseFloat($('#receive').val());
			if (yfje!=receive)
			{
				layer.msg('预付金额不等于应收金额!', {icon: 0,time: 3000});
				   return;
			}
			
			$('#thisForm').attr('action',url);
			$('#thisForm').submit()
		}
	
		function countYSPrice(){
			var contractJe=parseFloat($('#contractJe').val());//合同金额
			var feeratio=parseFloat($('#feeratio').val());//收款比例
			$('#receive').val(contractJe*feeratio);
		}
		
		
		
	</script>
</body>
</html>
