package org.example;

import org.example.dao.ClientAccountRepository;
import org.example.dao.common.DbConnector;
import org.example.service.Calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculateContrib {
    public static void main(String[] args) {
        System.out.println("Введите номер: ");
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();

        ClientAccountRepository clientAccountRepository = new ClientAccountRepository();
        Calculator calculator = new Calculator(clientAccountRepository);
        List<Calculator.ContribState> list = calculator.calculateIncome(i);

        System.out.println(list.toString());

    }
}
