package parser;

import java.util.*;

public class ParserBrace {

    /**
     * Содержит все не лишние открывающие рамки и
     * соответствующие им закрывающие.
     */
    protected Map<Integer, List<Node>> forSearch;

    /**
     * Содержит позиции всех скобок в строке.
     */
    protected List<Integer> allBracesPositions;

    /**
     * @param input
     * @return
     */
    public Set<String> validate(String input) {
        this.walkOverString(input);
        Set<String> result = new HashSet<>();
        if (forSearch.size() == 0) {
            String buffer;
            buffer = input.replace("{", "");
            buffer = buffer.replace("}", "");
            result.add(buffer);
            return result;
        }
        char[] bufferArray;
        String bufferResult;
        Set<List<Integer>> combos = this.combinationsGrabber(input);
        for (List<Integer> l : combos) {
            bufferArray = input.toCharArray();
            for (int i = 0; i < bufferArray.length; i++) {
                if (!l.contains(i) && allBracesPositions.contains(i)) {
                    bufferArray[i] = ' ';
                }
            }
            bufferResult = String.valueOf(bufferArray);
            bufferResult = bufferResult.replace(" ", "");
            result.add(bufferResult);
        }
        return result;
    }

    /**
     * Этот метод заполняет все поля необходимой информацией.
     *
     * @param input
     * @return
     */
    protected void walkOverString(String input) {
        int keyMark = 0;
        forSearch = new HashMap<>();
        allBracesPositions = new ArrayList<>();
        List<Node> bufferList;
        for (int i = 0; i < input.length(); i++) {
            char bufferChar = input.charAt(i);
            if (bufferChar == '{' || bufferChar == '}') {
                allBracesPositions.add(i);
            }
            if (bufferChar == '{') {
                bufferList = new ArrayList<>();
                for (int j = i; j < input.length(); j++) {
                    if (input.charAt(j) == '}') {
                        bufferList.add(new Node(i, j));
                    }
                }
                if (bufferList.size() > 0) {
                    forSearch.put(keyMark++, bufferList);
                }
            }
        }
    }

    /**
     * Запускаем рекурсивный метод.
     * В результате получим список только тех комбинаций,
     * в которых количество позиций равно количесиву
     * не лишних открывающих скобок умноженному на два.
     *
     * @param input
     * @return
     */
    protected Set<List<Integer>> combinationsGrabber(String input) {
        Set<List<Integer>> result = new HashSet<>();
        Set<List<Integer>> bufferResult = new HashSet<>();
        int checkSize = forSearch.size() * 2;
        for (Node n : forSearch.get(0)) {
            int open = n.getOpen();
            int close = n.getClose();
            bufferResult.addAll(this.runIntoDeep(forSearch, 0, open, close, input.length(),
                    new ArrayList<>(Arrays.asList(open, close))));
        }
        for (List<Integer> l : bufferResult) {
            if (l.size() == checkSize) {
                result.add(l);
            }
        }
        return result;
    }

    /**
     * Рекурсивный метод, позволяющий получить нам возможные комбинации
     * имеющихся открывающих и закрывающих скобок.
     *
     * @param map         - карта Node`ов.
     * @param key         - индекс списка нодов из карты, с которым работаем.
     * @param open        - позхиция текущей открывающей скобки.
     * @param close       - позхиция текущей закрывающей скобки.
     * @param rightLimit  - правый предел, за который нельзя выходить.
     * @param combination - список уже имеющихся позиций.
     * @return множество списков возможных комбинаций.
     */
    private Set<List<Integer>> runIntoDeep(Map<Integer, List<Node>> map, int key, int open,
                                           int close, int rightLimit, List<Integer> combination) {
        Set<List<Integer>> result = new HashSet<>();
        List<Integer> bufferCombinations;
        int openBuffer;
        int closeBuffer;
        int newKey = key + 1;
        if (newKey < map.keySet().size()) {
            for (Node n : map.get(newKey)) {
                openBuffer = n.getOpen();
                closeBuffer = n.getClose();
                if (open < openBuffer && close > closeBuffer) {
                    bufferCombinations = new ArrayList<>(combination);
                    bufferCombinations.add(openBuffer);
                    bufferCombinations.add(closeBuffer);
                    result.addAll(runIntoDeep(map, newKey, openBuffer, closeBuffer,
                            close, bufferCombinations));
                } else if (close < openBuffer && rightLimit > closeBuffer) {
                    bufferCombinations = new ArrayList<>(combination);
                    bufferCombinations.add(openBuffer);
                    bufferCombinations.add(closeBuffer);
                    result.addAll(runIntoDeep(map, newKey, openBuffer, closeBuffer,
                            rightLimit, bufferCombinations));
                } else if ((open < openBuffer && close > openBuffer) && close < closeBuffer) {
                    bufferCombinations = new ArrayList<>(combination);
                    result.addAll(runIntoDeep(map, newKey, open, close,
                            rightLimit, bufferCombinations));

                } else if (rightLimit < openBuffer) {
                    bufferCombinations = new ArrayList<>(combination);
                    bufferCombinations.add(openBuffer);
                    bufferCombinations.add(closeBuffer);
                    result.addAll(runIntoDeep(map, newKey, openBuffer, closeBuffer,
                            closeBuffer, bufferCombinations));
                }
            }
        } else {
            result.add(combination);
        }
        return result;
    }
}