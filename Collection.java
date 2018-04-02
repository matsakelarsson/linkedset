import java.util.Iterator;

public class Collection<T> implements Iterable<T> {
    /// Upper bounds the number of elements a collection may hold at any point
    private final int maxCapacity;
    /// Constructs a collection with a given maximum capacity
    public Collection(int maxCapacity) { this.maxCapacity = maxCapacity; }
    /// Returns the maximum capacity of the current collection
    public int maxCapacity() { return this.maxCapacity; }
    /// Returns an iterator which allows O(elements) iteration over the set
    public Iterator<T> iterator() {
        throw new RuntimeException("ERROR: this method must be overridden in a subclass!");
    }
    /// Removes elem if it is in the set
    /// Returns true if elem was removed
    public boolean remove(T elem){
        throw new RuntimeException("ERROR: this method must be overridden in a subclass!");
    }
    /// Adds elem to the set unless it is already in the set
    /// Returns true if elem was added
    public boolean add(T elem){
        throw new RuntimeException("ERROR: this method must be overridden in a subclass!");
    }
    /// Creates a new empty collection of the same type as the current this
    /// Needs overriding in subclass!
    public Collection<T> newEmptyCollection(int maxCapacity) {
        return new Collection<T>(maxCapacity);
    }
    /// Returns a copy of the collection
    /// No need to change or do anything with this
    public static <T> Collection<T> copy(Collection<T> c) {
        Collection<T> copy = c.newEmptyCollection(c.maxCapacity());
        for (T elem : c) {
            ((Set<T>) copy).add(elem);
        }
        return copy;
    }
    /// Returns the number of elements in the set
    public int size() {
        throw new RuntimeException("ERROR: this method must be overridden in a subclass!");
    }
    /// Use this if you want to print your set!
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        Iterator<T> iter = this.iterator();
        if (iter.hasNext()) {
            sb.append(iter.next());
        }
        while (iter.hasNext()) {
            sb.append(", " + iter.next());
        }
        sb.append("}");
        return sb.toString();
    }
}
