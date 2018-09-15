package org.streampipes.pe.processor.example;

import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.wrapper.params.binding.EventProcessorBindingParams;

public class ComputerVisionParameters extends EventProcessorBindingParams {

  private String apiKey;
  private String imageMapping;

  public ComputerVisionParameters(DataProcessorInvocation graph, String apiKey, String imageMapping) {
    super(graph);
    this.apiKey = apiKey;
    this.imageMapping = imageMapping;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getImageMapping() {
    return imageMapping;
  }
}
