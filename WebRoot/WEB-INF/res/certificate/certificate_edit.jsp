<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>资质管理</a></li>
					<li>
						<strong>
							<c:if test="${fn:length(vo.id)>0}">编辑</c:if> 
							<c:if test="${fn:length(vo.id)==0}">新增</c:if>
						</strong>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'update2File.do':'add2File.do'}" class="form-horizontal" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名 :</label></td>
								<td class="width-35">
									<div class="input-group">
										<input id="userName" placeholder="请选择" class="form-control required" validate="required" name="userName" type="text" value="${vo.userName }" /> 
										<input type="hidden" id="userId" name="userId" value="${vo.userId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>

								</td>
								<td class="width-15 active"><label class="pull-right">证书类型:</label></td>
								<td class="width-35">
									<select id="type" name="type" class=" form-control">
									</select>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">证书名称 :</label></td>
								<td class="width-35"><input id="name" placeholder="请输入证书名称" class="form-control" value="${vo.name }" name="name" type="text" /></td>
								<td class="width-15 active"><label class="pull-right">证书编号:</label></td>
								<td class="width-35"><input id="code" placeholder="请输入证书编号" class="form-control required" validate="required" name="code" value="${vo.code }" type="text" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">发证机关:</label></td>
								<td class="width-35"><input id="unit" placeholder="请输入发证机关" class="form-control" name="unit" type="text" value="${vo.unit }" /></td>
								<td class="width-15 active"><label class="pull-right">发证日期:</label></td>
								<td class="width-35"><input id="releaseDate" placeholder="请输入发证日期"  class="form-control dateISO" name="releaseDate" type="text" value="${vo.releaseDate }" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">起始日期:</label></td>
								<td class="width-35"><input id="startTime" placeholder="请输入证件有效起始日期" class="form-control required" validate="required" name="startTime" type="text" value="${vo.startTime }" /></td>
								<td class="width-15 active"><label class="pull-right">结束日期:</label></td>
								<td class="width-35"><input id="endTime" placeholder="请输入证件有效结束日期"  class="form-control dateISO required" validate="required" name="endTime" type="text" value="${vo.endTime }" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">上传资质:</label></td>
								<td><input type="hidden" name="filePath" id="filePath" value="${vo.filePath}" /> <input type="hidden" name="fileName" id="fileName" value="${vo.fileName}" /> <input type="file" name="file" /></td>
								<td colspan="2" id="removeFile"><c:if test="${fn:length(vo.fileName)>0}">
										<a href="download.do?filePath=${vo.filePath}&trueName=${vo.fileName}" class="btn btn-w-m btn-info">${vo.fileName}</a>
										<button type="button" class="btn btn-danger " onclick="removeFile('${vo.id}');">删除</button>
									</c:if></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">资质能力:</label></td>
								<td class="width-35" colspan="3">
									<textarea id="itemName" name="itemName" class="form-control" placeholder="请选择"  onclick="fnSelectItem()">${vo.itemName }</textarea>
									<input type="hidden" id="itemId"  name="itemId" value="${vo.itemId}">
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td class="width-35" colspan="3"><textarea maxlength="128" id="remark" name="remark" class="form-control" placeholder="请输入备注">${vo.remark }</textarea></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('save2File.do');">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
							<button class="btn btn-w-m btn-primary"  type="button" onclick="formSubmitAndBack();">
								<i class="fa fa-check" aria-hidden="true"></i> 保存并返回
							</button>
							<a class="btn btn-w-m btn-white" href="#" onclick="backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script type="text/javascript">
		$('input[type="file"]').prettyFile();
		laydate.render({
			  elem: '#startTime',
			  theme: 'molv',
			  done: function(value, date, endDate){
				  countFinishDay(value);
			  }
		});
		function countFinishDay(startTime){
			
			var array = startTime.split("-");
			var dt = new Date(array[0], (parseInt(array[1])-1), parseInt(array[2]));
			var month=dt.getMonth()+1;
			var day=dt.getDate();
			var year=dt.getFullYear();
			year=year+5;
			
			if (month.toString().length<2)
			{
				month = '0'+month;
			}
			if (day.toString().length<2)
			{
				day = '0'+day;
			}	
			var endTime=year+'-'+month+'-'+day;
			$('#endTime').val(endTime);
		}
		function removeFile(id) {
			location.href = '/res/certificate/removeFile.do?id=' + id;
		}
		function typeSelect() {
			$.ajax({
				url : '${basePath}sys/code/ajaxCodeContent.do?code=res-certType',
				datatype : "json",
				success : function(data) {
					var value = data.split(",");
					var optionstring = "";
					for (var i = 0; i < value.length; i++) {
						if ('${vo.type}' == value[i]) {
							optionstring += "<option value=\"" + value[i] + "\"  selected=\"selected\">"
									+ value[i] + "</option>";
						} else {
							optionstring += "<option value=\"" + value[i] + "\">"
									+ value[i] + "</option>";
						}
					}
					$("#type").html(optionstring);
					flagType = false;
				}
			});
		}
		function fnSelectUser() {
			var userId = $('#userId').val();
			var userName = $('#userName').val();
			layer.open({
				title : '人员选择',
				type : 2,
				area : [ '70%', '85%' ],
				fix : false, //不固定
				maxmin : true,
				content : '${basePath}sys/user/select.do?userVo.id=' + userId
						+ '&userVo.name=' + userName,
				btn : [ '确定', '取消' ], //按钮
				btn1 : function(index, layero) {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var user = iframeWin.fnSelect();
					$('#userId').val(user.id);
					$('#userName').val(user.name);
				}
			});
		}
		function fnSelectItem(){
			var id=$('#itemId').val();
			layer.open({
				title:'监测项目',	
				type: 2,
				area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}init/item/selects.do?ids='+id,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelect();
				  $('#itemName').val(data.name);
				  $('#itemId').val(data.id);
				}
			});
		}
		$(function() {
			typeSelect();
		});
	</script>
</body>
</html>
