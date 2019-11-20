<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
.panel-heading{
padding: 0px;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">仪器设备</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}" class="form-horizontal" enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">设备编号:</label></th>
								<td class="width-35"><input id="no" class="form-control required" validate="required" name="no" type="text" value="${vo.no}" /></td>
								<td class="active"><label class="pull-right">仪器名称:</label></td>
								<td><input name="name" value="${vo.name}" id="name" type="text" class="form-control required"  validate="required"/></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">规格/型号:</label></td>
								<td><input name="spec" value="${vo.spec}" id="spec" type="text" class="form-control" /></td>
								<td class="active"><label class="pull-right">不确定度:</label></td>
								<td><input name="accuracy" value="${vo.accuracy}" id="accuracy" type="text" class="form-control" />
							</tr>
							<tr>
								<td class="active"><label class="pull-right">测量范围:</label></td>
								<td><input name="measureRange" value="${vo.measureRange}" id="measureRange" type="text" class="form-control" /></td>
								<td class="active"><label class="pull-right">放置地点:</label></td>
								<td><input name="ext01" value="${vo.ext01}" id="ext01" type="text" class="form-control" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">制造厂商:</label></td>
								<td><input name="producer" type="text" value="${vo.producer}" class="form-control" id="producer" /></td>
								<td class="active"><label class="pull-right">出厂编号:</label></td>
								<td><input name="code" value="${vo.code}" id="code" type="text" class="form-control" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">出厂日期:</label></td>
								<td>
									<div class="input-group date">
										<input id="productDate" name="productDate" class="form-control dateISO" type="text" value="${vo.productDate}" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
								<td class="active"><label class="pull-right">厂家电话:</label></td>
								<td>
									<input id="producerPhone" name="producerPhone" class="form-control" type="text" value="${vo.producerPhone}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">购置日期:</label></td>
								<td>
									<div class="input-group date">
										<input id="purTime" name="purTime" class="form-control dateISO" type="text" value="${vo.purTime}" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
								<td class="active"><label class="pull-right">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</label></td>
								<td>
									<select name="state" id="state" class="input-sm form-control input-s-sm inline">
									</select>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">检定周期 (月):</label></td>
								<td><input id="verificationCycle" name="verificationCycle" value="${vo.verificationCycle}" placeholder="0" type="text" class="form-control digits number " /></td>
								<td class="active"><label class="pull-right">上次检定日期:</label></td>
								<td>
									<div class="input-group date">
										<input id="lastverificationDate" name="lastverificationDate" value="${vo.lastverificationDate}" type="text" class="form-control dateISO"/>
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">有效期至:</label></td>
								<td>
									<div class="input-group date">
										<input id="verificationDate" name="verificationDate" value="${vo.verificationDate}" type="text" class="form-control dateISO"/>
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
								<td class="active"><label class="pull-right">检定单位:</label></td>
								<td>
									<input id="verificationUnit" name="verificationUnit" value="${vo.verificationUnit}" type="text" class="form-control"/>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">证书编号:</label></td>
								<td>
									<input id="verificationResult" name="verificationResult" value="${vo.verificationResult}" type="text" class="form-control"/>
								</td>
								<td class="active"><label class="pull-right">操作指导书:</label></td>
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
								<td class="active"><label class="pull-right">校准周期 (月):</label></td>
								<td><input id="calibrationCycle" name="calibrationCycle" value="${vo.calibrationCycle}" placeholder="0" type="text" class="form-control digits number " /></td>
								<td class="active"><label class="pull-right">上次校准日期:</label></td>
								<td>
									<div class="input-group date">
										<input id="lastcalibrationDate" name="lastcalibrationDate" value="${vo.lastcalibrationDate}" type="text" class="form-control dateISO"/>
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">保管科室:</label></td>
								<td>
									<div class="input-group">
										<input name="deptName" value="${vo.deptName}" id="deptName" placeholder="请选择科室" type="text"  class="form-control" onclick="fnSelectOrg()" /> 
										<input name="deptId" value="${vo.deptId}" id="deptId" type="hidden" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectOrg()">选择</button>
										</div>
									</div>
								</td>
								<td class="active"><label class="pull-right">仪器保管人:</label></td>
								<td>
									<div class="input-group">
										<input name="keeper" id="keeper" value="${vo.keeper}" placeholder="请选择保管人" type="text"  class="form-control" onclick="fnSelectUser()" /> 
										<input name="keepId" id="keepId" value="${vo.keepId}" type="hidden" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">附件上传:</label></td>
								<td><input type="file" name="files" multiple="multiple" class="form-control"></td>
								<td colspan="2"><c:forEach items="${vo.fileList}" var="e" varStatus="v">
										<div style="float: left; margin-right: 10px;">
											<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a> <a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
										</div>
									</c:forEach></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">仪器图片:</label></td>
								<td><input type="file" name="file" class="form-control" accept="image/jpeg,image/gif,image/png"></td>
								<td colspan="2">
									<c:if test="${fn:length(vo.logo)>0}">
										<img alt="logo" src="${basePath}${vo.logo}" style="width: 100px;width: 100px;">
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3"><textarea rows="4" cols="60" placeholder="请输入备注" name="comment" class="form-control">${vo.comment}</textarea></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formsubmit2Save('save4Data.do');">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formsubmit();">
								<i class="fa fa-check" aria-hidden="true"></i> 保存并返回
							</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	  <!--PageOffice.js -->
     <script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
	<script type="text/javascript">
	$( 'input[type="file"]' ).prettyFile();
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
				 $('#thisForm').ajaxSubmit(function(res) {
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
	function stateSelect() {
		var state='${vo.state}';
		$.ajax({
			url: '${basePath}sys/code/ajaxCodeContent.do?code=res-state',
			datatype: "json",
			success: function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					if(state==value[i]){
						optionstring += "<option value=\"" + value[i] + "\"  selected=\"selected\">"+ value[i] + "</option>";
					}else{
						optionstring += "<option value=\"" + value[i] + "\" >"+ value[i] + "</option>";
					}
				}
				$("#state").html(optionstring);
			}
		});
	}
	function typeSelect() {
		var typeId='${vo.typeId}';
		$.ajax({
			url: '${basePath}sys/code/ajaxCodeContent.do?code=res-yqtype',
			datatype: "json",
			success: function(data) {
				var value = data.split(",");
				var optionstring = "<option value=\"\" >-请选择-</option>";
				for (var i = 0; i < value.length; i++) {
					if(typeId==value[i]){
						optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"+ value[i] + "</option>";
					}else{
						optionstring += "<option value=\"" + value[i] + "\" >"+ value[i] + "</option>";
					}
					
				}
				$("#typeId").html(optionstring);
			}
		});
	}
	function formsubmit2Save(url){
		if(!!$("#lastverificationDate").val()){
			if(!$("#verificationCycle").val()){
				layer.msg('请填写检定周期', {icon: 0,time: 3000});
				return false;
			}
		}
		if(!!$("#lastcalibrationDate").val()){
			if(!$("#calibrationCycle").val()){
				layer.msg('请填写校准周期', {icon: 0,time: 3000});
				return false;
			}
		}
		if(url.length>0){
			$("form").attr('action',url);
			$("form").submit();
		}else{
			layer.alert('传入的url有误!', {icon: 2,title:'系统提示',shadeClose: true});
		}
	}
	function formsubmit(){
		if(!!$("#lastverificationDate").val()){
			if(!$("#verificationCycle").val()){
				parent.toastr.error('请填写检定周期', '');
				return false;
			}
		}
		if(!!$("#lastcalibrationDate").val()){
			if(!$("#calibrationCycle").val()){
				parent.toastr.error('请填写校准周期', '');
				return false;
			}
		}
		var b = $("#thisForm").FormValidate();
		if(b){
			 $('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
			        backMainPage();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	}
	$(function(){
		typeSelect();
		stateSelect();
	});
	
	function fnSelectOrg(){
		var pId=$('#deptId').val();
		var pName=$('#deptName').val();
			layer.open({
				title:'部门选择',	
				type: 2,
				area: ['350px', '75%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}sys/org/select.do?id='+pId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				   var data=iframeWin.fnSelect();
	  				$('#deptId').val(data.id);
	 				 $('#deptName').val(data.name);
				}
			});
	}
	function fnSelectUser(){
		var keepId=$('#keepId').val();
		var deptId=$('#deptId').val();
		if(deptId==''){
			parent.toastr.error('请选择保管科室', '');
			return false;
		}
		layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'user_select.do?keepId='+keepId+'&deptId='+deptId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#keepId').val(data.id);
				$('#keeper').val(data.name);
			}
		});
	}
    </script>
</body>
</html>
