package collection;

public enum Climate {
    TROPICAL_SAVANNA,
    HUMIDSUBTROPICAL,
    OCEANIC;

    public static boolean isClimate(String s){
        try {
            Climate.valueOf(s);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
    public static String enumToStr(){
        String s = "";
        for (Climate o: Climate.values())
            s += o.name()+" ";
        return s;
    }
}
