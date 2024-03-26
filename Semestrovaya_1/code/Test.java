package Semestrovka_1.code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {

        int raz = 0;
        for (int k = 0; k < 10; k++) {
            for (int j = 0; j < 10; j++) {
                String num = k + "" + j;

                File file = new File("D:/AandSDSecondSem/src/main/java/Semestrovka_1/files/test_0" + num + ".txt");

                file.createNewFile();
                int size = 10000 - raz;

                FileWriter writer = new FileWriter("D:/AandSDSecondSem/src/main/java/Semestrovka_1/files/test_0" + num + ".txt");
                writer.write(size + "\n");

                for (int i = 0; i < size; i++) {
                    int deg = (int) (Math.pow(-1, (int) (Math.random() * 100)));
                    double n = (Math.random() * 500000) * deg;
                    writer.write(n + "\n");
                }

                writer.close();
                raz += (int) (Math.random() * 150);
            }
        }
    }
}
