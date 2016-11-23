import java.util.ArrayList;

public class VM {
    public static int populationSize = 100;
    public static VM[] population = new VM[populationSize];
    final int MAX = 1000;
    final byte LOAD = 0; // Reg = #1234
    final byte PUSH = 1; // push(Reg)
    final byte POP = 2; // Reg = pop()
    final byte MUL = 3; // Reg = reg*pop()
    final byte DIV = 4; // Reg = Reg/pop()
    final byte ADD = 5; // Reg = Reg+pop()
    final byte SUB = 6; // Reg = Reg-pop()
    final byte JIH = 7; // if Reg>0 then pc = pc + pop()

    ArrayList<Short> primeNumbers = new ArrayList<Short>();
    short mem[] = new short[MAX];
    short stack[] = new short[MAX];
    short sp, reg;
    int pc;

    VM() {
        this.pc = 0;
        this.sp = 0;
        this.reg = 0;
        this.initializeMemory();
        this.fillStackRandomly();
    }

    void push(short x) {

        if (isPrime(x)) {
            if (!primeNumbers.contains(x)) {
                primeNumbers.add(x);
                //System.out.println("Primzahl gefunden!");
            }
        }

        if (sp >= 0 && sp < MAX) {
            stack[sp++] = x;
        }
    }

    short pop() {
        sp--;
        if (sp < 0) {
            sp = (short) (Math.random() * (MAX-2));
        }
        return stack[sp];
    }

    void simulate() {
        int counter = 0;
        do {
            counter++;
            switch (mem[pc] & 7) {
                case LOAD: {
                    reg = (short) (mem[pc] >> 3);
                    push(reg);
                    pc++;
                    break;
                }
                case PUSH: {
                    push(reg);
                    pc++;
                    break;
                }
                case POP: {
                    reg = pop();
                    pc++;
                    break;
                }
                case MUL: {
                    reg = (short) (reg * pop());
                    push(reg);
                    pc++;
                    break;
                }
                case DIV: {
                    short d = pop();
                    if (d != 0) {
                        reg = (short) (reg / d);
                        push(reg);
                    }
                    pc++;
                    break;
                }
                case ADD: {
                    reg = (short) (reg + pop());
                    push(reg);
                    pc++;
                    break;
                }
                case SUB: {
                    reg = (short) (reg - pop());
                    push(reg);
                    pc++;
                    break;
                }
                case JIH: {
                    short d = pop();
                    if (pc + d >= 0) pc = (pc + d) % MAX;
                    else pc++;
                    break;
                }
            }
            pc = pc % MAX;
        } while (sp >= 0 && counter <= 10000);
    }

    public boolean isPrime(short x) {
        if (x < 3) {
            return x == 2;
        }
        if (x % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i < x; i += 2) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int getFitness() {
        return primeNumbers.size();
    }

    private void initializeMemory() {
        for (int i = 0; i < this.mem.length; i++) {
            this.mem[i] = (byte) (Math.random() * 8);
//			System.out.println("Random: " + this.mem[i]);
        }
    }

    private void fillStackRandomly() {
        for (int i = 0; i < this.stack.length; i++) {
            this.stack[i] = (byte) (Math.random() * 100);
//			System.out.println("Random: " + this.mem[i]);
        }
    }

    public static void createPopulation() {
        for (int i = 0; i < populationSize ; i++) {
            population[i] = new VM();
        }
    }

    public static void simulatePopulation() {
        for (int i = 0; i < populationSize; i++) {
            population[i].simulate();
        }
    }

    public static void main(String[] args) {

        createPopulation();
        simulatePopulation();

        for (int i = 0; i < populationSize; i++) {
            System.out.println("Fitness: " + population[i].getFitness());
        }

    }

}
