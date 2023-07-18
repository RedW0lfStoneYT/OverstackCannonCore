package dev.selena.overstack.config;

import dev.selena.config.FileManager;

import java.io.File;

public enum Configs {


        LANG(Lang.class, "", "Lang.json"),
        MAGIC_SAND(MagicSand.class, "", "MagicSand.json"),
        SAND_REMOVER(SandRemover.class, "", "SandRemover.json"),
        WALLS(Walls.class, "", "Walls.json"),
        FIRE(Fire.class, "", "Fire.json"),
        TICK_COUNTER(TickCounter.class, "", "TickCounter.json")
    ;


        private final File file;

        private final Class<?> clazz;

        <T> Configs(Class<T> clazz, String parent, String path) {
            this.clazz = clazz;
            file = FileManager.file(parent, path);
        }


        @SuppressWarnings("unchecked")
        public <T> T getConfig() {
            return (T) FileManager.loadFile(clazz, file);
        }

}
