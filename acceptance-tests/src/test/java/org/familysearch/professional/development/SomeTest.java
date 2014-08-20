package org.familysearch.professional.development;

import java.io.IOException;
import java.net.URL;

import com.google.api.client.http.GenericUrl;
import org.testng.annotations.Test;

import org.familysearch.professional.development.helpers.Client;

public class SomeTest extends TestBase {

  @Test
  public void testClient() throws IOException {
    Client client = new Client();
    GenericUrl genericUrl = new GenericUrl(new URL(this.getHost() + "/healthcheck"));
    System.out.println(genericUrl);
    String healthCheckStatus = client.request(genericUrl, String.class);
    System.out.println(healthCheckStatus);
  }

}