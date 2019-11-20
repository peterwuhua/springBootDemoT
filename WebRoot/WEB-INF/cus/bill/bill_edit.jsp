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
						<li><strong>编辑</strong></li>
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
									<div class="input-group">
										<input type="text" id="contractCode" name="contractCode" class="form-control required" readonly="readonly" placeholder="请选择" value="${vo.contractCode}"  onclick="chooseItem()"> 
										<input type="hidden" id="contractId" name="htId" value="${vo.htId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="chooseItem()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
								<td class="width-35">
									<input id="itemno" name="itemno" readonly="readonly" class="form-control" maxlength="32" type="text" value="${vo.itemno }" />
									<input id="projectId" name="pjtId" value="${vo.pjtId}" type="hidden" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">合同金额:</label></td>
								<td class="width-35">
									 <input type="text" id="contractJe" name="htje" readonly="readonly" class="form-control"   value="${vo.htje}"> 
								</td>
								<td class="width-15 active"><label class="pull-right">开票金额:</label></td>
								<td class="width-35"><input id="fpje" name="fpje"  class="form-control number" maxlength="32" type="text" value="${vo.fpje }" /></td>
							</tr>
							<tr>
								<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>开<br>票<br>信<br>息</label></td>
								<td class="active"><label class="pull-right">发票类型:</label></td>
								<td>
									<div class="radio i-checks">
	                                      <label class="checkbox-inline i-checks ">
	                                          <div class="iradio_square-green">
	                                          <input type="radio" value="增值税普通发票" name="ftype" <c:if test="${vo.ftype=='增值税普通发票' ||empty vo.ftype}">checked</c:if> >
	                                          </div>增值税普通发票
	                                      </label>
	                                      <label class="checkbox-inline i-checks ">
	                                          <div class="iradio_square-green">
	                                          <input type="radio" value="增值税专用发票" name="ftype" <c:if test="${vo.ftype=='增值税专用发票' }">checked</c:if>>
	                                          </div>增值税专用发票
	                                      </label>
	                                 </div>
								</td>
								
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
								<td class="width-35">
									<input type="text" id="custName" name="custName"   class="form-control required" validate="required"  value="${vo.custName}"> 
									<input type="hidden" id="custId" name="custId" >
									<input type="hidden" id="customerId" name="customerId" value="${vo.customerId}">
								</td>
								<td class="width-15 active"><label class="pull-right">信用代码 /<br> 身份证明:</label></td>
								<td class="width-35"><input id="custCode" name="custCode"   class="form-control required" validate="required" maxlength="32" type="text" value="${vo.custCode}" /></td>
							 </tr>
							 <tr>   
							    <td class="width-15 active"><label class="pull-right">注册地址:</label></td>
								<td class="width-35"><input id="address" name="address"  class="form-control"  maxlength="128"  type="text" value="${vo.address}" /></td>
								
								<td class="width-15 active"><label class="pull-right">注册电话:</label></td>
								<td class="width-35"><input id="telephone" name="telephone"   class="form-control"   type="text" value="${vo.telephone}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开户银行:</label></td>
								<td class="width-35"><input id="custBank" name="custBank"   class="form-control" maxlength="64" type="text" value="${vo.custBank}" /></td>
								<td class="width-15 active"><label class="pull-right">开户账号:</label></td>
								<td class="width-35"><input id="custAccount" name="custAccount"   class="form-control" type="text" value="${vo.custAccount}"/></td>
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
								<td colspan="3"><textarea maxlength="128" rows="2" class="form-control" id="fdesc" name="fdesc" placeholder="备注">${vo.fdesc}</textarea></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('addOrUpdate.do');"><i class="fa fa-check" aria-hidden="true"></i> 提交</button>
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
	
	
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
	});
	
	
	function chooseItem(){
		var id=$('#contractId').val();
		var index = layer.open({
			title:'合同信息',	
			type: 2,
			area: ['800px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/cus/contract/select.do?id='+id,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var selectData = iframeWin.fnSelect(); //查询的数据
				  $('#contractCode').val(selectData.code);
				  $('#contractId').val(selectData.id);
				  $.ajax({
						url:"/cus/bill/queryInfosByHtId.do",
						data:{"contractCode":selectData.code},		
						dataType:"json",
						type:"post",
						success:function(data){
							$("#custId").val(data.projectVo.custVo.customerVo.id);
							$("#customerId").val(data.projectVo.custVo.customerVo.id);
							$("#projectId").val(data.projectVo.id);
							$("#itemno").val(data.projectVo.no);
							$("#custName").val(data.projectVo.custVo.customerVo.name);
							$("#custCode").val(data.projectVo.custVo.customerVo.custCode);
							$("#address").val(data.projectVo.custVo.customerVo.custAddress);
							$("#telephone").val(data.projectVo.custVo.customerVo.custTel);
							$("#custBank").val(data.projectVo.custVo.customerVo.custBank);
							$("#custAccount").val(data.projectVo.custVo.customerVo.custAccount);
						}
					})
			}
		});
	}
		function formSubmit(url){
			
				if ($("#contractId").val() == null ||$("#contractId").val() =='')
				{
				layer.msg('请选择合同!', {icon: 0,time: 3000});
				   return;
				}
				$("form").attr('action',url);
				$("form").submit();
		}
		
		$('input[name="ftype"]').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
			changedRequired();//设置必填
		});
		function changedRequired(){
			var radioVal=$('input[name="ftype"]:checked').val();
			if(radioVal=='增值税专用发票'){
				$('#address').attr('validate','required');
				$('#address').addClass('required');
				$('#telephone').attr('validate','required');
				$('#telephone').addClass('required');
				$('#custBank').attr('validate','required');
				$('#custBank').addClass('required');
				$('#custAccount').attr('validate','required');
				$('#custAccount').addClass('required');
			}else{
				$('#address').removeAttr('validate');
				$('#address').removeClass('required');
				$('#telephone').removeAttr('validate','required');
				$('#telephone').removeClass('required');
				$('#custBank').removeAttr('validate','required');
				$('#custBank').removeClass('required');
				$('#custAccount').removeAttr('validate','required');
				$('#custAccount').removeClass('required');
			}
		};
		
		
	</script>
</body>
</html>
