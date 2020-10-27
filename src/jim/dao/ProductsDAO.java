package jim.dao;

import java.util.List;

import org.hibernate.Session;

import org.hibernate.query.Query;

import jim.entity.Products;

public class ProductsDAO {

	private Session session;

	public ProductsDAO(Session session) {
		this.session = session;
	}

	public Products insert(Products product) {
		Products result = session.get(Products.class, product.getId());

		if (result == null) {
			session.save(product);
			return product;
		}
		return null;
	}

	public Products select(int id) {//查單筆
		return session.get(Products.class, id);
	}
	public List<Products> selectByName(String name) {//查單筆
		Query<Products> query = session.createQuery("from Products where name like ?0 order by id", Products.class);
		query.setParameter(0, "%"+name+"%");
		List<Products> list = query.list();
		return list;
	}

	public List<Products> selectAll() {//查所有,用ID排序
		Query<Products> query = session.createQuery("from Products order by id", Products.class);
		List<Products> list = query.list();
		return list;
	}

	public Products update(int id, String name) {
		Products result = session.get(Products.class, id);
		if (result != null) {
			result.setName(name);
		}
		return result;
	}

	public boolean delete(int id) {
		Products products = session.get(Products.class, id);
		if (products != null) {
			session.delete(products);
			return true;
		}
		return false;
	}

}
