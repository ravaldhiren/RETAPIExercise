package com.p2ptransfer.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.p2ptransfer.cache.TransactionCache;
import com.p2ptransfer.cache.UserCache;
import com.p2ptransfer.model.Transaction;
import com.p2ptransfer.model.Transaction.STATUS;
import com.p2ptransfer.processengine.util.ApplicationHelper;
import com.p2ptransfer.model.User;

public class SimpleBankServiceImpl implements BankService {

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();

	private SimpleBankServiceImpl() {

	}

	/**
	 * 
	 * @author abhishek singleton holder returns lazily
	 * 
	 */
	private static class LazyHolder {
		static final SimpleBankServiceImpl INSTANCE = new SimpleBankServiceImpl();
	}

	public static SimpleBankServiceImpl getInstance() {
		return LazyHolder.INSTANCE;
	}

	public boolean transferFund(String debitId, String creditId, String amount, String Currency) throws Exception {

		validateInput(debitId, creditId, amount);

		writeLock.lock();

		try {
			Long dbId = Long.valueOf(debitId);
			Long crId = Long.valueOf(creditId);
			Double amt = Double.valueOf(amount);
			User crUsr = UserCache.getInstance().getUser(crId);
			User dbUsr = UserCache.getInstance().getUser(dbId);
			
			if (crUsr == null || dbUsr == null) {
				throw new Exception("NO_USER_FOUND");

			}
			 
			if(amt < 0.0){
				 throw new Exception("NEGATIVE_FUND_TRANSFER");
			}
			
			if (dbUsr.getAmount() < amt) {
				updateTransactionCache(crId, dbId, amt, Transaction.STATUS.FAILURE);
				throw new Exception("INSUFFICIENT_BALANCE");
			}
			
			crUsr.creditAmount(amt);
			dbUsr.debitAmount(amt);
			updateTransactionCache(crId, dbId, amt, Transaction.STATUS.SUCCESS);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Expects valid Long value for userId");
		} catch (Exception e) {
			throw e;
		}finally{
			writeLock.unlock();
		}
		
		return true;
	}

	public User getFundDetails(String userId) throws Exception {
		User usr = null;
		readLock.lock();
		try {
			Long id = Long.valueOf(userId);
			usr = UserCache.getInstance().getUser(id);
			if (usr == null) {
				throw new Exception("NO_USER_FOUND");
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Expects valid Long value for userId");
		}catch(Exception e){
			throw e;
		}finally{
			readLock.unlock();
		}
		return usr;
	}

	private void validateInput(String debitId, String creditId, String amount) throws Exception {
		if ((debitId == null || debitId.trim().equals("")) || (creditId == null || creditId.trim().equals(""))
				|| (amount == null || amount.trim().equals(""))) {
			throw new IllegalArgumentException(
					"Invalid data passed, debitUserId, creditUserId, amount can not be null");
		}

	}
	
	private void updateTransactionCache(Long crId, Long dbId, Double amt, STATUS status) {
			Transaction tr = ApplicationHelper.generateTransaction(crId, dbId, amt, status.status());
			TransactionCache.getInstance().updateTransactionCache(tr);
			
	}
}
