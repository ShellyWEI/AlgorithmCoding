package LinkedIn.CA1;

import java.util.*;

public class MaxStack {
    DoublyListNode head;
    DoublyListNode tail;
    //PriorityQueue<DoublyListNode> maxHeap;
    TreeMap<Integer, List<DoublyListNode>> map;
    public MaxStack() {
        this.head = null;
        this.tail = null;
        map = new TreeMap<>();
        // Can't use PriorityQueue since order can't be promised when same
        //maxHeap = new PriorityQueue<>((o1, o2) -> o2.value - o1.value);
    }
    // O(log n) to push, push on tail
    public void push(int x) {
        addToStack(x);
    }
    // O(log n) to pop, pop on tail
    public Integer pop() {
        int popValue = tail.value;
        removeFromStack(popValue);
        return popValue;
    }

    public Integer top() {
        if (tail == null) {
            return null;
        }
        return tail.value;
    }

    public Integer peekMax() {
        if (map.isEmpty()) {
            return null;
        }
        return map.lastKey();
    }

    public Integer popMax() {
        int peekMax = map.lastKey();
        removeFromStack(peekMax);
        return peekMax;
    }

    private void addToStack(int value) {
        if (head == null) {
            head = new DoublyListNode(value);
            tail = head;
        } else {
            DoublyListNode newNode = new DoublyListNode(value);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        List<DoublyListNode> sameValueNodes = map.computeIfAbsent(value, k -> new LinkedList<>());
        sameValueNodes.add(tail);
    }

    private void removeFromStack(int key) {
        DoublyListNode node = removeFromMap(key);
        // only one node;
        if (node.next == null && node.prev == null) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            // remove head
            head.next.prev = null;
            head = head.next;
        } else if (node.next == null) {
            // remove tail
            node.prev.next = null;
            tail = node.prev;
        } else {
            //remove in the middle
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private DoublyListNode removeFromMap(int key) {
        List<DoublyListNode> sameValueNodes = map.get(key);
        DoublyListNode nodeToBeRemoved = sameValueNodes.get(sameValueNodes.size() - 1);
        sameValueNodes.remove(sameValueNodes.size() - 1);
        if (sameValueNodes.isEmpty()) {
            map.remove(key);
        }
        return nodeToBeRemoved;
    }

    public static void main(String[] args) {
        MaxStack obj = new MaxStack();
        obj.push(-1919630);
        obj.push(7447491);
        obj.peekMax();
        obj.push(-2983652);
        obj.top();
        obj.popMax();
        obj.top();
        obj.push(-3334898);
        obj.push(8846062);
        obj.push(2479120);
        obj.top();
        obj.push(7050263);
        obj.pop();
        obj.peekMax();
        obj.popMax();
        obj.push(-3743643);
        obj.popMax();
        obj.push(-6196638);
        obj.top();
        obj.top();
        obj.popMax();
        obj.popMax();
        obj.push(-6436831);
        obj.push(-2835331);
        obj.push(3549770);
        obj.peekMax();
        obj.pop();
       // "push","peekMax","push","popMax","push","peekMax","pop","top","peekMax","top","peekMax","popMax","pop","top","push","peekMax","push","top","push","push","pop","push","push","push","popMax","top","push","popMax","peekMax","peekMax","push","pop","push","popMax","push","push","popMax","peekMax","pop","push","peekMax","popMax","popMax","popMax","push","push","peekMax","peekMax","pop","pop","popMax","push","peekMax","pop","top","top","push","push","top","push","pop","push","peekMax","popMax","push","peekMax","top","top","popMax","push","push","push","popMax"]
//[2643568],[],[-7853726],[],[-369996],[],[],[],[],[],[],[],[],[],[-9254475],[],[4462697],[],[7055993],[-4050360],[],[7162941],[-9620570],[6295114],[],[],[6366218],[],[],[],[-4009957],[],[4552628],[],[-7488568],[9893848],[],[],[],[1477827],[],[],[],[],[333068],[6486948],[],[],[],[],[],[-4255526],[],[],[],[],[-1598000],[-5135401],[],[-6279567],[],[-9375733],[],[],[-3078338],[],[],[],[],[8800414],[-4237017],[945773],[]]
        obj.push(2643568);
        obj.peekMax();
        obj.push(-7853726);
        obj.popMax();
        obj.push(-369996);
        obj.peekMax();

    }
}
