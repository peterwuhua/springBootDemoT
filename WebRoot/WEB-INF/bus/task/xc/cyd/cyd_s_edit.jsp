<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../../include/css.jsp"%>
<%@ include file="../../../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
.form-control{
	padding: 4px;
}
</style>
</head>
<body >
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="updateTaskPoint.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table style="margin-bottom: 5px;width: 100%">
						<tr>
							<td style="width: 12%;text-align: right;">校准器名称：</td>
							<td style="width:20%;padding: 2px;">
								<input id="deme1" name="deme1" value="${vo.deme1}" type="text" class="form-control" placeholder="校准器名称、型号、编号"/>
							</td>
							<td style="width: 12%;text-align: right;">功能区类别：</td>
							<td style="width: 20%;padding: 2px;">
								<select  id="gnq" name="gnq"  class="form-control required" validate="required">
									<option value="">请选择</option>
									<c:forEach items="${gnqList}" var="e">
										<c:choose>
											<c:when test="${vo.gnq==e}">
												<option value="${e}" selected="selected">${e}</option>
											</c:when>
											<c:otherwise>
												<option value="${e}">${e}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</td>
							<td style="width: 12%;text-align: right;">测量时工况：</td>
							<td style="padding: 2px;">
								<input id="deme2" name="deme2" value="${vo.deme2}" type="text" class="form-control" placeholder=""/>
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;气：</td>
							<td style="padding: 2px;">
								<select name="tx" data-placeholder="请选择" class="chosen-select form-control" multiple>
									<c:forEach items="${tqList}" var="e1" >
										<c:choose>
											<c:when test="${fn:contains(vo.tx,e1)}">
												<option value="${e1}" selected="selected" hassubinfo="true">${e1}</option>
											</c:when>
											<c:otherwise>
												<option value="${e1}" hassubinfo="true">${e1}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</td>
							<td style="text-align: right;">风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向：</td>
							<td style="padding: 2px;">
								<select name="fx" data-placeholder="请选择" class="chosen-select form-control" multiple>
									<c:forEach items="${fxList}" var="e1">
										<c:choose>
											<c:when test="${fn:contains(vo.fx,e1)}">
												<option value="${e1}" selected="selected" hassubinfo="true">${e1}</option>
											</c:when>
											<c:otherwise>
												<option value="${e1}" hassubinfo="true">${e1}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</td>
							<td style="text-align: right;">风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：</td>
							<td style="padding: 2px;">
								<input id="fs" name="fs" value="${vo.fs}" type="text" class="form-control" placeholder="m/s"/>
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">采样设备：</td>
							<td style="padding: 2px;">
								 <select id="cyAppId" name="cyAppId" data-placeholder="请选择" class="chosen-select form-control" multiple>
									<c:forEach items="${appList}" var="e" varStatus="s">
										<c:choose>
											<c:when test="${fn:contains(vo.cyAppId,e.id)}">
												<option value="${e.id}" selected="selected" hassubinfo="true">${e.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${e.id}" hassubinfo="true">${e.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select> 
								<input id="cyAppName" name="cyAppName" value="${vo.cyAppName}" type="hidden" /> 
							</td>
							<td style="text-align: right;">监测方法：</td>
							<td style="padding: 2px;">
								<%-- <div class="input-group" style="width: 100%">
									<input id="cyStandId" name="cyStandId" value="${vo.cyStandId}" type="hidden" /> <input id="cyStandName" name="cyStandName" value="${vo.cyStandName}" type="text" class="form-control" placeholder="请选择" onclick="chooseCyMothed()" />
								</div> --%>
								<select name="cyStandId" data-placeholder="请选择" class="chosen-select form-control" multiple>
									<c:forEach items="${cyMethodList}" var="e1" >
										<c:choose>
											<c:when test="${fn:contains(vo.cyStandId,e1.id)}">
												<option value="${e1.id}" selected="selected" hassubinfo="true">${e1.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${e1.id}" hassubinfo="true">${e1.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<input id="cyStandName" name="cyStandName" value="${vo.cyStandName}" type="hidden" />
							</td>
							<td style="text-align: right;">其他描述：</td>
							<td style="padding: 2px;">
								<input id="xcDesc" name="xcDesc" value="${vo.xcDesc}" type="text" class="form-control" placeholder="现场或样品情况" />
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">测&nbsp;试&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
									<select id="fxId" name="fxId" data-placeholder="请选择" class="chosen-select form-control required" validate="required" multiple>
										<c:forEach items="${userList}" var="e" varStatus="s">
											<c:choose>
												<c:when test="${fn:contains(vo.fxId,e.id)}">
													<option value="${e.id}" selected="selected" hassubinfo="true">${e.userName}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}" hassubinfo="true">${e.userName}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<input type="hidden" id="fxName" name="fxName" value="${vo.fxName}" >
							</td>
							<td style="text-align: right;">校&nbsp;核&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
									<select id="fxId" name="jhId"  class="form-control required" validate="required" onchange="checkThis(this);">
										<option value="">-请选择-</option>
										<c:forEach items="${userList}" var="e">
											<c:choose>
												<c:when test="${vo.jhId==e.id}">
													<option value="${e.id}" selected="selected">${e.userName}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}">${e.userName}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<input type="hidden" id="jhName" name="jhName" value="${vo.jhName}" >
							</td>
							<td style="text-align: right;">受检单位陪同人：</td>
							<td style="padding: 2px;">
								<input id="ptUser" name="ptUser" value="${vo.ptUser}" type="text" class="form-control" placeholder="受检单位陪同人" />
							</td>
						</tr>
					</table>
					<div style="overflow-x: auto;">
						<table class="table table-bordered" style="margin-bottom: 0px; min-width:1700px">
							<thead>
								<tr>
									<th width="50">
									</th>
									<th width="100px">测点名称</th>
									<th width="120px">测点编号</th>
									<th width="100px">开始时间</th>
									<th width="100px">结束时间</th>
									<th width="100px">昼夜类型</th>
									<th width="100px">声源类型</th>
									<th width="100px">校准前示值</th>
									<th width="100px">校准后示值</th>
									<th width="100px">Leq</th>
									<th width="100px">L10</th>
									<th width="100px">L50</th>
									<th width="100px">L90</th>
									<th width="100px">Lmax</th>
									<th width="100px">Lmin</th>
									<th width="100px">标准偏差SD</th>
									<th>监测项目</th>
								</tr>
							</thead>
							<tbody id="samp_tb">
								<c:forEach items="${vo.sampList}" var="e" varStatus="v">
									<tr>
										<td align="center">
											<a onclick="deleteSamp('${e.id}')"><i class="fa fa-close text-danger"></i></a>
								 			<input type="hidden" name="sampList[${v.index}].id" value="${e.id}">
								 			<input type="hidden" name="sampList[${v.index}].recordVo.id" value="${e.recordVo.id}">
								 		</td>
									 	<td>
								 			${e.pointName}
								 		</td>
								 		<td>
								 			<input type="text" class="form-control required" validate="required" name="sampList[${v.index}].sampCode" value="${e.sampCode}">
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
									 			<input type="text" class="form-control  required" validate="required" name="sampList[${v.index}].cyTime" value="${e.cyTime}"  data-mask="99:99">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
									 			<input type="text" class="form-control  required" validate="required" name="sampList[${v.index}].cyEndTime" value="${e.cyEndTime}"  data-mask="99:99">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			 <select  class="form-control" name="sampList[${v.index}].fcType">
								 			 	<c:choose>
								 			 		<c:when test="${e.fcType=='夜间'}">
								 			 			<option value="昼间">昼间</option>
							 			 				<option value="夜间" selected="selected">夜间</option>
								 			 		</c:when>
								 			 		<c:otherwise>
								 			 			<option value="昼间" selected="selected">昼间</option>
							 			 				<option value="夜间">夜间</option>
								 			 		</c:otherwise>
								 			 	</c:choose>
								 			 </select>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
									 			<input type="text" class="form-control" name="sampList[${v.index}].recordVo.demo1" value="${e.recordVo.demo1}">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
									 			<input type="text" class="form-control" name="sampList[${v.index}].recordVo.demo2" value="${e.recordVo.demo2}">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
									 			<input type="text" class="form-control" name="sampList[${v.index}].recordVo.demo3" value="${e.recordVo.demo3}">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
										 		<input type="text" class="form-control" name="sampList[${v.index}].recordVo.v1" value="${e.recordVo.v1}">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
										 		<input type="text" class="form-control" name="sampList[${v.index}].recordVo.v2" value="${e.recordVo.v2}">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
										 		<input type="text" class="form-control" name="sampList[${v.index}].recordVo.v3" value="${e.recordVo.v3}">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
										 		<input type="text" class="form-control" name="sampList[${v.index}].recordVo.v4" value="${e.recordVo.v4}">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
										 		<input type="text" class="form-control" name="sampList[${v.index}].recordVo.avg1" value="${e.recordVo.avg1}">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
										 		<input type="text" class="form-control" name="sampList[${v.index}].recordVo.avg2" value="${e.recordVo.avg2}">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
										 		<input type="text" class="form-control" name="sampList[${v.index}].recordVo.demo10" value="${e.recordVo.demo10}">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			${e.itemNames}
								 		</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12" style="text-align: right;">
							<a class="btn btn-w-m btn-success" type="button" onclick="fnSubmit4Save('updateCyd.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('updateCyd.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 完成</a>
							<a class="btn btn-w-m btn-white" href="javascript:close();" ><i class="fa fa-times" aria-hidden="true"></i> 关闭</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../../include/js.jsp"%>
	 <!-- Chosen -->
