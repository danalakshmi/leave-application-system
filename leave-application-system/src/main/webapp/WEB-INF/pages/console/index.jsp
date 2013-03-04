<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>[工作流管理系统]</title>
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
		<div class="header-box"><h1>工作流管理系统</h1></div>
	</div>
	<div region="west" title="系统菜单" split="true" style="width:120px;" border="false">
		<div id="menu-accordion-id">
			<div title="请假流程管理">
		    	<div class="icon_center">
					<a class="icon" href="javascript:void(0);" title="启动流程" name="${ctx }/leave/start-leave.do" 
						iconCls="icon-processInst"	frameId='startProcessFrame'>
						<img src="../images/icons/process.png" />
						启动流程
					</a>
					<a class="icon" href="javascript:void(0);" title="我的任务" name="${ctx }/leave/my-task-list.do" 
						iconCls="icon-process"	frameId='myTaskFrame'>
						<img src="../images/icons/processInst.png"/>
						我的任务
					</a>
					<a class="icon" href="javascript:void(0);" title="未分配任务" name="${ctx }/leave/unsigned-task-list.do" 
						iconCls="icon-process-dy"	frameId='unsignedTaskFrame'>
						<img src="../images/icons/personTask.png" />
						未分配任务
					</a>
					<a class="icon" href="javascript:void(0);" title="流程实例" name="${ctx }/leave/instance-list.do" 
						iconCls="icon-comon-info"	frameId='processInstanceFrame'>
						<img src="../images/icons/shareTask.png" />
						流程实例
					</a>
					<a class="icon" href="javascript:void(0);" title="历史流程实例" name="${ctx }/leave/history-instance-list.do" 
						iconCls="icon-comon-info"	frameId='historyInstanceFrame'>
						<img src="../images/icons/report.png" />
						历史流程实例
					</a>
				</div>
		    </div>
		</div>
	</div>
	<div region="center">
		<!-- tab -->
		<div id="tabs_center">
			<div title="首页">
			
			</div>
		</div>
	</div>
</body>

</html>
