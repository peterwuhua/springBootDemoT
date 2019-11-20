<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
   <meta charset="utf-8">
   <%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	 <div class="col-sm-12">
          <div class="ibox float-e-margins">
              <div class="ibox-title">
                  <h5>数据导入 </h5>
              </div>
              <div class="ibox-content">
                  <form method="post" action="import.do" class="form-horizontal" enctype="multipart/form-data">
                      <input name="param" type="hidden" value="${vo.customerVo.id}">
                      <div class="alert alert-warning">
                       	请先下载 <a href="downtemp.do?type=import&templateName=cus-client-import.xls&trueName=受检单位息导入模板.xls" class="btn btn-w-m btn-primary">模板文件</a>，填充数据后再做导入
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
                              <select name="type" class="input-sm form-control input-s-sm inline">
				<option value="1">追加现有数据</option>
				<option value="2">先清除已有数据再重新导入</option>
			</select>
                          </div>
                      </div>
                      <div class="hr-line-dashed"></div>
                      <div class="form-group">
                          <div class="col-sm-4 col-sm-offset-2">
                              <button class="btn btn-primary" type="submit">导入</button>
                              <c:if test="${fn:length(vo.customerVo.id)>0 }">
	                              <a href="gridTab.do?customerVo.id=${vo.customerVo.id}" class="btn btn-white" type="submit">返回</a>
                              </c:if>
                              <c:if test="${fn:length(vo.customerVo.id)<=0 }">
	                              <a href="gridPage.do" class="btn btn-white" type="submit">返回</a>
                              </c:if>
                          </div>
                      </div>
                  </form>
              </div>
    </div>
    </div>
   <%@ include file="../../include/js.jsp"%>
   <script>
	 $('input[type="file"]').prettyFile();
	</script>
</body>
</html>
