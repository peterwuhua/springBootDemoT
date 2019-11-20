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
				<form id="thisForm" method="post" action="update4Record.do" class="form-horizontal">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
							<th class="width-15 active"><label class="pull-right">计划名称:</label></th>
							<td class="width-35">
								${vo.title }
							</td>
							<th class="width-15 active"><label class="pull-right">实施部门:</label></th>
							<td>
								${vo.orgName}
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
							<th class="width-15 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label></th>
							<td class="width-35">
								${vo.type }
							</td>
							<th class="width-15 active"><label class="pull-right">预算经费:</label></th>
							<td class="width-35">
								${vo.price}
							</td>
						</tr>
						<tr>
							<th class="width-15 active"><label class="pull-right">实施日期:</label></th>
							<td class="width-35">
								${vo.udate}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">计划内容:</label></td>
							<td colspan="3">
								${vo.content}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">计&nbsp;&nbsp;划&nbsp;&nbsp;人:</label></td>
							<td>
								${vo.userName}
							</td>
							<th class="width-15 active"><label class="pull-right">计划日期:</label></th>
							<td class="width-35">
								${vo.date}
							</td>
						</tr>
							<tr>
								<td class="active"><label class="pull-right">比对验证结果:</label></td>
								<td colspan="3">
									<textarea name="result" id="result" placeholder="比对验证结果" class="form-control" >${vo.result}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea maxlength="128" name="remark" id="remark" class="form-control" >${vo.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">记&nbsp;&nbsp;录&nbsp;&nbsp;人:</label></td>
								<td>
									<div class="input-group">
										<input name="jlName" value="${vo.jlName}" id="jlName" type="text" class="form-control"  onclick="fnSelectUser()"/>
										<input name="jlId" value="${vo.jlId}" id="jlId" type="hidden" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
								<th class="width-15 active"><label class="pull-right">记录日期:</label></th>
								<td class="width-35">
									<input id="jlDate" class="form-control required dateISO" validate="required" name="jlDate" type="text" value="${vo.jlDate}" />
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
			var userId=$('#jlId').val();
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
				  $('#jlId').val(data.id);
				  $('#jlName').val(data.name);
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
