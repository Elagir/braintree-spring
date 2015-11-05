package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braintreegateway.MerchantAccount;
import com.braintreegateway.MerchantAccountRequest;
import com.braintreegateway.Result;
import com.util.BraintreePaymentGateway;

/* This is a implementation class where you generate client token */
@Service
public class BraintreeSubMerchantServiceImpl implements BraintreeSubMerchantService {

	@Autowired
	BraintreePaymentGateway gateway;
	
	@Override
	public Result<MerchantAccount> createSubMerchant(String customerId) {
		
		Customer customer = //get your customer
		
		MerchantAccountRequest request = new MerchantAccountRequest().
			    individual().
			        firstName(customer.getFirstName()).
			        lastName(customer.getLastName()).
			        email(customer.getEmail()).
			        phone(customer.getPhone()).
			        dateOfBirth(customer.getDOB()).
			        ssn(customer.getSSN()).
			        address().
			            streetAddress(customer.getAddress()).
			            locality(customer.getCity()).
			            region(customer.getState()).
			            postalCode(customer.getPostalCode()).
			            done().
			        done().
			    funding().
			        destination(MerchantAccount.FundingDestination.EMAIL).
			        email(customer.getEmail()).
			        mobilePhone(customer.getPhone()).
			        done().
			    tosAccepted(true).
			    masterMerchantAccountId(gateway.getMerchantId()).
			    id(customerId);

		Result<MerchantAccount> result = gateway.getGateway().merchantAccount().create(request);
		return result;
	}
/*
 * (non-Javadoc) We are assuming customer can have more than 1 business
 */
	@Override
	public Result<MerchantAccount> createSubMerchant(String customerId, String businessId) {
		
		Customer customer = //get your customer;
		
		Business  business = //get your business;
		
    	MerchantAccountRequest request = new MerchantAccountRequest().
    		    individual().
    		        firstName(customer.getFirstName()).
    		        lastName(customer.getLastName()).
    		        email(customer.getEmailAddress()).
    		        phone(customer.getTelNumber()).
    		        dateOfBirth(customer.getDOB()).
    		        ssn(customer.getSSN()).
    		        address().
    		            streetAddress(customer.getAddress()).
    		            locality(customer.getCity()).
    		            region(customer.getState()).
    		            postalCode(customer.getZipcode()).
    		            done().
    		        done().
    		    business().
    		        legalName(business.getName()).
    		        dbaName(business.getName()).
    		        taxId(business.getTaxID()).
    		        address().
    		            streetAddress(business.getAddress()).
    		            locality(business.getCity()).
    		            region(business.getState()).
    		            postalCode(business.getPostalCode()).
    		            done().
    		        done().
   		    funding().
    		        destination(MerchantAccount.FundingDestination.EMAIL).
    		        email(customer.getEmail()).
    		        mobilePhone(customer.getPhone()).
    		        done().
    		    tosAccepted(true).
    		    masterMerchantAccountId(gateway.getMerchantId()).
    		    id(customerId); //add business name

    		Result<MerchantAccount> result = gateway.getGateway().merchantAccount().create(request);
    		
    		return result;
    }
	
	@Override
	public MerchantAccount findSubMerchant(String customerId) {
		MerchantAccount result = gateway.getGateway().merchantAccount().find(customerId);
		return result;

	}
	
	
//	@Override
//    public Result<Transaction> holdInEscrow(String transactionId) {
//
//		Result<Transaction> holdInEscrow = gateway.getGateway().transaction().holdInEscrow(transactionId);
//        return holdInEscrow;
//	}
//
//    public Result<Transaction> releaseFromEscrow(String transactionId) {
//    	
//        Result<Transaction> releaseResult = gateway.getGateway().transaction().releaseFromEscrow(transactionId);
//        return releaseResult;
//    }
//
//	
	
}