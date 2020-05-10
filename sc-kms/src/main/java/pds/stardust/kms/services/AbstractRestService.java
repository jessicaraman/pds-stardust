package pds.stardust.kms.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public abstract class AbstractRestService {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final Logger logger;

    @Autowired
    public AbstractRestService(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.logger = LoggerFactory.getLogger(AbstractRestService.class);
    }

    protected  <T> ResponseEntity<T> get(String url, HttpEntity<?> requestEntity, Class<T> responseType) throws Exception {
        return get(url, requestEntity, responseType, true);
    }

    protected <T> ResponseEntity<T> get(String url, HttpEntity<?> requestEntity, Class<T> responseType, boolean logResponseBody) throws Exception {
        return send(url, HttpMethod.GET, requestEntity, responseType, false, logResponseBody);
    }

    protected <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, Class<T> responseType, boolean logRequestBody, boolean logResponseBody) throws Exception {
        return send(url, HttpMethod.POST, requestEntity, responseType, logRequestBody, logResponseBody);
    }

    protected <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, Class<T> responseType) throws Exception {
        return post(url, requestEntity, responseType, true, true);
    }

    protected <T> ResponseEntity<T> put(String url, HttpEntity<?> requestEntity, Class<T> responseType, boolean logRequestBody, boolean logResponseBody) throws Exception {
        return send(url, HttpMethod.PUT, requestEntity, responseType, logRequestBody, logResponseBody);
    }

    protected <T> ResponseEntity<T> put(String url, HttpEntity<?> requestEntity, Class<T> responseType) throws Exception {
        return put(url, requestEntity, responseType, true, true);
    }

    private <T> ResponseEntity<T> send(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, boolean logRequestBody, boolean logResponseBody) throws Exception {

        ResponseEntity<T> response;
        try {

            if (!requestEntity.hasBody()|| !logRequestBody) {
                logger.info("Send [{}] request with url=[{}], method=[{}]", getServiceName(), url, method.name());
            } else {
                logger.info("Send [{}] request with url=[{}], method=[{}], body={}", getServiceName(), url, method.name(), requestEntity.getBody());
            }

            response = restTemplate.exchange(url, method, requestEntity, responseType);

            if (response.getStatusCode().is2xxSuccessful()) {

                logger.info("Receive success [{}] from [{}]", response.getStatusCode().name(), getServiceName());

                if (logResponseBody) {
                    logger.info("Receive [{}] response: {}", getServiceName(), mapper.writeValueAsString(response));
                }

            } else {
                final String errorDetails = (response.getBody() != null) ? response.getBody().toString() : "";
                logger.warn("Receive unexpected response code [{}] from [{}], error details [{}]", response.getStatusCode().name(), getServiceName(), errorDetails);
            }

            return response;

        } catch (HttpStatusCodeException e) {

            logger.warn("Receive applicative error [{}] from [{}], body=[{}], header=[{}]", e.getStatusCode().name(), getServiceName(), e.getResponseBodyAsString(), e.getResponseHeaders());

            throw e;
        }

    }

    protected abstract String getServiceName();

}
