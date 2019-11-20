<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="col-sm-3">
	<div class="input-group">
		<input placeholder="请输入关键词" class="input-sm form-control"
			name="queryValue" value="${pageResult.queryValue}" id="queryValue"
			type="text"> <span class="input-group-btn">
			<button type="button" id="queryButton" onclick="gridReload();" class="btn btn-sm btn-primary">
				搜索</button>
		</span>
	</div>
</div>

<script>
$("#queryValue").bind("keydown",function(e){ 
	// 兼容FF和IE和Opera    
	var theEvent = e || window.event; 
	var code = theEvent.keyCode || theEvent.which || theEvent.charCode; 
	if (code == 13) { 
		$("#queryButton").click()
	}
})
</script>		