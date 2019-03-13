package org.ivd.weather.db.dao;

import org.ivd.weather.db.entity.ForecastEntity;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    public boolean isForecastEmpty(String city, Date date) {
        javax.persistence.Query query = em.createQuery(
                "select count(*) from ForecastEntity f where f.dateForecast =:date  and f.city =:city"
        );
        query.setParameter("date", date);
        query.setParameter("city", city);
        Long count = (Long) query.getSingleResult();
        return count == 0;
    }

    public ForecastEntity findByCityAndDate(String city, Date date) {
        if (date == null || city.isEmpty()) {
            throw new RuntimeException("Отсутствует значение даты или города");
        }
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<ForecastEntity> criteriaQuery = criteriaBuilder.createQuery(ForecastEntity.class);
        Root<ForecastEntity> root = criteriaQuery.from(ForecastEntity.class);
        criteriaQuery.select(root);
        Predicate predicate = criteriaBuilder.conjunction();
        Predicate datePredicate = criteriaBuilder.equal(root.get("dateForecast"), date);
        predicate = criteriaBuilder.and(predicate, datePredicate);
        Predicate cityPredicate = criteriaBuilder.equal(root.get("city"), city);
        predicate = criteriaBuilder.and(predicate, cityPredicate);
        criteriaQuery.where(predicate);
        return em.createQuery(criteriaQuery).getSingleResult();
    }
}
