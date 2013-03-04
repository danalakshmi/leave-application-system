package com.paul.leave.dao;

import com.paul.leave.valueObject.Leave;

public interface LeaveDao {

	void save(Leave leave);
	
	void update(Leave leave);
	
	void delete(Leave leave);
	
	Leave findByLeaveCode(String id);
}
