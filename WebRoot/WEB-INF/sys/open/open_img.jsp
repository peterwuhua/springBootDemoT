<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
   <link href="${basePath }h/css/font-awesome.css?v=4.4.0" rel="stylesheet">
   <link href="${basePath }h/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
<%@ include file="../../include/css.jsp"%>
	<a class="fancybox" id="img" href="" title=""></a>
    <script src="${basePath}h/js/plugins/peity/jquery.peity.min.js"></script>
    <!-- Fancy box -->
    <script src="${basePath}h/js/plugins/fancybox/jquery.fancybox.js"></script>
    <script>
       /*  $(document).ready(function () {
           
            document.getElementById("img").click();
        }); */
        function openImg(tempPath,user){
        	 var url = '${basePath}'+tempPath;
        	 $("#img").attr("title",user);
        	 $("#img").attr("href",url);
        	 $('.fancybox').fancybox({
                 openEffect: 'none',
                 closeEffect: 'none'
             });
        	document.getElementById("img").click();
        }
    </script>

