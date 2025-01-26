/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.youtube;

import domain.exceptions.MyOwnException;
import domain.exceptions.messages.TimeOut;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class APICaller {

  public static String getData(String urlString) throws MyOwnException {
    return getDataFromApi(urlString, 2);
  }

  private synchronized static String getDataFromApi(String urlString, int retries)
      throws MyOwnException {

    String content;
    try {
      URL url = new URL(urlString);
      try (InputStream is = url.openStream()) {
        content = new String(is.readAllBytes());
      }
    } catch (IOException e) {
      return retry(urlString, retries);
    }

    return content;
  }

  private static String retry(String urlString, int retries) throws MyOwnException {
    if (retries > 0) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        throw new MyOwnException(() -> "Could not retry API call. Waiting crashed", ex);
      }
      int restRetries = retries - 1;
      return getDataFromApi(urlString, restRetries);
    }
    throw new MyOwnException(new TimeOut(urlString), null);
  }

}
