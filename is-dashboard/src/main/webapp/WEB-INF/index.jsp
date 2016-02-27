<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
	String path = request.getContextPath();
	String rootPath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
	request.setAttribute("rootPath", rootPath);
	pageContext.setAttribute("newLineChar", "\n");
	
%>

<title>调度管理</title>
<!--
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
-->
<script src="<%=basePath%>/scripts/jquery.min.js"></script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>

* {
	margin: 0;
	padding: 0;
	font-family: Lato;
}

body {
	padding: 0px;
	background: #f6f3f7 - #111;
}

.flatTable {
	width: 100%;
	min-width: 500px;
	border-collapse: collapse;
	font-weight: bold;
	color: #6b6b6b;
}

tr {
	height: 50px;
	background: #D4D1D5;
	border-bottom: rgba(0, 0, 0, .05) 1px solid;
	font-size: 10pt;
	font-weight: normal;
}

td {
	box-sizing: border-box;
	padding-left: 30px;
}

.titleTr {
	height: 70px;
	color: #f6f3f7;
	background: #418a95;
	border: 0px solid;
	font-weight: bold;
	font-size: 20px;
}

.plusTd {
	background: url(<%=basePath%>/images/future_projects.png) center center no-repeat,
	rgba(0, 0, 0, .1);
}

.controlTd {
	position: relative;
	width: 80px;
	background: url(<%=basePath%>/images/mrg.png) center center no-repeat;
	cursor: pointer;
}

.headingTr {
	height: 30px;
	background: #63ACB7;
	color: #f6f3f7;
	font-size: 12pt;
	border: 0px solid;
}

.button {
	text-align: center;
	cursor: pointer;
}

.sForm {
	position: absolute;
	top: 0;
	right: -400px;
	width: 400px;
	height: 100%;
	background: #f6f3f7 - #222;
	overflow: hidden;
	transition: width 1s, right .3s;
	padding: 0px;
	box-sizing: border-box; 
}
. close { 
   float : right;
	height: 70px;
	width: 80px;
	padding-top: 25px;
	box-sizing: border-box;
	background: rgba(255, 0, 0, .4);
}
	
.title {
	width: 100%;
	height: 70px;
	padding-top: 20px;
	padding-left: 20px;
	box-sizing: border-box;
	background: rgba(0, 0, 0, .1);
}

.open {
	right: 0;
	width: 400px !important;
}

.settingsIcons {
	position: absolute;
	top: 0;
	right: 0;
	width: 0;
	overflow: hidden;
}

.display {
	width: 300px;
}

.settingsIcon {
	float: right;
	background: #418a95;
	color: #f6f3f7;
	height: 50px;
	width: 80px;
	padding-top: 15px;
	box-sizing: border-box;
	text-align: center;
	overflow: hidden;
	transition: width 1s;
}

.settingsIcon:hover {
	background: #418a95Dark;
}

