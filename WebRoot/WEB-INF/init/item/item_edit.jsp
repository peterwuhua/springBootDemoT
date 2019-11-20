<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
 
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">监测项目</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}" class="form-horizontal" id="listForm" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">项目名称:</label></td>
							<td class="width-35">
								<input id="name" name="name" class="form-control required" validate="required" maxlength=64 placeholder="监测项目" type="text" value="${vo.name}" />
							</td>
							<td class="active"><label class="pull-right">样品类别:</label></td>
							<td>
								<div class="input-group">
									<input type="text" id="sampTypeNames" name="sampTypeNames" class="form-control required" validate="required" placeholder="请选择" value="${vo.sampTypeNames}" onclick="fnSelectSampType()">
									<input type="hidden" id="sampTypeIds"  name="sampTypeIds" value="${vo.sampTypeIds}">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectSampType()">选择</button>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">计量单位:</label></td>
							<td>
								 <input id="unit" name="unit" class="form-control" maxlength=12 type="text" value="${vo.unit}" placeholder="计量单位"/>
							</td>
							<td class="active"><label class="pull-right">现场检测:</label></td>
							<td class="width-35" >
								<label class="checkbox-inline i-checks " >
									<div class="iradio_square-green" >
										<input type="radio"  name="isNow" value="是" <c:if test="${vo.isNow=='是'}">checked="checked"</c:if>>
									</div>是
								</label>
								<label class="checkbox-inline i-checks " >
									<div class="iradio_square-green" >
										<input type="radio"  name="isNow" value="否" <c:if test="${vo.isNow!='是'}">checked="checked"</c:if>>
									</div>否
								</label>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">监测费用:</label></td>
							<td>
								 <input id="price" name="price" class="form-control number" maxlength=12 type="text" value="${vo.price}" />
							</td>
							 <td class="active"><label class="pull-right">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label></td>
							<td><input id="sort" class="form-control digits" name="sort" value="${vo.sort}"/></td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">样品标识:</label></td>
							<td>
								 <input id="code" name="code" class="form-control" maxlength=16 type="text" value="${vo.code}" placeholder="项目在样品下的编号标识"/>
							</td>
							<td class="active"><label class="pull-right">作业指导书:</label></td>
								<td>
									<div class="input-group">
										<input name="fileName" value="${vo.fileName}" id="fileName" type="hidden"/> 
										<input name="filePath" value="${vo.filePath}" id="filePath" type="hidden" />
										<input name="fileId" value="${vo.fileId}" id="fileId" type="hidden" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectZds()">选择</button>
										</div>
										<div id="showFile" style="margin: 10px 0px 0px 10px;">
											<c:if test="${vo.fileName!=''}">
												<c:choose>
													<c:when test="${fn:contains(vo.filePath,'.doc')||fn:contains(vo.filePath,'.xls')}">
														<a href="javascript:fnOpenWord('${vo.fileId}');" >${vo.fileName}</a>
													</c:when>
													<c:when test="${fn:contains(vo.filePath,'.pdf')}">
														<a href="javascript:openFile('${basePath}sys/open/open.do','${vo.filePath}','pdf');" >${vo.fileName}</a>
													</c:when>
												</c:choose>
											</c:if>
										</div>
									</div>
								</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">限值范围:</label></td>
							<td >
								<div class="input-group">
									<span class="input-group-addon">下限</span>
									 <input id="minVal" style="width: 100px" placeholder="上限" class="form-control"  name="minVal" type="text" value="${vo.minVal}" />
									 <span class="input-group-addon">上限</span>
									 <input id="maxVal" style="width: 100px" placeholder="下限" class="form-control"  name="maxVal" type="text" value="${vo.maxVal}" />
								</div>
							</td>
						</tr>
						<!-- <tr>
							<td class="active"><label class="pull-right">容&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;器:</label></td>
							<td>
								<div class="input-group">
									<input type="text" id="rqNames" name="rqNames" class="form-control" placeholder="监测该项目的样品使用容器" value="${vo.rqNames}" onclick="fnSelectRq()">
									<input type="hidden" id="rqIds"  name="rqIds" value="${vo.rqIds}">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectRq()">选择</button>
									</div>
								</div>
							</td>
							 <td class="active"><label class="pull-right">保存期限:</label></td>
							<td><input id="saveHour" class="form-control digits" name="saveHour" value="${vo.saveHour}" placeholder="监测该项目的样品保存时间（小时）"/></td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">上传模板:</label></td>
							<td>
								<div style="width: 250px;float: left;">
									<input type="file" name="file" multiple="multiple" class="form-control" accept=".xls"/>
								</div>
								<div style="margin-top: 8px;">(<font color="red">限.xls文件</font>)</div>
							</td>
							<td colspan="2" id="removeFile">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
									 	<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
								 	</div>
								 </c:forEach>
							</td>
						</tr> -->
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea maxlength="128" id="remark" name="remark" class="form-control">${vo.remark}</textarea>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								子项
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<table id="methodTable" class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
									 <thead>
									 	<tr>
									 		<th width="60">序号</th>
									 		<th width="200">名称</th>
									 		<th width="100">编号</th>
									 		<th>描述</th>
									 		<th width="50" style="text-align: center;"><button onclick="addSubItem()" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i> 添加</button></th>
									 	</tr>
									 </thead>
									<tbody id="sub_item">
										<c:forEach items="${vo.subList}" var="e" varStatus="s">
											<tr index="${s.index}">
										 		<td>
										 			${s.index+1}
										 			<input type="hidden" name="subList[${s.index}].id" value="${e.id}">
										 		</td>
										 		<td>
										 			<input type="text" name="subList[${s.index}].name" value="${e.name}"  class="form-control required" validate="required">
										 		</td>
										 		<td>
										 			<input type="text" name="subList[${s.index}].code" value="${e.code}"  class="form-control">
										 		</td>
										 		<td>
										 			<input maxlength="128" type="text" name="subList[${s.index}].remark" value="${e.remark}"  class="form-control">
										 		</td>
										 		<td align="center"><a  href="javascript:;" onclick="deleteOne('${e.id}',this);"><font color="red">删除</font></a></td>
										 	</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								方法标准
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<table id="methodTable" class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
									 <thead>
									 	<tr>
									 		<th width="60">序号</th>
									 		<th width="150">标准编号</th>
									 		<th width="100">条款</th>
									 		<th>方法名称</th>
									 		<th width="50"><button onclick="fnSelectMethod()" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i> 新增</button></th>
									 	</tr>
									 </thead>
									<tbody id="classify_item">
										<c:forEach items="${vo.methodList}" var="e" varStatus="s">
											<tr id="${e.id}">
										 		<td>
										 			${s.index+1}
										 			<input type="hidden" name="methodList[${s.index}].id" value="${e.id}">
										 			<input type="hidden" id="methodVoId${s.index}" name="methodList[${s.index}].methodVo.id" value="${e.methodVo.id}">
										 		</td>
										 		<td>${e.methodVo.code}</td>
										 		<td>${e.methodVo.chapter}</td>
										 		<td>${e.methodVo.name}</td>
										 		<td align="center"><a  href="javascript:;" onclick="deleteThis('${e.id}');"><font color="red">删除</font></a></td>
										 	</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
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
	  <!--PageOffice.js -->
     <script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
	<script>
	$('input[type="file"]').prettyFile();
	function fnOpenWord(id){
		POBrowser.openWindow('${basePath}doc/document/open.do?id='+id,'width=1200px;height=800px;');
	}
	function fnSelectZds(){
		var fileId=$('#fileId').val();
		layer.open({
			title:'原始记录模板',	
			type: 2,
			area: ['800px','75%'],
			maxmin: true,
			content: 'doc_select.do?id='+fileId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#fileId').val(data.id);
				$('#fileName').val(data.name);
				$('#filePath').val(data.relativePath);
				 $('#listForm').ajaxSubmit(function(res) {
				    	if(res.status=='success'){
				    	    parent.toastr.success(res.message, '');
					        location.reload();
				        }else{
				        	parent.toastr.error(res.message, '');
				        }
					});
			}
		});
	}
	function removeFile(){
		 $("#removeFile").html("");
		 $("#filePath").val("");
		 $("#fileName").val("");
	}
	function deleteOne(id,obj){
		if(id==''){
			$(obj).parent().parent().remove();
		}else{
			layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$.ajax({ 
					url:"${basePath}init/item/update4del.do",
					data: {'ids':id},
					async:false,
					success: function(data){
						if("success"==data.status){
							layer.msg(data.message, {icon: 0,time: 3000});
							$(obj).parent().parent().remove();
						}else{
							layer.msg(data.message, {icon: 0,time: 3000});
						}
					},
					error:function(ajaxobj){  
						layer.msg(ajaxobj, {icon: 0,time: 3000});
				    }
				});
			});
		}
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
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
	});
	function addSubItem(){
		var id='${vo.id}';
		if(id==''){
			layer.msg('请先保存参数信息', {icon: 0,time: 3000});
			return true;
		}
		var num=$('#sub_item').find('tr').length;
		var index=0;
		if(num>0){
			index=parseInt($('#sub_item').find('tr').eq(num-1).attr('index'));
			index++;
		}
		$('#sub_item').append($('<tr index='+index+'>')
				.append('<td>'+(index+1)+'</td>')
				.append('<td><input type="text" name="subList['+index+'].name" value=""  class="form-control required" validate="required"></td>')
				.append('<td><input type="text" name="subList['+index+'].code" value=""  class="form-control"></td>')
				.append('<td><input type="text" name="subList['+index+'].remark" class="form-control" maxlength="128"></td>')
				.append('<td><a  href="javascript:;" onclick="deleteOne(\'\',this);"><font color="red">删除</font></a></td>'));
	}
	function fnSelectSampType(){
		var sampTypeId=$('#sampTypeIds').val();
		layer.open({
			title:'样品类别',	
			type: 2,
			area: ['300px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/sampType/select.do?id='+sampTypeId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#sampTypeIds').val(data.id);
			  $('#sampTypeNames').val(data.name);
			}
		});
	}
	function fnSelectMethod(){
		var id='${vo.id}';
		if(id==''){
			layer.msg('请先保存项目信息', {icon: 0,time: 3000});
			return true;
		}
		var methodId="";
		$('input[id^=methodVoId]').each(function(){
			methodId+=$(this).val()+",";
		});
		layer.open({
			title:'检测方法',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/itemMethod/select.do?itemVo.id='+id+'&methodVo.id='+methodId,
			btn: ['确定','取消'], //按钮
			yes: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  iframeWin.fnSelectM();
			}
		});
	}
	function deleteThis(id){
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			$.ajax({ 
				url:"${basePath}init/itemMethod/deleteOne.do",
				data: {'id':id},
				async:false,
				success: function(data){
					if("success"==data.status){
						layer.msg(data.message, {icon: 0,time: 3000});
						$('#classify_item').find('tr').each(function(){
							if($(this).attr('id')==id){
								$(this).remove();
							}
						});
					}else{
						layer.msg(data.message, {icon: 0,time: 3000});
					}
				},
				error:function(ajaxobj){  
					layer.msg(ajaxobj, {icon: 0,time: 3000});
			    }
			});
		});
	}
</script>
</body>
</html>
