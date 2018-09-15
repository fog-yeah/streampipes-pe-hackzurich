package org.streampipes.pe.processor.example;

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

public class ComputerVisionController extends StandaloneEventProcessingDeclarer<ComputerVisionParameters> {

	private static final String IMAGE = "image";
	private static final String API_KEY = "api-key";

	@Override
	public DataProcessorDescription declareModel() {
		return ProcessingElementBuilder.create("org.streampipes.processor.azure.vision.image",
						"Azure Computer Vision Image Classification", "Image " +
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
	public ConfiguredEventProcessor<ComputerVisionParameters> onInvocation
				(DataProcessorInvocation graph) {
		ProcessingElementParameterExtractor extractor = getExtractor(graph);

		String apiKey = extractor.singleValueParameter(API_KEY, String.class);
		String imageMapping = extractor.mappingPropertyValue(IMAGE);

		ComputerVisionParameters params = new ComputerVisionParameters(graph, apiKey, imageMapping);

		return new ConfiguredEventProcessor<>(params, () -> new ComputerVision(params));
	}

}
