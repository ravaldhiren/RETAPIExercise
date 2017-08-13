package com.p2ptransfer.processengine.util;

import com.p2ptransfer.cache.UserCache;
import com.p2ptransfer.model.Transaction;
import com.p2ptransfer.model.TransferType;
import com.p2ptransfer.model.User;

public class ApplicationHelper {

	private static volatile Long transactionId = 1L;
	public static void loadDataInCache(int load){
		
		for (int i = 1; i <= load; i++) {
			User usr = User.createUser(i, "Customer"+i , 10000);
			UserCache.getInstance().addUserToCache(new Long(i), usr);
			
		}
	}
	
	public static synchronized Transaction generateTransaction(Long cUserId, Long dUserId, Double amount, String status ){
		Long transactionId = generateTransactionId();
		// hardcoding the vlaue to NEFT for now this can be passed
		return Transaction.createTransaction(transactionId, TransferType.NEFT, cUserId, dUserId, amount, status);
	}

	private static Long generateTransactionId() {
		return transactionId++;
	}
}
