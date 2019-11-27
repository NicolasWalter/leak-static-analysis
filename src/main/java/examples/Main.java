package examples;

import analysis.SensitivityConverter;

public class Main {
    public static void main(String[] args){
        Object sensible = new Object();
        SensitivityConverter.markAsSensible(sensible);
        System.out.println(sensible);
        Integer noSensible = 5;
        System.out.println(noSensible);
        SensitivityConverter.sanitize(sensible);
        System.out.println(sensible);
        test();
    }

    public static void test(){
        Object asd = new Object();
        SensitivityConverter.markAsSensible(asd);
        System.out.println(asd);
    }
}
