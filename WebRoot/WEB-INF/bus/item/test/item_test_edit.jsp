<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<!-- Sweet Alert -->
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>

<style type="text/css">
 
#jcTb .input-group-btn .btn{
	padding: 6px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>数据录入</a></li>
					<c:if test="${vo.type=='2'}">
						<li><strong>重测</strong></li>
					</c:if>
					<li><strong>编辑</strong>
						<c:choose>
							<c:when test="${vo.isBack=='Y'}">
								（<span style="color: red"> 退回原因：${logVo.msg}</span> ）
							</c:when>
							<c:when test="${vo.checkMsg!='' && vo.checkMsg!=null}">
								（<span style="color: red"> ${vo.checkMsg}</span> ）
							</c:when>
						</c:choose>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="update4Data.do?isCommit=0" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<input name="ids" value="${vo.ids}" type="hidden" />
					<input name="taskVo.id" value="${vo.taskVo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
						<tr>
							<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
							<td class="width-35">
								<a href="javascript:void(0);" onclick="fnTask('${vo.taskVo.id}','${vo.taskVo.no}');">${vo.taskVo.no}</a>
							</td>
							<td class="width-15 active"><label class="pull-right">受检单位:</label></td>
							<td class="width-35">
								${vo.taskVo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测性质:</label></td>
							<td class="width-35">
								${vo.taskVo.taskType}
							</td>
							<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
							<td class="width-35">
								${vo.taskVo.sampName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测项目:</label></td>
							<td class="width-35">
								<input id="itemName" name="itemName" class="form-control" type="text" value="${vo.itemName}"  type="hidden" readonly="readonly" /> 
								<input type="hidden" id="itemId" name="itemId" value="${vo.itemId}"/>
							</td>
							<td class="width-15 active"><label class="pull-right">计量单位:</label></td>
							<td class="width-35">
	                         	<input id="unit" name="unit" class="form-control" maxlength=20 type="text" value="${vo.unit}" />
							</td>
						</tr>
						<c:if test="${vo.taskVo.pj=='是'}">
							<tr>
								<td class="width-15 active"><label class="pull-right">评价标准:</label></td>
								<td class="width-35">
									<textarea id="standName" name="standName" class="form-control required" validate="required" onclick="choosePstand()">${vo.standName}</textarea>
									<input id="standId" name="standId" value="${vo.standId}" type="hidden" />
								</td>
								<td class="width-15 active"><label class="pull-right">标&nbsp;准&nbsp;&nbsp;值:</label></td>
								<td class="width-35">
									<c:choose>
										<c:when test="${vo.limited!=''&&vo.limited!=null}">
												${vo.limited} ${vo.unit}
										</c:when> 
										<c:otherwise>
											<font color="red">限值未找到</font>
										</c:otherwise>
									</c:choose>
									<a  class="btn btn-info btn-xs" onclick="chooseLimit()">选择</a>
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测方法:</label></td>
							<td class="width-35">
								<textarea id="methodName" name="methodName" class="form-control required" validate="required" onclick="chooseMethod()">${vo.methodName}</textarea>
								<input id="methodId" name="methodId" value="${vo.methodId}" type="hidden" />
							</td>
							<td class="width-15 active"><label class="pull-right">检&nbsp;出&nbsp;&nbsp;限:</label></td>
							<td class="width-35">
								<textarea id="limitLine" name="limitLine" class="form-control" maxlength=64>${vo.limitLine}</textarea>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">使用仪器:</label></td>
							<td class="width-35">
								<textarea id="appName" name="appName" class="form-control " onclick="chooseApp()">${vo.appName}</textarea>
								<input id="appId" name="appId" value="${vo.appId}" type="hidden" />
							</td>
							<td class="width-15 active"><label class="pull-right">标准溶液配置:</label></td>
							<td class="width-35">
								<c:choose>
									<c:when test="${vo.pzSt=='1'}">
										<a class="btn btn-outline btn-success" type="button" onclick="fnBZPZ('${vo.id}','${vo.itemId}')">已填报</a>
									</c:when>
									<c:otherwise>
										<a class="btn btn-outline btn-danger" type="button" onclick="fnBZPZ('${vo.id}','${vo.itemId}')">未填报</a>
										<input type="hidden" name="unComp">
									</c:otherwise>
								</c:choose>
								<%-- <a class="btn btn-outline btn-danger" type="button" onclick="fnBZPZ('${e.id}','${vo.itemId}')">未填报</a>
								<input type="hidden" name="unComp"> --%>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">温&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度:</label></td>
							<td class="width-35">
								<input id="wd" name="wd" class="form-control" placeholder="°C" maxlength=20 type="text" value="${vo.wd}" /> 
							</td>
							<td class="width-15 active"><label class="pull-right">湿&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度:</label></td>
							<td class="width-35">
	                         	<input id="sd" name="sd" class="form-control" placeholder="%" maxlength=20 type="text" value="${vo.sd}" />
							</td>
						</tr>
						<tfoot>
							<tr>
								<td colspan="7"><b>检测结果</b></td>
							</tr>
						</tfoot>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 1px;">
						<thead>
							<tr>
								<th style="text-align: center;width: 50px;">序号</th>
								<th style="text-align: center;width: 80px;">样品名称</th>
								<th style="text-align: center;width: 80px;">采样日期</th>
								<th style="text-align: center;width: 80px;">样品编号</th>
								<c:choose>
									<c:when test="${vo.st=='气'}">
										<th style="text-align: center;width: 150px;">浓度值</th>
										<th style="text-align: center;width: 150px;">速率值</th>
									</c:when>
									<c:otherwise>
										<th style="text-align: center;width: 150px;">检测值</th>
									</c:otherwise>
								</c:choose>
								<th style="text-align: center;width: 80px;">单项判定</th>
							</tr>
						</thead>
						<tbody id="jcTb">
							<c:forEach items="${vo.itemList}" var="e" varStatus="s">
								<tr id="${e.id}" key="${s.index}">
									<td align="center">
										<c:choose>
											<c:when test="${e.trVo.oper=='1'}">
												<a onclick="deleteSamp('${e.trVo.id}')"><i class="fa fa-close text-danger"></i></a>
											</c:when>
											<c:otherwise>
												${s.index+1}
											</c:otherwise>
										</c:choose>
										<input type="hidden" value="${e.id}" name="itemList[${s.index}].id" />
										<input type="hidden" value="${e.trVo.id}" name="itemList[${s.index}].trVo.id" />
									</td>
									<td align="center">
										${e.trVo.sampVo.sampTypeName}
									</td>
									<td align="center">
										${e.cyDate}
									</td>
									<td>
	                                     <c:choose>
											<c:when test="${e.trVo.oper=='1'}">
												<input type="text" id="sampCode_${s.index}_${rs.index}" name="itemList[${s.index}].trVo.sampVo.sampCode" class="form-control" value="${e.trVo.sampVo.sampCode}">
											</c:when>
											<c:otherwise>
												${e.trVo.sampVo.sampCode}
												<input type="hidden" id="sampCode_${s.index}_${rs.index}" name="itemList[${s.index}].trVo.sampVo.sampCode" class="form-control" value="${e.trVo.sampVo.sampCode}">
											</c:otherwise>
										</c:choose>
									</td>
									<c:choose>
										<c:when test="${vo.st=='气' && e.trVo.sampVo.sampTypeName.indexOf('有组织')>=0}">
											<td>
			                                    <input type="text" id="value_${s.index}" key="${e.trVo.sampVo.recordVo.demo1}" name="itemList[${s.index}].trVo.value" class="form-control required" validate="required" value="${e.trVo.value}" onkeyup="keygo(event,this)" onchange="validtValueQ(this,'${s.index}');">
											</td>
											<td>
			                                    <input type="text" id="sl_${s.index}" name="itemList[${s.index}].trVo.sl" class="form-control" value="${e.trVo.sl}" onkeyup="keygo(event,this)">
											</td>
										</c:when>
										<c:when test="${vo.st=='气'}">
											<td>
			                                    <input type="text" id="value_${s.index}" key="${e.trVo.sampVo.recordVo.demo1}" name="itemList[${s.index}].trVo.value" class="form-control required" validate="required" value="${e.trVo.value}" onkeyup="keygo(event,this)" onchange="validtValue(this,'${s.index}');">
											</td>
											<td>
												/
											</td>
										</c:when>
										<c:otherwise>
											<td>
			                                    <input type="text" id="value_${s.index}" name="itemList[${s.index}].trVo.value" class="form-control required" validate="required" value="${e.trVo.value}" onkeyup="keygo(event,this)" onchange="validtValue(this,'${s.index}')">
											</td>
										</c:otherwise>
									</c:choose>
									<td >
										<select id="result_${s.index}" name="itemList[${s.index}].result" class="form-control" >
										 	<c:choose>
										 		<c:when test="${e.result=='合格'}">
										 			<option value="">/</option>
												 	<option value="合格" selected="selected">合格</option>
												 	<option value="不合格">不合格</option>
										 		</c:when>
										 		<c:when test="${e.result=='不合格'}">
										 			<option value="">/</option>
												 	<option value="合格">合格</option>
												 	<option value="不合格" selected="selected">不合格</option>
										 		</c:when>
										 		<c:otherwise>
										 			<option value="">/</option>
												 	<option value="合格">合格</option>
												 	<option value="不合格">不合格</option>
										 		</c:otherwise>
										 	</c:choose>
										 </select>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<!-- <tr>
							<td class="active"><label class="pull-right">原始记录:</label></td>
							<td colspan="3">
								<div class="input-group" style="100%">
									<select id="temp" name="temp" class="form-control" style="width: 200px;">
									 	<option value="">-请选择-</option>
									 	<c:forEach items="${vo.tempList}" var="e" varStatus="v">
									 		<c:choose>
										 		<c:when test="${vo.temp==e.relativePath}">
												 	<option value="${e.relativePath}" selected="selected">${e.name}</option>
										 		</c:when>
										 		<c:otherwise>
												 	<option value="${e.relativePath}">${e.name}</option>
										 		</c:otherwise>
										 	</c:choose>
									 	</c:forEach>
									 </select>
									 <div class="input-group-btn">
									 	<button tabindex="-1" class="btn btn-warning" type="button" onclick="createTemp(this)">重新生成</button>
										<c:choose>
											<c:when test="${vo.filePath==''||vo.filePath==null}">
												<button tabindex="-1" class="btn btn-default" type="button" onclick="fnOpenTemp(this)">在线编辑</button>
											</c:when>
											<c:otherwise>
												<button tabindex="-1" class="btn btn-info" type="button" onclick="fnOpenTemp(this)">在线编辑</button>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</td>
						</tr>-->
						<tr>
							<td class="active"><label class="pull-right">上传附件:</label></td>
							<td colspan="3" >
								<a class="btn btn-info" href="javascript:void(0);" onclick="fileUpload();" style="float: left; margin-right: 5px;">在线上传</a> 
								<div id="removeFile">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
										<div style="float: left; margin-right: 10px;">
											<c:choose>
												<c:when test="${fn:contains(e.fileName,'.doc')||fn:contains(e.fileName,'.xls')||fn:contains(e.fileName,'.ppt')||fn:contains(e.fileName,'.pdf')}">
													<a href="javascript:void(0);" onclick="fnShowFile('${e.id}');" class="btn btn-w-m btn-info">${e.fileName}</a>
												</c:when>
												<c:when test="${fn:contains(e.fileName,'.jpg')||fn:contains(e.fileName,'.png')||fn:contains(e.fileName,'.jpeg')||fn:contains(e.fileName,'.gif')}">
													<a href="javascript:openImg('${e.filePath}','${e.fileName}');" class="btn btn-w-m btn-info">${e.fileName}</a>
												</c:when>
												<c:otherwise>
													<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
												</c:otherwise>
											</c:choose>
											<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
										</div>
									</c:forEach>
								</div>
							</td>
						</tr> 
						<tr>
							<td class="width-15 active"><label class="pull-right">检&nbsp;测&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								<input id="testManId" name="testManId" type="hidden" value="${vo.testManId}" /> 
								<input id="testMan" name="testMan" class="form-control" maxlength=32  type="text" value="${vo.testMan}" readonly="readonly" />
							</td>
							<td class="active"><label class="pull-right">检测时间:	</label></td>
							<td>
								<div class="input-group" style="width: 100%">
									<input type="text" name="testTime" class="form-control  datetimeISO required" validate="required"  validate="required" value="${vo.testTime}" autocomplete="off">
									 <span class="input-group-addon">至</span>
									<input type="text" name="testEndTime" class="form-control  datetimeISO required" validate="required"  validate="required" value="${vo.testEndTime}" autocomplete="off">
							 	</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea  rows="2" id="testMsg" name="testMsg" class="form-control" maxlength="128">${vo.testMsg}</textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-info" type="button" onclick="fnAddZkSamp()"><i class="fa fa-plus" aria-hidden="true"></i> 质控样</a>
							<a class="btn btn-w-m btn-success" type="button" onclick="formSubmitSave('update4Data.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('update4Data.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../include/js.jsp"%>
<!-- Sweet alert -->
<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
<%@ include file="../../../sys/open/open_img.jsp"%>
</body>
<script>
$('input[type="file"]').prettyFile();
function fnShowFile(id){
	POBrowser.openWindow('${basePath}sys/files/showFile.do?id='+id,'width=1200px;height=800px;');
}
function fileUpload(){
	var id='${vo.id}';
	$('#thisForm').attr('action','update4Data.do?isCommit=0');
	$('#thisForm').ajaxSubmit(function(res) {
    	if(res.status=='error'){
        	parent.toastr.error(res.message, '');
        	return false;
        }
	});
	layer.open({
		title:'在线上传',	
		type: 2,
		area: ['700px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: 'upload.do?id='+id,
		end: function () {
            location.reload();
        }
	});
}
function fnTask(id,no){
	parent.layer.open({
		title:'任务【'+no+'】',	
		type: 2,
		area: ['1000px','85%'],
		fix: false, //不固定
		maxmin: true,
		content: '/bus/task/show.do?id='+id
	});
}
function copyVal(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('input').eq(0).val();
	var idValue1=obj.closest('td').find('input').eq(1).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#stand_method tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
		$(this).find('td').eq(indexTd).find('input').eq(1).val(idValue1);
	});
}
 
function copySel(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('select').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#jcTb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('select').eq(0).val(idValue);
	});
}
function formSubmitSave(url){
	$('#thisForm').attr('action',url);
	$('#thisForm').ajaxSubmit(function(res) {
    	if(res.status=='success'){
    	    parent.toastr.success(res.message, '');
    	    location.reload();
        }else{
        	parent.toastr.error(res.message, '');
        }
	});
}	
function fnSubmit(url){
	$('#thisForm').attr('action',url);
	var b = $("#thisForm").FormValidate();
	if(b){
		swal({
	        title: "您确定要提交该任务吗",
	        text: "提交后将无法修改，请谨慎操作！",
	        type: "success",
	        showCancelButton: true,
	        confirmButtonColor: "#1ab394",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消"
	    }, function () {
	    	$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
			        backMainPage();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
	    });
	}
}
function keygo(evt,obj){
	var cols=$('#jcTb').find('tr').length-1;
	var key = window .event?evt.keyCode:evt.which; 
	var tdIndex=$(obj).closest('td').index();
	var trIndex=$(obj).closest('tr').index();
	if (key==37){//左 
		if(tdIndex>5){
			var tdStr=$('#jcTb').find('tr:eq('+trIndex+')').find('td:eq('+(tdIndex-1)+')');
			if(tdStr.find('input').length>0){
				tdStr.find('input').focus();
			}else{
				tdStr.find('select').focus();
			}
		}
	}else if(key==38){//上
		if(trIndex>0){
			var tdStr=$('#jcTb').find('tr:eq('+(trIndex-1)+')').find('td:eq('+tdIndex+')');
			if(tdStr.find('input').length>0){
				tdStr.find('input').focus();
			}else{
				tdStr.find('select').focus();
			}
		}
	}else if(key==39){//右 
		if(tdIndex<8){
			var tdStr=$('#jcTb').find('tr:eq('+trIndex+')').find('td:eq('+(tdIndex+1)+')');
			if(tdStr.find('input').length>0){
				tdStr.find('input').focus();
			}else{
				tdStr.find('select').focus();
			}
		}
	}else if(key==40){//下
		if(trIndex<cols){
			var tdStr=$('#jcTb').find('tr:eq('+(trIndex+1)+')').find('td:eq('+tdIndex+')');
			if(tdStr.find('input').length>0){
				tdStr.find('input').focus();
			}else{
				tdStr.find('select').focus();
			}
		}
	}
}
function checkMethod(obj,n){
	var methodId=$('#methodId'+n).val();
	if(methodId==''){
		layer.msg('请选择检测方法！', {icon: 0,time: 1000});
		$(obj).blur();
	}
}
function removeFiles(id,obj){
	layer.confirm('确认要删除?', {icon:3, title:'系统提示'}, function(index){
		$.ajax({
			url:'${basePath}sys/files/deleteOne.do?id='+id,
			dataType:"json",
			type:"post",
			async:false,
			success: function(data){
				if(data.status=='success'){
					layer.msg(data.message, {icon: 0,time: 1000});
					$(obj).parent().remove();
				}
			},
			error:function(ajaxobj){
		    }  
		});
	});
}
 
