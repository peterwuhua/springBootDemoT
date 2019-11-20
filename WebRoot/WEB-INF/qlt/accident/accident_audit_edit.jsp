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
			<div class="ibox-content" style="padding-bottom: 0px;">
				<form id="thisForm" method="post" action="update4Audit.do" class="form-horizontal">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">事故单位:</label></th>
								<td>
									${vo.custName}
								</td>
								<th class="width-15 active"><label class="pull-right">事故类型:</label></th>
								<td class="width-35">
									${vo.type }
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;&nbsp;人:</label></th>
								<td class="width-35">
									${vo.user }
								</td>
								<th class="width-15 active"><label class="pull-right">联系电话:</label></th>
								<td class="width-35">
									${vo.phone }
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">事故描述:</label></td>
								<td colspan="3">
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">登&nbsp;&nbsp;记&nbsp;&nbsp;人:</label></td>
								<td>
									${vo.userName}
								</td>
								<th class="width-15 active"><label class="pull-right">登记日期:</label></th>
								<td class="width-35">
									${vo.date}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">事故处理:</label></td>
								<td colspan="3">
									<textarea name="result" id="result" placeholder="事故原因分析 以及处理意见" class="form-control" >${vo.result}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea maxlength="128" name="remark" id="remark" class="form-control" >${vo.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">处&nbsp;&nbsp;理&nbsp;&nbsp;人:</label></td>
								<td>
									<div class="input-group">
										<input name="auditName" value="${vo.auditName}" id="auditName" type="text" class="form-control"  onclick="fnSelectUser()"/>
										<input name="auditId" value="${vo.auditId}" id="auditId" type="hidden" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
								<th class="width-15 active"><label class="pull-right">处理日期:</label></th>
								<td class="width-35">
									<input id="auditDate" class="form-control required dateISO" validate="required" name="auditDate" type="text" value="${vo.auditDate}" />
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
		function fnSelectUser(){
			var userId=$('#auditId').val();
			parent.layer.open({
				title:'人员选择',	
				type: 2,
				area: ['800px', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}sys/account/selects.do?ids='+userId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin=layero.find('iframe')[0];
				  var data=iframeWin.contentWindow.fnSelect();
				  $('#auditId').val(data.id);
				  $('#auditName').val(data.name);
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
	</script>
</body>
</html>
