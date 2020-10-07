package com.exercise.StreamApi.service;

import com.exercise.StreamApi.dto.BitcoinDTO;
import com.exercise.StreamApi.entity.Bitcoin;
import java.text.ParseException;
import java.util.List;

public interface BitcoinService {

  public BitcoinDTO getBitcoinByCreateAt(String time) throws ParseException;

  public BitcoinDTO getAverageAndPercentageDifference(String firstTime, String secondTime)
      throws ParseException;

  void initData() throws InterruptedException;
}
