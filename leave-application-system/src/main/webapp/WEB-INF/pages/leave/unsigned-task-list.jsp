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
  			$.acflow.tableGrid('#tableList',{title:'<spring:message code="task.unasing.list"/>'});  		
  			
  		});
  		
  		function submitForm(taskId){
  			var action = "${ctx}/leave/claim-task.do";
  			var src = "${ctx}/leave/unsigned-task-list.do";
  			
  			var row = $('#tableList').datagrid('getSelected');
			if (row){
				$.messager.confirm('<spring:message code="task.pls.confirm"/>','<spring:message code="task.will.you.handle"/>- '+ row.id +' <spring:message code="task.num"/>',function(r){
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
	  	<div><img src="../images/info_16.png" /><spring:message code="system.current.page"/>：<spring:message code="flow.management"/>-&gt;<spring:message code="task.unasing.list"/></div>
	  	<div style="width: 100%;height: 400px;padding-top: 6px">
	    <table id="tableList">
	    	<thead>
	    		<tr>
	    			<th field="count" width="60" align="center"><spring:message code="task.num"/></th>
	    			<th field="id" width="40" align="center"><spring:message code="task.id"/></th>
	    			<th field="processInstanceId" width="80" align="center"><spring:message code="task.instance.id"/></th>
	    			<th field="name" width="180" align="center"><spring:message code="task.name"/></th>
	    			<th field="description" width="350" align="center"><spring:message code="task.desc"/></th>
	    			<th field="opAction" width="150" align="center"><spring:message code="task.action"/></th>
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
	    			<td><a href="javascript:submitForm(${task.id});" ><spring:message code="task.take.me"/></a>
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