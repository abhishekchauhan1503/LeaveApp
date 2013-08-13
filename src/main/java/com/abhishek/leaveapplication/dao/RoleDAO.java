package com.abhishek.leaveapplication.dao;

import com.abhishek.leaveapplication.model.Role;

public interface RoleDAO {
	public long createRole(Role roleType) throws Exception;
	public Role getRole(long roleId) throws Exception;
}
