<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
					<li class="active">缴费周期管理</li>
				</ol>

				<div class="clear"></div>

				<div class="col-md-12 col-sm-12">

					<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
					<!-- 常用功能：添加、导入、导出、批量删除 -->	
					<a href="javascript:;" class="btn btn-info" data-toggle="modal" data-target="#add_item">添加缴费周期</a> 
					<a href="javascript:;" class="btn btn-primary" data-toggle="modal" data-target="#import_item">导入缴费周期</a> 
					</c:if>
					
					<a href="../duration/exportExcel" class="btn btn-success">导出缴费周期</a>
					<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
					<a href="javascript:deleteAll();" class="btn btn-danger">批量删除</a>
					</c:if>

					<table class="table table-striped">
						<thead>
							<tr><th><input type="checkbox" id="selectAll" /> 序号</th>
								
								<th>交费开始日期</th>
								<th>交费结束日期</th>
								<th>工作名称</th>
								<th>通知内容</th>
								<th>是否有效</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>

							<c:if test="${!empty pu.show}">
								<c:forEach items="${pu.show}" var="item" varStatus="s">
									<tr onmouseover="updateItem('${item.id}','${item.startDate}','${item.endDate}', '${item.name}','${item.content}','${item.valid}');">
										<td><input type="checkbox" name="subBox" value="${item.id}"/>${s.count}</td>	
										
										<td>${item.startDate }</td>
										<td>${item.endDate }</td>
										<td>${item.name}</td>
										<td>${fn:substring(item.content,0, 10)}</td>
										<td>
										<c:choose>
											<c:when test="${item.valid}"><i class="fa fa-check"></i></c:when>
											<c:otherwise><i class="fa fa-times"></i></c:otherwise>
										</c:choose>
										</td>
										
										<td>
										<a href="../duration/duration_content?duration_id=${item.id}"  class="btn btn-default">查看</a>
											
										<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
										<a href="javascript:;" data-toggle="modal"
											data-target="#edit_item" class="btn btn-default">编辑</a> <a
											href="javascript:;" class="btn btn-link" data-toggle="modal"
											data-target="#del_item">删除</a>
										</c:if>
										</td>

									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
					${pu.code1 }


				</div>
			</div>
		</div>

<!-- 以下为弹出区域，支持常用功能 -->
		<!-- 导入条目 -->
		<div class="modal fade" id="import_item" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="../duration/importExcel" method="POST" enctype="multipart/form-data">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">导入缴费周期数据</h4>
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="exampleInputFile">选择缴费周期数据导入文件</label> 
							<input class="file" type="file" name="myfiles" />
							<p class="help-block">导入格式为 xls/xlsx</p>
						</div>
						<div class="clear"></div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">上传</button>
							<button type="reset" class="btn btn-default" data-dismiss="modal">取消</button>
						</div>
					</form>
				</div>
			</div>
		</div>


		<!-- 添加条目 -->
		<div class="modal fade" id="add_item" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">

					<form action="addDuration" method="post">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">添加条目</h4>
						</div>
					
						<div class="col-md-6 margin-bottom-15 cfg_add_input ">
							<label for="firstName" class="control-label" >交费开始日期</label> <input
								type="date" class="form-control" id="item_startDate_add"
								name="startDate" placeholder="交费开始日期">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input ">
							<label for="firstName" class="control-label">交费结束日期</label> <input
								type="date" class="form-control" id="item_endDate_add"
								name="endDate" placeholder="交费结束日期">
						</div>
						
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">通知名称</label> <input
								type="text" class="form-control" id="item_name_add"
								name="name" placeholder="工作名称">
						</div>
						
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">通知内容</label> 
							<textarea rows="4" cols="50" name="content" id="item_content_add" 
							placeholder="通知内容"></textarea>
							
						</div>
						
						
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">是否有效</label> <select
								name="valid" id="item_valid_add">
								<option value='true'>开启</option>
								<option value='false'>关闭</option>
								</select>
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

					<form action="updateDuration" method="post">
						<input type="hidden" name="id" id="item_id_edit" />

						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">编辑条目</h4>
						</div>

						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">交费开始日期</label> <input
								type="date" class="form-control" id="item_startDate_edit"
								name="startDate" placeholder="交费开始日期">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">交费结束日期</label> <input
								type="date" class="form-control" id="item_endDate_edit"
								name="endDate" placeholder="交费结束日期">
						</div>
						
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">工作名称</label> <input
								type="text" class="form-control" id="item_name_edit"
								name="name" placeholder="工作名称">
						</div>
						
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">通知内容</label> 
							<textarea rows="4" cols="50" name="content" id="item_content_edit" 
							placeholder="通知内容"></textarea>
							
						</div>
						
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">是否有效</label> <select
								name="valid" id="item_valid_edit">
								<option value='true'>开启</option>
								<option value='false'>关闭</option>
								</select>
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
					<form action="deleteDuration" method="post">
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
			function updateItem(item_id, item_startDate, item_endDate,
					item_name,item_content, item_valid) {
				$("#item_id_del").val(item_id);

				$("#item_id_edit").val(item_id);
				$("#item_startDate_edit").val(item_startDate);
				$("#item_endDate_edit").val(item_endDate);
				$("#item_name_edit").val(item_name);
				$("#item_content_edit").html(item_content);
				
				$("#item_valid_edit").val(item_valid);
			}
		</script>
		
		<script type="text/javascript">
			//如果点击全选按钮
			$("#selectAll").click(function() {
				$("input[name='subBox']").prop("checked", this.checked);
			});
			//如果点击每个按钮
			$("input[name='subBox']").click(
				function() {
					var $subs = $("input[name='subBox']");
					$("#selectAll").prop("checked", $subs.length == $subs.filter(":checked").length ? true : false);
			});
			//删除全部
			function deleteAll(){
				var ids = "";
				var total = 0;
				var checklist = document.getElementsByName("subBox");
					for (var i = 0; i < checklist.length; i++) {
						if (checklist[i].checked == true) {
							ids += checklist[i].value;
							ids += ",";
							total ++;
						}
					}
					ids = ids.substring(0, ids.length-1);
					if(total == 0){
						alert("您未勾选要删除的记录！");
						return false;
						return false();
					}else{
						if(!confirm("您是否要删除 "+total+" 条记录？")){
							return false;
							return false();
						}else{
							if(ids != "") window.location.href="../duration/deleteAllDuration?ids="+ids;						
						}	
					}
				}
		</script>
		
	</div>


</body>
</html>





