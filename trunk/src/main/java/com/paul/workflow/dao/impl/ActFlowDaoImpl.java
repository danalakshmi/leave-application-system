package com.paul.workflow.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.paul.workflow.dao.ActFlowDao;

@Repository("actFlowDao")
public class ActFlowDaoImpl implements ActFlowDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<String> findTaskAssingUserList(String processInstId,
			String taskKey) {
		String sql="select t.assignee_ from act_hi_taskinst t where t.proc_inst_id_=? and t.task_def_key_=? order by t.start_time_ desc";
		List<String> list=getJdbcTemplate().query(sql, new Object[]{processInstId,taskKey}, new RowMapper<String>(){

			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getString("assignee_");
			}});
		return list;
	}

}
