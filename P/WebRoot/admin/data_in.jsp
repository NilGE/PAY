<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
<title>${PROJECT_TITLE}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="viewport" content="width=device-width">
<%@include file="./common/css.jsp"%>
<%@include file="./common/js.jsp"%>
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
		<!-- 页面主体内容 -->


		<div class="templatemo-content-wrapper">
			<div class="templatemo-content">
				<ol class="breadcrumb">
					<li><a href="../admin/index.jsp">首页</a></li>
					<li class="active">数据导入</li>
				</ol>

				<div class="clear"></div>

				<div class="col-md-6 col-sm-6">

					<form action="../record/importExcel" method="POST"
						enctype="multipart/form-data">

						<div class="col-md-6 margin-bottom-30">
							<label for="exampleInputFile">选择学习中心数据导入文件</label> <input
								class="file" type="file" name="myfiles" />
							<p class="help-block">导入格式为 xls</p>
							<button type="submit" class="btn btn-primary">上传</button>
							<button type="reset" class="btn btn-default">重置</button>
						</div>
					</form>




				</div>

			</div>

		</div>



		<%@include file="./common/footer.jsp"%>
	</div>
</body>
</html>