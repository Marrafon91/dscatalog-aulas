import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite seu Peso ");
        double peso = sc.nextDouble();

        System.out.print("Digite sua Altura ");
        double altura = sc.nextDouble();

        double imc = calcularIMC(peso, altura);

        System.out.printf("Seu IMC é %.2f%n", imc);

        sc.close();
    }

    public static double calcularIMC(double peso, double altura) {
        return peso / (altura * altura);
    }
}