<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../../include/css.jsp"%>
</head>
<body id="tab_context">
<div class="col-sm-12" style="height: 100%">
	<div class="ibox float-e-margins">
		<div class="ibox-content" style="padding: 0px;">
			<form action="" method="post" >
				<div class="jqGrid_wrapper" >
					<table id="listTable"  class="table table-bordered">
						<thead>
							<tr>
								<td width="50"></td>
								<td width="150">设备编号</td>
								<td>仪器名称</td>
								<td>仪器规格型号</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${appList}" var="e" varStatus="v">
								<tr index="${v.index}">
									<td onclick="checkTd('${v.index}')">
										<label class="checkbox-inline i-checks ">
											<div class="icheckbox_square-green">
												<input type="checkbox" id="id_${v.index}" key="${e.name} ${e.spec}(${e.no})" name="id" value="${e.id}">
											</div> 
										</label>
									</td>
									<td onclick="checkTd('${v.index}')">${e.no}</td>
									<td onclick="checkTd('${v.index}')">${e.name}</td>
									<td onclick="checkTd('${v.index}')">${e.spec}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form>
		</div>
	</div>
</div>
<%@ include file="../../../include/js.jsp"%>
<%@ include file="../../../include/grid_page.jsp"%>
</body>
<script type="text/javascript">
	function fnSelect(){
		var id=[];
		var name=[];
		$('input[name="id"]:checked').each(function(){
			id.push($(this).val());
			name.push($(this).attr('key'));
		});
		var data={'id':id.join(','),'name':name.join(',')};
		return data;
	}
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
		var ids='${vo.appId}';
		$('input[name="id"]').each(function(){
			if(ids.indexOf($(this).val())!=-1){
				$(this).iCheck('check'); 
			}
		});
	});
	function checkTd(index){
		var checked=$('input[id="id_'+index+'"]').prop('checked');
		if(checked){
			$('input[id="id_'+index+'"]').iCheck('uncheck'); 
		}else{
			$('input[id="id_'+index+'"]').iCheck('check'); 
		}
	}
</script>
 
</html>
