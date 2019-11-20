<%@ taglib uri="/WEB-INF/tld/jstl/c.tld" prefix="c"%>
<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
 <script>
     if('${status.status}'=='success'){
     	parent.toastr.success('${status.message}', '');    	
     }else if('${status.status}'=='error'){
     	parent.toastr.error('${status.message}', '');  
     }
 </script>
