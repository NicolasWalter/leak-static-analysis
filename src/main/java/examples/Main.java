package examples;

import analysis.SensitivityConverter;

public class Main {
    public static void main(String[] args){
        Object obj1 = new Object();
        SensitivityConverter.markAsSensible(obj1);
        System.out.println(obj1);
        String prueba = "prueba string";
        Integer hola = 5;
        SensitivityConverter.markAsSensible(hola);
        System.out.println(prueba);
        System.out.println(hola);
    }
}
