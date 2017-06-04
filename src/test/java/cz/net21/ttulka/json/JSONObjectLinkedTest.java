package cz.net21.ttulka.json;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ttulka
 */
public class JSONObjectLinkedTest {

    private static final String JSON1 = "{\"c\":\"c\",\"a\":\"a\",\"b\":\"b\"}";

    private static final String JSON2 = "{\"c\":{\"c\":\"c\",\"a\":\"a\",\"b\":\"b\"},\"a\":{\"c\":\"c\",\"a\":\"a\",\"b\":\"b\"},\"b\":{\"c\":\"c\",\"a\":\"a\",\"b\":\"b\"}}";

    @Test
    public void jsonLinkedTest() {
        assertEquals(JSON1, new JSONObjectLinked(JSON1).toString());
        assertEquals(JSON2, new JSONObjectLinked(JSON2).toString());
    }

    @Test
    public void jsonToMapTest() {
        assertEquals("[c=c, a=a, b=b]", new JSONObjectLinked(JSON1).toMap().entrySet().toString());
        assertEquals("[c={c=c, a=a, b=b}, a={c=c, a=a, b=b}, b={c=c, a=a, b=b}]", new JSONObjectLinked(JSON2).toMap().entrySet().toString());
    }
}
