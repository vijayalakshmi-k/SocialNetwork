package com.niit.Dao;

import com.niit.model.ProfilePicture;

public interface ProfilePictureDao {
	void uploadProfilePicture(ProfilePicture profilePicture);

	ProfilePicture getImage(String email);
}
