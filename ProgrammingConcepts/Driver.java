// Program written by Nicolas Lauzon, Student ID 2495040.
import java.util.*;
public class Driver {
    static class MyArrayList<E> implements List<E> {
        private ListElement<E> head;
        private ListElement<E> tail;
        private int size;
        public MyArrayList() {}
        @Override public boolean add(E c) {
            ListElement<E> e = new ListElement<>(null, null, c);
            if (isEmpty()) {head = e; tail = e; size = 1;}
            else {
                ListElement<E> t = tail;
                t.setNext(e); e.setPrev(t); e.setRef(c); e.setNext(null); tail = e; size++;
            }
            return true;
        }
        @Override public void add(int index, E c) {
            if (index == size) add(c);
            else {
                if (!isEmptyOrIndexOutOfRange(index)) {
                    ListElement<E> e1 = new ListElement<>();
                    ListElement<E> e2 = head;
                    if (index == 0) {head = e1; e1.setPrev(null); e1.setNext(e2); e2.setPrev(e1);}
                    else {
                        for (int i = 0; i < index - 1; i++) e2 = e2.getNext();
                        ListElement<E> e3 = e2.getNext();
                        e2.setNext(e1); e1.setPrev(e2); e1.setNext(e3); e3.setPrev(e1);
                    }
                    e1.setRef(c); size++;
                }
            }
        }
        @Override public boolean addAll(Collection<? extends E> c) {return false;}
        @Override public boolean addAll(int index, Collection<? extends E> c) {return false;}
        @Override public void clear() {
            ListElement<E> e1 = head;
            for (int i = 0; i < size; i++) {
                ListElement<E> e2 = e1.getNext();
                e1.setPrev(null); e1.setRef(null); e1.setNext(null);
                e1 = e2;
            }
            head = null; tail = null; size = 0;
        }
        @Override public boolean contains(Object c) {
            if (isEmpty()) return false;
            ListElement<E> e = head;
            for (int i = 0; i < size; i++) {
                if (e.getRef() == c) return true;
                else e = e.getNext();
            }
            return false;
        }
        @Override public boolean containsAll(Collection<?> c) {return false;}
        @Override public E get(int index) {
            if (isEmptyOrIndexOutOfRange(index)) return null;
            return traverseForwardOrBackward(index).getRef();
        }
        @Override public int indexOf(Object c) {
            if (isEmpty()) return -1;
            ListElement<E> e = head;
            for (int i = 0; i < size; i++) {
                if (c == e.getRef()) return i;
                e = e.getNext();
            }
            return -1;
        }
        @Override public boolean isEmpty() {return (size == 0);}
        @Override public Iterator<E> iterator() {return null;}
        @Override public int lastIndexOf(Object c) {
            if (isEmpty()) return -1;
            ListElement<E> e = head;
            int index = -1;
            for (int i = 0; i < size; i++) {
                if (c == e.getRef()) index = i;
                e = e.getNext();
            }
            return index;
        }
        @Override public ListIterator<E> listIterator() {return null;}
        @Override public ListIterator<E> listIterator(int index) {return null;}
        @Override public E remove(int index) {
            if (isEmptyOrIndexOutOfRange(index)) return null;
            ListElement<E> e1 = new ListElement<>(null, null, null);
            ListElement<E> e2 = head;
            ListElement<E> e3 = e2.getNext();
            for (int i = 0; i < index; i++) {e1 = e2; e2 = e3; e3 = e3.getNext();}
            if (index == 0) {head = e3; e3.setPrev(null); size--;}
            else if (index == size -1) {tail = e1; e1.setNext(null); size--;}
            else {e1.setNext(e3); e3.setPrev(e1); size--;}
            return e2.getRef();
        }
        @Override public boolean remove(Object c) {
            if (isEmpty()) return false;
            ListElement<E> e1 = new ListElement<>(null, null, null);
            ListElement<E> e2 = head;
            ListElement<E> e3 = e2.getNext();
            for (int i = 0; i < size; i++) {
                if (c == e2.getRef()) {
                    if (i == 0) {head = e3; e3.setPrev(null); size--; return true;}
                    else if (i == size -1) {tail = e1; e1.setNext(null); size--; return true;}
                    else {e1.setNext(e3); e3.setPrev(e1); size--; return true;}
                }
                else {e1 = e2; e2 = e3; e3 = e3.getNext();}
            }
            return false;
        }
        @Override public boolean removeAll(Collection<?> c) {return false;}
        @Override public boolean retainAll(Collection<?> c) {return false;}
        @Override public int size() {return size;}
        @Override public E set(int index, E c) {
            if (isEmptyOrIndexOutOfRange(index)) return null;
            ListElement<E> e = traverseForwardOrBackward(index);
            E cResult = e.getRef(); e.setRef(c);
            return cResult;
        }
        @Override public List<E> subList(int fromIndex, int toIndex) {return null;}
        @Override public Object[] toArray() {return new Object[0];}
        @Override public <T> T[] toArray(T[] a) {return a;}
        public String toString() {
            StringBuilder result;
            if (isEmpty()) result = new StringBuilder("[]");
            else {
                ListElement<E> e = head;
                result = new StringBuilder("[");
                for (int i = 0; i < size; i++) {
                    if (i == size - 1) result.append(e.getRef().toString());
                    else result.append(e.getRef().toString()).append(", ");
                    e = e.getNext();
                }
                result.append("]");
            }
            return result.toString();
        }
        private boolean isEmptyOrIndexOutOfRange(int index) {
            if (isEmpty() || (index < 0) || (index >= size)) {
                System.out.println("Empty list or index out of range.");
                return true;
            }
            return false;
        }
        private ListElement<E> traverseForwardOrBackward(int index) {
            ListElement<E> e;
            if (2 * (index + 1) <= size) {e = head; for (int i = 0; i < index; i++) e = e.getNext();}
            else {e = tail; for (int i = 0; i < (size - index - 1); i++) e = e.getPrev();}
            return e;
        }
    }
    static class ListElement<E> {
        private ListElement<E> prev;
        private ListElement<E> next;
        private E ref;
        public ListElement() {}
        public ListElement(ListElement<E> prev, ListElement<E> next, E ref) {
            this.prev = prev; this.next = next; this.ref = ref;
        }
        public ListElement<E> getPrev() {return prev;}
        public ListElement<E> getNext() {return next;}
        public E getRef() {return ref;}
        public void setPrev(ListElement<E> prev) {this.prev = prev;}
        public void setNext(ListElement<E> next) {this.next = next;}
        public void setRef(E ref) {this.ref = ref;}
    }
    static class HondaCivic {
        private static int numberOfCarsInStock = 0;
        private final String[] availableColor = {"silver", "grey", "black", "white", "blue", "red"};
        private String model;
        private int numberOfDoors;
        private String color;
        private static double price; //The price of the car is common to all the HondaCivic objects created.
        public HondaCivic() {numberOfCarsInStock++;}
        public HondaCivic(String model, int numberOfDoors, String color, double newPrice) {
            this();
            this.model = model;
            this.numberOfDoors = numberOfDoors;
            this.color = colorCheck(color);
            price = priceCheck(newPrice);
        }
        public String getModel() {return model;}
        public int getNumberOfDoors() {return numberOfDoors;}
        public String getColor() {return color;}
        public double getPrice() {return price;}
        public void setModel(String model) {this.model = model;}
        public void setNumberOfDoors(int numberOfDoors) {this.numberOfDoors = numberOfDoors;}
        public void setColor(String color) {this.color = colorCheck(color);}
        public void setPrice(double newPrice) {price = priceCheck(newPrice);}
        private String colorCheck(String s) {
            String s1 = s.toLowerCase();
            boolean match = false;
            for (String s2 : availableColor) {if (s2.equals(s1)) {match = true; break;}}
            if (!match) {
                s1 = "white";
                System.out.println("Selected color unavailable. White color assigned by default.");
            }
            return s1;
        }
        private double priceCheck(double x) {
            if (x > 40000.0) {System.out.println("Price adjusted to the 40000.0 ceiling."); return 40000.0;}
            if (x < 20000.0) {System.out.println("Price adjusted to the 20000.0 floor."); return 20000.0;}
            return x;
        }
        public String toString() {
            return "[Model = " +  getModel() + ", Number of doors: " + getNumberOfDoors() +
                    ", Color: " + getColor() + ", Price: " + getPrice() + ".]";
        }
        public boolean equals(HondaCivic c){
            return ((this.model.equals(c.model)) && (this.numberOfDoors == c.numberOfDoors)
                    && (this.color.equals(c.color))); // && (this.price == c.price);
        }
        public void sell() {numberOfCarsInStock--;}
    }
    static class Animal {
        private int age;
        private double height; // in feet
        private String name;
        public Animal() {}
        public Animal(int age, double height, String name) {
            this.age = age; this.height = height; this.name = name;
        }
        public Animal(Animal a) {
            this.age = a.age; this.height = a.height; this.name = a.name;
        }
        public int getAge() {return age;}
        public double getHeight() {return height;}
        public String getName() {return name;}
        public void setAge(int age) {this.age = age;}
        public void setHeight(double height) {this.height = height;}
        public void setName(String name) {this.name = name;}
        public String toString() {
            return getName() + ", " + getAge() + " years old, " + getHeight() + " feet height. I am an animal!";
        }
        public boolean equals(Animal a) {
            return ((this.age == a.age) && (this.height == a.height) && (this.name.equals(a.name)));
        }
        public String speak() {return "Ho Ho Ho";}
    }
    static class Cat extends Animal {
        private int legs;
        public Cat() {super();}
        public Cat(int age, double height, String name, int legs) {
            super(age, height, name); this.legs = legs;
        }
        public Cat(Cat c) {super(c); this.legs = c.legs;}
        public int getLegs() {return legs;}
        public void setLegs(int legs) {this.legs = legs;}
        public String toString() {return "[" + super.toString() + " I have " + getLegs() + " legs. I am a cat!]";}
        public boolean equals(Cat c) {return (super.equals(c) && (this.legs == c.legs));}
        @Override public String speak() {return "Mew Mew Mew";}
    }
    static class Snake extends Animal {
        public Snake() {super();}
        public Snake(int age, double height, String name) {super(age, height, name);}
        public Snake(Snake s) {super(s);}
        public String toString() {
            if (this instanceof Cobra) return super.toString();
            else return "[" + super.toString() + " I am a snake!]";
        }
        public boolean equals(Snake s) {return super.equals(s);}
        @Override public String speak() {return "Hiss Hiss Hiss";}
    }
    static class Cobra extends Snake {
        public Cobra() {super();}
        public Cobra(int age, double height, String name) {super(age, height, name);}
        public Cobra(Cobra k) {super(k);}
        public String toString() {return "[" + super.toString() + " I am a cobra!]";}
        public boolean equals(Cobra k) {return super.equals(k);}
        @Override public String speak() {return "Buss Buss Buss";}
    }
    static class Customer {
        private String name;
        private String city;
        private String phone;
        private int age;
        public Customer() {}
        public Customer(String name, String city, String phone, int age) {
            this.name = name; this.city = city; this.phone = phone; this.age = age;
        }
        public String getName() {return name;}
        public String getCity() {return city;}
        public String getPhone() {return phone;}
        public int getAge() {return age;}
        public void setName(String name) {this.name = name;}
        public void setCity(String city) {this.city = city;}
        public void setPhone(String phone) {this.phone = phone;}
        public void setAge(int age) {this.age = age;}
        public String toString() {
            return "[name = " + getName() + ", city = " + getCity() + ", phone = "
                    + getPhone() + ", age = " + getAge() + "]";
        }
    }
    static void compareCars(List<String> ar) {
        ar.add("Toyota"); ar.add("Honda"); ar.add("Audi");
        ar.add(0, "Lexus");
        ar.add("VW");
        try {ar.add(2, "Lexus"); ar.add(5, "Lexus"); ar.add(10, "Lexus");}
        catch (IndexOutOfBoundsException ex) {System.out.println("IndexOutOfBounds exception");}
        System.out.println(ar.contains("Toyota"));
        System.out.println(ar.contains("VW"));
        System.out.println(ar.get(2));
        try {System.out.println(ar.get(12));}
        catch (IndexOutOfBoundsException e) {System.out.println("Index out of Bounds Exception");}
        System.out.println(ar.indexOf("Lxx"));
        System.out.println(ar.indexOf("Audi"));
        System.out.println(ar.lastIndexOf("Lexus"));
        System.out.println(ar.remove(0));
        System.out.println(ar.remove(4));
        System.out.println(ar.remove("Lexus"));
        System.out.println(ar.remove("VW"));
        System.out.println(ar.remove("Audi"));
        System.out.println(ar.set(1, "Subaru"));
        System.out.println(ar.size());
        System.out.println(ar);
        ar.clear();
        System.out.println(ar.size());
    }
    static <E> void compareGeneric(List<E> ar, E o1, E o2, E o3, E o4, E o5) {
        ar.add(o1); ar.add(o2); ar.add(o3); ar.add(o4);
        ar.add(0, o5);
        System.out.println(ar);
        System.out.println(ar.size());
        System.out.println(ar.contains(o5));
        System.out.println(ar.indexOf(o2));
        System.out.println(ar.get(0));
        System.out.println(ar.get(3));
        System.out.println(ar);
        ar.clear();
        System.out.println(ar);
    }
    static void compareCustomers(List<Customer> ar) {
        Customer c1 = new Customer("Steeve", "Montreal", "514-666-1234", 15);
        Customer c2 = new Customer("David", "Toronto", "514-777-1234", 16);
        Customer c3 = new Customer("Belal", "Montreal", "514-999-1234", 16);
        Customer c4 = new Customer("Ana", "Montreal", "514-666-1234", 15);
        Customer c5 = new Customer("Lele", "Toronto", "514-777-1234", 16);
        compareGeneric(ar, c1, c2, c3, c4, c5);
    }
    static void compareAnimals(List<Animal> ar) {
        Cat a1 = new Cat(5,0.8,"NiceCat",4);
        Cat a2 = new Cat(4, 1.2, "ThreeLeggedCat", 3);
        Snake a3 = new Snake(19, 5.5, "VeryOldSnake");
        Snake a4 = new Snake(2, 7.0, "SneakySnake");
        Cobra a5 = new Cobra(8,8.0,"FearsomeCobra");
        compareGeneric(ar, a1, a2, a3, a4, a5);
    }
    static void compareHondaCivics(List<HondaCivic> ar) {
        HondaCivic t1 = new HondaCivic("CX", 3, "red", 25000.0);
        HondaCivic t2 = new HondaCivic("MX", 5, "grey", 22000.0);
        ar.add(t1); ar.add(t2);
    }
    public static void main(String[] args) {
        // Program written by Nicolas Lauzon, Student ID 2495040.
        System.out.println("\nProgram written by Nicolas Lauzon, Student ID 2495040.");

        System.out.println("\nMyArrayList output for String objects:");
        System.out.println("======================================");
        MyArrayList<String> s1 = new MyArrayList<String>();
        compareCars(s1);
        System.out.println("\nArrayList output for String objects:");
        System.out.println("======================================");
        ArrayList<String> s2 = new ArrayList<String>();
        compareCars(s2);
        System.out.println("\nMyArrayList output for Customer objects:");
        System.out.println("========================================");
        MyArrayList<Customer> c1 = new MyArrayList<Customer>();
        compareCustomers(c1);
        System.out.println("\nArrayList output for Customer objects:");
        System.out.println("======================================");
        ArrayList<Customer> c2 = new ArrayList<Customer>();
        compareCustomers(c2);
        System.out.println("\nMyArrayList output for Animal objects:");
        System.out.println("========================================");
        MyArrayList<Animal> a1 = new MyArrayList<Animal>();
        compareAnimals(a1);
        System.out.println("\nArrayList output for Animal objects:");
        System.out.println("======================================");
        ArrayList<Animal> a2 = new ArrayList<Animal>();
        compareAnimals(a2);
        System.out.println("\nMyArrayList output for HondaCivic objects:");
        System.out.println("============================================");
        MyArrayList<HondaCivic> h1 = new MyArrayList<HondaCivic>();
        compareHondaCivics(h1);
        System.out.println(h1);
        System.out.println("\nArrayList output for HondaCivic objects:");
        System.out.println("==========================================");
        ArrayList<HondaCivic> h2 = new ArrayList<HondaCivic>();
        compareHondaCivics(h2);
        System.out.println(h2);
        System.out.println();
        if (s1.toString().equals(s2.toString())) System.out.println("Great! Cars list");
        else System.out.println("Check Cars list!");
        if (c1.toString().equals(c2.toString())) System.out.println("Great! Customers list");
        else System.out.println("Check Customers list!");
        if (a1.toString().equals(a2.toString())) System.out.println("Great! Animal list");
        else System.out.println("Check Animal list!");
        if (h1.toString().equals(h2.toString())) System.out.println("Great! HondaCivic list");
        else System.out.println("Check HondaCivic list!");
    }
}