package com.p2ptransfer.model;

/**
 * The responsibility to make the debit/credit operation thread safe lies with the invoker.
 * @author anursaxena
 *
 */
public class User {

	
	private final long userId;
	private final String name;
	// needs to be always updated no other value changes
	private volatile double amount;
	private final String CURRENCY="$";
	
	private User(long userId, String name, double amount) {
		this.userId = userId;
		this.name = name;
		this.amount = amount;
	}
	
	public static synchronized User createUser(long userId, String name, double amount){
		return new User(userId, name, amount);
	}
	
	public synchronized double  getAmount(){
		return amount;
	}
	
	public String getCurrency(){
		return CURRENCY;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public String getName(){
		return name;
	}
	
	public void debitAmount(Double amt){
		this.amount = this.amount-amt;
	}
	
	public void  creditAmount(Double amt){
		this.amount = this.amount+ amt;
	}
}
