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
								<li><a href="javascript:backMainPage();">账户</a></li>
								<li><strong>编辑</strong></li>
							</ol>
						</h5>
						<div class="ibox-tools">
							<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
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
										<th class="width-15 active"><label class="pull-right">客户名称:</label></th>
										<td class="width-35">
										<div class="input-group">
	                                        <div class="input-group-btn">
	                                            <button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectCustomer('${vo.customerVo.id}','${vo.customerVo.name}')">选择</button>
	                                        </div>
	                                        <input type="text" readonly="readonly" id="cusName" class="form-control" placeholder="请选择客户名称" value="${vo.customerVo.name}">
	                                        <input type="hidden" id="cusId" name="customerVo.id" value="${vo.customerVo.id}">
	                                    </div>
	                                    </td>
										<td class="width-15 active"><label class="pull-right">单位名称:</label></td>
										<td class="width-35"><input id="name" placeholder="请输入单位名称"  class="form-control required" maxlength="64" name="name" type="text" value="${vo.name}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">地址:</label></td>
										<td class="width-35"><input id="address" placeholder="请输入地址"  class="form-control" maxlength="128" name="address" type="text" value="${vo.address}"/></td>
										<td class="width-15 active"><label class="pull-right">开户银行:</label></td>
										<td class="width-35"><input id="bank" placeholder="请输入开户银行"  class="form-control" name="bank" type="text" value="${vo.bank}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">账号:</label></td>
										<td class="width-35"><input id="accountNum" placeholder="请输入账号"  class="form-control" name="accountNum" type="text" value="${vo.accountNum}"/></td>
										<td class="width-15 active"><label class="pull-right">行号:</label></td>
										<td class="width-35"><input id="bankNum" placeholder="请输入行号"  class="form-control" maxlength="32" name="bankNum" type="text" value="${vo.bankNum}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">同城结算号:</label></td>
										<td class="width-35"><input id="settlementNum" placeholder="请输入同城结算号"  class="form-control" maxlength="32" name="settlementNum" type="text" value="${vo.settlementNum}"/></td>
										<td class="width-15 active"><label class="pull-right">税务登记号:</label></td>
										<td class="width-35"><input id="registerNum" placeholder="请输入税务登记号"  class="form-control" maxlength="32" name="registerNum" type="text" value="${vo.registerNum}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">邮编:</label></td>
										<td class="width-35"><input id="zipCode" placeholder="请输入邮编"  class="form-control" maxlength="32" name="zipCode" type="text" value="${vo.zipCode}"/></td>
										<td class="width-15 active"><label class="pull-right">电话:</label></td>
										<td class="width-35"><input id="phone" placeholder="请输入电话"  class="form-control" name="phone" type="text" value="${vo.phone}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">传真:</label></td>
										<td class="width-35"><input id="fax" placeholder="请输入传真"  class="form-control" maxlength="32" name="fax" type="text" value="${vo.fax}"/></td>
										<td class="width-15 active"><label class="pull-right">联系人:</label></td>
										<td class="width-35"><input id="contacts" placeholder="请输入联系人"  class="form-control" name="contacts" type="text" value="${vo.contacts}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">备注:</label></td>
										<td class="width-35" colspan="3"><input id="remark" placeholder="请输入备注"  class="form-control" maxlength="128" name="remark" type="text" value="${vo.remark}"/></td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-success" type="button" onclick="formSubmit('save.do');">保存</button>
									<button class="btn btn-primary" type="submit">保存并返回</button>
									<a href="javascript:backMainPage();" class="btn btn-white" type="submit">返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
        <script>
function fnSelectCustomer(pId,pName){
	layer.open({
		title:'客户信息选择',	
		type: 2,
		area: ['800px', '470px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}cus/customer/select.do?id='+pId+'&name='+pName,
		btn: ['确定','取消'],
		btn1: function(index, layero) {
		  var iframeWin = window[layero.find('iframe')[0]['name']];
		  var selectData = iframeWin.fnSelect();
		  $("#cusName").val(selectData.name);
		  $("#cusId").val(selectData.id);
		}
	});
}
</script>
</body>
</html>
