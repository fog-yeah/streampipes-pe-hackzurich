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

import org.streampipes.model.DataProcessorType;
import org.streampipes.model.graph.DataProcessorDescription;
import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.model.schema.PropertyScope;
import org.streampipes.sdk.builder.ProcessingElementBuilder;
import org.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.streampipes.sdk.extractor.ProcessingElementParameterExtractor;
import org.streampipes.sdk.helpers.EpProperties;
import org.streampipes.sdk.helpers.EpRequirements;
import org.streampipes.sdk.helpers.Labels;
import org.streampipes.sdk.helpers.OutputStrategies;
import org.streampipes.sdk.helpers.SupportedFormats;
import org.streampipes.sdk.helpers.SupportedProtocols;
import org.streampipes.wrapper.standalone.ConfiguredEventProcessor;
import org.streampipes.wrapper.standalone.declarer.StandaloneEventProcessingDeclarer;

public class CustomClassifierController extends
        StandaloneEventProcessingDeclarer<CustomClassifierParameters> {

  private static final String IMAGE = "image";
  private static final String API_KEY = "api-key";

  @Override
  public DataProcessorDescription declareModel() {
    return ProcessingElementBuilder.create("org.streampipes.processor.azure.vision.custom",
            "Custom Computer Vision Image Classification", "Image " +
                    "Classification Description (Azure)")
            .category(DataProcessorType.FILTER)
            .requiredStream(StreamRequirementsBuilder
                    .create()
                    .requiredPropertyWithUnaryMapping(EpRequirements
                                    .domainPropertyReq("https://image.com"), Labels.from(IMAGE, "Image Classification", ""),
                            PropertyScope.NONE)
                    .build())
            .requiredTextParameter(Labels.from(API_KEY, "API Key", ""))
            .outputStrategy(OutputStrategies.fixed(
                    EpProperties.doubleEp(Labels.empty(), "score", "https://schema.org/score"),
                    EpProperties.stringEp(Labels.empty(), "category", "https://schema.org/category")

            ))
            .supportedProtocols(SupportedProtocols.kafka())
            .supportedFormats(SupportedFormats.jsonFormat())
            .build();
  }

  @Override
  public ConfiguredEventProcessor<CustomClassifierParameters> onInvocation
          (DataProcessorInvocation graph) {
    ProcessingElementParameterExtractor extractor = getExtractor(graph);

    String apiKey = extractor.singleValueParameter(API_KEY, String.class);
    String imageMapping = extractor.mappingPropertyValue(IMAGE);

    CustomClassifierParameters params = new CustomClassifierParameters(graph, apiKey, imageMapping);

    return new ConfiguredEventProcessor<>(params, () -> new CustomClassifier(params));
  }

}

