package com.practo.urlshortener.daos;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.practo.urlshortener.entities.Browser;
import com.practo.urlshortener.entities.Country;
import com.practo.urlshortener.entities.Referer;
import com.practo.urlshortener.entities.UrlVisit;
import com.practo.urlshortener.entities.Urls;

@Repository
@Transactional
public class AnalyticsDao {

	@Autowired
	SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/***
	 * 
	 * @param userID
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Urls> getURLs(int userID, int pageNo, int pageSize) {

		Query query = getSession().createQuery("from Urls where User_ID=:userID");
		query.setParameter("userID", userID);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

	public void saveURLVisit(UrlVisit urlVisit) {
		getSession().save(urlVisit);

	}

	public List groupByBrowser(long urlId) {
		Query query = getSession()
				.createSQLQuery("SELECT Browser, COUNT(*) FROM url_visit where URL =:urlId GROUP BY Browser");
		query.setParameter("urlId", urlId);

		return query.list();

	}

	public List groupByLocation(Long urlId) {
		Query query = getSession()
				.createSQLQuery("SELECT Location, COUNT(*) FROM url_visit where URL =:urlId GROUP BY Location");
		query.setParameter("urlId", urlId);

		return query.list();
	}

	public List groupByReferer(Long urlId) {
		Query query = getSession()
				.createSQLQuery("SELECT Referer, COUNT(*) FROM url_visit where URL =:urlId GROUP BY Referer");
		query.setParameter("urlId", urlId);

		return query.list();
	}

	public Referer createReferer(String referer) {
		Referer ref = new Referer(null, referer);
		getSession().save(ref);
		return ref;
	}

	public Country createCountry(String country) {
		Country ctry = new Country(null, country);
		getSession().save(ctry);
		return ctry;
	}

	public Browser createBrowser(String browser) {
		Browser brwsr = new Browser(null, browser);
		getSession().save(brwsr);
		return brwsr;
	}

	public List<Referer> getRefererList() {
		return getSession().createQuery("from Referer").list();
	}

	public List<Country> getCountryList() {
		return getSession().createQuery("from Country").list();
	}

	public List<Browser> getBrowserList() {
		return getSession().createQuery("from Browser").list();
	}

	public List<Urls> getURLs(int userID) {
		Query query = getSession().createQuery("from Urls where User_ID=:userID");
		query.setParameter("userID", userID);
		return query.list();
	}

}
