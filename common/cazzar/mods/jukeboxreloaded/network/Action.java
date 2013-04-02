package cazzar.mods.jukeboxreloaded.network;

public enum Action
{
    NEXT_TRACK(0), PREVIOUS_TRACK(1), PLAY(2), STOP(3);

    public static Action fromInt(int readInt)
    {
        for (final Action a : Action.values())
        {
            if (a.getAction() == readInt) return a;
        }
        return null;
    }

    private int actionID;

    Action(int actionID)
    {
        this.actionID = actionID;
    }

    public int getAction()
    {
        return actionID;
    }

}
