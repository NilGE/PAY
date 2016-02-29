<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:choose>
	<c:when test="${!empty sessionScope.admin_login}">
		<ul class="templatemo-sidebar-menu">
			<li>
				<form class="navbar-form">
					<!-- 			<input type="text" class="form-control" id="templatemo_search_box"
				placeholder="搜索..."> <span class="btn btn-default">Go</span> -->
				</form>
			</li>
			<li class="active"><a href="../user/index"><i
					class="fa fa-home"></i>首页</a></li>
			<li class="sub  <c:if test='${nav_tag=="CONFIG"}'>open</c:if>"><a
				href="javascript:;"> </i><i class="fa fa-cog"></i> 基本设置
					<div class="pull-right">
						<span class="caret"></span>
					</div>
			</a>
				<ul class="templatemo-submenu">
					<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
						<li><a href="../campus/view_campus">学习中心管理</a></li>
					</c:if>
					<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
						<li><a href="../major/view_major">专业管理</a></li>
					</c:if>
					<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
						<li><a href="../level/view_level">层次管理</a></li>
					</c:if>
					<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
						<li><a href="../term/view_term">批次管理</a></li>
					</c:if>
					<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
						<li><a href="../user/view_user">用户管理</a></li>
					</c:if>
					<li><a href="../user/view_password">修改密码</a></li>
				</ul></li>

			<li class="sub <c:if test='${nav_tag=="PAYMENT"}'>open</c:if>"><a
				href="javascript:;"> <i class="fa fa-suitcase"></i> 学费管理
					<div class="pull-right">
						<span class="caret"></span>
					</div>
			</a>
				<ul class="templatemo-submenu">

					<li><a href="../duration/view_duration">缴费周期及通知</a></li>

					<li><a href="../price/view_price">查看学费标准</a></li>

					<li><a href="../term/view_dynamic">查看批次及分成比例</a></li>

					<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
						<li><a href="../standard/view_standard">设定结算批次学分标准</a></li>
					</c:if>
					
						<li><a href="../percent/view_percent">查看弹性返款比例</a></li>
				
				</ul></li>




			<li class="sub <c:if test='${nav_tag=="RECORD"}'>open</c:if>"><a
				href="javascript:;"><i class="fa fa-pie-chart"></i> 数据分析
					<div class="pull-right">
						<span class="caret"></span>
					</div> </a>
				<ul class="templatemo-submenu">

					<li><a href="../record/view_record">查看导入记录</a></li>
					<li><a href="../record/analyseRecord">统计各中心缴费情况</a></li>

				</ul></li>


			<li><a href="javascript:;" data-toggle="modal"
				data-target="#confirmModal"><i class="fa fa-sign-out"></i>注销</a></li>
		</ul>

	</c:when>
	<c:otherwise>
		<body onload="javascript:window.location.href='../admin/login.jsp'"></body>
	</c:otherwise>
</c:choose>