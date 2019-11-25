package parser;

import java.util.*;

/**
 * Парсит варианты скобок используя таблицу Юнга.
 * int[x][y]
 * где в строках int[0][...] - индексы открывающих скобок
 * а в int[1][...] - индексы закрывающих скобок.
 */
public class JungsTableParserBrace extends ParserBrace {

    /**
     * Основной метод для парсинга.
     * Получает список всех комбинаций- проверяет их-
     * возвращает множество списков только верных комбинаций.
     * @param input
     * @return
     */
    @Override
    protected Set<List<Integer>> combinationsGrabber(String input) {
        Set<List<Integer>> result = new HashSet<>();
        this.walkOverString(input);
        int sizeLimit = this.forSearch.keySet().size();
        List<int[][]> combos = this.runIntoDeep(0, new int[2][sizeLimit]);
        for (int[][] a : combos) {
            if (this.checkMatrix(a)) {
                result.add(this.arrayIntoList(a));
            }
        }
        return result;
    }

    /**
     * Рекурсивный метод, возвращающий список двумерных массивов,
     * каждый из которых является возможной комбинацией (верной или нет).
     *
     * @param currentIndex
     * @param array
     * @return
     */
    private List<int[][]> runIntoDeep(int currentIndex, int[][] array) {
        List<int[][]> result = new ArrayList<>();
        int newCurrentIndex = currentIndex + 1;
        if (currentIndex < this.forSearch.keySet().size()) {
            for (Node n : this.forSearch.get(currentIndex)) {
                int[][] bufferArray = this.copycat(array);
                bufferArray[0][currentIndex] = n.getOpen();
                bufferArray[1][currentIndex] = n.getClose();
                result.addAll(this.runIntoDeep(newCurrentIndex, bufferArray));
            }
        } else {
            result.add(this.copycat(array));
        }
        return result;
    }

    /**
     * Метод проверку двумерного массива, который здесь и является таблицей Юнга.
     * Так, int[0][x] должен быть меньше int[1][x]
     * Так же проверяется отсутсвие дублирующих значений
     * (т.к. в каждой строке может быть только одно уникальное значение-
     * проверка проводится с помощью двух Set`ов.)
     *
     * @param inputArray
     * @return
     */
    private boolean checkMatrix(int[][] inputArray) {
        boolean result = true;
        Set<Integer> upperSet = new HashSet<>();
        Set<Integer> lowerSet = new HashSet<>();
        for (int i = 0; i < inputArray[0].length; i++) {
            if (inputArray[0][i] >= inputArray[1][i]) {
                result = false;
                break;
            }
            upperSet.add(inputArray[0][i]);
            lowerSet.add(inputArray[1][i]);
        }
        if (result) {
            if (upperSet.size() != inputArray[0].length
                    || lowerSet.size() != inputArray[1].length) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Метод преобразует двумерный массив в Лист.
     *
     * @param array
     * @return
     */
    private List<Integer> arrayIntoList(int[][] array) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < array[0].length; i++) {
            result.add(array[0][i]);
            result.add(array[1][i]);
        }
        return result;
    }

    /**
     * Метод для копирования двумерного массива.
     *
     * @param array
     * @return
     */
    private int[][] copycat(int[][] array) {
        int firstSize = array.length;
        int secondSize = array[0].length;
        int[][] result = new int[firstSize][secondSize];
        for (int i = 0; i < firstSize; i++) {
            for (int j = 0; j < secondSize; j++) {
                result[i][j] = array[i][j];
            }
        }
        return result;
    }

}