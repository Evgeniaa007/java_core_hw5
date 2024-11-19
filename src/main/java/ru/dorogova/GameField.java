package ru.dorogova;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Класс для представления и управления игровым полем.
 */
public class GameField {
    private int[] boardState;

    public static void main(String[] args) {
        int[] initialState = {2, 1, 0, 2, 0, 2, 0, 1, 3};
        GameField gameBoard = new GameField(initialState);

        try {
            gameBoard.writeToFile("gameField.bin");
            gameBoard.readFromFile("gameField.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameBoard.printBoard();
    }

    public GameField(int[] boardState) {
        this.boardState = boardState;
    }

    /**
     * Делаем из игрового поля одно число, которое не превышает 3 байта
     */
    public int encodeBoard() {
        int size = 0;
        for (int i = 0; i < boardState.length; i++) {
            size = size << 2;
            size = size | boardState[i];
            //System.out.println(Integer.toBinaryString(size) + " " + boardState[i] + " " + Integer.toBinaryString(boardState[i]));
        }
        return size;
    }

    /**
     * Обратно в человеческие цифры
     *
     */
    public void decodeBoard(int size) {
        for (int i = boardState.length - 1; i >= 0; i--) {
            int cellState = size & boardState[i];
            size = size >> 2;
            boardState[i] = cellState;
            //System.out.println(Integer.toBinaryString(size) + " " + boardState[i] + " " + Integer.toBinaryString(boardState[i]));
        }
    }

    /**
     * Запись в файл
     */
    public void writeToFile(String fileName) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))) {
            dos.writeInt(encodeBoard());
        }
    }

    /**
     * Чтение файла
     */
    public void readFromFile(String fileName) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
            int readBoard = dis.readInt();
            decodeBoard(readBoard);
        }
    }

    /**
     * Выводит состояние игрового поля на консоль.
     */
    public void printBoard() {
        for (int i = 0; i < boardState.length; i++) {
            char cellSymbol;
            switch (boardState[i]) {
                case 0:
                    cellSymbol = '.';
                    break;
                case 1:
                    cellSymbol = 'x';
                    break;
                case 2:
                    cellSymbol = 'o';
                    break;
                default:
                    cellSymbol = '?'; // неизвестное состояние ячейки
                    break;
            }
            // Печатает в строчке по три символа
            System.out.print(cellSymbol + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }
}