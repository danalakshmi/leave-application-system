package com.paul.leave.dao.impl;

import org.springframework.stereotype.Repository;

import com.paul.base.dao.BaseDao;
import com.paul.leave.dao.LeaveDao;
import com.paul.leave.valueObject.Leave;

@Repository("leaveDao")
public class LeaveDaoImpl extends BaseDao<Leave, String> implements LeaveDao {

	/*public void save(Leave leave) {
		getHibernateTemplate().save(leave);
	}

	public void update(Leave leave) {
		getHibernateTemplate().update(leave);
	}

	public void delete(Leave leave) {
		getHibernateTemplate().delete(leave);
	}

	public Leave findByLeaveCode(String id) {
		List<Leave> list = getHibernateTemplate().find("from Leave where id=?",id);
		return (Leave)list.get(0);
	}*/

}