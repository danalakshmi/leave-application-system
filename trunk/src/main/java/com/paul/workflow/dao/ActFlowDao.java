package com.paul.workflow.dao;

import java.util.List;

public interface ActFlowDao {
	
	/**
	 * 
	 * @param processInstId
	 * @param taskKey
	 * @return
	 */
	public List<String> findTaskAssingUserList(String processInstId,String taskKey);

}
