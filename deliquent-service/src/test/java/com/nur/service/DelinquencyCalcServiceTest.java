package com.nur.service;

import com.nur.model.DelinquencyMonthRecord;
import com.nur.model.DelinquencyRequest;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DelinquencyCalcServiceTest {

//    @Mock
//    private StepRateCalcRepository stepRateCalcRepository;
//    @Mock
//    private StepRateCalcMongoService stepRateCalcMongoService;
//    @InjectMocks
//    private DelinquencyCalcService delinquencyCalcService;

    @Autowired
    DelinquencyCalcService delinquencyCalcService;


//    public static DelinquencyRequest createRequest(){
//        return Instancio.create(DelinquencyRequest.class);
//    }

    @Test
    void getDelinquencyMonthlyRecordsTest(){
        List<DelinquencyMonthRecord> records = delinquencyCalcService.getDelinquencyMonthlyRecords(createRequest());
        assertTrue(records.size()>0 && records.size()<=84);
        assertNotNull(records);
    }

    private DelinquencyRequest createRequest() {
        DelinquencyRequest request = new DelinquencyRequest();
        request.setCurrentDueDate(LocalDate.now());
        request.setInterestRate(0.05);
        request.setPrincipleBalance(5000);
        request.setPrincipleAndInterest(1000);
        return request;
    }

}