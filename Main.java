public class Main {
    public static void main(String[] args) throws InterruptedException {

        new ShowTimeSpentNotify().start();
        new Show5SecondNotify().start();
        new Problem_2().buildQueue(15);

    }
}

// Напишіть програму, яка кожну секунду відображає на екрані дані про час, що пройшов від моменту запуску програми.
//
//Другий потік цієї ж програми кожні 5 секунд виводить повідомлення Пройшло 5 секунд.

class ShowTimeSpentNotify extends Thread {
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        synchronized (this) {
            try {
                while (true) {
                    this.wait(1000);
                    System.out.println("Пройшло " + (System.currentTimeMillis() - startTime) + " мілісекунд.");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class Show5SecondNotify extends Thread {
    @Override
    public void run() {
        synchronized (this) {
            try {
                while (true) {
                    this.wait(5000);
                    System.out.println("Пройшло 5 секунд.");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
