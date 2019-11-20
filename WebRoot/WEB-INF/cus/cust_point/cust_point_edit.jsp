<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
    <%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
						<h5>
							<ol class="breadcrumb">
								<li><a>监测点位</a></li>
								<li><strong>编辑</strong></li>
							</ol>
						</h5>
					</div>
                    <div class="ibox-content">
                       <form method="post" action="${fn:length(vo.id)>0? 'update.do':'add.do'}" class="form-horizontal">
	                    <c:if test="${fn:length(vo.id)>0}">
	                    	<input name="id" value="${vo.id}" type="hidden" />
	                    </c:if>
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
								   <tr>
										<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
										<td class="width-35">
											<div class="input-group">
		                                        <div class="input-group-btn">
		                                            <button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectCustomer()">选择</button>
		                                        </div>
		                                        <input type="text" readonly="readonly" id="cusName" class="form-control" placeholder="请选择客户名称" value="${vo.customerVo.name}">
		                                        <input type="hidden" id="cusId" name="customerVo.id" value="${vo.customerVo.id}">
		                                    </div>
	                                    </td>
										<td class="width-15 active"><label class="pull-right">联系人名称 :</label></td>
										<td class="width-35"><input id="name" placeholder="请输入联系人名称"  class="form-control required" validate="required" name="name" type="text" value="${vo.name}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">部门:</label></td>
										<td class="width-35"><input id="address" placeholder="请输入部门"  class="form-control" name="depart" type="text" value="${vo.depart}"/></td>
										<td class="width-15 active"><label class="pull-right">职务:</label></td>
										<td class="width-35"><input id="phone" placeholder="请输入职务"  class="form-control" name="duty" type="text" value="${vo.duty}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">地址:</label></td>
										<td class="width-35"><input id="address" placeholder="请输入地址"  class="form-control" name="address" type="text" value="${vo.address}"/></td>
										<td class="width-15 active"><label class="pull-right">电话:</label></td>
										<td class="width-35"><input id="phone" placeholder="请输入电话"  class="form-control" name="phone" type="text" value="${vo.phone}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">邮箱（Email）:</label></td>
										<td class="width-35"><input id="email" placeholder="请输入邮箱"  class="form-control email" name="email" type="text" value="${vo.email}"/></td>
										<td class="width-15 active"><label class="pull-right">备注:</label></td>
										<td class="width-35"><input maxlength="128" id="remark" placeholder="备注"  class="form-control" name="remark" type="text" value="${vo.remark}"/></td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-success" type="button" onclick="formSubmit('save.do');">保存</button>
									<button class="btn btn-primary" type="submit">保存并返回</button>
									<a class="btn btn-default" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
    <script>
function fnSelectCustomer(){
	var pId=$('#cusId').val();
	layer.open({
		title:'客户信息选择',	
		type: 2,
		area: ['800px', '470px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}cus/customer/select.do?id='+pId,
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
