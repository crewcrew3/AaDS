package Semestrovka_2.code;

public class BStarTree {
    private static final int M = 4;
    private Node root;
    private int operationCount;

    public BStarTree() {
        root = new Node(true);
        operationCount = 0;
    }

    public Node search(int key) {
        operationCount = 0;
        return search(root, key);
    }

    public Node search(Node node, int key) {
        int i = 0;
        while (i < node.getSize() && key > node.getKeys()[i]) { /*идем по ключам ноды и ищем элемент,
                                                                который больше искомого элемента. Запоминаем индекс - это будет индексом ребенка,
                                                                по которому мы сможем продолжить поиск(мы как бы спускаемся к числам, которые < node.getKeys()[i], но > чем последнее значение x в ноде, тчто key > x.
                                                                т.е. мы идем к промежутку, в котором может лежать наше значение */
            i++;
            operationCount++;
        }

        if (i < node.getSize() && key == node.getKeys()[i]) { //если совпало, то мы нашли
            return node;
        }
        if (node.isLeaf()) { /*если мы уперлись в значение, большее чем наше key, при этом оно не совпало с ним,
                              и если спускаться больше некуда(наша нода - лист), то искать смысла больше нет.*/
            return null;
        }
        operationCount++;
        return search(node.getChildren()[i], key); //продолжаем поиск элемента в подходящем ребенке, где может лежать искомый элемент
    }

    public void insert(int key) {
        operationCount = 0;
        Node current = root;
        if (current.getSize() == 2 * M - 1) { //если корень полный
            Node s = new Node(false);
            root = s;
            s.getChildren()[0] = current;
            current.setParent(s);
            operationCount++;
            splitChild(s, 0, current); /*Позже медиана(средний элемент) вставится в родительский узел, что может привести к его разделению и т. д. Если узел не имеет родителя (т.е. узел был корнем),
                                         создается новый корень над этим узлом (увеличив высоту дерева).*/
            insertNonFull(s, key); //Отвечает за вставку как таковую. Если переданная нода лист, кладет в нее, если не лист, ищет подходящих детей для вставляемого элемента. Если ребенок полный, то делит его.
        } else { //если корень не полный, то не увеличиваем высоту дерева, просто вставляем
            insertNonFull(current, key);
        }
    }

    public void insertNonFull(Node node, int key) {
        int i = node.getSize() - 1;
        if (node.isLeaf()) { //если лист (не полный)
            while (i >= 0 && key < node.getKeys()[i]) { //ищем правильную позицию для нового значения, "сдвигаем" элементы неполной ноды
                node.getKeys()[i + 1] = node.getKeys()[i];
                i--;
                operationCount ++;
            }
            node.getKeys()[i + 1] = key; //ставим элемент на нужную позицию
            node.setSize(node.getSize() + 1);
        } else { //если не лист
            while (i >= 0 && key < node.getKeys()[i]) { //ищем в какого ребенка нужно положить значение
                i--;
                operationCount++;
            }
            i++;
            if (node.getChildren()[i].getSize() == 2 * M - 1) { //если ребенок полный, то снова делим узел
                splitChild(node, i, node.getChildren()[i]);
                //разделили ноду на две и "освобождаем место" для нового значения. Теперь можем вставлять в него, как в неполный узел
                if (key > node.getKeys()[i]) {
                    i++;
                    operationCount++;
                }
            }
            operationCount++;
            insertNonFull(node.getChildren()[i], key); //пытаемся вставить элемент в подходящего ребенка
        }
    }

    public void splitChild(Node parent, int i, Node child) {
        Node newNode = new Node(child.isLeaf()); //создаем вторую ноду
        newNode.setSize(M - 1);
        for (int j = 0; j < M - 1; j++) {
            newNode.getKeys()[j] = child.getKeys()[j + M]; //копируем во вторую ноду некоторую часть элементов переполненной ноды
        }
        operationCount++;
        if (!child.isLeaf()) {
            for (int j = 0; j < M; j++) {
                newNode.getChildren()[j] = child.getChildren()[j + M]; //если не лист, то и соответствующих детей новая нода тоже забирает
            }
            operationCount++;
        }
        child.setSize(M - 1); //типа освободили место в ноде для нового элемента

        for (int j = parent.getSize(); j >= i + 1; j--) {
            parent.getChildren()[j + 1] = parent.getChildren()[j]; //двигаем детей родителя, которые правее, чем наша новая нода, чтобы добавить новую ноду в детей на правильную позицию
        }
        parent.getChildren()[i + 1] = newNode; //ставим ребенка на соответствующую позицию
        operationCount++;
        newNode.setParent(parent);
        for (int j = parent.getSize() - 1; j >= i; j--) {
            parent.getKeys()[j + 1] = parent.getKeys()[j]; //двигаем ключи в родителе
        }
        parent.getKeys()[i] = child.getKeys()[M - 1]; //переносим среднее значение в ребенке в родителя
        parent.setSize(parent.getSize() + 1);
        operationCount++;
    }

    public void delete(int key) {
        operationCount = 0;
        delete(root, key);
    }

    public void delete(Node node, int key) { //основной подсчет операций ведется во вспомогательных методах
        if (node == null) {
            return;
        }

        int i = 0;
        while (i < node.getSize() && key > node.getKeys()[i]) {
            i++;
            operationCount++;
        }

        // Случай 1: Ключ найден в текущем узле
        if (i < node.getSize() && key == node.getKeys()[i]) {
            if (node.isLeaf()) {
                removeFromLeaf(node, i);
            } else {
                removeFromNonLeaf(node, i);
            }
        } else {
            // Случай 2: Ключ не найден в текущем узле
            if (node.isLeaf()) {
                return; // Ключ отсутствует в дереве
            }

            // Ключ может быть в поддереве
            boolean flag = i == node.getSize(); // Флаг, указывающий, является ли дочерний узел последним
            if (node.getChildren()[i].getSize() < M) {
                fillChild(node, i);
            }

            if (flag && i > node.getSize()) {
                delete(node.getChildren()[i - 1], key);
            } else {
                delete(node.getChildren()[i], key);
            }
        }
        operationCount++;
    }

