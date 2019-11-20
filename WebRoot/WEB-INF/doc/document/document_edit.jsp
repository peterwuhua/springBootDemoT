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
					<li><a href="javascript:backMainPage();">文件</a></li>
					<li><strong>编辑</strong></li>
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
								<c:if test="${fn:length(vo.relativePath)>0}">
									<td class="active"><label class="pull-right">文件上传:</label></td>
									<td class="width-35"><input id="file" name="file" class="form-control" placeholder="内容" type="file" /></td>
									<td class="width-15 active"><label class="pull-right">操作:</label></td>
									<td class="width-35">
										<c:if test="${fn:length(vo.id)>0}">
											<a href="download.do?filePath=${vo.relativePath}&trueName=${vo.name}${vo.type}" class="btn btn-w-m btn-info">下载文件</a>
											<c:if test="${fn:contains(vo.type,'doc')||fn:contains(vo.type,'xls')||fn:contains(vo.type,'ppt')}">
												<a href="javascript:void(0);" onclick="fnShowFile();" class="btn btn-w-m btn-info">打开文件</a>
											</c:if>
											<c:if test="${vo.type eq '.pdf'}">
												<a href="javascript:openFile('${basePath}sys/open/open.do','${vo.relativePath}','pdf');" class="btn btn-w-m btn-info">打开文件</a>
											</c:if>
											<c:if test="${fn:contains(vo.type,'jpg')||fn:contains(vo.type,'png')||fn:contains(vo.type,'jpeg')||fn:contains(vo.type,'gif')}">
												<a href="javascript:openImg('${vo.relativePath}','${vo.name}');" class="btn btn-w-m btn-info">打开文件</a>
											</c:if>
										</c:if>
									</td>
								</c:if>
								<c:if test="${fn:length(vo.relativePath)==0 && fn:length(vo.id)>0}">
									<td class="active"><label class="pull-right">文件上传:</label></td>
									<td class="width-35"><input id="file" name="file" class="form-control" placeholder="内容" type="file" /></td>
									<td class="width-15 active"><label class="pull-right">操作:</label></td>
									<td class="width-35"><strong style="color: red;">未上传文件</strong></td>
								</c:if>
								<c:if test="${fn:length(vo.relativePath)==0 && fn:length(vo.id)==0}">
									<td class="active"><label class="pull-right">文件上传:</label></td>
									<td class="width-35"><input id="file" name="file" class="form-control" placeholder="内容" type="file" /></td>
									<td colspan="3"></td>
								</c:if>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">所属文件夹:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" readonly="readonly" id="selectName" class="form-control" placeholder="请选择存放路径" value="${vo.categoryVo.name}"> 
										<input type="hidden" id="selectId" name="categoryVo.id" value="${vo.categoryVo.id}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectcategory()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">名称:</label></td>
								<td class="width-35"><input id="name" placeholder="请输入文件名称" class="form-control required" validate="required" name="name" type="text" value="${vo.name}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">标题:</label></td>
								<td class="width-35"><input id="title" placeholder="请输入文件标题" class="form-control" name="title" type="text" value="${vo.title}" /></td>
								<td class="width-15 active"><label class="pull-right">标记:</label></td>
								<td class="width-35"><input id="sign" placeholder="请输入文件标记" class="form-control" name="sign" type="text" value="${vo.sign}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">是否所有人可见:</label></td>
								<td class="width-35">
									<select class="form-control" name="isAllVisible" id="isAllVisible" value="${vo.isAllVisible}">
										<c:choose>
											<c:when test="${'是' eq vo.isAllVisible}">
												<option value="是" selected="selected">是</option>
												<option value="否">否</option>
											</c:when>
											<c:otherwise>
												<option value="是">是</option>
												<option value="否" selected="selected">否</option>
											</c:otherwise>
										</c:choose>
									</select>
								</td>
								<td class="width-15 active"><label class="pull-right">顺序:</label></td>
								<td class="width-35"><input id="sort" class="form-control digits" name="sort" type="text" value="${vo.sort}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">说明:</label></td>
								<td colspan="3">
									<textarea id="describtion" placeholder="请输入文件说明" class="form-control" name="describtion" >${vo.describtion}</textarea>
								</td>	
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="botton" onclick="fnSave();">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
							<button class="btn btn-w-m btn-primary"  type="button" onclick="formSubmitAndBack();">
								<i class="fa fa-check" aria-hidden="true"></i> 保存并返回
							</button>
							<a href="javascript:backMainPage();" class="btn btn-w-m btn-white"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
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
		function fnShowFile(){
			POBrowser.openWindow('${basePath}doc/document/open.do?id=${vo.id}','width=1200px;height=800px;');
		}
	</script>
</body>
</html>
