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
					<li class="active">记录管理</li>
				</ol>

				<div class="clear"></div>

				<div class="col-md-12 col-sm-12">



					<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
					<a href="javascript:;" class="btn btn-default" data-toggle="modal"
						data-target="#add_item" disabled="disabled">添加记录</a> <a href="javascript:;"
						class="btn btn-primary" data-toggle="modal"
						data-target="#import_item">导入记录</a> 
					</c:if>	
						<a href="../record/exportExcel"
						class="btn btn-success">导出记录</a>
						<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
						<a href="javascript:deleteAll();"
						class="btn btn-danger">批量删除</a>
						</c:if>


					<c:if test='${ sessionScope.groupCode eq "ADMIN"}'>
						<form class=" pull-right" action="../record/view_record">
							<select name="campus_id" id="item_campus_add" class="select_lg">
								<option value="">全部</option>
								<c:forEach var="campus" items="${campusList}">
									<option value="${campus.id}" <c:if test='${campus.id eq campus_id }'>selected="selected"</c:if>>${campus.campusName}</option>
								</c:forEach>
							</select>
							<button class="btn btn-primary" type="submit">搜索</button>
						</form>
					</c:if>


					<div id="mouse_horwheel" class="requestpay_div">
						<table class="table table-striped table-hover pay_item_table ">
							<thead>
								<tr>
									<th><input type="checkbox" id="selectAll" /> 序号</th>
									<th>缴费序号</th>
									<th>缴费日期</th>
									<th>报名编号</th>
									<th>奥鹏卡号</th>
									<th id="college_th">院校</th>
									<th>院校学号</th>
									<th>管理中心</th>
									<th id="campus_th">学习中心</th>
									<th>批次代码</th>
									<th>入学批次</th>
									<th>层次</th>
									<th>专业名称</th>
									<th>姓名</th>
									<th>身份证号码</th>
									<th>费用科目</th>
									<th>金额</th>
									<th>费用来源</th>

									<th id="operation_th">操作</th>
								</tr>
							</thead>
							<tbody>

								<c:if test="${!empty pageBean.list}">
									<c:forEach items="${pageBean.list}" var="item" varStatus="s">
										<tr
											onmouseover="updateItem('${item.id}','${item.payid}','${item.paydate}','${item.regNO}','${item.aoPengNO}','${item.college}','${item.stuid}','${item.manageCenter}','${item.campusName}','${item.campusCode}','${item.levelName}','${item.termName}','${item.majorName}','${item.name}','${item.identity}','${item.item}','${item.money}','${item.way}');">

											<td><input type="checkbox" name="subBox" value="${item.id }"/>${s.count }</td>
											<td>${item.payid }</td>
											<td>${item.paydate }</td>
											<td>${item.regNO }</td>
											<td>${item.aoPengNO }</td>
											<td>${item.college }</td>
											<td>${item.stuid }</td>
											<td>${item.manageCenter }</td>
											<td>${item.campusName }</td>
											<td>${item.campusCode }</td>
											<td>${item.levelName }</td>
											<td>${item.termName }</td>
											<td>${item.majorName }</td>
											<td>${item.name }</td>
											<td>${item.identity }</td>
											<td>${item.item }</td>
											<td>${item.money }</td>
											<td>${item.way }</td>

											<td><a href="javascript:;" data-toggle="modal"
												data-target="#edit_item" class="btn btn-default"
												disabled="disabled">编辑</a> <a href="javascript:;"
												class="btn btn-link" data-toggle="modal"
												data-target="#del_item" disabled="disabled">删除</a></td>


										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>

					</div>

					<div class="pager clearfix text-center">
						<form name="pageForm" action="../record/view_record" method="POST">
							<input type="hidden" name="campus.id" value="${campus_id }">
							${pageBean.page.pageInfor}</form>
					</div>



				</div>
			</div>
		</div>


		<!-- 导入条目 -->
		<div class="modal fade" id="import_item" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="../record/importExcel" method="POST" enctype="multipart/form-data">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">导入数据</h4>
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="exampleInputFile">选择专业数据导入文件</label> 
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

					<form action="addRecord" method="post">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">添加条目</h4>
						</div>

						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">缴费序号</label> <input
								type="text" class="form-control" id="item_payid_add"
								name="payid" placeholder="缴费序号">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">缴费日期</label> <input
								type="text" class="form-control" id="item_paydate_add"
								name="paydate" placeholder="缴费日期">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">报名编号</label> <input
								type="text" class="form-control" id="item_regNO_add"
								name="regNO" placeholder="报名编号">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">奥鹏卡号</label> <input
								type="text" class="form-control" id="item_aoPengNO_add"
								name="aoPengNO" placeholder="奥鹏卡号">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">院校</label> <input
								type="text" class="form-control" id="item_college_add"
								name="college" placeholder="院校">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">院校学号</label> <input
								type="text" class="form-control" id="item_stuid_add"
								name="stuid" placeholder="院校学号">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">管理中心</label> <input
								type="text" class="form-control" id="item_manageCenter_add"
								name="manageCenter" placeholder="管理中心">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">学习中心</label> <input
								type="text" class="form-control" id="item_campusName_add"
								name="campusName" placeholder="学习中心">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">批次代码</label> <input
								type="text" class="form-control" id="item_campusCode_add"
								name="campusCode" placeholder="批次代码">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">入学批次</label> <input
								type="text" class="form-control" id="item_levelName_add"
								name="levelName" placeholder="入学批次">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">层次</label> <input
								type="text" class="form-control" id="item_termName_add"
								name="termName" placeholder="层次">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">专业名称</label> <input
								type="text" class="form-control" id="item_majorName_add"
								name="majorName" placeholder="专业名称">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">姓名</label> <input
								type="text" class="form-control" id="item_name_add" name="name"
								placeholder="姓名">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">身份证号码</label> <input
								type="text" class="form-control" id="item_identity_add"
								name="identity" placeholder="身份证号码">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">费用科目</label> <input
								type="text" class="form-control" id="item_item_add" name="item"
								placeholder="费用科目">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">金额</label> <input
								type="text" class="form-control" id="item_money_add"
								name="money" placeholder="金额">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">费用来源</label> <input
								type="text" class="form-control" id="item_way_add" name="way"
								placeholder="费用来源">
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

					<form action="updateRecord" method="post">
						<input type="hidden" name="id" id="item_id_edit" />

						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">编辑条目</h4>
						</div>

						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">缴费序号</label> <input
								type="text" class="form-control" id="item_payid_edit"
								name="payid" placeholder="缴费序号">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">缴费日期</label> <input
								type="text" class="form-control" id="item_paydate_edit"
								name="paydate" placeholder="缴费日期">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">报名编号</label> <input
								type="text" class="form-control" id="item_regNO_edit"
								name="regNO" placeholder="报名编号">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">奥鹏卡号</label> <input
								type="text" class="form-control" id="item_aoPengNO_edit"
								name="aoPengNO" placeholder="奥鹏卡号">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">院校</label> <input
								type="text" class="form-control" id="item_college_edit"
								name="college" placeholder="院校">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">院校学号</label> <input
								type="text" class="form-control" id="item_stuid_edit"
								name="stuid" placeholder="院校学号">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">管理中心</label> <input
								type="text" class="form-control" id="item_manageCenter_edit"
								name="manageCenter" placeholder="管理中心">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">学习中心</label> <input
								type="text" class="form-control" id="item_campusName_edit"
								name="campusName" placeholder="学习中心">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">批次代码</label> <input
								type="text" class="form-control" id="item_campusCode_edit"
								name="campusCode" placeholder="批次代码">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">入学批次</label> <input
								type="text" class="form-control" id="item_levelName_edit"
								name="levelName" placeholder="入学批次">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">层次</label> <input
								type="text" class="form-control" id="item_termName_edit"
								name="termName" placeholder="层次">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">专业名称</label> <input
								type="text" class="form-control" id="item_majorName_edit"
								name="majorName" placeholder="专业名称">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">姓名</label> <input
								type="text" class="form-control" id="item_name_edit" name="name"
								placeholder="姓名">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">身份证号码</label> <input
								type="text" class="form-control" id="item_identity_edit"
								name="identity" placeholder="身份证号码">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">费用科目</label> <input
								type="text" class="form-control" id="item_item_edit" name="item"
								placeholder="费用科目">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">金额</label> <input
								type="text" class="form-control" id="item_money_edit"
								name="money" placeholder="金额">
						</div>
						<div class="col-md-6 margin-bottom-15 cfg_add_input">
							<label for="firstName" class="control-label">费用来源</label> <input
								type="text" class="form-control" id="item_way_edit" name="way"
								placeholder="费用来源">
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
					<form action="deleteRecord" method="post">
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
		<script src="../templates/default/admin/js/horwheel.min.js"></script>
		<script src="../templates/default/admin/js/build.js"></script>
		<script>
			//鼠标滚轮控制横向滚动
			var horwheel = require('horwheel'), view = document
					.getElementById('mouse_horwheel');
			horwheel(view);
		</script>

		<script type="text/javascript">
			function updateItem(item_id, item_payid, item_paydate, item_regNO,
					item_aoPengNO, item_college, item_stuid, item_manageCenter,
					item_campusName, item_campusCode, item_levelName,
					item_termName, item_majorName, item_name, item_identity,
					item_item, item_money, item_way) {
				$("#item_id_del").val(item_id);

				$("#item_id_edit").val(item_id);
				$("#item_payid_edit").val(item_payid);
				$("#item_paydate_edit").val(item_paydate);
				$("#item_regNO_edit").val(item_regNO);
				$("#item_aoPengNO_edit").val(item_aoPengNO);
				$("#item_college_edit").val(item_college);
				$("#item_stuid_edit").val(item_stuid);
				$("#item_manageCenter_edit").val(item_manageCenter);
				$("#item_campusName_edit").val(item_campusName);
				$("#item_campusCode_edit").val(item_campusCode);
				$("#item_levelName_edit").val(item_levelName);
				$("#item_termName_edit").val(item_termName);
				$("#item_majorName_edit").val(item_majorName);
				$("#item_name_edit").val(item_name);
				$("#item_identity_edit").val(item_identity);
				$("#item_item_edit").val(item_item);
				$("#item_money_edit").val(item_money);
				$("#item_way_edit").val(item_way);
			}
		</script>
		<script type="text/javascript">
			//如果点击全选按钮
			$("#selectAll").click(function() {
				$("input[name='subBox']").prop("checked", this.checked);
				calTotal();
			});
			//如果点击每个按钮
			$("input[name='subBox']") .click(
				function() {
					var $subs = $("input[name='subBox']");
					$("#selectAll") .prop( "checked", $subs.length == $subs .filter(":checked").length ? true : false);
				});
			
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
					//alert(ids);
					if(total == 0){
						alert("您未勾选要删除的记录！");
						return false;
						return false();
					}else{
						if(!confirm("您是否要删除 "+total+" 条记录？")){
							return false;
							return false();
						}
						else{
							if(ids != ""){
								window.location.href="../record/deleteAllRecord?ids="+ids;
							}else{
								
							}
						}	
					}
					
					
				}
		</script>
	</div>


</body>
</html>





