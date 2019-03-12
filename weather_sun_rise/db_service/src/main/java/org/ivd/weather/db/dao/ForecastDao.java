package org.ivd.weather.db.dao;

import org.ivd.weather.db.entity.ForecastEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@RequestScoped
public class ForecastDao implements IForecastDao {
    private final Logger log = LoggerFactory.getLogger(ForecastDao.class);

    @PersistenceContext(unitName = "manager")
    private EntityManager em;

    public ForecastDao() {
    }

    @Override
    public void save(ForecastEntity entity) {
        em.persist(entity);
    }

    @Override
    public void update(ForecastEntity entity) {
        em.merge(entity);
    }

    @Override
    public ForecastEntity getByCityAndDate(String city, Date date) {
        ForecastEntity entity = null;
        javax.persistence.Query query = em.createQuery("select f from ForecastEntity f where f.dateForecast =:date  and f.city =:city");
        query.setParameter("date", date);
        query.setParameter("city", city);
        entity = (ForecastEntity) query.getResultList().get(0);

        return entity;
    }

    @Override
    public void find() {
        javax.persistence.Query query = em.createQuery("select f from ForecastEntity f ");
        log.info(query.getResultList().toString());

    }

    @Override
    public boolean isForecast(String city, Date date) {
        javax.persistence.Query query = em.createQuery(
                "select count(*) from ForecastEntity f where f.dateForecast =:date  and f.city =:city"
        );
        query.setParameter("date", date);
        query.setParameter("city", city);
        Long count = (Long) query.getSingleResult();
        return count == 0;
    }
}
