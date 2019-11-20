<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../../include/css.jsp"%>
<%@ include file="../../../../include/status.jsp"%>
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>

<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">档案</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form id="thisForm" method="post"  class="form-horizontal" enctype="multipart/form-data">
						<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类:</label></td>
								<td class="width-35">
										${vo.archiveTypeVo.name} 
										<input type="hidden" id="selectId" name="archiveTypeVo.id" value="${vo.archiveTypeVo.id}">
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label></td>
								<td class="width-35">${vo.code}</td>
								<td class="width-15 active"><label class="pull-right">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</label></td>
								<td class="width-35">${vo.title}</td>
							</tr>
							<tr>
							 	<td class="width-15 active"><label class="pull-right">归&nbsp;&nbsp;档&nbsp;人:</label></td>
								<td class="width-35">
									${vo.userName}
									<input id="userId" name="userId" type="hidden" value="${vo.userId}" />
								</td>
								<td class="width-15 active"><label class="pull-right">归档时间:</label></td>
								<td class="width-35">${vo.time}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">说&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;明:</label></td>
								<td colspan="3">
									<textarea id="describtion" class="form-control" name="describtion" readonly="readonly">${vo.describtion}</textarea>
								</td>	
							</tr>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  style="margin-bottom: 0px;">
						<thead>
							<tr>
								<th width="50">序号</th>
								<th width="20%">文件分类</th>
								<th width="40%">文件名称</th>
								<th >操作</th>
							</tr>
						</thead>
						<tbody id="room">
							<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								<tr>
									<td>
										${v.index+1}
										<input type="hidden" name="fileList[${v.index}].id"  value="${e.id}">
									</td>
									<td>
										${e.sign}
									</td>
									<td>
			                               	${e.fileName}
									</td>
									<td>
	                                    <c:choose>
	                                        <c:when test="${fn:contains(e.fileName,'.doc')||fn:contains(e.fileName,'.xls')||fn:contains(e.fileName,'.ppt')||fn:contains(e.fileName,'.pdf')}">
	                                            <a href="javascript:void(0);" onclick="fnShowFile('${e.id}');"
	                                               class="btn btn-w-m btn-info">在线预览</a>
	                                        </c:when>
	                                        <c:when test="${fn:contains(e.fileName,'.jpg')||fn:contains(e.fileName,'.png')||fn:contains(e.fileName,'.jpeg')||fn:contains(e.fileName,'.gif')}">
	                                            <a href="javascript:openImg('${e.filePath}','${e.fileName}');"
	                                               class="btn btn-w-m btn-info">在线预览</a>
	                                        </c:when>
	                                    </c:choose>
	                                    <c:if test="${e.fileName !='' && e.fileName!=null}">
	                                    	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}"  class="btn btn-w-m btn-info">下载</a>
	                                    </c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="4">
									<div class="pull-left">
										<c:choose>
											<c:when test="${fn:length(vo.fileList)==0}">
											 暂无数据
											</c:when>
											<c:otherwise>
												共 ${fn:length(vo.fileList)}  个
											</c:otherwise>
										</c:choose>
									</div>
								</td>
							</tr>
						</tfoot>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
					 <tbody>
						 	<tr>
								<td class="active"><label class="pull-right">审核意见:</label></td>
								<td colspan="3">
									<textarea id="auditCt" maxlength="128" class="form-control" name="auditCt" readonly="readonly">${vo.auditCt}</textarea>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">审&nbsp;&nbsp;核&nbsp;人:</label></td>
								<td class="width-35">
										${vo.auditName}
										<input id="auditId" name="auditId" type="hidden" value="${vo.auditId}" />
								</td>
								<td class="active"><label class="pull-right">审核日期:</label></td>
								<td>
		                           		${vo.auditTime}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">确认意见:</label></td>
								<td>
									${vo.confirmCt}
								</td>
								<td class="active"><label class="pull-right">查&nbsp;&nbsp;阅&nbsp;人:</label></td>
								<td>
										${vo.viewer}
										<input  id="viewerId" name="viewerId" type="hidden" value="${vo.viewerId}" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">确&nbsp;&nbsp;认&nbsp;人:</label></td>
								<td class="width-35">
									${vo.confirm}
									<input id="confirmId" name="confirmId" type="hidden" value="${vo.confirmId}" />
								</td>
								<td class="active"><label class="pull-right">确认日期:</label></td>
								<td>
		                           		${vo.confirmTime}
								</td>
							</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../../include/js.jsp"%>
<!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
<%@ include file="../../../../sys/open/open_img.jsp"%>
<!-- Sweet alert -->
<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>
		$('input[type="file"]').prettyFile();
	   function fnShowFile(id) {
	        POBrowser.openWindow('${basePath}bus/archiveFile/open.do?id=' + id, 'width=1200px;height=800px;');
	    }
	   
		function fnSelectUser(){
			var idStr=$('#viewerId').val();
			layer.open({
				title:'选择查阅人',	
				type: 2,
				area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '/sys/account/user_select.do?id='+idStr,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
					  var iframeWin = window[layero.find('iframe')[0]['name']];
					  var data=iframeWin.fnSelect();
					$('#viewerId').val(data.id);
					$('#viewer').val(data['userVo.name']);
				}
			});
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
	</script>
</body>
</html>
