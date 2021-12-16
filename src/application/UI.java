package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.*;
import java.util.stream.Collectors;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner input) {
        try {
            String s = input.nextLine().toLowerCase();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Erro ao ler a Posição. Valores válidos vão de a1 até h8.");
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Turno: " + chessMatch.getTurn());
        if (!chessMatch.getCheckMate()) {
            if (chessMatch.getCurrentPlayer() == Color.BRANCA) {
                System.out.println("Aguardando o jogador: " + ANSI_WHITE + chessMatch.getCurrentPlayer() + ANSI_RESET);
            } else {
                System.out.println("Aguardando o jogador: " + ANSI_PURPLE + chessMatch.getCurrentPlayer() + ANSI_RESET);
            } if (chessMatch.getCheck()) {
                System.out.println(ANSI_YELLOW + "CHEQUE!" + ANSI_RESET);
            }
        } else {
            System.out.println(ANSI_RED + "CHEQUEMATE!" + ANSI_RESET);
            if (chessMatch.getCurrentPlayer() == Color.BRANCA) {
                System.out.println("Winner: " + ANSI_WHITE + chessMatch.getCurrentPlayer() + ANSI_RESET);
            } else {
                System.out.println("Winner: " + ANSI_PURPLE + chessMatch.getCurrentPlayer() + ANSI_RESET);
            }
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        System.out.println(ANSI_CYAN_BACKGROUND + "  ««XadrezDoJean»» " + ANSI_RESET);
        for (int i = 0; i< pieces.length ; i++) {
            System.out.print(ANSI_CYAN_BACKGROUND + (8-i) + ANSI_RESET + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);
            }
            System.out.print(ANSI_CYAN_BACKGROUND + " " + ANSI_RESET);
            System.out.println();
        }
        System.out.println(ANSI_CYAN_BACKGROUND + "  a b c d e f g h  " + ANSI_RESET);
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        System.out.println(ANSI_CYAN_BACKGROUND + "  ««XadrezDoJean»» " + ANSI_RESET);
        for (int i = 0; i< pieces.length ; i++) {
            System.out.print(ANSI_CYAN_BACKGROUND + (8-i) + ANSI_RESET + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            System.out.print(ANSI_CYAN_BACKGROUND + " " + ANSI_RESET);
            System.out.println();
        }
        System.out.println(ANSI_CYAN_BACKGROUND + "  a b c d e f g h  " + ANSI_RESET);
    }

    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_GREEN_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (piece.getColor() == Color.BRANCA) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_PURPLE + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.BRANCA).collect(Collectors.toList());
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.PRETA).collect(Collectors.toList());
        System.out.println("Peças capturadas:");
        System.out.print("Brancas: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(ANSI_RESET);
        System.out.print("Pretas: ");
        System.out.print(ANSI_PURPLE);
        System.out.println(Arrays.toString(black.toArray()));
        System.out.print(ANSI_RESET);

    }
}
