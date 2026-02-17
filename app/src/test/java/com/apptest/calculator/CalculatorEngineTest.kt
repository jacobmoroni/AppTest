package com.apptest.calculator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class CalculatorEngineTest {
    @Test
    fun `respects operator precedence`() {
        assertEquals("14", CalculatorEngine.evaluate("2+3*4"))
    }

    @Test
    fun `handles calculator symbols`() {
        assertEquals("16", CalculatorEngine.evaluate("40÷5+2×4"))
    }

    @Test
    fun `supports unary minus`() {
        assertEquals("-3", CalculatorEngine.evaluate("-5+2"))
    }

    @Test
    fun `handles decimal division`() {
        assertEquals("2.5", CalculatorEngine.evaluate("5/2"))
    }

    @Test
    fun `throws for division by zero`() {
        assertThrows(ArithmeticException::class.java) {
            CalculatorEngine.evaluate("1/0")
        }
    }
}
