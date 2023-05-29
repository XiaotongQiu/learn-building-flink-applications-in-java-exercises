package models;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.ZonedDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkyOneAirlinesFlightData {
    private String emailAddress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime flightDepartureTime;
    private String iataDepartureCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime flightArrivalTime;
    private String iataArrivalCode;
    private String flightNumber;
    private String confirmation;

    public SkyOneAirlinesFlightData() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyOneAirlinesFlightData that = (SkyOneAirlinesFlightData) o;
        return emailAddress.equals(that.emailAddress) && flightDepartureTime.equals(that.flightDepartureTime) && iataDepartureCode.equals(that.iataDepartureCode) && flightArrivalTime.equals(that.flightArrivalTime) && iataArrivalCode.equals(that.iataArrivalCode) && flightNumber.equals(that.flightNumber) && confirmation.equals(that.confirmation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress, flightDepartureTime, iataDepartureCode, flightArrivalTime, iataArrivalCode, flightNumber, confirmation);
    }

    @Override
    public String toString() {
        return "SkyOneAirlinesFlightData{" +
                "emailAddress='" + emailAddress + '\'' +
                ", flightDepartureTime=" + flightDepartureTime +
                ", iataDepartureCode='" + iataDepartureCode + '\'' +
                ", flightArrivalTime=" + flightArrivalTime +
                ", iataArrivalCode='" + iataArrivalCode + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", confirmation='" + confirmation + '\'' +
                '}';
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public ZonedDateTime getFlightDepartureTime() {
        return flightDepartureTime;
    }

    public void setFlightDepartureTime(ZonedDateTime flightDepartureTime) {
        this.flightDepartureTime = flightDepartureTime;
    }

    public String getIataDepartureCode() {
        return iataDepartureCode;
    }

    public void setIataDepartureCode(String iataDepartureCode) {
        this.iataDepartureCode = iataDepartureCode;
    }

    public ZonedDateTime getFlightArrivalTime() {
        return flightArrivalTime;
    }

    public void setFlightArrivalTime(ZonedDateTime flightArrivalTime) {
        this.flightArrivalTime = flightArrivalTime;
    }

    public String getIataArrivalCode() {
        return iataArrivalCode;
    }

    public void setIataArrivalCode(String iataArrivalCode) {
        this.iataArrivalCode = iataArrivalCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
}
