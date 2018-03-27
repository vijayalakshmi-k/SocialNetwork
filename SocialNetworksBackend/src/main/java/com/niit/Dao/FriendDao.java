package com.niit.Dao;

import java.util.List;

import com.niit.model.Friend;
import com.niit.model.User;

public interface FriendDao {

	List<User> suggestedUsers(String email);

	void addFriend(Friend friend);

	List<Friend> pendingRequests(String toEmail);

	void acceptRequest(Friend request);

	void deleteRequest(Friend request);

	List<Friend> ListOfFriends(String email);
}
