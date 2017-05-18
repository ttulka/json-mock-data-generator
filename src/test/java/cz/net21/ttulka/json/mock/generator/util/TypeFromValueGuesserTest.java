package cz.net21.ttulka.json.mock.generator.util;

import org.junit.Test;

import cz.net21.ttulka.json.mock.generator.model.NodeTypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ttulka
 */
public class TypeFromValueGuesserTest {

    @Test
    public void tryToGuessTypeOfNodeFirstNameTest() {
        NodeTypes type = TypeFromValueGuesser.guessType("John");

        assertNotNull(type);
        assertEquals(NodeTypes.FIRST_NAME, type);
    }

    @Test
    public void tryToGuessTypeOfNodeLastNameTest() {
        NodeTypes type = TypeFromValueGuesser.guessType("Smith");

        assertNotNull(type);
        assertEquals(NodeTypes.LAST_NAME, type);
    }

    @Test
    public void tryToGuessTypeOfNodeFullNameTest() {
        NodeTypes type = TypeFromValueGuesser.guessType("John Smith");

        assertNotNull(type);
        assertEquals(NodeTypes.FULL_NAME, type);
    }

    @Test
    public void tryToGuessTypeOfNodeAddress1Test() {
        NodeTypes type = TypeFromValueGuesser.guessType("36 Lancaster Dr Se, 39208 Pearl");

        assertNotNull(type);
        assertEquals(NodeTypes.ADDRESS, type);
    }

    @Test
    public void tryToGuessTypeOfNodeAddress2Test() {
        NodeTypes type = TypeFromValueGuesser.guessType("Memphis, 901-640-9178 Shelby");

        assertNotNull(type);
        assertEquals(NodeTypes.ADDRESS, type);
    }

    @Test
    public void tryToGuessTypeOfNodeAddress3Test() {
        NodeTypes type = TypeFromValueGuesser.guessType("College Ave 123, 28301 Fayetteville");

        assertNotNull(type);
        assertEquals(NodeTypes.ADDRESS, type);
    }

    @Test
    public void tryToGuessTypeOfNodeAddress4Test() {
        NodeTypes type = TypeFromValueGuesser.guessType("College Ave 123, Fayetteville 28301");

        assertNotNull(type);
        assertEquals(NodeTypes.ADDRESS, type);
    }

    @Test
    public void tryToGuessTypeOfNodeIdTest() {
        NodeTypes type = TypeFromValueGuesser.guessType("cfcd208495d565ef");

        assertNotNull(type);
        assertEquals(NodeTypes.ID, type);
    }

    @Test
    public void tryToGuessTypeOfNodeEmailTest() {
        NodeTypes type = TypeFromValueGuesser.guessType("nold.field-cherry123@e-wired0.co.uk");

        assertNotNull(type);
        assertEquals(NodeTypes.EMAIL, type);
    }

    @Test
    public void tryToGuessTypeOfNodeTitleTest() {
        NodeTypes type = TypeFromValueGuesser.guessType("PhD.");

        assertNotNull(type);
        assertEquals(NodeTypes.TITLE, type);
    }

    @Test
    public void tryToGuessTypeOfNodePhoneTest() {
        NodeTypes type = TypeFromValueGuesser.guessType("516-407-9573");

        assertNotNull(type);
        assertEquals(NodeTypes.PHONE, type);
    }

    @Test
    public void tryToGuessTypeOfNodeIntegerTest() {
        NodeTypes type = TypeFromValueGuesser.guessType("-123");

        assertNotNull(type);
        assertEquals(NodeTypes.INTEGER, type);
    }

    @Test
    public void tryToGuessTypeOfNodeFloatTest() {
        NodeTypes type = TypeFromValueGuesser.guessType("+123.321");

        assertNotNull(type);
        assertEquals(NodeTypes.FLOAT, type);
    }

    @Test
    public void tryToGuessTypeOfNodeLoremTest() {
        NodeTypes type = TypeFromValueGuesser.guessType("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore.");

        assertNotNull(type);
        assertEquals(NodeTypes.LOREM, type);
    }
}
