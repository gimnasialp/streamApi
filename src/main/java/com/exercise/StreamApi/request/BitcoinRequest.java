package com.exercise.StreamApi.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "lprice",
    "curr1",
    "curr2"
})
public class BitcoinRequest {

  @JsonProperty("lprice")
  private String lprice;
  @JsonProperty("curr1")
  private String curr1;
  @JsonProperty("curr2")
  private String curr2;

  @JsonProperty("lprice")
  public String getLprice() {
    return lprice;
  }

  @JsonProperty("lprice")
  public void setLprice(String lprice) {
    this.lprice = lprice;
  }

  @JsonProperty("curr1")
  public String getCurr1() {
    return curr1;
  }

  @JsonProperty("curr1")
  public void setCurr1(String curr1) {
    this.curr1 = curr1;
  }

  @JsonProperty("curr2")
  public String getCurr2() {
    return curr2;
  }

  @JsonProperty("curr2")
  public void setCurr2(String curr2) {
    this.curr2 = curr2;
  }

}