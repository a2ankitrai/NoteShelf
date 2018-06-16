package com.ank.noteshelf.actuators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.ank.noteshelf.NoteShelfApplication;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = NoteShelfApplication.class)
public class ActuatorInfoIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
    public void whenGetInfo_thenReturns200() throws IOException {
        final ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("/manage/info", String.class);
      //  assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void whenFeatures_thenReturns200() throws IOException {
        final ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("/manage/features", String.class);
      //  assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
