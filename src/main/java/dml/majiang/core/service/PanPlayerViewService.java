package dml.majiang.core.service;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.panplayerview.CanNotSeeOtherPlayersPanView;
import dml.majiang.core.repository.PanRepository;
import dml.majiang.core.service.repositoryset.PanPlayerViewServiceRepositorySet;

public class PanPlayerViewService {
    public static CanNotSeeOtherPlayersPanView buildPanViewForPlayer(long panId, String playerId, PanPlayerViewServiceRepositorySet panPlayerViewServiceRepositorySet) {
        PanRepository panRepository = panPlayerViewServiceRepositorySet.getPanRepository();

        Pan pan = panRepository.find(panId);
        CanNotSeeOtherPlayersPanView panView = new CanNotSeeOtherPlayersPanView(playerId, pan);
        return panView;
    }
}
