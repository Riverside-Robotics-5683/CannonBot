package ravenrobotics.cannonbot;

public class Constants {
    public static class DSConstants {
        public static final int DRIVE_CONTROLLER = 0;

        public static final double LEFT_SLEW_RATE = 1.5;
        public static final double RIGHT_SLEW_RATE = 1.5;
        public static final double ROTATION_SLEW_RATE = 1.0;
    }

    public static class DriveConstants {
        public static final int FRONT_LEFT = 0;
        public static final int FRONT_RIGHT = 2;
        public static final int BACK_LEFT = 1;
        public static final int BACK_RIGHT = 3;
    }

    public static class PneumaticsConstants {
        public static final int BARREL_ONE = 0;
        public static final int BARREL_TWO = 1;
        public static final int BARREL_THREE = 2;
        public static final int BARREL_FOUR = 3;
    }
}
