package LinkedIn.CA2;

public class LinkedListMerge {
    public SingleListNode isMerged (SingleListNode one, SingleListNode two) {
        SingleListNode cur1 = one;
        SingleListNode cur2 = two;
        while (cur1 != null || cur2 != null) {
            // shorter one reaches end, point to other to continue loop, then both of them would start with same page
            // longer one also points to other
            if (cur1 == null) {
                cur1 = two;
            }
            if (cur2 == null) {
                cur2 = one;
            }
            // find merged node
            if (cur1 == cur2) {
                return cur1;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return null;
    }
    public SingleListNode findLoopEntry (SingleListNode head) {
        SingleListNode slow = head;
        SingleListNode fast = head;
        boolean meeted = false;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                fast = head;
                while (fast != slow) {
                    fast = fast.next;
                    slow = slow.next;
                }
                return slow;
            }
        }
        // no loop existed
        return null;
    }
}
