package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;
import datastructures.worklists.ArrayStack;

import java.util.*;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<A, HashTrieNode>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            ArrayStack<Entry<A, HashTrieNode>> ret = new ArrayStack<>();

            for (Item<A, HashTrieNode> item : pointers) {
                ret.add(new AbstractMap.SimpleEntry(item.key, item.value));
            }
            return ret.iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
        this.size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V retVal = null;

        if (this.root == null) {
            this.root = new HashTrieNode();
        }
        HashTrieNode node = (HashTrieNode)this.root;

        for (A element : key) {
            if (node.pointers.find(element) == null) {
                node.pointers.insert(element, new HashTrieNode());
            }
            node = node.pointers.find(element);
        }
        retVal = node.value;
        node.value = value;

        if (retVal == null) {
            size++;
        }
        return retVal;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (this.root == null) {
            return null;
        }
        HashTrieNode node = (HashTrieNode) this.root;
        for (A element : key) {
            node = node.pointers.find(element);
            if (node == null) {
                return null;
            }
        }
        return node.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        if (this.root == null) {
            return false;
        }
        HashTrieNode node = (HashTrieNode) this.root;
        for (A element : key) {
            node = node.pointers.find(element);
            if (node == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
