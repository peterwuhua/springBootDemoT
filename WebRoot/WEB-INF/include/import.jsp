<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
   <meta charset="utf-8">
   <%@ include file="css.jsp"%>
</head>
<body class="gray-bg">
 <div class="col-sm-12">
          <div class="ibox float-e-margins">
              <div class="ibox-title">
                  <h5>数据导入 </h5>
              </div>
              <div class="ibox-content">
                  <form method="post" action="import.do" class="form-horizontal" enctype="multipart/form-data">
                      <input name="param" type="hidden" value="${param.param }">
                      <input name="id" type="hidden" value="${vo.id }">
                      <div class="alert alert-warning">
                       	请先下载 <a href="downtemp.do?type=import&templateName=${param.templateName}&trueName=${param.trueName}" class="btn btn-w-m btn-primary">模板文件</a>，填充数据后再做导入
                   </div>
                     <div class="form-group">
                          <label class="col-sm-2 control-label">导入文件</label>
                          <div class="col-sm-10">
                              <input type="file" class="form-control" name="file" accept="application/vnd.ms-excel">
                          </div>
                      </div>
                      <div class="form-group">
                          <label class="col-sm-2 control-label">导入模式</label>
                          <div class="col-sm-10">
                              <select name="type" id="type" class="input-sm form-control input-s-sm inline" onchange="checkType(this.value);">
                         		 <c:if test="${'1' eq param.add }">
									<option value="1">追加现有数据</option>
                          		</c:if>
                         		 <c:if test="${'1' eq param.cleanAdd }">
									<option value="2">先清除已有数据再重新导入</option>
                         		 </c:if>
                         		 <c:if test="${'1' eq param.matching }">
									<option value="3">匹配导入</option>
                         		 </c:if>
							 </select>
                          </div>
                      </div>
                      <div class="hr-line-dashed"></div>
                      <div class="form-group">
                          <div class="col-sm-4 col-sm-offset-2">
                              <button class="btn btn-primary" type="submit">导入</button>
                              <a href="gridPage.do" class="btn btn-white">返回</a>
                          </div>
                      </div>
                  </form>
              </div>
    </div>
    </div>
   <%@ include file="js.jsp"%>
   <script type="text/javascript">
   		function checkType(type){
  			if(type=='2'){
	  			 layer.confirm("   选择该选项将会删除所有数据,且与其相关联的所有数据均会被删除,数据删除之后不可恢复,请慎用,继续选择该选项吗？", {icon:3, title:'系统提示',btn: ['继续', '放弃选择']}, function(index){
	  				layer.close(index);
	  			 },function(index){
	  				$("#type").val("1");
	  				layer.close(index);
	  			 });
  			}
   		}
   </script>
   <script>
	 $('input[type="file"]').prettyFile();
	</script>
</body>
</html>
