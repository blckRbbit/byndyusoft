package parser;

import exception.BSEmptyExpressionException;
import exception.BSUnexpectedCharacterException;
import exception.BSUnexpectedTokenException;
import support.Lexeme;
import support.LexemeType;

import java.util.ArrayList;
import java.util.List;

public class BSCalculatorParser {

    public static int calculate(String expression) {
        List<Lexeme> lexemes = analyzeLexeme(expression);
        LexemeBuffer buffer = new LexemeBuffer(lexemes);
        return getExpression(buffer);
    }

    private static class LexemeBuffer {

        private int position;
        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme next() {
            return lexemes.get(position++);
        }

        public void back() {
            position--;
        }

        public int getPosition() {
            return position;
        }
    }

    private static List<Lexeme> analyzeLexeme(String expression) {
        List<Lexeme> lexemes = new ArrayList<>();
        int position = 0;
        while (position < expression.length()) {
            char c = expression.charAt(position);
            switch (c) {
                case ' ':
                    position++;
                    continue;
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    position ++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    position ++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.PLUS, c));
                    position ++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.MINUS, c));
                    position ++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.MULTIPLY, c));
                    position ++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.DIVIDE, c));
                    position ++;
                    continue;
                default:
                    if (isDigit(c)) {
                        StringBuilder builder = new StringBuilder();
                        do {
                            builder.append(c);
                            position ++;
                            if (position >= expression.length()) {
                                break;
                            }
                            c = expression.charAt(position);
                        } while (isDigit(c));
                        lexemes.add(new Lexeme(LexemeType.NUMBER, builder.toString()));
                    } else {
                        throw new BSUnexpectedCharacterException("В выражении должны присутствовать только цифры и знаки ()+-*/");
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    private static boolean isDigit(Character c) {
        return c <= '9' && c >= '0';
    }

    private static int handlePlusMinus(LexemeBuffer lexemes) {
        int value = handleMultiplyDivide(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.getType()) {
                case PLUS:
                    value += handleMultiplyDivide(lexemes);
                    break;
                case MINUS:
                    value -= handleMultiplyDivide(lexemes);
                    break;
                case EOF:
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    private static int handleMultiplyDivide(LexemeBuffer lexemes) {
        int value = getFactor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.getType()) {
                case MULTIPLY:
                    value *= getFactor(lexemes);
                    break;
                case DIVIDE:
                    value /= getFactor(lexemes);
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    private static int getFactor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.getType()) {
            case NUMBER:
                return Integer.parseInt(lexeme.getValue());
            case LEFT_BRACKET:
                int value = getExpression(lexemes);
                lexeme = lexemes.next();
                if (lexeme.getType() != LexemeType.RIGHT_BRACKET) {
                    throw new BSUnexpectedTokenException(
                            String.format("Ошибка в арифметическом выражении. Позиция: %s", lexemes.getPosition())
                            );
                }
                return value;
            default:
                throw new BSUnexpectedTokenException(
                        String.format("Ошибка в арифметическом выражении. Позиция: %s", lexemes.getPosition())
                );
        }
    }

    private static int getExpression(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.getType() == LexemeType.EOF) {
            throw new BSEmptyExpressionException("Выражение не может быть пустой строкой!");
        } else {
            lexemes.back();
            return handlePlusMinus(lexemes);
        }

    }

}
