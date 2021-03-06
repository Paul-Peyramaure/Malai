package org.malai.swing.interaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.malai.interaction.Interaction;
import org.malai.interaction.InteractionHandler;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.malai.swing.interaction.library.KeysScrolling;

public class TestKeysScrolling extends TestInteraction<KeysScrolling> {
	@Override
	@Before
	public void setUp() {
		super.setUp();
		interaction = new KeysScrolling();
	}



	@Test
	public void testKeyPressKeyPressScroll() {
		interaction.onKeyPressure(123, 'a', 100, new JButton());
		interaction.onKeyPressure(234, 'a', 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				assertEquals(interaction.getIncrement(), 3);
				assertEquals(interaction.getKeys().size(), 2);
				assertEquals((int)interaction.getKeys().get(0), 123);
				assertEquals((int)interaction.getKeys().get(1), 234);
				assertEquals(interaction.getLastHIDUsed(), 33);
				assertEquals(interaction.getKeyHIDUsed(), 100);
				assertEquals(interaction.getPx(), 200., 0.0);
				assertEquals(interaction.getPy(), 300., 0.0);
				visitStop = true;
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				visitStart = true;// Because of key event recycling.
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onScroll(200, 300, -1, 3, 100, 33, new JButton());
		assertTrue(visitUpdate);
		assertTrue(visitStop);
		assertTrue(visitStart);
	}



	@Test
	public void testKeyPressKeyPressKeyRelease3() {
		interaction.onKeyPressure(123, 'a', 100, new JButton());
		interaction.onKeyPressure(234, 'a', 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyRelease(123, 'a', 101, new JButton());
	}


	@Test
	public void testKeyPressKeyPressKeyRelease2() {
		interaction.onKeyPressure(123, 'a', 100, new JButton());
		interaction.onKeyPressure(234, 'a', 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
				assertEquals(interaction.getIncrement(), 0);
				assertEquals(interaction.getKeys().size(), 1);
				assertEquals((int)interaction.getKeys().get(0), 234);
				assertEquals(interaction.getLastHIDUsed(), -1);
				assertEquals(interaction.getKeyHIDUsed(), 100);
				assertEquals(interaction.getPx(), 0., 0.0);
				assertEquals(interaction.getPy(), 0., 0.0);
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyRelease(123, 'b', 100, new JButton());
		assertTrue(visitUpdate);
	}



	@Test
	public void testKeyPressKeyPressKeyRelease1() {
		interaction.onKeyPressure(123, 'a', 100, new JButton());
		interaction.onKeyPressure(234, 'a', 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
				assertEquals(interaction.getIncrement(), 0);
				assertEquals(interaction.getKeys().size(), 1);
				assertEquals((int)interaction.getKeys().get(0), 123);
				assertEquals(interaction.getLastHIDUsed(), -1);
				assertEquals(interaction.getKeyHIDUsed(), 100);
				assertEquals(interaction.getPx(), 0., 0.0);
				assertEquals(interaction.getPy(), 0., 0.0);
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyRelease(234, 'a', 100, new JButton());
		assertTrue(visitUpdate);
	}



	@Test
	public void testKeyPressKeyPressWidthDiffHid() {
		interaction.onKeyPressure(123, 'a', 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyPressure(234, 'a', 101, new JButton());
	}



	@Test
	public void testKeyPressKeyPress() {
		interaction.onKeyPressure(123, 'a', 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
				assertEquals(interaction.getIncrement(), 0);
				assertEquals(interaction.getKeys().size(), 2);
				assertEquals((int)interaction.getKeys().get(0), 123);
				assertEquals((int)interaction.getKeys().get(1), 234);
				assertEquals(interaction.getLastHIDUsed(), -1);
				assertEquals(interaction.getKeyHIDUsed(), 100);
				assertEquals(interaction.getPx(), 0., 0.0);
				assertEquals(interaction.getPy(), 0., 0.0);
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyPressure(234, 'a', 100, new JButton());
		assertTrue(visitUpdate);
	}



	@Test
	public void testKeyPressKeyReleaseWithDiffHid() {
		interaction.onKeyPressure(123, 'a', 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyRelease(123, 'a', 200, new JButton());
	}



	@Test
	public void testKeyPressKeyReleaseWithDiffKey() {
		interaction.onKeyPressure(123, 'a', 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyRelease(1213, 'a', 100, new JButton());
	}


	@Test
	public void testKeyPressKeyRelease() {
		interaction.onKeyPressure(123, 'a', 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				visitAbort = true;
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyRelease(123, 'a', 100, new JButton());
		assertTrue(visitAbort);
	}


	@Test
	public void testKeyPress() {
		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
				assertEquals(interaction.getIncrement(), 0);
				assertEquals(interaction.getKeys().size(), 1);
				assertEquals((int)interaction.getKeys().get(0), 123);
				assertEquals(interaction.getLastHIDUsed(), -1);
				assertEquals(interaction.getKeyHIDUsed(), 100);
				assertEquals(interaction.getPx(), 0., 0.0);
				assertEquals(interaction.getPy(), 0., 0.0);
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				visitStart = true;
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyPressure(123, 'a', 100, new JButton());
		assertTrue(visitStart);
		assertTrue(visitUpdate);
	}


	@Test
	public void testScroll() {
		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				visitStop = true;
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				visitStart = true;
				assertEquals(interaction.getIncrement(), -5);
				assertEquals(interaction.getKeys().size(), 0);
				assertEquals(interaction.getLastHIDUsed(), 50);
				assertEquals(interaction.getKeyHIDUsed(), -1);
				assertEquals(interaction.getPx(), 10., 0.0);
				assertEquals(interaction.getPy(), 20., 0.0);
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onScroll(10, 20, 1, 5, 30, 50, new JButton());

		assertTrue(visitStart);
		assertTrue(visitStop);
	}
}
