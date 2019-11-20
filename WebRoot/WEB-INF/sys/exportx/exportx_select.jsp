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
                 <textarea id="textarea" style="width: 730px;height: 310px; font-size:36px" rows="17" cols="100"></textarea>
               </div>
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
