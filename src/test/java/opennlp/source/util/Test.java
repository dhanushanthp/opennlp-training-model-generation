package opennlp.source.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test {
	public static void main(String[] args) {
		Set<String> set = new HashSet<String>();
		set.add("$");
		String s = "$100asdf";
		if (set.contains( Character.toString(s.charAt(0)))) {
			System.out.println("done");
		}
	}
}
