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
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}" class="form-horizontal" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">分类:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" readonly="readonly" id="selectName" class="form-control" placeholder="请选择存放路径" value="${vo.archiveTypeVo.name}"> 
										<input type="hidden" id="selectId" name="archiveTypeVo.id" value="${vo.archiveTypeVo.id}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectcategory()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">编号:</label></td>
								<td class="width-35"><input id="code" class="form-control required" validate="required" name="code" type="text" value="${vo.code}" /></td>
								<td class="width-15 active"><label class="pull-right">名称:</label></td>
								<td class="width-35"><input id="title" class="form-control required" validate="required" name="title" type="text" value="${vo.title}" /></td>
							</tr>
							<tr>
							 	<td class="width-15 active"><label class="pull-right">归档人:</label></td>
								<td class="width-35">
									<input id="userName" class="form-control" name="userName" type="text" value="${vo.userName}" readonly="readonly"/>
									<input id="userId" name="userId" type="hidden" value="${vo.userId}" />
								</td>
								<td class="width-15 active"><label class="pull-right">归档时间:</label></td>
								<td class="width-35"><input id="time" class="form-control datetimeISO" name="time" type="text" value="${vo.time}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">说明:</label></td>
								<td colspan="3">
									<textarea id="describtion" class="form-control" name="describtion" >${vo.describtion}</textarea>
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
										<input type="text" name="fileList[${v.index}].sign" class="form-control required" validate="required" value="${e.sign}" placeholder="报告文件|原始记录单|采样单">
									</td>
									<td>
										  <a class="btn btn-info" href="javascript:void(0);" onclick="fileUpload('${e.id}');"
			                               style="float: left;margin-right: 5px;">在线上传</a>
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
	                                    <a onclick="removeFiles('${e.id}',this)" title="删除"  class="btn btn-w-m btn-danger">删除此行</a>
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
									<div class="pull-right">
										<button onclick="addFile()" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i> 添加</button>
									</div>
								</td>
							</tr>
						</tfoot>
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
				title : '分类选择',
				type : 2,
				area : [ '300px', '470px' ],
				fix : false, //不固定
				maxmin : true,
				content : '${basePath}bus/archiveType/select.do?id='+ pId,
				btn : [ '确定', '取消' ], //按钮
				btn1 : function(index, layero) {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var data=iframeWin.fnSelect();
					$('#selectId').val(data.id);
					$('#selectName').val(data.name);
				}
			});
		}
		function removeFiles(id, obj) {
	        layer.confirm('确认要删除?', {icon: 3, title: '系统提示'}, function (index) {
	            $.ajax({
	                url: '${basePath}bus/archiveFile/updateDel.do?ids=' + id,
	                dataType: "json",
	                type: "post",
	                async: false,
	                success: function (data) {
	                    if (data.status == 'success') {
	                        layer.msg(data.message, {icon: 0, time: 1000});
	                        $(obj).parent().parent().remove();
	                    }
	                },
	                error: function (ajaxobj) {
	                }
	            });
	        });
	    }
		function fileUpload(infoId) {
	        var id = '${vo.id}';
	        if (id == '' || id == null) {
	            layer.alert('请先保存表单！', {icon: 2, shadeClose: true});
	            return false;
	        }
	        $('#thisForm').attr('action', 'update4Data.do');
	        $('#thisForm').ajaxSubmit(function (res) {
	            if (res.status == 'error') {
	                parent.toastr.error(res.message, '');
	                return false;
	            } else {
	                layer.open({
	                    title: '资料上传',
	                    type: 2,
	                    area: ['700px', '400px'],
	                    fix: false, //不固定
	                    maxmin: true,
	                    content: 'file_upload.do?id=' + infoId,
	                    end: function () {
	                        location.reload();
	                    }
	                });
	            }
	        });
	    }
		function addFile(){
			$.ajax({
				url:'${basePath}bus/archiveFile/addFile.do?archiveVo.id=${vo.id}',
				dataType:"json",
				type:"post",
				async:false,
				success: function(data){
					if(data.status=='success'){
						parent.toastr.success(data.message, '');
						location.reload();
					}
				},
				error:function(ajaxobj){
					parent.toastr.error('添加检测点信息异常！', '');
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
