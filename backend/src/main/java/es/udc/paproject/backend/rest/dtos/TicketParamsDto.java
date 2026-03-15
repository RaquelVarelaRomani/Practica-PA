package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.*;

public class TicketParamsDto {

    @NotNull
    private Long sessionId;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer numberTickets;

    @NotBlank
    @Size(min = 16, max = 16)
    private String creditCardNumber;

    public TicketParamsDto(){}

    public TicketParamsDto(Long sessionId, Integer numberTickets, String creditCardNumber) {
        this.sessionId = sessionId;
        this.numberTickets = numberTickets;
        this.creditCardNumber = creditCardNumber;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getNumberTickets() {
        return numberTickets;
    }

    public void setNumberTickets(Integer numberTickets) {
        this.numberTickets = numberTickets;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
