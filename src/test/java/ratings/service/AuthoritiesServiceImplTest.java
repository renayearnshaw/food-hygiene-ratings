package ratings.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ratings.model.Authority;
import ratings.model.AuthorityResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthoritiesServiceImplTest {

    private static final String VERSION_KEY = "x-api-version";
    private static final String VERSION_VALUE = "2";
    private static final String AUTHORITIES_URI = "http://api.ratings.food.gov.uk/Authorities/basic";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestTemplate restTemplate;

    @Mock
    ResponseEntity<AuthorityResponse> responseEntity;

    @Mock
    AuthorityResponse authorityResponse;

    @InjectMocks
    private AuthoritiesService authoritiesService = new AuthoritiesServiceImpl(VERSION_KEY, VERSION_VALUE, AUTHORITIES_URI);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAuthorities() {

        //Given
        AuthorityResponse authorityResponse = new AuthorityResponse();
        authorityResponse.setAuthorities(Collections.singletonList(new Authority()));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(AuthorityResponse.class))
                .getBody()).thenReturn(authorityResponse);

        //When
        List<Authority> authorities = authoritiesService.getAuthorities();

        //Then
        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(AuthorityResponse.class));
        assertEquals(1, authorities.size());

    }
}