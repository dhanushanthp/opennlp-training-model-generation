package opennlp.source.namefinder.modelretrainer;

import java.io.File;
import opennlp.addons.modelbuilder.DefaultModelBuilderUtil;

public class ModelBuilderAddonUse {
  public static void main(String[] args) {
    File sentences = new File("data-for-trainng/sentence");

    File knownEntities = new File("data-for-trainng/knownentities");

    File blacklistedentities = new File("data-for-trainng/blentities");

    File annotatedSentences = new File("data-for-trainng/anno-sentence");

    File theModel = new File("/opt/data-extractor/nlp-models/en-ner-person.bin");

    DefaultModelBuilderUtil.generateModel(sentences, knownEntities, blacklistedentities,
            theModel, annotatedSentences, "person", 10);
  }
}