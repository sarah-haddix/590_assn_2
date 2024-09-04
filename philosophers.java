import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class philosophers extends Thread {
    public static Lock[] forks = {new ReentrantLock(), new ReentrantLock(), new ReentrantLock(), new ReentrantLock(), new ReentrantLock()};

    public int id;
    public Lock leftFork;
    public Lock rightFork;
    public int waitPeriod = ((id%2)+1)*1000;

    public philosophers(int id, Lock leftFork, Lock rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }
 
    public void waiting(long duration) throws InterruptedException{
        System.out.println("My name is " + this.id + " and I'm going to think now");
        philosophers.sleep(duration);
    }

    public boolean tryConsume() throws InterruptedException{
        if (leftFork.tryLock()) {
            try {
                if (rightFork.tryLock()) {
                    try {
                        System.out.println("My name is " + this.id + " and I picked up both forks and im eating.");
                        philosophers.sleep(1000);
                        return true;
                    } finally {
                        rightFork.unlock();
                    }
                }
            } finally {
                leftFork.unlock();
            }
        }
        return false;
    }
    
    // Philosophers will want to eat at least once every five seconds
    public void run() {
        while (true) {
            try {
                this.waiting((long) (Math.random() * 5000)); // Think for a random time
                boolean success = this.tryConsume(); 
                if (!success) {
                    this.waiting(waitPeriod); 
                } else {
                    this.waiting(5000); // Successfully ate, wait for the full period
                }
            } catch (InterruptedException e) {
                System.out.println("Philosopher " + this.id + " was interrupted.");
            }
        }
    }
}


