package LinkedIn.CA1;

import java.util.*;

public class MaxStack {
    DoublyListNode dummy = new DoublyListNode(0);
    DoublyListNode head;
    DoublyListNode tail;
    Integer max;
    //PriorityQueue<ListNode> maxHeap;
    TreeMap<Integer, List<DoublyListNode>> treeMap;
    public MaxStack() {
        head = dummy;
        tail = head;
        max = Integer.MIN_VALUE;
        treeMap = new TreeMap<>();
        // Can't use PriorityQueue since order can't be promised when same
        //maxHeap = new PriorityQueue<>((o1, o2) -> o2.value - o1.value);
    }
    // O(log n) to push, push on tail
    public void push(int x) {
        DoublyListNode newNode = new DoublyListNode(x);
        List<DoublyListNode> curList = treeMap.get(x);
        if (curList == null) {
            curList = new ArrayList<>();
        }
        curList.add(newNode);
        treeMap.put(x, curList);
        if (max == null || x > max) {
            max = x;
        }
        if (head == dummy) {
            head = newNode;
            tail = newNode;
            return;
        }
        tail.next = newNode;
        newNode.prev = tail;
        newNode.next = null;
        tail = newNode;
    }
    // O(log n) to pop, pop on tail
    public Integer pop() {
        if (tail == null) {
            return null;
        }
        Integer popValue = tail.value;
        if (popValue.equals(max)) {
            return popMax();
        }
        DoublyListNode prev = tail.prev;
        prev.next = null;
        tail.prev = null;
        tail = prev;
        List<DoublyListNode> curList = treeMap.get(popValue);
        if (curList.size() == 1) {
            treeMap.remove(popValue);
        } else {
            curList.remove(curList.size() - 1);
        }
        return popValue;
    }

    public Integer top() {
        if (tail == null) {
            return null;
        }
        return tail.value;
    }

    public Integer peekMax() {
        if (treeMap.isEmpty()) {
            return null;
        }
        return max;
    }

    public Integer popMax() {
        Integer curMax = peekMax();
        if (curMax != null) {
            List<DoublyListNode> curList = treeMap.get(curMax);
            DoublyListNode node = curList.get(curList.size() - 1);
            if (curList.size() == 1) {
                 treeMap.remove(curMax);
                 max = treeMap.isEmpty() ? null : treeMap.lastKey();
            } else {
                curList.remove(curList.size() - 1);
            }
            DoublyListNode prev = node.prev;
            DoublyListNode next = node.next;
            // remove only one element
            if (prev == null && next == null) {
                head = dummy;
                tail = head;
            } else if (prev == null) {
                next.prev = null;
                node.next = null;
                head = next;
            } else if (next == null) {
                prev.next = null;
                node.prev = null;
                tail = prev;
            } else {
                prev.next = next;
                next.prev = prev;
                node.prev = null;
                node.next = null;
            }
        }
        return curMax;
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