</script>
<script type="text/javascript">
//标准物质 、 方法、 仪器
//测试标准
function choosePstand(){
	var standId=$('#standId').val();
	var sampTypeId='${vo.sampTypeId}';
	layer.open({
		title:'评价标准',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/pstandard/select.do?id='+standId+'&sampTypeId='+sampTypeId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#standId').val(data.id);
			$('#standName').val(data.name);
			//保存方法
			$('#thisForm').attr('action','update4Data.do?isCommit=0');
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success('更新成功！', '');
		    	    location.reload();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	});
}
//限值
function chooseLimit(){
	var standId=$('#standId').val();
	if(standId==null||standId==''){
		layer.msg('请选择评价标准！', {icon: 0,time: 1000});
		return false;
	}
	var sampTypeId='${vo.sampTypeId}';
	var sampType='${vo.sampTypeName}';
	layer.open({
		title:'评价标准',	
		type: 2,
		area: ['1000px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/pstandItem/select.do?sampType='+encodeURI(sampType)+'&standId='+standId+'&itemVo.id=${vo.itemId}',
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#thisForm').attr('action','update4Limited.do?id=${vo.id}&uuid='+data.id);
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success('更新成功！', '');
		    	    location.reload();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	});
}
//测试方法
function chooseMethod(){
	var methodId=$('#methodId').val();
	var itemId=$('#itemId').val();
	layer.open({
		title:'检测方法',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/itemMethod/showMethod.do?methodVo.id='+methodId+'&itemVo.id='+itemId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#methodId').val(data.id);
			$('#methodName').val(data.name);
			$('#limitLine').val(data.limitLine);
			if(data.id!='methodId'){
				$('#appId').val(null);
				$('#appName').val(null);
			}
			//保存方法
			$('#thisForm').attr('action','update4Data.do?isCommit=0');
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success('更新成功！', '');
		    	    location.reload();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	});
}
//测试仪器
function chooseApp(){
	var methodId=$('#methodId').val();
	if(methodId==''){
		layer.msg('请选择检测方法！', {icon: 0,time: 1000});
		return false;
	}
	var appId=$('#appId').val();
	layer.open({
		title:'检测仪器',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: 'showApp.do?methodId='+methodId+'&appId='+appId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#appId').val(data.id);
			$('#appName').val(data.name);
			//保存方法
			$('#thisForm').attr('action','update4Data.do?isCommit=0');
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success('更新成功！', '');
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	});
}

