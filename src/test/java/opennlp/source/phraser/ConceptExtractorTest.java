package opennlp.source.phraser;

import java.io.IOException;
import java.util.Map;

import core.util.readFile;
import opennlp.source.conditional.chunker.ConceptExtractor;
import opennlp.tools.util.InvalidFormatException;

public class ConceptExtractorTest {
	private static int SWITCH = 0;
	public static void main(String[] args) throws InvalidFormatException, IOException {
		ConceptExtractor c = new ConceptExtractor();
		String[] input = {
				"One immediate benefit of making this assumption is that, if we allow for only two types of assets, asset market equilibrium reduces to he condition that the quantity of money supplied equals the quantity of money demanded.",
				"The aggregate demand(AD) curve slopes downward",
				"The two principal business cycle theories that we discuss in this book are the classical and the Keynesian theories",
				"The aggregate demand (AD) curve shows for any price level, P, the total quantity of goods and services, Y, demanded by households, firms, and governments",
				"The aggregate demand (AD) curve slopes downward, reflecting the fact that the aggregate quantity of goods and services demanded, Y, falls when the price level, P, rises",
				"I like to learn natural language processing and Artificial Intellegence",
				"We have already discussed the need for aggregation in macroeconomics. In the economic model of this book we follow standard macroeconomic practice and aggregate all the markets in the economy into"
						+ "three major markets: the market for goods and services, the asset market (in which assets such as money, stocks, bonds, and real estate are traded), and the labor market. We show how participants in the economy interact in each of"
						+ "these three markets and how these markets relate to one another and to the economy as a whole." };
		Map<String, String> phrases;
		if(SWITCH == 0){
			phrases = c.getConcept(input[0]);	
		}else{
			phrases = c.getConcept(readFile.getText("SampleInput.txt"));
		}
		 
		 System.out.println(phrases.keySet());
	}

}
