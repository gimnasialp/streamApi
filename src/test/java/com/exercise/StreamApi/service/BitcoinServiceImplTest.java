package com.exercise.StreamApi.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.exercise.StreamApi.Repository.BitcoinRepository;
import com.exercise.StreamApi.dto.BitcoinDTO;
import com.exercise.StreamApi.entity.Bitcoin;
import com.exercise.StreamApi.exception.BitcoinNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class BitcoinServiceImplTest {

  @MockBean
  private BitcoinRepository bitcoinRepository;

  @Autowired
  private BitcoinService bitcoinService;

  @Test
  void whenGetBitcoinWithExistsDates_success() throws ParseException {
    //Arrange
    String dateStr = "10:00:01";
    Date dateExpected = new SimpleDateFormat("HH:mm:ss").parse(dateStr);
    BitcoinDTO bitcoinExpected = new BitcoinDTO();
    Optional<Bitcoin> bitcoinOptional = Optional
        .of(buildFirstBitcoin());
    when(bitcoinRepository.findBitcoinByCreateAt(dateExpected)).thenReturn(bitcoinOptional);
    //Act
    bitcoinExpected = bitcoinService
        .getBitcoinByCreateAt(dateStr);
    //Assert
    assertEquals(bitcoinExpected.getPrice(), 2000.4);
    assertEquals(bitcoinExpected.getCreateAt().getTime(), dateExpected.getTime());

  }

  @Test
  void whenGetBitcoinWithNotExistsDates_FailAndThrowsException() {
    //Arrange
    String dateStr = "10:00:05";

    //Act & Assert
    assertThrows(BitcoinNotFoundException.class, () -> {
      bitcoinService
          .getBitcoinByCreateAt(dateStr);
    });

  }

  @Test
  void whenGetAverageAndPercentageDifferenceWithExistsDates_success() throws ParseException {
    //Arrange
    String firstD = "11:30:35";
    Date firstDate = new SimpleDateFormat("HH:mm:ss").parse(firstD);
    String secondD = "11:30:47";
    Date secondDate = new SimpleDateFormat("HH:mm:ss").parse(secondD);
    List<Bitcoin> listBitcoin = List.of(buildFirstBitcoin(), buildSecondBitcoin());
    when(bitcoinRepository.findByCreateAtBetween(firstDate, secondDate)).thenReturn(listBitcoin);

    //Act
    BitcoinDTO bitcoinDTO = bitcoinService.getAverageAndPercentageDifference(firstD, secondD);

    //Assert
    assertThat(bitcoinDTO.getAverage(), is(2000.6));
    assertThat(bitcoinDTO.getPercentageDifference(), is(greaterThan("0")));

  }

  @Test
  void whenGetAverageAndPercentageDifferenceWithNotExistsDates_FailAndThrowsException() {
    //Arrange
    String firstD = "11:30:35";
    String secondD = "11:30:47";

    //Act & Assert
    assertThrows(BitcoinNotFoundException.class, () -> {
      bitcoinService.
          getAverageAndPercentageDifference(firstD, secondD);
    });

  }

  private Bitcoin buildFirstBitcoin() {
    Bitcoin bitcoin = new Bitcoin();
    Date date = null;
    try {
      date = new SimpleDateFormat("HH:mm:ss").parse("10:00:01");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    bitcoin.setId(1L);
    bitcoin.setCreateAt(date);
    bitcoin.setPrice(2000.4);
    return bitcoin;
  }

  private Bitcoin buildSecondBitcoin() {
    Bitcoin bitcoin = new Bitcoin();
    Date date = null;
    try {
      date = new SimpleDateFormat("HH:mm:ss").parse("10:00:10");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    bitcoin.setId(1L);
    bitcoin.setCreateAt(date);
    bitcoin.setPrice(2000.8);
    return bitcoin;
  }
}