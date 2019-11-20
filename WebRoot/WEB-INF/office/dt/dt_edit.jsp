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
					<li><a href="javascript:backMainPage();">文件登记</a></li>
					<li><strong>编辑</strong>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<input type="hidden" id="filePath" name="filePath" value="${vo.filePath}">
					<input type="hidden" id="fileName" name="fileName" value="${vo.fileName}">
					  <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">上传文件:</label></td>
								<td class="width-35">
									<div class="input-group" style="width:100%;float: left;">
										<input id="file" name="file" class="form-control" placeholder="选择文件..." type="file" />
									</div>
									<!-- <a style="margin-left: 10px;" class="btn btn-info" type="button" onclick="fnOnline()">在线拍照</a> -->
								</td>
								<td class="width-15 active"><label class="pull-right">操作:</label></td>
								<td class="width-35">
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
									<input id="code" name="code" class="form-control required" validate="required" type="text" value="${vo.code }" />
								</td>
								<td class="width-15 active"><label class="pull-right">文件名称:</label></td>
								<td class="width-35">
									<input id="title" name="title" class="form-control required" validate="required" type="text" value="${vo.title }" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">文件来源:</label></td>
								<td>
									<input id="source" name="source" class="form-control" type="text" value="${vo.source }" />
								</td>
								<td class="active"><label class="pull-right">保密等级:</label></td>
								<td>
									<input id="dj" name="dj" class="form-control"type="text" value="${vo.dj }" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">文件目录:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" readonly="readonly" name="categoryVo.name" id="categoryName" class="form-control" placeholder="请选择存放路径" value="${vo.categoryVo.name}"> 
										<input type="hidden" id="categoryId" name="categoryVo.id" value="${vo.categoryVo.id}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectcategory()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">签&nbsp;&nbsp;发&nbsp;人:</label></td>
								<td>
									<div class="input-group">
										<input id="auditName" name="auditName" class="form-control required" validate="required" type="text" value="${vo.auditName}" onclick="fnSelectUser()"/>
										<input  id="auditId" name="auditId" type="hidden" value="${vo.auditId}" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
								<td class="active"><label class="pull-right">申请日期:</label></td>
								<td>
									<div class="input-group date">
		                           		<input id="date" name="date" class="form-control required datetimeISO" validate="required" type="text" value="${vo.date}" />
		                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="content" placeholder="内容" name="content">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea maxlength="128" rows="2" class="form-control" id="remark" name="remark">${vo.remark}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('save4Data.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('${fn:length(vo.id)>0? 'update4Data.do?isCommit=1':'add4Data.do?isCommit=1'}');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
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
		<%@ include file="../../sys/open/open_img.jsp"%>
		   <!-- Sweet alert -->
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>
		function formSubmit4Online(){
			$('#code').removeClass('required');
			$('#title').removeClass('required');
			$('#auditName').removeClass('required');
			
			$('#code').removeAttr('validate');
			$('#title').removeAttr('validate');
			$('#auditName').removeAttr('validate');
			
			$('#thisForm').attr('action','save4Data.do');
			$('#thisForm').submit();
		}
		function formSubmitSave(url){
			$('#thisForm').attr('action',url);
			$('#thisForm').submit();
		}
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
		    	})
			}
		}
	</script>
	<script>
	function fnSelectcategory() {
		layer.open({
			title : '路径选择',
			type : 2,
			area : [ '300px', '470px' ],
			fix : false, //不固定
			maxmin : true,
			content : '${basePath}doc/category/select.do?id='+ $('#categoryId').val(),
			btn : [ '确定', '取消' ], //按钮
			btn1 : function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#categoryId').val(data.id);
				$('#categoryName').val(data.name);
			}
		});
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
	$('input[type="file"]').prettyFile();
	$('input[type="file"]').on('change',function(){
		var f= $(this).get(0).files[0];
		var fn=f.name;
		fn=fn.substring(0,fn.lastIndexOf('.'));
		$('#title').val(fn);
	});
	function fnShowFile(){
		POBrowser.openWindow('${basePath}office/dt/open.do?id=${vo.id}','width=1200px;height=800px;');
	}
	function fnOnline(){
		var id='${vo.id}';
		layer.open({
			title:'在线拍照',	
			type: 2,
			area: ['800px', '450px'],
			fix: false, //不固定
			maxmin: true,
			content: 'online.do?id='+id
		});
	}
	</script>
</body>
</html>
