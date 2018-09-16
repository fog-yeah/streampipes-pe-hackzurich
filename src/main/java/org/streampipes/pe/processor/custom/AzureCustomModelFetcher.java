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
package org.streampipes.pe.processor.custom;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class AzureCustomModelFetcher {

  private byte[] imageBytes;
  private String apiKey;
  private String selectedModel;

  private List<String> urls = Arrays.asList("https://southcentralus.api.cognitive.microsoft" +
          ".com/customvision/v2.0/Prediction/9d630d16-dcc8-4e58-9276-2f93aba8a57c/image?iterationId=81d0a184-d621-4569-af0c-8c7858fa5a8a",
          "https://southcentralus.api.cognitive.microsoft.com/customvision/v2.0/Prediction/9d630d16-dcc8-4e58-9276-2f93aba8a57c/image?iterationId=1ff12bbb-11b2-468b-a2de-fbe69f6db577");

  public AzureCustomModelFetcher(byte[] imageBytes, String apiKey, String selectedModel) {
    this.imageBytes = imageBytes;
    this.apiKey = apiKey;
    this.selectedModel = selectedModel;
  }

  public AzureCustomModelResponse fetchResult() {
    HttpClient httpclient = HttpClients.createDefault();

    String selectedUrl = urls.get(0);

    if (this.selectedModel.equals("M5")) {
      selectedUrl = urls.get(1);
    }
    try
    {
      URIBuilder builder = new URIBuilder(selectedUrl);

      URI uri = builder.build();
      HttpPost request = new HttpPost(uri);
      request.setHeader("Content-Type", "application/octet-stream");
      request.setHeader("Prediction-Key", this.apiKey);


      // Request body
      ByteArrayEntity reqEntity = new ByteArrayEntity(imageBytes);
      request.setEntity(reqEntity);

      HttpResponse response = httpclient.execute(request);
      HttpEntity entity = response.getEntity();

      if (entity != null)
      {
        String modelResponse = EntityUtils.toString(entity);
        System.out.println(modelResponse);
        return new Gson().fromJson(modelResponse, AzureCustomModelResponse.class);
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
