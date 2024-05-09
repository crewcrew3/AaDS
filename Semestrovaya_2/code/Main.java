package Semestrovka_2.code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static Random random = new Random();
    public static FileWriter writer_insert;
    public static FileWriter writer_search;
    public static FileWriter writer_delete;

    public static void main(String[] args) throws IOException {
        //BStarTree myTree = new BStarTree();

        BStarTree tree = new BStarTree();

        int[] randomArray = generateRandomArray();

        initializeFilesForResults();

        //добавление
        List<Long> time_insert = new ArrayList<>();
        List<Integer> operations_insert = new ArrayList<>();
        for (int j : randomArray) {
            long start = System.nanoTime();

            tree.insert(j);

            long finish = System.nanoTime();
            long duration = finish - start;
            time_insert.add(duration);

            int operations = tree.getOperationCount();
            operations_insert.add(operations);

            String data = String.format("%-30s %-30s %-30s\n", j, duration , operations);
            writer_insert.write(data);
        }

        double average_time_insert = time_insert.stream().mapToLong(x -> x).average().getAsDouble();
        double average_operations_insert = operations_insert.stream().mapToInt(x -> x).average().getAsDouble();
        String data1 = String.format("%-40s %-40s\n", "Среднее время добавления: " + average_time_insert, "Среднее кол-во операций при добавлении: " + average_operations_insert);
        System.out.println(data1);
        writer_insert.write(data1);
        writer_insert.close();


        //поиск элемента
        List<Long> time_search = new ArrayList<>();
        List<Integer> operations_search = new ArrayList<>();
        for (int i = 0; i < 100; i++) {

            int index = random.nextInt(randomArray.length);
            long start = System.nanoTime();

            tree.search(randomArray[index]);

            long finish = System.nanoTime();
            long duration = finish - start;
            time_search.add(duration);

            int operations = tree.getOperationCount();
            operations_search.add(operations);

            String data = String.format("%-30s %-30s %-30s\n", randomArray[index], duration , operations);
            writer_search.write(data);
        }

        double average_time_search = time_search.stream().mapToLong(x -> x).average().getAsDouble();
        double average_operations_search = operations_search.stream().mapToInt(x -> x).average().getAsDouble();
        String data2 = String.format("%-40s %-40s\n", "Среднее время поиска: " + average_time_search, "Среднее кол-во операций при поиске: " + average_operations_search);
        System.out.println(data2);
        writer_search.write(data2);
        writer_search.close();


        //удаление элемента
        List<Long> time_delete = new ArrayList<>();
        List<Integer> operations_delete = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {

            int index = random.nextInt(randomArray.length);
            long start = System.nanoTime();

            tree.delete(randomArray[index]);

            long finish = System.nanoTime();
            long duration = finish - start;
            time_delete.add(duration);

            int operations = tree.getOperationCount();
            operations_delete.add(operations);

            String data = String.format("%-30s %-30s %-30s\n", randomArray[index], duration , operations);
            writer_delete.write(data);
        }

        double average_time_delete = time_delete.stream().mapToLong(x -> x).average().getAsDouble();
        double average_operations_delete = operations_delete.stream().mapToInt(x -> x).average().getAsDouble();
        String data3 = String.format("%-40s %-40s\n", "Среднее время удаления: " + average_time_delete, "Среднее кол-во операций при удалении: " + average_operations_delete);
        System.out.println(data3);
        writer_delete.write(data3);
        writer_delete.close();
    }

    public static void initializeFilesForResults () throws IOException {
        File results_insert = new File("D:/AandSDSecondSem/src/main/java/Semestrovka_2/results/results_insert.txt");
        File results_search = new File("D:/AandSDSecondSem/src/main/java/Semestrovka_2/results/results_search.txt");
        File results_delete = new File("D:/AandSDSecondSem/src/main/java/Semestrovka_2/results/results_delete.txt");
        results_insert.createNewFile();
        results_search.createNewFile();
        results_delete.createNewFile();
        writer_insert = new FileWriter("D:/AandSDSecondSem/src/main/java/Semestrovka_2/results/results_insert.txt");
        writer_search = new FileWriter("D:/AandSDSecondSem/src/main/java/Semestrovka_2/results/results_search.txt");
        writer_delete = new FileWriter("D:/AandSDSecondSem/src/main/java/Semestrovka_2/results/results_delete.txt");
        String headlines = String.format("%-30s %-30s %-10s\n", "Элемент массива", "Время в миллисекундах", "Количество операций");
        writer_insert.write(headlines);
        writer_search.write(headlines);
        writer_delete.write(headlines);
    }

    public static int[] generateRandomArray() {
        int[] randomArray = new int[10000];
        for (int i = 0; i < randomArray.length; i++) {
            // заполняем каждый элемент случайным числом
            randomArray[i] = random.nextInt();
        }
        return randomArray;
    }
}
