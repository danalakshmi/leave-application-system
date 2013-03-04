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
			    		parent.$.acflow.addFrameTab({title:'流程实例',href:url,isReload:true,frameId:'processInstanceFrame'});
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
	  	<div><img src="../images/info_16.png" />当前位置：工作流管理-&gt;启动流程</div>
	  	<div style="width: 100%;height: 400px;padding-top: 6px">
	  		<table cellpadding="5" cellspacing="10" bgcolor="efefef" >
	  			<tr>
	  				<td colspan="2" align="centre"><b>休假申请</b></td>
	  			</tr>
	  			<tr>
	  				<td>申请人</td>
	  				<td >
		   				<form:input path="employeeName" />
	  				</td>
	  			</tr>
	  			<tr>
	  				<td>休假时长</td>
	  				<td>
	  					<form:input path="numberOfDays" size="1" />
	  				</td>
	  			</tr>
	  			<tr>
	  				<td>休假开始日期</td>
	  				<td>
						<input type="date" name="startDate"/>
						<br>(YYYY-MM-DD)
	  				</td>
	  			</tr>
	  			<tr>
	  				<td>休假结束日期</td>
	  				<td>
	  					<input type="date" name="returnDate"/>
	  					<br>(YYYY-MM-DD)
	  				</td>
	  			</tr>
	  			<tr>
	  				<td>
	  				<td>
						<label>
					        <input type="checkbox" name="vacationPay"/> 带薪休假
						</label>
	  				</td>
	  			</tr>
	  			<tr>
	  				<td>
	  					休假原因
	  				<td>
					    <form:textarea path="vacationMotivation" />
	  				</td>
	  			</tr>
	  			<tr>
	  				<td></td>
	  				<td>
	  					<form:hidden path="processDefinitionId"/>
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

</body>
</html>