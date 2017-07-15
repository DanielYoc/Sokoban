package model.data;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class RecordsDataMapper {
	private static RecordsDataMapper instance;
	private SessionFactory m_Factory;

	public static RecordsDataMapper getInstance() {
		if (instance == null)
			instance = new RecordsDataMapper();
		return instance;
	}

	private RecordsDataMapper() {
		m_Factory = null;
	}

	private SessionFactory getFactory() {
		if (m_Factory == null) {
			Configuration configuration = new Configuration();
			configuration.configure("/hibernate/hibernate.cfg.xml");
			m_Factory = configuration.buildSessionFactory();
		}
		return m_Factory;
	}

	public void saveRecored(Record recored) {
		Session session = getFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(recored);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Record[] search(RecordQuery query) {
		Session session = getFactory().openSession();
		Record[] results = null;
		Query<Record> q = session.createQuery(
				"From model.data.Record r WHERE (:levelName IS NULL OR r.levelName = :levelName) AND (:userName IS NULL OR r.userName = :userName) ORDER BY r."
						+ (query.getOrderBy()) + (query.getIsDesc() ? " DESC" : ""));
		q.setFirstResult(query.getPage() * query.getResultsPerPage());
		q.setMaxResults(query.getResultsPerPage());
		q.setParameter("levelName", query.getLevelName());
		q.setParameter("userName", query.getUserName());
		List<Record> list = q.list();
		results = new Record[list.size()];
		results = (Record[]) list.toArray(results);
		return results;
	}

}
