package com.Magnus.OnlineFoodDelivery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Magnus.OnlineFoodDelivery.dto.*;

@Controller
 public class ApiController 
 {
	//private static final Logger log=LoggerFactory.getLogger(ApiController.class);
	
	@RequestMapping(value="api/customer/get",method = RequestMethod.GET)
	public @ResponseBody  Map<String,? extends Object> getCustomer(@RequestParam("start") int start,@RequestParam("limit")int limit,@RequestParam("sort") String sort)
	{
		Map<String, Object> objcust=new HashMap<String, Object>();
		List<UsersDto> listCustomer;
		JSONArray arrCust=new JSONArray(sort);
		JSONObject objCustomerJSON=arrCust.getJSONObject(0);
		String sortProperty = (String) objCustomerJSON.get("property");
	    String sortDirection = (String) objCustomerJSON.get("direction");
	PaginationDto pageDto=new PaginationDto();
	pageDto.setStart(start);
	pageDto.setLimit(limit);
	pageDto.setSortProperty(sortProperty);
	pageDto.setSortDirection(sortDirection);
	/*try {
		listCustomer = service.getAllCustomer(pageDto);
		LOGGER.info("Size of total customer :"+listCustomer.size());
	    map.put("TotalCount", service.totalCountCustomer());
	    map.put("data", listCustomer);
		return map;
	} catch (SQLException e) {
		LOGGER.error(e.getLocalizedMessage());	
		return map;
	}}*/
		return null;
	}
	 
 }
 
