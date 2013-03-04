package com.paul.leave.service;

import com.paul.leave.valueObject.Leave;

public interface LeaveService {
	
	void save(Leave leave);
	
	void update(Leave leave);
	
	void delete(Leave leave);
	
	Leave findByLeaveCode(String id);

}
