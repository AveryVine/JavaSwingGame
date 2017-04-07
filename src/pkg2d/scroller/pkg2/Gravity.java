package pkg2d.scroller.pkg2;

/**
 *
 * @author AveryVine
 */
public interface Gravity {
    public final double ACCELERATION = 9.81;
    public final int INITIAL_VELOCITY = -5;
    public int getVerticalTick();
    public void boost();
    public boolean isAirborne();
}
