package com.github.hualuomoli.demo.web.user;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller(value = "com.github.hualuomoli.demo.web.user.UserController")
@RequestMapping(value = "${mvc.security.auth}/user")
public class UserController {

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public User message(@PathVariable(value = "id") String id, HttpServletRequest request, HttpServletResponse response) {

		// sleep 2 seconds
		// Run.sleep(2);

		User user = new User(id, "hualuomoli", "花落莫离");

		return user;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@ResponseBody
	public User doMessage(User user, HttpServletRequest request, HttpServletResponse response, Model model) {

		System.out.println(user.getId());
		// sleep 2 seconds
		// Run.sleep(2);

		user = new User(user.getId(), "hualuomoli", "花落莫离");

		return user;
	}

	public static class User implements Serializable {

		private static final long serialVersionUID = -6169103355412739991L;

		private String id;
		private String username;
		private String nickname;

		public User() {
		}

		public User(String id, String username, String nickname) {
			this.id = id;
			this.username = username;
			this.nickname = nickname;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
	}

}
