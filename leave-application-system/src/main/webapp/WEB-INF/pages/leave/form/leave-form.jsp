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
			    		var url='${ctx}/leave/instance-list.do';
			    		parent.$.acflow.addFrameTab({title:'<spring:message code="flow.instance"/>',href:url,isReload:true,frameId:'processInstanceFrame'});
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

  	<form:form method="POST" commandName="leave" action="start-process.do">
	<div id="showLoading" style="margin: 10px">
	  	<div><img src="../images/info_16.png" /><spring:message code="system.current.page"/>：<spring:message code="flow.management"/>-&gt;<spring:message code="instance.start"/>启动流程</div>
	  	<div style="width: 100%;height: 400px;padding-top: 6px">
	  		<table cellpadding="5" cellspacing="10" bgcolor="efefef" >
	  			<tr>
	  				<td colspan="2" align="centre"><b><spring:message code="task.type"/></b></td>
	  			</tr>
	  			<tr>
	  				<td><spring:message code="task.leave.starter"/></td>
	  				<td >
		   				<form:input path="employeeName" />
	  				</td>
	  			</tr>
	  			<tr>
	  				<td><spring:message code="task.leave.days"/></td>
	  				<td>
	  					<form:input path="numberOfDays" size="1" />
	  				</td>
	  			</tr>
	  			<tr>
	  				<td><spring:message code="task.leave.start.day"/></td>
	  				<td>
						<input type="date" name="startDate"/>
						<br>(YYYY-MM-DD)
	  				</td>
	  			</tr>
	  			<tr>
	  				<td><spring:message code="task.leave.end.day"/></td>
	  				<td>
	  					<input type="date" name="returnDate"/>
	  					<br>(YYYY-MM-DD)
	  				</td>
	  			</tr>
	  			<tr>
	  				<td>
	  				<td>
						<label>
					        <input type="checkbox" name="vacationPay"/> <spring:message code="task.pay.leave"/>
						</label>
	  				</td>
	  			</tr>
	  			<tr>
	  				<td>
	  					<spring:message code="task.leave.reson"/>
	  				<td>
					    <form:textarea path="vacationMotivation" />
	  				</td>
	  			</tr>
	  			<tr>
	  				<td></td>
	  				<td>
	  					<form:hidden path="processDefinitionId"/>
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

</body>
</html>