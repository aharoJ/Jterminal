/*
 * Copyright (c) 2009-2011 Graham Edgecombe.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package io.aharoj;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import io.aharoj.bell.BellStrategy;
import io.aharoj.vt100.Vt100TerminalModel;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import io.aharoj.bell.BellStrategy;
import io.aharoj.vt100.Vt100TerminalModel;

import org.junit.BeforeClass;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A test for the {@link Vt100TerminalModel} class.
 * 
 * @author Graham Edgecombe
 */
public class TestVt100TerminalModel {

	/**
	 * The terminal model.
	 */
	private TerminalModel model;
	public static int test_count;
	public static FileWriter results_writer;

	/**
	 * Sets up the terminal model.
	 */
	@Before
	public void setUp() {
		model = new Vt100TerminalModel();
	}

	@BeforeClass
	public static void setUpWriter() {
		try {
			results_writer = new FileWriter("./M2.txt");
		} catch (IOException e) {
		}
		test_count = 1;
	}

	public void printTestOutput(int line_number, Object output) {
		
		  try {
		 results_writer.write(line_number+", "+output);
		 results_writer.write("\n");
		 results_writer.flush();
		  
		  } catch (IOException e) {
		  System.out.println("An error occurred.");
		  e.printStackTrace();
		  }
		 test_count++;
		 
	}

	/**
	 * Tests the word wrapping.
	 */
	@Test
	public void testWordWrapping() {
		try {
			int width = model.getColumns();
			for (int i = 0; i < (width + 1); i++) {
				model.print("h");
			}
			printTestOutput(1, model.getCell(0, 1).getCharacter());
		} catch (Exception e) {
			printTestOutput(1, e.getClass().getName());
		}
		// ----------------------------------------------------------------------
//assertEquals('h', model.getCell(0, 1).getCharacter());//test1
	}

	@Test

	public void testWordWrapping2() {
		try {
			int width = 1 + model.getColumns();
			for (int i = 0; i < (width + 1); i++) {
				model.print("h");
			}
			printTestOutput(2, model.getCell(0, 1).getCharacter());
		} catch (Exception e) {
			printTestOutput(2, e.getClass().getName());
		}
	}

	/**
	 * Tests special ASCII characters.
	 */
	@Test
	public void testSpecialCharacters() {
		try {
			model.print("\u0000");
			printTestOutput(3, model.getCell(0, 0));// mycode
		} catch (Exception e) {
			printTestOutput(3, e.getClass().getName());
		}
		// ----------------------------------------------------------------------
//assertNull(model.getCell(0, 0));//test2
		try {
			model.print("a\rb\rc");
			printTestOutput(4, model.getCell(0, 0).getCharacter());
//assertEquals('c', model.getCell(0, 0).getCharacter());// test3
		} catch (Exception e) {
			printTestOutput(4, e.getClass().getName());
		}
		try {
			model.print("\na");
			printTestOutput(5, model.getCell(0, 0).getCharacter());
			// ----------------------------------------------------------------------
//assertEquals('c', model.getCell(0, 0).getCharacter());//test4
			printTestOutput(6, model.getCell(0, 1).getCharacter());
			// ----------------------------------------------------------------------
//assertEquals('a', model.getCell(0, 1).getCharacter());//test5
		} catch (Exception e) {
			printTestOutput(5, e.getClass().getName());
			printTestOutput(6, e.getClass().getName());
		}
		try {
			model.print("\u007F");
			printTestOutput(7, model.getCell(0, 1));
			// ----------------------------------------------------------------------
//assertNull(model.getCell(0, 1));//test6
		} catch (Exception e) {
			printTestOutput(7, e.getClass().getName());
		}
		try {
			model.print("A\tB");
			printTestOutput(8, model.getCell(8, 1).getCharacter());
			// ----------------------------------------------------------------------
//assertEquals('B', model.getCell(8, 1).getCharacter());//test7
		} catch (Exception e) {
			printTestOutput(8, e.getClass().getName());
		}
	}

