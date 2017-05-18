package cz.net21.ttulka.json.mock.generator.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import cz.net21.ttulka.json.mock.generator.model.NodeTypes;
import cz.net21.ttulka.json.mock.generator.source.Bundle;

/**
 * Created by ttulka
 */
public class TypeFromValueGuesser {

    private static final String[] addressPatterns = {
            "(\\d+) (.+), ([-\\d\\s]+) (.+)",
            "(\\d+) (.+), (.+) ([-\\d\\s]+)",
            "(.+) (\\d+), ([-\\d\\s]+) (.+)",
            "(.+) (\\d+), (.+) ([-\\d\\s]+)",
            "(.+), (.+) ([-\\d\\s]+)",
            "(.+), ([-\\d\\s]+) (.+)"
    };
    private static final String idPattern = "[\\da-f]{16}";
    private static final String emailPattern ="[\\d\\w\\.\\-_]+@[\\d\\w\\.\\-_]+\\.[\\w]{2,6}";
    private static final String phonePattern ="[\\d]{3}-[\\d]{3}-[\\d]{4}";
    private static final String integerPattern ="[+-]{0,1}[\\d]+";
    private static final String floatPattern ="[+-]{0,1}[\\d]+[\\.]?[\\d]*";
    private static final String loremPattern ="[A-Z]([\\w,\\.]+ ){10,}[\\w,]+\\.";

    private static final Map<NodeTypes, Bundle> bundles = new HashMap<>();

    static {
        bundles.put(NodeTypes.FIRST_NAME, new Bundle(NodeTypes.FIRST_NAME));
        bundles.put(NodeTypes.LAST_NAME, new Bundle(NodeTypes.LAST_NAME));
        bundles.put(NodeTypes.TITLE, new Bundle(NodeTypes.TITLE));
        // TODO
    }

    public static NodeTypes guessType(Object value) {
        final String stringValue = value.toString().trim();

        if (tryToFindMatchInBundles(NodeTypes.TITLE, stringValue)) {
            return NodeTypes.TITLE;
        }
        if (tryToFindMatchInBundles(NodeTypes.FIRST_NAME, stringValue)) {
            return NodeTypes.FIRST_NAME;
        }
        if (tryToFindMatchInBundles(NodeTypes.LAST_NAME, stringValue)) {
            return NodeTypes.LAST_NAME;
        }
        if (tryToMatchFullName(stringValue)) {
            return NodeTypes.FULL_NAME;
        }
        if (tryToMatchAddress(stringValue)) {
            return NodeTypes.ADDRESS;
        }
        if (tryToMatchPattern(idPattern, stringValue)) {
            return NodeTypes.ID;
        }
        if (tryToMatchPattern(emailPattern, stringValue)) {
            return NodeTypes.EMAIL;
        }
        if (tryToMatchPattern(phonePattern, stringValue)) {
            return NodeTypes.PHONE;
        }
        if (tryToMatchPattern(integerPattern, stringValue)) {
            return NodeTypes.INTEGER;
        }
        if (tryToMatchPattern(floatPattern, stringValue)) {
            return NodeTypes.FLOAT;
        }
        if (tryToMatchPattern(loremPattern, stringValue)) {
            return NodeTypes.LOREM;
        }
        return null;
    }

    private static boolean tryToFindMatchInBundles(NodeTypes potentialType, String stringValue) {
        return bundles.get(potentialType).getValues().stream()
                .filter(stringValue::equals)
                .findAny()
                .isPresent();
    }

    private static boolean tryToMatchFullName(String stringValue) {
        String[] split = stringValue.split(" ");
        if (split.length == 2) {
            return tryToFindMatchInBundles(NodeTypes.FIRST_NAME, split[0])
                   && tryToFindMatchInBundles(NodeTypes.LAST_NAME, split[1]);
        }
        return false;
    }

    private static boolean tryToMatchAddress(String stringValue) {
        return Arrays.stream(addressPatterns)
                .map(Pattern::compile)
                .map(p -> p.matcher(stringValue))
                .filter(m -> m.matches())
                .findAny()
                .isPresent();
    }

    private static boolean tryToMatchPattern(String pattern, String stringValue) {
        return Pattern.compile(pattern).matcher(stringValue).matches();
    }
}
