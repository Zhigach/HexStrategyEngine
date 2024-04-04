package ru.geekbrains.viewer;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.model.Tile;
import ru.geekbrains.hexcore.utils.Hex;
import ru.geekbrains.viewer.interfaces.BattlefieldPresenter;
import ru.geekbrains.viewer.utils.GraphUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static java.lang.Math.sqrt;

@Setter
@Slf4j
public class SwingBattlefieldPresenter implements BattlefieldPresenter {
    private Battlefield battlefield;
    private JFrame jFrame;
    private DrawingPanel drawingPanel;

    //constants and global variables
    Color COLOURBACK = Color.GREEN;
    Color COLOURCELL = Color.ORANGE;
    Color COLOURGRID = Color.BLACK;
    Color COLOURONE = new Color(255, 255, 255, 200);
    Color COLOURONETXT = Color.BLUE;
    Color COLOURTWO = new Color(0, 0, 0, 200);
    Color COLOURTWOTXT = new Color(255, 0, 255);
    final int HEX_SIZE = 50;    //hex size in pixels
    final int BORDERS = 15;
    int SCR_HEIGHT;
    int SCR_WIDTH;

    private String WINDOW_TITLE = "Battlefield Presenter";

    public SwingBattlefieldPresenter(Battlefield battlefield) {
        this.battlefield = battlefield;
        battlefield.setBattlefieldPresenter(this);
    }

    public void setGUI() {
        SCR_HEIGHT = 2 * (HEX_SIZE * battlefield.getVerticalSize() - 1 + BORDERS * 2);
        SCR_WIDTH = 2 * (HEX_SIZE * battlefield.getHorizontalSize() + BORDERS * 2);
        {
            jFrame = new JFrame(WINDOW_TITLE);
            jFrame.setSize(SCR_WIDTH, SCR_HEIGHT);
            jFrame.setResizable(false);
            jFrame.setLocationRelativeTo(null);
            jFrame.setVisible(true);
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        {
            drawingPanel = new DrawingPanel();
            Container content = jFrame.getContentPane();
            content.add(drawingPanel);
        }
    }

    @Override
    public void draw() {
        drawingPanel.repaint();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void draw(Hex hex, Graphics2D g2) {

        Point centerPoint = pointyHexToPixel(hex);
        Polygon polygon = GraphUtils.getHexagon(centerPoint, HEX_SIZE);
        battlefield.getTerrainByCoordinate(hex).draw(g2, HEX_SIZE, centerPoint);
        for (Tile tile : battlefield.getUnitsByCoordinate(hex)) {
            tile.draw(g2, HEX_SIZE / 3, new Point(centerPoint.x - HEX_SIZE / 2, centerPoint.y + HEX_SIZE / 4));
        }
        g2.setColor(Color.BLACK);
        g2.drawPolygon(polygon);

        g2.drawString(hex.toString(), centerPoint.x - HEX_SIZE / 2, centerPoint.y - HEX_SIZE / 3);
    }

    /**
     * Get Hex coordinates on screen
     *
     * @param hex hex coordinates to be converted to pixels
     * @return java Point
     */
    public Point pointyHexToPixel(Hex hex) {
        int x = (int) (HEX_SIZE * (sqrt(3) * hex.getQ() + sqrt(3) / 2 * hex.getR()));
        int y = (int) (HEX_SIZE * (3. / 2 * hex.getR()));
        x += SCR_WIDTH / 2;
        y += SCR_HEIGHT / 2;
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
            Font f = new Font(Font.SERIF, Font.ITALIC, 15);
            g2.setFont(f);
            super.paintComponent(g2);

            //draw grid
            List<List<Tile>> hexes = battlefield.getTiles().values().stream().toList();
            log.info("Battlefield contains {} hexes to draw. Repainting...", hexes.size());
            for (Hex hex : battlefield.getTiles().keySet()) {
                //Point hexCenter = pointyHexToPixel(hex);
                //attlefield.draw(g2, hex, HEX_SIZE, new Point(hexCenter.x, hexCenter.y));
                draw(hex, g2);
            }
        }

        private MouseAdapter myMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                //Hexmech.drawHex(e.getPoint(), 10, (Graphics2D) DrawingPanel.super.getGraphics());
                repaint();
            }
        };
    } // end of DrawingPanel class
}