	/**
	 * Tests that the terminal scrolls once the buffer is full.
	 */
	@Test
	public void testBuffer() {
		try {
			model = new Vt100TerminalModel(model.getColumns(), 2, 2);
			model.print("This is line one.\r\n");
			model.print("This is line two. XXXXXX\r\n");
			model.print("And this is line three!");
			printTestOutput(9, model.getCell(0, 1).getCharacter());
			// ----------------------------------------------------------------------
//assertEquals('A', model.getCell(0, 1).getCharacter());//test8
			printTestOutput(10, model.getCell(23, 1));
			// ----------------------------------------------------------------------
//assertNull(model.getCell(23, 1));//test9
		} catch (Exception e) {
			printTestOutput(9, e.getClass().getName());
			printTestOutput(10, e.getClass().getName());

		}
	}

	@Test
	public void testBuffer2() {
		try {
			model = new Vt100TerminalModel(model.getColumns(), 2, 2);
			model.print("This is line one.\r\n");
			model.print("This is line two. XXXXXX\r\n");
			model.print("And this is line three!");
			printTestOutput(11, model.getCell(0, 5).getCharacter());
			// printTestOutput(131,model.getCell(30, 1).getCharacter());

			printTestOutput(12, model.getCell(23, 1));
			// ----------------------------------------------------------------------
//assertNull(model.getCell(23, 1));//test10
		} catch (Exception e) {
			printTestOutput(11, e.getClass().getName());
			printTestOutput(12, e.getClass().getName());

		}
	}

	/**
	 * Tests the erase functionality.
	 */

	@Test
	public void testErase() {
		model.print("Hello");
		model.print("\u009B2J");

		// ----------------------------------------------------------------------
		//assertNull(model.getCell(0, 0)); //test11
		// ----------------------------------------------------------------------
		//assertNull(model.getCell(1, 0)); //test12
		// ----------------------------------------------------------------------
		//assertNull(model.getCell(2, 0)); //test13
		// ----------------------------------------------------------------------
		//assertNull(model.getCell(3, 0)); //test14
		// ----------------------------------------------------------------------
		//assertNull(model.getCell(4, 0)); //test15
	}

	/**
	 * Tests moving the cursor.
	 */
	@Test
	public void testMoveCursor() {
		try {
			model.print("\u009B5B");
			printTestOutput(13, model.getCursorRow());
			// ----------------------------------------------------------------------
//assertEquals(5, model.getCursorRow());//test16
		} catch (Exception e) {
			printTestOutput(13, e.getClass().getName());
		}
		try {
			model.print("\u009B3A");
			printTestOutput(14, model.getCursorRow());
			// ----------------------------------------------------------------------
//assertEquals(2, model.getCursorRow());//test17
		} catch (Exception e) {
			printTestOutput(14, e.getClass().getName());
		}
		try {
			model.print("\u009B7C");
			printTestOutput(15, model.getCursorColumn());
			// ----------------------------------------------------------------------
assertEquals(7, model.getCursorColumn());//test18
		} catch (Exception e) {
			printTestOutput(15, e.getClass().getName());
		}
		try {
			model.print("\u009B4D");
			printTestOutput(16, model.getCursorColumn());
			// ----------------------------------------------------------------------
//assertEquals(3, model.getCursorColumn());//test19
		} catch (Exception e) {
			printTestOutput(16, e.getClass().getName());
		}
		model.setCursorColumn(15);
		model.setCursorRow(0);
		try {// is it the right place to put try code
			model.print("\u009B3E");
			printTestOutput(17, model.getCursorColumn());
			// ----------------------------------------------------------------------
//assertEquals(0, model.getCursorColumn());//test20
			printTestOutput(18, model.getCursorRow());
			assertEquals(3, model.getCursorRow());// test21
		} catch (Exception e) {
			printTestOutput(17, e.getClass().getName());
			printTestOutput(18, e.getClass().getName());
		}
		try {
			model.setCursorColumn(7);

			model.print("\u009BF");
			printTestOutput(19, model.getCursorColumn());
			//assertEquals(0, model.getCursorColumn());// test22
			printTestOutput(20, model.getCursorRow());
			//assertEquals(2, model.getCursorRow());// test23
		} catch (Exception e) {
			printTestOutput(19, e.getClass().getName());
			printTestOutput(20, e.getClass().getName());
		}
		try {
			model.print("\u009B4;8H");
			printTestOutput(21, model.getCursorRow());
			printTestOutput(22, model.getCursorColumn());
			//assertEquals(7, model.getCursorColumn());// test24
		} catch (Exception e) {
			printTestOutput(21, e.getClass().getName());
			printTestOutput(22, e.getClass().getName());

		}
	}

