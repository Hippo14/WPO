package Algorithm.filters;

/**
 * @author Krzysztof Macioszek
 */
public class FilterList {

    public FilterList() {

    }

    public static Integer[][] lowPassAverage() {
        return new Integer[][] {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
    }

    public static Integer[][] lowPassSquare() {
        return new Integer[][] {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}
        };
    }

    public static Integer[][] lowPassCircular() {
        return new Integer[][] {
                {0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0}
        };
    }

    public static Integer[][] lowPass1() {
        return new Integer[][] {
                {1, 1, 1},
                {1, 2, 1},
                {1, 1, 1}
        };
    }

    public static Integer[][] lowPass2() {
        return new Integer[][] {
                {1, 1, 1},
                {1, 4, 1},
                {1, 1, 1}
        };
    }

    public static Integer[][] lowPass3() {
        return new Integer[][] {
                {1, 1,  1},
                {1, 12, 1},
                {1, 1,  1}
        };
    }

    public static Integer[][] lowPassPiramid() {
        return new Integer[][] {
                {1, 2, 3, 2, 1},
                {2, 4, 6, 4, 2},
                {3, 6, 9, 6, 3},
                {2, 4, 6, 4, 2},
                {1, 2, 3, 2, 1}
        };
    }

    public static Integer[][] lowPassConical() {
        return new Integer[][] {
                {0, 0, 1, 0, 0},
                {0, 4, 4, 4, 0},
                {1, 2, 5, 2, 1},
                {0, 4, 4, 4, 0},
                {0, 0, 1, 0, 0}
        };
    }

    public static Integer[][] lowPassGauss1() {
        return new Integer[][] {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };
    }

    public static Integer[][] lowPassGauss2() {
        return new Integer[][] {
                {1, 1, 2, 1, 1},
                {1, 2, 4, 2, 1},
                {2, 4, 8, 4, 2},
                {1, 2, 4, 2, 1},
                {1, 1, 2, 1, 1}
        };
    }

    public static Integer[][] lowPassGauss3() {
        return new Integer[][] {
                {0, 1, 2, 1, 0},
                {1, 4, 8, 4, 1},
                {2, 8, 16, 8, 2},
                {1, 4, 8, 4, 1},
                {0, 1, 2, 1, 0}
        };
    }

    public static Integer[][] lowPassGauss4() {
        return new Integer[][] {
                {1, 4, 7, 4, 1},
                {4, 16, 26, 16, 4},
                {7, 26, 41, 26, 7},
                {4, 16, 26, 16, 4},
                {1, 4, 7, 4, 1}
        };
    }

    public static Integer[][] lowPassGauss5() {
        return new Integer[][] {
                {1, 1, 2, 2, 2, 1, 1},
                {1, 2, 2, 4, 2, 2, 1},
                {2, 2, 4, 8, 4, 2, 2},
                {2, 4, 8, 16, 8, 4, 2},
                {2, 2, 4, 8, 4, 2, 2},
                {1, 2, 2, 4, 2, 2, 1},
                {1, 1, 2, 2, 2, 1, 1}
        };
    }

    public static Integer[][] highPassDeleteAverage() {
        return new Integer[][] {
                {-1, -1, -1},
                {-1,  9, -1},
                {-1, -1, -1}
        };
    }

    public static Integer[][] highPass1() {
        return new Integer[][] {
                {0,  -1,  0},
                {-1,  5, -1},
                {0,  -1,  0}
        };
    }

    public static Integer[][] highPass2() {
        return new Integer[][] {
                {1,  -2,  1},
                {-2,  5, -2},
                {1,  -2,  1}
        };
    }

    public static Integer[][] highPass3() {
        return new Integer[][] {
                {0,   -1,  0},
                {-1,  20, -1},
                {0,   -1,  0}
        };
    }

    public static Integer[][] gradientDirectionalEast() {
        return new Integer[][] {
                {-1, 1,  1},
                {-1, -2, 1},
                {-1, 1,  1}
        };
    }

    public static Integer[][] gradientDirectionalSoutheast() {
        return new Integer[][] {
                {-1, -1, 1},
                {-1, -2, 1},
                { 1,  1, 1}
        };
    }

    public static Integer[][] gradientDirectionalSouth() {
        return new Integer[][] {
                {-1, -1, -1},
                { 1, -2,  1},
                { 1,  1,  1}
        };
    }

    public static Integer[][] gradientDirectionalSouthwest() {
        return new Integer[][] {
                { 1, -1, -1},
                { 1, -2, -1},
                { 1,  1,  1}
        };
    }

    public static Integer[][] gradientDirectionalWest() {
        return new Integer[][] {
                { 1,  1, -1},
                { 1, -2, -1},
                { 1,  1, -1}
        };
    }

    public static Integer[][] gradientDirectionalNorthwest() {
        return new Integer[][] {
                { 1,  1,  1},
                { 1, -2, -1},
                { 1, -1, -1}
        };
    }

    public static Integer[][] gradientDirectionalNorth() {
        return new Integer[][] {
                { 1,  1,  1},
                { 1, -2,  1},
                {-1, -1, -1}
        };
    }

    public static Integer[][] gradientDirectionalNortheast() {
        return new Integer[][] {
                { 1,  1,  1},
                {-1, -2,  1},
                {-1, -1,  1}
        };
    }
}
