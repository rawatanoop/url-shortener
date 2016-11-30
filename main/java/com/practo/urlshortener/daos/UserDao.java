package com.practo.urlshortener.daos;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.practo.urlshortener.entities.Urls;
import com.practo.urlshortener.entities.Users;

@Repository
@Transactional
public class UserDao {

	@Autowired
	SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public String getFullLink(Long id) {
		Urls url = new Urls();
		getSession().load(url, id);
		return url.getUrl();
	}

	public Long createShortURL(Urls url) {
		return (Long) getSession().save(url);
	}

	public Users getUserByEmailId(String emailID) {
		Query query = getSession().createQuery("from Users where Email=:emailID");
		query.setParameter("emailID", emailID);
		List list =  query.list();
		if (list == null || list.isEmpty())
			return null;
		return (Users) list.get(0);
	}

	public int createUser(Users userEnity) {
		return (int) getSession().save(userEnity);
	}

}
