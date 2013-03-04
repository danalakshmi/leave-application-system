<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
  <%@ include file="/commons/meta.jsp" %>
  <%@ include file="/commons/meta-easyui.jsp" %>  
  <script type="text/javascript" src="${ctx }/scripts/utils/ajaxupload.js"></script>
  <script type="text/javascript" src="${ctx }/scripts/appjs/common.js"></script> 
  <link href="${ctx }/scripts/jquery/showLoading/css/showLoading.css" rel="stylesheet" media="screen" />
  <script type="text/javascript" src="${ctx }/scripts/jquery/showLoading/js/jquery.showLoading.js"></script>
  <script type="text/javascript">
  <!--
  		$(document).ready(function(){
  			$.acflow.tableGrid('#tableList',{title:'未分配任务列表'});  		
  			
  		});
  		
  		function submitForm(taskId){
  			var action = "${ctx}/leave/claim-task.do";
  			var src = "${ctx}/leave/unsigned-task-list.do";
  			
  			var row = $('#tableList').datagrid('getSelected');
			if (row){
				$.messager.confirm('请确认','你打算接手此- '+ row.id +' 任务吗？?',function(r){
					if (r){
						
			  			$('#showLoading').showLoading();
			  			$.get(action,{taskId:taskId},function(result){
			  				$('#showLoading').hideLoading();
							if (result == "bad"){
								$.messager.show('Error', data, 'error');
							} else {
								parent.$("#unsignedTaskFrame").attr("src",src);
							}
						});
						
					}
				});
			}
  			
  			
  		}
  		
	-->
	  </script>
	  </head>
<body>
	
  	<div id="showLoading" style="margin: 10px">
	  	<div><img src="../images/info_16.png" />当前位置：工作流管理-&gt;未分配任务</div>
	  	<div style="width: 100%;height: 400px;padding-top: 6px">
	    <table id="tableList">
	    	<thead>
	    		<tr>
	    			<th field="count" width="60" align="center">编号</th>
	    			<th field="id" width="40" align="center">ID</th>
	    			<th field="processInstanceId" width="80" align="center">流程实例ID</th>
	    			<th field="name" width="180" align="center">名称</th>
	    			<th field="description" width="350" align="center">注释</th>
	    			<th field="opAction" width="150" align="center">操作</th>
	    		</tr>
	    	</thead>
	    	<tbody>
	    		<c:forEach items="${unassignedTaskList}" var="task" varStatus="stat">
	    		<tr>
	    			<td>${stat.count}</td>
	    			<td>${task.id }</td>
	    			<td>${task.processInstanceId}</td>
	    			<td>${task.name }</td>
	    			<td>${task.description }</td>
	    			<td><a href="javascript:submitForm(${task.id});" >接手此任务</a>
	    			</td>
	    		</tr>
	    		</c:forEach>
	    	</tbody>
	    </table>
	    <div style="text-align: center;padding-top: 12px">
	    	${pageNavi }
	    </div>
	    </div>
	    
	    <form id="showUrlForm" action="" method="get"></form>
    </div>

</body>
</html>