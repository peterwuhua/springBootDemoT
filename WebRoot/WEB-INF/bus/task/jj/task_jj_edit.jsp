<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<style type="text/css">
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
.form-control{
	padding: 4px;
}
.table > thead > tr > th{
    text-align: center;
}
.table > tbody > tr > td{
    padding: 2px;
}
.table > tbody > tr > td >input,.table > tbody > tr > td > div
,.table > tbody > tr > td > div>input,.table > tbody > tr > td > div>select{
  
}

</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><a>样品交接</a></li>
						<li><strong>编辑</strong></li>
					</ol>
				</h5>
			</div>
			<div class="ibox-content">
				<form method="post" action="update4Jj.do" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
						<tr>
							<td class="active"  style="width: 150px;"><label class="pull-right">任务编号:</label></td>
							<td>
								<a onclick="fnTask('${vo.id}','${vo.no}');">${vo.no}</a>
							</td>
							<td class="active"  style="width: 150px;"><label class="pull-right">单位名称:</label></td>
							<td>
								${vo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>
								${vo.sampName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">采样人员:</label></td>
							<td>
								${vo.cyName}
							</td>
							<td class="active"><label class="pull-right ">采样日期:</label></td>
							<td>
								${vo.cyDate}
								<c:if test="${vo.cyDate!=vo.cyEndDate && vo.cyEndDate!=null}">
									~${vo.cyEndDate}
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
								${vo.cyMsg}
							</td>
						</tr>
						<tr>
							<td colspan="4">样品信息</td>
						</tr>
					</table>
					 
					<div style="overflow-x: auto;">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;min-width: 1400px">
							 <thead>
							 	<tr>
							 		<th style="width: 45px;">序号</th>
							 		<th style="width: 90px;">样品名称</th>
							 		<th style="width: 130px;">样品编号</th>
							 		<th style="width: 80px;">采样时间</th>
							 		<th style="width: 10%;">性状描述</th>
							 		<!-- <th style="width: 100px;">样品状态</th> -->
							 		<th style="width: 100px;">固定剂</th>
							 		<th style="width: 10%;">保存位置</th>
							 		<th style="width: 65px;">保存<br>时间(h)</th>
							 		<th style="width: 65px;">培养<br>时间(h)</th>
							 		<th style="width: 100px;">保存条件</th>
							 		<th style="width: 50px;">是否<br>留样</th>
							 		<th style="min-width: 100px;">采样容器</th>
							 		<th>检测项目</th>
							 	</tr>
							 </thead>
							 <tbody id="samp_tb">
							 	<c:set var="num" value="0"/>
							 	<c:forEach items="${vo.cydList}" var="e" varStatus="v">
							 		<tr>
							 			<td colspan="12">
								 			检测点：${e.cyDate}-${e.pointName}  【<a href="javascript:void(0);" onclick="fnPoint('${e.id}','${e.sampName}')">采样单</a>】
								 		</td>
							 		</tr>
									<c:forEach items="${e.sampList}" var="e1" varStatus="v1">
										<tr>
									 		<td align="center">
									 			${v1.index+1}
									 			<input type="hidden" class="form-control" name="sampList[${num}].sort" value="${e1.sort}" >
									 			<input type="hidden" name="sampList[${num}].id" value="${e1.id}">
									 			<input id="sampTypeId_${num}" name="sampList[${num}].sampTypeId" value="${e1.sampTypeId}" type="hidden" />
									 		</td>
									 		<td>
									 			 ${e1.sampName}
									 		</td>
									 		<td>
									 			<input type="text" id="sampCode_${num}" class="form-control required" validate="required" name="sampList[${num}].sampCode" value="${e1.sampCode}" >
									 		</td>
									 		<td>
										 		<input type="text" class="form-control timeISO" name="sampList[${num}].cyTime" value="${e1.cyTime}">
									 		</td>
									 		<td>
									 			<div class="input-group" style="width: 100%">
										 			<textarea class="form-control" name="sampList[${num}].xz" >${e1.xz}</textarea>
										 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyTextareaVals(this);return false;"></span>
									 			</div>
									 		</td>
									 		<%-- <td>
									 			<div class="input-group" style="width: 100%">
										 			<textarea class="form-control" name="sampList[${num}].sampStatus" >${e1.sampStatus}</textarea>
										 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyTextareaVals(this);return false;"></span>
									 			</div>
									 		</td> --%>
									 		<td>
									 			<div class="input-group" style="width: 100%">
										 			<textarea class="form-control" name="sampList[${num}].tjj" >${e1.tjj}</textarea>
										 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyTextareaVals(this);return false;"></span>
									 			</div>
									 		</td>
									 		<td>
									 			<div class="input-group" style="width: 100%">
										 			<select class="form-control" name="sampList[${num}].saveAddress">
										 				<option value="">/</option>
										 				<c:forEach items="${saddrList}" var="e2">
										 					<c:choose>
											 					<c:when test="${e1.saveAddress==e2}">
											 						<option value="${e2}" selected="selected">${e2}</option>
											 					</c:when>
											 					<c:otherwise>
											 						<option value="${e2}">${e2}</option>
											 					</c:otherwise>
											 				</c:choose>
										 				</c:forEach>
										 			</select>
										 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copySel(this);return false;"></span>
									 			</div>
									 		</td>
									 		<td>
									 			<div class="input-group" style="width: 100%">
										 			<input type="text" class="form-control number" name="sampList[${num}].saveHour" value="${e1.saveHour}" placeholder="h">
										 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span>
									 			</div>
									 		</td>
									 		<td>
									 			<div class="input-group" style="width: 100%">
										 			<input type="text" class="form-control number" name="sampList[${num}].pyHour" value="${e1.pyHour}" placeholder="h">
										 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span>
									 			</div>
									 		</td>
									 		<td>
									 			<div class="input-group" style="width: 100%">
										 			<select class="form-control" name="sampList[${num}].saveRequest">
										 				<option value="">/</option>
										 				<c:forEach items="${srList}" var="e2">
										 					<c:choose>
											 					<c:when test="${e1.saveRequest==e2}">
											 						<option value="${e2}" selected="selected">${e2}</option>
											 					</c:when>
											 					<c:otherwise>
											 						<option value="${e2}">${e2}</option>
											 					</c:otherwise>
											 				</c:choose>
										 				</c:forEach>
										 			</select>
										 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copySel(this);return false;"></span>
									 			</div>
									 		</td>
									 		<td>
									 			<div class="input-group" style="width: 100%">
										 			<select class="form-control" name="sampList[${num}].ly">
										 				<c:choose>
										 					<c:when test="${e1.ly=='是'}">
										 						<option selected="selected">是</option>
										 						<option>否</option>
										 					</c:when>
										 					<c:otherwise>
										 						<option>是</option>
										 						<option selected="selected">否</option>
										 					</c:otherwise>
										 				</c:choose>
										 			</select>
									 			</div>
									 		</td>
									 		<td>
									 			<c:forEach items="${e1.containerList}" var="e2">
									 				${e2.container}
									 			</c:forEach>
									 		</td>
									 		<td>
									 			${e1.itemNames}
									 			<!-- <div class="input-group" style="width: 100%">
													<textarea id="itemNames_${num}" name="sampList[${num}].itemNames" class="form-control required" validate="required" onclick="chooseItem('${e.itemIds}','${num}','${e1.id}')">${e1.itemNames}</textarea>
													<input id="itemIds_${num}" name="sampList[${num}].itemIds" value="${e1.itemIds}" type="hidden" />
												</div> -->
									 		</td>
									 	</tr>
									 	<c:set var="num" value="${num+1}"/>
									</c:forEach>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">样品处理要求:</label></td>
							<td class="width-35" colspan="3">
								<div class="radio i-checks">
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="委托方自取" name="dealRequest" <c:if test="${vo.dealRequest=='委托方自取'}">checked</c:if>>
                                          </div>委托方自取
                                      </label>
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="委托检测机构进行销毁" name="dealRequest" <c:if test="${vo.dealRequest=='委托检测机构进行销毁'}">checked</c:if>>
                                          </div>委托检测机构进行销毁
                                      </label>
                                  </div>
							</td>
						</tr>
						<tr>
							<th class="active"><label class="pull-right">上传附件:</label></th>
							<td>
								<input type="file" name="files" multiple="multiple" class="form-control"/>
							</td>
							<td colspan="2" id="removeFile">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-info">${e.fileName}</a>
									 	<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">交接人员:</label></td>
							<td class="width-35">
								<input type="hidden" id="reciveUserId" name="reciveUserId" value="${vo.reciveUserId}">
								<input type="text" id="reciveUser" name="reciveUser" value="${vo.reciveUser}" class="form-control"  readonly="readonly">
							</td>
							<td class="width-15 active"><label class="pull-right">交接时间:</label></td>
							<td class="width-35">
								<div class="input-group date">
									<input type="text" id="reciveDate" name="reciveDate" class="form-control datetimeISO required" validate="required" value="${vo.reciveDate}">
									  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="reciveMsg" name="reciveMsg" maxlength="128">${vo.reciveMsg}</textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-info" type="button" onclick="fnPrint()"><i class="fa fa-check" aria-hidden="true"></i> 编号打印</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmitSave('update4Jj.do?isCommit=0')"><i class="fa fa-check" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-success" type="button" onclick="fnSubmit('update4Jj.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
							<a class="btn btn-w-m btn-danger" type="button" onclick="fnSubmitBack('updateBack.do?isCommit=-1')"><i class="fa fa-check" aria-hidden="true"></i> 退回</a>
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
	<script>
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
	function fnPoint(id,sampName){
		var url='/bus/taskXc/showCyd.do?id='+id;
		var sampType='${vo.sampType}';
		if(sampType!='环境'){
			url='/bus/taskXc/showCyd4Zw.do?id='+id;
		}
		parent.layer.open({
			title:'【'+sampName+'】采样单',	
			type: 2,
			area: ['80%','80%'],
			fix: false, //不固定
			maxmin: true,
			content: url
		});
	}
	function copySel(obj){
		obj=$(obj);
		var idValue=obj.closest('td').find('select').eq(0).val();
		var indexTr=obj.closest('td').closest('tr').index();
		var indexTd=obj.closest('td').index();
		$('#samp_tb tr:gt('+indexTr+')').each(function(){
			$(this).find('td').eq(indexTd).find('select').eq(0).val(idValue);
		});
	}
	function copyVals(obj){
		obj=$(obj);
		var idValue=obj.closest('td').find('input').eq(0).val();
		var indexTr=obj.closest('td').closest('tr').index();
		var indexTd=obj.closest('td').index();
		$('#samp_tb tr:gt('+indexTr+')').each(function(){
			$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
		});
	}
	function copyTextareaVals(obj){
		obj=$(obj);
		var idValue=obj.closest('td').find('textarea').eq(0).val();
		var indexTr=obj.closest('td').closest('tr').index();
		var indexTd=obj.closest('td').index();
		$('#samp_tb tr:gt('+indexTr+')').each(function(){
			$(this).find('td').eq(indexTd).find('textarea').eq(0).val(idValue);
		});
	}
	function fnSubmitSave(url){
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
	function fnSubmitBack(url){
		var reciveMsg=$('#reciveMsg').val();
		if(reciveMsg==''){
			parent.toastr.error('请在备注栏输入退回原因！', '');
			return false;
		}
		swal({
	        title: "您确定要退回该任务吗",
	        text: "提交后将无法修改，请谨慎操作！",
	        type: "success",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消"
	    }, function () {
	    	$('#thisForm').attr('action',url);
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
	/*function chooseItem(n){
		var itemIds=$('#itemIds_'+n).val();
		var sampId=$('#sampTypeId_'+n).val();
		layer.open({
			title:'检测项目',	
			type: 2,
			area: ['800px', '80%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/item/selects.do?ids='+itemIds+'&sampTypeIds='+sampId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#itemIds_'+n).val(data.id);
				$('#itemNames_'+n).val(data.name);
			}
		});
	}*/
	function chooseItem(allIds,n,samplingId){
		var id=$('#itemIds_'+n).val();
		//获取已选分包项目
		var	url='item_select.do?ids='+allIds+'&id='+id;
		layer.open({
			title:'项目选择',	
			type: 2,
			 area: ['800px', '450px'],
			fix: false, //不固定
			maxmin: true,
			content: url,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#itemIds_'+n).val(data.id.join(','));
				$('#itemNames_'+n).val(data.name.join(','));
				var sampCode=ajaxGetSampCode(samplingId,data.id.join(','));
				$('#sampCode_'+n).val(sampCode);
			}
		});
	}
	function ajaxGetSampCode(samplingId,itemIds){
		var code='';
		$.ajax({ 
			url:"${basePath}bus/taskAp/ajaxGetSampCode.do",
			data: {'id':samplingId,'ids':itemIds},
			async:false,
			success: function(data){
				code=data;
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }
		});
		return code;
	}
	/*function deleteSamp(obj,id){
		$.ajax({ 
			url:"${basePath}bus/sampling/deleteSamp.do?id="+id,
			dataType:"json",
			type:"post",
			success: function(data){
				parent.layer.msg(data.message, {icon: 0,time: 3000})
				if("success"==data.status){
					$(obj).parent().parent().remove();
				}
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }  
		});
	}
	function copySamp(id){
		 $('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    		$.ajax({ 
					url:"${basePath}bus/sampling/copySamp.do?id="+id,
					dataType:"json",
					type:"post",
					success: function(data){
						parent.layer.msg(data.message, {icon: 0,time: 3000})
						if("success"==data.status){
							location.reload()
						}
					},
					error:function(ajaxobj){  
						layer.msg(ajaxobj, {icon: 0,time: 3000});
				    }  
				});
	        }else{
	        	parent.toastr.error('拷贝异常！', '');
	        }
		});
	}*/
	$(function(){
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
		$('input[type="file"]').prettyFile();
	});
	//打印
	function fnPrint(){
		parent.layer.open({
			title:'条码打印',	
			type: 2,
			 area: ['800px', '500px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}bus/taskJj/print.do?id=${vo.id}'
		});
	}
	</script>
</body>
</html>
