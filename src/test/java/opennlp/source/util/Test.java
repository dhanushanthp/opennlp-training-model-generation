package opennlp.source.util;

import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		int number_of_divide = 4;
		int i = 5;
		System.out.println(i * number_of_divide + number_of_divide -1);
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		list.add("four");
		list.add("five");
		list.add("six");
		System.out.println(list.subList(1, list.size()));
	}
}
