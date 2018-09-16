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

import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.wrapper.params.binding.EventProcessorBindingParams;

public class CustomClassifierParameters extends EventProcessorBindingParams {

  private String apiKey;
  private String imageMapping;
  private String selectedModel;

  public CustomClassifierParameters(DataProcessorInvocation graph, String apiKey, String
          imageMapping, String selectedModel) {
    super(graph);
    this.apiKey = apiKey;
    this.imageMapping = imageMapping;
    this.selectedModel = selectedModel;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getImageMapping() {
    return imageMapping;
  }

  public String getSelectedModel() {
    return selectedModel;
  }
}
