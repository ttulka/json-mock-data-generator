package cz.net21.ttulka.json.mock.generator.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Node types test.
 *
 * @author ttulka
 */
public class NodeTypesTest {

    @Test
    public void parseTest() {
        assertEquals(NodeTypes.VALUE, NodeTypes.parse("value"));
        assertEquals(NodeTypes.COMPOSITE, NodeTypes.parse("composite"));
        assertEquals(NodeTypes.LIST, NodeTypes.parse("list"));
        assertEquals(NodeTypes.ARRAY, NodeTypes.parse("array"));
        assertEquals(NodeTypes.INTEGER, NodeTypes.parse("integer"));
        assertEquals(NodeTypes.FLOAT, NodeTypes.parse("float"));
        assertEquals(NodeTypes.DATE, NodeTypes.parse("date"));
        assertEquals(NodeTypes.RANDOM, NodeTypes.parse("random"));
        assertEquals(NodeTypes.ID, NodeTypes.parse("id"));
        assertEquals(NodeTypes.LOREM, NodeTypes.parse("lorem"));
        assertEquals(NodeTypes.FULL_NAME, NodeTypes.parse("fullName"));
        assertEquals(NodeTypes.FIRST_NAME, NodeTypes.parse("firstName"));
        assertEquals(NodeTypes.LAST_NAME, NodeTypes.parse("lastName"));
        assertEquals(NodeTypes.TITLE, NodeTypes.parse("title"));
        assertEquals(NodeTypes.EMAIL, NodeTypes.parse("email"));
        assertEquals(NodeTypes.PHONE, NodeTypes.parse("phone"));
        assertEquals(NodeTypes.ADDRESS, NodeTypes.parse("address"));
        assertEquals(NodeTypes.FILE, NodeTypes.parse("file"));
    }
}
