package ru.geekbrains.viewer;

import lombok.Setter;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.Tile;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Math.sqrt;

@Setter
public class SwingBattlefieldPresenter implements BattlefieldPresenter {
    private Battlefield battlefield;
    JFrame jFrame;

    //constants and global variables
    Color COLOURBACK = Color.GREEN;
    Color COLOURCELL = Color.ORANGE;
    Color COLOURGRID = Color.BLACK;
    Color COLOURONE = new Color(255, 255, 255, 200);
    Color COLOURONETXT = Color.BLUE;
    Color COLOURTWO = new Color(0, 0, 0, 200);
    Color COLOURTWOTXT = new Color(255, 0, 255);
    final int HEX_SIZE = 60;    //hex size in pixels
    final int BORDERS = 15;
    final int SCR_HEIGHT;
    final int SCR_WIDTH;

    private String WINDOW_TITLE = "Battlefield Presenter";

    public SwingBattlefieldPresenter(Battlefield battlefield) {
        this.battlefield = battlefield;
        SCR_HEIGHT = 2*( HEX_SIZE * battlefield.getVerticalSize() ) + BORDERS * 2;
        SCR_WIDTH = 2*( HEX_SIZE * battlefield.getHorizontalSize() ) + BORDERS * 2;
        {
            jFrame = new JFrame(WINDOW_TITLE);
            jFrame.setSize(SCR_WIDTH, SCR_HEIGHT);
            //jFrame.setSize((int) (SCRSIZE / 1.23), SCRSIZE);
            jFrame.setResizable(false);
            jFrame.setLocationRelativeTo(null);
            jFrame.setVisible(true);
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        {
            DrawingPanel drawingPanel = new DrawingPanel();
            Container content = jFrame.getContentPane();
            content.add(drawingPanel);
        }
    }

    @Override
    public void draw() {

    }

    /**
     * Get Hex coordinates on screen
     * @param hex hex coordinates to be converted to pixels
     * @return java Point
     */
    public Point pointyHexToPixel(Hex hex) {
        int x = (int) ( HEX_SIZE * (sqrt(3) * hex.getQ() +  sqrt(3)/2 * hex.getR()) );
        int y = (int) ( HEX_SIZE * (3./2 * hex.getR()) );
        x += SCR_WIDTH/2;
        y += SCR_HEIGHT/2;
        return new Point(x, y);
    }


    class DrawingPanel extends JPanel {

        public DrawingPanel() {
            setBackground(COLOURBACK);
            addMouseListener(myMouseAdapter);
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            super.paintComponent(g2);

            //draw grid
            List<List<Tile>> hexes = battlefield.getTiles().values().stream().toList();
            System.out.printf("Battlefield contains %s hexes to draw\n", hexes.size());
            for (List<Tile> tiles : hexes) {
                Point hexCenter = pointyHexToPixel(tiles.get(0).getHex());
                Hexmech.drawHex(hexCenter, HEX_SIZE, g2);
                //fill hexes if needed Hexmech.fillHex(i, j, board[i][j], g2);
            }
        }

        private MouseAdapter myMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                Hexmech.drawHex(e.getPoint(), 10, (Graphics2D) DrawingPanel.super.getGraphics());
                repaint();
            }
        };
    } // end of DrawingPanel class
}
