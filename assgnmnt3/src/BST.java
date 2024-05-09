import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> {
    private Node root;
    private int size;
    public int size() {
        return size;
    }




    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public void put(K key, V val) {
        if (get(key) != null) {
            return;
        }
        root = insertByRoot(root, key, val);
    }

    private Node insertByRoot(Node root, K key, V val) {
        if (root != null) {
            if (key.compareTo(root.key) >= 0) {
                root.right = insertByRoot(root.right, key, val);
            } else {
                root.left = insertByRoot(root.left, key, val);
            }
            // Обновление размера дерева после добавления нового узла
            size++;

            return root;
        } else {
            // Увеличиваем размер дерева, так как создаем новый узел
            size++;

            return new Node(key, val);
        }
    }


    public V get(K key) {
        Node current = root;

        if (current != null && !current.key.equals(key)) {
            do {
                if (key.compareTo(current.key) < 0) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            } while (current != null && !current.key.equals(key));
        }

        return current != null ? current.val : null;
    }

    public void delete(K key) {
        boolean checkLeft = false;
        Node node = root;
        Node parent = root;

        while (node.key != key) {
            parent = node;

            if (node.key.compareTo(key) > 0) {
                checkLeft = true;
                node = node.left;
            } else {
                checkLeft = false;
                node = node.right;
            }
            if (node == null) {
                return;
            }
        }

        if (node.left != null || node.right != null) {
            if (node.right == null) {
                if (node == root) {
                    root    = node.left;
                } else if (checkLeft) {
                    parent.left = node.left;
                } else {
                    parent.right = node.left;
                }
            } else if (node.left == null) {
                if (node == root) {
                    root = node.right;
                } else if (checkLeft) {
                    parent.left = node.right;
                } else {
                    parent.right = node.right;
                }
            } else {
                Node temp = node;

                if (node == root) {
                    root = temp;
                } else if (checkLeft) {
                    parent.left = temp;
                } else {
                    parent.right = temp;
                }
                temp.left = node.left;
            }
        } else {
            if (node == root) {
                root = null;
            }
            if (checkLeft) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
    }

    public Iterable<K> iterator() {
        return (Iterable<K>) new BinarySearchTreeIterator<K>(this.root);
    }

    class BinarySearchTreeIterator<K extends Comparable<? super K>> implements Iterator<K> {

        private Stack<Node> stack = new Stack<>();
        private Node curr;
        private Node pending;

        BinarySearchTreeIterator(Node root) {
            if (root == null) {
                throw new IllegalArgumentException();
            }
            curr = root;
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            pending = stack.pop();
            curr = pending.right;
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            return (K) pending.key;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }
    }

    public static void main(String[] args) {
        BST<Integer, Integer> binarySearchTree = new BST<>();

        binarySearchTree.put(1, 5);
        binarySearchTree.put(2, 6);
        binarySearchTree.put(3, 7);
        binarySearchTree.put(4, 8);

        System.out.println(binarySearchTree.get(1));
        System.out.println(binarySearchTree.get(2));
        System.out.println(binarySearchTree.get(3));
        System.out.println(binarySearchTree.get(4));

        binarySearchTree.delete(2);
    }
}