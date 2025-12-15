import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public record Sudoku(List<Integer> numbers) {
    public Sudoku {
        for (int number : numbers) {
            if (number < -1 || number > 9)
                throw new IndexOutOfBoundsException("the number provided has an invalid range for sudoku");
        }
    }

    void print() {
        int printed = 0;
        int line = 0;
        int separators = 0;

        for (int number : numbers) {
            if (printed == 9) {
                if (++line == 3) {
                    System.out.print("---------------------------------\n");
                    line = 0;
                }
                printed = 0;
            }
            System.out.print(" " + (number == -1 ? " " : number) + " ");
            printed++;
            if (printed % 3 == 0) {
                if (++separators == 3) {
                    separators = 0;
                    System.out.print('\n');
                } else System.out.print(" | ");
            }
        }
    }

    int getIndex(int row, int column) {
        return row * 9 + column;
    }

    int getRowIndex(int index) {
        return index / 9;
    }

    int getColumnIndex(int index) {
        return index % 9;
    }

    int getGridIndex(int index) {
        int row = getRowIndex(index);
        int col = getColumnIndex(index);

        return (row / 3) * 3 + (col / 3);
    }

    List<Integer> getRow(int rowIndex) {
        List<Integer> result = new ArrayList<>(9);
        int start = rowIndex * 9;

        for (int i = 0; i < 9; i++) {
            result.add(numbers.get(start + i));
        }
        return result;
    }

    List<Integer> getColumn(int columnIndex) {
        List<Integer> result = new ArrayList<>(9);

        for (int row = 0; row < 9; row++) {
            result.add(numbers.get(row * 9 + columnIndex));
        }
        return result;
    }

     List<Integer> getGrid(int gridIndex) {
        List<Integer> grid = new ArrayList<>(9);

        int startRow = (gridIndex / 3) * 3;
        int startCol = (gridIndex % 3) * 3;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int index = (startRow + r) * 9 + (startCol + c);
                grid.add(numbers.get(index));
            }
        }
        return grid;
    }


    List<Integer> getPossibilitiesForCell(int index) {
        int cell = numbers.get(index);
        if (cell != -1) return Collections.emptyList();
        List<Integer> row = getRow(getRowIndex(index));
        List<Integer> column = getColumn(getColumnIndex(index));
        List<Integer> grid = getGrid(getGridIndex(index));
        //System.out.println("grid of " + index + " has index " + getGridIndex(index) + " and its elements are: " + getGrid(getGridIndex(index)));

        List<Integer> possibilities = new ArrayList<>(9);
        IntStream.range(1,10).filter(i -> !row.contains(i) && !column.contains(i) && !grid.contains(i)).forEach(possibilities::add);
        return possibilities;
    }

    boolean solve() {
        boolean changed = false;
        while (numbers.contains(-1)) {
            int minimalSize = Integer.MAX_VALUE;
            int minimalIndex = 0;
            List<Integer> minimalPossibilities = null;

            for (int i = 0; i < numbers.size(); i++) {
                if (numbers.get(i) != -1) continue;
                List<Integer> possibilities = getPossibilitiesForCell(i);
                if (possibilities.isEmpty()) {
                    System.out.println("Cell " + i + " has no possibilities found.");
                    return false;
                }
                int possibilitiesSize = possibilities.size();

                if (!possibilities.isEmpty() && minimalSize > possibilitiesSize) {
                    minimalSize = possibilitiesSize;
                    minimalIndex = i;
                    minimalPossibilities = possibilities;
                }
                if (possibilitiesSize == 1) {
                    System.out.println("Set "+ i + " to " + possibilities.getFirst());
                    numbers.set(i, possibilities.getFirst());
                    changed = true;
                }
            }

            if (minimalSize > 1 && minimalSize != Integer.MAX_VALUE) {
                System.out.println("Multiples possibilities found for cell " + minimalIndex + ", trying a new branch.");
                for (int possibility : minimalPossibilities) {
                    List<Integer> numbersClone = new ArrayList<>(numbers);
                    numbersClone.set(minimalIndex, possibility);
                    if (new Sudoku(numbersClone).solve()) {
                        numbers.clear();
                        numbers.addAll(numbersClone);
                        return true;
                    }
                }
            }

            if (!changed) {
                System.out.println("Could not solve this Sudoku.");
                return false;
            }
            changed = false;
        }
        return true;
    }
}
