package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. You should use the prime numbers in the given PRIME_SIZES list to resize
 * your HashTable with prime numbers.
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private double loadFactor;
    private int size;
    private Dictionary<K,V>[] hashTable;

    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        loadFactor = 0.0;
        size = 0;
        hashTable = new Dictionary[PRIME_SIZES[0]];
        for(int i = 0; i < 11; i ++) {
            hashTable[i] = newChain.get();
        }
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if (loadFactor >= 1) {
            hashTable = rehash(hashTable);
        }
        int index = Math.abs(key.hashCode() % hashTable.length);

        if (hashTable[index] == null) {
            hashTable[index] = newChain.get();
        }
        V retValue = null;
        if (this.find(key) == null) {
            size++;
        }
        else {
            retValue = this.find(key);
        }
        hashTable[index].insert(key, value);
        loadFactor = size / hashTable.length;
        return retValue;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int index = Math.abs(key.hashCode() % hashTable.length);
        if(hashTable[index] == null) {
            return null;
        }
        return hashTable[index].find(key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        Iterator<Item<K,V>> it = new Iterator<Item<K,V>>() {
            private int cur = 0;

            Iterator<Item<K,V>> iteratorItem = null;

            @Override
            public boolean hasNext() {
                if (iteratorItem == null) {
                    while (hashTable[cur] == null || hashTable[cur].size() == 0) {
                        cur++;
                        if (cur >= hashTable.length) {
                            return false;
                        }
                    }
                    iteratorItem = hashTable[cur].iterator();
                }
                if (iteratorItem.hasNext()) {
                    return true;
                }
                int temp = cur + 1;

                if (temp >= hashTable.length) {
                    return false;
                }
                while (hashTable[temp] == null || hashTable[temp].size() == 0) {
                    temp++;
                    if (temp >= hashTable.length) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public Item<K, V> next() {
                if(!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (iteratorItem.hasNext()) {
                    return iteratorItem.next();
                }
                cur++;
                while (hashTable[cur] == null || hashTable[cur].size() == 0) {
                    cur++;
                }
                iteratorItem = hashTable[cur].iterator();
                return iteratorItem.next();
            }
        };
        return it;
    }

    @Override
    public String toString() {
        String ret = "";

        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] == null)
                continue;
            ret += hashTable[i].toString();
        }
        return ret;
    }

    private Dictionary<K,V>[] rehash(Dictionary<K,V> array[]) {
        Dictionary<K,V>[] newArray;
        int curSize = array.length;
        int newSize = 0;
        int i = 0;
        for (i = 0; i < PRIME_SIZES.length; i++) {
            if (curSize < PRIME_SIZES[i]) {
                newSize = PRIME_SIZES[i];
                break;
            }
        }
        if (i == PRIME_SIZES.length) {
            newSize = 2 * curSize;
        }
        newArray = new Dictionary[newSize];

        for (i = 0; i < array.length; i++) {
            if (array[i] == null) {
                continue;
            }
            for (Item<K,V> item : array[i]) {
                int index = Math.abs(item.key.hashCode() % newArray.length);
                if (newArray[index] == null) {
                    newArray[index] = newChain.get();
                }
                newArray[index].insert(item.key, item.value);
            }
        }
        return newArray;
     }
}
