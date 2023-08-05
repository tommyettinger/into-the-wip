package io.github.fourlastor.game.di.modules;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.harlequin.animation.AnimationNode;
import io.github.fourlastor.harlequin.loader.dragonbones.DragonBonesLoader;
import io.github.fourlastor.harlequin.loader.spine.SpineLoader;
import io.github.fourlastor.harlequin.loader.spine.model.SpineEntity;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class AssetsModule {

    private static final String PATH_TEXTURE_ATLAS = "images/packed/images.pack.atlas";
    private static final String PATH_LEVELS = "maps/levels.ldtk";
    private static final String PATH_WAVE_SHADER = "shaders/wave.vs";
    public static final String WHITE_PIXEL = "white-pixel";
    private static final String PATH_SPINE_JSON = "animations/animation_spine.json";
    private static final String PATH_DRAGON_BONES_JSON = "images/included/animations/dancer/dancer.json";

    @Provides
    public DragonBonesLoader dragonBonesLoader(JsonReader json) {
        return new DragonBonesLoader();
    }

    @Provides
    public SpineLoader spineLoader(JsonReader json) {
        return new SpineLoader(json);
    }

    @Provides
    @Singleton
    public AssetManager assetManager(SpineLoader spineLoader, DragonBonesLoader dragonBonesLoader) {
        AssetManager assetManager = new AssetManager();
        assetManager.setLoader(SpineEntity.class, spineLoader);
        assetManager.setLoader(AnimationNode.Group.class, dragonBonesLoader);
        assetManager.load(PATH_TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();
        return assetManager;
    }

    @Provides
    @Singleton
    public TextureAtlas textureAtlas(AssetManager assetManager) {
        return assetManager.get(PATH_TEXTURE_ATLAS, TextureAtlas.class);
    }

    @Provides
    @Singleton
    @Named(WHITE_PIXEL)
    public TextureRegion whitePixel(TextureAtlas atlas) {
        return atlas.findRegion("whitePixel");
    }
}
