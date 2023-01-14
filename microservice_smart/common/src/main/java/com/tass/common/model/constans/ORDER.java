package com.tass.common.model.constans;

public interface ORDER {
    class STATUS{
        public static final int CREATED = 1;
        public static final int FAIL = 2;
        public static final int SUCCESS = 3;
    }

    class SUCCESS_STATUS {
        public static final int SUCCESS = 1;
        public static final int FAIL = 0;
    }
}
