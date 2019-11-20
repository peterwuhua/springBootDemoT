<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>受<br>检<br>单<br>位</label></td>
								<td class="active" style="width: 15%;"><label class="pull-right">单位名称:</label></td>
								<td style="width: 35%">
									${vo.custVo.custName}
								</td>
								 <td class="active" style="width: 15%;"><label class="pull-right">单位地址:</label></td>
								<td >
									${vo.custVo.custAddress} 
								</td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td>
									${vo.custVo.custUser}
								</td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td>
									${vo.custVo.custTel}
								</td>
							</tr>
							<tr>
								 <td class="active"><label class="pull-right">行业分类和代码:</label></td>
								<td>
									${vo.custVo.industry} 
								</td>
								<td class="active"><label class="pull-right">单位性质:</label></td>
								<td>
									${vo.custVo.attribute}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">主要产品:</label></td>
								<td colspan="3">
									${vo.custVo.product}
								</td>
							</tr>
							<tr>
								<td class="active" rowspan="3" style="text-align: center;width: 50px;"><label>委<br>托<br>单<br>位</label></td>
								<td class=" active"><label class="pull-right">单位名称:</label></td>
								<td >
									${vo.custVo.wtName}
								</td>
								<td class=" active"><label class="pull-right">单位地址:</label></td>
								<td >
									${vo.custVo.wtAddress}
								</td>
								
							</tr>
							<tr>
								<td class="active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td>
									${vo.custVo.wtUser}
								</td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td>
									${vo.custVo.wtTel}
								</td>
							</tr>
							<tr>
								 <td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
								<td>
									${vo.custVo.wtEmail}
								</td>
								<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
								<td>
									${vo.custVo.wtZip}
								</td>
							</tr>
							<tr>
								<td id="rowsTd" class="active" rowspan="7" style="text-align: center;"><label>检<br>测<br>要<br>求</label></td>
								 <td class="active"><label class="pull-right">检测类型:</label></td>
								<td>
									${vo.taskType}
								</td>
								<td class="active"><label class="pull-right">样品名称:</label></td>
								<td>
									${vo.sampName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">立项日期:</label></td>
								<td>
									${vo.rdate}
								</td>
								<td class="active"><label class="pull-right">项目频次:</label></td>
								<td>
									${vo.pc} ${vo.pcUnit}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">单次周期:</label></td>
								<td>
									${vo.cycle}
									${vo.cycleUnit}
								</td>
								<td class="active"><label class="pull-right">拟完成日期:</label></td>
								<td>
									${vo.finishDate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">报告份数:</label></td>
								<td>
									${vo.reportNum}
								</td>
								<td class="active"><label class="pull-right">取报告方式:</label></td>
								<td>
									${vo.reportWay}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;急:</label></td>
								<td>
									${vo.jj}
								</td>
								<td class="active"><label class="pull-right ">现场踏勘:</label></td>
								<td class="zcytd">
									${vo.xctk}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">委托方提供资料:</label></td>
								<td colspan="3">
									${vo.info}
								</td>
							</tr>
							<tr>
								<th class="active"><label class="pull-right">上传文件:</label></th>
								<td colspan="3">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
									 	<div style="float: left;margin-right: 10px;">
										 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
									 	</div>
									 </c:forEach>
								</td>
							</tr>
							<tr>
								<td class="active" rowspan="2" style="text-align: center;"><label>受<br>理<br>方<br>信<br>息</label></td>
								<td class="active"><label class="pull-right">立&nbsp;&nbsp;项&nbsp;&nbsp;人:</label></td>
								<td>
									${vo.userName}
								</td>
								<td class="active"><label class="pull-right ">受理日期:</label></td>
								<td>
									${vo.sdate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
<script type="text/javascript">
$('input[type="file"]').prettyFile();
 
$(document).ready(function() {
	//多选框内删除
	$(".chosen-container").hover(function(){  
        $(this).addClass('chosen-container-active');  
    },function(){  
    	 $(this).removeClass('chosen-container-active');  
    })
	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green",
	});
	
});
function showIM(id){
	parent.layer.open({
		title:'已选项目方法列表',	
		type: 2,
		area: ['700px', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}/bus/im/list4Im.do?busId='+id,
	});
}
</script>
</body>
</html>
