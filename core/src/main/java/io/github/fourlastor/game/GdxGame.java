package io.github.fourlastor.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import io.github.fourlastor.game.demo.DemoComponent;
import io.github.fourlastor.game.di.GameComponent;
import io.github.fourlastor.game.route.Router;
import javax.inject.Inject;

public class GdxGame extends Game implements Router {

    private final InputMultiplexer multiplexer;

    private final DemoComponent.Builder demoComponentBuilder;

    private Screen pendingScreen = null;

    @Inject
    public GdxGame(InputMultiplexer multiplexer, DemoComponent.Builder demoComponentBuilder) {
        this.multiplexer = multiplexer;
        this.demoComponentBuilder = demoComponentBuilder;
    }

    @Override
    public void create() {
        //        if (Gdx.app.getType() != Application.ApplicationType.Android) {
        //
        //            Cursor customCursor =
        //                    Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("images/included/whitePixel.png")),
        // 0, 0);
        //            Gdx.graphics.setCursor(customCursor);
        //        }
        Gdx.input.setInputProcessor(multiplexer);
        goToDemo();
    }

    @Override
    public void render() {
        if (pendingScreen != null) {
            setScreen(pendingScreen);
            pendingScreen = null;
        }
        super.render();
    }

    public static GdxGame createGame() {
        return GameComponent.component().game();
    }

    @Override
    public void goToDemo() {
        pendingScreen = demoComponentBuilder.router(this).build().screen();
    }

    @Override
    public void goToLevel() {
        Gdx.app.error("GdxGame", "Level screen is missing");
    }
}
