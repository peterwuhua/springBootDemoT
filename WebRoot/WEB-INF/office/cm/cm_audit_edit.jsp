<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">行政审批</a></li>
					<li><strong>编辑</strong>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<c:if test="${fn:length(vo.code)>0}">
						<input name="code" value="${vo.code}" type="hidden" />
					</c:if>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label></td>
								<td class="width-35">
									<input id="type" name="type" class="form-control " readonly="readonly" type="text" value="${vo.type }" />
								</td>
								<td class="width-15 active"><label class="pull-right">编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label></td>
								<td class="width-35">
									<input id="code" name="code" class="form-control " readonly="readonly" type="text" value="${vo.code }" />
								</td>
							</tr>
							<!-- <tr>
								<td class="active"><label class="pull-right">开始时间:</label></td>
								<td>
									<div class="input-group date" >
										<input id="beginTime" name="beginTime"  class="form-control  datetimeISO" readonly="readonly" type="text" value="${vo.beginTime }" /> 
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span> 
									</div> 
								</td>
								<td class="active"><label class="pull-right">结束时间:</label></td>
								<td>
									<div class="input-group date" >
										 <input id="endTime" name="endTime"  class="form-control  datetimeISO" readonly="readonly" type="text" value="${vo.endTime }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span> 
									</div> 
								</td>
							</tr> -->
							<tr>
								<td class="active"><label class="pull-right">成&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本:</label></td>
								<td>
									<input id="price" name="price" class="form-control number" readonly="readonly" type="text" value="${vo.price }" />
								</td>
								<td class="active"><label class="pull-right">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时:</label></td>
								<td>
									<input id="hours" name="hours" class="form-control number" readonly="readonly" type="text" value="${vo.hours }" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="content" readonly="readonly" placeholder="内容" name="content">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">参与人员:</label></td>
								<td>
									${vo.userNames}
								</td>
								<td class="active"><label class="pull-right">申请日期:</label></td>
								<td>
									${vo.sdate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
							<tr>
								<th class="active"><label class="pull-right">上传附件:</label></th>
								<td colspan="3" id="removeFile">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
									 	<div style="float: left;margin-right: 10px;">
										 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-info">${e.fileName}</a>
									 	</div>
									 </c:forEach>
								 </td>
							 </tr>
						</tbody>
					</table>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active"><label class="pull-right">审批意见:</label></td>
								<td>
									<input maxlength="128" class="form-control" id="auditCt" type="text" value="${vo.auditCt}" name="auditCt"/>
								</td>
								<td class="active"><label class="pull-right">抄&nbsp;&nbsp;送&nbsp;人:</label></td>
								<td>
									<div class="input-group">
										<input id="viewNames" name="viewNames" class="form-control required" validate="required" type="text" value="${vo.viewNames}" onclick="fnSelectUsers()"/>
										<input id="viewIds" name="viewIds" type="hidden" value="${vo.viewIds}" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUsers()">选择 (多选)</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审&nbsp;&nbsp;批&nbsp;人:</label></td>
								<td>
									<div class="input-group">
										<input id="auditName" name="auditName" class="form-control "  type="text" value="${vo.auditName}" onclick="fnSelectUser()"/>
										<input  id="auditId" name="auditId" type="hidden" value="${vo.auditId}" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
								<td class="active"><label class="pull-right">审批日期:</label></td>
								<td>
									<div class="input-group date">
		                           		<input id="auditTime" name="auditTime" class="form-control required datetimeISO" validate="required" type="text" value="${vo.auditTime}" />
		                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('updateData.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 通过</a>
							<a class="btn btn-w-m btn-danger" href="javascript:;" onclick="formSubmit4Back('updateData.do?isCommit=-1');"><i class="fa fa-check" aria-hidden="true"></i> 退回</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
		   <!-- Sweet alert -->
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>
	function formSubmit(url){
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
			swal({
		        title: "您确定要提交吗",
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
	function formSubmit4Back(url){
		var auditCt=$('#auditCt').val();
		if(auditCt==''){
			parent.toastr.error('请在审批意见栏输入退回原因！', '');
			return false;
		}
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
			swal({
		        title: "您确定要退回该任务吗",
		        text: "提交后将无法修改，请谨慎操作！",
		        type: "warning",
		        showCancelButton: true,
		        confirmButtonColor: "#DD6B55",
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
	function fnSelectUser(){
		var idStr=$('#auditId').val();
		layer.open({
		
			title:'选择审批人',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/sys/account/user_select.do?id='+idStr,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelect();
				$('#auditId').val(data.id);
				$('#auditName').val(data['userVo.name']);
			}
		});
	}
	function fnSelectUsers(){
		var idStr=$('#viewIds').val();
		layer.open({
			title:'选择抄送人',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/sys/account/user_selects.do?ids='+idStr,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelect();
				$('#viewIds').val(data.id);
				$('#viewNames').val(data.name);
			}
		});
	}
	
	</script>
</body>
</html>
