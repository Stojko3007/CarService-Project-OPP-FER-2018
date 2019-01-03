package hr.fer.opp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.dao.GenericDAO;
import hr.fer.opp.model.User;

@Service("userService")
public class UserService {

	@Autowired
	protected GenericDAO<User> dao;
	
	public List<User> listAll() {
		return dao.read();
	}
	
	private boolean exists(User user) {
		List<User> list = dao.read();
		return list.contains(user);
	}
	
	public User showRecord(String key) {
		User user = dao.read(key);
		if (user != null) {
			return user;
		}
		throw new NullPointerException("Cant list null user");
	}

	public void deleteRecord(User user) {
		if (exists(user)) {
			dao.delete(user);
		} else {
			throw new IllegalArgumentException("This user doesn't exist");
		}
	}

	public boolean createRecord(User user) {
		if (user != null && !exists(user) && user.getEmail().length() > 0) {
			dao.create(user);
			return true;
		}
		return false;
	}

	public void updateRecord(User user) {
		User temp = dao.read(user.getEmail());

		if (temp == null) {
			throw new NullPointerException("User with this email does not exist.");
		}
		dao.update(user);
	}

}
