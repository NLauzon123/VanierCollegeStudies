public class ExceptionDemo {
    public static void f1(int n) throws ArithmeticException, MyException {
        System.out.println("Fonction f1 started.");
        switch (n) {
            case 3: throw new MyException("Nicolas, exception in f1, n = 3.");
            case 4, 5:
                try {
                    if (n ==4) throw new MyException("Nicolas, exception in f1, n = 4.");
                    System.out.println("Fonction f1, n = 5.");
                }
                catch(MyException ex) {
                    System.out.println("My exception in f1 " + ex.getMessage());
                    throw ex;
                }
                break;
            case 6, 7: f2(n); break;
            default: System.out.println(n / 0);
        }
        System.out.println("Fonction f1 ended.");
    }

    public static void f2(int n) throws MyException {
        System.out.println("Fonction f2 started.");
        f3(n);
        System.out.println("Fonction f2 ended.");
    }

    public static void f3(int n) throws MyException {
        System.out.println("Fonction f3 started.");
        if (n ==6) throw new MyException("Nicolas, exception in f3, n = 6.");
        System.out.println("Fonction f3 ended.");
    }

    public static void main(String[] args) {
        // n = 1: normal code, activate default.
        // n = 2: crashes the program.
        // n = 3: create MyException.
        // n = 4: create MyException and handles it, and rethrow it.
        // n = 5: does not throw MyException.
        // n = 6: method rewinding.
        // n = 7: method rewinding that does not throw an exception.
        int n = 7;

        if (n == 2) throw new MyException("Nicolas creeated an exception.");

        try {
            System.out.println("Try block started in the main.");
            f1(n);
            System.out.println("This will printed if there is no exception.");
        }
        catch(MyException ex) {System.out.println("My exception in the main, " +  ex.getMessage());}
        catch(ArithmeticException ex) {System.out.println("Arthmetic exception " +  ex.getMessage());}
        catch(RuntimeException ex) {System.out.println("Runtime exception " +  ex.getMessage());}
        catch(Exception ex) {System.out.println("Exception " +  ex.getMessage());}
        finally {System.out.println("Finally block started in the main. Always printed.");}

        System.out.println("This will be printed if exception handled.");
    }
}