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
					<li><a>工作查阅</a></li>
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
								    <input  id="name" name="name" readonly="readonly" class="form-control" type="text" value="${vo.name}" />
								</td>
								<td class="width-15 active"><label class="pull-right">汇报部门:</label></td>
								<td class="width-35">
									<input  id="depart" name="depart" readonly="readonly" class="form-control " type="text" value="${vo.depart}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">工作内容:</label></td>
								<td colspan="3">
								   <textarea rows="2" class="form-control" readonly="readonly" id="content"  name="content">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">汇报人员:</label></td>
								<td>
									<input  id="person" name="person" class="form-control " readonly="readonly" type="text" value="${vo.person}" />
									<input type="hidden" id="personId" name="personId" value="${vo.personId}" >
								</td>
								<td class="active"><label class="pull-right">汇报日期:</label></td>
								<td>
									<input  id="reportDate" name="reportDate" readonly="readonly" class="form-control " type="text" value="${vo.reportDate}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">工作文件:</label></td>
								 	 <td colspan="3">
										 <c:if test="${fn:length(vo.filepath)>0}">
														<a href="download.do?filePath=${vo.filepath}&trueName=${vo.filename}" class="btn btn-w-m btn-info">下载文件</a>
										</c:if>
									</td>
							</tr>
														<tr>
							    <td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							    <td colspan="3">
							    	<textarea maxlength="128" rows="3" class="form-control" id="remark" readonly="readonly" name="remark">${vo.remark}</textarea>
							    </td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">查&nbsp;&nbsp;阅&nbsp;人:</label></td>
								<td>
										<input type="text" id="viewer" name="viewer" class="form-control"   value="${vo.viewer}" /> 
										<input type="hidden" id="viewerId" name="viewerId" value="${vo.viewerId}"/>
								</td>
								<td class="active"><label class="pull-right">查阅日期:</label></td>
								<td>
									<input type="text" id="viewDate" name="viewDate" class="form-control"   value="${vo.viewDate}" />
								</td>
							</tr>
							<tr>
							    <td class="active"><label class="pull-right">反馈内容:</label></td>
							    <td colspan="3">
							    	<textarea rows="3" class="form-control" id="fkContent" placeholder="反馈内容" name="fkContent" maxlength="128">${vo.fkContent}</textarea>
							    </td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
	                <div class="form-group">
	                    <div class="col-sm-12 col-sm-offset-1">
	                        <a class="btn btn-w-m btn-success" href="javascript:void(0);"
	                           onclick="formSubmit('${fn:length(vo.id)>0? 'update4Data.do?isCommit=1':'add4Data.do?isCommit=1'}');"><i
	                                class="fa fa-mail-forward" aria-hidden="true"></i> 提交</a>
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