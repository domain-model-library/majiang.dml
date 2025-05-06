package dml.majiang.simulator.base.service;

import dml.common.repository.TestCommonSingletonRepository;
import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.repository.PanRepository;
import dml.majiang.simulator.base.entity.*;
import dml.majiang.simulator.base.repository.PaiExchangeStateContainerRepository;

import java.util.List;

public class PaiExchangeService {
    private PaiExchangeStateContainerRepository paiExchangeStateContainerRepository;
    private PanRepository panRepository;

    public PaiExchangeService(PanRepository panRepository) {
        this.panRepository = panRepository;
        paiExchangeStateContainerRepository = TestCommonSingletonRepository.instance(PaiExchangeStateContainerRepository.class,
                new PaiExchangeStateContainer());
    }

    public PaiExchangeState mouseEnteredForPaiExchange(int paiId) {
        PaiExchangeStateContainer paiExchangeStateContainer = paiExchangeStateContainerRepository.take();
        paiExchangeStateContainer.mouseEnteredInPai(paiId);
        return paiExchangeStateContainer.getState();
    }

    public PaiExchangeState mousePressedForPaiExchange(int paiId) {
        PaiExchangeStateContainer paiExchangeStateContainer = paiExchangeStateContainerRepository.take();
        paiExchangeStateContainer.mousePressedOnPai(paiId);
        return paiExchangeStateContainer.getState();
    }

    public PaiExchangeState mouseReleasedForPaiExchange() {
        PaiExchangeStateContainer paiExchangeStateContainer = paiExchangeStateContainerRepository.take();
        paiExchangeStateContainer.mouseReleased();
        return paiExchangeStateContainer.getState();
    }

    public PaiExchangeState mouseExitedForPaiExchange(int paiId) {
        PaiExchangeStateContainer paiExchangeStateContainer = paiExchangeStateContainerRepository.take();
        paiExchangeStateContainer.mouseExitedFromPai(paiId);
        return paiExchangeStateContainer.getState();
    }

    public void exchangePai(long panId, int firstPaiId, int secondPaiId) {
        Pan pan = panRepository.take(panId);
        List<Pai> avaliablePaiList = pan.getAvaliablePaiList();
        List<PanPlayer> panPlayerList = pan.allPlayers();
        PaiExchanger firstPaiExchanger = null;
        PaiExchanger secondPaiExchanger = null;
        for (Pai pai : avaliablePaiList) {
            if (pai.getId() == firstPaiId) {
                firstPaiExchanger = new AvaliablePaiExchanger(pai, avaliablePaiList);
            } else if (pai.getId() == secondPaiId) {
                secondPaiExchanger = new AvaliablePaiExchanger(pai, avaliablePaiList);
            }
            if (firstPaiExchanger != null && secondPaiExchanger != null) {
                break;
            }
        }
        if (firstPaiExchanger == null || secondPaiExchanger == null) {
            for (PanPlayer panPlayer : panPlayerList) {
                List<Pai> paiList = panPlayer.getShoupaiList();
                for (Pai pai : paiList) {
                    if (pai.getId() == firstPaiId) {
                        firstPaiExchanger = new PlayerFangrushoupaiExchanger(panPlayer, pai.getId());
                    } else if (pai.getId() == secondPaiId) {
                        secondPaiExchanger = new PlayerFangrushoupaiExchanger(panPlayer, pai.getId());
                    }
                    if (firstPaiExchanger != null && secondPaiExchanger != null) {
                        break;
                    }
                }
                if (firstPaiExchanger != null && secondPaiExchanger != null) {
                    break;
                }
            }
        }
        if (firstPaiExchanger != null && secondPaiExchanger != null) {
            firstPaiExchanger.exchange(secondPaiExchanger);
        }
        PaiExchangeStateContainer paiExchangeStateContainer = paiExchangeStateContainerRepository.take();
        paiExchangeStateContainer.setState(new InitalPaiExchangeState());
    }
}