	/**
	 * Tests the SGR escape sequence.
	 */
	@Test
	public void testSgr() {
		try {
			model.print("\u009B2;33;41mX");

			TerminalCell cell = model.getCell(0, 0);
			printTestOutput(23, cell);
			//assertNotNull(cell);// test25
			printTestOutput(24, cell.getCharacter());
			//assertEquals('X', cell.getCharacter());// test26
			printTestOutput(25, cell.getBackgroundColor());
			//assertEquals(Color.RED, cell.getBackgroundColor());// test27
			printTestOutput(26, cell.getForegroundColor());
			//assertEquals(Color.YELLOW, cell.getForegroundColor());// test28
		} catch (Exception e) {
			printTestOutput(23, e.getClass().getName());
			printTestOutput(24, e.getClass().getName());
			printTestOutput(25, e.getClass().getName());
			printTestOutput(26, e.getClass().getName());
		}
		try {
			model.print("\u009B0m\rX");

			TerminalCell cell = model.getCell(0, 0);
			printTestOutput(27, cell);
			//assertNotNull(cell);// test29
			printTestOutput(28, cell.getCharacter());
			//assertEquals('X', cell.getCharacter());// test30
			printTestOutput(29, cell.getBackgroundColor());
			//assertEquals(model.getDefaultBackgroundColor(), cell.getBackgroundColor());// test31
			printTestOutput(30, cell.getForegroundColor());
			//assertEquals(model.getDefaultForegroundColor(), cell.getForegroundColor());
			// test32
		} catch (Exception e) {
			printTestOutput(27, e.getClass().getName());
			printTestOutput(28, e.getClass().getName());
			printTestOutput(29, e.getClass().getName());
			printTestOutput(30, e.getClass().getName());

		}
	}

	/**
	 * Tests saving and restoring the cursor.
	 */
	@Test
	public void testSaveAndRestoreCursor() {
		try {// is this right?
			model.setCursorColumn(3);
			model.setCursorRow(17);

			model.print("\u009Bs");// what about this?

			model.setCursorColumn(5);
			model.setCursorRow(23);

			model.print("\u009Bu");
			printTestOutput(31, model.getCursorColumn());
			//assertEquals(3, model.getCursorColumn());// test33
			printTestOutput(32, model.getCursorRow());
			//assertEquals(17, model.getCursorRow()); // test34
		} catch (Exception e) {
			printTestOutput(31, e.getClass().getName());
			printTestOutput(32, e.getClass().getName());
		} // end catch
	}

	/**
	 * Tests the printing of a simple message.
	 */
	@Test
	public void testPrint() {
		try {
			model.print("Hi");
			printTestOutput(33, model.getCell(0, 0).getCharacter());
			//assertEquals('H', model.getCell(0, 0).getCharacter()); // test35
			printTestOutput(34, model.getCell(1, 0).getCharacter());
			//assertEquals('i', model.getCell(1, 0).getCharacter()); // test36
			printTestOutput(35, model.getCell(2, 0));
			//assertNull(model.getCell(2, 0)); // test37
		} catch (Exception e) {
			printTestOutput(33, e.getClass().getName());
			printTestOutput(34, e.getClass().getName());
			printTestOutput(35, e.getClass().getName());
		} // end of catch
	}

	/**
	 * Tests that the bell is sounded.
	 */
	@Test
	public void testBell() {
		try {
			final int[] counter = new int[1];

			BellStrategy strategy = new BellStrategy() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see com.grahamedgecombe.jterminal.bell.BellStrategy#soundBell()
				 */
				@Override
				public void soundBell() {
					counter[0]++;
				}
			};

			model.setBellStrategy(strategy);

			model.print("\u0007");
			printTestOutput(36, counter[0]);
			//assertEquals(1, counter[0]); // test38

			model.print("Hello, \u0007World!\u0007\r\n");
			printTestOutput(37, counter[0]);
			//assertEquals(3, counter[0]); // test39
		} catch (Exception e) {
			printTestOutput(36, e.getClass().getName());
			printTestOutput(37, e.getClass().getName());
		}
	}
}