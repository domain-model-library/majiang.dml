package dml.majiang.simulator.base.ui;

import dml.majiang.core.entity.Pai;
import dml.majiang.simulator.base.controller.Controller;
import dml.majiang.simulator.base.entity.MahjongTileSceneEnum;
import dml.majiang.simulator.base.entity.PlayStateEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MahjongTile extends JLabel {
    public static String[] paiChinese = {
            "一万", "二万", "三万", "四万", "五万", "六万", "七万", "八万", "九万",
            "一筒", "二筒", "三筒", "四筒", "五筒", "六筒", "七筒", "八筒", "九筒",
            "一条", "二条", "三条", "四条", "五条", "六条", "七条", "八条", "九条",
            "东风", "南风", "西风", "北风", "红中", "发财", "白板",
            "春", "夏", "秋", "冬", "梅", "兰", "竹", "菊"
    };

    private int paiId;

    // 完整构造器
    public MahjongTile(Pai pai, PlayStateEnum playState, MahjongTileSceneEnum scene, boolean canDaPai) {
        super(paiChinese[pai.getPaiType().ordinal()]);
        this.paiId = pai.getId();

        // 基础样式初始化
        if (!scene.equals(MahjongTileSceneEnum.chuPaiZu)) {
            setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
            setForeground(Color.WHITE);
            setOpaque(true);
            setBackground(Color.BLACK);
            setHorizontalAlignment(SwingConstants.CENTER);
            setPreferredSize(new Dimension(38, 20));
        } else {
            setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
            setForeground(Color.WHITE);
            setOpaque(true);
            setBackground(Color.LIGHT_GRAY);
            setHorizontalAlignment(SwingConstants.CENTER);
            setPreferredSize(new Dimension(35, 18));
        }

        // 添加鼠标监听
        if (scene.equals(MahjongTileSceneEnum.chuPaiZu)) {
            return;
        }
        if (canDaPai) {
            addMouseListener(new DaPaiListener());
            return;
        }
        if (playState.equals(PlayStateEnum.shoupaiEditing)) {
            addMouseListener(new PaiExchangeListener());
        } else if (playState.equals(PlayStateEnum.waitingForMoAction)) {
            if (scene.equals(MahjongTileSceneEnum.availablePai)) {
                addMouseListener(new MoPaiListener());
            }
        }
    }

    public void updateNotSelected() {
        setBackground(Color.BLACK);
        setCursor(Cursor.getDefaultCursor());
    }

    public int getPaiId() {
        return paiId;
    }

    public void updateSelect() {
        setBackground(Color.BLACK);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void updateSelected() {
        setBackground(Color.LIGHT_GRAY);
        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    public void updateSelectSecond() {
        setBackground(Color.LIGHT_GRAY);
        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    public void updateSelectMoPai() {
        setBackground(Color.LIGHT_GRAY);
    }

    public void updateSelectDaPai() {
        setBackground(Color.LIGHT_GRAY);
    }

    // 换牌专用鼠标监听器
    private class PaiExchangeListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            Controller.mouseEnteredForPaiExchange(paiId);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Controller.mousePressedForPaiExchange(paiId);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Controller.mouseReleasedForPaiExchange();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Controller.mouseExitedForPaiExchange(paiId);
        }
    }

    // 摸牌专用鼠标监听器
    private class MoPaiListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            updateSelectMoPai();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            updateNotSelected();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // 判断是否为双击（点击次数为2）
            if (e.getClickCount() == 2) {
                //指定摸牌
                Controller.specificMoPai(paiId);
            }
        }
    }

    // 打牌专用鼠标监听器
    private class DaPaiListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            updateSelectDaPai();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            updateNotSelected();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // 判断是否为双击（点击次数为2）
            if (e.getClickCount() == 2) {
                //打牌
                Controller.daPai(paiId);
            }
        }
    }
}