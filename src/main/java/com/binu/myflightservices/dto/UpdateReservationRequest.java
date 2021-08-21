package com.binu.myflightservices.dto;

public class UpdateReservationRequest {

	private int reservationId;
	private Boolean checkInFlag;
	private int numberOfBags;

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public Boolean getCheckInFlag() {
		return checkInFlag;
	}

	public void setCheckInFlag(Boolean checkInFlag) {
		this.checkInFlag = checkInFlag;
	}

	public int getNumberOfBags() {
		return numberOfBags;
	}

	public void setNumberOfBags(int numberOfBags) {
		this.numberOfBags = numberOfBags;
	}

}
