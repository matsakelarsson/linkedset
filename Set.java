/// Do not change this file!
public interface Set<T> {
    /// Returns true if elem is in the set
    public boolean contains(T elem);
    /// Removes elem if it is in the set
    /// Returns true if elem was removed
    public boolean remove(T elem);
    /// Adds elem to the set unless it is already in the set
    /// Returns true if elem was added
    public boolean add(T elem);
    /// Removes all elements in other from the current set
    /// Returns the number of elements removed
    public int removeAll(Collection<T> other);
    /// Adds all elements in other to the current set
    /// Returns the number of elements added
    public int addAll(Collection<T> other);
    /// Returns all elements in the set in an array
    /// Order of elements in the array should match iteration order!
    public Object[] asArray();
    /// Returns the number of elements in the set
    public int size();
}
