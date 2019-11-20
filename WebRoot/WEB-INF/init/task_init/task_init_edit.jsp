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
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">采样方法</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">测试参数:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="itemName" name="itemName" class="form-control required" validate="required" value="${vo.itemName}" onclick="fnSelectItem()">
										<input type="hidden" id="itemId"  name="itemId" value="${vo.itemId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectItem()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">检测标准:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="methodName" name="methodName" class="form-control"  value="${vo.methodName}" onclick="fnSelectMethod()">
										<input type="hidden" id="methodId"  name="methodId" value="${vo.methodId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectMethod()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">使用仪器:</label></td>
								<td>
									<div class="input-group">
										<input type="text" id="appName" name="appName" class="form-control" value="${vo.appName}"  onclick="fnSelectApp()">
										<input type="hidden" id="appId"  name="appId" value="${vo.appId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectApp()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">采样介质:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="jzName" name="jzName" class="form-control" value="${vo.jzName}" onclick="chooseCt()">
										<input type="hidden" id="jzId" name="jzId" value="${vo.jzId}" >
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="chooseCt()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">采样流量:</label></td>
								<td class="width-35">
									<input type="text" id="ll" name="ll" class="form-control" value="${vo.ll}">
								</td>
								<td class="width-15 active"><label class="pull-right">采样时长:</label></td>
								<td class="width-35">
									<input type="text" id="hours" name="hours" class="form-control" value="${vo.hours}">
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">储运条件:</label></td>
								<td class="width-35">
									<input type="text" id="conditions" name="conditions" class="form-control" value="${vo.conditions}">
								</td>
								<td class="width-15 active"><label class="pull-right">固定剂:</label></td>
								<td class="width-35">
									<input type="text" id="gdj" name="gdj" class="form-control" value="${vo.gdj}">
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">能力范围:</label></td>
								<td>
									<div class="radio i-checks">
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="是" name="isMay" <c:if test="${vo.isMay!='否'}">checked</c:if>>
                                            </div> 是
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="否" name="isMay" <c:if test="${vo.isMay=='否'}">checked</c:if>>
                                            </div> 否
                                        </label>
                                    </div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									 <textarea maxlength="128" class="form-control" rows="2" cols="80" id="remark" name="remark">${vo.remark}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
		function fnSelectItem(){
			var id=$('#itemId').val();
			layer.open({
				title:'监测项目',	
				type: 2,
				area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '/init/item/select.do?sort=1&id='+id,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelect();
				  $('#itemName').val(data.name);
				  $('#itemId').val(data.id);
				}
			});
		}
		 
		function fnSelectMethod(){
			var ids=$('#methodId').val();
			layer.open({
				title:'检测方法',	
				type: 2,
				area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: 'method_select.do?ids='+ids,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelect();
				  $('#methodName').val(data.name);
				  $('#methodId').val(data.id);
				}
			});
		}
		function fnSelectApp(){
			var ids=$('#appId').val();
			layer.open({
				title:'使用仪器',	
				type: 2,
				area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}res/appara/select.do?id='+ids,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelect();
				  $('#appName').val(data.name);
				  $('#appId').val(data.id);
				}
			});
		}
		function chooseCt(){
			var ids=$('#jzId').val();
			layer.open({
				title:'采样介质',	
				type: 2,
				area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}res/consumable/selects.do?ids='+ids,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelect();
				  $('#jzId').val(data.id);
				  $('#jzName').val(data.name);
				}
			});
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
