package com.exercise.StreamApi.controller;

import com.exercise.StreamApi.dto.BitcoinDTO;
import com.exercise.StreamApi.service.BitcoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/streamApi")
@Api(value = "Stream Api Challenge")
public class StreamApiController {

  @Autowired
  private BitcoinService bitcoinService;

  @ApiOperation(value = "Get Bitcoin with TimeStamp format HH:mm:ss")
  @GetMapping("/bitcoin/price/time/{time}")
  public BitcoinDTO getBitcoinPriceByTimeStamp(@PathVariable String time) throws ParseException {
    return bitcoinService.getBitcoinByCreateAt(time);
  }

  @ApiOperation(value = "Get Average and Percentage Difference to two Times in format HH:mm:ss")
  @GetMapping("/bitcoin/values/firstime/{firstTime}/secondtime/{secondTime}")
  public BitcoinDTO getAverageAndPercentageDifferenceBetweenTwoTimes(@PathVariable String firstTime,
      @PathVariable String secondTime) throws ParseException {
    return bitcoinService.getAverageAndPercentageDifference(firstTime, secondTime);
  }

  @ApiOperation(value = "Init thread work to consume extern Services each 10 seconds with data's persistence")
  @GetMapping("/")
  public void init() throws InterruptedException {
    bitcoinService.initData();
  }

}
