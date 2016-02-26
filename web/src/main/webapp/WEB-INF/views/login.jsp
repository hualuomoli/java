<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%
	String path = request.getContextPath();
	System.out.println("path:" + path);
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
	System.out.println("basePath:" + basePath);
%>
</head>
<body>
	<form method="post" action="${basePath}/login">
		<table>
			<tr>
				<td colspan="2" id="message">${message}</td>
			</tr>
			<tr>
				<td>userName</td>
				<td><input type="text" name="userName" /></td>
			</tr>
			<tr>
				<td>password</td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="commit" /></td>
				<td><input type="button" value="asynchronous"
					onclick="doLogin();" /></td>
			</tr>
		</table>
	</form>
	<script
		src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
		function doLogin() {
			var userName = $('input[name="userName"]').val();
			var password = $('input[name="password"]').val();
			var entity = {
				"userName" : userName,
				"password" : password
			};
			$.post("${basePath}/login/check", entity, function(data) {
				if ("ok" === data.ok) {
					window.location.href = "${basePath}/" + data.indexPath;
				} else {
					$('#message').html(data.message);
				}
			}, "json");
		}
	</script>
</body>
</html>