<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

<style type="text/css">
.panel-heading {
	padding: 0px;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">客户名称:</label></th>
								<td colspan="3">
									<div class="input-group" style="width: 100%">
                                        <input type="text" readonly="readonly" id="custName" name="custName" class="form-control" placeholder="请选择客户名称" value="${vo.custName}">
                                        <input type="hidden" id="custId" name="custId" value="${vo.custId}">
                                   		<div class="input-group-btn">
                                            <button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectCustomer()">选择</button>
                                        </div>
                                    </div>
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">回&nbsp;&nbsp;访&nbsp;&nbsp;人:</label></th>
								<td class="width-35">
									<input id="user" class="form-control required" validate="required" name="user" type="text" value="${vo.user }" />
								</td>
								<th class="width-15 active"><label class="pull-right">联系电话:</label></th>
								<td class="width-35">
									<input id="phone" class="form-control required" validate="required" name="phone" type="text" value="${vo.phone }" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">拜&nbsp;&nbsp;访&nbsp;&nbsp;人:</label></td>
								<td>
									<input name="userName" value="${vo.userName}" id="userName" type="text" class="form-control" readonly="readonly"/>
									<input name="userId" value="${vo.userId}" id="userId" type="hidden" />
								</td>
								<th class="width-15 active"><label class="pull-right">拜访日期:</label></th>
								<td class="width-35">
									<input id="date" class="form-control required dateISO" validate="required" name="date" type="text" value="${vo.date}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">回访结果:</label></td>
								<td>
									<div class="radio i-checks">
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="1" name="status" <c:if test="${vo.status=='1'}">checked</c:if>>
                                            </div>继续跟踪
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="2" name="status" <c:if test="${vo.status!='1'}">checked</c:if>>
                                            </div>回访完成
                                        </label>
                                    </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">回访详情:</label></td>
								<td colspan="3">
									<textarea name="content" id="content" class="form-control" >${vo.content}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script type="text/javascript">
	function fnSelectCustomer(){
		var pId=$('#custId').val();
		parent.layer.open({
			title:'客户信息选择',	
			type: 2,
			area: ['800px', '470px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}cus/pubcustomer/select.do?id='+pId,
			btn: ['确定','取消'],
			btn1: function(index, layero) {
			  var iframeWin=layero.find('iframe')[0];
			  var data=iframeWin.contentWindow.fnSelect();
			  $("#custName").val(data.name);
			  $("#custId").val(data.id);
			}
		});
	}
		var index = parent.layer.getFrameIndex(window.name);
		function fnSelect() {
			var t = $("#thisForm").FormValidate();
			if (t) {
				$('#thisForm').ajaxSubmit(function(res) {
					if (res.status == 'success') {
						parent.layer.msg(res.message, {
							icon : 0,
							time : 3000
						})
						parent.jqgridReload();
						parent.layer.close(index);
					} else {
						layer.msg(res.message, {
							icon : 0,
							time : 3000
						});
					}
				});
			}
		}
		$(document).ready(function() {
			$(".i-checks").iCheck({
				checkboxClass : "icheckbox_square-green",
				radioClass : "iradio_square-green",
			});
		});
	</script>
</body>
</html>
