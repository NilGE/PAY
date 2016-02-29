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
					<li class="active">角色管理</li>
				</ol>

				<div class="clear"></div>

				<div class="col-md-12 col-sm-12">


					<a href="javascript:;" class="btn btn-info" data-toggle="modal"
						data-target="#add_item">添加角色</a>





					<div class="btn-group pull-right" id="templatemo_sort_btn">
						<button type="button" class="btn btn-default">排序</button>
						<button type="button" class="btn btn-default dropdown-toggle"
							data-toggle="dropdown">
							<span class="caret"></span> <span class="sr-only">Toggle
								Dropdown</span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">角色名称</a></li>
							<li><a href="#">角色代码</a></li>
							<li><a href="#">更新时间</a></li>
						</ul>
					</div>

					<form class=" pull-right">
						<input type="text" class="form-control" id="templatemo_search_box"
							placeholder="搜索..."> <span class="btn btn-primary">搜索</span>
					</form>

					<table class="table table-striped">
						<thead>
							<tr>
								<th>ID</th>
<th>权限组名称</th>
<th>权限组代码</th>

								<th>操作</th>
							</tr>
						</thead>
						<tbody>

							<c:if test="${!empty groupList}">
								<c:forEach items="${groupList}" var="item">
									<tr
										onmouseover="updateItem('${item.id}','${item.groupName}','${item.groupCode}');">
										
										<td>${item.id }</td><td>${item.groupName }</td><td>${item.groupCode }</td>
										
										<td><a href="javascript:;" class="btn btn-default"
											data-toggle="modal" data-target="#edit_item"
											class="btn btn-default">编辑</a> <a href="javascript:;"
											class="btn btn-link" data-toggle="modal"
											data-target="#del_item">删除</a></td>


									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>

					<ul class="pagination pull-right">
						<li class="disabled"><a href="#">&laquo;</a></li>
						<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
						<li><a href="#">2 <span class="sr-only">(current)</span></a></li>
						<li><a href="#">3 <span class="sr-only">(current)</span></a></li>
						<li><a href="#">4 <span class="sr-only">(current)</span></a></li>
						<li><a href="#">5 <span class="sr-only">(current)</span></a></li>
						<li><a href="#">&raquo;</a></li>
					</ul>

				</div>
			</div>
		</div>



		<!-- 添加条目 -->
		<div class="modal fade" id="add_item" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">

					<form action="addGroup" method="post">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">添加条目</h4>
						</div>
						
						<div class="col-md-6 margin-bottom-15 cfg_add_input"><label for="firstName" class="control-label">权限组名称</label> <input type="text" class="form-control" id="item_groupName_add" name="groupName" placeholder="权限组名称"></div><div class="col-md-6 margin-bottom-15 cfg_add_input"><label for="firstName" class="control-label">权限组代码</label> <input type="text" class="form-control" id="item_groupCode_add" name="groupCode" placeholder="权限组代码"></div>
					
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

					<form action="updateGroup" method="post">
						<input type="hidden" name="id" id="item_id_edit" />

						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">编辑条目</h4>
						</div>
						
						<div class="col-md-6 margin-bottom-15 cfg_add_input"><label for="firstName" class="control-label">权限组名称</label> <input type="text" class="form-control" id="item_groupName_edit" name="groupName" placeholder="权限组名称"></div><div class="col-md-6 margin-bottom-15 cfg_add_input"><label for="firstName" class="control-label">权限组代码</label> <input type="text" class="form-control" id="item_groupCode_edit" name="groupCode" placeholder="权限组代码"></div>
				
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
					<form action="deleteGroup" method="post">
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
			function updateItem(item_id,item_groupName,item_groupCode) {
				$("#item_id_del").val(item_id);
				
				$("#item_id_edit").val(item_id);$("#item_groupName_edit").val(item_groupName);$("#item_groupCode_edit").val(item_groupCode);
			}
		</script>
	</div>


</body>
</html>





