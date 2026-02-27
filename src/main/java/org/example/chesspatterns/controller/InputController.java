//package org.example.chesspatterns.controller;
//
//import org.example.chesspatterns.core.GameManager;
//import org.example.chesspatterns.model.board.Board;
//import org.example.chesspatterns.pattern.command.Move;
//import org.example.chesspatterns.model.pieces.Piece;
//import org.example.chesspatterns.view.BoardPanel;
//
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class InputController extends MouseAdapter {
//
//    private final Board boardModel;
//    private final BoardPanel boardView;
//
//    public InputController(Board boardModel, BoardPanel boardView) {
//        this.boardModel = boardModel;
//        this.boardView = boardView;
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        int col = e.getX() / boardView.tileSize;
//        int row = e.getY() / boardView.tileSize;
//
//        Piece piece = boardModel.getPieceAtLocation(col, row);
//        if (piece != null) {
//           
//            boolean isAllowed = GameManager.getInstance().getCurrentState().canSelectPiece(piece);
//            if (isAllowed) {
//                boardModel.setSelectedPiece(piece);
//                updateDragState(e);
//            }
//        }
//    }
//
//    @Override
//    public void mouseDragged(MouseEvent e) {
//        // Dragging effect
//        if (boardModel.getSelectedPiece() != null) {
//            updateDragState(e);
//        }
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//        int col = e.getX() / boardView.tileSize;
//        int row = e.getY() / boardView.tileSize;
//
//        Piece selectedPiece = boardModel.getSelectedPiece();
//
//        if (selectedPiece != null) {
//            Move move = new Move(boardModel, selectedPiece, col, row);
//
//            if (boardModel.isValidMove(move)) {
//                boardModel.makeMove(move);
//            }
//
//            boardModel.setSelectedPiece(null);
//            boardView.clearDragPosition();
//        }
//    }
//
//
//    private void updateDragState(MouseEvent e) {
//        // Calculate top-left corner so mouse is in the center of the piece
//        int x = e.getX() - (boardView.tileSize / 2);
//        int y = e.getY() - (boardView.tileSize / 2);
//
//        // Update the View
//        boardView.setDragPosition(x, y);
//    }
//}
//
//
