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
								<th class="width-15 active"><label class="pull-right">被查部门:</label></th>
								<td>
									<div class="input-group" style="width: 100%">
                                        <input type="text" readonly="readonly" id="orgName" name="orgName" class="form-control required" validate="required" value="${vo.orgName}" onclick="fnSelectOrg()">
                                        <input type="hidden" id="orgId" name="orgId" value="${vo.orgId}">
                                   		<div class="input-group-btn">
                                            <button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectOrg()">选择</button>
                                        </div>
                                    </div>
								</td>
								<th class="width-15 active"><label class="pull-right">抽查日期:</label></th>
								<td class="width-35">
									<input id="date" class="form-control required dateISO" validate="required" name="date" type="text" value="${vo.date}" />
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></th>
								<td class="width-35">
									<input id="user" class="form-control required" validate="required" name="user" type="text" value="${vo.user }" />
								</td>	
								<th class="width-15 active"><label class="pull-right">联系电话:</label></th>
								<td class="width-35">
									<input id="phone" class="form-control required" validate="required" name="phone" type="text" value="${vo.phone }" />
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">执行标准:</label></th>
								<td class="width-35">
									<input id="stand" class="form-control" name="stand" type="text" value="${vo.stand}" />
								</td>
								<th class="width-15 active"><label class="pull-right">使用仪器:</label></th>
								<td class="width-35">
									<input id="appName" class="form-control" name="appName" type="text" value="${vo.appName}" />
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">抽&nbsp;&nbsp;查&nbsp;人:</label></th>
								<td class="width-35">
									<div class="input-group">
										<input name="userName" value="${vo.userName}" id="userName" type="text" class="form-control"  onclick="fnSelectUser()"/>
										<input name="userId" value="${vo.userId}" id="userId" type="hidden" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
								<th class="width-15 active"><label class="pull-right">监&nbsp;&nbsp;督&nbsp;人:</label></th>
								<td class="width-35">
									<div class="input-group">
										<input name="jdName" value="${vo.jdName}" id="jdName" type="text" class="form-control"  onclick="fnSelectJd()"/>
										<input name="jdId" value="${vo.jdId}" id="jdId" type="hidden" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">抽查内容:</label></td>
								<td colspan="3">
									<textarea name="content" id="content" class="form-control" >${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">抽查结果:</label></td>
								<td colspan="3">
									<textarea name="result" id="result" class="form-control" >${vo.result}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">综合评价:</label></td>
								<td colspan="3">
									<textarea name="pj" id="pj" class="form-control" >${vo.pj}</textarea>
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
	function fnSelectOrg(){
		var orgId=$('#orgId').val();
		parent.layer.open({
			title:'部门选择',	
			type: 2,
			area: ['300px', '400px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/org/select_all.do?id='+orgId,
			btn: ['确定','取消'],
			btn1: function(index, layero) {
			  var iframeWin=layero.find('iframe')[0];
			  var data=iframeWin.contentWindow.fnSelect();
			  $("#orgName").val(data.name);
			  $("#orgId").val(data.id);
			}
		});
	}
	function fnSelectUser(){
		var userId=$('#userId').val();
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
			  $('#userId').val(data.id);
			  $('#userName').val(data.name);
			}
		});
	}
	function fnSelectJd(){
		var userId=$('#jdId').val();
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
			  $('#jdId').val(data.id);
			  $('#jdName').val(data.name);
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