    public void removeFromNonLeaf(Node node, int index) {
        int key = node.getKeys()[index];

        // Случай 1: Предшественник имеет не менее M ключей
        if (node.getChildren()[index].getSize() >= M) {
            int pred = getPred(node, index);
            node.getKeys()[index] = pred;
            operationCount++;
            delete(node.getChildren()[index], pred);
        }
        // Случай 2: Последователь имеет не менее M ключей
        else if (node.getChildren()[index + 1].getSize() >= M) {
            int succ = getSucc(node, index);
            node.getKeys()[index] = succ;
            operationCount++;
            delete(node.getChildren()[index + 1], succ);
        }
        // Случай 3: Оба дочерних узла имеют M-1 ключей, объединяем их
        else {
            mergeChildren(node, index);
            delete(node.getChildren()[index], key);
        }
    }

    public void removeFromLeaf(Node node, int index) { //Удаление сразу из листа
        for (int i = index + 1; i < node.getSize(); i++) {
            node.getKeys()[i - 1] = node.getKeys()[i];
        }
        operationCount++;
        node.setSize(node.getSize() - 1);
    }

    public int getPred(Node node, int index) {
        Node cur = node.getChildren()[index];
        while (!cur.isLeaf()) {
            cur = cur.getChildren()[cur.getSize()];
        }
        operationCount++;
        return cur.getKeys()[cur.getSize() - 1];
    }

    public int getSucc(Node node, int index) {
        Node cur = node.getChildren()[index + 1];
        while (!cur.isLeaf()) {
            cur = cur.getChildren()[0];
        }
        operationCount++;
        return cur.getKeys()[0];
    }

    public void fillChild(Node node, int index) {
        if (index != 0 && node.getChildren()[index - 1].getSize() >= M) {
            borrowFromPrev(node, index);
        } else if (index != node.getSize() && node.getChildren()[index + 1].getSize() >= M) {
            borrowFromNext(node, index);
        } else {
            if (index != node.getSize()) {
                mergeChildren(node, index);
            } else {
                mergeChildren(node, index - 1);
            }
        }
    }

    public void borrowFromPrev(Node node, int index) { //заимствуем у левого сиблинга
        Node child = node.getChildren()[index];
        Node sibling = node.getChildren()[index - 1];

        for (int i = child.getSize() - 1; i >= 0; i--) {
            child.getKeys()[i + 1] = child.getKeys()[i];
        }
        operationCount++;

        if (!child.isLeaf()) {
            for (int i = child.getSize(); i >= 0; i--) {
                child.getChildren()[i + 1] = child.getChildren()[i];
            }
            operationCount++;
        }

        child.getKeys()[0] = node.getKeys()[index - 1];

        if (!child.isLeaf()) {
            child.getChildren()[0] = sibling.getChildren()[sibling.getSize()];
            operationCount++;
        }

        node.getKeys()[index - 1] = sibling.getKeys()[sibling.getSize() - 1];
        operationCount++;

        child.setSize(child.getSize() + 1);
        sibling.setSize(sibling.getSize() - 1);
    }

    public void borrowFromNext(Node node, int index) { //заимствуем у правого сиблинга
        Node child = node.getChildren()[index];
        Node sibling = node.getChildren()[index + 1];

        child.getKeys()[child.getSize()] = node.getKeys()[index];

        if (!child.isLeaf()) {
            child.getChildren()[child.getSize() + 1] = sibling.getChildren()[0];
            operationCount++;
        }

        node.getKeys()[index] = sibling.getKeys()[0];

        for (int i = 1; i < sibling.getSize(); i++) {
            sibling.getKeys()[i - 1] = sibling.getKeys()[i];
        }
        operationCount++;

        if (!sibling.isLeaf()) {
            for (int i = 1; i <= sibling.getSize(); i++) {
                sibling.getChildren()[i - 1] = sibling.getChildren()[i];
            }
            operationCount++;
        }

        child.setSize(child.getSize() + 1);
        sibling.setSize(sibling.getSize() - 1);
    }

    public void mergeChildren(Node node, int index) {
        Node child = node.getChildren()[index];
        Node sibling = node.getChildren()[index + 1];

        child.getKeys()[M - 1] = node.getKeys()[index];

        for (int i = 0; i < sibling.getSize(); i++) {
            child.getKeys()[i + M] = sibling.getKeys()[i];
        }
        operationCount++;

        if (!child.isLeaf()) {
            for (int i = 0; i <= sibling.getSize(); i++) {
                child.getChildren()[i + M] = sibling.getChildren()[i];
            }
            operationCount++;
        }

        for (int i = index + 1; i < node.getSize() - 1; i++) {
            node.getKeys()[i] = node.getKeys()[i + 1];
        }
        operationCount++;

        for (int i = index + 2; i <= node.getSize(); i++) {
            node.getChildren()[i - 1] = node.getChildren()[i];
        }
        operationCount++;
        child.setSize(child.getSize() + sibling.getSize() + 1);
        node.setSize(node.getSize() - 1);
    }

    public int getOperationCount() {
        return operationCount;
    }
}
