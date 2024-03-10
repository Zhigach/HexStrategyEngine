package ru.geekbrains.viewer;

import ru.geekbrains.hexcore.Battlefield;

import javax.swing.*;

public class BattlefieldDrawer implements BattlefieldPresenter {

    Battlefield battlefield;
    JFrame jFrame;

    private static String WINDOW_TITLE = "Battlefield Presenter";
    private static int WINDOW_WIDTH = 700;
    private static int WINDOW_HEIGHT = 450;



    public BattlefieldDrawer(Battlefield battlefield) {
        jFrame = new JFrame(WINDOW_TITLE);
        jFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void createView() {

    }

    @Override
    public void draw() {

    }
}
