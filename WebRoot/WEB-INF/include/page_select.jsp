<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="col-sm-3">
<div class="input-group">
    <input placeholder="请输入关键词" class="input-sm form-control" value="${pageResult.queryValue}" name="queryValue" type="text" > 
        <span class="input-group-btn">
        	<button type="submit" class="btn btn-sm btn-primary"> 搜索</button>
        </span>
    </div>
</div>
<script>
	var queryColumn = '${pageResult.queryColumn}';
	//查询字段
	if(queryColumn.length>0){
		$("select[name='queryColumn']").val(queryColumn);
	}
</script>