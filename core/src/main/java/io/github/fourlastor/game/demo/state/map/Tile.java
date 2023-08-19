package io.github.fourlastor.game.demo.state.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.fourlastor.game.coordinates.Hex;

public class Tile {
    public final Image actor;
    public final Hex hex;
    public final TileType type;

    public Tile(Image image, GridPoint2 position, TileType type) {
        this.actor = image;
        this.hex = new Hex(position);
        this.type = type;
    }

    public static class Link implements Connection<Tile> {

        private final Tile from;
        private final Tile to;

        public Link(Tile from, Tile to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public float getCost() {
            return 1;
        }

        @Override
        public Tile getFromNode() {
            return from;
        }

        @Override
        public Tile getToNode() {
            return to;
        }
    }
}
