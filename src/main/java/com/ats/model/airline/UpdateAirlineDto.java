package com.ats.model.airline;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UpdateAirlineDto {
    private String airlineModel;
    private String airlineName;
    @Min(value = 1)
    private Integer numberOfSeats;
}
