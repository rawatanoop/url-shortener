package com.practo.urlshortener.daos;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.practo.urlshortener.entities.Urls;

@Repository
@Transactional
public class ShortenerDao {

	@Autowired
	SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Urls getFullLink(Long id) {
		Urls url = new Urls();
		try {
			getSession().load(url, id);
		} catch (Exception e) {
			return null;
		}
		return url;
	}

	public Long createShortURL(Urls url) {
		return (Long) getSession().save(url);
	}

	public Urls getURL(Urls urls) {
		Query query = getSession().createQuery("from Urls where User_ID=:userID and URL=:url");
		query.setParameter("userID", urls.getUserID());
		query.setParameter("url", urls.getUrl());
		List list = query.list();
		if (list != null && !list.isEmpty())
			return (Urls) list.get(0);
		return null;
	}

}
