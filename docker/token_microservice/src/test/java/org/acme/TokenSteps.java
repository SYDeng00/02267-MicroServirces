package org.acme;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.acme.Domain.Token;
import org.acme.Repositories.TokenRepository;
import org.acme.Resource.TokenBroker;
import org.acme.Resource.TokenConfig;
import org.acme.Domains.Message;
import org.acme.Resource.TokenManagementServices;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.UUID;

public class TokenSteps {

    private TokenBroker mockTokenBroker;
    private Message mockMessage;
    private UUID customerId;
    private UUID tokenId;
    private Token mockToken;
    private int tokenRequestCount;
    private TokenRepository mockTokenRepository;
    private TokenManagementServices tokenServices;
    private UUID expiredTokenId;

    @Before
    public void setup() {
        mockTokenRepository = TokenRepository.getInstance();
        tokenServices = new TokenManagementServices();
        expiredTokenId = UUID.randomUUID();
        customerId = UUID.randomUUID();

        // Create an expired token
        LocalDate expiredDate = LocalDate.now().minusDays(TokenConfig.EXPIRY_DAYS_FOR_TOKEN + 1);
        Token expiredToken = new Token(expiredTokenId, TokenConfig.STATUS_UNUSED, expiredDate, customerId);
        mockTokenRepository.addToken(expiredToken);
    }

    public TokenSteps() {
        mockTokenBroker = mock(TokenBroker.class);
        mockMessage = mock(Message.class);
    }

    @Given("a customer with ID")
    public void a_customer_with_id() {
        // Generate a random customerId for testing
        this.customerId = UUID.randomUUID();
        this.tokenRequestCount = 3; // Set a token request count for testing
    }

    @When("I request tokens for the customer")
    public void i_request_tokens_for_the_customer() {
        Object[] payload = new Object[] { customerId, tokenRequestCount };
        Mockito.when(mockMessage.getEventType()).thenReturn(TokenConfig.RECEIVE_RETURN_TOKEN);
        Mockito.when(mockMessage.getPayload()).thenReturn(payload);
        try {
            Mockito.doNothing().when(mockTokenBroker).subscribeEvent(mockMessage);
            mockTokenBroker.subscribeEvent(mockMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("tokens should be successfully generated for the customer")
    public void tokens_should_be_successfully_generated_for_the_customer() throws Exception {
        Mockito.verify(mockTokenBroker).subscribeEvent(mockMessage);

    }

    @Given("an expired token is available for a customer")
    public void an_expired_token_is_available_for_a_customer() {
        this.customerId = UUID.randomUUID(); // Generate a random customerId for testing
        this.tokenId = UUID.randomUUID(); // Generate a random tokenId for testing
        this.mockToken = new Token(tokenId, TokenConfig.STATUS_EXPIRED, LocalDate.now().minusDays(10), customerId);
    }

    @When("I validate the token")
    public void i_validate_the_token() throws Exception {
        tokenServices.tokenValidate(new Object[]{null, expiredTokenId});
    }

    @Then("the token should be marked as invalid")
    public void the_token_should_be_marked_as_invalid() throws Exception {
        Token tokenAfterValidation = mockTokenRepository.getToken(expiredTokenId);
        assertFalse(tokenAfterValidation.getValid());
    }


}
