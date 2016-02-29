<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
					<li class="active">学习中心管理</li>
				</ol>

				<div class="clear"></div>

				<div class="col-md-12 col-sm-12">

				<a href="../record/exportBackMoney" class="btn btn-success">导出返款情况</a>
				<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
				<a href="../record/exportFile" class="btn btn-primary">导出报表</a>
				</c:if>
				<a href="javascript:clearCache();" class="btn btn-warning">清除缓存数据</a>




					<%-- <c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
						<form class=" pull-right" action="../record/analyseRecord">
							<select name="campus_id" id="item_campus_add" class="select_lg">
								<option value="">全部</option>
								<c:forEach var="campus" items="${campusList}">
									<option value="${campus.id}">${campus.campusName}</option>
								</c:forEach>
							</select>
							<button class="btn btn-primary" type="submit">搜索</button>
						</form>
					</c:if>
 --%>


					<table class="table table-striped">
						<thead>
							<tr>
								<th>序号</th>
								<th>学习中心</th>
								<th>有效缴费人数</th>
								
								<th>固定人数</th>
								<th>动态人数</th>
								<th>缴费总人数</th>
								
								<th>固定缴费金额</th>
								<th>弹性批次有效缴费金额</th>
								<th>总金额</th>
								
								<th>动态批次</th>
								<th>静态批次</th>  
								<th>动态比例</th> 
								<th>固定比例</th> 
								
								<th>固定分成金额</th>
								<th>动态分成金额</th>
								<th>返款总金额</th>

								
							</tr>
						</thead>
						<tbody>

							<c:if test="${!empty pu.show}">
								<c:forEach items="${pu.show}" var="item" varStatus="s">
									<tr
										onmouseover="updateItem('${item.id}','${item.campus.campusName}','${item.campus.campusCode}');">

										<td>${s.count}</td>
										<td>${item.campus.campusName }</td>																			
										<td>${item.valid }</td>
										<td>${item.staticNumber }</td>
										<td>${item.dynamicNumber }</td>
										<td>${item.total }</td>
										
										<td>${item.staticMoney }</td>
										<td>${item.dynamicMoney }</td>
										<td>${item.totalMoney }</td>
										<td><c:if test="${!empty item.dynamicTerm }">${item.dynamicTerm }</c:if></td>
										<td><c:if test="${!empty item.staticTerm }">${item.staticTerm }</c:if></td>
										<td><c:if test="${!empty item.dynamicTerm }"><fmt:formatNumber type="percent">${item.percent }</fmt:formatNumber></c:if></td>
										<td><c:if test="${!empty item.staticTerm }"><fmt:formatNumber type="percent">${item.staticPercent }</fmt:formatNumber></c:if></td>
										
										<td><fmt:formatNumber value="${item.staticBackMoney }"
												pattern="##.##" minFractionDigits="2"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${item.dynamicBackMoney }"
												pattern="##.##" minFractionDigits="2"></fmt:formatNumber></td>
										
										<td><fmt:formatNumber value="${item.backMoney }"
												pattern="##.##" minFractionDigits="2"></fmt:formatNumber></td>

										


									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
					${pu.code1 }


				</div>
			</div>
		</div>



		<!-- 添加条目 -->
		<div class="modal fade" id="add_item" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">

					<form action="addCampus" method="post">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">添加条目</h4>
						</div>

						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">学习中心名称</label> <input
								type="text" class="form-control" id="item_campusName_add"
								name="campusName" placeholder="学习中心名称">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">学习中心代码</label> <input
								type="text" class="form-control" id="item_campusCode_add"
								name="campusCode" placeholder="学习中心代码">
						</div>

						<div class="clear"></div>

						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">添加</button>
							<button type="reset" class="btn btn-default" data-dismiss="modal">取消</button>
						</div>
					</form>

				</div>
			</div>
		</div>


		<!-- 编辑条目 -->
		<div class="modal fade" id="edit_item" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">

					<form action="updateCampus" method="post">
						<input type="hidden" name="id" id="item_id_edit" />

						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">编辑条目</h4>
						</div>

						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">学习中心名称</label> <input
								type="text" class="form-control" id="item_campusName_edit"
								name="campusName" placeholder="学习中心名称">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">学习中心代码</label> <input
								type="text" class="form-control" id="item_campusCode_edit"
								name="campusCode" placeholder="学习中心代码">
						</div>

						<div class="clear"></div>




						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">编辑</button>
							<button type="reset" class="btn btn-default" data-dismiss="modal">取消</button>
						</div>

					</form>
				</div>
			</div>
		</div>

		<!-- 删除条目 -->
		<div class="modal fade" id="del_item" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="deleteCampus" method="post">
						<input type="hidden" name="id" id="item_id_del" />
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">是否确定删除?</h4>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">是</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">否</button>
						</div>

					</form>
				</div>
			</div>
		</div>






		<%@include file="./common/footer.jsp"%>
		<%@include file="./common/js.jsp"%>

		<script type="text/javascript">
			function updateItem(item_id, item_campusName, item_campusCode) {
				$("#item_id_del").val(item_id);

				$("#item_id_edit").val(item_id);
				$("#item_campusName_edit").val(item_campusName);
				$("#item_campusCode_edit").val(item_campusCode);
			}
			
			function clearCache(){
				$.ajax({
					type : "post",
					url : "../record/clearCache",
					/* data : {
						"cwsfglid" : id,
						"status" : status
					}, */
					dataType : "json",//设置需要返回的数据类型
					success : function(data) {				
						alert(data['msg']);
					}
				});
			}
		</script>
	</div>


</body>
</html>





