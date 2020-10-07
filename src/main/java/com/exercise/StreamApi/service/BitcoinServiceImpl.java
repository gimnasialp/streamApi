package com.exercise.StreamApi.service;

import com.exercise.StreamApi.Repository.BitcoinRepository;
import com.exercise.StreamApi.dto.BitcoinDTO;
import com.exercise.StreamApi.entity.Bitcoin;
import com.exercise.StreamApi.exception.BitcoinNotFoundException;
import com.exercise.StreamApi.request.BitcoinRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BitcoinServiceImpl implements BitcoinService {

  @Value("${urlBitcoin}")
  private String urlBitcoin;

  @Autowired
  private BitcoinRepository bitcoinRepository;

  @Autowired
  private RestTemplate clientRest;

  private static int count = 0;

  private final int FINALCOUNT = 60;

  @Override
  public BitcoinDTO getBitcoinByCreateAt(String time) throws ParseException {
    Date date = new SimpleDateFormat("HH:mm:ss").parse(time);
    Bitcoin bitcoin = bitcoinRepository.findBitcoinByCreateAt(date)
        .orElseThrow(() -> new BitcoinNotFoundException());
    return new BitcoinDTO(bitcoin.getPrice(), bitcoin.getCreateAt());
  }

  @Override
  public BitcoinDTO getAverageAndPercentageDifference(String firstTime, String secondTime)
      throws ParseException {
    Date firstDate = new SimpleDateFormat("HH:mm:ss").parse(firstTime);
    Date secondDate = new SimpleDateFormat("HH:mm:ss").parse(secondTime);
    List<Bitcoin> bitcoinList = bitcoinRepository.findByCreateAtBetween(firstDate, secondDate);
    if (bitcoinList.isEmpty()) {
      throw new BitcoinNotFoundException();
    }
    Double average = calculateAverage(bitcoinList);
    String percentageDiff = calculatePercentageDifference(bitcoinList, average);
    return new BitcoinDTO(average, percentageDiff);
  }

  private String calculatePercentageDifference(List<Bitcoin> bitcoinList, Double average) {
    Double max = bitcoinList.stream().max(Comparator.comparing(bitcoin -> bitcoin.getPrice())).get()
        .getPrice();
    return String.format("%.2f", (100 - ((average * 100) / max)));
  }


  private Double calculateAverage(List<Bitcoin> bitcoinsPrices) {
    return (bitcoinsPrices.stream().mapToDouble(bitcoin -> bitcoin.getPrice()).sum()) / Double
        .valueOf(bitcoinsPrices.size());

  }

  @Override
  public void initData() throws InterruptedException {

    ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    Runnable task1 = () -> {
      queryAndPersistence();
      count++;
    };
    ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, 0, 10, TimeUnit.SECONDS);

    if (count == FINALCOUNT) {
      scheduledFuture.cancel(true);
      ses.shutdown();
    }
  }

  private void queryAndPersistence() {

    String cryptos = clientRest.getForObject(urlBitcoin, String.class);
    ObjectMapper mapper = new ObjectMapper();
    BitcoinRequest bitcoinRequest = null;
    try {
      bitcoinRequest = mapper.readValue(cryptos, BitcoinRequest.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    Bitcoin bitcoin = new Bitcoin();
    bitcoin.setPrice(Double.valueOf(bitcoinRequest.getLprice()));
    bitcoin.setCreateAt(new Date());
    bitcoinRepository.save(bitcoin);
  }

}
