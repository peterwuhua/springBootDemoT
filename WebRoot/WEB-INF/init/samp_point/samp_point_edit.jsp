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
					<li><a href="javascript:backMainPage();">环境监测点位</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-10 active"><label class="pull-right">点位名称:</label></td>
							<td class="width-20"><input id="name" class="form-control required" validate="required"  maxlength=64 name="name" type="text" value="${vo.name}" placeholder="监测点位或监测断面"/></td>
							<td class="width-10 active"><label class="pull-right">点位代码:</label></td>
							<td class="width-20"><input id="code" name="code" class="form-control" maxlength=64 type="text" value="${vo.code}" placeholder="代码"/></td>
							<td class="width-10 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label></td>
							<td class="">
								<select id="type" name="type" class="form-control">
									
								</select>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">样品类别:</label></td>
							<td>
								<div class="input-group">
									<select style="width: 110px;" id="sampTypeId" name="sampTypeId" class="form-control" onchange="changeSampType()">
										<c:forEach items="${sampTypeList}" var="e" varStatus="s">
											<c:choose>
												<c:when test="${e.id==vo.sampTypeId}">
													<option value="${e.id}" selected="selected">${e.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}">${e.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<select style="width: 140px;" id="sampId" name="sampId" class="form-control"  onchange="changeSamp()">
										<c:forEach items="${sampList}" var="e" varStatus="s">
											<c:choose>
												<c:when test="${e.id==vo.sampId}">
													<option value="${e.id}" selected="selected">${e.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}">${e.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
							</td>
							<td class="active"><label class="pull-right">所在地:</label></td>
							<td class=""><input id="sx" name="sx" class="form-control" type="text" value="${vo.sx}" placeholder="监测点或断面所在地"/></td>
							<td class="active"><label class="pull-right">所在地代码:</label></td>
							<td class=""><input id="sxCode" name="sxCode" class="form-control" type="text" value="${vo.sxCode}" placeholder="所在地代码"/></td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">河流/区域名称:</label></td>
							<td><input id="hl" name="hl" class="form-control" type="text" value="${vo.hl}" placeholder="监测河流、水体名称"/></td>
							<td class="active"><label class="pull-right">河流/区域代码:</label></td>
							<td><input id="hlCode" name="hlCode" class="form-control" type="text" value="${vo.hlCode}" placeholder="代码"/></td>
							<td class="active"><label class="pull-right">年采水量:</label></td>
							<td><input id="csl" name="csl" class="form-control" type="text" value="${vo.csl}" placeholder="年采水量"/></td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">流&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向:</label></td>
							<td><input id="lx" name="lx" class="form-control" type="text" value="${vo.lx}" placeholder="流向"/></td>
							<td class="active"><label class="pull-right">水期代码:</label></td>
							<td><input id="sqdm" name="sqdm" class="form-control" type="text" value="${vo.sqdm}" placeholder="代码"/></td>
							<td class=" active"><label class="pull-right">功&nbsp;能&nbsp;&nbsp;区:</label></td>
							<td class=""><input id="gnq" name="gnq" class="form-control" type="text" value="${vo.gnq}"  placeholder="功能区 或 水域功能代码"/></td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">交接管辖级别:</label></td>
							<td><input id="jjdmjb" name="jjdmjb" class="form-control" type="text" value="${vo.jjdmjb}" placeholder=""/></td>
							<td class="active"><label class="pull-right">网管级别:</label></td>
							<td><input id="wgdmjb" name="wgdmjb" class="form-control" type="text" value="${vo.wgdmjb}" placeholder="水、气、声网管级别"/></td>
							<td class="active"><label class="pull-right">域管级别:</label></td>
							<td><input id="ygdmjb" name="ygdmjb" class="form-control" type="text" value="${vo.ygdmjb}" /></td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">地下水层名称:</label></td>
							<td class=""><input id="dxs" name="dxs" class="form-control" type="text" value="${vo.dxs}" /></td>
							<td class="active"><label class="pull-right">采样深度:</label></td>
							<td class=""><input id="sd" name="sd" class="form-control" type="text" value="${vo.sd}" /></td>
							<td class="active"><label class="pull-right">水平点代码:</label></td>
							<td class=""><input id="sp" name="sp" class="form-control" type="text" value="${vo.sp}" /></td>
						</tr>
						<tr>
							<td class=" active"><label class="pull-right">经&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度:</label></td>
							<td class=""><input id="lng" name="lng" class="form-control" type="text" value="${vo.lng}" /></td>
							<td class=" active"><label class="pull-right">纬&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度:</label></td>
							<td class=""><input id="lat" name="lat" class="form-control" type="text" value="${vo.lat}" /></td>
							<td class=" active"><label class="pull-right">垂直点代码:</label></td>
							<td class=""><input id="cz" name="cz" class="form-control" type="text" value="${vo.cz}" /></td>
						</tr>
						<tr>
							<td class=" active"><label class="pull-right">监测频次:</label></td>
							<td>
								<input style="width: 100px;float: left;" id="pc" name="pc" value="${vo.pc}" type="text"  class="form-control digits" />
								 <select style="width: 100px;float: left;" id="pcUnit" name="pcUnit"   class="form-control" >
									<option value="">请选择</option>
									<c:forEach items="${pcUnitList}" var="e1">
										<c:choose>
											<c:when test="${e1.name==vo.pcUnit}">
												<option value="${e1.name}" selected="selected">${e1.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${e1.name}">${e1.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								 </select>
							</td>
							<td class=""><label class="pull-right">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label></td>
							<td><input id="sort" name="sort" class="form-control " placeholder="排序" type="number" value="${vo.sort}" /></td>
						</tr>
						<tr>
							<td class=" active"><label class="pull-right">监测项目:</label></td>
							<td class="" colspan="5">
								<textarea id="itemName" name="itemName" class="form-control"  onclick="fnSelectItem()">${vo.itemName}</textarea>
								<input type="hidden" id="itemId"  name="itemId" value="${vo.itemId}">
							</td>
						</tr>
						<tr>
							<td class=" active"><label class="pull-right">上报要求:</label></td>
							<td class="" colspan="5">
								<textarea id="sbyq" name="sbyq" class="form-control">${vo.sbyq}</textarea>
							</td>
						</tr>
						<tr>
							<td class=" active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td class="" colspan="5">
								<textarea maxlength="128" id="remark" name="remark" class="form-control">${vo.remark}</textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('save.do');">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
							<button class="btn btn-w-m btn-primary"  type="button" onclick="formSubmitAndBack();">
								<i class="fa fa-check" aria-hidden="true"></i> 保存并返回
							</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
	function changeSampType(){
		var sampTypeId=$('#sampTypeId').val();
		$.ajax({
			url : '${basePath}init/sampType/listData.do?pid='+sampTypeId,
			datatype : "json",
			success : function(data) {
				var optionstring = '';
				for (var i = 0; i < data.length; i++) {
					if('${vo.sampId}'== data[i].id){
						optionstring += '<option value="'+data[i].id+'" selected="selected">'+data[i].name+'</option>';
					}else{
						optionstring += '<option value="'+data[i].id+'" >'+data[i].name+'</option>';
					}
				}
				$("#sampId").html(optionstring);
			}
		});
		$('#itemId').val('');
		$('#itemName').val('');
	}
	function changeSamp(){
		$('#itemId').val('');
		$('#itemName').val('');
	}
	function typeSelect() {
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=samp-point-type',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = '<option value="" >-请选择-</option>';
				for (var i = 0; i < value.length; i++) {
					if('${vo.type}'== value[i]){
						optionstring += '<option value="'+value[i]+'" selected="selected">'+value[i]+'</option>';
					}else{
						optionstring += '<option value="'+value[i]+'" >'+value[i]+'</option>';
					}
				}
				$("#type").html(optionstring);
			}
		});
	}
	 $(function(){
		 typeSelect();
	 });
	 function fnSelectItem(){
		 var itemId=$('#itemId').val();
		 var sampId=$('#sampId').val();
		 layer.open({
				title:'监测项目',	
				type: 2,
				area: ['800px', '80%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}init/item/selects.do?ids='+itemId+'&sampTypeIds='+sampId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var data=iframeWin.fnSelect();
					$('#itemId').val(data.id);
					$('#itemName').val(data.name);
				}
			});
	 }
	</script>
</body>
</html>
