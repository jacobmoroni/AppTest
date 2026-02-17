package com.apptest.calculator

import java.math.BigDecimal
import java.math.MathContext

object CalculatorEngine {
    private val mathContext = MathContext.DECIMAL64

    fun evaluate(expression: String): String {
        val normalized = expression
            .replace('×', '*')
            .replace('÷', '/')
            .trim()

        require(normalized.isNotEmpty()) { "Expression cannot be empty." }

        val result = Parser(normalized).parse()
        return formatResult(result)
    }

    private fun formatResult(value: BigDecimal): String {
        if (value.compareTo(BigDecimal.ZERO) == 0) {
            return "0"
        }

        return value.stripTrailingZeros().toPlainString()
    }

    private class Parser(private val input: String) {
        private var index = 0

        fun parse(): BigDecimal {
            val value = parseExpression()
            skipWhitespace()
            require(index == input.length) { "Unexpected token at position $index." }
            return value
        }

        private fun parseExpression(): BigDecimal {
            var value = parseTerm()

            while (true) {
                skipWhitespace()
                value = when {
                    match('+') -> value.add(parseTerm(), mathContext)
                    match('-') -> value.subtract(parseTerm(), mathContext)
                    else -> return value
                }
            }
        }

        private fun parseTerm(): BigDecimal {
            var value = parseFactor()

            while (true) {
                skipWhitespace()
                value = when {
                    match('*') -> value.multiply(parseFactor(), mathContext)
                    match('/') -> {
                        val divisor = parseFactor()
                        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
                            throw ArithmeticException("Division by zero.")
                        }
                        value.divide(divisor, mathContext)
                    }

                    else -> return value
                }
            }
        }

        private fun parseFactor(): BigDecimal {
            skipWhitespace()

            if (match('+')) {
                return parseFactor()
            }

            if (match('-')) {
                return parseFactor().negate(mathContext)
            }

            if (match('(')) {
                val value = parseExpression()
                skipWhitespace()
                require(match(')')) { "Missing closing parenthesis at position $index." }
                return value
            }

            return parseNumber()
        }

        private fun parseNumber(): BigDecimal {
            skipWhitespace()
            val start = index
            var hasDigits = false

            while (peek()?.isDigit() == true) {
                hasDigits = true
                index++
            }

            if (peek() == '.') {
                index++
                while (peek()?.isDigit() == true) {
                    hasDigits = true
                    index++
                }
            }

            require(hasDigits) { "Expected number at position $index." }
            return input.substring(start, index).toBigDecimal(mathContext)
        }

        private fun match(expected: Char): Boolean {
            if (peek() == expected) {
                index++
                return true
            }
            return false
        }

        private fun peek(): Char? = input.getOrNull(index)

        private fun skipWhitespace() {
            while (peek()?.isWhitespace() == true) {
                index++
            }
        }
    }
}
