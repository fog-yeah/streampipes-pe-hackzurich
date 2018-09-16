package org.streampipes.main;

import org.streampipes.container.init.DeclarersSingleton;
import org.streampipes.container.standalone.init.StandaloneModelSubmitter;
import org.streampipes.dataformat.json.JsonDataFormatFactory;
import org.streampipes.messaging.kafka.SpKafkaProtocolFactory;
import org.streampipes.pe.processor.custom.CustomClassifierController;
import org.streampipes.pe.processor.example.ComputerVisionController;

import org.streampipes.config.Config;

public class Init extends StandaloneModelSubmitter {

  public static void main(String[] args) throws Exception {
    DeclarersSingleton.getInstance()
            .add(new ComputerVisionController())
            .add(new CustomClassifierController());

    DeclarersSingleton.getInstance().setPort(Config.INSTANCE.getPort());
    DeclarersSingleton.getInstance().setHostName(Config.INSTANCE.getHost());

    DeclarersSingleton.getInstance().registerDataFormat(new JsonDataFormatFactory());
    DeclarersSingleton.getInstance().registerProtocol(new SpKafkaProtocolFactory());

    new Init().init(Config.INSTANCE);

  }


}
