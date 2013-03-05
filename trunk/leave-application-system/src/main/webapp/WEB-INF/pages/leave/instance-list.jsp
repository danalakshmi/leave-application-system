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
  			$.acflow.tableGrid('#tableList',{title:'<spring:message code="instance.list"/>'});  			
  		});
  		
  	})(jQuery);
  
	function showDiagram(id){
		var url='${ctx}/leave/show-diagram.do?processInstanceId='+id;
		parent.$.acflow.addFrameTab({title:'<spring:message code="flow.diagram"/>',href:url,isReload:true,frameId:'diagramFrameID1',iconCls:'icon-process'});
	}
  
	-->
	  </script>
	  </head>
<body>
	
  	<div id="showLoading" style="margin: 10px">
	  	<div><img src="../images/info_16.png" /><spring:message code="system.current.page"/>：<spring:message code="flow.management"/>-&gt;<spring:message code="instance.list"/></div>
	  	<div style="width: 100%;height: 400px;padding-top: 6px">
	    <table id="tableList">
	    	<thead>
	    		<tr>
	    			<th field="count" width="60" align="center"><spring:message code="task.num"/></th>
	    			<th field="id" width="60" align="center"><spring:message code="task.id"/></th>
	    			<th field="name" width="200" align="center"><spring:message code="instance.key"/></th>
	    			<th field="opAction" width="150" align="center"><spring:message code="task.action"/></th>
	    		</tr>
	    	</thead>
	    	<tbody>
	    		<c:forEach items="${processInstanceList}" var="processInstance" varStatus="stat">
	    		<tr>
	    			<td>${stat.count}</td>
	    			<td>${processInstance.id}</td>
	    			<td>${processInstance.businessKey}</td>
	    			<td><a href="javascript:showDiagram('${processInstance.id}');" ><spring:message code="instance.track.diagram"/></a>
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