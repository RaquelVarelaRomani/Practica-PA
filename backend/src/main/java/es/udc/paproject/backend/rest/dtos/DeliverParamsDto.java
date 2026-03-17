package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DeliverParamsDto {

    private String creditCardNumber;

    public DeliverParamsDto() {}

    public DeliverParamsDto(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @NotBlank
    @Size(min = 16, max = 16)
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

}
