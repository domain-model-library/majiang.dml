package dml.majiang.simulator.base.controller;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.simulator.base.entity.PaiExchangeState;
import dml.majiang.simulator.base.entity.PlayConfig;
import dml.majiang.simulator.base.entity.PlayStateEnum;
import dml.majiang.simulator.base.entity.SecondPaiSelected;
import dml.majiang.simulator.base.service.PaiExchangeService;
import dml.majiang.simulator.base.service.PlayService;
import dml.majiang.simulator.base.ui.CommandPanel;
import dml.majiang.simulator.base.ui.MainFrame;
import dml.majiang.simulator.base.ui.PanPanel;

import java.util.List;

public class Controller {
    private static MainFrame mainFrame;
    private static PlayService playService;
    private static PaiExchangeService paiExchangeService;
    private static PanPanel panPanel;
    private static CommandPanel commandPanel;

    public static void startup(PlayService playService, long panId) {
        playService.setPanId(panId);
        Controller.playService = playService;
        Controller.paiExchangeService = new PaiExchangeService(playService.getPanRepository());
        // 启动UI
        javax.swing.SwingUtilities.invokeLater(() -> {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }

    public static void startPan() {
        Pan pan = playService.startPan();
        PlayStateEnum playState = playService.getPlayState();
        List<String[]> panSpecialRulesStateView = playService.getPanSpecialRulesStateView();
        panPanel.showPan(pan, playState, panSpecialRulesStateView);
        PlayConfig playConfig = playService.getPlayConfig();
        commandPanel.updateModifyShoupaiDoneButton(playConfig, playState);
    }

    public static void setMainFrame(MainFrame mainFrame) {
        Controller.mainFrame = mainFrame;
    }

    public static void setPanPanel(PanPanel panPanel) {
        Controller.panPanel = panPanel;
    }

    public static void setCommandPanel(CommandPanel commandPanel) {
        Controller.commandPanel = commandPanel;
    }

    public static void mouseEnteredForPaiExchange(int paiId) {
        PaiExchangeState paiExchangeState = paiExchangeService.mouseEnteredForPaiExchange(paiId);
        panPanel.updateForPaiExchange(paiExchangeState);
    }

    public static void mousePressedForPaiExchange(int paiId) {
        PaiExchangeState paiExchangeState = paiExchangeService.mousePressedForPaiExchange(paiId);
        panPanel.updateForPaiExchange(paiExchangeState);
    }

    public static void mouseReleasedForPaiExchange() {
        PaiExchangeState paiExchangeState = paiExchangeService.mouseReleasedForPaiExchange();
        if (paiExchangeState instanceof SecondPaiSelected) {
            paiExchangeService.exchangePai(((SecondPaiSelected) paiExchangeState).getFirstPaiId(),
                    ((SecondPaiSelected) paiExchangeState).getSecondPaiId());
            Pan pan = playService.getPan();
            PlayStateEnum playState = playService.getPlayState();
            List<String[]> panSpecialRulesStateView = playService.getPanSpecialRulesStateView();
            panPanel.showPan(pan, playState, panSpecialRulesStateView);
            PlayConfig playConfig = playService.getPlayConfig();
            commandPanel.updateModifyShoupaiDoneButton(playConfig, playState);
        } else {
            panPanel.updateForPaiExchange(paiExchangeState);
        }
    }

    public static void mouseExitedForPaiExchange(int paiId) {
        PaiExchangeState paiExchangeState = paiExchangeService.mouseExitedForPaiExchange(paiId);
        panPanel.updateForPaiExchange(paiExchangeState);
    }

    public static void setCanModifyShoupai(boolean canModifyShoupai) {
        playService.setCanModifyShoupai(canModifyShoupai);
        PlayConfig playConfig = playService.getPlayConfig();
        PlayStateEnum playState = playService.getPlayState();
        commandPanel.updateModifyShoupaiDoneButton(playConfig, playState);
    }

    public static void setCanSetMopai(boolean canSetMopai) {
        playService.setCanSetMopai(canSetMopai);
    }

    public static void specificMoPai(int paiId) {
        playService.specificMoPai(paiId);
        Pan pan = playService.getPan();
        PlayStateEnum playState = playService.getPlayState();
        List<String[]> panSpecialRulesStateView = playService.getPanSpecialRulesStateView();
        panPanel.showPan(pan, playState, panSpecialRulesStateView);
        PlayConfig playConfig = playService.getPlayConfig();
        commandPanel.updateModifyShoupaiDoneButton(playConfig, playState);
    }

    public static void daPai(int paiId) {
        PanPlayerAction daAction = playService.findDaCandidateAction(paiId);
        playService.action(daAction.getActionPlayerId(), daAction.getId());
        Pan pan = playService.getPan();
        PlayStateEnum playState = playService.getPlayState();
        List<String[]> panSpecialRulesStateView = playService.getPanSpecialRulesStateView();
        panPanel.showPan(pan, playState, panSpecialRulesStateView);
        PlayConfig playConfig = playService.getPlayConfig();
        commandPanel.updateModifyShoupaiDoneButton(playConfig, playState);
    }

    public static void modifyShoupaiDone() {
        playService.modifyShoupaiDone();
        Pan pan = playService.getPan();
        PlayStateEnum playState = playService.getPlayState();
        List<String[]> panSpecialRulesStateView = playService.getPanSpecialRulesStateView();
        panPanel.showPan(pan, playState, panSpecialRulesStateView);
        PlayConfig playConfig = playService.getPlayConfig();
        commandPanel.updateModifyShoupaiDoneButton(playConfig, playState);
    }

    public static void action(String playerId, int actionId) {
        playService.action(playerId, actionId);
        Pan pan = playService.getPan();
        PlayStateEnum playState = playService.getPlayState();
        List<String[]> panSpecialRulesStateView = playService.getPanSpecialRulesStateView();
        panPanel.showPan(pan, playState, panSpecialRulesStateView);
        PlayConfig playConfig = playService.getPlayConfig();
        commandPanel.updateModifyShoupaiDoneButton(playConfig, playState);
        //TODO 胡的展示和pan结束
    }
}
