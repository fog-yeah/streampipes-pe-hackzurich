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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.wrapper.routing.SpOutputCollector;
import org.streampipes.wrapper.standalone.engine.StandaloneEventProcessorEngine;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;

public class CustomClassifier extends StandaloneEventProcessorEngine<CustomClassifierParameters> {

  private static Logger LOG;

  private String imageFieldName;
  private String apiKey;
  private ObjectMapper oMapper;
  private String selectedModel;

  public CustomClassifier(CustomClassifierParameters params) {
    super(params);
  }


  @Override
  public void onInvocation(CustomClassifierParameters params, DataProcessorInvocation
          dataProcessorInvocation) {
    this.imageFieldName = params.getImageMapping();
    this.apiKey = params.getApiKey();
    this.oMapper = new ObjectMapper();
    this.selectedModel = params.getSelectedModel();

  }

  @Override
  public void onEvent(Map<String, Object> in, String s, SpOutputCollector out) {
    String imageBase64 = String.valueOf(in.get(imageFieldName));

    byte[] imageBytes = new byte[0];
    try {
      imageBytes = Base64.getDecoder().decode(imageBase64.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    AzureCustomModelResponse response = new AzureCustomModelFetcher(imageBytes, apiKey,
            selectedModel).fetchResult();

    if (response != null) {
      response.setImage(imageBase64);
      out.onEvent(oMapper.convertValue(response, Map.class));
    }
  }

  @Override
  public void onDetach() {

  }
}
