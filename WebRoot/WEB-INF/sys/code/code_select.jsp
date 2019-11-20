<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
</head>
<body>
         <div class="col-sm-12" style="height: 100%">
           <div class="ibox float-e-margins">
                <table style="width: 100%;height: 100%">
                	<tr>
                		<td>
                			 <textarea id="textarea" class="form-control" style="width:100%;height:100%; font-size:36px"></textarea>
                		</td>
                	</tr>
                </table>
           </div>
    	</div>
    <%@ include file="../../include/js.jsp"%>
    <script>
    $.ajax({
	    url: 'ajaxCodeContent.do?code=globle-tsfh',
	    datatype : "json",
	    success: function(data) {  
			$("#textarea").html(data);
	    }
	});
    </script>
</body>
</html>
