<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
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
								<td width="150">方法编号</td>
								<td>条款</td>
								<td>方法名称</td>
								<td>检出限</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${methodList}" var="e" varStatus="v">
								<tr index="${v.index}">
									<td onclick="checkTd('${v.index}')">
										<label class="radio radio-info radio-inline i-checks ">
											<div class="iradio_square-green">
												<input type="radio" id="id_${v.index}" key="${e.code} ${e.chapter} ${e.name}" key1="${e.minLine}" name="id" value="${e.id}">
											</div> 
										</label>
									</td>
									<td onclick="checkTd('${v.index}')">${e.code}</td>
									<td onclick="checkTd('${v.index}')">${e.chapter}</td>
									<td onclick="checkTd('${v.index}')">${e.name}</td>
									<td onclick="checkTd('${v.index}')">${e.minLine}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form>
		</div>
	</div>
</div>
<%@ include file="../../include/js.jsp"%>
<%@ include file="../../include/grid_page.jsp"%>
</body>
<script type="text/javascript">
	function fnSelect(){
		var id='';
		var name='';
		var limitLine='';
		$('input[name="id"]:checked').each(function(){
			id=$(this).val();
			name=$(this).attr('key');
			limitLine=$(this).attr('key1');
		});
		var data={'id':id,'name':name,'limitLine':limitLine};
		return data;
	}
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
		var ids='${vo.methodVo.id}';
		$('input[name="id"]').each(function(){
			if(ids==$(this).val()){
				$(this).iCheck('check'); 
			}
		});
	});
	function checkTd(index){
		var checked=$('input[id="id_'+index+'"]').prop('checked');
		$('input[id="id_'+index+'"]').iCheck('check'); 
	}
</script>
 
</html>
