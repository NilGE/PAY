<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<!-- Modal -->
<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">是否确定注销?</h4>
			</div>
			<div class="modal-footer">
				<a href="../user/adminLogout" class="btn btn-primary">是</a>
				<button type="button" class="btn btn-default" data-dismiss="modal">否</button>
			</div>
		</div>
	</div>
</div>


<footer class="templatemo-footer">
	<div class="templatemo-copyright">
		<p>Copyright &copy; 2015 ${PROJECT_TITLE}</p>
	</div>
</footer>
