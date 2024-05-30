package com.Magnus.OnlineFoodDelivery.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Magnus.OnlineFoodDelivery.bo.OrderBo;
import com.Magnus.OnlineFoodDelivery.bo.PaginationBo;
import com.Magnus.OnlineFoodDelivery.bo.ProductBo;
import com.Magnus.OnlineFoodDelivery.bo.RoleBo;
import com.Magnus.OnlineFoodDelivery.bo.UserWalletBo;
import com.Magnus.OnlineFoodDelivery.bo.UsersBo;
import com.Magnus.OnlineFoodDelivery.bo.WalletTxBo;
import com.Magnus.OnlineFoodDelivery.common.UserStatus;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	private static final Logger log=Logger.getLogger(UserDaoImpl.class);
	
	UserWalletBo objUserWallet;
	@Autowired
	private HibernateTemplate objTemplate;
	
	@Override
	public int createUser(UsersBo customer, RoleBo role) throws SQLException {
		try
		{
		this.objTemplate.save(customer);
		this.objTemplate.save(role);
		objUserWallet=new UserWalletBo();
		objUserWallet.setAmount(0);
		objUserWallet.setUserid(customer);
		objUserWallet.setDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.objTemplate.save(objUserWallet);
		return 1;
		}
		catch(Exception ex)
		{
			log.error(ex.getLocalizedMessage());
		}
		return 0;
	}
	@SuppressWarnings("unchecked")
	@Override
	public UsersBo checkCred(String username) throws SQLException 
	{
		List<UsersBo> users=null;
		UsersBo objUser=null;
		try {
			log.debug("checkCred is Started : userName : "+username);
		users=this.objTemplate.getSessionFactory().getCurrentSession().createQuery("from UsersBo where email=?").setParameter(0, username).list();
		if(!users.isEmpty())
		{
			objUser=users.get(0);
		}

			
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		log.debug("checkCred is Ended userName : "+username);
		return objUser;
	}

	@Override
	public List<UsersBo> getAllUser(PaginationBo bo) throws SQLException {
		Session session = this.objTemplate.getSessionFactory().getCurrentSession();
		return session
				.createQuery(
						"from UsersBo ORDER BY " + bo.getSortProperty() + " " + bo.getSortDirection().toUpperCase())
				.setFirstResult(bo.getStart()).setMaxResults(bo.getLimit()).list();
	}

	@Override
	public long getTotalUser() throws SQLException {
		
		return 0;
	}

	@Override
	public UsersBo getUser(String username) throws SQLException {
		List<UsersBo> users = (List<UsersBo>) this.objTemplate.getSessionFactory().getCurrentSession()
				.createQuery("from UsersBo where email=?").setParameter(0, username).list();

		UsersBo usersBo = null;

		for (UsersBo bo : users) {
			usersBo = bo;
			if (usersBo != null) {
				break;
			}

		}

		return usersBo;
	}

	
	@Override
	public String updateUser(String username, UsersBo users) throws SQLException {
		try {
		Query query=this.objTemplate.getSessionFactory().getCurrentSession().createQuery("update UsersBo set firstname = :firstname ,lastname=:lastname ,phoneNo=:phone,address=:address where email = :email");
		
		query.setParameter("firstname", users.getFirstname());
		query.setParameter("lastname", users.getLastname());
		query.setParameter("phone", users.getPhoneNo());
		query.setParameter("address", users.getAddress());
		query.setParameter("email", username);
		return "Inserted Successfully";
		}
		catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return "Failure";
	}

	@Override
	public String deleteUser(String username) throws SQLException {
		/*
		 * Query query = this.objTemplate.getSessionFactory().getCurrentSession()
		 * .createQuery("delete UsersBo where email = :email");
		 * query.setParameter("eamil", username);
		 */
		Query query=this.objTemplate.getSessionFactory().getCurrentSession().createQuery("update UsersBo set enabled=:enabled where email = :email");
		query.setParameter("enabled", false);
		
		return "delete";
	}

	@Override
	public int createProduct(ProductBo product) throws SQLException {
		try {
			this.objTemplate.save(product);
			return 1;
			
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return 0;
	}

	@Override
	public List<ProductBo> fatchAllProduct(PaginationBo bo) throws SQLException {
		Session session = this.objTemplate.getSessionFactory().getCurrentSession();
		return session
				.createQuery(
						"from ProductBo ORDER BY " + bo.getSortProperty() + " " + bo.getSortDirection().toUpperCase())
				.setFirstResult(bo.getStart()).setMaxResults(bo.getLimit()).list();
	}

	@Override
	public long getTotalProduct() throws SQLException {
		
		return 0;
	}

	@Override
	public ProductBo getProduct(Integer id) throws SQLException {
		List<ProductBo> productList = (List<ProductBo>) this.objTemplate.getSessionFactory().getCurrentSession()
				.createQuery("from ProductBo where id=?").setParameter(0, id).list();
		ProductBo product = new ProductBo();
		for (ProductBo bo : productList) {
			product = bo;
			if (product != null) {
				break;
			}

		}
		return product;
	}

	@Override
	public String updateProduct(Integer id, ProductBo product) throws SQLException {
		Query query = this.objTemplate.getSessionFactory().getCurrentSession()
				.createQuery("update ProductBo set name = :name ,lastname=:lastname ,"
						+ "phoneNo=:phone,address=:address" + " where id = :id");
		query.setParameter("firstname", product.getName());
		query.setParameter("lastname", product.getPrice());
		query.setParameter("id", id);
		return "Update Product Successfully";
	}

	@Override
	public String deleteProduct(Integer id) throws SQLException {
		
		return null;
	}

	@Override
	public int createOrder(OrderBo order) throws SQLException {
		this.objTemplate.save(order);
		return 1;
	}

	@Override
	public List<OrderBo> fatchOrder(PaginationBo bo) throws SQLException {
		String parem=bo.getSortProperty(); 
		if(bo.getSortProperty().equalsIgnoreCase("foodname")) 		
			parem="id"; 
		if(bo.getSortProperty().equalsIgnoreCase("resturentname")) 		
			parem="resturent_name"; 
		return (List<OrderBo>) this.objTemplate.getSessionFactory().getCurrentSession()
				.createQuery("from OrderBo ORDER BY " +parem + " "
						+ bo.getSortDirection().toUpperCase())
				.setFirstResult(bo.getStart()).setMaxResults(bo.getLimit()).list();
	}

	@Override
	public long getTotalOrder() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<OrderBo> fatchOrderByUser(String username, PaginationBo bo) throws SQLException {
		return (List<OrderBo>) this.objTemplate.getSessionFactory().getCurrentSession()
				.createQuery("from OrderBo   ORDER BY " + bo.getSortProperty() + " "
						+ bo.getSortDirection().toUpperCase())
				.setFirstResult(bo.getStart()).setMaxResults(bo.getLimit()).list();
	}

	@Override
	public int addMoney(UserWalletBo wallet, String cardNo) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Double getBalance(String username) throws SQLException {
		UsersBo bo = this.getUser(username);
		Double result = 0.0;
		List<UserWalletBo> userList = (List<UserWalletBo>) this.objTemplate.getSessionFactory()
				.getCurrentSession().createQuery("from UserWalletBo where userid=:userid  ").setParameter("userid", bo)
				.list();
		for (UserWalletBo d : userList) {
			objUserWallet = d;
			result = d.getAmount();
		}

		return result;
	}

	@Override
	public int updateBalance(String username, Double amount, String cardNo) throws SQLException {
		Double oldamount = this.getBalance(username);
		WalletTxBo tx = new WalletTxBo();
		int result = this.objTemplate.getSessionFactory().getCurrentSession()
				.createSQLQuery("update wallet w set w.wallet_money=:amount where w.userid=:userid ")
				.setParameter("amount", amount + oldamount).setParameter("userid", username).executeUpdate();

		if (result == 0) {
			UsersBo bo = this.getUser(username);
			UserWalletBo walletBo = new UserWalletBo();

			walletBo.setAmount(amount);
			walletBo.setDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			walletBo.setUserid(bo);
			tx.setAmount(amount);
			tx.setUsername(username);
			tx.setDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			tx.setCardNo(Long.parseLong(cardNo));
			tx.setTransection(walletBo);
			this.objTemplate.getSessionFactory().getCurrentSession().save(walletBo);
			this.objTemplate.getSessionFactory().getCurrentSession().save(tx);
		} else {
			tx.setAmount(amount);
			tx.setUsername(username);
			tx.setDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			tx.setCardNo(Long.parseLong(cardNo));
			this.getBalance(username);
			tx.setTransection(objUserWallet);
			this.objTemplate.getSessionFactory().getCurrentSession().save(tx);
			return result;
		}
		return 0;
	}

	@Override
	public List<UserWalletBo> fatchAllWallat(PaginationBo bo) throws SQLException {
		return (List<UserWalletBo>) this.objTemplate.getSessionFactory().getCurrentSession()
				.createQuery(
						"from UserWalletBo ORDER BY" + bo.getSortProperty() + " " + bo.getSortDirection().toUpperCase())
				.setFirstResult(bo.getStart()).setMaxResults(bo.getLimit()).list();
	}

	@Override
	public long getTotalWallat() throws SQLException {
		return (Long) this.objTemplate.getSessionFactory().getCurrentSession().createQuery("select count(*) from UserWalletBo").uniqueResult();
				
	}

	@Override
	public int subMoney(String username, Double amount) throws SQLException {
		Double oldamount = this.getBalance(username);
		WalletTxBo tx = new WalletTxBo();
		int result = this.objTemplate.getSessionFactory().getCurrentSession()
				.createSQLQuery("update wallet w set w.wallet_money=:amount where w.userid=:userid ")
				.setParameter("amount", oldamount - amount).setParameter("userid", username).executeUpdate();
		tx.setAmount(amount);
		tx.setUsername(username);
		tx.setDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.getBalance(username);
		tx.setTransection(objUserWallet);
		this.objTemplate.getSessionFactory().getCurrentSession().save(tx);
		return result;
	}

	@Override
	public List<WalletTxBo> getTxByUser(String username, PaginationBo bo) throws SQLException {
		return (List<WalletTxBo>) this.objTemplate.getSessionFactory().getCurrentSession()
				.createQuery("from WalletTxBo where username=:userid ORDER BY " + bo.getSortProperty() + " "
						+ bo.getSortDirection().toUpperCase())
				.setParameter("userid", username).setFirstResult(bo.getStart()).setMaxResults(bo.getLimit()).list();
	}

	@Override
	public long getTotalTx() throws SQLException {
		return 	(Long) this.objTemplate.getSessionFactory().getCurrentSession().createQuery("select Count(*) from WalletTxBo").uniqueResult();
		
	}

}
