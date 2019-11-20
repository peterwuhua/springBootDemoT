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
                    <div class="ibox-content">
                        <form method="post" action="${fn:length(vo.id)>0? 'updateTab.do':'addTab.do'}" class="form-horizontal">
		                    <c:if test="${fn:length(vo.id)>0}">
		                    	<input name="id" value="${vo.id}" type="hidden" />
		                    </c:if>
		                    <input name="customerVo.id" value="${vo.customerVo.id}" type="hidden" />
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">单位名称:</label></td>
										<td class="width-35"><input id="name" placeholder="请输入名称"  class="form-control required" maxlength="64" name="name" type="text" value="${vo.name}"/></td>
										<td class="width-15 active"><label class="pull-right">地址:</label></td>
										<td class="width-35"><input id="address" placeholder="请输入地址"  class="form-control" maxlength="128" name="address" type="text" value="${vo.address}"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">开户银行:</label></td>
										<td class="width-35"><input id="bank" placeholder="请输入开户银行"  class="form-control" name="bank" type="text" value="${vo.bank}"/></td>
										<td class="width-15 active"><label class="pull-right">账号:</label></td>
										<td class="width-35"><input id="accountNum" placeholder="请输入账号"  class="form-control" name="accountNum" type="text" value="${vo.accountNum}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">行号:</label></td>
										<td class="width-35"><input id="bankNum" placeholder="请输入行号"  class="form-control" maxlength="32" name="bankNum" type="text" value="${vo.bankNum}"/></td>
										<td class="width-15 active"><label class="pull-right">同城结算号:</label></td>
										<td class="width-35"><input id="settlementNum" placeholder="请输入同城结算号"  class="form-control" maxlength="32" name="settlementNum" type="text" value="${vo.settlementNum}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">税务登记号:</label></td>
										<td class="width-35"><input id="registerNum" placeholder="请输入税务登记号"  class="form-control" maxlength="32" name="registerNum" type="text" value="${vo.registerNum}"/></td>
										<td class="width-15 active"><label class="pull-right">邮编:</label></td>
										<td class="width-35"><input id="zipCode" placeholder="请输入邮编"  class="form-control" maxlength="32" name="zipCode" type="text" value="${vo.zipCode}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">电话:</label></td>
										<td class="width-35"><input id="phone" placeholder="请输入电话"  class="form-control" name="phone" type="text" value="${vo.phone}"/></td>
										<td class="width-15 active"><label class="pull-right">传真:</label></td>
										<td class="width-35"><input id="fax" placeholder="请输入传真"  class="form-control" maxlength="32" name="fax" type="text" value="${vo.fax}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">联系人:</label></td>
										<td class="width-35"><input id="contacts" placeholder="请输入联系人"  class="form-control" name="contacts" type="text" value="${vo.contacts}"/></td>
										<td class="width-15 active"><label class="pull-right">备注:</label></td>
										<td class="width-35"><input id="remark" placeholder="请输入备注"  class="form-control" maxlength="128" name="remark" type="text" value="${vo.remark}"/></td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
</body>
<script>
	var index = parent.layer.getFrameIndex(window.name); 
	function saveTable(_target){
		$("form").attr('target',_target);
		$("form").submit();
		parent.$("#customerId").val('${vo.customerVo.id}');
		//parent.layer.close(index);
	}
</script>
</html>
