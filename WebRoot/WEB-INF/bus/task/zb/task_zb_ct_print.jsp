<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<style type="text/css">
body{
	line-height: inherit;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="form-group">
				<div class="col-sm-12" style="text-align: center;">
					<a class="btn btn-w-m btn-info" href="javascript:;" onclick="prn1_preview()"><i class="fa fa-search" aria-hidden="true"></i> 打印预览</a>
					<a class="btn btn-w-m btn-info" href="javascript:;" onclick="prn_Design()"><i class="fa fa-search" aria-hidden="true"></i> 打印设计</a>
					<a class="btn btn-w-m btn-success" href="javascript:;" onclick="prn1_print()"><i class="fa fa-print" aria-hidden="true"></i> 直接打印</a>
				</div>
			</div>
			<div class="hr-line-dashed"></div>
			<div class="ibox-content" id="tm_div">
				<table class="table table-bordered" style="width:700px;">
					<thead>
						<tr>
							<td align="center" style="width: 50px;">序号</td>
						 	<td align="center">名称</td>
					 		<td align="center" style="width: 100px">数量</td>
				 		</tr>
					</thead>
					<tbody>
						<c:forEach items="${ctlist}" var="e" varStatus="v">
					 		<tr>
					 			<td align="center">${v.index+1}</td>
					 			<td>${e.container}</td>
					 			<td align="center">${e.num}</td>
					 		</tr>
					 	</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
<script src="${basePath}h/js/plugins/qrcode/jquery.qrcode.min.js"></script>
<script src="${basePath}static/js/Lodop/LodopFuncs.js?basePath=${basePath}"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="${basePath}static/js/Lodop/install_lodop64.exe"></embed>
</object>

<script type="text/javascript">
function prn1_preview() {	
	CreateOneFormPage();	
	LODOP.PREVIEW();	
};
function prn1_print() {		
	CreateOneFormPage();
	LODOP.PRINT();	
};
function prn_Design() {		
	CreateOneFormPage();
	LODOP.PRINT_DESIGN();		
};	
function CreateOneFormPage(){
	LODOP=getLodop();  
	LODOP.PRINT_INIT("条码打印");
	LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
	var strBodyStyle='<style type="text/css" id="style">'+
	'.table-bordered {border: 1px solid #EBEBEB;border-collapse: collapse;}'+
	'td{border: 1px solid #EBEBEB;vertical-align: middle; height: 39px;padding: 8px;line-height: 1.42857143;}'
	+'</style>';
	var htmlct=strBodyStyle+'<body><div>'+$('#tm_div').html()+'</div></body>';
	LODOP.ADD_PRINT_HTM(50,50,'RightMargin:50px','BottomMargin:50px',htmlct);
	LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",1);//横向时的正向显示
};  
</script>
</body>
</html>
