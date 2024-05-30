package com.Magnus.OnlineFoodDelivery.service;

import java.sql.SQLException;
import java.util.List;

import com.Magnus.OnlineFoodDelivery.dto.*;

public interface FoodService 
{
	   //User 
		int saveCustomer(UsersDto dto) throws SQLException;
		List<UsersDto> getAllCustomer(PaginationDto page) throws SQLException;
		//food 
		List<ProductDto> getAllProduct(PaginationDto page) throws SQLException;
		public int saveProduct(ProductDto dto) throws SQLException;
		//order details
		List<OrderDto> getAllOrder(PaginationDto page)throws SQLException;
		List<OrderDto> getAllOrderByUser(PaginationDto page)throws SQLException;
		int orderFoodByUser(OrderDto dto)throws SQLException;
		//wallet
		public int saveWallet(UserWalletDto dto) throws SQLException;
		List<String> getAllCards(String username,PaginationDto page)throws SQLException;
		public int updateWallet(UserWalletDto dto) throws SQLException;
		List<UserWalletDto> getAllWallet(PaginationDto page)throws SQLException;
		public Double getBalanceOfUser()throws SQLException;
		public int deductWallet(Double amount) throws SQLException;
		
		public List<WalletTxDto> fatchTxByUser(PaginationDto page)throws SQLException;
		
		//Pagination
		List<UsersDto> getAllCustomer(int start, int limit, String sortProperty, String sortDirection) throws SQLException;
		List<ProductDto> getAllProduct() throws SQLException;
		List<OrderDto> getAllOrder(int start, int limit, String sortProperty, String sortDirection)throws SQLException;
		List<OrderDto> getAllOrderByUser(int start, int limit, String sortProperty, String sortDirection)throws SQLException;
		List<String> getAllCards(String username,int start, int limit, String sortProperty, String sortDirection)throws SQLException;
		List<UserWalletDto> getAllWallet(String username,int start, int limit, String sortProperty, String sortDirection)throws SQLException;
		public List<WalletTxDto> fatchTxByUser(String username,int start, int limit, String sortProperty, String sortDirection)throws SQLException;
		
		//Total Count
		public long totalCountCustomer() throws SQLException;
		public long totalCountFood() throws SQLException;
		public long totalCountOrderHis() throws SQLException;
		public long totalCountTransection() throws SQLException;
		public long totalCountWallet() throws SQLException;

}