//标准物质
/**function chooseSt(){
	var stId=$('#stId').val();
	layer.open({
		title:'标准物质',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}res/standard/selects.do?ids='+stId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#stId').val(data.id);
			$('#stName').val(data.name);
			//保存方法
			$('#thisForm').attr('action','update4Data.do?isCommit=0');
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success('更新成功！', '');
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	});
}**/
</script>
<script type="text/javascript">
function createTemp(obj){
	var tempName = $("#temp").val();
	if (tempName=="")
		{
		layer.msg('请选择相应的原始记录模板', {icon: 0,time: 3000});
		 return;
		}
	var msg=checkTempFile();
	$('#thisForm').attr('action','update4Data.do?isCommit=0');
	$('#thisForm').ajaxSubmit(function(res) {
    	if(res.status=='success'){
    		if(msg==''){
    			if(confirm("文件已经存在，要重新生成吗？")){
    				POBrowser.openWindow('${basePath}bus/itemTest/createTemp.do?id=${vo.id}','width=1200px;height=800px;');
    			}
    		}else{
    			POBrowser.openWindow('${basePath}bus/itemTest/createTemp.do?id=${vo.id}','width=1200px;height=800px;');
    			$(obj).next().removeClass('btn-default')
    			$(obj).next().addClass('btn-info')
    		}
        }else{
        	parent.toastr.error(res.message, '');
        }
	});
}
function fnOpenTemp(obj){
	var tempName = $("#temp").val();
	if (tempName=="")
		{
		layer.msg('请选择相应的原始记录模板', {icon: 0,time: 3000});
		 return;
		}
	POBrowser.openWindow('${basePath}bus/itemTest/editTemp.do?id=${vo.id}','width=1200px;height=800px;');
	$(obj).removeClass('btn-default')
	$(obj).addClass('btn-info')
}

