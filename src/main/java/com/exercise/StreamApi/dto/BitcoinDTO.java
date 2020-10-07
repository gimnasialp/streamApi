package com.exercise.StreamApi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class BitcoinDTO {

  @ApiModelProperty(notes = "Price of Bitcoin")
  private Double price;

  @ApiModelProperty(notes = "attribute to show average between more than one Bitcoin")
  private Double average;

  @ApiModelProperty(notes = "attribute to show percentageDifference between average and greater value of bitcoin's price")
  private String percentageDifference;

  @ApiModelProperty(notes = "Time in format HH:mm:SS")
  private Date createAt;

  public BitcoinDTO(Double price,Date createAt){
    this.price= price;
    this.createAt= createAt;
  }

  public BitcoinDTO(Double average,String percentageDifference){
    this.average= average;
    this.percentageDifference= percentageDifference;
  }

}
