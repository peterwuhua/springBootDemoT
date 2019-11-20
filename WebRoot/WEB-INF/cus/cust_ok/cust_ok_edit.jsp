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
								<li><a href="javascript:backMainPage();">联系人</a></li>
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
										<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
										<td class="width-35">
											<div class="input-group">
		                                        <div class="input-group-btn">
		                                            <button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectCustomer()">选择</button>
		                                        </div>
		                                        <input type="text" readonly="readonly" id="cusName" class="form-control" value="${vo.customerVo.name}">
		                                        <input type="hidden" id="cusId" name="customerVo.id" value="${vo.customerVo.id}">
		                                    </div>
	                                    </td>
										<td class="width-15 active"><label class="pull-right">调&nbsp;&nbsp;查&nbsp;人:</label></td>
										<td class="width-35"><input id="custUser" class="form-control required" validate="required" name="custUser" type="text" value="${vo.custUser}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">联系电话:</label></td>
										<td class="width-35">
											<input id="custMobile" class="form-control" name="custMobile" type="text" value="${vo.custMobile}"/>
										</td>
										<td class="width-15 active"><label class="pull-right">调查日期:</label></td>
										<td class="width-35">
											<div class="input-group date">
												<input id="cdate" name="cdate"  class="form-control dateISO" type="text" value="${vo.cdate }" />
												<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										</td>
										
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">评&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分:</label></td>
										<td class="width-35"><input id="pf" class="form-control digits" name="pf" type="text" value="${vo.pf}"/></td>
										<td class="width-15 active"><label class="pull-right">意见建议:</label></td>
										<td class="width-35">
											<input id="resean" class="form-control" name="resean" type="text" value="${vo.resean}"/>
										</td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
										<td class="width-35" colspan="3">
											<textarea rows="2" class="form-control" name="remark" maxlength="128">${vo.remark}</textarea>
										</td>
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
