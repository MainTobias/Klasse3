package com;


import javafx.scene.media.AudioClip;
import java.io.File;
import java.util.Objects;

public abstract class Application extends javafx.application.Application {
    private static final String MEDIA_PATH = String.valueOf(Objects.requireNonNull(Application.class.getResource("/com/media.mp3")).getPath());

    @Override
    public void init() {
        AudioClip sound = new AudioClip(new File(MEDIA_PATH).toURI().toString());
        sound.play();
    }
}
