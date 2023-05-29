package models;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;
import java.util.Objects;

public class FlightData {
    private String emailAddress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime departureTime;
    private String departureAirportCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime arrivalTime;
    private String arrivalAirportCode;
    private String flightNumber;
    private String confirmationCode;

    public FlightData() {
    }

    @Override
    public String toString() {
        return "FlightData{" +
                "emailAddress='" + emailAddress + '\'' +
                ", departureTime=" + departureTime +
                ", departureAirportCode='" + departureAirportCode + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", arrivalAirportCode='" + arrivalAirportCode + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", confirmationCode='" + confirmationCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightData that = (FlightData) o;
        return emailAddress.equals(that.emailAddress) && departureTime.equals(that.departureTime) && departureAirportCode.equals(that.departureAirportCode) && arrivalTime.equals(that.arrivalTime) && arrivalAirportCode.equals(that.arrivalAirportCode) && flightNumber.equals(that.flightNumber) && confirmationCode.equals(that.confirmationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress, departureTime, departureAirportCode, arrivalTime, arrivalAirportCode, flightNumber, confirmationCode);
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(ZonedDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public ZonedDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ZonedDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
}