function checkTempFile(){
	var msg='';
	$.ajax({ 
		url:"checkTempFile.do?id=${vo.id}",
		dataType:"json",
		type:"post",
		async:false,
		success: function(data){
			if("error"==data.type){
				msg=data.message;
			}
		},
		error:function(ajaxobj){  
			msg=ajaxobj;
	    }  
	});
	return msg;
}
</script>
<script type="text/javascript">
//质控模块
function fnAddZkSamp(){
	$('#thisForm').attr('action','update4Data.do');
	$('#thisForm').ajaxSubmit(function(res) {
    	if(res.status=='success'){
    	   $('#id').val(res.object);
    		layer.open({
    			title:'添加质控样',	
    			type: 2,
    			area: ['600px', '400px'],
    			fix: false, //不固定
    			maxmin: true,
    			content: 'edit4Zk.do?id=${vo.id}',
    			btn: ['确定','取消'], //按钮
    			btn1: function(index, layero) {
    				var iframeWin = window[layero.find('iframe')[0]['name']];
    				var id=iframeWin.fnSelect();
    				if(id!=''){
    					location.assign('edit.do?id=${vo.id}')
    					parent.layer.msg('添加成功', {icon: 0,time: 3000})
    				}else{
    					parent.layer.msg('添加失败', {icon: 0,time: 3000})
    				}
    			}
    		});
        }else{
        	parent.layer.msg('添加失败', {icon: 0,time: 3000})
        }
	});

}
function deleteSamp(trId){
	$.ajax({ 
		url:"${basePath}/bus/itemTest/delete4Zk.do",
		data: {'id':trId},
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
function validtValueQ(obj,index){
	var ll=parseFloat($(obj).attr('key'));
	var vl=$(obj).val();
	if(!isNaN(ll) && !isNaN(vl)){
		$('#sl_'+index).val(ll*vl/1000000);
	}
	$.ajax({ 
		url:'checkValue.do?id=${vo.id}&value='+vl,
		dataType:"json",
		type:"post",
		async:false,
		success: function(data){
			 if(data.type=='error'){
				 $('#result_'+index).val('不合格');
			 }else{
				 $('#result_'+index).val('合格');
			 }
		},
		error:function(ajaxobj){  
			msg=ajaxobj;
	    }  
	});
}
function validtValue(obj,index){
	var vl=$(obj).val();
	$.ajax({ 
		url:'checkValue.do?id=${vo.id}&value='+vl,
		dataType:"json",
		type:"post",
		async:false,
		success: function(data){
			 if(data.type=='error'){
				 $('#result_'+index).val('不合格');
			 }else{
				 $('#result_'+index).val('合格');
			 }
		},
		error:function(ajaxobj){  
			msg=ajaxobj;
	    }  
	});
}

function fnBZPZ(id,itemId){
	 $('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status!='success'){
	        	parent.toastr.error(res.message, '');
	        	return false;
	        }else{
	        	parent.layer.open({
	        		title:'',	
	        		type: 2,
	        		area: ['100%', '100%'],
	        		fix: true,
	        		maxmin: false,
	        		content: '${basePath}bus/stantSolut/editBz.do?taskItemId='+id+'&itemId='+itemId,
	        		end: function () {
	                    location.reload();
	                }
	        	});
	        }
		});
}

</script>
</html>
