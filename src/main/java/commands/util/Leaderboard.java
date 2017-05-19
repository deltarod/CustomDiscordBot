package commands.util;

/*
 * Work in progress to create a leaderboard
 * sorted from greatest to least
 */
public class Leaderboard<K extends Comparable<K>,V> {

    //simple node
    public class Node{
        K key;
        V value;
        Node next;
        Node previous;

        Node(K key, V value){
            this.key = key;
            this.value = value;
            next = null;
            previous = null;
        }
    }

    //iterator for getNext function
    private class Iterator{
        private Node current;
        private int index;
        Iterator(Node n){
            current = n;
            index = 0;
        }

        void next(){
            current = current.next;
            index++;
        }

    }


    private Node first;
    private int size;
    private Iterator iterator;

    public Leaderboard(){
        first = null;
        size = 0;
        iterator = null;
    }

    public void put(K key, V value){
        if(first == null){
            first = new Node(key, value);
            size++;
        }
        else{
            put(key, value, first);
        }
    }

    private void put(K key, V value, Node n){
        //if node > key, next node
        //if node = key, put into list
        //if node < key, put previous spot
        //greatest to least

        //next = lesser, previous = greater

        //if current node is greater, go to next node. if null, insert
        if(n.key.compareTo(key)>0){
            if(n.next == null){
                //if no next
                n.next = new Node(key,value);
                n.next.previous = n;
                size++;
            }
            else {
                //next node
                put(key, value, n.next);
            }
        }
        //if node = key, insert into next
        else if(n.key.compareTo(key) == 0){
            Node newN = new Node(key,value);
            newN.next = n.next;
            newN.previous = n;
            n.next = newN;
            if(newN.next != null) {
                newN.next.previous = newN;
            }
            size++;
        }
        //if node < key, n.prev = newN
        else{
            //if current node is first
            if(n == first){
                Node newN = new Node(key, value);
                newN.next = first;
                first.previous = newN;
                first = newN;
                size++;
            }
            //if not first, insert before
            else{
                Node newN = new Node(key, value);
                newN.next = n;
                newN.previous = n.previous;
                n.previous = newN;
                newN.previous.next = newN;
                size++;
            }
        }
    }

    public K getCurrentKey(){
        if(iterator == null){
            iterator = new Iterator(first);
            return iterator.current.key;
        }
        else{
            return iterator.current.key;
        }
    }
    public V getCurrentValue(){
        if(iterator == null){
            iterator = new Iterator(first);
            return iterator.current.value;
        }
        else{
            return iterator.current.value;
        }
    }

    public void next(){
        iterator.next();
    }

    public boolean hasCurrent(){
        if(iterator == null){
            iterator = new Iterator(first);
            return iterator.current != null;
        }
        else{
            return iterator.current != null;
        }
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node n = first;
        while(true){
            sb.append(n.value);
            sb.append(" : ");
            sb.append(n.key);
            sb.append("\n");
            n = n.next;
            if(n==null) break;
        }
        return sb.toString();
    }
}
