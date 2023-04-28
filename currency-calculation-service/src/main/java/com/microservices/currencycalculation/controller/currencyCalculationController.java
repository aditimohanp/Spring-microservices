package com.microservices.currencycalculation.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.microservices.currencycalculation.facade.CurrencyExchangeProxy;
import com.microservices.currencycalculation.model.CalculatedAmount;


@RestController

@RequestMapping("main")
public class currencyCalculationController {
	
	
	@Autowired
	private CurrencyExchangeProxy proxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CalculatedAmount calculateAmount(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity ) throws InterruptedException {

	Map<String,String> uriVariables=new HashMap<>();
	uriVariables.put("from", from);
	uriVariables.put("to", to);
	
	ResponseEntity<CalculatedAmount> responseEntity = new RestTemplate().getForEntity("http://localhost:8002/currency-exchange/from/{from}/to/{to}",CalculatedAmount.class,uriVariables);
	
	HttpStatusCode status = responseEntity.getStatusCode();
	CalculatedAmount calculatedAmount = responseEntity.getBody();
	
	if(status==HttpStatus.OK) {
	return new CalculatedAmount(calculatedAmount.getId(),calculatedAmount.getFrom(),calculatedAmount.getTo(),calculatedAmount.getConversionMultiple(),quantity,
			quantity.multiply(calculatedAmount.getConversionMultiple()), calculatedAmount.getPort());
	}else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
       
        throw new InterruptedException("Some error occured");
    } else {
     
        throw new RuntimeException("Failed to retrieve exchange rate");
    }
	
}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CalculatedAmount calculateAmountFeign(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity ) throws InterruptedException {
try {	
		CalculatedAmount calculatedAmount = proxy.retrieveExchangeValue(from,to);
	
	return new CalculatedAmount(calculatedAmount.getId(),calculatedAmount.getFrom(),calculatedAmount.getTo(),calculatedAmount.getConversionMultiple(),quantity,quantity.multiply(calculatedAmount.getConversionMultiple()), calculatedAmount.getPort());
	
	   }catch(HttpStatusCodeException ex){
		   HttpStatusCode status = ex.getStatusCode();
           if (status == HttpStatus.NOT_FOUND) {
        	   
               throw new InterruptedException("Exchange rate not found");
           } else {
        	   
               throw new RuntimeException("Failed to retrieve exchange rate");
           }
	   }
	}
}

