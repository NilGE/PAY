<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
<title>${PROJECT_TITLE}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="viewport" content="width=device-width">
<%@include file="./common/css.jsp"%>


</head>
<body>
	<div class="navbar navbar-inverse navbar-red" role="navigation">
		<div class="navbar-header">
			<div class="logo">
				<h1>${PROJECT_TITLE}</h1>
			</div>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">${PROJECT_TITLE}</span> <span class="icon-bar"></span>
				<span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
		</div>
	</div>
	<div class="template-page-wrapper">
		<div class="navbar-collapse collapse templatemo-sidebar">
			<%@include file="./common/nav.jsp"%>
		</div>
		<!--/.navbar-collapse -->


		<div class="templatemo-content-wrapper">
			<div class="templatemo-content">
				<ol class="breadcrumb">
					<li><a href="../user/index">首页</a></li>
					<li class="active">修改密码</li>
				</ol>

				<div class="clear"></div>

				<form action="../user/updatePwd" method="post"
					class="form-horizontal templatemo-signin-form">
					<div class="col-md-6 margin-bottom-15 cfg_add_input">
						<label for="firstName" class="control-label">原始密码</label> <input
							type="password" class="form-control" name="oldPwd"
							placeholder="原始密码">
					</div>

					<div class="col-md-6 margin-bottom-15 cfg_add_input">
						<label for="firstName" class="control-label">重置密码</label> <input
							type="password" class="form-control" name="newPwd" id="pwd"
							placeholder="重置密码">
					</div>

					<div class="col-md-6 margin-bottom-15 cfg_add_input">
						<label for="firstName" class="control-label">重复密码</label> <input
							type="password" class="form-control" name="" id="repwd"
							placeholder="重复密码">

					</div>
					<div class="clear"></div>
					<button type="submit" onclick="return verifyPwd();"
						class="btn btn-primary">确认</button>
					<button type="reset" class="btn btn-default" data-dismiss="modal">取消</button>
				</form>

			</div>
		</div>

		<!-- 修改密码提示信息 -->
		<c:if test="${!empty message }">
			<script>alert("${message}");</script>	
		</c:if>



		<%@include file="./common/footer.jsp"%>
		<%@include file="./common/js.jsp"%>

		<script type="text/javascript">
			function verifyPwd() {
				var pwd = $("#pwd").val();
				var repwd = $("#repwd").val();
				if (pwd == null || repwd == null || pwd == "" || repwd == "") {
					alert("密码不能为空");
					return false;
				}
				if (pwd != repwd) {
					alert("请确认密码与重复密码一致！");
					return false;
				}
				return true;
			}
		</script>
	</div>


</body>
</html>





