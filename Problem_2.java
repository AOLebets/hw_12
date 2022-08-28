import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Problem_2 {

// Напишіть програму, що виводить в консоль рядок, що складається з чисел від 1 до n, але з заміною деяких значень:
//
//якщо число ділиться на 3 - вивести fizz
//якщо число ділиться на 5 - вивести buzz
//якщо число ділиться на 3 і на 5 одночасно - вивести fizzbuzz
//Наприклад, для n = 15, очікується такий результат: 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz.
//
//Програма має бути багатопоточною, і працювати з 4 потоками:
//
//Потік A викликає fizz() щоб перевірити, чи ділиться число на 3, і якщо так - додати в чергу на виведення для потоку D рядок fizz.
//Потік B викликає buzz() щоб перевірити, чи ділиться число на 5, і якщо так - додати в чергу на виведення для потоку D рядок buzz.
//Потік C викликає fizzbuzz() щоб перевірити, чи ділиться число на 3 та 5 одночасно, і якщо так - додати в чергу на виведення для потоку D рядок fizzbuzz.
//Потік D викликає метод number(), щоб вивести наступне число з черги, якщо є таке число для виведення.
    BlockingQueue<Integer> fizzQueue = new LinkedBlockingDeque<>(1);
    BlockingQueue<Integer> buzzQueue = new LinkedBlockingDeque<>(1);
    BlockingQueue<Integer> fizzbuzzQueue = new LinkedBlockingDeque<>(1);
    BlockingQueue<Integer> numberQueue = new LinkedBlockingDeque<>(1);
    BlockingQueue<String> finalQueue = new LinkedBlockingDeque<>(1);

    public void buildQueue(int n) throws InterruptedException {

        List<String> resultList = new ArrayList<>();

        Thread a = new Thread_A();
        Thread b = new Thread_B();
        Thread c = new Thread_C();
        Thread d = new Thread_D();
        a.start();
        b.start();
        c.start();
        d.start();

        for (int i = 1; i <= n; i++) {
            fizzQueue.put(i);
            buzzQueue.put(i);
            fizzbuzzQueue.put(i);
            numberQueue.put(i);

            resultList.add(finalQueue.take());
        }

        fizzQueue.put(-1);
        buzzQueue.put(-1);
        fizzbuzzQueue.put(-1);
        numberQueue.put(-1);

        System.out.println(String.join(", ", resultList));

        return;

    }

    class Thread_A extends Thread {
        private int number;
        @Override
        public void run() {
            try {
                while (true) {
                    number = fizzQueue.take();
                    if (number % 3 == 0 && number % 5 != 0 && number > 0) {
                        fizz();
                    } else if (number == -1) {
                        return;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        public void fizz() {
            try {
                finalQueue.put("fizz");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Thread_B extends Thread {
        private int number;
        @Override
        public void run() {
            try {
                while (true) {
                    number = buzzQueue.take();
                    if (number % 5 == 0 && number % 3 != 0  && number > 0) {
                        buzz();
                    } else if (number == -1) {
                        return;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        public void buzz() {
            try {
                finalQueue.put("buzz");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Thread_C extends Thread {
        private int number;
        @Override
        public void run() {
            try {
                while (true) {
                    number = fizzbuzzQueue.take();
                    if (number % 3 == 0 && number % 5 == 0 && number > 0) {
                        fizzbuzz();
                    } else if (number == -1) {
                        return;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        public void fizzbuzz() {
            try {
                finalQueue.put("fizzbuzz");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Thread_D extends Thread {
        private int number;
        @Override
        public void run() {
            try {
                while (true) {
                    number = numberQueue.take();
                    if (number % 3 != 0 && number % 5 != 0 && number > 0) {
                        number();
                    } else if (number == -1) {
                        return;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        public void number() {
            try {
                finalQueue.put(String.valueOf(number));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

