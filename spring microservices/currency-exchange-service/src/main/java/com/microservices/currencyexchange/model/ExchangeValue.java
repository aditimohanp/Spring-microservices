package com.microservices.currencyexchange.model;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "exchange_value")

public class ExchangeValue {
@Id
@Column(name="id")
private int id;
@Column(name="currency_from")
private String from;
@Column(name="currency_to")
private String to;
@Column(name="conversion_multiple")
public BigDecimal conversionMultiple;
@Column(name="port")
private int port;

public ExchangeValue() {}

public ExchangeValue(int id, String from, String to, BigDecimal conversionMultiple) {
super();
this.id = id;
this.from = from;
this.to = to;
this.conversionMultiple = conversionMultiple;
}

public int getId() {
return id;
}
public void setId(int id) {
this.id = id;
}
public String getFrom() {
return from;
}
public void setFrom(String from) {
this.from = from;
}
public String getTo() {
return to;
}
public void setTo(String to) {
this.to = to;
}

public BigDecimal getConversionMultiple() {
	return conversionMultiple;
}

public void setConversionMultiple(BigDecimal conversionMultiple) {
	this.conversionMultiple = conversionMultiple;
}

public int getPort() {
return port;
}
public void setPort(int port) {
this.port = port;
}


@Override
public String toString() {
return "ExchangeValue [id=" + id + ", from=" + from + ", to=" + to + ", conversionMultiple=" + conversionMultiple
+ ", port=" + port + "]";
}
}




	