<script src="${basePath}h/js/plugins/chosen/chosen.jquery.js"></script>
      <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
<script type="text/javascript">
var index = parent.layer.getFrameIndex(window.name); 

$(document).ready(function(){
	var config = {
	          '.chosen-select': {},
	          '.chosen-select-deselect': {
	              allow_single_deselect: true
	          },
	          '.chosen-select-no-single': {
	              disable_search_threshold: 10
	          },
	          '.chosen-select-no-results': {
	              no_results_text: 'Oops, nothing found!'
	          },
	          '.chosen-select-width': {
	              width: "95%"
	          }
	      }
	      for (var selector in config) {
	          $(selector).chosen(config[selector]);
	      }
})
function fnSubmit4Save(url){
	$.ajax({ 
		url:url,
		dataType:"json",
		data:$('#thisForm').serialize(),
		type:"post",
		success: function(data){
			parent.layer.msg(data.message, {icon: 0,time: 3000})
		},
		error:function(ajaxobj){  
			layer.msg(ajaxobj, {icon: 0,time: 3000});
	    }  
	});
}
function fnSubmit(url){
	var t = $("#thisForm").FormValidate();
	if(t){
		$.ajax({ 
			url:url,
			dataType:"json",
			data:$('#thisForm').serialize(),
			type:"post",
			success: function(data){
				parent.layer.msg(data.message, {icon: 0,time: 3000})
				if("success"==data.status){
					parent.layer.close(index);
				}
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }  
		});
	}
}
//采样标准 弹出层
function chooseCyMothed(){
	var ids=$('#cyStandId').val();
	parent.layer.open({
		title:'采样标准',	
		type: 2,
		area: ['800px', '500px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/sampSource/selects.do?ids='+ids,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin=layero.find('iframe')[0];
			var data=iframeWin.contentWindow.fnSelect();
		    $('#cyStandId').val(data.id);
			$('#cyStandName').val(data.name);
			parent.layer.close(index);
		}
	});
}
function close(){
	parent.layer.close(index);
}
function copyVals(obj){
	obj=$(obj);
	var value0=obj.closest('td').find('input').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#samp_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(value0);
	});
}
function addSamp(pointId,type){
	$.ajax({ 
		url:"${basePath}bus/taskAp/addSamp.do",
		data: {'pointId':pointId,'type':type},
		async:false,
		success: function(data){
			layer.msg(data.message, {icon: 0,time: 3000});
			if(data.status='success'){
				 window.location.reload(true);
			}
		},
		error:function(ajaxobj){  
			layer.msg(ajaxobj, {icon: 0,time: 3000});
	    }
	});
}
function deleteSamp(sampId){
	$.ajax({ 
		url:"${basePath}bus/sampling/deleteSamp.do",
		data: {'id':sampId},
		async:false,
		success: function(data){
			layer.msg(data.message, {icon: 0,time: 3000});
			if(data.status='success'){
				 window.location.reload(true);
			}
		},
		error:function(ajaxobj){  
			layer.msg(ajaxobj, {icon: 0,time: 3000});
	    }
	});
}
function checkThis(obj){
	var t=$(obj).find('option:selected').text();
	$(obj).closest('td').find('input').val(t);
}

