import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * The algorithm works as follows:
 * A philosopher will want to eat at a random time, at least every five seconds.
 * When the philosopher wants to eat, they will attempt to pick up their left fork and then they will attempt to pick up their right fork. The exception to this is the first philosopher, who will attempt to do this in the opposite order. If it is not possible for a philosopher to pick up their forks, they will wait for either one or two seconds. Philosopher 1 will wait for 1 second, philosopher 2 for 2 seconds, philosopher 3 for 1 second, philosopher 4 for 2 seconds, and philosopher 5 for 1 second. This staggered waiting time is so that there is not a scenario where two philosophers next to each other would keep picking up one fork and putting it back down at exactly the same time, causing a loop and deadlock.
 * When a philosopher is able to eat, they will eat for one second and then put down their forks and wait for 5 seconds. This is done so that the philosophers next to them will be able to fulfill an eating turn in that time, preventing starvation. 
 * This process repeats indefinitely.
 */

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


