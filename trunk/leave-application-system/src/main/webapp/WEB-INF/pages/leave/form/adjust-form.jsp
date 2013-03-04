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
			    		parent.$.acflow.addFrameTab({title:'我的任务',href:url,isReload:true,frameId:'myTaskFrame',iconCls:'icon-process'});
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
	  	<div><img src="../images/info_16.png" />当前位置：工作流管理-&gt;任务处理</div>
	  	<div style="width: 100%;height: 400px;padding-top: 6px">
	  		<table cellpadding="5" cellspacing="10" bgcolor="efefef" >
	  			<tr>
	  				<td colspan="2" align="centre"><b>调整请假申请</b></td>
	  			</tr>
	  			<tr>
	  				<td valign="top">事由</td>
	  				<td >
		   				你的经理否决了你要休
						<c:out value="${leave.numberOfDays}" />
						天的休假申请. <br /> 拒绝原因:
						<c:out value="${leave.managerMotivation}" />
						<form:hidden path="managerMotivation" />
	  				</td>
	  			</tr>
	  			<tr>
	  				<td>休假天数</td>
	  				<td>
						<form:input	path="numberOfDays" size="1" />
					</td>
	  			</tr>
	  			<tr>
	  				<td>带薪休假？</td>
	  				<td>
						<input type="checkbox" name="vacationPay" />
	  				</td>
	  			</tr>
	  			<tr>
	  				<td>休假原因</td>
	  				<td>
	  					<form:textarea	path="vacationMotivation" />
	  				</td>
	  			</tr>
	  			<tr>
	  				<td valign="top">是否重新发给<br>经理审批？</td>
	  				<td>
	  					<label for="resendRequest1"><form:radiobutton
							path="resendRequest" value="1" />是</label><br /> 
						<label for="resendRequest2"><form:radiobutton
							path="resendRequest" value="0" />否</label>
	  				</td>
	  			</tr>
	  			<tr>
	  				<td></td>
	  				<td>
	  					<form:hidden path="taskId"/>
	  					<form:hidden path="employeeName" />
						<form:hidden path="id" />
						<form:hidden path="vacationApproved" />
						<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:$('#leave').submit();">提交</a>
		   				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#leave').form('clear')">重置</a>
	  				</td>
	  			</tr>
	  		</table>
	    </div>
    </div>
	</form:form>
</body>
</html>