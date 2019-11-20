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
					<li><a href="javascript:backMainPage();">文件签发</a></li>
					<li><strong>编辑</strong>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">上传文件:</label></td>
								<td class="width-35" colspan="3">
									<c:if test="${fn:length(vo.id)>0}">
										<a href="download.do?filePath=${vo.filePath}&trueName=${vo.fileName}" class="btn btn-w-m btn-info">下载文件</a>
										<c:if test="${fn:contains(vo.type,'doc')||fn:contains(vo.type,'xls')||fn:contains(vo.type,'ppt')}">
											<a href="javascript:void(0);" onclick="fnShowFile();" class="btn btn-w-m btn-info">打开文件</a>
										</c:if>
										<c:if test="${vo.type eq '.pdf'}">
											<a href="javascript:openFile('${basePath}sys/open/open.do','${vo.filePath}','pdf');" class="btn btn-w-m btn-info">打开文件</a>
										</c:if>
										<c:if test="${fn:contains(vo.type,'jpg')||fn:contains(vo.type,'png')||fn:contains(vo.type,'jpeg')||fn:contains(vo.type,'gif')}">
											<a href="javascript:openImg('${vo.filePath}','${vo.fileName}');" class="btn btn-w-m btn-info">打开文件</a>
										</c:if>
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">文件编号:</label></td>
								<td class="width-35">
									${vo.code }
								</td>
								<td class="width-15 active"><label class="pull-right">文件名称:</label></td>
								<td class="width-35">
									${vo.title }
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">文件来源:</label></td>
								<td>
									${vo.source }
								</td>
								<td class="active"><label class="pull-right">保密等级:</label></td>
								<td>
									${vo.dj }
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">文件目录:</label></td>
								<td class="width-35">
									${vo.path}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">签&nbsp;&nbsp;发&nbsp;人:</label></td>
								<td>
									${vo.auditName}
								</td>
								<td class="active"><label class="pull-right">申请日期:</label></td>
								<td>
									${vo.date}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述:</label></td>
								<td colspan="3">
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
						</tbody>
					</table>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active"><label class="pull-right">审核意见:</label></td>
								<td>
									<input maxlength="128" class="form-control" id="auditCt" type="text" value="${vo.auditCt}" name="auditCt"/>
								</td>
								<td class="active"><label class="pull-right">下&nbsp;发&nbsp;&nbsp;人:</label></td>
								<td>
									<div class="input-group">
										<input id="sendName" name="sendName" class="form-control required" validate="required" type="text" value="${vo.sendName}" onclick="fnSelectUser()"/>
										<input  id="sendId" name="sendId" type="hidden" value="${vo.sendId}" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审&nbsp;&nbsp;核&nbsp;人:</label></td>
								<td>
									<input readonly="readonly" id="auditName" name="auditName" class="form-control " type="text" value="${vo.auditName}" />
									<input  id="auditId" name="auditId" type="hidden" value="${vo.auditId}" />
								</td>
								<td class="active"><label class="pull-right">审核日期:</label></td>
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
		<!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
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
			parent.toastr.error('请输入原因！', '');
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
		var idStr=$('#sendId').val();
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
				$('#sendId').val(data.id);
				$('#sendName').val(data['userVo.name']);
			}
		});
	}
	function fnShowFile(){
		POBrowser.openWindow('${basePath}office/dt/open.do?id=${vo.id}','width=1200px;height=800px;');
	}
	</script>
</body>
</html>
