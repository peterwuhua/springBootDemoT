<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>工作汇报</a></li>
					<li><strong>编辑</strong>
					</li>
				</ol>
			</div>
			<div class="ibox-content" style="padding: 5px 10px;">
				<form method="post" action="" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" id="id" value="${vo.id}" type="hidden" />
					</c:if>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">工作名称:</label></td>
								<td class="width-35">
								    <input  id="name" name="name" class="form-control required" validate="required" type="text" value="${vo.name}" />
								</td>
								<td class="width-15 active"><label class="pull-right">汇报部门:</label></td>
								<td class="width-35">
									<input  id="depart" name="depart" class="form-control " type="text" value="${vo.depart}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">工作内容:</label></td>
								<td colspan="3">
								   <textarea rows="2" maxlength="256" class="form-control" id="content" placeholder="工作内容" name="content">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">查&nbsp;&nbsp;阅&nbsp;人:</label></td>
								<td>
									<div class="input-group">
										<input type="text" id="viewer" name="viewer" class="form-control"  placeholder="请选择" value="${vo.viewer}" onclick="fnSelectViewer()"> 
										<input type="hidden" id="viewerId" name="viewerId" value="${vo.viewerId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectViewer()">选择</button>
										</div>
									 </div>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">汇报人员:</label></td>
								<td>
									<input  id="person" name="person" class="form-control " type="text" value="${vo.person}" />
									<input type="hidden" id="personId" name="personId" value="${vo.personId}" >
								</td>
								<td class="active"><label class="pull-right">汇报日期:</label></td>
								<td>
									<input  id="reportDate" name="reportDate" class="form-control " type="text" value="${vo.reportDate}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">附件上传:</label></td>
								<td>
								 	<input id="file" type="file" name="file" class="form-control"/>
								</td>
							</tr>
							<tr>
							    <td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							    <td colspan="3">
							    	<textarea maxlength="128" rows="3" class="form-control" id="remark" placeholder="备注" name="remark">${vo.remark}</textarea>
							    </td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
	                <div class="form-group">
	                    <div class="col-sm-12 col-sm-offset-1">
	                        <a class="btn btn-w-m btn-primary" href="javascript:void(0);"
	                           onclick="formSubmitSave('save4Data.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
	                        <a class="btn btn-w-m btn-success" href="javascript:void(0);"
	                           onclick="formSubmit('${fn:length(vo.id)>0? 'update4Data.do?isCommit=1':'add4Data.do?isCommit=1'}');"><i
	                                class="fa fa-mail-forward" aria-hidden="true"></i> 保存并提交</a>
	                        <a class="btn btn-w-m btn-white" href="javascript:backMainPage();">
	                        <i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
	                    </div>
	                </div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
        <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
	<script>
	function fnSelectViewer() {
		layer.open({
			title:'用户信息',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/selects.do',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var selectData = iframeWin.fnSelect();
			  $("#viewer").val(selectData.name);
			  $("#viewerId").val(selectData.id);
			}
		})
	}
	$(document).ready(function() {
		$('input[type="file"]').prettyFile();
	});
	function formSubmitSave(url){
		$('#thisForm').attr('action',url);
		$('#thisForm').submit()
	}
	function formSubmit(url){
		$('#thisForm').attr('action',url);
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
	
	function backMainPage(){
		 var basePath=location.href.substr(0,location.href.lastIndexOf("/")+1);
		 var currentIframe=$(window.parent.document).find(".J_iframe").filter( ":visible" );
		 var editPageKey=$(currentIframe).attr("data-id");
		 var mainPageKey=window.parent.pageMap.get(editPageKey);
		 window.parent.pageMap.remove(editPageKey);//移除map缓存
		 var mainIframe=$(window.parent.document).find('iframe[data-id="'+mainPageKey+'"]')[0];
		 $(mainIframe).attr("data-id",editPageKey);
		 $(mainIframe).css("display","inline");
		 var treeUrl=mainIframe.contentWindow.treeUrl;
		 if(!!treeUrl&&treeUrl!='undefined'){
			 mainIframe.contentWindow.reloadTree(basePath+treeUrl);
		 }
		 mainIframe.contentWindow.jqgridReload(); 
		 currentIframe.remove();
	}
	
	</script>
</body>
</html>