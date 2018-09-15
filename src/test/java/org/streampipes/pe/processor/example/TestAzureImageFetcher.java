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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TestAzureImageFetcher {

  public static void main(String[] args) {
    AzureImageFetcher imageFetcher = null;
    try {
      imageFetcher = new AzureImageFetcher(extractBytes(), "");
    } catch (IOException e) {
      e.printStackTrace();
    }

    imageFetcher.fetchResult();
  }

  public static byte[] extractBytes() throws IOException {
    // open image
    File imgPath = new File("C:\\Users\\riemer\\Pictures\\Pixiebob-cat.jpg");

    BufferedImage bImage = ImageIO.read(imgPath);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImageIO.write(bImage, "jpg", bos );
    byte [] data = bos.toByteArray();

   return data;
  }
}
