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
			    		parent.$.acflow.addFrameTab({title:'<spring:message code="task.my.task"/>',href:url,isReload:true,frameId:'myTaskFrame',iconCls:'icon-process'});
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
	  				<td colspan="2" align="centre"><b><spring:message code="task.adjudgement"/></b></td>
	  			</tr>
	  			<tr>
	  				<td valign="top"><spring:message code="task.reason"/></td>
	  				<td >
		   				<spring:message code="task.reject.action"/>
						<c:out value="${leave.numberOfDays}" />
						<spring:message code="task.leave.day"/>. <br /> 
						<spring:message code="task.reject.reason"/>:
						<c:out value="${leave.managerMotivation}" />
						<form:hidden path="managerMotivation" />
	  				</td>
	  			</tr>
	  			<tr>
	  				<td><spring:message code="task.leave.days"/></td>
	  				<td>
						<form:input	path="numberOfDays" size="1" />
					</td>
	  			</tr>
	  			<tr>
	  				<td><spring:message code="task.pay.leave"/></td>
	  				<td>
						<input type="checkbox" name="vacationPay" />
	  				</td>
	  			</tr>
	  			<tr>
	  				<td><spring:message code="task.leave.reson"/></td>
	  				<td>
	  					<form:textarea	path="vacationMotivation" />
	  				</td>
	  			</tr>
	  			<tr>
	  				<td valign="top"><spring:message code="task.resend"/><br><spring:message code="task.resend.approve"/></td>
	  				<td>
	  					<label for="resendRequest1"><form:radiobutton
							path="resendRequest" value="1" /><spring:message code="task.resend.yes"/></label><br /> 
						<label for="resendRequest2"><form:radiobutton
							path="resendRequest" value="0" /><spring:message code="task.resend.no"/></label>
	  				</td>
	  			</tr>
	  			<tr>
	  				<td></td>
	  				<td>
	  					<form:hidden path="taskId"/>
	  					<form:hidden path="employeeName" />
						<form:hidden path="id" />
						<form:hidden path="vacationApproved" />
						<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:$('#leave').submit();"><spring:message code="task.submit"/></a>
		   				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#leave').form('clear')"><spring:message code="task.reset"/></a>
	  				</td>
	  			</tr>
	  		</table>
	    </div>
    </div>
	</form:form>
</body>
</html>