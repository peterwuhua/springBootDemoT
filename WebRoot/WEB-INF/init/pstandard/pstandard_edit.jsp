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
						<li><a>评价标准</a></li>
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
									<td class="width-15 active"><label class="pull-right">标准名称:</label></td>
									<td class="width-35"><input id="name" placeholder="标准名称"  class="form-control required" validate="required"
									 			name="name" type="text" value="${vo.name}"/></td>
									<td class="width-15 active"><label class="pull-right">标准编号:</label></td>
									<td class="width-35"><input id="code" placeholder="标准编号"  class="form-control required" validate="required"
									 			name="code" type="text" value="${vo.code}"/></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">英文名称:</label></td>
									<td colspan="3"><input id="enName" placeholder="英文名称"  class="form-control"
									 			name="enName" type="text" value="${vo.enName}"/>
									 </td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">样品类别:</label></td>
									<td>
										<div class="input-group" style="width: 100%">
											<input type="text" id="sampTypeName" name="sampTypeName" class="form-control required" validate="required" placeholder="请选择" value="${vo.sampTypeName}" onclick="fnSelectSampType()">
											<input type="hidden" id="sampTypeId"  name="sampTypeId" value="${vo.sampTypeId}">
											<div class="input-group-btn">
												<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectSampType()">选择</button>
											</div>
										</div>
									</td>
									<td class="active"><label class="pull-right">标准类型:</label></td>
									<td>
									 	 <select id="type" name="type" class="form-control">
										</select>
									</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">发布日期:</label></td>
									<td><input id="fbDate" class="form-control dateISO" name="fbDate" value="${vo.fbDate}" placeholder="发布日期"/></td>
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
									<td class="active"><label class="pull-right">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</label></td>
									<td>
										<label class="checkbox-inline i-checks " >
											<div class="iradio_square-green" >
												<input type="radio"  name="status" value="现行" <c:if test="${vo.status=='现行'}">checked="checked"</c:if>>
											</div>现行
										</label>
										<label class="checkbox-inline i-checks " >
											<div class="iradio_square-green" >
												<input type="radio"  name="status" value="已作废" <c:if test="${vo.status=='已作废'}">checked="checked"</c:if>>
											</div>已作废
										</label>
									</td>
									<td class="active"><label class="pull-right">代替标准:</label></td>
									<td>
										<div class="input-group" style="width: 100%">
											<input type="text" id="standNames" name="standNames" class="form-control" value="${vo.standNames}" >
											<input type="hidden" id="standIds"  name="standIds" value="${vo.standIds}">
											<div class="input-group-btn">
												<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectStand()">选择</button>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td class="width-15 active"><label class="pull-right">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label></td>
									<td><input id="sort" class="form-control digits" name="sort" value="${vo.sort}" /></td>
								</tr>
								<tr>
									<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
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
	$(function(){  
		codeSelect();//状态
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
		
		var id='${vo.id}';
		if(id!=''){
			$('#code').attr('readonly','readonly');
			$('#code').addClass('disabled');
		}else{
			$('#code').attr('onchange','validateCode(this)');
		}
 	});
	function codeSelect() {
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=stand-type',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					if('${vo.type}'==value[i]){
						optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"+ value[i] + "</option>";
					}else{
						optionstring += "<option value=\"" + value[i] + "\" >"+ value[i] + "</option>";
					}
				}
				$('#type').html(optionstring);
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
	function fnSelectStand(){
		var code=$('#code').val();
		if(code==''){
			layer.msg("请输入标准基础信息");
			return ;
		}
		layer.open({
			title:'标准选择',	
			type: 2,
			area: ['800px', '400px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/pstandard/selects.do?ids='+$('#standIds').val(),
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  if(data.code.indexOf(code)>=0){
				  layer.msg("代替标准选择异常！");
			  }else{
				  $('#standIds').val(data.id);
				  $('#standNames').val(data.name); 
			  }
			}
		});
	}
</script>
</html>
