package com.wenable.hemanth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wenable.hemanth.beans.TokenBean;
import com.wenable.hemanth.dao.UserDao;
import com.wenable.hemanth.model.User;
import com.wenable.hemanth.utils.TokenUtils;


@Service
public class UserService
{
	
	@Autowired
	UserDao dao;
	@Autowired
	TokenUtils tu;

	public TokenBean getToken(User bean) {
		User user = dao.getByUsernameAndPassword(bean.getUsername(), bean.getPassword());

		TokenBean tokenBean = new TokenBean();
		if (user == null) {
			tokenBean.setToken("Please. Register first");
		} else {
			tokenBean.setToken(tu.getToken(user));
		}

		return tokenBean;
	}

	public User add(User bean, boolean check) {
		User user = null;
		if (bean.getPassword() != null && bean.getUsername() != null) {
			if (check) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "User with given username already exists");
			} else {
				user = dao.add(bean);

			}
		} else {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "username/password cannot be null");
		}

		return user;
	}

	public boolean existByUsername(String username) {

		return dao.existsByUsername(username);
	}

	public List<User> getAllUsers() {

		return dao.getAllUsers();
	}

	public void deleteUser(String id) {

		dao.deleteUser(id);

	}

	public User updateUser(String id, User bean) {

		User user = getOneUser(id);
		if (user != null) {
			if (bean.getPassword() != null) {
				user.setPassword(bean.getPassword());
			}
		}

		return dao.updateUser(user);
	}

	public User getOneUser(String id) {

		return dao.getOneUser(id);
	}

}
