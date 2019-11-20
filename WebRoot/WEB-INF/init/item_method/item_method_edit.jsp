<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">采样规范</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content" style="padding-bottom: 0px;">
				<form method="post" action="updateData.do" class="form-horizontal" id="listForm">
					<input id="id" name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-10 active"><label class="pull-right">检测项目:</label></td>
								<td class="width-40">${vo.itemVo.name}</td>
								<td class="width-10 active"><label class="pull-right">方法标准:</label></td>
								<td class="width-40">${vo.methodVo.code} ${vo.methodVo.name}</td>
							</tr>
							<tr>
								<td class="width-10 active"><label class="pull-right">采样设备:</label></td>
								<td class="width-40">
									<input id="cyAppName" class="form-control" name="cyAppName" type="text" value="${vo.cyAppName}"  onclick="fnSelectApp()"/>
									<input id="cyAppId"   name="cyAppId" type="hidden" value="${vo.cyAppId}" />
								</td>
								<td class="width-10 active"><label class="pull-right">采样容器:</label></td>
								<td class="width-40">
									<input id="ctName" class="form-control" name="ctName" type="text" value="${vo.ctName}" onclick="fnSelectRq()"/>
									<input id="ctId"   name="ctId" type="hidden" value="${vo.ctId}" />
								</td>
							</tr>
							<tr>
								<td class="width-10 active"><label class="pull-right">运输、存储条件:</label></td>
								<td class="width-40">
									<input id="cctj" class="form-control"  name="cctj" type="text" value="${vo.cctj}" />
								</td>
								<td class="width-10 active"><label class="pull-right">存储时长:</label></td>
								<td class="width-40">
									<input id="bcsj" placeholder="h" class="form-control"  name="bcsj" type="text" value="${vo.bcsj}" />
								</td>
							</tr>
							<tr>
								<td class="width-10 active"><label class="pull-right">采样流量:</label></td>
								<td class="width-40">
									<input id="cyll" class="form-control"  name="cyll" type="text" value="${vo.cyll}" />
								</td>
								<td class="width-10 active"><label class="pull-right">采样时长:</label></td>
								<td class="width-40">
									<input id="cysc" class="form-control"  name="cysc" type="text" value="${vo.cysc}" />
								</td>
							</tr>
							 <tr>
								<td class="width-10 active"><label class="pull-right">采样体积:</label></td>
								<td class="width-40">
									<input id="cytj" class="form-control"  name="cytj" type="text" value="${vo.cytj}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备注:</label></td>
								<td colspan="3">
									<textarea maxlength="128" rows="3" id="remark" class="form-control" name="remark">${vo.remark}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
<script type="text/javascript">
function fnSelectApp(){
	var appIds=$('#cyAppId').val();
	layer.open({
		title:'采样设备选择',	
		type: 2,
		area: ['800px','75%'],
		maxmin: true,
		content: '${basePath}res/appara/selects.do?ids='+appIds,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#cyAppId').val(data.id);
			$('#cyAppName').val(data.name);
		}
	});
}
function fnSelectRq(){
	var rqIds=$('#ctId').val();
	layer.open({
		title:'采样容器',	
		type: 2,
		area: ['800px', '75%'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}res/consumable/select.do?id='+rqIds,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
		  var iframeWin = window[layero.find('iframe')[0]['name']];
		  var data=iframeWin.fnSelect();
		  $('#ctId').val(data.id);
		  $('#ctName').val(data.model+data.name);
		}
	});
}
function formSubmitAndBack(){
	var b = $("form").FormValidate();
	if(b){
		 $('form').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
		        backMainPage();
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
}
</script>
</html>