package org.ivd.weather.user.dao;

import org.ivd.weather.user.entity.ForecastEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Repository
public class ForecastDao implements IForecastDao {
    @PersistenceContext
    private EntityManager em;

    public ForecastEntity findByCityDate(Date date, String city) {
        javax.persistence.Query query = em.createQuery(
                "select f from ForecastEntity f where f.dateForecast =:date  and f.city =:city"
        );
        query.setParameter("date", date);
        query.setParameter("city", city);
        ForecastEntity entity = (ForecastEntity) query.getResultList().get(0);
        return entity;
    }


}
