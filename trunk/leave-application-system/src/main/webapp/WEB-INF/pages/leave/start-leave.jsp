<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<body>
	<h2><a href="start.do"><font color="red">流程列表</font></a> </h2>

	<table border="1">
		<tr bgcolor="ededed">
			<th>Name</th>
			<th>Key</th>
			<th>id</th>
			<th>Action</th>
		</tr>
		<c:forEach var="processDefinition" items="${processDefinitionList}">
			<tr>
				<td><c:out value="${processDefinition.name}" />
				</td>
				<td><c:out value="${processDefinition.key}" />
				</td>
				<td><c:out value="${processDefinition.id}" />
				</td>
				<td><a href="start-process.do?processDefinitionId=${processDefinition.id}" >启动此流程</a>
					|&nbsp;&nbsp;<a href="show-diagram.do?processDefinitionId=${processDefinition.id}" target="_blank">查看流程图</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<hr>
	<br>
	<h2><font color="blue">我的任务</font></h2>
	
	<table border="1">
		<tr bgcolor="ededed">
			<th>编号</th>
			<th>ID</th>
			<th>名称</th>
			<th>注释</th>
			<th>动作</th>
		</tr>
		<c:forEach var="task" items="${tasksList}" varStatus="stat">
			<tr>
				<td><c:out value="${stat.count}" />
				</td>
				<td><c:out value="${task.id}" />${stat.index}
				</td>
				<td><c:out value="${task.name}" />
				</td>
				<td><c:out value="${task.description}" />
				</td>
				<td><a href="get-taskForm.do?taskId=${task.id}" >处理此任务</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<hr>
	<br>
	<h2><font color="green">未分配任务</font></h2>
	<table border="1">
		<tr bgcolor="ededed">
			<th>ID</th>
			<th>名称</th>
			<th>注释</th>
			<th>动作</th>
		</tr>
		<c:forEach var="task" items="${unassignedTaskList}">
			<tr>
				<td><c:out value="${task.id}" />
				</td>
				<td><c:out value="${task.name}" />
				</td>
				<td><c:out value="${task.description}" />
				</td>
				<td><a href="claim-task.do?taskId=${task.id}" >接手此任务</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<hr>
	<br>
	<h2><font color="orange">流程实例列表</font></h2>
	<table border="1">
		<tr bgcolor="ededed">
			<th>ID</th>
			<th>KEY</th>
			<th>动作</th>
		</tr>
		<c:forEach var="processInstance" items="${processInstanceList}">
			<tr>
				<td><c:out value="${processInstance.id}" />
				</td>
				<td><c:out value="${processInstance.businessKey}" />
				</td>
				<td><a href="show-diagram.do?processInstanceId=${processInstance.id}" target="_blank">轨迹图</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<hr>
	<br>
	<h2><font color="pink">历史流程实例列表</font></h2>
	<table border="1">
		<tr bgcolor="ededed">
			<th>编号</th>
			<th>ID</th>
			<th>KEY</th>
			<th>发起人</th>
			<th>发起时间</th>
			<th>动作</th>
		</tr>
		<c:forEach var="historicProcessinstance" items="${historicProcessinstanceList}" varStatus="stat">
			<tr>
				<td><c:out value="${stat.count}" />
				</td>
				<td><c:out value="${historicProcessinstance.id}" />
				</td>
				<td><c:out value="${historicProcessinstance.processDefinitionId}" />
				</td>
				<td><c:out value="${historicProcessinstance.startUserId}" />
				</td>
				<td><fmt:formatDate type="both" value="${historicProcessinstance.startTime}" />
				</td>
				<td>&nbsp;
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>