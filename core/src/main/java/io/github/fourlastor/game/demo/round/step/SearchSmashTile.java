package io.github.fourlastor.game.demo.round.step;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import io.github.fourlastor.game.coordinates.Hex;
import io.github.fourlastor.game.demo.state.GameState;
import io.github.fourlastor.game.demo.state.map.Tile;
import java.util.function.Consumer;

public class SearchSmashTile extends Step<Hex> {

    private final Hex hex;

    @AssistedInject
    public SearchSmashTile(@Assisted Hex hex) {
        this.hex = hex;
    }

    @Override
    public void enter(GameState state, Consumer<Hex> continuation) {
        //        List<Tile> searched = state.newGraph.search(state.tileAt(hex), filter);
        //        for (Tile tile : searched) {
        //            if (tile.hex.equals(hex)) {
        //                continue;
        //            }
        //            if (tile.type == TileType.SOLID) {
        //                tile.actor.addListener(new SearchListener(tile, continuation));
        //                tile.actor.setColor(Color.YELLOW);
        //            } else {
        //                tile.actor.setColor(Color.CORAL);
        //            }
        //        }
    }

    @Override
    public void exit(GameState state) {
        for (Tile tile : state.tiles) {
            for (EventListener listener : tile.actor.getListeners()) {
                if (listener instanceof SearchListener) {
                    tile.actor.removeListener(listener);
                }
            }
            tile.actor.setColor(Color.WHITE);
        }
    }

    @AssistedFactory
    public interface Factory {
        SearchSmashTile create(Hex hex);
    }

    private static class SearchListener extends ClickListener {

        private final Tile tile;
        private final Consumer<Hex> continuation;

        private SearchListener(Tile tile, Consumer<Hex> continuation) {
            this.tile = tile;
            this.continuation = continuation;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            continuation.accept(tile.hex);
        }
    }
}
