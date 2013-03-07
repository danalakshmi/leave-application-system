package com.paul.leave.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paul.leave.dao.LeaveDao;
import com.paul.leave.service.LeaveService;
import com.paul.leave.valueObject.Leave;

@Service("leaveService")
@Transactional(readOnly = true)
public class LeaveServiceImpl implements LeaveService {
	
	@Autowired
	private LeaveDao leaveDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
	public void save(Leave leave) {
		leaveDao.save(leave);
	}

	public void update(Leave leave) {
		leaveDao.save(leave);
	}

	public void delete(Leave leave) {
		leaveDao.remove(leave);
	}

	public Leave findByLeaveCode(String id) {
		return leaveDao.find(id);
	}

	public LeaveDao getLeaveDao() {
		return leaveDao;
	}

	public void setLeaveDao(LeaveDao leaveDao) {
		this.leaveDao = leaveDao;
	}

}
