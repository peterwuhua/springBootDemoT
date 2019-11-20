<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="favicon.ico">
    <%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>头像上传</h5>
                </div>
                <div class="ibox-content">
                    <input type="hidden" name="id" id="id" value="${vo.id}">
                    <form method="post" action="" class="form-horizontal" >
                        <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
                            <tr>
                                <td class="active" width="150"><label class="pull-right">上传图片:</label></td>
                                <td>
                                    <a class="btn btn-info" href="javascript:void(0);" onclick="fileUpload();" style="float: left;margin-right: 5px;">在线上传</a>
                                </td>
                            </tr>
                            <tr>
                                <td class="active"> </td>
                                <td>
                                    <img alt="" src="${basePath}static/upload/${(vo.avatar==null||vo.avatar=='')?'avatar/profile-pic.jpg':vo.avatar}" height="400" width="400">
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
<script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${basePath}h/js/content.js?v=1.0.0"></script>
<script src="${basePath}h/js/plugins/layer/layer.min.js"></script>
</body>
<script>
    function fileUpload(){
        var id='${vo.id}';
        layer.open({
            title:'头像上传',
            type: 2,
            area: ['700px', '400px'],
            fix: false, //不固定
            maxmin: true,
            content: 'upload.do?id='+id,
            end: function () {
                location.reload();
            }
        });
    }
</script>
</html>
