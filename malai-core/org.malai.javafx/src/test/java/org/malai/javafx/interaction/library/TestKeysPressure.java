package org.malai.javafx.interaction.library;

import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestKeysPressure extends BaseJfXInteractionTest<KeysPressure> {
	@Override
	protected KeysPressure createInteraction() {
		return new KeysPressure();
	}
}
