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
					<li><a href="javascript:backMainPage();">方法标准</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content" style="padding-bottom: 0px;">
				<form method="post" action="${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}" class="form-horizontal" id="listForm" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input id="id" name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<input id="standId" name="standId" value="${vo.standId}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-10 active"><label class="pull-right">方法编号:</label></td>
								<td class="width-40"><input id="code" class="form-control required" validate="required" name="code" type="text" value="${vo.code}" /></td>
								<td class="width-10 active"><label class="pull-right">条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;款:</label></td>
								<td class="width-40"><input id="chapter" placeholder="条款" class="form-control" name="chapter" type="text" value="${vo.chapter}" /></td>
							</tr>
							<tr>
								<td class="width-10 active"><label class="pull-right">方法名称:</label></td>
								<td class="width-40" colspan="3"><input id="name" class="form-control required" validate="required" name="name" type="text" value="${vo.name}" /></td>
							</tr>
							<tr>
								<td class="width-10 active"><label class="pull-right">检&nbsp;&nbsp;出&nbsp;限:</label></td>
								<td class="width-40">
									<input id="minLine" placeholder="上限" class="form-control"  name="minLine" type="text" value="${vo.minLine}" />
								</td>
								<td class="width-10 active"><label class="pull-right">检出下限:</label></td>
								<td class="width-40">
									<input id="maxLine" placeholder="下限" class="form-control"  name="maxLine" type="text" value="${vo.maxLine}" />
								</td>
							</tr>
							<tr>
								<td class="width-10 active"><label class="pull-right">收费标准:</label></td>
								<td class="width-40"><input id="price" placeholder="0.0" class="form-control"  name="price" type="number" value="${vo.price}" /></td>
								<td class="width-10 active"><label class="pull-right">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时:</label></td>
								<td class="width-40"><input id="hours" placeholder="0" class="form-control"  name="hours" type="number" value="${vo.hours}" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">监测项目:</label></td>
								<td colspan="3">
									<div class="input-group" style="width: 100%">
										<textarea rows="3" id="itemNames" class="form-control" name="itemNames" onclick="fnSelectItem()">${vo.itemNames}</textarea>
										<input type="hidden" id="itemIds"  name="itemIds" value="${vo.itemIds}">
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">使用仪器:</label></td>
								<td colspan="3">
									<input id="appIds" class="form-control" name="appIds" type="hidden" value="${vo.appIds}"/>
									<textarea rows="3" id="appNames" class="form-control" name="appNames" onclick="fnSelectApp()">${vo.appNames}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">原始记录模板:</label></td>
								<td colspan="3">
									<input id="tempIds" class="form-control" name="tempIds" type="hidden" value="${vo.tempIds}"/>
									<textarea rows="3" id="tempNames" class="form-control" name="tempNames" onclick="fnSelectTemp()">${vo.tempNames}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="submitTo('save4Data.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
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
function fnSelectItem(){
	var itemIds=$('#itemIds').val();
	parent.layer.open({
		title:'检测项目',	
		type: 2,
		area: ['800px', '75%'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/item/selects.do?ids='+itemIds,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
		  var iframeWin=layero.find('iframe')[0];
		  var data=iframeWin.contentWindow.fnSelect();
		  $('#itemIds').val(data.id);
		  $('#itemNames').val(data.name);
		  parent.layer.close(index);
		}
	});
}
function fnSelectApp(){
	var appIds=$('#appIds').val();
	layer.open({
		title:'检测设备选择',	
		type: 2,
		area: ['800px','75%'],
		maxmin: true,
		content: '${basePath}res/appara/selects.do?ids='+appIds,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#appIds').val(data.id);
			$('#appNames').val(data.name);
		}
	});
}
function fnSelectTemp(){
	var tempIds=$('#tempIds').val();
	layer.open({
		title:'原始记录模板',	
		type: 2,
		area: ['800px','75%'],
		maxmin: true,
		content: 'doc_select.do?ids='+tempIds,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#tempIds').val(data.id);
			$('#tempNames').val(data.name);
		}
	});
}
function validateCode(obj){
	var code=$(obj).val();
	$.ajax({ 
		url:"ajaxCheckCode.do",
		dataType:"json",
		data:{'code':code},
		type:"post",
		success: function(data){
			if("success"!=data.status){
				layer.msg(data.message);
				$(obj).val('');
			}
		},
		error:function(ajaxobj){  
			layer.msg(ajaxobj, {icon: 0,time: 3000});
	    }  
	});
}
function submitTo(uri){
	$("#listForm").attr("action",uri);
	$("#listForm").submit();
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