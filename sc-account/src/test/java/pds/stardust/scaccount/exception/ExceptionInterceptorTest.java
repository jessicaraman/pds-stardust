package pds.stardust.scaccount.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;

/**
 * ExceptionInterceptorTest
 */
@RunWith(MockitoJUnitRunner.class)
class ExceptionInterceptorTest {

    @InjectMocks
    @Spy
    ExceptionInterceptor exceptionInterceptorSpy;
    private CustomException customException = new CustomException(1, "Some message", "Some details");

    @BeforeEach
    void setUp() {
        initMockito();
    }

    private void initMockito() {
        // init annotations
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handleAllExceptionsTest() {
        ResponseEntity<Object> response = exceptionInterceptorSpy.handleAllExceptions(customException);
        CustomExceptionSchema expectedResponse = new CustomExceptionSchema(1, "Some message", "Some details");
        ResponseEntity<Object> expected = new ResponseEntity<>(expectedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody(), sameBeanAs(expected.getBody()));
    }


}