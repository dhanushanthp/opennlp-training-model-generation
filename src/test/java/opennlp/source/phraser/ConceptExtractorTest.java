package opennlp.source.phraser;

import java.io.IOException;
import java.util.Map;

import opennlp.tools.util.InvalidFormatException;

public class ConceptExtractorTest {

	public static void main(String[] args) throws InvalidFormatException, IOException {
		ConceptExtractor c = new ConceptExtractor();
		String[] input = {
				"The aggregate demand(AD) curve slopes downward",
				"The two principal business cycle theories that we discuss in this book are the classical and the Keynesian theories",
				"The aggregate demand (AD) curve shows for any price level, P, the total quantity of goods and services, Y, demanded by households, firms, and governments",
				"The aggregate demand (AD) curve slopes downward, reflecting the fact that the aggregate quantity of goods and services demanded, Y, falls when the price level, P, rises",
				"I like to learn natural language processing and Artificial Intellegence",
				"We have already discussed the need for aggregation in macroeconomics. In the economic model of this book we follow standard macroeconomic practice and aggregate all the markets in the economy into"
						+ "three major markets: the market for goods and services, the asset market (in which assets such as money, stocks, bonds, and real estate are traded), and the labor market. We show how participants in the economy interact in each of"
						+ "these three markets and how these markets relate to one another and to the economy as a whole." };
		 Map<String, String> phrases = c.getConcept(input[5]);
		 System.out.println(phrases.keySet());
	}

}
