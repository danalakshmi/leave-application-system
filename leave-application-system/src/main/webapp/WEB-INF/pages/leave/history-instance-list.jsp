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
  	(function($) {
  		$(document).ready(function(){
  			$.acflow.tableGrid('#tableList',{title:'<spring:message code="history.instance.list"/>'});  			
  		});
  		
  	})(jQuery);
	-->
	  </script>
	  </head>
<body>
	
  	<div id="showLoading" style="margin: 10px">
	  	<div><img src="../images/info_16.png" /><spring:message code="system.current.page"/>：<spring:message code="flow.management"/>-&gt;<spring:message code="history.instance.list"/></div>
	  	<div style="width: 100%;height: 400px;padding-top: 6px">
	    <table id="tableList">
	    	<thead>
	    		<tr>
	    			<th field="count" width="60" align="center"><spring:message code="task.num"/></th>
	    			<th field="id" width="60" align="center"><spring:message code="task.id"/></th>
	    			<th field="processDefinitionId" width="150" align="center"><spring:message code="instance.key"/></th>
	    			<th field="startUserId" width="150" align="center"><spring:message code="history.requester"/></th>
	    			<th field="startTime" width="150" align="center"><spring:message code="history.request.time"/></th>
	    			<th field="opAction" width="150" align="center"><spring:message code="task.action"/></th>
	    		</tr>
	    	</thead>
	    	<tbody>
	    		<c:forEach items="${historicProcessinstanceList}" var="historicProcessinstance" varStatus="stat">
	    		<tr>
	    			<td>${stat.count}</td>
	    			<td>${historicProcessinstance.id}</td>
	    			<td>${historicProcessinstance.processDefinitionId}</td>
	    			<td>${historicProcessinstance.startUserId}</td>
	    			<td><fmt:formatDate type="both" value="${historicProcessinstance.startTime}" /></td>
	    			<td>&nbsp;
	    			</td>
	    		</tr>
	    		</c:forEach>
	    	</tbody>
	    </table>
	    <div style="text-align: center;padding-top: 12px">
	    	${pageNavi }
	    </div>
	    </div>
	    
	    <form id="showUrlForm" action="" target="_blank" method="post"></form>
    </div>

</body>
</html>