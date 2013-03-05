<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<html>
<head>
  <%@ include file="/commons/meta.jsp" %>
  <%@ include file="/commons/meta-easyui.jsp" %>  
  <link href="${ctx }/scripts/jquery/showLoading/css/showLoading.css" rel="stylesheet" media="screen" />
  <script type="text/javascript" src="${ctx }/scripts/appjs/common.js"></script> 
  <script type="text/javascript" src="${ctx }/scripts/jquery/showLoading/js/jquery.showLoading.js"></script>
  <script type="text/javascript">
  <!--
	  $(document).ready(function(){
			$('#leave').form({
				onSubmit: function(){  
					$('#showLoading').showLoading();
			        // return false to prevent submit;  
			    },  
			    success:function(data){
			    	$('#showLoading').hideLoading();
			    	if(data == "bad"){
				        $.messager.alert('Info', data, 'info');
			    	}
			    	else{
			    		//alert($('#tabs_center').tabs('select','任务处理').attr('id'));
			    		var parentTabs = parent.$('#tabs_center');
			    		var tab = getSelectedTab();
			    		var url='${ctx}/leave/my-task-list.do';
			    		/*
			    		var taskTab = parentTabs.tabs('getTab','我的任务');
			    		alert(taskTab.panel('options').frameId);
			    		parentTabs.tabs('update',{
								tab: taskTab,
								options:{
									href: url
								}
			    			}
			    		);
			    		parentTabs.tabs('select',taskTab.title);
			    		*/
			    		parent.$.acflow.addFrameTab({title:'<spring:message code="my.task"/>',href:url,isReload:true,frameId:'myTaskFrame',iconCls:'icon-process'});
			    		parentTabs.tabs('close',tab.panel('options').title);
			    	}
			    }
			});
			
		});
  
	function getSelectedTab(){
		var tab = parent.$('#tabs_center').tabs('getSelected');
		return tab;
	}
  
	-->
	  </script>
	  </head>
<body>

  	<form:form method="POST" commandName="leave" action="handle-task-form.do">
	<div id="showLoading" style="margin: 10px">
	  	<div><img src="../images/info_16.png" /><spring:message code="system.current.page"/>：<spring:message code="flow.management"/>-&gt;<spring:message code="task.handle"/></div>
	  	<div style="width: 100%;height: 400px;padding-top: 6px">
	  		<table cellpadding="5" cellspacing="10" bgcolor="efefef" >
	  			<tr>
	  				<td colspan="2" align="centre"><b><spring:message code="task.leave.arrpove"/></b></td>
	  			</tr>
	  			<tr>
	  				<td><spring:message code="task.reason"/></td>
	  				<td >
		   				<c:out value="${leave.employeeName}" /><form:hidden path="employeeName"/><form:hidden path="id"/>
						 	<spring:message code="task.leave.for"/> 
						 <c:out value="${leave.numberOfDays}" /><form:hidden path="numberOfDays"/>
						 	<spring:message code="task.leave.day"/>.
	  				</td>
	  			</tr>
	  			<tr>
	  				<td><spring:message code="task.leave.reson"/></td>
	  				<td><c:out value="${leave.vacationMotivation}" /><form:hidden path="vacationMotivation"/></td>
	  			</tr>
	  			<tr>
	  				<td><spring:message code="task.leave.approve"/></td>
	  				<td>
						<form:select path="vacationApproved">
							<form:option value="1"><spring:message code="task.leave.approve"/></form:option>
							<form:option value="0"><spring:message code="task.leave.reject"/></form:option>
						</form:select>
	  				</td>
	  			</tr>
	  			<tr>
	  				<td><spring:message code="task.reject.reason"/></td>
	  				<td><form:textarea path="managerMotivation"/></td>
	  			</tr>
	  			<tr>
	  				<td></td>
	  				<td>
	  					<form:hidden path="taskId"/>
						<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:$('#leave').submit();"><spring:message code="task.submit"/></a>
		   				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#leave').form('clear')"><spring:message code="task.reset"/></a>
	  				</td>
	  			</tr>
	  		</table>
	    </div>
    </div>
	</form:form>
	<SCRIPT language="javascript">
		function submitme() {
			alert("submit");
			$("#leave").submit();
		}
	</SCRIPT>
	

</body>
</html>