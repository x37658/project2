package com.iii.eeit124.shopping.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iii.eeit124.entity.Categories;

@Repository
@Transactional
public class ShoppingAanlysisDaoImpl implements ShoppingAanlysisDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Map<String, BigDecimal> getAllCategoriesCost(Integer id) {
		@SuppressWarnings("rawtypes")
		Query query = sessionFactory.getCurrentSession().createQuery("From Categories");
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		@SuppressWarnings("unchecked")
		List<Categories> categories = query.getResultList();
		for(Categories categoriey:categories) {
			BigDecimal sum = new BigDecimal(0);
			@SuppressWarnings("rawtypes")
			Query query2 = sessionFactory.getCurrentSession().createSQLQuery("select sum(oi.price*oi.discount) from order_items oi left join products p on p.id=oi.product_id left join orders o on o.id=oi.order_id left join categories c on c.id=p.category_id where o.buyer_id=?0 and c.id=?1 group by p.category_id");
			query2.setParameter(0, id);
			query2.setParameter(1, categoriey.getId());
			if (query2.uniqueResult() != null) {				
				sum = (BigDecimal)query2.uniqueResult();
			}
			map.put(categoriey.getName(), sum);
		}
		return map;
	}
	

	
}