function setAppData(id,name){
	var appUL=$('#appUL');
	var index=0;
	var indexStr=appUL.children("li").last().attr('index');
	if(indexStr!=undefined){
		index=parseInt(indexStr)+1;
	}
	for(var i=0;i<id.length;i++){
		var liStr='<li class="search-choice" index="'+index+'"><span>'+name[i]+'</span>'+
          '<input name="appList['+index+'].appId" value="'+id[i]+'" type="hidden" />'+
          '<input name="appList['+index+'].appName" value="'+name[i]+'" type="hidden" />'+
          '<a class="search-choice-close"  onclick="removeLi(this)" title="删除"><i class="fa fa-close text-danger"></i></a></li>';
          appUL.append(liStr);
          index++;
	}
}
function removeLi(obj){
	var t = $(obj).parent(); 
	t.remove();
	parent.layer.msg('删除成功', {icon: 0,time: 3000});
}
//采样设备
function fnSelectApp(){
	var ids='';
	$('input[name$=".appId"]').each(function(){
		if(ids.indexOf($(this).val())<0){
			ids+=$(this).val()+",";
		}
	});
	layer.open({
		title:'采样设备',	
		type: 2,
		area: ['70%', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}bus/taskAp/appara_select.do?ids='+ids,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
		  var iframeWin = window[layero.find('iframe')[0]['name']];
		  var data=iframeWin.fnSelect();
		  setAppData(data.id,data.name);
		}
	});
}

</script>
</body>
</html>