tr:nth-child(3) .settingsIcon { 
    height:51px;
}
.openIcon {
	width: 80px;
}
</style>
<script type="text/javaScript">

	$(".button").click(function() {
		$("#sForm").toggleClass("open");
	});

	$(".controlTd").click(function() {
		$(this).children(".settingsIcons").toggleClass("display");
		$(this).children(".settingsIcon").toggleClass("openIcon");
	});
	
	function validateAdd() {
			if ($.trim($('#jobName').val()) == '') {
				alert('name不能为空！');
				$('#jobName').focus();
				return false;
			}
			if ($.trim($('#jobGroup').val()) == '') {
				alert('group不能为空！');
				$('#jobGroup').focus();
				return false;
			}
			if ($.trim($('#cronExpression').val()) == '') {
				alert('cron表达式不能为空！');
				$('#cronExpression').focus();
				return false;
			}
			if ($.trim($('#beanClass').val()) == '' && $.trim($('#springId').val()) == '') {
				$('#beanClass').focus();
				alert('类路径和spring id至少填写一个');
				return false;
			}
			if ($.trim($('#methodName').val()) == '') {
				$('#methodName').focus();
				alert('方法名不能为空！');
				return false;
			}
			return true;
		}
		function add() {
			if (validateAdd()) {
				showWaitMsg();
				$.ajax({
					type : "POST",
					async : false,
					dataType : "JSON",
					cache : false,
					url : "${basePath}task/add",
					data : $("#addForm").serialize(),
					success : function(data) {
						hideWaitMsg();
						if (data.flag) {

							location.reload();
						} else {
							alert(data.msg);
						}

					}//end-callback
				});//end-ajax
			}
		}
		function changeJobStatus(jobId, cmd) {
			showWaitMsg();
			$.ajax({
				type : "POST",
				async : false,
				dataType : "JSON",
				cache : false,
				url : "${basePath}task/changeJobStatus",
				data : {
					jobId : jobId,
					cmd : cmd
				},
				success : function(data) {
					hideWaitMsg();
					if (data.flag) {

						location.reload();
					} else {
						alert(data.msg);
					}

				}//end-callback
			});//end-ajax
		}
		function updateCron(jobId) {
			var cron = prompt("输入cron表达式！", "")
			if (cron) {
				showWaitMsg();

				$.ajax({
					type : "POST",
					async : false,
					dataType : "JSON",
					cache : false,
					url : "${basePath}task/updateCron",
					data : {
						jobId : jobId,
						cron : cron
					},
					success : function(data) {
						hideWaitMsg();
						if (data.flag) {
							location.reload();
						} else {
							alert(data.msg);
						}

					}//end-callback
				});//end-ajax
			}

		}
		function showWaitMsg(msg) {
			if (msg) {

			} else {
				msg = '正在处理，请稍候...';
			}
			var panelContainer = $("body");
			$("<div id='msg-background' class='datagrid-mask' style=\"display:block;z-index:10006;\"></div>").appendTo(panelContainer);
			var msgDiv = $("<div id='msg-board' class='datagrid-mask-msg' style=\"display:block;z-index:10007;left:50%\"></div>").html(msg).appendTo(
					panelContainer);
			msgDiv.css("marginLeft", -msgDiv.outerWidth() / 2);
		}
		function hideWaitMsg() {
			$('.datagrid-mask').remove();
			$('.datagrid-mask-msg').remove();
		}
		function updateParams(jobId){
			var extParams = prompt("输入参数！", "")
			if (extParams) {
				showWaitMsg();

				$.ajax({
					type : "POST",
					async : false,
					dataType : "JSON",
					cache : false,
					url : "${basePath}task/updateExtParams",
					data : {
						jobId : jobId,
						extParams : extParams
					},
					success : function(data) {
						hideWaitMsg();
						if (data.flag) {
							location.reload();
						} else {
							alert(data.msg);
						}

					}//end-callback
				});//end-ajax
			}
		}
</script>
</head>
<body>


<table class="flatTable">
	
	<tr class="titleTr">
		<td class="titleTd" colspan="4">爱钱进.借啊调度管理</td>
		<td colspan="5"></td>
		<td class="plusTd button"></td>
	</tr>
	
	<tbody>
	<tr class="headingTr">
		<td width="15%">name</td>
		<td width="5%">group</td>
	    <td width="10%">cron表达式</td>
		<td width="10%">描述</td>
		<td width="5%">同步</td>
		<td width="5%">spring id</td>
		<td width="10%">方法名</td>
		<td width="15%">绑定参数</td>
		<td width="15%" colspan="2">操作</td>
	</tr>

    <c:forEach var="job" items="${taskList}">
    <tr <c:if test="${job.jobStatus=='1' }">style="color:#0AA770"</c:if>>
        <td>${job.jobName }</td>
        <td>${job.jobGroup }</td>
        <td>${job.cronExpression }</td>
        <td>${job.description }</td>
        <td>${job.isConcurrent }</td>
        <td>${job.springId }</td>
        <td>${job.methodName }</td>
        <td>${job.extParams }</td>
        <td colspan="2">
            <c:choose>
                <c:when test="${job.jobStatus=='1' }">
                    <a href="javascript:;"
                        onclick="changeJobStatus('${job.jobId}','stop')">停止</a>&nbsp;
                </c:when>
                <c:otherwise>
                    <a href="javascript:;"
                        onclick="changeJobStatus('${job.jobId}','start')">开启</a>&nbsp;
                </c:otherwise>
            </c:choose>
            <a href="javascript:;" onclick="updateParams('${job.jobId}')">更新参数</a>
            &nbsp;
            <a href="javascript:;" onclick="updateCron('${job.jobId}')">更新cron</a>
        </td>
    </tr>
    </tbody>
    
    </c:forEach>
				<!--
				<tr>
					<td>n</td>
					<td><input type="text" name="jobName" id="jobName"></input></td>
					<td><input type="text" name="jobGroup" id="jobGroup"></input></td>
					<td>0<input type="hidden" name="jobStatus" value="0"></input></td>
					<td><input type="text" name="cronExpression"
						id="cronExpression"></input></td>
					<td><input type="text" name="description" id="description"></input></td>
					<td><select name="isConcurrent" id="isConcurrent">
							<option value="1">1</option>
							<option value="0">0</option>
					</select></td>
					<td><input type="text" name="beanClass" id="beanClass"></input></td>
					<td><input type="text" name="springId" id="springId"></input></td>
					<td><input type="text" name="methodName" id="methodName"></input></td>
					<td><input type="button" onclick="add()" value="保存" /></td>
				</tr>
			 -->
</table>

</body>
</html>
