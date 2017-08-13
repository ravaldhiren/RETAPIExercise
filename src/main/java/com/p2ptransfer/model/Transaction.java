package com.p2ptransfer.model;

import java.util.Date;

public class Transaction {
	private final long transactionId;
	private final TransferType type;
	private final long cUserId;
	private final long dUserId;
	private final Double amount;
	private final Date timeStamp;
	private final String status;
	
	private Transaction(long transactionId, TransferType type, long cUserId, long dUserId, Double amount, Date dt, String status) {
		this.transactionId = transactionId;
		this.type = type;
		this.cUserId = cUserId;
		this.dUserId = dUserId;
		this.amount = amount;
		this.timeStamp = dt;
		this.status = status;
	}

	

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", type=" + type + ", cUserId=" + cUserId + ", dUserId="
				+ dUserId + ", amount=" + amount + ", timeStamp=" + timeStamp + ", status=" + status + "]";
	}



	public Long getTransactionId() {
		return transactionId;
	}

	public TransferType getType() {
		return type;
	}

	public Long getcUserId() {
		return cUserId;
	}

	public Long getdUserId() {
		return dUserId;
	}
	
	public Date getTimeStamp() {
		return new Date(timeStamp.getTime());
	}
	
	public static synchronized Transaction createTransaction(long transactionId, TransferType type, long sUserId, long dUserId, double amount, String status){
		long time = System.nanoTime();
		return new Transaction( transactionId,  type,  sUserId,  dUserId, amount, new Date(time), status);
	}
	public enum STATUS{
		SUCCESS("SUCCESS"), FAILURE("FAILURE");
		
		String status;
		STATUS(String status){
			this.status = status;
		}
		public String status(){
			return this.status;
		}
	}

}
