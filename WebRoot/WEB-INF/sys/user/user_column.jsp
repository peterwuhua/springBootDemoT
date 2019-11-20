<%@ page contentType="text/html;charset=UTF-8"%>
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
               <div class="ibox-content">
                 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">导出模板:</label></th>
								<td class="width-35">
									 <select name="parentVo.id" class="input-sm form-control input-s-sm inline"">
											<option>
												新模板
											</option>
                                    </select> 
								</td>
								<td class="width-15 active"><label class="pull-right">模板名称:</label></td>
								<td class="width-35"><input id="name" placeholder="模板名称"  class="form-control" maxlength=64 name="name" type="text" value="${vo.name}"/></td>
							</tr>
							<tr>
								<td colspan="4">
									 <c:forEach items="${columnMap}" var="e" varStatus="s">
					                    <label class="checkbox-inline i-checks " >
										<div class="icheckbox_square-green" >
											<input type="checkbox"  name="roleIds" title="${e.value }" value="${e.key}">
										</div><span title="${e.value }">${s.index+1}、${e.value}</span>
										</label> 
					                 </c:forEach>
								</td>
							</tr>
						</tbody>
					</table>		
               </div>
           </div>
    	</div>
    <%@ include file="../../include/js.jsp"%>
    
    </body>
     <script>
     	function selectTemp() {
     		return {tempName : '人员信息列表',  tempCode : 'sys-user-export.xls' }
     	};
     	
     	$(document).ready(function(){
        	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",});
       	});
    </script>
</html>
