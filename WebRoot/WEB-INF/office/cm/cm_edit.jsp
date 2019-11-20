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
					<li><a>行政审批</a></li>
					<li><strong>编辑</strong>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					  <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label></td>
								<td class="width-35">
									<select name="type" id="type" class="form-control required" validate="required">
										<option value="">请选择</option>
										<c:forEach items="${typeList}" var="e" varStatus="s">
											<c:choose>
												<c:when test="${e==vo.type}">
													<option value="${e}" selected="selected">${e}</option>
												</c:when>
												<c:otherwise>
													<option value="${e}">${e}</option>
												</c:otherwise>
											</c:choose>
										 </c:forEach>
									</select>
								</td>
								<td class="width-15 active"><!-- <label class="pull-right">项目名称:</label> --></td>
								<td class="width-35">
									<%-- <div class="input-group">
										<input id="title" name="title" class="form-control required" validate="required" type="text" value="${vo.title }" />
										<div class="input-group-btn">
		                                     <button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown">
		                                         <span class="caret"></span>
		                                     </button>
		                                     <ul class="dropdown-menu dropdown-menu-right" role="menu">
		                                     </ul>
		                             	</div>
	                                 </div> --%>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">成&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本:</label></td>
								<td>
									<input id="price" name="price" class="form-control number required" type="text" value="${vo.price }" validate="required" />
								</td>
								<td class="active"><label class="pull-right">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时:</label></td>
								<td>
									<input id="hours" name="hours" class="form-control number required" type="text" value="${vo.hours }" validate="required"/>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="content" placeholder="内容" name="content" maxlength="128">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">参与人员:</label></td>
								<td>
									<div class="input-group">
										<input id="userNames" name="userNames" class="form-control required" validate="required" type="text" value="${vo.userNames}" onclick="fnUsers()"/>
										<input  id="userIds" name="userIds" type="hidden" value="${vo.userIds}" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnUsers()">选择</button>
										</div>
									</div>
								</td>
								<td class="active"><label class="pull-right">申请日期:</label></td>
								<td>
									<div class="input-group date">
		                           		<input id="sdate" name="sdate" class="form-control required dateISO" validate="required" type="text" value="${vo.sdate}" />
		                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审&nbsp;&nbsp;核&nbsp;人:</label></td>
								<td>
									<div class="input-group">
										<input id="auditName" name="auditName" class="form-control required" validate="required" type="text" value="${vo.auditName}" onclick="fnSelectUser()"/>
										<input  id="auditId" name="auditId" type="hidden" value="${vo.auditId}" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
								<td class="active"><label class="pull-right">抄&nbsp;&nbsp;送&nbsp;人:</label></td>
								<td>
									<div class="input-group">
										<input id="viewNames" name="viewNames" class="form-control required" validate="required" type="text" value="${vo.viewNames}" onclick="fnSelectUsers()"/>
										<input id="viewIds" name="viewIds" type="hidden" value="${vo.viewIds}" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUsers()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="remark" name="remark" maxlength="128">${vo.remark}</textarea>
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
		   <!-- Sweet alert -->
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
    <script src="${basePath}h/js/plugins/suggest/bootstrap-suggest.min.js"></script>
	<script>
		$('input[type="file"]').prettyFile();
		function formSubmitSave(url){
			$('#thisForm').attr('action',url);
			$('#thisForm').submit()
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
	$("#title").bsSuggest({
	    indexId: 0, //data.value 的第几个数据，作为input输入框的内容
	    indexKey: 0, //data.value 的第几个数据，作为input输入框的内容
	    data:getDjData()
	});
	function getDjData(){
		 var data = {value: []};
		 $.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=cm-type',
			async:false,
			datatype : "json",
			success : function(datas) {
				var val = datas.split(",");
				for (var i = 0; i < val.length; i++) {
					data.value.push({word: val[i]});
				}
			}
		});
		 data.defaults = '';
		return data;
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
	function fnUsers(){
		var idStr=$('#userIds').val();
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
				$('#userIds').val(data.id);
				$('#userNames').val(data.name);
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
	function fnSelectProject(){
		layer.open({
			title:'选择任务',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/task/select.do',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#no').val(data.no);
				$('#title').val(data.title+' '+data.vs);
				$('#userIds').val(data.testId);
				$('#userNames').val(data.testName);
				$('#viewIds').val(data.testId);
				$('#viewNames').val(data.testName);
			}
		});
	}
	</script>
</body>
</html>
