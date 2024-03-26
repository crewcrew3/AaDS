package Semestrovka_1.code;

public class MaxHeap { //куча, у которой в корне максимальный элемент

    private double[] heap;
    private int size = 0; //отслеживать текущий размер кучи
    private final int CAPACITY;

    private int k; //колво итераций

    public MaxHeap(int size) {
        CAPACITY = size;
        heap = new double[size];
    }

    public void add(double element) {
        heap[size] = element;
        size++;
        int i = size - 1;//индекс узла, который мы добавили
        while ((i != 0) && heap[i] > heap[(i - 1) / 2]) { //пока мы не дошли до корня
            // и пока условие на ребре нарушается,
            //то двигаем наш элемент наверх
            swap(i, (i - 1) / 2); //двигаем наш элемент наверх, т.е. меняем его местами с его родителем
            i = (i - 1) / 2;
            k++;
        }

    }

    public void swap(int i, int j) {
        double x = heap[i];
        heap[i] = heap[j];
        heap[j] = x;
    }

    public double maximumDelete() { //возвращает максимум, который мы удалили
        double max = heap[0];

        swap(0, size - 1); //меняем местами корень(максимум) с последним элементом в дереве(спускаем наш максимум, чтобы было удобней удалять)
        size--;

        //Восстановим порядок в нашей куче. Идем от корня и смотрим, не нарушается ли условие на ребрах.
        int i = 0;
        int child = (2 * i) + 1;

        //Проверяем, есть ли второй ребенок. Если да - сравниваем двоих детей между собой и
        //берем того, кто оказался больше.
        if (child + 1 < size && heap[child] < heap[child + 1]) {
            child++;
        }

        //выполняем этот процесс до тех пор, пока у i-ой вершины есть дети и i-ая вершина оказывается меньше, чем максимальный из двух детей.
        while (2 * i < (size - 1) && heap[i] < heap[child]) {
            swap(i, child);
            i = child;
            child = 2 * i + 1; //снова выбираем ребенка
            if (child + 1 < size && heap[child] < heap[child + 1]) {
                child++;
            }
            k++;
        }
        return max;
    }

    public void setFromEnd (double element) { //заполняем массив с конца
        heap[size] = element;
        k++; //в мейне мы будем вызывать цикл, который будет использовать этот метод. Поэтому кол-во итераций удобнее посчитать сразу здесь
    }

    public int getCAPACITY() {
        return CAPACITY;
    }

    public int getK() {
        return k;
    }

    //туСтринг идет по капасити потому что я хочу видеть максимумы, которые были спущены вниз
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < CAPACITY; i++) {
            str += heap[i] + "  ";
        }
        return str;
    }
}
