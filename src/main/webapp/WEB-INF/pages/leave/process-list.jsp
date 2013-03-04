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
  			$.acflow.tableGrid('#tableList',{title:'启动流程'});  			
  		});
  		
  	})(jQuery);
  
	function showDiagram(id){
		var url='${ctx}/leave/show-diagram.do?processDefinitionId='+id;
		parent.$.acflow.addFrameTab({title:'流程图',href:url,isReload:true,frameId:'diagramFrameID1',iconCls:'icon-process'});
	}
	
	function startProcess(id){
		//var url='${ctx}/leave/show-diagram.do?processDefinitionId='+id;
		var url='${ctx}/leave/start-process.do?processDefinitionId='+id;
		parent.$.acflow.addFrameTab({title:'填写请假单',href:url,isReload:true,frameId:'startProcessFrameID1',iconCls:'icon-processInst'});		
 	}
	
	-->
	  </script>
	  </head>
<body>
	
  	<div id="showLoading" style="margin: 10px">
	  	<div><img src="../images/info_16.png" />当前位置：工作流管理-&gt;启动流程</div>
	  	<div style="width: 100%;height: 400px;padding-top: 6px">
	    <table id="tableList">
	    	<thead>
	    		<tr>
	    			<th field="name" width="150" align="center">名称</th>
	    			<th field="key" width="150" align="center">KEY</th>
	    			<th field="id" width="150" align="center">ID</th>
	    			<th field="opAction" width="150" align="center">操作</th>
	    		</tr>
	    	</thead>
	    	<tbody>
	    		<c:forEach items="${processDefinitionList}" var="processDefinition" varStatus="stat">
	    		<tr>
	    			<td>${processDefinition.name}</td>
	    			<td>${processDefinition.key}</td>
	    			<td>${processDefinition.id}</td>
	    			<td><a href="javascript:startProcess('${processDefinition.id}');" >启动此流程</a>
					|&nbsp;&nbsp;<a href="javascript:showDiagram('${processDefinition.id }');" >查看流程图</a>
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