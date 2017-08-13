package com.p2ptransfer.model;

public enum TransferType {

	IMPS("IMPS"),NEFT("NEFT"),RTGS("RTGS");
	

	private String type;

	TransferType(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
    
}
