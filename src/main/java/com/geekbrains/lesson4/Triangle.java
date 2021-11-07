package com.geekbrains.lesson4;

public class Triangle {
    public static double calculateSquare (double a, double b, double c) throws Exception {
        if (a <= 0 || b <= 0 || c <= 0) throw new Exception("Необходимо проверить введенные значения");
        if (a == b && b == c) throw new Exception("Равносторонний треугольник");
        if (a == b || a == c || b == c) throw new Exception("Равнобедренный треугольник");
        double halfPerimeter = (a + b + c) / 2;
        return Math.sqrt(halfPerimeter * (halfPerimeter - a) * (halfPerimeter - b) * (halfPerimeter - c));
    }
}
