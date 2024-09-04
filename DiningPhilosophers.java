

public class DiningPhilosophers {
    // create philosophers/threads
    public static void main(String[] args){
        philosophers p1 = new philosophers(1, philosophers.forks[1], philosophers.forks[0]);
        philosophers p2 = new philosophers(2, philosophers.forks[2], philosophers.forks[1]);
        philosophers p3 = new philosophers(3, philosophers.forks[3], philosophers.forks[2]);
        philosophers p4 = new philosophers(4, philosophers.forks[4], philosophers.forks[3]);
        philosophers p5 = new philosophers(5, philosophers.forks[0], philosophers.forks[4]);

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();

    }
}
