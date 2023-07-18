package dev.selena.overstack.features;

public interface IFeature {

    public void run();
    public boolean isEnabled();
    public void loadCommands();

}
