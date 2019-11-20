<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

</head>
<body class="gray-bg">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a>检测依据</a></li>
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
									<td class="width-15 active"><label class="pull-right">标准编号:</label></td>
									<td class="width-35"><input id="code" placeholder="标准编号"  class="form-control required" validate="required"
									 			name="code" type="text" value="${vo.code}" /></td>
									<td class="width-15 active"><label class="pull-right">标准名称:</label></td>
									<td class="width-35"><input id="name" placeholder="标准名称"  class="form-control"
									 			name="name" type="text" value="${vo.name}"/></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">英文名称:</label></td>
									<td><input id="enName" placeholder="英文名称"  class="form-control"
									 			name="enName" type="text" value="${vo.enName}"/></td>
									<td class="active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label></td>
									<td>
									 	 <select id="type" name="type" class="form-control" onclick="codeSelect('stand-type','type');">
											<option value="${vo.type}">${vo.type}</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">发布日期:</label></td>
									<td><input id="fbDate" class="form-control dateISO" name="fbDate" value="${vo.fbDate}"  placeholder="发布日期"/></td>
									<td class="active"><label class="pull-right">实施日期:</label></td>
									<td><input id="ssDate" class="form-control dateISO" name="ssDate" value="${vo.ssDate}" placeholder="实施日期"/></td>
								</tr>
								<tr>
									<th class="active"><label class="pull-right">上传文件:</label></th>
									<td>
										<input type="hidden" name="filePath" id="filePath" value="${vo.filePath}"/>
										<input type="hidden" name="fileName" id="fileName" value="${vo.fileName}"/>
										<input type="file" name="file"  accept=".pdf"/>
									</td>
									<td colspan="2" id="removeFile">
										<c:if test="${fn:length(vo.fileName)>0}">
											<a href="javascript:;" onclick="openPdf('${basePath}${vo.filePath}','${vo.fileName}')" class="btn btn-w-m btn-info">${vo.fileName}</a>
											<button type="button" class="btn btn-danger " onclick="removeFile();">删除</button>
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
									<td class="width-35" colspan="3">
										<textarea maxlength="128" rows="2" cols="60"  id="remark" name="remark" class="form-control">${vo.remark}</textarea>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-12 col-sm-offset-1">
								<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('save4Data.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
								<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
								<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	<%@ include file="../../include/js.jsp"%>
	<script src="${basePath}open/openPdf.js" type="text/javascript"></script>
</body>
<script>
        $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
</script>

<script type="text/javascript">

	$('input[type="file"]').prettyFile();
	
	function removeFile(){
	 $("#removeFile").html("");
	 $("#filePath").val("");
	 $("#fileName").val("");
	}
	
	var $$ = function(func){  
        if (document.addEventListener) {  
            window.addEventListener("load", func, false);  
        }  
        else if (document.attachEvent) {  
            window.attachEvent("onload", func);  
        }
   } 
	$$(function(){  
		codeSelect('stand-type','type');//状态
		var id='${vo.id}';
		if(id!=''){
			$('#code').attr('readonly','readonly');
			$('#code').addClass('disabled');
		}else{
			$('#code').attr('onchange','validateCode(this)');
		}
 	});
	function codeSelect(code,idstr) {
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code='+code,
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					optionstring += "<option value=\"" + value[i] + "\" >"
							+ value[i] + "</option>";
				}
				$('select[name$="'+idstr+'"]').each(function(){
					var val = $(this).val();
					$(this).html(optionstring);
					$(this).attr("onclick","");
					if(val.length>0){
						$(this).val(val);
					}
				});
			}
		});
	}
	function validateCode(obj){
		var code=$(obj).val();
		$.ajax({ 
			url:"ajaxCheckCode.do",
			dataType:"json",
			data:{'code':code},
			type:"post",
			success: function(data){
				if("success"!=data.status){
					layer.msg(data.message);
					$(obj).val('');
				}
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }  
		});
	}
	function fnSelectSampType(){
		var sampTypeId=$('#sampTypeId').val();
		layer.open({
			title:'样品类别',	
			type: 2,
			area: ['300px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/sampType/selects.do?ids='+sampTypeId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#sampTypeId').val(data.id);
			  $('#sampTypeName').val(data.name);
			}
		});
	}
</script>
</html>
