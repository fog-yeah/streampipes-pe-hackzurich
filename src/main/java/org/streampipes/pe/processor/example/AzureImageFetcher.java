/*
Copyright 2018 FZI Forschungszentrum Informatik

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.streampipes.pe.processor.example;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.streampipes.pe.processor.example.model.AzureResponse;

import java.net.URI;

public class AzureImageFetcher {

  private byte[] imageBytes;
  private String apiKey;

  public AzureImageFetcher(byte[] imageBytes, String apiKey) {
    this.imageBytes = imageBytes;
    this.apiKey = apiKey;
  }

  public AzureResponse fetchResult() {
    HttpClient httpclient = HttpClients.createDefault();

    try
    {
      URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/vision/v1.0/analyze");

      builder.setParameter("visualFeatures", "Categories,Tags,Description");
      //builder.setParameter("details", "{string}");
      builder.setParameter("language", "en");

      URI uri = builder.build();
      HttpPost request = new HttpPost(uri);
      request.setHeader("Content-Type", "application/octet-stream");
      request.setHeader("Ocp-Apim-Subscription-Key", this.apiKey);


      // Request body
      ByteArrayEntity reqEntity = new ByteArrayEntity(imageBytes);
      request.setEntity(reqEntity);

      HttpResponse response = httpclient.execute(request);
      HttpEntity entity = response.getEntity();

      if (entity != null)
      {
        String modelResponse = EntityUtils.toString(entity);
        return new Gson().fromJson(modelResponse, AzureResponse.class);
      } else {
        return null;
      }
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }

    return null;
  }
}
