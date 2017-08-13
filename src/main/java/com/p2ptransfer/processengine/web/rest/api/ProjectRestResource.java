package com.p2ptransfer.processengine.web.rest.api;

import static spark.Spark.*;

import java.util.Map;

import com.p2ptransfer.processengine.util.ApplicationHelper;
import com.p2ptransfer.processengine.util.JsonUtil;
import com.p2ptransfer.service.BankService;
import com.p2ptransfer.service.SimpleBankServiceImpl;

import spark.Request;
import spark.Response;
import spark.Route;

public class ProjectRestResource {
	private final static String CURRENCY="$";

	private static void triggerRestResources(){
		get("/v1/user/:userid", new Route() {
	        public Object handle(Request request, Response response) {
	        	Map<String, String> requestMap = request.params();
	        	String userId = requestMap.get(":userid");
	        	BankService bankingService =  SimpleBankServiceImpl.getInstance();
	        	try {
					return JsonUtil.createResponse( bankingService.getFundDetails(userId), "SUCCESS","200");
				} catch (Exception e) {
					return JsonUtil.createErrorResponse( e.getMessage(), "500");

				}
	        }

			      
	    });
		
		post("/v1/user/transaction", new Route() {
	        public Object handle(Request request, Response response) {
	        	
	        		Map<String, String> bodyMap = JsonUtil.parseBody(request);
		        	String toUserId = bodyMap.get("toUserId");
		        	String fromUserId = bodyMap.get("fromUserId");
		        	String amount = bodyMap.get("amount");
		        	
		        	BankService bankingService =  SimpleBankServiceImpl.getInstance();
		        	try {
						return JsonUtil.createResponse( bankingService.transferFund(fromUserId, toUserId, amount, CURRENCY), "SUCCESS","200");
					} catch (Exception e) {
						return JsonUtil.createErrorResponse( e.getMessage(), "500");

					}
		        }		      
		    
			      
	    });
		}
	
	
	
	public static void main(String[] args) {
		ApplicationHelper.loadDataInCache(2);
		triggerRestResources();
    }
}
