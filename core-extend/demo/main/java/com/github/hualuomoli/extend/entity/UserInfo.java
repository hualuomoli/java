package com.github.hualuomoli.extend.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.BaseField;
import com.github.hualuomoli.extend.entity.UploadFile;
import com.github.hualuomoli.extend.entity.User;

@SuppressWarnings("serial")
@EntityTable(comment = "用户信息")
public class UserInfo extends BaseField {

	@EntityColumn(comment = "用户昵称", length = 200)
	private String nickname;
	@EntityColumn(comment = "性别", length = 1)
	private String sex;
	@EntityColumn(comment = "用户头像", relation = "id", name = "photo_id")
	private UploadFile photo;
	@EntityColumn(comment = "用户头像URL", length = 200)
	private String photoUrl;
	@EntityColumn(comment = "登录用户", relation = "id", name = "user_id")
	private User user;

	public UserInfo() {
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public UploadFile getPhoto() {
		return photo;
	}

	public void setPhoto(UploadFile photo) {
		this.photo = photo;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
