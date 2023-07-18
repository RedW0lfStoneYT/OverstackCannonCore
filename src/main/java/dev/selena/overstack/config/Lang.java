package dev.selena.overstack.config;

public class Lang {


    public String NO_BUTTON = "&cThere is no button at this location, please press another button and try again",
    NOT_PRESSED_BUTTON = "&cYou have not pressed a button",
    FIRED = "&bFired",
    NO_LEVER = "&cThere is no lever at this location, please flick one and try again",
    NOT_FLICKED_LEVER = "&cYou have not flicked any levers",
    FLICKED = "&bLever flicked",
    TICKS_SUBTRACTED = "&cSubtracted %REDSTONE_TICK_COUNT% (%GAME_TICK_COUNT% GT) Ticks.\n RT: %TOTAL_REDSTONE_TICKS%, GT: %TOTAL_GAME_TICKS%",
    TICKS_ADD = "&aAdded %REDSTONE_TICK_COUNT% (%GAME_TICK_COUNT% GT) Ticks.\n RT: %TOTAL_REDSTONE_TICKS%, GT: %TOTAL_GAME_TICKS%",
    TICKS_RESET = "&cTicks reset. RT: %TOTAL_REDSTONE_TICKS%, GT: %TOTAL_GAME_TICKS%";

    public static Lang get() {
        return Configs.LANG.getConfig();
    }
}
