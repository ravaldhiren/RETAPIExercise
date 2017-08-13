package com.p2ptransfer.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.p2ptransfer.model.Transaction;

public class TransactionCache {

	private volatile ConcurrentHashMap<Long , Transaction> transactionCache = new ConcurrentHashMap<Long,Transaction>();
	private TransactionCache(){
		
	}
	public static TransactionCache getInstance(){
		return LazyTransactionCacheHolder.INSTANCE;
	}
	private static class LazyTransactionCacheHolder {
		static final TransactionCache INSTANCE = new TransactionCache();
	}
	public synchronized void updateTransactionCache(Transaction tr){
		
		transactionCache.put(tr.getcUserId(), tr);
	}
}
