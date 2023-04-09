package io.aharoj;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import io.aharoj.bell.BellStrategy;
import io.aharoj.vt100.Vt100TerminalModel;

public class TestVt100TerminalModel {

	/**
	 * The terminal model.
	 */
	private TerminalModel model;

	/**
	 * Sets up the terminal model.
	 */
	@Before
	public void setUp(){
		model = new Vt100TerminalModel();
	}

	/**
	 * Tests the word wrapping.
	 */
	@Test
	public void testWordWrapping() {
		int width = model.getColumns();
		for (int i = 0; i < (width + 1); i++) {
			model.print("h");
		}
		assertEquals('h', model.getCell(0, 1).getCharacter());
	}

	/**
	 * Tests special ASCII characters.
	 */
	@Test
	public void testSpecialCharacters() {
		model.print("\u0000");
		assertNull(model.getCell(0, 0));

		model.print("a\rb\rc");
		assertEquals('c', model.getCell(0, 0).getCharacter());

		model.print("\na");
		assertEquals('c', model.getCell(0, 0).getCharacter());
		assertEquals('a', model.getCell(0, 1).getCharacter());

		model.print("\u007F");
		assertNull(model.getCell(0, 1));

		model.print("A\tB");
		assertEquals('B', model.getCell(8, 1).getCharacter());
	}

	/**
	 * Tests that the terminal scrolls once the buffer is full.
	 */
	@Test
	public void testBuffer() {
		model = new Vt100TerminalModel(model.getColumns(), 2, 2);
		model.print("This is line one.\r\n");
		model.print("This is line two. XXXXXX\r\n");
		model.print("And this is line three!");
		assertEquals('A', model.getCell(0, 1).getCharacter());
		assertNull(model.getCell(23, 1));
	}

	/**
	 * Tests the erase functionality.
	 */
	@Test
	public void testErase() {
		model.print("Hello");
		model.print("\u009B2J");
		for (int i = 0; i < 5; i++) {
			assertNull(model.getCell(i, 0));
		}
	}

	/**
	 * Tests moving the cursor.
	 */
	@Test
	public void testMoveCursor() {
		model.print("\u009B5B");
		assertEquals(5, model.getCursorRow());

		model.print("\u009B3A");
		assertEquals(2, model.getCursorRow());

		model.print("\u009B7C");
		assertEquals(7, model.getCursorColumn());

		model.print("\u009B4D");
		assertEquals(3, model.getCursorColumn());

		model.setCursorColumn(15);
		model.setCursorRow(0);

		model.print("\u009B3E");
		assertEquals(0, model.getCursorColumn());
		assertEquals(3, model.getCursorRow());

		model.setCursorColumn(7);

		model.print("\u009BF");
		assertEquals(0, model.getCursorColumn());
		assertEquals(2, model.getCursorRow());

		model.print("\u009B4;8H");
		assertEquals(3, model.getCursorRow());
		assertEquals(7, model.getCursorColumn());
	}

	/**
	 * Tests the SGR escape sequence.
	 */
	@Test
	public void testSgr() {
		model.print("\u009B2;33;41mX");

		TerminalCell cell = model.getCell(0, 0);
		assertNotNull(cell);
		assertEquals('X', cell.getCharacter());

		assertEquals(Color.RED, cell.getBackgroundColor());
		assertEquals(Color.YELLOW, cell.getForegroundColor());

		model.print("\u009B0m\rX");

		cell = model.getCell(0, 0);

		assertNotNull(cell);
		assertEquals('X', cell.getCharacter());

		assertEquals(model.getDefaultBackgroundColor(), cell.getBackgroundColor());
		assertEquals(model.getDefaultForegroundColor(), cell.getForegroundColor());
	}

	/**
	 * Tests saving and restoring the cursor.
	 */
	@Test
	public void testSaveAndRestoreCursor() {
		model.setCursorColumn(3);
		model.setCursorRow(17);

		model.print("\u009Bs");

		model.setCursorColumn(5);
		model.setCursorRow(23);

		model.print("\u009Bu");

		assertEquals(3, model.getCursorColumn());
		assertEquals(17, model.getCursorRow());
	}

	/**
	 * Tests the printing of a simple message.
	 */
	@Test
	public void testPrint() {
		model.print("Hi");
		assertEquals('H', model.getCell(0, 0).getCharacter());
		assertEquals('i', model.getCell(1, 0).getCharacter());
		assertNull(model.getCell(2, 0));
	}

	/**
	 * Tests that the bell is sounded.
	 */
	@Test
	public void testBell() {
		final int[] counter = new int[1];

		BellStrategy strategy = new BellStrategy() {
			/*
			 * (non-Javadoc)
			 * @see com.grahamedgecombe.jterminal.bell.BellStrategy#soundBell()
			 */
			@Override
			public void soundBell() {
				counter[0]++;
			}
		};

		model.setBellStrategy(strategy);

		model.print("\u0007");
		assertEquals(1, counter[0]);

		model.print("Hello, \u0007World!\u0007\r\n");
		assertEquals(3, counter[0]);
	}

}

