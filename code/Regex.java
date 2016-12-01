package iiit.ire.miniproj.indexerutils;

import java.util.regex.Pattern;

public class Regex {
	   public static Pattern patternSeparator = Pattern.compile("[^a-z0-9]");
	   public static Pattern patternGarbage = Pattern.compile("\\{\\{.*\\}\\}");
	   public static Pattern patternSplit = Pattern.compile("-");
	   public static Pattern patternCategory = Pattern.compile("\\[\\[category:[a-z1-9 ]+\\]\\]");
	   public static Pattern patternInfoBox = Pattern.compile("\\{\\{infobox.*\\}\\}\n\n", Pattern.DOTALL);
	   public static Pattern patternReferences = Pattern.compile("==[ ]?references[ ]?==.*\n\n", Pattern.DOTALL);
	   public static Pattern patternExternalLinks = Pattern.compile("==external links==.*\n\n", Pattern.DOTALL);
}
