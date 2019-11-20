<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
</head>

<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">档案</a></li>
					<li><strong>查看</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}" class="form-horizontal" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">分类:</label></td>
								<td class="width-35" colspan="3">
									${vo.archiveTypeVo.name}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">编号:</label></td>
								<td class="width-35">${vo.code}</td>
								<td class="width-15 active"><label class="pull-right">名称:</label></td>
								<td class="width-35">${vo.title}</td>
							</tr>
							<tr>
							 	<td class="width-15 active"><label class="pull-right">归档人:</label></td>
								<td class="width-35">
									${vo.userName}
								</td>
								<td class="width-15 active"><label class="pull-right">归档时间:</label></td>
								<td class="width-35">${vo.time}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">说明:</label></td>
								<td colspan="3">
									${vo.describtion}
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
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a href="javascript:backMainPage();" class="btn btn-w-m btn-white"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
		<!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
	<%@ include file="../../../sys/open/open_img.jsp"%>
	<script>
		function fnSave() {
			$('form').attr('action', 'save4Data.do');
			$('form').submit();
		}

		function fnSelectcategory() {
			var pId=$('#selectId').val();
			layer.open({
				title : '文件夹选择',
				type : 2,
				area : [ '300px', '470px' ],
				fix : false, //不固定
				maxmin : true,
				content : '${basePath}doc/category/select.do?id='+ pId,
				btn : [ '确定', '取消' ], //按钮
				btn1 : function(index, layero) {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var data=iframeWin.fnSelect();
					$('#selectId').val(data.id);
					$('#selectName').val(data.name);
				}
			});
		}
	</script>
	<script>
		$('input[type="file"]').prettyFile();
		 function fnShowFile(id) {
		        POBrowser.openWindow('${basePath}bus/archiveFile/open.do?id=' + id, 'width=1200px;height=800px;');
		    }
	</script>
</body>
</html>
