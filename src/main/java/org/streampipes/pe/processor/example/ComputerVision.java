
package org.streampipes.pe.processor.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.pe.processor.example.model.AzureResponse;
import org.streampipes.wrapper.routing.SpOutputCollector;
import org.streampipes.wrapper.standalone.engine.StandaloneEventProcessorEngine;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;

public class ComputerVision extends StandaloneEventProcessorEngine<ComputerVisionParameters> {

  private static Logger LOG;

  private String imageFieldName;
  private String apiKey;
  private ObjectMapper oMapper;

  public ComputerVision(ComputerVisionParameters params) {
        super(params);
  }

  @Override
  public void onInvocation(ComputerVisionParameters parameters,
        DataProcessorInvocation graph) {
    this.imageFieldName = parameters.getImageMapping();
    this.apiKey = parameters.getApiKey();
    this.oMapper = new ObjectMapper();

  }

  @Override
  public void onEvent(Map<String, Object> in, String sourceInfo, SpOutputCollector out) {
    String imageBase64 = String.valueOf(in.get(imageFieldName));

    byte[] imageBytes = new byte[0];
    try {
      imageBytes = Base64.getDecoder().decode(imageBase64.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    AzureResponse response = new AzureImageFetcher(imageBytes, apiKey).fetchResult();

    if (response != null) {
      response.setImage(imageBase64);
      out.onEvent(oMapper.convertValue(response, Map.class));
    }


  }

  @Override
  public void onDetach() {

  }
}
