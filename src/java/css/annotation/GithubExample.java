package css.annotation;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.builder.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

public class GithubExample {

  private static final Log LOGGER = LogFactory.getLog(GithubExample.class);
  private static final String NETWORK_NAME = "Github";
  private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";
  private static final Token EMPTY_TOKEN = null;

  public static void main(String[] args)
  {
    // Replace these with your own api key and secret
    String apiKey = "8f0c28fd94cf6351ff84";
    String apiSecret = "eb1e81d6e0842e7ef7a0053744eaa9e170d7061f";
    OAuthService service = new ServiceBuilder()
                                  .provider(GithubApi.class)
                                  .apiKey(apiKey)
                                  .apiSecret(apiSecret)
                                  .callback("http://localhost:8080/metastyle/static/oauthcallback")
                                  .build();
    Scanner in = new Scanner(System.in);

    LOGGER.info("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
    LOGGER.info("");

    // Obtain the Authorization URL
    LOGGER.info("Fetching the Authorization URL...");
    String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
    LOGGER.info("Got the Authorization URL!");
    LOGGER.info("Now go and authorize Scribe here:");
    LOGGER.info(authorizationUrl);
    LOGGER.info("And paste the authorization code here");
    System.out.print(">>");
    Verifier verifier = new Verifier(in.nextLine());
    LOGGER.info("");
    
    // Trade the Request Token and Verfier for the Access Token
    LOGGER.info("Trading the Request Token for an Access Token...");
    Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
    LOGGER.info("Got the Access Token!");
    LOGGER.info("(if your curious it looks like this: " + accessToken + " )");
    LOGGER.info("");

    // Now let's go and ask for a protected resource!
    LOGGER.info("Now we're going to access a protected resource...");
    OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
    service.signRequest(accessToken, request);
    Response response = request.send();
    LOGGER.info("Got it! Lets see what we found...");
    LOGGER.info("");
    LOGGER.info(response.getCode());
    LOGGER.info(response.getBody());

    LOGGER.info("");
    LOGGER.info("Thats it man! Go and build something awesome with Scribe! :)");

  }
}