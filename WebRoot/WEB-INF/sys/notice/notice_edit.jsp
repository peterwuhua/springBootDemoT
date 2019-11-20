<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">公告</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="addData.do" class="form-horizontal">
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">主&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题:</label></td>
								<td colspan="3"><input id="subject" name="subject" class="form-control required" validate="required" maxlength=64 placeholder="主题" type="text" value="${vo.subject}" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:</label></td>

								<td colspan="3"><textarea id="editor_id" name="content" style="width: 100%; height: 300px;">
                                 </textarea></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">发布日期:</label></td>
								<td><input id="sendTime" name="sendTime" class="form-control required dateISO" validate="required" placeholder="发布时间" type="text" value="${vo.sendTime }" /></td>
								<td class="active"><label class="pull-right">截至日期:</label></td>
								<td><input id="endTime" name="endTime" class="form-control required dateISO" validate="required" placeholder="发布时间" type="text" value="${vo.endTime }" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">发&nbsp;&nbsp;布&nbsp;&nbsp;人:</label></td>
								<td colspan="3"><input id="userName" name="userName" class="form-control" placeholder="发布人" type="text" value="${vo.userName }" readonly="readonly" /> <input id="userId" name="userId" class="form-control" type="hidden" value="${vo.userId }" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">通知科室:</label></td>
								<td colspan="3">
									<div class="input-group">
										<input style="width: 600px;" id="orgName" name="orgName" class="form-control "  placeholder="选择科室" type="text" value="${vo.orgName}" onclick="fnSelect()" /> <input id="orgId" name="orgId" class="form-control" type="hidden" value="${vo.orgId }" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect()">选择</button>
										</div>
										<div style="display: table-cell; height: 32px; vertical-align: middle;" aria-hidden="true">
											（<font color="red">注：不选时本部门所有人可见</font>）
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit();">
								<i class="fa fa-check" aria-hidden="true"></i> 提交
							</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script type="text/javascript">
		function fnSelect() {
			var pId = $('#orgId').val();
			layer.open({
				title : '部门选择',
				type : 2,
				area : [ '350px', '75%' ],
				fix : false, //不固定
				maxmin : true,
				content : '${basePath}sys/org/select2.do?ids=' + pId,
				btn : [ '确定', '取消' ], //按钮
				btn1 : function(index, layero) {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var data = iframeWin.fnSelect();
					$('#orgId').val(data.id);
					$('#orgName').val(data.name);
				}
			});
		}
		KindEditor.ready(function(K) {
			window.editor = K.create('#editor_id', {
				items : [ 'undo', 'redo', '|', 'cut', 'copy', '|',
						'justifyleft', 'justifycenter', 'justifyright',
						'justifyfull', 'insertorderedlist',
						'insertunorderedlist', 'indent', 'outdent',
						'selectall', '|', 'fullscreen', 'fontname', 'fontsize',
						'|', 'forecolor', 'hilitecolor', 'bold', 'italic',
						'underline', 'strikethrough', 'lineheight',
						'removeformat' ]
			});

		});

		function formSubmit() {
			editor.sync();
			var b = $("form").FormValidate();
			var sendTime = $("input[name='sendTime']").val()
			var endTime = $("input[name='endTime']").val()
			if(sendTime < endTime){
				if (b) {
					$('form').ajaxSubmit(function(res) {
						if (res.status == 'success') {
							parent.toastr.success(res.message, '');
							backMainPage();
						} else {
							parent.toastr.error(res.message, '');
						}
					});
				}
			}else if(sendTime == endTime){
				if (b) {
					$('form').ajaxSubmit(function(res) {
						if (res.status == 'success') {
							parent.toastr.success(res.message, '');
							backMainPage();
						} else {
							parent.toastr.error(res.message, '');
						}
					});
				}
			}else{
				layer.msg("截至时间不能小于发布时间", {icon: 0,time: 3000});
			}
			
			
		}
	</script>
</body>
</html>
