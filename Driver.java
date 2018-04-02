import java.util.Iterator;

public class Driver {
    private static boolean soft = false;
    private static int mod = java.lang.reflect.Modifier.PRIVATE;

    public static void prod(boolean exp, String msg) {
        if (soft == false) {
            assert exp : msg;
        } else {
            if (exp == false) System.err.println(msg);
        }
    }
    public static void main(String[] args) {
        soft = args.length == 1 && args[0].equalsIgnoreCase("soft");
        if (soft) {
            System.out.println("NOTE: testing in soft mode!");
        }

        Integer[] ints   = new Integer[] { 1, 2, 3, 4, 1000, 2000, 3000, 4000 };
        Integer[] copies = new Integer[ints.length];
        for (int i = 0; i < ints.length; ++i) copies[i] = new Integer(ints[i]);
        Driver.test(ints, copies);
    }
    public static <T> void test(T[] elements, T[] copies) {
        /// Testing Contains
        LinkedSet<String> foo = new LinkedSet<String>(1);
        foo.add(new String("Strung"));
        prod(foo.contains("Strung"),"Contains seems broken");

        for (java.lang.reflect.Field f : foo.getClass().getDeclaredFields()) {
            prod((f.getModifiers() & mod) > 0, "Seems you have forgotten about encapsulation of fields!");
        }

        /// Testing ADD
        LinkedSet<T> l = new LinkedSet<T>(elements.length + 2);
        Collection<T> c = l;
        for (int i = 0; i < elements.length; ++i) {
            l.add(elements[i]);
        }
        prod(c.size() == elements.length,"Set has wrong size!");
        for (int i = 0; i < elements.length; ++i) {
            l.add(elements[i]);
        }
        prod(c.size() == elements.length,"Set is a multi-sst (but should not be) (identitical duplicates found)");
        for (int i = 0; i < copies.length; ++i) {
            l.add(copies[i]);
        }
        prod(c.size() == elements.length,"Set is a multi-set (but should not be) (equivalent duplicates found)");

        for (int i = 0; i < copies.length; ++i) {
            prod(l.contains(copies[i]),"Contains seems broken -- expected " + copies[i] + " in set");
        }
        for (int i = 0; i < elements.length; ++i) {
            prod(l.contains(elements[i]),"Contains seems broken -- expected " + elements[i] + " in set");
        }

        Collection<T> copy = Collection.copy(c);
        prod(copy instanceof LinkedSet,"LinkedSet does not override copy()!");
        ((Set<T>) copy).remove(elements[0]);

        prod(((Set<T>) copy).size() < c.size(),"Remove seems broken 1 ");
        prod(((Set<T>) copy).contains(elements[0]) == false,"Remove seems broken 2 ");

        LinkedSet<Integer> i1 = new LinkedSet<Integer>(10);
        LinkedSet<Integer> i2 = new LinkedSet<Integer>(10);

        prod(i1.addAll(i2) == 0,"addAll seems broken");
        i2.add(4711);
        prod(i1.addAll(i2) == 1,"addAll seems broken still");

        i1.add(new Integer(10000));
        i2.add(new Integer(10000));
        prod(i1.equals(i2),"Set equals not properly implemented");

        LinkedSet<Integer> i3 = new LinkedSet<Integer>(10);
        LinkedSet<Integer> i4 = new LinkedSet<Integer>(10);
        i3.add(42000);
        i3.add(4200);
        i4.add(4200);
        i4.add(42000);
        prod(i3.equals(i4),"Set equals seems to be ordered...");

        Object[] ints = i1.asArray();
        prod(ints.length == 2,"asArray seems broken 1");
        prod(ints[0].equals(4711) ,"asArray seems broken 2");
        prod(ints[1].equals(10000),"asArray seems broken 3");

        Iterator<T> iter = c.iterator();
        Object[] ts = l.asArray();
        for (int i = 0; i < ts.length; ++i) {
            prod(iter.hasNext(),"Iterator seems broken 1");
            prod(iter.next().equals(ts[i]),"Iterator seems broken 2");
        }

        i2.add(new Integer(42));
        prod(i2.removeAll(i1) == 2,"removeAll seems broken 1");
        prod(i2.size() == 1,"removeAll or size seem broken");

        copy = LinkedSet.copy(c);

        prod(c.equals(copy) && copy.equals(c),"Equals seem broken and possibly not symmetric!");

        /// Testing MaxCapacity
        try {
            LinkedSet<Object> stupid = new LinkedSet<Object>(0);
            stupid.add(new String());
            prod(false,"Managed to add more elements than max capacity");
        } catch (SetFullException sfe) {
            /// All good
        }
        try {
            LinkedSet<Object> stupid = new LinkedSet<Object>(0);
            stupid.add("Object");
            prod(false,"Managed to add more elements than max capacity");
        } catch (SetFullException sfe) {
            /// All good
        }


        if (soft) {
            System.err.println("-------------------------");
            System.err.println("If you didn't see any error messages above, time to run make test (not make test-soft)!");
        } else {
            System.err.println("All tests pass! Well done!");
        }
    }
}
