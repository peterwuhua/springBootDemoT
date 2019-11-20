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
					<input type="hidden" name="customerVo.id" id="customerId" value="${vo.customerVo.id}" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">客户名称:</label></th>
								<td class="width-35">${vo.customerVo.name}</td>
								<th class="width-15 active"><label class="pull-right">拜访人:</label></th>
								<td class="width-35">
									<input id="user" class="form-control required" validate="required" name="user" type="text" value="${vo.user }" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">回&nbsp;&nbsp;访&nbsp;人:</label></td>
								<td>
									<input name="userName" value="${vo.userName}" id="userName" type="text" class="form-control" readonly="readonly"/>
									<input name="userId" value="${vo.userId}" id="userId" type="hidden" />
								</td>
								<th class="width-15 active"><label class="pull-right">回访日期:</label></th>
								<td class="width-35">
									<input id="vdate" class="form-control required dateISO" validate="required" name="vdate" type="text" value="${vo.vdate}" />
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
								<td class="active"><label class="pull-right">回访详情 :</label></td>
								<td colspan="3">
									<textarea name="result" id="result" class="form-control" >${vo.result}</textarea>
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
