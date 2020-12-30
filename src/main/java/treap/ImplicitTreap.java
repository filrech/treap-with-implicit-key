package treap;

import java.util.*;

public class ImplicitTreap<T extends Comparable<T>> extends AbstractList<T> implements List<T> {

    private static class Node<T> {
        T value;
        double priority;
        Node<T> left = null;
        Node<T> right = null;
        int size;

        Node(T value) {
            this.value = value;
            this.size = 1;
            priority = Math.random();
        }
    }

    private Node<T> root = null;

    ImplicitTreap() {
        this.root = null;
    }

    ImplicitTreap(Node<T> node) {
        this.root = node;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public void clear() {
        this.root = null;
    }

    @Override
    public int size() {
        return root.size;
    }

    private int getSize(Node<T> node) {
        return node == null ? 0 : node.size;
    }

    private void calcSize(Node<T> node) {
        if (node == null) return;
        node.size = getSize(node.left) + getSize(node.right) + 1;
    }

    private class Pair {
        Node<T> left;
        Node<T> right;

        Pair(Node<T> left, Node<T> right) {
            this.left = left;
            this.right = right;
        }

        Pair() {
            this.left = null;
            this.right = null;
        }
    }

    public Node<T> merge(Node<T> leftNode, Node<T> rightNode) {
        if (leftNode == null) return rightNode;
        if (rightNode == null) return leftNode;

        Node<T> newRootNode;

        if (leftNode.priority > rightNode.priority) {
            leftNode.right = merge(leftNode.right, rightNode);
            newRootNode = leftNode;
        } else {
            rightNode.left = merge(leftNode, rightNode.left);
            newRootNode = rightNode;
        }
        calcSize(newRootNode);

        return newRootNode;
    }

    public Pair split(Node<T> treap, int pos) {
        if (treap == null) return new Pair();

        Pair splittedTreap;
        int l = getSize(treap.left);
        if (l >= pos) {
            splittedTreap = split(treap.left, pos);
            treap.left = splittedTreap.right;
            calcSize(treap);
            return new Pair(splittedTreap.left, treap);
        } else {
            splittedTreap = split(treap.right, pos - l - 1);
            treap.right = splittedTreap.left;
            calcSize(treap);
            return new Pair(treap, splittedTreap.right);
        }
    }

    @Override
    public T get(int pos) {
        if (pos > getSize(root) || pos <= 0) {
            throw new IllegalArgumentException("size");
        }
        return get(pos, root);
    }

    private T get(int pos, Node<T> node) {
        if (node == null) return null;

        int key = getSize(node.left) + 1;
        int cmp = Integer.compare(pos, key);
        if (cmp < 0) {
            return get(pos, node.left);
        } else if (cmp > 0) {
            return get(pos - getSize(node.left) - 1, node.right);
        }
        return node.value;
    }

    @Override
    public void add(int pos, T value) {
        Pair splittedTreap = split(root, pos - 1);
        this.root = merge(merge(splittedTreap.left, new Node<>(value)), splittedTreap.right);
    }

    @Override
    public T remove(int pos) {
        T valueToRemove = get(pos);
        if (valueToRemove == null) return null;
        Pair splittedTreap = split(root, pos);
        root = merge(splittedTreap.left.left, merge(splittedTreap.left.right, splittedTreap.right));
        return valueToRemove;
    }

    public void shiftLeft(int pos) {
        Pair splittedTreap = split(root, pos);
        root = merge(splittedTreap.right, splittedTreap.left);
    }

    public void shiftRight(int pos) {
        Pair splittedTreap = split(root, getSize(root) - pos);
        root = merge(splittedTreap.right, splittedTreap.left);
    }

    public void modifyValue(int pos, T value) {
        modifyValue(root, pos, value);
    }

    private void modifyValue(Node<T> treap, int pos, T value) {
        int l = getSize(treap.left) + 1;

        if (l == pos) {
            treap.value = value;
        } else if (l > pos) {
            modifyValue(treap.left, pos, value);
        } else {
            modifyValue(treap.right, pos - getSize(treap.left) - 1, value);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new TreapIterator();
    }

    public class TreapIterator implements Iterator<T> {
        ArrayDeque<Node<T>> dq = new ArrayDeque<>();
        Node<T> currentNode = null;

        void genIterator(Node<T> node) {
            if (node.left != null) {
                genIterator(node.left);
            }
            dq.addLast(node);
            if (node.right != null) {
                genIterator(node.right);
            }
        }

        TreapIterator() {
            if (root != null) {
                genIterator(root);
                currentNode = dq.getFirst();
            }
        }

        public boolean hasNext() {
            return !dq.isEmpty();
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            currentNode = dq.removeFirst();
            return currentNode.value;
        }
    }
}