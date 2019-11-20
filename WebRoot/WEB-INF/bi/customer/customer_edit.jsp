<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>客户源数据</h5>
				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'update.do':'add.do'}" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">编号:</label></td>
								<td class="width-35"><input id="code" placeholder="编号"  class="form-control" name="code" type="text" value="${vo.code}"/></td>
								<td class="width-15 active"><label class="pull-right">名称:</label></td>
								<td class="width-35"><input id="name" name="name" class="form-control " placeholder="名称" type="text" value="${vo.name }" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">级别:</label></td>
								<td class="width-35"><input id="grade" placeholder="级别"  class="form-control" name="grade" type="text" value="${vo.grade}"/></td>
								<td class="width-15 active"><label class="pull-right">客户经理:</label></td>
								<td class="width-35"><input id="saler" name="saler" class="form-control " placeholder="客户经理" type="text" value="${vo.saler }" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">客户来源:</label></td>
								<td class="width-35"><input id="souce" placeholder="客户来源"  class="form-control" name="souce" type="text" value="${vo.souce}"/></td>
								<td class="width-15 active"><label class="pull-right">客户行业:</label></td>
								<td class="width-35"><input id="industry" name="industry" class="form-control " placeholder="客户行业" type="text" value="${vo.industry }" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">类型:</label></td>
								<td class="width-35"><input id="customerType" placeholder="类型"  class="form-control" name="customerType" type="text" value="${vo.customerType}"/></td>
								<td class="width-15 active"><label class="pull-right">客户建立时间:</label></td>
								<td class="width-35">
									<input id="buildDate" name="buildDate" class="form-control dateISO" placeholder="客户建立时间" type="text" value="${vo.buildDate }" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">区域:</label></td>
								<td class="width-35">
									<div class="input-group">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect()">选择</button>
										</div>
										<input type="text" readonly="readonly" id="selectPath" class="form-control" placeholder="请选择上级区域" name="areaPath" value="${vo.areaPath}">
									</div>
								</td>
								<input type="hidden" name="province" id="province" value=""/>
								<input type="hidden" name="city" id="city" value=""/>
								<input type="hidden" name="county" id="county" value=""/>
								<td class="width-15 active"><label class="pull-right">数据来源:</label></td>
								<td class="width-35" colspan="3">
									<select id="dataSouce" name="dataSouce" class="input-sm form-control" onclick="dataSouceSelect();">
										<option value="${vo.dataSouce}">${vo.dataSouce}</option>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-4 col-sm-offset-2">
							<button class="btn btn-success" id="reSave" type="botton">保存</button>
							<button class="btn btn-primary" id="submitBut" type="button">保存并返回</button>
							<a href="gridPage.do" class="btn btn-white" type="button">返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
    </div>
    <%@ include file="../../include/js.jsp"%>
</body>
<script>
$(function(){
	$('.laydate_m').hide();
	//将地区中的值分别存入省市县中
	$('#submitBut').click(function(){
		var pathList = $('#selectPath').val().split('/');
		$.each(pathList,function(i,n){
			if(i == 1){
				$('#province').val(n);
			}else if(i == 2){
				$('#city').val(n);
			}else if(i == 3){
				$('#county').val(n);
			}
		})
		$('form').submit();
	})
})

var flagType = true;
	function dataSouceSelect() {
		if (!flagType) {
			return flase;
		}
		$.ajax({
			url : '${basePath}/sys/code/ajaxCodeContent.do?code=bi-dataSouce',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					optionstring += "<option value=\"" + value[i] + "\" >" + value[i] + "</option>";
				}
				$("#dataSouce").html(optionstring);
				flagType = false;
			}
		});
	}
	
$(function(){
	$('.laydate_m').hide();
	//将地区中的值分别存入省市县中
	$('#reSave').click(function(){
		var pathList = $('#selectPath').val().split('/');
		$.each(pathList,function(i,n){
			if(i == 1){
				$('#province').val(n);
			}else if(i == 2){
				$('#city').val(n);
			}else if(i == 3){
				$('#county').val(n);
			}
		})
		$('form').attr('action','save.do');
		$('form').submit();
	})
})

function fnSelect(){
	layer.open({
		title:'区域选择',	
		type: 2,
		area: ['300px', '470px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}sys/area/select.do',
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			iframeWin.fnSelect();
		}
	});
}
 
</script>
</html>
