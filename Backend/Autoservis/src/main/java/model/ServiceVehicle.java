package model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ServiceVehicle")
public class ServiceVehicle extends Vehicle {

	@OneToOne
	private RegisteredUser rentedTo;

	public ServiceVehicle(String licensePlate, Model model, String year, RegisteredUser rentedTo) {
		super(licensePlate, model, year);
		this.rentedTo = rentedTo;
	}

	public RegisteredUser getRentedTo() {
		return rentedTo;
	}

	public void setRentedTo(RegisteredUser rentedTo) {
		this.rentedTo = rentedTo;
	}

}
