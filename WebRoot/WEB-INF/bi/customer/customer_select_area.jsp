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
			
			<div class="ibox-content">
				<form id="seachForm" action="" class="form-horizontal">
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-50 active"><label class="pull-right">省:</label></td>
								<td>
									<select id="province" class="input-sm form-control input-s-sm inline value_0">
										
									</select>
								</td>
							</tr>
							<tr>
								<td class="width-50 active"><label class="pull-right">市:</label></td>
								<td>
									<select id="city" class="input-sm form-control input-s-sm inline value_1">
											
									</select>
								</td>
							</tr>
							<tr>
								<td class="width-50 active"><label class="pull-right">县:</label></td>
								<td>
									<select id="county" class="input-sm form-control input-s-sm inline value_2">
										
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
    </div>
</body>
<%@ include file="../../include/js.jsp"%>
	<script type="text/javascript">
		
		$(function(){
			var province = $('#province').attr('id');
			var city = $('#city').attr('id');
			var county = $('#county').attr('id');
			areaData(province,null,null);	//初始化
			$('#province').change(function(){
				var data = $(this).find('option:selected').html();
				areaData(city,province,data);
				$('#county').val('');
			});
			$('#city').change(function(){
				var data = $(this).find('option:selected').html();
				areaData(county,city,data);
			});
			
		})
		
		//获取区域内容
		function areaData(type,parentType,data){
			$.ajax({
				type : "post",
				async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
				url : "areaData.do",    
				data : {"pageType":type,"parentType":parentType,"data":data},
				dataType : "json",       
				success : function(result) {
					$('#'+type).children().remove();
					$('#'+type).append(
						$('<option>').html("")
					);
					$.each(result,function(i,n){
						$('#'+type).append(
							$('<option>').html(n)
						);
					})
				}
			})
		}
		
		function fnSelect(){
			var province = $('#province').val(),
			city = $('#city').val(),
			county = $('#county').val();
			var str = province;
			if(null != city && '' != city){
				str = str + '/' + city;
				if(null != county && '' != county){
					str = str + '/' + county;
				}
 			}
			parent.$('#areaPath').val(str);
		}
	</script>
</html>