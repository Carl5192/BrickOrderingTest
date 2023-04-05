package com.caci.demo.model;

import java.util.Objects;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String orderReference;

    private int numberOfBricks;

    private boolean isDispatched;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderReference() {
		return orderReference;
	}

	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}

	public int getNumberOfBricks() {
		return numberOfBricks;
	}

	public void setNumberOfBricks(int numberOfBricks) {
		this.numberOfBricks = numberOfBricks;
	}
	
	public boolean isDispatched() {
		return isDispatched;
	}

	public void setDispatched(boolean isDispatched) {
		this.isDispatched = isDispatched;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isDispatched, numberOfBricks, orderReference);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(id, other.id) && isDispatched == other.isDispatched
				&& numberOfBricks == other.numberOfBricks && Objects.equals(orderReference, other.orderReference);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderReference=" + orderReference + ", numberOfBricks=" + numberOfBricks
				+ ", isDispatched=" + isDispatched + "]";
	}
	
	
}