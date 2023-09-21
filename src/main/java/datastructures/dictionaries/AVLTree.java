package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    // TODO: Implement me!
    private class AVLNode extends BSTNode {
        private int height;

        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
        }
    }

    V oldValue = null;

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        root = insertHelper((AVLNode ) root, key, value);
        return oldValue;
    }

    private AVLNode insertHelper(AVLNode node, K key, V value) {
        if (node == null) {
            node = new AVLNode(key, value);
            size++;
            return node;
        }
        if (key.compareTo(node.key) == 0) {
            oldValue = node.value;
            node.value = value;
        } else if (key.compareTo(node.key) < 0) {
            node.children[0] = insertHelper((AVLNode ) node.children[0], key, value);
            AVLNode left = (AVLNode ) node.children[0];
            AVLNode right = (AVLNode ) node.children[1];

            if (height(left) - height(right) == 2) {
                if( key.compareTo(left.key) < 0)
                    node = rotateWithLeft(node);
                else
                    node = doubleWithLeft(node);
            }
        } else {
            node.children[1] = insertHelper((AVLNode) node.children[1], key, value);
            AVLNode left = (AVLNode) node.children[0];
            AVLNode right = (AVLNode) node.children[1];
            if (height(right) - height(left) == 2) {
                if (key.compareTo(right.key) > 0)
                    node = rotateWithRight(node);
                else
                    node = doubleWithRight(node);
            }
        }
        node.height = Math.max(height((AVLNode ) node.children[0]), height((AVLNode ) node.children[1]) ) + 1;
        return node;
    }

    private AVLNode rotateWithLeft(AVLNode node) {
        AVLNode temp = (AVLNode ) node.children[0];
        node.children[0] = temp.children[1];
        temp.children[1] = node;
        node.height = Math.max(height((AVLNode) node.children[0]), height((AVLNode) node.children[1])) + 1;
        temp.height = Math.max(height((AVLNode) temp.children[0]), height((AVLNode) temp.children[0])) + 1;
        return temp;
    }

    private AVLNode rotateWithRight(AVLNode node) {
        AVLNode temp = (AVLNode) node.children[1];
        node.children[1] = temp.children[0];
        temp.children[0] = node;
        node.height = Math.max(height((AVLNode) node.children[0]), height((AVLNode) node.children[1])) + 1;
        temp.height = Math.max(height((AVLNode) temp.children[0]), height((AVLNode) temp.children[0])) + 1;
        return temp;
    }

    private AVLNode doubleWithLeft(AVLNode node) {
        node.children[0] = rotateWithRight((AVLNode) node.children[0]);
        return rotateWithLeft(node);
    }

    private AVLNode doubleWithRight(AVLNode node) {
        node.children[1] = rotateWithLeft((AVLNode )node.children[1]);
        return rotateWithRight(node);
    }

    private int height(AVLNode node ) {
        return node == null ? -1 : node.height;
    }

}
