package org.ivd.weather.user.dao;

import org.ivd.weather.error.exception.WeatherException;
import org.ivd.weather.user.entity.ForecastEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

@Repository
public class ForecastDaoImpl implements ForecastDao {
    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    public ForecastEntity findByCityDate(Date date, String city) throws WeatherException {
        if (date == null || city.isEmpty()) {
            throw new WeatherException("Отсутствует значение даты или города");
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
