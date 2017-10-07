package org.yakimovdenis.resttask.support;

public class PatternResolver {

    public static String resolvePattern(String preRegex) {
        String startsWith;
        String mainData;
        boolean isNotMainData = false;
        String endsWith;
        StringBuilder builder = new StringBuilder((int) (preRegex.length() * 1.5));
        startsWith = preRegex.startsWith("^.*") ? "^.*" : preRegex.startsWith("^") ? "^" : "";
        endsWith = preRegex.endsWith(".*$") ? ".*$" : preRegex.endsWith("$") ? "$" : "";
        mainData = preRegex.substring(startsWith.length(), preRegex.length() - endsWith.length());
        if (mainData.contains("^")) {
            mainData = mainData.replace("^", "");
        } else {
            if (mainData.startsWith("[")) {
                mainData = "[^"+mainData.substring(1,mainData.length());
            } else {
                mainData="[^"+mainData+"]";
            }
            isNotMainData = true;
        }

        boolean normalEnding = startsWith.length()==1;
        if (isNotMainData && endsWith.length() > 1) {
            builder.append("^");
        } else {
            builder.append(startsWith);
        }
        builder.append(mainData);
        if (isNotMainData && endsWith.length()>1 && !normalEnding) {
            builder.append("*$");
        } else {
            builder.append(endsWith);
        }
        return builder.toString();
    }
}
