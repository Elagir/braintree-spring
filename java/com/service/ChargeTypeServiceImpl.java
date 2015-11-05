package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ChargeTypeDAO;

@Service
public class ChargeTypeServiceImpl implements ChargeTypeService {
	
	@Autowired
	ChargeTypeDAO chargeTypeDAO;

	@Transactional
	public Double getChargeAmmountByType(String chargeType){
		
		return chargeTypeDAO.getChargeAmmountByType(chargeType);
	}

}
