package com.p2ptransfer.service;

import com.p2ptransfer.model.User;

public interface BankService {
	 boolean transferFund(String debitId,  String creditId, String amount, String Currency) throws Exception;
	 User getFundDetails(String userId) throws Exception;
}
