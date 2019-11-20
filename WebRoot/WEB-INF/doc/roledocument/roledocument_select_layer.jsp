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
				<h5>${value}</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
				</div>
			</div>
			<div class="ibox-content">
				<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
					<input type="hidden" id="inputValue" value="${inputValue}"/>
					<input type="hidden" id="inputType" value="${type}" />
					<tbody>
						<tr>
							<td width="15%" class="active"><label class="pull-right">${value}:</label></td>
							<td colspan="3">
								<c:forEach items="${list}" var="e" varStatus="s">
									<label class="checkbox-inline i-checks " >
									<div class="icheckbox_square-green" >
										<input type="checkbox" value="${e}" class="checkedType">
									</div>${e}
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
<script>
		$(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
		
		//判断父类页面input中是否存在相同的值，存在则默认checked
		$(function(){
			var vs = $('#inputValue').val().split(',');
			$.each(vs,function(i,n){
				$('.checkedType').each(function(){
					if($(this).val() == n){
						$(this).attr('checked',true);
						$(this).parent().addClass('checked');
					}
				})
			})
		})
		
		var index = parent.layer.getFrameIndex(window.name);  //父类页面
		//父类页面传值
		function fnSelect(){
			var type = $('#inputType').val();
			var checkboxVal = $('input[type="checkbox"]:checked'),
				listStr = new Array();
			checkboxVal.each(function(){
				listStr.push($(this).val());
				
			})
			parent.$('#'+type).val(listStr);
			parent.layer.close(index);
			
		}
	</script> 
    </body>
</html>