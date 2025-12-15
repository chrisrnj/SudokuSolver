import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Paste the sudoku game (each line from left to right and numbers separated by comma, empty cells use -1):");
        String[] input = scanner.nextLine().split(",");
        List<Integer> numbers = Arrays.stream(input).map(Integer::parseInt).collect(Collectors.toList());

        Sudoku sudoku = new Sudoku(numbers);
        sudoku.print();
        System.out.println("Solving sudoku...");
        sudoku.solve();
        sudoku.print();
    }

    static void printAndTest(int index) {
        Sudoku sudoku = new Sudoku(Arrays.asList(1,2,3,4,5,6,7,8,9,4,5,6,7,8,9,1,2,3,7,8,9,1,2,3,4,5,6,3,1,2,6,4,5,9,7,8,6,4,5,9,7,8,3,1,2,9,7,8,-1,1,2,6,4,5,2,3,1,5,6,4,8,9,7,5,6,4,8,9,7,2,3,1,8,9,7,2,3,1,5,6,4));
        sudoku.print();
        int row;
        int column;
        System.out.println("cell at index " + index + " = " + sudoku.numbers().get(index));
        System.out.println("row is " + (row = sudoku.getRowIndex(index)));
        System.out.println("column is " + (column = sudoku.getColumnIndex(index)));
        System.out.println("elements at row " + row + " = " + sudoku.getRow(row));
        System.out.println("elements at column " + column + " = " + sudoku.getColumn(column));
        System.out.println("possibilities for cell " + index + ": " + sudoku.getPossibilitiesForCell(index));
        System.out.println("removing 2 elements from row " + row + " and two elements from column " + column);
        sudoku.numbers().set(sudoku.getIndex(row, 0), -1);
        sudoku.numbers().set(sudoku.getIndex(0, column), -1);
        sudoku.numbers().set(sudoku.getIndex(row, 8), -1);
        sudoku.numbers().set(sudoku.getIndex(8, column), -1);
        sudoku.numbers().set(sudoku.getIndex(3, 0), -1);
        //sudoku.numbers().set(sudoku.getIndex(3, 5), -1);
        sudoku.numbers().set(sudoku.getIndex(6, 3), -1);



        sudoku.print();
        System.out.println("possibilities for cell " + index + ": " + sudoku.getPossibilitiesForCell(index));

        System.out.println("trying to solve...");
        sudoku.solve();
        sudoku.print();
    }

    static void testGrid(Sudoku sudoku, int grid, int startRow, int startColumn) {
        System.out.println("Grid " + grid + " - " + sudoku.getGridIndex(sudoku.getIndex(startRow, startColumn)));
        System.out.println("Grid " + grid + " - " + sudoku.getGridIndex(sudoku.getIndex(startRow, startColumn + 2)));
        System.out.println("Grid " + grid + " - " + sudoku.getGridIndex(sudoku.getIndex(startRow + 2, startColumn)));
        System.out.println("Grid " + grid + " - " + sudoku.getGridIndex(sudoku.getIndex(startRow + 2, startColumn + 2)));
    }
}
