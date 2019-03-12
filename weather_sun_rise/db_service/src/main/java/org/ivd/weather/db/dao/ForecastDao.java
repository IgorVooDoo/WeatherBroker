package org.ivd.weather.db.dao;

import org.ivd.weather.db.entity.ForecastEntity;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@RequestScoped
public class ForecastDao implements IForecastDao {

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
        ForecastEntity entity;
        javax.persistence.Query query = em.createQuery("select f from ForecastEntity f where f.dateForecast =:date  and f.city =:city");
        query.setParameter("date", date);
        query.setParameter("city", city);
        entity = (ForecastEntity) query.getResultList().get(0);
        return entity;
    }

    @Override
    public boolean isForecastEmpty(String city, Date date) {
        javax.persistence.Query query = em.createQuery(
                "select count(*) from ForecastEntity f where f.dateForecast =:date  and f.city =:city"
        );
        query.setParameter("date", date);
        query.setParameter("city", city);
        Long count = (Long) query.getSingleResult();
        return count == 0;
    }

  /*  @Override
    public ForecastEntity findByCityAndDate(String city, Date date) {
        CityDateKey key = new CityDateKey(city,date.toString());
        ForecastEntity entity = em.find(ForecastEntity.class, key);
        if(entity == null){
            throw new EntityNotFoundException("Нет значения для "+city+" "+date);
        }
        return entity;
    }*/
}
