package com.wenable.hemanth.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wenable.hemanth.beans.TokenBean;
import com.wenable.hemanth.model.User;
import com.wenable.hemanth.service.UserService;



@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService service;
	
	
	
	@PostMapping("/login")
	public ResponseEntity<TokenBean> getJwt(@RequestBody User bean)
	{
		TokenBean token=service.getToken(bean);
		
		return ResponseEntity.ok(token);
	}


	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User bean) {
		boolean check = this.service.existByUsername(bean.getUsername());
		User user = service.add(bean, check);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = service.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable String id) {
		service.deleteUser(id);
		return "User Deleted Successfully!!";
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User bean) {
		User users = service.updateUser(id, bean);
		return ResponseEntity.ok(users);

	}

	@GetMapping("/one/{id}")
	public ResponseEntity<User> getOneUser(@PathVariable String id) {
		User user = service.getOneUser(id);
		return ResponseEntity.ok(user);
	}
	 
}
