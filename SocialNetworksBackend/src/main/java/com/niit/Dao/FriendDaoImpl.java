package com.niit.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Friend;
import com.niit.model.User;

@Repository
@Transactional
public class FriendDaoImpl implements FriendDao {
	@Autowired
	private SessionFactory sessionFactory;

	public List<User> suggestedUsers(String email) {

		Session session = sessionFactory.getCurrentSession();
		
		/*
		String queryString ="select * from user_s180250 where emails in"
				+ "(select emails from user_s180250 where emails!=? " 
				+ "minus"
				+ "(select toId_emails from friend_s180250 where fromId_emails=? "
				+ "union"
				+ "select fromId_emails from friend_s180250 where toId_emails=? ))";
				
		SQLQuery query= session.createSQLQuery(queryString);
		query.setString(0, email);
		query.setString(1, email);
		query.setString(2, email);
				*/
		String queryString ="select * from user_s180250 where emails in ((select emails from user_s180250 where emails!=?)minus((select toId_emails from friend_s180250 where fromId_emails=?)union(select fromId_emails from friend_s180250 where toId_emails=?)))";
		SQLQuery query= session.createSQLQuery(queryString);
		query.setString(0, email);
		query.setString(1, email);
		query.setString(2, email);
		query.addEntity(User.class);
		List<User> suggestedUsers =query.list();
		System.out.println("suggested users:"+suggestedUsers);
		return suggestedUsers;
	}

	public void addFriend(Friend friend) {
		Session session = sessionFactory.getCurrentSession();
		session.save(friend);
	}

	public List<Friend> pendingRequests(String toEmail) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Friend where status=? and toId.emails=?");
		query.setCharacter(0, 'P');
		query.setString(1, toEmail);
		List<Friend> pendingRequests = query.list();
		return pendingRequests;
	}

	public void acceptRequest(Friend request) {
		Session session = sessionFactory.getCurrentSession(); // requests pending
		request.setStatus('A'); //update friend from s180250 set status='A' where='id'
		session.update(request);//only one row updated
	}
	public void deleteRequest(Friend request)
	{
		Session session=sessionFactory.getCurrentSession();
		session.delete(request); //delete from friend s180250 only one row deleted
		
	}

	public List<Friend> ListOfFriends(String email) {
		Session session=sessionFactory.getCurrentSession();
		Query query1=session.createQuery("select f.toId from Friend f where f.fromId.emails=? and f.status=?");
		query1.setString(0, email);
		query1.setCharacter(1, 'A');
		List<Friend> friendList1 = query1.list();
		Query query2=session.createQuery("select f.fromId from Friend f where f.toId.emails=? and f.status=?");
		query2.setString(0, email);
		query2.setCharacter(1, 'A');
		List<Friend> friendList2 = query2.list();
		friendList1.addAll(friendList2);
		return friendList1;


}
}