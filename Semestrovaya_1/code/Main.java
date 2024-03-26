package Semestrovka_1.code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {

        File folderTests = new File("D:/AandSDSecondSem/src/main/java/Semestrovka_1/files");
        File results = new File("D:/AandSDSecondSem/src/main/java/Semestrovka_1/results.txt");


        results.createNewFile();

        FileWriter writer = new FileWriter("D:/AandSDSecondSem/src/main/java/Semestrovka_1/results.txt");
        String headlines = String.format("%-30s %-30s %-30s %-10s\n", "", "Размер входных данных", "Время работы в миллисекундах", "Количество итераций");
        writer.write(headlines);

        File[] testsData = folderTests.listFiles();
        int k = 0;
        if (testsData != null) {
            for (File file : testsData) {
                if (file.isFile()) {
                    try {
                        Scanner scanner = new Scanner(file);

                        int capacity = scanner.nextInt();
                        scanner.nextLine();

                        MaxHeap heap = new MaxHeap(capacity);

                        long start = System.nanoTime();

                        //сразу формируем кучу
                        while (scanner.hasNext()) {
                            //обычный hasDouble не сработал, тк у меня в файле числа с точками, а не с запятыми и он не распознает их как Double
                            String str = scanner.next();
                            Pattern p = Pattern.compile("-?[0-9]+\\.[0-9]+");
                            Matcher m = p.matcher(str);
                            if (m.matches()) {
                                double result = convertToDouble(str);
                                heap.add(result);
                            } else {
                                System.out.println("Некорректное значение");
                                break;
                            }
                        }
                        scanner.close();

                        //System.out.println("Файл " + file.getName());
                        //System.out.println("Неотсортированный массив (но уже преобразованный в кучу):");
                        //System.out.println(heap);

                        //сортируем
                        sort(heap);

                        long finish = System.nanoTime();
                        double time = (finish - start) / 1000000.0;
                        //long nanoTime = finish - start;

                        String data = String.format("%-30s %-30s %-30s %-30s\n", file.getName(), capacity ,time, heap.getK());
                        writer.write(data);

                        /*System.out.println("Время работы алгоритма в наносекундах: " + nanoTime);
                        System.out.println("Время работы алгоритма в миллисекундах: " + time);
                        System.out.println("Количество операций в алгоритме: " + heap.getK());

                        System.out.println("Отсортированный массив:");
                        System.out.println(heap);
                        System.out.println();
                        System.out.println();
                        System.out.println();*/
                        k++;

                        scanner.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Файл не найден.");
                    }
                }
            }
        } else {
            System.out.println("Папка пуста или не существует.");
        }

        System.out.println("Отсортировали файлов всего: "+ k);
        writer.close();
    }

    public static void sort(MaxHeap heap) {
        for (int i = heap.getCAPACITY() - 1; i >= 0; i--) {
            heap.setFromEnd(heap.maximumDelete());
        }
    }

    private static double convertToDouble(String str) {
        str = str.replace(',', '.');
        return Double.parseDouble(str);
    }

}
