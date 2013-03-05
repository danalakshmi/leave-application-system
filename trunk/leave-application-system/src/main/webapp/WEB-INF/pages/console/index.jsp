<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>[<spring:message code="system.name" text="default text" />]</title>
    <%@ include file="/commons/meta.jsp" %>
    <%@ include file="/commons/meta-easyui.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx }/scripts/jquery/zTree/zTreeStyle/zTreeStyle.css" />
    <link rel="stylesheet" type="text/css" href="${ctx }/scripts/jquery/zTree/zTreeStyle/zTreeIcons.css" />
	<link rel="stylesheet" type="text/css" href="${ctx }/styles/icon_app.css" />	
	<link rel="stylesheet" type="text/css" href="${ctx }/styles/index_layout.css" />
	<script type="text/javascript" src="${ctx }/scripts/utils/jquery.json-2.2.min.js"></script>	
	<script type="text/javascript" src="${ctx }/scripts/jquery/zTree/jquery-ztree-2.5.min.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/appjs/index_layout.js"></script>
	<script type="text/javascript">
	<!--
		$(document).ready(function(){
			$.acflow.init();
		});
	-->
	</script>
  </head>
<body class="easyui-layout">
	<div region="north" border="false" style="height:30px;">
		<div class="header-box"><h1><spring:message code="system.name" text="default text" />
			<a href="?language=en_US">en</a> | <a href="?language=zh_CN">cn</a></h1>
		</div>
	</div>
	<div region="west" title="<spring:message code="system.menu"/>" split="true" style="width:120px;" border="false">
		<div id="menu-accordion-id">
			<div title="<spring:message code="flow.management"/>">
		    	<div class="icon_center">
					<a class="icon" href="javascript:void(0);" title="<spring:message code="start.flow"/>" name="${ctx }/leave/start-leave.do" 
						iconCls="icon-processInst"	frameId='startProcessFrame'>
						<img src="../images/icons/process.png" />
						<spring:message code="start.flow"/>
					</a>
					<a class="icon" href="javascript:void(0);" title="<spring:message code="my.task"/>" name="${ctx }/leave/my-task-list.do" 
						iconCls="icon-process"	frameId='myTaskFrame'>
						<img src="../images/icons/processInst.png"/>
						<spring:message code="my.task"/>
					</a>
					<a class="icon" href="javascript:void(0);" title="<spring:message code="unasign.task"/>" name="${ctx }/leave/unsigned-task-list.do" 
						iconCls="icon-process-dy"	frameId='unsignedTaskFrame'>
						<img src="../images/icons/personTask.png" />
						<spring:message code="unasign.task"/>
					</a>
					<a class="icon" href="javascript:void(0);" title="<spring:message code="flow.instance"/>" name="${ctx }/leave/instance-list.do" 
						iconCls="icon-comon-info"	frameId='processInstanceFrame'>
						<img src="../images/icons/shareTask.png" />
						<spring:message code="flow.instance"/>
					</a>
					<a class="icon" href="javascript:void(0);" title="<spring:message code="history.instance"/>" name="${ctx }/leave/history-instance-list.do" 
						iconCls="icon-comon-info"	frameId='historyInstanceFrame'>
						<img src="../images/icons/report.png" />
						<spring:message code="history.instance"/>
					</a>
				</div>
		    </div>
		</div>
	</div>
	<div region="center">
		<!-- tab -->
		<div id="tabs_center">
			<div title="<spring:message code="system.home"/>">
			
			</div>
		</div>
	</div>
</body>

</html>
